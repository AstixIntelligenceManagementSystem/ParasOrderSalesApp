
package project.astix.com.parasorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblDistributorIDOrderIDLeft {

    @SerializedName("Customer")
    @Expose
    private String customer;
    @SerializedName("PDAOrderId")
    @Expose
    private Integer pDAOrderId;
    @SerializedName("flgInvExists")
    @Expose
    private Integer flgInvExists;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getPDAOrderId() {
        return pDAOrderId;
    }

    public void setPDAOrderId(Integer pDAOrderId) {
        this.pDAOrderId = pDAOrderId;
    }

    public Integer getFlgInvExists() {
        return flgInvExists;
    }

    public void setFlgInvExists(Integer flgInvExists) {
        this.flgInvExists = flgInvExists;
    }

}
