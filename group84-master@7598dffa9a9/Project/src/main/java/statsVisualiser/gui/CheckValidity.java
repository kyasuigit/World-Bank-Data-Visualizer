package statsVisualiser.gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
/** 
 * Our Check validity class, to check different applicabilites of inputs.
 * @author Terrence Ju, Kohei Yasui, Kevin Yang, John Dick
*/
public class CheckValidity {
    /**
     * Checks the text file and returns true if it is applicable, returns false if it's not
     * @param country
     * @param analysis
     * @return boolean
     */
    public static boolean checkCountryAndAnalysis(String country, String analysis) {
        String line = "";
        String comma = ",";
        try {
            BufferedReader read = new BufferedReader(new FileReader("Project/src/main/java/statsVisualiser/gui/validCountries.txt"));  //reads the text file
            line = read.readLine();
                while ((line = read.readLine()) != null) {
                    String[] countryDat = line.split(comma);  //splits the line by commas
                    if (countryDat[0].equals(country)) {
                        for (int i = 3; i < countryDat.length; i++) {
                            countryDat[i] = removePunctuations(countryDat[i]);
                            if (countryDat[i].equals(analysis))
                                return true;  //returns if the analysis and country works
                        }
                    }   
                } 
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false; 
    }

    /**
     * The same function as before, but this time checking for a range of years
     * @param country
     * @param analysis
     * @param year1
     * @param year2
     * @return boolean
     */
    public static boolean checkCountryAnalysisYear(String country, String analysis, int year1, int year2) {
        if (checkCountryAndAnalysis(country, analysis)) {
            String line = "";
            String comma = ",";
            try {
                BufferedReader read = new BufferedReader(new FileReader("Project/src/main/java/statsVisualiser/gui/validCountries.txt"));  //reads the text file
                line = read.readLine();
                while ((line = read.readLine()) != null) {
                    String[] countryDat = line.split(comma);  //splits by commas
                    countryDat[1] = removePunctuations(countryDat[1]);  //removes punctuations
                    countryDat[2] = removePunctuations(countryDat[2]);
                    if (Integer.valueOf(countryDat[1]) <= year1 && Integer.valueOf(countryDat[2]) >= year2) {
                        read.close();
                        return true;  //return true if the range of years works with the country and analysis types
                    }
                }
                read.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false; 
    }
    /**
     * Simple remove punctuation functions
     * @param string
     * @return String
     */
    public static String removePunctuations(String string) {
        String replc = "";
        for (Character c : string.toCharArray()) {
            if(Character.isLetterOrDigit(c))
                replc += c;
        }
        return replc;
    }
}
