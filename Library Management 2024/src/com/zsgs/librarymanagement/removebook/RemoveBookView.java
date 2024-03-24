package com.zsgs.librarymanagement.removebook;

import java.util.Scanner;

public class RemoveBookView {

	RemoveBookModel removeBookModel;
	public RemoveBookView()
	{
		this.removeBookModel=new RemoveBookModel(this);
	}
	public void initRemove()
	{
		System.out.println("\nEnter the Book Id of the Book to be Removed");
		Scanner sc=new Scanner(System.in);
		removeBookModel.removeBooks(sc.nextInt());
	}
	public void onSuccess()
	{
		System.out.println("Book Removed Successfully......");
		
	}
	public void alertText(String alertText)
	{
		System.out.println(alertText);
	}
}
