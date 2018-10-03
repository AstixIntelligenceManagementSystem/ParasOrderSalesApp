
package project.astix.com.parasorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblBloodGroup {

    @SerializedName("BloddGroups")
    @Expose
    private String bloddGroups;

    public String getBloddGroups() {
        return bloddGroups;
    }

    public void setBloddGroups(String bloddGroups) {
        this.bloddGroups = bloddGroups;
    }

}
