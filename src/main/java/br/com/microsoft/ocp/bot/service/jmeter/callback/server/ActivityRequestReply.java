package br.com.microsoft.ocp.bot.service.jmeter.callback.server;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Activity;

public class ActivityRequestReply {
	private static final Logger log = LoggerFactory.getLogger(ActivityRequestReply.class);

	private static final ActivityRequestReply INSTANCE = new ActivityRequestReply();

	public static ActivityRequestReply getInstance() {
		return INSTANCE;
	}

	private Map<String, BlockingQueue<Activity>> buffer = new ConcurrentHashMap<>();

	public synchronized BlockingQueue<Activity> setRequest(String activityId) {
		log.info(String.format("setRequest(%s)", activityId));

		BlockingQueue<Activity> queue = null;

		if (buffer.get(activityId) == null) {
			queue = new LinkedBlockingQueue<Activity>();
			buffer.put(activityId, queue);
		} else {
			queue = (BlockingQueue<Activity>) buffer.get(activityId);
		}

		return queue;
	}

	public synchronized void setResponse(String replyToActivityId, Activity response) throws InterruptedException {
		BlockingQueue<Activity> queue = buffer.get(replyToActivityId);

		if (queue == null) {
			log.debug(String.format("Received a callback for activity %s but no sampler is waiting. Discarding it...",
					replyToActivityId));
		} else {
			log.debug(String.format("Received a callback for activity %s. Sending to queue.", replyToActivityId));
			queue.put(response);
		}

	}

	public synchronized void clear(String replyToActivityId) {
		buffer.remove(replyToActivityId);
	}
}
