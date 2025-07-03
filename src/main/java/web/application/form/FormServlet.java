package web.application.form;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.application.bootstrap.BootstrapDocument;
import web.application.bootstrap.BootstrapForm;
import web.application.database.Credentials;
import web.application.database.Database;
import web.application.database.SqlServer;
import web.application.html.HtmlTag;
import web.application.html.SingleHtmlTag;
import web.application.utils.HtmlMethod;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/ProcessForm")
public class FormServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private Database database;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		ServletContext context = config.getServletContext();
		
		String ip = context.getInitParameter("database.ip");
		int port = Integer.parseInt(context.getInitParameter("database.port"));
		
		String user = context.getInitParameter("database.user");
		String password = context.getInitParameter("database.password");
		
		Credentials credentials = new Credentials(user, password);
		String databaseName = context.getInitParameter("database.name");
		
		database = new SqlServer(ip, port, credentials, databaseName);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BootstrapDocument document = new BootstrapDocument("Анкета");
		
		HtmlTag container = new HtmlTag("main");
		container.setAttribute("class", "container-md pt-3");
		document.getBody().addElement(container);
		
		HtmlTag h1 = new HtmlTag("h1");
		h1.setTextContent("Анкета");
		container.addElement(h1);
		
		SingleHtmlTag hr = new SingleHtmlTag("hr");
		container.addElement(hr);
		
		BootstrapForm form = new BootstrapForm();
		container.addElement(form);
		
		form.setMethod(HtmlMethod.POST);
		form.setAction("/Form/ProcessForm");
		
		form.addInput("firstname", "text", "Имя:");
		form.addInput("lastname", "text", "Фамилия:");
		form.addInput("phone", "tel", "Номер телефона:");
		form.addInput("email", "email", "Адрес электронной почты:");
		form.addSubmitButton();
		
		document.send(response);
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
				String sql = "INSERT INTO Forms (FirstName, LastName, PhoneNumber, EmailAddress) VALUES (?, ?, ?, ?)";
				
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setNString(1, firstName);
				statement.setNString(2, lastName);
				statement.setNString(3, phoneNumber);
				statement.setNString(4, emailAddress);
				
				int rows = statement.executeUpdate();
				
				System.out.printf("Количество затронутых строк: %d\n", rows);
			} catch (SQLException exception) {
				throw new RuntimeException(exception);
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
		BootstrapDocument document = new BootstrapDocument();
		
		StringBuilder builder = new StringBuilder();
		
		for (String line : lines)
			builder.append(line);
		
		document.getBody().setTextContent(builder.toString());
		document.send(response);
	}
	
	private void sendResponse(HttpServletResponse response, String line) throws IOException {
		sendResponse(response, new String[] { line });
	}
}
