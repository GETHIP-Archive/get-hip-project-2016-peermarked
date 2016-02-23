package com.gallup.gethip.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.gallup.gethip.helpers.Link;
import com.gallup.gethip.helpers.ThemeString;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Matthew Meacham
 * 
 *         Represents a paper
 *
 */
@XmlRootElement
@DatabaseTable(tableName = "papers")
public class Paper {

	@DatabaseField(id = true, columnName = "id")
	private long id;
	@DatabaseField(columnName = "author")
	private String author;
	@DatabaseField(columnName = "title")
	private String title;
	@DatabaseField(columnName = "content")
	private String content;
	@DatabaseField(columnName = "created")
	private Date created;

	// TODO Fix
	// @DatabaseField
	// private List<String> themes = new ArrayList<String>();
	// @DatabaseField
	// private Map<Long, Comment> comments = new HashMap<Long, Comment>();
	// @DatabaseField
	// private List<Link> links = new ArrayList<Link>();

	@ForeignCollectionField(eager = false)
	Collection<ThemeString> themes = new ArrayList<ThemeString>();
	@ForeignCollectionField(eager = false)
	Collection<Comment> comments = new ArrayList<Comment>();
	@ForeignCollectionField(eager = false)
	Collection<Link> links = new ArrayList<Link>();

	public Paper() {

	}

	public Paper(String author, String title, String content, List<String> themes) {
		this.author = author;
		this.title = title;
		this.content = content;
		this.created = new Date();
		this.setThemes(themes);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	// Ignore for XML conversions (and JSON)
	@XmlTransient
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	// Ignore for XML conversions (and JSON)
	@XmlTransient
	public Collection<ThemeString> getThemes() {
		return themes;
	}

	public void setThemes(List<String> themes) {
		List<ThemeString> themeStrings = new ArrayList<ThemeString>();
		themes.forEach(e -> themeStrings.add(new ThemeString(e)));
		this.themes = themeStrings;
	}

	public void addTheme(String theme) {
		themes.add(new ThemeString(theme));
	}

	// Ignore for XML conversions (and JSON)
	@XmlTransient
	public Collection<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void addLink(String url, String rel) {
		// FIXME fix this
		Link link = new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
	}

}
