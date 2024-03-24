package com.zsgs.librarymanagement.login;

import java.util.Scanner;

import com.zsgs.librarymanagement.LibraryManagement2024;
import com.zsgs.librarymanagement.manageusers.ManageUsersView;

public class LoginView {
	private LoginModel loginModel;

	public LoginView() {
		loginModel = new LoginModel(this);
	}

	public void init() {
		System.out.println("-------------------" + LibraryManagement2024.getInstance().getAppName()
				+ "-------------------------------");
		while (true) {
			System.out.println("Please Proceed to Login");
			System.out.println("User or Admin ?");
			Scanner sc = new Scanner(System.in);
			String s = sc.nextLine();
			if (s.equalsIgnoreCase("user")) {
				new ManageUsersView().initAdd();
			} else if (s.equalsIgnoreCase("admin")) {
				proceedLogin();
			}
		}
	}

	public void onLoginFailed(String alertText) {
		System.out.println(alertText);
		checkForLogin();
	}

	private void checkForLogin() {
		System.out.println("Do you want to Try Again \n Type Yes / No ");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();
		if (choice.equalsIgnoreCase("yes")) {
			proceedLogin();
		} else if (choice.equalsIgnoreCase("no")) {
			System.out.println("--------Thank You---------");
		} else {
			System.out.println("Invalid Choice, Please Enter Valid Choice.\n");
			checkForLogin();
		}

	}

	public void OnSuccess() {
		System.out.println("Login Success!.....\n-----Welcome To " + LibraryManagement2024.getInstance().getAppName()
				+ "-------");
		loginModel.librarySetup();

	}

	private void proceedLogin() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the Username");
		String userName = scanner.next();
		System.out.println("Enter the Password");
		String password = scanner.next();

		loginModel.validateUser(userName, password);
	}

}
