package ch.heigvd.amt.user.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    public static final String JWT_FILTER_PARAMETER_ID = "jwtSecret";

//    @Bean
//    public FilterRegistrationBean<JwtFilter> jwtFilterRegistration() {
//        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
//        JwtFilter filter = new JwtFilter();
//
//        registrationBean.setFilter(filter);
//        registrationBean.addUrlPatterns("/nope/*");
//        registrationBean.setOrder(1);
//        registrationBean.addInitParameter(JWT_FILTER_PARAMETER_ID, "secret");
//        return registrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean<PasswordResetFilter> passwordResetFilterFilterRegistrationBean() {
//        FilterRegistrationBean<PasswordResetFilter> registrationBean = new FilterRegistrationBean<>();
//        PasswordResetFilter filter = new PasswordResetFilter();
//
//        registrationBean.setFilter(filter);
//        registrationBean.addUrlPatterns("/passwords");
//        registrationBean.addInitParameter(JWT_FILTER_PARAMETER_ID, "secret");
//        return registrationBean;
//    }
}
