
package project.astix.com.parasorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPDAVanDayEndDetResult {

    @SerializedName("flgDayEndRequestAccept")
    @Expose
    private Integer flgDayEndRequestAccept;

    public Integer getFlgDayEndRequestAccept() {
        return flgDayEndRequestAccept;
    }

    public void setFlgDayEndRequestAccept(Integer flgDayEndRequestAccept) {
        this.flgDayEndRequestAccept = flgDayEndRequestAccept;
    }

}
