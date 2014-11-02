package gdg.nat.ksc.present.adapter;

import gdg.nat.view.FragmentPagerIndicatorItem;
import gdg.nat.view.SlidingTabLayout.TabColorizer;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SlidingIndicatorPagerAdapter extends FragmentPagerAdapter{
	private List<FragmentPagerIndicatorItem> listTabs = new ArrayList<FragmentPagerIndicatorItem>();
	
	private TabColorizer tabColorizer = new TabColorizer(){
		@Override
		public int getIndicatorColor(int position){
			return listTabs.get(position).getIndicatorColor();
		}
		
		@Override
		public int getDividerColor(int position){
			return Color.GRAY;
		}
	};
	
	public SlidingIndicatorPagerAdapter(FragmentManager fm){
		super(fm);
	}
	
	public void setListTab(List<FragmentPagerIndicatorItem> listTabs){
		this.listTabs = listTabs;
		notifyDataSetChanged();
	}
	
	public void clear(){
		this.listTabs.clear();
		notifyDataSetChanged();
	}
	
	public void addTab(FragmentPagerIndicatorItem tab){
		this.listTabs.add(tab);
	}
	
	public TabColorizer getTabColorizer(){
		return tabColorizer;
	}
	
	@Override
	public Fragment getItem(int i){
		return listTabs.get(i).createFragment();
	}
	
	@Override
	public int getCount(){
		return listTabs.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position){
		return listTabs.get(position).getTitle();
	}
}