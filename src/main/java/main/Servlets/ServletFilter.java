package main.Servlets;


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
		if (session != null) {
			if (session.getAttribute("login") != null && session.getAttribute("pswd") != null) {
				filterChain.doFilter(servletRequest, servletResponse);
			} else throw new ServletException("доступ запрещен");
		}
	}

	@Override
	public void destroy() {

	}
}
