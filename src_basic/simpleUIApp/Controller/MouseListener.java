package simpleUIApp.Controller;

import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.simpleUI.KeyPress;
import fr.ubordeaux.simpleUI.MouseHandler;
import simpleUIApp.Items.Item;
import simpleUIApp.Items.Planet;

/**
 * The mouseListener.
 */
public class MouseListener implements MouseHandler<Item> {

    /**
     * Represent a dragList that contains items dragged.
     */
	ArrayList<Item> dragList = new ArrayList<Item>();;

    /**
     * The mouseListener constructor.
     */
	public MouseListener() {
		super();
	}

    /**
     * The implemented method of {@link MouseHandler}.
     * @param items is the list of items available at the click position.
     * @param keyPress Represent the key pressed.
     */
	@Override
	public void mouseClicked(List<Item> items, KeyPress keyPress) {
		System.out.println("Select " + items + " " + keyPress);
		for (Item testItem : items) {
			System.out.println("Mouse click " + testItem);
		}
	}

    /**
     * The implemented method of {@link MouseHandler}.
     * @param items is the list of items available at the click position.
     * @param keyPress Represent the key pressed.
     */
	@Override
	public void mouseDrag(List<Item> items, KeyPress keyPress) {
		dragList = new ArrayList<Item>(items);
		System.out.println("Drag :" + dragList);
	}

    /**
     * The implemented method of {@link MouseHandler}.
     * @param items is the list of items available at the click position.
     * @param keyPress Represent the key pressed.
     */
	@Override
	public void mouseDragging(List<Item> items, KeyPress keyPress) {
		if (!items.isEmpty())
			System.out.println("Dragging :" + items);
	}

    /**
     * The implemented method of {@link MouseHandler}.
     * @param items is the list of items available at the click position.
     * @param keyPress Represent the key pressed.
     */
	@Override
	public void mouseDrop(List<Item> items, KeyPress keyPress) {
		System.out.println("Drag& Drop :" + dragList + " => " + items + " using " + keyPress.toString());
		if (!items.isEmpty()) {
			for (Item item : dragList) {
				if(item instanceof Planet) {
					if (((Planet) item).getType() != Planet.Type.IA)
						item.setObjective(items.get(0));
				} else
					item.setObjective(items.get(0));
			}
		}
	}

    /**
     * The implemented method of {@link MouseHandler}.
     * @param items is the list of items available at the click position.
     * @param keyPress Represent the key pressed.
     */
	@Override
	public void mouseOver(List<Item> items, KeyPress keyPress) {
		// TODO Auto-generated method stub

	}

    /**
     * The implemented method of {@link MouseHandler}.
     * @param items is the list of items available at the click position.
     * @param keyPress Represent the key pressed.
     */
	@Override
	public void mouseWheelMoved(List<Item> items, KeyPress keyPress, int offset_wheel) {

		for(Item item : items)
			if(item instanceof Planet){
				if(((Planet) item).getType() != Planet.Type.IA || ((Planet) item).getType() != Planet.Type.NEUTRAL){
					Planet planet = (Planet) item;

					switch (keyPress) {
						case UNKNOWN:
							planet.incrementPercentageToSend(offset_wheel);
							break;

						case CRTL:
							planet.incrementPercentageToSend(offset_wheel * 10);
							break;


					}
				}
			}
		System.out.println(items + " using " + keyPress.toString() + " wheel rotate " + offset_wheel);
	}
}
