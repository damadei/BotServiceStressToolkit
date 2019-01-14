/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package br.com.microsoft.ocp.bot.service.jmeter.sampler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.microsoft.ocp.bot.service.jmeter.auth.AuthenticationException;
import br.com.microsoft.ocp.bot.service.jmeter.builder.ConversationUpdateActivityBuilder;
import br.com.microsoft.ocp.bot.service.jmeter.callback.server.ActivityRequestReply;
import br.com.microsoft.ocp.bot.service.jmeter.callback.server.HttpResponseException;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Activity;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.ConversationUpdate;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Member;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Message;

public class ConversationUpdateSampler extends BaseBotSampler {

	private static final String USER_PLACEHOLDER = "${user}";

	private static final long serialVersionUID = 240L;

	private static final Logger log = LoggerFactory.getLogger(ConversationUpdateSampler.class);

	public static final String NUM_OF_EXPECTED_RESPONSES = "NUM_OF_EXPECTED_RESPONSES";
	public static final String MEMBERS_ADDED = "MEMBERS_ADDED";

	public static final int NUM_OF_EXPECTED_RESPONSES_DEFAULT_VALUE = 0;
	public static final String MEMBERS_ADDED_DEFAULT_VALUE = "${user}, default-bot";

	public ConversationUpdateSampler() {
	}

	@Override
	public SampleResult sample(Entry entry) {
		JMeterContext context = getThreadContext();
		JMeterVariables vars = context.getVariables();

		SampleResult res = new SampleResult();
		res.setSampleLabel(getName());
		res.sampleStart();
		res.setDataType(SampleResult.TEXT);

		try {
			ConversationUpdate conversationUpdate = createConversationUpdate();
			vars.put(Constants.CONVERSATION_ID, conversationUpdate.getConversation().getId());

			BlockingQueue<Activity> queue = null;
			if (shouldWaitForResponseActivity()) {
				queue = ActivityRequestReply.getInstance().setRequest(conversationUpdate.getId());
			}

			send(conversationUpdate);

			String respText = "CONVERSATION: " + conversationUpdate.getConversation().getId() + NEW_LINE;
			respText += "USER ID: " + conversationUpdate.getFrom().getId() + NEW_LINE;

			if (shouldWaitForResponseActivity()) {
				List<Message> responses = getResponses(conversationUpdate, queue, getNumberOfResponseMessagesExpected(),
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

	private ConversationUpdate createConversationUpdate() {
		Member fromMember = new Member(ensureFromUser());
		Member recipientMember = new Member(getRecipientMemberId());
		
		List<String> membersAddedIds = null;

		if(getMembersAdded().contains(",")) {
			membersAddedIds = Arrays.asList(getMembersAdded().split(",|,\\s*|\\s*,\\s*|\\s*,"));
		} else {
			membersAddedIds = Arrays.asList(new String[]{getMembersAdded()});
		}

		List<Member> membersAdded = new ArrayList<>();
		for (String memberId : membersAddedIds) {
			memberId = memberId.trim();

			//special placeholder to add username to the conversation
			//as username can be random generated we have to get it dynamically
			if(memberId.equalsIgnoreCase(USER_PLACEHOLDER)) {
				memberId = fromMember.getId();
			}

			membersAdded.add(new Member(memberId));
		}

		ConversationUpdate conversationUpdate = ConversationUpdateActivityBuilder.build(membersAdded, fromMember,
				recipientMember, getChannelId(), getCallbackUrl());

		return conversationUpdate;
	}

	private String ensureFromUser() {
		JMeterContext context = getThreadContext();
		JMeterVariables vars = context.getVariables();

		String fromUserId = "";
		if (getGenRandomUserIdPerThread()) {
			if (vars.get(Constants.USER) == null) {
				fromUserId = "user-" + RandomStringUtils.randomAlphabetic(3, 7);
				vars.put(Constants.USER, fromUserId);
			} else {
				fromUserId = vars.get(Constants.USER);
			}
		} else {
			fromUserId = getFromMemberId();
		}

		return fromUserId;
	}

	public int getNumberOfResponseMessagesExpected() {
		return getPropertyAsInt(NUM_OF_EXPECTED_RESPONSES);
	}

	public void setNumberOfResponseMessagesExpected(int numberOfResponseMessagesExpected) {
		setProperty(NUM_OF_EXPECTED_RESPONSES, numberOfResponseMessagesExpected);
	}

	public String getMembersAdded() {
		return getPropertyAsString(MEMBERS_ADDED);
	}

	public void setMembersAdded(String membersAdded) {
		setProperty(MEMBERS_ADDED, membersAdded);
	}
}
