package br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas;

import java.util.List;

public class Activity {

	public Activity() {
	}

	private String type;

	private String id;

	private String channelId;

	private String timestamp;

	private String localTimestamp;

	private Member from;

	private Member recipient;

	private Conversation conversation;

	private ChannelData channelData;

	private String replyToId;

	private List<Entity> entities;

	private String serviceUrl;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setLocalTimestamp(String localTimestamp) {
		this.localTimestamp = localTimestamp;
	}

	public String getLocalTimestamp() {
		return localTimestamp;
	}

	public Member getFrom() {
		return from;
	}

	public void setFrom(Member from) {
		this.from = from;
	}

	public Member getRecipient() {
		return recipient;
	}

	public void setRecipient(Member recipient) {
		this.recipient = recipient;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public ChannelData getChannelData() {
		return channelData;
	}

	public void setChannelData(ChannelData channelData) {
		this.channelData = channelData;
	}

	public String getReplyToId() {
		return replyToId;
	}

	public void setReplyToId(String replyToId) {
		this.replyToId = replyToId;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conversation == null) ? 0 : conversation.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (conversation == null) {
			if (other.conversation != null)
				return false;
		} else if (!conversation.equals(other.conversation))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
