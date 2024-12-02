package com.svi.process;

import java.util.ArrayList;

public class ReConstrucTabDataByRow {
	
	private ArrayList<ArrayList<String>> tabPileByRow = new ArrayList<>();
	
	public void reBuildTabPile(ArrayList<ArrayList<String>> tabPileByColumn) {
        int maxColumnCount = getMaxColumnCount(tabPileByColumn); // Get the maximum number of columns
        
        for (int columnIndex = 0; columnIndex < maxColumnCount; columnIndex++) {
            ArrayList<String> column = new ArrayList<>();

            for (ArrayList<String> row : tabPileByColumn) {
                if (columnIndex < row.size()) {
                    column.add(row.get(columnIndex));
                } else {
                    column.add(""); // Add an empty string if the column index is out of bounds for a row
                }
            }
            tabPileByRow.add(column);
        }        
    }
	
    private static int getMaxColumnCount(ArrayList<ArrayList<String>> tabPileByColumn) {
        int maxColumnCount = 0;

        for (ArrayList<String> row : tabPileByColumn) {
            int columnCount = row.size();
            if (columnCount > maxColumnCount) {
                maxColumnCount = columnCount;
            }
        }
        return maxColumnCount;
    }

	public ArrayList<ArrayList<String>> getConvPileCardsList() {
		return tabPileByRow;
	}

}
