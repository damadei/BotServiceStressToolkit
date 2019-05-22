package br.com.microsoft.ocp.bot.service.jmeter.builder;

import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Conversation;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Event;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Member;

import javax.json.JsonObject;

import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
public class EventActivityBuilder extends ActivityBaseBuilder {
	
	public EventActivityBuilder() {
		
	}
	
	public static Event build(JsonObject channeldata, String name, Member from,
			Member recipient, String channelId, String serviceUrl, String conversationId) {
		Event event = new Event();
		event.setType(Event.EVENT_TYPE);
		event.setChanneldata(channeldata);
		event.setName(name);
		event.setFrom(from);

		event.setTimestamp(
				ISODateTimeFormat.dateHourMinuteSecondMillis().withZoneUTC().print(System.currentTimeMillis()));
		event.setTimestamp(ISODateTimeFormat.dateHourMinuteSecondMillis().withZone(DateTimeZone.forOffsetHours(-3))
				.print(System.currentTimeMillis()));

		event.setRecipient(recipient);
		event.setConversation(new Conversation(conversationId));
		event.setServiceUrl(serviceUrl);
		event.setChannelId(channelId);
		
		setActivityProperties(event);
		
		return event;
	}

}
