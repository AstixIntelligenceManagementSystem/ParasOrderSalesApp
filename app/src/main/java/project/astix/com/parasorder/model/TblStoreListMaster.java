
package project.astix.com.parasorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStoreListMaster {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("StoreID")
    @Expose
    private Integer storeID;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("StoreLatitude")
    @Expose
    private Double storeLatitude;
    @SerializedName("StoreLongitude")
    @Expose
    private Double storeLongitude;
    @SerializedName("StoreType")
    @Expose
    private Integer storeType;
    @SerializedName("LastTransactionDate")
    @Expose
    private String lastTransactionDate;
    @SerializedName("LastVisitDate")
    @Expose
    private String lastVisitDate;
    @SerializedName("Sstat")
    @Expose
    private String sstat;
    @SerializedName("IsClose")
    @Expose
    private Integer isClose;
    @SerializedName("IsNextDat")
    @Expose
    private Integer isNextDat;
    @SerializedName("RouteID")
    @Expose
    private Integer routeID;
    @SerializedName("PaymentStage")
    @Expose
    private String paymentStage;
    @SerializedName("flgHasQuote")
    @Expose
    private Integer flgHasQuote;
    @SerializedName("flgAllowQuotation")
    @Expose
    private Integer flgAllowQuotation;
    @SerializedName("flgSubmitFromQuotation")
    @Expose
    private Integer flgSubmitFromQuotation;
    @SerializedName("RouteNodeType")
    @Expose
    private Integer routeNodeType;
    @SerializedName("DBR")
    @Expose
    private String dBR;
    @SerializedName("CollectionReq")
    @Expose
    private Object collectionReq;
    @SerializedName("StoreIDPDA")
    @Expose
    private Object storeIDPDA;
    @SerializedName("flgHasBussiness")
    @Expose
    private Integer flgHasBussiness;
    @SerializedName("flgfeedbackReq")
    @Expose
    private Integer flgfeedbackReq;
    @SerializedName("OwnerName")
    @Expose
    private String ownerName;
    @SerializedName("StoreContactNo")
    @Expose
    private String storeContactNo;
    @SerializedName("StoreCatType")
    @Expose
    private String storeCatType;
    @SerializedName("StoreAddress")
    @Expose
    private String storeAddress;
    @SerializedName("StoreCity")
    @Expose
    private String storeCity;
    @SerializedName("StorePinCode")
    @Expose
    private Integer storePinCode;
    @SerializedName("StoreState")
    @Expose
    private String storeState;
    @SerializedName("flgRuleTaxVal")
    @Expose
    private Integer flgRuleTaxVal;
    @SerializedName("OutStanding")
    @Expose
    private Integer outStanding;
    @SerializedName("OverDue")
    @Expose
    private Integer overDue;
    @SerializedName("flgTransType")
    @Expose
    private Integer flgTransType;
    @SerializedName("SalesPersonName")
    @Expose
    private String salesPersonName;
    @SerializedName("SalesPersonContact")
    @Expose
    private String salesPersonContact;
    @SerializedName("TaxNumber")
    @Expose
    private Object taxNumber;
    @SerializedName("IsComposite")
    @Expose
    private Integer isComposite;
    @SerializedName("CityID")
    @Expose
    private Integer cityID;
    @SerializedName("StateID")
    @Expose
    private Integer stateID;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Double getStoreLatitude() {
        return storeLatitude;
    }

    public void setStoreLatitude(Double storeLatitude) {
        this.storeLatitude = storeLatitude;
    }

    public Double getStoreLongitude() {
        return storeLongitude;
    }

    public void setStoreLongitude(Double storeLongitude) {
        this.storeLongitude = storeLongitude;
    }

    public Integer getStoreType() {
        return storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    public String getLastTransactionDate() {
        return lastTransactionDate;
    }

    public void setLastTransactionDate(String lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
    }

    public String getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(String lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public String getSstat() {
        return sstat;
    }

    public void setSstat(String sstat) {
        this.sstat = sstat;
    }

    public Integer getIsClose() {
        return isClose;
    }

    public void setIsClose(Integer isClose) {
        this.isClose = isClose;
    }

    public Integer getIsNextDat() {
        return isNextDat;
    }

    public void setIsNextDat(Integer isNextDat) {
        this.isNextDat = isNextDat;
    }

    public Integer getRouteID() {
        return routeID;
    }

    public void setRouteID(Integer routeID) {
        this.routeID = routeID;
    }

    public String getPaymentStage() {
        return paymentStage;
    }

    public void setPaymentStage(String paymentStage) {
        this.paymentStage = paymentStage;
    }

    public Integer getFlgHasQuote() {
        return flgHasQuote;
    }

    public void setFlgHasQuote(Integer flgHasQuote) {
        this.flgHasQuote = flgHasQuote;
    }

    public Integer getFlgAllowQuotation() {
        return flgAllowQuotation;
    }

    public void setFlgAllowQuotation(Integer flgAllowQuotation) {
        this.flgAllowQuotation = flgAllowQuotation;
    }

    public Integer getFlgSubmitFromQuotation() {
        return flgSubmitFromQuotation;
    }

    public void setFlgSubmitFromQuotation(Integer flgSubmitFromQuotation) {
        this.flgSubmitFromQuotation = flgSubmitFromQuotation;
    }

    public Integer getRouteNodeType() {
        return routeNodeType;
    }

    public void setRouteNodeType(Integer routeNodeType) {
        this.routeNodeType = routeNodeType;
    }

    public String getDBR() {
        return dBR;
    }

    public void setDBR(String dBR) {
        this.dBR = dBR;
    }

    public Object getCollectionReq() {
        return collectionReq;
    }

    public void setCollectionReq(Object collectionReq) {
        this.collectionReq = collectionReq;
    }

    public Object getStoreIDPDA() {
        return storeIDPDA;
    }

    public void setStoreIDPDA(Object storeIDPDA) {
        this.storeIDPDA = storeIDPDA;
    }

    public Integer getFlgHasBussiness() {
        return flgHasBussiness;
    }

    public void setFlgHasBussiness(Integer flgHasBussiness) {
        this.flgHasBussiness = flgHasBussiness;
    }

    public Integer getFlgfeedbackReq() {
        return flgfeedbackReq;
    }

    public void setFlgfeedbackReq(Integer flgfeedbackReq) {
        this.flgfeedbackReq = flgfeedbackReq;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getStoreContactNo() {
        return storeContactNo;
    }

    public void setStoreContactNo(String storeContactNo) {
        this.storeContactNo = storeContactNo;
    }

    public String getStoreCatType() {
        return storeCatType;
    }

    public void setStoreCatType(String storeCatType) {
        this.storeCatType = storeCatType;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }

    public Integer getStorePinCode() {
        return storePinCode;
    }

    public void setStorePinCode(Integer storePinCode) {
        this.storePinCode = storePinCode;
    }

    public String getStoreState() {
        return storeState;
    }

    public void setStoreState(String storeState) {
        this.storeState = storeState;
    }

    public Integer getFlgRuleTaxVal() {
        return flgRuleTaxVal;
    }

    public void setFlgRuleTaxVal(Integer flgRuleTaxVal) {
        this.flgRuleTaxVal = flgRuleTaxVal;
    }

    public Integer getOutStanding() {
        return outStanding;
    }

    public void setOutStanding(Integer outStanding) {
        this.outStanding = outStanding;
    }

    public Integer getOverDue() {
        return overDue;
    }

    public void setOverDue(Integer overDue) {
        this.overDue = overDue;
    }

    public Integer getFlgTransType() {
        return flgTransType;
    }

    public void setFlgTransType(Integer flgTransType) {
        this.flgTransType = flgTransType;
    }

    public String getSalesPersonName() {
        return salesPersonName;
    }

    public void setSalesPersonName(String salesPersonName) {
        this.salesPersonName = salesPersonName;
    }

    public String getSalesPersonContact() {
        return salesPersonContact;
    }

    public void setSalesPersonContact(String salesPersonContact) {
        this.salesPersonContact = salesPersonContact;
    }

    public Object getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(Object taxNumber) {
        this.taxNumber = taxNumber;
    }

    public Integer getIsComposite() {
        return isComposite;
    }

    public void setIsComposite(Integer isComposite) {
        this.isComposite = isComposite;
    }

    public Integer getCityID() {
        return cityID;
    }

    public void setCityID(Integer cityID) {
        this.cityID = cityID;
    }

    public Integer getStateID() {
        return stateID;
    }

    public void setStateID(Integer stateID) {
        this.stateID = stateID;
    }

}
