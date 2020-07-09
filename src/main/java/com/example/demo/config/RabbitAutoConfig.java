package com.example.demo.config;

import java.time.Duration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author jiangqingdong
 * @version v1.0
 * @Description
 * @DATE 2020/7/9 0009 19:56
 * @Copyright Copyright © 2019 深圳花儿绽放网络科技股份有限公司. All rights reserved.
 */
@Configuration
@ConditionalOnClass({RabbitTemplate.class, Channel.class})
@EnableConfigurationProperties(RabbitProperties.class)
@Import(RabbitAnnotationDrivenConfiguration.class)
public class RabbitAutoConfig {

    @Configuration
    @ConditionalOnMissingBean(ConnectionFactory.class)
    protected static class RabbitConnectionFactoryCreator {
        //rabbitmq 连接工厂
        @Bean
        public CachingConnectionFactory rabbitConnectionFactory(RabbitProperties properties, ObjectProvider<ConnectionNameStrategy> connectionNameStrategy)
                throws Exception {
            PropertyMapper map = PropertyMapper.get();
            //创建连接工厂
            CachingConnectionFactory factory = new CachingConnectionFactory(getRabbitConnectionFactoryBean(properties).getObject());
            //连接地址
            map.from(properties::determineAddresses).to(factory::setAddresses);
            //生产端的消息确认
            map.from(properties::isPublisherConfirms).to(factory::setPublisherConfirms);
            //不可路由消息的处理
            map.from(properties::isPublisherReturns).to(factory::setPublisherReturns);
            //配置通信管道
            RabbitProperties.Cache.Channel channel = properties.getCache().getChannel();
            map.from(channel::getSize).whenNonNull().to(factory::setChannelCacheSize);
            map.from(channel::getCheckoutTimeout).whenNonNull().as(Duration::toMillis)
                    .to(factory::setChannelCheckoutTimeout);
            //配置管道缓存
            RabbitProperties.Cache.Connection connection = properties.getCache()
                    .getConnection();
            map.from(connection::getMode).whenNonNull().to(factory::setCacheMode);
            map.from(connection::getSize).whenNonNull()
                    .to(factory::setConnectionCacheSize);
            map.from(connectionNameStrategy::getIfUnique).whenNonNull()
                    .to(factory::setConnectionNameStrategy);
            return factory;
        }
    }

    @Configuration
    @Import(RabbitConnectionFactoryCreator.class)
    protected static class RabbitTemplateConfiguration {
        //模版配置类
        @Bean
        @ConditionalOnSingleCandidate(ConnectionFactory.class)
        @ConditionalOnMissingBean
        public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
            PropertyMapper map = PropertyMapper.get();
            RabbitTemplate template = new RabbitTemplate(connectionFactory);
            //配置消息转换器
            MessageConverter messageConverter = this.messageConverter.getIfUnique();
            if (messageConverter != null) {
                template.setMessageConverter(messageConverter);
            }
            //设置为true 那么不可达消息会交给RetrunConfirm 处理，若设置为false  消息队列直接删除该消息
            template.setMandatory(determineMandatoryFlag());
            //配置模版属性
            RabbitProperties.Template properties = this.properties.getTemplate();
            if (properties.getRetry().isEnabled()) {
                template.setRetryTemplate(createRetryTemplate(properties.getRetry()));
            }
            //设置超时时间
            map.from(properties::getReceiveTimeout).whenNonNull().as(Duration::toMillis)
                    .to(template::setReceiveTimeout);
            //配置 回复超时时间
            map.from(properties::getReplyTimeout).whenNonNull().as(Duration::toMillis)
                    .to(template::setReplyTimeout);
            //配置交换机
            map.from(properties::getExchange).to(template::setExchange);
            //配置路由key
            map.from(properties::getRoutingKey).to(template::setRoutingKey);
            return template;
        }

        private boolean determineMandatoryFlag() {
            Boolean mandatory = this.properties.getTemplate().getMandatory();
            return (mandatory != null) ? mandatory : this.properties.isPublisherReturns();
        }

        private RetryTemplate createRetryTemplate(RabbitProperties.Retry properties) {
            PropertyMapper map = PropertyMapper.get();
            RetryTemplate template = new RetryTemplate();
            SimpleRetryPolicy policy = new SimpleRetryPolicy();
            map.from(properties::getMaxAttempts).to(policy::setMaxAttempts);
            template.setRetryPolicy(policy);
            ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
            map.from(properties::getInitialInterval).whenNonNull().as(Duration::toMillis)
                    .to(backOffPolicy::setInitialInterval);
            map.from(properties::getMultiplier).to(backOffPolicy::setMultiplier);
            map.from(properties::getMaxInterval).whenNonNull().as(Duration::toMillis)
                    .to(backOffPolicy::setMaxInterval);
            template.setBackOffPolicy(backOffPolicy);
            return template;
        }

        @Bean
        @ConditionalOnSingleCandidate(ConnectionFactory.class)
        @ConditionalOnProperty(prefix = "spring.rabbitmq", name = "dynamic", matchIfMissing = true)
        @ConditionalOnMissingBean
        public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
            return new RabbitAdmin(connectionFactory);
        }
    }

    @Configuration
    @ConditionalOnClass(RabbitMessagingTemplate.class)
    @ConditionalOnMissingBean(RabbitMessagingTemplate.class)
    @Import(RabbitTemplateConfiguration.class)
    protected static class MessagingTemplateConfiguration {
        @Bean
        @ConditionalOnSingleCandidate(RabbitTemplate.class)
        public RabbitMessagingTemplate rabbitMessagingTemplate(
                RabbitTemplate rabbitTemplate) {
            return new RabbitMessagingTemplate(rabbitTemplate);
        }
    }

}
