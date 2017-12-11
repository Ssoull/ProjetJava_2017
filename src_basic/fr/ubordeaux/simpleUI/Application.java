package fr.ubordeaux.simpleUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Collection;

import javax.swing.Timer;

/**
 * An Application is a thread of execution which will handle simultaneously the
 * rendering of graphical elements, mouse handling and executing delayed
 * instructions. The only way to use an Application is to call its run method.
 * The use of concurrent thread is forbidden. In order to execute concurrent
 * instructions, one has to call in the run method the timer method.
 * 
 */
public class Application {
	private Application() {
		// static helper
	}

	static void checkEDT() {
		if (!EventQueue.isDispatchThread())
			throw new IllegalStateException("this method must be called in run()");
	}

	/**
	 * Main routine in order to create an area for painting elements of type
	 * <i>I</i>. It will create an {@link Arena} using the Collection of items
	 * <i>itemCollection</i> and the {@link ItemManager} <i>itemManager</i> and then
	 * call the method <i>run</i> of the {@link ApplicationRunnable}
	 * <i>runnable</i>.
	 * 
	 * @param <I>
	 *            type of elements to paint
	 * @param itemCollection
	 *            collection of the elements to paint. At each invocation of the
	 *            refresh() method the collection will be iterate and the elements
	 *            will be drawn in the order of iteration. In order to assure an
	 *            order of painting you will have to use an ordered implementation
	 *            of Collection.
	 * @param itemManager
	 *            an instance of an implementation of {@link ItemManager}
	 * @param runnable
	 *            an instance of an implementation of {@link ApplicationRunnable}
	 */
	public static <I> void run(final Collection<I> itemCollection, final ItemManager<I> itemManager,
			final ApplicationRunnable<I> runnable) {

		try {
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					Arena<I> arena = new Arena<I>(itemCollection, itemManager);
					runnable.run(arena, itemCollection);
				}
			});
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			if (cause instanceof RuntimeException) {
				throw (RuntimeException) cause;
			}
			if (cause instanceof Error) {
				throw (Error) cause;
			}
			throw new UndeclaredThrowableException(e);
		}
	}

	/**
	 * The only way to execute some instructions in your {@link Application} at a
	 * fixed delay.
	 * 
	 * @param delay
	 *            the delay in milliseconds
	 * @param runnable
	 *            an instance of an implementation of {@link TimerRunnable}
	 */
	public static void timer(long delay, final TimerRunnable runnable) {
		if (delay < 1 || delay > Integer.MAX_VALUE)
			throw new IllegalArgumentException("delay not in range " + delay);

		Timer timer = new Timer((int) delay, null);
		final TimerTask timerTask = new TimerTask(timer);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runnable.run(timerTask);
			}
		});
		timer.start();
	}
}