/*
 * File: Grid.java
 *
 * Umutcan Golbasi and Eglen Galindo
 * -----------------------
 * This program implements a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import acm.io.*;
import java.awt.event.*;
import javax.swing.*;

public class Grid extends Program
					implements GridConstants {

	/**
	 * This method has the responsibility for initializing the
	 * interactors in the application, and taking care of any other
	 * initialization that needs to be performed.
	 */
	public void init() {
		initializeNorth();
		initializeWest();
		addActionListeners(); //for interactors
		add(canvas); //the GridCanvas
    }

	/*
	 * Initializes the interactors on the northern part of the program.
	 */

	private void initializeNorth() {
		JLabel nameLabel = new JLabel("Name");
		nameText = new JTextField(TEXT_FIELD_SIZE);
		addProfile = new JButton("Add");
		deleteProfile = new JButton("Delete");
		lookupProfile = new JButton("Lookup");
		add(nameLabel, NORTH);
		add(nameText, NORTH);
		add(addProfile, NORTH);
		add(deleteProfile, NORTH);
		add(lookupProfile, NORTH);
	}

	/*
	 * Initializes the interactors on the western part of the program.
	 */

	private void initializeWest() {
		chStatusText = new JTextField(TEXT_FIELD_SIZE);
		chStatusText.addActionListener(this);
		chStatusButton = new JButton("Change Status");
		chPictureText = new JTextField(TEXT_FIELD_SIZE);
		chPictureText.addActionListener(this);
		chPictureButton = new JButton("Change Picture");
		addFriendText = new JTextField(TEXT_FIELD_SIZE);
		addFriendText.addActionListener(this);
		addFriendButton = new JButton("Add Friend");
		add(chStatusText, WEST);
		add(chStatusButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(chPictureText, WEST);
		add(chPictureButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(addFriendText, WEST);
		add(addFriendButton, WEST);
	}


    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {

    	String enteredName = nameText.getText(); //stores the entry in the name text field

    	if (e.getSource() == addProfile && !enteredName.equals("")) { //add button (no empty string)
    		if (database.containsProfile(enteredName)) { //checks if the profile already exists
    			currentProfile = new GridProfile(enteredName);
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("A profile with the name " + enteredName + " already exists");
    		} else { //adds the profile to the database and displays it
    			currentProfile = new GridProfile(enteredName);
    			database.addProfile(currentProfile);
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("New profile created");
    		}

    	} else if (e.getSource() == deleteProfile && !enteredName.equals("")) { //delete button (no empty string)
    		canvas.removeAll();
    		currentProfile = null;
    		if (database.containsProfile(enteredName)) { //checks if the profile exists
    			database.deleteProfile(enteredName);
    			canvas.showMessage("Profile of " + enteredName + " deleted");
    		} else {
    			canvas.showMessage("A profile with name " + enteredName + " does not exist");
    		}

    	} else if (e.getSource() == lookupProfile && !enteredName.equals("")) { //lookup button (no empty string)
    		canvas.removeAll();
    		if (database.containsProfile(enteredName)) { //checks if the profile already exists
    			currentProfile = database.getProfile(enteredName);
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("Displaying " + enteredName);
    		} else {
    			canvas.showMessage("A profile with name " + enteredName + " does not exist");
    			currentProfile = null;
    		}

    	} else if ((e.getSource() == chStatusButton || e.getSource() == chStatusText) //change status
    											&& !chStatusText.getText().equals("")) { //no empty string
    		String status = chStatusText.getText();
    		if (currentProfile == null) { //checks if a profile is selected
    			canvas.showMessage("Please select a profile to change status");
    		} else { //updates the status of a selected profile
    			currentProfile.setStatus(currentProfile.getName() + " is " + status);
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("Status updated to " + status);
    		}

    	} else if ((e.getSource() == chPictureButton || e.getSource() == chPictureText) //change picture
    											&& !chPictureText.getText().equals("")) { //no empty string
    		String pictureFile = chPictureText.getText();
    		if (currentProfile == null) { //checks if a profile is selected
    			canvas.showMessage("Please select a profile to change picture");
    		} else {
    			GImage image = null;
    			try { //checks if the filename exists
    				image = new GImage(pictureFile);
    				currentProfile.setImage(image);
    			} catch (ErrorException ex) {
    				image = null;
    			}
    			canvas.displayProfile(currentProfile);
    			if (image == null) {
    				canvas.showMessage("Unable to open file: " + pictureFile);
    			} else {
    				canvas.showMessage("Picture updated");
    			}
    		}


    	} else if ((e.getSource() == addFriendButton || e.getSource() == addFriendText) //add friend
    											&& !addFriendText.getText().equals("")) { //no empty string
    		if (currentProfile == null) { //checks if a profile is selected
    			canvas.showMessage("Please select a profile to add friend");
    		} else {
    			String friendName = addFriendText.getText();
    			if (currentProfile.getName().equals(friendName)) { //checks if the person is trying to add themselves
    				canvas.showMessage("A person cannot add themselves as friend");
    			} else if (database.containsProfile(friendName) == false) { //checks if the added friend is in the database
    				canvas.showMessage(friendName + " does not exist");
    			} else {
    				GridProfile friendProfile = database.getProfile(friendName);
    				if (currentProfile.addFriend(friendName) == false) { //checks if the couple is already friends
    					canvas.showMessage(currentProfile.getName() + " already has " + friendName + " as a friend");
    				} else { //successfully adds the friend after all the tests
    					currentProfile.addFriend(friendName);
    					friendProfile.addFriend(currentProfile.getName());
    					canvas.displayProfile(currentProfile);
    					canvas.showMessage(friendName + " added as a friend");
    				}
    			}
    		}
    	}
    }

    /* Private instance variables */

    private JTextField nameText;
    private JTextField chStatusText;
    private JTextField chPictureText;
    private JTextField addFriendText;
    private JButton addProfile;
    private JButton deleteProfile;
    private JButton lookupProfile;
    private JButton chStatusButton;
    private JButton chPictureButton;
    private JButton addFriendButton;
    private GridDatabase database = new GridDatabase();
    private GridCanvas canvas = new GridCanvas();
    private GridProfile currentProfile = null; //the profile that is displayed/selected

}
