package web.application.html;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletResponse;

public class HtmlDocument extends HtmlTag {

	protected final HtmlTag head;
	protected final HtmlTag body;
	
	protected final HtmlTag title;
	
	public HtmlDocument(String documentTitle) {
		super("html");
		
		head = new HtmlTag("head");
		addElement(head);
		
		SingleHtmlTag meta = new SingleHtmlTag("meta");
		meta.setAttribute("charset", "UTF-8");
		head.addElement(meta);
		
		title = new HtmlTag("title");
		title.setTextContent(documentTitle);
		addElement(title);
		
		body = new HtmlTag("body");
		addElement(body);
	}
	
	public HtmlDocument() {
		this("HTML Document");
	}
	
	public HtmlTag getHead() {
		return head;
	}
	
	public HtmlTag getBody() {
		return body;
	}
	
	public String getTitle() {
		return title.getTextContent();
	}
	
	public void setTitle(String title) {
		this.title.setTextContent(title);
	}
	
	public void send(HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		
		try (PrintWriter writer = response.getWriter()) {
			writer.append(toHtml());
		}
	}
	
	@Override
	public String toHtml() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("<!DOCTYPE html>");
		builder.append(super.toHtml());
		
		return builder.toString();
	}
}
