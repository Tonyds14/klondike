package com.svi.process;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svi.main.KlondikeMain;
import com.svi.object.CardGameInfo;
import com.svi.object.CardProperties;
import com.svi.object.ScanTempInfo;

public class ProcessGame {
	
	static boolean gameContinue = true;
	static boolean tabChange = false;
	static boolean wasteChange = false;
	
	static String moveMssg ="";
	static int ctrMove = 0;
	static int ctrNoMove = 0;

	static ArrayList<String> wasteCards =new ArrayList<>();
	static String wasteActiveCard ="";
	
	static String cardScanOutputMssg = "";
	static List<ScanTempInfo> cardScanOutput = new ArrayList<>();
	
	static List<ScanTempInfo> scanStockCardsList = new ArrayList<>();
	static List<ScanTempInfo> scanOutputList = new ArrayList<>();
	static List<ScanTempInfo> actionList = new ArrayList<>();
	static Queue<ScanTempInfo> actionQueue = new LinkedList<>();
	static List<ScanTempInfo> processedList = new ArrayList<>();
	
	static ArrayList<ArrayList<String>> tabXCards = new ArrayList<>();
	static ArrayList<ArrayList<String>> tabOCards = new ArrayList<>();
	static ArrayList<ArrayList<String>> tabOBCards = new ArrayList<>();
	static ArrayList<ArrayList<String>> tabOMCards = new ArrayList<>();
//	static List<String> tabActiveCardsList = new ArrayList<>();
	static List<List<String>> activeCardsListValidPre = new ArrayList<>();
	static List<List<String>> activeCardsListValidNext = new ArrayList<>();
	static LinkedHashSet<String> openMidCards = new LinkedHashSet<>();
	static LinkedHashSet<String> openBottomCards = new LinkedHashSet<>();
	static List<String> lastElements = new ArrayList<>();
	static List<String> listValidKingToMove = new ArrayList<>();
	
	static ArrayList<ArrayList<String>> acePile = new ArrayList<>();
	static ArrayList<String> acePileActiveCards = new ArrayList<>();
	static ArrayList<String> aceActiveCardsNextCardList = new ArrayList<>();
	
	static ArrayList<ArrayList<String>> tabPileByColumn = new ArrayList<>();
	static ArrayList<String> stockCards = new ArrayList<>();
	static List<CardGameInfo> listGameCardsRef = new ArrayList<>();
	static List<CardProperties> cardPropertyList  = new ArrayList<>();
	static int talonCount = 0;
	static boolean processTab = true;
	static boolean processWaste = true;
	static boolean lastCheckTab = false;
	static boolean tabPileEmpty = false;
	static boolean gameWon = false;
	static boolean flipWasteCards = false;
	static boolean stockNoMove = false;
	static String tempProcessedTabCard = "";
	static String tempDrawnWasteCard = "";
	static String tempTabActionListMssg = "";
	
	
	public static void processGame() {
	
		tabPileByColumn = KlondikeMain.getTabPileByColumn();
		stockCards = KlondikeMain.getStockCards();
		listGameCardsRef = KlondikeMain.getListGameCardsRef();
		cardPropertyList = KlondikeMain.getCardPropertyList();
		talonCount = KlondikeMain.getTalonCount();			
		
		
		while(gameContinue) {	
		
			while(processTab || lastCheckTab) {	
				buildAndScanTabCards();							
				if(!actionQueue.isEmpty()) {
					processQueueBuildScanNext();
					ctrNoMove = 0;
				} else {
					ctrNoMove++;
					processTab = false;
					processWaste = true;
					tabPileEmpty = checkAllInnerListOfTabPileIsEmpty();
					if(stockCards.isEmpty()&& wasteCards.isEmpty()&&tabPileEmpty) {
						processTab = false;
						lastCheckTab = false;
						processWaste = false;
						gameWon = true;
						
					}
				}
			}
			
			while(processWaste) {  
				buildAndScanWasteCards();	
				if(!actionQueue.isEmpty()) {
					processWasteQueueBuildScanNext();
					ctrNoMove = 0;
				} else {
					ctrNoMove++;
					processWaste = false;
					processTab = true;
					if(tempTabActionListMssg == "no available move") {
						processTab = false;
					}
					tabPileEmpty = checkAllInnerListOfTabPileIsEmpty();
					if(stockCards.isEmpty()&&wasteCards.isEmpty()&& !tabPileEmpty) {
						lastCheckTab = true;
					} else {
						lastCheckTab = false;
					}
				}
				
			}									
			
			if(ctrNoMove>5 && stockCards.isEmpty()) {
				gameWon = false;
				processTab = false;
				processWaste = false;
//				checkPossibleMoveForStockCards();
//				if(stockNoMove) {
//					gameWon = false;
//					processTab = false;
//					processWaste = false;
//				} else {
//					processTab = true;
//					processWaste = true;
//				}
				
			}
						
			if(lastCheckTab) {
				processTab = true;
			}				
			
			if(gameWon) {
				gameContinue = false;				
			} else {
				if(!processTab && !processWaste) {
					gameContinue = false;
				}
			}
		}
		
		if(gameWon) {
			System.out.println("game ended - won");
		} else {
			System.out.println("game ended - lose");
		}
		
	}
	
	
	public static void checkPossibleMoveForStockCards() {
		buildTabActiveCardList();
		preScanStockAndWasteCards();
		buildActionList();
		if(actionList.isEmpty()) {
			stockNoMove = true;
		} else {
			stockNoMove = false;
			actionList.clear();
		}
		
		
	}
	
