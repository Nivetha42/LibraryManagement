package com.zsgs.librarymanagement.datalayer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.zsgs.librarymanagement.model.Allot;
import com.zsgs.librarymanagement.model.Book;
import com.zsgs.librarymanagement.model.Credentials;
import com.zsgs.librarymanagement.model.Library;
import com.zsgs.librarymanagement.model.Transaction;
import com.zsgs.librarymanagement.model.User;

public class LibraryDatabase {
	private static LibraryDatabase libraryDatabase;
	private Library library;
	private List<Book> bookList = new ArrayList();
	private List<User> userList = new ArrayList();
	private List<Allot> assignedList = new ArrayList();
	private List<Credentials> credentialList = new ArrayList();
	private List<Transaction> history = new ArrayList();

	private static int id = 0;
	private static int userId = 100;

	public static LibraryDatabase getInstanse() {
		if (libraryDatabase == null) {
			libraryDatabase = new LibraryDatabase();
		}
		return libraryDatabase;
	}

	public Library getLibrary() {
		return library;
	}

	public List<Credentials> getCredentialList() {
		return credentialList;

	}

	public void insertLibrary(Library library2) {
		
		this.library = library2;

	}

	public List<Book> getAllBooks() {
		return bookList;
	}

	public boolean removeBook(int bookId) {
		for (Book book : bookList) {
			if (book.getId() == bookId && book.getIsAssigned() == false) {
				bookList.remove(book);
				return true;
			}
		}
		return false;
	}

	public Book getBook(int bookId) {
		for (Book book : bookList) {
			if (book.getId() == bookId) {
				return book;
			}
		}
		// select query with condition.
		return null;
	}

	public List<Book> searchBooks(String bookName) {
		List<Book> searchResult = new ArrayList<>();
		for (Book book : bookList) {
			if (book.getName().contains(bookName)) {
				searchResult.add(book);
			}
		}
		return searchResult;
	}

	public boolean insertBook(Book book) {
		boolean hasBook = false;
		for (Book addedBook : bookList) {
			if (addedBook.getName().equals(book.getName()) && addedBook.getAuthor().equals(book.getAuthor())) {
				hasBook = true;
				break;
			}
		}
		if (hasBook) {
			return false;
		} else {
			bookList.add(book);
			return true;
		}
	}

	public boolean insertUser(User user) {
		boolean hasUser = false;
		for (User addedUser : userList) {
			if (addedUser.getEmailId().equals(user.getEmailId())) {
				hasUser = true;
			}
		}
		if (hasUser) {
			return false;
		} else {
			user.setUserId(userId);
			userList.add(user);
			return true;
		}
	}

	public void insertCredentials(Credentials c) {
		c.setUserId(userId);
		userId++;
		credentialList.add(c);
	}

	public boolean isUser(int userId) {
		for (User user : userList) {
			if (user.getUserId() == userId) {
				return true;
			}
		}
		return false;

	}

	public boolean isBookAlloted(int bookId) {
		for (Book book : bookList) {
			if (book.getId() == bookId) {
				if (book.getIsAssigned() == false) {
					return true;
				}
			}
		}
		return false;

	}

	public List<User> getAllUsers() {
		return userList;
	}

	public void assignBook(int bookId, boolean state) {
		for (Book book : bookList) {
			if (book.getId() == bookId) {
				book.setAssigned(state);
			}
		}
	}

	public boolean assignBooks(Allot allotUser) {
		if (assignedList.size() == 0) {
			id = 0;
		}
		int count = 0;
		for (Allot allot : assignedList) {
			if (allot.getUserId() == allotUser.getUserId()) {
				count++;
			}
			if (count >= 2) {
				return false;
			}
		}

		if (isUser(allotUser.getUserId()) && isBookAlloted(allotUser.getBookId())) {
			allotUser.setAllotId(id + 1);
			id++;
			assignBook(allotUser.getBookId(), true);
			assignedList.add(allotUser);
			history.add(new Transaction(allotUser.getUserId(), allotUser.getBookId(), "borrow", new Date()));
			return true;
		}
		return false;

	}

	public boolean returnProcess(int allotId) {
		for (Allot allot : assignedList) {
			if (allot.getAllotId() == allotId) {
				assignBook(allot.getBookId(), false);
				history.add(new Transaction(allot.getUserId(), allot.getBookId(), "return", new Date()));
				assignedList.remove(allot);
				return true;
			}
		}
		return false;
	}
	
	

	public List<Allot> getAssignedList() {
		return assignedList;
	}

	public List<Transaction> getAllTransactionList() {
		return history;
	}

}
