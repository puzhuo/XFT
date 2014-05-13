package com.pz.xingfutao.ui.sub;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.adapter.SearchHotWordAdapter;
import com.pz.xingfutao.api.SearchApi;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.api.KeyboardInvoke;
import com.pz.xingfutao.ui.base.RefreshableListViewFragment;

public class SearchFragment extends RefreshableListViewFragment implements KeyboardInvoke{
	
	private List<String> hotWords;
	private SearchHotWordAdapter adapter;
	
	private InputMethodManager inputMethodManager;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setMode(MODE_SEARCH_BAR | MODE_RIGHT_BUTTON | MODE_LEFT_BUTTON);
		
		list.setDivider(getResources().getDrawable(android.R.drawable.divider_horizontal_textfield));
		list.setDividerHeight(1);
		
		getLeftButton().setImageResource(R.drawable.selector_title_button_back);
		
		getSearchBar().setHint(R.string.fragment_search_searchbar_hint);
		getSearchBar().setFocusableInTouchMode(true);
		getSearchBar().requestFocus();
		
		inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(getSearchBar(), 0);
		
		
		hotWords = new ArrayList<String>();
		adapter = new SearchHotWordAdapter(getActivity(), hotWords);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String key = (String) parent.getAdapter().getItem(position);
				ItemListFragment fragment = new ItemListFragment();
				Bundle bundle = new Bundle();
				bundle.putString("search_key", key);
				fragment.setArguments(bundle);
				
				startFragmentWithBackEnabled(fragment, key);
			}
		});
		
		NetworkHandler.getInstance(getActivity()).jsonRequest(Method.POST, SearchApi.getTopSearchUrl(), new Listener<JSONObject>(){
			@Override
			public void onResponse(JSONObject jsonObject){
				hotWords.clear();
				hotWords.addAll(SearchApi.parseTopSearch(jsonObject));
				adapter.notifyDataSetChanged();
			}
		}, this);
	}
	
	@Override
	protected void onClick(int id){
		switch(id){
		case MODE_RIGHT_BUTTON:
			String key = getSearchBar().getText().toString();
			if(key != null && key.length() > 0){
				ItemListFragment fragment = new ItemListFragment();
				Bundle bundle = new Bundle();
				bundle.putString("search_key", key);
				fragment.setArguments(bundle);
				
				startFragmentWithBackEnabled(fragment, key);
			}
			break;
		case MODE_LEFT_BUTTON:
			dismiss();
			break;
		}
	}

	@Override
	protected void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected boolean pullable(){
		return false;
	}
	
}