	public static void preScanStockAndWasteCards() {
		scanOutputList.clear();
		//card assessment should be perform to elements based on talon count
		//need to build list - to get elements in stockCards and wasteCards that will be for assessment
		if (!stockCards.isEmpty()) {
			for(String stock: stockCards) {
				PerformWasteCardScanV2.performCardScan(stock);//			 
				cardScanOutput = PerformWasteCardScanV2.getCardScanOutput();
				cardScanOutputMssg = PerformWasteCardScanV2.getCardScanOutputMssg();	
				scanOutputList.addAll(cardScanOutput);
				insertSort(scanOutputList);
			}			
		}
		
		if (!wasteCards.isEmpty()) {
			for(String waste: wasteCards) {
				PerformWasteCardScanV2.performCardScan(waste);//			 
				cardScanOutput = PerformWasteCardScanV2.getCardScanOutput();
				cardScanOutputMssg = PerformWasteCardScanV2.getCardScanOutputMssg();	
				scanOutputList.addAll(cardScanOutput);
				insertSort(scanOutputList);
			}			
		}
		
	}

	
	public static boolean checkAllInnerListOfTabPileIsEmpty() {
		boolean allInnerListsEmpty = tabPileByColumn.stream().allMatch(ArrayList::isEmpty);
		return allInnerListsEmpty;
	}
	
	public static void buildAndScanTabCards() {
		buildTabActiveCardList();		
		scanAndBuildActionForTabActiveCards();		
//		printActionList();
	}
	
	public static void buildAndScanWasteCards() {
		buildTabActiveCardList(); // added 07/04
		
		buildWasteActiveCard();	
		if(!wasteCards.isEmpty()) {
			displayWasteCardDraw();				
			scanAndBuildActionForWasteActiveCard();				
			printScanOutputList();
		}
			
	}
	
	
	public static void processWasteQueueBuildScanNext() {

		while (!actionQueue.isEmpty()) {

			ScanTempInfo queue = actionQueue.poll();
			String processName = queue.getScanMssg().substring(0,11).trim();
			String holdCard = queue.getCardValue();
			String scanMssg = queue.getScanMssg();
			List<Integer> targetPileIndex =queue.getTargetPileIndex();		

			processActionQueue(processName,holdCard,scanMssg,targetPileIndex);
			
			ctrMove++;
			BuildConsoleMssg.buildMessage(ctrMove, tabPileByColumn, stockCards, wasteCards, acePile, moveMssg, listGameCardsRef);

			actionList.remove(queue);
			processedList.add(queue);			
			
		}		

	}	
	
	
	public static void getNextWasteActiveCard() {
		if(!wasteCards.isEmpty()) {
			wasteActiveCard = wasteCards.get(0);
		}
	}
	
	
	public static void buildWasteActiveCard() {
		
		if(!wasteCards.isEmpty() && stockCards.isEmpty() && actionQueue.isEmpty()) {
			flipStockAndWasteIfEmpyWasteCards();
			processTab = false;
			processWaste = true;
		}
		
		
		ArrayList<String> holdWasteCard = new ArrayList<>();
		for (int i = 0; i < talonCount; i++) {
		    if (!stockCards.isEmpty()) {
		        String card = stockCards.remove(0);
		        holdWasteCard.add(0, card);
		    } else {
		    	System.out.println("no more cards in stock pile.");
		    	flipWasteCards = true;
		        break;
		    }
		}
		wasteCards.addAll(0, holdWasteCard);
		System.out.println("holdWasteCards: "+holdWasteCard);

		if(!wasteCards.isEmpty()) {
			wasteActiveCard = wasteCards.get(0);
		}
		
	}
	
