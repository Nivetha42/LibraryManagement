package com.zsgs.librarymanagement.viewbook;


import java.util.List;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.model.Book;

public class ViewBooksModel {

	private ViewBooksView viewBooksView;
	public ViewBooksModel(ViewBooksView viewBooksView) {
	this.viewBooksView=viewBooksView;
	}
	public void getBookList() {
		List<Book> allBooks=LibraryDatabase.getInstanse().getAllBooks();
		viewBooksView.alertText("----------------------------------------------------------------------------");
		viewBooksView.alertText("Book Id\t\tBook Name\t\tBook Author");
		viewBooksView.alertText("----------------------------------------------------------------------------");
		if(allBooks.size()!=0)
		{
			for(Book book:allBooks)
			{
				viewBooksView.viewBook(book);
			}
		}
		else
		{
			viewBooksView.alertText("\nNo Books Found ....... Add Some Books");
		}
		viewBooksView.alertText("----------------------------------------------------------------------------");
		
	}
	

}
