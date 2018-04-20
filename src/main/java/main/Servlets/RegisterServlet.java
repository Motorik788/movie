package main.Servlets;

import main.ApplicationConfiguration;
import main.FilmLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
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
	@Autowired
	private FilmLibrary library;

//	public RegisterServlet() {
//		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
//		library = (FilmLibrary) context.getBean(FilmLibrary.class);
//	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("windows-1251");
		resp.setContentType("text/html;charset=windows-1251");
		String name = req.getParameter("name");
		String login = req.getParameter("login");
		String pswd = req.getParameter("pswd");
		resp.getWriter().print(name);
		if (name != null && login != null && pswd != null)
			resp.getWriter().print(library.getUserManager().register(name, login, pswd));
		else throw new IOException("параметры не должны быть null");
	}
}
