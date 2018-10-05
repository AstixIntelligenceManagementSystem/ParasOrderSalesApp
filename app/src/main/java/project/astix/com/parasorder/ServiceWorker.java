package project.astix.com.parasorder;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Environment;

import com.astix.Common.CommonInfo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class ServiceWorker 
{
	SharedPreferences sharedPref;
	
	public static int flagExecutedServiceSuccesfully=0;
	public int chkTblStoreListContainsRow=1;
	
    //Live Path WebServiceAndroidParagSFATesting
	//public String UrlForWebService="http://115.124.126.184/WebServiceAndroidParagSFA/Service.asmx";
	
	//Testing Path
	//public String UrlForWebService="http://115.124.126.184/WebServiceAndroidParagSFATesting/Service.asmx";
	public String UrlForWebService= CommonInfo.WebServicePath.trim();
	//int counts;
	public String currSysDate;
	public String SysDate;
	public int newStat = 0;
	public int timeout=0;
	Locale locale  = new Locale("en", "UK");
	String pattern = "###.##";
	DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale);
	String movie_name;
	String director;
	String exceptionCode;
	private Context context;
	//private ServiceWorker _activity;
	private ContextWrapper cw;

	public ServiceWorker callfnSingleCallAllWebService(Context ctx,int ApplicationID,String uuid) 
	{
		this.context = ctx;
		PRJDatabase dbengine = new PRJDatabase(context);
		
		int chkTblStoreListContainsRow=1;
		StringReader read;
		InputSource inputstream;
		final String SOAP_ACTION = "http://tempuri.org/fnSingleCallAllStoreMappingMeijiTT";
		final String METHOD_NAME = "fnSingleCallAllStoreMappingMeijiTT";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;
		
	    //Create request
		SoapObject table = null; //Contains table of dataset that returned
		// through SoapObject
		SoapObject client = null; //Its the client petition to the web service
		SoapObject tableRow = null; //Contains row of table
		SoapObject responseBody = null; //Contains XML content of dataset

		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;
		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		sse.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,0);
		
		ServiceWorker setmovie = new ServiceWorker();
		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);
			client.addProperty("ApplicationID", ApplicationID);
			client.addProperty("uuid", uuid.toString());
			
			sse.setOutputSoapObject(client);
			sse.bodyOut = client;
			androidHttpTransport.call(SOAP_ACTION, sse);
			responseBody = (SoapObject)sse.bodyIn;
			// This step: get file XML
			//responseBody = (SoapObject) sse.getResponse();
			int totalCount = responseBody.getPropertyCount();

			// System.out.println("Kajol 2 :"+totalCount);
	        String resultString=androidHttpTransport.responseDump;
			
	        String name=responseBody.getProperty(0).toString();
	        
	       // System.out.println("Kajol 3 :"+name);
	        
	        XMLParser xmlParser = new XMLParser();
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder db = dbf.newDocumentBuilder();
	            InputSource is = new InputSource();
	            is.setCharacterStream(new StringReader(name));
	            Document doc = db.parse(is);
	            
	           
	            
	           dbengine.deleteAllSingleCallWebServiceTable();
	           //dbengine.open();
	          int  gblQuestIDForOutChannel=0;

			NodeList tblGetPDARsnRtrnMstr = doc.getElementsByTagName("tblGetReturnsReasonForPDAMstr");
			for (int i = 0; i < tblGetPDARsnRtrnMstr.getLength(); i++)
			{


				String stockStatusId="0";
				String stockStatus="0";








				Element element = (Element) tblGetPDARsnRtrnMstr.item(i);



				if(!element.getElementsByTagName("StockStatusId").equals(null))
				{
					NodeList QuestionIDNode = element.getElementsByTagName("StockStatusId");
					Element      line = (Element) QuestionIDNode.item(0);
					if(QuestionIDNode.getLength()>0)
					{
						stockStatusId=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("StockStatus").equals(null))
				{
					NodeList OptionIDNode = element.getElementsByTagName("StockStatus");
					Element      line = (Element) OptionIDNode.item(0);
					if(OptionIDNode.getLength()>0)
					{
						stockStatus=xmlParser.getCharacterDataFromElement(line);
					}
				}


			//	dbengine.fnInsertTBLReturnRsn(stockStatusId,stockStatus);
			}

			NodeList tblQuestIDForOutChannel = doc.getElementsByTagName("tblQuestIDForOutChannel");
			for (int i = 0; i < tblQuestIDForOutChannel.getLength(); i++)
			{
				int grpQuestId=0;
				int questId=0;
				String optId="0-0-0";
				int sectionCount=0;
				Element element = (Element) tblQuestIDForOutChannel.item(i);
				if(!element.getElementsByTagName("GrpQuestID").equals(null))
				{
					NodeList QuestIDForOutChannelNode = element.getElementsByTagName("GrpQuestID");
					Element     line = (Element) QuestIDForOutChannelNode.item(0);
					if(QuestIDForOutChannelNode.getLength()>0)
					{
						grpQuestId=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}
				if(!element.getElementsByTagName("QuestID").equals(null))
				{
					NodeList QuestIDNode = element.getElementsByTagName("QuestID");
					Element     line = (Element) QuestIDNode.item(0);
					if(QuestIDNode.getLength()>0)
					{
						questId=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
						gblQuestIDForOutChannel=questId;
					}
				}
				if(!element.getElementsByTagName("OptionID").equals(null))
				{
					NodeList optIDNode = element.getElementsByTagName("OptionID");
					Element     line = (Element) optIDNode.item(0);
					if(optIDNode.getLength()>0)
					{
						optId=xmlParser.getCharacterDataFromElement(line);

					}
				}
				if(!element.getElementsByTagName("SectionCount").equals(null))
				{
					NodeList SectionCountNode = element.getElementsByTagName("SectionCount");
					Element     line = (Element) SectionCountNode.item(0);
					if(SectionCountNode.getLength()>0)
					{
						sectionCount=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));

					}
				}
				dbengine.saveOutletChammetQstnIdGrpId(grpQuestId, questId,optId,sectionCount);
			}

			NodeList tblQuestIDForName = doc.getElementsByTagName("tblQuestIDForName");
			for (int i = 0; i < tblQuestIDForName.getLength(); i++)
			{
				int id=0;
				int grpQuestId=0;
				int questId=0;
				String questDesc="";
				Element element = (Element) tblQuestIDForName.item(i);
				if(!element.getElementsByTagName("ID").equals(null))
				{
					NodeList IDNode = element.getElementsByTagName("ID");
					Element     line = (Element) IDNode.item(0);
					if(IDNode.getLength()>0)
					{
						id=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}
				if(!element.getElementsByTagName("GrpQuestID").equals(null))
				{
					NodeList QuestIDForOutChannelNode = element.getElementsByTagName("GrpQuestID");
					Element     line = (Element) QuestIDForOutChannelNode.item(0);
					if(QuestIDForOutChannelNode.getLength()>0)
					{
						grpQuestId=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}
				if(!element.getElementsByTagName("QuestID").equals(null))
				{
					NodeList QuestIDNode = element.getElementsByTagName("QuestID");
					Element     line = (Element) QuestIDNode.item(0);
					if(QuestIDNode.getLength()>0)
					{
						questId=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));

					}
				}
				if(!element.getElementsByTagName("QuestDesc").equals(null))
				{
					NodeList QuestDescNode = element.getElementsByTagName("QuestDesc");
					Element     line = (Element) QuestDescNode.item(0);
					if(QuestDescNode.getLength()>0)
					{
						questDesc=xmlParser.getCharacterDataFromElement(line);

					}
				}
				dbengine.savetblQuestIDForName(id,grpQuestId, questId,questDesc);
			}
			NodeList tblSPGetDistributorDetailsNode = doc.getElementsByTagName("tblGetPDAQuestMstr");
			for (int i = 0; i < tblSPGetDistributorDetailsNode.getLength(); i++)
			{
				String QuestID="0";
				String QuestCode="0";
				String QuestDesc="0";
				String QuestType="0";

				String AnsControlType="0";
				String AsnControlInputTypeID="0";
				String AnsControlInputTypeMaxLength="0";
				String AnsControlInputTypeMinLength="0";
				String AnsMustRequiredFlg="0";
				String QuestBundleFlg="0";
				String ApplicationTypeID="0";
				String Sequence="0";
				String answerHint="N/A";

				String QuestDescHindi="0";


				int flgQuestIDForOutChannel=0;

				Element element = (Element) tblSPGetDistributorDetailsNode.item(i);




				if(!element.getElementsByTagName("QuestID").equals(null))
				{
					NodeList QuestIDNode = element.getElementsByTagName("QuestID");
					Element     line = (Element) QuestIDNode.item(0);
					if(QuestIDNode.getLength()>0)
					{
						QuestID=xmlParser.getCharacterDataFromElement(line);
						if(gblQuestIDForOutChannel==Integer.parseInt(QuestID))
						{
							flgQuestIDForOutChannel=1;
						}
					}
				}
				if(!element.getElementsByTagName("QuestCode").equals(null))
				{
					NodeList QuestCodeNode = element.getElementsByTagName("QuestCode");
					Element     line = (Element) QuestCodeNode.item(0);
					if(QuestCodeNode.getLength()>0)
					{
						QuestCode=xmlParser.getCharacterDataFromElement(line);
					}
				}



				if(!element.getElementsByTagName("QuestDesc").equals(null))
				{
					NodeList QuestDescNode = element.getElementsByTagName("QuestDesc");
					Element     line = (Element) QuestDescNode.item(0);
					if(QuestDescNode.getLength()>0)
					{
						QuestDesc=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("QuestType").equals(null))
				{
					NodeList QuesTypeNode = element.getElementsByTagName("QuestType");
					Element     line = (Element) QuesTypeNode.item(0);
					if(QuesTypeNode.getLength()>0)
					{
						QuestType=xmlParser.getCharacterDataFromElement(line);
					}
				}


				if(!element.getElementsByTagName("AnsControlType").equals(null))
				{
					NodeList AnsControlTypeNode = element.getElementsByTagName("AnsControlType");
					Element     line = (Element) AnsControlTypeNode.item(0);
					if(AnsControlTypeNode.getLength()>0)
					{
						AnsControlType=xmlParser.getCharacterDataFromElement(line);
					}
				}



				if(!element.getElementsByTagName("AsnControlInputTypeID").equals(null))
				{
					NodeList AsnControlInputTypeIDNode = element.getElementsByTagName("AsnControlInputTypeID");
					Element     line = (Element) AsnControlInputTypeIDNode.item(0);
					if(AsnControlInputTypeIDNode.getLength()>0)
					{
						AsnControlInputTypeID=xmlParser.getCharacterDataFromElement(line);
					}
				}



				if(!element.getElementsByTagName("AnsControlInputTypeMaxLength").equals(null))
				{
					NodeList AnsControlInputTypeMaxLengthNode = element.getElementsByTagName("AnsControlInputTypeMaxLength");
					Element      line = (Element) AnsControlInputTypeMaxLengthNode.item(0);
					if(AnsControlInputTypeMaxLengthNode.getLength()>0)
					{
						AnsControlInputTypeMaxLength=xmlParser.getCharacterDataFromElement(line);
					}
				}


				if(!element.getElementsByTagName("AnsControlIntputTypeMinLength").equals(null))
				{
					NodeList AnsControlInputTypeMinLengthNode = element.getElementsByTagName("AnsControlIntputTypeMinLength");
					Element      line = (Element) AnsControlInputTypeMinLengthNode.item(0);
					if(AnsControlInputTypeMinLengthNode.getLength()>0)
					{
						AnsControlInputTypeMinLength=xmlParser.getCharacterDataFromElement(line);
					}
				}



				if(!element.getElementsByTagName("AnsMustRequiredFlg").equals(null))
				{
					NodeList AnsMustRequiredFlgNode = element.getElementsByTagName("AnsMustRequiredFlg");
					Element      line = (Element) AnsMustRequiredFlgNode.item(0);
					if(AnsMustRequiredFlgNode.getLength()>0)
					{
						AnsMustRequiredFlg=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("QuestBundleFlg").equals(null))
				{
					NodeList QuestBundleFlgNode = element.getElementsByTagName("QuestBundleFlg");
					Element      line = (Element) QuestBundleFlgNode.item(0);
					if(QuestBundleFlgNode.getLength()>0)
					{
						QuestBundleFlg=xmlParser.getCharacterDataFromElement(line);
					}
				}


				if(!element.getElementsByTagName("ApplicationTypeID").equals(null))
				{
					NodeList ApplicationTypeIDNode = element.getElementsByTagName("ApplicationTypeID");
					Element      line = (Element) ApplicationTypeIDNode.item(0);
					if(ApplicationTypeIDNode.getLength()>0)
					{
						ApplicationTypeID=xmlParser.getCharacterDataFromElement(line);
					}
				}


				if(!element.getElementsByTagName("Sequence").equals(null))
				{
					NodeList SequenceNode = element.getElementsByTagName("Sequence");
					Element      line = (Element) SequenceNode.item(0);
					if(SequenceNode.getLength()>0)
					{
						Sequence=xmlParser.getCharacterDataFromElement(line);
					}
				}


				if(!element.getElementsByTagName("AnswerHint").equals(null))
				{
					NodeList SequenceNode = element.getElementsByTagName("AnswerHint");
					Element      line = (Element) SequenceNode.item(0);
					if(SequenceNode.getLength()>0)
					{
						answerHint=xmlParser.getCharacterDataFromElement(line);
					}
				}


				if(!element.getElementsByTagName("QuestDescHindi").equals(null))
				{
					NodeList QuestDescHindiNode = element.getElementsByTagName("QuestDescHindi");
					Element      line = (Element) QuestDescHindiNode.item(0);
					if(QuestDescHindiNode.getLength()>0)
					{
						answerHint=xmlParser.getCharacterDataFromElement(line);
					}
				}




				//dbengine.savetblQuestionMstr(QuestID, QuestCode, QuestDesc, QuestType, AnsControlType, AsnControlInputTypeID, AnsControlInputTypeMaxLength, AnsMustRequiredFlg, QuestBundleFlg, ApplicationTypeID, Sequence,AnsControlInputTypeMinLength,answerHint,flgQuestIDForOutChannel,QuestDescHindi);

			}

			// dbengine.savetblQuestionMstr(QuestID, QuestCode, QuestDesc, QuestType, AnsControlType, AsnControlInputTypeID, AnsControlInputTypeMaxLength, AnsMustRequiredFlg, QuestBundleFlg, ApplicationTypeID, Sequence,AnsControlInputTypeMinLength,answerHint,flgQuestIDForOutChannel);
	               
	           // }

			NodeList tblGetPDAQuestGrpMappingNode = doc.getElementsByTagName("tblGetPDAQuestGrpMapping");
			for (int i = 0; i < tblGetPDAQuestGrpMappingNode.getLength(); i++)
			{
				String GrpQuestID="0";
				String QuestID="0";
				String GrpID="0";
				String GrpNodeID="0";

				String GrpDesc="0";
				String SectionNo="0";
				String GrpCopyID="0";
				String QuestCopyID="0";
				String sequence="0";

				Element element = (Element) tblGetPDAQuestGrpMappingNode.item(i);

				if(!element.getElementsByTagName("GrpQuestID").equals(null))
				{
					NodeList GrpQuestIDNode = element.getElementsByTagName("GrpQuestID");
					Element     line = (Element) GrpQuestIDNode.item(0);
					if(GrpQuestIDNode.getLength()>0)
					{
						GrpQuestID=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("QuestID").equals(null))
				{
					NodeList QuestIDNode = element.getElementsByTagName("QuestID");
					Element     line = (Element) QuestIDNode.item(0);
					if(QuestIDNode.getLength()>0)
					{
						QuestID=xmlParser.getCharacterDataFromElement(line);
					}
				}



				if(!element.getElementsByTagName("GrpID").equals(null))
				{
					NodeList GrpIDNode = element.getElementsByTagName("GrpID");
					Element     line = (Element) GrpIDNode.item(0);
					if(GrpIDNode.getLength()>0)
					{
						GrpID=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("GrpNodeID").equals(null))
				{
					NodeList GrpNodeIDNode = element.getElementsByTagName("GrpNodeID");
					Element     line = (Element) GrpNodeIDNode.item(0);
					if(GrpNodeIDNode.getLength()>0)
					{
						GrpNodeID=xmlParser.getCharacterDataFromElement(line);
					}
				}


				if(!element.getElementsByTagName("GrpDesc").equals(null))
				{
					NodeList GrpDescNode = element.getElementsByTagName("GrpDesc");
					Element     line = (Element) GrpDescNode.item(0);
					if(GrpDescNode.getLength()>0)
					{
						GrpDesc=xmlParser.getCharacterDataFromElement(line);
					}
				}




				if(!element.getElementsByTagName("SectionNo").equals(null))
				{
					NodeList SectionNoNode = element.getElementsByTagName("SectionNo");
					Element     line = (Element) SectionNoNode.item(0);
					if(SectionNoNode.getLength()>0)
					{
						SectionNo=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("GrpCopyID").equals(null))
				{
					NodeList GrpCopyIDNode = element.getElementsByTagName("GrpCopyID");
					Element     line = (Element) GrpCopyIDNode.item(0);
					if(GrpCopyIDNode.getLength()>0)
					{
						GrpCopyID=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("QuestCopyID").equals(null))
				{
					NodeList QuestCopyIDNode = element.getElementsByTagName("QuestCopyID");
					Element     line = (Element) QuestCopyIDNode.item(0);
					if(QuestCopyIDNode.getLength()>0)
					{
						QuestCopyID=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("Sequence").equals(null))
				{
					NodeList SequenceNode = element.getElementsByTagName("Sequence");
					Element     line = (Element) SequenceNode.item(0);
					if(SequenceNode.getLength()>0)
					{
						sequence=xmlParser.getCharacterDataFromElement(line);
					}
				}

				//dbengine.savetblPDAQuestGrpMappingMstr(GrpQuestID, QuestID, GrpID, GrpNodeID, GrpDesc, SectionNo, GrpCopyID, QuestCopyID,sequence);

			}

			// dbengine.savetblPDAQuestGrpMappingMstr(GrpQuestID, QuestID, GrpID, GrpNodeID, GrpDesc, SectionNo,GrpCopyID,QuestCopyID);
	               
	           // }

			NodeList tblGetPDAQuestOptionMstrNode = doc.getElementsByTagName("tblGetPDAQuestOptionMstr");
			for (int i = 0; i < tblGetPDAQuestOptionMstrNode.getLength(); i++)
			{

				String OptID="0";
				String QuestID="0";
				String OptionNo="0";
				String OptionDescr="0";
				String Sequence="0";






				Element element = (Element) tblGetPDAQuestOptionMstrNode.item(i);

				if(!element.getElementsByTagName("OptID").equals(null))
				{
					NodeList OptIDNode = element.getElementsByTagName("OptID");
					Element      line = (Element) OptIDNode.item(0);
					if(OptIDNode.getLength()>0)
					{
						OptID=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("QuestID").equals(null))
				{
					NodeList QuestIDNode = element.getElementsByTagName("QuestID");
					Element      line = (Element) QuestIDNode.item(0);
					if(QuestIDNode.getLength()>0)
					{
						QuestID=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("AnsVal").equals(null))
				{
					NodeList OptionNoDNode = element.getElementsByTagName("AnsVal");
					Element      line = (Element) OptionNoDNode.item(0);
					if(OptionNoDNode.getLength()>0)
					{
						OptionNo=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("OptionDescr").equals(null))
				{
					NodeList OptionDescrNode = element.getElementsByTagName("OptionDescr");
					Element      line = (Element) OptionDescrNode.item(0);
					if(OptionDescrNode.getLength()>0)
					{
						OptionDescr=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("Sequence").equals(null))
				{
					NodeList SequenceNode = element.getElementsByTagName("Sequence");
					Element      line = (Element) SequenceNode.item(0);
					if(SequenceNode.getLength()>0)
					{

						Sequence=xmlParser.getCharacterDataFromElement(line);

					}
				}
			//	dbengine.savetblOptionMstr(OptID, QuestID, OptionNo, OptionDescr, Sequence);
				System.out.println("OptID:" + OptID + "QuestID:" + QuestID + "OptionNo:" + OptionNo + "OptionDescr:" + OptionDescr + "Sequence:" + Sequence);

			}
			// dbengine.savetblOptionMstr(OptID, QuestID, OptionNo, OptionDescr, Sequence);

	            // }


			NodeList tblGetPDAQuestionDependentMstrNode = doc.getElementsByTagName("tblGetPDAQuestionDependentMstr");
			for (int i = 0; i < tblGetPDAQuestionDependentMstrNode.getLength(); i++)
			{


				String QuestionID="0";
				String OptionID="0";
				String DependentQuestionID="0";

				String GrpID="0";
				String DpndntGrpID="0";







				Element element = (Element) tblGetPDAQuestionDependentMstrNode.item(i);



				if(!element.getElementsByTagName("QuestID").equals(null))
				{
					NodeList QuestionIDNode = element.getElementsByTagName("QuestID");
					Element      line = (Element) QuestionIDNode.item(0);
					if(QuestionIDNode.getLength()>0)
					{
						QuestionID=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("OptID").equals(null))
				{
					NodeList OptionIDNode = element.getElementsByTagName("OptID");
					Element      line = (Element) OptionIDNode.item(0);
					if(OptionIDNode.getLength()>0)
					{
						OptionID=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("DependentQuestionID").equals(null))
				{
					NodeList DependentQuestionIDNode = element.getElementsByTagName("DependentQuestionID");
					Element      line = (Element) DependentQuestionIDNode.item(0);
					if(DependentQuestionIDNode.getLength()>0)
					{
						DependentQuestionID=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("GrpQuestID").equals(null))
				{
					NodeList GrpIDNode = element.getElementsByTagName("GrpQuestID");
					Element      line = (Element) GrpIDNode.item(0);
					if(GrpIDNode.getLength()>0)
					{
						GrpID=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("GrpDepQuestID").equals(null))
				{
					NodeList GrpDepQuestIDNode = element.getElementsByTagName("GrpDepQuestID");
					Element      line = (Element) GrpDepQuestIDNode.item(0);
					if(GrpDepQuestIDNode.getLength()>0)
					{
						DpndntGrpID=xmlParser.getCharacterDataFromElement(line);
					}
				}
			//	dbengine.savetblQuestionDependentMstr(QuestionID, OptionID, DependentQuestionID,GrpID,DpndntGrpID);

				//  dbengine.savetblOptionMstr(OptID, QuestID, OptionNo, OptionDescr, Sequence);
				System.out.println("QuestionID:" + QuestionID + "OptionID:" + OptionID + "DependentQuestionID:" + DependentQuestionID);

			}

			NodeList tblPDAQuestOptionDependentMstr = doc.getElementsByTagName("tblPDAQuestOptionDependentMstr");
			for (int i = 0; i < tblPDAQuestOptionDependentMstr.getLength(); i++)
			{
				int qstId=0;
				int depQstId=0;
				int grpQstId=0;
				int grpDepQstId=0;
				Element element = (Element) tblPDAQuestOptionDependentMstr.item(i);




				if(!element.getElementsByTagName("QstId").equals(null))
				{
					NodeList QstIdNode = element.getElementsByTagName("QstId");
					Element     line = (Element) QstIdNode.item(0);
					if(QstIdNode.getLength()>0)
					{
						qstId=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}
				if(!element.getElementsByTagName("DepQstId").equals(null))
				{
					NodeList DepQstIdNode = element.getElementsByTagName("DepQstId");
					Element     line = (Element) DepQstIdNode.item(0);
					if(DepQstIdNode.getLength()>0)
					{
						depQstId=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}



				if(!element.getElementsByTagName("GrpQuestID").equals(null))
				{
					NodeList GrpQuestIDNode = element.getElementsByTagName("GrpQuestID");
					Element     line = (Element) GrpQuestIDNode.item(0);
					if(GrpQuestIDNode.getLength()>0)
					{
						grpQstId=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}

				if(!element.getElementsByTagName("GrpDepQuestID").equals(null))
				{
					NodeList GrpDepQuestIDNode = element.getElementsByTagName("GrpDepQuestID");
					Element     line = (Element) GrpDepQuestIDNode.item(0);
					if(GrpDepQuestIDNode.getLength()>0)
					{
						grpDepQstId=Integer.valueOf(xmlParser.getCharacterDataFromElement(line));
					}
				}
	         	              /*   int qstId=0;
		         	            	int depQstId=0;
		         	            	int grpQstId=0;
		         	            	int grpDepQstId=0;*/
				dbengine.savetblPDAQuestOptionDependentMstr(qstId, depQstId, grpQstId, grpDepQstId);

			}

			NodeList tblPDAQuestOptionValuesDependentMstr = doc.getElementsByTagName("tblPDAQuestOptionValuesDependentMstr");
			for (int i = 0; i < tblPDAQuestOptionValuesDependentMstr.getLength(); i++)
			{
				int DepQstId=0;
				String DepAnswValId="0";
				int QstId=0;
				String AnswValId="0";
				String OptDescr="N/A";
				int Sequence=0;
				int GrpQuestID=0;
				int GrpDepQuestID=0;

				Element element = (Element) tblPDAQuestOptionValuesDependentMstr.item(i);




				if(!element.getElementsByTagName("DepQstId").equals(null))
				{
					NodeList DepQstIdNode = element.getElementsByTagName("DepQstId");
					Element     line = (Element) DepQstIdNode.item(0);
					if(DepQstIdNode.getLength()>0)
					{
						DepQstId=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}
				if(!element.getElementsByTagName("DepOptID").equals(null))
				{
					NodeList DepAnswValIdNode = element.getElementsByTagName("DepOptID");
					Element     line = (Element) DepAnswValIdNode.item(0);
					if(DepAnswValIdNode.getLength()>0)
					{
						DepAnswValId=xmlParser.getCharacterDataFromElement(line);
					}
				}



				if(!element.getElementsByTagName("QstId").equals(null))
				{
					NodeList QstIdNode = element.getElementsByTagName("QstId");
					Element     line = (Element) QstIdNode.item(0);
					if(QstIdNode.getLength()>0)
					{
						QstId=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}

				if(!element.getElementsByTagName("OptID").equals(null))
				{
					NodeList AnswValIdNode = element.getElementsByTagName("OptID");
					Element     line = (Element) AnswValIdNode.item(0);
					if(AnswValIdNode.getLength()>0)
					{
						AnswValId=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("OptDescr").equals(null))
				{
					NodeList OptDescrNode = element.getElementsByTagName("OptDescr");
					Element     line = (Element) OptDescrNode.item(0);
					if(OptDescrNode.getLength()>0)
					{
						OptDescr=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("Sequence").equals(null))
				{
					NodeList AnswValIdNode = element.getElementsByTagName("Sequence");
					Element     line = (Element) AnswValIdNode.item(0);
					if(AnswValIdNode.getLength()>0)
					{
						Sequence=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}
				if(!element.getElementsByTagName("GrpQuestID").equals(null))
				{
					NodeList GrpQuestIDNode = element.getElementsByTagName("GrpQuestID");
					Element     line = (Element) GrpQuestIDNode.item(0);
					if(GrpQuestIDNode.getLength()>0)
					{
						GrpQuestID=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}
				if(!element.getElementsByTagName("GrpDepQuestID").equals(null))
				{
					NodeList GrpDepQuestIDNode = element.getElementsByTagName("GrpDepQuestID");
					Element     line = (Element) GrpDepQuestIDNode.item(0);
					if(GrpDepQuestIDNode.getLength()>0)
					{
						GrpDepQuestID=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}
				dbengine.savetblPDAQuestOptionValuesDependentMstr(DepQstId, DepAnswValId, QstId, AnswValId, OptDescr, Sequence, GrpQuestID, GrpDepQuestID);

			}



	                         
	                         
	                         NodeList tblOutletChannelBusinessSegmentMaster = doc.getElementsByTagName("tblOutletChannelBusinessSegmentMaster");
	         	            for (int i = 0; i < tblOutletChannelBusinessSegmentMaster.getLength(); i++)
	         	            {
	         	            	int OutChannelID=0;
	         	            	String ChannelName="NA";
	         	            	int BusinessSegmentID=0;
	         	            	String BusinessSegment="NA";
	         	                Element element = (Element) tblOutletChannelBusinessSegmentMaster.item(i);
	         	                
	         	                

	         	                 
	         	                if(!element.getElementsByTagName("OutChannelID").equals(null))
	         	                 {
	         	                 NodeList OutChannelIDNode = element.getElementsByTagName("OutChannelID");
	         	                 Element     line = (Element) OutChannelIDNode.item(0);
	         		                if(OutChannelIDNode.getLength()>0)
	         		                {
	         		                	OutChannelID=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
	         		                }
	         	            	 }
	         	                if(!element.getElementsByTagName("ChannelName").equals(null))
	         	                 {
	         	                 NodeList ChannelNameNode = element.getElementsByTagName("ChannelName");
	         	                 Element     line = (Element) ChannelNameNode.item(0);
	         		                if(ChannelNameNode.getLength()>0)
	         		                {
	         		                	ChannelName=xmlParser.getCharacterDataFromElement(line);
	         		                }
	         	            	 }
	         	                
	         	                
	         	              
	         	                   if(!element.getElementsByTagName("BusinessSegmentID").equals(null))
	         	                 {
	         	                 NodeList BusinessSegmentIDNode = element.getElementsByTagName("BusinessSegmentID");
	         	                 Element     line = (Element) BusinessSegmentIDNode.item(0);
	         		                if(BusinessSegmentIDNode.getLength()>0)
	         		                {
	         		                	BusinessSegmentID=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
	         		                }
	         	            	 }
	         	                
	         	                  if(!element.getElementsByTagName("BusinessSegment").equals(null))
	         	                 {
	         	                 NodeList BusinessSegmentNode = element.getElementsByTagName("BusinessSegment");
	         	                 Element     line = (Element) BusinessSegmentNode.item(0);
	         		                if(BusinessSegmentNode.getLength()>0)
	         		                {
	         		                	BusinessSegment=xmlParser.getCharacterDataFromElement(line);
	         		                }
	         	            	 }
	         	                   
	         	             dbengine.savetblOutletChannelBusinessSegmentMaster(OutChannelID, ChannelName, BusinessSegmentID, BusinessSegment);
	         	               
	         	            }
	            
            setmovie.director = "1";
            //dbengine.close();;
			return setmovie;

		} catch (Exception e) 
		{
			 //dbengine.close();;
			setmovie.exceptionCode=e.getCause().getMessage();
			System.out.println("Aman Exception occur in fnSingleCallAllWebService :"+e.toString());
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			
			
			return setmovie;
		}
	}
	public ServiceWorker fnGetDistributorTodaysStock(Context ctx,int CustomerNodeID, int CustomerNodeType,String bydate,String IMEINo,String SysDate, int AppVersionID)
	{
		this.context = ctx;


		int chkTblStoreListContainsRow=1;
		StringReader read;
		InputSource inputstream;
		final String SOAP_ACTION = "http://tempuri.org/fnGetDistributorTodaysStock";
		final String METHOD_NAME = "fnGetDistributorTodaysStock";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;

		//Create request
		SoapObject table = null; //Contains table of dataset that returned
		// through SoapObject
		SoapObject client = null; //Its the client petition to the web service
		SoapObject tableRow = null; //Contains row of table
		SoapObject responseBody = null; //Contains XML content of dataset

		//DBHelper dbengine=new DBHelper(ctx);
		PRJDatabase dbengine = new PRJDatabase(context);
		//dbengine.open();
		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;
		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		sse.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,0);

		ServiceWorker setmovie = new ServiceWorker();
		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);
			//client.addProperty("ApplicationID", ApplicationID);
			//client.addProperty("uuid", uuid.toString());

			//String rID=dbengine.GetActiveRouteIDSunilAgain();


			Date currDate= new Date();
			SimpleDateFormat currDateFormat =new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);

			currSysDate = currDateFormat.format(currDate).toString();
			SysDate = currSysDate.trim().toString();

			client.addProperty("CustomerNodeID",CustomerNodeID);
			client.addProperty("CustomerNodeType",CustomerNodeType);

			client.addProperty("bydate", bydate.toString());
			client.addProperty("IMEINo", IMEINo.toString());
			//client.addProperty("rID", rID.toString());
			client.addProperty("SysDate", SysDate.toString());
			client.addProperty("AppVersionID", dbengine.AppVersionID.toString());


			sse.setOutputSoapObject(client);
			sse.bodyOut = client;
			androidHttpTransport.call(SOAP_ACTION, sse);
			responseBody = (SoapObject)sse.bodyIn;
			// This step: get file XML
			//responseBody = (SoapObject) sse.getResponse();
			int totalCount = responseBody.getPropertyCount();

			// System.out.println("Kajol 2 :"+totalCount);
			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			// System.out.println("Kajol 3 :"+name);

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);

			//  dbengine.deleteDistributorStockTbles();

			NodeList tblDistributorDayReportNode = doc.getElementsByTagName("tblDistributorDayReport");
			for (int i = 0; i < tblDistributorDayReportNode.getLength(); i++)
			{
				String ProductNodeID="0";
				String ProductNodeType="0";
				String SKUName="0";
				String FlvShortName="0";
				String StockDate="0";

				Element element = (Element) tblDistributorDayReportNode.item(i);

				if(!element.getElementsByTagName("ProductNodeID").equals(null))
				{
					NodeList ProductNodeIDNode = element.getElementsByTagName("ProductNodeID");
					Element      line = (Element) ProductNodeIDNode.item(0);
					if(ProductNodeIDNode.getLength()>0)
					{
						ProductNodeID=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("ProductNodeType").equals(null))
				{
					NodeList ProductNodeTypeNode = element.getElementsByTagName("ProductNodeType");
					Element      line = (Element) ProductNodeTypeNode.item(0);
					if(ProductNodeTypeNode.getLength()>0)
					{
						ProductNodeType=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("SKUName").equals(null))
				{
					NodeList SKUNameNode = element.getElementsByTagName("SKUName");
					Element      line = (Element) SKUNameNode.item(0);
					if(SKUNameNode.getLength()>0)
					{
						SKUName=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("FlvShortName").equals(null))
				{
					NodeList FlvShortNameNode = element.getElementsByTagName("FlvShortName");
					Element      line = (Element) FlvShortNameNode.item(0);
					if(FlvShortNameNode.getLength()>0)
					{
						FlvShortName=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("StockDate").equals(null))
				{
					NodeList StockDateNode = element.getElementsByTagName("StockDate");
					Element      line = (Element) StockDateNode.item(0);
					if(StockDateNode.getLength()>0)
					{
						StockDate=xmlParser.getCharacterDataFromElement(line);
					}
				}


				//AutoId= i +1;
				dbengine.savetblDistributorDayReport(ProductNodeID, ProductNodeType, SKUName, FlvShortName,StockDate,CustomerNodeID,CustomerNodeType);

			}


			NodeList tblDistributorDayReportColumnsDescNode = doc.getElementsByTagName("tblDistributorDayReportColumnsDesc");
			for (int i = 0; i < tblDistributorDayReportColumnsDescNode.getLength(); i++)
			{
				String DistDayReportCoumnName="0";
				String DistDayReportColumnDisplayName="0";

				Element element = (Element) tblDistributorDayReportColumnsDescNode.item(i);

				if(!element.getElementsByTagName("DistDayReportCoumnName").equals(null))
				{
					NodeList DistDayReportCoumnNameNode = element.getElementsByTagName("DistDayReportCoumnName");
					Element      line = (Element) DistDayReportCoumnNameNode.item(0);
					if(DistDayReportCoumnNameNode.getLength()>0)
					{
						DistDayReportCoumnName=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("DistDayReportColumnDisplayName").equals(null))
				{
					NodeList DistDayReportColumnDisplayNameNode = element.getElementsByTagName("DistDayReportColumnDisplayName");
					Element      line = (Element) DistDayReportColumnDisplayNameNode.item(0);
					if(DistDayReportColumnDisplayNameNode.getLength()>0)
					{
						DistDayReportColumnDisplayName=xmlParser.getCharacterDataFromElement(line);
					}
				}

				//AutoId= i +1;
				dbengine.savetblDistributorDayReportColumnsDesc(DistDayReportCoumnName, DistDayReportColumnDisplayName,CustomerNodeID,CustomerNodeType);
			}

			//dbengine.close();;
			setmovie.director = "1";

			return setmovie;

		} catch (Exception e)
		{
			//dbengine.close();;
			setmovie.exceptionCode=e.getCause().getMessage();
			System.out.println("Aman Exception occur in fnDistributor :"+e.toString());
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();


			return setmovie;
		}
	}


	public ServiceWorker getCategory(Context ctx, String uuid)
	{
		this.context = ctx;

		ServiceWorker setmovie = new ServiceWorker();
		PRJDatabase dbengine = new PRJDatabase(context);
		//dbengine.open();
		
		final String SOAP_ACTION = "http://tempuri.org/GetCategoryMstr";
		final String METHOD_NAME = "GetCategoryMstr";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;

		SoapObject table = null; // Contains table of dataset that returned
									// through SoapObject
		SoapObject client = null; // Its the client petition to the web service
		SoapObject tableRow = null; // Contains row of table
		SoapObject responseBody = null; // Contains XML content of dataset
		
		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;

		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		sse.addMapping(NAMESPACE, "ServiceWorker", this.getClass());
		// Note if class name isn't "ABC_CLASS_NAME" ,you must change
		sse.dotNet = true; // if WebService written .Net is result=true
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				URL,timeout);



		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);
			
			//////// System.out.println("soap obj date: "+ dateVAL);
			
		
			//client.addProperty("bydate", dateVAL.toString());
			client.addProperty("IMEINo", uuid.toString());
			
			
			//////// System.out.println("SRVC WRKR - dateVAL.toString(): "+dateVAL.toString());
			////// System.out.println("SRVC WRKR - uuid.toString(): "+uuid.toString());
			////// System.out.println("Arjun Calling category webservice 1");
			sse.setOutputSoapObject(client);
			sse.bodyOut = client;
			androidHttpTransport.call(SOAP_ACTION, sse);

			// This step: get file XML
			responseBody = (SoapObject) sse.getResponse();
			
			
			  SoapObject soDiffg = (SoapObject) responseBody.getProperty("diffgram");
				
	            if(soDiffg.hasProperty("NewDataSet"))
	            {
	            	// remove information XML,only retrieved results that returned
	    			responseBody = (SoapObject) responseBody.getProperty(1);
	    			
	    			// get information XMl of tables that is returned
	    			table = (SoapObject) responseBody.getProperty(0);
	            }
	            else
	            {
	            	
	            }
			
			int chkTblCategoryListContainsRow=0;
			
			if(soDiffg.hasProperty("NewDataSet"))
			{
				chkTblCategoryListContainsRow=1;
				dbengine.Delete_tblCategory_for_refreshData();
			}
			////// System.out.println("chkTblStoreListContainsRow: for Category "+ chkTblStoreListContainsRow);
			
			if(chkTblCategoryListContainsRow==1)
			{
				//////// System.out.println("h1");
				////// System.out.println("Arjun Calling category webservice 4");
				//////// System.out.println("table.getPropertyCount()"+table.getPropertyCount());
				if(table.getPropertyCount()>0)
				{
					//////// System.out.println("h2");
				for(int i = 0; i <= table.getPropertyCount() -1 ; i++)
				{
				
				//////// System.out.println("table - prop count: "+ table.getPropertyCount());
				tableRow = (SoapObject) table.getProperty(i);
				//////// System.out.println("i value: "+ i);
				
				//////// System.out.println("table - prop count: "+ table.getPropertyCount());
				tableRow = (SoapObject) table.getProperty(i);
				//////// System.out.println("i value: "+ i);
				
				
				String stID = "NA";
				String deDescr = "NA";
				int CatOrdr=0;
				
				if((tableRow.hasProperty("NODEID")))
				{
					if (tableRow.getProperty("NODEID").toString().isEmpty() ) 
					{
						stID="NA";
					} 
					else
					{
						stID = tableRow.getProperty("NODEID").toString().trim();
					}
				}
				
				if((tableRow.hasProperty("CATEGORY")))
				{
					if (tableRow.getProperty("CATEGORY").toString().isEmpty() ) 
					{
						deDescr="NA";
					} 
					else
					{
						deDescr = tableRow.getProperty("CATEGORY").toString().trim();
					}
				}
				
				
				if (tableRow.hasProperty("CatOrdr") ) 
				{
					if (tableRow.getProperty("CatOrdr").toString().isEmpty() ) 
					{
						CatOrdr=0;
					} 
					else
					{
						CatOrdr =Integer.parseInt(tableRow.getProperty("CatOrdr").toString().trim());
						
					}
				}
				
				
				//dbengine.saveCategory(stID.trim(), deDescr.trim(),CatOrdr);
			
				
				
					}
			}
			} //aa
			////dbengine.close();;
				
			
			//dbengine.close();;		// #4

			setmovie.director = "1";
			flagExecutedServiceSuccesfully=3;
			// System.out.println("ServiceWorkerNitish getCategory Inside");
			return setmovie;

		} catch (Exception e) {
			
			// System.out.println("Aman Exception occur in GetCategoryMstr :"+e.toString());
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			flagExecutedServiceSuccesfully=0;
			//dbengine.close();;
			return setmovie;
		}


	}



	public ServiceWorker callInvoiceButtonStoreMstr(Context ctx, String dateVAL, String uuid, String rID,HashMap<String,String> hmapInvoiceOrderIDandStatusNew) {
		this.context = ctx;
		
		PRJDatabase dbengine = new PRJDatabase(context);
		//dbengine.open();
		
		
		final String SOAP_ACTION = "http://tempuri.org/GetInvoiceButtonStoreMstr";
		final String METHOD_NAME = "GetInvoiceButtonStoreMstr";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;
	

		SoapObject table = null; // Contains table of dataset that returned
									// through SoapObject
		SoapObject client = null; // Its the client petition to the web service
		SoapObject tableRow = null; // Contains row of table
		SoapObject responseBody = null; // Contains XML content of dataset
		
		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;

	
		
		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		sse.addMapping(NAMESPACE, "ServiceWorker", this.getClass());
		// Note if class name isn't "movie" ,you must change
		sse.dotNet = true; // if WebService written .Net is result=true
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				URL,timeout);

		ServiceWorker setmovie = new ServiceWorker();
		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);
			
			
			client.addProperty("bydate", dateVAL.toString());
			client.addProperty("IMEINo", uuid.toString());
			client.addProperty("rID", rID.toString());
		
			
			
			sse.setOutputSoapObject(client);
			sse.bodyOut = client;
			androidHttpTransport.call(SOAP_ACTION, sse);

			// This step: get file XML
			responseBody = (SoapObject) sse.getResponse();
			// remove information XML,only retrieved results that returned
			responseBody = (SoapObject) responseBody.getProperty(1);
			
			// get information XMl of tables that is returned
			table = (SoapObject) responseBody.getProperty(0);
			
					// #1
			
			//dbengine.reCreateDB();
			////// System.out.println("Debug: " + "dbengine.open");
		
			////// System.out.println("chkTblStoreListContainsRow In Product: "+ chkTblStoreListContainsRow);
			if(table.getPropertyCount() >= 1 )
			{
				chkTblStoreListContainsRow=1;
			}
			//dbengine.reCreateDB();
			if(chkTblStoreListContainsRow==1)
			{
				String StoreID="0";
				String StoreName="NA";
				String RouteID="0";
				String RouteName="NA";
				String DistID="0";
				String DistName="NA";
				String InvoiceForDate="0";
				String flgSubmit="0";
				String OrderID="0";
				
				
				
				
					for(int i = 0; i <= table.getPropertyCount() -1 ; i++)
					{
					
						////// System.out.println("table - prop count: "+ table.getPropertyCount());
						tableRow = (SoapObject) table.getProperty(i);
						////// System.out.println("i value: "+ i);
						
						if (tableRow.hasProperty("StoreID")) 
						{
							if (tableRow.getProperty("StoreID").toString().isEmpty() ) 
							{
								StoreID="0";
							} 
							else
							{
								StoreID= tableRow.getProperty("StoreID").toString().trim();
								
							}
						} 
						
						
						if (tableRow.hasProperty("StoreName")) 
						{
							if (tableRow.getProperty("StoreName").toString().isEmpty() ) 
							{
								StoreName="NA";
							} 
							else
							{
								StoreName= tableRow.getProperty("StoreName").toString().trim();
								
							}
						} 
						
						if (tableRow.hasProperty("RouteID")) 
						{
							if (tableRow.getProperty("RouteID").toString().isEmpty() ) 
							{
								RouteID="0";
							} 
							else
							{
								RouteID= tableRow.getProperty("RouteID").toString().trim();
								
							}
						} 
						
						if (tableRow.hasProperty("RouteName")) 
						{
							if (tableRow.getProperty("RouteName").toString().isEmpty() ) 
							{
								RouteName="NA";
							} 
							else
							{
								RouteName= tableRow.getProperty("RouteName").toString().trim();
								
							}
						} 
						
						
						if (tableRow.hasProperty("DistID")) 
						{
							if (tableRow.getProperty("DistID").toString().isEmpty() ) 
							{
								DistID="0";
							} 
							else
							{
								DistID= tableRow.getProperty("DistID").toString().trim();
								
							}
						} 
						
						if (tableRow.hasProperty("DistName")) 
						{
							if (tableRow.getProperty("DistName").toString().isEmpty() ) 
							{
								DistName="NA";
							} 
							else
							{
								DistName= tableRow.getProperty("DistName").toString().trim();
								
							}
						} 
						
						if (tableRow.hasProperty("InvoiceForDate")) 
						{
							if (tableRow.getProperty("InvoiceForDate").toString().isEmpty() ) 
							{
								InvoiceForDate="0";
							} 
							else
							{
								InvoiceForDate= tableRow.getProperty("InvoiceForDate").toString().trim();
								
							}
						} 
						
						if (tableRow.hasProperty("flgSubmit")) 
						{
							if (tableRow.getProperty("flgSubmit").toString().isEmpty() ) 
							{
								flgSubmit="NA";
							} 
							else
							{
								flgSubmit= tableRow.getProperty("flgSubmit").toString().trim();
								
							}
						} 
						
						if (tableRow.hasProperty("OrderID")) 
						{
							if (tableRow.getProperty("OrderID").toString().isEmpty() ) 
							{
								OrderID="0";
							} 
							else
							{
								OrderID= tableRow.getProperty("OrderID").toString().trim();
								
							}
						} 
						
						//// System.out.println("Ajay testing StoreID :"+StoreID);
						//// System.out.println("Ajay testing  OrderID :"+OrderID);
						
						/*dbengine.inserttblInvoiceButtonStoreMstr(StoreID,StoreName,RouteID,
								RouteName,DistID,DistName,InvoiceForDate,flgSubmit,uuid.toString(),OrderID);*/
						
						
						if(hmapInvoiceOrderIDandStatusNew.containsKey(OrderID))
					      {
					       if(hmapInvoiceOrderIDandStatusNew.get(OrderID).equals("4"))
					       {
					        //dbengine.fnDeletetblInvoiceSubmittedRecords(OrderID);
					       }
					       else
					       {
					        dbengine.fnDeletetblInvoiceSubmittedRecords(OrderID);
					      //  dbengine.inserttblPendingInvoices(StoreID,StoreName,RouteID,RouteName,DistID,DistName,InvoiceForDate,flgSubmit,uuid.toString(),OrderID);
					       }
					      }
					      else
					      {
					      // dbengine.inserttblPendingInvoices(StoreID,StoreName,RouteID,RouteName,DistID,DistName,InvoiceForDate,flgSubmit,uuid.toString(),OrderID);
					      }
					
					}
			
				
			}

			dbengine.fnDeleteUnWantedSubmitedInvoiceOrders();
			//dbengine.close();;		// #4
			
			setmovie.director = "1";
			
			return setmovie;
//return counts;
		} catch (Exception e) {

			setmovie.exceptionCode=e.getCause().getMessage();
			// System.out.println("Aman Exception occur in GetInvoiceButtonStoreMstr :"+e.toString());
			////// System.out.println("aman getallProduct: 16 "+e.toString());
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			//dbengine.close();;
			return setmovie;
		}
	}
		public ServiceWorker callInvoiceButtonProductMstr(Context ctx, String dateVAL, String uuid, String rID) {
			this.context = ctx;
			
			PRJDatabase dbengine = new PRJDatabase(context);
			//dbengine.open();	
			
			final String SOAP_ACTION = "http://tempuri.org/GetInvoiceButtonProductMstr";
			final String METHOD_NAME = "GetInvoiceButtonProductMstr";
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = UrlForWebService;
		
// System.out.println("Abhinav Raj and Nithis Imei No="+uuid);
// System.out.println("Abhinav Raj and Nithis Route Id="+rID);
			SoapObject table = null; // Contains table of dataset that returned
										// through SoapObject
			SoapObject client = null; // Its the client petition to the web service
			SoapObject tableRow = null; // Contains row of table
			SoapObject responseBody = null; // Contains XML content of dataset
			
			//SoapObject param
			HttpTransportSE transport = null; // That call webservice
			SoapSerializationEnvelope sse = null;

		
			
			sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			sse.addMapping(NAMESPACE, "ServiceWorker", this.getClass());
			// Note if class name isn't "movie" ,you must change
			sse.dotNet = true; // if WebService written .Net is result=true
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					URL,timeout);

			ServiceWorker setmovie = new ServiceWorker();
			try {
				client = new SoapObject(NAMESPACE, METHOD_NAME);
				
				
				client.addProperty("bydate", dateVAL.toString());
				client.addProperty("IMEINo", uuid.toString());
				client.addProperty("rID", rID.toString());
			
				sse.setOutputSoapObject(client);
				sse.bodyOut = client;
				androidHttpTransport.call(SOAP_ACTION, sse);

				// This step: get file XML
				responseBody = (SoapObject) sse.getResponse();
				// remove information XML,only retrieved results that returned
				responseBody = (SoapObject) responseBody.getProperty(1);
				
				// get information XMl of tables that is returned
				table = (SoapObject) responseBody.getProperty(0);
				
					
				//dbengine.fnDeletetblInvoiceButtonProductMstr();
				
				//dbengine.reCreateDB();
				////// System.out.println("Debug: " + "dbengine.open");
			
				////// System.out.println("chkTblStoreListContainsRow In Product: "+ chkTblStoreListContainsRow);
				if(table.getPropertyCount() >= 1 )
				{
					chkTblStoreListContainsRow=1;
				}
				//dbengine.reCreateDB();
				if(chkTblStoreListContainsRow==1)
				{
					String ProductId="0";
					String ProductName="NA";
					
						for(int i = 0; i <= table.getPropertyCount() -1 ; i++)
						{
						
							////// System.out.println("table - prop count: "+ table.getPropertyCount());
							tableRow = (SoapObject) table.getProperty(i);
							////// System.out.println("i value: "+ i);
							
							if (tableRow.hasProperty("ProductId")) 
							{
								if (tableRow.getProperty("ProductId").toString().isEmpty() ) 
								{
									ProductId="0";
								} 
								else
								{
									ProductId= tableRow.getProperty("ProductId").toString().trim();
									
								}
							} 
							
							
							if (tableRow.hasProperty("ProductName")) 
							{
								if (tableRow.getProperty("ProductName").toString().isEmpty() ) 
								{
									ProductName="NA";
								} 
								else
								{
									ProductName= tableRow.getProperty("ProductName").toString().trim();
									
								}
							} 
							
							
							
						//	dbengine.inserttblInvoiceButtonProductMstr(ProductId,ProductName);
						}
				
					
				}
				
				
				//dbengine.close();;		// #4
				
				setmovie.director = "1";
				
				return setmovie;
	//return counts;
			} catch (Exception e) {

				setmovie.exceptionCode=e.getCause().getMessage();
				// System.out.println("Aman Exception occur in GetInvoiceButtonProductMstr :"+e.toString());
				////// System.out.println("aman getallProduct: 16 "+e.toString());
				setmovie.director = e.toString();
				setmovie.movie_name = e.toString();
				//dbengine.close();;	
				return setmovie;
			}

		}

	
	
	
	public ServiceWorker callInvoiceButtonStoreProductwiseOrder(Context ctx, String dateVAL, String uuid, String rID,HashMap<String,String> hmapInvoiceOrderIDandStatusNew) {
		this.context = ctx;
		
		PRJDatabase dbengine = new PRJDatabase(context);
		//dbengine.open();
		
		final String SOAP_ACTION = "http://tempuri.org/GetInvoiceButtonStoreProductwiseOrder";
		final String METHOD_NAME = "GetInvoiceButtonStoreProductwiseOrder";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;
	

		SoapObject table = null; // Contains table of dataset that returned
									// through SoapObject
		SoapObject client = null; // Its the client petition to the web service
		SoapObject tableRow = null; // Contains row of table
		SoapObject responseBody = null; // Contains XML content of dataset
		
		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;

	
		
		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		sse.addMapping(NAMESPACE, "ServiceWorker", this.getClass());
		// Note if class name isn't "movie" ,you must change
		sse.dotNet = true; // if WebService written .Net is result=true
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				URL,timeout);

		ServiceWorker setmovie = new ServiceWorker();
		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);
			
			
			client.addProperty("bydate", dateVAL.toString());
			client.addProperty("IMEINo", uuid.toString());
			client.addProperty("rID", rID.toString());
		
			sse.setOutputSoapObject(client);
			sse.bodyOut = client;
			androidHttpTransport.call(SOAP_ACTION, sse);

			// This step: get file XML
			responseBody = (SoapObject) sse.getResponse();
			// remove information XML,only retrieved results that returned
			responseBody = (SoapObject) responseBody.getProperty(1);
			
			// get information XMl of tables that is returned
			table = (SoapObject) responseBody.getProperty(0);
			
					
			
			
			//dbengine.reCreateDB();
			////// System.out.println("Debug: " + "dbengine.open");
		
			////// System.out.println("chkTblStoreListContainsRow In Product: "+ chkTblStoreListContainsRow);
			if(table.getPropertyCount() >= 1 )
			{
				chkTblStoreListContainsRow=1;
			}
			//dbengine.reCreateDB();
			if(chkTblStoreListContainsRow==1)
			{
				String StoreID="0";
				String ProductID="0";
				String OrderQty="0";
				String ProductPrice="NA";
				String InvoiceForDate="0";
				String OrderID="0";
				String CatID="0";
				int Freeqty=0;
				double TotLineDiscVal=0.0;
				
					for(int i = 0; i <= table.getPropertyCount() -1 ; i++)
					{
					
						////// System.out.println("table - prop count: "+ table.getPropertyCount());
						tableRow = (SoapObject) table.getProperty(i);
						////// System.out.println("i value: "+ i);
						
						if (tableRow.hasProperty("StoreID")) 
						{
							if (tableRow.getProperty("StoreID").toString().isEmpty() ) 
							{
								StoreID="0";
							} 
							else
							{
								StoreID= tableRow.getProperty("StoreID").toString().trim();
								
							}
						} 
						
						
						if (tableRow.hasProperty("ProductID")) 
						{
							if (tableRow.getProperty("ProductID").toString().isEmpty() ) 
							{
								ProductID="0";
							} 
							else
							{
								ProductID= tableRow.getProperty("ProductID").toString().trim();
								
							}
						} 
						
						if (tableRow.hasProperty("OrderQty")) 
						{
							if (tableRow.getProperty("OrderQty").toString().isEmpty() ) 
							{
								OrderQty="0";
							} 
							else
							{
								OrderQty= tableRow.getProperty("OrderQty").toString().trim();
								
							}
						} 
						
						if (tableRow.hasProperty("ProductPrice")) 
						{
							if (tableRow.getProperty("ProductPrice").toString().isEmpty() ) 
							{
								ProductPrice="0";
							} 
							else
							{
								ProductPrice= tableRow.getProperty("ProductPrice").toString().trim();
								
							}
						} 
						
						
						
						if (tableRow.hasProperty("InvoiceForDate")) 
						{
							if (tableRow.getProperty("InvoiceForDate").toString().isEmpty() ) 
							{
								InvoiceForDate="0";
							} 
							else
							{
								InvoiceForDate= tableRow.getProperty("InvoiceForDate").toString().trim();
								
							}
						} 
						if (tableRow.hasProperty("OrderID")) 
						{
							if (tableRow.getProperty("OrderID").toString().isEmpty() ) 
							{
								OrderID="0";
							} 
							else
							{
								OrderID= tableRow.getProperty("OrderID").toString().trim();
								
							}
						} 
						
						if (tableRow.hasProperty("CatID")) 
						{
							if (tableRow.getProperty("CatID").toString().isEmpty() ) 
							{
								CatID="0";
							} 
							else
							{
								CatID= tableRow.getProperty("CatID").toString().trim();
								
							}
						} 
						
						if (tableRow.hasProperty("Freeqty")) 
						{
							if (tableRow.getProperty("Freeqty").toString().isEmpty() ) 
							{
								Freeqty=0;
							} 
							else
							{
								Freeqty= Integer.parseInt(tableRow.getProperty("Freeqty").toString().trim());
								
							}
						} 
						
						if (tableRow.hasProperty("TotLineDiscVal")) 
						{
							if (tableRow.getProperty("TotLineDiscVal").toString().isEmpty() ) 
							{
								TotLineDiscVal=0.00;
							} 
							else
							{
								TotLineDiscVal= Double.parseDouble(tableRow.getProperty("TotLineDiscVal").toString().trim());
								
							}
						} 
						//TotLineDiscVal
					//	// System.out.println("Ajay testing order StoreID :"+StoreID);
					//	// System.out.println("Ajay testing order OrderID :"+OrderID);
						
						
						
						
						/*dbengine.inserttblInvoiceButtonStoreProductwiseOrder(StoreID,ProductID,OrderQty,
								ProductPrice,InvoiceForDate,OrderID,CatID,Freeqty,TotLineDiscVal);*/
						
						
						
						/*if(hmapInvoiceOrderIDandStatusNew.containsKey(OrderID))
					      { 
					      }
					      else
					      {
					      dbengine.inserttblInvoiceButtonStoreProductwiseOrder(StoreID,ProductID,OrderQty,ProductPrice,InvoiceForDate,OrderID,CatID,Freeqty,TotLineDiscVal);
					      }*/
						
						if(hmapInvoiceOrderIDandStatusNew.containsKey(OrderID))
					      {
					       if(hmapInvoiceOrderIDandStatusNew.get(OrderID).equals("4"))
					       {
					        //dbengine.fnDeletetblInvoiceSubmittedRecords(OrderID);
					       }
					       else
					       {
					        
					       // dbengine.inserttblInvoiceButtonStoreProductwiseOrder(StoreID,ProductID,OrderQty,ProductPrice,InvoiceForDate,OrderID,CatID,Freeqty,TotLineDiscVal);
					       }
					      }
					      else
					      {
					    	//  dbengine.inserttblInvoiceButtonStoreProductwiseOrder(StoreID,ProductID,OrderQty,ProductPrice,InvoiceForDate,OrderID,CatID,Freeqty,TotLineDiscVal);
					      }
						
						
					}
			
				
			}
			
			
			//dbengine.close();;		// #4
			
			setmovie.director = "1";
			
			return setmovie;
//return counts;
		} catch (Exception e) {

			setmovie.exceptionCode=e.getCause().getMessage();
			// System.out.println("Aman Exception occur in GetInvoiceButtonStoreProductwiseOrder :"+e.toString());
			////// System.out.println("aman getallProduct: 16 "+e.toString());
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			//dbengine.close();;
			return setmovie;
		}

	}


	
	// Working for Parag Summary
		public ServiceWorker getCallspRptGetSKUWiseDaySummary(Context ctx,String uuid ,String dateVAL) 
		{
			this.context = ctx;
			PRJDatabase dbengine = new PRJDatabase(context);
			//dbengine.open();	
			
			//String Sstat = "0";
			
			final String SOAP_ACTION = "http://tempuri.org/CallspRptGetSKUWiseDaySummary";
			final String METHOD_NAME = "CallspRptGetSKUWiseDaySummary";
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = UrlForWebService;
			// URL: L service
			decimalFormat.applyPattern(pattern);
			// NAMESPACE: must have in service page

			// METHOD_NAME: function in web service

			// SOAP_ACTION = NAMESPACE + METHOD_NAME

			SoapObject table = null; // Contains table of dataset that returned
										// through SoapObject
			SoapObject client = null; // Its the client petition to the web service
			SoapObject tableRow = null; // Contains row of table
			SoapObject responseBody = null; // Contains XML content of dataset
			
			//SoapObject param
			HttpTransportSE transport = null; // That call webservice
			SoapSerializationEnvelope sse = null;

			
			sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			sse.addMapping(NAMESPACE, "ServiceWorker", this.getClass());
			// Note if class name isn't "movie" ,you must change
			sse.dotNet = true; // if WebService written .Net is result=true
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					URL,timeout);

			ServiceWorker setmovie = new ServiceWorker();
			try {
				client = new SoapObject(NAMESPACE, METHOD_NAME);
				
				//String dateVAL = "00.00.0000";
				Date currDate= new Date();
				SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
							
				currSysDate = currDateFormat.format(currDate).toString();
				SysDate = currSysDate.trim().toString();
				
				// System.out.println("Summary new SKUWise occur value bydate"+ dateVAL.toString());
				// System.out.println("Summary new SKUWise occur value IMEINo"+ uuid.toString());
				
				
				
				client.addProperty("IMEINo", uuid.toString());
				client.addProperty("bydate", dateVAL.toString());
				
				
				sse.setOutputSoapObject(client);
				sse.bodyOut = client;
				androidHttpTransport.call(SOAP_ACTION, sse);

				// This step: get file XML
				responseBody = (SoapObject) sse.getResponse();
				// remove information XML,only retrieved results that returned
				responseBody = (SoapObject) responseBody.getProperty(1);
				
				// get information XMl of tables that is returned
				table = (SoapObject) responseBody.getProperty(0);
				
					// #1
			
				//////// System.out.println("Debug: " + "dbengine.open");
				
				//chkTblStoreListContainsRow
				if(table.getPropertyCount() >= 1 )
				{
					chkTblStoreListContainsRow=1;
				}
				//////// System.out.println("chkTblStoreListContainsRow: for StoreList "+ chkTblStoreListContainsRow);
				if(chkTblStoreListContainsRow==1)
				{	
					//////// System.out.println("table - prop count: "+ table.getPropertyCount());
					int AutoId=0;
					for(int i = 0; i <= table.getPropertyCount() -1 ; i++)
					{
						tableRow = (SoapObject) table.getProperty(i);
						//////// System.out.println("i value: "+ i);
						
						
						
						String ProductId="0";
						String Product="0";
						String MRP="0";
						String Rate="0";
						String NoofStores="0";
						String OrderQty="0";
						String FreeQty="0";
						String DiscValue="0";
						String ValBeforeTax="0";
						String TaxValue="0";
						String ValAfterTax="0";
						String Lvl="0";
						String Category="0";
						String UOM="0";
						
						if (tableRow.hasProperty("ProductId")) 
						{
							if (tableRow.getProperty("ProductId").toString().isEmpty() ) 
							{
								ProductId="0";
							} 
							else
							{
								ProductId= tableRow.getProperty("ProductId").toString().trim();
							}
						} 
						if (tableRow.hasProperty("Product")) 
						{
							if (tableRow.getProperty("Product").toString().isEmpty() ) 
							{
								Product="0";
							} 
							else
							{
								Product= tableRow.getProperty("Product").toString().trim();
							}
						} 
						if (tableRow.hasProperty("MRP")) 
						{
							if (tableRow.getProperty("MRP").toString().isEmpty() ) 
							{
								MRP="0";
							} 
							else
							{
								MRP=tableRow.getProperty("MRP").toString().trim();
							}
						} 
						if (tableRow.hasProperty("Rate")) 
						{
							if (tableRow.getProperty("Rate").toString().isEmpty() ) 
							{
								Rate="0";
							} 
							else
							{
								Rate= tableRow.getProperty("Rate").toString().trim();
							}
						} 
						
						
						if (tableRow.hasProperty("NoofStores")) 
						{
							if (tableRow.getProperty("NoofStores").toString().isEmpty() ) 
							{
								NoofStores="0";
							} 
							else
							{
								NoofStores= tableRow.getProperty("NoofStores").toString().trim();
								
							}
						}
						if (tableRow.hasProperty("OrderQty")) 
						{
							if (tableRow.getProperty("OrderQty").toString().isEmpty() ) 
							{
								OrderQty="0";
							} 
							else
							{
								OrderQty= tableRow.getProperty("OrderQty").toString().trim();
								
							}
						}
						
					
						
						if (tableRow.hasProperty("FreeQty")) 
						{
							if (tableRow.getProperty("FreeQty").toString().isEmpty() ) 
							{
								FreeQty="0";
							} 
							else
							{
								FreeQty= tableRow.getProperty("FreeQty").toString().trim();
								
							}
						}
						if (tableRow.hasProperty("DiscValue")) 
						{
							if (tableRow.getProperty("DiscValue").toString().isEmpty() ) 
							{
								DiscValue="0";
							} 
							else
							{
								DiscValue= tableRow.getProperty("DiscValue").toString().trim();
								
							}
						}
						
						
						
						
						if (tableRow.hasProperty("ValBeforeTax")) 
						{
							if (tableRow.getProperty("ValBeforeTax").toString().isEmpty() ) 
							{
								ValBeforeTax="0";
							} 
							else
							{
								ValBeforeTax= tableRow.getProperty("ValBeforeTax").toString().trim();
								
							}
						} 
						if (tableRow.hasProperty("TaxValue")) 
						{
							if (tableRow.getProperty("TaxValue").toString().isEmpty() ) 
							{
								TaxValue="0";
							} 
							else
							{
								TaxValue= tableRow.getProperty("TaxValue").toString().trim();
								
							}
						} 
						
						if (tableRow.hasProperty("ValAfterTax")) 
						{
							if (tableRow.getProperty("ValAfterTax").toString().isEmpty() ) 
							{
								ValAfterTax="0";
							} 
							else
							{
								ValAfterTax= tableRow.getProperty("ValAfterTax").toString().trim();
								
							}
						}
						
						if (tableRow.hasProperty("Lvl")) 
						{
							if (tableRow.getProperty("Lvl").toString().isEmpty() ) 
							{
								Lvl="0";
							} 
							else
							{
								Lvl= tableRow.getProperty("Lvl").toString().trim();
								
							}
						}
						
						if (tableRow.hasProperty("Category")) 
						{
							if (tableRow.getProperty("Category").toString().isEmpty() ) 
							{
								Category="0";
							} 
							else
							{
								Category= tableRow.getProperty("Category").toString().trim();
								
							}
						}
						if (tableRow.hasProperty("UOM")) 
						{
							if (tableRow.getProperty("UOM").toString().isEmpty() ) 
							{
								UOM="0";
							} 
							else
							{
								UOM= tableRow.getProperty("UOM").toString().trim();
								
							}
						}
						
						
						
						
						
						AutoId= i +1;
						
						// System.out.println("Value inserting  231 AutoId:"+AutoId);
						// System.out.println("Value inserting  231 ProductId:"+ProductId);
						// System.out.println("Value inserting  231 Product:"+Product);
						// System.out.println("Value inserting  231 MRP:"+MRP);
						// System.out.println("Value inserting  231 Rate:"+Rate);
						// System.out.println("Value inserting  231 NoofStores:"+NoofStores);
						// System.out.println("Value inserting  231 OrderQty:"+OrderQty);
						// System.out.println("Value inserting  231 FreeQty:"+FreeQty);
								
						/*dbengine.savetblSKUWiseDaySummary(AutoId,ProductId,Product,MRP,
								Rate,NoofStores,OrderQty,FreeQty,DiscValue,
								ValBeforeTax,TaxValue,ValAfterTax,Lvl,Category,UOM);*/
					
						
					}	
				}
				
				//dbengine.close();;		// #4
				
				/*setmovie.director = tableRow.getProperty("Director").toString();
				setmovie.movie_name = tableRow.getProperty("Movie").toString();*/
				setmovie.director = "1";
				
				
				return setmovie;
	//return counts;
			} catch (Exception e) {
				setmovie.exceptionCode=e.getCause().getMessage();
			// System.out.println("Aman Exception occur in CallspRptGetSKUWiseDaySummary :"+e.toString());
				setmovie.director = e.toString();
				setmovie.movie_name = e.toString();
				//dbengine.close();;
				
				return setmovie;
			}

			
		}
		
		public ServiceWorker getCallspRptGetStoreWiseDaySummary(Context ctx,String uuid ,String dateVAL) 
		{
			this.context = ctx;
			PRJDatabase dbengine = new PRJDatabase(context);
			//dbengine.open();
			//String Sstat = "0";
			
			final String SOAP_ACTION = "http://tempuri.org/CallspRptGetStoreWiseDaySummary";
			final String METHOD_NAME = "CallspRptGetStoreWiseDaySummary";
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = UrlForWebService;
			// URL: L service
			decimalFormat.applyPattern(pattern);
			// NAMESPACE: must have in service page

			// METHOD_NAME: function in web service

			// SOAP_ACTION = NAMESPACE + METHOD_NAME

			SoapObject table = null; // Contains table of dataset that returned
										// through SoapObject
			SoapObject client = null; // Its the client petition to the web service
			SoapObject tableRow = null; // Contains row of table
			SoapObject responseBody = null; // Contains XML content of dataset
			
			//SoapObject param
			HttpTransportSE transport = null; // That call webservice
			SoapSerializationEnvelope sse = null;

			
			sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			sse.addMapping(NAMESPACE, "ServiceWorker", this.getClass());
			// Note if class name isn't "movie" ,you must change
			sse.dotNet = true; // if WebService written .Net is result=true
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					URL,timeout);

			ServiceWorker setmovie = new ServiceWorker();
			try {
				client = new SoapObject(NAMESPACE, METHOD_NAME);
				
				//String dateVAL = "00.00.0000";
				Date currDate= new Date();
				SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
							
				currSysDate = currDateFormat.format(currDate).toString();
				SysDate = currSysDate.trim().toString();
				
				// System.out.println("Summary new SKUWise occur value bydate"+ dateVAL.toString());
				// System.out.println("Summary new SKUWise occur value IMEINo"+ uuid.toString());
				
				
				
				client.addProperty("IMEINo", uuid.toString());
				client.addProperty("bydate", dateVAL.toString());
				
				
				sse.setOutputSoapObject(client);
				sse.bodyOut = client;
				androidHttpTransport.call(SOAP_ACTION, sse);

				// This step: get file XML
				responseBody = (SoapObject) sse.getResponse();
				// remove information XML,only retrieved results that returned
				responseBody = (SoapObject) responseBody.getProperty(1);
				
				// get information XMl of tables that is returned
				table = (SoapObject) responseBody.getProperty(0);
				
						// #1
			
				//////// System.out.println("Debug: " + "dbengine.open");
				
				//chkTblStoreListContainsRow
				if(table.getPropertyCount() >= 1 )
				{
					chkTblStoreListContainsRow=1;
				}
				//////// System.out.println("chkTblStoreListContainsRow: for StoreList "+ chkTblStoreListContainsRow);
				if(chkTblStoreListContainsRow==1)
				{	
					//////// System.out.println("table - prop count: "+ table.getPropertyCount());
					int AutoId=0;
					for(int i = 0; i <= table.getPropertyCount() -1 ; i++)
					{
						tableRow = (SoapObject) table.getProperty(i);
						//////// System.out.println("i value: "+ i);
						
						
						
						 String Store="0";
						 String LinesperBill="0";
						 String StockValue="0";
						 String DiscValue="0";
						 String ValBeforeTax="0";
						 String TaxValue="0";
						 String ValAfterTax="0";
						 String Lvl="0";
						 String StockQty="0";
						
						if (tableRow.hasProperty("Store")) 
						{
							if (tableRow.getProperty("Store").toString().isEmpty() ) 
							{
								Store="0";
							} 
							else
							{
								Store= tableRow.getProperty("Store").toString().trim();
							}
						} 
						if (tableRow.hasProperty("LinesperBill")) 
						{
							if (tableRow.getProperty("LinesperBill").toString().isEmpty() ) 
							{
								LinesperBill="0";
							} 
							else
							{
								LinesperBill= tableRow.getProperty("LinesperBill").toString().trim();
							}
						} 
						if (tableRow.hasProperty("StockValue")) 
						{
							if (tableRow.getProperty("StockValue").toString().isEmpty() ) 
							{
								StockValue="0";
							} 
							else
							{
								StockValue=tableRow.getProperty("StockValue").toString().trim();
							}
						} 
						
						
						
						if (tableRow.hasProperty("DiscValue")) 
						{
							if (tableRow.getProperty("DiscValue").toString().isEmpty() ) 
							{
								DiscValue="0";
							} 
							else
							{
								DiscValue= tableRow.getProperty("DiscValue").toString().trim();
							}
						} 
						
						
						if (tableRow.hasProperty("ValBeforeTax")) 
						{
							if (tableRow.getProperty("ValBeforeTax").toString().isEmpty() ) 
							{
								ValBeforeTax="0";
							} 
							else
							{
								ValBeforeTax= tableRow.getProperty("ValBeforeTax").toString().trim();
								
							}
						}
						if (tableRow.hasProperty("TaxValue")) 
						{
							if (tableRow.getProperty("TaxValue").toString().isEmpty() ) 
							{
								TaxValue="0";
							} 
							else
							{
								TaxValue= tableRow.getProperty("TaxValue").toString().trim();
								
							}
						}
						
						
						
						if (tableRow.hasProperty("ValAfterTax")) 
						{
							if (tableRow.getProperty("ValAfterTax").toString().isEmpty() ) 
							{
								ValAfterTax="0";
							} 
							else
							{
								ValAfterTax= tableRow.getProperty("ValAfterTax").toString().trim();
								
							}
						}
						if (tableRow.hasProperty("Lvl")) 
						{
							if (tableRow.getProperty("Lvl").toString().isEmpty() ) 
							{
								Lvl="0";
							} 
							else
							{
								Lvl= tableRow.getProperty("Lvl").toString().trim();
								
							}
						}
						
						
						
						
						
						
						
						AutoId= i +1;
						
						
								
					/*	dbengine.savetblStoreWiseDaySummary(AutoId,Store,LinesperBill,StockValue,
								DiscValue,ValBeforeTax,TaxValue,ValAfterTax,Lvl);*/
					
						
					}	
				}
				
				//dbengine.close();;		// #4
				
				/*setmovie.director = tableRow.getProperty("Director").toString();
				setmovie.movie_name = tableRow.getProperty("Movie").toString();*/
				setmovie.director = "1";
				
				
				return setmovie;
	//return counts;
			} catch (Exception e) {
				setmovie.exceptionCode=e.getCause().getMessage();
			// System.out.println("Aman Exception occur in GetStoreListMR :"+e.toString());
				setmovie.director = e.toString();
				setmovie.movie_name = e.toString();
				//dbengine.close();;
				return setmovie;
			}

			
		}
		
		public ServiceWorker getCallspRptGetStoreSKUWiseDaySummary(Context ctx,String uuid ,String dateVAL) 
		{
			this.context = ctx;
			PRJDatabase dbengine = new PRJDatabase(context);
			//dbengine.open();	
			//String Sstat = "0";
			
			final String SOAP_ACTION = "http://tempuri.org/CallspRptGetStoreSKUWiseDaySummary";
			final String METHOD_NAME = "CallspRptGetStoreSKUWiseDaySummary";
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = UrlForWebService;
			// URL: L service
			decimalFormat.applyPattern(pattern);
			// NAMESPACE: must have in service page

			// METHOD_NAME: function in web service

			// SOAP_ACTION = NAMESPACE + METHOD_NAME

			SoapObject table = null; // Contains table of dataset that returned
										// through SoapObject
			SoapObject client = null; // Its the client petition to the web service
			SoapObject tableRow = null; // Contains row of table
			SoapObject responseBody = null; // Contains XML content of dataset
			
			//SoapObject param
			HttpTransportSE transport = null; // That call webservice
			SoapSerializationEnvelope sse = null;

			
			sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			sse.addMapping(NAMESPACE, "ServiceWorker", this.getClass());
			// Note if class name isn't "movie" ,you must change
			sse.dotNet = true; // if WebService written .Net is result=true
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					URL,timeout);

			ServiceWorker setmovie = new ServiceWorker();
			try {
				client = new SoapObject(NAMESPACE, METHOD_NAME);
				
				//String dateVAL = "00.00.0000";
				Date currDate= new Date();
				SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
							
				currSysDate = currDateFormat.format(currDate).toString();
				SysDate = currSysDate.trim().toString();
				
				// System.out.println("Summary new SKUWise occur value bydate"+ dateVAL.toString());
				// System.out.println("Summary new SKUWise occur value IMEINo"+ uuid.toString());
				
				
				
				client.addProperty("IMEINo", uuid.toString());
				client.addProperty("bydate", dateVAL.toString());
				
				
				sse.setOutputSoapObject(client);
				sse.bodyOut = client;
				androidHttpTransport.call(SOAP_ACTION, sse);

				// This step: get file XML
				responseBody = (SoapObject) sse.getResponse();
				// remove information XML,only retrieved results that returned
				responseBody = (SoapObject) responseBody.getProperty(1);
				
				// get information XMl of tables that is returned
				table = (SoapObject) responseBody.getProperty(0);
				
					// #1
			
				//////// System.out.println("Debug: " + "dbengine.open");
				
				//chkTblStoreListContainsRow
				if(table.getPropertyCount() >= 1 )
				{
					chkTblStoreListContainsRow=1;
				}
				//////// System.out.println("chkTblStoreListContainsRow: for StoreList "+ chkTblStoreListContainsRow);
				if(chkTblStoreListContainsRow==1)
				{	
					//////// System.out.println("table - prop count: "+ table.getPropertyCount());
					int AutoId=0;
					for(int i = 0; i <= table.getPropertyCount() -1 ; i++)
					{
						tableRow = (SoapObject) table.getProperty(i);
						//////// System.out.println("i value: "+ i);
						
						
						String ProductId="0";
						 String Product="0";
						 String MRP="0";
						 String Rate="0";
						 String OrderQty="0";
						 String FreeQty="0";
						 String DiscValue="0";
						 String ValBeforeTax="0";
						 String TaxValue="0";
						 String ValAfterTax="0";
						 String Lvl="0";
						 String StoreId="0";
						 String StockQty="0";
						
						
						if (tableRow.hasProperty("ProductId")) 
						{
							if (tableRow.getProperty("ProductId").toString().isEmpty() ) 
							{
								ProductId="0";
							} 
							else
							{
								ProductId= tableRow.getProperty("ProductId").toString().trim();
							}
						} 
						if (tableRow.hasProperty("Product")) 
						{
							if (tableRow.getProperty("Product").toString().isEmpty() ) 
							{
								Product="0";
							} 
							else
							{
								Product= tableRow.getProperty("Product").toString().trim();
							}
						} 
						if (tableRow.hasProperty("MRP")) 
						{
							if (tableRow.getProperty("MRP").toString().isEmpty() ) 
							{
								MRP="0";
							} 
							else
							{
								MRP=tableRow.getProperty("MRP").toString().trim();
							}
						} 
						if (tableRow.hasProperty("Rate")) 
						{
							if (tableRow.getProperty("Rate").toString().isEmpty() ) 
							{
								Rate="0";
							} 
							else
							{
								Rate= tableRow.getProperty("Rate").toString().trim();
							}
						} 
						
						
						
						if (tableRow.hasProperty("OrderQty")) 
						{
							if (tableRow.getProperty("OrderQty").toString().isEmpty() ) 
							{
								OrderQty="0";
							} 
							else
							{
								OrderQty= tableRow.getProperty("OrderQty").toString().trim();
								
							}
						}
						
						
						
						
						if (tableRow.hasProperty("FreeQty")) 
						{
							if (tableRow.getProperty("FreeQty").toString().isEmpty() ) 
							{
								FreeQty="0";
							} 
							else
							{
								FreeQty= tableRow.getProperty("FreeQty").toString().trim();
								
							}
						}
						if (tableRow.hasProperty("DiscValue")) 
						{
							if (tableRow.getProperty("DiscValue").toString().isEmpty() ) 
							{
								DiscValue="0";
							} 
							else
							{
								DiscValue= tableRow.getProperty("DiscValue").toString().trim();
								
							}
						}
						
						
						
						
						if (tableRow.hasProperty("ValBeforeTax")) 
						{
							if (tableRow.getProperty("ValBeforeTax").toString().isEmpty() ) 
							{
								ValBeforeTax="0";
							} 
							else
							{
								ValBeforeTax= tableRow.getProperty("ValBeforeTax").toString().trim();
								
							}
						} 
						
						
						
						if (tableRow.hasProperty("TaxValue")) 
						{
							if (tableRow.getProperty("TaxValue").toString().isEmpty() ) 
							{
								TaxValue="0";
							} 
							else
							{
								TaxValue= tableRow.getProperty("TaxValue").toString().trim();
								
							}
						} 
						
						if (tableRow.hasProperty("ValAfterTax")) 
						{
							if (tableRow.getProperty("ValAfterTax").toString().isEmpty() ) 
							{
								ValAfterTax="0";
							} 
							else
							{
								ValAfterTax= tableRow.getProperty("ValAfterTax").toString().trim();
								
							}
						}
						
						if (tableRow.hasProperty("Lvl")) 
						{
							if (tableRow.getProperty("Lvl").toString().isEmpty() ) 
							{
								Lvl="0";
							} 
							else
							{
								Lvl= tableRow.getProperty("Lvl").toString().trim();
								
							}
						}
						
						if (tableRow.hasProperty("StoreId")) 
						{
							if (tableRow.getProperty("StoreId").toString().isEmpty() ) 
							{
								StoreId="0";
							} 
							else
							{
								StoreId= tableRow.getProperty("StoreId").toString().trim();
								
							}
						}
						
						if (tableRow.hasProperty("StockQty")) 
						{
							if (tableRow.getProperty("StockQty").toString().isEmpty() ) 
							{
								StockQty="0";
							} 
							else
							{
								StockQty= tableRow.getProperty("StockQty").toString().trim();
								
							}
						}
						
						
						
						
						
						
						AutoId= i +1;
						
						
						/*dbengine.savetblStoreSKUWiseDaySummary(AutoId,ProductId,Product,MRP,
								Rate,OrderQty,FreeQty,DiscValue,
								ValBeforeTax,TaxValue,ValAfterTax,Lvl,StoreId,StockQty);*/
					
						
					}	
				}
				
				//dbengine.close();;		// #4
				
				/*setmovie.director = tableRow.getProperty("Director").toString();
				setmovie.movie_name = tableRow.getProperty("Movie").toString();*/
				setmovie.director = "1";
				
				
				return setmovie;
	//return counts;
			} catch (Exception e) {
				setmovie.exceptionCode=e.getCause().getMessage();
			// System.out.println("Aman Exception occur in GetStoreListMR :"+e.toString());
				setmovie.director = e.toString();
				setmovie.movie_name = e.toString();
				//dbengine.close();;
				
				return setmovie;
			}

			
		}
		
		public ServiceWorker getCallspPDAGetDaySummary(Context ctx,String uuid ,String dateVAL) 
		{
			this.context = ctx;
			PRJDatabase dbengine = new PRJDatabase(context);
			//dbengine.open();	
			//String Sstat = "0";
			
			final String SOAP_ACTION = "http://tempuri.org/CallspPDAGetDaySummary";
			final String METHOD_NAME = "CallspPDAGetDaySummary";
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = UrlForWebService;
			// URL: L service
			decimalFormat.applyPattern(pattern);
			// NAMESPACE: must have in service page

			// METHOD_NAME: function in web service

			// SOAP_ACTION = NAMESPACE + METHOD_NAME

			SoapObject table = null; // Contains table of dataset that returned
										// through SoapObject
			SoapObject client = null; // Its the client petition to the web service
			SoapObject tableRow = null; // Contains row of table
			SoapObject responseBody = null; // Contains XML content of dataset
			
			//SoapObject param
			HttpTransportSE transport = null; // That call webservice
			SoapSerializationEnvelope sse = null;

			
			sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			sse.addMapping(NAMESPACE, "ServiceWorker", this.getClass());
			// Note if class name isn't "movie" ,you must change
			sse.dotNet = true; // if WebService written .Net is result=true
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					URL,timeout);

			ServiceWorker setmovie = new ServiceWorker();
			try {
				client = new SoapObject(NAMESPACE, METHOD_NAME);
				
				//String dateVAL = "00.00.0000";
				Date currDate= new Date();
				SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
							
				currSysDate = currDateFormat.format(currDate).toString();
				SysDate = currSysDate.trim().toString();
				
				// System.out.println("Summary new SKUWise occur value bydate"+ dateVAL.toString());
				// System.out.println("Summary new SKUWise occur value IMEINo"+ uuid.toString());
				
				
				
				client.addProperty("IMEINo", uuid.toString());
				client.addProperty("bydate", dateVAL.toString());
				
				
				sse.setOutputSoapObject(client);
				sse.bodyOut = client;
				androidHttpTransport.call(SOAP_ACTION, sse);

				// This step: get file XML
				responseBody = (SoapObject) sse.getResponse();
				// remove information XML,only retrieved results that returned
				responseBody = (SoapObject) responseBody.getProperty(1);
				
				// get information XMl of tables that is returned
				table = (SoapObject) responseBody.getProperty(0);
				
					// #1
			
				//////// System.out.println("Debug: " + "dbengine.open");
				
				//chkTblStoreListContainsRow
				if(table.getPropertyCount() >= 1 )
				{
					chkTblStoreListContainsRow=1;
				}
				//////// System.out.println("chkTblStoreListContainsRow: for StoreList "+ chkTblStoreListContainsRow);
				if(chkTblStoreListContainsRow==1)
				{	
					//////// System.out.println("table - prop count: "+ table.getPropertyCount());
					int AutoId=0;
					for(int i = 0; i <= table.getPropertyCount() -1 ; i++)
					{
						tableRow = (SoapObject) table.getProperty(i);
						//////// System.out.println("i value: "+ i);
						
						
						String Measures="0";
						 String TodaysSummary="0";
						 String MTDSummary="0";
						 
						
						
						if (tableRow.hasProperty("Measures")) 
						{
							if (tableRow.getProperty("Measures").toString().isEmpty() ) 
							{
								Measures="0";
							} 
							else
							{
								Measures= tableRow.getProperty("Measures").toString().trim();
							}
						} 
						if (tableRow.hasProperty("TodaysSummary")) 
						{
							if (tableRow.getProperty("TodaysSummary").toString().isEmpty() ) 
							{
								TodaysSummary="0";
							} 
							else
							{
								TodaysSummary= tableRow.getProperty("TodaysSummary").toString().trim();
							}
						} 
						if (tableRow.hasProperty("MTDSummary")) 
						{
							if (tableRow.getProperty("MTDSummary").toString().isEmpty() ) 
							{
								MTDSummary="0";
							} 
							else
							{
								MTDSummary=tableRow.getProperty("MTDSummary").toString().trim();
							}
						} 
						
						
						
						
						AutoId= i +1;
						
						
						dbengine.savetblAllSummary(AutoId,Measures,TodaysSummary,MTDSummary);
					
						
					}	
				}
				
				//dbengine.close();;		// #4
				
				/*setmovie.director = tableRow.getProperty("Director").toString();
				setmovie.movie_name = tableRow.getProperty("Movie").toString();*/
				setmovie.director = "1";
				
				
				return setmovie;
	//return counts;
			} catch (Exception e) {
				setmovie.exceptionCode=e.getCause().getMessage();
			// System.out.println("Aman Exception occur in GetStoreListMR :"+e.toString());
				setmovie.director = e.toString();
				setmovie.movie_name = e.toString();
				//dbengine.close();;
				
				return setmovie;
			}

			
		}
		
		
		public ServiceWorker getCallspRptGetSKUWiseMTDSummary(Context ctx,String uuid ,String dateVAL) 
		{
			this.context = ctx;
			PRJDatabase dbengine = new PRJDatabase(context);
			//dbengine.open();
			//String Sstat = "0";
			
			final String SOAP_ACTION = "http://tempuri.org/CallspRptGetSKUWiseMTDSummary";
			final String METHOD_NAME = "CallspRptGetSKUWiseMTDSummary";
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = UrlForWebService;
			// URL: L service
			decimalFormat.applyPattern(pattern);
			// NAMESPACE: must have in service page

			// METHOD_NAME: function in web service

			// SOAP_ACTION = NAMESPACE + METHOD_NAME

			SoapObject table = null; // Contains table of dataset that returned
										// through SoapObject
			SoapObject client = null; // Its the client petition to the web service
			SoapObject tableRow = null; // Contains row of table
			SoapObject responseBody = null; // Contains XML content of dataset
			
			//SoapObject param
			HttpTransportSE transport = null; // That call webservice
			SoapSerializationEnvelope sse = null;

			
			sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			sse.addMapping(NAMESPACE, "ServiceWorker", this.getClass());
			// Note if class name isn't "movie" ,you must change
			sse.dotNet = true; // if WebService written .Net is result=true
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					URL,timeout);

			ServiceWorker setmovie = new ServiceWorker();
			try {
				client = new SoapObject(NAMESPACE, METHOD_NAME);
				
				//String dateVAL = "00.00.0000";
				Date currDate= new Date();
				SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
							
				currSysDate = currDateFormat.format(currDate).toString();
				SysDate = currSysDate.trim().toString();
				
				// System.out.println("Summary new SKUWise occur value bydate"+ dateVAL.toString());
				// System.out.println("Summary new SKUWise occur value IMEINo"+ uuid.toString());
				
				
				
				client.addProperty("IMEINo", uuid.toString());
				client.addProperty("bydate", dateVAL.toString());
				
				
				sse.setOutputSoapObject(client);
				sse.bodyOut = client;
				androidHttpTransport.call(SOAP_ACTION, sse);

				// This step: get file XML
				responseBody = (SoapObject) sse.getResponse();
				// remove information XML,only retrieved results that returned
				responseBody = (SoapObject) responseBody.getProperty(1);
				
				// get information XMl of tables that is returned
				table = (SoapObject) responseBody.getProperty(0);
				
					// #1
			
				//////// System.out.println("Debug: " + "dbengine.open");
				
				//chkTblStoreListContainsRow
				if(table.getPropertyCount() >= 1 )
				{
					chkTblStoreListContainsRow=1;
				}
				//////// System.out.println("chkTblStoreListContainsRow: for StoreList "+ chkTblStoreListContainsRow);
				if(chkTblStoreListContainsRow==1)
				{	
					//////// System.out.println("table - prop count: "+ table.getPropertyCount());
					int AutoId=0;
					for(int i = 0; i <= table.getPropertyCount() -1 ; i++)
					{
						tableRow = (SoapObject) table.getProperty(i);
						//////// System.out.println("i value: "+ i);
						
						
						
						String ProductId="0";
						String Product="0";
						String MRP="0";
						String Rate="0";
						String NoofStores="0";
						String OrderQty="0";
						String FreeQty="0";
						String DiscValue="0";
						String ValBeforeTax="0";
						String TaxValue="0";
						String ValAfterTax="0";
						String Lvl="0";
						String Category="0";
						String UOM="0";
						
						if (tableRow.hasProperty("ProductId")) 
						{
							if (tableRow.getProperty("ProductId").toString().isEmpty() ) 
							{
								ProductId="0";
							} 
							else
							{
								ProductId= tableRow.getProperty("ProductId").toString().trim();
							}
						} 
						if (tableRow.hasProperty("Product")) 
						{
							if (tableRow.getProperty("Product").toString().isEmpty() ) 
							{
								Product="0";
							} 
							else
							{
								Product= tableRow.getProperty("Product").toString().trim();
							}
						} 
						if (tableRow.hasProperty("MRP")) 
						{
							if (tableRow.getProperty("MRP").toString().isEmpty() ) 
							{
								MRP="0";
							} 
							else
							{
								MRP=tableRow.getProperty("MRP").toString().trim();
							}
						} 
						if (tableRow.hasProperty("Rate")) 
						{
							if (tableRow.getProperty("Rate").toString().isEmpty() ) 
							{
								Rate="0";
							} 
							else
							{
								Rate= tableRow.getProperty("Rate").toString().trim();
							}
						} 
						
						
						if (tableRow.hasProperty("NoofStores")) 
						{
							if (tableRow.getProperty("NoofStores").toString().isEmpty() ) 
							{
								NoofStores="0";
							} 
							else
							{
								NoofStores= tableRow.getProperty("NoofStores").toString().trim();
								
							}
						}
						if (tableRow.hasProperty("OrderQty")) 
						{
							if (tableRow.getProperty("OrderQty").toString().isEmpty() ) 
							{
								OrderQty="0";
							} 
							else
							{
								OrderQty= tableRow.getProperty("OrderQty").toString().trim();
								
							}
						}
						
					
						
						if (tableRow.hasProperty("FreeQty")) 
						{
							if (tableRow.getProperty("FreeQty").toString().isEmpty() ) 
							{
								FreeQty="0";
							} 
							else
							{
								FreeQty= tableRow.getProperty("FreeQty").toString().trim();
								
							}
						}
						if (tableRow.hasProperty("DiscValue")) 
						{
							if (tableRow.getProperty("DiscValue").toString().isEmpty() ) 
							{
								DiscValue="0";
							} 
							else
							{
								DiscValue= tableRow.getProperty("DiscValue").toString().trim();
								
							}
						}
						
						
						
						
						if (tableRow.hasProperty("ValBeforeTax")) 
						{
							if (tableRow.getProperty("ValBeforeTax").toString().isEmpty() ) 
							{
								ValBeforeTax="0";
							} 
							else
							{
								ValBeforeTax= tableRow.getProperty("ValBeforeTax").toString().trim();
								
							}
						} 
						if (tableRow.hasProperty("TaxValue")) 
						{
							if (tableRow.getProperty("TaxValue").toString().isEmpty() ) 
							{
								TaxValue="0";
							} 
							else
							{
								TaxValue= tableRow.getProperty("TaxValue").toString().trim();
								
							}
						} 
						
						if (tableRow.hasProperty("ValAfterTax")) 
						{
							if (tableRow.getProperty("ValAfterTax").toString().isEmpty() ) 
							{
								ValAfterTax="0";
							} 
							else
							{
								ValAfterTax= tableRow.getProperty("ValAfterTax").toString().trim();
								
							}
						}
						
						if (tableRow.hasProperty("Lvl")) 
						{
							if (tableRow.getProperty("Lvl").toString().isEmpty() ) 
							{
								Lvl="0";
							} 
							else
							{
								Lvl= tableRow.getProperty("Lvl").toString().trim();
								
							}
						}
						
						if (tableRow.hasProperty("Category")) 
						{
							if (tableRow.getProperty("Category").toString().isEmpty() ) 
							{
								Category="0";
							} 
							else
							{
								Category= tableRow.getProperty("Category").toString().trim();
								
							}
						}
						if (tableRow.hasProperty("UOM")) 
						{
							if (tableRow.getProperty("UOM").toString().isEmpty() ) 
							{
								UOM="0";
							} 
							else
							{
								UOM= tableRow.getProperty("UOM").toString().trim();
								
							}
						}
						
						
						
						
						
						AutoId= i +1;
						
						// System.out.println("Value inserting  231 AutoId:"+AutoId);
						// System.out.println("Value inserting  231 ProductId:"+ProductId);
						// System.out.println("Value inserting  231 Product:"+Product);
						// System.out.println("Value inserting  231 MRP:"+MRP);
						// System.out.println("Value inserting  231 Rate:"+Rate);
						// System.out.println("Value inserting  231 NoofStores:"+NoofStores);
						// System.out.println("Value inserting  231 OrderQty:"+OrderQty);
						// System.out.println("Value inserting  231 FreeQty:"+FreeQty);
								
						/*dbengine.savetblSKUWiseDaySummary(AutoId,ProductId,Product,MRP,
								Rate,NoofStores,OrderQty,FreeQty,DiscValue,
								ValBeforeTax,TaxValue,ValAfterTax,Lvl,Category,UOM);*/
					
						
					}	
				}
				
				//dbengine.close();;		// #4
				
				/*setmovie.director = tableRow.getProperty("Director").toString();
				setmovie.movie_name = tableRow.getProperty("Movie").toString();*/
				setmovie.director = "1";
				
				
				return setmovie;
	//return counts;
			} catch (Exception e) {
				setmovie.exceptionCode=e.getCause().getMessage();
			// System.out.println("Aman Exception occur in CallspRptGetSKUWiseDaySummary :"+e.toString());
				setmovie.director = e.toString();
				setmovie.movie_name = e.toString();
				//dbengine.close();;	
				return setmovie;
			}

			
		}
		
		public ServiceWorker getCallspRptGetStoreWiseMTDSummary(Context ctx,String uuid ,String dateVAL) 
		{
			this.context = ctx;
			PRJDatabase dbengine = new PRJDatabase(context);
			//dbengine.open();
			//String Sstat = "0";
			
			final String SOAP_ACTION = "http://tempuri.org/CallspRptGetStoreWiseMTDSummary";
			final String METHOD_NAME = "CallspRptGetStoreWiseMTDSummary";
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = UrlForWebService;
			// URL: L service
			decimalFormat.applyPattern(pattern);
			// NAMESPACE: must have in service page

			// METHOD_NAME: function in web service

			// SOAP_ACTION = NAMESPACE + METHOD_NAME

			SoapObject table = null; // Contains table of dataset that returned
										// through SoapObject
			SoapObject client = null; // Its the client petition to the web service
			SoapObject tableRow = null; // Contains row of table
			SoapObject responseBody = null; // Contains XML content of dataset
			
			//SoapObject param
			HttpTransportSE transport = null; // That call webservice
			SoapSerializationEnvelope sse = null;

			
			sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			sse.addMapping(NAMESPACE, "ServiceWorker", this.getClass());
			// Note if class name isn't "movie" ,you must change
			sse.dotNet = true; // if WebService written .Net is result=true
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					URL,timeout);

			ServiceWorker setmovie = new ServiceWorker();
			try {
				client = new SoapObject(NAMESPACE, METHOD_NAME);
				
				//String dateVAL = "00.00.0000";
				Date currDate= new Date();
				SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
							
				currSysDate = currDateFormat.format(currDate).toString();
				SysDate = currSysDate.trim().toString();
				
				// System.out.println("Summary new SKUWise occur value bydate"+ dateVAL.toString());
				// System.out.println("Summary new SKUWise occur value IMEINo"+ uuid.toString());
				
				
				
				client.addProperty("IMEINo", uuid.toString());
				client.addProperty("bydate", dateVAL.toString());
				
				
				sse.setOutputSoapObject(client);
				sse.bodyOut = client;
				androidHttpTransport.call(SOAP_ACTION, sse);

				// This step: get file XML
				responseBody = (SoapObject) sse.getResponse();
				// remove information XML,only retrieved results that returned
				responseBody = (SoapObject) responseBody.getProperty(1);
				
				// get information XMl of tables that is returned
				table = (SoapObject) responseBody.getProperty(0);
				
						// #1
			
				//////// System.out.println("Debug: " + "dbengine.open");
				
				//chkTblStoreListContainsRow
				if(table.getPropertyCount() >= 1 )
				{
					chkTblStoreListContainsRow=1;
				}
				//////// System.out.println("chkTblStoreListContainsRow: for StoreList "+ chkTblStoreListContainsRow);
				if(chkTblStoreListContainsRow==1)
				{	
					//////// System.out.println("table - prop count: "+ table.getPropertyCount());
					int AutoId=0;
					for(int i = 0; i <= table.getPropertyCount() -1 ; i++)
					{
						tableRow = (SoapObject) table.getProperty(i);
						//////// System.out.println("i value: "+ i);
						
						
						
						
						 String Store="0";
						 String LinesperBill="0";
						 String StockValue="0";
						 String DiscValue="0";
						 String ValBeforeTax="0";
						 String TaxValue="0";
						 String ValAfterTax="0";
						 String Lvl="0";
						
						if (tableRow.hasProperty("Store")) 
						{
							if (tableRow.getProperty("Store").toString().isEmpty() ) 
							{
								Store="0";
							} 
							else
							{
								Store= tableRow.getProperty("Store").toString().trim();
							}
						} 
						if (tableRow.hasProperty("LinesperBill")) 
						{
							if (tableRow.getProperty("LinesperBill").toString().isEmpty() ) 
							{
								LinesperBill="0";
							} 
							else
							{
								LinesperBill= tableRow.getProperty("LinesperBill").toString().trim();
							}
						} 
						if (tableRow.hasProperty("StockValue")) 
						{
							if (tableRow.getProperty("StockValue").toString().isEmpty() ) 
							{
								StockValue="0";
							} 
							else
							{
								StockValue=tableRow.getProperty("StockValue").toString().trim();
							}
						} 
						
						
						
						if (tableRow.hasProperty("DiscValue")) 
						{
							if (tableRow.getProperty("DiscValue").toString().isEmpty() ) 
							{
								DiscValue="0";
							} 
							else
							{
								DiscValue= tableRow.getProperty("DiscValue").toString().trim();
							}
						} 
						
						
						if (tableRow.hasProperty("ValBeforeTax")) 
						{
							if (tableRow.getProperty("ValBeforeTax").toString().isEmpty() ) 
							{
								ValBeforeTax="0";
							} 
							else
							{
								ValBeforeTax= tableRow.getProperty("ValBeforeTax").toString().trim();
								
							}
						}
						if (tableRow.hasProperty("TaxValue")) 
						{
							if (tableRow.getProperty("TaxValue").toString().isEmpty() ) 
							{
								TaxValue="0";
							} 
							else
							{
								TaxValue= tableRow.getProperty("TaxValue").toString().trim();
								
							}
						}
						
						
						
						if (tableRow.hasProperty("ValAfterTax")) 
						{
							if (tableRow.getProperty("ValAfterTax").toString().isEmpty() ) 
							{
								ValAfterTax="0";
							} 
							else
							{
								ValAfterTax= tableRow.getProperty("ValAfterTax").toString().trim();
								
							}
						}
						if (tableRow.hasProperty("Lvl")) 
						{
							if (tableRow.getProperty("Lvl").toString().isEmpty() ) 
							{
								Lvl="0";
							} 
							else
							{
								Lvl= tableRow.getProperty("Lvl").toString().trim();
								
							}
						}
						
						
						
						
						
						
						
						AutoId= i +1;
						
						
								
						/*dbengine.savetblStoreWiseDaySummary(AutoId,Store,LinesperBill,StockValue,
								DiscValue,ValBeforeTax,TaxValue,ValAfterTax,Lvl);*/
					
						
					}	
				}
				
				//dbengine.close();;		// #4
				
				/*setmovie.director = tableRow.getProperty("Director").toString();
				setmovie.movie_name = tableRow.getProperty("Movie").toString();*/
				setmovie.director = "1";
				
				
				return setmovie;
	//return counts;
			} catch (Exception e) {
				setmovie.exceptionCode=e.getCause().getMessage();
			// System.out.println("Aman Exception occur in GetStoreListMR :"+e.toString());
				setmovie.director = e.toString();
				setmovie.movie_name = e.toString();
				//dbengine.close();;
				return setmovie;
			}

			
		}
		
		public ServiceWorker getCallspRptGetStoreSKUWiseMTDSummary(Context ctx,String uuid ,String dateVAL) 
		{
			this.context = ctx;
			PRJDatabase dbengine = new PRJDatabase(context);
			//dbengine.open();
			//String Sstat = "0";
			
			final String SOAP_ACTION = "http://tempuri.org/CallspRptGetStoreSKUWiseMTDSummary";
			final String METHOD_NAME = "CallspRptGetStoreSKUWiseMTDSummary";
			final String NAMESPACE = "http://tempuri.org/";
			final String URL = UrlForWebService;
			// URL: L service
			decimalFormat.applyPattern(pattern);
			// NAMESPACE: must have in service page

			// METHOD_NAME: function in web service

			// SOAP_ACTION = NAMESPACE + METHOD_NAME

			SoapObject table = null; // Contains table of dataset that returned
										// through SoapObject
			SoapObject client = null; // Its the client petition to the web service
			SoapObject tableRow = null; // Contains row of table
			SoapObject responseBody = null; // Contains XML content of dataset
			
			//SoapObject param
			HttpTransportSE transport = null; // That call webservice
			SoapSerializationEnvelope sse = null;

			
			sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			sse.addMapping(NAMESPACE, "ServiceWorker", this.getClass());
			// Note if class name isn't "movie" ,you must change
			sse.dotNet = true; // if WebService written .Net is result=true
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					URL,timeout);

			ServiceWorker setmovie = new ServiceWorker();
			try {
				client = new SoapObject(NAMESPACE, METHOD_NAME);
				
				//String dateVAL = "00.00.0000";
				Date currDate= new Date();
				SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
							
				currSysDate = currDateFormat.format(currDate).toString();
				SysDate = currSysDate.trim().toString();
				
				// System.out.println("Summary new SKUWise occur value bydate"+ dateVAL.toString());
				// System.out.println("Summary new SKUWise occur value IMEINo"+ uuid.toString());
				
				
				
				client.addProperty("IMEINo", uuid.toString());
				client.addProperty("bydate", dateVAL.toString());
				
				
				sse.setOutputSoapObject(client);
				sse.bodyOut = client;
				androidHttpTransport.call(SOAP_ACTION, sse);

				// This step: get file XML
				responseBody = (SoapObject) sse.getResponse();
				// remove information XML,only retrieved results that returned
				responseBody = (SoapObject) responseBody.getProperty(1);
				
				// get information XMl of tables that is returned
				table = (SoapObject) responseBody.getProperty(0);
				
						// #1
			
				//////// System.out.println("Debug: " + "dbengine.open");
				
				//chkTblStoreListContainsRow
				if(table.getPropertyCount() >= 1 )
				{
					chkTblStoreListContainsRow=1;
				}
				//////// System.out.println("chkTblStoreListContainsRow: for StoreList "+ chkTblStoreListContainsRow);
				if(chkTblStoreListContainsRow==1)
				{	
					//////// System.out.println("table - prop count: "+ table.getPropertyCount());
					int AutoId=0;
					for(int i = 0; i <= table.getPropertyCount() -1 ; i++)
					{
						tableRow = (SoapObject) table.getProperty(i);
						//////// System.out.println("i value: "+ i);
						
						
						
					
						
						 String ProductId="0";
						 String Product="0";
						 String MRP="0";
						 String Rate="0";
						 String OrderQty="0";
						 String FreeQty="0";
						 String DiscValue="0";
						 String ValBeforeTax="0";
						 String TaxValue="0";
						 String ValAfterTax="0";
						 String Lvl="0";
						 String StoreId="0";
						 String StockQty="0";
						
						
						if (tableRow.hasProperty("ProductId")) 
						{
							if (tableRow.getProperty("ProductId").toString().isEmpty() ) 
							{
								ProductId="0";
							} 
							else
							{
								ProductId= tableRow.getProperty("ProductId").toString().trim();
							}
						} 
						if (tableRow.hasProperty("Product")) 
						{
							if (tableRow.getProperty("Product").toString().isEmpty() ) 
							{
								Product="0";
							} 
							else
							{
								Product= tableRow.getProperty("Product").toString().trim();
							}
						} 
						if (tableRow.hasProperty("MRP")) 
						{
							if (tableRow.getProperty("MRP").toString().isEmpty() ) 
							{
								MRP="0";
							} 
							else
							{
								MRP=tableRow.getProperty("MRP").toString().trim();
							}
						} 
						if (tableRow.hasProperty("Rate")) 
						{
							if (tableRow.getProperty("Rate").toString().isEmpty() ) 
							{
								Rate="0";
							} 
							else
							{
								Rate= tableRow.getProperty("Rate").toString().trim();
							}
						} 
						
						
						
						if (tableRow.hasProperty("OrderQty")) 
						{
							if (tableRow.getProperty("OrderQty").toString().isEmpty() ) 
							{
								OrderQty="0";
							} 
							else
							{
								OrderQty= tableRow.getProperty("OrderQty").toString().trim();
								
							}
						}
						
						
						
						
						if (tableRow.hasProperty("FreeQty")) 
						{
							if (tableRow.getProperty("FreeQty").toString().isEmpty() ) 
							{
								FreeQty="0";
							} 
							else
							{
								FreeQty= tableRow.getProperty("FreeQty").toString().trim();
								
							}
						}
						if (tableRow.hasProperty("DiscValue")) 
						{
							if (tableRow.getProperty("DiscValue").toString().isEmpty() ) 
							{
								DiscValue="0";
							} 
							else
							{
								DiscValue= tableRow.getProperty("DiscValue").toString().trim();
								
							}
						}
						
						
						
						
						if (tableRow.hasProperty("ValBeforeTax")) 
						{
							if (tableRow.getProperty("ValBeforeTax").toString().isEmpty() ) 
							{
								ValBeforeTax="0";
							} 
							else
							{
								ValBeforeTax= tableRow.getProperty("ValBeforeTax").toString().trim();
								
							}
						} 
						
						
						
						if (tableRow.hasProperty("TaxValue")) 
						{
							if (tableRow.getProperty("TaxValue").toString().isEmpty() ) 
							{
								TaxValue="0";
							} 
							else
							{
								TaxValue= tableRow.getProperty("TaxValue").toString().trim();
								
							}
						} 
						
						if (tableRow.hasProperty("ValAfterTax")) 
						{
							if (tableRow.getProperty("ValAfterTax").toString().isEmpty() ) 
							{
								ValAfterTax="0";
							} 
							else
							{
								ValAfterTax= tableRow.getProperty("ValAfterTax").toString().trim();
								
							}
						}
						
						if (tableRow.hasProperty("Lvl")) 
						{
							if (tableRow.getProperty("Lvl").toString().isEmpty() ) 
							{
								Lvl="0";
							} 
							else
							{
								Lvl= tableRow.getProperty("Lvl").toString().trim();
								
							}
						}
						
						if (tableRow.hasProperty("StoreId")) 
						{
							if (tableRow.getProperty("StoreId").toString().isEmpty() ) 
							{
								StoreId="0";
							} 
							else
							{
								StoreId= tableRow.getProperty("StoreId").toString().trim();
								
							}
						}
						
						if (tableRow.hasProperty("StockQty")) 
						{
							if (tableRow.getProperty("StockQty").toString().isEmpty() ) 
							{
								StockQty="0";
							} 
							else
							{
								StockQty= tableRow.getProperty("StockQty").toString().trim();
								
							}
						}
						
						
						
						
						
						
						AutoId= i +1;
						
						
						/*dbengine.savetblStoreSKUWiseDaySummary(AutoId,ProductId,Product,MRP,
								Rate,OrderQty,FreeQty,DiscValue,
								ValBeforeTax,TaxValue,ValAfterTax,Lvl,StoreId,StockQty);*/
					
						
					}	
				}
				
				//dbengine.close();;		// #4
				
				/*setmovie.director = tableRow.getProperty("Director").toString();
				setmovie.movie_name = tableRow.getProperty("Movie").toString();*/
				setmovie.director = "1";
				
				
				return setmovie;
	//return counts;
			} catch (Exception e) {
				setmovie.exceptionCode=e.getCause().getMessage();
			// System.out.println("Aman Exception occur in GetStoreListMR :"+e.toString());
				setmovie.director = e.toString();
				setmovie.movie_name = e.toString();
				//dbengine.close();;	
				return setmovie;
			}

			
		}
		
		



	public ServiceWorker getCallfnGetActualVsTargetReport(Context ctx,String uuid ,String dateVAL) //(Context ctx,int ApplicationID,String uuid)
	{
		this.context = ctx;


		int chkTblStoreListContainsRow=1;
		StringReader read;
		InputSource inputstream;
		final String SOAP_ACTION = "http://tempuri.org/fnGetActualVsTargetReport";
		final String METHOD_NAME = "fnGetActualVsTargetReport";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;

		//Create request
		SoapObject table = null; //Contains table of dataset that returned
		// through SoapObject
		SoapObject client = null; //Its the client petition to the web service
		SoapObject tableRow = null; //Contains row of table
		SoapObject responseBody = null; //Contains XML content of dataset

		//DBHelper dbengine=new DBHelper(ctx);
		PRJDatabase dbengine = new PRJDatabase(context);
		//dbengine.open();
		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;
		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		sse.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,0);

		ServiceWorker setmovie = new ServiceWorker();
		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);
			//client.addProperty("ApplicationID", ApplicationID);
			//client.addProperty("uuid", uuid.toString());

			String rID=dbengine.GetActiveRouteIDSunilAgain();



			client.addProperty("bydate", dateVAL.toString());
			client.addProperty("uuid", uuid.toString());
			client.addProperty("rID", Integer.parseInt(rID));


			sse.setOutputSoapObject(client);
			sse.bodyOut = client;
			androidHttpTransport.call(SOAP_ACTION, sse);
			responseBody = (SoapObject)sse.bodyIn;
			// This step: get file XML
			//responseBody = (SoapObject) sse.getResponse();
			int totalCount = responseBody.getPropertyCount();

			// System.out.println("Kajol 2 :"+totalCount);
			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			// System.out.println("Kajol 3 :"+name);

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);



			//  <tblSPGetDistributorDetails> <NodeID>1</NodeID> <Descr>SUDARSAN TRADERS</Descr> <Code>101338</Code> <PNodeID>8</PNodeID> </tblSPGetDistributorDetails>
			//  dbengine.deleteAllSingleCallWebServiceTable();


			int AutoId=0;

			NodeList tblGetPDAQuestionDependentMstrNode = doc.getElementsByTagName("tblActualVsTargetReport");
			for (int i = 0; i < tblGetPDAQuestionDependentMstrNode.getLength(); i++)
			{


			                         /*String QuestionID="0";
			                         String OptionID="0";
			                         String DependentQuestionID="0";*/

				String Descr="0";
				String TodayTarget="0";
				String TodayAchieved="0";
				String TodayBal="0";
				String Todayflg="0";
				String MonthTarget="0";
				String MonthAchieved="0";
				String MonthBal="0";
				String Monthflg="0";

				int ValTgtOrPrdctFlg=1;



				Element element = (Element) tblGetPDAQuestionDependentMstrNode.item(i);



				if(!element.getElementsByTagName("Descr").equals(null))
				{
					NodeList QuestionIDNode = element.getElementsByTagName("Descr");
					Element      line = (Element) QuestionIDNode.item(0);
					if(QuestionIDNode.getLength()>0)
					{
						Descr=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("TodayTarget").equals(null))
				{
					NodeList OptionIDNode = element.getElementsByTagName("TodayTarget");
					Element      line = (Element) OptionIDNode.item(0);
					if(OptionIDNode.getLength()>0)
					{
						TodayTarget=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("TodayAchieved").equals(null))
				{
					NodeList DependentQuestionIDNode = element.getElementsByTagName("TodayAchieved");
					Element      line = (Element) DependentQuestionIDNode.item(0);
					if(DependentQuestionIDNode.getLength()>0)
					{
						TodayAchieved=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("TodayBalance").equals(null))
				{
					NodeList TodayBalNode = element.getElementsByTagName("TodayBalance");
					Element      line = (Element) TodayBalNode.item(0);
					if(TodayBalNode.getLength()>0)
					{
						TodayBal=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("Todayflg").equals(null))
				{
					NodeList QuestionIDNode = element.getElementsByTagName("Todayflg");
					Element      line = (Element) QuestionIDNode.item(0);
					if(QuestionIDNode.getLength()>0)
					{
						Todayflg=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("MonthTarget").equals(null))
				{
					NodeList OptionIDNode = element.getElementsByTagName("MonthTarget");
					Element      line = (Element) OptionIDNode.item(0);
					if(OptionIDNode.getLength()>0)
					{
						MonthTarget=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("MonthAchieved").equals(null))
				{
					NodeList DependentQuestionIDNode = element.getElementsByTagName("MonthAchieved");
					Element      line = (Element) DependentQuestionIDNode.item(0);
					if(DependentQuestionIDNode.getLength()>0)
					{
						MonthAchieved=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("MonthBalance").equals(null))
				{
					NodeList MonthBalNode = element.getElementsByTagName("MonthBalance");
					Element      line = (Element) MonthBalNode.item(0);
					if(MonthBalNode.getLength()>0)
					{
						MonthBal=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("Monthflg").equals(null))
				{
					NodeList DependentQuestionIDNode = element.getElementsByTagName("Monthflg");
					Element      line = (Element) DependentQuestionIDNode.item(0);
					if(DependentQuestionIDNode.getLength()>0)
					{
						Monthflg=xmlParser.getCharacterDataFromElement(line);
					}
				}
				// System.out.println("QuestionID:"+QuestionID+"OptionID:"+OptionID+"DependentQuestionID:"+DependentQuestionID);
				AutoId= i +1;
				//1 = ProductFlag for =ValTgtOrPrdctFlg
				dbengine.savetblTargetVsAchievedSummary(AutoId,Descr,TodayTarget,TodayAchieved,TodayBal,
						Todayflg,MonthTarget,MonthAchieved,MonthBal,Monthflg,ValTgtOrPrdctFlg);

			}


//tblValueVolumeTarget
			NodeList tblValueVolumeTarget = doc.getElementsByTagName("tblValueVolumeTarget");
			for (int i = 0; i < tblValueVolumeTarget.getLength(); i++)
			{


						                         /*String QuestionID="0";
						                         String OptionID="0";
						                         String DependentQuestionID="0";*/

				String Descr="0";
				String TodayTarget="0";
				String TodayAchieved="0";
				String TodayBal="0";
				String Todayflg="0";
				String MonthTarget="0";
				String MonthAchieved="0";
				String MonthBal="0";
				String Monthflg="0";

				int ValTgtOrPrdctFlg=0;



				Element element = (Element) tblValueVolumeTarget.item(i);



				if(!element.getElementsByTagName("Descr").equals(null))
				{
					NodeList QuestionIDNode = element.getElementsByTagName("Descr");
					Element      line = (Element) QuestionIDNode.item(0);
					if(QuestionIDNode.getLength()>0)
					{
						Descr=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("TodayTarget").equals(null))
				{
					NodeList OptionIDNode = element.getElementsByTagName("TodayTarget");
					Element      line = (Element) OptionIDNode.item(0);
					if(OptionIDNode.getLength()>0)
					{
						TodayTarget=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("TodayAchieved").equals(null))
				{
					NodeList DependentQuestionIDNode = element.getElementsByTagName("TodayAchieved");
					Element      line = (Element) DependentQuestionIDNode.item(0);
					if(DependentQuestionIDNode.getLength()>0)
					{
						TodayAchieved=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("TodayBalance").equals(null))
				{
					NodeList TodayBalNode = element.getElementsByTagName("TodayBalance");
					Element      line = (Element) TodayBalNode.item(0);
					if(TodayBalNode.getLength()>0)
					{
						TodayBal=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("Todayflg").equals(null))
				{
					NodeList QuestionIDNode = element.getElementsByTagName("Todayflg");
					Element      line = (Element) QuestionIDNode.item(0);
					if(QuestionIDNode.getLength()>0)
					{
						Todayflg=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("MonthTarget").equals(null))
				{
					NodeList OptionIDNode = element.getElementsByTagName("MonthTarget");
					Element      line = (Element) OptionIDNode.item(0);
					if(OptionIDNode.getLength()>0)
					{
						MonthTarget=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("MonthAchieved").equals(null))
				{
					NodeList DependentQuestionIDNode = element.getElementsByTagName("MonthAchieved");
					Element      line = (Element) DependentQuestionIDNode.item(0);
					if(DependentQuestionIDNode.getLength()>0)
					{
						MonthAchieved=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("MonthBalance").equals(null))
				{
					NodeList MonthBalNode = element.getElementsByTagName("MonthBalance");
					Element      line = (Element) MonthBalNode.item(0);
					if(MonthBalNode.getLength()>0)
					{
						MonthBal=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("Monthflg").equals(null))
				{
					NodeList DependentQuestionIDNode = element.getElementsByTagName("Monthflg");
					Element      line = (Element) DependentQuestionIDNode.item(0);
					if(DependentQuestionIDNode.getLength()>0)
					{
						Monthflg=xmlParser.getCharacterDataFromElement(line);
					}
				}
				// System.out.println("QuestionID:"+QuestionID+"OptionID:"+OptionID+"DependentQuestionID:"+DependentQuestionID);
				AutoId= i +1;
				//1 = ProductFlag for =ValTgtOrPrdctFlg
				dbengine.savetblTargetVsAchievedSummary(AutoId,Descr,TodayTarget,TodayAchieved,TodayBal
						,Todayflg,MonthTarget,MonthAchieved,MonthBal,Monthflg,ValTgtOrPrdctFlg);

			}


			NodeList tblActualVsTargetNote = doc.getElementsByTagName("tblActualVsTargetNote");
			for (int i = 0; i < tblActualVsTargetNote.getLength(); i++)
			{


						                         /*String QuestionID="0";
						                         String OptionID="0";
						                         String DependentQuestionID="0";*/

				String Descr="0";


				int ValTgtOrPrdctFlg=1;



				Element element = (Element) tblActualVsTargetNote.item(i);



				if(!element.getElementsByTagName("MsgToDisplay").equals(null))
				{
					NodeList QuestionIDNode = element.getElementsByTagName("MsgToDisplay");
					Element      line = (Element) QuestionIDNode.item(0);
					if(QuestionIDNode.getLength()>0)
					{
						Descr=xmlParser.getCharacterDataFromElement(line);
					}
				}

				dbengine.savetblTargetVsAchievedNote(Descr);

			}

			//dbengine.close();;
			setmovie.director = "1";

			return setmovie;

		} catch (Exception e)
		{
			setmovie.exceptionCode=e.getCause().getMessage();
			//dbengine.close();;
			System.out.println("Aman Exception occur in fnSingleCallAllWebService :"+e.toString());
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();


			return setmovie;
		}
	}



	public ServiceWorker getfnCallspPDAGetDayAndMTDSummary(Context ctx ,String dateVAL,String uuid)
	{
		this.context = ctx;
		PRJDatabase dbengine = new PRJDatabase(context);
		//dbengine.open();
		//String Sstat = "0";

		final String SOAP_ACTION = "http://tempuri.org/fnCallspPDAGetDayAndMTDSummary";
		final String METHOD_NAME = "fnCallspPDAGetDayAndMTDSummary";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;
		// URL: L service
		decimalFormat.applyPattern(pattern);
		// NAMESPACE: must have in service page

		// METHOD_NAME: function in web service

		// SOAP_ACTION = NAMESPACE + METHOD_NAME

		SoapObject table = null; // Contains table of dataset that returned
		// through SoapObject
		SoapObject client = null; // Its the client petition to the web service
		SoapObject tableRow = null; // Contains row of table
		SoapObject responseBody = null; // Contains XML content of dataset

		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;


		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		sse.addMapping(NAMESPACE, "ServiceWorker", this.getClass());
		// Note if class name isn't "movie" ,you must change
		sse.dotNet = true; // if WebService written .Net is result=true
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				URL,timeout);

		ServiceWorker setmovie = new ServiceWorker();
		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);

			//String dateVAL = "00.00.0000";
			Date currDate= new Date();
			SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

			currSysDate = currDateFormat.format(currDate).toString();
			SysDate = currSysDate.trim().toString();

			// System.out.println("Summary new SKUWise occur value bydate"+ dateVAL.toString());
			// System.out.println("Summary new SKUWise occur value IMEINo"+ uuid.toString());


			//client.addProperty("bydate", dateVAL.toString());

			client.addProperty("IMEINo", uuid.toString());
			client.addProperty("bydate", dateVAL.toString());


			sse.setOutputSoapObject(client);
			sse.bodyOut = client;
			androidHttpTransport.call(SOAP_ACTION, sse);

			// This step: get file XML
			responseBody = (SoapObject) sse.getResponse();
			// remove information XML,only retrieved results that returned
			responseBody = (SoapObject) responseBody.getProperty(1);

			// get information XMl of tables that is returned
			table = (SoapObject) responseBody.getProperty(0);

			// #1

			//////// System.out.println("Debug: " + "dbengine.open");

			//chkTblStoreListContainsRow
			if(table.getPropertyCount() >= 1 )
			{
				chkTblStoreListContainsRow=1;
			}
			//////// System.out.println("chkTblStoreListContainsRow: for StoreList "+ chkTblStoreListContainsRow);
			if(chkTblStoreListContainsRow==1)
			{
				//////// System.out.println("table - prop count: "+ table.getPropertyCount());
				int AutoId=0;
				for(int i = 0; i <= table.getPropertyCount() -1 ; i++)
				{
					tableRow = (SoapObject) table.getProperty(i);
					//////// System.out.println("i value: "+ i);


					String Measures="0";
					String TodaysSummary="0";
					String MTDSummary="0";

					String TableNo="0";
					String ColorCode="0";


					if (tableRow.hasProperty("Measures"))
					{
						if (tableRow.getProperty("Measures").toString().isEmpty() )
						{
							Measures="0";
						}
						else
						{
							Measures= tableRow.getProperty("Measures").toString().trim();
						}
					}
					if (tableRow.hasProperty("TodaysSummary"))
					{
						if (tableRow.getProperty("TodaysSummary").toString().isEmpty() )
						{
							TodaysSummary="0";
						}
						else
						{
							TodaysSummary= tableRow.getProperty("TodaysSummary").toString().trim();
						}
					}
					if (tableRow.hasProperty("MTDSummary"))
					{
						if (tableRow.getProperty("MTDSummary").toString().isEmpty() )
						{
							MTDSummary="0";
						}
						else
						{
							MTDSummary=tableRow.getProperty("MTDSummary").toString().trim();
						}
					}






					if (tableRow.hasProperty("TableNo"))
					{
						if (tableRow.getProperty("TableNo").toString().isEmpty() )
						{
							TableNo="0";
						}
						else
						{
							TableNo=tableRow.getProperty("TableNo").toString().trim();
						}
					}



					if (tableRow.hasProperty("ColorCode"))
					{
						if (tableRow.getProperty("ColorCode").toString().isEmpty() )
						{
							ColorCode="0";
						}
						else
						{
							ColorCode=tableRow.getProperty("ColorCode").toString().trim();
						}
					}


					AutoId= i +1;


				//	dbengine.savetblAllSummaryDayAndMTD(AutoId,Measures,TodaysSummary,MTDSummary,TableNo,ColorCode);


				}
			}

			//dbengine.close();;		// #4

				/*setmovie.director = tableRow.getProperty("Director").toString();
				setmovie.movie_name = tableRow.getProperty("Movie").toString();*/
			setmovie.director = "1";


			return setmovie;
			//return counts;
		} catch (Exception e) {
			setmovie.exceptionCode=e.getCause().getMessage();
			// System.out.println("Aman Exception occur in GetStoreListMR :"+e.toString());
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			//dbengine.close();;

			return setmovie;
		}


	}
	public ServiceWorker getDsrRegistrationData(Context ctx,String IMEINo,String MobNo,String DOB)
	{

		this.context = ctx;
		PRJDatabase dbengine = new PRJDatabase(context);
		//dbengine.open();

		decimalFormat.applyPattern(pattern);

		int chkTblStoreListContainsRow=1;
		StringReader read;
		InputSource inputstream;
		final String SOAP_ACTION = "http://tempuri.org/fnPDAGetPersonDetailsForRegistration";
		final String METHOD_NAME = "fnPDAGetPersonDetailsForRegistration";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;
		//Create request
		SoapObject table = null; // Contains table of dataset that returned
		// through SoapObject
		SoapObject client = null; // Its the client petition to the web service
		SoapObject tableRow = null; // Contains row of table
		SoapObject responseBody = null; // Contains XML content of dataset

		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;

		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		sse.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,timeout);

		ServiceWorker setmovie = new ServiceWorker();


		// // System.out.println("Kajol 100");

		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);



			// // System.out.println("Kajol 101");
			client.addProperty("IMEINo", IMEINo.toString().trim());
			client.addProperty("MobNo", MobNo.toString().trim());
			client.addProperty("DOB", DOB.toString().trim());

			// // System.out.println("Kajol 102");
			sse.setOutputSoapObject(client);
			// // System.out.println("Kajol 103");
			sse.bodyOut = client;
			// // System.out.println("Kajol 104");

			androidHttpTransport.call(SOAP_ACTION, sse);

			// // System.out.println("Kajol 1");

			responseBody = (SoapObject)sse.bodyIn;
			// This step: get file XML
			//responseBody = (SoapObject) sse.getResponse();
			int totalCount = responseBody.getPropertyCount();

			// // System.out.println("Kajol 2 :"+totalCount);
			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			// // System.out.println("Kajol 3 :"+name);

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);

			dbengine.Delete_tblUserRegistarationStatus();
			dbengine.Delete_tblDsrRegDetails();







			NodeList tblUserRegistarationStatusNode = doc.getElementsByTagName("tblUserRegistarationStatus");
			for (int i = 0; i < tblUserRegistarationStatusNode.getLength(); i++)
			{

				String Flag="0";
				String MsgToDisplay="0";

				Element element = (Element) tblUserRegistarationStatusNode.item(i);

				if(!element.getElementsByTagName("Flag").equals(null))
				{
				NodeList FlagNode = element.getElementsByTagName("Flag");
				Element line = (Element) FlagNode.item(0);
				if(FlagNode.getLength()>0)
				{
					Flag=xmlParser.getCharacterDataFromElement(line);
				}}
				if(!element.getElementsByTagName("MsgToDisplay").equals(null))
				{
				NodeList MsgToDisplayNode = element.getElementsByTagName("MsgToDisplay");
					Element	 line = (Element) MsgToDisplayNode.item(0);
				if(MsgToDisplayNode.getLength()>0)
				{
					MsgToDisplay=xmlParser.getCharacterDataFromElement(line);
				}}

				dbengine.savetblUserRegistarationStatus(Flag,MsgToDisplay);

			}

			NodeList tblUserRegisteredPersonalDetailsNode = doc.getElementsByTagName("tblUserRegisteredPersonalDetails");
			for (int i = 0; i < tblUserRegisteredPersonalDetailsNode.getLength(); i++)
			{

				String IMEI_string="0";
				String ClickedDateTime_string="0";
				String FirstName="0";
				String LastName="0";
				String ContactNo="0";
				String DOB_string="0";
				String Gender="0";
				String IsMarried="0";
				String MarriageDate="0";
				String Qualification="0";
				String SelfieName_string="0";
				String SelfiePath_string="0";
				String EmailId="0";
				String BloodGroup="0";
				String SignName_string="0";
				String SignPath_string="0";
				String PhotoName="0";
				String PersonNodeId="0";
				String PersonNodeType="0";

				Element element = (Element) tblUserRegisteredPersonalDetailsNode.item(i);

				if(!element.getElementsByTagName("FirstName").equals(null))
				{
				NodeList FirstNameNode = element.getElementsByTagName("FirstName");
				Element line = (Element) FirstNameNode.item(0);
				if(FirstNameNode.getLength()>0)
				{
					FirstName=xmlParser.getCharacterDataFromElement(line);
				}
				}

				if(!element.getElementsByTagName("LastName").equals(null))
				{
				NodeList LastNameNode = element.getElementsByTagName("LastName");
					Element line = (Element) LastNameNode.item(0);
				if(LastNameNode.getLength()>0)
				{
					LastName=xmlParser.getCharacterDataFromElement(line);
				}}
				if(!element.getElementsByTagName("ContactNo").equals(null))
				{
				NodeList ContactNoNode = element.getElementsByTagName("ContactNo");
					Element line = (Element) ContactNoNode.item(0);
				if(ContactNoNode.getLength()>0)
				{
					ContactNo=xmlParser.getCharacterDataFromElement(line);
				}}
				if(!element.getElementsByTagName("DOB").equals(null)) {
					NodeList DOBNode = element.getElementsByTagName("DOB");
					Element line = (Element) DOBNode.item(0);
					if (DOBNode.getLength() > 0) {
						DOB_string = xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("Gender").equals(null)) {
				NodeList GenderNode = element.getElementsByTagName("Gender");
					Element line = (Element) GenderNode.item(0);
				if(GenderNode.getLength()>0)
				{
					Gender=xmlParser.getCharacterDataFromElement(line);
				}}

				if(!element.getElementsByTagName("IsMarried").equals(null)) {
				NodeList IsMarriedNode = element.getElementsByTagName("IsMarried");
					Element line = (Element) IsMarriedNode.item(0);
				if(IsMarriedNode.getLength()>0)
				{
					IsMarried=xmlParser.getCharacterDataFromElement(line);
				}}

				if(!element.getElementsByTagName("MarriageDate").equals(null)) {
					NodeList MarriageDateNode = element.getElementsByTagName("MarriageDate");
					Element	line = (Element) MarriageDateNode.item(0);
					if (MarriageDateNode.getLength() > 0) {
						MarriageDate = xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("Qualification").equals(null)) {
					NodeList QualificationNode = element.getElementsByTagName("Qualification");
					Element line = (Element) QualificationNode.item(0);
					if (QualificationNode.getLength() > 0) {
						Qualification = xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("EmailId").equals(null)) {
					NodeList EmailIdNode = element.getElementsByTagName("EmailId");
					Element	line = (Element) EmailIdNode.item(0);
					if (EmailIdNode.getLength() > 0) {
						EmailId = xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("BloodGroup").equals(null)) {
				NodeList BloodGroupNode = element.getElementsByTagName("BloodGroup");
					Element line = (Element) BloodGroupNode.item(0);
				if(BloodGroupNode.getLength()>0)
				{
					BloodGroup=xmlParser.getCharacterDataFromElement(line);
				}}


				if(!element.getElementsByTagName("PhotoName").equals(null)) {
					NodeList PhotoNameNode = element.getElementsByTagName("PhotoName");
					Element line = (Element) PhotoNameNode.item(0);
					if (PhotoNameNode.getLength() > 0) {
						PhotoName = xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("PersonNodeId").equals(null)) {
				NodeList PersonNodeIdNode = element.getElementsByTagName("PersonNodeId");
					Element line = (Element) PersonNodeIdNode.item(0);
				if(PersonNodeIdNode.getLength()>0)
				{
					PersonNodeId=xmlParser.getCharacterDataFromElement(line);
				}}
					if(!element.getElementsByTagName("PersonNodeType").equals(null)) {
				NodeList PersonNodeTypeNode = element.getElementsByTagName("PersonNodeType");
						Element	line = (Element) PersonNodeTypeNode.item(0);
				if(PersonNodeTypeNode.getLength()>0)
				{
					PersonNodeType=xmlParser.getCharacterDataFromElement(line);
				}}

				dbengine.savetblDsrRegDetails(IMEI_string,ClickedDateTime_string,FirstName,LastName,ContactNo,DOB_string,Gender,IsMarried,MarriageDate,Qualification,SelfieName_string,SelfiePath_string,EmailId,BloodGroup,SignName_string,SignPath_string,0,PhotoName,PersonNodeId,PersonNodeType);

			}




			setmovie.director = "1";
			//dbengine.close();;
			return setmovie;

		} catch (Exception e) {
			setmovie.exceptionCode=e.getCause().getMessage();
			// System.out.println("Aman Exception occur in GetIMEIVersionDetailStatusNew :"+e.toString());
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			//dbengine.close();;

			return setmovie;
		}





	}



	public ServiceWorker fnGetPDACollectionMaster(Context ctx, String dateVAL, String uuid, String rID )
	{
		this.context = ctx;

		PRJDatabase dbengine = new PRJDatabase(context);
		String RouteType="0";
		try
		{
			//dbengine.open();
			String RouteID=dbengine.GetActiveRouteID();
			RouteType=dbengine.FetchRouteType(RouteID);
			//dbengine.close();;
			System.out.println("hi"+RouteType);
		}
		catch(Exception e)
		{
			System.out.println("error"+e);
		}

		//dbengine.open();
		dbengine.deleteAllCollectionTables();

		final String SOAP_ACTION = "http://tempuri.org/fnGetPDACollectionMaster";
		final String METHOD_NAME = "fnGetPDACollectionMaster";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;

		SoapObject table = null; // Contains table of dataset that returned
		// through SoapObject
		SoapObject client = null; // Its the client petition to the web service
		SoapObject tableRow = null; // Contains row of table
		SoapObject responseBody = null; // Contains XML content of dataset

		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;

		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		sse.addMapping(NAMESPACE, "ServiceWorker", this.getClass());
		// Note if class name isn't "ABC_CLASS_NAME" ,you must change
		sse.dotNet = true; // if WebService written .Net is result=true
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				URL,timeout);

		ServiceWorker setmovie = new ServiceWorker();
		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);

			Date currDate= new Date();
			SimpleDateFormat currDateFormat =new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);

			currSysDate = currDateFormat.format(currDate).toString();
			SysDate = currSysDate.trim().toString();

			/*// System.out.println("Aman Exception occur value bydate"+ dateVAL.toString());
			// System.out.println("Aman Exception occur value IMEINo"+ uuid.toString());
			// System.out.println("Aman Exception occur value rID"+ rID.toString());
			// System.out.println("Aman Exception occur value SysDate"+ SysDate.toString());
			// System.out.println("Aman Exception occur value AppVersionID"+ dbengine.AppVersionID.toString());
			*/
			client.addProperty("bydate", dateVAL.toString());
			client.addProperty("IMEINo", uuid.toString());
			client.addProperty("rID", rID.toString());
			client.addProperty("RouteType", RouteType);
			client.addProperty("SysDate", SysDate.toString());
			client.addProperty("AppVersionID", dbengine.AppVersionID.toString());



			sse.setOutputSoapObject(client);
			sse.bodyOut = client;
			System.out.println("S1");

			androidHttpTransport.call(SOAP_ACTION, sse);
			responseBody = (SoapObject)sse.bodyIn;


			int totalCount = responseBody.getPropertyCount();

			// System.out.println("Kajol 2 :"+totalCount);
			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			// System.out.println("Kajol 3 :"+name);

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);
			System.out.println("shivam4");

			//   //dbengine.open();

			NodeList tblBankMasterNode = doc.getElementsByTagName("tblBankMaster");
			for (int i = 0; i < tblBankMasterNode.getLength(); i++)
			{

				String BankId="0";
				String	BankName="0";
				String LoginIdIns="0";
				String TimeStampIns="0";
				String LoginIdUpd="0";
				String TimeStampUpd="0";


				Element element = (Element) tblBankMasterNode.item(i);

				if(!element.getElementsByTagName("BankId").equals(null))
				{

					NodeList BankIdNode = element.getElementsByTagName("BankId");
					Element     line = (Element) BankIdNode.item(0);

					if(BankIdNode.getLength()>0)
					{

						BankId=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("BankName").equals(null))
				{

					NodeList BankNameNode = element.getElementsByTagName("BankName");
					Element     line = (Element) BankNameNode.item(0);

					if(BankNameNode.getLength()>0)
					{

						BankName=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("LoginIdIns").equals(null))
				{

					NodeList LoginIdInsNode = element.getElementsByTagName("LoginIdIns");
					Element     line = (Element) LoginIdInsNode.item(0);

					if(LoginIdInsNode.getLength()>0)
					{

						LoginIdIns=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("TimeStampIns").equals(null))
				{

					NodeList TimeStampInsNode = element.getElementsByTagName("TimeStampIns");
					Element     line = (Element) TimeStampInsNode.item(0);

					if(TimeStampInsNode.getLength()>0)
					{

						TimeStampIns=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("LoginIdUpd").equals(null))
				{

					NodeList LoginIdUpdNode = element.getElementsByTagName("LoginIdUpd");
					Element     line = (Element) LoginIdUpdNode.item(0);

					if(LoginIdUpdNode.getLength()>0)
					{

						LoginIdUpd=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("TimeStampUpd").equals(null))
				{

					NodeList TimeStampUpdNode = element.getElementsByTagName("TimeStampUpd");
					Element     line = (Element) TimeStampUpdNode.item(0);

					if(TimeStampUpdNode.getLength()>0)
					{

						TimeStampUpd=xmlParser.getCharacterDataFromElement(line);
					}
				}


			//	dbengine.savetblBankMaster(BankId, BankName, LoginIdIns, TimeStampIns, LoginIdUpd, TimeStampUpd);
			}

			NodeList tblInstrumentMasterNode = doc.getElementsByTagName("tblInstrumentMaster");
			for (int i = 0; i < tblInstrumentMasterNode.getLength(); i++)
			{

				String InstrumentModeId="0";
				String	InstrumentMode="0";
				String InstrumentType="0";



				Element element = (Element) tblInstrumentMasterNode.item(i);

				if(!element.getElementsByTagName("InstrumentModeId").equals(null))
				{

					NodeList InstrumentModeIdNode = element.getElementsByTagName("InstrumentModeId");
					Element     line = (Element) InstrumentModeIdNode.item(0);

					if(InstrumentModeIdNode.getLength()>0)
					{

						InstrumentModeId=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("InstrumentMode").equals(null))
				{

					NodeList InstrumentModeNode = element.getElementsByTagName("InstrumentMode");
					Element     line = (Element) InstrumentModeNode.item(0);

					if(InstrumentModeNode.getLength()>0)
					{

						InstrumentMode=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("InstrumentType").equals(null))
				{

					NodeList InstrumentTypeNode = element.getElementsByTagName("InstrumentType");
					Element     line = (Element) InstrumentTypeNode.item(0);

					if(InstrumentTypeNode.getLength()>0)
					{

						InstrumentType=xmlParser.getCharacterDataFromElement(line);
					}
				}


				//dbengine.savetblInstrumentMaster(InstrumentModeId, InstrumentMode, InstrumentType);
			}

			setmovie.director = "1";
			// System.out.println("ServiceWorkerNitish getallStores Completed ");
			flagExecutedServiceSuccesfully=40;
			return setmovie;


		} catch (Exception e) {
			setmovie.exceptionCode=e.getCause().getMessage();
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			flagExecutedServiceSuccesfully=0;
			//dbengine.close();;
			return setmovie;
		}

	}





	public ServiceWorker fnGetVanStockData(Context ctx,String IMEINo)
	{
		this.context = ctx;
		String querryString="";
		String dbrId="123";
		PRJDatabase dbengine = new PRJDatabase(context);

		final String SOAP_ACTION = "http://tempuri.org/fnSendGetGetVanStockDet";

		final String METHOD_NAME = "fnSendGetGetVanStockDet";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;

		// through SoapObject
		SoapObject client = null; //Its the client petition to the web service
		SoapObject responseBody = null; //Contains XML content of dataset




		//SoapObject param
		SoapSerializationEnvelope sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		sse.dotNet = true;

		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,0);
		String DstId_OrderPdaId=dbengine.getDistinctInvoiceNumbers();
		//String strStoreCollectionUniquneVisitId=dbengine.getDistinctCollectionPaymentIds();
		//dbengine.open();
		ServiceWorker setmovie = new ServiceWorker();

		try
		{
			client = new SoapObject(NAMESPACE, METHOD_NAME);

			client.addProperty("NewDistributorIDOrderID", DstId_OrderPdaId.toString());
			client.addProperty("uuid", IMEINo.toString());
			client.addProperty("CoverageAreaNodeID", CommonInfo.CoverageAreaNodeID);
			client.addProperty("coverageAreaNodeType", CommonInfo.CoverageAreaNodeType);
			//client.addProperty("strStoreCollectionUniquneVisitId",strStoreCollectionUniquneVisitId);

			sse.setOutputSoapObject(client);

			sse.bodyOut = client;

			androidHttpTransport.call(SOAP_ACTION, sse);

			responseBody = (SoapObject)sse.bodyIn;

			int totalCount = responseBody.getPropertyCount();

			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);

			dbengine.deleteCompleteDataDistStock();
			//dbengine.Delete_tblProductList_for_refreshData();
			//dbengine.Delete_tblCategory_for_refreshData();
			if(CommonInfo.hmapAppMasterFlags.get("flgNeedStock")==1 && CommonInfo.hmapAppMasterFlags.get("flgCalculateStock")==1 ) {
				NodeList tblCycleIDNode = doc.getElementsByTagName("tblCycleID");
				for (int i = 0; i < tblCycleIDNode.getLength(); i++)
				{

					int cycleId=0;
					String CycStartTime="0";
					String CycleTime="0";

					Element element = (Element) tblCycleIDNode.item(i);

					if(!element.getElementsByTagName("CycleID").equals(null))
					{
						NodeList CycleIDNode = element.getElementsByTagName("CycleID");
						Element      line = (Element) CycleIDNode.item(0);
						if(CycleIDNode.getLength()>0)
						{
							cycleId=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
						}
					}
					if(!element.getElementsByTagName("CycStartTime").equals(null))
					{
						NodeList CycStartTimeNode = element.getElementsByTagName("CycStartTime");
						Element      line = (Element) CycStartTimeNode.item(0);
						if(CycStartTimeNode.getLength()>0)
						{
							CycStartTime=xmlParser.getCharacterDataFromElement(line);
						}
					}
					if(!element.getElementsByTagName("CycleTime").equals(null))
					{
						NodeList CycStartTimeNode = element.getElementsByTagName("CycleTime");
						Element      line = (Element) CycStartTimeNode.item(0);
						if(CycStartTimeNode.getLength()>0)
						{
							CycleTime=xmlParser.getCharacterDataFromElement(line);
						}
					}


         /*if(flgStockOutEntryDone==0)
         {
            flagExecutedServiceSuccesfully=1;
            return setmovie;
         }*/

					//System.out.println("Column DESC TBL..."+IncId+"-"+ReportColumnName+"-"+DisplayColumnName);

					//dbengine.insertCycleId(cycleId,CycStartTime,CycleTime);

				}
				NodeList dtDistributorStockOutFlgNode = doc.getElementsByTagName("dtDistributorStockOutFlg");
				for (int i = 0; i < dtDistributorStockOutFlgNode.getLength(); i++)
				{

					int flgStockOutEntryDone=0;


					Element element = (Element) dtDistributorStockOutFlgNode.item(i);

					if(!element.getElementsByTagName("flgStockOutEntryDone").equals(null))
					{
						NodeList flgStockOutEntryDoneNode = element.getElementsByTagName("flgStockOutEntryDone");
						Element      line = (Element) flgStockOutEntryDoneNode.item(0);
						if(flgStockOutEntryDoneNode.getLength()>0)
						{
							flgStockOutEntryDone=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
						}
					}
					dbengine.insertStockOut(flgStockOutEntryDone);

				}
			}


			NodeList dtDistributorIDOrderIDLeftNode = doc.getElementsByTagName("dtDistributorIDOrderIDLeft");
			for (int i = 0; i < dtDistributorIDOrderIDLeftNode.getLength(); i++)
			{

				String DistID="";
				String PDAOrderId="";
				int flgProcessedInvoice=0;
				Element element = (Element) dtDistributorIDOrderIDLeftNode.item(i);

				if(!element.getElementsByTagName("Customer").equals(null))
				{
					NodeList CustomerNode = element.getElementsByTagName("Customer");
					Element      line = (Element) CustomerNode.item(0);
					if(CustomerNode.getLength()>0)
					{
						DistID=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("PDAOrderId").equals(null))
				{
					NodeList PDAOrderIdNode = element.getElementsByTagName("PDAOrderId");
					Element      line = (Element) PDAOrderIdNode.item(0);
					if(PDAOrderIdNode.getLength()>0)
					{
						PDAOrderId=xmlParser.getCharacterDataFromElement(line);
					}
				}
                if(!element.getElementsByTagName("flgInvExists").equals(null))
                {
                    NodeList flgProcessedInvoiceNode = element.getElementsByTagName("flgInvExists");
                    Element      line = (Element) flgProcessedInvoiceNode.item(0);
                    if(flgProcessedInvoiceNode.getLength()>0)
                    {
                        flgProcessedInvoice=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
                    }
                }

				//dbengine.insertDistributorLeftOrderId(DistID,PDAOrderId,flgProcessedInvoice);
				//System.out.println("Column DESC TBL..."+IncId+"-"+ReportColumnName+"-"+DisplayColumnName);
			}

			NodeList dtDistributorProdctStockNode = doc.getElementsByTagName("dtDistributorProdctStock");
			for (int i = 0; i < dtDistributorProdctStockNode.getLength(); i++)
			{
				//// insertDistributorStock(String prdctId,String stockQntty,String distributorNodeIdNodeType,String SKUName,String OpeningStock,String TodaysAddedStock,String CycleAddedStock,String NetStockQty,String TodaysUnloadStk,String CycleUnloadStk,String CategoryID)
				String distId="0";
				String productId="0";
				String StockQntity="0";
				String NetStockQty="0";
				String SKUName="0";
				String ProductNodeType="0";
				String StockDate="0";

				String OpeningStock="0";
				String TodaysAddedStock="0";
				String CycleAddedStock="0";
				String FinalStockQty="0";
				String CategoryID="0";
				String TodaysUnloadStk="0";
				String CycleUnloadStk="0";

				Element element = (Element) dtDistributorProdctStockNode.item(i);

				if(!element.getElementsByTagName("Customer").equals(null))
				{
					NodeList CustomerNode = element.getElementsByTagName("Customer");
					Element      line = (Element) CustomerNode.item(0);
					if(CustomerNode.getLength()>0)
					{
						distId=xmlParser.getCharacterDataFromElement(line);
						dbrId=distId;
					}
				}

				if(!element.getElementsByTagName("ProductNodeID").equals(null))
				{
					NodeList ProductNodeIDNode = element.getElementsByTagName("ProductNodeID");
					Element      line = (Element) ProductNodeIDNode.item(0);
					if(ProductNodeIDNode.getLength()>0)
					{
						productId=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("SKUName").equals(null))
				{
					NodeList SKUNameNode = element.getElementsByTagName("SKUName");
					Element      line = (Element) SKUNameNode.item(0);
					if(SKUNameNode.getLength()>0)
					{
						SKUName=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("StockDate").equals(null))
				{
					NodeList StockDateNode = element.getElementsByTagName("StockDate");
					Element      line = (Element) StockDateNode.item(0);
					if(StockDateNode.getLength()>0)
					{
						StockDate=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("DayOpeningStock").equals(null))
				{
					NodeList OpeningStockNode = element.getElementsByTagName("DayOpeningStock");
					Element      line = (Element) OpeningStockNode.item(0);
					if(OpeningStockNode.getLength()>0)
					{
						OpeningStock=xmlParser.getCharacterDataFromElement(line);
					}
				}


				if(!element.getElementsByTagName("TodaysAddedStock").equals(null))
				{
					NodeList AddedStockNode = element.getElementsByTagName("TodaysAddedStock");
					Element      line = (Element) AddedStockNode.item(0);
					if(AddedStockNode.getLength()>0)
					{
						TodaysAddedStock=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("CycleAddedStock").equals(null))
				{
					NodeList CycleAddedStockNode = element.getElementsByTagName("CycleAddedStock");
					Element      line = (Element) CycleAddedStockNode.item(0);
					if(CycleAddedStockNode.getLength()>0)
					{
						CycleAddedStock=xmlParser.getCharacterDataFromElement(line);
					}
				}


				if(!element.getElementsByTagName("FinalStockQty").equals(null))
				{
					NodeList StockQtyNode = element.getElementsByTagName("FinalStockQty");
					Element      line = (Element) StockQtyNode.item(0);
					if(StockQtyNode.getLength()>0)
					{
						StockQntity=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("NetSalesQty").equals(null))
				{
					NodeList NetStockQtyNode = element.getElementsByTagName("NetSalesQty");
					Element      line = (Element) NetStockQtyNode.item(0);
					if(NetStockQtyNode.getLength()>0)
					{
						NetStockQty=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("TodaysUnloadStock").equals(null))
				{
					NodeList TodaysUnloadStkNode = element.getElementsByTagName("TodaysUnloadStock");
					Element      line = (Element)TodaysUnloadStkNode.item(0);
					if(TodaysUnloadStkNode.getLength()>0)
					{
						TodaysUnloadStk=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("CycleUnloadStock").equals(null))
				{
					NodeList CycleUnloadStkNode = element.getElementsByTagName("CycleUnloadStock");
					Element      line = (Element)CycleUnloadStkNode.item(0);
					if(CycleUnloadStkNode.getLength()>0)
					{
						CycleUnloadStk=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("CategoryID").equals(null))
				{
					NodeList CategoryIDNode = element.getElementsByTagName("CategoryID");
					Element      line = (Element)CategoryIDNode.item(0);
					if(CategoryIDNode.getLength()>0)
					{
						CategoryID=xmlParser.getCharacterDataFromElement(line);
					}
				}
				/*if(CommonInfo.flgDrctslsIndrctSls==1) {
					dbengine.insertDistributorStock(productId, StockQntity, distId, SKUName, OpeningStock, TodaysAddedStock, CycleAddedStock, NetStockQty, TodaysUnloadStk, CycleUnloadStk, CategoryID);
				}
				else
				{
					dbengine.insertDistributorStockPermanetTableDirectly(productId, StockQntity, distId, SKUName, OpeningStock, TodaysAddedStock, CycleAddedStock, NetStockQty, TodaysUnloadStk, CycleUnloadStk, CategoryID);
				}*/
					System.out.println("MASTER TBL..."+productId+"-"+SKUName+"-"+StockQntity);
			}


			flagExecutedServiceSuccesfully=39;
			if(CommonInfo.hmapAppMasterFlags.get("flgNeedStock")==1 && CommonInfo.hmapAppMasterFlags.get("flgCalculateStock")==1 ) {
				int statusId = dbengine.confirmedStock();
				if (statusId == 2) {
					dbengine.insertConfirmWArehouse(dbrId, "1");
					dbengine.inserttblDayCheckIn(1);
				}
			}
			setmovie.director = "1";
			return setmovie;
		} catch (Exception e) {
			setmovie.exceptionCode=e.getCause().getMessage();
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			flagExecutedServiceSuccesfully=0;
			//dbengine.close();;
			return setmovie;
		}
	}



	public ServiceWorker fnGetStockUploadedStatus(Context ctx,String bydate, String IMEINo)
	{
		this.context = ctx;
		String querryString="";

		final String SOAP_ACTION = "http://tempuri.org/fnGetStockUploadedStatus";
		final String METHOD_NAME = "fnGetStockUploadedStatus";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;

		// through SoapObject
		SoapObject client = null; //Its the client petition to the web service
		SoapObject responseBody = null; //Contains XML content of dataset

		PRJDatabase dbengine = new PRJDatabase(context);
		//dbengine.open();

		//SoapObject param
		sharedPref = context.getSharedPreferences(CommonInfo.Preference, context.MODE_PRIVATE);
		SoapSerializationEnvelope sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		sse.dotNet = true;

		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,0);

		ServiceWorker setmovie = new ServiceWorker();

		try
		{
			client = new SoapObject(NAMESPACE, METHOD_NAME);

			client.addProperty("bydate", bydate.toString());
			client.addProperty("uuid", IMEINo.toString());

			sse.setOutputSoapObject(client);

			sse.bodyOut = client;

			androidHttpTransport.call(SOAP_ACTION, sse);

			responseBody = (SoapObject)sse.bodyIn;

			int totalCount = responseBody.getPropertyCount();

			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);

			//dbengine.deleteIncentivesTbles();
			dbengine.Delete_tblStockUploadedStatus();

			NodeList tblStockUploadedStatusNode = doc.getElementsByTagName("tblStockUploadedStatus");
			for (int i = 0; i < tblStockUploadedStatusNode.getLength(); i++)
			{
				int flgStockTrans=0;
				int VanLoadUnLoadCycID=0;
				String CycleTime="0";
				int statusId=0;
				int flgDayEnd=0;


				Element element = (Element) tblStockUploadedStatusNode.item(i);

				if(!element.getElementsByTagName("flgStockTrans").equals(null))
				{
					NodeList flgStockTransNode = element.getElementsByTagName("flgStockTrans");
					Element      line = (Element) flgStockTransNode.item(0);
					if(flgStockTransNode.getLength()>0)
					{
						flgStockTrans=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}
				if(flgStockTrans!=0)
				{
					if(!element.getElementsByTagName("VanLoadUnLoadCycID").equals(null))
					{
						NodeList CycleIDNode = element.getElementsByTagName("VanLoadUnLoadCycID");
						Element      line = (Element) CycleIDNode.item(0);
						if(CycleIDNode.getLength()>0)
						{
							VanLoadUnLoadCycID=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
						}
					}
					if(!element.getElementsByTagName("CycleTime").equals(null))
					{
						NodeList CycleTimeNode = element.getElementsByTagName("CycleTime");
						Element      line = (Element) CycleTimeNode.item(0);
						if(CycleTimeNode.getLength()>0)
						{
							CycleTime=xmlParser.getCharacterDataFromElement(line);
						}
					}


				}
				if(!element.getElementsByTagName("StatusID").equals(null))
				{
					NodeList StatusIDNode = element.getElementsByTagName("StatusID");
					Element      line = (Element) StatusIDNode.item(0);
					if(StatusIDNode.getLength()>0)
					{
						statusId=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}

				if(!element.getElementsByTagName("flgDayEnd").equals(null))
				{
					NodeList flgDayEndNode = element.getElementsByTagName("flgDayEnd");
					Element      line = (Element) flgDayEndNode.item(0);
					if(flgDayEndNode.getLength()>0)
					{
						flgDayEnd=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
						SharedPreferences.Editor editor = sharedPref.edit();
						editor.putInt("FinalSubmit", flgDayEnd);
						editor.commit();
					}
				}

				if(statusId!=2)
				{
					dbengine.deleteVanConfirmFlag();
				}



				dbengine.inserttblStockUploadedStatus(flgStockTrans,VanLoadUnLoadCycID,CycleTime,statusId,flgDayEnd);

			}


			//dbengine.close();;
			setmovie.director = "1";

			return setmovie;

		}
		catch (Exception e)
		{
			setmovie.exceptionCode=e.getCause().getMessage();
			//dbengine.close();;
			System.out.println("Aman Exception occur in fnIncentive :"+e.toString());

			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();

			return setmovie;
		}
	}

	public int fnConfirmStockUpdate(Context ctx)
	{
		this.context = ctx;

		int flgDataConfirmed=-1;
		final String SOAP_ACTION = "http://tempuri.org/fnGetPDAConfirmVanStock";
		final String METHOD_NAME = "fnGetPDAConfirmVanStock";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;

		// through SoapObject
		SoapObject client = null; //Its the client petition to the web service
		SoapObject responseBody = null; //Contains XML content of dataset

		PRJDatabase dbengine = new PRJDatabase(context);
		int vanCycleId= dbengine.fetchtblVanCycleId();
		String CycStartTime=dbengine.fetchtblVanCycleTime();
		//dbengine.open();

		//SoapObject param
		SoapSerializationEnvelope sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		sse.dotNet = true;

		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,0);

		ServiceWorker setmovie = new ServiceWorker();

		try
		{
			client = new SoapObject(NAMESPACE, METHOD_NAME);

			client.addProperty("CycleID", vanCycleId);
			client.addProperty("CycleDate", CycStartTime);

			sse.setOutputSoapObject(client);

			sse.bodyOut = client;

			androidHttpTransport.call(SOAP_ACTION, sse);

			responseBody = (SoapObject)sse.bodyIn;

			int totalCount = responseBody.getPropertyCount();

			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);

			//dbengine.deleteIncentivesTbles();


			NodeList tblflgDataConfirmedNode = doc.getElementsByTagName("tblflgDataConfirmed");
			for (int i = 0; i < tblflgDataConfirmedNode.getLength(); i++)
			{




				Element element = (Element) tblflgDataConfirmedNode.item(i);

				if(!element.getElementsByTagName("flgDataConfirmed").equals(null))
				{
					NodeList flgStockTransNode = element.getElementsByTagName("flgDataConfirmed");
					Element      line = (Element) flgStockTransNode.item(0);
					if(flgStockTransNode.getLength()>0)
					{
						flgDataConfirmed=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));

					}
				}




			}


			//dbengine.close();;
			setmovie.director = "1";

			return flgDataConfirmed;

		}
		catch (Exception e)
		{
			setmovie.exceptionCode=e.getCause().getMessage();
			//dbengine.close();;
			System.out.println("Aman Exception occur in fnIncentive :"+e.toString());

			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();

			return flgDataConfirmed;
		}
	}



//-----------------------------------------------------------------------------------------------------------


	public ServiceWorker getStoreWiseOutStandings(Context ctx, String dateVAL, String uuid, String rID, String RouteType)
	{
		this.context = ctx;

		PRJDatabase dbengine = new PRJDatabase(context);
		//dbengine.open();

		final String SOAP_ACTION = "http://tempuri.org/fnGetStoreOutStandings";//GetProductListMRNewProductFilterTest";
		final String METHOD_NAME = "fnGetStoreOutStandings";//GetProductListMRNewProductFilterTest
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;

		decimalFormat.applyPattern(pattern);
		SoapObject table = null; // Contains table of dataset that returned
		// throug SoapObject
		SoapObject client = null; // Its the client petition to the web service
		SoapObject tableRow = null; // Contains row of table
		SoapObject responseBody = null; // Contains XML content of dataset

		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;



		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		//sse.addMapping(NAMESPACE, "ServiceWorker", this.getClass());
		// Note if class name isn't "movie" ,you must change
		sse.dotNet = true; // if WebService written .Net is result=true
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,timeout);


		ServiceWorker setmovie = new ServiceWorker();
		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);

			//String dateVAL = "00.00.0000";

			//////// System.out.println("soap obj date: "+ dateVAL);
			client.addProperty("bydate", dateVAL.toString());
			client.addProperty("IMEINo", uuid.toString());
			client.addProperty("rID", rID.toString());
			client.addProperty("RouteType", RouteType);
			//client.addProperty("SysDate", SysDate.toString());
			//client.addProperty("AppVersionID", dbengine.AppVersionID.toString());
			client.addProperty("flgAllRoutesData", CommonInfo.flgAllRoutesData);
			client.addProperty("CoverageAreaNodeID", 0);
			client.addProperty("coverageAreaNodeType", 0);


			/*client.addProperty("bydate", dateVAL.toString());
			client.addProperty("IMEINo", uuid.toString());*/

			sse.setOutputSoapObject(client);
			sse.bodyOut = client;
			androidHttpTransport.call(SOAP_ACTION, sse);
			responseBody = (SoapObject)sse.bodyIn;
			String resultString=androidHttpTransport.responseDump;
			String name=responseBody.getProperty(0).toString();
			// This step: get file XML
			/*responseBody = (SoapObject) sse.getResponse();
			  String name=responseBody.getProperty(0).toString();*/

			// System.out.println("Kajol 3 :"+name);

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);

			dbengine.Delete_tblLastOutstanding_for_refreshData();

			NodeList tblLastOutstanding = doc.getElementsByTagName("tblLastOutstanding");
			for (int i = 0; i < tblLastOutstanding.getLength(); i++)
			{


				String StoreID="NA";
				Double Outstanding=0.0;
				Double AmtOverdue=0.0;


				Element element = (Element) tblLastOutstanding.item(i);

				if(!element.getElementsByTagName("Storeid").equals(null))
				{

					NodeList StoreIDNode = element.getElementsByTagName("Storeid");
					Element     line = (Element) StoreIDNode.item(0);

					if(StoreIDNode.getLength()>0)
					{

						StoreID=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("OutStanding").equals(null))
				{

					NodeList OutstandingNode = element.getElementsByTagName("OutStanding");
					Element     line = (Element) OutstandingNode.item(0);

					if(OutstandingNode.getLength()>0)
					{

						Outstanding=Double.parseDouble(xmlParser.getCharacterDataFromElement(line));
						Outstanding=Double.parseDouble(decimalFormat.format(Outstanding));
					}
				}
				if(!element.getElementsByTagName("AmtOverdue").equals(null))
				{

					NodeList AmtOverdueNode = element.getElementsByTagName("AmtOverdue");
					Element     line = (Element) AmtOverdueNode.item(0);

					if(AmtOverdueNode.getLength()>0)
					{

						AmtOverdue=Double.parseDouble(xmlParser.getCharacterDataFromElement(line));
						AmtOverdue=Double.parseDouble(decimalFormat.format(AmtOverdue));
					}
				}

			//	dbengine.savetblLastOutstanding(StoreID,Outstanding,AmtOverdue);
			}

			NodeList tblInvoiceLastVisitDetails = doc.getElementsByTagName("tblInvoiceLastVisitDetails");
			for (int i = 0; i < tblInvoiceLastVisitDetails.getLength(); i++)
			{


				String StoreID="NA";
				String InvCode="00";
				String InvDate="NA";
				String OutstandingAmt="0.0";
				String AmtOverdue="0.0";


				Element element = (Element) tblInvoiceLastVisitDetails.item(i);

				if(!element.getElementsByTagName("Storeid").equals(null))
				{

					NodeList StoreIDNode = element.getElementsByTagName("Storeid");
					Element     line = (Element) StoreIDNode.item(0);

					if(StoreIDNode.getLength()>0)
					{

						StoreID=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("InvCode").equals(null))
				{

					NodeList InvCodeNode = element.getElementsByTagName("InvCode");
					Element     line = (Element) InvCodeNode.item(0);

					if(InvCodeNode.getLength()>0)
					{

						InvCode=xmlParser.getCharacterDataFromElement(line);

					}
				}

				if(!element.getElementsByTagName("InvDate").equals(null))
				{

					NodeList InvDateNode = element.getElementsByTagName("InvDate");
					Element     line = (Element) InvDateNode.item(0);

					if(InvDateNode.getLength()>0)
					{

						InvDate=xmlParser.getCharacterDataFromElement(line);

					}
				}
				if(!element.getElementsByTagName("OutStandingAmt").equals(null))
				{

					NodeList OutstandingAmtNode = element.getElementsByTagName("OutStandingAmt");
					Element     line = (Element) OutstandingAmtNode.item(0);

					if(OutstandingAmtNode.getLength()>0)
					{

						Double OutstandingAmtServer= Double.valueOf(""+Double.parseDouble(xmlParser.getCharacterDataFromElement(line)));
						OutstandingAmt=""+Double.parseDouble(decimalFormat.format(OutstandingAmtServer));
					}
				}
				if(!element.getElementsByTagName("AmtOverdue").equals(null))
				{

					NodeList AmtOverdueNode = element.getElementsByTagName("AmtOverdue");
					Element     line = (Element) AmtOverdueNode.item(0);

					if(AmtOverdueNode.getLength()>0)
					{

						Double AmtOverdueServer= Double.valueOf(""+Double.parseDouble(xmlParser.getCharacterDataFromElement(line)));
						AmtOverdue=""+Double.parseDouble(decimalFormat.format(AmtOverdueServer));
					}
				}

			//	dbengine.savetblInvoiceLastVisitDetails(StoreID,InvCode,InvDate,OutstandingAmt,AmtOverdue);
			}
			//dbengine.close();;

			setmovie.director = "1";
			flagExecutedServiceSuccesfully=2;
			// System.out.println("ServiceWorkerNitish getallProduct Inside");
			return setmovie;
//return counts;
		} catch (Exception e) {
			setmovie.exceptionCode=e.getCause().getMessage();
			// System.out.println("Aman Exception occur in GetProductListMRNew :"+e.toString());
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			flagExecutedServiceSuccesfully=0;
			//dbengine.close();;
			return setmovie;
		}

	}





	public ServiceWorker getStoreAllDetails(Context ctx,String uuid,String CurDate,int DatabaseVersion,int ApplicationID,String RegistrationID)
	{

		this.context = ctx;
		PRJDatabase dbengine = new PRJDatabase(context);
		//dbengine.open();

		decimalFormat.applyPattern(pattern);

		int chkTblStoreListContainsRow=1;
		StringReader read;
		InputSource inputstream;
		final String SOAP_ACTION = "http://tempuri.org/fnGetLTSummaryAndPreAddedOutletDetails";
		final String METHOD_NAME = "fnGetLTSummaryAndPreAddedOutletDetails";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;
		//Create request
		SoapObject table = null; // Contains table of dataset that returned
		// through SoapObject
		SoapObject client = null; // Its the client petition to the web service
		SoapObject tableRow = null; // Contains row of table
		SoapObject responseBody = null; // Contains XML content of dataset

		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;

		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		sse.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,timeout);

		ServiceWorker setmovie = new ServiceWorker();


		// // System.out.println("Kajol 100");

		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);



			// // System.out.println("Kajol 101");
			client.addProperty("uuid", uuid.toString());
			client.addProperty("DatabaseVersion", DatabaseVersion);
			client.addProperty("ApplicationID", ApplicationID);
			client.addProperty("RegistrationID", RegistrationID);

			// // System.out.println("Kajol 102");
			sse.setOutputSoapObject(client);
			// // System.out.println("Kajol 103");
			sse.bodyOut = client;
			// // System.out.println("Kajol 104");

			androidHttpTransport.call(SOAP_ACTION, sse);

			// // System.out.println("Kajol 1");

			responseBody = (SoapObject)sse.bodyIn;
			// This step: get file XML
			//responseBody = (SoapObject) sse.getResponse();
			int totalCount = responseBody.getPropertyCount();

			// // System.out.println("Kajol 2 :"+totalCount);
			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			// // System.out.println("Kajol 3 :"+name);

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);


			dbengine.delete_all_storeDetailTables();




			NodeList tblUserNameNode = doc.getElementsByTagName("tblUserName");
			for (int i = 0; i < tblUserNameNode.getLength(); i++)
			{

				String UserName="0";

				Element element = (Element) tblUserNameNode.item(i);
				if(!element.getElementsByTagName("UserName").equals(null))
				{
					NodeList UserNameNode = element.getElementsByTagName("UserName");
					Element     line = (Element) UserNameNode.item(0);
					if (UserNameNode.getLength()>0)
					{
						UserName=xmlParser.getCharacterDataFromElement(line);
					}
				}

				dbengine.saveTblUserName(UserName);
			}

			NodeList tblStoreCountDetailsNode = doc.getElementsByTagName("tblStoreCountDetails");
			for (int i = 0; i < tblStoreCountDetailsNode.getLength(); i++)
			{

				String TotStoreAdded="0";
				String TodayStoreAdded ="0";


				Element element = (Element) tblStoreCountDetailsNode.item(i);
				if(!element.getElementsByTagName("TotStoreAdded").equals(null))
				{
					NodeList TotStoreAddedNode = element.getElementsByTagName("TotStoreAdded");
					Element     line = (Element) TotStoreAddedNode.item(0);
					if (TotStoreAddedNode.getLength()>0)
					{
						TotStoreAdded=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("TodayStoreAdded").equals(null))
				{
					NodeList TodayStoreAddedNode = element.getElementsByTagName("TodayStoreAdded");
					Element     line = (Element) TodayStoreAddedNode.item(0);
					if (TodayStoreAddedNode.getLength()>0)
					{
						TodayStoreAdded=xmlParser.getCharacterDataFromElement(line);
					}
				}

				dbengine.saveTblStoreCountDetails(TotStoreAdded, TodayStoreAdded);
			}

			NodeList tblPreAddedStoresNode = doc.getElementsByTagName("tblPreAddedStores");
			for (int i = 0; i < tblPreAddedStoresNode.getLength(); i++)
			{

				String StoreID="0";
				String StoreName ="0";
				String LatCode ="0";
				String LongCode ="0";
				String DateAdded ="0";
				int flgOldNewStore=0;
				int flgRemap=0;
				int Sstat=0;
				int RouteID=0;
				int RouteNodeType=0;
				Element element = (Element) tblPreAddedStoresNode.item(i);


				if(!element.getElementsByTagName("RouteNodeID").equals(null))
				{
					NodeList RouteIDNode = element.getElementsByTagName("RouteNodeID");
					Element     line = (Element) RouteIDNode.item(0);
					if (RouteIDNode.getLength()>0)
					{
						RouteID=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}


				if(!element.getElementsByTagName("RouteNodeType").equals(null))
				{
					NodeList RouteNodeTypeNode = element.getElementsByTagName("RouteNodeType");
					Element     line = (Element) RouteNodeTypeNode.item(0);
					if (RouteNodeTypeNode.getLength()>0)
					{
						RouteNodeType=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}

				if(!element.getElementsByTagName("StoreIDDB").equals(null))
				{
					NodeList StoreIDNode = element.getElementsByTagName("StoreIDDB");
					Element     line = (Element) StoreIDNode.item(0);
					if (StoreIDNode.getLength()>0)
					{
						StoreID=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("StoreName").equals(null))
				{
					NodeList StoreNameNode = element.getElementsByTagName("StoreName");
					Element     line = (Element) StoreNameNode.item(0);
					if (StoreNameNode.getLength()>0)
					{
						StoreName=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("LatCode").equals(null))
				{
					NodeList LatCodeNode = element.getElementsByTagName("LatCode");
					Element     line = (Element) LatCodeNode.item(0);
					if (LatCodeNode.getLength()>0)
					{
						LatCode=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("LongCode").equals(null))
				{
					NodeList LongCodeNode = element.getElementsByTagName("LongCode");
					Element     line = (Element) LongCodeNode.item(0);
					if (LongCodeNode.getLength()>0)
					{
						LongCode=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("DateAdded").equals(null))
				{
					NodeList DateAddedNode = element.getElementsByTagName("DateAdded");
					Element     line = (Element) DateAddedNode.item(0);
					if (DateAddedNode.getLength()>0)
					{
						DateAdded=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("flgRemap").equals(null))
				{
					NodeList flgRemapNode = element.getElementsByTagName("flgRemap");
					Element     line = (Element) flgRemapNode.item(0);
					if (flgRemapNode.getLength()>0)
					{
						flgRemap=Integer.parseInt(xmlParser.getCharacterDataFromElement(line));
					}
				}


			//	dbengine.saveTblPreAddedStores(StoreID, StoreName, LatCode, LongCode, DateAdded,flgOldNewStore,flgRemap,Sstat,RouteID,RouteNodeType);
			}

			NodeList tblPreAddedStoresDataDetailsNode = doc.getElementsByTagName("tblPreAddedStoresDataDetails");
			for (int i = 0; i < tblPreAddedStoresDataDetailsNode.getLength(); i++)
			{

				String StoreIDDB="0";
				String GrpQuestID ="0";
				String QstId ="0";
				String AnsControlTypeID ="0";

				String AnsTextVal ="0";

				String flgPrvVal ="2";


				Element element = (Element) tblPreAddedStoresDataDetailsNode.item(i);

				if(!element.getElementsByTagName("StoreIDDB").equals(null))
				{
					NodeList StoreIDDBNode = element.getElementsByTagName("StoreIDDB");
					Element     line = (Element) StoreIDDBNode.item(0);
					if (StoreIDDBNode.getLength()>0)
					{
						StoreIDDB=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("GrpQuestID").equals(null))
				{
					NodeList GrpQuestIDNode = element.getElementsByTagName("GrpQuestID");
					Element     line = (Element) GrpQuestIDNode.item(0);
					if (GrpQuestIDNode.getLength()>0)
					{
						GrpQuestID=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("QstId").equals(null))
				{
					NodeList QstIdNode = element.getElementsByTagName("QstId");
					Element     line = (Element) QstIdNode.item(0);
					if (QstIdNode.getLength()>0)
					{
						QstId=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("AnsControlTypeID").equals(null))
				{
					NodeList AnsControlTypeIDNode = element.getElementsByTagName("AnsControlTypeID");
					Element     line = (Element) AnsControlTypeIDNode.item(0);
					if (AnsControlTypeIDNode.getLength()>0)
					{
						AnsControlTypeID=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("Ans").equals(null))
				{
					NodeList AnsTextValNode = element.getElementsByTagName("Ans");
					Element     line = (Element) AnsTextValNode.item(0);
					if (AnsTextValNode.getLength()>0)
					{
						AnsTextVal=xmlParser.getCharacterDataFromElement(line);
					}
				}

				if(!element.getElementsByTagName("flgPrvValue").equals(null))
				{
					NodeList OptionValueNode = element.getElementsByTagName("flgPrvValue");
					Element     line = (Element) OptionValueNode.item(0);
					if (OptionValueNode.getLength()>0)
					{
						flgPrvVal=xmlParser.getCharacterDataFromElement(line);
					}
				}


			//	dbengine.saveTblPreAddedStoresDataDetails(StoreIDDB, GrpQuestID, QstId, AnsControlTypeID,AnsTextVal,flgPrvVal);
			}









			setmovie.director = "1";
			//dbengine.close();;
			return setmovie;

		} catch (Exception e) {

			// System.out.println("Aman Exception occur in GetIMEIVersionDetailStatusNew :"+e.toString());
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			//dbengine.close();;

			return setmovie;
		}





	}



	public ServiceWorker getConfirmtionRqstStock(Context ctx,String RqstdStk,String uuid,String CoverageAreaNodeID,String coverageAreaNodeType,int statusID)
	{
		this.context = ctx;
		PRJDatabase dbengine = new PRJDatabase(context);

		decimalFormat.applyPattern(pattern);

		int chkTblStoreListContainsRow=1;
		StringReader read;
		InputSource inputstream;
		final String SOAP_ACTION = "http://tempuri.org/fnSpVanStockRequestSave";
		final String METHOD_NAME = "fnSpVanStockRequestSave";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;
		//Create request
		SoapObject table = null; // Contains table of dataset that returned
		// through SoapObject
		SoapObject client = null; // Its the client petition to the web service
		SoapObject tableRow = null; // Contains row of table
		SoapObject responseBody = null; // Contains XML content of dataset

		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;

		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		sse.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,timeout);

		ServiceWorker setmovie = new ServiceWorker();
		Date currDate= new Date();
		SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

		currSysDate = currDateFormat.format(currDate).toString();
		String crntDate = currSysDate.trim().toString();
		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);


					client.addProperty("bydate", crntDate);
			client.addProperty("IMEINo", uuid.toString());
			client.addProperty("CoverageAreaNodeID", CoverageAreaNodeID);

			client.addProperty("coverageAreaNodeType", coverageAreaNodeType);
			client.addProperty("Prdvalues", RqstdStk);
			client.addProperty("StatusID", statusID);


			sse.setOutputSoapObject(client);

			sse.bodyOut = client;

			androidHttpTransport.call(SOAP_ACTION, sse);

			responseBody = (SoapObject)sse.bodyIn;
			// This step: get file XML
			//responseBody = (SoapObject) sse.getResponse();
			int totalCount = responseBody.getPropertyCount();

			// // System.out.println("Kajol 2 :"+totalCount);
			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);

			//dbengine.open();




			NodeList tblDistributorListForSONode = doc.getElementsByTagName("tblflgRequestStockAccepted");
			for (int i = 0; i < tblDistributorListForSONode.getLength(); i++)
			{

				String VanLoadUnLoadCycID="0";
				String flgRequestAccept="0";


				Element element = (Element) tblDistributorListForSONode.item(i);
				if(!element.getElementsByTagName("VanLoadUnLoadCycID").equals(null))
				{
					NodeList VanLoadUnLoadCycIDNode = element.getElementsByTagName("VanLoadUnLoadCycID");
					Element     line = (Element) VanLoadUnLoadCycIDNode.item(0);
					if (VanLoadUnLoadCycIDNode.getLength()>0)
					{
						VanLoadUnLoadCycID=xmlParser.getCharacterDataFromElement(line);
					}
				}
				if(!element.getElementsByTagName("flgRequestAccept").equals(null))
				{
					NodeList flgRequestAcceptNode = element.getElementsByTagName("flgRequestAccept");
					Element     line = (Element) flgRequestAcceptNode.item(0);
					if (flgRequestAcceptNode.getLength()>0)
					{
						flgRequestAccept=xmlParser.getCharacterDataFromElement(line);

							if(flgRequestAccept.equals("1"))
							{
								setmovie.director = "1";
								break;
							}


							else
							{
								setmovie.director = "0";
								break;
							}


					}
				}

			}




			//dbengine.close();
			return setmovie;

		} catch (Exception e) {
			//setmovie.exceptionCode=e.getCause().getMessage();
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			//dbengine.close();

			return setmovie;
		}
	}

	public ServiceWorker submitDayEndClosure(Context ctx,String uuid,int flgUnloading)
	{
		this.context = ctx;
		PRJDatabase dbengine = new PRJDatabase(context);

		decimalFormat.applyPattern(pattern);

		int chkTblStoreListContainsRow=1;
		StringReader read;
		InputSource inputstream;
		final String SOAP_ACTION = "http://tempuri.org/fnPDAVanDayEndDet";
		final String METHOD_NAME = "fnPDAVanDayEndDet";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;
		//Create request
		SoapObject table = null; // Contains table of dataset that returned
		// through SoapObject
		SoapObject client = null; // Its the client petition to the web service
		SoapObject tableRow = null; // Contains row of table
		SoapObject responseBody = null; // Contains XML content of dataset

		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;

		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		sse.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,timeout);

		ServiceWorker setmovie = new ServiceWorker();
		Date currDate= new Date();

		long syncTIMESTAMP = System.currentTimeMillis();
		Date dateobj = new Date(syncTIMESTAMP);
		SimpleDateFormat df = new SimpleDateFormat(
				"dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
		String EndTS = df.format(dateobj);

		int cycleId=dbengine.fetchtblVanCycleId();
		if(cycleId==-1)
		{
			cycleId=0;

		}

		SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		String rID=dbengine.GetActiveRouteID();
		currSysDate = currDateFormat.format(currDate).toString();
		String crntDate = currSysDate.trim().toString();
		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);



			client.addProperty("PDA_IMEI", uuid.toString());
			client.addProperty("DayEndTime", EndTS);
			client.addProperty("VisitDate", crntDate);

			client.addProperty("AppVersionID", CommonInfo.DATABASE_VERSIONID);
			client.addProperty("VanLoadUnLoadCycID", cycleId);
			client.addProperty("flgUnloading", flgUnloading);


			sse.setOutputSoapObject(client);

			sse.bodyOut = client;

			androidHttpTransport.call(SOAP_ACTION, sse);

			responseBody = (SoapObject)sse.bodyIn;
			// This step: get file XML
			//responseBody = (SoapObject) sse.getResponse();
			int totalCount = responseBody.getPropertyCount();

			// // System.out.println("Kajol 2 :"+totalCount);
			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);

			//dbengine.open();




			NodeList tblDistributorListForSONode = doc.getElementsByTagName("tblflgPDAVanDayEndDet");
			for (int i = 0; i < tblDistributorListForSONode.getLength(); i++)
			{

				String VanLoadUnLoadCycID="0";
				String flgRequestAccept="0";


				Element element = (Element) tblDistributorListForSONode.item(i);

				if(!element.getElementsByTagName("flgDayEndRequestAccept").equals(null))
				{
					NodeList flgRequestAcceptNode = element.getElementsByTagName("flgDayEndRequestAccept");
					Element     line = (Element) flgRequestAcceptNode.item(0);
					if (flgRequestAcceptNode.getLength()>0)
					{
						flgRequestAccept=xmlParser.getCharacterDataFromElement(line);
						if(flgRequestAccept.equals("1"))
						{
							setmovie.director = "1";
							break;
						}
						else
						{
							setmovie.director = "0";
							break;
						}
					}
				}

			}




			//dbengine.close();
			return setmovie;

		} catch (Exception e) {
			//setmovie.exceptionCode=e.getCause().getMessage();
			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			//dbengine.close();

			return setmovie;
		}
	}


	public ServiceWorker getPDAAddedOutletSummaryReport(Context ctx,String uuid,int flgDrillLevel)
	{

		this.context = ctx;
		PRJDatabase dbengine = new PRJDatabase(context);


		decimalFormat.applyPattern(pattern);

		int chkTblStoreListContainsRow=1;
		StringReader read;
		InputSource inputstream;
		final String SOAP_ACTION = "http://tempuri.org/fnGetPDAGetAddedOutletSummaryReport";
		final String METHOD_NAME = "fnGetPDAGetAddedOutletSummaryReport";
		final String NAMESPACE = "http://tempuri.org/";
		final String URL = UrlForWebService;
		//Create request
		SoapObject table = null; // Contains table of dataset that returned
		// through SoapObject
		SoapObject client = null; // Its the client petition to the web service
		SoapObject tableRow = null; // Contains row of table
		SoapObject responseBody = null; // Contains XML content of dataset

		//SoapObject param
		HttpTransportSE transport = null; // That call webservice
		SoapSerializationEnvelope sse = null;

		sse = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		sse.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,timeout);

		ServiceWorker setmovie = new ServiceWorker();


		// // System.out.println("Kajol 100");

		try {
			client = new SoapObject(NAMESPACE, METHOD_NAME);

			// // System.out.println("Kajol 101");
			client.addProperty("IMEINo", uuid.toString());
			client.addProperty("flgDrillLevel", flgDrillLevel);

			// // System.out.println("Kajol 102");
			sse.setOutputSoapObject(client);
			// // System.out.println("Kajol 103");
			sse.bodyOut = client;
			// // System.out.println("Kajol 104");

			androidHttpTransport.call(SOAP_ACTION, sse);

			// // System.out.println("Kajol 1");

			responseBody = (SoapObject)sse.bodyIn;
			// This step: get file XML
			//responseBody = (SoapObject) sse.getResponse();
			int totalCount = responseBody.getPropertyCount();

			// // System.out.println("Kajol 2 :"+totalCount);
			String resultString=androidHttpTransport.responseDump;

			String name=responseBody.getProperty(0).toString();

			// // System.out.println("Kajol 3 :"+name);

			XMLParser xmlParser = new XMLParser();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(name));
			Document doc = db.parse(is);

			dbengine.droptblDAGetAddedOutletSummaryReport();
			dbengine.createtblDAGetAddedOutletSummaryReport();

			NodeList tblDAGetAddedOutletSummaryReport = doc.getElementsByTagName("tblDAGetAddedOutletSummaryReport");

			for (int i = 0; i < tblDAGetAddedOutletSummaryReport.getLength(); i++)
			{
				String Header="0";
				String Child="0";
				String TotalStores="0";
				String Validated="0";
				String Pending="0";
				int FlgNormalOverall=0;

				Element element = (Element) tblDAGetAddedOutletSummaryReport.item(i);

				NodeList HeaderNode = element.getElementsByTagName("Header");
				Element line = (Element) HeaderNode.item(0);
				if(HeaderNode.getLength()>0)
				{
					Header=xmlParser.getCharacterDataFromElement(line);
				}

				NodeList ChildNode = element.getElementsByTagName("Child");
				line = (Element) ChildNode.item(0);
				if(ChildNode.getLength()>0)
				{
					Child=xmlParser.getCharacterDataFromElement(line);
				}

				NodeList TotalStoresNode = element.getElementsByTagName("TotalStores");
				line = (Element) TotalStoresNode.item(0);
				if(TotalStoresNode.getLength()>0)
				{
					TotalStores=xmlParser.getCharacterDataFromElement(line);
				}

				NodeList ValidatedNode = element.getElementsByTagName("Validated");
				line = (Element) ValidatedNode.item(0);
				if(ValidatedNode.getLength()>0)
				{
					Validated=xmlParser.getCharacterDataFromElement(line);
				}

				NodeList PendingNode = element.getElementsByTagName("Pending");
				line = (Element) PendingNode.item(0);
				if(PendingNode.getLength()>0)
				{
					Pending=xmlParser.getCharacterDataFromElement(line);
				}

				dbengine.savetblDAGetAddedOutletSummaryReport(Header,Child,TotalStores,Validated,Pending,FlgNormalOverall);
				System.out.println("HEADER..."+Header);
			}

			NodeList tblDAGetAddedOutletSummaryOverallReport = doc.getElementsByTagName("tblDAGetAddedOutletSummaryOverallReport");

			for (int i = 0; i < tblDAGetAddedOutletSummaryOverallReport.getLength(); i++)
			{
				String Header="0";
				String Child="0";
				String TotalStores="0";
				String Validated="0";
				String Pending="0";
				int FlgNormalOverall=1;

				Element element = (Element) tblDAGetAddedOutletSummaryOverallReport.item(i);

				NodeList HeaderNode = element.getElementsByTagName("Header");
				Element line = (Element) HeaderNode.item(0);
				if(HeaderNode.getLength()>0)
				{
					Header=xmlParser.getCharacterDataFromElement(line);
				}

				NodeList ChildNode = element.getElementsByTagName("Child");
				line = (Element) ChildNode.item(0);
				if(ChildNode.getLength()>0)
				{
					Child=xmlParser.getCharacterDataFromElement(line);
				}

				NodeList TotalStoresNode = element.getElementsByTagName("TotalStores");
				line = (Element) TotalStoresNode.item(0);
				if(TotalStoresNode.getLength()>0)
				{
					TotalStores=xmlParser.getCharacterDataFromElement(line);
				}

				NodeList ValidatedNode = element.getElementsByTagName("Validated");
				line = (Element) ValidatedNode.item(0);
				if(ValidatedNode.getLength()>0)
				{
					Validated=xmlParser.getCharacterDataFromElement(line);
				}

				NodeList PendingNode = element.getElementsByTagName("Pending");
				line = (Element) PendingNode.item(0);
				if(PendingNode.getLength()>0)
				{
					Pending=xmlParser.getCharacterDataFromElement(line);
				}

				dbengine.savetblDAGetAddedOutletSummaryReport(Header,Child,TotalStores,Validated,Pending,FlgNormalOverall);
				System.out.println("HEADER..."+Header);
			}

			setmovie.director = "1";

			return setmovie;

		} catch (Exception e) {

			setmovie.director = e.toString();
			setmovie.movie_name = e.toString();
			dbengine.close();

			return setmovie;
		}

	}

}
