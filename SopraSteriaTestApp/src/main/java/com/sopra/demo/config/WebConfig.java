package com.sopra.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class WebConfig  implements WebMvcConfigurer  {


    @Bean
    @Description("Thymeleaf template resolver serving HTML 5")
    public ClassLoaderTemplateResolver templateResolver() {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");

        return templateResolver;
    }

    @Bean
    @Description("Thymeleaf template engine with Spring integration")
    public SpringTemplateEngine templateEngine() {

       SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());

        return templateEngine;
    }

    @Bean
    @Description("Thymeleaf view resolver")
    public ViewResolver viewResolver() {

        ViewResolver viewResolver = new ThymeleafViewResolver();

        ((ThymeleafViewResolver) viewResolver).setTemplateEngine(templateEngine());
        ((ThymeleafViewResolver) viewResolver).setCharacterEncoding("UTF-8");

        return viewResolver;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(

                "/static/**")

                .addResourceLocations(
                        "classpath:/resources/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/createUser.html").setViewName("createUser");
        registry.addViewController("/loginSuccess.html").setViewName("loginSuccess");
        registry.addViewController("/admin.html").setViewName("admin");
        registry.addViewController("/modify.html").setViewName("modify");
        registry.addViewController("/loggedIn.html").setViewName("loggedIn");
        registry.addViewController("/createForm.html").setViewName("createForm");
        registry.addViewController("/adminDeny.html").setViewName("adminDeny");
        registry.addViewController("/answerQuestion.html").setViewName("answerQuestion");
        registry.addViewController("/answerSpecQuestion.html").setViewName("answerSpecQuestion");
        registry.addViewController("/radioAnswer.html").setViewName("radioAnswer");
        registry.addViewController("/checkBoxAnswer.html").setViewName("checkBoxAnswer");
        registry.addViewController("/createQuestions.html").setViewName("createQuestions");
        registry.addViewController("/chooseFormAndAnswers.html").setViewName("chooseFormAndAnswers");
        registry.addViewController("/showingFormAnswers.html").setViewName("showingFormAnswers");
        registry.addViewController("/sucess.html").setViewName("sucess");


    }
/*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/callback/", "/webjars/**", "/error**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }


 */

}