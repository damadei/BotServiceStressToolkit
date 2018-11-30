package br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas;

import javax.json.JsonObject;

public class Attachment {

	public Attachment() {
	}

	private String contentType;

	private JsonObject content;

	private String contentUrl;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public JsonObject getContent() {
		return content;
	}

	public void setContent(JsonObject content) {
		this.content = content;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	@Override
	public String toString() {
		return "Attachment [contentType=" + contentType + ", content=" + content + ", contentUrl=" + contentUrl + "]";
	}

}
