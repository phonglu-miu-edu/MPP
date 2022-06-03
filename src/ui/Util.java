package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;



public class Util {
	public static final Color DARK_BLUE = Color.BLUE.darker();
	public static final Color ERROR_MESSAGE_COLOR = Color.RED.darker(); //dark red
	public static final Color INFO_MESSAGE_COLOR = new Color(24, 98, 19); //dark green
	public static final Color LINK_AVAILABLE = DARK_BLUE;
	public static final Color LINK_NOT_AVAILABLE = Color.gray;
	public static Color LIGHT_BLUE = new Color(0xf2ffff);
	

	public static Color TABLE_HEADER_FOREGROUND = LIGHT_BLUE;
	public static Color TABLE_HEADER_BACKGROUND = DARK_BLUE;
	public static Color TABLE_PANE_BACKGROUND= LIGHT_BLUE;
	public static Color WINDOW_BORDER = DARK_BLUE;
	public static Color TABLE_BACKGROUND= LIGHT_BLUE;
	public static Color FILLER_COLOR = Color.white;
	//rgb(18, 75, 14)

	public static int SCREEN_WIDTH = 640;
	public static int SCREEN_HEIGHT = 480;
	
	public static Font makeSmallFont(Font f) {
        return new Font(f.getName(), f.getStyle(), (f.getSize()-2));
    }
	
	public static void adjustLabelFont(JLabel label, Color color, boolean bigger) {
		if(bigger) {
			Font f = new Font(label.getFont().getName(), 
					label.getFont().getStyle(), (label.getFont().getSize()+2));
			label.setFont(f);
		} else {
			Font f = new Font(label.getFont().getName(), 
					label.getFont().getStyle(), (label.getFont().getSize()-2));
			label.setFont(f);
		}
		label.setForeground(color);
		
	}
	/** Sorts a list of numeric strings in natural number order */
	public static List<String> numericSort(List<String> list) {
		Collections.sort(list, new NumericSortComparator());
		return list;
	}
	
	static class NumericSortComparator implements Comparator<String>{
		@Override
		public int compare(String s, String t) {
			if(!isNumeric(s) || !isNumeric(t)) 
				throw new IllegalArgumentException("Input list has non-numeric characters");
			int sInt = Integer.parseInt(s);
			int tInt = Integer.parseInt(t);
			if(sInt < tInt) return -1;
			else if(sInt == tInt) return 0;
			else return 1;
		}
	}
	
	public static boolean isNumeric(String s) {
		if(s==null) return false;
		try {
			Integer.parseInt(s);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	public static void centerFrameOnDesktop(Component f) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int height = toolkit.getScreenSize().height;
		int width = toolkit.getScreenSize().width;
		int frameHeight = f.getSize().height;
		int frameWidth = f.getSize().width;
		f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
	}
    
    public static Font makeBoldFont(Font f) {
        return new Font(f.getName(), Font.BOLD, f.getSize());
    }
    
    public static JPanel createStandardTablePanePanel(JTable table, JScrollPane tablePane){
    	//configure header
        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER_BACKGROUND);
        header.setForeground(TABLE_HEADER_FOREGROUND);
        Font f = header.getFont();
        f = makeBoldFont(f);
        header.setFont(f);
        table.setTableHeader(header);  
        
        //set colors
		tablePane.getViewport().setBackground(TABLE_PANE_BACKGROUND);
		tablePane.setBorder(new WindowBorder(WINDOW_BORDER));
		table.setBackground(TABLE_BACKGROUND);
		
		//place inside a JPanel and return	
		JPanel tablePanePanel = new JPanel();
    	tablePanePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		tablePanePanel.add(tablePane);
		tablePanePanel.setBackground(FILLER_COLOR);
		
		return tablePanePanel;
    }
    public static void createCustomColumns(JTable table, 
            int tableWidth, 
            float[] columnProportions,
            String[] columnHeaders) {

		table.setAutoCreateColumnsFromModel(false);
		int num = columnHeaders.length;
		for(int i = 0; i < num; ++i) {
			TableColumn column = new TableColumn(i);
			column.setHeaderValue(columnHeaders[i]);
			column.setMinWidth(Math.round(columnProportions[i]*tableWidth));
			table.addColumn(column);
		}
    }
}
