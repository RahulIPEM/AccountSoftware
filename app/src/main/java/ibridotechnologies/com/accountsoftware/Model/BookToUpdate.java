package ibridotechnologies.com.accountsoftware.Model;

/**
 * Created by sushil on 12/09/18.
 */

public class BookToUpdate {

    private static String bookId,bookTitle,bookAuthor,bookIsbnNo;

    public static String getBookId() {
        return bookId;
    }

    public static void setBookId(String bookId) {
        BookToUpdate.bookId = bookId;
    }

    public static String getBookTitle() {
        return bookTitle;
    }

    public static void setBookTitle(String bookTitle) {
        BookToUpdate.bookTitle = bookTitle;
    }

    public static String getBookAuthor() {
        return bookAuthor;
    }

    public static void setBookAuthor(String bookAuthor) {
        BookToUpdate.bookAuthor = bookAuthor;
    }

    public static String getBookIsbnNo() {
        return bookIsbnNo;
    }

    public static void setBookIsbnNo(String bookIsbnNo) {
        BookToUpdate.bookIsbnNo = bookIsbnNo;
    }
}
