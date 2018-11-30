package br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas;

import java.util.List;

public class ConversationUpdate extends Activity {

	public static final String CONVERSATION_UPDATE_TYPE = "conversationUpdate";
	
	public ConversationUpdate() {
	}

	private List<Member> membersAdded;

	private List<Member> membersRemoved;

	private String topicName;

	private String historyDisclosed;

	public List<Member> getMembersAdded() {
		return membersAdded;
	}

	public void setMembersAdded(List<Member> membersAdded) {
		this.membersAdded = membersAdded;
	}

	public List<Member> getMembersRemoved() {
		return membersRemoved;
	}

	public void setMembersRemoved(List<Member> membersRemoved) {
		this.membersRemoved = membersRemoved;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getHistoryDisclosed() {
		return historyDisclosed;
	}

	public void setHistoryDisclosed(String historyDisclosed) {
		this.historyDisclosed = historyDisclosed;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
