package com.svi.process;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import com.svi.object.CardGameInfo;

public class BuildConsoleMssg {	
	
	public static void buildMessage(int ctrCardMove,ArrayList<ArrayList<String>> tabPileByColumn
					,ArrayList<String> stockCards,ArrayList<String> wasteCards,ArrayList<ArrayList<String>> acePile,String moveMssg
					,List<CardGameInfo> listGameCardsRef) {
		
		
		printHeader(ctrCardMove);
		printAceSection(acePile);
		printTabPile(tabPileByColumn,listGameCardsRef);
		printMove(moveMssg,ctrCardMove);
		printStockWastePile(stockCards,wasteCards);  
		printAcePile(acePile);
//		printFlags(); //parameters: flags
		printSummary(tabPileByColumn,acePile,stockCards,wasteCards); 
		
	}
	
	
	public static void printAcePile(ArrayList<ArrayList<String>> acePile) {
		System.out.println("\tACE PILE :");
		for(ArrayList<String> row: acePile) {
			System.out.println("\t\t"+row);			
		}
	}
	
	public static void printHeader(int ctrCardMove) {
		String startLine = "\n************************************************************************************************************************"; 
		String header = "CARD MOVE COUNT: "+ctrCardMove;
		System.out.println(startLine);
//		System.out.println(header);
	}

	
	public static void printAceSection(ArrayList<ArrayList<String>> acePile) {
		String card8 = "";
		String card9 = "";
		String card10 = "";
		String card11 = "";

		for (ArrayList<String> row : acePile) {
		    if (row != null && !row.isEmpty()) {
		        int lastIndex = row.size() - 1;
		        String lastElement = row.get(lastIndex);
		        if (lastElement != null) {
		            if (card8.isEmpty()) {
		                card8 = lastElement;
		            } else if (card9.isEmpty()) {
		                card9 = lastElement;
		            } else if (card10.isEmpty()) {
		                card10 = lastElement;
		            } else if (card11.isEmpty()) {
		                card11 = lastElement;
		            }
		        }
		    }
		}
		
      String fmtCard8 = String.format("%-4s", card8);
      String fmtCard9 = String.format("%-4s", card9);
      String fmtCard10 = String.format("%-4s", card10);
      String fmtCard11 = String.format("%-4s", card11);
      
      String aceSectionInfo = "\n\t\t\tACE FOUNDATION | "+fmtCard8+" | "+fmtCard9+" | "+fmtCard10+" | "+fmtCard11+" | ";
      System.out.println(aceSectionInfo);
      System.out.println();

	}

	public static void printTabPile(ArrayList<ArrayList<String>> tabPileByColumn,List<CardGameInfo> listGameCardsRef) {
		ArrayList<ArrayList<String>> tabPileByRow = new ArrayList<>();	
		
//		reConstrucTabData(pileCardsList,convPileCardsList);	
		ReConstrucTabDataByRow construct = new ReConstrucTabDataByRow();
		construct.reBuildTabPile(tabPileByColumn);
		tabPileByRow = construct.getConvPileCardsList();
			
//        int rowIndex = 0;
//        for (ArrayList<String> column : tabPileByRow) {
////            System.out.println(column);
//            copyColumnDataToPrintFields(column,tabPileByRow,rowIndex);
//            rowIndex++;
//        }
//        System.out.println();
        
        ArrayList<ArrayList<String>> tabPileByRowStat = new ArrayList<>();
        tabPileByRowStat = relateTabPileForStat(tabPileByRow,listGameCardsRef);
        
        int rowIndex = 0;
        for (ArrayList<String> column : tabPileByRowStat) {
            copyColumnDataToPrintFields(column,tabPileByRowStat,rowIndex);
            rowIndex++;
        }
        System.out.println();

	}
	
	
	public static ArrayList<ArrayList<String>> relateTabPileForStat(ArrayList<ArrayList<String>> tabPileByRow,List<CardGameInfo> listGameCardsRef) {
		ArrayList<ArrayList<String>> tabPileByRowStat = new ArrayList<>();
		
        for (ArrayList<String> innerList : tabPileByRow) {
            ArrayList<String> updatedRow = new ArrayList<>();
            
            for (String cardValue : innerList) {
                CardGameInfo cardGameInfo = findCardGameInfo(listGameCardsRef, cardValue);
                if (cardGameInfo != null) {
                    String updatedValue = cardValue +" "+ cardGameInfo.getCardStat();
                    updatedRow.add(updatedValue);
                } else {
                    updatedRow.add(cardValue); // Add original value if no match found
                }
            }
            
            tabPileByRowStat.add(updatedRow);
        }
        
        // Print the updated tabPileByRowStat
//        System.out.println("tabPileByRowStat");
//        for (ArrayList<String> innerList : tabPileByRowStat) {
//            System.out.println(innerList);
//        }
        
        return tabPileByRowStat;
		
	}
	
