
package project.astix.com.parasorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPDAConfirmVanStockResult {

    @SerializedName("flgDataConfirmed")
    @Expose
    private Integer flgDataConfirmed;

    public Integer getFlgDataConfirmed() {
        return flgDataConfirmed;
    }

    public void setFlgDataConfirmed(Integer flgDataConfirmed) {
        this.flgDataConfirmed = flgDataConfirmed;
    }

}
