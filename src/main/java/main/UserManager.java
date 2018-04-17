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

	//private static main.UserManager instance;

//	private main.UserManager() {
//	}

	public User getCurrentUser() {
		return current;
	}

//	public static main.UserManager Instance() {
//		if (instance == null) {
//			synchronized (main.UserManager.class) {
//				if (instance == null)
//					instance = new main.UserManager();
//			}
//		}
//		return instance;
//	}

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
