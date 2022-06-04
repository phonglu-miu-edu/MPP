package ui;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;

public class GuiControl {
    private static GuiControl control;

    private GuiControl() {}

    public static GuiControl getInstance() {
        if(control == null) {
            control = new GuiControl();
        }
        return control;
    }

    /** CURR_DIR is the current working directory, which is the directory of this project*/
    public static final String CURR_DIR = System.getProperty("user.dir");
    public static final String SPLASH_IMAGE = CURR_DIR + "/src/UI/resources/logo.png";
    public static int SCREEN_WIDTH = 1040;
    public static int SCREEN_HEIGHT = 680;

    public static int LOGIN_SCREEN_WIDTH = 640;
    public static int LOGIN_SCREEN_HEIGHT = 480;
    private static final int BOX_HEIGHT = 3;
    private static final int BOX_WIDTH = 3;

    //colors
    public static Color DARK_BLUE = Color.blue.darker();
    public static Color ERROR_RED = Color.RED;
    public static Color LIGHT_BLUE = new Color(0xf2ffff);
    public static Color DARK_GRAY = new Color(0xcccccc);
    public static Color APRICOT = new Color(0xfff2a9);

    public static Color MAIN_SCREEN_COLOR = LIGHT_BLUE;
    public static Color TABLE_BACKGROUND= LIGHT_BLUE;
    public static Color TABLE_PANE_BACKGROUND= LIGHT_BLUE;
    public static Color SCREEN_BACKGROUND = LIGHT_BLUE;
    public static Color QUANTITY_SCREEN_BGRND = APRICOT;
    public static Color QUANTITY_SCREEN_TEXT = DARK_BLUE;
    public static Color TABLE_HEADER_FOREGROUND = LIGHT_BLUE;
    public static Color TABLE_HEADER_BACKGROUND = DARK_BLUE;
    public static Color WINDOW_BORDER = DARK_BLUE;
    public static Color FILLER_COLOR = Color.white;

    public static void centerFrameOnDesktop(Component f) {
        final int SHIFT_AMOUNT = 0;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        int frameHeight = f.getSize().height;
        int frameWidth = f.getSize().height;
        f.setLocation(((width - frameWidth)/2) - SHIFT_AMOUNT, (height - frameHeight)/3);
    }

    public static Font makeSmallFont(Font f) {
        return new Font(f.getName(), f.getStyle(), (f.getSize()-2));
    }

    public static Font makeLargeFont(Font f) {
        return new Font(f.getName(), f.getStyle(), (f.getSize()+2));
    }
    public static Font makeVeryLargeFont(Font f) {
        return new Font(f.getName(), f.getStyle(), (f.getSize()+4));
    }

    public static Font makeBoldFont(Font f) {
        return new Font(f.getName(), Font.BOLD, f.getSize());
    }

    public static Font makeDialogFont(Font f) {
        return new Font("Dialog", f.getStyle(), f.getSize());
    }

    public static JPanel createStandardTablePanePanel(JTable table, JScrollPane tablePane){
        //configure header
        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER_BACKGROUND);
        header.setForeground(TABLE_HEADER_FOREGROUND);
        Font f = header.getFont();
        f = GuiControl.makeBoldFont(f);
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

    public static JPanel createStandardButtonPanel(JButton[] buttons) {
        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(FILLER_COLOR);
        if(buttons != null && buttons.length>0) {
            for(int i = 0; i < buttons.length; ++i) {
                buttonPanel.add(buttons[i]);
            }
        }
        return buttonPanel;
    }

    public static Box.Filler createHBrick(int numStackedHorizontally) {
        int width = BOX_WIDTH * numStackedHorizontally;
        Dimension d = new Dimension(width, BOX_HEIGHT);
        return new Box.Filler(d,d,d);
    }
}
