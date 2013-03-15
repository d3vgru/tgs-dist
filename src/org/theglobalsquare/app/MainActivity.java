package org.theglobalsquare.app;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.*;

import org.theglobalsquare.framework.*;
import org.theglobalsquare.ui.OverviewListFragment;
import org.theglobalsquare.ui.SearchFragment;

public class MainActivity extends org.renpy.android.PythonActivity {
	public final static int TAB_SEARCH = 0;
	public final static int TAB_OVERVIEW = 1;
	
	private boolean composerShowing = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		setContentView(R.layout.main_activity);

        setTitle(getString(R.string.short_name));
        
        configureTabs();
	}
	
	public void configureTabs() {
		ActionBar bar = getSupportActionBar();
		
		// make sure to setContentView() first (in super.onCreate)
		mViewPager = (ViewPager)findViewById(R.id.mainActivityPager);

		mTabsAdapter = new TabsAdapter(this, mViewPager);

		// TAB_SEARCH
        mTabsAdapter.addTab(
                bar.newTab().setText(getString(R.string.searchBtnLabel)),
                SearchFragment.class, null);
        // TAB_OVERVIEW
        mTabsAdapter.addTab(
                bar.newTab().setText(getString(R.string.overviewLabel)),
                OverviewListFragment.class, null);
        // tabs for each square the user has joined
        mTabsAdapter.addTab(
                bar.newTab().setText("abc"),
                MessageListFragment.class, null);
        mTabsAdapter.addTab(
                bar.newTab().setText("Another Square"),
                MessageListFragment.class, null);
        
        // select Overview (My Squares) tab
        mViewPager.setCurrentItem(TAB_OVERVIEW);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// FIXME disable until startup complete
		switch(item.getItemId()) {
			case R.id.menu_create:
				// TODO show new square dialog
				Toast.makeText(this, R.string.createBtnLabel, Toast.LENGTH_SHORT).show();
				break;
			case R.id.menu_compose:
				showComposer();
				break;
			case R.id.menu_help:
				// TODO show help dialog
				Toast.makeText(this, R.string.helpBtnLabel, Toast.LENGTH_SHORT).show();
				break;
			case R.id.menu_refresh:
				mTabsAdapter.notifyDataSetChanged();
				break;
			case R.id.menu_search:
				// select Search tab
		        mViewPager.setCurrentItem(TAB_SEARCH);
				break;
			case R.id.menu_settings:
				// TODO show settings dialog
				Toast.makeText(this, R.string.settingsBtnLabel, Toast.LENGTH_SHORT).show();
				break;
			default:
				// unknown option, maybe a superclass can handle it
				return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	@Override
	public void onBackPressed() {
		if(composerShowing)
			hideComposer();
		else super.onBackPressed();
	}
	
	private ViewGroup getComposer() {
		return (ViewGroup)findViewById(R.id.mainActivityComposer);
	}
	
	public void showComposer() {
		getComposer().setVisibility(View.VISIBLE);
		composerShowing = true;
	}
	
	public void hideComposer() {
		getComposer().setVisibility(View.GONE);
		composerShowing = false;
	}

}
