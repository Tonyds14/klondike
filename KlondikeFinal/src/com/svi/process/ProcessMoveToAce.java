package com.svi.process;

import java.util.ArrayList;
import java.util.List;

import com.svi.main.KlondikeMain;
import com.svi.object.CardGameInfo;
import com.svi.object.CardProperties;


public class ProcessMoveToAce {
	
	static String aceMssg ="";
	static ArrayList<String> acePileActiveCards = new ArrayList<>();
	static ArrayList<String> acePileActiveCardsNextCard = new ArrayList<>();
	
	static ArrayList<ArrayList<String>> acePile = new ArrayList<>();
	static List<CardGameInfo> listGameCardsRef = new ArrayList<>();
	static ArrayList<ArrayList<String>> tabPileByColumn = new ArrayList<>();
	static ArrayList<String> wasteCards =new ArrayList<>();
	static String wasteActiveCard ="";
	static List<CardProperties> cardPropertyList  = new ArrayList<>();
	
	public static void processMoveToAce(String holdCard,String processName) {

		acePile = ProcessGame.getAcePile();
		listGameCardsRef = ProcessGame.getListGameCardsRef();
		tabPileByColumn = ProcessGame.getTabPileByColumn();
		wasteCards = ProcessGame.getWasteCards();
		cardPropertyList = KlondikeMain.getCardPropertyList();
		
		aceMssg = holdCard+" moved to Ace Pile";
		updateAcePile(holdCard);  			//return acePile
		getAceActiveCards();				//return acePileActiveCards 
		generateValidNextCardsForAcePile(); //return acePileActiveCardsNextCard
		updateListGameCardsRef(holdCard); 	//return listGameCardsRef
		
		switch(processName) {
		case "01ATABTOACE":
			updateTabPile(holdCard); 		//return tabPileByColumn
			break;
		case "01BTABTOACE":
			updateTabPile(holdCard); 		//return tabPileByColumn
			break;
		case "01AWASTOACE":
			updateWasteCards(holdCard); 	//return wasteCards
			break;
		case "01BWASTOACE":
			updateWasteCards(holdCard);  	//return wasteCards
			break;
			
		default:
			System.out.println(processName+" not define in ProcessMoveToAce");
		}
		

	}
	
	public static void updateTabPile(String holdCard) {
		for (ArrayList<String> column : tabPileByColumn) {
		    column.removeIf(element -> element.equals(holdCard));
		}
	}
	
	public static void updateListGameCardsRef(String holdCard) {
		String elementBeforeTarget = getElementBeforeValue(tabPileByColumn, holdCard);
		for (CardGameInfo ref:listGameCardsRef ) {
			if(ref.getCardValue().equals(elementBeforeTarget)) {
				ref.setCardStat("O");
			}
		}
		
		for (CardGameInfo ref:listGameCardsRef ) {
			if(ref.getCardValue().equals(holdCard)) {
				ref.setCardLoc("ace");
				ref.setLastMove(aceMssg);
			}
		}		
		
	}
	
    public static String getElementBeforeValue(ArrayList<ArrayList<String>> tabPileByColumn, String targetValue) {
        for (ArrayList<String> row : tabPileByColumn) {
            for (int i = 1; i < row.size(); i++) {
                if (row.get(i).equals(targetValue)) {
                    return row.get(i - 1);
                }
            }
        }
        return null; // Element not found or targetValue is in the first position
    }
	
	public static void updateAcePile(String holdCard) {
		if (acePile.isEmpty()) {
			acePile.add(new ArrayList<>());
			acePile.get(0).add(holdCard);
		} else {
			readUpdateAce(acePile,holdCard);
		}
		
	}
	
	public static void readUpdateAce(ArrayList<ArrayList<String>> acePile,String holdCard) {

		boolean matchFound = false;
		
        for (ArrayList<String> row : acePile) {
            if (!row.isEmpty()) {
                String firstElement = row.get(0);
                if (firstElement.charAt(0) == holdCard.charAt(0)) {
                    row.add(holdCard);
                    matchFound = true;
                    break;
                }
            }
        }
        
        if (!matchFound) {
            ArrayList<String> newRow = new ArrayList<>();
            newRow.add(holdCard);
            acePile.add(newRow);
        }
        
//        for (ArrayList<String> row : acePile) {
//            for (String element : row) {
//                System.out.print(element + " ");
//            }
//            System.out.println();
//        }

        
	}
	
	public static void getAceActiveCards() {
		acePileActiveCards.clear();
		for (ArrayList<String> row : acePile) {
		    int lastIndex = row.size() - 1;
		    String lastElement = row.get(lastIndex);
		    acePileActiveCards.add(lastElement);
		}

//		for (String element : acePileActiveCards) {
//		    System.out.println(element);
//		}
		
	}

	public static void generateValidNextCardsForAcePile() {
		acePileActiveCardsNextCard.clear();
		for(String card: acePileActiveCards) {
			if(!card.isEmpty()) {
				getNextCardList(card);				
			}
		}		
	}
	
	public static void getNextCardList(String card) {
		for (CardProperties prop: cardPropertyList) {
			if(prop.getCardValue().equals(card)) {
				acePileActiveCardsNextCard.add(prop.getValidNextAceRef());
				break;
			}
		}	
		
	}
	
	
	public static void updateListGameCardsRefWaste(List<CardGameInfo> listGameCardsRef,ArrayList<String> wasteCards,String holdCard) {
		String nextCard = getNextCard(wasteCards, holdCard);
		for (CardGameInfo ref:listGameCardsRef ) {
			if(ref.getCardValue().equals(nextCard)) {
				ref.setCardStat("O");
			}
		}
		
		for (CardGameInfo ref:listGameCardsRef ) {
			if(ref.getCardValue().equals(holdCard)) {
				ref.setCardLoc("ace");
				ref.setLastMove(aceMssg);
			}
		}		
		
	}
	
	public static String getNextCard(ArrayList<String> wasteCards, String holdCard) {
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
	
	public static void updateWasteCards(String holdCard) {
		wasteCards.removeIf(card -> card.equals(holdCard));
	}
	
	public static void getNextWasteActiveCard() {
		if(!wasteCards.isEmpty()) {
			wasteActiveCard = wasteCards.get(0);
		}
	}
	
	public static String getAceMssg() {
		return aceMssg;
	}


	public static ArrayList<String> getAcePileActiveCards() {
		return acePileActiveCards;
	}


	public static ArrayList<String> getAcePileActiveCardsNextCard() {
		return acePileActiveCardsNextCard;
	}

	public static ArrayList<ArrayList<String>> getAcePile() {
		return acePile;
	}

	public static List<CardGameInfo> getListGameCardsRef() {
		return listGameCardsRef;
	}

	public static ArrayList<ArrayList<String>> getTabPileByColumn() {
		return tabPileByColumn;
	}

	public static ArrayList<String> getWasteCards() {
		return wasteCards;
	}

	public static String getWasteActiveCard() {
		return wasteActiveCard;
	}
	
	

	
	
	

}
