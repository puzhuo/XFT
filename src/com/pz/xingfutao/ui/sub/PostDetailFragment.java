package com.pz.xingfutao.ui.sub;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.adapter.PostDetailAdapter;
import com.pz.xingfutao.api.ForumApi;
import com.pz.xingfutao.entities.CommentEntity;
import com.pz.xingfutao.entities.PostDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;
import com.pz.xingfutao.utils.PLog;

public class PostDetailFragment extends BaseBackButtonFragment {

	private ListView list;
	private PostDetailAdapter adapter;
	private PostDetailEntity postDetailEntity;
	private List<CommentEntity> commentList;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentView(R.layout.fragment_post_detail);
		
		list = (ListView) content.findViewById(R.id.list);
		postDetailEntity = new PostDetailEntity();
		commentList = new ArrayList<CommentEntity>();
		adapter = new PostDetailAdapter(getActivity(), postDetailEntity, commentList);
		
		list.setAdapter(adapter);
		
		if(getArguments() != null && getArguments().containsKey("post_id")){
			NetworkHandler.getInstance(getActivity()).stringRequest(Method.GET, ForumApi.getPostDetailUrl(getArguments().getString("post_id")), new Listener<String>(){
				@Override
				public void onResponse(String string){
					
					postDetailEntity = ForumApi.parsePostDetail(string);
					
					adapter.setPostDetailEntity(postDetailEntity);
					adapter.notifyDataSetChanged();
					
					//TextViewUtil.formatContent(getActivity(), postDetailEntity.getContent(), tv);
				}
			}, this, true);
			
			NetworkHandler.getInstance(getActivity()).stringRequest(Method.GET, ForumApi.getPostCommentUrl(getArguments().getString("post_id"), "1", "20"), new Listener<String>(){
				@Override
				public void onResponse(String response){
					if(!response.equals("null")){
						PLog.d("comment", response);
						commentList.clear();
						commentList.addAll(ForumApi.parsePostComment(response));
						adapter.notifyDataSetChanged();
					}
				}
			}, this);
		}
	}
	
	@Override
	protected boolean isContentEmpty(){
		return postDetailEntity == null || postDetailEntity.getContent() == null;
	}
	
}