	public static void scanWasteActiveCard() {
		scanOutputList.clear();
		if(!wasteActiveCard.isEmpty()) {
			PerformWasteCardScanV2.performCardScan(wasteActiveCard);//			 
			cardScanOutput = PerformWasteCardScanV2.getCardScanOutput();
			cardScanOutputMssg = PerformWasteCardScanV2.getCardScanOutputMssg();	
			scanOutputList.addAll(cardScanOutput);
			
		}
	}
	
	
	
	public static void displayWasteCardDraw() {
		ctrMove++;
		String moveMssg = wasteActiveCard +" drawn";
		BuildConsoleMssg.buildMessage(ctrMove, tabPileByColumn, stockCards, wasteCards, acePile, moveMssg, listGameCardsRef);
	
		if(tempDrawnWasteCard.equals(wasteActiveCard)) {
			processTab = true;
			processWaste = false;
			tempDrawnWasteCard = "";
			actionQueue.clear();
//			processNextWasteCard = false;
		} else {
			tempDrawnWasteCard = wasteActiveCard;
		}
	}
	
	public static void processQueueBuildScanNext() {
		while (!actionQueue.isEmpty()) {			
			
			ScanTempInfo queue = actionQueue.poll();
			String processName = queue.getScanMssg().substring(0,11).trim();
			String holdCard = queue.getCardValue();
			String scanMssg = queue.getScanMssg();
			List<Integer> targetPileIndex =queue.getTargetPileIndex();			
			
			if(tempProcessedTabCard.equals(holdCard)) {
				processTab = false;
				tempProcessedTabCard = "";
				processWaste = true;
				actionQueue.clear();
			} else {
				tempProcessedTabCard = holdCard;
				
				processActionQueue(processName,holdCard,scanMssg,targetPileIndex);
				ctrMove++;
				BuildConsoleMssg.buildMessage(ctrMove, tabPileByColumn, stockCards, wasteCards, acePile, moveMssg, listGameCardsRef);
				
				actionList.remove(queue);
				processedList.add(queue);	
				
			}
	        
		}	
		
	}
	
	
	
	public static void buildTabActiveCardList() {
		BuildTabActiveCardsList.buildTabActiveCardsList();
		tabXCards = BuildTabActiveCardsList.getTabXCards();
		tabOCards = BuildTabActiveCardsList.getTabOCards();
		tabOMCards = BuildTabActiveCardsList.getTabOMCards();
		tabOBCards = BuildTabActiveCardsList.getTabOBCards();
		activeCardsListValidPre = BuildTabActiveCardsList.getActiveCardsListValidPre();
		activeCardsListValidNext = BuildTabActiveCardsList.getActiveCardsListValidNext();	
		openMidCards = BuildTabActiveCardsList.getOpenMidCards();
		openBottomCards = BuildTabActiveCardsList.getOpenBottomCards();
		lastElements = BuildTabActiveCardsList.getLastElements();
		listValidKingToMove = BuildTabActiveCardsList.getListValidKingToMove();
		
		
	}
		
	
	public static void scanAndBuildActionForWasteActiveCard() {
		scanWasteActiveCard();
		buildWasteActionList();			
		buildWasteActionQueue();
		
	}
	
