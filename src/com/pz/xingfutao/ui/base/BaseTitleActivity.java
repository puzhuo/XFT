package com.pz.xingfutao.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pz.xingfutao.R;
import com.pz.xingfutao.widget.ScrollTextView;
import com.pz.xingfutao.widget.XFProgressBar;

@Deprecated
public class BaseTitleActivity extends Activity {
	
	protected static final int MODE_TITLE = 0x1;
	protected static final int MODE_LEFT_BUTTON = 0x2;
	protected static final int MODE_RIGHT_BUTTON = 0x4;
	protected static final int MODE_SEARCH_BAR = 0x8;
	
	private LinearLayout content;
	
	private XFProgressBar progressBar;
	
	private ScrollTextView title;
	private ImageView leftButton;
	private ImageView rightButton;
	private EditText searchBar;
	
	private int mode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_title_base);
		
		content = (LinearLayout) findViewById(R.id.content);
		
		progressBar = (XFProgressBar) findViewById(R.id.progress_bar);
		
		title = (ScrollTextView) findViewById(R.id.title);
		leftButton = (ImageView) findViewById(R.id.left_button);
		rightButton = (ImageView) findViewById(R.id.right_button);
		searchBar = (EditText) findViewById(R.id.search_bar);
		
		title.setClickable(true);
		leftButton.setClickable(true);
		rightButton.setClickable(true);
		searchBar.setClickable(true);
		
		OnClickListener onClickListener = new OnClickListener(){
			@Override
			public void onClick(View v){
				switch(v.getId()){
				case R.id.title:
					BaseTitleActivity.this.onClick(MODE_TITLE);
					break;
				case R.id.left_button:
					BaseTitleActivity.this.onClick(MODE_LEFT_BUTTON);
					break;
				case R.id.right_button:
					BaseTitleActivity.this.onClick(MODE_RIGHT_BUTTON);
					break;
				case R.id.search_bar:
					BaseTitleActivity.this.onClick(MODE_SEARCH_BAR);
				}
			}
		};
		
		title.setOnClickListener(onClickListener);
		leftButton.setOnClickListener(onClickListener);
		rightButton.setOnClickListener(onClickListener);
		searchBar.setOnClickListener(onClickListener);
		
		if(getIntent().hasExtra("title")){
			title.setText(getIntent().getStringExtra("title"));
		}
		
		/*
		//tabView = (TabView) findViewById(R.id.tab);
		tabView.setTab(new String[]{getString(R.string.tab_title_store),
                                    getString(R.string.tab_title_category),
                                    getString(R.string.tab_title_cart),
                                    getString(R.string.tab_title_forum),
                                    getString(R.string.tab_title_profile)},
                       new int[]{R.drawable.selector_tab_store,
                                 R.drawable.selector_tab_category,
                                 R.drawable.selector_tab_cart,
                                 R.drawable.selector_tab_forum,
                                 R.drawable.selector_tab_profile},
                       new Class<?>[]{TabStoreActivity.class,
                                      TabCategoryActivity.class,
                                      TabCartActivity.class,
                                      TabForumActivity.class,
                                      TabProfileActivity.class});
		 */
	}
	
	@Override
	final public void setContentView(int layoutRes){
		View contentView = getLayoutInflater().inflate(layoutRes, null, false);
		
		LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		
		content.addView(contentView, contentParams);
	}
	
	public void setContentViewWithMode(int layoutRes, int mode){
		setContentView(layoutRes);
		
		setMode(mode);
	}
	
	protected void setMode(int mode){
		
		this.mode = mode;
		
		title.setVisibility(View.GONE);
		searchBar.setVisibility(View.GONE);
		leftButton.setVisibility(View.GONE);
		rightButton.setVisibility(View.GONE);
		
		if((mode & MODE_TITLE) != 0) title.setVisibility(View.VISIBLE);
		if((mode & MODE_LEFT_BUTTON) != 0) leftButton.setVisibility(View.VISIBLE);
		if((mode & MODE_RIGHT_BUTTON) != 0) rightButton.setVisibility(View.VISIBLE);
		if((mode & MODE_SEARCH_BAR) != 0) searchBar.setVisibility(View.VISIBLE);
	}
	
	protected int getMode(){
		return mode;
	}
	
	protected EditText getSearchBar(){
		return searchBar;
	}
	
	protected ImageView getLeftButton(){
		return leftButton;
	}
	
	protected ImageView getRightButton(){
		return rightButton;
	}
	
	protected ScrollTextView getTitleView(){
		return title;
	}
	
	protected void progressBarToggle(boolean visible){
		if(visible){
			progressBar.setVisibility(View.VISIBLE);
		}else{
			progressBar.setVisibility(View.GONE);
		}
	}
	
	protected void onClick(int id){
		
	}
}
