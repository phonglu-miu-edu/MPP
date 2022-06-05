package ui;

import business.SystemController;
import entities.Book;
import entities.CheckoutRecord;
import entities.CheckoutRecordEntry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OverdueWindow extends JFrame implements LibWindow {
    public static final OverdueWindow INSTANCE = new OverdueWindow();
    private boolean isInitialized = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            OverdueWindow checkoutWindow = new OverdueWindow();
            checkoutWindow.init();
        });
    }

    @Override
    public void init() {
        setBounds(100, 100, 500, 500);
        setVisible(true);
        setResizable(false);
        Util.centerFrameOnDesktop(this);

        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(LoadCheckoutBooks());
        container.add(LoadStatusBar());

        this.isInitialized = true;
    }

    @Override
    public boolean isInitialized(){
        return this.isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        this.isInitialized = val;
    }

    public OverdueWindow() {
    }

    private Container LoadCheckoutBooks() {
        List<String> columns = new ArrayList<>();
        columns.add("Member Id");
        columns.add("Title");
        columns.add("Checkout length");

        List<CheckoutRecord> checkoutRecords = new SystemController().getAllCheckoutRecords();

        List<String[]> values = new ArrayList<>();
        for (CheckoutRecord checkoutModel : checkoutRecords) {
            for (CheckoutRecordEntry entry : checkoutModel.getEntries()) {
                Book book = entry.getBookCopy().getBook();
                values.add(new String[]{checkoutModel.getId(), book.getTitle(), entry.getDueDate().toString()});
            }
        }

        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][]{}), columns.toArray());
        JTable checkoutBooksTable = new JTable(tableModel);
        JScrollPane checkoutBooksScrollPane = new JScrollPane(checkoutBooksTable);

        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.add(checkoutBooksScrollPane);

        return container;
    }

    public Container LoadStatusBar() {
        JButton backToMainButton = new JButton("<= Back to Main");
        backToMainButton.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });

        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(backToMainButton);

        return container;
    }
}
