package com.pz.xingfutao.ui.sub;

import android.os.Bundle;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;
import com.pz.xingfutao.utils.TextViewUtil;

public class ArticleFragment extends BaseBackButtonFragment{

	private TextView tv;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentView(R.layout.fragment_article);
		tv = (TextView) super.content.findViewById(R.id.tv);
		
		new ArgExec(this, "content", "article_url"){
			@Override
			protected void set(int i, Object value){
				switch(i){
				case 0:
					TextViewUtil.formatContent(getActivity(), (String) value, tv);
					break;
				case 1:
					break;
				}
			}
		};
	}
}
