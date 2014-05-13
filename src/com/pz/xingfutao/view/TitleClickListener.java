package com.pz.xingfutao.view;

public class TitleClickListener implements TitleListener{
	
	private int[] imageResources;
	private String[] titles;
	
	public TitleClickListener(int[] imageResources, String[] titles){
		this.imageResources = imageResources;
		this.titles = titles;
	}
	
	public int[] getImageRes(){
		return imageResources;
	}
	
	public String[] getTitles(){
		return titles;
	}

	@Override
	public void onClick(int position){}
}
