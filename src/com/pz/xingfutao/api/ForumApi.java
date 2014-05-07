package com.pz.xingfutao.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pz.xingfutao.entities.CommentEntity;
import com.pz.xingfutao.entities.ImageMap;
import com.pz.xingfutao.entities.PostDetailEntity;
import com.pz.xingfutao.utils.PLog;

public class ForumApi extends BaseApi{
	private static final String forumCategoryUrl = baseForumUrl + "bbs/category_api.php";
	private static final String forumHotUrl = baseForumUrl + "bbs/forumhot_api.php";
	private static final String forumPostDetailUrl = baseForumUrl + "bbs/viewthread_api.php";
	private static final String forumCategoryListUrl = baseForumUrl + "bbs/forums_api.php";
	private static final String forumPostCommentUrl = baseForumUrl + "bbs/comment_view_api.php";
	
	public static String getForumCategoryUrl(){
		return forumCategoryUrl;
	}
	
	public static String getForumHotUrl(){
		return forumHotUrl;
	}
	
	public static String getPostDetailUrl(String postId){
		return forumPostDetailUrl + "?post_id=" + postId;
	}
	
	public static String getForumCategoryListUrl(String categoryId, String type){
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("category_id", categoryId));
		params.add(new BasicNameValuePair("type", type));
		
		return forumCategoryListUrl + "?" + URLEncodedUtils.format(params, "UTF-8");
	}
	
	public static String getPostCommentUrl(String postId, String startIndex, String endIndex){
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("post_id", postId));
		params.add(new BasicNameValuePair("start_index", startIndex));
		params.add(new BasicNameValuePair("end_index", endIndex));
		
		return forumPostCommentUrl + "?" + URLEncodedUtils.format(params, "UTF-8");
	}
	
	public static List<ImageMap> parseForumCategory(String jsonString){
		List<ImageMap> result = null;
		
		try{
			JSONArray jsonArray = new JSONArray(jsonString);
			
			int length = jsonArray.length();
			if(length > 0){
				result = new ArrayList<ImageMap>();
				
				for(int i = 0; i < length; i++){
					ImageMap imageMap = new ImageMap();
					imageMap.setLinkType(ImageMap.LINK_CATEGORY_LIST);
					imageMap.setImageLink(jsonArray.getJSONObject(i).getString("img_url"));
					imageMap.setTitle(jsonArray.getJSONObject(i).getString("title"));
					imageMap.setLink(jsonArray.getJSONObject(i).getString("category_id"));
					
					result.add(imageMap);
				}
				
				PLog.d("size", result.size() + "");
				
			}
			
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static List<PostDetailEntity> parseForumHot(String jsonString){

		return new Gson().fromJson(jsonString, new TypeToken<List<PostDetailEntity>>(){}.getType());
	}
	
	public static PostDetailEntity parsePostDetail(String response){
		PostDetailEntity result = null;
		
		try{
			JSONArray jsonArray = new JSONArray(response);
			String jsonObjectString = jsonArray.getJSONObject(0).toString();
			
			result = new Gson().fromJson(jsonObjectString, PostDetailEntity.class);
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static List<PostDetailEntity> parseCateogryList(String response){
		
		try{
			return new Gson().fromJson(response, new TypeToken<List<PostDetailEntity>>(){}.getType());
		}catch(IllegalStateException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static List<CommentEntity> parsePostComment(String response){
		return new Gson().fromJson(response, new TypeToken<List<CommentEntity>>(){}.getType());
	}
}
