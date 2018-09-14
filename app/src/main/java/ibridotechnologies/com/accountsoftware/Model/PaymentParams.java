package ibridotechnologies.com.accountsoftware.Model;

/**
 * Created by Rahul on 10/08/18.
 */

public class PaymentParams {
    private static int orderId,paymentModeId,partyId;
    private static String paymentAmount,paymentReceivedDate,chequeNo,chequeDate,savedBy,bankId,financialYear;

    public static int getPartyId() {
        return partyId;
    }

    public static void setPartyId(int partyId) {
        PaymentParams.partyId = partyId;
    }

    public static String getFinancialYear() {
        return financialYear;
    }

    public static void setFinancialYear(String financialYear) {
        PaymentParams.financialYear = financialYear;
    }

    public static int getOrderId() {
        return orderId;
    }

    public static void setOrderId(int orderId) {
        PaymentParams.orderId = orderId;
    }

    public static int getPaymentModeId() {
        return paymentModeId;
    }

    public static void setPaymentModeId(int paymentModeId) {
        PaymentParams.paymentModeId = paymentModeId;
    }

    public static String getBankId() {
        return bankId;
    }

    public static void setBankId(String bankId) {
        PaymentParams.bankId = bankId;
    }

    public static String getPaymentAmount() {
        return paymentAmount;
    }

    public static void setPaymentAmount(String paymentAmount) {
        PaymentParams.paymentAmount = paymentAmount;
    }

    public static String getPaymentReceivedDate() {
        return paymentReceivedDate;
    }

    public static void setPaymentReceivedDate(String paymentReceivedDate) {
        PaymentParams.paymentReceivedDate = paymentReceivedDate;
    }

    public static String getChequeNo() {
        return chequeNo;
    }

    public static void setChequeNo(String chequeNo) {
        PaymentParams.chequeNo = chequeNo;
    }

    public static String getChequeDate() {
        return chequeDate;
    }

    public static void setChequeDate(String chequeDate) {
        PaymentParams.chequeDate = chequeDate;
    }

    public static String getSavedBy() {
        return savedBy;
    }

    public static void setSavedBy(String savedBy) {
        PaymentParams.savedBy = savedBy;
    }
}
