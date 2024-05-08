package checkNews.support.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Color;

/**
 * This class produce a bar chart
 * requires org.jfree.jfreechart; en module-info.java  and  jfreechart-1.5.3.jar.  Set on 'properties-java build path-libraries-module path'
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */
public class BarChart {
	
	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	/**
	 * Builder
	 * @param frameName, frame name
	 * @param chartName, chart name
	 * @param columnName, column name
	 * @param columnFigures, column data 
	 */
	public BarChart(String frameName, String chartName, String[] columnName, int[] columnFigures) {
		
		JFrame frame = new JFrame();
		int[] data = columnFigures;
		
		CategoryDataset dataset = addDataset(data, columnName);
        JFreeChart chart = ChartFactory.createBarChart(chartName, "", "", dataset, PlotOrientation.VERTICAL,false, true, false);
                
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        frame.add(chartPanel);

        frame.pack();
        frame.setTitle(frameName);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
	}
	
	
	/*--------------------------*/
	/*    PRIVATE METHODS       */
	/*--------------------------*/
	
	/**
	 * Method for creating the dataset
	 * @param data
	 * @param columnName
	 * @return
	 */
	private CategoryDataset addDataset(int[] data, String[] columnName) {

        var dataset = new DefaultCategoryDataset();
        
        for (int i=0; i < data.length; i++) {
        	
        	dataset.setValue(data[i], "Number of", columnName[i]);
        }
        
        return dataset;
    }
}
