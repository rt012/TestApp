package com.example.testapp.adapter;

import java.util.ArrayList;

import com.example.testapp.bo.HeadElement;
import com.example.testapp2.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * ExpandableListAdapter handles the elements of the ExpandableList
 * @author remi
 *
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
	 
    private Context context;
    private ArrayList<HeadElement> listDataHeader; 
    
 
    public ExpandableListAdapter(Context context, ArrayList<HeadElement> listDataHeader) {
        this.context = context;
        this.listDataHeader = listDataHeader;
       
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
       return this.listDataHeader.get(groupPosition).getSubliste().get(childPosititon).getTitle();
      
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
 
        final String childText = (String) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = LayoutInflater.from(context);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }
 
        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
 
        txtListChild.setText(childText);
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
      if(this.listDataHeader.get(groupPosition).getSubliste() != null)
    	 return  this.listDataHeader.get(groupPosition).getSubliste().size();
	return 0;
        
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        HeadElement headerElement = (HeadElement) getGroup(groupPosition);
        String headerTitle = headerElement.getText();
        int headerCount = headerElement.getCount();
        if (convertView == null) {
        	LayoutInflater infalInflater = LayoutInflater.from(context);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
 
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        TextView lbCount = (TextView) convertView.findViewById(R.id.lbCount);
        String label;
        if(headerCount == 0) label = "kein Katalog"; else label = headerCount + " Kataloge"; 
        lbCount.setText("("+ label + " vorhanden)");
        
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}