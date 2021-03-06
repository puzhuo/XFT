package com.pz.xingfutao.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.pz.xingfutao.R;
import com.pz.xingfutao.graphics.RoundDrawable;
import com.pz.xingfutao.ui.api.KeyboardInvoke;
import com.pz.xingfutao.ui.api.RequireLogin;
import com.pz.xingfutao.ui.sub.LoginFragment;
import com.pz.xingfutao.ui.tab.TabActivity;
import com.pz.xingfutao.utils.LoginUtil;
import com.pz.xingfutao.utils.PLog;
import com.pz.xingfutao.view.SwipeBackListener;
import com.pz.xingfutao.widget.ScrollTextView;
import com.pz.xingfutao.widget.XFProgressBar;
import com.pz.xingfutao.widget.XFToast;

/**
 * avoid using view id with content or view instance name with content
 * @author 7heaven
 *
 */
public class BaseTitleFragment extends Fragment {
	
	protected static final int MODE_TITLE = 0x1;
	protected static final int MODE_LEFT_BUTTON = 0x2;
	protected static final int MODE_RIGHT_BUTTON = 0x4;
	protected static final int MODE_SEARCH_BAR = 0x8;
	
	private int mode;
	
	protected View content;
	private LayoutInflater inflater;
	
	private boolean enableSwipe;
	private boolean interceptBackButton;
	protected boolean errorOccur = false;
	
	private ScrollTextView title;
	private ImageView leftButton;
	private ImageView rightButton;
	private EditText searchBar;
	private ImageView searchIcon;
	
	private XFProgressBar progressBar;
	private RelativeLayout loadingLayout;
	
	private LinearLayout rContent;
	private LinearLayout emptyContent;
	private LinearLayout errorContent;
	
