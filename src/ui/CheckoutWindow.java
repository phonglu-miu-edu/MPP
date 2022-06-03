package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

import business.SystemController;
import entities.Book;
import entities.LibraryMember;
import models.ComboBoxItem;

public class CheckoutWindow extends JFrame {
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
	}

	private Container LoadMembers() {
		List<LibraryMember> members = SystemController.allMembers();
		Vector model = new Vector();
		for (LibraryMember libraryMember : members) {
			String id = libraryMember.getMemberId();
			String name = libraryMember.getFirstName() + " " + libraryMember.getLastName();
			model.addElement(new ComboBoxItem(id, name ));
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
		List<Book> books = SystemController.allBooks();
		Vector model = new Vector();
		for (Book book : books) {
			String id = book.getId();
			String name = book.getTitle();
			model.addElement(new ComboBoxItem(String.valueOf(id), name ));
		}

		JComboBox comboBox = new JComboBox(model);
		comboBox.addItemListener(e -> {
			ComboBoxItem item = (ComboBoxItem)e.getItem();

			Book foundBook = null;

			for (Book book : books) {
				if (book.getId().equals(item.getId())) {
					foundBook = book;
					break;
				}
			}

			if (foundBook != null) {

			}
		});

		JLabel lblMember = new JLabel("Books");

		Container container = new Container();
		container.setLayout(new FlowLayout(FlowLayout.LEFT));
		container.add(lblMember);
		container.add(comboBox);

		return container;
	}

	private Container LoadDate(Book book) {
		JLabel lblMember = new JLabel("Max checkout date");
		JLabel lblMaxCheckoutLength = new JLabel(String.valueOf(book.getMaxCheckoutLength()));

		Container container = new Container();
		container.setLayout(new FlowLayout(FlowLayout.LEFT));
		container.add(lblMember);
		container.add(lblMaxCheckoutLength);

		return container;
	}
}
