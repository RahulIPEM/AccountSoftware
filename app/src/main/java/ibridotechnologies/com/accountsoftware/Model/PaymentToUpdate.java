package ibridotechnologies.com.accountsoftware.Model;

/**
 * Created by sushil on 30/08/18.
 */

public class PaymentToUpdate {

    private static String Payment_Id;

    public static String getPayment_Id() {
        return Payment_Id;
    }

    public static void setPayment_Id(String payment_Id) {
        Payment_Id = payment_Id;
    }

    public static String getPaymentMode_Id() {
        return PaymentMode_Id;
    }

    public static void setPaymentMode_Id(String paymentMode_Id) {
        PaymentMode_Id = paymentMode_Id;
    }

    public static String getPayment_Amount() {
        return Payment_Amount;
    }

    public static void setPayment_Amount(String payment_Amount) {
        Payment_Amount = payment_Amount;
    }

    public static String getPayment_ReceivedDate() {
        return Payment_ReceivedDate;
    }

    public static void setPayment_ReceivedDate(String payment_ReceivedDate) {
        Payment_ReceivedDate = payment_ReceivedDate;
    }

    public static String getBank_Id() {
        return Bank_Id;
    }

    public static void setBank_Id(String bank_Id) {
        Bank_Id = bank_Id;
    }

    public static String getChequeNo() {
        return ChequeNo;
    }

    public static void setChequeNo(String chequeNo) {
        ChequeNo = chequeNo;
    }

    public static String getChequeDate() {
        return ChequeDate;
    }

    public static void setChequeDate(String chequeDate) {
        ChequeDate = chequeDate;
    }

    public static String getLastMod_By() {
        return LastMod_By;
    }

    public static void setLastMod_By(String lastMod_By) {
        LastMod_By = lastMod_By;
    }

    private static String PaymentMode_Id;
    private static String Payment_Amount;
    private static String Payment_ReceivedDate;
    private static String Bank_Id;
    private static String ChequeNo;
    private static String ChequeDate;
    private static String LastMod_By;
    private static String Party_Id;

    public static String getParty_Id() {
        return Party_Id;
    }

    public static void setParty_Id(String party_Id) {
        Party_Id = party_Id;
    }
}
