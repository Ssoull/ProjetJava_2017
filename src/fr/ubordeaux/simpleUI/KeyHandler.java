package fr.ubordeaux.simpleUI;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

/**
 * The KeyHandler which will be called by the {@link Arena} in case of key
 * events such as key pressed, released or typed
 */
public interface KeyHandler {

	/**
	 * returns the {@link JFrame} which will be listening for {@link KeyEvent}.
	 * 
	 * @return a {@link JFrame}
	 */
	public JFrame getParentFrame();

	/**
	 * keyPressed is called when a key is pressed.
	 * 
	 * @param key
	 *            is the character associated with the key in this event. For
	 *            example, the KEY_TYPED event for shift + "a" returns the value for
	 *            "A". It corresponds to the Unicode character defined for this key
	 *            event. If no valid Unicode character exists for this key event,
	 *            CHAR_UNDEFINED is returned.
	 */
	public void keyPressed(char key);

	/**
	 * keyReleased is called when a key is released.
	 * 
	 * @param key
	 *            is the character associated with the key in this event. For
	 *            example, the KEY_TYPED event for shift + "a" returns the value for
	 *            "A". It corresponds to the Unicode character defined for this key
	 *            event. If no valid Unicode character exists for this key event,
	 *            CHAR_UNDEFINED is returned.
	 */
	public void keyReleased(char key);

	/**
	 * keyTyped is called when a key has been typed.
	 * 
	 * @param key
	 *            is the character associated with the key in this event. For
	 *            example, the KEY_TYPED event for shift + "a" returns the value for
	 *            "A". It corresponds to the Unicode character defined for this key
	 *            event. If no valid Unicode character exists for this key event,
	 *            CHAR_UNDEFINED is returned.
	 */
	public void keyTyped(char key);
}