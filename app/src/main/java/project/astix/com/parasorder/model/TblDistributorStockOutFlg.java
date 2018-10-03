
package project.astix.com.parasorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblDistributorStockOutFlg {

    @SerializedName("flgStockOutEntryDone")
    @Expose
    private Integer flgStockOutEntryDone;

    public Integer getFlgStockOutEntryDone() {
        return flgStockOutEntryDone;
    }

    public void setFlgStockOutEntryDone(Integer flgStockOutEntryDone) {
        this.flgStockOutEntryDone = flgStockOutEntryDone;
    }

}
