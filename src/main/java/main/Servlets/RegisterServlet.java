package main.Servlets;

import main.ApplicationConfiguration;
import main.FilmLibrary;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by dryzu on 16.04.2018.
 */
@WebServlet(name = "registerServlet", urlPatterns = "/user/register")
public class RegisterServlet extends HttpServlet {
	private FilmLibrary library;

	public RegisterServlet() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
		library = (FilmLibrary) context.getBean("films");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().print(library.getUserManager().register(req.getParameter("name"), req.getParameter("login"), req.getParameter("pswd")));
	}
}
