/**
 * This class deals with creating graphs of different types in accordance with what the MainPage feeds to this class. 
 * @author  Kohei Yasui, Terrence Ju, John Dick, Kevin Yang
 * */
package statsVisualiser.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Dictionary;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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

public class CreateGraph {

	public static JScrollPane reportPanel;     //Declaring all of our variables which will be used in our methods
	public static ChartPanel barChartPanel;
	public static ChartPanel scatterChartPanel;
	public static ChartPanel lineChartPanel; 
	public static String variable1;
	public static String variable2;
	public static String variable3;
	public static String label1;
	public static String label2;
	/**
	 * Our bar graph method that creates the bar graph and sets it to JPanel west. 
	 * @param west JPanel to edit
	 * @param data1 // datasets 1-3
	 * @param data2
	 * @param data3
	 * @param analysisMethod // analysis method
	 */ 
    public static void createBar(JPanel west, Dictionary<Integer, Float> data1, Dictionary<Integer, Float> data2, Dictionary<Integer, Float> data3,  String analysisMethod){

		checkAnalysisType(analysisMethod);    //uses our check analysis method to return which analysis type we are using
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();    //creates three empty datasets
		DefaultCategoryDataset dataset2 =  new DefaultCategoryDataset();
		DefaultCategoryDataset dataset3 =  new DefaultCategoryDataset();
		if (data3 != null) {    //our if statement if it is a three dataset comparison
			for (int x = MainPage.yearStart; x <= MainPage.yearEnd; x++){   //for loop within our year parameters
				dataset.setValue(data1.get(x), variable1, Integer.toString(x));  //fill up our dataset1 with the first data recieved
				dataset.setValue(null, variable2, Integer.toString(x));
				dataset.setValue(null, variable3, Integer.toString(x));
			}
			
			for (int x = MainPage.yearStart; x <= MainPage.yearEnd; x++){  //year parameters loop
				dataset2.setValue(null, variable1, Integer.toString(x));
				dataset2.setValue(data2.get(x), variable2, Integer.toString(x));   //fill up our dataset2 with the second data recieved
				dataset2.setValue(null, variable3, Integer.toString(x));
			}

			for (int x = MainPage.yearStart; x <= MainPage.yearEnd; x++){   //year parameters loop
				dataset3.setValue(null, variable1, Integer.toString(x));
				dataset3.setValue(null, variable2, Integer.toString(x));
				dataset3.setValue(data3.get(x), variable3, Integer.toString(x));   //fill up our dataset3 with the third data recieved
			}

		}

		else if (data2 != null) {    //our if statement for if it's a two dataset comparison
			for (int x = MainPage.yearStart; x <= MainPage.yearEnd; x++){      //another year parameter loop
				dataset.setValue(data1.get(x), variable1, Integer.toString(x));   //fill up our dataset1 with the first data received
				dataset.setValue(null, variable2, Integer.toString(x));
			}
		
			for (int x = MainPage.yearStart; x <= MainPage.yearEnd; x++){    //year parameter loop
				dataset2.setValue(null, variable1, Integer.toString(x));
				dataset2.setValue(data2.get(x), variable2, Integer.toString(x));    //fill up our dataset2 with the second data recieved
			}

		}

		else {   //if we only get one dataset, and we just
			for (int x = MainPage.yearStart; x <= MainPage.yearEnd; x++){
				dataset.setValue(data1.get(x), variable1, Integer.toString(x));     //fill up our dataset1 with the data recieved
			}

		}

		CategoryPlot plot = new CategoryPlot();  
		BarRenderer barrenderer1 = new BarRenderer();   //creates our bar renders
		BarRenderer barrenderer2 = new BarRenderer();   
		BarRenderer barrenderer3 = new BarRenderer();   

		plot.setDataset(0, dataset);  
		plot.setRenderer(0, barrenderer1);
		CategoryAxis domainAxis = new CategoryAxis("Year"); //sets our axis label
		plot.setDomainAxis(domainAxis); 
		plot.setRangeAxis(new NumberAxis(label1));
		if (data2 != null) {  //adds in the additional dataset
			plot.setDataset(1, dataset2);
			plot.setRenderer(1, barrenderer2);
			plot.setRangeAxis(1, new NumberAxis(label2));
		} 
		if (data3 != null) {  //adds in the final dataset
			plot.setDataset(2, dataset3);
			plot.setRenderer(2, barrenderer3);
		}
		

		plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
		plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

		JFreeChart barChart = new JFreeChart(analysisMethod, new Font("Serif", java.awt.Font.BOLD, 18), plot, true);  //creates the bar graph
		barChartPanel = new ChartPanel(barChart);
		barChartPanel.setPreferredSize(new Dimension(575, 375)); //dimensions of the graph
		barChartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); //creates our border
		barChartPanel.setBackground(Color.white); //sets background colour
		west.add(barChartPanel); //adds our graph to the screen
        
    }
	/**
	 * Creates our scattergraph function using the JPanel west. 
	 * @param west
	 * @param dataset1
	 * @param dataset2
	 * @param dataset3
	 * @param analysisMethod
	 */
    public static void createScatter(JPanel west, Dictionary<Integer, Float> dataset1, 
	Dictionary<Integer, Float> dataset2, Dictionary<Integer, Float> dataset3, String analysisMethod){
		checkAnalysisType(analysisMethod);  //checks for the analysis method
       	TimeSeries series1 = new TimeSeries(variable1);
		for (int x = MainPage.yearStart; x <= MainPage.yearEnd; x++) {  //adds in the first dataset into the time series
			series1.add(new Year(x), dataset1.get(x));
		}

		TimeSeries series2 = null;  
		if (dataset2 != null) {  //if there is a second dataset, adds our data into it
			series2 = new TimeSeries(variable2);
			for (int x = MainPage.yearStart; x <= MainPage.yearEnd; x++) {
				series2.add(new Year(x), dataset2.get(x));
			}
		}

		TimeSeries series3 = null;
		if (dataset3 != null) {  //if there is a third dataset, adds our data to it
			series3 = new TimeSeries(variable3);
			for (int x = MainPage.yearStart; x <= MainPage.yearEnd; x++){
				series3.add(new Year(x), dataset3.get(x));
			}
		}

		TimeSeriesCollection dataCollection1 = new TimeSeriesCollection();   //creates a time series collection
		dataCollection1.addSeries(series1);  //adds our first time series to the collection
		TimeSeriesCollection dataCollection2 = null;
		if (dataset2 != null) {   //adds our second time series 
			dataCollection2 = new TimeSeriesCollection();
			dataCollection2.addSeries(series2);
		}
				
		if (dataset3 != null) { //adds our third time series
			dataCollection1.addSeries(series3);
		}

		XYPlot plot = new XYPlot();  //creates a new XYPlot
		XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
		XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);

		plot.setDataset(0, dataCollection1);
		plot.setRenderer(0, itemrenderer1);
		DateAxis domainAxis = new DateAxis("Year");    //labels our axis
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(label1));

		if (dataset2 != null) {   //if we have two datasets and need to add them
			plot.setDataset(1, dataCollection2);
			plot.setRenderer(1, itemrenderer2);
			plot.setRangeAxis(1, new NumberAxis(label2));
		}

		plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis

		if (dataset2 != null) {
			plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis
		}

		JFreeChart scatterChart = new JFreeChart(analysisMethod, new Font("Serif", java.awt.Font.BOLD, 18), plot, true);   //creates the graph aesthetics
		scatterChartPanel = new ChartPanel(scatterChart);
		scatterChartPanel.setPreferredSize(new Dimension(575, 375));   
		scatterChartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		scatterChartPanel.setBackground(Color.white);  
		west.add(scatterChartPanel);  //adds our graph to the panel
    }
	/**
	 * This method creates a line graph given the different data from the backend. 
	 * @param west
	 * @param dataset1
	 * @param dataset2
	 * @param dataset3
	 * @param analysisMethod
	 */
    public static void createLine(JPanel west, Dictionary<Integer, Float> dataset1, 
	Dictionary<Integer, Float> dataset2, Dictionary<Integer, Float> dataset3, String analysisMethod){
        checkAnalysisType(analysisMethod);
		
		XYSeries series1 = new XYSeries(variable1);
		
		for (int x = MainPage.yearStart; x <= MainPage.yearEnd; x++){
			series1.add(x, dataset1.get(x)); //adds our data to our first series
		}
	

		XYSeries series2 = null;
		if (dataset2 != null){
			series2 = new XYSeries(variable2);
			for (int x = MainPage.yearStart; x<= MainPage.yearEnd; x++){
				series2.add(x, dataset2.get(x));  //adds our second data set to our second series
			}
		}
		
		XYSeries series3 = null;
		if (dataset3 != null){
			series3 = new XYSeries(variable3);
			for (int x = MainPage.yearStart; x <= MainPage.yearEnd; x++){
				series3.add(x, dataset3.get(x));  //adds our third data set to our third series
			}
		}
	
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);   //adds our series to our collection
		if (dataset2 != null){   
		dataset.addSeries(series2);     //adds our second dataset if we have it
		}
		if (dataset3 != null){  
			dataset.addSeries(series3);    //adds our third dataset if we have it
		}

		JFreeChart chart = ChartFactory.createXYLineChart(analysisMethod, "Year", "", dataset,
				PlotOrientation.VERTICAL, true, true, false); //creates our chart

		XYPlot plot = chart.getXYPlot();    

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.BLUE);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));

		plot.setRenderer(renderer);     //graph aesthetics
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		chart.getLegend().setFrame(BlockBorder.NONE);

		chart.setTitle(new TextTitle(analysisMethod, new Font("Serif", java.awt.Font.BOLD, 18)));    //more graph aesthetics
		lineChartPanel = new ChartPanel(chart);
		lineChartPanel.setPreferredSize(new Dimension(575, 375));
		lineChartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		lineChartPanel.setBackground(Color.white);
		west.add(lineChartPanel);  //adds our graph to the panel

    }
	/**
	 * Creates our analysis report
	 * @param west
	 * @param dataset1
	 * @param dataset2
	 * @param dataset3
	 * @param analysisMethod
	 */
	public static void createReport(JPanel west, Dictionary<Integer, Float> dataset1, 
	Dictionary<Integer, Float> dataset2, Dictionary<Integer, Float> dataset3, String analysisMethod) {
		checkAnalysisType(analysisMethod);
        JTextArea report = new JTextArea();
        report.setEditable(false);     //makes it so it can't be edited
        report.setPreferredSize(new Dimension(575, 375));     //text aesthetics
        report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        report.setBackground(Color.white);
		Font font = report.getFont();
		Font f2 = new Font(font.getFontName(), font.getStyle(), font.getSize()-2);
		report.setFont(f2);
        String reportMessage;
		reportMessage = analysisMethod + "\n"+"==========================\n";
		if (dataset3 != null) {   //if we have three datasets, add all the analysis comparisons
			for (int x = MainPage.yearStart; x<=MainPage.yearEnd; x++){
				reportMessage = reportMessage + "Year "+Integer.toString(x)+":\n"+"\t" +variable1 +" => "+Float.toString(dataset1.get(x))+"\n"+"\t" + variable2 +" => "
				+Float.toString(dataset2.get(x))+"\n"+"\t"+variable3+ " => "+ Float.toString(dataset3.get(x))+"\n"+"\n";
			}
		}
		else if (dataset2 != null) {  //if we have two datasets
			for (int x = MainPage.yearStart; x<=MainPage.yearEnd; x++){
				reportMessage = reportMessage + "Year "+Integer.toString(x)+":\n"+"\t" +variable1 +" => "+Float.toString(dataset1.get(x))+"\n"+"\t" + variable2 +" => "
				+Float.toString(dataset2.get(x))+"\n";
			}
		}
		else {    //if we have one dataset
			for (int x = MainPage.yearStart; x<=MainPage.yearEnd; x++){
				reportMessage = reportMessage + "Year "+Integer.toString(x)+":\n"+"\t" +variable1 +" => "+Float.toString(dataset1.get(x))+"\n";
			}
		}
        report.setText(reportMessage);
        reportPanel = new JScrollPane(report);
        west.add(reportPanel);   //adds our report to the panel
    }
	/**
	 * Checks our analysis type to see which data we should use in the code
	 * @param analysisMethod
	 */
	public static void checkAnalysisType(String analysisMethod) {
		if (analysisMethod.equals("CO2 emissions vs Energy use vs PM2.5 air pollution (3-series graph)")) {   //comparison to analysis types to use for our parameters
			variable1 = "CO2 (metric tons/capita)";
			variable2 = "Energy use (kg of oil/capita)";
			variable3 = "PM2.5 mean (micrograms/cubic meter)";
			label1 = "Grams";
			label2 = "Tons";
		}	
		else if (analysisMethod.equals("PM2.5 air pollution vs Forest area (2-series graphs)")) {
			variable1 = "PM2.5 air pollution (micrograms/cubic meter)";
			variable2 = "Forest Area (Percentage)";
			label1 = "Micrograms";
			label2 = "Percentage";
		}
		else if (analysisMethod.equals("CO2 emissions vs GDP per capita (2-series graphs)")) {
			variable1 = "CO2 emissions";
			variable2 = "GDP per capita";
			label1 = "Metric tons/capita";
			label2 = "US Dollars";
		}
		else if (analysisMethod.equals("Average Forest area (% of land area) for the selected years (1-series graphs)")) {
			variable1 = "Average Forest Area(%)";
			label1 = "Percentage";
		}
		else if (analysisMethod.equals("Government expenditure on education (1-series graphs)")) {
			variable1 = "Government expenditure on education";
			label1 = "US Dollars";
		}
		else if (analysisMethod.equals("Hospital beds vs Current health expenditure (2-series graphs)")) {
			variable1 = "Hospital beds/1000 ppl";
			variable2 = "Current health expenditure";
			label1 = "Ratio";
			label2 = "US Dollars";
		}
		else if (analysisMethod.equals("Current health expenditure per capita vs Mortality rate, infant (2-series graphs)")) {
			variable1 = "Current health expenditure/capita";
			variable2 = "Infant mortality rate";
			label1 = "US Dollars";
			label2 = "Percentage";
		}
		else if (analysisMethod.equals("Government expenditure on education vs Current health expenditure (2-series graphs)")) {
			variable1 = "Government expenditure on education";
			variable2 = "Current health expenditure";
			label1 = "US Dollars";
			label2 = "US Dollars";
		}
	}
}
