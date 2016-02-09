/**
 * Controller for a visual interface
 * 
 * Author: Katie Dodds
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class Controller {

    @FXML
    private TextField bookAuthorField;

    @FXML
    private TextField bookTitleField;

    @FXML
    private TextField keywordSearchField;

    @FXML
    private TextField bookIdField;

    @FXML
    private Label bookCountLabel;

    @FXML
    private Label responseLabel;

//    use instance of BookDB to store library of books
    private BookDB library = new BookDB();

    @FXML
    private void addBook() {

//        get book info
        String title = bookTitleField.getText();
        String author = bookAuthorField.getText();

//      add book to library
        if (title.length() > 0 && author.length() > 0) {
            int bookId = library.addBook(author, title);
            printResponse(String.format("%s : %d", title, bookId));
        }

//        clear text fields after use
        bookTitleField.setText("");
        bookAuthorField.setText("");

    }


    public void searchBook(ActionEvent actionEvent) {

        String result = "";
//        get keyword to use to search
        String keyword = keywordSearchField.getText();

//        search library for book
        ArrayList<Integer> bookIds = library.searchBook(keyword);

//        compile responses into String
        for (int i = 0; i < bookIds.size(); ++i) {
            String book = library.getBook(bookIds.get(i));
            result += book;
        }

        printResponse(result);

    }

    public void getBook(ActionEvent actionEvent) {

        String result = "";

//        use book id to get book info
        if (bookIdField.getText().length() > 0) {
            int bookId = Integer.parseInt( bookIdField.getText() );
            result = library.getBook(bookId);
        } else {
            result = "Enter book id to get book from database";
        }

        printResponse(result);

    }

    public void removeBook(ActionEvent actionEvent) {

        int bookId = Integer.parseInt( bookIdField.getText() );

//        remove book from library
        library.removeBook(bookId);

        printResponse("Book Removed");

    }

    public void numBooks(ActionEvent actionEvent) {
//        get total num of books in library
        int totalBooks = library.numBooks();
//        set BookCountLabel to updated count
        bookCountLabel.setText(Integer.toString(totalBooks));
    }

    private void printResponse(String response) {

        responseLabel.setText(response);

    }

    
    
    
}
