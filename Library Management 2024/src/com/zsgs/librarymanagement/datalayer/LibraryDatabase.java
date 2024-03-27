package com.zsgs.librarymanagement.datalayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.google.gson.JsonParser;
import com.zsgs.librarymanagement.model.Allot;
import com.zsgs.librarymanagement.model.Book;
import com.zsgs.librarymanagement.model.Credentials;
import com.zsgs.librarymanagement.model.Library;
import com.zsgs.librarymanagement.model.Transaction;
import com.zsgs.librarymanagement.model.User;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LibraryDatabase {
	private static LibraryDatabase libraryDatabase;
	private Library library;
	private List<Book> bookList = new ArrayList();
	private List<User> userList =  getUserList();
	private List<Allot> assignedList ;
	private List<Credentials> credentialList = getUserCredentials();
	private List<Transaction> history = new ArrayList();

	private static int id = 0;
	private static int userId = 100;

	public static LibraryDatabase getInstanse() {
		if (libraryDatabase == null) {
			libraryDatabase = new LibraryDatabase();
		}
		return libraryDatabase;
	}

	public Library getLibrary() throws java.text.ParseException  {
		assignedList=getBorrowDetails();
		bookList=getbookList();
		history=getTransactionDetails();
		this.library = readJsonFile();
		return library;
	}

	public List<Credentials> getCredentialList() {
		return getUserCredentials();

	}

	public void insertLibrary(Library library2) {

		this.library = library2;
		save();

	}

	public List<Book> getAllBooks() {
		return getbookList();
	}

	public boolean removeBook(int bookId) {
		for (Book book : bookList) {
			if (book.getId() == bookId && book.getIsAssigned() == false) {
				bookList.remove(book);
				
				writeJsonBook();
				return true;
			}
		}
		return false;
	}

	public Book getBook(int bookId) {
		for (Book book : getbookList()) {
			if (book.getId() == bookId) {
				return book;
			}
		}
		// select query with condition.
		return null;
	}

	public List<Book> searchBooks(String bookName) {
		List<Book> searchResult = new ArrayList<>();
		for (Book book : getbookList()) {
			if (book.getName().contains(bookName)) {
				searchResult.add(book);
			}
		}
		return searchResult;
	}

	public boolean insertBook(Book book) {
		boolean hasBook = false;
		for (Book addedBook : getbookList()) {
			if (addedBook.getName().equals(book.getName()) && addedBook.getAuthor().equals(book.getAuthor())) {
				hasBook = true;
				break;
			}
		}
		if (hasBook) {
			return false;
		} else {
			bookList.add(book);
			writeJsonBook();
			return true;
		}
	}

	public boolean insertUser(User user) {
		boolean hasUser = false;
		for (User addedUser : userList) {
			if (addedUser.getEmailId().equals(user.getEmailId())) {
				hasUser = true;
			}
		}
		if (hasUser) {
			return false;
		} else {
			user.setUserId(userId);
			userList.add(user);
			writeJsonUser();
			return true;
		}
	}

	public void insertCredentials(Credentials c) {
		c.setUserId(userId);
		userId++;
		credentialList.add(c);
		writeJsonCredentials();
	}

	public boolean isUser(int userId) {
		for (User user : userList) {
			if (user.getUserId() == userId) {
				return true;
			}
		}
		return false;

	}

	public boolean isBookAlloted(int bookId) {
		for (Book book : bookList) {
			if (book.getId() == bookId) {
//				System.out.println(book.getIsAssigned()+" "+bookId);
				if (book.getIsAssigned() == false) {
					return true;
				}
			}
		}
		return false;

	}

	public List<User> getAllUsers() {
		return getUserList();
	}

	public void assignBook(int bookId, boolean state) {
		for (Book book : bookList) {
			if (book.getId() == bookId) {
				book.setAssigned(state);
				writeJsonBook();
			}
		}
	}

	public boolean assignBooks(Allot allotUser) throws java.text.ParseException {
		if (assignedList.size() == 0) {
			id = 0;
		}
		int count = 0;
		for (Allot allot :  getBorrowDetails()) {
			if (allot.getUserId() == allotUser.getUserId()) {
				count++;
			}
			if (count >= 2) {
				return false;
			}
		}

		if (isUser(allotUser.getUserId()) && isBookAlloted(allotUser.getBookId())) {
			allotUser.setAllotId(id + 1);
			id++;
			assignBook(allotUser.getBookId(), true);
			assignedList.add(allotUser);
			writeJsonAllot();
			history.add(new Transaction(allotUser.getUserId(), allotUser.getBookId(), "borrow", new Date()));
			writeJsonHistory();
			return true;
		}
		return false;

	}

	public boolean returnProcess(int allotId) {
		for (Allot allot : assignedList) {
			if (allot.getAllotId() == allotId) {
				assignBook(allot.getBookId(), false);
				history.add(new Transaction(allot.getUserId(), allot.getBookId(), "return", new Date()));
				writeJsonHistory();
				assignedList.remove(allot);
				writeJsonAllot();
				return true;
			}
		}
		return false;
	}

	public List<Allot> getAssignedList() throws java.text.ParseException {
		return  getBorrowDetails();
	}

	public List<Transaction> getAllTransactionList() throws Exception {
		return getTransactionDetails();
	}

	void save() {
		JSONArray jsonArray = new JSONArray();

		JSONObject libJson = new JSONObject();
		libJson.put("Id", library.getId());
		libJson.put("LibraryName", library.getLibraryName());
		libJson.put("emailId", library.getEmailId());
		jsonArray.add(libJson);
		try (FileWriter file = new FileWriter("library.json")) {
			file.write(jsonArray.toJSONString());
//			System.out.println("written to library.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Library readJsonFile() {
		Library l = new Library();
		File file = new File("library.json");
        if (!file.exists() || file.length() == 0) {
            return l;
        }
		try {
			JSONParser parser = new JSONParser();
			JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("library.json"));
			for (Object obj : jsonArray) {
				JSONObject jsonObject = (JSONObject) obj;
				long id = (long) jsonObject.get("Id");
				String emailId = (String) jsonObject.get("emailId");
				String libName = (String) jsonObject.get("LibraryName");
				l.setId((int) id);
				l.setEmailId(emailId);
				l.setLibraryName(libName);
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return l;
	}
	  private  void writeJsonBook() {
	        JSONArray jsonArray = new JSONArray();
//	        System.out.println(bookList);
	        for (Book book : bookList) {
	            JSONObject bookJson = new JSONObject();
	            bookJson.put("Id", book.getId());
	            bookJson.put("name", book.getName());
	            bookJson.put("author", book.getAuthor());
	            bookJson.put("isAssigned",book.getIsAssigned());
	            bookJson.put("Publication",book.getPublication());
	            bookJson.put("Edition", book.getEdition());
	            jsonArray.add(bookJson);
	        }

	        try (FileWriter file = new FileWriter("Books.json")) {
	            file.write(jsonArray.toJSONString());
//	            System.out.println("written to Books.json");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  private List<Book> getbookList() {
			List<Book> book = new ArrayList<Book>();
			File file = new File("Books.json");
	        if (!file.exists() || file.length() == 0) {
//	            System.out.println("No Books data available in the file.");
	            return book;
	        }
			try {
				JSONParser parser = new JSONParser();
				JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("Books.json"));
				for (Object obj : jsonArray) {
					JSONObject jsonObject = (JSONObject) obj;
					long id = (long) jsonObject.get("Id");
					String name = (String) jsonObject.get("name");
					String author = (String) jsonObject.get("author");
					boolean assigned=(boolean) jsonObject.get("isAssigned");
					String publication=(String) jsonObject.get("Publication");
					String edition=(String) jsonObject.get("Edition");
					Book b=new Book();
					b.setId(id);
					b.setName(name);
					b.setAuthor(author);
					b.setAssigned(assigned);
					b.setPublication(publication);
					b.setEdition(edition);
					book.add(b);
				}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}

			return book;
		}
	  private  void writeJsonUser() {
	        JSONArray jsonArray = new JSONArray();
//	         System.out.println(userList);
	        for (User user : userList) {
	            JSONObject userJson = new JSONObject();
	            userJson.put("Id", user.getUserId());
	            userJson.put("email", user.getEmailId());
	            userJson.put("name", user.getUserName());
	            jsonArray.add(userJson);
	           
	        }

	        try (FileWriter file = new FileWriter("users.json")) {
	            file.write(jsonArray.toJSONString());
//	            System.out.println("written to users.json");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  private List<User> getUserList() {
			List<User> user = new ArrayList<User>();
			File file = new File("users.json");
	        if (!file.exists() || file.length() == 0) {
//	            System.out.println("No users data available in the file.");
	            return user;
	        }
	        long id=0;
			try {
				JSONParser parser = new JSONParser();
				JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("users.json"));
				for (Object obj : jsonArray) {
					JSONObject jsonObject = (JSONObject) obj;
					 id = (long) jsonObject.get("Id");
					String name = (String) jsonObject.get("name");
					String email = (String) jsonObject.get("email");
					User u=new User();
					u.setUserId((int)id);
					u.setUserName(name);
					u.setEmailId(email);
					user.add(u);
					
				}
				userId=(int)id+1;
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}

			return user;
		}
	  private  void writeJsonCredentials() {
	        JSONArray jsonArray = new JSONArray();
	        for (Credentials user :credentialList) {
	            JSONObject userJson = new JSONObject();
	            userJson.put("Id", user.getUserId());
	            userJson.put("password", user.getPassword());
	            jsonArray.add(userJson);
	           
	        try (FileWriter file = new FileWriter("credentials.json")) {
	            file.write(jsonArray.toJSONString());
//	            System.out.println("written to credentials.json");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  }
	  private List<Credentials> getUserCredentials() {
			List<Credentials> user = new ArrayList<Credentials>();
			File file = new File("credentials.json");
	        if (!file.exists() || file.length() == 0) {
//	            System.out.println("No users data available in the file.");
	            return user;
	        }
	        long id=0;
			try {
				JSONParser parser = new JSONParser();
				JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("credentials.json"));
				for (Object obj : jsonArray) {
					JSONObject jsonObject = (JSONObject) obj;
					 id = (long) jsonObject.get("Id");
					String password= (String) jsonObject.get("password");
					Credentials c=new Credentials();
					c.setUserId((int)id);
					c.setPassword(password);
					user.add(c);
					
				}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}

			return user;
		}
	  private  void writeJsonAllot() {
	        JSONArray jsonArray = new JSONArray();
	        for (Allot user : assignedList) {
	            JSONObject userJson = new JSONObject();
	            userJson.put("Id", user.getAllotId());
	            userJson.put("userId", user.getUserId());
	            userJson.put("bookId", user.getBookId());
	            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
	            userJson.put("dueDate", sdf.format(user.getReturnDate()));
	            jsonArray.add(userJson);
	           
	        }

	        try (FileWriter file = new FileWriter("borrowList.json")) {
	            file.write(jsonArray.toJSONString());
//	            System.out.println("written to borrowList.json");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  private List<Allot> getBorrowDetails() throws java.text.ParseException  {
			List<Allot> borrow = new ArrayList<Allot>();
			File file = new File("borrowList.json");
	        if (!file.exists() || file.length() == 0) {
//	            System.out.println("No users data available in the file.");
	            return borrow;
	        }
	        long id=0;
			try {
				JSONParser parser = new JSONParser();
				JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("borrowList.json"));
				for (Object obj : jsonArray) {
					JSONObject jsonObject = (JSONObject) obj;
					 id = (long) jsonObject.get("Id");
					long uId= (long) jsonObject.get("userId");
					long bId= (long) jsonObject.get("bookId");
					String date=(String) jsonObject.get("dueDate");
					Allot c=new Allot();
					c.setAllotId((int)id);
					c.setUserId((int)uId);
					c.setBookId((int)bId);
					c.setReturnDate(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(date));
					borrow.add(c);
					
				}
				this.id=(int)id;
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}

			return borrow;
		}
	  private  void writeJsonHistory() {
	        JSONArray jsonArray = new JSONArray();
	        for (Transaction user : history) {
	            JSONObject userJson = new JSONObject();
	            userJson.put("userId", user.getUserId());
	            userJson.put("bookId", user.getBookId());
	            userJson.put("type", user.getType());
	            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
	            userJson.put("Date", sdf.format(user.getDate()));
	            jsonArray.add(userJson);
	           
	        }

	        try (FileWriter file = new FileWriter("transactionList.json")) {
	            file.write(jsonArray.toJSONString());
//	            System.out.println("written to transactionList.json");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  private List<Transaction> getTransactionDetails() throws java.text.ParseException  {
			List<Transaction> t= new ArrayList<Transaction>();
			File file = new File("transactionList.json");
	        if (!file.exists() || file.length() == 0) {
//	            System.out.println("No  data available in the file.");
	            return t;
	        }
	        long id=0;
			try {
				JSONParser parser = new JSONParser();
				JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("transactionList.json"));
				for (Object obj : jsonArray) {
					JSONObject jsonObject = (JSONObject) obj;
					 id = (long) jsonObject.get("userId");
					long bId= (long) jsonObject.get("bookId");
					String type=  (String) jsonObject.get("type");
					String date=(String) jsonObject.get("Date");
					Transaction c=new Transaction((int)id, (int)bId, type, new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(date));
					
					t.add(c);
					
				}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}

			return t;
		}
}
