package org.theglobalsquare.app;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.*;

import org.theglobalsquare.framework.*;
import org.theglobalsquare.framework.values.*;
import org.theglobalsquare.ui.*;

public class MainActivity extends org.renpy.android.PythonActivity
		implements PropertyChangeListener {
	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// the TGSEvent object
		Object value = event.getNewValue();
		String out = "";
		if(value instanceof TGSEvent) {
			TGSEvent<? extends TGSObject> e = (TGSEvent<? extends TGSObject>)value;
			out = "EVENT: " + value.getClass().getName() + ": " + e.getSubject().getName() + " " + e.getVerb();
		} else if(value instanceof TGSObject) {
			out = "VALUE: " + ((TGSObject)value).getName() + ": " + value;
		}
		monitor(out);
	}

	public final static int TAB_SEARCH = 0;
	public final static int TAB_OVERVIEW = 1;
	public final static int TAB_MONITOR = 2;
	
	private boolean composerShowing = false;
	
	private String monitorTxt = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		monitor("MainActivity: INIT");
		
		logHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				monitor(msg.obj.toString());
				super.handleMessage(msg);
			}
		};
		
		logger = this;
// this gets done in org.renpy.android.PythonActivity
//		setContentView(R.layout.main_activity);

        setTitle(getString(R.string.short_name));
        
        configureTabs();
        
        configureButtons();
	}
	
	protected void configureTabs() {
		ActionBar bar = getSupportActionBar();
		
		// make sure to setContentView() first (in super.onCreate)
		mViewPager = (ViewPager)findViewById(R.id.mainActivityPager);

		mTabsAdapter = new TabsAdapter(this, mViewPager);

		// TAB_SEARCH
        mTabsAdapter.addTab(
                bar.newTab().setText(R.string.searchBtnLabel),
                SearchFragment.class, null);
        // TAB_OVERVIEW
        mTabsAdapter.addTab(
                bar.newTab().setText(R.string.overviewLabel),
                OverviewListFragment.class, null);
        // TAB_MONITOR
        mTabsAdapter.addTab(
        		bar.newTab().setText(R.string.monitorLabel),
        		MonitorFragment.class, null);
        
        // TODO tabs for each square the user has joined
        /*
        mTabsAdapter.addTab(
                bar.newTab().setText("abc"),
                MessageListFragment.class, null);
        mTabsAdapter.addTab(
                bar.newTab().setText("Another Square"),
                MessageListFragment.class, null);
        */
        
        // select Monitor tab
        mViewPager.setCurrentItem(TAB_MONITOR);
	}
	
	protected void configureButtons() {
		ImageButton sendBtn = (ImageButton)findViewById(R.id.sendBtn);
		sendBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// SEND A MESSAGE
				
				// get text from input
				EditText et = (EditText)findViewById(R.id.messageTxt);
				String body = et.getText().toString();
				
				// create message
				TGSMessage subject = new TGSMessage();
				subject.setFrom(TGSUser.getMe());
				subject.setBody(body);
				
				// send message to event queue
				TGSMessageEvent event = new TGSMessageEvent();
				event.setVerb(TGSMessage.SEND);
				event.setSubject(subject);
				TGSEventProxy.sendEvent(event, true);
				
				// clear text
				et.setText(null);
				
				monitor("UI: sent message");
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
	    return true;
	}
	
	private Handler logHandler = null;
	private static MainActivity logger = null;
	
	// to be used by python
	public static void log(String message) {
		if(logger != null) {
			Message msg = new Message();
			msg.obj = message;
			logger.logHandler.sendMessage(msg);
		}
	}
		
	public void monitor(String message) {
		this.monitorTxt += "\n" + message;
		final TextView monitorTxt = (TextView) findViewById(R.id.monitor);
		if(monitorTxt != null)
			monitorTxt.setText(this.monitorTxt);
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
