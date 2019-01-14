package br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas;

import java.util.ArrayList;
import java.util.List;

public class SuggestedActions {

	private List<String> to;

	private List<CardAction> actions;

	public SuggestedActions() {
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public List<CardAction> getActions() {
		return actions;
	}

	public void setActions(List<CardAction> actions) {
		this.actions = actions;
	}
}
