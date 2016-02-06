package com.gallup.gethip.helpers;

import com.gallup.gethip.model.Paper;
import com.j256.ormlite.field.DatabaseField;

/**
 * 
 * @author Matthew Meacham
 * 
 *         Represents a string that would contains a theme, this is necessary because the themes are contained within a List
 *
 */
public class ThemeString {
	@DatabaseField(canBeNull = true, foreign = true)
	private Paper paper;
	@DatabaseField
	private String theme;

	public ThemeString() {

	}

	public ThemeString(String theme) {
		this.theme = theme;
	}

}
