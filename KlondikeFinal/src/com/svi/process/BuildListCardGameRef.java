package com.svi.process;

import java.util.ArrayList;
import java.util.List;

import com.svi.object.CardGameInfo;
import com.svi.object.CardProperties;

public class BuildListCardGameRef {
	
	static List<CardGameInfo> listGameCardsRef = new ArrayList<>();
	
	public static List<CardGameInfo> buildList(List<CardProperties> cardPropertyList,ArrayList<ArrayList<String>> tabPileByColumn,ArrayList<String> stockCards) {
		
		buildListTabCardGameRef(cardPropertyList,tabPileByColumn);
		buildListStockCardGameRef(cardPropertyList,stockCards);
//		dispCardGameData();
		
		return listGameCardsRef;
	}

	public static void dispCardGameData() {
		System.out.println("\nlistGameCardsRef");
		for(CardGameInfo data: listGameCardsRef) {
			System.out.println(data.getCardValue()+"; "+data.getCardStat()+"; "+data.getCardLoc()+"; "+data.getCardPile()
				+"; "+data.getPileSeqNum()+"; "+data.getLastMove());
		}
	}
		
	public static void buildListTabCardGameRef(List<CardProperties> cardPropertyList,ArrayList<ArrayList<String>> tabPileByColumn) {

		String cardValue = "";
		String cardStat ="";
		String cardLoc ="";
		int cardPile = 0;
		int pileSeqNum = 0;
		String lastMove ="";

		int pileNum = 0;
		
		for(ArrayList<String> rowData: tabPileByColumn ) {
			pileNum++;
			int lastIndex = rowData.size() - 1;
			int currentIndex = 0;
			
			if(!rowData.isEmpty() ) {
				
				for(String data: rowData) {
					cardValue = data;
					
					if (currentIndex == lastIndex) 
						cardStat = "O";
					else
						cardStat ="X";
					
					cardLoc ="tab";
					cardPile = pileNum;
					pileSeqNum = currentIndex;

					CardGameInfo cardGameData = new CardGameInfo(cardValue,cardStat,cardLoc,cardPile,pileSeqNum,lastMove);
					listGameCardsRef.add(cardGameData);					
					currentIndex++;	
				}
				
			}
			
		}
		
	}
	
	public static void buildListStockCardGameRef(List<CardProperties> cardPropertyList,ArrayList<String> stockCards) {
		String cardValue = "";
		String cardStat ="";
		String cardLoc ="";
		int cardPile = 0;
		int pileSeqNum = 0;
		String lastMove ="";
		
		int ctrCard = 0;

		for (String stockData: stockCards) {
			ctrCard++;
			cardValue = stockData;
			cardStat ="X";
			cardLoc ="sto";
			cardPile = 0;
			pileSeqNum = ctrCard;
			
			CardGameInfo cardGameData = new CardGameInfo(cardValue,cardStat,cardLoc,cardPile,pileSeqNum,lastMove);
			listGameCardsRef.add(cardGameData);
		}
			
		
	}
	
	


}
