package web.application.form;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.application.database.Credentials;
import web.application.database.Database;
import web.application.database.SqlServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/ProcessForm")
public class FormServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private final Database database;
	
	public FormServlet() {
		String ip = "localhost"; //getInitParameter("database.ip");
		int port = 1433; //Integer.parseInt(getInitParameter("database.port"));
		
		String user = "java_jdbc"; //getInitParameter("database.user");
		String password = "password."; //getInitParameter("database.password");
		
		Credentials credentials = new Credentials(user, password);
		String databaseName = "java_servlets"; //getInitParameter("database.name");
		
		database = new SqlServer(ip, port, credentials, databaseName);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		
		if (firstName == null || firstName.isBlank()) {
			sendResponse(response, "Ошибка: Не указано имя!");
			return;
		}
		
		String phoneNumber = request.getParameter("phone");
		String emailAddress = request.getParameter("email");
		
		if (emailAddress == null || emailAddress.isBlank()) {
			sendResponse(response, "Ошибка: Не указан адрес электронной почты!");
			return;
		}
		
		database.useConnection(connection -> {
			try {
				System.out.println(connection.isValid(1));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		
		sendResponse(response, new String[] {
			"<h1>Данные из анкеты</h1>",
			"<hr>",
			"<p><b>Имя:</b> %s</p>".formatted(firstName),
			"<p><b>Фамилия:</b> %s</p>".formatted(lastName),
			"<p><b>Номер телефона:</b> %s</p>".formatted(phoneNumber),
			"<p><b>Адрес электронной почты:</b> %s</p>".formatted(emailAddress)
		});
	}
	
	private void sendResponse(HttpServletResponse response, String[] lines) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("<html>");
		
		builder.append("<head>");
			builder.append("<title>Результат</title>");
		builder.append("</head>");
		
		builder.append("<body>");
		
		for (String line : lines)
			builder.append(line);
		
		builder.append("</body>");
		
		builder.append("</html>");
		
		try (PrintWriter writer = response.getWriter()) {
			writer.append(builder.toString());
		}
	}
	
	private void sendResponse(HttpServletResponse response, String line) throws IOException {
		sendResponse(response, new String[] { line });
	}
}
