package ibridotechnologies.com.accountsoftware;

/**
 * Created by sushil on 10/08/18.
 */

public class Params {

    private static int bookId,partyId;
    private static String orderId;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public static String getOrderId() {
        return orderId;
    }

    public static void setOrderId(String orderId) {
        Params.orderId = orderId;
    }
}
