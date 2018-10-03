package project.astix.com.parasorder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.astix.Common.CommonInfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//import com.astix.sfatju.R;


public class LauncherActivity extends BaseActivity
{

	public  int click_but_distribtrStock=0;
	public Button but_distribtrStock;
	int CstmrNodeId=0,CstomrNodeType= 0;


	public static int RowId=0;
	
	public String ReasonId;
	public String ReasonText="NA";
	public  Button but_GetStore,but_NoStoreVisit;
	public ProgressDialog mProgressDialog;
	public String passDate;
	public String fDate;
	public String fDate2write;
	
	public String imei;
	public String rID;
	ImageView imgVw_logout;
	public int powerCheck=0;
	
	public  PowerManager.WakeLock wl;
	public String Noti_text="Null";
	public int MsgServerID=0;
	public SimpleDateFormat sdf;
	
	public Date currDate;
	public SimpleDateFormat currDateFormat;
	public String currSysDate;
	public String currPassedSysDate;
	
	public int chkFlgForErrorToCloseApp=0;
	public int IsSchemeApplicable=3;
	public String[] route_name;	
	public String[] route_name_id;
	
	public String selected_route_id="0";
	public  Button but_Invoice;
	public String myExistingPickerDate;
	
	//ProgressDialog pDialog2;
	public ProgressDialog pDialogGetStores;
	public Spinner routeDDL;
	public String srvcDone = "0";
	
	ArrayList<String> exCaseList = new ArrayList<String>();
	PRJDatabase dbengine = new PRJDatabase(this);
	//LocationManager newLM333;
	Date strDate;
	//	DatePicker selectedDate;
	
	
	private int selected = 0; // change selected to global 
	public TextView tv_Route;
	public String twoDigitDate;
	public String CompareDate="";
	
	public RadioButton TodaySelection;
	public RadioButton TomrSelection;
	
