package br.com.microsoft.ocp.bot.service.jmeter.builder;

import java.util.List;
import java.util.UUID;

import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Conversation;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.ConversationUpdate;
import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Member;

public class ConversationUpdateActivityBuilder extends ActivityBaseBuilder {

	public ConversationUpdateActivityBuilder() {
	}

	public static ConversationUpdate build(List<Member> membersAdded, Member from, Member recipient, String channelId,
			String serviceUrl) {

		ConversationUpdate conversationUpdate = new ConversationUpdate();
		conversationUpdate.setType(ConversationUpdate.CONVERSATION_UPDATE_TYPE);
		conversationUpdate.setMembersAdded(membersAdded);
		conversationUpdate.setChannelId(channelId);
		conversationUpdate.setTimestamp(getTimestamp());
		conversationUpdate.setLocalTimestamp(getLocalTimestamp());
		conversationUpdate.setConversation(new Conversation(UUID.randomUUID().toString()));
		conversationUpdate.setFrom(from);
		conversationUpdate.setRecipient(recipient);
		conversationUpdate.setServiceUrl(serviceUrl);

		setActivityProperties(conversationUpdate);

		return conversationUpdate;
	}
}
