package com.svi.object;

import java.util.List;

public class CardProperties {
	private int cardID;
	private String cardValue;
	private String cardSuit;
	private int rankSeq;
	private List<String> listValidPreCard;
	private String validNextAceRef;
	private List<String> listValidNextCard;
	
    public CardProperties (int cardID, String cardValue, String cardSuit, int rankSeq
				, List<String> listValidPreCard, String validNextAceRef,List<String> listValidNextCard) {
    	this.cardID = cardID;
    	this.cardValue = cardValue;
    	this.cardSuit = cardSuit;
    	this.rankSeq = rankSeq;     
    	this.listValidPreCard = listValidPreCard;
    	this.validNextAceRef = validNextAceRef;
    	this.listValidNextCard = listValidNextCard; 
    }

	public int getCardID() {
		return cardID;
	}

	public void setCardID(int cardID) {
		this.cardID = cardID;
	}

	public String getCardValue() {
		return cardValue;
	}

	public void setCardValue(String cardValue) {
		this.cardValue = cardValue;
	}

	public String getCardSuit() {
		return cardSuit;
	}

	public void setCardSuit(String cardSuit) {
		this.cardSuit = cardSuit;
	}

	public int getRankSeq() {
		return rankSeq;
	}

	public void setRankSeq(int rankSeq) {
		this.rankSeq = rankSeq;
	}

	public List<String> getListValidPreCard() {
		return listValidPreCard;
	}

	public void setListValidPreCard(List<String> listValidPreCard) {
		this.listValidPreCard = listValidPreCard;
	}

	public String getValidNextAceRef() {
		return validNextAceRef;
	}

	public void setValidNextAceRef(String validNextAceRef) {
		this.validNextAceRef = validNextAceRef;
	}

	public List<String> getListValidNextCard() {
		return listValidNextCard;
	}

	public void setListValidNextCard(List<String> listValidNextCard) {
		this.listValidNextCard = listValidNextCard;
	}    


    
    

}
