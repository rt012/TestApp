package com.example.testapp.fragments;

import java.util.ArrayList;

import com.example.testapp.bo.HeadElement;
import com.example.testapp2.R;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment which displays the detailed view of a sub element
 * @author remi
 *
 */
public class DetailFragment extends Fragment {
	private TextView elementName;
	private TextView elementText;
	private ArrayList<HeadElement> rootarray = null;
	private int groupPosition;
	private int childPosition;
	
	
	public DetailFragment() {}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        elementName = (TextView)rootView.findViewById(R.id.elementName);
        elementText = (TextView) rootView.findViewById(R.id.elementText);
     
        elementName.setText( rootarray.get(groupPosition).getSubliste().get(childPosition).getTitle());
        elementText.setText(rootarray.get(groupPosition).getSubliste().get(childPosition).getSubtext());
        setHasOptionsMenu(true);
        return rootView;
    }
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	 
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.reload, menu);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		case R.id.reload:
		return true;
			
		default: return super.onOptionsItemSelected(item);
		}
	}

	

	

	
	/**
	 * Helper method to get required attributes from  {@link ListFragment.java} 
	 * @param l: is the array which is used to display data in the expandable listview 
	 * @param groupPosition: is the Position of the header element  which user has pushed 
	 * @param childPosition: is the Position of the sub element which the user  has pushed 
	 */

	public void setArguments(ArrayList<HeadElement> l, int groupPosition, int childPosition) {
		rootarray = l;
		this.groupPosition = groupPosition;
		this.childPosition = childPosition;
	}
	
	
}
