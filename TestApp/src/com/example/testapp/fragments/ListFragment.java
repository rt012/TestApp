package com.example.testapp.fragments;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.testapp.adapter.ExpandableListAdapter;
import com.example.testapp.bo.HeadElement;
import com.example.testapp.bo.SubElement;
import com.example.testapp.json.GetElements;
import com.example.testapp.json.GetHeader;
import com.example.testapp.json.ServiceHandler;
import com.example.testapp2.R;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ListFragment extends Fragment implements OnChildClickListener, OnGroupExpandListener, OnClickListener {
	
    protected static final String TAG = null;
	private ExpandableListView expListView;
    private	static ArrayList<HeadElement> rootarray = new ArrayList<HeadElement>();
    // set groupPosition to -1 to insure that after starting the fragment the first time no listview element will expand automatically
    private  int groupPosition = -1;
    
    private GetElements getElements;

	private Object expandedItem;
	private TextView header;

	
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		 	inflater.inflate(R.menu.reload, menu);

	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		  
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
    
     // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
        expListView.setGroupIndicator(null);
        
    // Initialize the list header
        header = new TextView(getActivity());
        header.setText("");
        expListView.addHeaderView(header);
        
        
    //  calling async tast to get the header element   
        GetHeader getHeader = new GetHeader(getActivity(), header);
	    getHeader.execute();
       
    // Setup timer to refresh Header automatically each 15 seconds
        Timer refreshTimer = new Timer();
        refreshTimer.scheduleAtFixedRate(new TimerTask() {
        	  @Override
        	  public void run() {
        		  
        		  GetHeader getHeader = new GetHeader(getActivity(), header);
        	      getHeader.execute();
        	  }
        	}, 15*1000, 15*1000);
          
 
     // Calling async task to get the listview elements 
        getElements = new GetElements(getActivity(), expListView, groupPosition, expandedItem);
        getElements.execute(groupPosition);
        
     // Listview on child click listener
       expListView.setOnChildClickListener(this);
        
     // Listview Group expanded listener
        expListView.setOnGroupExpandListener(this);
        
     // insure that the fragment has a actionBar       
        setHasOptionsMenu(true);
        
      
        
        return rootView;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// after the user has pushed the reload button in the actionbar, the Listview will be refreshed 
		switch(item.getItemId()){
		case R.id.reload:
			 getElements = new GetElements(getActivity(), expListView, groupPosition, expandedItem);
		     getElements.execute(groupPosition);
			return true;
			
		default: return super.onOptionsItemSelected(item);
		}
	}

	// After the user has clicked a child element of the listview the detail fragment appear
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		
		   	DetailFragment detailFragment = new DetailFragment();
		   	// helper method to commit required information to the detail fragment 
		  	detailFragment.setArguments(rootarray, groupPosition, childPosition);
		    FragmentTransaction transaction = getFragmentManager().beginTransaction();
		    transaction.replace(R.id.root_frame, detailFragment);
		    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		    transaction.addToBackStack(null);
		    transaction.commit();
		return false;
	}

	//  get the groupPosition and the Item which the user has clicked
	@Override
	public void onGroupExpand(int arg0) {
		this.groupPosition = arg0;
  		expandedItem =  expListView.getItemAtPosition(arg0+1);
		
		
	}

	// Helper method for GetElements class to commit back the received data from the HTTP call 
	public void setRootArray(ArrayList<HeadElement> rootArray) {
		this.rootarray = rootArray;
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
	

