package com.pz.xingfutao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.entities.ImageMap;
import com.pz.xingfutao.entities.PostDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.utils.FragmentUtil;
import com.pz.xingfutao.utils.TimeUtil;

public class TabForumAdapter extends BaseAdapter{
	
	private Context context;
	private List<ImageMap> categoryList;
	private List<PostDetailEntity> hotPost;
	
	private static final int NUM_COLUMN = 4;
	
	public TabForumAdapter(Context context, List<ImageMap> categoryList, List<PostDetailEntity> hotPost){
		this.context = context;
		this.categoryList = categoryList;
		this.hotPost = hotPost;
	}

	@Override
	public int getCount() {
		return hotPost.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getViewTypeCount(){
		return 2;
	}
	
	@Override
	public int getItemViewType(int position){
		if(position == 0){
			return 0;
		}
		
		return 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		
		switch(getItemViewType(position)){
		case 0:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_tab_forum_category, null, false);
				
				
				LinearLayout row = null;
				
				if(categoryList != null && categoryList.size() > 0 && ((ViewGroup) convertView.findViewById(R.id.category_section)).getChildCount() < categoryList.size()){
					((ViewGroup) convertView.findViewById(R.id.category_section)).removeAllViewsInLayout();
					
					for(int i = 0; i < categoryList.size(); i++){
						if(i % NUM_COLUMN == 0){
							LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
							rowParams.weight = 1F;
							
							LinearLayout tRow = new LinearLayout(context);
							tRow.setOrientation(LinearLayout.HORIZONTAL);
							
							View h = new View(context);
							h.setBackgroundDrawable(context.getResources().getDrawable(android.R.drawable.divider_horizontal_textfield));
							
							LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
							
							((ViewGroup) convertView.findViewById(R.id.category_section)).addView(h, dividerParams);
							((ViewGroup) convertView.findViewById(R.id.category_section)).addView(tRow, rowParams);
							
							row = tRow;
						}
						
						if(row != null){
							LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
							lineParams.weight = 1F;
							
							View child = inflater.inflate(R.layout.item_listview_tab_forum_category_single, null, false);
							final int ii = i;
							child.setOnClickListener(new OnClickListener(){
								@Override
								public void onClick(View v){
									FragmentUtil.startImageMappingFragment(context, categoryList.get(ii));
								}
							});
							
							NetworkHandler.getInstance(context).imageRequest(categoryList.get(i).getImageLink(), (ImageView) child.findViewById(R.id.cat_thumb));
							((TextView) child.findViewById(R.id.cat_title)).setText(categoryList.get(i).getTitle());
							
							row.addView(child, lineParams);
							
							if(i + 1 % NUM_COLUMN != 0){
								View v = new View(context);
								v.setBackgroundDrawable(context.getResources().getDrawable(android.R.drawable.divider_horizontal_textfield));
								
								LinearLayout.LayoutParams vDividerParams = new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);
								row.addView(v, vDividerParams);
							}
						}
					}
					
					View h = new View(context);
					h.setBackgroundDrawable(context.getResources().getDrawable(android.R.drawable.divider_horizontal_textfield));
					
					LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
					
					((ViewGroup) convertView.findViewById(R.id.category_section)).addView(h, dividerParams);
				}
			}
			
			
			break;
		case 1:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_tab_forum_list, null, false);
			}
			
			int location = position - 1;
			
			if(hotPost.get(location).getImageThumb() != null && hotPost.get(location).getImageThumb() != ""){
				((ImageView) convertView.findViewById(R.id.thumb)).setVisibility(View.VISIBLE);
				NetworkHandler.getInstance(context).imageRequest(hotPost.get(location).getImageThumb(), (ImageView) convertView.findViewById(R.id.thumb));
			}else{
				((ImageView) convertView.findViewById(R.id.thumb)).setVisibility(View.GONE);
			}
			
			((TextView) convertView.findViewById(R.id.title)).setText(hotPost.get(location).getTitle());
			((TextView) convertView.findViewById(R.id.user_name)).setText(hotPost.get(location).getUserName());
			((TextView) convertView.findViewById(R.id.time_stamp)).setText(TimeUtil.getFormattedTime("M-dd HH:mm", hotPost.get(location).getTimestamp()));
			((TextView) convertView.findViewById(R.id.comment_count)).setText(hotPost.get(location).getCommentCount() + "");
			
			break;
		}
		return convertView;
	}

}
