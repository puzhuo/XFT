package com.pz.xingfutao.ui.tab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.pz.xingfutao.R;
import com.pz.xingfutao.XFApplication;
import com.pz.xingfutao.ui.api.KeyboardInvoke;
import com.pz.xingfutao.ui.base.BaseTitleFragment;
import com.pz.xingfutao.ui.sub.LoginFragment;
import com.pz.xingfutao.ui.sub.SignUpFragment;
import com.pz.xingfutao.ui.tab.fragments.TabCartFragment;
import com.pz.xingfutao.ui.tab.fragments.TabCategoryFragment;
import com.pz.xingfutao.ui.tab.fragments.TabForumFragment;
import com.pz.xingfutao.ui.tab.fragments.TabProfileFragment;
import com.pz.xingfutao.ui.tab.fragments.TabStoreFragment;
import com.pz.xingfutao.utils.FragmentUtil;

public class TabActivity extends FragmentActivity{
	
	private FragmentTabHost tabHost;
	
	private List<BaseTitleFragment> fragments;
	
	private boolean exit = false;
	
	private Map<String, Bundle> fragmentBundles;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		fragments = new ArrayList<BaseTitleFragment>();
		
		setContentView(R.layout.activity_tab);
		
		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		
		setupTab();
		
		fragmentBundles = new HashMap<String, Bundle>();
	}
	
	public void addToBundle(Fragment fragment, Bundle bundle){
		if(fragmentBundles.containsKey(fragment.getClass().getName())){
			fragmentBundles.get(fragment.getClass().getName()).putAll(bundle);
		}else{
			fragmentBundles.put(fragment.getClass().getName(), bundle);
		}
	}
	
	public Bundle getBundle(Fragment fragment){
		return fragmentBundles.get(fragment.getClass().getName());
	}
	
	private void setupTab(){
		tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		tabHost.addTab(newTab(getString(R.string.tab_title_store), R.drawable.selector_tab_store), TabStoreFragment.class, null);
		tabHost.addTab(newTab(getString(R.string.tab_title_category), R.drawable.selector_tab_category), TabCategoryFragment.class, null);
		tabHost.addTab(newTab(getString(R.string.tab_title_cart), R.drawable.selector_tab_cart), TabCartFragment.class, null);
		tabHost.addTab(newTab(getString(R.string.tab_title_forum), R.drawable.selector_tab_forum), TabForumFragment.class, null);
		tabHost.addTab(newTab(getString(R.string.tab_title_profile), R.drawable.selector_tab_profile), TabProfileFragment.class, null);
	}
	
	private TabSpec newTab(String tag, int drawableResId){
		View indicator = getLayoutInflater().inflate(R.layout.tab_item, (ViewGroup) findViewById(android.R.id.tabs), false);
		((TextView) indicator.findViewById(R.id.title)).setText(tag);
		((ImageView) indicator.findViewById(R.id.icon)).setImageResource(drawableResId);
		
		TabSpec tabSpec = tabHost.newTabSpec(tag);
		tabSpec.setIndicator(indicator);
		
		return tabSpec;
	}
	
	public void switchToTab(int index){
		tabHost.setCurrentTab(index);
	}
	
	public void addFragmentToBackStack(BaseTitleFragment fragment){
		fragments.add(fragment);
	}
	
	public void removeFragmentsAndSwitchTo(final Fragment fragment, final int index){
		
		switchToTab(index);
		
		if(fragment != null && fragments.size() > 0){
			Animation animation = AnimationUtils.loadAnimation(this, R.anim.fragment_slide_right_exit);
			animation.setDuration(300);
			animation.setAnimationListener(new AnimationListener(){

				@Override
				public void onAnimationStart(Animation animation) {}

				@Override
				public void onAnimationEnd(Animation animation) {
					while(fragments.size() > 0){
						Fragment fragment = fragments.get(fragments.size() - 1);
						getSupportFragmentManager().beginTransaction().remove(fragment).commit();
						fragments.remove(fragment);
					}
					
				}

				@Override
				public void onAnimationRepeat(Animation animation) {}
			});
			
			
			fragment.getView().startAnimation(animation);
		}
		
	}
	
	public void removeFragment(final Fragment fragment){
		View v = fragment.getView();
		
		if(v != null && v.getAnimation() != null && v.getAnimation().hasStarted()){
			return;
		}
		
		if(fragment instanceof KeyboardInvoke){
			InputMethodManager inputMethodManager = (InputMethodManager) XFApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
		}
		
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.fragment_slide_right_exit);
		animation.setDuration(300);
		animation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationStart(Animation animation) {}

			@Override
			public void onAnimationEnd(Animation animation) {
				BaseTitleFragment pendingFragment = null;
				String pendingTag = null;
				if(fragment instanceof LoginFragment || fragment instanceof SignUpFragment){
					pendingFragment = FragmentUtil.getPendingFragment((BaseTitleFragment) fragment);
					if(fragment.getArguments() != null) pendingTag = fragment.getArguments().getString("tag");
					
				}
				
				getSupportFragmentManager().beginTransaction().remove(fragment).commit();
				if(fragments.contains(fragment)) fragments.remove(fragment);
				if(fragments.size() > 0){
					getSupportFragmentManager().beginTransaction().show(fragments.get(fragments.size() - 1));
				}
				
				if(pendingFragment != null){
					getLastFragment().startFragmentWithBackEnabled(pendingFragment, pendingTag);
				}else{
					getLastFragment().onResume();
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {}
			
		});
		
		v.startAnimation(animation);
	}
	
	public BaseTitleFragment getLastFragment(){
		if(fragments.size() > 0) return fragments.get(fragments.size() - 1);
		return (BaseTitleFragment) getSupportFragmentManager().findFragmentByTag(tabHost.getCurrentTabTag());
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(fragments.size() > 0){
				((BaseTitleFragment) fragments.get(fragments.size() - 1)).dismiss();
				return true;
			}else if(!exit){
				exit = true;
				Toast.makeText(this, getString(R.string.activity_tab_click_again_to_quit), Toast.LENGTH_LONG).show();
				new Handler().postDelayed(new Runnable(){
					@Override
					public void run(){
						exit = false;
					}
				}, 1500L);
				return true;
			}
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
