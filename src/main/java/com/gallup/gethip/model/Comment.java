package com.gallup.gethip.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Matthew Meacham
 * 
 *         Represents a comment to a paper
 *
 */
@XmlRootElement
@DatabaseTable(tableName = "comments")
public class Comment {

	@DatabaseField(id = true, columnName = "id")
	private long id;
	@DatabaseField(columnName = "comment")
	private String comment;
	@DatabaseField(columnName = "author")
	private String author;
	@DatabaseField(columnName = "created")
	private Date created;
	@DatabaseField(canBeNull = true, foreign = true)
	private Paper paper;

	public Comment() {

	}

	public Comment(long id, String comment, String author) {
		this.id = id;
		this.comment = comment;
		this.author = author;
		this.created = new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
