package ibridotechnologies.com.accountsoftware.Model;

/**
 * Created by sushil on 09/08/18.
 */

public class Order {
    private static int partyId,bookId,orderId;
    private static String orderReceivedDate,pageNo,pagesRate,artWork,artWorkRate,coloring,coloringRate,writing,writingRate,proofing,proofingRate,financialYear,savedBy;

    public static int getOrderId() {
        return orderId;
    }

    public static void setOrderId(int orderId) {
        Order.orderId = orderId;
    }

    public static String getFinancialYear() {
        return financialYear;
    }

    public static void setFinancialYear(String financialYear) {
        Order.financialYear = financialYear;
    }

    public String getProofing() {
        return proofing;
    }

    public void setProofing(String proofing) {
        Order.proofing = proofing;
    }

    public String getProofingRate() {
        return proofingRate;
    }

    public void setProofingRate(String proofingRate) {
        Order.proofingRate = proofingRate;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getOrderReceivedDate() {
        return orderReceivedDate;
    }

    public void setOrderReceivedDate(String orderReceivedDate) {
        this.orderReceivedDate = orderReceivedDate;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPagesRate() {
        return pagesRate;
    }

    public void setPagesRate(String pagesRate) {
        this.pagesRate = pagesRate;
    }

    public String getArtWork() {
        return artWork;
    }

    public void setArtWork(String artWork) {
        this.artWork = artWork;
    }

    public String getArtWorkRate() {
        return artWorkRate;
    }

    public void setArtWorkRate(String artWorkRate) {
        this.artWorkRate = artWorkRate;
    }

    public String getColoring() {
        return coloring;
    }

    public void setColoring(String coloring) {
        this.coloring = coloring;
    }

    public String getColoringRate() {
        return coloringRate;
    }

    public void setColoringRate(String coloringRate) {
        this.coloringRate = coloringRate;
    }

    public String getWriting() {
        return writing;
    }

    public void setWriting(String writing) {
        this.writing = writing;
    }

    public String getWritingRate() {
        return writingRate;
    }

    public void setWritingRate(String writingRate) {
        this.writingRate = writingRate;
    }

    public String getSavedBy() {
        return savedBy;
    }

    public void setSavedBy(String savedBy) {
        this.savedBy = savedBy;
    }
}
