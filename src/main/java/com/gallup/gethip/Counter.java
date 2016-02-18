package com.gallup.gethip;

import com.sun.jersey.spi.resource.Singleton;

/**
 * 
 * @author Matthew Meacham
 * 
 *         The point of this class is to avoid a request to the database to receive its size every time that a new user or paper is created, this will reduce the overhead associated with creation
 *
 */
@Singleton
public class Counter {

	private static int numberOfProfiles = 0;
	private static int numberOfPapers = 0;

	public Counter() {

	}

	public static int getNextProfileId() {
		numberOfProfiles++;
		return numberOfProfiles;
	}

	public static int getNextPaperId() {
		numberOfPapers++;
		return numberOfPapers;
	}

	public static int getNumberOfProfiles() {
		return numberOfProfiles;
	}

	public static int getNumberOfPapers() {
		return numberOfPapers;
	}

}
