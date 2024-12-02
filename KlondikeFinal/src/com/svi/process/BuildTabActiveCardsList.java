package com.svi.process;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import com.svi.main.KlondikeMain;
import com.svi.object.CardGameInfo;
import com.svi.object.CardProperties;

public class BuildTabActiveCardsList {
	
	static ArrayList<ArrayList<String>> tabXCards = new ArrayList<>();
	static ArrayList<ArrayList<String>> tabOCards = new ArrayList<>();
	static ArrayList<ArrayList<String>> tabOBCards = new ArrayList<>();
	static ArrayList<ArrayList<String>> tabOMCards = new ArrayList<>();
	static List<String> tabActiveCardsList = new ArrayList<>();
	static List<List<String>> activeCardsListValidPre = new ArrayList<>();
	static List<List<String>> activeCardsListValidNext = new ArrayList<>();
	static LinkedHashSet<String> openMidCards = new LinkedHashSet<>();
	static LinkedHashSet<String> openBottomCards = new LinkedHashSet<>();
	
	static ArrayList<ArrayList<String>> tabPileByColumn = new ArrayList<>();
	static List<CardGameInfo> listGameCardsRef = new ArrayList<>();
	static List<CardProperties> cardPropertyList  = new ArrayList<>();
	static List<String> lastElements = new ArrayList<>();
	static List<String> listValidKingToMove = new ArrayList<>();
	
	
	public static void buildTabActiveCardsList() {
		
		tabPileByColumn = ProcessGame.getTabPileByColumn();
		listGameCardsRef = ProcessGame.getListGameCardsRef();
		cardPropertyList = KlondikeMain.getCardPropertyList();		
		
		buildTabXTabOCardsList();
		buildOMOBCardsList();
		mergeOMOBCardsList();
		buildListOpenMidCards();
		buildListOpenBottomCards();
		generateListValidPreCards();
		generateListValidNextCards();
		buildListLastElement();
		buildListValidKingToMove();
		
	}
	
	public static List<String> buildListValidKingToMove() {
		listValidKingToMove.clear();
		List<String> listKingInTabPile = new ArrayList<>();
		for (ArrayList<String> row : tabPileByColumn) {
		    for (int col = 3; col < row.size(); col++) {
		        String element = row.get(col);
		        if (element.contains("K")) {
		        	listKingInTabPile.add(element);
		        }
		    }
		}
		
		filterListKingWithOpenStat(listKingInTabPile);
		
		return listValidKingToMove;
	}
	
	public static List<String> filterListKingWithOpenStat(List<String> listKingInTabPile) {
		for (String cardValue : listKingInTabPile) {
        	String cardStat = getCardStat(cardValue);
            if (cardStat.equals("O")) {
            	listValidKingToMove.add(cardValue);
            }
        }
		return listValidKingToMove;
	}
	
	
	
	public static List<String> buildListLastElement() {
        lastElements.clear();
        for (ArrayList<String> row : ProcessGame.tabPileByColumn) {
            int lastIndex = row.size() - 1;
        	if(!row.isEmpty()) {
                String lastElement = row.get(lastIndex);
                lastElements.add(lastElement);
        	} else {
        		lastElements.add(null);
        	}

        }        
        return lastElements;
        
	}
	
	public static void buildTabXTabOCardsList() {
		tabXCards.clear();
		tabOCards.clear();
		
		for (ArrayList<String> column : tabPileByColumn) {
			ArrayList<String> tabXColumn = new ArrayList<>();
            ArrayList<String> tabOColumn = new ArrayList<>();
            
            for (String cardValue : column) {
            	String cardStat = getCardStat(cardValue);
                if (cardStat.equals("X")) {
                    tabXColumn.add(cardValue);
                } else if (cardStat.equals("O")) {
                    tabOColumn.add(cardValue);
                }
            }
            tabXCards.add(tabXColumn);
            tabOCards.add(tabOColumn);
		}
		
//        System.out.println("tabXCards: ");
//        for (ArrayList<String> column : tabXCards) {
//            System.out.println(column);
//        }
//
//        System.out.println("tabOCards: "+tabOCards);
//        for (ArrayList<String> column : tabOCards) {
//            System.out.println(column);
//        }
		
	}
	
	
	private static String getCardStat(String cardValue) {
        String cardStat ="";
		for (CardGameInfo data:listGameCardsRef) {
        	if(cardValue.equals(data.getCardValue())) {
        		cardStat = data.getCardStat();
        	}
        }		
		return cardStat;
    }
	
	
	public static void buildOMOBCardsList () {
		tabOBCards.clear();
		tabOMCards.clear();
		
        for (ArrayList<String> row : tabOCards) {
        	int rowSize = row.size();
        	if (rowSize > 0) {
        		String firstElement = row.get(0);
        		String lastElement = row.get(rowSize - 1);
        		
        		ArrayList<String> obRow = new ArrayList<>();
        		obRow.add(lastElement);
        		tabOBCards.add(obRow);
        		
        		ArrayList<String> omRow = new ArrayList<>();
                omRow.add(firstElement);
                tabOMCards.add(omRow);
        	}
        }
        
//        System.out.println("tabOBCards:");
//        for (ArrayList<String> row : tabOBCards) {
//            System.out.println(row);
//        }
//        
//        System.out.println("tabOMCards:");
//        for (ArrayList<String> row : tabOMCards) {
//            System.out.println(row);
//        }
            
	}
	
