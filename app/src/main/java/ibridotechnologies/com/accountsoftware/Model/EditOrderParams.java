package ibridotechnologies.com.accountsoftware.Model;

/**
 * Created by sushil on 11/08/18.
 */

public class EditOrderParams {
    private static int partyId,bookId,orderId;
    private static String pageNo,pagesRate,artWork,artWorkRate,coloring,coloringRate,writing,writingRate,orderReceivedDate,proofing,proofingRate,financialYear;

    public static String getProofing() {
        return proofing;
    }

    public static void setProofing(String proofing) {
        EditOrderParams.proofing = proofing;
    }

    public static String getProofingRate() {
        return proofingRate;
    }

    public static void setProofingRate(String proofingRate) {
        EditOrderParams.proofingRate = proofingRate;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        EditOrderParams.financialYear = financialYear;
    }

    public static int getPartyId() {
        return partyId;
    }

    public static void setPartyId(int partyId) {
        EditOrderParams.partyId = partyId;
    }

    public static int getBookId() {
        return bookId;
    }

    public static void setBookId(int bookId) {
        EditOrderParams.bookId = bookId;
    }

    public static int getOrderId() {
        return orderId;
    }

    public static void setOrderId(int orderId) {
        EditOrderParams.orderId = orderId;
    }

    public static String getPageNo() {
        return pageNo;
    }

    public static void setPageNo(String pageNo) {
        EditOrderParams.pageNo = pageNo;
    }

    public static String getPagesRate() {
        return pagesRate;
    }

    public static void setPagesRate(String pagesRate) {
        EditOrderParams.pagesRate = pagesRate;
    }

    public static String getArtWork() {
        return artWork;
    }

    public static void setArtWork(String artWork) {
        EditOrderParams.artWork = artWork;
    }

    public static String getArtWorkRate() {
        return artWorkRate;
    }

    public static void setArtWorkRate(String artWorkRate) {
        EditOrderParams.artWorkRate = artWorkRate;
    }

    public static String getColoring() {
        return coloring;
    }

    public static void setColoring(String coloring) {
        EditOrderParams.coloring = coloring;
    }

    public static String getColoringRate() {
        return coloringRate;
    }

    public static void setColoringRate(String coloringRate) {
        EditOrderParams.coloringRate = coloringRate;
    }

    public static String getWriting() {
        return writing;
    }

    public static void setWriting(String writing) {
        EditOrderParams.writing = writing;
    }

    public static String getWritingRate() {
        return writingRate;
    }

    public static void setWritingRate(String writingRate) {
        EditOrderParams.writingRate = writingRate;
    }

    public static String getOrderReceivedDate() {
        return orderReceivedDate;
    }

    public static void setOrderReceivedDate(String orderReceivedDate) {
        EditOrderParams.orderReceivedDate = orderReceivedDate;
    }
}