	public static void buildWasteActionQueue() {
        for (ScanTempInfo scanTempInfo : actionList) {
            actionQueue.add(scanTempInfo);
        }        
        actionQueue.removeAll(processedList);
	}
	
	
	public static void buildWasteActionList() {
		actionList.clear();
		for (ScanTempInfo scanTempInfo : scanOutputList) {
		    if (!scanTempInfo.getScanMssg().contains("NOACTION")) {
		        actionList.add(scanTempInfo);
		    }
		}
		actionList.removeAll(processedList);		
	        
    }

	
	public static void flipStockAndWasteIfEmpyWasteCards() {		
		if(stockCards.isEmpty()&& actionQueue.isEmpty()) {
			System.out.println("stock cards empty. flip waste and stock cards");
			for (int i = wasteCards.size() - 1; i >= 0; i--) {
			    stockCards.add(wasteCards.get(i));
			}
			wasteCards.clear();
		}		
		
	}
	
	
	public static void scanAndBuildActionForTabActiveCards() {
		scanOutputList.clear();
		scanOpenMidCards();	
		scanOpenBottomCards();
		buildActionList();			
		buildActionQueue();
		
	}
	
		
	public static void printWorkingArrayListRef() {
		System.out.println("openMidCards: "+openMidCards);
		System.out.println("openBottomCards: "+openBottomCards);
		System.out.println("activeCardsListValidPre: "+activeCardsListValidPre);
		System.out.println("activeCardsListValidNext: "+activeCardsListValidNext);
		System.out.println("acePile: "+acePile);
		System.out.println("stockPile: "+stockCards);
		System.out.println("wastePile: "+wasteCards);
	}
	
	
	public static void printActionQueue() {
        System.out.println("\nactionQueue: ");
        for (ScanTempInfo item : actionQueue) {
        	System.out.println(item.getCardValue()+"; "+item.getScanMssg()+"; "+item.getCtrMove());
        }
	}
	
	public static void printScanOutputList() {
        System.out.println("scanOutputList");
        for (ScanTempInfo item : scanOutputList) {
        	System.out.println(item.getCardValue()+"; "+item.getScanMssg()+"; "+item.getCtrMove());
        }
	}
		
	
	public static void printActionList() {
        System.out.println("\nactionList");
        if(!actionList.isEmpty()) {
        	for (ScanTempInfo item : actionList) {
            	System.out.println(item.getCardValue()+"; "+item.getScanMssg()+"; "+item.getCtrMove());
            } 
        	tempTabActionListMssg ="";
        } else {
        	System.out.println("no available move");
        	tempTabActionListMssg = "no available move";
        }
        
	}
	
	
	public static void printProcessedList() {
        System.out.println("\nprocessedList contents");
        for (ScanTempInfo item : processedList) {
        	System.out.println(item.getCardValue()+"; "+item.getScanMssg()+"; "+item.getCtrMove());
        }
	}
	
	
	public static void scanOpenBottomCards() {
		if (!openBottomCards.isEmpty()) {
			for (String activeCard: openBottomCards) { 				
			 PerformTabCardScan.performCardScan(activeCard);//			 
			 cardScanOutput = PerformTabCardScan.getCardScanOutput();
			 cardScanOutputMssg = PerformTabCardScan.getCardScanOutputMssg();			 
			 scanOutputList.addAll(cardScanOutput);
			 insertSort(scanOutputList);			 
			}
		} else {
//			System.out.println("openBottomCardsList is empty.");
		}	
		
	}
	
	public static void scanOpenMidCards() {
		if (!openMidCards.isEmpty()) {
			for (String activeCard: openMidCards) {   			
				 PerformTabCardScan.performCardScan(activeCard);//			 
				 cardScanOutput = PerformTabCardScan.getCardScanOutput();
				 cardScanOutputMssg = PerformTabCardScan.getCardScanOutputMssg();			 
				 scanOutputList.addAll(cardScanOutput);
				 insertSort(scanOutputList);
			}
		} else {
//			System.out.println("openMidCardsList is empty.");
		}	
	}
	
	
	public static void insertSort(List<ScanTempInfo> scanOutputList) {
	    int n = scanOutputList.size();

	    for (int i = 1; i < n; i++) {
	    	
	        ScanTempInfo key = scanOutputList.get(i);
	        int j = i - 1;
	        
	        while (j >= 0 && compareByFirst3Bytes(scanOutputList.get(j), key) > 0) {
	            scanOutputList.set(j + 1, scanOutputList.get(j));
	            j--;
	        }        

	        scanOutputList.set(j + 1, key);
	    }
    }
	
	private static int compareByFirst3Bytes(ScanTempInfo a, ScanTempInfo b) {
	    String aFirst3Bytes = a.getScanMssg().substring(0, 3);
	    String bFirst3Bytes = b.getScanMssg().substring(0, 3);
	    
	    return aFirst3Bytes.compareTo(bFirst3Bytes);
	}
	
	

