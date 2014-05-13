package com.pz.xingfutao.net;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class XFJsonObjectRequest extends JsonObjectRequest{
	
	private Context context;
	
	private Map<String, String> params;
	
	public XFJsonObjectRequest(Context context, int method, String url, Listener<JSONObject> listener, ErrorListener errorListener){
		super(method, url, null, listener, errorListener);
		
		this.context = context;
	}

	public XFJsonObjectRequest(Context context, int method, String url, JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		
		this.context = context;
	}
	
	public XFJsonObjectRequest(Context context, int method, String url, Map<String, String> params, Listener<JSONObject> listener, ErrorListener errorListener){
		super(method, url, null, listener, errorListener);
		
		this.context = context;
		this.params = params;
	}
	
	@Override
	public Map<String, String> getParams(){
		return params;
	}
    
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response){
    	SessionHandler.checkSessionCookie(context, response.headers);
    	
    	return super.parseNetworkResponse(response);
    }
    
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError{
    	Map<String, String> headers = super.getHeaders();
    	
    	if(headers == null || headers.equals(Collections.emptyMap())){
    		headers = new HashMap<String, String>();
    	}
    	
    	SessionHandler.addSessionCookie(context, headers);
    	
    	return headers;
    }

}
