package com.zsgs.librarymanagement.librarysetup;

import com.zsgs.librarymanagement.datalayer.LibraryDatabase;
import com.zsgs.librarymanagement.model.Library;

public class LibrarySetupModel {
	private LibrarySetupView librarySetupView;
	private Library library;

	public LibrarySetupModel(LibrarySetupView librarySetupView) {
		this.librarySetupView = librarySetupView;
		library = LibraryDatabase.getInstanse().getLibrary();
	}

	public void startSetUp() {
		if (library == null || library.getId() == 0) {
			librarySetupView.initiateSetUp();
		} else {
			librarySetupView.onSetUpComplete(library);
		}
	}

	public void createLibrary(Library library) {
		boolean valid = true;
		// Validate name

		if (library.getLibraryName().length() < 3 || library.getLibraryName().length() > 50) {
			librarySetupView.showAlert("Invalid Library Details");
		} else {
			LibraryDatabase.getInstanse().insertLibrary(library);
			librarySetupView.onSetUpComplete(library);
		}

	}

}
