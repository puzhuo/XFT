package com.pz.xingfutao.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pz.xingfutao.entities.CommentEntity;
import com.pz.xingfutao.entities.ImageBrickEntity;
import com.pz.xingfutao.entities.ImageFlowEntity;
import com.pz.xingfutao.entities.ImageHotEntity;
import com.pz.xingfutao.entities.ImageMap;
import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.entities.base.BaseTabStoreEntity;
import com.pz.xingfutao.utils.PLog;

public class ContentApi extends BaseApi{
	
	private static final String storeContentUrl = baseUrl + "wap_goods_api.php?act=search_all";
	private static final String goodDetailUrl = baseUrl + "wap_goods_api.php?act=search_goods_detail";
	private static final String mainCategoryUrl = baseUrl + "wap_goods_api.php?act=search_category_tree";
	private static final String mainCategoryDetailListUrl = baseUrl + "wap_goods_api.php?act=search_category";
	private static final String recommendGoodListUrl = baseUrl + "wap_goods_api.php?act=search_goods_list&intro=";
	
	private static final String itemDetailCommentUrl = baseUrl + "wap_goods_api.php?act=search_comment_list";
	
	private static final String feedbackUrl = baseUrl + "";
	
	public static String getStoreContentUrl(){
		return storeContentUrl;
	}
	
	public static String getGoodDetailUrl(String goodId){
		return goodDetailUrl + "&goods_id=" + goodId;
	}
	
	public static String getMainCategoryUrl(){
		return mainCategoryUrl;
	}
	
	public static String getMainCategoryDetailListUrl(String cId){
		return mainCategoryDetailListUrl + "&c_id=" + cId;
	}
	
	public static String getRecommendGoodListUrl(String key){
		return recommendGoodListUrl + key;
	}
	
	public static String getFeedbackUrl(String content){
		return feedbackUrl + "?content=" + content;
	}
	
	public static String getItemDetailCommentUrl(String goodId){
		return itemDetailCommentUrl + "&goods_id=" + goodId;
	}
	
	public static boolean checkFeedback(String response){
		return false;
	}
	
