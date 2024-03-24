package com.zsgs.librarymanagement.searchbook;

import java.util.Scanner;

import com.zsgs.librarymanagement.model.Book;

public class SearchBookView {
	private SearchBookModel searchBookModel;
	public SearchBookView()
	{
		this.searchBookModel=new SearchBookModel(this);
	}
	public void initSearch()
	{
		System.out.println("\n Enter the Book Name that you want to Search : ");
		Scanner sc=new Scanner(System.in);
		searchBookModel.searchBook(sc.nextLine());
		
	}
	public void alertText(String alertText)
	{
		System.out.println(alertText);
	}
	public void ViewBook(Book book)
	{
		System.out.println(book.getId()+"\t\t"+book.getName()+"\t\t"+book.getAuthor());
	}
	
}
