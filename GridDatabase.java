/*
 * File: GridDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * Grid application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.util.*;

public class GridDatabase implements GridConstants {

	/**
	 * Constructor
	 * This method takes care of any initialization needed for
	 * the database.
	 */
	public GridDatabase() {}


	/**
	 * This method adds the given profile to the database.  If the
	 * name associated with the profile is the same as an existing
	 * name in the database, the existing profile is replaced by
	 * the new profile passed in.
	 */
	public void addProfile(GridProfile profile) {
		String name = profile.getName();
		if (database.containsKey(name)) {
			database.remove(name);
			database. put(name, profile);
		} else {
			database. put(name, profile);
		}
	}


	/**
	 * This method returns the profile associated with the given name
	 * in the database.  If there is no profile in the database with
	 * the given name, the method returns null.
	 */
	public GridProfile getProfile(String name) {
		if (database.containsKey(name)) {
			return database.get(name);
		} else {
			return null;
		}
	}


	/**
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 *
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		if (database.containsKey(name)) {
			GridProfile removed = database.get(name);
			Iterator<String> it = removed.getFriends();
			while (it.hasNext()) {
				String friendOfRemoved = it.next();
				GridProfile removedFriendProfile = database.get(friendOfRemoved);
				removedFriendProfile.removeFriend(name);
			}
			database.remove(name);
		}
	}


	/**
	 * This method returns true if there is a profile in the database
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		if(database.containsKey(name)) {
			return true;
		} else {
			return false;
		}
	}

	/* The hashmap that holds all profiles with unique names as keys. Note that this hashmap is
	 * an instance variable, as it is interacted with in the class' methods. */
	private Map<String, GridProfile> database = new HashMap<String, GridProfile>();

}
