package com.zsgs.librarymanagement.managebook;

import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.zsgs.librarymanagement.model.Book;
import com.zsgs.librarymanagement.model.Transaction;
import com.zsgs.librarymanagement.removebook.RemoveBookView;
import com.zsgs.librarymanagement.searchbook.SearchBookView;
import com.zsgs.librarymanagement.viewbook.ViewBooksView;

public class ManageBookView {
	ManageBookModel manageBookModel;

	public ManageBookView() {
		manageBookModel = new ManageBookModel(this);
	}

	public void init() {
		Scanner sc=new Scanner(System.in);
		while(true)
		{
			System.out.println("1.Add Book\t2.Search Book\t3.View Book\t4.Remove Book\t0.Exit");
			int choice=sc.nextInt();
			switch(choice)
			{
			case 1:
				addBook();
				break;
			case 2:
				new SearchBookView().initSearch();
				break;
			case 3:
				new ViewBooksView().initView();
				break;
			case 4:
				new RemoveBookView().initRemove();
				break;
			case 0:
				return;
			default:
				System.out.println("Enter Valid Option");
			}
		}
		
		
	}
	public void addBook()
	{
		System.out.println("Enter Book Details: ");
		Scanner sc = new Scanner(System.in);
		Book book = new Book();
		System.out.println("Enter Book Id");
		book.setId(sc.nextLong());
		sc.nextLine();
		System.out.println("Enter Book Name:");
		book.setName(sc.nextLine());
		System.out.println("Enter Book Author: ");
		book.setAuthor(sc.nextLine());
		manageBookModel.addNewBook(book);
	}

	public void showAlert(String alertText) {
		System.out.println(alertText);
	}

	public void showLibraryName(String libraryName) {
		System.out.println("In Manage Book Library Name" + libraryName);
	}

	public void onBookAdded(Book book) {
		System.out.println("\n --------Book '" + book.getName() + "'Added Sucessfully ----------\n");
		checkForAddNewBook();
	}

	private void checkForAddNewBook() {
		System.out.println("Do you want to add more Books? \n Type Yes/No");
		Scanner sc = new Scanner(System.in);
		String choice = sc.next();
		if (choice.equalsIgnoreCase("yes")) {
			addBook();
		} else if (choice.equalsIgnoreCase("no")) {
			System.out.println("Thanks For Adding Books ");
		} else {
			System.out.println("Invalid Choice, Please Enter Valid Choice.");
			checkForAddNewBook();
		}
	}

	public void onBookExist(Book book) {
		System.out.println("\n --------Book '" + book.getName() + "'Already Exist ----------\n");
		checkForAddNewBook();
	}

	public void viewHistory() {
		Scanner sc=new Scanner(System.in);
		while(true)
		{
			System.out.println("Search by");
			System.out.println("1.UserId\t2.BookId\t3.ViewAll\t4.Exit");
			int choice=sc.nextInt();
			switch(choice)
			{
			case 1:
				System.out.println("Enter User Id : ");
				manageBookModel.getByUserId(sc.nextInt());
				break;
			case 2:
				System.out.println("Enter User Id : ");
				manageBookModel.getByBookId(sc.nextInt());
				break;
			case 3:
				manageBookModel.getHistory();
				break;
			case 4:
				return;
			default:
				System.out.println("Enter Valid Option");
				
				
			}
		}
		
	}

	public void displayTransactions(Transaction history, String bookName) {
		System.out.println(history.getUserId() + "\t\t" + history.getBookId() + "\t\t" + bookName + "\t\t"
				+ history.getType() + "\t\t" + new SimpleDateFormat("dd/MM/yyyy").format(history.getDate()));
	}

}
