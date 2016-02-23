package com.gallup.gethip.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Matthew Meacham
 * 
 *         Represents a profile
 *
 */
@XmlRootElement
@DatabaseTable(tableName = "profiles")
public class Profile {

	@DatabaseField(id = true, columnName = "id")
	private long id;
	@DatabaseField(columnName = "profile_name", unique = true)
	private String profileName;
	@DatabaseField(columnName = "first_name")
	private String firstName;
	@DatabaseField(columnName = "last_name")
	private String lastName;
	@DatabaseField(columnName = "password")
	private String password;
	@DatabaseField(columnName = "salt")
	private String salt;
	@DatabaseField(columnName = "created")
	private Date created;
	@DatabaseField(columnName = "score")
	private int score;

	public Profile() {

	}

	public Profile(long id, String profileName, String firstName, String lastName, String password, int score) {
		this.id = id;
		this.profileName = profileName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.score = score;
		this.created = new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	// Obviously don't send this with a response
	@XmlTransient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlTransient
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
