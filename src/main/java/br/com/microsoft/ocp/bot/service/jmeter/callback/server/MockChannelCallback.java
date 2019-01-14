package br.com.microsoft.ocp.bot.service.jmeter.callback.server;

import java.io.InputStream;
import java.nio.charset.Charset;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.microsoft.ocp.bot.service.jmeter.plugin.schemas.Activity;

@Path("/v3")
public class MockChannelCallback {
	private static final Logger log = LoggerFactory.getLogger(MockChannelCallback.class);

	@POST
	@Path("/conversations")
	public Response createConversation() {
		log.info("createConversation()");

		return Response.accepted().build();
	}

	@POST
	@Path("/conversations/{conversationId}/activities")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendToConversation(@PathParam("conversationId") String conversationId, Activity activity) {
		log.info(String.format("sendToConversation(conversationId=%d) [Activity Id=%d])", conversationId,
				activity.getId()));
		return Response.accepted().build();
	}

	@POST
	@Path("/conversations/{conversationId}/activities/{activityId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response replyToActivity(@PathParam("conversationId") String conversationId,
			@PathParam("activityId") String activityId, InputStream is) {
		log.info(String.format("replyToActivity(conversationId=%s, activityId=%s)", conversationId, activityId));
		String payload;
		try {
			payload = IOUtils.toString(is, Charset.forName("UTF-8"));
			Jsonb jsonb = JsonbBuilder.create();
			Activity activity = jsonb.fromJson(payload, Activity.class);

			if (activity.getType() == "typing") {
				log.info(String.format(
						"Typing response received, ignoging for replyToActivity(conversationId=%s, activityId=%s) [Activity(replyToId=%s)]",
						conversationId, activityId, activity.getReplyToId()));
			} else {
				activity = ActivityParserFactory.parse(activity.getType(), payload, conversationId, activityId);
				activity.setReplyToId(activityId);

				ActivityRequestReply.getInstance().setResponse(activityId, activity);
				log.info(String.format("replyToActivity(conversationId=%s, activityId=%s) [Activity(replyToId=%s)]",
						conversationId, activityId, activity.getReplyToId()));
			}

			return Response.accepted().build();
		} catch (Exception e) {
			log.error("Error in replyToActivity", e);
			return Response.serverError().entity("Error: " + e).build();
		}
	}

	@PUT
	@Path("/conversations/{conversationId}/activities/{activityId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateActivity(@PathParam("conversationId") String conversationId,
			@PathParam("acitivityId") String activityId, Activity activity) {
		log.info(String.format("replyToActivity(conversationId=%s, activityId=%s)", conversationId, activityId));
		return Response.accepted().build();
	}

	@DELETE
	@Path("/conversations/{conversationId}/activities/{activityId}")
	public Response deleteActivity(@PathParam("conversationId") String conversationId,
			@PathParam("acitivityId") String activityId) {
		log.info(String.format("deleteActivity(conversationId=%s, activityId=%s)", conversationId, activityId));

		return Response.accepted().build();
	}

	@GET
	@Path("/conversations/{conversationId}/members")
	public Response getConversationMembers(@PathParam("conversationId") String conversationId) {
		log.info(String.format("getConversationMembers(conversationId=%s)", conversationId));

		return Response.ok().build();
	}

	@GET
	@Path("/conversations/{conversationId}/activities/{activityId}/members")
	public Response getActivityMembers(@PathParam("conversationId") String conversationId,
			@PathParam("acitivityId") String activityId) {
		log.info(String.format("getActivityMembers(conversationId=%s, activityId=%s)", conversationId));

		return Response.ok().build();
	}

	@POST
	@Path("/conversations/{conversationId}/attachments")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendAttachments(@PathParam("conversationId") String conversationId, Activity activity) {
		log.info(String.format("sendAttachments(conversationId=%s)", conversationId));

		return Response.ok().build();
	}

}