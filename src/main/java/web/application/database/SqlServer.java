package web.application.database;

public class SqlServer extends Database {

	private static final int DEFAULT_PORT = 1433;
	
	public SqlServer(String ip, int port, Credentials credentials, String databaseName) {
		super(ip, port, credentials, databaseName);
	}
	
	public SqlServer(String ip, Credentials credentials, String databaseName) {
		super(ip, DEFAULT_PORT, credentials, databaseName);
	}
	
	protected String getConnectionString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("jdbc:sqlserver://");
		builder.append(ip).append(':').append(port).append(';');
		builder.append("encrypt=true;");
		builder.append("trustServerCertificate=true;");
		builder.append("databaseName=").append(databaseName).append(';');
		
		return builder.toString();
	}
	
	protected String getDriver() {
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	}
}
