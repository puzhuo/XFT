package com.pz.xingfutao.ui.sub;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.pz.xingfutao.R;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.ui.api.KeyboardInvoke;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;
import com.pz.xingfutao.widget.XFToast;

public class AddressEditFragment extends BaseBackButtonFragment implements KeyboardInvoke{

	private EditText consignee;
	private EditText number;
	private EditText address;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentViewWithMode(R.layout.fragment_address_edit, getMode() | MODE_RIGHT_BUTTON);
		getTitleView().setText(getString(R.string.title_address_edit));
		getRightButton().setImageResource(R.drawable.selector_title_button_confirm);
		
		consignee = (EditText) content.findViewById(R.id.consignee);
		number = (EditText) content.findViewById(R.id.number);
		address = (EditText) content.findViewById(R.id.address);
		
		String sharedConsignee = XFSharedPreference.getInstance(getActivity()).getConsignee();
		String sharedNumber = XFSharedPreference.getInstance(getActivity()).getPhoneNumber();
		String sharedAddress = XFSharedPreference.getInstance(getActivity()).getAddress();
		
		if(sharedConsignee != null) consignee.setText(sharedConsignee);
		if(sharedNumber != null) number.setText(sharedNumber);
		if(sharedAddress != null) address.setText(sharedAddress);
		
		address.setOnEditorActionListener(new OnEditorActionListener(){

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				switch(actionId){
				case EditorInfo.IME_ACTION_DONE:
					if(check()){
						dismiss();
					}
					return true;
				}
				return false;
			}
			
		});
	}
	
	@Override
	protected void onClick(int id){
		switch(id){
		case MODE_RIGHT_BUTTON:
			if(check()){
				dismiss();
			}
			break;
		}
	}
	
	private boolean check(){
		if(consignee.getText().length() == 0){
			XFToast.showTextShort("要填收货人的，亲");
			return false;
		}
		
		if(number.getText().length() == 0){
			XFToast.showTextShort("号码呢?");
			return false;
		}
		if(address.getText().length() == 0){
			XFToast.showTextShort("没有地址送不了货哦");
			return false;
		}
		
		XFSharedPreference.getInstance(getActivity()).setAddress(address.getText().toString());
		XFSharedPreference.getInstance(getActivity()).putConsignee(consignee.getText().toString());
		XFSharedPreference.getInstance(getActivity()).putPhoneNumber(number.getText().toString());
		
		return true;
	}
}
