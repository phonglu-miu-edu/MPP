package ui;

import business.SystemController;
import entities.Book;
import entities.LibraryMember;
import models.ComboBoxItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

public class CheckoutWindow extends JFrame {
    JLabel lblMaxCheckoutLengthValue = new JLabel();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new CheckoutWindow().setVisible(true));
    }

    public CheckoutWindow() {
        setBounds(100, 100, 400, 300);
        initialize();
    }

    private void initialize() {
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(LoadMembers());
        container.add(LoadBooks());

        List<Book> books = new SystemController().allBooks();
        if (books.size() > 0) {
            container.add(LoadDate(books.get(0)));
        }

        container.add(LoadCheckoutDate());
    }

    private Container LoadMembers() {
        List<LibraryMember> members = new SystemController().allMembers();
        Vector model = new Vector();
        for (LibraryMember libraryMember : members) {
            String id = libraryMember.getMemberId();
            String name = libraryMember.getFirstName() + " " + libraryMember.getLastName();
            model.addElement(new ComboBoxItem(id, name));
        }

        JComboBox comboBox = new JComboBox(model);
        JLabel lblMember = new JLabel("Member");

        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(lblMember);
        container.add(comboBox);

        return container;
    }

    private Container LoadBooks() {
        List<Book> books = new SystemController().allBooks();
        Vector model = new Vector();
        for (Book book : books) {
            String id = book.getId();
            String name = book.getTitle();
            model.addElement(new ComboBoxItem(String.valueOf(id), name));
        }

        JComboBox comboBox = new JComboBox(model);
        comboBox.addItemListener(e -> {
            ComboBoxItem item = (ComboBoxItem) e.getItem();

            Book foundBook = null;

            for (Book book : books) {
                if (book.getId().equals(item.getId())) {
                    foundBook = book;
                    break;
                }
            }

            if (foundBook != null) {
                setMaxCheckoutLengthValueLabel(foundBook.getMaxCheckoutLength());
            }
        });

        JLabel lblMember = new JLabel("Books");

        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(lblMember);
        container.add(comboBox);

        if (books.size() > 0) {
            Book firstBook = books.get(0);
            setMaxCheckoutLengthValueLabel(firstBook.getMaxCheckoutLength());
        }

        return container;
    }

    private Container LoadDate(Book book) {
        setMaxCheckoutLengthValueLabel(book == null ? 0 : book.getMaxCheckoutLength());

        JLabel lblMaxCheckoutLength = new JLabel("Max checkout length");

        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(lblMaxCheckoutLength);
        container.add(this.lblMaxCheckoutLengthValue);

        return container;
    }

    private Container LoadCheckoutDate() {
        JLabel lblCheckoutLength = new JLabel("Checkout length");
        JLabel lblCheckoutDays = new JLabel("day(s)");
        JTextField txtCheckout = new JTextField();
        txtCheckout.setColumns(3);
        txtCheckout.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });

        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(lblCheckoutLength);
        container.add(txtCheckout);
        container.add(lblCheckoutDays);

        return container;
    }

    private void setMaxCheckoutLengthValueLabel(int length) {
        this.lblMaxCheckoutLengthValue.setText(length + " day(s)");
    }
}
