package ui;

import business.SystemController;
import entities.Book;
import entities.CheckoutRecord;
import entities.LibraryMember;
import models.CheckoutModel;
import models.ComboBoxItem;
import models.ResponseModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CheckoutWindow extends JFrame implements LibWindow {
    public static final CheckoutWindow INSTANCE = new CheckoutWindow();
    private static JTextField txtMember = new JTextField();
    private static JTextField txtBook = new JTextField();
    private boolean isInitialized = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CheckoutWindow checkoutWindow = new CheckoutWindow();
            checkoutWindow.init();
        });
    }

    @Override
    public void init() {
        setBounds(100, 100, 500, 200);
        setVisible(true);
        setResizable(false);
        Util.centerFrameOnDesktop(this);

        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(LoadMembers());
        container.add(LoadBookCopies());
        container.add(LoadCheckoutButton());
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

    public CheckoutWindow() {
    }

    private Container LoadMembers() {
        JLabel lblMember = new JLabel("Member");
        txtMember.setColumns(15);

        List<LibraryMember> members = new SystemController().allMembers();
        Vector model = new Vector();
        for (LibraryMember libraryMember : members) {
            String id = libraryMember.getMemberId();
            String name = libraryMember.getFirstName() + " " + libraryMember.getLastName();
            model.addElement(new ComboBoxItem(id, name));
        }

//        JComboBox comboBox = new JComboBox(model);
//        comboBox.addItemListener(e -> {
//            ComboBoxItem item = (ComboBoxItem) e.getItem();
//
//            LibraryMember foundLibraryMember = null;
//
//            for (LibraryMember libraryMember : members) {
//                if (libraryMember.getMemberId().equals(item.getId())) {
//                    foundLibraryMember = libraryMember;
//                    break;
//                }
//            }
//
//            if (foundLibraryMember != null) {
//                CheckoutWindow.selectedLibraryMember = foundLibraryMember;
//            }
//        });
//
//        if (members.size() > 0) {
//            CheckoutWindow.selectedLibraryMember = members.get(0);
//        }

        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(lblMember);
        container.add(txtMember);

        return container;
    }

    private Container LoadBookCopies() {
        JLabel lblBook = new JLabel("Books");
        txtBook.setColumns(15);

        List<Book> books = new SystemController().allBooksHasAvailableCopies();
        Vector model = new Vector();
        for (Book book : books) {
            String id = book.getId();
            String name = book.getTitle();
            model.addElement(new ComboBoxItem(String.valueOf(id), name));
        }

//        JComboBox comboBox = new JComboBox(model);
//        comboBox.addItemListener(e -> {
//            ComboBoxItem item = (ComboBoxItem) e.getItem();
//
//            Book foundBook = null;
//
//            for (Book book : books) {
//                if (book.getId().equals(item.getId())) {
//                    foundBook = book;
//                    break;
//                }
//            }
//
//            if (foundBook != null) {
//                CheckoutWindow.selectedBook = foundBook;
//                setMaxCheckoutLengthValueLabel(foundBook.getMaxCheckoutLength());
//            }
//        });
//
//        if (books.size() > 0) {
//            Book book = books.get(0);
//            setMaxCheckoutLengthValueLabel(book.getMaxCheckoutLength());
//            CheckoutWindow.selectedBook = book;
//        }

        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(lblBook);
        container.add(txtBook);

//        setMaxCheckoutLengthValueLabel(CheckoutWindow.selectedBook == null ? 0 : CheckoutWindow.selectedBook.getMaxCheckoutLength());
//
//        JLabel lblMaxCheckoutLength = new JLabel("Max checkout length");
//
//        container.add(lblMaxCheckoutLength);
//        container.add(this.lblMaxCheckoutLengthValue);

        return container;
    }

//    private Container LoadAddToListButton() {
//        JButton btnCheckout = new JButton("Add to list");
//        btnCheckout.addActionListener(e -> {
//            String checkoutLengthText = txtCheckoutLength.getText();
//            int checkoutLength = Integer.parseInt(checkoutLengthText);
//            int maxCheckoutLength = CheckoutWindow.selectedBook.getMaxCheckoutLength();
//
//            if (checkoutLength > maxCheckoutLength) {
//                JOptionPane.showMessageDialog(null, "Max of checkout length is " + maxCheckoutLength);
//                return;
//            }
//
//            for (CheckoutModel checkoutModel : checkoutModels) {
//                if (checkoutModel.getIsbn().equals(CheckoutWindow.selectedBook.getIsbn())) {
//                    return;
//                }
//            }
//
//            String isbn = CheckoutWindow.selectedBook.getIsbn();
//            String title = CheckoutWindow.selectedBook.getTitle();
//            this.checkoutModels.add(new CheckoutModel(CheckoutWindow.selectedBook.getIsbn(), CheckoutWindow.selectedBook.getTitle(), checkoutLength));
//
//            DefaultTableModel model = (DefaultTableModel) CheckoutWindow.tableModel;
//            model.addRow(new Object[]{isbn, title, checkoutLength});
//        });
//
//        Container container = new Container();
//        container.setLayout(new FlowLayout(FlowLayout.RIGHT));
//        container.add(btnCheckout);
//
//        return container;
//    }

//    private Container LoadCheckoutBooks() {
//        List<String> columns = new ArrayList<>();
//        columns.add("ISBN");
//        columns.add("Title");
//        columns.add("Max checkout length");
//
//        List<String[]> values = new ArrayList<>();
//        for (CheckoutModel checkoutModel : this.checkoutModels) {
//            values.add(new String[]{checkoutModel.getIsbn(), checkoutModel.getTitle(), String.valueOf(checkoutModel.getCheckoutLength())});
//        }
//
//        tableModel = new DefaultTableModel(values.toArray(new Object[][]{}), columns.toArray());
//        JTable checkoutBooksTable = new JTable(tableModel);
//        JScrollPane checkoutBooksScrollPane = new JScrollPane(checkoutBooksTable);
//
//        Container container = new Container();
//        container.setLayout(new FlowLayout(FlowLayout.CENTER));
//        container.add(checkoutBooksScrollPane);
//
//        return container;
//    }

    private Container LoadCheckoutButton() {
        JButton btnCheckout = new JButton("Checkout");
        btnCheckout.addActionListener(e -> {
            ResponseModel<CheckoutRecord> responseModel = new SystemController().checkout(txtMember.getText(), txtBook.getText());

            if (responseModel.getIsSuccess()) {
                JOptionPane.showMessageDialog(null, "Checked out is success");
            } else {
                JOptionPane.showMessageDialog(null, responseModel.getErrorMessage());
            }
        });

        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(btnCheckout);

        return container;
    }

//    private Container LoadCheckoutDate() {
//        JLabel lblCheckoutLength = new JLabel("Checkout length");
//        JLabel lblCheckoutDays = new JLabel("day(s)");
//
//        txtCheckoutLength.setColumns(3);
//        txtCheckoutLength.addKeyListener(new KeyAdapter() {
//            public void keyTyped(KeyEvent e) {
//            char c = e.getKeyChar();
//            if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
//                getToolkit().beep();
//                e.consume();
//            }
//            }
//        });
//
//        Container container = new Container();
//        container.setLayout(new FlowLayout(FlowLayout.LEFT));
//        container.add(lblCheckoutLength);
//        container.add(txtCheckoutLength);
//        container.add(lblCheckoutDays);
//
//        return container;
//    }

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

//    private void setMaxCheckoutLengthValueLabel(int length) {
//        this.lblMaxCheckoutLengthValue.setText(length + " day(s)");
//    }
}
