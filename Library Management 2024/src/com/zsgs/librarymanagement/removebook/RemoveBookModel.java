package com.zsgs.librarymanagement.removebook;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;

public class RemoveBookModel {
	 RemoveBookView  removeBookView;
	
	public RemoveBookModel(RemoveBookView removeBookView) {
		this.removeBookView=removeBookView;
	}
	public void removeBooks(int bookId)
	{
		if(LibraryDatabase.getInstanse().removeBook(bookId))
		{
			removeBookView.onSuccess();
		}
		else
		{
			removeBookView.alertText("\nBook Not Found ----------------------------");
		}
	}
	 
}
