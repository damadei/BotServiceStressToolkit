package br.com.microsoft.ocp.bot.service.jmeter.sampler;

public class TimeoutException extends Exception {

	private static final long serialVersionUID = -3787937731077191266L;

	public TimeoutException(String message) {
		super(message);
	}
}
