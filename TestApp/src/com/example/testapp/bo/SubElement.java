package com.example.testapp.bo;


/**
 * Class which represents a JSON Object of the sub-array 
 * @author remi
 *
 */
public class SubElement {
	
	private String image;
	private String title;
	private String subtext;
	
	
	
	public SubElement(String image, String title, String subtext) {
		super();
		this.image = image;
		this.title = title;
		this.subtext = subtext;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtext() {
		return subtext;
	}
	public void setSubtext(String subtext) {
		this.subtext = subtext;
	}
	
	

}
