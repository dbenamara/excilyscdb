package mycdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mycdb.model.Company;
import mycdb.model.Computer;
import mycdb.services.CompanyService;
import mycdb.services.ComputerService;

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
	
	public List<Computer> getOnePageOfComputer(int offset, int number) {
		return (ComputerService.getInstance().getPageComputer(offset, number));
	}
	public List<Company> getOnePageOfCompany(int offset, int number) {
		return (CompanyService.getInstance().getPageCompany(offset, number));
	}
	
	
	public void pagineComputer() {
		List<Computer> computs = new ArrayList<>();
		boolean condition = true;
		String saisie;
		int offset = 0;
		int number = 20;
		int tailleL = ComputerService.getInstance().getlength();

		computs = getOnePageOfComputer(offset, number);
		System.out.println(computs);
		while (condition) {
			//tailleL = ComputerService.getInstance().getlength();
			System.out.println("pres n for next p for previous page s to stop");
			saisie = sc.next();
			condition = (saisie.contentEquals("n")) || (saisie.contentEquals("p"));
			//System.out.println("AVANT IF");
			if (saisie.equals("n")) {
				//System.out.println("APRES IF");
				if ((tailleL - number) >= 20) {

					offset += 20;
					number += 20;
					computs = getOnePageOfComputer(offset, number);
				} else {
					System.out.println("vous etes a la derniere page!");
				}
			}
			if (saisie == "p") {
				if (offset < 20) {
					System.out.println("vous etes a la premiere page!");
				} else {
					offset -= 20;
					number -= 20;
					computs = getOnePageOfComputer(offset, number);
				}

			}
			System.out.println(computs);
		}
	}
	
	public void pagineCompany() {
		List<Company> company = new ArrayList<>();
		boolean condition = true;
		String saisie;
		int offset = 0;
		int number = 20;
		int tailleL = CompanyService.getInstance().getlength();

		company = getOnePageOfCompany(offset, number);
		System.out.println(company);
		while (condition) {
			System.out.println("pres n for next p for previous page s fort stop");
			saisie = sc.next();
			condition = (saisie.contentEquals("n")) || (saisie.contentEquals("p"));
			if (saisie.equals("n")) {
				if ((tailleL - number) >= 20) {

					offset += 20;
					number += 20;
					company = getOnePageOfCompany(offset, number);
				} else {
					System.out.println("vous etes a la derniere page!");
				}
			}
			if (saisie.equals("p")) {
				if (offset < 20) {
					System.out.println("vous etes a la premiere page!");
				} else {
					offset -= 20;
					number -= 20;
					company = getOnePageOfCompany(offset, number);
				}

			}
			System.out.println(company);
		}
}
}
