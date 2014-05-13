package com.pz.xingfutao.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.ui.tab.TabActivity;
import com.pz.xingfutao.utils.PLog;
import com.pz.xingfutao.widget.GestureLock;
import com.pz.xingfutao.widget.GestureLock.OnGestureEventListener;
import com.pz.xingfutao.widget.XFToast;

public class GestureActivity extends Activity{
	
	private GestureLock gestureLock;
	private Button confirm;
	private Button cancel;
	private TextView desc;
	
	private boolean editMode;
	
	private int[] gestureList;
	
	private List<Integer> gestureEdit;
	private List<Integer> gestureEditConfirm;

	private boolean confirmMode;
	
	private Vibrator vibrator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture);
		
		vibrator = (Vibrator) GestureActivity.this.getSystemService(VIBRATOR_SERVICE);
		
		confirmMode = false;
		
		gestureLock = (GestureLock) findViewById(R.id.gesture);
		confirm = (Button) findViewById(R.id.confirm);
		cancel = (Button) findViewById(R.id.cancel);
		desc = (TextView) findViewById(R.id.desc);
		gestureLock.setOnGestureEventListener(new OnGestureEventListener(){

			@Override
			public void onBlockSelected(int position) {
				vibrator.vibrate(20L);
				if(editMode){
					if(confirmMode){
						gestureEditConfirm.add(position);
					}else{
						gestureEdit.add(position);
					}
				}
			}

			@Override
			public void onGestureEvent(boolean matched) {
				if(matched && !editMode){
					startActivity(new Intent(GestureActivity.this, TabActivity.class));
					finish();
				}
			}

			@Override
			public void onUnmatchedExceedBoundary() {
				if(!editMode){
					XFToast.showTextShort("输入太多次错误");
					finish();
				}
			}
			
		});
		
		confirm.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if(confirmMode){
					if(gestureEdit.size() == gestureEditConfirm.size()){
						boolean checked = true;
						for(int i = 0; i < gestureEdit.size(); i++){
							if(gestureEdit.get(i).compareTo(gestureEditConfirm.get(i)) != 0){
								checked = false;
							}
						}
						
						if(checked){
							XFToast.showTextShort("设置成功");
							XFSharedPreference.getInstance(GestureActivity.this).putGestureList(gestureEdit.toArray(new Integer[gestureEdit.size()]));
							XFSharedPreference.getInstance(GestureActivity.this).setIsGestureProtect(true);
							finish();
						}else{
							gestureEdit.clear();
							gestureEditConfirm.clear();
							XFToast.showTextShort("两次输入不同");
							confirmMode = false;
							confirm.setText("确认");
						}
					}else{
						gestureEdit.clear();
						gestureEditConfirm.clear();
						XFToast.showTextShort("两次输入不同");
						confirmMode = false;
						confirm.setText("确认");
					}
				}else{
					confirm.setText("再次确认");
					confirmMode = true;
				}
			}
		});
		
		editMode = false;
		if(getIntent() != null && getIntent().hasExtra("edit_mode") && getIntent().getBooleanExtra("edit_mode", false)){
			editMode = true;
		}
		
		if(editMode){
			confirm.setVisibility(View.VISIBLE);
			cancel.setVisibility(View.VISIBLE);
			gestureEdit = new ArrayList<Integer>();
			gestureEditConfirm = new ArrayList<Integer>();
		}else{
			gestureList = XFSharedPreference.getInstance(this).getGestureList();
			if(gestureList != null){
				gestureLock.setCorrectGesture(gestureList);
				PLog.d("gesure", gestureList.toString());
			}
			confirm.setVisibility(View.GONE);
			cancel.setVisibility(View.GONE);
		}
	}
}
