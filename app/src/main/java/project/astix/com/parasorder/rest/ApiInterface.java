package project.astix.com.parasorder.rest;



import project.astix.com.parasorder.model.AllMasterTablesModel;
import project.astix.com.parasorder.model.AllSummaryReportDay;
import project.astix.com.parasorder.model.AllSummarySKUWiseDay;
import project.astix.com.parasorder.model.AllSummaryStoreSKUWiseDay;
import project.astix.com.parasorder.model.AllSummaryStoreWiseDay;
import project.astix.com.parasorder.model.Data;
import project.astix.com.parasorder.model.IMEIVersionDetails;
import project.astix.com.parasorder.model.IMEIVersionParentModel;
import project.astix.com.parasorder.model.ReportsInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {



    @POST("Home/PersonValidation")
    Call<IMEIVersionParentModel> Call_IMEIVersionDetailStatus(@Body IMEIVersionDetails IMEIVersionDetails);

    @POST("Home/AllData")
    Call<AllMasterTablesModel> Call_AllMasterData(@Body Data data);

    @POST("Home/GetAllDaySummary")
    Call<AllSummaryReportDay> Call_AllSummaryReportDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetSKUWiseDaySummary")
    Call<AllSummarySKUWiseDay> Call_AllSummarySKUWiseDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetSKUWiseMTDSummary")
    Call<AllSummarySKUWiseDay> Call_AllSummarySKUWiseMTDDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetStoreWiseDaySummary")
    Call<AllSummaryStoreWiseDay> Call_AllSummaryStoreWiseDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetStoreWiseMTDSummary")
    Call<AllSummaryStoreWiseDay> Call_AllSummaryStoreWiseMTDDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetStoreSKUWiseDaySummary")
    Call<AllSummaryStoreSKUWiseDay> Call_AllSummaryStoreSKUWiseDay(@Body ReportsInfo reportsInfo);


    @POST("Home/GetStoreSKUWiseMTDSummary")
    Call<AllSummaryStoreSKUWiseDay> Call_AllSummaryStoreSKUWiseMTDDay(@Body ReportsInfo reportsInfo);

}
