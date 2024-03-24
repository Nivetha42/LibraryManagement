package com.zsgs.librarymanagement;

import com.zsgs.librarymanagement.login.LoginView;

public class LibraryManagement2024 {

	private String appName = "Library Management System";
	private String appVersion = "0.0.1";
	private static LibraryManagement2024 libraryManagement;

	private LibraryManagement2024() {

	}

	public static LibraryManagement2024 getInstance() {
		if (libraryManagement == null) {
			libraryManagement = new LibraryManagement2024();
		}
		return libraryManagement;

	}

	private void create() {
		LoginView loginView = new LoginView();
		loginView.init();
	}

	public String getAppName() {
		return appName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public static void main(String[] args) {
		LibraryManagement2024.getInstance().create();

	}

}