	public static void buildActionList() {
		actionList.clear();
		
		List<ScanTempInfo> filteredScanList = filterScanOutByNoActionNotInProcessed();

		if(!filteredScanList.isEmpty()) {
			actionList.add(filteredScanList.get(0));
		}

    }
	
	
	public static List<ScanTempInfo> filterScanOutByNoActionNotInProcessed() {
		List<ScanTempInfo> filteredScanList = new ArrayList<>();
		for (ScanTempInfo scanTempInfo : scanOutputList) {
			if (scanTempInfo.getScanMssg().contains("NOACTION")) {
				continue;
			} else {
				if (processedList.contains(scanTempInfo)) {
					continue;
				} else {
					filteredScanList.add(scanTempInfo);
				}
			}		
		}		
		return filteredScanList;
	}	
	
	public static void buildActionQueue() {
        for (ScanTempInfo scanTempInfo : actionList) {
            actionQueue.add(scanTempInfo);
        }
        
        actionQueue.removeAll(processedList);
        
	}
	
	
	public static void processActionQueue(String processName,String holdCard,String scanMssg,List<Integer> targetPileIndex) {
		
		switch(processName) {
		case "01ATABTOACE":
			System.out.println(holdCard+": 01 MOVTOACE PROCESSING Move# "+ctrMove);
			
			ProcessMoveToAce.processMoveToAce(holdCard,processName);
			acePile = ProcessMoveToAce.getAcePile();
			acePileActiveCards = ProcessMoveToAce.getAcePileActiveCards();
			aceActiveCardsNextCardList = ProcessMoveToAce.getAcePileActiveCardsNextCard();
			listGameCardsRef = ProcessMoveToAce.getListGameCardsRef();
			tabPileByColumn = ProcessMoveToAce.getTabPileByColumn();
			moveMssg = ProcessMoveToAce.getAceMssg();
			
			break;
					
		case "01BTABTOACE":
			System.out.println(holdCard+": 02 MOVTOACE PROCESSING Move# "+ctrMove);
				
			ProcessMoveToAce.processMoveToAce(holdCard,processName);
			acePile = ProcessMoveToAce.getAcePile();
			acePileActiveCards = ProcessMoveToAce.getAcePileActiveCards();
			aceActiveCardsNextCardList = ProcessMoveToAce.getAcePileActiveCardsNextCard();
			listGameCardsRef = ProcessMoveToAce.getListGameCardsRef();
			tabPileByColumn = ProcessMoveToAce.getTabPileByColumn();
			moveMssg = ProcessMoveToAce.getAceMssg();
			
			break;
				
		case "03BBOTTOTAB":
			System.out.println(holdCard+": 03 MOVTOTAB PROCESSING Move# "+ctrMove);
			
			ProcessMoveToTab.processMoveToTab(processName, holdCard, targetPileIndex);
			tabPileByColumn = ProcessMoveToTab.getTabPileByColumn();
			listGameCardsRef = ProcessMoveToTab.getListGameCardsRef();
			moveMssg = ProcessMoveToTab.getTabMssg();
					
			break;
		
			
		case "03AMIDTOTAB":
			System.out.println(holdCard+": 03 MIDTOTAB PROCESSING Move# "+ctrMove);
			
			ProcessMoveToTab.processMoveToTab(processName, holdCard, targetPileIndex);
			tabPileByColumn = ProcessMoveToTab.getTabPileByColumn();
			listGameCardsRef = ProcessMoveToTab.getListGameCardsRef();
			moveMssg = ProcessMoveToTab.getTabMssg();
					
			break;
			
		case "02 KTNWPILE":
			System.out.println(holdCard+": 05 KTNWPILE PROCESSING Move# "+ctrMove);
				
			ProcessMoveToTab.processMoveToTab(processName, holdCard, targetPileIndex);
			tabPileByColumn = ProcessMoveToTab.getTabPileByColumn();
			listGameCardsRef = ProcessMoveToTab.getListGameCardsRef();
			moveMssg = ProcessMoveToTab.getTabMssg();
					
			break;
				
		case "01AWASTOACE":
			System.out.println(holdCard+": 01 WASTOACE PROCESSING Move# "+ctrMove);
				
			ProcessMoveToAce.processMoveToAce(holdCard,processName);
			acePile = ProcessMoveToAce.getAcePile();
			acePileActiveCards = ProcessMoveToAce.getAcePileActiveCards();
			aceActiveCardsNextCardList = ProcessMoveToAce.getAcePileActiveCardsNextCard();
			listGameCardsRef = ProcessMoveToAce.getListGameCardsRef();
			wasteCards = ProcessMoveToAce.getWasteCards();
			moveMssg = ProcessMoveToAce.getAceMssg();
//			wasteActiveCard = ProcessMoveToAce.getWasteActiveCard();
					
			break;
			
		case "01BWASTOACE":
			System.out.println(holdCard+": 02 WASTOACE PROCESSING Move# "+ctrMove);
			
			ProcessMoveToAce.processMoveToAce(holdCard,processName);
			acePile = ProcessMoveToAce.getAcePile();
			acePileActiveCards = ProcessMoveToAce.getAcePileActiveCards();
			aceActiveCardsNextCardList = ProcessMoveToAce.getAcePileActiveCardsNextCard();
			listGameCardsRef = ProcessMoveToAce.getListGameCardsRef();
			wasteCards = ProcessMoveToAce.getWasteCards();
			moveMssg = ProcessMoveToAce.getAceMssg();
//			wasteActiveCard = ProcessMoveToAce.getWasteActiveCard();
					
			break;
			
		case "03 WASTOTAB":
			System.out.println(holdCard+": 03 WASTOTAB PROCESSING Move# "+ctrMove);
			
			ProcessMoveToTab.processMoveToTab(processName, holdCard, targetPileIndex);
			tabPileByColumn = ProcessMoveToTab.getTabPileByColumn();
			listGameCardsRef = ProcessMoveToTab.getListGameCardsRef();
			wasteCards = ProcessMoveToTab.getWasteCards();
			moveMssg = ProcessMoveToTab.getTabMssg();
					
			break;
			
		case "04 KWNWPILE":
			System.out.println(holdCard+": 04 KWNWPILE PROCESSING Move# "+ctrMove);
				
			ProcessMoveToTab.processMoveToTab(processName, holdCard, targetPileIndex);
			tabPileByColumn = ProcessMoveToTab.getTabPileByColumn();
			listGameCardsRef = ProcessMoveToTab.getListGameCardsRef();
			moveMssg = ProcessMoveToTab.getTabMssg();
					
			break;
			
		case "10 NOACTION":
			System.out.println(holdCard+": 10 NOACTION PROCESSING Move# "+ctrMove);
				
			break;
				
				
		default:
			System.out.println("processName not define why go here?");
			gameContinue = false;

		}		
				
	}	
	
	
	public static ArrayList<String> getWasteCards() {
		return wasteCards;
	}


