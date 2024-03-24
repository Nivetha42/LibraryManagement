package com.zsgs.librarymanagement.assignBooks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.model.Allot;
import com.zsgs.librarymanagement.model.Book;

public class AssignBooksModel {
	AssignBooksView assignBooksView;
	public AssignBooksModel(AssignBooksView assignBooksView) {
		this.assignBooksView=assignBooksView;
	}
	public boolean isBookPresent(List<Book> books,int bookId)
	{
		for(Book book:books)
		{
			if(book.getId()==bookId)
			{
				return true;
			}
		}
		return false;
	}
	public void assignBooks(Allot allot) {
	
		List<Book> books=LibraryDatabase.getInstanse().getAllBooks();
		if(LibraryDatabase.getInstanse().isUser(allot.getUserId())==false)
		{
			assignBooksView.alertText("User Not Found");
		}else if(isBookPresent(books,allot.getBookId())==false)
		{
			assignBooksView.alertText("Book is Not Present .... Enter a Valid Book Id ....");
		}
		else if(LibraryDatabase.getInstanse().isBookAlloted(allot.getBookId())==false)
		{
			assignBooksView.alertText("Book is Already Alloted");
		}
		else if(LibraryDatabase.getInstanse().assignBooks(allot)==true)
		{
			assignBooksView.alertText("Book is allocated Successfully to the Given User assigned ID is "+allot.getAllotId());
		}
		else
		{
			assignBooksView.alertText("User Reached his / her Maximum Limit for Borrowing Books");
		}
		
	}
	
}
