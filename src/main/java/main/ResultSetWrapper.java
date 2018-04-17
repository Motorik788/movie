package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by dryzu on 12.04.2018.
 */
public class ResultSetWrapper {
	private ResultSet set;
	private Connection con;
	private PreparedStatement statement;

	public ResultSetWrapper(Connection con, PreparedStatement statement, ResultSet set) {
		this.set = set;
		this.con = con;
		this.statement = statement;
	}

	public ResultSet getResultSet() {
		return set;
	}

	public void close() {
		try {
			con.close();
			statement.close();
			set.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
