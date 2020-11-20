/*
 * File: GridCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class GridCanvas extends GCanvas
					implements GridConstants {

	/**
	 * Constructor
	 * This method takes care of any initialization needed for
	 * the display
	 */
	public GridCanvas() {}

	/**
	 * This method displays a message string near the bottom of the
	 * canvas.  Every time this method is called, the previously
	 * displayed message (if any) is replaced by the new message text
	 * passed in.
	 */
	public void showMessage(String msg) { //the message is an instance variable always on the screen
		message.setLabel(msg); //instead of adding and deleting, only the label of the message changes
		message.setFont(MESSAGE_FONT);
		double x = (getWidth() / 2) - (message.getWidth() / 2);
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		add(message, x, y);
	}

	/**
	 * This method displays the given profile on the canvas.  The
	 * canvas is first cleared of all existing items (including
	 * messages displayed near the bottom of the screen) and then the
	 * given profile is displayed.  The profile display includes the
	 * name of the user from the profile, the corresponding image
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(GridProfile profile) {
		removeAll();
		displayName(profile.getName());
		displayImage(profile.getImage());
		displayStatus(profile.getStatus());
		displayFriends(profile.getFriends());
	}

	/*
	 * Displays the name of a profile in the correct place and font.
	 */

	private void displayName(String name) {
		GLabel nameLabel = new GLabel(name);
		nameLabel.setFont(PROFILE_NAME_FONT);
		nameLabel.setColor(Color.BLUE);
		nameAscent = nameLabel.getAscent();
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + nameAscent;
		add(nameLabel, x, y);
	}

	/*
	 * Displays the image of the profile in the correct place. If there is no image,
	 * it displays the appropriate box. The image is also bounded by this box.
	 */

	private void displayImage(GImage image) {
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + nameAscent + IMAGE_MARGIN;
		if (image != null) {
			image.setBounds(x, y, IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image, x, y);
		} else {
			GRect imageBox = new GRect(x, y, IMAGE_WIDTH, IMAGE_HEIGHT);
			add(imageBox);
			GLabel noImage = new GLabel("No Image");
			noImage.setFont(PROFILE_IMAGE_FONT);
			x += (IMAGE_WIDTH / 2) - (noImage.getWidth()/2);
			y += (IMAGE_HEIGHT / 2);
			add(noImage, x, y);
		}
	}

	/*
	 * Displays the status in the proper place and font.
	 */

	private void displayStatus(String status) {
		GLabel statusLabel = new GLabel(status);
		statusLabel.setFont(PROFILE_STATUS_FONT);
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + nameAscent + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + statusLabel.getHeight();
		if (getElementAt(x, y) != null) {
			remove(getElementAt(x, y));
		}
		add(statusLabel, x, y);
	}

	/*
	 * Displays the friend list on the proper place on the canvas.
	 */

	private void displayFriends(Iterator<String> friends) {
		GLabel friendsLabel = new GLabel("Friends: ");
		friendsLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		double x = getWidth() / 2;
		double y = TOP_MARGIN + nameAscent;
		add(friendsLabel, x, y);
		Iterator<String> it = friends;
		for (int i = 1; it.hasNext(); i++) {
			GLabel friendNameLabel = new GLabel(it.next());
			friendNameLabel.setFont(PROFILE_FRIEND_FONT);
			y = y + (10 * i);
			add (friendNameLabel, x, y);
		}
	}

	/* Private instance variables. */

	private double nameAscent; //used for calculations
	private GLabel message = new GLabel(""); //always on the screen

}
