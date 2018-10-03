package project.astix.com.parasorder.rest;



import project.astix.com.parasorder.model.AllMasterTablesModel;
import project.astix.com.parasorder.model.Data;
import project.astix.com.parasorder.model.IMEIVersionDetails;
import project.astix.com.parasorder.model.IMEIVersionParentModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {



    @POST("Home/PersonValidation")
    Call<IMEIVersionParentModel> Call_IMEIVersionDetailStatus(@Body IMEIVersionDetails IMEIVersionDetails);

    @POST("Home/AllData")
    Call<AllMasterTablesModel> Call_AllMasterData(@Body Data data);


}
