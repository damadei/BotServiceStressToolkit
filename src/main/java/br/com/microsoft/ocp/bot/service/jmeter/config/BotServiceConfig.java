package br.com.microsoft.ocp.bot.service.jmeter.config;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.testelement.TestStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.microsoft.ocp.bot.service.jmeter.callback.server.EmbeddedServer;

public class BotServiceConfig extends ConfigTestElement implements TestStateListener {

	private static final Logger log = LoggerFactory.getLogger(BotServiceConfig.class);

	private static final long serialVersionUID = 904907815828684192L;
	public static final String BOT_URL = "BOT_URL";
	public static final String CALLBACK_URL = "CALLBACK_URL";
	public static final String CALLBACK_SERVER_HOST = "CALLBACK_SERVER_HOST";
	public static final String CALLBACK_SERVER_PORT = "CALLBACK_SERVER_PORT";
	public static final String FROM_MEMBER_ID = "FROM_MEMBER_ID";
	public static final String RECIPIENT_MEMBER_ID = "RECIPIENT_MEMBER_ID";
	public static final String CHANNEL_ID = "CHANNEL_ID";
	public static final String GEN_RANDOM_ID_PER_THREAD = "GEN_RANDOM_ID_PER_THREAD";
	public static final String RESPONSE_TIMEOUT = "RESPONSE_TIMEOUT";

	public static final String BOT_URL_DEFAULT_VALUE = "http://localhost:3978/api/messages";
	public static final String CALLBACK_URL_DEFAULT_VALUE = "http://localhost:45678";
	public static final String CALLBACK_SERVER_HOST_DEFAULT_VALUE = "";
	public static final String CALLBACK_SERVER_PORT_DEFAULT_VALUE = "45678";
	public static final String FROM_MEMBER_ID_DEFAULT_VALUE = "default-user";
	public static final String RECIPIENT_MEMBER_ID_DEFAULT_VALUE = "default-bot";
	public static final String CHANNEL_ID_DEFAULT_VALUE = "emulator";
	public static final boolean GEN_RANDOM_ID_PER_THREAD_DEFAULT_VALUE = true;
	public static final int RESPONSE_TIMEOUT_DEFAULT_VALUE = 5;

	@Override
	public void testStarted() {
		log.info("Test started. Starting the embedded callback server.");
		EmbeddedServer.ensureServerRunning(getPropertyAsString(CALLBACK_SERVER_HOST),
				getPropertyAsInt(CALLBACK_SERVER_PORT));
		log.info("Embedded callback server started.");
	}

	@Override
	public void testStarted(String host) {
		testStarted();
	}

	@Override
	public void testEnded() {
		try {
			log.info("Test stopped. Stopping the embedded callback server.");
			EmbeddedServer.stop();
			log.info("Embedded callback server stopped.");
		} catch (Exception e) {
			log.error("Error stopping server", e);
		}
	}

	@Override
	public void testEnded(String host) {
		testEnded();
	}
}
