package checkNews.support.gui;


import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;


/**
 * Source: https://java-swing-tips.blogspot.com/2016/09/changing-jtable-header-text-alignment.html
 */

public class HeaderTable implements TableCellRenderer {
	
	private int horizontalAlignment = SwingConstants.LEFT;
		  
	public HeaderTable(int horizontalAlignment) {
		    this.horizontalAlignment = horizontalAlignment;
	}
	
	@Override
	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		TableCellRenderer r = table.getTableHeader().getDefaultRenderer();
		JLabel l = (JLabel) r.getTableCellRendererComponent(
		table, value, isSelected, hasFocus, row, column);
		l.setHorizontalAlignment(horizontalAlignment);
		return l;
	}
	
}
