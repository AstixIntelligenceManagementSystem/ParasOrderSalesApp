
package project.astix.com.parasorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblDAGetAddedOutletSummaryOverallReport {


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;

    }
    @SerializedName("Header")
    @Expose
    private String header;

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;

    }

    public Integer getTotalStores() {
        return totalStores;
    }

    public void setTotalStores(Integer totalStores) {
        this.totalStores = totalStores;

    }

    public Integer getValidated() {
        return validated;
    }

    public void setValidated(Integer validated) {
        this.validated = validated;

    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;

    }

    public Integer getFlgNormalOverall() {
        return flgNormalOverall;
    }

    public void setFlgNormalOverall(Integer flgNormalOverall) {
        this.flgNormalOverall = flgNormalOverall;

    }

    @SerializedName("Child")
    @Expose
    private String child;
    @SerializedName("TotalStores")
    @Expose
    private Integer totalStores;

    @SerializedName("Validated")
    @Expose
    private Integer validated;

    @SerializedName("Pending")
    @Expose
    private Integer pending;

    @SerializedName("FlgNormalOverall")
    @Expose
    private Integer flgNormalOverall;


}
