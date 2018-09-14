package ibridotechnologies.com.accountsoftware.Model;

/**
 * Created by sushil on 13/09/18.
 */

public class BalanceDetails {
    private static String financialYear,partyId,fromDate,toDate,getCount,total;

    public static String getGetCount() {
        return getCount;
    }

    public static void setGetCount(String getCount) {
        BalanceDetails.getCount = getCount;
    }

    public static String getTotal() {
        return total;
    }

    public static void setTotal(String total) {
        BalanceDetails.total = total;
    }

    public static String getFinancialYear() {
        return financialYear;
    }

    public static void setFinancialYear(String financialYear) {
        BalanceDetails.financialYear = financialYear;
    }

    public static String getPartyId() {
        return partyId;
    }

    public static void setPartyId(String partyId) {
        BalanceDetails.partyId = partyId;
    }

    public static String getFromDate() {
        return fromDate;
    }

    public static void setFromDate(String fromDate) {
        BalanceDetails.fromDate = fromDate;
    }

    public static String getToDate() {
        return toDate;
    }

    public static void setToDate(String toDate) {
        BalanceDetails.toDate = toDate;
    }
}
