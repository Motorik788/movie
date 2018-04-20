package main;

/**
 * Created by dryzu on 12.04.2018.
 */
public class UserManager implements IUserCommands {
	private User current;
	private IUserStorage storage;

	public UserManager(IUserStorage storage) {
		this.storage = storage;
	}


	public User getCurrentUser() {
		return current;
	}

	@Override
	public boolean register(String name, String login, String password) {
		boolean res = false;
		if (storage.getUser(login) == null) {
			storage.addUser(name, login, password);
			res = true;
		}

		return res;
	}

	@Override
	public boolean login(String login, String password) throws Exception {
		User user = storage.getUser(login);
		if (user != null) {
			if (user.valid(password)) {
				current = user;
				return true;
			} else return false;
		} else throw new Exception("пользователя с таким логином не существует");
	}

	@Override
	public void logout() {
		current = null;
	}
}
