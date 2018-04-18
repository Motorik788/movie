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
import org.springframework.stereotype.Component;

/**
 * Created by dryzu on 16.04.2018.
 */

@WebServlet(name = "loginServlet", urlPatterns = "/user/login")
public class LoginServlet extends HttpServlet {
	//@Autowired
	private FilmLibrary library;

	public LoginServlet() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
		library = (FilmLibrary) context.getBean("films");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=windows-1251");
		String login = req.getParameter("login");
		//String name = req.getParameter("name");
		String pswd = req.getParameter("pswd");
		try {
			boolean res = library.getUserManager().login(login, pswd);
			if (res) {
				req.getSession().setAttribute("login", login);
				req.getSession().setAttribute("pswd", pswd);
				;
				resp.getWriter().print("Привет, " + library.getUserManager().getCurrentUser().getName());
			} else resp.getWriter().print("не правильный пароль");
		} catch (Exception ex) {
			resp.getWriter().print(ex.getMessage());
		}
	}
}

