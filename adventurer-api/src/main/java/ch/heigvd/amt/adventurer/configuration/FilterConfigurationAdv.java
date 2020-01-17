package ch.heigvd.amt.adventurer.configuration;

import ch.heigvd.amt.adventurer.api.util.JwtFilterAdv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class FilterConfigurationAdv {


    @Autowired
    private JwtFilterAdv jwtFilter;

    @Bean
    public FilterRegistrationBean<JwtFilterAdv> jwtFilterRegistration() {
        FilterRegistrationBean<JwtFilterAdv> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(jwtFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
