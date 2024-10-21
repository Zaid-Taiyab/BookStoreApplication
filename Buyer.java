package application;

import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class Buyer extends Application {

    private ObservableList<Book> cartItems = FXCollections.observableArrayList();
    private Label cartContentsLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SunDevil Book Buying System - Buyer's Page");

        // Main layout for the page
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20, 20, 20, 20));

        // Set the new maroon background color for the entire layout
        mainLayout.setStyle("-fx-background-color: #801f33;");  // Updated Maroon color

        // Top section: Page title
        Label pageTitle = new Label("Welcome to the SunDevil Book Exchange");
        pageTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");  // White text for contrast
        HBox titleBox = new HBox(pageTitle);
        titleBox.setPadding(new Insets(10, 0, 20, 0));
        titleBox.setAlignment(Pos.CENTER);

        // Left section: Filters
        VBox filterBox = new VBox(20);
        filterBox.setPadding(new Insets(20));
        filterBox.setStyle("-fx-background-color: #F5DEB3; -fx-border-color: #ffffff; -fx-border-width: 2px; "
                + "-fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label filterTitle = new Label("Filters");
        filterTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #801f33;");

        // Category ComboBox
        Label categoryLabel = new Label("Browse by Category:");
        categoryLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #801f33;");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("All Categories", "Natural Science", "Computer Science", "Math", "English Language", "Other");
        categoryComboBox.setValue("All Categories");
        categoryComboBox.setStyle("-fx-font-size: 14px; -fx-background-color: #f5f5dc; -fx-border-color: #801f33; "
                + "-fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Condition ComboBox
        Label conditionLabel = new Label("Filter by Condition:");
        conditionLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #801f33;");
        ComboBox<String> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().addAll("All Conditions", "Used Like New", "Moderately Used", "Heavily Used");
        conditionComboBox.setValue("All Conditions");
        conditionComboBox.setStyle("-fx-font-size: 14px; -fx-background-color: #f5f5dc; -fx-border-color: #801f33; "
                + "-fx-border-radius: 5px; -fx-background-radius: 5px;");

        filterBox.getChildren().addAll(filterTitle, categoryLabel, categoryComboBox, conditionLabel, conditionComboBox);

        // Center section: Book list and details
        VBox bookDetailsBox = new VBox(10);
        bookDetailsBox.setPadding(new Insets(20));
        bookDetailsBox.setPrefWidth(500);
        bookDetailsBox.setStyle("-fx-background-color: #F5DEB3; -fx-border-color: #ffffff; -fx-border-width: 2px; "
                + "-fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label bookListTitle = new Label("Available Books");
        bookListTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #801f33;");

        TableView<Book> bookTable = new TableView<>();
        bookTable.setPrefWidth(300);
        bookTable.setStyle("-fx-selection-bar: #801f33; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        titleCol.setPrefWidth(146.99);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setStyle("-fx-background-color: #f5f5dc; -fx-text-fill: #801f33; -fx-font-weight: bold;");

        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        authorCol.setPrefWidth(100);
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorCol.setStyle("-fx-background-color: #f5f5dc; -fx-text-fill: #801f33; -fx-font-weight: bold;");

        TableColumn<Book, String> conditionCol = new TableColumn<>("Condition");
        conditionCol.setPrefWidth(100);
        conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
        conditionCol.setStyle("-fx-background-color: #f5f5dc; -fx-text-fill: #801f33; -fx-font-weight: bold;");

        TableColumn<Book, Double> priceCol = new TableColumn<>("Price");
        priceCol.setPrefWidth(90);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setStyle("-fx-background-color: #f5f5dc; -fx-text-fill: #801f33; -fx-font-weight: bold;");
        priceCol.setCellFactory(column -> new TableCell<Book, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText("$" + String.format("%.2f", item));
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });

        TableColumn<Book, Void> addToCartCol = new TableColumn<>("Action");
        addToCartCol.setPrefWidth(70);
        addToCartCol.setStyle("-fx-background-color: #f5f5dc;");

        // Add a button to each row
        addToCartCol.setCellFactory(param -> new TableCell<Book, Void>() {
            private final Button addButton = new Button("Add");

            {
                addButton.setStyle("-fx-background-color: #801f33; -fx-text-fill: #ffffff; -fx-font-size: 12px; "
                        + "-fx-background-radius: 25px;");

                addButton.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    // Add the book to the cart
                    cartItems.add(book);
                    updateCartLabel();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(addButton);
                }
            }
        });

        bookTable.getColumns().addAll(titleCol, authorCol, conditionCol, priceCol, addToCartCol);

        // Sample data for the TableView
        ObservableList<Book> bookList = FXCollections.observableArrayList(
                new Book("Book A", "Author A", "New", 9.99),
                new Book("Book B", "Author B", "Moderately Used", 8.99),
                new Book("Book C", "Author C", "Heavily Used", 5.99),
                new Book("Book D", "Author D", "New", 11.99)
        );

        bookTable.setItems(bookList);

        bookDetailsBox.getChildren().addAll(bookListTitle, bookTable);

        // Right section: Cart and actions
        VBox cartBox = new VBox(20);
        cartBox.setPadding(new Insets(20));
        cartBox.setPrefWidth(200);
        cartBox.setStyle("-fx-background-color: #F5DEB3; -fx-border-color: #ffffff; -fx-border-width: 2px; "
                + "-fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label cartTitle = new Label("Shopping Cart");
        cartTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #801f33;");

        cartContentsLabel = new Label("Cart is empty.");
        cartContentsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #801f33;");

        Button viewCartButton = new Button("View Cart");
        viewCartButton.setStyle("-fx-background-color: #801f33; -fx-text-fill: #ffffff; -fx-font-size: 14px; "
                + "-fx-padding: 10px; -fx-background-radius: 5px;");
        viewCartButton.setPrefWidth(160);

        Button checkoutButton = new Button("Checkout");
        checkoutButton.setStyle("-fx-background-color: #801f33; -fx-text-fill: #ffffff; -fx-font-size: 14px; "
                + "-fx-padding: 10px; -fx-background-radius: 5px;");
        checkoutButton.setPrefWidth(160);

        Button clearCartButton = new Button("Clear Cart");
        clearCartButton.setStyle("-fx-background-color: #801f33; -fx-text-fill: #ffffff; -fx-font-size: 14px; "
                + "-fx-padding: 10px; -fx-background-radius: 5px;");
        clearCartButton.setPrefWidth(160);

        cartBox.getChildren().addAll(cartTitle, cartContentsLabel, viewCartButton, checkoutButton, clearCartButton);

        // Assemble the layout
        mainLayout.setTop(titleBox);
        mainLayout.setLeft(filterBox);
        mainLayout.setCenter(bookDetailsBox);
        mainLayout.setRight(cartBox);
        Scene scene = new Scene(mainLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        viewCartButton.setOnAction(event -> {
            StringBuilder cartInfo = new StringBuilder();
            double totalPrice = 0;
            for (Book b : cartItems) {
                cartInfo.append(b.getTitle()).append(" - $").append(String.format("%.2f", b.getPrice())).append("\n");
                totalPrice += b.getPrice();
            }
            cartInfo.append("\nTotal Price: $").append(String.format("%.2f", totalPrice));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cart Contents");
            alert.setHeaderText("Your Cart");
            alert.setContentText(cartInfo.toString());
            alert.showAndWait();
        });

        checkoutButton.setOnAction(event -> {
            if (cartItems.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Checkout");
                alert.setHeaderText(null);
                alert.setContentText("Your cart is empty.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Checkout");
                alert.setHeaderText(null);
                alert.setContentText("Purchase completed. Confirmation emails sent.");
                alert.showAndWait();

                cartItems.clear();
                updateCartLabel();
            }
        });

        clearCartButton.setOnAction(event -> {
            cartItems.clear();
            updateCartLabel();
        });
    }

    private void updateCartLabel() {
        if (cartItems.isEmpty()) {
            cartContentsLabel.setText("Cart is empty.");
        } else {
            cartContentsLabel.setText("Items in Cart: " + cartItems.size());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public class Book {
        private String title;
        private String author;
        private String condition;
        private double price;

        public Book(String title, String author, String condition, double price) {
            this.title = title;
            this.author = author;
            this.condition = condition;
            this.price = price;
        }

        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public String getCondition() { return condition; }
        public double getPrice() { return price; }
    }
}

