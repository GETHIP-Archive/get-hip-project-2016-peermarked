package com.gallup.gethip.helpers;

import com.gallup.gethip.model.Paper;
import com.j256.ormlite.field.DatabaseField;

/**
 * 
 * @author Matthew Meacham
 * 
 *         Represents an html Link
 *
 */
public class Link {

	@DatabaseField
	private String link;
	@DatabaseField
	private String rel;
	@DatabaseField(canBeNull = true, foreign = true)
	private Paper paper;

	public Link() {

	}

	public Link(String link, String rel) {
		this.link = link;
		this.rel = rel;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

}
