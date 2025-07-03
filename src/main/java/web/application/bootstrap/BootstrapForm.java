package web.application.bootstrap;

import web.application.html.HtmlTag;
import web.application.html.SingleHtmlTag;
import web.application.utils.HtmlMethod;

public class BootstrapForm extends HtmlTag {

	protected HtmlMethod method;
	
	public BootstrapForm() {
		super("form");
	}
	
	public void setMethod(HtmlMethod method) {
		setAttribute("method", method.getName());
	}
	
	public String getAction() {
		return getAttribute("action");
	}
	
	public void setAction(String action) {
		setAttribute("action", action);
	}
	
	protected HtmlTag createContainer() {
		HtmlTag container = new HtmlTag("div");
		container.setAttribute("class", "mb-3");
		
		return container;
	}
	
	public void addInput(String inputName, String inputType, String labelText) {
		HtmlTag container = createContainer();
		addElement(container);
		
		HtmlTag label = new HtmlTag("label");
		label.setAttribute("class", "form-label");
		label.setAttribute("for", inputName);
		label.setTextContent(labelText);
		
		container.addElement(label);
		
		SingleHtmlTag input = new SingleHtmlTag("input");
		input.setAttribute("class", "form-control");
		input.setAttribute("name", inputName);
		input.setAttribute("type", inputType);
		
		container.addElement(input);
	}
	
	public void addSubmitButton() {
		HtmlTag container = createContainer();
		addElement(container);
		
		HtmlTag button = new HtmlTag("button");
		button.setAttribute("class", "btn btn-primary");
		button.setAttribute("type", "submit");
		button.setTextContent("Отправить");
		
		container.addElement(button);
	}
}
