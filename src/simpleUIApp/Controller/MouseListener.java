package simpleUIApp.Controller;

import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.simpleUI.KeyPress;
import fr.ubordeaux.simpleUI.MouseHandler;
import simpleUIApp.Items.Item;
import simpleUIApp.Items.Planet;

public class MouseListener implements MouseHandler<Item> {

	ArrayList<Item> dragList = new ArrayList<Item>();;

	public MouseListener() {
		super();
	}

	@Override
	public void mouseClicked(List<Item> items, KeyPress keyPress) {
		System.out.println("Select " + items + " " + keyPress);
		for (Item testItem : items) {
			System.out.println("Mouse click " + testItem);
		}
	}

	@Override
	public void mouseDrag(List<Item> items, KeyPress arg1) {
		dragList = new ArrayList<Item>(items);
		System.out.println("Drag :" + dragList);
	}

	@Override
	public void mouseDragging(List<Item> items, KeyPress keyPress) {
		if (!items.isEmpty())
			System.out.println("Dragging :" + items);
	}

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

	@Override
	public void mouseOver(List<Item> items, KeyPress keyPress) {
		// TODO Auto-generated method stub

	}

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
