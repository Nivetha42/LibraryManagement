package com.zsgs.librarymanagement.manageusers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.zsgs.librarymanagement.model.Allot;
import com.zsgs.librarymanagement.model.Credentials;
import com.zsgs.librarymanagement.model.Transaction;
import com.zsgs.librarymanagement.model.User;

public class ManageUsersView {
	private ManageUsersModel manageUsersModel;

	public ManageUsersView() {
		manageUsersModel = new ManageUsersModel(this);
	}

	public void initAdd() throws ParseException {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("1-SignUp \n2-Login \n3-Exit \nEnter Your Choice: ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				AddUser();
				break;
			case 2:
				loginUser();
				break;
			case 3:
				System.out.println("-------------------Thank You-----------------------------");
				return;
			default:
				System.out.println("Enter valid Option");

			}

		}
	}

	private void loginUser() throws ParseException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the UserId");
		int uId = sc.nextInt();
		System.out.println("Enter the Password");
		String password = sc.next();
		Credentials credentials = new Credentials();
		credentials.setUserId(uId);
		credentials.setPassword(password);
		manageUsersModel.checkUser(credentials);

	}

	public void getAllUsers() {
		manageUsersModel.getAllUsers();
	}

	public void AddUser() {
		System.out.println("Enter the User Details :  ");
		Scanner sc = new Scanner(System.in);
		User user = new User();
		Credentials c = new Credentials();
		System.out.println("Enter the UserName : ");
		String userName = sc.nextLine();
		user.setUserName(userName);
		System.out.println("Enter User EmailId : ");
		user.setEmailId(sc.nextLine());
		System.out.println("Enter the Phone Number : ");
		user.setPhoneNo(sc.nextLong());
		System.out.println("Enter Password : ");
		String password = sc.next();
		c.setPassword(password);
		manageUsersModel.addNewUser(user, c);

	}

	public void OnsucessLogin(int userId) throws ParseException {
		System.out.println("Login Successful");
		manageUsersModel.getUserDetails(userId);

	}

	public void printUser(User user) {
		manageUsersModel.getAllUsers();
	}

	public void onUserExist(User user) {
		System.out.println("\n------- User '" + user.getUserName() + "' Already Exist ------- \n");

	}

	public void onUserAdded(User user) {
		System.out.println("\n------- User '" + user.getUserName() + "' Added successfully ...User Id "
				+ user.getUserId() + "------- \n");

	}

	public void alertText(String alertText) {
		System.out.println(alertText);

	}

	public void display(Allot allot, String userName, String bookName) {
		System.out.println(allot.getAllotId() + "\t\t" + bookName + "\t\t" + new SimpleDateFormat("dd/MM/yyyy").format(allot.getReturnDate()));
	}

	public void displayAllUsers(User user) {
		System.out.println(user.getUserId() + "\t\t" + user.getUserName() + "\t\t" + user.getEmailId());

	}

}
