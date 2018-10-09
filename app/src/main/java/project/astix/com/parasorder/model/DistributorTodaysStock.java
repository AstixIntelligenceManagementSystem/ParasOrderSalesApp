package project.astix.com.parasorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistributorTodaysStock {

    @SerializedName("IMEINo")
    @Expose
    private String iMEINo;

    @SerializedName("CustomerNodeId")
    @Expose
    private int customerNodeId;

    @SerializedName("CustomerNodeType ")
    @Expose
  private int customerNodeType ;

    public String getiMEINo() {
        return iMEINo;
    }

    public void setiMEINo(String iMEINo) {
        this.iMEINo = iMEINo;
    }

    public int getCustomerNodeId() {
        return customerNodeId;
    }

    public void setCustomerNodeId(int customerNodeId) {
        this.customerNodeId = customerNodeId;
    }

    public int getCustomerNodeType() {
        return customerNodeType;
    }

    public void setCustomerNodeType(int customerNodeType) {
        this.customerNodeType = customerNodeType;
    }


}
