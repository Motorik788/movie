package main;

/**
 * Created by dryzu on 12.04.2018.
 */
public interface IUserStorage {
	User getUser(String login);
	boolean addUser(String name, String login, String password);
}
