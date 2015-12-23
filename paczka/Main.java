package paczka;

import java.util.ArrayList;

public class Main {
public static void main(String[] arg){
WriteToFile.initialize(15);
	RunTest(6, 1, 5000);
}

public static void RunTest(int bees, int timeForHole, int timeForBees){
		BeeHive b = new BeeHive(bees, timeForHole);
		BeeGenerator.generateBees(bees, timeForBees, b);
}

}