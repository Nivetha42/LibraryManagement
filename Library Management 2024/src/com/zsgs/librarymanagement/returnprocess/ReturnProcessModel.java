package com.zsgs.librarymanagement.returnprocess;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.model.Allot;
import com.zsgs.librarymanagement.model.Book;
import com.zsgs.librarymanagement.model.User;

public class ReturnProcessModel {
	ReturnProcessView returnProcessView;
	public ReturnProcessModel(ReturnProcessView returnProcessView2) {
		this.returnProcessView=returnProcessView2;
	}
	public void manageReturnProcess(int assignId) {
		
		if(LibraryDatabase.getInstanse().returnProcess(assignId))
		{
			returnProcessView.alertText("Book Returned Successfully.");
		}
		else
		{
			returnProcessView.alertText("Incorrect AssignId");
		}
	}
	public String getUserName(int userId)
	{
		List<User> userList=LibraryDatabase.getInstanse().getAllUsers();
			for (User user : userList) {
				if (user.getUserId() == userId) {
					return user.getUserName();
				}
			}
		return "User Not Found";
		
	}
	public String getBookName(int bookId)
	{
		List<Book> bookList=LibraryDatabase.getInstanse().getAllBooks();
			for (Book book : bookList) {
				if (book.getId() == bookId) {
					return book.getName();
				}
			}
		return "Book Not Found";
		
	}
	public void viewAllAllotment()
	{
		int flag=0;
		List<Allot> allotedList=LibraryDatabase.getInstanse().getAssignedList();
		returnProcessView.alertText("------------------------------------------------------------------------------------------");
		returnProcessView.alertText("Allot Id\tUser Id\t\tUserName\tBook Id\t\tBook Name\tReturn Date");
		returnProcessView.alertText("------------------------------------------------------------------------------------------");
		for(Allot allot:allotedList)
		{
			returnProcessView.display(allot,getUserName(allot.getUserId()),getBookName(allot.getBookId()));
			flag=1;
		}
		if(flag==0)
		{
			returnProcessView.alertText("\n\tNo Books are Assigned -------Please Allot Some Books");
		}
		returnProcessView.alertText("------------------------------------------------------------------------------------------");
		
	}
	public void findOverDue() {
		List<Allot> allotedList=LibraryDatabase.getInstanse().getAssignedList();
		Date date=new Date();
		int flag=0;
		returnProcessView.alertText("------------------------------------------------------------------------------------------");
		returnProcessView.alertText("Allot Id\tUser Id\t\tUserName\tBook Id\t\tBook Name\tReturn Date");
		returnProcessView.alertText("------------------------------------------------------------------------------------------");
		for(Allot allot:allotedList)
		{
			if(date.compareTo(allot.getReturnDate())>0)
					{
				returnProcessView.display(allot,getUserName(allot.getUserId()),getBookName(allot.getBookId()));
				flag=1;
					}
		}
		if(flag==0)
		{
			returnProcessView.alertText("No OverDue Books ");
		}
		returnProcessView.alertText("------------------------------------------------------------------------------------------");
	}
	
}
