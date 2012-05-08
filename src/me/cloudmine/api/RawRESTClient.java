package me.cloudmine.api;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

/**
 * Just like {@link RESTClient}, but swallows all exceptions
 * @author ilya
 *
 */
public class RawRESTClient extends RESTClient {

	@Override
	public HttpResponse makeRequest(int type, String uri, String data){
		try {
			return super.makeRequest(type, uri, data);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public HttpResponse makeRequest(int type, String uri, String data, SnippetOptions options){
		try {
			return super.makeRequest(type, uri, data, options);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public HttpResponse makeRequest(int type, String uri){
		try {
			return super.makeRequest(type, uri);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public HttpResponse makeRequest(int type, String uri, SnippetOptions options){
		try {
			return super.makeRequest(type, uri, options);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public String getBody(){
		try {
			return super.getBody();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
