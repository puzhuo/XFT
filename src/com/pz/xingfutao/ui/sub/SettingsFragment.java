package com.pz.xingfutao.ui.sub;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.adapter.SettingsAdapter;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.entities.SettingsMap;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;
import com.pz.xingfutao.utils.LoginUtil;
import com.pz.xingfutao.view.AlertListener;
import com.pz.xingfutao.view.PopupWindowDispatcher;

public class SettingsFragment extends BaseBackButtonFragment{

	private ListView list;
	private List<SettingsMap> datas;
	private SettingsAdapter adapter;
	
	private SettingsMap div;
	private SettingsMap exitMap;
	
	private String[] settingsTitle;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentView(R.layout.fragment_settings);
		getTitleView().setText(getString(R.string.title_settings));
		
		list = (ListView) content.findViewById(R.id.list);
		
		settingsTitle = getActivity().getResources().getStringArray(R.array.settings_titles);
		
		div = new SettingsMap().setType(SettingsMap.MODE_DIV);
		exitMap = new SettingsMap().setTitle("退出当前账号").setType(SettingsMap.MODE_EXIT);
		
		datas = new ArrayList<SettingsMap>();
		
		addDatas();
		
		adapter = new SettingsAdapter(getActivity(), datas);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View target, int position, long id){
				switch(position){
				case 0:
					break;
				case 2:
					PopupWindowDispatcher.getInstance(getActivity()).popupAlert(getView(), new AlertListener(getString(R.string.fragment_settings_confirm_wipe_image)){
						@Override
						public void onClick(View v){
							NetworkHandler.getInstance(getActivity()).wipeImageCache();
							PopupWindowDispatcher.getInstance(getActivity()).dismiss();
						}
					}, new AlertListener(getString(R.string.fragment_settings_cancel)){
						@Override
						public void onClick(View v){
							PopupWindowDispatcher.getInstance(getActivity()).dismiss();
						}
					});
					
					break;
				case 3:
					PopupWindowDispatcher.getInstance(getActivity()).popupAlert(getView(), new AlertListener(getString(R.string.fragment_settings_confirm_wipe_history)){
						@Override
						public void onClick(View v){
							NetworkHandler.getInstance(getActivity()).wipeJsonCache();
							PopupWindowDispatcher.getInstance(getActivity()).dismiss();
						}
					}, new AlertListener(getString(R.string.fragment_settings_cancel)){
						@Override
						public void onClick(View v){
							PopupWindowDispatcher.getInstance(getActivity()).dismiss();
						}
					});
					break;
				case 5:
					break;
				case 6:
					
					startFragmentWithBackEnabled(new AboutFragment(), "");
					
					break;
				case 8:
					break;
				case 10:
					PopupWindowDispatcher.getInstance(getActivity()).popupAlert(getView(), new AlertListener(getString(R.string.fragment_settings_confirm_exit)){
						@Override
						public void onClick(View v){
							XFSharedPreference.getInstance(getActivity()).putSession("");
							XFSharedPreference.getInstance(getActivity()).putUserId(0);
							addDatas();
							PopupWindowDispatcher.getInstance(getActivity()).dismiss();
						}
					}, new AlertListener(getString(R.string.fragment_settings_cancel)){
						@Override
						public void onClick(View v){
							PopupWindowDispatcher.getInstance(getActivity()).dismiss();
						}
					}, getString(R.string.fragment_settings_confirm_exit_desc));
					break;
				}
			}
		});
		
		
	}
	
	private void addDatas(){
		datas.clear();
		
		for(String title : settingsTitle){
			SettingsMap settingsMap = new SettingsMap();
			if(title.contains("->")){
				settingsMap.setType(SettingsMap.MODE_ARROW_WITH_EXTRA);
				settingsMap.setTitle(title.substring(0, title.indexOf("->")));
				settingsMap.setExtra(title.substring(title.indexOf("->") + 2));
			}else if(title.contains("center:")){
				settingsMap.setType(SettingsMap.MODE_EXIT);
				settingsMap.setTitle(title.substring(title.indexOf("center:") + 7));
			}else if(title.contains("divider")){
				settingsMap.setType(SettingsMap.MODE_DIV);
			}else{
				settingsMap.setType(SettingsMap.MODE_ARROW_WITH_EXTRA);
				settingsMap.setTitle(title);
			}
			
			if(title.endsWith("_")){
				if(settingsMap.getTitle() != null) settingsMap.setTitle(settingsMap.getTitle().substring(0, settingsMap.getTitle().lastIndexOf("_")));
				settingsMap.setExtra("exit_item");
				if(LoginUtil.checkLogin()) datas.add(settingsMap);
			}else{
				datas.add(settingsMap);
			}
		}
		
		if(adapter != null) adapter.notifyDataSetChanged();
	}
}
