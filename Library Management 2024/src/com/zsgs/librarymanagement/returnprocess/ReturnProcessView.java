package com.zsgs.librarymanagement.returnprocess;

import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.zsgs.librarymanagement.model.Allot;

public class ReturnProcessView {

	ReturnProcessModel returnProcessModel;
	public ReturnProcessView()
	{
		returnProcessModel=new ReturnProcessModel(this);
	}
	public void init()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("\nEnter the Assigned Id : ");
		int assignId=sc.nextInt();
		returnProcessModel.manageReturnProcess(assignId);
		
	}
	public void alertText(String alertText) {
	System.out.println(alertText);
		
	}
	public void displayAllotedBook()
	{
		returnProcessModel.viewAllAllotment();
	}
	public void overDueBooks()
	{
		returnProcessModel.findOverDue();
	}
	public void display(Allot a,String userName,String name) {
		
		System.out.println(a.getAllotId()+"\t\t"+a.getUserId()+"\t\t"+userName+"\t\t"+a.getBookId()+"\t\t"+name+"\t\t"+new SimpleDateFormat("dd/MM/yyyy").format(a.getReturnDate()));
	}
	

}
