package com.example.assignmentcat201;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookFile {
    public static final String FILE_NAME = "src/main/java/com/example/assignmentcat201/bookLibrary.csv";

    // Save books to file
    public static void saveLibrary(LibraryClass library) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) { // `false` overwrites the file
            for (BookClass book : library.getAllBooks()) {
                writer.write(book.getTitle() + "," +
                        book.getAuthor() + "," +
                        book.getISBN() + "," +
                        book.isAvailable() + "," +
                        (book.getBorrowerName() == null ? "" : book.getBorrowerName()));
                writer.newLine();
            }
            System.out.println("Books saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    // Load books from file
    public static List<BookClass> loadLibrary() {
        List<BookClass> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {  // Use FILE_NAME
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                for (int i = 0; i < details.length; i++) {
                    details[i] = details[i].trim();
                }

                if (details.length >= 4) {
                    String title = details[0];
                    String author = details[1];
                    String ISBN = details[2];
                    boolean isAvailable = Boolean.parseBoolean(details[3]);
                    String borrowerName = details.length > 4 ? details[4] : null;
                    books.add(new BookClass(title, author, ISBN, isAvailable, borrowerName));
                    System.out.println("Loaded book: " + title + " by " + author);
                }
            }
            System.out.println("Books loaded from file.");
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }
        return books;
    }
}
