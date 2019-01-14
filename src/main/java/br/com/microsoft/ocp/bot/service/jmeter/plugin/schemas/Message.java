package br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas;

import java.util.ArrayList;
import java.util.List;

public class Message extends Activity {

	public static final String MESSAGE_TYPE = "message";
	
	public Message() {
	}

	private String text;
	private String textFormat;
	private String locale;
	private String speak;
	private String inputHint;
	private List<Attachment> attachments;
	private String attachmentLayout;
	private String summary;
	private SuggestedActions suggestedActions;
	private String value;
	private String expiration;
	private String importance;
	private String deliveryMode;

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setTextFormat(String textFormat) {
		this.textFormat = textFormat;
	}

	public String getTextFormat() {
		return textFormat;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getLocale() {
		return locale;
	}

	public void setSpeak(String speak) {
		this.speak = speak;
	}

	public String getSpeak() {
		return speak;
	}

	public void setInputHint(String inputHint) {
		this.inputHint = inputHint;
	}

	public String getInputHint() {
		return inputHint;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getAttachmentLayout() {
		return attachmentLayout;
	}

	public void setAttachmentLayout(String attachmentLayout) {
		this.attachmentLayout = attachmentLayout;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public SuggestedActions getSuggestedActions() {
		return suggestedActions;
	}

	public void setSuggestedActions(SuggestedActions suggestedActions) {
		this.suggestedActions = suggestedActions;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
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
