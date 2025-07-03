package web.application.html;

import java.util.ArrayList;
import java.util.List;

public class HtmlTag extends SingleHtmlTag {

	protected String textContent;
	protected List<HtmlRenderable> innerElements;
	
	public HtmlTag(String tag) {
		super(tag);
		innerElements = new ArrayList<>();
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public List<HtmlRenderable> getElements() {
		return new ArrayList<>(innerElements);
	}
	
	public boolean hasElement(HtmlRenderable element) {
		return innerElements.contains(element);
	}
	
	public void addElement(HtmlRenderable element) {
		innerElements.add(element);
	}
	
	public boolean removeElement(HtmlRenderable element) {
		return innerElements.remove(element);
	}
	
	protected void renderTagContent(StringBuilder builder) {
		if (textContent != null) {
			builder.append(textContent);
		} else {
			for (HtmlRenderable element : innerElements) {
				builder.append(element.toHtml());
			}
		}
	}
	
	@Override
	public String toHtml() {
		StringBuilder builder = new StringBuilder();
		
		builder.append('<').append(tag);
		renderTagAttributes(builder);
		builder.append('>');
		renderTagContent(builder);
		builder.append("</").append(tag).append('>');
		
		return builder.toString();
	}
}
