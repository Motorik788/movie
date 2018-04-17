package main;

/**
 * Created by dryzu on 05.04.2018.
 */
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

	public String getName() {
		return name;
	}

	public String getLogin() {
		return login;
	}

	public int getId() {
		return id;
	}
}
