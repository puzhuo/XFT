package com.pz.xingfutao.utils;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import android.widget.Toast;

import com.pz.xingfutao.R;
import com.pz.xingfutao.graphics.BackgroundTextDrawable;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.widget.PatternLessEmo;


public class TextViewUtil {
	
	private static final String IMG_PATTERN = "\\[img\\]([a-zA-Z0-9\\/\\:\\.\\_\\-]+)\\[\\/img\\]";
	private static final String I_PATTERN = "\\[i\\=([a-zA-Z0-9\\/\\:\\.\\_\\-]+)\\]([^\\[\\]]+)\\[\\/i]";
	private static final String LINK_PATTERN = "\\[url\\=([a-zA-Z0-9\\/\\:\\.\\_\\-]+)\\]([^\\[\\]]+)\\[\\/url]";
	private static final String EMO_PATTERN = "\\{\\:([0-9\\_]+)\\:\\}";
	
	public static void formatContent(final Context context, String content, final TextView textView){
		
		String formatContent = content;
		
		final int textWidthForEmo = (int) (textView.getTextSize() * 1.4F);
		final int textWidth = textView.getMeasuredWidth() - textView.getPaddingLeft() - textView.getPaddingRight();
		
		Iterator<String> it = PatternLessEmo.emoMap.keySet().iterator();
		
		while(it.hasNext()){
			String key = it.next();
			formatContent = formatContent.replace(key, PatternLessEmo.emoMap.get(key));
		}
		
		String formatContentForHtml = formatContent;
		
		formatContent = Html.fromHtml(formatContent).toString();
		
		final SpannableString ss = new SpannableString(formatContent);
		
		//pre
		Spanned htmlSpanned = Html.fromHtml(formatContentForHtml);
		ImageSpan[] preHtmlSpan = htmlSpanned.getSpans(0, formatContentForHtml.length(), ImageSpan.class);
		
		for(ImageSpan imageSpan : preHtmlSpan){
		    final int start = htmlSpanned.getSpanStart(imageSpan);
		    final int end = htmlSpanned.getSpanEnd(imageSpan);
		    
		    final Drawable d = context.getResources().getDrawable(R.drawable.pre_load_image);
		    PLog.d("textWidth:" + textWidth, "d.getIntrinsicWidth():" + d.getIntrinsicWidth());
		    d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
		    
		    ss.setSpan(new ImageSpan(d, DynamicDrawableSpan.ALIGN_BOTTOM), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		    
		    NetworkHandler.getInstance(context).networkImageSpannable(imageSpan.getSource(), ss, textView, start, end);
		}
		
		PLog.d("ss", ss.getSpans(0, ss.length(), ImageSpan.class).toString());
		
		Pattern pattern = Pattern.compile(IMG_PATTERN  + "|" + I_PATTERN + "|" + LINK_PATTERN + "|" + EMO_PATTERN);
		Matcher matcher = pattern.matcher(formatContent);
		
		while(matcher.find()){
			final String group = matcher.group();
			final int start = matcher.start();
			final int end = matcher.end();
			
			if(group.startsWith("[img]")){
				final String url = group.substring(5, group.lastIndexOf("["));
				Drawable drawable = context.getResources().getDrawable(R.drawable.pre_load_image);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				
				ss.setSpan(new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BASELINE), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				ss.setSpan(new ClickableSpan(){
					@Override
					public void onClick(View widget) {
						Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
					}
				}, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				NetworkHandler.getInstance(context).networkImageSpannable(url, ss, textView, start, end);
			}else if(group.startsWith("{:")){
				String identifier = context.getPackageName() + ":drawable/emo_" + group.substring(2, group.lastIndexOf(":"));
				int id = context.getResources().getIdentifier(identifier, null, null);
				if(id != 0){
					Drawable emo = context.getResources().getDrawable(id);
					emo.setBounds(0, 0, textWidthForEmo, textWidthForEmo);
					ss.setSpan(new ImageSpan(emo, DynamicDrawableSpan.ALIGN_BASELINE), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			}else if(group.startsWith("[url")){
				final String url = group.substring(5, group.indexOf("]"));
				final String title = group.substring(group.indexOf("]") + 1, group.lastIndexOf("["));
				
				BackgroundTextDrawable d = new BackgroundTextDrawable("Google", 0xFFFFFFFF, context.getResources().getColor(R.color.global_highlight_color), 10);
				d.setBounds(0, 0, 150, textWidthForEmo);
				
				ss.setSpan(new ImageSpan(d, DynamicDrawableSpan.ALIGN_BOTTOM), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				ss.setSpan(new URLSpan(url), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}else if(group.startsWith("[i")){
				final String type = group.substring(3, group.indexOf("]"));
				final String src = group.substring(group.indexOf("]") + 1, group.lastIndexOf("["));
				
				Drawable d = context.getResources().getDrawable(R.drawable.ic_launcher);
				d.setBounds(0, 0, 0, 0);
				
				ss.setSpan(new ImageSpan(d, DynamicDrawableSpan.ALIGN_BOTTOM), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		
		textView.setText(ss);
	}
	
	public static String ToDBC(String input) {
		   char[] c = input.toCharArray();
		   for (int i = 0; i< c.length; i++) {
		       if (c[i] == 12288) {
		         c[i] = (char) 32;
		         continue;
		       }if (c[i]> 65280&& c[i]< 65375)
		          c[i] = (char) (c[i] - 65248);
		       }
		   return new String(c);
		}
}
		