package me.cloudmine.api;

import org.json.JSONObject;

public class SnippetOptions 
{

	/**
	 * The name of the snippet you would like to run
	 * Found at your dashboard at cloudmine.me
	 */
	public String snippetName = null;
	
	/**
	 * When true, only the results of the function call are returned. When false (default), 
	 * return the original data along with the results of the function call.
	 */
	public boolean resultOnly = false;
	
	/**
	 * When true, run the code snippet asynchronously. This will cause the HTTP call to return 
	 * immediately but will leave your code running on our servers. The only way to return data 
	 * from an asynchronous call is to save it back using the CloudMine API from within the function. 
	 * When false (default), the HTTP call will not complete until the function is done running.
	 */
	public boolean async = false;

	/**
	 * Allows you to pass in arbitrary parameters to the function. They will be available as data.params. 
	 * If specified as valid JSON, they will already be parsed as a JSON object.
	 */
	public JSONObject params = null;
	
}
