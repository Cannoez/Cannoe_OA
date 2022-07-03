package com.zeal.zeal_oa.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @version: java version 1.8
 * @author: zeal
 * @description:
 * @date: 2022-06-16 21:40
 */
@Configuration
public class DruidConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }
    //后台监控功能
    //因为springBoot 内置了servlet容器,所以没有web.xml,替代方法:ServletRegistrationBean
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean=new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");
        //后台需要有人登录 账号密码配置
        HashMap<String, String> initParameters = new HashMap<>();
        //增加配置
        initParameters.put("loginUsername","admin");//登录key是固定的 loginUsername loginPassword
        initParameters.put("loginPassword","123456");

        //允许谁可以访问
        initParameters.put("allow","");//空所有人都可以localhost本地访问
        //禁止谁可以访问
        //initParameters.put("zeal","zea的ip地址")
        bean.setInitParameters(initParameters);//设置初始化参数
        return bean;
    }
    //filter
    @Bean
    public FilterRegistrationBean WebStatFilter(){
        FilterRegistrationBean<Filter> bean=new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());
        //可以过滤哪些请求呢
        HashMap<String , String> initParameters = new HashMap<>();
       //这些东西不进行统计
        initParameters.put("exclusions","*.js,*.css,/druid/*,*.eot,*.woff,*.woff2,*.svg,*.ttf,*.all.min.js");
        bean.setInitParameters(initParameters);
       return bean;
    }
}
