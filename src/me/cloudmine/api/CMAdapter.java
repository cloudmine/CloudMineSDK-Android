package me.cloudmine.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.util.Log;

public class CMAdapter {
	private static String API_SERVER = "http://nosslapi.cloudmine.me";

	String app_id, api_key;
	
	public CMAdapter(){
	}
	
	public CMAdapter(String app_id, String api_key){
		init(app_id, api_key);
	}
	
	public void init(String app_id, String api_key){
		this.app_id = app_id;
		this.api_key = api_key;
	}

	public static String join(Object[] array, char separator) {
        if (array == null) {
            return null;
        }
        int arraySize = array.length;
        int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0].toString().length()) + 1) * arraySize);
        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }


	public JSONObject getValues(){
		return getValues(null, null);	
	}
	
	public JSONObject getValues(String[] keys){
		return getValues(keys, null);
	}
	
	public JSONObject getValues(String[] keys, SnippetOptions options){
     	// make request to API_SERVER/v1/app/APP_ID/text/
		String uri = API_SERVER + "/v1/app/" + app_id + "/text";
		
		if ((null != options) && (null != options.snippetName)){
			uri += "?f=" + options.snippetName;
		}
		else{
			uri += "/";
		}
		
		if(keys != null){
			uri += "?keys=" + join(keys, ',');
		}
		
		RawRESTClient client = new RawRESTClient();
		client.setHeader("X-CloudMine-ApiKey", api_key);

		if( client.makeRequest(RESTClient.GET, uri, options) == null ){
			return null;
		}
		
		if( client.getStatusCode() >= 300 ){
			Log.d("JSON", "GET call returned HTTP code: " + client.getStatusCode());
        	return null;
		}

		String content = client.getBody();
		if(content == null){
			return null;
		}

		JSONObject response; 
		try {
			response = new JSONObject(content);

			if (null != options)
			{
				return response;
			}
			else
			{
				JSONObject success = response.getJSONObject("success");
				return success;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ContentValues jsonToContentValues(JSONObject json){
		ContentValues values = new ContentValues(json.length());
		Iterator<String> keys = json.keys();
		while(keys.hasNext()){
			String key = keys.next();
			try {
				values.put(key, json.getString(key));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return values;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map contentValuesToMap(ContentValues value){
		Map m = new HashMap<String, Object>();
		for(Entry<String, Object> e : value.valueSet()){
			m.put(e.getKey(), e.getValue());
		}
		return m;
	}
	
	@SuppressWarnings("rawtypes")
	public String updateValue(String key, Map values){
		JSONObject value = new JSONObject(values);
		return updateValue(key, value, null);
	}

	@SuppressWarnings("rawtypes")
	public String updateValue(String key, Map values, SnippetOptions options){
		JSONObject value = new JSONObject(values);
		return updateValue(key, value, options);
	}
	
	public String updateValue(String key, JSONObject value){
		return updateValue(key, value, null);
	}
	
	public String updateValue(String key, ContentValues values){
		JSONObject value = new JSONObject(contentValuesToMap(values));
		return updateValue(key, value, null);
	}
	
	public String updateValue(String key, ContentValues values, SnippetOptions options){
		JSONObject value = new JSONObject(contentValuesToMap(values));
		return updateValue(key, value, options);
	}
	
	public String updateValue(String key, JSONObject value, SnippetOptions options){ //TODO
		JSONObject dataobj = new JSONObject();
		try {
			dataobj.put(key, value);
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		String data= "";
		try {
			data = dataobj.toString(4);
			System.out.println("Putting value: " + data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String uri = API_SERVER + "/v1/app/" + app_id + "/text";
		
		if ((null != options) && (null != options.snippetName)){
			uri += "?f=" + options.snippetName;
		}
		else{
			uri += "/";
		}
				
		RawRESTClient client = new RawRESTClient();
		client.setHeader("X-CloudMine-ApiKey", api_key);
		client.setHeader("content-type", "application/json");
		client.makeRequest(RESTClient.PUT, uri, data, options);
		if(client.getResponse() == null){
			return null;
		}

		Log.d("JSON", "PUT call returned HTTP code: " + client.getStatusCode() + " " + client.getStatusMessage());

		String content = client.getBody();
		if(content == null){
			return null;
		}

		if(client.getStatusCode() >= 300){
			Log.d("JSON", "HTTP content: " + content);
			return null;
		}

		JSONObject response; 
		try {
			response = new JSONObject(content);

			if (null != options)
			{
				return response.toString();
			}
			else
			{
				JSONObject success = response.getJSONObject("success");
				String key_ret = success.keys().next().toString();
				return key_ret;
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
        return null;
	}
	
	public String deleteKeys(){
		return deleteKeys(null, null);	
	}
	
	public String deleteKeys(String[] keys){
		return deleteKeys(keys, null);	
	}
	
	public String deleteKeys(String[] keys, SnippetOptions options){
		String uri = API_SERVER + "/v1/app/" + app_id + "/data";
		
		if ((null != options) && (null != options.snippetName)){
			uri += "?f=" + options.snippetName;
		}
		else{
			uri += "/";
		}
		
		if(keys != null){
			uri += "?keys=" + join(keys, ',');
		}
		
		RawRESTClient client = new RawRESTClient();
		client.setHeader("X-CloudMine-ApiKey", api_key);
		if( client.makeRequest(RESTClient.DELETE, uri, options) == null ){
			return null;
		}
		
		if(client.getResponse() == null){
			return null;
		}
		
		String content = client.getBody();
		if(content == null){
			return null;
		}
		
		JSONObject response; 
		try {
			response = new JSONObject(content);
			if (null != options)
			{
				return response.toString();
			}
			else
			{
				JSONObject success = response.getJSONObject("success");
				return success.toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.d("JSON", "DELETE call returned HTTP code: " + client.getStatusCode());
        
		return null; 	
	}

}
