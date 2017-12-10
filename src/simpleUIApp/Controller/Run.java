package simpleUIApp.Controller;

import javax.swing.*;

import fr.ubordeaux.simpleUI.Application;
import fr.ubordeaux.simpleUI.ApplicationRunnable;
import fr.ubordeaux.simpleUI.Arena;
import fr.ubordeaux.simpleUI.TimerRunnable;
import fr.ubordeaux.simpleUI.TimerTask;
import simpleUIApp.Items.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class Run implements ApplicationRunnable<Item> {

	private int width;
	private int height;

	public Run(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void run(final Arena<Item> itemsArena, Collection<Item> items) {
		MouseListener mouseHandler = new MouseListener();

		/*
		 * We build the graphical interface by adding the graphical component
		 * corresponding to the Arena - by calling createComponent - to a JFrame.
		 */
		final JFrame frame = new JFrame("Test Arena");

		/*
		 * This is our KeyHandler that will be called by the Arena in case of key events
		 */
		KeyListener keyListener = new KeyListener(frame);

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(itemsArena.createComponent(this.width, this.height, mouseHandler, keyListener));
		frame.pack();
		frame.setVisible(true);

		/*
		 * We initially draw the component
		 */
		itemsArena.refresh();

		/*
		 * We ask the Application to call the following run function every seconds. This
		 * method just refresh the component.
		 */
		Application.timer(100, new TimerRunnable() {

			public void run(TimerTask timerTask) {
				Planet.deleteSpaceShipList();
				itemsArena.refresh();
				for (Item item : items) {
					if (item instanceof SpaceShip && items.contains(item))
						item.action();
				}
			}

		});

        setupDelayProductionSpaceShips();
	}


    public static void setupDelayProductionSpaceShips() {

        for (Item item : Planet.getItems()) {
            if (item instanceof Planet) {
                Application.timer(((Planet) item).getTimerProductionSpaceShips(), new TimerRunnable() {

                    public void run(TimerTask timerTask) {
                        if (Planet.getItems().contains(item)) {
                            item.action();
                        }
                    }
                });
            }
        }
    }


    public static void savedPlanetAndSpaceShips()  {

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream("saved.tmp");
            oos = new ObjectOutputStream(fos);

            oos.writeObject(Planet.getItems());
        }
        catch (IOException ie) {
            JOptionPane.showMessageDialog(null, "Error on save !!", "/!\\ ERROR /!\\", JOptionPane.INFORMATION_MESSAGE);
        }
        finally {
            try {
                if (fos != null) {
                    fos.close();
                }

                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadPlanetAndSpaceShips()  {

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream("saved.tmp");
            ois = new ObjectInputStream(fis);

            Planet.getItems().clear();
            Planet.getItems().addAll((ArrayList<Item>)ois.readObject());

            setupDelayProductionSpaceShips();
        }
        catch (IOException ie) {
            JOptionPane.showMessageDialog(null, "No saved file !", "/!\\ WARNING /!\\", JOptionPane.INFORMATION_MESSAGE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }

                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
