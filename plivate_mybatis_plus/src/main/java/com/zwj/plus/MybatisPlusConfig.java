package com.zwj.plus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zwj
 * @date 2021年08月27日
 */
@Configuration
@MapperScan("com.zwj.mapper*")
public class MybatisPlusConfig {
    @Bean
    public TestPlus testPlus() {
        return new TestPlus();
    }
}
