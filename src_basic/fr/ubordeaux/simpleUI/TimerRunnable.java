package fr.ubordeaux.simpleUI;

/**
 * The TimerRunnable interface should be implemented by any class whose
 * instances are intended to be executed in the <i>run</i> method of an
 * {@link Application}. The class must define a method called run which takes a
 * {@link TimerTask}.
 * 
 */
public interface TimerRunnable {
	/**
	 * called by {@link Application} in its <i>timer</i> method at a fixed delay.
	 * 
	 * @param timerTask
	 *            a reference to the {@link TimerTask} corresponding to the actual
	 *            task that can be used to cancel this last.
	 */
	public void run(TimerTask timerTask);
}