package fr.ubordeaux.simpleUI;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;

/**
 * Any class implementing the interface ItemManager should provide for any
 * instance of type <i>I</i> the implementation of four methods that any item
 * that should be handled by the Arena have to defined.
 * 
 * @param <I>
 *            type of elements to be handle
 */
public interface ItemManager<I> {

	/**
	 * Returns if the item of type <i>I</i> contains the location
	 * 
	 * @param location
	 *            a {@link Point2D} which one want to know if it is contained in the
	 *            item
	 * @param item
	 *            an <i>I</i> in which the location may or not be contained
	 * @return <code>true</code> if item contains location; <code>false</code>
	 *         otherwise
	 */
	public boolean contains(Point2D location, I item);

	/**
	 * Returns if a given item intersect with a given selection. Essentially used
	 * for range selection purpose
	 * 
	 * @param selection
	 *            a {@link Shape} that may intersect or not with the item
	 * @param item
	 *            an <i>I</i> that may intersect or not with the selection
	 * @return <code>true</code> if item intersects location; <code>false</code>
	 *         otherwise
	 */
	public boolean intersects(Shape selection, I item);

	/**
	 * Returns if a given item is contained into a given selection. Essentially used
	 * for range selection purpose
	 * 
	 * @param selection
	 *            a {@link Shape} that may contained or not the item
	 * @param item
	 *            an <i>I</i> that may be contained or not into the selection
	 * @return <code>true</code> if item is contained into selection;
	 *         <code>false</code> otherwise
	 */
	public boolean isContained(Shape selection, I item);

	/**
	 * Renders the item as a graphical element in the {@link Graphics2D} graphics
	 * 
	 * @param graphics
	 *            the graphical container of the rendering of item
	 * @param item
	 *            the <i>I</i> to graphically render
	 */
	public void draw(Graphics2D graphics, I item);
}