	final public ErrorListener errorListener = new ErrorListener(){

		@Override
		public void onErrorResponse(VolleyError error) {
			progressBarToggle(false);
			
			errorOccur = true;
			onExtraErrorHandle(error);
			if(shallShowErrorPage() && getErrorStateView() != null && emptyContent != null && rContent != null){
				contentTrans();
				if(!isContentEmpty()){
					XFToast.showTextShort(R.string.fragment_common_error);
				}
			}
		}
		
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		if(this instanceof KeyboardInvoke){
			getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		this.inflater = inflater;
		
		content = inflater.inflate(R.layout.activity_title_base, null, false);
		
		if(enableSwipe){
			content.setFocusableInTouchMode(true);
			content.requestFocus();
			content.setOnTouchListener(new SwipeBackListener());
		}
		
		
		title = (ScrollTextView) content.findViewById(R.id.title);
		leftButton = (ImageView) content.findViewById(R.id.left_button);
		rightButton = (ImageView) content.findViewById(R.id.right_button);
		searchBar = (EditText) content.findViewById(R.id.search_bar);
		searchIcon = (ImageView) content.findViewById(R.id.search_icon);
		searchBar.setBackgroundDrawable(new RoundDrawable(0xFFFFFFFF));
		
		progressBar = (XFProgressBar) content.findViewById(R.id.progress_bar);
		loadingLayout = (RelativeLayout) content.findViewById(R.id.loading);
		
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
		
		if(getArguments() != null && getArguments().containsKey("title")){
			title.setText(getArguments().getString("title"));
		}else{
			if(getTag() != null && getTag() != "") title.setText(getTag());
		}
		
		return content;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(getArguments() != null && getArguments().containsKey("extra_back_button")){
			PLog.d("back_button", "back_button");
			setMode(getMode() | MODE_LEFT_BUTTON);
			getLeftButton().setImageResource(R.drawable.selector_title_button_back);
			getLeftButton().setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					dismiss();
				}
			});
		}
		contentTrans();
	}
	
	public void enableSwipeGesture(boolean enable){
		if(content != null){
			if(enable){
				content.setFocusableInTouchMode(true);
				content.requestFocus();
				content.setOnTouchListener(new SwipeBackListener());
			}else{
				content.setOnTouchListener(null);
			}
		}
	}
	
	public void interceptBackButton(boolean intercept){
	}
	
	protected void setContentView(int layoutRes){
		
		final LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		
		rContent = (LinearLayout) content.findViewById(R.id.content);
		rContent.addView(inflater.inflate(layoutRes, null, false), contentParams);
		rContent.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){}
		});
		
		emptyContent = (LinearLayout) content.findViewById(R.id.empty_content);
		emptyContent.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){}
		});
		errorContent = (LinearLayout) content.findViewById(R.id.error_content);
		errorContent.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){}
		});
		
		if(getEmptyStateView() != null) emptyContent.addView(getEmptyStateView(), contentParams);
		if(getErrorStateView() != null && shallShowErrorPage()) errorContent.addView(getErrorStateView(), contentParams);
		
		ViewTreeObserver observer = content.getViewTreeObserver();
		observer.addOnPreDrawListener(new OnPreDrawListener(){

			@Override
			public boolean onPreDraw() {
				contentTrans();
				
				return true;
			}
			
		});
	}
	
	private void contentTrans(){
		if(errorOccur && errorContent != null && isContentEmpty() && errorContent.getChildCount() > 0){
			errorContent.setVisibility(View.VISIBLE);
			if(emptyContent != null) emptyContent.setVisibility(View.GONE);
			if(rContent != null) rContent.setVisibility(View.GONE);
		}else if(isContentEmpty() && emptyContent != null && emptyContent.getChildCount() > 0){
			emptyContent.setVisibility(View.VISIBLE);
			if(errorContent != null) errorContent.setVisibility(View.GONE);
			if(rContent != null) rContent.setVisibility(View.GONE);
		}else{
			if(rContent != null) rContent.setVisibility(View.VISIBLE);
			if(errorContent != null) errorContent.setVisibility(View.GONE);
			if(emptyContent != null) emptyContent.setVisibility(View.GONE);
		}
	}
	
	protected void setMode(int mode){
		this.mode = mode;
		
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
	
	protected int getMode(){
		return mode;
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
	
	protected boolean shallShowErrorPage(){
		return true;
	}
	
	protected void onExtraErrorHandle(VolleyError error){}
	
	protected View getErrorStateView(){
		
		return inflateResource(R.layout.fragment_common_error_page);
	}
	
	protected View inflateResource(int layoutId){
		if(this != null && getActivity() != null){
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			if(inflater != null){
				return inflater.inflate(layoutId, null, false);
			}
		}
		
		return null;
	}
	
	protected View getEmptyStateView(){
		return inflateResource(R.layout.empty_layout_global_loading);
	}
	
	public void progressBarToggle(boolean enabled){
		contentTrans();
		if(enabled){
			if(isContentEmpty()){
				loadingLayout.setVisibility(View.VISIBLE);
			}else{
				progressBar.setVisibility(View.VISIBLE);
				if(getTitleUpperText() != null) getTitleView().setUpperText(getTitleUpperText());
			}
		}else{
			getTitleView().backward();
			progressBar.setVisibility(View.GONE);
			loadingLayout.setVisibility(View.GONE);
		}
	}
	
	public void startFragmentWithBackEnabled(BaseTitleFragment fragment, String tag){
		Fragment f = ((TabActivity) getActivity()).getLastFragment();
		if(f != null && f.getView() != null && f.getView().getAnimation() != null && f.getView().getAnimation().hasStarted()){
			return;
		}
		if(((TabActivity) getActivity()).getLastFragment() instanceof KeyboardInvoke){
			InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getSearchBar().getWindowToken(), 0);
		}
		
		if(fragment instanceof RequireLogin && !LoginUtil.checkLogin()){
			Bundle bundle = fragment.getArguments();
			if(bundle == null) bundle = new Bundle();
			bundle.putString("fragment_class", fragment.getClass().getName());
			PLog.d("fragment", fragment.getClass().getName());
			if(tag != null) bundle.putString("tag", tag);
			
			fragment = new LoginFragment();
			fragment.setArguments(bundle);
		}
		
		fragment.interceptBackButton(true);
		fragment.enableSwipeGesture(true);
		getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fragment_slide_right_enter,
				                                                    R.anim.fragment_slide_right_exit,
				                                                    R.anim.fragment_slide_left_enter,
				                                                    R.anim.fragment_slide_left_exit).add(android.R.id.tabcontent, fragment, tag).commit();
		if(enableSwipe){
			getFragmentManager().beginTransaction().hide(this);
		}
		
		((TabActivity) getActivity()).addFragmentToBackStack(fragment);
	}
	
	public void dismiss(){
		
		if(getActivity() != null) ((TabActivity) getActivity()).removeFragment(this);
	}
	
	protected abstract class ArgExec{
		private String[] key;
		private Fragment fragment;
		
		public ArgExec(Fragment fragment, String... key){
			this.key = key;
			this.fragment = fragment;
			
			for(int i = 0; i < key.length; i++){
				exe(i);
			}
		}
		
		protected abstract void set(int i, Object value);
		
		public void exe(int i){
			Bundle bundle = fragment.getArguments();
			if(bundle != null && bundle.containsKey(key[i])){
				set(i, bundle.get(key[i]));
			}
		}
	}
	
	public void setErrorState(boolean error){
		errorOccur = error;
		contentTrans();
	}
	
	protected String getTitleUpperText(){
		return null;
	}
}
