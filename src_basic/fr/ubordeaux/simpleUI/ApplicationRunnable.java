package fr.ubordeaux.simpleUI;

import java.util.Collection;

/**
 * The ApplicationRunnable interface should be implemented by any class whose
 * instances are intended to be executed in an {@link Application}. The class
 * must define a method called run which takes an {@link Arena} and a
 * {@link Collection} of items of type <i>I</i>.
 * 
 * @param <I>
 *            type of the elements handled by the {@link Arena}
 */
public interface ApplicationRunnable<I> {
	/**
	 * called by {@link Application} as the main routine of this last which aims is
	 * to handle the {@link Arena} and its Collection of items.
	 * 
	 * @param arenaModel
	 *            An {@link Arena} of <i>I</i> which will be created by the
	 *            {@link Application} when its method <i>run</i> will be called.
	 * @param itemCollection
	 *            A Collection of items which will be used by the {@link Arena} for
	 *            drawing purpose.
	 */
	public void run(Arena<I> arenaModel, Collection<I> itemCollection);
}