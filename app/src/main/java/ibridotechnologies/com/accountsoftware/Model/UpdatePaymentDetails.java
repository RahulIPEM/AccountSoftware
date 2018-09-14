package ibridotechnologies.com.accountsoftware.Model;

/**
 * Created by sushil on 11/08/18.
 */

public class UpdatePaymentDetails {
    private static int bankId,orderId,paymentModeId,partyId;
    private static String paymentAmount,paymentReceivedDate,chequeNo,chequeDate;

    public static int getPartyId() {
        return partyId;
    }

    public static void setPartyId(int partyId) {
        UpdatePaymentDetails.partyId = partyId;
    }

    public static int getBankId() {
        return bankId;
    }

    public static void setBankId(int bankId) {
        UpdatePaymentDetails.bankId = bankId;
    }

    public static int getOrderId() {
        return orderId;
    }

    public static void setOrderId(int orderId) {
        UpdatePaymentDetails.orderId = orderId;
    }

    public static int getPaymentModeId() {
        return paymentModeId;
    }

    public static void setPaymentModeId(int paymentModeId) {
        UpdatePaymentDetails.paymentModeId = paymentModeId;
    }

    public static String getPaymentAmount() {
        return paymentAmount;
    }

    public static void setPaymentAmount(String paymentAmount) {
        UpdatePaymentDetails.paymentAmount = paymentAmount;
    }

    public static String getPaymentReceivedDate() {
        return paymentReceivedDate;
    }

    public static void setPaymentReceivedDate(String paymentReceivedDate) {
        UpdatePaymentDetails.paymentReceivedDate = paymentReceivedDate;
    }

    public static String getChequeNo() {
        return chequeNo;
    }

    public static void setChequeNo(String chequeNo) {
        UpdatePaymentDetails.chequeNo = chequeNo;
    }

    public static String getChequeDate() {
        return chequeDate;
    }

    public static void setChequeDate(String chequeDate) {
        UpdatePaymentDetails.chequeDate = chequeDate;
    }
}
