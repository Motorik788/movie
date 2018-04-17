package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dryzu on 12.04.2018.
 */
public class OracleUserStorage implements IUserStorage {

	OracleConnectionHelper helper;

	public OracleUserStorage(String connectionString) {
		helper = new OracleConnectionHelper(connectionString);
	}

	public static User parseSetToUser(ResultSet set) throws Exception {
		if (set.next()) {
			int id = set.getInt(1);
			String userLogin = set.getString(2);
			String userPswd = set.getString(3);
			String userName = set.getString(4);
			boolean isAdmin = Boolean.parseBoolean(set.getString(5));
			return !isAdmin ? new User(id, userLogin, userPswd, userName) : new AdminUser(id, userLogin, userPswd, userName);
		} else return null;
	}

	@Override
	public User getUser(String login) {
		List<Object> params = new ArrayList<>();
		params.add(login);
		ResultSetWrapper set = helper.select("select * from USER_INFO where LOGIN = ?", params);
		try {
			if (set.getResultSet() != null) {
				return parseSetToUser(set.getResultSet());
			} else return null;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		set.close();
		return null;
	}

	@Override
	public boolean addUser(String name, String login, String password) {
		List<Object> params = new ArrayList<>();
		ResultSetWrapper set = helper.select("select MAX(ID_USER) from USER_INFO", null);
		if (set == null)
			return false;
		try {
			set.getResultSet().next();
			params.add(set.getResultSet().getInt(1) + 1);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		params.add(login);
		params.add(password);
		params.add(name);
		params.add(false);
		set.close();
		return helper.insert("insert into USER_INFO values(?,?,?,?,?)", params) != -1;
	}
}
