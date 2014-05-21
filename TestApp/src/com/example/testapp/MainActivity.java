package com.example.testapp;

import com.example.testapp2.R;
import com.example.testapp.adapter.TabsPagerAdapter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

/**
 * MainActivity to realize the ViewPager function
 * @author remi
 *
 */

public class MainActivity extends FragmentActivity implements
ActionBar.TabListener {

private ViewPager viewPager;
private TabsPagerAdapter mAdapter;
private ActionBar actionBar;
// Tab titles
private String[] tabs = { "Tap One", "Tap Two" };

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	
// Initilization
	viewPager = (ViewPager) findViewById(R.id.pager);
	actionBar = getActionBar();
	mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
	viewPager.setAdapter(mAdapter);
	actionBar.setHomeButtonEnabled(false);
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);   
	
// Adding Tabs
	for (String tab_name : tabs) {
	    actionBar.addTab(actionBar.newTab().setText(tab_name)
	            .setTabListener(this));
	   
	    
	    
	}
	
	

	

/**
 * on swiping the viewpager make respective tab selected
 * */
viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

    @Override
    public void onPageSelected(int position) {
        // on changing the page
        // make respected tab selected
        actionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }
});


}

@Override
public boolean onCreateOptionsMenu(Menu menu) {

	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	
	
	switch (item.getItemId()){
		case R.id.action_settings:
			System.out.println("settings");
			break;
	}
	return super.onOptionsItemSelected(item);
}
	

	
	

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		 // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

}
