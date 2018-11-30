package br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas;

public class ClientCapabilities extends Entity {

	private String type = "ClientCapabilities";
	private Boolean requiresBotState;
	private Boolean supportsTts;
	private Boolean supportsListening;

	public ClientCapabilities() {
	}

	public ClientCapabilities(Boolean requiresBotState, Boolean supportsTts, Boolean supportsListening) {
		super();
		this.requiresBotState = requiresBotState;
		this.supportsTts = supportsTts;
		this.supportsListening = supportsListening;
	}

	public String getType() {
		return type;
	}

	public Boolean getRequiresBotState() {
		return requiresBotState;
	}

	public void setRequiresBotState(Boolean requiresBotState) {
		this.requiresBotState = requiresBotState;
	}

	public Boolean getSupportsTts() {
		return supportsTts;
	}

	public void setSupportsTts(Boolean supportsTts) {
		this.supportsTts = supportsTts;
	}

	public Boolean getSupportsListening() {
		return supportsListening;
	}

	public void setSupportsListening(Boolean supportsListening) {
		this.supportsListening = supportsListening;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((requiresBotState == null) ? 0 : requiresBotState.hashCode());
		result = prime * result + ((supportsListening == null) ? 0 : supportsListening.hashCode());
		result = prime * result + ((supportsTts == null) ? 0 : supportsTts.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ClientCapabilities other = (ClientCapabilities) obj;
		if (requiresBotState == null) {
			if (other.requiresBotState != null)
				return false;
		} else if (!requiresBotState.equals(other.requiresBotState))
			return false;
		if (supportsListening == null) {
			if (other.supportsListening != null)
				return false;
		} else if (!supportsListening.equals(other.supportsListening))
			return false;
		if (supportsTts == null) {
			if (other.supportsTts != null)
				return false;
		} else if (!supportsTts.equals(other.supportsTts))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
