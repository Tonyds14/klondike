package com.svi.object;

import java.util.List;

public class CardGameInfo {
	private String cardValue;
	private String cardStat;
	private String cardLoc;
	private int cardPile;
	private int pileSeqNum;
	private String lastMove;
	
	public CardGameInfo(String cardValue,String cardStat,String cardLoc,int cardPile,int pileSeqNum,String lastMove) {
		this.cardValue = cardValue;
		this.cardStat = cardStat;
		this.cardLoc = cardLoc;
		this.cardPile = cardPile;
		this.pileSeqNum = pileSeqNum;
		this.lastMove = lastMove;

	}

	public String getCardValue() {
		return cardValue;
	}

	public void setCardValue(String cardValue) {
		this.cardValue = cardValue;
	}

	public String getCardStat() {
		return cardStat;
	}

	public void setCardStat(String cardStat) {
		this.cardStat = cardStat;
	}

	public String getCardLoc() {
		return cardLoc;
	}

	public void setCardLoc(String cardLoc) {
		this.cardLoc = cardLoc;
	}

	public int getCardPile() {
		return cardPile;
	}

	public void setCardPile(int cardPile) {
		this.cardPile = cardPile;
	}

	public String getLastMove() {
		return lastMove;
	}

	public void setLastMove(String lastMove) {
		this.lastMove = lastMove;
	}


	public int getPileSeqNum() {
		return pileSeqNum;
	}

	public void setPileSeqNum(int pileSeqNum) {
		this.pileSeqNum = pileSeqNum;
	}

	
	
}
