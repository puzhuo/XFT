package com.pz.xingfutao.ui.sub;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.adapter.ItemListAdapter;
import com.pz.xingfutao.api.ContentApi;
import com.pz.xingfutao.api.SearchApi;
import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;
import com.pz.xingfutao.utils.FragmentUtil;
import com.pz.xingfutao.utils.PLog;
import com.pz.xingfutao.utils.Renderer;

public class ItemListFragment extends BaseBackButtonFragment{
	
	private GridView list;
	private List<ItemDetailEntity> datas;
	private ItemListAdapter adapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentView(R.layout.fragment_search_result);
		
		list = (GridView) content.findViewById(R.id.list);
		
		datas = new ArrayList<ItemDetailEntity>();
		
		adapter = new ItemListAdapter(getActivity(), datas);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View target, int position, long id){
				FragmentUtil.startImageMappingFragment(getActivity(), datas.get(position).getItemMap());
			}
		});
		
		new ArgExec(this, "search_key", "item_list_api", "url_list"){

			@Override
			protected void set(int i, Object value) {
				switch(i){
				case 0:
					NetworkHandler.getInstance(getActivity()).jsonRequest(Method.POST, SearchApi.getSearchUrl((String) value), new Listener<JSONObject>(){
						@Override
						public void onResponse(JSONObject jsonObject){
							PLog.d("json", jsonObject.toString());
							datas.clear();
							datas.addAll(SearchApi.parseSearchResult(jsonObject));
							
							if(datas.size() % 2 != 0) datas.add(new ItemDetailEntity());
							
							adapter.notifyDataSetChanged();
						}
					}, ItemListFragment.this);
					break;
				case 1:
					NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, ContentApi.getMainCategoryDetailListUrl((String) value), new Listener<String>(){
						@Override
						public void onResponse(String response){
							datas.clear();
							datas.addAll(ContentApi.parseMainCategoryDetail(response));
							adapter.notifyDataSetChanged();
						}
					}, ItemListFragment.this);
					break;
				case 2:
					NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, ContentApi.getRecommendGoodListUrl((String) value), new Listener<String>(){
						@Override
						public void onResponse(String response){
							datas.clear();
							datas.addAll(ContentApi.parseMainCategoryDetail(response));
							adapter.notifyDataSetChanged();
						}
					}, ItemListFragment.this);
					 
					break;
				}
			}
			
		};
		
	}

	@Override
	public boolean isContentEmpty(){
		return list == null || list.getAdapter() == null || list.getAdapter().getCount() == 0;
	}
	
}
