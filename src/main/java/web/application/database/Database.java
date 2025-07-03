package web.application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Consumer;

public abstract class Database {

	protected final String ip;
	protected final int port;
	
	protected final Credentials credentials;
	protected final String databaseName;
	
	protected Database(String ip, int port, Credentials credentials, String databaseName) {
		this.ip = ip;
		this.port = port;
		
		this.credentials = credentials;
		this.databaseName = databaseName;
	}
	
	protected abstract String getConnectionString();
	protected abstract String getDriver();
	
	public void useConnection(Consumer<Connection> consumer) {
		try {
			Class.forName(getDriver());
		} catch (ClassNotFoundException exception) {
			throw new RuntimeException(exception);
		}
		
		String connectionString = getConnectionString();
		String user = credentials.getUser();
		String password = credentials.getPassword();
		
		try (Connection connection = DriverManager.getConnection(connectionString, user, password)) {
			consumer.accept(connection);
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}
}