	public static void mergeOMOBCardsList() {		
		tabActiveCardsList.clear();
		LinkedHashSet<String> tempActiveCardsList = new LinkedHashSet<>();		
		
		for (ArrayList<String> row : tabOMCards) {
			tempActiveCardsList.addAll(row);
		}		
		for (ArrayList<String> row : tabOBCards) {
			tempActiveCardsList.addAll(row);
		}		
		tabActiveCardsList.addAll(tempActiveCardsList);
		
//		System.out.println("\ntabActiveCardsList "+tabActiveCardsList);
	}
	
	public static void buildListOpenMidCards() {
		openMidCards.clear();

		for (ArrayList<String> omCards : tabOMCards) {
		    boolean found = false;
		    for (ArrayList<String> obCards : tabOBCards) {
		        if (omCards.equals(obCards)) {
		            found = true;
		            break;
		        }
		    }
		    if (!found) {
		        for (String card : omCards) {
		            openMidCards.add(card);
		        }
		    }
		}		
	}
	
	
	public static void buildListOpenBottomCards() {
		openBottomCards.clear();
		
		for (ArrayList<String> cardList : tabOBCards) {
		    for (String card : cardList) {
		        openBottomCards.add(card);
		    }
		}
	}
	
	public static void generateListValidPreCards() {
		activeCardsListValidPre.clear();

		List<String> holdList = new ArrayList<>();
		for(String activeCard: openBottomCards) {
			holdList = getListPre(activeCard);
			activeCardsListValidPre.add(holdList);
		}				
	}
	
	
	public static List<String> getListPre(String activeCard) {
		List<String> nextCardList = new ArrayList<>();
		for (CardProperties prop: cardPropertyList) {
			if(prop.getCardValue().equals(activeCard)) {
				nextCardList = prop.getListValidPreCard();
				break;
			} else {
//				System.out.println(activeCard+" not found in cardPropertyList");
			}
		}	
		return nextCardList;
	}
	
	
	public static void generateListValidNextCards() {
		activeCardsListValidNext.clear();
		
		List<String> holdList = new ArrayList<>();
		for(String activeCard: openBottomCards) {
			holdList = getListNext(activeCard);
			activeCardsListValidNext.add(holdList);
		}				
	}
	
	public static List<String> getListNext(String activeCard) {
		List<String> nextCardList = new ArrayList<>();
		for (CardProperties prop: cardPropertyList) {
			if(prop.getCardValue().equals(activeCard)) {
				nextCardList = prop.getListValidNextCard();
				break;
			} else {
//				System.out.println(activeCard+" not found in cardPropertyList");
			}
		}	
		return nextCardList;
	}

	public static ArrayList<ArrayList<String>> getTabOCards() {
		return tabOCards;
	}

	public static ArrayList<ArrayList<String>> getTabOBCards() {
		return tabOBCards;
	}

	public static ArrayList<ArrayList<String>> getTabOMCards() {
		return tabOMCards;
	}

	public static List<String> getTabActiveCardsList() {
		return tabActiveCardsList;
	}

	public static List<List<String>> getActiveCardsListValidPre() {
		return activeCardsListValidPre;
	}

	public static List<List<String>> getActiveCardsListValidNext() {
		return activeCardsListValidNext;
	}

	public static ArrayList<ArrayList<String>> getTabXCards() {
		return tabXCards;
	}

	public static LinkedHashSet<String> getOpenMidCards() {
		return openMidCards;
	}

	public static LinkedHashSet<String> getOpenBottomCards() {
		return openBottomCards;
	}

	public static List<String> getLastElements() {
		return lastElements;
	}

	public static List<String> getListValidKingToMove() {
		return listValidKingToMove;
	}


	
}
