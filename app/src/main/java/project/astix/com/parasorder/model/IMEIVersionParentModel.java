
package project.astix.com.parasorder.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IMEIVersionParentModel {

    @SerializedName("tblUserAuthentication")
    @Expose
    private List<TblUserAuthentication> tblUserAuthentication = null;
    @SerializedName("tblAvailableVersion")
    @Expose
    private List<TblAvailableVersion> tblAvailableVersion = null;

    public List<TblUserAuthentication> getTblUserAuthentication() {
        return tblUserAuthentication;
    }

    public void setTblUserAuthentication(List<TblUserAuthentication> tblUserAuthentication) {
        this.tblUserAuthentication = tblUserAuthentication;
    }

    public List<TblAvailableVersion> getTblAvailableVersion() {
        return tblAvailableVersion;
    }

    public void setTblAvailableVersion(List<TblAvailableVersion> tblAvailableVersion) {
        this.tblAvailableVersion = tblAvailableVersion;
    }

}
