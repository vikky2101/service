package com.demo.config;


import com.demo.models.QueryMapperFactory;
import com.demo.models.ResponseMapperFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

  @Bean(name = "queryMapperFactory")
  public FactoryBean queryMapperFactoryBean() {
    ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
    factoryBean.setServiceLocatorInterface(QueryMapperFactory.class);
    return factoryBean;
  }

  @Bean(name = "responseMapperFactory")
  public FactoryBean responseMapperFactory() {
    ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
    factoryBean.setServiceLocatorInterface(ResponseMapperFactory.class);
    return factoryBean;
  }
}
