package com.zsgs.librarymanagement.librarysetup;

import java.util.Scanner;

import com.zsgs.librarymanagement.LibraryManagement2024;
import com.zsgs.librarymanagement.assignBooks.AssignBooksView;
import com.zsgs.librarymanagement.login.LoginView;
import com.zsgs.librarymanagement.managebook.ManageBookView;
import com.zsgs.librarymanagement.manageusers.ManageUsersView;
import com.zsgs.librarymanagement.model.Library;
import com.zsgs.librarymanagement.removebook.RemoveBookView;
import com.zsgs.librarymanagement.returnprocess.ReturnProcessView;
import com.zsgs.librarymanagement.searchbook.SearchBookView;
import com.zsgs.librarymanagement.viewbook.ViewBooksView;

public class LibrarySetupView {
	public LibrarySetupModel librarySetupModel;

	public LibrarySetupView() {
		librarySetupModel = new LibrarySetupModel(this);
	}

	public void libraryInit() {
		librarySetupModel.startSetUp();
	}

	public void initiateSetUp() {
		// get details using scanner
		System.out.println("\nEnter Library Details : ");
		Scanner sc = new Scanner(System.in);
		Library library = new Library();
		System.out.println("\nEnter Library Name : ");
		library.setLibraryName(sc.nextLine());
		System.out.println("\nEnter Library Email: ");
		library.setEmailId(sc.nextLine());
		library.setId(1);
		librarySetupModel.createLibrary(library);
	}

	public void onSetUpComplete(Library library) {
		System.out.println("\nLibrary SetUp Completed. ");
		System.out.println("\nCurrent Library Name - " + library.getLibraryName());
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println(
					"\n1.Manage Books \n2.List All Users \n3.Assign Books to Users \n4.Return Books \n5.View Allotment List \n6.View OverDue Books \n7.View History \n8.LogOut \n0.Exit \nEnter Your Choice :  ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				new ManageBookView().init();
				break;
			case 2:
				new ManageUsersView().getAllUsers();
				break;
			case 3:
				new AssignBooksView().initAssignBooks();
				break;
			case 4:
				new ReturnProcessView().init();
				break;
			case 5:
				new ReturnProcessView().displayAllotedBook();
				break;
			case 6:
				new ReturnProcessView().overDueBooks();
				break;
			case 7:
				new ManageBookView().viewHistory();
				break;
			case 8:
				System.out.println("\n --- You are Logged Out Sucessfully --- \n");
				new LoginView().init();
				return;
			case 0:
				System.out.println("\n--- Thanks for Using " + LibraryManagement2024.getInstance().getAppName() + "----");
				return;
			default:
				System.out.println("Please Enter Valid Choice \n");
			}
		}

	}

	public void showAlert(String alertText) {
		System.out.println(alertText);
		initiateSetUp();
	}

}
