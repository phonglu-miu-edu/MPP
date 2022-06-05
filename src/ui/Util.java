package ui;

import dataaccess.Auth;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	public static final String MAIN_LABEL = "Library Management System";
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

	public static final int TABLE_WIDTH = Math.round(0.75f*Util.SCREEN_WIDTH);
    public static final int DEFAULT_TABLE_HEIGHT = Math.round(0.75f*Util.SCREEN_HEIGHT);
    
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

	public static void showMainScreen(Auth auth) {
		//Initialized all windows
		LibrarySystem.initAllWindows();
		LibrarySystem.hideAllWindows();
		Util.centerFrameOnDesktop(LibrarySystem.INSTANCE);
		LibrarySystem.INSTANCE.setVisible(true);
	}

	public static void showLoginForm() {
		LibrarySystem.INSTANCE.setVisible(false);
		LoginWindow loginForm = new LoginWindow();
		loginForm.setVisible(true);
	}

	public static void makeLabel(JPanel p, String s) {
		JLabel l = new JLabel(s);
		p.add(leftPaddedPanel(l));
	}

	public static JPanel leftPaddedPanel(JLabel label) {
		JPanel paddedPanel = new JPanel();
		paddedPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		paddedPanel.add(GuiControl.createHBrick(1));
		paddedPanel.add(label);
		paddedPanel.setBackground(GuiControl.SCREEN_BACKGROUND);
		return paddedPanel;
	}

	public static boolean validateNumberFormat(JTextField textField, String format, boolean isMandatory, String defaultText) {
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(textField.getText().trim());

		if(!matcher.matches()) {
			if(!isMandatory) {
				if(textField.getText().trim().equals(defaultText)) {
					textField.setBackground(Color.WHITE);
					return true;
				}
			}
			textField.setBackground(Color.PINK);
			return false;
		} else {
			textField.setBackground(Color.WHITE);
			return true;
		}

	}
	public static boolean validateMandatory(JTextField textField) {
		if(textField.getText().trim().equals("")) {
			textField.setBackground(Color.PINK);
			return false;
		} else {
			textField.setBackground(Color.WHITE);
			return true;
		}
	}

	public static void resetFields(List<JTextField> list) {
		for(JTextField field : list) {
			System.out.println("field:"+field);
			field.setText("");
		}
	}
	
	public static void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 15; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        if(width > 300)
	            width=300;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
}
