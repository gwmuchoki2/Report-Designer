/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builder;

import java.io.File;
import java.io.StringReader;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ListModelList;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 *
 * @author gerald
 */
@Stateless
@LocalBean
public class BuilderGlobals 
{
    @PersistenceContext(name="onlineBeanPU")
    private static byte[] key = {
            0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79
    };//"thisIsASecretKey";
    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = 
    new byte[] { 'P', 'E', 'E', 'A', 'P', 'E', 'X', 'B', 'A', 'N', 'K', 'I', 'N', 'G', 'G', 'E' };

    private static EntityManager myglobalEM=null;

    public static EntityManager getMyglobalEM() 
    {
        return myglobalEM;
    }

    public static void setMyglobalEM(EntityManager myglobalEM) 
    {
        BuilderGlobals.myglobalEM = myglobalEM;
    }
    public static void GetInitialContext() throws NamingException
    {
        Context initCtx = new InitialContext(); 
         
        setMyglobalEM((javax.persistence.EntityManager)initCtx.lookup("java:comp/env/onlineBeanPU"));
    }   
    public String StuffKey(String strID,int intNum)
    {
        String strStuffedID="";
        strStuffedID = strID;
        if(strID.length()>=intNum)
        {
            return strID;
        }
        while(strStuffedID.length()<intNum)
        {
            strStuffedID='0'+strStuffedID;
        }
        return strStuffedID;
    }  
    public String ReverseStuffKey(String strID,int intNum)
    {
        String strStuffedID="";
        strStuffedID = strID;
        if(strID.length()>=intNum)
        {
            return strID;
        }
        while(strStuffedID.length()<intNum)
        {
            strStuffedID=strStuffedID+'0';
        }
        return strStuffedID;
    }
    public void PopulateZKossCombo(Combobox cboCombo,ArrayList states)
    {
        try
        {

            if(!states.isEmpty())
            {
                //cboRoles.setModel(ListModels.toListSubModel(new ListModelList(states)));
                ListModelList lm2 = new ListModelList(states);
		lm2.addSelection(lm2.get(0));
		cboCombo.setModel(lm2);                
                cboCombo.setItemRenderer(new ComboitemRenderer()
                {
                    public void render(Comboitem lstm, Object t, int i) throws Exception 
                    {
                        lstm.setValue(((Object[])t)[0].toString());
                        lstm.setLabel(((Object[])t)[1].toString());
                    }                  
                });
            }
            
        }
        catch(Exception ex)
        {
            
        }
    }
    public long GenerateRandomNumber()//long FromNumber,long ToNumber)
    {
        long RandomNumber =0;
        int min = 124357;
        int max = 999999999;
         
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        RandomNumber=(long)i1;
        
        return RandomNumber;
    }    
    public Object GetXMLNodeValue(NodeList NodeList,String DataType)
    {
        try
        {
            Object objData = NodeList.item(0).getFirstChild().getNodeValue();//objData="17/02/2012";
            if(DataType.equalsIgnoreCase("D") && !objData.toString().equalsIgnoreCase(""))
            {
                //objData = new GlobalMethods.ApexDateParser().FormatStringToDate(objData.toString(), "dd/MM/yyyy");
            }
            if(objData.toString().equalsIgnoreCase("TRT_OB_BAL_APEX"))
            {
                objData="Opening Balance";
            }
            else if(objData.toString().equalsIgnoreCase("TRT_CL_BAL_APEX"))
            {
                objData="Closing Balance";
            }
            return objData;
        }
        catch(Exception ex)
        {
            return "";
        }
    }
  public void showNotify(String msg, Component ref) {
        Clients.showNotification(msg, "info", ref, "end_center", 2000);
    }   
    public ArrayList GetColumnSummaryFunctions(String LanguageID,String ID)
    {
        ArrayList status =new ArrayList();
        try
        {
            ID =ID.toUpperCase();
            if(ID.equalsIgnoreCase("INT") || ID.equalsIgnoreCase("BIGINT") || ID.equalsIgnoreCase("NUMERIC") || ID.equalsIgnoreCase("DECIMAL")
                    || ID.equalsIgnoreCase("INTEGER") || ID.equalsIgnoreCase("SMALLINT") || ID.equalsIgnoreCase("FLOAT") || ID.equalsIgnoreCase("DOUBLE"))
            {
               status = GetSystemCodeDetails("en","NumericSummaryFunctions"); 
            }
            else
            {
               status = GetSystemCodeDetails("en","TextSummaryFunctions");  
            }
        } 
        catch(Exception ex)
        {
            
        }
        return status;
    }
    public ArrayList GetSystemCodeDetails(String LanguageID,String ID)
    {
        ArrayList status =new ArrayList();
        try
        {
            Session session=Sessions.getCurrent();
            String ResourcePaths=session.getAttribute("ResourcePaths").toString();            
            //Read Label_LanguageID;
            File fXmlFile = new File(ResourcePaths+"/SystemCodeDetails_"+ LanguageID+".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile); 
            //optional But Recommended
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Item");
            for (int temp = 0; temp < nList.getLength(); temp++) 
            {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) 
                {
                    Element eElement = (Element) nNode;
                    if(((NodeList) eElement.getElementsByTagName("ID")).item(0).getFirstChild().getNodeValue().toString().trim().equalsIgnoreCase(ID))
                    {
                        if(((NodeList) eElement.getElementsByTagName("StatusID")).item(0).getFirstChild().getNodeValue().toString().trim().equalsIgnoreCase("1"))
                        {
                            status.add(new Object[]{((NodeList) eElement.getElementsByTagName("LookUpID")).item(0).getFirstChild().getNodeValue(),
                                                     ((NodeList) eElement.getElementsByTagName("Description")).item(0).getFirstChild().getNodeValue()});
                        }
                    }
                }
            }            
        }
        catch(Exception ex)
        {

        } 
        return status;
   }
    public Comboitem getSelectedIndexComboboxItem(Combobox combobox, String value) 
     {
        List<Comboitem> items = combobox.getItems();
        Comboitem item = items.get(0);
        for (Comboitem comboitem : items) {
            String label = (String)comboitem.getLabel();
            String cval = (String)comboitem.getValue();
            if ((label!=null && label.equalsIgnoreCase(value)) || (cval != null  && cval.equalsIgnoreCase(value))) {
                item = comboitem;
                break;
            }
        }
        return item;
    }
    public String encrypt(String valueToEnc) //throws Exception 
    {
        try
        {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encValue = c.doFinal(valueToEnc.getBytes());
            String encryptedValue = new BASE64Encoder().encode(encValue);
            return encryptedValue;
        }
        catch(Exception ex)
        {
            return valueToEnc;
        }
    }
public String decrypt(String encryptedValue) //throws Exception 
    {
        try
        {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
            byte[] decValue = c.doFinal(decordedValue);
            String decryptedValue = new String(decValue);
            return decryptedValue;
        }
        catch(Exception ex)
        {
            return encryptedValue;
        }
    }    
  private static Key generateKey() throws Exception 
    {
        Key key = new SecretKeySpec(keyValue, ALGORITHM);
        return key;
    }     
    
    public String CheckNullString(Object obj )
    {
        String str="";
        try
        {
            if(obj!=null)
            {
               str=(String)obj; 
            }
        }
        catch(Exception ex)
        {
            
        }
        return str;
    }
    public Document NormalizeXMLString(String DataString)
    {
        String strLabelID=DataString;
        Document doc=null;
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc =  dBuilder.parse( new InputSource( new StringReader( strLabelID ) ) );  ; 
            //optional But Recommended
            doc.getDocumentElement().normalize();
                       
        }
        catch(Exception ex)
        {
            
        }
        return doc;
    } 
    
}
