package web.application.html;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class SingleHtmlTag implements HtmlRenderable {

	protected String tag;
	protected Map<String, String> attributes;
	
	public SingleHtmlTag(String tag) {
		setTag(tag);
		attributes = new LinkedHashMap<>();
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		if (tag == null || tag.isBlank())
			throw new IllegalArgumentException("Tag cannot be null or blank");
		
		this.tag = tag;
	}
	
	public Set<String> getAttributes() {
		return new LinkedHashSet<>(attributes.keySet());
	}
	
	public boolean hasAttribute(String name) {
		return attributes.containsKey(name);
	}
	
	public String getAttribute(String name) {
		return attributes.get(name);
	}
	
	public void setAttribute(String name, String value) {
		attributes.put(name, value);
	}
	
	public void addAttribute(String name) {
		attributes.put(name, null);
	}
	
	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	public void clearAttributes() {
		attributes.clear();
	}
	
	protected void renderTagAttributes(StringBuilder builder) {
		for (Map.Entry<String, String> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();
			String attributeValue = entry.getValue();
			
			builder.append(' ').append(attributeName);
			
			if (attributeValue != null)
				builder.append("=\"").append(attributeValue).append('"');
		}
	}
	
	public String toHtml() {
		StringBuilder builder = new StringBuilder();
		
		builder.append('<').append(getTag());
		renderTagAttributes(builder);
		builder.append(" />");
		
		return builder.toString();
	}
}
