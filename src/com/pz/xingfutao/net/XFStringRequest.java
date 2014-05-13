package com.pz.xingfutao.net;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class XFStringRequest extends StringRequest{

	private Context context;
	
	private Map<String, String> params;
    
    public XFStringRequest(Context context, int method, String url, Listener<String> listener, ErrorListener errorListener){
    	super(method, url, listener, errorListener);
    }
    
    public XFStringRequest(Context context, int method, String url, Map<String, String> params, Listener<String> listener, ErrorListener errorListener){
    	super(method, url, listener, errorListener);
    	
    	this.params = params;
    }
    
    @Override
    public Map<String, String> getParams(){
    	return params;
    }
    
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response){
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
