package br.com.microsoft.ocp.bot.service.jmeter.callback.server;

public class HttpResponseException extends Exception {

	private static final long serialVersionUID = 8487461672095066703L;

	private int httpStatusCode;
	private String httpStatusText;

	public HttpResponseException(int httpStatusCode, String httpStatusText) {
		super(httpStatusText);
		this.httpStatusCode = httpStatusCode;
		this.httpStatusText = httpStatusText;
	}

	@Override
	public String toString() {
		return String.format("HTTP Error. Status = {0}. Text = {1}", httpStatusCode, httpStatusText);
	}

}
