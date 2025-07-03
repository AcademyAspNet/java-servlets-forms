package web.application.bootstrap;

import web.application.html.HtmlDocument;
import web.application.html.HtmlTag;
import web.application.html.SingleHtmlTag;

public class BootstrapDocument extends HtmlDocument {

	public BootstrapDocument(String documentTitle) {
		super(documentTitle);
		
		SingleHtmlTag bootstrapCssLink = new SingleHtmlTag("link");
		bootstrapCssLink.setAttribute("rel", "stylesheet");
		bootstrapCssLink.setAttribute("href", "https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css");
		bootstrapCssLink.setAttribute("integrity", "sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr");
		bootstrapCssLink.setAttribute("crossorigin", "anonymous");
		
		head.addElement(bootstrapCssLink);
		
		HtmlTag bootstrapScript = new HtmlTag("script");
		bootstrapScript.setAttribute("src", "https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js");
		bootstrapScript.setAttribute("integrity", "sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q");
		bootstrapScript.setAttribute("crossorigin", "anonymous");
		
		head.addElement(bootstrapScript);
	}
	
	public BootstrapDocument() {
		this("Bootstrap Document");
	}
}
