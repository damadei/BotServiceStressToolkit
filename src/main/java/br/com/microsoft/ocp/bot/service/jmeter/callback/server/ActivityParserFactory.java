package br.com.microsoft.ocp.bot.service.jmeter.callback.server;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Activity;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Conversation;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Message;

public class ActivityParserFactory {
	private static final Logger log = LoggerFactory.getLogger(ActivityParserFactory.class);

	public static Activity parse(String type, String jsonPayload, String conversationId, String activityId) {
		Activity activity = parse(type, jsonPayload, conversationId);
		activity.setId(activityId);
		return activity;
	}

	
	public static Activity parse(String type, String jsonPayload, String conversationId) {
		Activity activity = parse(type, jsonPayload);
		activity.setConversation(new Conversation(conversationId));
		
		return activity;
	}

	public static Activity parse(String type, String jsonPayload) {
		//log.debug(jsonPayload);

		Jsonb jsonb = JsonbBuilder.create();

		if (type.equals("message")) {
			return jsonb.fromJson(jsonPayload, Message.class);
		}

		return jsonb.fromJson(jsonPayload, Activity.class);
	}

}
