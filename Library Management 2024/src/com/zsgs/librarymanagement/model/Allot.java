package com.zsgs.librarymanagement.model;

import java.util.Date;

public class Allot {
	private int allotId;
	private int userId;
	private int BookId;
	private Date returnDate;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBookId() {
		return BookId;
	}
	public void setBookId(int bookId) {
		BookId = bookId;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public int getAllotId() {
		return allotId;
	}
	public void setAllotId(int allotId) {
		this.allotId = allotId;
	}
	
	
	
	
}
