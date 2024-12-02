package com.svi.process;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import com.svi.main.KlondikeMain;
import com.svi.object.CardGameInfo;
import com.svi.object.CardProperties;
import com.svi.object.ScanTempInfo;

public class PerformWasteCardScanV2 {
	static boolean cardIsKingToMove = false;
	static boolean canBePutToAce = false;
	static boolean canBeMoveToTab = false;
	static boolean canBeMoveToMidTab = false;
	
	static String scanKingToMoveMssg ="";
	static String scanAceMssg ="";
	static String scanCardNextMoveMssg ="";
	static String scanCardNextMoveMidMssg ="";
	
	static int sourcePile =0;
	static List<Integer> targetPileIndex = null;
	static String holdCard ="";
	
	static List<ScanTempInfo> cardScanKing = new ArrayList<>();
	static List<ScanTempInfo> cardScanAce = new ArrayList<>();
	static List<ScanTempInfo> cardScanTab = new ArrayList<>();
	
	static List<CardProperties> cardPropertyList  = new ArrayList<>();
	
	static List<ScanTempInfo> cardScanOutput = new ArrayList<>();
	static String cardScanOutputMssg = "";
	
	static int ctrActiveCol = 0;
	static ArrayList<String> aceActiveCardsNextCardList = new ArrayList<>();
	static List<List<String>> cardsValidNextList = new ArrayList<>();
	static List<CardGameInfo> listGameCardsRef = new ArrayList<>();
	static List<List<String>> activeCardsListValidNext = new ArrayList<>();
	static List<List<String>> activeCardsListValidPre = new ArrayList<>();
	static ArrayList<ArrayList<String>> acePile = new ArrayList<>();
	static ArrayList<ArrayList<String>> tabOBCards = new ArrayList<>();
	static ArrayList<ArrayList<String>> tabOMCards = new ArrayList<>();
	
	static LinkedHashSet<String> openMidCards = new LinkedHashSet<>();
	static LinkedHashSet<String> openBottomCards = new LinkedHashSet<>();
	static List<String> lastElements = new ArrayList<>();
	
	static int ctrMove = 0;
	
	public static List<ScanTempInfo> performCardScan(String card) {
		
//		ctrActiveCol = ProcessGame.getCtrActiveCol();
		aceActiveCardsNextCardList = ProcessGame.getAceActiveCardsNextCardList();
		ctrMove = ProcessGame.getCtrMove();
		
		listGameCardsRef = ProcessGame.getListGameCardsRef();
		activeCardsListValidNext = ProcessGame.getActiveCardsListValidNext();
		activeCardsListValidPre = ProcessGame.getActiveCardsListValidPre();

		acePile = ProcessGame.getAcePile();
		tabOMCards = ProcessGame.getTabOMCards();
		
		tabOBCards = ProcessGame.getTabOBCards();
		cardPropertyList = KlondikeMain.getCardPropertyList();
		
		openMidCards = ProcessGame.getOpenMidCards();
		openBottomCards = ProcessGame.getOpenBottomCards();
		lastElements = ProcessGame.getLastElements();
		
		cardScanOutput.clear();
		cardScanAce = scanCardAce(card);
		cardScanTab = scanCardNextMove(card);
		cardScanKing = scanCardKing(card);	
		cardAssessment(card);
		
		return cardScanOutput;
	}
	
	
	public static void cardAssessment(String card) {
		if (canBePutToAce) {
			cardScanOutputMssg = scanAceMssg;
			cardScanOutput.addAll(cardScanAce);
		} else {
			if(cardIsKingToMove) { 							
				cardScanOutputMssg = scanKingToMoveMssg;	
				cardScanOutput.addAll(cardScanKing);			
			} else {
				if(canBeMoveToTab) {							
					cardScanOutputMssg = scanCardNextMoveMssg;	
					cardScanOutput.addAll(cardScanTab);	
				} else {
					cardScanOutputMssg = "10 NOACTION "+card+" cannot move to acePile, tab, not king or active pile not less than 7" ;
					List<Integer> listTargetPileIndex = null;
					int targetPile = 0;
					ScanTempInfo scanData = new ScanTempInfo(card,cardScanOutputMssg,listTargetPileIndex,ctrMove );
					cardScanOutput.add(scanData);
				}
			}
		}		
	}
	
	
	public static List<ScanTempInfo> scanCardAce(String card) {
		canBePutToAce = false;
		scanAceMssg ="";
		List<Integer> listTargetPileIndex = new ArrayList<>();
		List<ScanTempInfo> cardScanAce = new ArrayList<>();
		boolean checkAceNextCardsList = false;
		if ((card.toString()).contains("A")) {			
			scanAceMssg = "01AWASTOACE : "+card +" Move card to Ace Pile ";
			canBePutToAce = true;
		} else {
			
			int index = aceActiveCardsNextCardList.indexOf(card);
			if (index != -1) {
//			    System.out.println("Value found at index: " + index);
		    	scanAceMssg = "01BWASTOACE : "+card + " can be put into Ace Pile ";
				canBePutToAce = true;
			} else {
//			    System.out.println("Value not found in the list.");
		    	scanAceMssg = "10 NOACTION : "+card + " cannot be put in Ace Pile ";
			}
						
		}
		
		ScanTempInfo scanData = new ScanTempInfo(card,scanAceMssg,listTargetPileIndex,ctrMove);
		cardScanAce.add(scanData);
		
		return cardScanAce;
		
	}
	
