package br.com.microsoft.ocp.bot.service.jmeter.sampler;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.microsoft.ocp.bot.service.jmeter.auth.AuthenticationException;
import br.com.microsoft.ocp.bot.service.jmeter.builder.MessageActivityBuilder;
import br.com.microsoft.ocp.bot.service.jmeter.callback.server.ActivityRequestReply;
import br.com.microsoft.ocp.bot.service.jmeter.callback.server.HttpResponseException;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Activity;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Member;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Message;

public class MessageSampler extends BaseBotSampler {

	private static final Logger log = LoggerFactory.getLogger(MessageSampler.class);

	private static final long serialVersionUID = 240L;

	public static final String NUM_OF_EXPECTED_RESPONSES = "NUM_OF_EXPECTED_RESPONSES_FOR_MESSAGE";
	public static final String MESSAGE_TEXT = "MESSAGE_TEXT";
	public static final String MESSAGE_TEXT_FORMAT = "MESSAGE_TEXT_FORMAT";
	public static final String LOCALE = "LOCALE";

	public static final int NUM_OF_EXPECTED_RESPONSES_DEFAULT_VALUE = 1;
	public static final String MESSAGE_TEXT_DEFAULT_VALUE = "";
	public static final String MESSAGE_TEXT_FORMAT_DEFAULT_VALUE = "plain";
	public static final String LOCALE_DEFAULT_VALUE = "en-US";

	@Override
	public SampleResult sample(Entry entry) {
		SampleResult res = new SampleResult();
		res.setSampleLabel(getName());
		res.sampleStart();
		res.setDataType(SampleResult.TEXT);

		try {
			JMeterContext context = getThreadContext();
			JMeterVariables vars = context.getVariables();

			String conversationId = vars.get(Constants.CONVERSATION_ID);

			Message requestMessage = MessageActivityBuilder.build(conversationId, getMessageText(),
					getMessageTextFormat(), getLocale(), new Member(getFromUser()), new Member(getRecipientMemberId()),
					getChannelId(), getCallbackUrl());

			BlockingQueue<Activity> queue = null;
			if (shouldWaitForResponseActivity()) {
				queue = ActivityRequestReply.getInstance().setRequest(requestMessage.getId());
			}

			send(requestMessage);

			String respText = "CONVERSATION: " + requestMessage.getConversation().getId() + NEW_LINE;
			respText += "USER ID: " + requestMessage.getFrom().getId() + NEW_LINE;

			if (shouldWaitForResponseActivity()) {
				List<Message> responses = getResponses(requestMessage, queue, getNumberOfResponseMessagesExpected(),
						Message.class);

				respText += getFullResponseText(responses);
			}

			res.setResponseData(respText, null);
			res.sampleEnd();
			res.setSuccessful(true);

		} catch (UnirestException | HttpResponseException | InterruptedException e) {
			res = setErrorResponse("Unexpected error", e, res);
		} catch (TimeoutException e) {
			res = setErrorResponse("Timeout exception", e, res);
		} catch (AuthenticationException e) {
			res = setErrorResponse("Authentication exception", e, res);
		}

		return res;
	}

	private boolean shouldWaitForResponseActivity() {
		return getNumberOfResponseMessagesExpected() > 0;
	}

	private String getMessageText() {
		return getPropertyAsString(MESSAGE_TEXT);
	}

	private String getLocale() {
		return getPropertyAsString(LOCALE);
	}

	private String getMessageTextFormat() {
		return getPropertyAsString(MESSAGE_TEXT_FORMAT);
	}

	public int getNumberOfResponseMessagesExpected() {
		return getPropertyAsInt(NUM_OF_EXPECTED_RESPONSES);
	}

}
