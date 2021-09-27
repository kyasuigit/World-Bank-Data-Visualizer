/**
 * This class creates a list of countries given the country_list.csv file, and has several methods attached.
 * @author  Kohei Yasui, John Dick, Kevin Yang, Terrence Ju
 */
package statsVisualiser.gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Arrays;

public class CountryList {

    private Dictionary<String, String> countryList;
    /** 
     * This method constructs the countryList given the country_list.csv file. 
    */
    public CountryList() {
        String line = "";
        String comma = ",";
        countryList = new Hashtable<String,String>();  //a new hashtable to store our country list

       try {
           
            BufferedReader read = new BufferedReader(new FileReader("Project/src/main/java/statsVisualiser/gui/country_list.csv"));   //reads the text file containing the countries
            line = read.readLine();  
            while ((line = read.readLine()) != null) { // loop throughout the entire file
                String[] countryDat = line.split(comma);  //splits the data by the commas
                countryDat[1] = removePunctuations(countryDat[1]);
                countryList.put(countryDat[1], countryDat[5]);  //puts the country into the list
            }
            read.close();
       }
       catch(IOException e) { // prints out an error message if necessary 
           e.printStackTrace();
       }
    }
    /**
     * This method is the getter method to return the dictionary of countries with their respective codes. 
     * @return countryList
     */
    public Dictionary<String, String> getCountryDictionary(){
        return countryList;
    }
    /**
     * Returns an array of all countries available. 
     */
    public String[] getListOfCountries() {
        int countryNum = countryList.size();
        String[] countries = new String[countryNum];
        Enumeration countryEnum = countryList.keys();
        for (int i = 0; i < countryNum; i++) {
            countries[i] = (String) (countryEnum.nextElement());
        }
        Arrays.sort(countries);
        return countries;
    }
    /**
     * simple remove punctuation function
     * @param string
     * @return
     */
    public String removePunctuations(String string) {
        String replc = "";
        for (Character c : string.toCharArray()) {
            if(Character.isLetterOrDigit(c) || Character.isSpaceChar(c))
                replc += c;  
        }
        return replc;
    }
}
