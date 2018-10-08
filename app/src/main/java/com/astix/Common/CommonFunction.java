package com.astix.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

import project.astix.com.parasorder.InterfaceRetrofit;
import project.astix.com.parasorder.ProductEntryForm;
import project.astix.com.parasorder.R;
import project.astix.com.parasorder.model.AllAddedOutletSummaryReportModel;
import project.astix.com.parasorder.model.AllSummaryReportDay;
import project.astix.com.parasorder.model.AllSummarySKUWiseDay;
import project.astix.com.parasorder.model.AllSummaryStoreSKUWiseDay;
import project.astix.com.parasorder.model.AllSummaryStoreWiseDay;
import project.astix.com.parasorder.model.AllTargetVsAchieved;
import project.astix.com.parasorder.model.InvoiceList;
import project.astix.com.parasorder.model.ReportsAddedOutletSummary;
import project.astix.com.parasorder.model.ReportsInfo;
import project.astix.com.parasorder.model.TblActualVsTargetNote;
import project.astix.com.parasorder.model.TblActualVsTargetReport;
import project.astix.com.parasorder.model.TblAllSummaryDay;
import project.astix.com.parasorder.model.TblBankMaster;
import project.astix.com.parasorder.model.TblBloodGroup;
import project.astix.com.parasorder.model.TblCategoryMaster;
import project.astix.com.parasorder.model.TblCycleID;
import project.astix.com.parasorder.model.TblDAGetAddedOutletSummaryOverallReport;
import project.astix.com.parasorder.model.TblDAGetAddedOutletSummaryReport;
import project.astix.com.parasorder.model.TblDayStartAttendanceOption;
import project.astix.com.parasorder.model.TblDistributorIDOrderIDLeft;
import project.astix.com.parasorder.model.TblDistributorProductStock;
import project.astix.com.parasorder.model.TblDistributorStockOutFlg;
import project.astix.com.parasorder.model.TblEducationQuali;
import project.astix.com.parasorder.model.TblGetPDAQuestGrpMapping;
import project.astix.com.parasorder.model.TblGetPDAQuestMstr;
import project.astix.com.parasorder.model.TblGetPDAQuestOptionMstr;
import project.astix.com.parasorder.model.TblGetPDAQuestionDependentMstr;
import project.astix.com.parasorder.model.TblGetReturnsReasonForPDAMstr;
import project.astix.com.parasorder.model.TblInstrumentMaster;
import project.astix.com.parasorder.model.TblInvoiceCaption;
import project.astix.com.parasorder.model.TblInvoiceExecutionProductList;
import project.astix.com.parasorder.model.TblInvoiceLastVisitDetails;
import project.astix.com.parasorder.model.TblIsSchemeApplicable;
import project.astix.com.parasorder.model.TblLastOutstanding;
import project.astix.com.parasorder.model.TblManagerMstr;
import project.astix.com.parasorder.model.TblPDAGetExecutionSummary;
import project.astix.com.parasorder.model.TblPDAGetLODQty;
import project.astix.com.parasorder.model.TblPDAGetLastOrderDate;
import project.astix.com.parasorder.model.TblPDAGetLastOrderDetails;
import project.astix.com.parasorder.model.TblPDAGetLastOrderDetailsTotalValues;
import project.astix.com.parasorder.model.TblPDAGetLastVisitDate;
import project.astix.com.parasorder.model.TblPDAGetLastVisitDetails;
import project.astix.com.parasorder.model.TblPDANotificationMaster;
import project.astix.com.parasorder.model.TblPDAQuestOptionDependentMstr;
import project.astix.com.parasorder.model.TblPDAQuestOptionValuesDependentMstr;
import project.astix.com.parasorder.model.TblPendingInvoices;
import project.astix.com.parasorder.model.TblPreAddedStores;
import project.astix.com.parasorder.model.TblPreAddedStoresDataDetails;
import project.astix.com.parasorder.model.TblPriceApplyType;
import project.astix.com.parasorder.model.TblProductListLastVisitStockOrOrderMstr;
import project.astix.com.parasorder.model.TblProductListMaster;
import project.astix.com.parasorder.model.TblProductSegementMap;
import project.astix.com.parasorder.model.TblProductWiseInvoice;
import project.astix.com.parasorder.model.TblQuestIDForName;
import project.astix.com.parasorder.model.TblQuestIDForOutChannel;
import project.astix.com.parasorder.model.TblRouteListMaster;
import project.astix.com.parasorder.model.TblSKUWiseDaySummary;
import project.astix.com.parasorder.model.TblStateCityMaster;
import project.astix.com.parasorder.model.TblStockUploadedStatus;
import project.astix.com.parasorder.model.TblStoreCloseReasonMaster;
import project.astix.com.parasorder.model.TblStoreCountDetails;
import project.astix.com.parasorder.model.TblStoreLastDeliveryNoteNumber;
import project.astix.com.parasorder.model.TblStoreListMaster;
import project.astix.com.parasorder.model.TblStoreListWithPaymentAddress;
import project.astix.com.parasorder.model.TblStoreSKUWiseDaySummary;
import project.astix.com.parasorder.model.TblStoreSomeProdQuotePriceMstr;
import project.astix.com.parasorder.model.TblStoreWiseDaySummary;
import project.astix.com.parasorder.model.TblSupplierMstrList;
import project.astix.com.parasorder.model.TblUOMMapping;
import project.astix.com.parasorder.model.TblUOMMaster;
import project.astix.com.parasorder.model.AllMasterTablesModel;
import project.astix.com.parasorder.model.Data;
import project.astix.com.parasorder.model.TblAppMasterFlags;
import project.astix.com.parasorder.model.TblUserName;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import project.astix.com.parasorder.PRJDatabase;
import project.astix.com.parasorder.model.AllMasterTablesModel;
import project.astix.com.parasorder.model.Data;
import project.astix.com.parasorder.model.TblBloodGroup;
import project.astix.com.parasorder.model.TblDayStartAttendanceOption;
import project.astix.com.parasorder.model.TblEducationQuali;
import project.astix.com.parasorder.model.TblGetPDAQuestionDependentMstr;
import project.astix.com.parasorder.model.TblRouteListMaster;
import project.astix.com.parasorder.model.TblValueVolumeTarget;
import project.astix.com.parasorder.rest.ApiClient;
import project.astix.com.parasorder.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonFunction {


    public static Bitmap normalizeImageForUri(Context context, Uri uri)
    {
        Bitmap rotatedBitmap = null;

        try {

            ExifInterface exif = new ExifInterface(uri.getPath());

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            rotatedBitmap = rotateBitmap(bitmap, orientation);
            if (!bitmap.equals(rotatedBitmap))
            {
                saveBitmapToFile(context, rotatedBitmap, uri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }

    private static  Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();

            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    private  static void saveBitmapToFile(Context context, Bitmap croppedImage, Uri saveUri) {
        if (saveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = context.getContentResolver().openOutputStream(saveUri);
                if (outputStream != null) {
                    croppedImage.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException e) {

            } finally {
                closeSilently(outputStream);
                croppedImage.recycle();
            }
        }
    }

    private static  void closeSilently(@Nullable Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (Throwable t) {
            // Do nothing
        }
    }

    public static void getAllMasterTableModelData(Context context, final String imei, String RegistrationID,String msgToShow){
      final  PRJDatabase dbengine = new PRJDatabase(context);
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle(msgToShow);//context.getResources().getString(R.string.Loading));
        mProgressDialog.setMessage(context.getResources().getString(R.string.RetrivingDataMsg));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList=new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = sdf.format(date1).toString().trim();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService=ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp=  dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String  CoverageNodeId= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String   CoverageNodeType= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData=1;
        String  serverDateForSPref=	dbengine.fnGetServerDate();
        ArrayList<InvoiceList> arrDistinctInvoiceNumbersNew=dbengine.getDistinctInvoiceNumbersNew();
        Data data=new Data();
        data.setApplicationTypeId(CommonInfo.Application_TypeID);
        data.setIMEINo(imei);
        data.setVersionId(CommonInfo.DATABASE_VERSIONID);
        data.setRegistrationId(RegistrationID);
        data.setForDate(fDate);
        data.setFlgAllRouteData(1);

        data.setInvoiceList(arrDistinctInvoiceNumbersNew);
        data.setInvoiceList(null);
        data.setRouteNodeId(0);
        data.setRouteNodeType(0);
        data.setCoverageAreaNodeId(Integer.parseInt(CoverageNodeId));
        data.setCoverageAreaNodeType(Integer.parseInt(CoverageNodeType));

        Call<AllMasterTablesModel> call= apiService.Call_AllMasterData(data);
        call.enqueue(new Callback<AllMasterTablesModel>() {
            @Override
            public void onResponse(Call<AllMasterTablesModel> call, Response<AllMasterTablesModel> response) {
                if(response.code()==200){
                    AllMasterTablesModel allMasterTablesModel=  response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    dbengine.deletetblDayStartAttendanceOptions();
                    List<TblDayStartAttendanceOption> tblDayStartAttendanceOption=  allMasterTablesModel.getTblDayStartAttendanceOptions();
                    if(tblDayStartAttendanceOption.size()>0){

                        int AutoIdStore= 0;
                        for(TblDayStartAttendanceOption dayStartAttendanceOption:tblDayStartAttendanceOption){
                            AutoIdStore=AutoIdStore++;
                            dbengine.savetblDayStartAttendanceOptions(AutoIdStore,""+dayStartAttendanceOption.getReasonId(),dayStartAttendanceOption.getReasonDescr(),dayStartAttendanceOption.getFlgToShowTextBox(),dayStartAttendanceOption.getFlgSOApplicable(),dayStartAttendanceOption.getFlgDSRApplicable(),dayStartAttendanceOption.getFlgNoVisitOption(),dayStartAttendanceOption.getSeqNo(),dayStartAttendanceOption.getFlgDelayedReason());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblDayStartAttendanceOptions");
                    }
                    //table 2
                    dbengine.Delete_tblRouteMasterAndDistributorMstr();
                    List<TblRouteListMaster> tblRouteListMaster=  allMasterTablesModel.getTblRouteListMaster();
                    if(tblRouteListMaster.size()>0){

                        int AutoIdStore= 0;
                        for(TblRouteListMaster RouteListMaster:tblRouteListMaster){

                            dbengine.saveRoutesInfo(""+RouteListMaster.getID(),""+RouteListMaster.getRouteType(),RouteListMaster.getDescr(),Integer.parseInt(RouteListMaster.getActive().toString()),Integer.parseInt(RouteListMaster.getActive().toString()),fDate);
                        }
                    }
                    else{
                        blankTablearrayList.add("tblRouteListMaster");
                    }
                    //table 3
                    dbengine.Delete_bloodGroupMstr();
                    List<TblBloodGroup> tblBloodGroup=  allMasterTablesModel.getTblBloodGroup();

                    if(tblBloodGroup.size()>0){
                        int AutoIdStore= 0;
                        for(TblBloodGroup BloodGroup:tblBloodGroup){
                            dbengine.savetblBloodGroup(BloodGroup.getBloddGroups());

                        }
                    }
                    else{
                        blankTablearrayList.add("tblBloodGroup");
                    }
                    //table 4
                    dbengine.Delete_tblEducationQuali();
                    List<TblEducationQuali> tblEducationQuali=  allMasterTablesModel.getTblEducationQuali();

                    if(tblEducationQuali.size()>0){
                        int AutoIdStore= 0;
                        for(TblEducationQuali EducationQuali:tblEducationQuali){
                            dbengine.savetblEducationQuali(EducationQuali.getQualification());

                        }
                    }
                    else{
                        blankTablearrayList.add("tblEducationQuali");
                    }
                    //table 5
                    dbengine.Delete_tblQuestIDForOutChannel();
                    List<TblQuestIDForOutChannel> tblQuestIDForOutChannel=  allMasterTablesModel.getTblQuestIDForOutChannel();

                    if(tblQuestIDForOutChannel.size()>0){
                        for(TblQuestIDForOutChannel QuestIDForOutChannel:tblQuestIDForOutChannel){
                            dbengine.saveOutletChammetQstnIdGrpId(QuestIDForOutChannel.getGrpQuestID(),QuestIDForOutChannel.getQuestID(),QuestIDForOutChannel.getOptionID(),QuestIDForOutChannel.getSectionCount());

                        }

                    }
                    else{
                        blankTablearrayList.add("tblQuestIDForOutChannel");
                    }
                    //table 6
                    dbengine.Delete_tblGetPDAQuestMstr();
                    List<TblGetPDAQuestMstr> tblGetPDAQuestMstr=  allMasterTablesModel.getTblGetPDAQuestMstr();

                    if(tblGetPDAQuestMstr.size()>0){
                        int AutoIdStore= 0;

                        dbengine.savetblQuestionMstrRetroFit(tblGetPDAQuestMstr);


                    }
                    else{
                        blankTablearrayList.add("tblGetPDAQuestMstr");
                    }


                    dbengine.deletetblStoreCloseReasonMaster();
                    List<TblStoreCloseReasonMaster> tblStoreCloseReasonMaster=  allMasterTablesModel.getTblStoreCloseReasonMaster();


                    if(tblStoreCloseReasonMaster.size()>0){

                        for(TblStoreCloseReasonMaster StoreCloseReasonMaster:tblStoreCloseReasonMaster){
                            dbengine.savetblStoreCloseReasonMaster(StoreCloseReasonMaster.getCloseReasonID(),StoreCloseReasonMaster.getCloseReasonDescr());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblStoreCloseReasonMaster");
                    }


                    //table 7
                    dbengine.Delete_tblQuestIDForName();
                    List<TblQuestIDForName> tblQuestIDForName=  allMasterTablesModel.getTblQuestIDForName();

                    if(tblQuestIDForName.size()>0){

                        for(TblQuestIDForName QuestIDForName:tblQuestIDForName){
                            dbengine.savetblQuestIDForName(QuestIDForName.getID(),QuestIDForName.getGrpQuestID(),QuestIDForName.getQuestID(),QuestIDForName.getQuestDesc());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblQuestIDForName");
                    }

                    //table 8----------------
                    dbengine.Delete_tblPDAQuestGrpMappingMstr();
                    List<TblGetPDAQuestGrpMapping> tblGetPDAQuestGrpMapping=  allMasterTablesModel.getTblGetPDAQuestGrpMapping();

                    if(tblGetPDAQuestGrpMapping.size()>0){


                        dbengine.savetblPDAQuestGrpMappingMstr(tblGetPDAQuestGrpMapping);
                    }
                    else{
                        blankTablearrayList.add("tblGetPDAQuestGrpMapping");
                    }

                    //table 9-------------------------------
                    dbengine.Delete_tblOptionMstr();
                    List<TblGetPDAQuestOptionMstr> tblGetPDAQuestOptionMstr=  allMasterTablesModel.getTblGetPDAQuestOptionMstr();

                    if(tblGetPDAQuestOptionMstr.size()>0){
                        dbengine.savetblOptionMstrRetrofit(tblGetPDAQuestOptionMstr);


                    }
                    else{
                        blankTablearrayList.add("tblGetPDAQuestOptionMstr");
                    }
                    //table 10-------------------------------
                    dbengine.Delete_tblQuestionDependentMstr();
                    List<TblGetPDAQuestionDependentMstr> tblGetPDAQuestionDependentMstr=  allMasterTablesModel.getTblGetPDAQuestionDependentMstr();

                    if(tblGetPDAQuestionDependentMstr.size()>0){
                        for(TblGetPDAQuestionDependentMstr GetPDAQuestionDependentMstr:tblGetPDAQuestionDependentMstr){
                            dbengine.savetblQuestionDependentMstr(GetPDAQuestionDependentMstr.getQuestID(),GetPDAQuestionDependentMstr.getOptID(),GetPDAQuestionDependentMstr.getDependentQuestID(),GetPDAQuestionDependentMstr.getGrpQuestID(),GetPDAQuestionDependentMstr.getGrpDepQuestID());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblGetPDAQuestionDependentMstr");
                    }

                    //table 11-------------------------------
                    dbengine.Delete_tblPDAQuestOptionDependentMstr();
                    List<TblPDAQuestOptionDependentMstr> tblPDAQuestOptionDependentMstr=  allMasterTablesModel.getTblPDAQuestOptionDependentMstr();

                    if(tblPDAQuestOptionDependentMstr.size()>0){
                        for(TblPDAQuestOptionDependentMstr PDAQuestOptionDependentMstr:tblPDAQuestOptionDependentMstr){
                            dbengine.savetblPDAQuestOptionDependentMstr(PDAQuestOptionDependentMstr.getQstId(),PDAQuestOptionDependentMstr.getDepQstId(),PDAQuestOptionDependentMstr.getQstId(),PDAQuestOptionDependentMstr.getGrpDepQuestID());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblPDAQuestOptionDependentMstr");
                    }
                    //table 12-------------------------------
                    dbengine.Delete_tblPDAQuestOptionValuesDependentMstr();
                    List<TblPDAQuestOptionValuesDependentMstr> tblPDAQuestOptionValuesDependentMstr=  allMasterTablesModel.getTblPDAQuestOptionValuesDependentMstr();

                    if(tblPDAQuestOptionValuesDependentMstr.size()>0){
                        for(TblPDAQuestOptionValuesDependentMstr PDAQuestOptionValuesDependentMstr:tblPDAQuestOptionValuesDependentMstr){
                            dbengine.savetblPDAQuestOptionValuesDependentMstr(PDAQuestOptionValuesDependentMstr.getDepQstId(),PDAQuestOptionValuesDependentMstr.getDepOptID(),PDAQuestOptionValuesDependentMstr.getQuestId(),PDAQuestOptionValuesDependentMstr.getOptID(),PDAQuestOptionValuesDependentMstr.getOptDescr(),PDAQuestOptionValuesDependentMstr.getSequence(),PDAQuestOptionValuesDependentMstr.getGrpQuestID(),PDAQuestOptionValuesDependentMstr.getGrpDepQuestID());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblPDAQuestOptionValuesDependentMstr");
                    }
                    //table 13-------------------------------
                    dbengine.Delete_tblNotificationMstr();
                    List<TblPDANotificationMaster> tblPDANotificationMaster=  allMasterTablesModel.getTblPDANotificationMaster();

                    if(tblPDANotificationMaster.size()>0){
                        int SerialNo=0;
                        for(TblPDANotificationMaster PDANotificationMaster:tblPDANotificationMaster){
                            SerialNo= SerialNo++;
                            dbengine.inserttblNotificationMstr(SerialNo,imei,PDANotificationMaster.getNotificationMessage(),PDANotificationMaster.getMsgSendingTime(),0,0,"0",0,PDANotificationMaster.getMsgServerID());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblPDANotificationMaster");
                    }
                    //table 14-------------------------------
                    dbengine.delete_all_storeDetailTables();//deleting all tables related to
                    List<TblUserName> tblUserName=  allMasterTablesModel.getTblUserName();

                    if(tblUserName.size()>0){

                        for(TblUserName UserName:tblUserName){
                            dbengine.saveTblUserName(UserName.getUserName());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblUserName");
                    }
                    //table 15-------------------------------

                    List<TblStoreCountDetails> tblStoreCountDetails=  allMasterTablesModel.getTblStoreCountDetails();

                    if(tblStoreCountDetails.size()>0){

                        for(TblStoreCountDetails StoreCountDetails:tblStoreCountDetails){

                            dbengine.saveTblStoreCountDetails(""+StoreCountDetails.getTotStoreAdded(),""+StoreCountDetails.getTodayStoreAdded());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblStoreCountDetails");
                    }
                    //table 16-------------------------------
                    //already deleted above

                    List<TblPreAddedStores> tblPreAddedStores=  allMasterTablesModel.getTblPreAddedStores();

                    HashMap<String, String> hmapPreAddedStoreIdSstat=new HashMap<String, String>();
                    hmapPreAddedStoreIdSstat=dbengine.checkForPreAddedStoreIdSstat();
                    if(tblPreAddedStores.size()>0){

                        dbengine.saveTblPreAddedStoresRetrofit(tblPreAddedStores,hmapPreAddedStoreIdSstat);
                    }
                    else{
                        blankTablearrayList.add("tblPreAddedStores");
                    }

                    if(hmapPreAddedStoreIdSstat!=null && hmapPreAddedStoreIdSstat.size()>0) {
                        hmapPreAddedStoreIdSstat.clear();
                        hmapPreAddedStoreIdSstat=null;
                    }
                    //table 17-------------------------------
                    //already deleted above
                    List<TblPreAddedStoresDataDetails> tblPreAddedStoresDataDetails=  allMasterTablesModel.getTblPreAddedStoresDataDetails();

                    if(tblPreAddedStoresDataDetails.size()>0){

                        dbengine.saveTblPreAddedStoresDataDetailsRetrofit(tblPreAddedStoresDataDetails);
                    }
                    else{
                        blankTablearrayList.add("tblPreAddedStoresDataDetails");
                    }

                    //table 18-------------------------------
                    dbengine.deletetblStateCityMaster();
                    List<TblStateCityMaster> tblStateCityMaster=  allMasterTablesModel.getTblStateCityMaster();

                    if(tblStateCityMaster.size()>0){
                        for(TblStateCityMaster StateCityMaster:tblStateCityMaster){
                            dbengine.fnsavetblStateCityMaster(""+StateCityMaster.getStateID(),StateCityMaster.getState(),""+StateCityMaster.getCityID(),StateCityMaster.getCity(),StateCityMaster.getCityDefault());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblStateCityMaster");
                    }

                    //table 19-------------------------------
                    dbengine.Delete_tblProductList_for_refreshData();
                    List<TblProductListMaster> tblProductListMaster=  allMasterTablesModel.getTblProductListMaster();

                    if(tblProductListMaster.size()>0){

                        dbengine.saveSOAPdataProductListRetrofit(tblProductListMaster);

                    }
                    else{
                        blankTablearrayList.add("tblProductListMaster");
                    }

                    //table 20-------------------------------
                    //deleted above
                    List<TblProductSegementMap> tblProductSegementMap=  allMasterTablesModel.getTblProductSegementMap();

                    if(tblProductSegementMap.size()>0){

                        dbengine.saveProductSegementMapRetrofit(tblProductSegementMap);

                    }
                    else{
                        blankTablearrayList.add("tblProductSegementMap");
                    }

                    //table 21-------------------------------
                    //deleted above
                    List<TblPriceApplyType> tblPriceApplyType=  allMasterTablesModel.getTblPriceApplyType();

                    if(tblPriceApplyType.size()>0){
                        for(TblPriceApplyType priceApplyType:tblPriceApplyType){
                            dbengine.savetblPriceApplyType(priceApplyType.getDiscountLevel(),priceApplyType.getCutoffvalue());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblPriceApplyType");
                    }

                    //table 22-------------------------------
                    //deleted above
                    List<TblUOMMaster> tblUOMMaster=  allMasterTablesModel.getTblUOMMaster();

                    if(tblUOMMaster.size()>0){
                        for(TblUOMMaster priceApplyType:tblUOMMaster){
                            dbengine.insertUOMMstr(priceApplyType.getBUOMID(),priceApplyType.getBUOMName(),priceApplyType.getFlgRetailUnit());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblUOMMaster");
                    }
                    //table 23-------------------------------
                    //deleted above
                    List<TblUOMMapping> tblUOMMapping=  allMasterTablesModel.getTblUOMMapping();

                    if(tblUOMMapping.size()>0){
                        for(TblUOMMapping UOMMapping:tblUOMMapping){
                            dbengine.insertUOMMapping(UOMMapping.getNodeID(),UOMMapping.getNodeType(),UOMMapping.getBaseUOMID(),UOMMapping.getPackUOMID(),UOMMapping.getRelConversionUnits(),UOMMapping.getFlgVanLoading());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblUOMMapping");
                    }

                    //table 24-------------------------------
                    //deleted above
                    dbengine.delete_tblManagerMstr();
                    List<TblManagerMstr> tblManagerMstr=  allMasterTablesModel.getTblManagerMstr();

                    if(tblManagerMstr.size()>0){
                        for(TblManagerMstr ManagerMstr:tblManagerMstr){
                            dbengine.savetblManagerMstr(""+ManagerMstr.getPersonID(),""+ManagerMstr.getPersonType(),ManagerMstr.getPersonName(),""+ManagerMstr.getManagerID(),""+ManagerMstr.getManagerType(),ManagerMstr.getManagerName());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblManagerMstr");
                    }
                    //table 25-------------------------------
                    //deleted above
                    dbengine.Delete_tblCategory_for_refreshData();
                    List<TblCategoryMaster> tblCategoryMaster=  allMasterTablesModel.getTblCategoryMaster();

                    if(tblCategoryMaster.size()>0){
                        for(TblCategoryMaster CategoryMaster:tblCategoryMaster){
                            dbengine.saveCategory(CategoryMaster.getNODEID(),CategoryMaster.getCATEGORY(),0);
                        }

                    }
                    else{
                        blankTablearrayList.add("tblCategoryMaster");
                    }

                    //table 26-------------------------------

                    dbengine.deletetblBankMaster();
                    List<TblBankMaster> tblBankMaster=  allMasterTablesModel.getTblBankMaster();

                    if(tblBankMaster.size()>0){

                        dbengine.savetblBankMaster(tblBankMaster);

                    }
                    else{
                        blankTablearrayList.add("tblBankMaster");
                    }
                    //table 27-------------------------------
                    //deleted above

                    List<TblInstrumentMaster> tblInstrumentMaster=  allMasterTablesModel.getTblInstrumentMaster();

                    if(tblInstrumentMaster.size()>0){
                        for(TblInstrumentMaster instrumentMaster:tblInstrumentMaster){
                            dbengine.savetblInstrumentMaster(instrumentMaster.getInstrumentModeId(),instrumentMaster.getInstrumentMode(),instrumentMaster.getInstrumentType());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblInstrumentMaster");
                    }

                    //table 28-------------------------------

                    dbengine.Delete_tblStockUploadedStatus();

                    List<TblStockUploadedStatus> tblStockUploadedStatus=  allMasterTablesModel.getTblStockUploadedStatus();

                    if(tblStockUploadedStatus.size()>0){
                        for(TblStockUploadedStatus StockUploadedStatus:tblStockUploadedStatus){
                            dbengine.inserttblStockUploadedStatus(StockUploadedStatus.getFlgStockTrans(),StockUploadedStatus.getVanLoadUnLoadCycID(),StockUploadedStatus.getCycleTime(),StockUploadedStatus.getStatusID(),StockUploadedStatus.getFlgDayEnd());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblStockUploadedStatus");
                    }

                    //table 29-------------------------------

                    dbengine.deleteCompleteDataDistStock();

                    List<TblCycleID> tblCycleID=  allMasterTablesModel.getTblCycleID();
                    if(CommonInfo.flgDrctslsIndrctSls==1) {
                        if (tblCycleID.size() > 0) {
                            for (TblCycleID CycleID : tblCycleID) {
                                dbengine.insertCycleId(CycleID.getCycleID(), CycleID.getCycStartTime(), CycleID.getCycleTime());
                            }
                        } else {
                            blankTablearrayList.add("tblCycleID");
                        }
                    }

                    //table 30-------------------------------
                    //deleted above
                    List<TblDistributorStockOutFlg> tblDistributorStockOutFlg=  allMasterTablesModel.getTblDistributorStockOutFlg();

                    if(tblDistributorStockOutFlg.size()>0){
                        for(TblDistributorStockOutFlg DistributorStockOutFlg:tblDistributorStockOutFlg){
                            dbengine.insertStockOut(DistributorStockOutFlg.getFlgStockOutEntryDone());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblDistributorStockOutFlg");
                    }

                    //table 31-------------------------------
                    dbengine.Delete_tblAppMasterFlags();
                    List<TblAppMasterFlags> tblAppMasterFlags=  allMasterTablesModel.getTblAppMasterFlags();

                    if(tblAppMasterFlags.size()>0){
                        for(TblAppMasterFlags AppMasterFlags:tblAppMasterFlags){
                            dbengine.saveAppMasterFlags(AppMasterFlags.getFlgShowSalesTargetValue(),AppMasterFlags.getFlgShowDistributorStock(),AppMasterFlags.getFlgShowInvoice(),AppMasterFlags.getFlgShowPOSM(),AppMasterFlags.getFlgShowPaymentStageAtLastVisitPage(),AppMasterFlags.getFlgShowDeliveryAddressButtonOnOrder(),AppMasterFlags.getFlgShowManagerOnStoreList(),AppMasterFlags.getFlgShowTragetVsAchived(),AppMasterFlags.getFlgFilterProductOnCategoryOrSearchBasis(),AppMasterFlags.getFlgNeedStock(),AppMasterFlags.getFlgCalculateStock(),AppMasterFlags.getFlgControlStock(),AppMasterFlags.getFlgManageCollection(),AppMasterFlags.getFlgControlCollection(),AppMasterFlags.getFlgManageScheme(),AppMasterFlags.getFlgManageSalesQuotation(),AppMasterFlags.getFlgManageExecution());
                        }
                        CommonInfo.hmapAppMasterFlags=dbengine.fnGetAppMasterFlags();
                    }
                    else{
                        blankTablearrayList.add("tblAppMasterFlags");
                    }

                    List<TblDistributorProductStock> tblDistributorProductStock=  allMasterTablesModel.getTblDistributorProductStock();

                    if(tblDistributorProductStock.size()>0){
                        if(CommonInfo.flgDrctslsIndrctSls==1) {
                            dbengine.insertDistributorStock(tblDistributorProductStock);
                            if(CommonInfo.hmapAppMasterFlags.get("flgNeedStock")==1 && CommonInfo.hmapAppMasterFlags.get("flgCalculateStock")==1 ) {
                                int statusId = dbengine.confirmedStock();
                                if (statusId == 2) {
                                    dbengine.insertConfirmWArehouse(tblDistributorProductStock.get(0).getCustomer(), "1");
                                    dbengine.inserttblDayCheckIn(1);
                                }
                            }
                        }
                        else
                        {
                            dbengine.insertDistributorStockPermanetTableDirectly(tblDistributorProductStock);
                        }

                    }
                    else{
                        blankTablearrayList.add("tblDistributorProductStock");
                    }
                    //deleted above
                    List<TblDistributorIDOrderIDLeft> tblDistributorIDOrderIDLeft=  allMasterTablesModel.getTblDistributorIDOrderIDLeft();

                    if(tblDistributorIDOrderIDLeft.size()>0){
                        for(TblDistributorIDOrderIDLeft DistributorIDOrderIDLeft:tblDistributorIDOrderIDLeft){
                            dbengine.insertDistributorLeftOrderId(DistributorIDOrderIDLeft.getCustomer(),DistributorIDOrderIDLeft.getPDAOrderId(),DistributorIDOrderIDLeft.getFlgInvExists());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblDistributorIDOrderIDLeft");
                    }
                    //table 32-------------------------------
                    dbengine.Delete_tblInvoiceCaption();
                    List<TblInvoiceCaption> tblInvoiceCaption=  allMasterTablesModel.getTblInvoiceCaption();

                    if(tblInvoiceCaption.size()>0){
                        for(TblInvoiceCaption InvoiceCaption:tblInvoiceCaption){
                            dbengine.savetblInvoiceCaption(InvoiceCaption.getInvPrefix(),InvoiceCaption.getVanIntialInvoiceIds(),InvoiceCaption.getInvSuffix());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblInvoiceCaption");
                    }

                    //table 33-------------------------------
                    dbengine.Delete_tblGetReturnsReasonForPDAMstr();
                    List<TblGetReturnsReasonForPDAMstr> tblGetReturnsReasonForPDAMstr=  allMasterTablesModel.getTblGetReturnsReasonForPDAMstr();

                    if(tblGetReturnsReasonForPDAMstr.size()>0){
                        for(TblGetReturnsReasonForPDAMstr GetReturnsReasonForPDAMstr:tblGetReturnsReasonForPDAMstr){
                            dbengine.fnInsertTBLReturnRsn(GetReturnsReasonForPDAMstr.getStockStatusId(),GetReturnsReasonForPDAMstr.getStockStatus());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblGetReturnsReasonForPDAMstr");
                    }
                    //table 34-------------------------------
                    dbengine.Delete_tblGetReturnsReasonForPDAMstr();
                    List<TblIsSchemeApplicable> tblIsSchemeApplicable=  allMasterTablesModel.getTblIsSchemeApplicable();

                    if(tblIsSchemeApplicable.size()>0){
                        for(TblIsSchemeApplicable IsSchemeApplicable:tblIsSchemeApplicable){
                            dbengine.SavePDAIsSchemeApplicable(IsSchemeApplicable.getIsSchemeApplicable());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblIsSchemeApplicable");
                    }
                    //table 35-------------------------------

                    //table 36-------------------------------
                    dbengine.Delete_tblSupplierMstrList();
                    List<TblSupplierMstrList> tblSupplierMstrList=  allMasterTablesModel.getTblSupplierMstrList();

                    if(tblSupplierMstrList.size()>0){
                        for(TblSupplierMstrList SupplierMstrList:tblSupplierMstrList){
                            dbengine.saveSuplierMstrData(SupplierMstrList.getNodeID(),SupplierMstrList.getNodeType(),SupplierMstrList.getDescr(),SupplierMstrList.getLatCode(),SupplierMstrList.getLongCode(),SupplierMstrList.getFlgMapped(),SupplierMstrList.getAddress(),SupplierMstrList.getState(),SupplierMstrList.getCity(),SupplierMstrList.getPinCode(),SupplierMstrList.getPhoneNo(),SupplierMstrList.getTaxNumber(),SupplierMstrList.getEMailID(),SupplierMstrList.getFlgStockManage(),SupplierMstrList.getFlgDefault());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblSupplierMstrList");
                    }
                    //Nitish--------------------------------------------------

                    //table 51-------------------------------
                    dbengine.Delete_tblLastOutstanding_for_refreshData();
                    //deleted above
                    List<TblLastOutstanding> tblLastOutstanding=  allMasterTablesModel.getTblLastOutstanding();

                    if(tblLastOutstanding.size()>0){

                        dbengine.savetblLastOutstanding(tblLastOutstanding);

                    }
                    else{
                        blankTablearrayList.add("tblLastOutstanding");
                    }
                    //table 50-------------------------------
                    List<TblInvoiceLastVisitDetails> tblInvoiceLastVisitDetails=  allMasterTablesModel.getTblInvoiceLastVisitDetails();

                    if(tblInvoiceLastVisitDetails.size()>0){

                        dbengine.savetblInvoiceLastVisitDetails(tblInvoiceLastVisitDetails);

                    }
                    else{
                        blankTablearrayList.add("tblInvoiceLastVisitDetails");
                    }


                    //table 49-------------------------------
                    dbengine.deltblPDAGetExecutionSummary();
                    //deleted above
                    List<TblPDAGetExecutionSummary> tblPDAGetExecutionSummary=  allMasterTablesModel.getTblPDAGetExecutionSummary();

                    if(tblPDAGetExecutionSummary.size()>0){

                        dbengine.inserttblForPDAGetExecutionSummary(tblPDAGetExecutionSummary);

                    }
                    else{
                        blankTablearrayList.add("tblPDAGetExecutionSummary");
                    }


                    //table 48-------------------------------
                    dbengine.deletetblPDAGetLastOrderDetailsTotalValues();
                    //deleted above
                    List<TblPDAGetLastOrderDetailsTotalValues> tblPDAGetLastOrderDetailsTotalValues=  allMasterTablesModel.getTblPDAGetLastOrderDetailsTotalValues();

                    if(tblPDAGetLastOrderDetailsTotalValues.size()>0){

                        dbengine.inserttblspForPDAGetLastOrderDetails_TotalValues(tblPDAGetLastOrderDetailsTotalValues);

                    }
                    else{
                        blankTablearrayList.add("tblPDAGetLastOrderDetailsTotalValues");
                    }


                    //table 47-------------------------------
                    dbengine.deltblPDAGetLastOrderDetailsData();
                    //deleted above
                    List<TblPDAGetLastOrderDetails> tblPDAGetLastOrderDetails=  allMasterTablesModel.getTblPDAGetLastOrderDetails();

                    if(tblPDAGetLastOrderDetails.size()>0){

                        dbengine.inserttblForPDAGetLastOrderDetails(tblPDAGetLastOrderDetails);

                    }
                    else{
                        blankTablearrayList.add("tblPDAGetLastOrderDetails");
                    }

                    //table 46-------------------------------
                    dbengine.deletetblPDAGetLastVisitDetails();
                    //deleted above
                    List<TblPDAGetLastVisitDetails> tblPDAGetLastVisitDetails=  allMasterTablesModel.getTblPDAGetLastVisitDetails();

                    if(tblPDAGetLastVisitDetails.size()>0){

                        dbengine.inserttblForPDAGetLastVisitDetails(tblPDAGetLastVisitDetails);

                    }
                    else{
                        blankTablearrayList.add("tblPDAGetLastVisitDetails");
                    }


                    //table 45-------------------------------
                    dbengine.deletetblPDAGetLastOrderDateData();
                    //deleted above
                    List<TblPDAGetLastOrderDate> tblPDAGetLastOrderDate=  allMasterTablesModel.getTblPDAGetLastOrderDate();

                    if(tblPDAGetLastOrderDate.size()>0){

                        dbengine.inserttblForPDAGetLastOrderDate(tblPDAGetLastOrderDate);

                    }
                    else{
                        blankTablearrayList.add("tblPDAGetLastOrderDate");
                    }
                    //table 44-------------------------------
                    dbengine.deletetblPDAGetLastVisitDate();
                    //deleted above
                    List<TblPDAGetLastVisitDate> tblPDAGetLastVisitDate=  allMasterTablesModel.getTblPDAGetLastVisitDate();

                    if(tblPDAGetLastVisitDate.size()>0){

                        dbengine.inserttblForPDAGetLastVisitDate(tblPDAGetLastVisitDate);

                    }
                    else{
                        blankTablearrayList.add("tblPDAGetLastVisitDate");
                    }
                    //table 43-------------------------------
                    dbengine.deletetblPDAGetLODQty();
                    //deleted above
                    List<TblPDAGetLODQty> tblPDAGetLODQty=  allMasterTablesModel.getTblPDAGetLODQty();

                    if(tblPDAGetLODQty.size()>0){

                        dbengine.inserttblLODOnLastSalesSummary(tblPDAGetLODQty);

                    }
                    else{
                        blankTablearrayList.add("tblPDAGetLODQty");
                    }
                    //table 42-------------------------------
                    dbengine.deletetblProductListLastVisitStockOrOrderMstr();
                    //deleted above
                    List<TblProductListLastVisitStockOrOrderMstr> tblProductListLastVisitStockOrOrderMstr=  allMasterTablesModel.getTblProductListLastVisitStockOrOrderMstr();

                    if(tblProductListLastVisitStockOrOrderMstr.size()>0){

                        dbengine.savetblProductListLastVisitStockOrOrderMstr(tblProductListLastVisitStockOrOrderMstr);

                    }
                    else{
                        blankTablearrayList.add("tblProductListLastVisitStockOrOrderMstr");
                    }


                    //table 41-------------------------------
                    HashMap<String, String> hmapStoreIdSstat=new HashMap<String, String>();
                    hmapStoreIdSstat=dbengine.checkForStoreIdSstat();
                    HashMap<String, String> hmapflgOrderType=new HashMap<String, String>();
                    hmapflgOrderType=dbengine.checkForStoreflgOrderType();
                    dbengine.Delete_tblStore_for_refreshDataButNotNewStore();
                    dbengine.fndeleteStoreAddressMapDetailsMstr();
                    //deleted above
                    List<TblStoreListMaster> tblStoreListMaster=  allMasterTablesModel.getTblStoreListMaster();

                    if(tblStoreListMaster.size()>0){


                        dbengine.saveSOAPdataStoreList(tblStoreListMaster,hmapStoreIdSstat,hmapflgOrderType);

                    }
                    else{
                        blankTablearrayList.add("tblStoreListMaster");
                    }
                    if(hmapStoreIdSstat!=null && hmapStoreIdSstat.size()>0) {
                        hmapStoreIdSstat.clear();
                        hmapStoreIdSstat=null;
                    }

                    //table 40-------------------------------

                    //deleted above
                    List<TblStoreListWithPaymentAddress> tblStoreListWithPaymentAddress=  allMasterTablesModel.getTblStoreListWithPaymentAddress();

                    if(tblStoreListWithPaymentAddress.size()>0){

                        dbengine.saveSOAPdataStoreListAddressMap(tblStoreListWithPaymentAddress);

                    }
                    else{
                        blankTablearrayList.add("tblStoreListWithPaymentAddressMR");
                    }



                    //table 40-------------------------------

                    //deleted above
                    List<TblStoreSomeProdQuotePriceMstr> tblStoreSomeProdQuotePriceMstr=  allMasterTablesModel.getTblStoreSomeProdQuotePriceMstr();

                    if(tblStoreSomeProdQuotePriceMstr.size()>0){

                        dbengine.insertMinDelQty(tblStoreSomeProdQuotePriceMstr);

                    }
                    else{
                        blankTablearrayList.add("tblStoreSomeProdQuotePriceMstr");
                    }

                    //table 39-------------------------------

                    //deleted above
                    List<TblStoreLastDeliveryNoteNumber> tblStoreLastDeliveryNoteNumber=  allMasterTablesModel.getTblStoreLastDeliveryNoteNumber();

                    if(tblStoreLastDeliveryNoteNumber.size()>0){

                        for(TblStoreLastDeliveryNoteNumber tblStoreLastDeliveryNoteNumberData:tblStoreLastDeliveryNoteNumber)
                        {
                            int LastDeliveryNoteNumber=0;
                            LastDeliveryNoteNumber=tblStoreLastDeliveryNoteNumberData.getLastDeliveryNoteNumber();
                            int valExistingDeliveryNoteNumber=0;
                            valExistingDeliveryNoteNumber=dbengine.fnGettblStoreLastDeliveryNoteNumber();
                            if(valExistingDeliveryNoteNumber<LastDeliveryNoteNumber) {
                                dbengine.Delete_tblStoreLastDeliveryNoteNumber();
                                dbengine.savetblStoreLastDeliveryNoteNumber(LastDeliveryNoteNumber);
                            }
                        }


                    }
                    else{
                        blankTablearrayList.add("tblStoreLastDeliveryNoteNumber");
                    }



                    HashMap<String,String> hmapInvoiceOrderIDandStatus=new HashMap<String, String>();
                    hmapInvoiceOrderIDandStatus=dbengine.fetchHmapInvoiceOrderIDandStatus();
                    List<TblPendingInvoices> tblPendingInvoices=  allMasterTablesModel.getTblPendingInvoices();

                    if(tblPendingInvoices.size()>0){

                            dbengine.inserttblPendingInvoices(tblPendingInvoices,hmapInvoiceOrderIDandStatus);

                    }
                    else{
                        blankTablearrayList.add("tblPendingInvoices");
                    }






                    List<TblInvoiceExecutionProductList> tblInvoiceExecutionProductList=  allMasterTablesModel.getTblInvoiceExecutionProductList();
                    dbengine.fnDeletetblInvoiceExecutionProductList();
                    if(tblInvoiceExecutionProductList.size()>0){

                        dbengine.inserttblInvoiceExecutionProductList(tblInvoiceExecutionProductList);

                    }
                    else{
                        blankTablearrayList.add("tblInvoiceExecutionProductList");
                    }


                    List<TblProductWiseInvoice> tblProductWiseInvoice=  allMasterTablesModel.getTblProductWiseInvoice();
                    if(tblProductWiseInvoice.size()>0){

                        dbengine.inserttblProductWiseInvoice(tblProductWiseInvoice,hmapInvoiceOrderIDandStatus);

                    }
                    else{
                        blankTablearrayList.add("tblProductWiseInvoice");
                    }

                    dbengine.fnDeleteUnWantedSubmitedInvoiceOrders();

                    hmapInvoiceOrderIDandStatus=null;
                    dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(1);
                    mProgressDialog.dismiss();
                    interfaceRetrofit.success();
                   // sendIntentToOtherActivityAfterAllDataFetched();

                }
                else{
                    mProgressDialog.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllMasterTablesModel> call, Throwable t) {
                System.out.println();
                dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(0);
                mProgressDialog.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });



    }




    public static void getAllSummaryReportData(Context context, final String imei, String RegistrationID,String msgToShow){
        final  PRJDatabase dbengine = new PRJDatabase(context);
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle(msgToShow);//context.getResources().getString(R.string.Loading));
        mProgressDialog.setMessage(context.getResources().getString(R.string.RetrivingDataMsg));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList=new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = sdf.format(date1).toString().trim();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        String PersonNodeIdAndNodeType= dbengine.fngetSalesPersonMstrData();

        int PersonNodeId=0;

        int PersonNodeType=0;
        if(!PersonNodeIdAndNodeType.equals("0^0")) {
            PersonNodeId = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            PersonNodeType = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        }

        String prsnCvrgId_NdTyp=  dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String  CoverageNodeId= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String   CoverageNodeType= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData=1;
        String  serverDateForSPref=	dbengine.fnGetServerDate();

        ReportsInfo reportsInfo=new ReportsInfo();
        reportsInfo.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsInfo.setIMEINo(imei);
        reportsInfo.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsInfo.setForDate(fDate);
        reportsInfo.setSalesmanNodeId(PersonNodeId);
        reportsInfo.setSalesmanNodeType(PersonNodeType);
        reportsInfo.setFlgDataScope(0);

        Call<AllSummaryReportDay> call= apiService.Call_AllSummaryReportDay(reportsInfo);
        call.enqueue(new Callback<AllSummaryReportDay>() {
            @Override
            public void onResponse(Call<AllSummaryReportDay> call, Response<AllSummaryReportDay> response) {
                if(response.code()==200){
                    AllSummaryReportDay allSummaryReportDayModel=  response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    dbengine.truncateAllSummaryDayDataTable();
                    List<TblAllSummaryDay> tblAllSummaryDay=  allSummaryReportDayModel.getTblAllSummaryDay();
                    if(tblAllSummaryDay.size()>0){
                        dbengine.savetblAllSummaryDayAndMTD(tblAllSummaryDay);
                    }
                    else{
                        blankTablearrayList.add("tblAllSummaryDay");
                    }
                    mProgressDialog.dismiss();
                    interfaceRetrofit.success();
                }
                else{
                    mProgressDialog.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllSummaryReportDay> call, Throwable t) {
                System.out.println();
                mProgressDialog.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });



    }




    public static void getAllSKUWiseMTDSummaryReport(Context context, final String imei, String RegistrationID,String msgToShow){
        final  PRJDatabase dbengine = new PRJDatabase(context);
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle(msgToShow);//context.getResources().getString(R.string.Loading));
        mProgressDialog.setMessage(context.getResources().getString(R.string.RetrivingDataMsg));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList=new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = sdf.format(date1).toString().trim();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        String PersonNodeIdAndNodeType= dbengine.fngetSalesPersonMstrData();

        int PersonNodeId=0;

        int PersonNodeType=0;
        if(!PersonNodeIdAndNodeType.equals("0^0")) {
            PersonNodeId = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            PersonNodeType = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        }

        String prsnCvrgId_NdTyp=  dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String  CoverageNodeId= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String   CoverageNodeType= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData=1;
        String  serverDateForSPref=	dbengine.fnGetServerDate();

        ReportsInfo reportsInfo=new ReportsInfo();
        reportsInfo.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsInfo.setIMEINo(imei);
        reportsInfo.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsInfo.setForDate(fDate);
        reportsInfo.setSalesmanNodeId(PersonNodeId);
        reportsInfo.setSalesmanNodeType(PersonNodeType);
        reportsInfo.setFlgDataScope(0);

        Call<AllSummarySKUWiseDay> call= apiService.Call_AllSummarySKUWiseMTDDay(reportsInfo);
        call.enqueue(new Callback<AllSummarySKUWiseDay>() {
            @Override
            public void onResponse(Call<AllSummarySKUWiseDay> call, Response<AllSummarySKUWiseDay> response) {
                if(response.code()==200){
                    AllSummarySKUWiseDay allSummarySKUWiseDayModel=  response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    dbengine.truncateSKUDataTable();
                    List<TblSKUWiseDaySummary> tblSKUWiseDaySummary=  allSummarySKUWiseDayModel.getTblSKUWiseDaySummary();
                    if(tblSKUWiseDaySummary.size()>0){
                        dbengine.savetblSKUWiseDaySummary(tblSKUWiseDaySummary);
                    }
                    else{
                        blankTablearrayList.add("tblSKUWiseDaySummary");
                    }
                    mProgressDialog.dismiss();
                    interfaceRetrofit.success();
                }
                else{
                    mProgressDialog.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllSummarySKUWiseDay> call, Throwable t) {
                System.out.println();
                mProgressDialog.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });



    }



    public static void getAllStoreWiseMTDSummaryReport(Context context, final String imei, String RegistrationID,String msgToShow){
        final  PRJDatabase dbengine = new PRJDatabase(context);
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle(msgToShow);//context.getResources().getString(R.string.Loading));
        mProgressDialog.setMessage(context.getResources().getString(R.string.RetrivingDataMsg));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList=new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = sdf.format(date1).toString().trim();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        String PersonNodeIdAndNodeType= dbengine.fngetSalesPersonMstrData();

        int PersonNodeId=0;

        int PersonNodeType=0;
        if(!PersonNodeIdAndNodeType.equals("0^0")) {
            PersonNodeId = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            PersonNodeType = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        }

        String prsnCvrgId_NdTyp=  dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String  CoverageNodeId= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String   CoverageNodeType= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData=1;
        String  serverDateForSPref=	dbengine.fnGetServerDate();

        ReportsInfo reportsInfo=new ReportsInfo();
        reportsInfo.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsInfo.setIMEINo(imei);
        reportsInfo.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsInfo.setForDate(fDate);
        reportsInfo.setSalesmanNodeId(PersonNodeId);
        reportsInfo.setSalesmanNodeType(PersonNodeType);
        reportsInfo.setFlgDataScope(0);

        Call<AllSummaryStoreWiseDay> call= apiService.Call_AllSummaryStoreWiseMTDDay(reportsInfo);
        call.enqueue(new Callback<AllSummaryStoreWiseDay>() {
            @Override
            public void onResponse(Call<AllSummaryStoreWiseDay> call, Response<AllSummaryStoreWiseDay> response) {
                if(response.code()==200){
                    AllSummaryStoreWiseDay allSummaryStoreWiseDayModel=  response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    dbengine.truncateStoreWiseDataTable();
                    List<TblStoreWiseDaySummary> tblStoreWiseDaySummary=  allSummaryStoreWiseDayModel.getTblStoreWiseDaySummary();
                    if(tblStoreWiseDaySummary.size()>0){
                        dbengine.savetblStoreWiseDaySummary(tblStoreWiseDaySummary);
                    }
                    else{
                        blankTablearrayList.add("tblSKUWiseDaySummary");
                    }
                    mProgressDialog.dismiss();
                    interfaceRetrofit.success();
                }
                else{
                    mProgressDialog.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllSummaryStoreWiseDay> call, Throwable t) {
                System.out.println();
                mProgressDialog.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });



    }


    public static void getAllStoreSKUWiseMTDSummaryReport(Context context, final String imei, String RegistrationID,String msgToShow){
        final  PRJDatabase dbengine = new PRJDatabase(context);
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle(msgToShow);//context.getResources().getString(R.string.Loading));
        mProgressDialog.setMessage(context.getResources().getString(R.string.RetrivingDataMsg));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList=new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = sdf.format(date1).toString().trim();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        String PersonNodeIdAndNodeType= dbengine.fngetSalesPersonMstrData();

        int PersonNodeId=0;

        int PersonNodeType=0;
        if(!PersonNodeIdAndNodeType.equals("0^0")) {
            PersonNodeId = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            PersonNodeType = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        }

        String prsnCvrgId_NdTyp=  dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String  CoverageNodeId= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String   CoverageNodeType= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData=1;
        String  serverDateForSPref=	dbengine.fnGetServerDate();

        ReportsInfo reportsInfo=new ReportsInfo();
        reportsInfo.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsInfo.setIMEINo(imei);
        reportsInfo.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsInfo.setForDate(fDate);
        reportsInfo.setSalesmanNodeId(PersonNodeId);
        reportsInfo.setSalesmanNodeType(PersonNodeType);
        reportsInfo.setFlgDataScope(0);

        Call<AllSummaryStoreSKUWiseDay> call= apiService.Call_AllSummaryStoreSKUWiseMTDDay(reportsInfo);
        call.enqueue(new Callback<AllSummaryStoreSKUWiseDay>() {
            @Override
            public void onResponse(Call<AllSummaryStoreSKUWiseDay> call, Response<AllSummaryStoreSKUWiseDay> response) {
                if(response.code()==200){
                    AllSummaryStoreSKUWiseDay allSummaryStoreSKUWiseDayModel=  response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    dbengine.truncateStoreAndSKUWiseDataTable();
                    List<TblStoreSKUWiseDaySummary> tblStoreSKUWiseDaySummary=  allSummaryStoreSKUWiseDayModel.getTblStoreSKUWiseDaySummary();
                    if(tblStoreSKUWiseDaySummary.size()>0){
                        dbengine.savetblStoreSKUWiseDaySummary(tblStoreSKUWiseDaySummary);
                    }
                    else{
                        blankTablearrayList.add("tblStoreSKUWiseDaySummary");
                    }
                    mProgressDialog.dismiss();
                    interfaceRetrofit.success();
                }
                else{
                    mProgressDialog.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllSummaryStoreSKUWiseDay> call, Throwable t) {
                System.out.println();
                mProgressDialog.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });



    }

    public static void getAllTargetVsAcheivedData(Context context, final String imei, String RegistrationID,String msgToShow){
        final  PRJDatabase dbengine = new PRJDatabase(context);
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle(msgToShow);//context.getResources().getString(R.string.Loading));
        mProgressDialog.setMessage(context.getResources().getString(R.string.RetrivingDataMsg));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList=new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = sdf.format(date1).toString().trim();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp=  dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String  CoverageNodeId= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String   CoverageNodeType= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData=1;
        String  serverDateForSPref=	dbengine.fnGetServerDate();

        Data data=new Data();
        data.setApplicationTypeId(CommonInfo.Application_TypeID);
        data.setIMEINo(imei);
        data.setVersionId(CommonInfo.DATABASE_VERSIONID);
        data.setRegistrationId(RegistrationID);
        data.setForDate(fDate);
        data.setFlgAllRouteData(1);
        // data.setInvoiceList(null);
        data.setRouteNodeId(0);
        data.setRouteNodeType(0);
        data.setCoverageAreaNodeId(Integer.parseInt(CoverageNodeId));
        data.setCoverageAreaNodeType(Integer.parseInt(CoverageNodeType));

        Call<AllTargetVsAchieved> call= apiService.Call_AllTargetVsAchieved(data);
        call.enqueue(new Callback<AllTargetVsAchieved>() {
            @Override
            public void onResponse(Call<AllTargetVsAchieved> call, Response<AllTargetVsAchieved> response) {
                if(response.code()==200){
                    AllTargetVsAchieved allTargetVsAchievedModel=  response.body();


                    dbengine.truncatetblTargetVsAchievedSummary();

                    List<TblActualVsTargetReport> tblActualVsTargetReport=  allTargetVsAchievedModel.getTblActualVsTargetReport();

                    if(tblActualVsTargetReport.size()>0){
                        dbengine.savetblTargetVsAchievedSummary(tblActualVsTargetReport);

                    }
                    else{
                        blankTablearrayList.add("tblActualVsTargetReport");
                    }

                    //table 29-------------------------------

                    List<TblValueVolumeTarget> tblValueVolumeTarget=  allTargetVsAchievedModel.getTblValueVolumeTarget();

                    if(tblValueVolumeTarget.size()>0){
                        dbengine.saveValueVolumeTarget(tblValueVolumeTarget);

                    }
                    else{
                        blankTablearrayList.add("tblValueVolumeTarget");
                    }

                    List<TblActualVsTargetNote> tblActualVsTargetNote=  allTargetVsAchievedModel.getTblActualVsTargetNote();

                    if(tblValueVolumeTarget.size()>0){
                        for(TblActualVsTargetNote tblActualVsTargetNoteData:tblActualVsTargetNote)
                        {
                            dbengine.savetblTargetVsAchievedNote(tblActualVsTargetNoteData.getMsgToDisplay());
                        }

                    }
                    else{
                        blankTablearrayList.add("tblActualVsTargetNote");
                    }



                    mProgressDialog.dismiss();
                    interfaceRetrofit.success();
                    // sendIntentToOtherActivityAfterAllDataFetched();

                }
                else{
                    mProgressDialog.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllTargetVsAchieved> call, Throwable t) {
                System.out.println();
                mProgressDialog.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });



    }

    public static void getAllAddedOutletSummaryReportModel(Context context, final String imei, String RegistrationID, String msgToShow, Integer flgDrillLevel){
        final  PRJDatabase dbengine = new PRJDatabase(context);
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle(msgToShow);//context.getResources().getString(R.string.Loading));
        mProgressDialog.setMessage(context.getResources().getString(R.string.RetrivingDataMsg));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList=new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = sdf.format(date1).toString().trim();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp=  dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String  CoverageNodeId= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String   CoverageNodeType= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData=1;
        String  serverDateForSPref=	dbengine.fnGetServerDate();

        ReportsAddedOutletSummary reportsAddedOutletSummary=new ReportsAddedOutletSummary();
        reportsAddedOutletSummary.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsAddedOutletSummary.setIMEINo(imei);
        reportsAddedOutletSummary.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsAddedOutletSummary.setFlgDrillLevel(flgDrillLevel);
        reportsAddedOutletSummary.setForDate(fDate);

        Call<AllAddedOutletSummaryReportModel> call= apiService.Call_AllPDAGetAddedOutletSummaryReport(reportsAddedOutletSummary);
        call.enqueue(new Callback<AllAddedOutletSummaryReportModel>() {
            @Override
            public void onResponse(Call<AllAddedOutletSummaryReportModel> call, Response<AllAddedOutletSummaryReportModel> response) {
                if(response.code()==200){
                    AllAddedOutletSummaryReportModel allAddedOutletSummaryReportModelModel=  response.body();


                    dbengine.droptblDAGetAddedOutletSummaryReport();
                    dbengine.createtblDAGetAddedOutletSummaryReport();

                    List<TblDAGetAddedOutletSummaryReport> tblDAGetAddedOutletSummaryReport=  allAddedOutletSummaryReportModelModel.getTblDAGetAddedOutletSummaryReport();

                    if(tblDAGetAddedOutletSummaryReport.size()>0){
                        dbengine.savetblDAGetAddedOutletSummaryReport(tblDAGetAddedOutletSummaryReport);

                    }
                    else{
                        blankTablearrayList.add("tblDAGetAddedOutletSummaryReport");
                    }

                    //table 29-------------------------------

                    List<TblDAGetAddedOutletSummaryOverallReport> tblDAGetAddedOutletSummaryOverallReport=  allAddedOutletSummaryReportModelModel.getTblDAGetAddedOutletSummaryOverallReport();

                    if(tblDAGetAddedOutletSummaryOverallReport.size()>0){
                        dbengine.savetblDAGetAddedOutletSummaryOverallReport(tblDAGetAddedOutletSummaryOverallReport);

                    }
                    else{
                        blankTablearrayList.add("tblDAGetAddedOutletSummaryOverallReport");
                    }


                    mProgressDialog.dismiss();
                    interfaceRetrofit.success();
                    // sendIntentToOtherActivityAfterAllDataFetched();

                }
                else{
                    mProgressDialog.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllAddedOutletSummaryReportModel> call, Throwable t) {
                System.out.println();
                mProgressDialog.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });



    }

}
