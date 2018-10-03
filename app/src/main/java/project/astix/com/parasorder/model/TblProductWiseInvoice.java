
package project.astix.com.parasorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblProductWiseInvoice {

    @SerializedName("ProductId")
    @Expose
    private Integer productId;

    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public String getInVoiceForDate() {
        return inVoiceForDate;
    }

    public void setInVoiceForDate(String inVoiceForDate) {
        this.inVoiceForDate = inVoiceForDate;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getCatID() {
        return catID;
    }

    public void setCatID(Integer catID) {
        this.catID = catID;
    }

    public Integer getTotLiveDiscVal() {
        return totLiveDiscVal;
    }

    public void setTotLiveDiscVal(Integer totLiveDiscVal) {
        this.totLiveDiscVal = totLiveDiscVal;
    }

    public Integer getFreeQty() {
        return freeQty;
    }

    public void setFreeQty(Integer freeQty) {
        this.freeQty = freeQty;
    }

    public Integer getRouteID() {
        return routeID;
    }

    public void setRouteID(Integer routeID) {
        this.routeID = routeID;
    }

    public Integer getRouteNodeType() {
        return routeNodeType;
    }

    public void setRouteNodeType(Integer routeNodeType) {
        this.routeNodeType = routeNodeType;
    }

    @SerializedName("StoreID")
    @Expose
    private Integer storeID;

    @SerializedName("OrderQty")
    @Expose
    private Integer orderQty;

    @SerializedName("ProductPrice")
    @Expose
    private Float productPrice;

    @SerializedName("InvoiceForDate")
    @Expose
    private String inVoiceForDate;

    @SerializedName("OrdrID")
    @Expose
    private Integer orderID;

    @SerializedName("CatID")
    @Expose
    private Integer catID;

    @SerializedName("TotLineDiscVal")
    @Expose
    private Integer totLiveDiscVal;

    @SerializedName("Freeqty")
    @Expose
    private Integer freeQty;

    @SerializedName("RouteID")
    @Expose
    private Integer routeID;

    @SerializedName("RouteNodeType")
    @Expose
    private Integer routeNodeType;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }


}
