package me.cloudmine.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class RESTClient {
	public static final int GET = 1;
	public static final int POST = 2;
	public static final int PUT = 3;
	public static final int DELETE = 4;
	
	private HttpResponse response = null;
	private HashMap<String, String> headers = null;
	
	public void setHeader(String key, String value){
		if(headers == null){
			headers = new HashMap<String, String>();
		}
		headers.put(key, value);
	}

	public HttpResponse makeRequest(int type, String uri) throws ClientProtocolException, IOException{
		return makeRequest(type, uri, null);
	}
	
	public HttpResponse makeRequest(int type, String uri, String data) throws ClientProtocolException, IOException{
		HttpRequestBase request = null; 
		
		switch(type){
		case GET:
			request = new HttpGet(uri);
			break;
		case POST:
			request = new HttpPost(uri);
			break;
		case PUT:
			request = new HttpPut(uri);
			break;
		case DELETE:
			request = new HttpDelete(uri);
			break;
		default:
			throw new RuntimeException("Invalid HTTP request type: " + type);
		}
		
		if( headers != null ){
			for(Entry<String, String> header : headers.entrySet()){
				request.setHeader(header.getKey(), header.getValue());
			}
		}
		
		if( data != null){
			try {
				if( request instanceof HttpPut )
					((HttpPut) request).setEntity(new StringEntity(data, "UTF-8"));
				if( request instanceof HttpPost )
					((HttpPost) request).setEntity(new StringEntity(data, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}

		HttpClient client = new DefaultHttpClient();
		
		System.out.println("Making " + request.getMethod() + " request to: " + uri);
        HttpResponse httpResponse = client.execute(request);

        this.response = httpResponse;
        return httpResponse;
	}
	    
	public HttpResponse getResponse(){
		return this.response;
	}
	
	public int getStatusCode(){
		if( response != null ){
	        return response.getStatusLine().getStatusCode();
		} else {
			throw new RuntimeException("Request not executed");
		}
	}
	
	public String getStatusMessage(){
		if( response != null ){
	        return response.getStatusLine().getReasonPhrase();
		} else {
			throw new RuntimeException("Request not executed");
		}
	}
	

	public String getBody() throws IllegalStateException, IOException{
        HttpEntity entity = response.getEntity();
        if(entity != null){
			return readStream(entity.getContent());
        }
        return null;
	}
	
	private String readStream(InputStream in){
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuffer buff = new StringBuffer();
		String line;
		
		try {
			while( (line = reader.readLine()) != null ){
				buff.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return buff.toString();
	}

}
