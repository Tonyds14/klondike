package com.svi.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svi.object.CardGameInfo;
import com.svi.object.CardProperties;

public class SpreadCard {
	
	private static ArrayList<ArrayList<String>> rowCards = new ArrayList<>();
	private static ArrayList<ArrayList<String>> tabPileByColumn = new ArrayList<>();

	private static ArrayList<String> tabActiveCards = new ArrayList<>();
	private static ArrayList<String> stockCards = new ArrayList<>();

	private static ArrayList<List<String>> tabActiveCardsNextCardList = new ArrayList<>();
	private static List<CardGameInfo> listGameCardsRef = new ArrayList<>();
	
	public static void spreadCard(String[] inputCardList,List<CardProperties> cardPropertyList) {
		mapInputCardsByRow(inputCardList);
		createTabPile();
		createStockPile(inputCardList);

		listGameCardsRef = BuildListCardGameRef.buildList(cardPropertyList, tabPileByColumn, stockCards);		
		
	}


	public static void mapInputCardsByRow(String[] inputCardList) {
		String[] row1Cards = new String[7];
		String[] row2Cards = new String[7];
		String[] row3Cards = new String[7];
		String[] row4Cards = new String[7];
		String[] row5Cards = new String[7];
		String[] row6Cards = new String[7];
		String[] row7Cards = new String[7];
        
        System.arraycopy(inputCardList, 0, row1Cards, 0, 7);
        System.arraycopy(inputCardList, 7, row2Cards, 1, 6);
        System.arraycopy(inputCardList, 13, row3Cards, 2, 5);
        System.arraycopy(inputCardList, 18, row4Cards, 3, 4);
        System.arraycopy(inputCardList, 22, row5Cards, 4, 3);
        System.arraycopy(inputCardList, 25, row6Cards, 5, 2);
        System.arraycopy(inputCardList, 27, row7Cards, 6, 1);        
        
        rowCards.add(new ArrayList<>(Arrays.asList(row1Cards)));
        rowCards.add(new ArrayList<>(Arrays.asList(row2Cards)));
        rowCards.add(new ArrayList<>(Arrays.asList(row3Cards)));
        rowCards.add(new ArrayList<>(Arrays.asList(row4Cards)));
        rowCards.add(new ArrayList<>(Arrays.asList(row5Cards)));
        rowCards.add(new ArrayList<>(Arrays.asList(row6Cards)));
        rowCards.add(new ArrayList<>(Arrays.asList(row7Cards)));

//        System.out.println("\nrowCards");
//        for (ArrayList<String> row : rowCards) {
//            for (String element : row) {
//                System.out.print(element + " ");
//            }
//            System.out.println();
//        }	
        
	}
	

	public static void createTabPile() {
		
		int numOfCol = rowCards.get(0).size();
		int tabRefID = 0;
		System.out.println();
		for (int col = 0; col<numOfCol; col++) {
			ArrayList<String> colSet = new ArrayList<>();
			int pileNum = col+1;
			int pileSeqNum = 1;
			for (ArrayList<String> row : rowCards) {
                String element = row.get(col);
                if (element != null) {
                	//build data for tabPileByColumn
                    colSet.add(element);  
                
                    pileSeqNum++;
                    tabRefID++;
                }
            }
			tabPileByColumn.add(colSet);
//			System.out.println("Pile "+(col+1)+" cards: " +colSet);
		}		
//		System.out.println("\ntabPileByColumn");
//		System.out.println(tabPileByColumn);
		
		//restructure tabPileByColumn into tabPileByRow
//		ReConstrucTabDataByRow tabRow = new ReConstrucTabDataByRow();
//		tabRow.reBuildTabPile(tabPileByColumn);
//		tabPileByRow = tabRow.getConvPileCardsList();
		
	}
	
	
	public static void createStockPile(String[] inputCardList) {
		for (int i = 28; i < inputCardList.length; i++) {
        	stockCards.add(inputCardList[i]);
		}
		
//		System.out.println("\nstockCards");
//		String stringStockCards = String.join(", ", stockCards);
//		System.out.println(stringStockCards);

	}


	public static ArrayList<ArrayList<String>> getRowCards() {
		return rowCards;
	}


	public static ArrayList<ArrayList<String>> getTabPileByColumn() {
		return tabPileByColumn;
	}



	public static ArrayList<String> getTabActiveCards() {
		return tabActiveCards;
	}


	public static ArrayList<String> getStockCards() {
		return stockCards;
	}


	public static ArrayList<List<String>> getTabActiveCardsNextCardList() {
		return tabActiveCardsNextCardList;
	}


	public static List<CardGameInfo> getListGameCardsRef() {
		return listGameCardsRef;
	}
	


	
}
