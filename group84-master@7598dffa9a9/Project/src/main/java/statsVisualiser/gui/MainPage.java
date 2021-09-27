/**
 * This class handles all UI interfacing with the user.
 * @author  Kohei Yasui, Terrence Ju, John Dick, Kevin Yang
 * SYSTEM STARTED THROUGH LOGIN CLASS
 */
package statsVisualiser.gui;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JPanel;
import java.awt.Color;
//imports various packages to be used 
import java.io.File;    
import java.awt.BasicStroke;
import java.awt.BorderLayout;

//imports swing items. 
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Event.*;
import java.awt.GridLayout;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;
import java.awt.*;


// imports jfreechart items. 

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
public class MainPage extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    
    //Parameters for graphs
    public static int yearStart = 2005;
    public static int yearEnd = 2020;
    public static String analysisMethod = "GDP";
    public static String currentCountry = "Canada";

    private static JFrame frame = new JFrame("Country Data Visualizer");
    private static JButton quitApp; 
    private static JButton addView;
    private static JButton removeView;
    private static JButton recalculate; 
    private static Vector<String> viewsNames = new Vector<String>();
    private static JComboBox<String> fromList;
    private static JComboBox<String> toList;
    private static JComboBox<String> methodsList;
    private static JComboBox<String> countriesList;
    private static CountryList countryListObj = new CountryList();
    private static String dataType1, dataType2, dataType3; 
    private static testBackend backendData1, backendData2, backendData3;
    private static JPanel east = new JPanel();
    private static JPanel south = new JPanel();
    private static int graphCount = 0;
    private static JComboBox<String> viewsList;

    private static boolean pieActive = false;
    private static boolean barActive = false;
    private static boolean lineActive = false;
    private static boolean reportActive = false;
    private static boolean scatterActive = false;
    private static String inputmethod = "";
    /**
     * This constructor sets up the initial UI the user interacts with. 
     * @throws IOException
     */
    public MainPage() throws IOException{
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // frame.setUndecorated(true);
        frame.setResizable(false);
        east.setLayout(new GridLayout(2,0));
        quitApp = new JButton("Quit");  //Jbuttons for quit, +/- JButton, Recalculate
        addView = new JButton("+ View");
        removeView = new JButton("- View");
        recalculate = new JButton("Recalculate");
        
        quitApp.addActionListener(this);
        addView.addActionListener(this);
        removeView.addActionListener(this);       
        recalculate.addActionListener(this);
    }
    /**
     * Creates a window to display the UI.
     * @throws IOException
     */
    public static void createWindow() throws IOException{  
       
        JLabel viewsLabel = new JLabel("Available Views: ");
        //Gets the look and feel of the client's platform

        //Creates quit button and sets padding
        JPanel p = new JPanel();
        //Creates border around the button
        Border padding = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        p.setBorder(padding);
        p.add(quitApp);

        //Creates label with countries
        JLabel chooseCountry = new JLabel("Select a country: ");
		Vector<String> countryNames = new Vector<String>();
        
        String[] countryList = countryListObj.getListOfCountries();
        
        //Adds countries to list
        for (int x = 0; x < countryList.length; x++){
            countryNames.add(countryList[x]);
        }
        
		//countryNames.sort(null);
        //From last comment to here this code is good, replace with array of country names

		countriesList = new JComboBox<String>(countryNames);

		JLabel from = new JLabel("From");
		JLabel to = new JLabel("To");

		Vector<String> years = new Vector<String>();
		for (int i = yearStart; i <= yearEnd; i++) {
			years.add("" + i);
		}
		fromList = new JComboBox<String>(years);
		toList = new JComboBox<String>(years);
        fromList.setPrototypeDisplayValue("text here");
        toList.setPrototypeDisplayValue("text here");

        JPanel yearBorder = new JPanel();
        JPanel yearEndBorder = new JPanel();
        //Creates border around the button
        // Border yearPadding = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        // yearBorder.setBorder(padding);
        // yearBorder.add(fromList);
        // yearEndBorder.setBorder(padding);
        // yearEndBorder.add(toList);

        JPanel north = new JPanel();
		north.add(chooseCountry);
		north.add(countriesList);
		north.add(from);
		north.add(fromList);
		north.add(to);
		north.add(toList);

		viewsNames.add("Line Graph");
		viewsNames.add("Bar Graph");
		viewsNames.add("Scatter Plot");
		viewsNames.add("Report");
		viewsList= new JComboBox<String>(viewsNames);

		JLabel methodLabel = new JLabel("        Choose analysis method: "); //dropdown menu for analysis types

		Vector<String> methodsNames = new Vector<String>();

		methodsNames.add("CO2 emissions vs Energy use vs PM2.5 air pollution (3-series graph)");
        methodsNames.add("PM2.5 air pollution vs Forest area (2-series graphs)");
		methodsNames.add("CO2 emissions vs GDP per capita (2-series graphs)");
		methodsNames.add("Average Forest area (% of land area) for the selected years (1-series graphs)");
		methodsNames.add("Government expenditure on education (1-series graphs)");
		methodsNames.add("Hospital beds vs Current health expenditure (2-series graphs)");
        methodsNames.add("Current health expenditure per capita vs Mortality rate, infant (2-series graphs)");
        methodsNames.add("Government expenditure on education vs Current health expenditure (2-series graphs)");

		methodsList = new JComboBox<String>(methodsNames);

		south.add(viewsLabel);
		south.add(viewsList);
		south.add(addView);
		south.add(removeView);

		south.add(methodLabel);
		south.add(methodsList);
		south.add(recalculate);
        south.add(p);

		// Set charts region
		JPanel west = new JPanel();
		west.setLayout(new GridLayout(2, 0));

		frame.add(north, BorderLayout.NORTH);
		frame.add(east, BorderLayout.EAST);
		frame.add(south, BorderLayout.SOUTH);
		frame.add(west, BorderLayout.WEST);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // sets the chart size 

        frame.setSize(1200, 800);
        Rectangle r = new Rectangle(1200, 800);
        frame.setBounds(r); //512 x 256px size
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    } 
    /**
     * This method takes the action performed and can edit the UI several different ways depending on how the user interacts. 
     * @param ActionEvent evt
     */
    public void actionPerformed(ActionEvent evt) {
        Object src = evt.getSource();
        //Action to be performed on quit button
        if (src == quitApp) {
          frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          frame.dispose();
        }
        //Action to be performed on add graph button
        else if (src == addView){
            update();

            boolean valid = CheckValidity.checkCountryAndAnalysis(currentCountry, inputmethod);
            boolean valid2 = CheckValidity.checkCountryAnalysisYear(currentCountry, inputmethod, yearStart, yearEnd);
            if (valid == false || valid2 == false){
             JOptionPane.showMessageDialog(this, " Data does not exist for these parameters, please re-select "); //error message
            }
            else{
                if ((String)viewsList.getSelectedItem() == "Scatter Plot" && !scatterActive){ //if it is a scatter plot
                    graphCount++;
                
                    if (backendData3 != null)    {
                        CreateGraph.createScatter(east, backendData1.getData(), backendData2.getData(), 
                        backendData3.getData(), analysisMethod);
                    } else if (backendData2 != null){
                        CreateGraph.createScatter(east, backendData1.getData(), backendData2.getData(), 
                        null, analysisMethod);
                    } else{
                        CreateGraph.createScatter(east, backendData1.getData(), null, null, analysisMethod);
                    }
                
                scatterActive = true;
                }
                else if ((String)viewsList.getSelectedItem() == "Bar Graph" && !barActive){  //if it is a bar graph
                    graphCount++;
                    if (graphCount <= 3){
                        if (backendData3 != null)    {
                            CreateGraph.createBar(east, backendData1.getData(), backendData2.getData(), 
                            backendData3.getData(), analysisMethod);
                    } else if (backendData2 != null){
                        CreateGraph.createBar(east, backendData1.getData(), backendData2.getData(), 
                        null, analysisMethod);
                    } else{
                        CreateGraph.createBar(east, backendData1.getData(), null, null, analysisMethod);
                    }
                }
                else {
                    CreateGraph.createBar(east, backendData1.getData(), backendData2.getData(), backendData3.getData(), analysisMethod);
                }
                barActive = true;
                }
                else if ((String)viewsList.getSelectedItem() == "Report" && !reportActive){  //if it is a report
                    graphCount++;
               
                    if (backendData3 != null)    {
                        CreateGraph.createReport(east, backendData1.getData(), backendData2.getData(), 
                        backendData3.getData(), analysisMethod);
                    } 
                    else if (backendData2 != null){
                        CreateGraph.createReport(east, backendData1.getData(), backendData2.getData(), 
                        null, analysisMethod);
                    }
                    else{
                        CreateGraph.createReport(east, backendData1.getData(), null, null, analysisMethod);
                    }
                
                reportActive = true;
                
                }
                else if ((String)viewsList.getSelectedItem() == "Line Graph" && !lineActive){
                graphCount++;
            
                if (backendData3 != null)    {
                    CreateGraph.createLine(east, backendData1.getData(), backendData2.getData(), 
                    backendData3.getData(), analysisMethod);
                } else if (backendData2 != null){
                    CreateGraph.createLine(east, backendData1.getData(), backendData2.getData(), 
                    null, analysisMethod);
                } else{
                    CreateGraph.createLine(east, backendData1.getData(), null, null, analysisMethod);
                }
              
                lineActive = true;
                }
                else{
                    JOptionPane.showMessageDialog(this, " Already have a graph of that type, press recalculate to return new graph "); //error message
                }
           
            SwingUtilities.updateComponentTreeUI(frame);
            }
        }
        //Action to be performed on remove view
        else if (src == removeView){
            update();
            graphCount--;
            if ((String)viewsList.getSelectedItem() == "Scatter Plot"){
                // get it to remove indivdual graphs
                if (!scatterActive){
                    JOptionPane.showMessageDialog(this, " View doesn't exist ");
                }else{
                    east.remove(CreateGraph.scatterChartPanel);
                    scatterActive = false;
                }
            }
            else if ((String)viewsList.getSelectedItem() == "Bar Graph"){
                // get it to remove indivdual graphs
                if (!barActive){
                    JOptionPane.showMessageDialog(this, " View doesn't exist ");
                }else{
                    east.remove(CreateGraph.barChartPanel);
                    barActive = false;
                }
            }  
            else if ((String)viewsList.getSelectedItem() == "Line Graph"){
                // get it to remove indivdual graphs
                if (!lineActive){
                    JOptionPane.showMessageDialog(this, " View doesn't exist ");
                }
                else{
                    east.remove(CreateGraph.lineChartPanel);
                    lineActive = false;
                }
            } 
            else if ((String)viewsList.getSelectedItem() == "Report"){
                if (!reportActive){
                    JOptionPane.showMessageDialog(this, " View doesn't exist ");
                }else{
                    east.remove(CreateGraph.reportPanel);
                    reportActive = false;
                }
            }
            SwingUtilities.updateComponentTreeUI(frame);
        }
        else if (src == recalculate){
            //Recalculate
           update();
           boolean valid = CheckValidity.checkCountryAndAnalysis( currentCountry,  inputmethod);
           boolean valid2 = CheckValidity.checkCountryAnalysisYear( currentCountry,  inputmethod, yearStart, yearEnd);
           if (valid == false || valid2 == false){  //if the validity is not there
            JOptionPane.showMessageDialog(this, " Data does not exist for these parameters, please re-select "); //error message
           }
           else{
                east.removeAll();
                
                if (barActive){   //for bar graph
                    if (backendData3 != null)    {
                     CreateGraph.createBar(east, backendData1.getData(), backendData2.getData(), backendData3.getData(), analysisMethod);
                }
                else if (backendData2 != null){
                    CreateGraph.createBar(east, backendData1.getData(), backendData2.getData(), null, analysisMethod);
                } else{
                    CreateGraph.createBar(east, backendData1.getData(), null, null, analysisMethod);
                }
            }
            
            if (scatterActive){  //for scatter graph
                if (backendData3 != null)    {
                    CreateGraph.createScatter(east, backendData1.getData(), backendData2.getData(), 
                    backendData3.getData(), analysisMethod);
                 } else if (backendData2 != null){
                    CreateGraph.createScatter(east, backendData1.getData(), backendData2.getData(), 
                    null, analysisMethod);
            } else{
                CreateGraph.createScatter(east, backendData1.getData(), null, null, analysisMethod);
            }
           }
           if (lineActive){  //for line graph
            if (backendData3 != null)    {
                CreateGraph.createLine(east, backendData1.getData(), backendData2.getData(), 
                backendData3.getData(), analysisMethod);
            } else if (backendData2 != null){
                CreateGraph.createLine(east, backendData1.getData(), backendData2.getData(), 
                null, analysisMethod);
            } else{
                CreateGraph.createLine(east, backendData1.getData(), null, null, analysisMethod);
            }
              
           }
           if (reportActive){  //for report
            if (backendData3 != null)    {
                CreateGraph.createReport(east, backendData1.getData(), backendData2.getData(), 
                backendData3.getData(), analysisMethod);
            } else if (backendData2 != null){
                CreateGraph.createReport(east, backendData1.getData(), backendData2.getData(), 
                null, analysisMethod);
            } else{
                CreateGraph.createReport(east, backendData1.getData(), null, null, analysisMethod);
            }
           }
           
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            update();
            SwingUtilities.updateComponentTreeUI(frame);
           }
        }
    }
    /**
     * The Update function that updates the existing graphs. 
     */
    public void update(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        String yearStartText = (String)fromList.getSelectedItem();
        int yearStartValue = Integer.parseInt(yearStartText); 
        yearStart = yearStartValue;

        String yearEndText = (String)toList.getSelectedItem();
        int yearEndValue = Integer.parseInt(yearEndText); 
        yearEnd = yearEndValue;

        analysisMethod = (String)methodsList.getSelectedItem();

        currentCountry = (String)countriesList.getSelectedItem();


        String currentCountryCode = countryListObj.getCountryDictionary().get(currentCountry);

        if (analysisMethod.equals("CO2 emissions vs Energy use vs PM2.5 air pollution (3-series graph)")) { // creates data objects for all 3 variables 
            backendData1 = new testBackend("co2 emissions", yearStart, yearEnd, currentCountryCode);
            backendData2 = new testBackend("energy use", yearStart, yearEnd, currentCountryCode);
            backendData3 = new testBackend("pm 2.5", yearStart, yearEnd, currentCountryCode);
            inputmethod = "ana1";
        }
        else if (analysisMethod.equals("PM2.5 air pollution vs Forest area (2-series graphs)")) {
            backendData1 = new testBackend("pm 2.5", yearStart, yearEnd, currentCountryCode);
            backendData2 = new testBackend("forest area", yearStart, yearEnd, currentCountryCode);
            backendData3 = null;
            inputmethod = "ana2";
        }
        else if (analysisMethod.equals("CO2 emissions vs GDP per capita (2-series graphs)")) {
            backendData1 = new testBackend("co2 emissions", yearStart, yearEnd, currentCountryCode);
            backendData2 = new testBackend("gdp", yearStart, yearEnd, currentCountryCode);
            backendData3 = null;
            inputmethod = "ana3";
        }
        else if (analysisMethod.equals("Average Forest area (% of land area) for the selected years (1-series graphs)")) {
            backendData1 = new testBackend("forest area", yearStart, yearEnd, currentCountryCode);
            backendData2 = null;
            backendData3 = null;
            inputmethod = "ana4";
        }
        else if (analysisMethod.equals("Government expenditure on education (1-series graphs)")) {
            backendData1 = new testBackend("government expenditure on education", yearStart, yearEnd, currentCountryCode);
            backendData2 = null;
            backendData3 = null;
            inputmethod = "ana5";
        }
        else if (analysisMethod.equals("Hospital beds vs Current health expenditure (2-series graphs)")) {
            backendData1 = new testBackend("hospital beds", yearStart, yearEnd, currentCountryCode);
            backendData2 = new testBackend("current health expenditure", yearStart, yearEnd, currentCountryCode);
            backendData3 = null;
            inputmethod = "ana6";
        }
        else if (analysisMethod.equals("Current health expenditure per capita vs Mortality rate, infant (2-series graphs)")) {
            backendData1 = new testBackend("current health expenditure per capita", yearStart, yearEnd, currentCountryCode);
            backendData2 = new testBackend("mortality rate", yearStart, yearEnd, currentCountryCode);
            backendData3 = null;
            inputmethod = "ana7";
        }
        else if (analysisMethod.equals("Government expenditure on education vs Current health expenditure (2-series graphs)")) {
            backendData1 = new testBackend("government expenditure on education", yearStart, yearEnd, currentCountryCode);
            backendData2 = new testBackend("current health expenditure", yearStart, yearEnd, currentCountryCode);
            backendData3 = null;
            inputmethod = "ana8";
        }
    }

    public static void main(String args[]) throws Exception{
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainPage testButtons = new MainPage();
        createWindow();
    }
}
