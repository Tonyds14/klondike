package com.svi.process;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import com.svi.object.CardGameInfo;


public class ProcessMoveToTab {
	
	static String tabMssg ="";
	static String wasteActiveCard ="";
	
	static ArrayList<ArrayList<String>> tabPileByColumn = new ArrayList<>();
	static List<CardGameInfo> listGameCardsRef = new ArrayList<>();
	static ArrayList<String> wasteCards =new ArrayList<>();
	
	public static void processMoveToTab(String processName,String holdCard,List<Integer> targetPileIndex) {
		
		tabPileByColumn = ProcessGame.getTabPileByColumn();		//return tabPileByColumn
		listGameCardsRef = ProcessGame.getListGameCardsRef();	//return listGameCardsRef
		wasteCards = ProcessGame.getWasteCards();				//return wasteCards
		
		int targetPile = targetPileIndex.get(0);
		tabMssg = holdCard+" move to pile "+(targetPile+1);
		
		switch(processName) {
		case "03BBOTTOTAB":
			processTabToTab(holdCard,targetPile);
			break;
		case "03AMIDTOTAB":
			processMidOpenCards(holdCard,targetPile);
			break;
			
		case "03 WASTOTAB":
			processWasteToTab(holdCard,targetPile);

			break;
		
		case "02 KTNWPILE":
			processKingNewPile(holdCard,targetPile);
			break;
			
		case "04 KWNWPILE":
			processKingFromWaste(holdCard,targetPile);
			break;
			
		default:
			System.out.println(processName+" not define in ProcessMoveToTab");
				

		}
		
	}
	
	
	public static void processMidOpenCards(String holdCard,int targetPile) {
		updateListGameCardsRef(holdCard);
		LinkedHashSet<String> extractedElements = extractElementsInTargetColumn(holdCard);
		removeElementsinTabPile(extractedElements);
//		addElementsInTabPile(extractedElements,targetPile);	
		appendElementsInTabPile(extractedElements,targetPile);
	}
	
	
	public static void processKingFromWaste(String holdCard,int targetPile) {
		updateListGameCardsRefKingFromWaste(holdCard);

		wasteCards.removeIf(card -> card.equals(holdCard));
		
		ArrayList<String> targetRow = tabPileByColumn.get(targetPile);
		targetRow.add(holdCard);
		
	}
	
	
	public static void processKingNewPile(String holdCard,int targetPile) {
		updateListGameCardsRef(holdCard);
		LinkedHashSet<String> extractedElements = extractElementsInTargetColumn(holdCard);
		removeElementsinTabPile(extractedElements);
//		appendElementsInTabPile(extractedElements,targetPile);
		addElementsInTabPile(extractedElements,targetPile);		
		
	}
	
	public static LinkedHashSet<String> extractElementsInTargetColumn(String holdCard) {
		LinkedHashSet<String> extractedElements = new LinkedHashSet<>();

		for (ArrayList<String> row : tabPileByColumn) {
		    boolean foundCardValue = false;

		    for (String element : row) {		    	
		    	
		        if (foundCardValue) {
		            extractedElements.add(element);
		        }

		        if (element.equals(holdCard)) {
		            foundCardValue = true;
		            extractedElements.add(element);
		        }
		    }
		}
		return extractedElements;
	}
	
	public static void removeElementsinTabPile(LinkedHashSet<String> extractedElements) {
		if(!extractedElements.isEmpty()) {
			for (ArrayList<String> row : tabPileByColumn) {
				row.removeAll(extractedElements);
			}
		}
	}
	
	
	public static void appendElementsInTabPile(LinkedHashSet<String> extractedElements, int targetPile) {
		if(!extractedElements.isEmpty()) {
			ArrayList<String> extractedElementsList = new ArrayList<>(extractedElements);
			ArrayList<String> row = tabPileByColumn.get(targetPile);
			row.addAll(extractedElementsList);
		}

	}
	
	public static void addElementsInTabPile(LinkedHashSet<String> extractedElements,int targetPile) {
		ArrayList<String> elementsToAdd = new ArrayList<>(extractedElements);
		if(!extractedElements.isEmpty()) {
			if (targetPile >= 0 && targetPile < tabPileByColumn.size()) {
			    tabPileByColumn.set(targetPile, elementsToAdd);
			} else {
			    System.out.println("Invalid targetRowIndex");
			}
		}
		
	}
	
