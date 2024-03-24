package com.zsgs.librarymanagement.searchbook;

import java.util.ArrayList;
import java.util.List;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.model.Book;

public class SearchBookModel {
	SearchBookView searchBookView;
	public SearchBookModel(SearchBookView searchBookView) {
		this.searchBookView=searchBookView;
	}
	public void searchBook(String keyword)
	{
		List<Book> searchBookResult=LibraryDatabase.getInstanse().searchBooks(keyword);
		searchBookView.alertText("----------------------------------------------------------------------------");
		searchBookView.alertText("Book Id\t\tBookName\t\tAuthor");
		searchBookView.alertText("----------------------------------------------------------------------------");
		if(searchBookResult.size()!=0)
		{
		for(Book book:searchBookResult)
		{
			searchBookView.ViewBook(book);
		}
		}
		else
		{
			searchBookView.alertText("Book Not Found");
			
		}
		searchBookView.alertText("----------------------------------------------------------------------------");
		
	}

}
