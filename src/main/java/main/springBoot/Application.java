package main.springBoot;

import main.Servlets.LoginServlet;
import main.Servlets.RegisterServlet;
import main.Servlets.ServletFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Created by dryzu on 19.04.2018.
 */
@SpringBootApplication(scanBasePackages = "main")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(
				Application.class, args);
	}

	@Bean
	public ServletRegistrationBean loginServlet()
	{
		ServletRegistrationBean bean = new ServletRegistrationBean(new LoginServlet(), "/user/login");
		bean.setLoadOnStartup(1);
		return bean;
	}

	@Bean
	public ServletRegistrationBean registerServlet()
	{
		ServletRegistrationBean bean = new ServletRegistrationBean(new RegisterServlet(), "/user/register");
		bean.setLoadOnStartup(1);
		return bean;
	}

	@Bean
	public FilterRegistrationBean securityServletFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new ServletFilter());
		bean.addUrlPatterns("/library/*");
		return bean;
	}
}
