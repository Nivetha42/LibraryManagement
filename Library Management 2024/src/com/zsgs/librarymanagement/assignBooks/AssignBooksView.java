package com.zsgs.librarymanagement.assignBooks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.zsgs.librarymanagement.model.Allot;

public class AssignBooksView {
	AssignBooksModel AssignBooksModel;
	public AssignBooksView()
	 {
		 AssignBooksModel=new AssignBooksModel(this);
	 }
	public void initAssignBooks()
	{
		System.out.println("Assign Books to Users ");
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the UserId");
		Allot allot=new Allot();
		allot.setUserId(sc.nextInt());
		System.out.println("Enter the BookId");
		allot.setBookId(sc.nextInt());
		System.out.println("Enter the Return Date (dd/mm/yyyy): ");
		String returnDate=sc.next();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  
		try {
			allot.setReturnDate(sdf.parse(returnDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		AssignBooksModel.assignBooks(allot);
		
	}
	public void alertText(String alertText)
	{
		System.out.println(alertText);
		checkAssignBooks();
	}
	private void checkAssignBooks() {
		System.out.println("Do you want to Assign Books Again \n Type Yes / No ");
		Scanner scanner=new Scanner(System.in);
		String choice=scanner.next();
		if(choice.equalsIgnoreCase("yes"))
		{
			initAssignBooks();
		}
		else if(choice.equalsIgnoreCase("no"))
		{
			System.out.println("--------Thank You---------");
		}
		else
		{
			System.out.println("Invalid Choice, Please Enter Valid Choice.\n");
			checkAssignBooks();
		}
		
	}

	
}
