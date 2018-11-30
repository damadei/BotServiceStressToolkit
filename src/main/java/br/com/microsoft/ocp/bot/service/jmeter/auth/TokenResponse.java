package br.com.microsoft.ocp.bot.service.jmeter.auth;

import javax.json.bind.annotation.JsonbProperty;

public class TokenResponse {

	@JsonbProperty("token_type")
	private String tokenType;

	@JsonbProperty("expires_in")
	private int expiresIn;

	@JsonbProperty("ext_expires_in")
	private int extExpiresIn;

	@JsonbProperty("access_token")
	private String accessToken;

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public int getExtExpiresIn() {
		return extExpiresIn;
	}

	public void setExtExpiresIn(int extExpiresIn) {
		this.extExpiresIn = extExpiresIn;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "TokenResponse [tokenType=" + tokenType + ", expiresIn=" + expiresIn + ", extExpiresIn=" + extExpiresIn
				+ ", accessToken=" + accessToken + "]";
	}

}
