package com.example.testapp.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;












import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.example.testapp.adapter.ExpandableListAdapter;
import com.example.testapp.bo.HeadElement;
import com.example.testapp.bo.SubElement;
import com.example.testapp.fragments.DetailFragment;
import com.example.testapp.fragments.ListFragment;

/**
 * Async task class to get json by making HTTP call
 * */
public class GetElements extends AsyncTask<Integer, Void, Void>  {
	
private ProgressDialog pDialog;
private ArrayList<HeadElement> rootarray = new ArrayList<HeadElement>();
private Context context;
// URL to get Data JSON n
private static String url = "http://www.adrodev.de/tests/2332-server-catalogues.php";

// JSON Node Names

private static final String TAG_TEXT="text";
private static final String TAG_COUNT="count";
private static final String TAG_SUB="sub";
private static final String TAG_IMAGE="image";
private static final String TAG_title="title";
private static final String TAG_SUBTEXT="text";

private JSONArray elements = null;


private ArrayList<SubElement> subElementList;
private ExpandableListAdapter listAdapter;
private ExpandableListView expListView;
private  int groupPosition;
private Object expandedItem;


public GetElements(Context context, ExpandableListView expListView, int groupPosition, Object expandedItem ) {
	this.context = context;
	this.expListView = expListView;
	this.groupPosition = groupPosition;
	this.expandedItem = expandedItem;
}
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Integer... arg0) {
    	
    	// important when the user reload the Listview ;to insure that the array is empty
    	rootarray.clear();
        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
        
        // Check if the data source is complete
        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
                
                 
                // Getting JSON Array node
           
                JSONArray jsonarray = new JSONArray(jsonStr);
              // looping through the arrays and put the data in the correct attribute
                for(int i = 0; i < jsonarray.length(); i++) {
                	
                	JSONObject e = jsonarray.getJSONObject(i);
                	String text = e.getString(TAG_TEXT);
                	int count = e.getInt(TAG_COUNT);
                	if(e.length() > 2) {
                	JSONArray sub = e.getJSONArray(TAG_SUB);
                	subElementList = new ArrayList<SubElement>();
                	for(int a = 0; a < sub.length(); a++) {
                		
                		JSONObject subelement = sub.getJSONObject(a);
                		String image = subelement.getString(TAG_IMAGE);
                		String title = subelement.getString(TAG_title);
                		String subtext = subelement.getString(TAG_SUBTEXT);
                	
                		subElementList.add(new SubElement(image, title, subtext));
                		
                	}
                	
                	rootarray.add(new HeadElement(text, count, subElementList));
               
                	}else {
                		
            
                		rootarray.add(new HeadElement(text,count, null));
                	}
                	}
                
                
                		
                
              

            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("fail");
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
        
         /**
         * Updating parsed JSON data into ListView
         * */
        ArrayList<HeadElement> rootarray1 = sort(rootarray);
        listAdapter = new ExpandableListAdapter(context, rootarray1);
        expListView.setAdapter(listAdapter);
        
        // check if a element of the listview is expanded 
        // ( '-1' is the default value, which means no element of the Listview is expanded) 
        if(groupPosition != -1 ) {
    	// get the groupPosition of the element which is expanded 	
        	for(int i = 0;  expandedItem.equals(rootarray.get(i).getText()) ; i++) {
        		groupPosition = i; }
        	expListView.expandGroup(groupPosition);
        		
        }
       // commit back the current rootarray
       ListFragment fl= new ListFragment();
       fl.setRootArray(rootarray);
     
        
    	} 
    
    


    // Bubblesort method to insure that Listview elements are everytime in the same order 
	public ArrayList<HeadElement> sort(ArrayList<HeadElement> array) {
		int n = array.size();
		for(int i = n-1; i >= 0; i--) {
			for (int j = 1; j <= i; j++) {
				if ( array.get(j-1).getCount() > array.get(j).getCount()) {
				HeadElement temp = array.get(j-1);
	        	array.set(j-1, array.get(j));
	        	array.set(j, temp);
					
				}
			}
		}
	return array;
}
}
