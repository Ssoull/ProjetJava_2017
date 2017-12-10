package simpleUIApp.Controller;

import javax.swing.JFrame;

import fr.ubordeaux.simpleUI.KeyHandler;

/**
 * The keyListner class.
 */
public class KeyListener implements KeyHandler {

	/**
	 * The window of the game.
	 */
	private JFrame mFrame;

	/**
	 * The keyListener constructor.
	 * @param frame The window of the game.
	 */
	public KeyListener(JFrame frame) {
		mFrame = frame;
	}

	/**
	 * The implemented method of {@link KeyHandler}.
	 * @return Return the window object.
	 */
	@Override
	public JFrame getParentFrame() {
		return mFrame;
	}

	/**
	 * The implemented method of {@link KeyHandler}.
	 * @param key is the character associated with the key in this event. For
	 *            example, the KEY_TYPED event for shift + "a" returns the value for
	 *            "A". It corresponds to the Unicode character defined for this key
	 *            event. If no valid Unicode character exists for this key event,
	 *            CHAR_UNDEFINED is returned.
	 */
	@Override
	public void keyPressed(char key) {

	}

	/**
	 * The implemented method of {@link KeyHandler}.
	 * @param key is the character associated with the key in this event. For
	 *            example, the KEY_TYPED event for shift + "a" returns the value for
	 *            "A". It corresponds to the Unicode character defined for this key
	 *            event. If no valid Unicode character exists for this key event,
	 *            CHAR_UNDEFINED is returned.
	 */
	@Override
	public void keyReleased(char key) {

	}

	/**
	 * The implemented method of {@link KeyHandler}.
	 * @param key is the character associated with the key in this event. For
	 *            example, the KEY_TYPED event for shift + "a" returns the value for
	 *            "A". It corresponds to the Unicode character defined for this key
	 *            event. If no valid Unicode character exists for this key event,
	 *            CHAR_UNDEFINED is returned.
	 */
	@Override
	public void keyTyped(char key) {
		switch (key) {
		case 's':
			Run.savedPlanetAndSpaceShips();
			break;
		case 'l':
			Run.loadPlanetAndSpaceShips();
			break;

		default:
			// do nothing
			break;
		}
	}

}
