package checkNews.support.gui;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

/**
 * This class produce a Line chart
 * requires org.jfree.jfreechart; en module-info.java  and jfreechart-1.5.3.jar.  Set on 'properties-java build path-libraries-module path'
 * @author Jose Javier Culebras
 * @version 1.0
 * 
 */

public class LineChart {
	
	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	/**
	 * 
	 * @param frameName
	 * @param chartName
	 * @param columnFigures
	 * @param axisYName
	 * @param axisXName
	 * @param dataSetLabel
	 * @param minXRange
	 * @param maxXRange
	 */
	public LineChart(String frameName, String chartName, int[] columnFigures, String axisYName, String axisXName, String dataSetLabel, int minXRange, int maxXRange ) {
	
		JFrame frame = new JFrame();
		int[] data = columnFigures;
		
		XYDataset dataset = addDataset(data, dataSetLabel);
		JFreeChart chart = ChartFactory.createXYLineChart(chartName,axisYName, axisXName, dataset, PlotOrientation.VERTICAL, true, true, false);
	
		XYPlot plot = chart.getXYPlot();					
		NumberAxis axis = (NumberAxis) plot.getDomainAxis();
		axis.setAutoRange(false);
		axis.setRange(minXRange, maxXRange); //Set the X range
		
        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.setTitle(new TextTitle(chartName, new Font("Serif", java.awt.Font.BOLD, 18) ) );

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
	 * @param dataSetLabel
	 * @return
	 */
	private XYDataset addDataset(int[] data, String dataSetLabel ) {
		
		var series = new XYSeries(dataSetLabel);
        
        for (int i=0; i < data.length; i++) {
        	
        	series.add(i, data[i]);
        } 
        
        var dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }
	
}
