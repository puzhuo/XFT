package com.pz.xingfutao.ui.sub;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.adapter.PostListAdapter;
import com.pz.xingfutao.api.ForumApi;
import com.pz.xingfutao.entities.PostDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.base.RefreshableListViewFragment;
import com.pz.xingfutao.utils.PLog;

public class PostListFragment extends RefreshableListViewFragment{
	
	private List<PostDetailEntity> datas;
	private PostListAdapter adapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setMode(MODE_TITLE | MODE_LEFT_BUTTON);
		getLeftButton().setImageResource(R.drawable.selector_title_button_back);
		
		datas = new ArrayList<PostDetailEntity>();
		adapter = new PostListAdapter(getActivity(), datas);
		list.setAdapter(adapter);
		list.setDivider(getResources().getDrawable(android.R.drawable.divider_horizontal_textfield));
		list.setBackgroundColor(0xFFFFFFFF);
		list.setDividerHeight(1);
		list.setSelector(getResources().getDrawable(R.drawable.selector_global_adapterview));
		
		list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View target, int position, long id){
				PostDetailFragment fragment = new PostDetailFragment();
				Bundle bundle = new Bundle();
				bundle.putString("post_id", datas.get(position).getPostId());
				fragment.setArguments(bundle);
				
				startFragmentWithBackEnabled(fragment, getString(R.string.title_post_detail));
			}
		});
		
		if(getArguments() != null){
			if(getArguments().containsKey("category_id")){
				NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, ForumApi.getForumCategoryListUrl(getArguments().getString("category_id"), "0"), new Listener<String>(){
					@Override
					public void onResponse(String response){
						if(!response.equals("\"\"")){
							datas.clear();
							datas.addAll(ForumApi.parseCateogryList(response));
							PLog.d("datas", datas.toString());
							adapter.notifyDataSetChanged();
						}
					}
				}, this);
			}
		}
	}

	@Override
	protected void onRefresh() {
		
	}

	
	@Override
	protected void onClick(int id){
		switch(id){
		case MODE_LEFT_BUTTON:
			dismiss();
			break;
		}
	}
}
