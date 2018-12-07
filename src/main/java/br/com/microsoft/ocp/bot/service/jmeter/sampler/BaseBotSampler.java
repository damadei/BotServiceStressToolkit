package br.com.microsoft.ocp.bot.service.jmeter.sampler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.RequestBodyEntity;

import br.com.microsoft.ocp.bot.service.jmeter.auth.AuthHelper;
import br.com.microsoft.ocp.bot.service.jmeter.auth.AuthenticationException;
import br.com.microsoft.ocp.bot.service.jmeter.auth.TokenResponse;
import br.com.microsoft.ocp.bot.service.jmeter.callback.server.HttpResponseException;
import br.com.microsoft.ocp.bot.service.jmeter.config.BotServiceConfig;
import br.com.microsoft.ocp.bot.service.jmeter.config.BotServiceSecurityConfig;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Activity;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Attachment;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Message;

public abstract class BaseBotSampler extends AbstractSampler {

	private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

	private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";

	private static final Logger log = LoggerFactory.getLogger(BaseBotSampler.class);

	private static final long serialVersionUID = 240L;

	public static final String NEW_LINE = "\r\n";

	public static final String RESPONSE_NUMBER = "RESPONSE %d:";
	public static final String ATTACHMENT_NUMBER = "ATTACHMENT %d:";

	private static final String TOKEN = "TOKEN";

	private static final String AUTHORIZATION_HEADER = "Authorization";

	protected void ensureResponseSuccess(@SuppressWarnings("rawtypes") HttpResponse response)
			throws HttpResponseException {
		if (response.getStatus() > 400) {
			throw new HttpResponseException(response.getStatus(), response.getStatusText());
		}
	}

	protected String getFromUser() {
		JMeterContext context = getThreadContext();
		JMeterVariables vars = context.getVariables();

		if (getGenRandomUserIdPerThread()) {
			return vars.get(Constants.USER);
		} else {
			return getFromMemberId();
		}
	}

	protected SampleResult setErrorResponse(String message, Exception e, SampleResult res) {
		log.error(message, e);
		res.setResponseData(e.getMessage(), "UTF-8");
		res.sampleEnd();
		res.setSuccessful(false);

		return res;
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> getResponses(Activity requestActivity, BlockingQueue<Activity> queue,
			int numberOfResponseMessagesExpected, Class<T> clazz) throws InterruptedException, TimeoutException {

		log.info(String.format("Waiting for %d response(s) of activity %s at url %s", numberOfResponseMessagesExpected,
				requestActivity.getId(), requestActivity.getServiceUrl()));

		List<T> responses = new ArrayList<>();
		while (true) {
			Activity activity;
			try {
				activity = queue.poll(getResponseTimeout(), TimeUnit.SECONDS);

				if (activity == null) {
					throw new TimeoutException(
							String.format("Timeout waiting for response. Activity: %s. Conversation: %s",
									requestActivity.getId(), requestActivity.getConversation().getId()));
				}

				if (clazz.isInstance(activity)) {
					responses.add((T) activity);
					if (responses.size() >= numberOfResponseMessagesExpected) {
						log.info(String.format("Got all (%d) responses for activity %s", responses.size(),
								requestActivity.getId()));
						break;
					} else {
						log.info(String.format("Got %d responses for activity %s", responses.size(),
								requestActivity.getId()));
					}
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw e;
			}
		}

		return responses;
	}

	protected Activity send(Activity activity) throws UnirestException, HttpResponseException, AuthenticationException {
		log.info(String.format(
				"Sending activity of type %s with activityId=%s, conversationId=%s with callbackUrl as %s",
				activity.getType(), activity.getId(), activity.getConversation().getId(), activity.getServiceUrl()));

		Jsonb jsonb = JsonbBuilder.create();
		String jsonPayload = jsonb.toJson(activity);

		log.debug("Sending payload: " + jsonPayload);

		RequestBodyEntity request = Unirest.post(getBotUrl()).body(jsonPayload);
		Map<String, List<String>> headers = request.getHttpRequest().getHeaders();
		headers.put(CONTENT_TYPE_HEADER_KEY, Collections.singletonList(CONTENT_TYPE_APPLICATION_JSON));

		if (hasAuthProperties()) {
			JMeterVariables vars = getThreadContext().getVariables();
			log.debug("Has security data");

			if (vars.get(TOKEN) == null) {
				log.debug("Token is null, authenticating");
				TokenResponse tokenResponse = AuthHelper.getToken(getPropertyAsString(BotServiceSecurityConfig.APP_ID),
						getPropertyAsString(BotServiceSecurityConfig.CLIENT_SECRET));

				vars.put(TOKEN, tokenResponse.getAccessToken());

			} else {
				log.debug("Token is not null, will reuse it");
			}

			String token = vars.get(TOKEN);
			headers.put(AUTHORIZATION_HEADER, Collections.singletonList("Bearer " + token));

		} else {
			log.debug("No security properties available. Will proceed without authentication.");
		}

		HttpResponse<InputStream> postResponse = request.asBinary();
		ensureResponseSuccess(postResponse);

		return activity;
	}

	private boolean hasAuthProperties() {
		return StringUtils.isNotEmpty(getPropertyAsString(BotServiceSecurityConfig.APP_ID));
	}

	protected String getFullResponseText(List<Message> responses) {
		String respText = getTextResponse(responses);
		String attachmentsText = getAttachmentsResponseAsJsonString(responses);

		if (attachmentsText != null) {
			respText += attachmentsText;
		}

		return respText;
	}

	protected String getTextResponse(List<Message> responses) {
		String respText = "";
		int i = 1;
		for (Message message : responses) {
			respText += String.format(RESPONSE_NUMBER, i++) + StringUtils.trimToEmpty(message.getText()) + NEW_LINE;
		}

		return respText + NEW_LINE;
	}

	protected String getAttachmentsResponseAsJsonString(List<Message> responses) {
		String attachmentsJsonAsString = "";

		Jsonb jsonb = JsonbBuilder.create();

		int i=1;
		for (Message message : responses) {
			if (message.getAttachments() != null && message.getAttachments().size() > 0) {
				for (Attachment attachment : message.getAttachments()) {
					String attachmentPayload = jsonb.toJson(attachment);
					attachmentsJsonAsString += String.format("ATTACHMENT #%d: ", i++) + attachmentPayload + NEW_LINE;
				}
			}
		}

		if (attachmentsJsonAsString.length() == 0) {
			attachmentsJsonAsString = null;
		}

		return StringUtils.trimToEmpty(attachmentsJsonAsString + NEW_LINE);
	}

	public boolean getGenRandomUserIdPerThread() {
		return getPropertyAsBoolean(BotServiceConfig.GEN_RANDOM_ID_PER_THREAD);
	}

	public String getFromMemberId() {
		return getPropertyAsString(BotServiceConfig.FROM_MEMBER_ID);
	}

	public String getRecipientMemberId() {
		return getPropertyAsString(BotServiceConfig.RECIPIENT_MEMBER_ID);
	}

	public String getChannelId() {
		return getPropertyAsString(BotServiceConfig.CHANNEL_ID);
	}

	public String getCallbackUrl() {
		return getPropertyAsString(BotServiceConfig.CALLBACK_URL);
	}

	private String getBotUrl() {
		return getPropertyAsString(BotServiceConfig.BOT_URL);
	}

	private int getResponseTimeout() {
		return getPropertyAsInt(BotServiceConfig.RESPONSE_TIMEOUT);
	}

}
