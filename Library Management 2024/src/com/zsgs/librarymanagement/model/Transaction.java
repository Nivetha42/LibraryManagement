package com.zsgs.librarymanagement.model;

import java.util.Date;

public class Transaction {
private int userId;
private int bookId;
private String type;
private Date date;
public Transaction(int userId, int bookId, String type, Date date) {
	this.userId = userId;
	this.bookId = bookId;
	this.type = type;
	this.date = date;
}
public Date getDate() {
	return date;
}
public int getUserId() {
	return userId;
}
public int getBookId() {
	return bookId;
}

public String getType() {
	return type;
}

}
