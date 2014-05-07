package com.pz.xingfutao.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pz.xingfutao.entities.ItemDetailEntity;

public class SearchApi extends BaseApi{
	
	private static final String topSearchUrl = baseUrl + "wap_search_api.php?act=wap_search_keyword";
	private static final String searchResultUrl = baseUrl + "wap_search_api.php?";
	
	public static String getTopSearchUrl(){
		return topSearchUrl;
	}
	
	public static String getSearchUrl(String keyword){
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("act", "wap_search_goods"));
		params.add(new BasicNameValuePair("keywords", keyword));
		
		return searchResultUrl + URLEncodedUtils.format(params, "UTF-8");
	}
	
	public static List<String> parseTopSearch(JSONObject jsonObject){
		List<String> result = null;
		
		try{
			if(jsonObject.getString("result").equals("success")){
				JSONArray infoJSON = jsonObject.getJSONArray("info");
				result = new ArrayList<String>();
				for(int i = 0; i < infoJSON.length(); i++){
					result.add(infoJSON.getJSONObject(i).getString("keyword"));
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static List<ItemDetailEntity> parseSearchResult(JSONObject jsonObject){
		List<ItemDetailEntity> result = null;
		
		try{
			if(jsonObject.getString("result").equals("success")){
				result = new ArrayList<ItemDetailEntity>();
				JSONArray infoJson = jsonObject.getJSONArray("info");
				
				result = new Gson().fromJson(infoJson.toString(), new TypeToken<List<ItemDetailEntity>>(){}.getType());
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return result;
		
	}
}
