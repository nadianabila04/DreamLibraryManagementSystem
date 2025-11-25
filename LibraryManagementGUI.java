package com.example.assignmentcat201;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class LibraryManagementGUI extends Application {

    private Stage primaryStage;
    private LibraryClass library = new LibraryClass(); // Shared library instance

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Library Management System");

        // Show the main menu
        showMainMenu();

        // Load books from the file
        library.loadFromFile();

        primaryStage.show();
    }

    public void showMainMenu() {
        // Outer container
        VBox root = new VBox(20); // Add spacing between components
        root.setAlignment(Pos.CENTER);

        // Header label
        Label header = new Label("Welcome to Dream Library ^^");
        header.getStyleClass().add("header-label");

        // Button Grid
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(10); // Horizontal spacing between buttons
        buttonGrid.setVgap(20); // Vertical spacing between rows
        buttonGrid.setAlignment(Pos.CENTER);

        // Buttons
        Button addBookButton = createButton("Add Book");
        Button borrowBookButton = createButton("Borrow Book");
        Button returnBookButton = createButton("Return Book");
        Button viewBooksButton = createButton("View All Books");
        Button exitButton = createButton("Exit");

        // Add buttons to grid
        buttonGrid.add(addBookButton, 0, 0); // Column 0, Row 0
        buttonGrid.add(borrowBookButton, 1, 0); // Column 1, Row 0
        buttonGrid.add(returnBookButton, 0, 1); // Column 0, Row 1
        buttonGrid.add(viewBooksButton, 1, 1); // Column 1, Row 1

        // Add Exit button below, centered
        VBox exitContainer = new VBox(exitButton);
        exitContainer.setAlignment(Pos.CENTER);

        // Button actions
        addBookButton.setOnAction(e -> showAddBookScreen());
        borrowBookButton.setOnAction(e -> showBorrowBookScreen());
        returnBookButton.setOnAction(e -> showReturnBookScreen());
        viewBooksButton.setOnAction(e -> showViewBooksScreen());
        exitButton.setOnAction(e -> primaryStage.close());

        // Add header, button grid, and exit button to the root
        root.getChildren().addAll(header, buttonGrid, exitContainer);

        // Set up the scene and apply the CSS
        Scene scene = new Scene(root, 500, 400);
        String css = getClass().getResource("LibraryManagement.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
    }

    // Add Book Screen
    public void showAddBookScreen() {
        GridPane layout = createGridPane();

        Label titleLabel = new Label("Book Title:");
        titleLabel.getStyleClass().add("field-label");
        TextField titleField = new TextField();

        Label authorLabel = new Label("Author:");
        authorLabel.getStyleClass().add("field-label");
        TextField authorField = new TextField();

        Label isbnLabel = new Label("ISBN:");
        isbnLabel.getStyleClass().add("field-label");
        TextField isbnField = new TextField();

        Button addButton = createButton("Add Book");
        Button backButton = createButton("Back");

        layout.add(titleLabel, 0, 0);
        layout.add(titleField, 1, 0);
        layout.add(authorLabel, 0, 1);
        layout.add(authorField, 1, 1);
        layout.add(isbnLabel, 0, 2);
        layout.add(isbnField, 1, 2);
        layout.add(addButton, 0, 3);
        layout.add(backButton, 1, 3);

        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "All fields are required!");
            } else {
                library.addBook(new BookClass(title, author, isbn, true, null));
                showAlert(Alert.AlertType.INFORMATION, "Book added: " + title);
                titleField.clear();
                authorField.clear();
                isbnField.clear();
            }
        });
        backButton.setOnAction(e -> showMainMenu());

        Scene scene = new Scene(layout, 400, 300);
        String css = getClass().getResource("LibraryManagement.css").toExternalForm();
        scene.getStylesheets().add(css); // Apply the CSS to this scene

        primaryStage.setScene(scene); // Set the scene
    }

    // Borrow Book Screen
    public void showBorrowBookScreen() {
        GridPane layout = createGridPane();

        Label titleLabel = new Label("Book Title:");
        titleLabel.getStyleClass().add("field-label");
        TextField titleField = new TextField();

        Label isbnLabel = new Label("ISBN:");
        isbnLabel.getStyleClass().add("field-label");
        TextField isbnField = new TextField();

        Label borrowerLabel = new Label("Borrower Name:");
        borrowerLabel.getStyleClass().add("field-label");
        TextField borrowerField = new TextField();

        Button borrowButton = createButton("Borrow Book");
        Button backButton = createButton("Back");

        layout.add(titleLabel, 0, 0);
        layout.add(titleField, 1, 0);
        layout.add(isbnLabel, 0, 1);
        layout.add(isbnField, 1, 1);
        layout.add(borrowerLabel, 0, 2);
        layout.add(borrowerField, 1, 2);
        layout.add(borrowButton, 0, 3);
        layout.add(backButton, 1, 3);

        borrowButton.setOnAction(e -> {
            String title = titleField.getText().trim();
            String isbn = isbnField.getText().trim();
            String borrower = borrowerField.getText().trim();

            if (title.isEmpty() || isbn.isEmpty() || borrower.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "All fields are required!");
                return;
            }

            BookClass book = library.findBook(title);
            if (book == null || !book.getISBN().equals(isbn)) {
                showAlert(Alert.AlertType.ERROR, "Book not found or ISBN mismatch.");
            } else if (!book.isAvailable()) {
                showAlert(Alert.AlertType.ERROR, "Book is already borrowed.");
            } else {
                library.borrowBook(isbn, borrower);
                showAlert(Alert.AlertType.INFORMATION, "Book borrowed successfully by " + borrower);
            }

            titleField.clear();
            isbnField.clear();
            borrowerField.clear();
        });

        backButton.setOnAction(e -> showMainMenu());

        // Create a new scene and apply the CSS file
        Scene scene = new Scene(layout, 400, 300);
        String css = getClass().getResource("LibraryManagement.css").toExternalForm();
        scene.getStylesheets().add(css); // Apply the CSS to this scene

        primaryStage.setScene(scene); // Set the scene
    }

    // Return Book Screen
    public void showReturnBookScreen() {
        GridPane layout = createGridPane();

        Label identifierLabel = new Label("Title/ISBN:");
        identifierLabel.getStyleClass().add("field-label");
        TextField identifierField = new TextField();

        Button returnButton = createButton("Return Book");
        Button backButton = createButton("Back");

        layout.add(identifierLabel, 0, 0);
        layout.add(identifierField, 1, 0);
        layout.add(returnButton, 0, 1);
        layout.add(backButton, 1, 1);

        returnButton.setOnAction(e -> {
            String identifier = identifierField.getText();
            if (identifier.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Title/ISBN is required!");
            } else {
                library.returnBook(identifier);
                showAlert(Alert.AlertType.INFORMATION, "Book returned: " + identifier);
                identifierField.clear();
            }
        });
        backButton.setOnAction(e -> showMainMenu());

        // Create a new scene and apply the CSS file
        Scene scene = new Scene(layout, 400, 300);
        String css = getClass().getResource("LibraryManagement.css").toExternalForm();
        scene.getStylesheets().add(css); // Apply the CSS to this scene

        primaryStage.setScene(scene); // Set the scene
    }

    // View Books Screen
    public void showViewBooksScreen() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label bookListLabel = new Label("Book List:");
        bookListLabel.getStyleClass().add("field-label");
        TextArea booksTextArea = new TextArea();
        booksTextArea.setEditable(false);

        StringBuilder books = new StringBuilder();
        List<BookClass> allBooks = library.getAllBooks();
        for (BookClass book : allBooks) {
            books.append("Title: ").append(book.getTitle())
                    .append(", Author: ").append(book.getAuthor())
                    .append(", ISBN: ").append(book.getISBN())
                    .append(", Available: ").append(book.isAvailable() ? "Yes" : "No").append("\n");
        }
        booksTextArea.setText(books.toString());

        ScrollPane scrollPane = new ScrollPane(booksTextArea);
        scrollPane.setFitToWidth(true);

        scrollPane.setPrefWidth(500);
        scrollPane.setPrefHeight(300);

        Button backButton = createButton("Back");
        backButton.setOnAction(e -> showMainMenu());

        layout.getChildren().addAll(bookListLabel, scrollPane, backButton);

        // Create a new scene and apply the CSS file
        Scene scene = new Scene(layout, 400, 300);
        String css = getClass().getResource("LibraryManagement.css").toExternalForm();
        scene.getStylesheets().add(css); // Apply the CSS to this scene

        primaryStage.setWidth(650);
        primaryStage.setHeight(400);
        primaryStage.setScene(scene); // Set the scene
    }

    private GridPane createGridPane() {
        GridPane layout = new GridPane();
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setAlignment(Pos.CENTER);
        return layout;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(130); // Set a fixed width for all buttons
        button.getStyleClass().add("button");
        return button;
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
