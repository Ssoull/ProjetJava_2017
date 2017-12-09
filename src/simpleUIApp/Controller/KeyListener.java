package simpleUIApp.Controller;

import javax.swing.JFrame;

import fr.ubordeaux.simpleUI.KeyHandler;
import simpleUIApp.Items.Planet;

public class KeyListener implements KeyHandler {

	private JFrame mFrame;

	public KeyListener(JFrame frame) {
		mFrame = frame;
	}

	@Override
	public JFrame getParentFrame() {
		return mFrame;
	}

	@Override
	public void keyPressed(char key) {

	}

	@Override
	public void keyReleased(char key) {

	}

	@Override
	public void keyTyped(char key) {
		switch (key) {
		case 's':
			Planet.savedPlanetAndSpaceShips();
			break;
		case 'l':
			Planet.loadPlanetAndSpaceShips();
			break;

		default:
			// do nothing
			break;
		}
	}

}