	public static List<CommentEntity> parseItemDetailComment(String response){
		List<CommentEntity> result = null;
		try{
			JSONObject jsonObject = new JSONObject(response);
			
			result = new ArrayList<CommentEntity>();
			if(jsonObject.has("result") && jsonObject.getString("result").equals("success")){
				JSONArray infoArray = jsonObject.getJSONArray("info");
				int length = infoArray.length();
				for(int i = 0; i < length; i++){
					CommentEntity commentEntity = new CommentEntity();
					commentEntity.setName(infoArray.getJSONObject(i).getString("username"));
					commentEntity.setComment(infoArray.getJSONObject(i).getString("content"));
					commentEntity.setCommentId(infoArray.getJSONObject(i).getString("add_time"));
					
					PLog.d("comment", "asdf");
					
					result.add(commentEntity);
				}
			}
			
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static List<ItemDetailEntity> parseItemList(String response){
		try {
			return new Gson().fromJson(new JSONObject(response).getJSONArray("info").toString(), new TypeToken<List<ItemDetailEntity>>(){}.getType());
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static List<ItemDetailEntity> parseMainCategoryDetail(String response){
		try {
			return new Gson().fromJson(new JSONObject(response).getJSONArray("info").toString(), new TypeToken<List<ItemDetailEntity>>(){}.getType());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ArrayList<ItemDetailEntity>();
		}
	}
	
	public static List<ImageMap> parseMainCategoryUrl(JSONObject jsonObject){
		List<ImageMap> result = null;
		
		try{
			if(jsonObject.getString("result").equals("success")){
				result = new ArrayList<ImageMap>();
				JSONArray infoJson = jsonObject.getJSONArray("info");
				int length = infoJson.length();
				for(int i = 0; i < length; i++){
					ImageMap imageMap = new ImageMap();
					imageMap.setLinkType(ImageMap.LINK_GOOD_LIST);
					imageMap.setLink(infoJson.getJSONObject(i).getString("id"));
					imageMap.setTitle(infoJson.getJSONObject(i).getString("name"));
					
					result.add(imageMap);
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static List<BaseTabStoreEntity> parseStoreContent(String response){
		List<BaseTabStoreEntity> result = null;
		
		try{
			JSONObject jsonObject = new JSONObject(response);
			if(jsonObject.getString("result").equals("success")){
				result = new ArrayList<BaseTabStoreEntity>();
				JSONArray infoArray = jsonObject.getJSONArray("info");
				int infoLength = infoArray.length();
				for(int i = 0; i < infoLength; i++){
					String style = infoArray.getJSONObject(i).getString("style");
					String content = infoArray.getJSONObject(i).getJSONArray("content").toString();
					BaseTabStoreEntity entity = null;
					if(style.equals("COVER_FLOW")){
						entity = new ImageFlowEntity();
					}else if(style.equals("RECOMMEND")){
						entity = new ImageBrickEntity();
					}else if(style.equals("HOT")){
						entity = new ImageHotEntity();
					}
					
					ImageMap[] imageMaps = new Gson().fromJson(content, new TypeToken<ImageMap[]>(){}.getType());
					
					entity.setImageMaps(imageMaps);
					
					result.add(entity);
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	public static List<BaseTabStoreEntity> parseStoreContent(JSONObject jsonObject){
		List<BaseTabStoreEntity> result = null;
		try{
			if(jsonObject.getString("result").equals("success")){
				result = new ArrayList<BaseTabStoreEntity>();
				ImageFlowEntity imageFlow = new ImageFlowEntity();
				JSONObject infoJson = jsonObject.getJSONObject("info");
				JSONArray imageFlowsJSON = infoJson.getJSONArray("top_five");
				ImageMap[] imageMaps = new ImageMap[imageFlowsJSON.length()];
				for(int i = 0; i < imageMaps.length; i++){
					imageMaps[i] = new ImageMap();
					if(imageFlowsJSON.getJSONObject(i).has("goods_id")){
						imageMaps[i].setLinkType(ImageMap.LINK_GOOD_DETAIL);
						imageMaps[i].setLink(imageFlowsJSON.getJSONObject(i).getString("goods_id"));
					}else{
						imageMaps[i].setLinkType(ImageMap.LINK_ADS);
					}
					
					imageMaps[i].setImageLink(imageFlowsJSON.getJSONObject(i).getString("goods_img"));
					imageMaps[i].setTitle(imageFlowsJSON.getJSONObject(i).getString("goods_name"));
				}
				
				imageFlow.setImageUrls(imageMaps);
				result.add(imageFlow);
				
				JSONArray recommendJson = infoJson.getJSONArray("recommend");
				for(int j = 0; j < recommendJson.length(); j++){
					ImageBrickEntity imageBrick = new ImageBrickEntity();
					JSONObject keyJson = recommendJson.getJSONObject(j);
					
					imageBrick.setPrimaryImageMap(new ImageMap().setImageLink(keyJson.getString("img")).
							                                     setLinkType(ImageMap.LINK_URL_GOOD_LIST).
							                                     setLink(keyJson.getString("url")));
					
					ImageMap[] imageMap = new ImageMap[5];
					for(int k = 0; k < 5; k++){
						imageMap[k] = new ImageMap();
						if(keyJson.has(k + "")){
					        imageMap[k].setImageLink(keyJson.getJSONObject(k + "").getString("goods_img"));
					        imageMap[k].setLink(keyJson.getJSONObject(k + "").getString("goods_id"));
					        imageMap[k].setLinkType(ImageMap.LINK_GOOD_DETAIL);
					        imageMap[k].setTitle(keyJson.getJSONObject(k + "").getString("goods_name"));
						}
					}
					
					imageBrick.setSubImageMap(imageMap);
					result.add(imageBrick);
				}
				
				
				String[] keywords = new String[]{"hot", "best", "new"};
				for(int j = 0; j < keywords.length; j++){
					ImageBrickEntity imageBrick = new ImageBrickEntity();
					JSONObject keyJson = infoJson.getJSONObject(keywords[j]);
					
					imageBrick.setPrimaryImageMap(new ImageMap().setImageLink(keyJson.getString("img_" + keywords[j])).
							                                     setLinkType(ImageMap.LINK_GOOD_DETAIL).
							                                     setLink(keyJson.getString(keywords[j] + "_url")));
					
					ImageMap[] imageMap = new ImageMap[5];
					for(int k = 0; k < 5; k++){
						imageMap[k] = new ImageMap();
						if(keyJson.has(k + "")){
					        imageMap[k].setImageLink(keyJson.getJSONObject(k + "").getString("goods_img"));
					        imageMap[k].setLink(keyJson.getJSONObject(k + "").getString("goods_id"));
					        imageMap[k].setLinkType(ImageMap.LINK_GOOD_DETAIL);
					        imageMap[k].setTitle(keyJson.getJSONObject(k + "").getString("goods_name"));
						}
					}
					
					imageBrick.setSubImageMap(imageMap);
					result.add(imageBrick);
					
				}
				 
				
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return result;
	}
	 */
	
	public static ItemDetailEntity parseItemDetail(JSONObject jsonObject){
		ItemDetailEntity itemDetailEntity = null;
		try{
			if(jsonObject.getString("result").equals("success")){
				
				Gson gson = new Gson();
				
				String info = jsonObject.getJSONObject("info").toString();
				
				itemDetailEntity = gson.fromJson(info, ItemDetailEntity.class);
				
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return itemDetailEntity;
	}
}
