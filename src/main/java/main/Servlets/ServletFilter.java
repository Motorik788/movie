package main.Servlets;


import main.ApplicationConfiguration;
import main.FilmLibrary;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dryzu on 16.04.2018.
 */
@WebFilter(urlPatterns = "/library/*")
public class ServletFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpSession session = req.getSession(false);
		servletResponse.setContentType("text/html;charset=windows-1251");
		if (session != null) {
//			ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
//			FilmLibrary library = context.getBean(FilmLibrary.class);
//			servletResponse.getWriter().print(library.getUserManager().getCurrentUser());
			if (session.getAttribute("login") != null) {
				filterChain.doFilter(servletRequest, servletResponse);
			} //else throw new ServletException("доступ запрещен");
			else servletResponse.getWriter().print("доступ запрещен");
		} //else throw new ServletException("доступ запрещен");
		else servletResponse.getWriter().print("доступ запрещен");
	}

	@Override
	public void destroy() {

	}
}
