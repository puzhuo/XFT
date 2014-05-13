package com.pz.xingfutao.ui.sub;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.api.ForumApi;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.api.KeyboardInvoke;
import com.pz.xingfutao.ui.api.RequireLogin;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;
import com.pz.xingfutao.utils.PLog;
import com.pz.xingfutao.widget.XFToast;

public class PostFragment extends BaseBackButtonFragment implements RequireLogin, KeyboardInvoke{

	private EditText titleEdit;
	private EditText contentEdit;
	
	private CheckBox check;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentViewWithMode(R.layout.fragment_post, getMode() | MODE_RIGHT_BUTTON);
		getTitleView().setText(getString(R.string.title_post));
		getRightButton().setImageResource(R.drawable.selector_title_button_confirm);
		
		titleEdit = (EditText) content.findViewById(R.id.post_title);
		contentEdit = (EditText) content.findViewById(R.id.edit);
		check = (CheckBox) content.findViewById(R.id.anonymous);
	}
	
	@Override
	protected void onClick(int id){
		super.onClick(id);
		switch(id){
		case MODE_RIGHT_BUTTON:
			if(titleEdit.getText().length() == 0){
				XFToast.showTextShort("标题不能为空");
				return;
			}
			
			if(contentEdit.getText().length() == 0){
				XFToast.showTextShort("内容不能为空");
				return;
			}
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("anonymous", check.isChecked() ? "1" : "0");
			params.put("content", contentEdit.getText().toString());
			params.put("title", titleEdit.getText().toString());
			params.put("user_id", XFSharedPreference.getInstance(getActivity()).getUserId() + "");
			
			NetworkHandler.getInstance(getActivity()).addToStringWithPost(ForumApi.getPostUrl(), params, this, new Listener<String>(){
				@Override
				public void onResponse(String response){
					if(ForumApi.checkPost(response)){
						XFToast.showTextShort("发表成功");
						dismiss();
					}
				}
			});
			
			break;
		}
	}
}
