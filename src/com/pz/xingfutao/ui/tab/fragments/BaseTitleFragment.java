package com.pz.xingfutao.ui.tab.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pz.xingfutao.R;
import com.pz.xingfutao.graphics.RoundDrawable;
import com.pz.xingfutao.widget.ScrollTextView;
import com.pz.xingfutao.widget.XFProgressBar;

public class BaseTitleFragment extends Fragment {
	
	protected static final int MODE_TITLE = 0x1;
	protected static final int MODE_LEFT_BUTTON = 0x2;
	protected static final int MODE_RIGHT_BUTTON = 0x4;
	protected static final int MODE_SEARCH_BAR = 0x8;
	
	protected View content;
	private LayoutInflater inflater;
	
	private ScrollTextView title;
	private ImageView leftButton;
	private ImageView rightButton;
	private EditText searchBar;
	private ImageView searchIcon;
	
	private XFProgressBar progressBar;
	
	private LinearLayout rContent;
	private LinearLayout emptyContent;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		this.inflater = inflater;
		
		content = inflater.inflate(R.layout.activity_title_base, null, false);
		
		title = (ScrollTextView) content.findViewById(R.id.title);
		leftButton = (ImageView) content.findViewById(R.id.left_button);
		rightButton = (ImageView) content.findViewById(R.id.right_button);
		searchBar = (EditText) content.findViewById(R.id.search_bar);
		searchIcon = (ImageView) content.findViewById(R.id.search_icon);
		searchBar.setBackgroundDrawable(new RoundDrawable(0xFFFFFFFF));
		
		progressBar = (XFProgressBar) content.findViewById(R.id.progress_bar);
		
		title.setClickable(true);
		leftButton.setClickable(true);
		rightButton.setClickable(true);
		
		OnClickListener onClickListener = new OnClickListener(){
			@Override
			public void onClick(View v){
				switch(v.getId()){
				case R.id.title:
					BaseTitleFragment.this.onClick(MODE_TITLE);
					break;
				case R.id.left_button:
					BaseTitleFragment.this.onClick(MODE_LEFT_BUTTON);
					break;
				case R.id.right_button:
					BaseTitleFragment.this.onClick(MODE_RIGHT_BUTTON);
					break;
				case R.id.search_bar:
					BaseTitleFragment.this.onClick(MODE_SEARCH_BAR);
					break;
				}
			}
		};
		
		title.setOnClickListener(onClickListener);
		leftButton.setOnClickListener(onClickListener);
		rightButton.setOnClickListener(onClickListener);
		searchBar.setOnClickListener(onClickListener);
		
		if(getTag() != null) title.setText(getTag());
		
		return content;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(isContentEmpty() && emptyContent != null && rContent != null){
			if(emptyContent.getChildCount() == 0){
				View v = getEmptyStateView();
				if(v != null){
					LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
					emptyContent.addView(v, contentParams);
				}
				
			}
			
			emptyContent.setVisibility(View.VISIBLE);
			rContent.setVisibility(View.GONE);
		}
	}
	
	protected void setContentView(int layoutRes){
		
		final LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		
		rContent = (LinearLayout) content.findViewById(R.id.content);
		rContent.addView(inflater.inflate(layoutRes, null, false), contentParams);
		
		emptyContent = (LinearLayout) content.findViewById(R.id.empty_content);
		
		ViewTreeObserver observer = content.getViewTreeObserver();
		observer.addOnPreDrawListener(new OnPreDrawListener(){

			@Override
			public boolean onPreDraw() {
				if(isContentEmpty()){
					if(emptyContent.getChildCount() == 0){
						View v = getEmptyStateView();
						if(v != null){
							emptyContent.addView(v, contentParams);
						}
						
					}
					
					emptyContent.setVisibility(View.VISIBLE);
					rContent.setVisibility(View.GONE);
				}else{
					emptyContent.setVisibility(View.GONE);
					rContent.setVisibility(View.VISIBLE);
				}
				return true;
			}
			
		});
	}
	
	protected void setMode(int mode){
		
		title.setVisibility(View.GONE);
		searchBar.setVisibility(View.GONE);
		leftButton.setVisibility(View.GONE);
		rightButton.setVisibility(View.GONE);
		searchIcon.setVisibility(View.GONE);
		
		if((mode & MODE_TITLE) != 0) title.setVisibility(View.VISIBLE);
		if((mode & MODE_LEFT_BUTTON) != 0) leftButton.setVisibility(View.VISIBLE);
		if((mode & MODE_RIGHT_BUTTON) != 0) rightButton.setVisibility(View.VISIBLE);
		if((mode & MODE_SEARCH_BAR) != 0){
			searchBar.setVisibility(View.VISIBLE);
			searchIcon.setVisibility(View.VISIBLE);
		}
		
	}
	
	protected void setContentViewWithMode(int layoutRes, int mode){
		setContentView(layoutRes);
		setMode(mode);
	}
	
	protected ScrollTextView getTitleView(){
		return title;
	}
	
	protected ImageView getLeftButton(){
		return leftButton;
	}
	
	protected ImageView getRightButton(){
		return rightButton;
	}
	
	protected EditText getSearchBar(){
		return searchBar;
	}
	
	protected void onClick(int id){}

	protected boolean isContentEmpty(){
		return false;
	}
	
	protected View getEmptyStateView(){
		return null;
	}
	
	protected void progressBarToggle(boolean enabled){
		if(enabled){
			progressBar.setVisibility(View.VISIBLE);
		}else{
			progressBar.setVisibility(View.GONE);
		}
	}
}
