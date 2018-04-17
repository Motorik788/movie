package main;

/**
 * Created by dryzu on 12.04.2018.
 */
public interface IUserCommands {
	boolean register(String name, String login, String password);
	boolean login(String login, String password) throws  Exception;
	void  logout();
}
