package com.zsgs.librarymanagement.login;

import com.zsgs.librarymanagement.librarysetup.LibrarySetupView;

class LoginModel {
	private LoginView loginView;

	LoginModel(LoginView loginView) {
		this.loginView = loginView;
	}

	public void validateUser(String userName, String password) {
		if (isValidUserName(userName)) {
			if (isValidPassword(userName,password)) {
				loginView.OnSuccess();

			} else {
				loginView.onLoginFailed("Incorrect Password.");
			}
		} else {
			loginView.onLoginFailed("Incorrect Username.");
		}
	}

	public void librarySetup() {
		LibrarySetupView librarySetUpView = new LibrarySetupView();
		librarySetUpView.libraryInit();
	}

	private boolean isValidUserName(String userName) {
		return userName.equals("zsgs") || userName.equals("zsgsAdmin");
	}

	private boolean isValidPassword(String userName,String password) {
		return (userName.equals("zsgs")&& password.equals("admin"))||userName.equals("zsgsAdmin")&& password.equals("admin123");
	}

}
