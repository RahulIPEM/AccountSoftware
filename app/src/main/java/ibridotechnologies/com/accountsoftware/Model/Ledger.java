package ibridotechnologies.com.accountsoftware.Model;

/**
 * Created by sushil on 28/08/18.
 */

public class Ledger {
    private static String partyId, bookId,financialYear,partyName;

    public static String getPartyName() {
        return partyName;
    }

    public static void setPartyName(String partyName) {
        Ledger.partyName = partyName;
    }

    public static String getPartyId() {
        return partyId;
    }

    public static void setPartyId(String partyId) {
        Ledger.partyId = partyId;
    }

    public static String getBookId() {
        return bookId;
    }

    public static void setBookId(String bookId) {
        Ledger.bookId = bookId;
    }

    public static String getFinancialYear() {
        return financialYear;
    }

    public static void setFinancialYear(String financialYear) {
        Ledger.financialYear = financialYear;
    }
}
