package main;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dryzu on 05.04.2018.
 */
//@XmlRootElement
public class User {
	private int id;
	private String name;
	private String login;
	private String password;

	public User(int id, String login, String password, String name) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.id = id;
	}

	public boolean valid(String pswd) {
		return pswd.equals(password);
	}

	//@XmlElement
	public String getName() {
		return name;
	}

	//@XmlElement
	public String getLogin() {
		return login;
	}

	//@XmlElement
	public int getId() {
		return id;
	}
}
