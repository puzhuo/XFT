package com.pz.xingfutao.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.adapter.PopupTitleAdapter;
import com.pz.xingfutao.utils.SystemMeasurementUtil;

public class PopupWindowDispatcher {
	
	private Context context;
	private static PopupWindowDispatcher instance;
	
	private PopupWindow window;
	
	public static PopupWindowDispatcher getInstance(Context context){
		if(instance == null){
			instance = new PopupWindowDispatcher(context);
		}
		
		return instance;
	}
	
	private PopupWindowDispatcher(Context context){
		this.context = context;
	}
	
	public void popupTitle(View anchor, final TitleClickListener listener, int gravity){
		if(listener != null && anchor != null && (window == null || (window != null && !window.isShowing()))){
			View container = LayoutInflater.from(context).inflate(R.layout.popupwindow_title, null, false);
			container.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					dismiss();
				}
			});
			
			ListView list = (ListView) container.findViewById(R.id.list);
			PopupTitleAdapter adapter = new PopupTitleAdapter(context, listener.getImageRes(), listener.getTitles());
			list.setAdapter(adapter);
			list.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View target, int position, long id){
					listener.onClick(position);
					dismiss();
				}
			});
			
			window = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
			window.setBackgroundDrawable(new ColorDrawable(-00000));
			window.setAnimationStyle(R.style.PopupWindowSlideFade);
			
			window.showAtLocation(anchor, gravity, (gravity & Gravity.LEFT) != 0 ? anchor.getLeft() : anchor.getRight(), (gravity & Gravity.TOP) != 0 ? anchor.getBottom() + SystemMeasurementUtil.getStatusBarHeight(context) : anchor.getTop() + SystemMeasurementUtil.getStatusBarHeight(context));
		}
	}
	
	public void popupInterceptAlert(View anchor, AlertListener confirmListener, AlertListener cancelListener, String primaryDesc){
		popupInterceptAlert(anchor, confirmListener, cancelListener, primaryDesc, null);
	}
	
	public void popupInterceptAlert(View anchor, AlertListener confirmListener, AlertListener cancelListener, String primaryDesc, String extraDesc){
		if(confirmListener != null && cancelListener != null && (window == null || (window != null && !window.isShowing())) && primaryDesc != null){
			View container = LayoutInflater.from(context).inflate(R.layout.popupwindow_intercept_alert, null, false);
			
			TextView pDesc = (TextView) container.findViewById(R.id.desc_primary);
			pDesc.setText(primaryDesc);
			
			TextView eDesc = (TextView) container.findViewById(R.id.extra_desc);
			if(extraDesc != null){
				eDesc.setText(extraDesc);
			}else{
				eDesc.setVisibility(View.GONE);
			}
			
			Button confirm = (Button) container.findViewById(R.id.confirm);
			confirm.setText(confirmListener.getTitle());
			confirm.setOnClickListener(confirmListener);
			
			Button cancel = (Button) container.findViewById(R.id.cancel);
			cancel.setText(cancelListener.getTitle());
			cancel.setOnClickListener(cancelListener);
			
			window = new PopupWindow(container, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
			window.setBackgroundDrawable(new ColorDrawable(0x40000000));
			window.setAnimationStyle(R.style.PopupWindowSlideFade);
			
			window.showAtLocation(anchor, Gravity.TOP, 0, 0);
		}
	}
	
	public void popupAlert(View anchor, AlertListener confirmListener, AlertListener cancelListener){
		popupAlert(anchor, confirmListener, cancelListener, null);
	}
	
	public void popupAlert(View anchor, AlertListener confirmListener, AlertListener cancelListener, String extra){
		if(confirmListener != null && cancelListener != null && (window == null || (window != null && !window.isShowing()))){
			View container = LayoutInflater.from(context).inflate(R.layout.popupwindow_alert, null, false);
			container.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					dismiss();
				}
			});
			
			TextView desc = (TextView) container.findViewById(R.id.desc);
			if(extra == null){
				desc.setVisibility(View.GONE);
			}else{
				desc.setText(extra);
			}
			
			Button confirm = (Button) container.findViewById(R.id.confirm);
			confirm.setText(confirmListener.getTitle());
			confirm.setOnClickListener(confirmListener);
			
			Button cancel = (Button) container.findViewById(R.id.cancel);
			cancel.setText(cancelListener.getTitle());
			cancel.setOnClickListener(cancelListener);
			
			window = new PopupWindow(container, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
			window.setBackgroundDrawable(new ColorDrawable(0x40000000));
			window.setAnimationStyle(R.style.PopupWindowSlideFade);
			
			window.showAtLocation(anchor, Gravity.TOP, 0, 0);
		}
	}
	
	public void dismiss(){
		if(window != null){
			window.dismiss();
		}
	}
	
}
