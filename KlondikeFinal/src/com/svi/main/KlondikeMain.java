package com.svi.main;

import java.util.ArrayList;
import java.util.List;

import com.svi.object.CardGameInfo;
import com.svi.object.CardProperties;
import com.svi.process.BuildCardPropertyList;
import com.svi.process.BuildConsoleMssg;
import com.svi.process.BuildTabActiveCardsList;
import com.svi.process.GetUserInput;
import com.svi.process.ProcessGame;
import com.svi.process.ReadInputTextFile;
import com.svi.process.ShuffleCards;
import com.svi.process.SpreadCard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class KlondikeMain {
	
	static String[] inputCardList = new String[52];
	static int talonCount = 0;
	static List<CardProperties> cardPropertyList  = new ArrayList<>();
	static String shuffleInd = "";
	
//	static List<ProcessListInfo> processList = new ArrayList<>();
	
	static ArrayList<ArrayList<String>> tabPileByColumn = new ArrayList<>();
	static ArrayList<String> stockCards = new ArrayList<>();
	static List<CardGameInfo> listGameCardsRef = new ArrayList<>();
	
	static ArrayList<ArrayList<String>> tabActiveCards = new ArrayList<>();
	static ArrayList<String> wasteCards = new ArrayList<>();
	static ArrayList<ArrayList<String>> acePile = new ArrayList<>();
	static ArrayList<String> acePileTopCards = new ArrayList<>();
	static String moveMssg = "";
	static ArrayList<List<String>> tabActiveCardsNextCardList = new ArrayList<>();
	
	static ArrayList<ArrayList<String>> tabXCards = new ArrayList<>();
	static ArrayList<ArrayList<String>> tabOCards = new ArrayList<>();
	static ArrayList<ArrayList<String>> tabOBCards = new ArrayList<>();
	static ArrayList<ArrayList<String>> tabOMCards = new ArrayList<>();
	static List<String> tabActiveCardsList = new ArrayList<>();
	static List<List<String>> activeCardsListValidPre = new ArrayList<>();
	static List<List<String>> activeCardsListValidNext = new ArrayList<>();
	
	static boolean flgGameContinue = true;
	static boolean flgTabChange = false;
	static boolean flgWasteChange = false;
	
	static FileOutputStream fos;

	public static void main(String[] args) {


		
		inputCardList = ReadInputTextFile.ReadInputText();
		
		GetUserInput.getUserInput();
		talonCount = GetUserInput.getTalonData();
		shuffleInd = GetUserInput.getShuffleInd();
		
		String[] shuffledCards = null;
		if(shuffleInd.toUpperCase().equals("Y")) {
			shuffledCards = ShuffleCards.randomShuffleCards(inputCardList);
		} else {
			shuffledCards = inputCardList;
		}
		
//		String[] shuffledCards = ShuffleCards.shuffleCards(inputCardList, numOfShuffles);
		
		cardPropertyList = BuildCardPropertyList.buildCardProperty(shuffledCards);		

		SpreadCard.spreadCard(shuffledCards, cardPropertyList);
		listGameCardsRef = SpreadCard.getListGameCardsRef();
		tabPileByColumn = SpreadCard.getTabPileByColumn();
		stockCards = SpreadCard.getStockCards();		
		
		int ctrMove = 0;
		moveMssg = "Game Cards Prepared";
		BuildConsoleMssg.buildMessage(ctrMove, tabPileByColumn, stockCards, wasteCards, acePile, moveMssg,listGameCardsRef);

		ProcessGame.processGame();
		
	}
	
	public static void initializeOutput() { 
		String projectPath = System.getProperty("user.dir");
		String outputFilePath =projectPath + File.separator +"Output" + File.separator;
		
		// Create the output directory if it doesn't exist
		File outputDir = new File(outputFilePath);
		outputDir.mkdirs();
		
		File outputFile = new File(outputFilePath + "console_output.txt");
		
		try {
            FileOutputStream fos = new FileOutputStream(outputFile);

            PrintStream ps = new PrintStream(fos);

            System.setOut(ps);

            System.out.println("This will be saved to: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	public static List<CardProperties> getCardPropertyList() {
		return cardPropertyList;
	}

	public static ArrayList<ArrayList<String>> getTabPileByColumn() {
		return tabPileByColumn;
	}

	public static ArrayList<String> getStockCards() {
		return stockCards;
	}

	public static List<CardGameInfo> getListGameCardsRef() {
		return listGameCardsRef;
	}

	public static int getTalonCount() {
		return talonCount;
	}
	
	

}
