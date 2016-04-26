package com.fortify.processrunner.jira.processor;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.HttpMethod;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;

import com.fortify.processrunner.context.Context;
import com.fortify.processrunner.context.ContextProperty;
import com.fortify.processrunner.jira.connection.JiraConnectionFactory;
import com.fortify.processrunner.jira.context.IContextJira;
import com.fortify.processrunner.processor.AbstractProcessor;
import com.fortify.util.rest.IRestConnection;
import com.sun.jersey.api.client.WebResource;

public abstract class AbstractProcessorJiraSubmitIssue extends AbstractProcessor {
	private static final Log LOG = LogFactory.getLog(AbstractProcessorJiraSubmitIssue.class);
	private JiraConnectionFactory connectionFactory;

	public JiraConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(JiraConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	@Override
	public List<ContextProperty> getContextProperties(Context context) {
		List<ContextProperty> result = new ArrayList<ContextProperty>();
		result.add(new ContextProperty("JiraUserName", "Jira user name", false));
		result.add(new ContextProperty("JiraPassword", "Jira password", false));
		return result;
	}
	
	@Override
	// TODO Add IContextJiraUserCredentials interface instead of accessing map keys
	// TODO Simplify this method?
	protected boolean preProcess(Context context) {
		JiraConnectionFactory cf = getConnectionFactory();
		String userName = (String)context.get("JiraUserName");
		String password = (String)context.get("JiraPassword");
		if ( !StringUtils.isBlank(userName) ) {
			cf.setUserName(userName);
		}
		if ( !StringUtils.isBlank(password) ) {
			cf.setPassword(password);
		}
		
		if ( cf.getUserName()==null ) {
			cf.setUserName(System.console().readLine("Jira User Name: "));
		}
		if ( cf.getPassword()==null ) {
			cf.setPassword(new String(System.console().readPassword("Jira Password: ")));
		}
		return true;
	}
	
	@Override
	protected boolean process(Context context) {
		IRestConnection conn = connectionFactory.getConnection();
		JSONObject issueToBeSubmitted = getIssueToBeSubmitted(context);
		LOG.trace("Submitting issue to JIRA: "+issueToBeSubmitted);
		WebResource.Builder resource = conn.getBaseResource().path("/rest/api/latest/issue").entity(issueToBeSubmitted);
		JSONObject submitResult = conn.executeRequest(HttpMethod.POST, resource, JSONObject.class);
		String submittedIssueKey = submitResult.optString("key");
		
		IContextJira contextJira = context.as(IContextJira.class);
		String submittedIssueBrowserURL = conn.getBaseResource()
				.path("/browse/").path(submittedIssueKey).getURI().toString();
		contextJira.setSubmittedIssueId(submittedIssueKey);
		contextJira.setSubmittedIssueBugTrackerName("JIRA");
		contextJira.setSubmittedIssueBrowserURL(submittedIssueBrowserURL);
		return true;
	}
	
	protected abstract JSONObject getIssueToBeSubmitted(Context context);
}