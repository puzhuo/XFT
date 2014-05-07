package com.pz.xingfutao.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pz.xingfutao.entities.base.BaseEntity;

public class CommentEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6942253683385296201L;
	
	@Expose
	@SerializedName("comment_id")
	private String commentId;
	@Expose
	@SerializedName("user_name")
	private String name;
	@Expose
	@SerializedName("gender")
	private String gender;
	@Expose
	@SerializedName("user_avatar")
	private String avatar;
	@Expose
	@SerializedName("content")
	private String comment;
	@Expose
	@SerializedName("comment_time")
	private long timestamp;
	
	
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
	@Override
	public String toString() {
		return "CommentEntity [commentId=" + commentId + ", name=" + name
				+ ", gender=" + gender + ", avatar=" + avatar + ", comment="
				+ comment + ", timestamp=" + timestamp + "]";
	}
	
}
