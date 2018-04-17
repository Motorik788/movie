package main;

import java.util.HashMap;

/**
 * Created by dryzu on 12.04.2018.
 */
public class BasicUserStorage implements IUserStorage {
	private HashMap<String, User> Users = new HashMap<>();

	public BasicUserStorage() {
		Users.put("admin", new AdminUser(0, "admin", "admin", "Administrator"));
	}

	@Override
	public User getUser(String login) {
		User user = null;
		if (Users.containsKey(login)) {
			user = Users.get(login);
		}
		return user;
	}

	@Override
	public boolean addUser(String name, String login, String password) {
		if (!Users.containsKey(login)) {
			Users.put(login, new User(-1, login, password, name));
			return true;
		} else return false;
	}
}
