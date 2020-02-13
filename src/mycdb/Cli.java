package mycdb;

import java.util.Scanner;

/**
 * @author djamel
 *
 */
public class Cli {
	private String entree;
	private int min;
	private int max;
	private Scanner sc;
	int nbComputer;
	
	public Cli() {
		this.sc = new Scanner(System.in);
		nbComputer = (574);
	}
	
	public void printMenu() {
		System.out.println("0.Afficher les ordinateurs");
		System.out.println("1. Afficher les compagnies");
		System.out.println("2. Afficher un ordinateur");
		System.out.println("3. Créer un ordinateur");
		System.out.println("3. Quitter\n");
	}
	
	public void printWelcome() {
		System.out.println("------Bienvenue------\n\n");
		System.out.println("Veuillez choisir entre ces différentes possibilités :\n");
		printMenu();
	}
	
	public int getComputerId() {
		System.out.println("Entrez l'id de l'ordinateur");
		int res = getEntry(1, nbComputer);
		
		return res;
	}
	
	public void increaseNbComputer() {
		nbComputer++;
	}
	
	public int getEntry(int min, int max) {
		boolean res = false;
		int entry = -1;
		String line;
		System.out.flush();
		try {
			do {
				try {
					line = sc.next();
					sc.nextLine();
					entry = Integer.parseInt(line);
					if(entry <min|| entry> max) {
						System.out.println("Veuillez entrer un nombre du menu.");
						//printMenu();
						System.out.println("test");
						entry = -1;
					}
				}catch(Exception e) {
					System.out.println("Veuillez entrer un nombre du menu.");
					System.out.println("test");
					printMenu();
					entry = -1;
				}
			}while(entry < min || entry > max);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		return entry;
	}
}
