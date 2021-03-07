package com.liaomessi.design_patterns.state_pattern.factory;

import com.liaomessi.design_patterns.state_pattern.manager.state.MovieState;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Blue
 * @date 2021/3/6
 * @description
 */
@Configuration
public class FactoryConfig {
    @Bean
    public FactoryBean movieStateFactory() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(MovieStateFactory.class);
        return factoryBean;
    }
}
