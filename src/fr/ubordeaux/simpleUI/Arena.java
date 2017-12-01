package fr.ubordeaux.simpleUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * An Arena is a simplified way of handling graphical elements for Java project
 * at the University of Bordeaux based on fr.umlv.remix project. The Arena
 * handle the rendering of a collection of items of type <i>I</i> which should
 * be provided with an {@link ItemManager}. The Arena reacts to mouse events by
 * calling {@link MouseHandler} methods with some useful parameters such as a
 * {@link List} of items of type <i>I</i> which are in the range of the mouse
 * action.
 * 
 * @param <I>
 *            type of elements to be handle
 */
public class Arena<I> {
	final Collection<I> itemCollection;
	final ItemManager<I> itemManager;
	private final CopyOnWriteArrayList<ArenaComponent> listeners = new CopyOnWriteArrayList<ArenaComponent>();

	/**
	 * Creates a new Arena which will handle a {@link Collection} of items using an
	 * {@link ItemManager}
	 * 
	 * @param itemCollection
	 *            the {@link Collection} of items to handle
	 * @param itemManager
	 *            the {@link ItemManager} used to handle the items of itemCollection
	 */
	Arena(Collection<I> itemCollection, ItemManager<I> itemManager) {
		this.itemCollection = itemCollection;
		this.itemManager = itemManager;
	}

	/**
	 * Cleans the Arena and redraws all the items of the {@link Collection} in the
	 * order of iteration of this last
	 */
	public void refresh() {
		Application.checkEDT();
		internalRefresh();
	}

	void internalRefresh() {
		for (ArenaComponent component : listeners)
			component.repaint();
	}

	/**
	 * Method which creates the graphical component to add to a {@link JFrame} in
	 * order to render the application.
	 * 
	 * @param width
	 *            width in pixels of the interface
	 * @param height
	 *            height in pixels of the interface
	 * @param mouseHandler
	 *            the {@link MouseHandler} which will be called by the Arena in case
	 *            of mouse events such as mouse click, drag and drop or mouse wheel
	 *            move.
	 * @param keyHandler
	 *            the {@link KeyHandler} which will be called by the arena is the
	 *            user press/release keys of the keyboards.
	 * @return a {@link JComponent} to add to a {@link JFrame}
	 */
	public JComponent createComponent(int width, int height, final MouseHandler<I> mouseHandler,
			final KeyHandler keyHandler) {
		final ArenaComponent component = new ArenaComponent();
		component.setPreferredSize(new Dimension(width, height));
		listeners.add(component);

		class MouseManager extends MouseAdapter implements MouseMotionListener {

			Point dragOrigin = null;
			boolean newDrag = false;

			private ArrayList<I> returnListOfElements(Point mouseLocation) {
				ArrayList<I> list = new ArrayList<I>();
				for (I item : itemCollection) {
					if (itemManager.contains(mouseLocation, item)) {
						list.add(item);
					}
				}
				return list;
			}

			private KeyPress getKey(MouseEvent e) {
				return (e.isAltGraphDown() ? KeyPress.ALTGR
						: (e.isControlDown() ? KeyPress.CRTL : (e.isShiftDown() ? KeyPress.SHIFT : KeyPress.UNKNOWN)));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Point mouseLocation = e.getPoint();
				mouseHandler.mouseClicked(returnListOfElements(mouseLocation), getKey(e));
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				Point mouseLocation = e.getPoint();
				if (newDrag) {
					mouseHandler.mouseDrag(returnListOfElements(dragOrigin), getKey(e));
					newDrag = false;
				}
				mouseHandler.mouseDragging(returnListOfElements(mouseLocation), getKey(e));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Point mouseLocation = e.getPoint();
				dragOrigin = mouseLocation;
				newDrag = true;
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				Point mouseLocation = e.getPoint();
				ArrayList<I> overItems = returnListOfElements(mouseLocation);
				mouseHandler.mouseOver(overItems, getKey(e));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (!newDrag) {
					Point mouseLocation = e.getPoint();
					mouseHandler.mouseDrop(returnListOfElements(mouseLocation), getKey(e));
				}
			}

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				Point mouseLocation = e.getPoint();
				mouseHandler.mouseWheelMoved(returnListOfElements(mouseLocation), getKey(e), e.getWheelRotation());
			}

		}
		class KeyManager extends KeyAdapter {
			@Override
			public void keyPressed(KeyEvent e) {
				keyHandler.keyPressed(e.getKeyChar());
			}

			@Override
			public void keyReleased(KeyEvent e) {
				keyHandler.keyReleased(e.getKeyChar());
			}

			@Override
			public void keyTyped(KeyEvent e) {
				keyHandler.keyTyped(e.getKeyChar());
			}
		}
		KeyManager keyManager = new KeyManager();

		MouseManager mouseManager = new MouseManager();
		component.addMouseListener(mouseManager);
		component.addMouseWheelListener(mouseManager);
		component.addMouseMotionListener(mouseManager);
		keyHandler.getParentFrame().addKeyListener(keyManager);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(component);
		return panel;
	}

	class ArenaComponent extends JComponent {
		private Rectangle selection;
		private static final long serialVersionUID = -3577754646135073086L;

		public void setSelection(Rectangle selection) {
			this.selection = selection;
			internalRefresh();
		}

		@Override
		protected void paintComponent(Graphics g) {

			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.BLACK);
			for (I item : itemCollection) {
				itemManager.draw(g2, item);
			}

			// draw selection // Not usable with drag and drops
			if (selection == null)
				return;
			g2.setColor(Color.black);
			g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_MITER,
					new float[] { 10, 10 }, 5));
			g2.draw(selection);
		}
	}
}