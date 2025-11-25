package com.example.assignmentcat201;

import java.util.ArrayList;
import java.util.List;

public class LibraryClass {
    private List<BookClass> books = new ArrayList<>();
    private static final String FILE_NAME = "bookLibrary.csv";

    // Add a book to the library
    public boolean addBook(BookClass book) {
        for (BookClass b : books) {
            if (b.getISBN().equals(book.getISBN())) {
                System.out.println("This ISBN already exists.");
                return false;
            }
        }
        books.add(book);
        saveToFile();
        System.out.println("Book added successfully: " + book.getTitle());
        return true;
    }

    // Find a book by title or ISBN
    public BookClass findBook(String query) {
        for (BookClass book : books) {
            if (book.getTitle().equalsIgnoreCase(query) || book.getISBN().equals(query)) {
                return book;
            }
        }
        return null;
    }

    // Borrow a book by title or ISBN
    public boolean borrowBook(String identifier, String borrowerName) {
        BookClass book = findBook(identifier);
        if (book != null && book.isAvailable()) {
            book.borrowBook(borrowerName);
            saveToFile();
            System.out.println("Book borrowed successfully: " + book.getTitle() + " by " + borrowerName);
            return true;
        }
        if (book == null) {
            System.out.println("Book not found: " + identifier);
        } else {
            System.out.println("Book is already borrowed: " + book.getTitle());
        }
        return false;
    }


    // Return a book by title or ISBN
    public boolean returnBook(String identifier) {
        BookClass book = findBook(identifier);
        if (book != null && !book.isAvailable()) {
            book.returnBook(identifier);
            saveToFile();
            System.out.println("Book returned successfully: " + book.getTitle());
            return true;
        }
        if (book == null) {
            System.out.println("Book not found: " + identifier);
        } else {
            System.out.println("Book is already available: " + book.getTitle());
        }
        return false;
    }

    // Search for a book
    public void searchBook(String query) {
        boolean found = false;
        for (BookClass book : books) {
            if (book.getTitle().equalsIgnoreCase(query) ||
                    book.getAuthor().equalsIgnoreCase(query) ||
                    book.getISBN().equals(query)) {
                    book.displayDetails();
                    found = true;
            }
        }
        if (!found) {
            System.out.println("No books found!");
        }
    }

    // Display all books in the library
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (BookClass book : books) {
                book.displayDetails();
                System.out.println();
            }
        }
    }

    // Save to file
    public void saveToFile() {
        BookFile.saveLibrary(this);
    }

    // Load books from file
    public void loadFromFile() {
        books = BookFile.loadLibrary();
    }

    // Getter for books
    public List<BookClass> getAllBooks() {
        return books;
    }
}
