package main;

import java.sql.*;
import java.util.List;
import java.util.Locale;

/**
 * Created by dryzu on 12.04.2018.
 */
public final class OracleConnectionHelper {

	String connectionString;

	public OracleConnectionHelper(String connectionString) {
		this.connectionString = connectionString;
		try {
			Locale.setDefault(Locale.ENGLISH); // магия и подключение работает
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public ResultSetWrapper select(String prepareSQL, List<Object> params) {
		ResultSet set = null;
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = DriverManager.getConnection(connectionString, "system", "1234");
			statement = con.prepareStatement(prepareSQL);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					statement.setObject(i + 1, params.get(i));
				}
			}
			set = statement.executeQuery();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return new ResultSetWrapper(con, statement, set);
	}

	//insert, delete, update
	public int insert(String prepareSQL, List<Object> params) {
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = DriverManager.getConnection(connectionString, "system", "1234");
			statement = con.prepareStatement(prepareSQL);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					statement.setObject(i + 1, params.get(i));
				}
			}
			return statement.executeUpdate();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				if (con != null) con.close();
				if (statement != null) statement.close();
			} catch (Exception e) {

			}
		}
		return -1;
	}
}
