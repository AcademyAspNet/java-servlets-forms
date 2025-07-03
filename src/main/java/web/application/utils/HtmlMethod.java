package web.application.utils;

public enum HtmlMethod {

	GET("get"),
	POST("post");
	
	private final String name;
	
	private HtmlMethod(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
