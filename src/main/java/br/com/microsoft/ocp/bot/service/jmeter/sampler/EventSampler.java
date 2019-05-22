package br.com.microsoft.ocp.bot.service.jmeter.sampler;

import java.io.StringReader;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.microsoft.ocp.bot.service.jmeter.auth.AuthenticationException;
import br.com.microsoft.ocp.bot.service.jmeter.builder.EventActivityBuilder;
import br.com.microsoft.ocp.bot.service.jmeter.callback.server.ActivityRequestReply;
import br.com.microsoft.ocp.bot.service.jmeter.callback.server.HttpResponseException;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Activity;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Member;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Message;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Event;

public class EventSampler extends BaseBotSampler {

	private static final Logger Log = LoggerFactory.getLogger(EventSampler.class);
	
	private static final long serialVersionUID = 240L;
	
	public static final String NUM_OF_EXPECTED_RESPONSES = "NUM_OF_EXCPECTED_RESPONSES_FOR_MESSAGE";
	public static final String EVENT_NAME = "NAME";
	public static final String CHANNELDATA = "CHANNELDATA";
	
	public static final String NUM_OF_EXPECTED_RESPONSES_DEFAULT_VALUE = "1";
	public static final String EVENT_NAME_DEFAULT_VALUE = "EventName";
	public static final String CHANNELDATA_DEFAULT_VALUE = "{\"Key\": \"Value\"}";

	@Override
	public SampleResult sample(Entry e) {
		SampleResult res = new SampleResult();
		res.setSampleLabel(getName());
		res.sampleStart();
		res.setDataType(SampleResult.TEXT);
		
		try {
			JMeterContext context = getThreadContext();
			JMeterVariables vars = context.getVariables();
			
			String conversationId = vars.get(Constants.CONVERSATION_ID);
			
			Event requestEvent = EventActivityBuilder.build(getChanneldata(), getEventName(),
					new Member(getFromUser()), new Member(getRecipientMemberId()), getChannelId(),
					getCallbackUrl(), conversationId);
			
			BlockingQueue<Activity> queue = null;
			if (shouldWaitForResponseActivity()) {
				queue = ActivityRequestReply.getInstance().setRequest(requestEvent.getId());
			}
			
			send(requestEvent);
			
			String respText = "CONVERSATION: " + requestEvent.getConversation().getId() + NEW_LINE;
			respText += "USER ID:" + requestEvent.getFrom().getId() + NEW_LINE;
			
			if (shouldWaitForResponseActivity()) {
				List<Message> responses = getResponses(requestEvent, queue, getNumberOfResponseMessagesExpected(),
						Message.class);

				respText += getFullResponseText(responses);
			}

			res.setResponseData(respText, null);
			res.sampleEnd();
			res.setSuccessful(true);
		} catch (UnirestException | HttpResponseException | InterruptedException err) {
			res = setErrorResponse("Unexpected error", err, res);
		} catch (TimeoutException err) {
			res = setErrorResponse("Timeout exception", err, res);
		} catch (AuthenticationException err) {
			res = setErrorResponse("Authentication exception", err, res);
		}

		return res;
	}
	
	private boolean shouldWaitForResponseActivity() {
		return getNumberOfResponseMessagesExpected() > 0;
	}
	
	private JsonObject getChanneldata() {
		String jsonStr = getPropertyAsString(CHANNELDATA);
		System.out.println("JSON string: " + jsonStr);
		JsonReader jsonReader = Json.createReader(new StringReader(jsonStr));
		JsonObject obj = jsonReader.readObject();
		jsonReader.close();
		
		return obj;
	}
	
	private String getEventName() {
		return getPropertyAsString(EVENT_NAME);
	}
	
	public int getNumberOfResponseMessagesExpected() {
		System.out.println("Number of Responses: " + getPropertyAsInt(NUM_OF_EXPECTED_RESPONSES));
		return getPropertyAsInt(NUM_OF_EXPECTED_RESPONSES);
	}

}
