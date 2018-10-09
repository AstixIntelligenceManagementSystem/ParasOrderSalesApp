
package project.astix.com.parasorder.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockData {

    @SerializedName("tblStockUploadedStatus")
    @Expose
    private List<TblStockUploadedStatus> tblStockUploadedStatus = null;
    @SerializedName("tblDistributorProductStock")
    @Expose
    private List<TblDistributorProductStock> tblDistributorProductStock = null;
    @SerializedName("tblCycleID")
    @Expose
    private List<TblCycleID> tblCycleID = null;
    @SerializedName("tblDistributorStockOutFlg")
    @Expose
    private List<TblDistributorStockOutFlg> tblDistributorStockOutFlg = null;
    @SerializedName("tblDistributorIDOrderIDLeft")
    @Expose
    private List<TblDistributorIDOrderIDLeft> tblDistributorIDOrderIDLeft = null;

    public List<TblStockUploadedStatus> getTblStockUploadedStatus() {
        return tblStockUploadedStatus;
    }

    public void setTblStockUploadedStatus(List<TblStockUploadedStatus> tblStockUploadedStatus) {
        this.tblStockUploadedStatus = tblStockUploadedStatus;
    }

    public List<TblDistributorProductStock> getTblDistributorProductStock() {
        return tblDistributorProductStock;
    }

    public void setTblDistributorProductStock(List<TblDistributorProductStock> tblDistributorProductStock) {
        this.tblDistributorProductStock = tblDistributorProductStock;
    }

    public List<TblCycleID> getTblCycleID() {
        return tblCycleID;
    }

    public void setTblCycleID(List<TblCycleID> tblCycleID) {
        this.tblCycleID = tblCycleID;
    }

    public List<TblDistributorStockOutFlg> getTblDistributorStockOutFlg() {
        return tblDistributorStockOutFlg;
    }

    public void setTblDistributorStockOutFlg(List<TblDistributorStockOutFlg> tblDistributorStockOutFlg) {
        this.tblDistributorStockOutFlg = tblDistributorStockOutFlg;
    }

    public List<TblDistributorIDOrderIDLeft> getTblDistributorIDOrderIDLeft() {
        return tblDistributorIDOrderIDLeft;
    }

    public void setTblDistributorIDOrderIDLeft(List<TblDistributorIDOrderIDLeft> tblDistributorIDOrderIDLeft) {
        this.tblDistributorIDOrderIDLeft = tblDistributorIDOrderIDLeft;
    }

}
