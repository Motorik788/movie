package main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by dryzu on 16.03.2018.
 */
public class Main {
	public static void main(String[] args) throws Exception {
		MenuLibrary menuLibrary = new MenuLibrary();
		System.out.println(System.getProperty("user.dir"));
		menuLibrary.Show();
	}
}

class MenuLibrary {

	FilmLibrary filmLibrary = FilmLibrary.createOracleStorageLibrary("jdbc:oracle:thin:@localhost:1521:xe");
	//main.FilmLibrary filmLibrary = main.FilmLibrary.createBasicStorageLibrary();
	Scanner scan = new Scanner(System.in);

	public void Show() {
		while (true) {
			showMenu();
			String command = scan.nextLine();
			getCommand(command);
		}
	}

	private void showMenu() {
		System.out.println("Menu:");
		User user = filmLibrary.getUserManager().getCurrentUser();
		if (user == null) {
			System.out.println("\t1.login");
			System.out.println("\t2.register");
		} else {
			System.out.println("Привет, " + user.getName());
			System.out.println("\t1.search");
			System.out.println("\t2.detail");
			System.out.println("\t3.addreview");
			System.out.println("\t4.logout");
			if (user instanceof AdminUser) {
				System.out.println("\t5.deletereview");
			}
		}
	}

	private void getCommand(String command) {
		if (filmLibrary.getUserManager().getCurrentUser() == null) {
			switch (command) {
				case "login":
					login();
					break;
				case "register":
					register();
					break;
				default:
					System.out.println("Такой команды нет");
					break;
			}
		} else {

			switch (command) {
				case "search":
					search();
					break;
				case "detail":
					detail();
					break;
				case "addreview":
					addReview();
					break;
				case "logout":
					//filmLibrary.logout();
					filmLibrary.getUserManager().logout();
					break;
				case "deletereview":
					if (filmLibrary.getUserManager().getCurrentUser() instanceof AdminUser) {
						deleteReview();
					}
					break;
				default:
					System.out.println("Такой команды нет");
					break;
			}
		}
	}

	private void login() {
		String login, password;
		System.out.println("введите логин");
		login = scan.nextLine();
		System.out.println("введите пароль");
		password = scan.nextLine();
		try {
			//if (!filmLibrary.login(login, password))
			if (!filmLibrary.getUserManager().login(login, password))
				System.out.println("Неправильный пароль");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void register() {
		String name, login, password;
		System.out.println("введите имя");
		name = scan.nextLine();
		System.out.println("введите логин");
		login = scan.nextLine();
		System.out.println("введите пароль");
		password = scan.nextLine();

		if (!StringUtil.isNullOrEmpty(name) && !StringUtil.isNullOrEmpty(login) && !StringUtil.isNullOrEmpty(password)) {
			//if (!filmLibrary.register(name, login, password)) {
			if (!filmLibrary.getUserManager().register(name, login, password)) {
				System.out.println("Пользователь с таким логином уже сущесвует");
			}
		}
	}

	private void search() {
		String name;
		Date date = null;
		System.out.println("введите название");
		name = scan.nextLine();
		try {
			System.out.println("введите год");
			date = new SimpleDateFormat("YYYY").parse(scan.nextLine());

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		ConsoleWaiter waiter = new ConsoleWaiter();
		waiter.startWait();
		try {
			Thread.sleep(6000);
		} catch (Exception ex) {

		}
		waiter.stopWaiting();
		filmLibrary.search(name, date).forEach(i -> System.out.println(i.toShortInfo()));
	}

	private void detail() {
		String imdb;
		System.out.println("Введите идентификатор фильма");
		imdb = scan.nextLine();
		System.out.println(filmLibrary.detail(imdb));
	}

	private void addReview() {
		String imdb, text;
		float rating;
		System.out.println("Введите индетификатор фильма");
		imdb = scan.nextLine();
		System.out.println("введите свой отзыв");
		text = scan.nextLine();
		System.out.println("введите свою оценку");
		rating = Float.parseFloat(scan.nextLine());
		filmLibrary.addReview(imdb, text, rating);
	}

	private void deleteReview() {
		String id;
		System.out.println("Введите Id комментария");
		id = scan.nextLine();
		filmLibrary.deleteReview(id);
	}
}

