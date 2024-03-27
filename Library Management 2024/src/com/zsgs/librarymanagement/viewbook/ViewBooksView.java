package com.zsgs.librarymanagement.viewbook;

import com.zsgs.librarymanagement.model.Book;

public class ViewBooksView {
	private ViewBooksModel viewBooksModel;

	public ViewBooksView() {
		this.viewBooksModel = new ViewBooksModel(this);
	}

	public void initView() {
		viewBooksModel.getBookList();

	}

	public void viewBook(Book book) {
		System.out.println(book.getId()+"\t\t"+book.getName()+"\t\t"+book.getAuthor()+"\t\t"+book.getEdition()+"\t\t"+book.getPublication());
	}

	public void alertText(String alertText) {
		System.out.println(alertText);
	}

}
