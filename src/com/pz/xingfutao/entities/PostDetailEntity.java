package com.pz.xingfutao.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pz.xingfutao.entities.base.BaseEntity;

public class PostDetailEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7994391213152876698L;
	
	@Expose
	@SerializedName("title")
	private String title;
	@Expose
	@SerializedName("time")
	private long timestamp;
	@Expose
	@SerializedName("user_name")
	private String userName;
	@Expose
	@SerializedName("user_id")
	private String userId;
	@Expose
	@SerializedName("user_avatar")
	private String userAvatar;
	@Expose
	@SerializedName("gender")
	private String gender;
	@Expose
	@SerializedName("content")
	private String content;
	@Expose
	@SerializedName("comment_count")
	private int commentCount;
	
	@Expose
	@SerializedName("img_thumb")
	private String imageThumb;
	@Expose
	@SerializedName("post_id")
	private String postId;
	
	@Expose
	@SerializedName("content_brief")
	private String contentBrief;
	
	public String getContentBrief(){
		return contentBrief;
	}
	
	public void setContentBrief(String contentBrief){
		this.contentBrief = contentBrief;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserAvatar() {
		return userAvatar;
	}
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getImageThumb() {
		return imageThumb;
	}
	public void setImageThumb(String imageThumb) {
		this.imageThumb = imageThumb;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	
	@Override
	public String toString() {
		return "PostDetailEntity [title=" + title + ", timestamp=" + timestamp
				+ ", userName=" + userName + ", userId=" + userId
				+ ", userAvatar=" + userAvatar + ", gender=" + gender
				+ ", content=" + content + ", commentCount=" + commentCount
				+ ", imageThumb=" + imageThumb + ", postId=" + postId
				+ ", contentBrief=" + contentBrief + "]";
	}
	
}
