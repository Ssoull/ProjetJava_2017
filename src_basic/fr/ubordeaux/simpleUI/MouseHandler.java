package fr.ubordeaux.simpleUI;

import java.util.List;

/**
 * The MouseHandler which will be called by the {@link Arena} in case of mouse
 * events such as mouse click, drag and drop or mouse wheel move
 *
 * @param <I>
 *            type of elements to be handle
 */
public interface MouseHandler<I> {
	/**
	 * mouseClicked is called when a mouse click has been done.
	 * 
	 * @param items
	 *            is the list of items available at the click position
	 * @param key
	 *            is the key pressed during the drag and drop from "CTRL", "SHIFT",
	 *            "ALT GR" and "UNKOWN" (default)
	 */
	public void mouseClicked(List<I> items, KeyPress key);

	/**
	 * mouseDrag is called at start of a drag and drop event.
	 * 
	 * @param itemsDrag
	 *            is the list of items available at the drag position
	 * @param key
	 *            is the key pressed during the drag and drop from "CTRL", "SHIFT",
	 *            "ALT GR" and "UNKOWN" (default)
	 */
	public void mouseDrag(List<I> itemsDrag, KeyPress key);

	/**
	 * mouseDragging is called during a drag and drop.
	 * 
	 * @param itemsDragging
	 *            is the list of items available at the current position
	 * @param key
	 *            is the key pressed during the drag and drop from "CTRL", "SHIFT",
	 *            "ALT GR" and "UNKOWN" (default)
	 */
	public void mouseDragging(List<I> itemsDragging, KeyPress key);

	/**
	 * mouseDrop is called when a drag and drop has been done.
	 * 
	 * @param itemsDrop
	 *            is the list of items available at the drop position
	 * @param key
	 *            is the key pressed during the drag and drop from "CTRL", "SHIFT",
	 *            "ALT GR" and "UNKOWN" (default)
	 */
	public void mouseDrop(List<I> itemsDrop, KeyPress key);

	/**
	 * mouseOver is called with the elements available at the current position.
	 * 
	 * @param itemsOver
	 *            is the list of items available at the current position
	 * @param key
	 *            is the key currently pressed from "CTRL", "SHIFT", "ALT GR" and
	 *            "UNKOWN" (default)
	 */
	public void mouseOver(List<I> itemsOver, KeyPress key);

	/**
	 * mouseWheelMoved is called when a mouse wheel moved has been done.
	 * 
	 * @param items
	 *            is the list of items available at the click position
	 * @param key
	 *            is the key pressed during the drag and drop from "CTRL", "SHIFT",
	 *            "ALT GR" and "UNKOWN" (default)
	 * @param wheelRotation
	 *            either -1 or 1 depending on the direction of the rotation of the
	 *            mouse wheel
	 */
	public void mouseWheelMoved(List<I> items, KeyPress key, int wheelRotation);
}