	private static CardGameInfo findCardGameInfo(List<CardGameInfo> listGameCardsRef, String cardValue) {
        for (CardGameInfo cardGameInfo : listGameCardsRef) {
            if (cardGameInfo.getCardValue().equals(cardValue)) {
                return cardGameInfo;
            }
        }
        return null;
    }
	
	
	public static ArrayList<ArrayList<String>> reConstrucTabData(ArrayList<ArrayList<String>> pileCardsList,ArrayList<ArrayList<String>> convPileCardsList) {
        int maxColumnCount = getMaxColumnCount(pileCardsList); // Get the maximum number of columns
        
        for (int columnIndex = 0; columnIndex < maxColumnCount; columnIndex++) {
            ArrayList<String> column = new ArrayList<>();

            for (ArrayList<String> row : pileCardsList) {
                if (columnIndex < row.size()) {
                    column.add(row.get(columnIndex));
                } else {
                    column.add(""); // Add an empty string if the column index is out of bounds for a row
                }
            }
            convPileCardsList.add(column);
        }        
        return convPileCardsList;
    }

	
    //method to get the maximum number of columns in the original 2D ArrayList
    private static int getMaxColumnCount(ArrayList<ArrayList<String>> pileCardsList) {
        int maxColumnCount = 0;

        for (ArrayList<String> row : pileCardsList) {
            int columnCount = row.size();
            if (columnCount > maxColumnCount) {
                maxColumnCount = columnCount;
            }
        }
        return maxColumnCount;
    }
	   
    
    public static void copyColumnDataToPrintFields(ArrayList<String> column,ArrayList<ArrayList<String>> convPileCardsList,int rowIndex) {
      String card1 =getColumnElement(convPileCardsList, rowIndex, 0);
      String card2 =getColumnElement(convPileCardsList, rowIndex, 1);
      String card3 =getColumnElement(convPileCardsList, rowIndex, 2);
      String card4 =getColumnElement(convPileCardsList, rowIndex, 3);;
      String card5 =getColumnElement(convPileCardsList, rowIndex, 4);
      String card6 =getColumnElement(convPileCardsList, rowIndex, 5);;
      String card7 =getColumnElement(convPileCardsList, rowIndex, 6);       
      String fmtCard1 = String.format("%-7s", card1);
      String fmtCard2 = String.format("%-7s", card2);
      String fmtCard3 = String.format("%-7s", card3);
      String fmtCard4 = String.format("%-7s", card4);
      String fmtCard5 = String.format("%-7s", card5);
      String fmtCard6 = String.format("%-7s", card6);
      String fmtCard7 = String.format("%-7s", card7);
              
      String detail1 = "\t| "+fmtCard1+"| "+fmtCard2+"| "+fmtCard3+"| "+fmtCard4+"| "+fmtCard5+"| "+fmtCard6+"| "+fmtCard7+"|";
    	System.out.println(detail1);
    }
	
    
    private static String getColumnElement(ArrayList<ArrayList<String>> convPileCardsList, int columnIndex, int rowIndex) {
        ArrayList<String> column = convPileCardsList.get(columnIndex);

        if (rowIndex < column.size()) {
            return column.get(rowIndex);
        } else {
            return ""; // Return an empty string if the row index is out of bounds for the column
        }
    }
    
    
	public static void printMove(String moveMssg,int ctrCardMove) {
		String moveDtls =moveMssg;
//		System.out.println("Move : "+moveDtls);
        System.out.println("Move "+ctrCardMove+" : "+moveDtls);
	}
	
	
	public static void printStockWastePile(ArrayList<String> stockCards,ArrayList<String> wasteCards) {
		String dispStockPile = String.join(", ", stockCards);
		
		String dispWastePile ="";
		if (wasteCards.isEmpty()) {
			dispWastePile = "no cards drawn yet";
		} else
			dispWastePile = String.join(", ", wasteCards);

		
		
        String detail21 = "\tSTOCK PILE : "+dispStockPile;
        String detail22 = "\tWASTE PILE : "+dispWastePile;
        System.out.println(detail21);
        System.out.println(detail22);
		
	}
	
	public static void printFlags() {
        String flgInd = "flgTabChange, flgWasteChange,flgAceChange, flgGameContinue";
        System.out.println(flgInd);
	}
	
	
	
	public static void printSummary(ArrayList<ArrayList<String>> pileCardsList,ArrayList<ArrayList<String>> acePile,ArrayList<String> stockCards,ArrayList<String> wasteCards) {
		int elementCount = 0;		
		for (ArrayList<String> row : pileCardsList) {
            elementCount += row.size();
        }
		int pileCardsCount = elementCount;
		
		elementCount = 0;		
		for (ArrayList<String> row : acePile) {
            elementCount += row.size();
        }
		int acePileCount = elementCount;
	
        String summary1 = "\nTALLY CARDS COUNT PER PILE:";
        String summary2 = "\tTAB: "+pileCardsCount;
        String summary3 = "\tACE: "+acePileCount;
        String summary4 = "\tSTOCK: "+stockCards.size();
        String summary5 = "\tWASTE: "+wasteCards.size();
        
        String endLine = "\n------------------------------------------------------------------------------------------------------------------------";     

//        System.out.println(summary1);
//        System.out.println(summary2);
//        System.out.println(summary3);
//        System.out.println(summary4);
//        System.out.println(summary5);
        System.out.println(endLine);
	}
}
