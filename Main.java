package com.example.assignmentcat201;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryClass library = new LibraryClass();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to Dream Library:");
            System.out.println("1. Add Book");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. Search Book");
            System.out.println("5. Display All Books");
            System.out.println("6. Save Books to File");
            System.out.println("7. Load Books from File");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();

                    if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
                        System.out.println("All fields are required.");
                        break;
                    }

                    BookClass newBook = new BookClass(title, author, isbn, true, null);
                    if (library.addBook(newBook)) {
                        System.out.println("Book added successfully!");
                    } else {
                        System.out.println("Book with this ISBN already exists.");
                    }
                    break;

                case 2:
                    System.out.print("Enter the title of the book to borrow: ");
                    String borrowTitle = scanner.nextLine().trim();
                    System.out.print("Enter borrower's name: ");
                    String borrowerName = scanner.nextLine().trim();
                    System.out.print("Enter the ISBN of the book to borrow: ");
                    String borrowIsbn = scanner.nextLine().trim();

                    if (borrowTitle.isEmpty() || borrowerName.isEmpty() || borrowIsbn.isEmpty()) {
                        System.out.println("All fields are required.");
                        break;
                    }

                    library.borrowBook(borrowIsbn, borrowerName);
                    break;

                case 3:
                    System.out.print("Enter the ISBN of the book to return: ");
                    String returnIsbn = scanner.nextLine().trim();

                    if (returnIsbn.isEmpty()) {
                        System.out.println("ISBN is required.");
                        break;
                    }

                    library.returnBook(returnIsbn);
                    break;

                case 4:
                    System.out.print("Enter the title/author/ISBN to search: ");
                    String searchQuery = scanner.nextLine().trim();
                    if (searchQuery.isEmpty()) {
                        System.out.println("Search query cannot be empty.");
                        break;
                    }
                    library.searchBook(searchQuery);
                    break;

                case 5:
                    library.displayAllBooks();
                    break;

                case 6:
                    library.saveToFile();
                    System.out.println("Books saved to file.");
                    break;

                case 7:
                    library.loadFromFile();
                    break;

                case 8:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;  // Exit the loop and program

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
