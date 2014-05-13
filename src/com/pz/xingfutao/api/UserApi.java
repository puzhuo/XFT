package com.pz.xingfutao.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pz.xingfutao.R;
import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.utils.PLog;
import com.pz.xingfutao.widget.XFToast;

public class UserApi extends BaseApi{

	
	private static final String retrieveFavUrl = baseUrl + "wap_fav_api.php?act=my_fav_list";
	private static final String addFavUrl = baseUrl + "wap_fav_api.php?act=add_my_fav";
	private static final String delFavUrl = baseUrl + "wap_fav_api.php?act=del_my_fav";
	private static final String isGoodFavUrl = baseUrl + "wap_fav_api.php?act=my_fav_goods";
	private static final String myOrderUrl = baseUrl + "";
	private static final String retrieveCartUrl = baseUrl + "wap_cart_api.php?act=show_goods";
	private static final String addCartUrl = baseUrl + "wap_cart_api.php?act=add_to_cart";
	private static final String updateCartUrl = baseUrl + "wap_cart_api.php?act=update_goods";
	
	private static final String settleUrl = baseUrl + "wap_cart_api.php?act=buyok";
	
	public static String getRetrieveFavUrl(int userId){
		return retrieveFavUrl + "&user_id=" + userId;
	}
	
	public static String getAddFavUrl(int userId, String goodId){
		return addFavUrl + "&user_id=" + userId + "&goods_id=" + goodId;
	}
	
	public static String getDelFavUrl(int userId, String goodId){
		return delFavUrl + "&user_id=" + userId + "&goods_id=" + goodId;
	}
	
	public static String getIsGoodFavUrl(int userId, String goodId){
		return isGoodFavUrl + "&user_id=" + userId + "&goods_id=" + goodId;
	}
	
	public static String getRetrieveCartUrl(int userId){
		return retrieveCartUrl + "&user_id=" + userId;
	}
	
	public static String getAddCartUrl(String goodId, int userId){
		return addCartUrl + "&goods_id=" + goodId + "&user_id=" + userId;
	}
	
	public static String getUpdateCartUrl(){
		return updateCartUrl;
	}
	
	public static Map<String, String> getUpdateCartParams(List<ItemDetailEntity> goods){
		Map<String, String> result = null;
		if(goods != null && goods.size() > 0){
			result = new HashMap<String, String>();
			String param = "[";
			
			for(int i = 0; i < goods.size(); i++){
				String item = "{\"id\":\"" + goods.get(i).getId() + "\",\"count\":" + goods.get(i).getPurchaseCount() + "}";
				
				item = i == goods.size() - 1 ? item : item + ",";
				
				param += item;
			}
			
			param += "]";
			
			PLog.d("param", param);
			
			result.put("str", param);
		}
		
		return result;
	}
	
	public static String getSettleUrl(int userId){
		return settleUrl + "&user_id=" + userId;
	}
	
	public static Map<String, String> getSettleUrlParams(String address, String phoneNumber, String consignee, String message, List<ItemDetailEntity> goods){
		Map<String, String> result = null;
		
		if(address != null && phoneNumber != null && consignee != null && goods != null && goods.size() > 0){
			result = new HashMap<String, String>();
			String param = "{\"consignee\":\"" + consignee + "\",\"mobile\":\"" + phoneNumber + "\",\"address\":\"" + address + "\",\"postscript\":\"" + (message == null ? "" : message) + "\",\"goods\":[";
			
			float totalPrice = 0;
			for(int i = 0; i < goods.size(); i++){
				
				float price = goods.get(i).getShopPrice() * goods.get(i).getPurchaseCount();
				
				String item = "{\"goods_id\":\"" + goods.get(i).getId() + "\",\"count\":" + goods.get(i).getPurchaseCount() + "}";
				
				totalPrice += price;
				
				item = i == goods.size() - 1 ? item : item + ",";
				
				param += item;
			}
			
			
			param += "],\"total_price\":" + totalPrice + "}";
			
			PLog.d("params", param);
			
			result.put("settle", param);
		}
		
		return result;
	}
	
	public static boolean checkSettle(String response){
		try{
			JSONObject jsonObject = new JSONObject(response);
			if(jsonObject.has("result") && jsonObject.getString("result").equals("success")){
				return true;
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean checkAddFav(String response){
		try{
			JSONObject jsonObject = new JSONObject(response);
			if(jsonObject.has("result") && jsonObject.getString("result").equals("success")){
				if(jsonObject.getJSONObject("info").getString("msg").equals("添加成功")){
					XFToast.showTextShort(R.string.fragment_item_detail_add_fav_success);
					return true;
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean checkDelFav(String response){
		try{
			JSONObject jsonObject = new JSONObject(response);
			if(jsonObject.has("result") && jsonObject.getString("result").equals("success")){
				if(jsonObject.getJSONObject("info").getString("msg").equals("删除成功")){
					XFToast.showTextShort(R.string.fragment_item_detail_del_fav_success);
					return true;
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean checkAddToCart(String response){
		try{
			JSONObject jsonObject = new JSONObject(response);
			if(jsonObject.has("result") && jsonObject.getString("result").equals("success")){
				if(jsonObject.getString("info").equals("添加成功")) return true;
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean isGoodFav(String response){
		
		return commonResultCheck(response);
	}
	
	public static List<ItemDetailEntity> parseRetrieveFav(String response){
		List<ItemDetailEntity> result = new ArrayList<ItemDetailEntity>();
		try{
			JSONObject jsonObject = new JSONObject(response);
			if(jsonObject.has("result") && jsonObject.getString("result").equals("success")){
				result = new Gson().fromJson(jsonObject.getJSONArray("info").toString(), new TypeToken<List<ItemDetailEntity>>(){}.getType());
			}
		}catch(JSONException e){
			e.printStackTrace();
		}

		return result;
	}
	
	private static boolean commonResultCheck(String response){
		try{
			JSONObject jsonObject = new JSONObject(response);
			if(jsonObject.has("result") && jsonObject.getString("result").equals("success")){
				if(jsonObject.getInt("info") == 1) return true;
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return false;
	}
}
