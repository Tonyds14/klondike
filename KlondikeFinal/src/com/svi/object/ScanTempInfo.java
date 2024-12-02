package com.svi.object;

import java.util.List;

public class ScanTempInfo {

	private String cardValue;
	private String scanMssg;
	private List<Integer> targetPileIndex;
	private int ctrMove;
	
	public ScanTempInfo (String cardValue, String scanMssg,List<Integer> targetPileIndex,int ctrMove) {
		this.cardValue = cardValue;
		this.scanMssg = scanMssg;
		this.targetPileIndex = targetPileIndex;
		this.ctrMove = ctrMove;
	}

	public String getCardValue() {
		return cardValue;
	}

	public void setCardValue(String cardValue) {
		this.cardValue = cardValue;
	}

	public String getScanMssg() {
		return scanMssg;
	}

	public void setScanMssg(String scanMssg) {
		this.scanMssg = scanMssg;
	}



	public List<Integer> getTargetPileIndex() {
		return targetPileIndex;
	}

	public void setTargetPileIndex(List<Integer> targetPileIndex) {
		this.targetPileIndex = targetPileIndex;
	}	

	public int getCtrMove() {
		return ctrMove;
	}

	public void setCtrMove(int ctrMove) {
		this.ctrMove = ctrMove;
	}	
	
}
