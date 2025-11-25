package com.example.assignmentcat201;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.assignmentcat201.BookFile.FILE_NAME;

public class BookClass {
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable;
    private String borrowerName;

    // Constructor
    public BookClass(String title, String author, String ISBN, boolean isAvailable, String borrowerName) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = isAvailable;
        this.borrowerName = borrowerName;
    }

    // Getters and Setters
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getISBN() {
        return ISBN;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public String getBorrowerName() {
        return borrowerName;
    }

    // Methods to borrow books
    public boolean borrowBook(String borrowerName) {
        if (isAvailable) {
            this.isAvailable = false;
            this.borrowerName = borrowerName;
            System.out.println("Book borrowed by " + borrowerName);
            return true;
        } else {
            System.out.println("Book is already borrowed!");
            return false;
        }
    }

    // Methods to return books
    public boolean returnBook(String identifier) {
        // Check if the identifier matches title or ISBN
        if (identifier.equalsIgnoreCase(this.title) || identifier.equals(this.ISBN)) {
            if (!isAvailable) {
                isAvailable = true;
                borrowerName = null;
                System.out.println("Book returned successfully: " + title);
                return true;
            } else {
                System.out.println("The book was not borrowed.");
                return false;
            }
        }
        return false;
    }

    // Method to display book details
    public void displayDetails() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + ISBN);
        System.out.println("Availability: " + (isAvailable ? "Available" : "Borrowed"));
        if (!isAvailable && borrowerName != null) {
            System.out.println("Borrowed by: " + borrowerName);
        }
    }
}
