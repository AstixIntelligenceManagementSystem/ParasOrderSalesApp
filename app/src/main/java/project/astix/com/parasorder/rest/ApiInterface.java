package project.astix.com.parasorder.rest;



import project.astix.com.parasorder.model.AllAddedOutletSummaryReportModel;
import project.astix.com.parasorder.model.AllMasterTablesModel;
import project.astix.com.parasorder.model.AllSummaryReportDay;
import project.astix.com.parasorder.model.AllSummarySKUWiseDay;
import project.astix.com.parasorder.model.AllSummaryStoreSKUWiseDay;
import project.astix.com.parasorder.model.AllSummaryStoreWiseDay;
import project.astix.com.parasorder.model.AllTargetVsAchieved;
import project.astix.com.parasorder.model.ConfirmVanStock;
import project.astix.com.parasorder.model.Data;
import project.astix.com.parasorder.model.DistributorStockData;
import project.astix.com.parasorder.model.DistributorTodaysStock;
import project.astix.com.parasorder.model.IMEIVersionDetails;
import project.astix.com.parasorder.model.IMEIVersionParentModel;
import project.astix.com.parasorder.model.PersonInfo;
import project.astix.com.parasorder.model.RegistrationValidation;
import project.astix.com.parasorder.model.ReportsAddedOutletSummary;
import project.astix.com.parasorder.model.ReportsInfo;
import project.astix.com.parasorder.model.StockData;
import project.astix.com.parasorder.model.TblPDAConfirmVanStockResult;
import project.astix.com.parasorder.model.TblPDAVanDayEndDetResult;
import project.astix.com.parasorder.model.TblSaveVanStockRequestResult;
import project.astix.com.parasorder.model.VanDayEnd;
import project.astix.com.parasorder.model.VanStockRequest;
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


    @POST("Home/GetTargetVsAchieved")
    Call<AllTargetVsAchieved> Call_AllTargetVsAchieved(@Body Data data);


    @POST("Home/GetPDAGetAddedOutletSummaryReport")
    Call<AllAddedOutletSummaryReportModel> Call_AllPDAGetAddedOutletSummaryReport(@Body ReportsAddedOutletSummary reportsAddedOutletSummary);

    @POST("Home/GetPersonDetail")
    Call<RegistrationValidation> Call_GetRegistrationDetails(@Body PersonInfo personInfo);


    @POST("Home/SaveVanStockRequest")
    Call<TblSaveVanStockRequestResult> Call_SaveVanStockRequest(@Body VanStockRequest VanStockRequest);

    @POST("Home/PDAConfirmVanStock")
    Call<TblPDAConfirmVanStockResult> Call_PDAConfirmVanStock(@Body ConfirmVanStock ConfirmVanStock);

    @POST("Home/PDAVanDayEnd")
    Call<TblPDAVanDayEndDetResult> Call_PDAVanDayEnd(@Body VanDayEnd VanDayEnd);

    @POST("Home/StockMaster")
    Call<StockData> Call_StockData(@Body Data data);

    @POST("Home/GetDistributorTodaysStock")
    Call<DistributorStockData> Call_DistributorTodayStockData(@Body DistributorTodaysStock data);


}