	public static String getWasteActiveCard() {
		return wasteActiveCard;
	}


	public static ArrayList<ArrayList<String>> getAcePile() {
		return acePile;
	}


	public static List<ScanTempInfo> getProcessedList() {
		return processedList;
	}


	public static ArrayList<String> getAceActiveCardsNextCardList() {
		return aceActiveCardsNextCardList;
	}



	public static ArrayList<ArrayList<String>> getTabOBCards() {
		return tabOBCards;
	}


	public static ArrayList<ArrayList<String>> getTabOMCards() {
		return tabOMCards;
	}


	public static int getCtrMove() {
		return ctrMove;
	}
	

	public static List<List<String>> getActiveCardsListValidNext() {
		return activeCardsListValidNext;
	}


	public static List<CardGameInfo> getListGameCardsRef() {
		return listGameCardsRef;
	}


	public static ArrayList<ArrayList<String>> getTabPileByColumn() {
		return tabPileByColumn;
	}


	public static LinkedHashSet<String> getOpenMidCards() {
		return openMidCards;
	}


	public static LinkedHashSet<String> getOpenBottomCards() {
		return openBottomCards;
	}


	public static List<List<String>> getActiveCardsListValidPre() {
		return activeCardsListValidPre;
	}	
	
	public static String getMoveMssg() {
		return moveMssg;
	}


	public static List<String> getLastElements() {
		return lastElements;
	}


	public static List<String> getListValidKingToMove() {
		return listValidKingToMove;
	}
	
	
	
}
