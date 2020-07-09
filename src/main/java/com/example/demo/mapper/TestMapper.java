package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 *把sql写在方法上(mapper文件上必须加上@Mapper注解)
 * @author jiangqd
 * @date 2019/1/12
 */
@Mapper
public interface TestMapper {

    /**
     * 查询
     * @return res
     */
    @Select("select username from user")
    List<String> getUsername ();


}