	public String temp_select_routename="NA";
	public String temp_select_routeid="NA";
	public ProgressBar progressBar;
	public boolean serviceException=false;
	
	
	LinkedHashMap<String, String> hmapReasonIdAndDescr_details=new LinkedHashMap<String, String>();
	
	
	 String[] reasonNames;
	public String newfullFileName;
	public String[] xmlForWeb = new String[1];
	int serverResponseCode = 0;
	public int syncFLAG = 0;

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();

	}

	public boolean onKeyDown(int keyCode, KeyEvent event)    // Control the PDA Native Button Handling
	{
		  // TODO Auto-generated method stub
		  if(keyCode==KeyEvent.KEYCODE_BACK)
		  {
			  return true;
			 // finish();
		  }
		  if(keyCode==KeyEvent.KEYCODE_HOME)
		  {
			 // finish();
			  
		  }
		  if(keyCode==KeyEvent.KEYCODE_MENU)
		  {
			  return true;
		  }
		  if(keyCode==KeyEvent.KEYCODE_SEARCH)
		  {
			  return true;
		  }

		  return super.onKeyDown(keyCode, event);
	}

	 @Override
	  public void onDestroy() 
	  {
	   super.onDestroy();
	  // wl.release();
	  }

	@Override
	protected void onCreate(Bundle savedInstanceState)
	 {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher_new);
		
		TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		imei = tManager.getDeviceId();
		
		
		if(CommonInfo.imei.trim().equals(null) || CommonInfo.imei.trim().equals(""))
		{
			imei = tManager.getDeviceId();
			CommonInfo.imei=imei;
		}
		else
		{
			imei= CommonInfo.imei.trim();
		}

		 if(dbengine.isDBOpen()==false)
		 {
			 dbengine.open();
		 }

		currDate = new Date();
		currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		
		currSysDate = currDateFormat.format(currDate).toString();
		


	    Date date1=new Date();
		sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		passDate = sdf.format(date1).toString();
		fDate = passDate.trim().toString();
		
		
		tv_Route=(TextView)findViewById(R.id.tv_Route);
		imgVw_logout=(ImageView) findViewById(R.id.imgVw_logout);
		
		imgVw_logout.setOnClickListener(new OnClickListener() 
		 {
			@Override
			public void onClick(View v) 

			{
//				imgVw_logout.setBackgroundResource(R.drawable.logout_new);
				imgVw_logout.setImageResource(R.drawable.logout_hover);
			}
		 });
		
		
		if(powerCheck==0)
		   {
		        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		        wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
		        wl.acquire();
		   }

		route_name = dbengine.fnGetRouteInfoForDDL();
		route_name_id = dbengine.fnGetRouteIdsForDDL();

		String strGetActiveRouteId=dbengine.GetActiveRouteID();
		if(route_name.length>0)
		{
			for(int i=0;i<route_name.length;i++)
			{
				if(route_name_id[i].equals(strGetActiveRouteId))
				{
					selected=i;
				}
			}
		tv_Route.setText(""+route_name[selected]);
		selected_route_id=route_name_id[selected];
		temp_select_routename=route_name[selected];
		}
		rID=selected_route_id;
		//tv_Route.setText(""+temp_select_routename);
		
		temp_select_routeid=selected_route_id;//=temp_select_routeid;
        rID=temp_select_routeid;
        
        
        
        
       currDate = new Date();
	   currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
	   currSysDate = currDateFormat.format(currDate).toString();
	   String newdateformattoday=fDate;
		
		
		TodaySelection = (RadioButton) findViewById(R.id.radio0);
		TomrSelection = (RadioButton) findViewById(R.id.radio1);
		TodaySelection.setText(getResources().getString(R.string.radioTodayRoute)+newdateformattoday);
		TomrSelection.setText(getResources().getString(R.string.radioChooseDifferentRoute));
		int checkValueForRadioButton=dbengine.GetActiveRouteIDForRadioButton();
		if(checkValueForRadioButton==0)
		{
			TodaySelection.setChecked(false);
			TomrSelection.setChecked(true);
		}
		but_NoStoreVisit = (Button) findViewById(R.id.but_NoStoreVisit);

		//dbengine.open();
		boolean DBCurrentName=dbengine.doesDatabaseExist(this, dbengine.DATABASE_NAME) ;
		//dbengine.close();
	
		
		
		
		
		if((DBCurrentName== true))
		{

			if(( true))
			{
			
				Intent trans2storeList = new Intent(LauncherActivity.this, StoreSelection.class);
				trans2storeList.putExtra("imei", imei);
				trans2storeList.putExtra("userDate", currSysDate);
				trans2storeList.putExtra("pickerDate", fDate);
				trans2storeList.putExtra("rID", rID);
				startActivity(trans2storeList);
				finish();
			
			}
		}
	}

	 
	 @Override
		protected void onStop()
		{
			super.onStop();
			
		}
	 
	@Override
	protected void onResume()
	{
		super.onResume();
		
		

		TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		imei = tManager.getDeviceId();
		
		if(CommonInfo.imei.trim().equals(null) || CommonInfo.imei.trim().equals(""))
		{
			imei = tManager.getDeviceId();
			CommonInfo.imei=imei;
		}
		else
		{
			imei= CommonInfo.imei.trim();
		}
		
		currDate = new Date();
		currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		
		currSysDate = currDateFormat.format(currDate).toString();
		
		
		
       //dbengine.open();
		
		//dbengine.maintainPDADate();
		route_name = dbengine.fnGetRouteInfoForDDL();
		route_name_id = dbengine.fnGetRouteIdsForDDL();
		
		String strGetActiveRouteId=dbengine.GetActiveRouteID();
		//dbengine.close();
		if(route_name.length>0)
		{
			for(int i=0;i<route_name.length;i++)
			{
				if(route_name_id[i].equals(strGetActiveRouteId))
				{
					selected=i;
				}
			}
		tv_Route.setText(""+route_name[selected]);
		selected_route_id=route_name_id[selected];
		temp_select_routename=route_name[selected];
		}
		rID=selected_route_id;
		//tv_Route.setText(""+temp_select_routename);
		
		temp_select_routeid=selected_route_id;//=temp_select_routeid;
        rID=temp_select_routeid;
		
		}

}
