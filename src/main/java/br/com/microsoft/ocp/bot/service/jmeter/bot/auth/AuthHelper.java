package br.com.microsoft.ocp.bot.service.jmeter.bot.auth;

import java.io.InputStream;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class AuthHelper {
	public static TokenResponse getToken(String appId, String clientSecret) throws AuthenticationException {

		HttpResponse<InputStream> postResponse;
		try {
			postResponse = Unirest.post("https://login.microsoftonline.com/botframework.com/oauth2/v2.0/token")
					.field("grant_type", "client_credentials")
					.field("client_id", appId)
					.field("client_secret", clientSecret)
					.field("scope", String.format("%s/.default", appId)).asBinary();

			if (postResponse.getStatus() == 200) {
				Jsonb jsonb = JsonbBuilder.create();
				TokenResponse resp = jsonb.fromJson(postResponse.getBody(), TokenResponse.class);
				return resp;
			} else {
				throw new AuthenticationException("Status code is not 200: " + postResponse.getStatus()
						+ ". Response text: " + postResponse.getStatusText());
			}

		} catch (UnirestException e) {
			throw new AuthenticationException("Error authenticating Bot", e);
		}
	}
}
