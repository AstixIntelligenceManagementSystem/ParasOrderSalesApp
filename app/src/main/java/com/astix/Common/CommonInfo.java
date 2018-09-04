package com.astix.Common;

import android.net.Uri;
import java.io.File;

public class CommonInfo {
//firebaseID astix8  pass: astix1234


	public static String ActiveRouteSM = "0";
	public static int AnyVisit = 0;
	public static String AppLatLngJsonFile = "BalajiOrderSFALatLngJson";

	public static int Application_TypeID = 2;
	public static final String AttandancePreference = "BalajiOrderAttandancePreference";
	public static int CoverageAreaNodeID = 0;
	public static int CoverageAreaNodeType = 0;
	public static String DATABASE_NAME = "DbBalajiOrderSFAApp";
	public static int DATABASE_VERSIONID = 24;
	public static String AppVersionID = "1.8";
	public static int DayStartClick = 0;
	public static int DistanceRange = 3000;
	public static final String DistributorCheckInXMLFolder = "BalajiOrderDistributorCheckInXML";
	public static final String DistributorMapXMLFolder = "BalajiOrderDistributorMapXML";
	public static final String DistributorStockXMLFolder = "BalajiOrderDistributorStockXML";
	public static String DistributorSyncPath = "http://103.20.212.194/ReadXML_BalajiSFADistributionLive/Default.aspx";
	public static String FinalLatLngJsonFile = "BalajiOrderSFAFinalLatLngJson";
	public static int FlgDSRSO = 0;
	public static String ImageSyncPath = "http://103.20.212.194/ReadXML_BalajiImagesLive/Default.aspx";
	public static String ImagesFolder = "BalajiOrderSFAImages";
	public static String ImagesFolderServer = "BalajiOrderSFAImagesServer";
	public static String InvoiceSyncPath = "http://103.20.212.194/ReadXML_BalajiInvoiceLive/DefaultGT.aspx";
	public static String InvoiceXMLFolder = "BalajOrderInvoiceXml";
	public static String OrderSyncPath = "http://103.20.212.194/ReadXML_BalajiLive/DefaultGTSFA.aspx";
	public static String OrderSyncPathDistributorMap = "http://103.20.212.194/ReadXML_BalajiLive/DefaultSODistributorMappingGT.aspx";
	public static String OrderTextSyncPath = "http://103.20.212.194/ReadTxtFileForBalajiSFALive/default.aspx";
	public static String OrderXMLFolder = "BalajiOrderSFAXml";
	public static final String Preference = "BalajiOrderPrefrence";
	public static String SalesPersonTodaysTargetMsg = "";
	public static String SalesQuoteId = "BLANK";
	public static int SalesmanNodeId = 0;
	public static int SalesmanNodeType = 0;
	public static String TextFileFolder = "BalajiOrderTextFile";
	public static String VersionDownloadAPKName = "BalajiOrder.apk";
	public static String VersionDownloadPath = "http://103.20.212.194/downloads/";
	public static String WebServicePath = "http://103.20.212.194/WebServiceAndroidBalajiLive/Service.asmx";
	public static String WebStockInUrl = "http://103.20.212.194/Balaji/manageorder/frmstockin.aspx";
	public static String WebStockOutUrl = "http://103.20.212.194/Balaji/manageorder/frmStockTransferToVanDetail_PDA.aspx";
	public static String clickedTagPhoto_savedInstance = null;
	public static String fileContent = "";
	public static int flgAllRoutesData = 1;
	public static int flgDataScope = 0;
	public static String globalValueOfPaymentStage = "0_0_0";
	public static File imageF_savedInstance = null;
	public static String imageName_savedInstance = null;
	public static String imei = "";
	public static int VanLoadedUnloaded = 0;
	public static String newQuottionID = "NULL";
	public static String prcID = "NULL";
	public static String quatationFlag = "";
	public static String sPrefVanLoadedUnloaded = "VanLoadedUnloaded";
	public static Uri uriSavedImage_savedInstance = null;



