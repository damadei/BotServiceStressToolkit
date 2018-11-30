package br.com.microsoft.ocp.bot.service.jmeter.builder;

import java.util.UUID;

import org.joda.time.format.ISODateTimeFormat;

import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Activity;

public class ActivityBaseBuilder {

	protected static String getTimestamp() {
		return ISODateTimeFormat.dateHourMinuteSecondMillis().withZoneUTC().print(System.currentTimeMillis());
	}

	protected static String getLocalTimestamp() {
		return ISODateTimeFormat.dateHourMinuteSecondMillis().print(System.currentTimeMillis());
	}
	
	protected static void setActivityProperties(Activity activity) {
		activity.setId(UUID.randomUUID().toString());
	}
}