	public static List<ScanTempInfo> scanCardNextMove(String card) {
		canBeMoveToTab = false;
		scanCardNextMoveMssg ="";
		List<ScanTempInfo> cardScanTab = new ArrayList<>();
		
		List<Integer> listTargetPileIndex = new ArrayList<>();
		List<Integer> pileNum = new ArrayList<>();		
        for (int i = 0; i < activeCardsListValidPre.size(); i++) {
            List<String> innerList = activeCardsListValidPre.get(i);
            if (innerList.contains(card)) {
            	
            	List<String> tempListPreCard = getListNextCard(card);
            	
            	for (String cardRef : tempListPreCard) {
        			listTargetPileIndex = getIndexInLastElements(cardRef,lastElements,listTargetPileIndex);
        		}
        		
        		
        		for(int tempIndex:listTargetPileIndex) {
        			pileNum.add(tempIndex+1);
        		}    		

                canBeMoveToTab = true;
                break;
            }
        }

        if (!listTargetPileIndex.isEmpty()) {           
        	scanCardNextMoveMssg = "03 WASTOTAB : "+card+" can move to pile "+pileNum;
        } else {
        	scanCardNextMoveMssg ="10 NOACTION  : "+card+" cannot move to tabPile";
        }				
		
		ScanTempInfo scanData = new ScanTempInfo(card,scanCardNextMoveMssg,listTargetPileIndex,ctrMove);
		cardScanTab.add(scanData);
		
		return cardScanTab;
		
	}
	
	public static List<String> getListNextCard(String card) {
		List<String> tempListPreCard = new ArrayList<>();
		for(CardProperties prop : cardPropertyList) {
			if(card.equals(prop.getCardValue())){
				tempListPreCard = prop.getListValidNextCard();
				break;
			}
		}		
		return tempListPreCard;
	}
	
	
	public static List<Integer> getIndexInLastElements(String card,List<String> lastElements,List<Integer> listTargetPileIndex) {
		int index = lastElements.indexOf(card);
        if (index != -1) {
//            System.out.println("Value found at index: " + index);
            listTargetPileIndex.add(index);
        } else {
//            System.out.println("Value not found in the list.");
        }
        return listTargetPileIndex;
	}
	
	public static List<String> buildListLastElement() {
        List<String> lastElements = new ArrayList<>();
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
	
	
	public static int searchIndexIntabOBCardsList(String card) {
		int rowIndex = -1;
        for (int i = 0; i < ProcessGame.tabPileByColumn.size(); i++) {
            ArrayList<String> row = ProcessGame.tabPileByColumn.get(i);
            if (row.contains(card)) {
                rowIndex = i;
                break;
            }
        }

        if (rowIndex != -1) {
            System.out.println("Found at row index: " + rowIndex);
        } else {
            System.out.println("Value not found in the ArrayList.");
        }
		
        return rowIndex;
	
	}
	
	public static List<ScanTempInfo> scanCardKing(String card) {
		char suit = card.charAt(2);
		String suitS = Character.toString(suit);
		cardIsKingToMove = false;
		scanKingToMoveMssg = "";
		List<Integer> listTargetPileIndex = new ArrayList<>();
		List<ScanTempInfo> cardScanKing = new ArrayList<>();
		
		ArrayList<Integer> emptyRowIndices = checkPileWithNoElement();
		
		if(suitS.equals("K") && emptyRowIndices.size() > 0) {
			scanKingToMoveMssg = "04 KWNWPILE : Card "+card+" can be move to blank pile. Active Pile count: "+(7-emptyRowIndices.size());
			cardIsKingToMove = true;
		} else {
			scanKingToMoveMssg = "10 NOACTION  : Active pile count is "+(7-emptyRowIndices.size())+" ";
		}		
		
		ScanTempInfo scanData = new ScanTempInfo(card,scanKingToMoveMssg,emptyRowIndices,ctrMove );
		cardScanKing.add(scanData);
		
		return cardScanKing;
	}

	public static ArrayList<Integer> checkPileWithNoElement() {
		ArrayList<Integer> emptyRowIndices = new ArrayList<>();
		for (int i = 0; i < ProcessGame.tabPileByColumn.size(); i++) {
		    ArrayList<String> rowData = ProcessGame.tabPileByColumn.get(i);
		    if (rowData.isEmpty()) {
		        emptyRowIndices.add(i);
		    }
		}
		return emptyRowIndices;
	}
	
	
	public static List<ScanTempInfo> getCardScanOutput() {
		return cardScanOutput;
	}


	public static String getCardScanOutputMssg() {
		return cardScanOutputMssg;
	}
	
	
	
}
