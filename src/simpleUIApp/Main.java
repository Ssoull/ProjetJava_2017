package simpleUIApp;

import java.util.ArrayList;
import java.util.Random;

import fr.ubordeaux.simpleUI.*;

public class Main {
	public static void main(String[] args) {
		Random random = new Random();
		int widthWindow = 400;
		int heightWindow = 500;
		//ArrayList<Item> testItemList = new ArrayList<Item>();
		/*
		 * Randomly position 25 Ships in the Arena zone (defined afterwards)
		 */
		/*for (int i = 0; i < 25; i++) {
			testItemList.add(new SpaceShip(random.nextInt(400), random.nextInt(500), 10));
		}*/
		
		ArrayList<Item> items = new ArrayList<Item>();
		
		int randomSize;
		int countPlanets = 0;
		Planet planet;
		while (countPlanets < 5) {
			randomSize = random.nextInt(50-40) + 40;
			
			planet = new Planet(random.nextInt(widthWindow-randomSize) + randomSize/2, 
					random.nextInt(heightWindow-randomSize) + randomSize/2, randomSize);
			
			int countItem = 0;
			boolean contains = false;
			while (countItem < items.size() && !contains) {
				
				if (items.get(countItem) instanceof Planet) {
					
					Planet checkPlanet = (Planet) items.get(countItem);
					contains = checkPlanet.containsPlanet(planet);
				}
				
				countItem++;
			}
			
			if (!contains) {
				countPlanets++;
				items.add(planet);
			}
		}
		
		Planet.setItems(items);
		//planets.add(new Planet(100, 100, randomSize));
		
		
		Manager manager = new Manager();
		Run r = new Run(widthWindow, heightWindow);

		/*
		 * Call the run method of Application providing an initial item Collection, an
		 * item manager and an ApplicationRunnable
		 */
		Application.run(items, manager, r);
	}
}
