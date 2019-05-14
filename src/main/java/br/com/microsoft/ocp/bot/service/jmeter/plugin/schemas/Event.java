package br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas;

import javax.json.JsonObject;

public class Event extends Activity {
	public static final String EVENT_TYPE = "event";
	
	public Event() {
	}
	
	private String name;
	private JsonObject channeldata;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public JsonObject getChanneldata() {
		return channeldata;
	}
	
	public void setChanneldata(JsonObject channeldata) {
		this.channeldata = channeldata;
	}
	
	
}
