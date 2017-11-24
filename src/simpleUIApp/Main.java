package simpleUIApp;

import java.util.ArrayList;
import java.util.Random;

import fr.ubordeaux.simpleUI.*;

public class Main {
	public static void main(String[] args) {
		Random random = new Random();
		int widthWindow = 400;
		int heightWindow = 500;
		
		ArrayList<Item> items = new ArrayList<Item>();
		
		int randomSize;
		int countPlanets = 0;
		Planet planet;
		while (countPlanets < 5) {
			randomSize = random.nextInt(80-40) + 40;

			planet = new Planet(random.nextInt(widthWindow-randomSize) + randomSize/2,
								random.nextInt(heightWindow-randomSize) + randomSize/2, randomSize);
			
			int countItem = 0;
			boolean contains = false;
			while (countItem < items.size() && !contains) {

				if (items.get(countItem) instanceof Planet) {
					
					Planet checkPlanet = (Planet) items.get(countItem);
					contains = checkPlanet.containsItem(planet);
				}
				
				countItem++;
			}
			
			if (!contains) {
				countPlanets++;
				items.add(planet);
			}
		}

		
		Planet.setItems(items);
		
		Manager manager = new Manager();
		Run r = new Run(widthWindow, heightWindow);

		/*
		 * Call the run method of Application providing an initial item Collection, an
		 * item manager and an ApplicationRunnable
		 */
		Application.run(items, manager, r);
	}
}
