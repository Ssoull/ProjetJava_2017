package fr.ubordeaux.simpleUI;

import javax.swing.Timer;

/**
 * A task which is executed at fixed delay until it has been canceled by calling
 * its {@link #cancel()} method
 *
 */
public class TimerTask {
	private final Timer timer;

	TimerTask(Timer timer) {
		this.timer = timer;
	}

	public void cancel() {
		timer.stop();
	}
}