package com.svi.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import com.svi.object.CardProperties;

public class BuildCardPropertyList {

	static List<CardProperties> cardPropertyList = new ArrayList<>();
	
	public static List<CardProperties> buildCardProperty(String[] shuffledCards) {
		int cardID =0;
		String cardValue ="";
		String cardSuit = "";
		int rankSeq =0;
		List<String> listValidPreCard = null;
		String validNextAceRef ="";
		List<String> listValidNextCard = null;
		
	    for (String card: shuffledCards) {
	    	if(card!=null) {
	    		cardID++;
	    		cardValue = card;
	    		
	    		StringTokenizer tokenizer = new StringTokenizer(card, "-");
	    		String isuit = tokenizer.nextToken();
	    		String irank = tokenizer.nextToken();
	    		
	    		GetCardSuitCardRankSeq(isuit, irank);
	    		cardSuit = CardSuitRank.getCardSuit();
	    		rankSeq = CardSuitRank.getRankSeq();
	    		
	    		listValidPreCard = GenerateValidNextList(isuit, irank);
	    		
	    		validNextAceRef = isuit+"-"+GetPriorRank(irank);
	    		
	    		listValidNextCard = GeneratelistValidNextCard(isuit, irank);
	    		
//	    		//build data to add in cardPropertyList
	    		CardProperties cardProp = new CardProperties(cardID,cardValue,cardSuit,rankSeq
	    				,listValidPreCard,validNextAceRef,listValidNextCard);
	    		cardPropertyList.add(cardProp);	    		
	    	}	    	
	    }
	    
	    //sort cardPropertyList by suit and rankSeq
	    Collections.sort(cardPropertyList, Comparator
                .comparing(CardProperties::getCardSuit)
                .thenComparing(CardProperties::getRankSeq));
	    
//	    displayCardPropertyList();
	    
		return cardPropertyList;
	}
	
	
	public static CardSuitRank GetCardSuitCardRankSeq(String isuit, String irank) {
	    String cardSuit ="";
	    int rankSeq = 0;
		
		switch (isuit) {
		case "D" :
			cardSuit = "D";
			break;
		case "H" :
			cardSuit = "H";
			break;
		case "S" :
			cardSuit = "S";
			break;
		case "C" :
			cardSuit = "C";
			break;			
		}
		
		switch(irank) {
		case "K":
			rankSeq = 13;
			break;
		case "Q":
			rankSeq = 12;
			break;
		case "J":
			rankSeq = 11;
			break;
		case "10":
			rankSeq = 10;
			break;
		case "9":
			rankSeq = 9;
			break;
		case "8":
			rankSeq = 8;
			break;
		case "7":
			rankSeq = 7;
			break;
		case "6":
			rankSeq = 6;
			break;
		case "5":
			rankSeq = 5;
			break;
		case "4":
			rankSeq = 4;
			break;
		case "3":
			rankSeq = 3;
			break;
		case "2":
			rankSeq = 2;
			break;
		case "A":
			rankSeq = 1;
			break;
		}		
		return new CardSuitRank(cardSuit, rankSeq);			
	}	
	
	
	public static class CardSuitRank {
	    private static String CardSuit;
	    private static int rankSeq;

	    @SuppressWarnings("static-access")
		public CardSuitRank(String CardSuit, int rankSeq) {
	        this.CardSuit = CardSuit;
	        this.rankSeq = rankSeq;
	    }

	    public static String getCardSuit() {
	        return CardSuit;
	    }

	    public static int getRankSeq() {
	        return rankSeq;
	    }
	}

	
	public static List<String> GeneratelistValidNextCard(String isuit, String irank) {
		List<String> validNextCards = new ArrayList<>();
		
		List<String> suitNextList = GetNextSuit(isuit);		
		String nextRank = GetPriorRank(irank);
		
		for(String suit: suitNextList) {
			String validNextCard = suit + "-" + nextRank;
		    validNextCards.add(validNextCard);
		}
		
		return validNextCards;
	}
	
	public static List<String> GenerateValidNextList(String isuit, String irank) {
		List<String> validNextCards = new ArrayList<>();
		
		List<String> suitNextList = GetNextSuit(isuit);		
		String nextRank = GetNextRank(irank);
		
		for(String suit: suitNextList) {
			String validNextCard = suit + "-" + nextRank;
		    validNextCards.add(validNextCard);
		}
		
		return validNextCards;
	}
	
	
	public static List<String> GetNextSuit(String inputSuit) {
		List<String> nextSuit = new ArrayList<>();
		
		switch(inputSuit) {
		case "D" :
			nextSuit.add("C");
			nextSuit.add("S");
			break;
		case "H" :
			nextSuit.add("C");
			nextSuit.add("S");
			break;
		case "C" :
			nextSuit.add("D");
			nextSuit.add("H");
			break;
		case "S" :
			nextSuit.add("D");
			nextSuit.add("H");
			break;
		}		
		return nextSuit;
		
	}	

	
	public static String GetNextRank(String inputRank) {
		String nextRank = "";
		
		switch(inputRank) {
		case "K":
			nextRank = "Q";
			break;
		case "Q":
			nextRank = "J";
			break;
		case "J":
			nextRank = "10";
			break;
		case "10":
			nextRank = "9";
			break;
		case "9":
			nextRank = "8";
			break;
		case "8":
			nextRank = "7";
			break;
		case "7":
			nextRank = "6";
			break;
		case "6":
			nextRank = "5";
			break;
		case "5":
			nextRank = "4";
			break;
		case "4":
			nextRank = "3";
			break;
		case "3":
			nextRank = "2";
			break;
		case "2":
			nextRank = "A";
			break;
		case "A":
			nextRank = "0";
			break;
		}
		return nextRank;
	}
	
	public static String GetPriorRank(String inputRank) {
		String nextRank = "";
		
		switch(inputRank) {
		case "K":
			nextRank = "0";
			break;
		case "Q":
			nextRank = "K";
			break;
		case "J":
			nextRank = "Q";
			break;
		case "10":
			nextRank = "J";
			break;
		case "9":
			nextRank = "10";
			break;
		case "8":
			nextRank = "9";
			break;
		case "7":
			nextRank = "8";
			break;
		case "6":
			nextRank = "7";
			break;
		case "5":
			nextRank = "6";
			break;
		case "4":
			nextRank = "5";
			break;
		case "3":
			nextRank = "4";
			break;
		case "2":
			nextRank = "3";
			break;
		case "A":
			nextRank = "2";
			break;
		}
		return nextRank;
	}
	

	public static void displayCardPropertyList() {
	    System.out.println("\nBuildCardPropertyList: cardPropertyList");
	    for (CardProperties cardProp: cardPropertyList) {
	    	System.out.println(cardProp.getCardID()+"; "+cardProp.getCardValue()+"; "+cardProp.getCardSuit()
	    	+"; "+cardProp.getRankSeq()+"; "+cardProp.getValidNextAceRef()+"; "+cardProp.getListValidPreCard()
	    	+"; "+cardProp.getListValidNextCard());
	    }		
	}
	
}
