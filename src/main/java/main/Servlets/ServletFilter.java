package main.Servlets;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by dryzu on 16.04.2018.
 */
@WebFilter(urlPatterns = "/")
public class ServletFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

	}

	@Override
	public void destroy() {

	}
}
