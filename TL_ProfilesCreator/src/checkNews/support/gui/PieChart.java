package checkNews.support.gui;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

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

public class PieChart {
	
	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	/**
	 * 
	 * @param frameName
	 * @param chartName
	 * @param names
	 * @param columnFigures
	 */
	public PieChart(String frameName, String chartName, String[] names, int[] columnFigures) {
		
		JFrame frame = new JFrame();
		int[] data = columnFigures;
		
		@SuppressWarnings("rawtypes")
		DefaultPieDataset dataset = addDataset(data, names);
        JFreeChart chart = ChartFactory.createPieChart(chartName, dataset, false, true, false);
                
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
	 * @param names
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private DefaultPieDataset addDataset(int[] data, String[] names) {

        var dataset = new DefaultPieDataset();
        
        for (int i=0; i < data.length; i++) {
        	
        	dataset.setValue(names[i], data[i]);
        }
        
        return dataset;
    }

}
