package main.Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import main.ApplicationConfiguration;
import main.FilmLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by dryzu on 16.04.2018.
 */
@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
	//@Autowired
	private FilmLibrary library;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
		library = context.getBean(FilmLibrary.class);
		resp.getWriter().println(library == null);
		//super.doGet(req, resp);
	}
}

