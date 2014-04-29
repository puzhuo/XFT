package com.pz.xingfutao.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pz.xingfutao.entities.ImageBrickEntity;
import com.pz.xingfutao.entities.ImageFlowEntity;
import com.pz.xingfutao.entities.ImageMap;
import com.pz.xingfutao.entities.base.BaseTabStoreEntity;

public class ContentApi extends BaseApi{
	
	private static final String storeContentUrl = baseUrl + "wap_goods_api.php?act=search_all";
	private static final String goodDetailUrl = baseUrl + "wap_goods_api.php?act=search_goods_detail";
	
	
	public static String getStoreContentUrl(){
		return storeContentUrl;
	}
	
	public static String getGoodDetailUrl(String goodId){
		return goodDetailUrl + "&goods_id=" + goodId;
	}
	
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
					if(imageFlowsJSON.getJSONObject(i).has("good_id")){
						imageMaps[i].setLinkType(ImageMap.LINK_GOOD_DETAIL);
						imageMaps[i].setLink(imageFlowsJSON.getJSONObject(i).getString("goods_id"));
					}else{
						imageMaps[i].setLinkType(ImageMap.LINK_ADS);
					}
					
					imageMaps[i].setImageLink(baseUrl + imageFlowsJSON.getJSONObject(i).getString("goods_thumb"));
					imageMaps[i].setTitle(imageFlowsJSON.getJSONObject(i).getString("goods_name"));
				}
				
				imageFlow.setImageUrls(imageMaps);
				result.add(imageFlow);
				
				String[] keywords = new String[]{"hot", "best", "new"};
				for(int j = 0; j < keywords.length; j++){
					ImageBrickEntity imageBrick = new ImageBrickEntity();
					JSONObject keyJson = infoJson.getJSONObject(keywords[j]);
					
					imageBrick.setPrimaryImageMap(new ImageMap().setImageLink(baseUrl + keyJson.getString("img_" + keywords[j])).
							                                     setLinkType(ImageMap.LINK_GOOD_DETAIL).
							                                     setLink(keyJson.getString(keywords[j] + "_url")));
					
					ImageMap[] imageMap = new ImageMap[5];
					for(int k = 0; k < 5; k++){
						imageMap[k] = new ImageMap();
						if(keyJson.has(k + "")){
					        imageMap[k].setImageLink(baseUrl + keyJson.getJSONObject(k + "").getString("goods_thumb"));
					        imageMap[k].setLink(baseUrl + keyJson.getJSONObject(k + "").getString("goods_id"));
					        imageMap[k].setLinkType(ImageMap.LINK_GOOD_DETAIL);
					        imageMap[k].setTitle(baseUrl + keyJson.getJSONObject(k + "").getString("goods_name"));
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
}
