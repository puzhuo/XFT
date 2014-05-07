package com.pz.xingfutao.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.pz.xingfutao.R;
import com.pz.xingfutao.entities.ImageMap;
import com.pz.xingfutao.ui.base.BaseTitleFragment;
import com.pz.xingfutao.ui.sub.ItemDetailFragment;
import com.pz.xingfutao.ui.tab.TabActivity;

public class FragmentUtil {
	
	private static final SparseArray<String> fragmentMap;
	private static final SparseArray<String> bundleMap;
	
	static{
		fragmentMap = new SparseArray<String>();
		fragmentMap.put(ImageMap.LINK_ADS, "com.pz.xingfutao.ui.sub.AdsFragment");
		fragmentMap.put(ImageMap.LINK_ARTICLE, "com.pz.xingfutao.ui.sub.ArticleFragment");
		fragmentMap.put(ImageMap.LINK_GOOD_DETAIL, "com.pz.xingfutao.ui.sub.ItemDetailFragment");
		fragmentMap.put(ImageMap.LINK_GOOD_LIST, "com.pz.xingfutao.ui.sub.ItemListFragment");
		fragmentMap.put(ImageMap.LINK_CATEGORY_LIST, "com.pz.xingfutao.ui.sub.PostListFragment");
		fragmentMap.put(ImageMap.LINK_GOOD_DESC, "com.pz.xingfutao.ui.sub.ArticleFragment");
		
		bundleMap = new SparseArray<String>();
		bundleMap.put(ImageMap.LINK_ADS, "ads_image_url");
		bundleMap.put(ImageMap.LINK_ARTICLE, "article_url");
		bundleMap.put(ImageMap.LINK_GOOD_DETAIL, "good_id");
		bundleMap.put(ImageMap.LINK_GOOD_LIST, "item_list_api");
		bundleMap.put(ImageMap.LINK_CATEGORY_LIST, "category_id");
		bundleMap.put(ImageMap.LINK_GOOD_DESC, "content");
	}

	@Deprecated
	public static void startItemDetailFragment(Context context, Bundle bundle){
		if(context instanceof TabActivity){
			Fragment fragment = ((TabActivity) context).getLastFragment();
			if(fragment instanceof BaseTitleFragment){
				ItemDetailFragment itemFragment = new ItemDetailFragment();
				itemFragment.setArguments(bundle);
				
				((BaseTitleFragment) fragment).startFragmentWithBackEnabled(itemFragment, fragment.getString(R.string.title_good_detail));
			}
		}
	}
	
	@Deprecated
	public static void startItemDetailFragment(Context context, String goodId){
		Bundle bundle = new Bundle();
		bundle.putString("good_id", goodId);
		
		startItemDetailFragment(context, bundle);
	}
	
	public static void startImageMappingFragment(Context context, ImageMap imageMap){
		if(context instanceof TabActivity){
			Fragment fragment = ((TabActivity) context).getLastFragment();
			if(fragment instanceof BaseTitleFragment){
				try {
					Class<?> clazz = Class.forName(fragmentMap.get(imageMap.getLinkType()));
					
					BaseTitleFragment newFragmentInstance = (BaseTitleFragment) clazz.newInstance();
					Bundle bundle = new Bundle();
					bundle.putString(bundleMap.get(imageMap.getLinkType()), imageMap.getLink());
					if(imageMap.getTitle() != null) bundle.putString("title", imageMap.getTitle());
					newFragmentInstance.setArguments(bundle);
					
					((BaseTitleFragment) fragment).startFragmentWithBackEnabled(newFragmentInstance, null);
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
