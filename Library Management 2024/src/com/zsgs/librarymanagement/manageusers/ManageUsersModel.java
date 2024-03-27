package com.zsgs.librarymanagement.manageusers;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.model.Allot;
import com.zsgs.librarymanagement.model.Book;
import com.zsgs.librarymanagement.model.Credentials;
import com.zsgs.librarymanagement.model.User;

public class ManageUsersModel {
	private ManageUsersView manageUsersView;

	public ManageUsersModel(ManageUsersView manageUsersView) {
		this.manageUsersView = manageUsersView;
	}

	public void addNewUser(User user, Credentials credentials) {
		if (LibraryDatabase.getInstanse().insertUser(user)) {
			LibraryDatabase.getInstanse().insertCredentials(credentials);
			manageUsersView.onUserAdded(user);

		} else {
			manageUsersView.onUserExist(user);
		}
	}

	public void checkUser(Credentials credentials) throws ParseException {
		List<Credentials> c = LibraryDatabase.getInstanse().getCredentialList();

		for (Credentials cd : c) {

			if (cd.getUserId() == credentials.getUserId() && cd.getPassword().equals(credentials.getPassword())) {
				manageUsersView.OnsucessLogin(cd.getUserId());
				return;
			}
		}
		manageUsersView.alertText("InValid Credentials");

	}

	public String getUserName(int userId) {
		List<User> userList = LibraryDatabase.getInstanse().getAllUsers();
		for (User user : userList) {
			if (user.getUserId() == userId) {
				return user.getUserName();
			}
		}
		return "User Not Found";

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

	public void getUserDetails(int userId) throws ParseException {
		List<Allot> allotedList = LibraryDatabase.getInstanse().getAssignedList();
		int flag = 0;
		manageUsersView.alertText("----------------------------------------------------------------------------");
		manageUsersView.alertText("AssignedId\t\tBookName\t\tDueDate");
		manageUsersView.alertText("----------------------------------------------------------------------------");
		for (Allot allot : allotedList) {
			if (allot.getUserId() == userId) {
				manageUsersView.display(allot, getUserName(allot.getUserId()), getBookName(allot.getBookId()));
				flag = 1;
			}
		}
		if (flag == 0) {
			manageUsersView.alertText("\nBook Not Alloted");
		}
		manageUsersView.alertText("----------------------------------------------------------------------------");

	}

	public void getAllUsers() {
		List<User> userList = LibraryDatabase.getInstanse().getAllUsers();
		manageUsersView.alertText("----------------------------------------------------------------------------");
		manageUsersView.alertText("UserId\t\tUserName\t\tEmailId");
		manageUsersView.alertText("----------------------------------------------------------------------------");
		for (User user : userList) {
			manageUsersView.displayAllUsers(user);
		}
		manageUsersView.alertText("----------------------------------------------------------------------------");
	}

}
