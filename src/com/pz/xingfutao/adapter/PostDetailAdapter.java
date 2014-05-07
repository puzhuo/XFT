package com.pz.xingfutao.adapter;

import java.util.List;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.entities.CommentEntity;
import com.pz.xingfutao.entities.PostDetailEntity;
import com.pz.xingfutao.entities.base.BaseEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.utils.TextViewUtil;
import com.pz.xingfutao.utils.TimeUtil;

public class PostDetailAdapter extends BaseAdapter {
	
	private Context context;
	private PostDetailEntity postDetailEntity;
	private List<CommentEntity> commentList;
	
	private String[] floorNames;
	private String[] floorBackgrounds;
	
	public PostDetailAdapter(Context context, PostDetailEntity postDetailEntity, List<CommentEntity> commentList){
		this.context = context;
		this.postDetailEntity = postDetailEntity;
		this.commentList = commentList;
		
		floorNames = context.getResources().getStringArray(R.array.floor_name);
		floorBackgrounds = context.getResources().getStringArray(R.array.floor_background);
	}
	
	public void setPostDetailEntity(PostDetailEntity postDetailEntity){
		this.postDetailEntity = postDetailEntity;
	}

	@Override
	public int getCount() {
		int count = 0;
		if(postDetailEntity != null) count = 1;
		return count + commentList.size();
	}

	@Override
	public BaseEntity getItem(int position) {
		if(postDetailEntity == null) return commentList.get(position);
		return position == 0 ? postDetailEntity : commentList.get(position - 1);
	}
	
	@Override
	public int getViewTypeCount(){
		return 2;
	}
	
	@Override
	public int getItemViewType(int position){
		return position == 0 ? 0 : 1;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		
		switch(getItemViewType(position)){
		case 0:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_post_detail_content, null, false);
			}
			
			if(postDetailEntity.getUserAvatar() != null){
				((TextView) convertView.findViewById(R.id.title)).setText(postDetailEntity.getTitle());
				NetworkHandler.getInstance(context).imageRequest(postDetailEntity.getUserAvatar(), (ImageView) convertView.findViewById(R.id.avatar));
				((TextView) convertView.findViewById(R.id.user_name)).setText(postDetailEntity.getUserName());
				
				((TextView) convertView.findViewById(R.id.content)).setMovementMethod(LinkMovementMethod.getInstance());
				TextViewUtil.formatContent(context, postDetailEntity.getContent(), ((TextView) convertView.findViewById(R.id.content)));
				
				String gender = postDetailEntity.getGender();
				if(gender.equals("1")){
					((ImageView) convertView.findViewById(R.id.gender)).setImageResource(R.drawable.gender_male);
				}
				if(gender.equals("2")){
					((ImageView) convertView.findViewById(R.id.gender)).setImageResource(R.drawable.gender_female);
				}
			}
			
			
			break;
		case 1:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_post_detail_comment, null, false);
			}
			
			NetworkHandler.getInstance(context).imageRequest(commentList.get(position - 1).getAvatar(), (ImageView) convertView.findViewById(R.id.avatar));
			
			TextView floorText = (TextView) convertView.findViewById(R.id.floor);
			
			if(position - 1 < floorNames.length - 1){
				floorText.setText(floorNames[position - 1]);
			}else{
				floorText.setText(position + floorNames[floorNames.length - 1]);
			}
			
			int id;
			if(position - 1< floorBackgrounds.length - 1){
				id = context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + floorBackgrounds[position - 1], null, null);
			}else{
				id = context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + floorBackgrounds[floorBackgrounds.length - 1], null, null);
			}
			
			floorText.setBackgroundDrawable(context.getResources().getDrawable(id));
			
			((TextView) convertView.findViewById(R.id.user_name)).setText(commentList.get(position - 1).getName());
			((TextView) convertView.findViewById(R.id.timestamp)).setText(TimeUtil.getFormattedTime("M-dd HH:mm", commentList.get(position - 1).getTimestamp()));
			
			TextViewUtil.formatContent(context, commentList.get(position - 1).getComment(), ((TextView) convertView.findViewById(R.id.content)));
			//((TextView) convertView.findViewById(R.id.content)).setText(commentList.get(position - 1).getComment());
			
			break;
		}
		
		return convertView;
	}

}
