package db;

import model.entities.Usuario;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	private Usuario[] users; // Simulated in-memory database

	private static Connection conn = null;
	
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public DB() {
		// Initialize the in-memory database with some users (for demonstration)
		users = new Usuario[]{
				new Usuario("Eduardo", "Longen", "edu@gmail.com", "senha123"),
				new Usuario("Daniel", "Hartmann", "daniel@gmail.com", "senha456")
		};
	}

	public boolean authenticateUser(String email, String password) {
		// Check if the user with the given email and password exists in the database
		for (Usuario user : users) {
			if (user.getEmail().equals(email) && user.getSenha().equals(password)) {
				return true; // Authentication successful
			}
		}
		return false; // Authentication failed
	}

	public void registerUser(Usuario newUser) {
		// Add the new user to the database (for demonstration)
		Usuario[] newArray = new Usuario[users.length + 1];
		System.arraycopy(users, 0, newArray, 0, users.length);
		newArray[users.length] = newUser;
		users = newArray;
	}

}
