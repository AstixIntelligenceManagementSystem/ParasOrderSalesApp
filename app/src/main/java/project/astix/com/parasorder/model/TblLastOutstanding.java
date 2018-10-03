
package project.astix.com.parasorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblLastOutstanding {

    @SerializedName("Storeid")
    @Expose
    private Integer storeid;
    @SerializedName("OutStanding")
    @Expose
    private Integer outStanding;

    public Integer getStoreid() {
        return storeid;
    }

    public void setStoreid(Integer storeid) {
        this.storeid = storeid;
    }

    public Integer getOutStanding() {
        return outStanding;
    }

    public void setOutStanding(Integer outStanding) {
        this.outStanding = outStanding;
    }

}
