package alphabetizenator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Functionality {
	// Global variables
	static List<String> lEntries;
	static BufferedReader brReader;
	static GUI gui;

	public static void main(String[] args) {
		//Instantiate the GUI
		gui = new GUI();
	}

	public static void readFile(File file) {
		lEntries = new LinkedList<String>();
		String strTemp;
		
		// Declare the flag for showing whether or not
		// any further manipulation of the list is required
		boolean bEntriesUnsorted;

		// Try to read the file from gui
		try {
			brReader = new BufferedReader(new FileReader(gui.getFile()));

			/*
			 * First, we read the contents of the
			 * input file and put each line into a list
			 */
			String strLine;

			if (!gui.cbReverseOrderIsTicked()) {
				// Words on each line go in the same order
				while ((strLine = brReader.readLine()) != null) {
					lEntries.add(strLine);
				}
			} else {
				// Words on each line go in reverse order
				
				List<String> lWordsGivenOrder = new LinkedList<String>();
				StringBuilder sbCurrentWord = new StringBuilder();
				StringBuilder sbReversedLine = new StringBuilder();
				
				while ((strLine = brReader.readLine()) != null) {
					// Each character will be looked at, to see where the spaces are
					for (int lineCharCnt = 0; lineCharCnt < strLine.length(); lineCharCnt++) {
						if (strLine.charAt(lineCharCnt) != ' ') {
							// Still part of current word. Add the letter
							sbCurrentWord.append(strLine.charAt(lineCharCnt));
						} else {
							// Space found. Current word to be added to lWordsGivenOrder
							lWordsGivenOrder.add(sbCurrentWord.toString());
							sbCurrentWord = new StringBuilder();
						}
					}
					
					/*
					 * Every lWordsGivenOrder entry has to be added
					 * in reverse to lEntries. This will do that:
					 */
					for (int reverseWordsCnt = lWordsGivenOrder.size() - 1; reverseWordsCnt >= 0; reverseWordsCnt--) {
						sbReversedLine.append(lWordsGivenOrder.get(reverseWordsCnt));
					}
					
					lEntries.add(sbReversedLine.toString());
					sbReversedLine = new StringBuilder();
					
					//lLinesGivenOrder.add(lWordsGivenOrder);
					//String strTest = new String(lLinesGivenOrder.get(1).get(1));
				}
				/*
				// Add every entry from lWordsGivenOrder
				// to lEntries, but in reverse order.
				for (int addEntriesCnt = lWordsGivenOrder.size() - 1; addEntriesCnt >= 0; addEntriesCnt--) {
					
				}
				*/
			}

			brReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: FileNotFoundException thrown");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ERROR: IOException thrown");
			e.printStackTrace();
		}

		/*
		 * Now, we go through lEntries,
		 * and copy every entry to lNewEntries
		 * in alphabetical order.
		 */

		strTemp = new String();
		// Stay in the while as long as
		// the entries are unsorted
		do {
			bEntriesUnsorted = false;

			for (int cnt = 1; cnt < lEntries.size(); cnt++) {
				if ((lEntries.get(cnt-1).compareToIgnoreCase(lEntries.get(cnt))) > 0) {
					// Two entries have been found in the wrong order.
					// The following lines swap the entries around.

					strTemp = lEntries.get(cnt-1);
					lEntries.set(cnt-1, lEntries.get(cnt));
					lEntries.set(cnt, strTemp);

					// Set the flag
					bEntriesUnsorted = true;
				}
			}
		} while (bEntriesUnsorted == true);

		// Have every entry added to GUI's jtaAlphabetizedText
		gui.addText(lEntries);
	}
}
