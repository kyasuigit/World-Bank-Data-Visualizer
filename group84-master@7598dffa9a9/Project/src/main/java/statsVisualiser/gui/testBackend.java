/**
 * This class handles all background connections to the World Bank API and back. 
 * @author Kohei Yasui, Kevin Yang, Terrence Ju, John Dick
 * */
package statsVisualiser.gui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser; 
import java.util.Hashtable;
import java.util.Dictionary;

/**
 * This program is used to grab information from the api and returning a dictionary object to the user.
 * 
 * April 3, 2021 (4:26 pm)
 * Made changes to the class to ensure that the class is fully functional.
 * 
 * April 4, 2021 (4:03 pm)
 * Add some things to make sure that it can grab data for a few more indicators.
 * 
 * April 4, 2021 (7:13 pm)
 * Added some changes to ensure type conversions are accurate.
 * 
 * April 7, 2021 (10:51 am)
 * Made a change to ensure that data is read in the correct format.
 */

public class testBackend {

    // String to store the country to change the url. 
    private String countrySelection;

    // Integer to store the starting year.
    private int yearStart;

    // Integer to store the end year.
    private int yearEnd;

    // String to store the type of analysis for the url.
    private String analysisType;

    // String to store the url.
    private String url;

    // Creating a dictionary to store all of the data 
    private Dictionary <Integer, Float> dataDictionary = new Hashtable <Integer, Float>(); 

    /**
     * This is the constructor for the class. When we create an object of this kind, it will allow us to 
     * store the appropriate variables where they should be stored. We will also store the url in its 
     * appropriate format.
     * @param typeOfAnalysis String for the analysis type selected.
     * @param startingYear int for the starting year selected.
     * @param endingYear int for the ending year selected.
     * @param country String for the selected country.
     */
    public testBackend (String typeOfAnalysis, int startingYear, int endingYear, String country){
        // Storing the values so they can be used throughout the program.
        countrySelection = country.toLowerCase();
        yearStart = startingYear;
        yearEnd = endingYear;

        // Checking what analysis type is selected. Not the most efficient thing but switch statements with
        // strings is not supported on our version of maven so it can only be this. 
        // DEFAULT: total population.
        if (typeOfAnalysis.toLowerCase().equals("mortality rate")){
            analysisType = "SP.DYN.IMRT.IN";
        }

        else if (typeOfAnalysis.toLowerCase().equals("current health expenditure per capita")){
            analysisType = "SH.XPD.CHEX.PC.CD";
        }

        else if (typeOfAnalysis.toLowerCase().equals("current health expenditure")){
            analysisType = "SH.XPD.CHEX.GD.ZS";
        }

        else if (typeOfAnalysis.toLowerCase().equals("government expenditure on education")){
            analysisType = "SE.XPD.TOTL.GD.ZS";
        }

        else if (typeOfAnalysis.toLowerCase().equals("forest area")){
            analysisType = "AG.LND.FRST.ZS";
        }

        else if (typeOfAnalysis.toLowerCase().equals("total population")){
            analysisType = "SP.POP.TOTL";
        }

        else if (typeOfAnalysis.toLowerCase().equals("hospital beds")){
            analysisType = "SH.MED.BEDS.ZS";
        }

        else if (typeOfAnalysis.toLowerCase().equals("gdp")){
            analysisType = "NY.GDP.PCAP.CD";
        }

        else if (typeOfAnalysis.toLowerCase().equals("co2 emissions")){
            analysisType= "EN.ATM.CO2E.PC";
        }

        else if (typeOfAnalysis.toLowerCase().equals("pm 2.5")){
            analysisType = "EN.ATM.PM25.MC.M3";
        }
        
        else if (typeOfAnalysis.toLowerCase().equals("energy use")){
            analysisType = "EG.USE.PCAP.KG.OE";
        }

        else{
            analysisType = "SP.POP.TOTL";
        }

        // This could be wrong. Make sure it works as inteded. We need to make so changes here to add
        // analysis type.
        url = "http://api.worldbank.org/v2/country/" + countrySelection + "/indicator/" + analysisType + "?date=" + yearStart + ":" + yearEnd + "&format=json";
    }

    /**
     * This method will be called when we want to get a dictionary from the class. This dicitonary will 
     * contain pairs of data in the form of: [DATE, DATA]. Both will be integers.
     * @return
     */
    public Dictionary<Integer, Float> getData(){

        // Integer for the data type of the given year.
        float dataForYear = 0;

        try {

            // Creating a new URL object with the url provided. 
            URL newUrl = new URL (url); 

            // Opening an HTTP request to the given url.
            HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();

            // Set the mode to get so we can retrieve information from the connection.
            conn.setRequestMethod("GET");

            // Connect to the url.
            conn.connect();

            // Store the response code. This will be used to verify that the connection was made appropriately.
            int responsecode = conn.getResponseCode();

            // If the connection has been appropriately made (status == okay), we can preceed.
            if (responsecode == 200){

                // Creating a string to store information.
                String inline = "";

                // Creating a scanner object to read from the url.
                Scanner sc = new Scanner(newUrl.openStream());

                // While there is another line to read, we can add to the string variable.
                while (sc.hasNext()){
                    inline += sc.nextLine();
                }
                sc.close(); // Closing the scanners connection.

                // Creating a jsonarray with the given information.
                JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();

                // Grabbing the size of each individual entry.
                int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();

                // Integer to store the current year.
                int year;

                // Looping through the entries one by one.
                 for (int i = 0; i < sizeOfResults; i++){

                    // Grabbing the year from the given json entry.
                    year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
                    
                    // If there is nothing in the field, we put 0 in the current data point.
                    if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull()){
                        dataForYear = 0;
                    }

                    // Otherwise store the value into our temporary variable.
                    else{
                        dataForYear = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsFloat();
                    }

                    // After all is said and done, we will store the pairing into the dictionary.
                    dataDictionary.put(year, dataForYear);
                 }
            }

            // At the very end, we can return the dictionary as intended.
            return (dataDictionary);
        }
    
        // If an error is encountered for some reason, return a null object after printing out the
        // traced error.
        catch (IOException e){
            e.printStackTrace();
            return (null);
        }
    }
}
