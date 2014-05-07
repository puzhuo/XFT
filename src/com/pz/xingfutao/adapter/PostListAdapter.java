package com.pz.xingfutao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.entities.PostDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.utils.TimeUtil;

public class PostListAdapter extends BaseAdapter{
	
	private Context context;
	private List<PostDetailEntity> datas;
	
	public PostListAdapter(Context context, List<PostDetailEntity> datas){
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public PostDetailEntity getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_tab_forum_list, null, false);
		}
		
		if(getItem(position).getImageThumb() != null && !getItem(position).getImageThumb().equals("")){
			((ImageView) convertView.findViewById(R.id.thumb)).setVisibility(View.VISIBLE);
			NetworkHandler.getInstance(context).imageRequest(getItem(position).getImageThumb(), (ImageView) convertView.findViewById(R.id.thumb));
		}else{
			((ImageView) convertView.findViewById(R.id.thumb)).setVisibility(View.GONE);
		}
		((TextView) convertView.findViewById(R.id.title)).setText(getItem(position).getTitle());
		((TextView) convertView.findViewById(R.id.user_name)).setText(getItem(position).getUserName());
		((TextView) convertView.findViewById(R.id.time_stamp)).setText(TimeUtil.getFormattedTime("M-dd HH:mm", getItem(position).getTimestamp()));
		((TextView) convertView.findViewById(R.id.comment_count)).setText(getItem(position).getCommentCount() + "");
		
		return convertView;
	}

}
