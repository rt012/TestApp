package com.example.testapp.bo;

import java.util.ArrayList;

/**
 * Class which represents a JSON Object of the data source
 * @author remi
 *
 */
public class HeadElement  {

private String text;
private int count;
private ArrayList<SubElement> subliste;



public HeadElement(String text, int count, ArrayList<SubElement> subliste) {
	super();
	this.text = text;
	this.count = count;
	this.subliste = subliste;
}

public HeadElement(String text, int count) {
	super();
	this.text = text;
	this.count = count;
	
}
public ArrayList<SubElement> getSubliste() {
	return subliste;
}
public void setSubliste(ArrayList<SubElement> subliste) {
	this.subliste = subliste;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
} 


}
