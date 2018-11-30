package br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas;

public class ChannelData {

	private String clientActivityId;

	public ChannelData() {
	}

	public ChannelData(String clientActivityId) {
		this.clientActivityId = clientActivityId;
	}

	public String getClientActivityId() {
		return clientActivityId;
	}
	
	public void setClientActivityId(String clientActivityId) {
		this.clientActivityId = clientActivityId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientActivityId == null) ? 0 : clientActivityId.hashCode());
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
		ChannelData other = (ChannelData) obj;
		if (clientActivityId == null) {
			if (other.clientActivityId != null)
				return false;
		} else if (!clientActivityId.equals(other.clientActivityId))
			return false;
		return true;
	}

}
