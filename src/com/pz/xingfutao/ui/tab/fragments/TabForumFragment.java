package com.pz.xingfutao.ui.tab.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.adapter.TabForumAdapter;
import com.pz.xingfutao.api.ForumApi;
import com.pz.xingfutao.entities.ImageMap;
import com.pz.xingfutao.entities.PostDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.base.RefreshableListViewFragment;
import com.pz.xingfutao.ui.sub.MyPostFragment;
import com.pz.xingfutao.ui.sub.MyPrivateListFragment;
import com.pz.xingfutao.ui.sub.PostDetailFragment;
import com.pz.xingfutao.ui.sub.PostFragment;
import com.pz.xingfutao.view.PopupWindowDispatcher;
import com.pz.xingfutao.view.TitleClickListener;


public class TabForumFragment extends RefreshableListViewFragment {
	
	private TabForumAdapter adapter;
	private List<ImageMap> categoryList;
	private List<PostDetailEntity> hotPosts;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setMode(MODE_TITLE | MODE_LEFT_BUTTON | MODE_RIGHT_BUTTON);
		getLeftButton().setImageResource(R.drawable.selector_title_button_menu);
		getRightButton().setImageResource(R.drawable.selector_title_button_edit);
		
		categoryList = new ArrayList<ImageMap>();
		hotPosts = new ArrayList<PostDetailEntity>();
		
		adapter = new TabForumAdapter(getActivity(), categoryList, hotPosts);
		list.setAdapter(adapter);
		list.setDivider(getActivity().getResources().getDrawable(android.R.drawable.divider_horizontal_textfield));
		list.setDividerHeight(1);
		list.setSelector(getActivity().getResources().getDrawable(R.drawable.selector_global_adapterview));
		list.setBackgroundColor(0xFFFFFFFF);
		list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				if(position > 0 && categoryList.size() > 0){
					PostDetailFragment fragment = new PostDetailFragment();
					Bundle bundle = new Bundle();
					bundle.putString("post_id", hotPosts.get(position - 1).getPostId());
					fragment.setArguments(bundle);
					
					startFragmentWithBackEnabled(fragment, getString(R.string.title_post_detail));
				}
			}
		});
		
		refreshContent();
	}
	
	@Override
	protected boolean isContentEmpty(){
		return categoryList == null || categoryList.size() == 0 || hotPosts == null || hotPosts.size() == 0;
	}

	@Override
	protected void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	
	private void refreshContent(){
		NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, ForumApi.getForumCategoryUrl(), new Listener<String>(){
			@Override
			public void onResponse(String string){
				
				categoryList.clear();
				categoryList.addAll(ForumApi.parseForumCategory(string));
				adapter.notifyDataSetChanged();
			}
		}, this);
		
		NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, ForumApi.getForumHotUrl(), new Listener<String>(){
			@Override
			public void onResponse(String string){
				hotPosts.clear();
				hotPosts.addAll(ForumApi.parseForumHot(string));
				adapter.notifyDataSetChanged();
			}
		}, this);
	}
	
	@Override
	protected void onClick(int id){
		switch(id){
		case MODE_LEFT_BUTTON:
			PopupWindowDispatcher.getInstance(getActivity()).popupTitle(getLeftButton(), new TitleClickListener(new int[]{R.drawable.ic_launcher, R.drawable.ic_launcher},
					                                                                                            new String[]{"我的帖子", "我的私信"}){
				@Override
				public void onClick(int position){
					switch(position){
					case 0:
						startFragmentWithBackEnabled(new MyPostFragment(), "");
						break;
					case 1:
						startFragmentWithBackEnabled(new MyPrivateListFragment(), "");
						break;
					}
				}
			}, Gravity.TOP | Gravity.LEFT);
			break;
		case MODE_RIGHT_BUTTON:
			startFragmentWithBackEnabled(new PostFragment(), "");
		}
	}
}
