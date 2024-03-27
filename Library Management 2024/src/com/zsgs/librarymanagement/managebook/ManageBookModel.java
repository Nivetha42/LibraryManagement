package com.zsgs.librarymanagement.managebook;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.model.Book;
import com.zsgs.librarymanagement.model.Library;
import com.zsgs.librarymanagement.model.Transaction;

public class ManageBookModel {
	private ManageBookView manageBookView;

	public ManageBookModel(ManageBookView manageBookView) throws ParseException {
		this.manageBookView = manageBookView;
		Library library = LibraryDatabase.getInstanse().getLibrary();
	}

	public void addNewBook(Book book) {
		if (LibraryDatabase.getInstanse().insertBook(book)) {
			manageBookView.onBookAdded(book);
		} else {
			manageBookView.onBookExist(book);
		}
	}

	public String getBookName(int bookId) {
		List<Book> bookList = LibraryDatabase.getInstanse().getAllBooks();
		for (Book book : bookList) {
			if (book.getId() == bookId) {
				return book.getName();
			}
		}
		return "Book Not Found";

	}

	public void getHistory() throws Exception {

		List<Transaction> history = LibraryDatabase.getInstanse().getAllTransactionList();
		if (history == null) {
			manageBookView.showAlert("No Transactions Yet");
			return;
		}
		manageBookView.showAlert("-------------------------------History Details------------------------------");
		manageBookView.showAlert("UserId\t\tBookId\t\tBook Name\t\tType\t\tDate");
		for (Transaction t : history) {
			manageBookView.displayTransactions(t, getBookName(t.getBookId()));
		}
		manageBookView.showAlert("----------------------------------------------------------------------------");

	}

	public void getByUserId(int userId) throws Exception {
		List<Transaction> history = LibraryDatabase.getInstanse().getAllTransactionList();
		if (history == null) {
			manageBookView.showAlert("No Transactions Yet");
			return;
		}
		manageBookView.showAlert("-------------------------------History Details------------------------------");
		manageBookView.showAlert("UserId\t\tBookId\t\tBook Name\t\tType\t\tDate");
		for (Transaction t : history) {
			if(t.getUserId()==userId)
			{
			manageBookView.displayTransactions(t, getBookName(t.getBookId()));
			}
		}
		manageBookView.showAlert("----------------------------------------------------------------------------");
	}

	public void getByBookId(int bookId) throws Exception {
		List<Transaction> history = LibraryDatabase.getInstanse().getAllTransactionList();
		if (history == null) {
			manageBookView.showAlert("No Transactions Yet");
			return;
		}
		manageBookView.showAlert("-------------------------------History Details------------------------------");
		manageBookView.showAlert("UserId\t\tBookId\t\tBook Name\t\tType\t\tDate");
		for (Transaction t : history) {
			if(t.getBookId()==bookId)
			{
			manageBookView.displayTransactions(t, getBookName(t.getBookId()));
			}
		}
		manageBookView.showAlert("----------------------------------------------------------------------------");
		
	}

}
