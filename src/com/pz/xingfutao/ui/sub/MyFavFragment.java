package com.pz.xingfutao.ui.sub;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.adapter.ItemListAdapter;
import com.pz.xingfutao.api.UserApi;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.api.RequireLogin;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;
import com.pz.xingfutao.ui.tab.TabActivity;
import com.pz.xingfutao.utils.FragmentUtil;
import com.pz.xingfutao.utils.PLog;

public class MyFavFragment extends BaseBackButtonFragment implements RequireLogin{

	private GridView list;
	private List<ItemDetailEntity> datas;
	private ItemListAdapter adapter;
	
	@Override
	public void onResume(){
		super.onResume();
		
		
		setContentView(R.layout.fragment_my_fav);
		getTitleView().setText(getString(R.string.title_my_fav));
		
		list = (GridView) content.findViewById(R.id.list);
		
		datas = new ArrayList<ItemDetailEntity>();
		adapter = new ItemListAdapter(getActivity(), datas);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View target, int position, long id){
				FragmentUtil.startImageMappingFragment(getActivity(), adapter.getItem(position).getItemMap());
			}
		});
		
		NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, UserApi.getRetrieveFavUrl(XFSharedPreference.getInstance(getActivity()).getUserId()), new Listener<String>(){
			@Override
			public void onResponse(String response){
				PLog.d("response", response);
				datas.clear();
				datas.addAll(UserApi.parseRetrieveFav(response));
				
				adapter.notifyDataSetChanged();
			}
		}, this);
	}
	
	@Override
	protected boolean isContentEmpty(){
		return list == null || list.getAdapter() == null || list.getAdapter().getCount() == 0;
	}
	
	@Override
	protected View getEmptyStateView(){
		View emptyView = inflateResource(R.layout.empty_layout_my_fav);
		emptyView.findViewById(R.id.go_shopping).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				((TabActivity) getActivity()).removeFragmentsAndSwitchTo(MyFavFragment.this, 0);
			}
		});
		
		return emptyView;
	}
}
