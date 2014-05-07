package com.pz.xingfutao.ui.sub;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.adapter.ItemDetailAdapter;
import com.pz.xingfutao.api.ContentApi;
import com.pz.xingfutao.entities.CommentEntity;
import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;
import com.pz.xingfutao.utils.PLog;

public class ItemDetailFragment extends BaseBackButtonFragment {
	
	private ItemDetailEntity itemDetailEntity;
	private List<CommentEntity> commentLists;
	private ItemDetailAdapter adapter;
	
	private ListView list;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		getTitleView().setText(getString(R.string.title_good_detail));
		
		setContentView(R.layout.fragment_item_detail);
		
		itemDetailEntity = new ItemDetailEntity();
		commentLists = new ArrayList<CommentEntity>();
		adapter = new ItemDetailAdapter(getActivity(), itemDetailEntity, commentLists);
		
		list = (ListView) content.findViewById(R.id.list);
		list.setAdapter(adapter);
		
		if(getArguments().containsKey("good_id")){
			PLog.d("goodid",  getArguments().getString("good_id"));
			NetworkHandler.getInstance(getActivity()).jsonRequest(Method.POST, ContentApi.getGoodDetailUrl(getArguments().getString("good_id")), new Listener<JSONObject>(){

				@Override
				public void onResponse(JSONObject jsonObject) {
					PLog.d("json_detail", jsonObject.toString());
					
					adapter.setItemDetail(ContentApi.parseItemDetail(jsonObject));
					adapter.notifyDataSetChanged();
				}
				
			}, this);
		}
	}

	@Override
	protected boolean isContentEmpty(){
		return list == null || list.getAdapter() == null || list.getAdapter().getCount() == 0;
	}
}
