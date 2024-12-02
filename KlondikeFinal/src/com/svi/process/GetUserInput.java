package com.svi.process;

import java.util.Scanner;

public class GetUserInput {

	private static int numShfls = 0;
	private static int talonData = 0;
	private static boolean isValidCountShuffle = false;
	private static boolean isValidTalonData = false;
	static Scanner scanner = new Scanner(System.in); 
	
	private static String shuffleInd ="";
	
	public static void getUserInput() {
				
		while(!isValidCountShuffle) {		
			try {
				while (true) {
//					System.out.print("\nEnter the number of shuffles: ");
//					numShfls = Integer.parseInt(scanner.nextLine());
//					if (numShfls >= 0 && numShfls <= 2147483647) {
//						isValidCountShuffle = true;
//						getDataForTalon();
//						break;
//					} else
//						System.out.println("Input is outside the valid range. Min number of shuffles is 0, and max is 2,147,483,647.");
				
					System.out.print("\nSHUFFLE CARDS? [Y/N]: ");
					shuffleInd = scanner.nextLine();
					if(shuffleInd.toUpperCase().equals("Y") || shuffleInd.toUpperCase().equals("N")) {
						isValidCountShuffle = true;
						getDataForTalon();
						break;						
					} else
						System.out.println("Invalid input [Y/N] only");
				}
			} catch (Exception e) {
				System.out.println("Invalid input. Please enter a valid integer.");
			}		
		}		
		scanner.close();			
	}
	
	public static void getDataForTalon() {
		
		while(!isValidTalonData) {		
			try {
				while (true) {
					System.out.print("\nCOUNT OF CARDS TO GET AT STOCK PILE AT A TIME [1 or 3]: ");
					talonData = Integer.parseInt(scanner.nextLine());
					if (talonData == 1 || talonData == 3) {
						isValidTalonData = true;
						break;
					}						
					else 
						System.out.println("Input is outside the valid option. Choose either 1 or 3 only");
					}
			} catch (Exception e) {
				System.out.println("Invalid input. Please enter a valid integer.");
			}
		}
		
	}

	public static int getNumOfShuffles() {
		return numShfls;
	}

	public static int getTalonData() {
		return talonData;
	}

	public static String getShuffleInd() {
		return shuffleInd;
	}	
	
	
	
}