	//Dev

	/*public static String ActiveRouteSM = "0";
	public static int AnyVisit = 0;
	public static String AppLatLngJsonFile = "BalajiOrderSFALatLngJson";

	public static int Application_TypeID = 2;
	public static final String AttandancePreference = "BalajiOrderAttandancePreference";
	public static int CoverageAreaNodeID = 0;
	public static int CoverageAreaNodeType = 0;
	public static String DATABASE_NAME = "DbBalajiOrderSFAApp";
	public static int DATABASE_VERSIONID = 22;
	public static String AppVersionID = "1.7";
	public static int DayStartClick = 0;
	public static int DistanceRange = 3000;
	public static final String DistributorCheckInXMLFolder = "BalajiOrderDistributorCheckInXML";
	public static final String DistributorMapXMLFolder = "BalajiOrderDistributorMapXML";
	public static final String DistributorStockXMLFolder = "BalajiOrderDistributorStockXML";
	public static String DistributorSyncPath = "http://103.20.212.194/ReadXML_BalajiSFADistributionDevelopment/Default.aspx";
	public static String FinalLatLngJsonFile = "BalajiOrderSFAFinalLatLngJson";
	public static int FlgDSRSO = 0;
	public static String ImageSyncPath = "http://103.20.212.194/ReadXML_BalajiImagesDevelopment/Default.aspx";
	public static String ImagesFolder = "BalajiOrderSFAImages";
	public static String ImagesFolderServer = "BalajiOrderSFAImagesServer";
	public static String InvoiceSyncPath = "http://103.20.212.194/ReadXML_BalajiInvoiceDevelopment/DefaultGT.aspx";
	public static String InvoiceXMLFolder = "BalajOrderInvoiceXml";
	public static String OrderSyncPath = "http://103.20.212.194/ReadXML_BalajiDevelopment/DefaultGTSFA.aspx";
	public static String OrderSyncPathDistributorMap = "http://103.20.212.194/ReadXML_BalajiDev/DefaultSODistributorMappingGT.aspx";
	public static String OrderTextSyncPath = "http://103.20.212.194/ReadTxtFileForBalajiSFADev/default.aspx";
	public static String OrderXMLFolder = "BalajiOrderSFAXml";
	public static final String Preference = "BalajiOrderPrefrence";
	public static String SalesPersonTodaysTargetMsg = "";
	public static String SalesQuoteId = "BLANK";
	public static int SalesmanNodeId = 0;
	public static int SalesmanNodeType = 0;
	public static String TextFileFolder = "BalajiOrderTextFile";
	public static String VersionDownloadAPKName = "BalajiOrder_Dev.apk";
	public static String VersionDownloadPath = "http://103.20.212.194/downloads/";
	public static String WebServicePath = "http://103.20.212.194/WebServiceAndroidBalajiDevelopment/Service.asmx";
	public static String WebStockInUrl = "http://103.20.212.194/Balajidev/manageorder/frmstockin.aspx";
	public static String WebStockOutUrl = "http://103.20.212.194/Balajidev/manageorder/frmStockTransferToVanDetail_PDA.aspx";
	public static String clickedTagPhoto_savedInstance = null;
	public static String fileContent = "";
	public static int flgAllRoutesData = 1;
	public static int flgDataScope = 0;
	public static String globalValueOfPaymentStage = "0_0_0";
	public static File imageF_savedInstance = null;
	public static String imageName_savedInstance = null;
	public static String imei = "";
	public static int VanLoadedUnloaded = 0;
	public static String newQuottionID = "NULL";
	public static String prcID = "NULL";
	public static String quatationFlag = "";
	public static String sPrefVanLoadedUnloaded = "VanLoadedUnloaded";
	public static Uri uriSavedImage_savedInstance = null;*/
}