	public static void addKingInTabPile(LinkedHashSet<String> extractedElements,int targetPile) {
		ArrayList<String> elementsToAdd = new ArrayList<>(extractedElements);
		if(!extractedElements.isEmpty()) {
			if (targetPile >= 0 && targetPile < tabPileByColumn.size()) {
			    tabPileByColumn.set(targetPile, elementsToAdd);
			} else {
			    System.out.println("Invalid targetRowIndex");
			}
		}
		
	}
	
	
	public static void processTabToTab(String holdCard,int targetPile) {
		
		updateListGameCardsRef(holdCard);
		removeHoldCardToRow(holdCard);		
		addHoldCardToRow(targetPile, holdCard);
		
	}
	
	
	public static void removeHoldCardToRow(String holdCard) {
		for (ArrayList<String> column : tabPileByColumn) {
		    column.removeIf(element -> element.equals(holdCard));
		}
	}
	
	
    public static void addHoldCardToRow(int targetPileIndex, String holdCard) {
        if (targetPileIndex >= 0 && targetPileIndex < tabPileByColumn.size()) {
            ArrayList<String> targetRow = tabPileByColumn.get(targetPileIndex);
            targetRow.add(holdCard);
        } else {
            System.out.println("Invalid targetPileIndex"); // Handle index out of range error
        }
    }
	
	public static void updateListGameCardsRefKingFromWaste(String holdCard) {
	
		for (CardGameInfo ref:listGameCardsRef ) {
				if(ref.getCardValue().equals(holdCard)) {
					ref.setCardStat("O");
				}
		}			

	}
    
	public static void updateListGameCardsRef(String holdCard) {
		String elementBeforeTarget = getElementBeforeValue(holdCard);
		
		if(elementBeforeTarget != null) {
			for (CardGameInfo ref:listGameCardsRef ) {
				if(ref.getCardValue().equals(elementBeforeTarget)) {
					ref.setCardStat("O");
				}
			}			
		}

		
		for (CardGameInfo ref:listGameCardsRef ) {
			if(ref.getCardValue().equals(holdCard)) {
				ref.setLastMove(tabMssg);
			}
		}
		
	}
	
	
    public static String getElementBeforeValue(String targetValue) {
        for (ArrayList<String> row : tabPileByColumn) {
            for (int i = 1; i < row.size(); i++) {
                if (row.get(i).equals(targetValue)) {
                    return row.get(i - 1);
                }
            }
        }
        return null; // Element not found or targetValue is in the first position
    }

    
    public static ArrayList<String> processWasteToTab(String holdCard,int targetPile) {
		
		updateListGameCardsRefWaste(holdCard);
		updatetabPileFromWaste(holdCard,targetPile);
		updateWasteCards(holdCard);
		getNextWasteActiveCard();
    	
		return wasteCards;
    }
    
	public static void getNextWasteActiveCard() {
		if(!wasteCards.isEmpty()) {
			wasteActiveCard = wasteCards.get(0);
		}
	}
    
	public static void updateListGameCardsRefWaste(String holdCard) {
		String nextCard = getNextCard(holdCard);
	    if (nextCard != null) {
	    	System.out.println("Next card after " + holdCard + ": " + nextCard);
	    	wasteActiveCard = nextCard;
	    } else {
	    	System.out.println("No card found after " + holdCard);
	    }
	    
		for (CardGameInfo ref:listGameCardsRef ) {
			if(ref.getCardValue().equals(nextCard)) {
				ref.setCardStat("O");
			}
		}	    
		
		for (CardGameInfo ref:listGameCardsRef ) {
			if(ref.getCardValue().equals(holdCard)) {
				ref.setCardStat("O");
				ref.setCardLoc("tab");
				ref.setLastMove(tabMssg);
			}
		}
		
	}
	
	public static String getNextCard(String holdCard) {
        boolean foundHoldCard = false;

        for (int i = 0; i < wasteCards.size() - 1; i++) {
            if (foundHoldCard) {
                return wasteCards.get(i);
            }

            if (wasteCards.get(i).equals(holdCard)) {
                foundHoldCard = true;
            }
        }

        return null; // Hold card not found or no card after hold card
    }
	
	public static void updatetabPileFromWaste(String holdCard,int targetPile) {
		
		addHoldCardToRow(targetPile, holdCard);			

	}
    
	
	public static void updateWasteCards(String holdCard) {
		wasteCards.removeIf(card -> card.equals(holdCard));
	}
		

	public static String getTabMssg() {
		return tabMssg;
	}


	public static String getWasteActiveCard() {
		return wasteActiveCard;
	}

	public static ArrayList<ArrayList<String>> getTabPileByColumn() {
		return tabPileByColumn;
	}

	public static List<CardGameInfo> getListGameCardsRef() {
		return listGameCardsRef;
	}

	public static ArrayList<String> getWasteCards() {
		return wasteCards;
	}
    
    


}
