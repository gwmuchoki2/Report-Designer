/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builder;

import Entities.eReportMaster;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gerald
 */
public class BuilderDatabaseConnection 
{
        public ArrayList GetObectFields(String ProcedureName, Object[] Params)
        {
            ResultSet rsData=null;
            ArrayList arrayData=new ArrayList();
            Connection con=null;
            CallableStatement cs=null;
            try 
            {
                con=GetDBConnections();
                if(con==null)
                    return arrayData;
                 cs= GetCallableStatement(con, ProcedureName, Params);
                 if(cs!=null)
                    cs.execute();
                 else
                     return arrayData;
                 /*String str = cs.getString(1);
                 if (str != null) {
                     System.out.println(str);
                 }
                 else 
                 {*/
                rsData = cs.getResultSet();
                ResultSetMetaData rsMetaData = rsData.getMetaData();
                int numberOfColumns = rsMetaData.getColumnCount(); 
                for (int i = 1; i <= numberOfColumns; i++) 
                {
                    System.out.println("column MetaData ");
                    System.out.println("column number " + i);

                    // get the designated column's SQL type.
                    int type = rsMetaData.getColumnType(i);
                    String typename = rsMetaData.getColumnTypeName(i);
                    System.out.println(rsMetaData.getColumnType(i));
                    arrayData.add(new Object[]{rsMetaData.getColumnName(i),rsMetaData.getColumnTypeName(i)});
                    /*
                    if (type == Types.VARCHAR || type == Types.CHAR) 
                    {
                        //out.print(rs.getString(i));
                    } 
                    else 
                    {
                        //out.print(rs.getLong(i));
                    } 
                    -7 	BIT
                   -6 	TINYINT
                   -5 	BIGINT
                   -4 	LONGVARBINARY 
                   -3 	VARBINARY
                   -2 	BINARY
                   -1 	LONGVARCHAR
                   0 	NULL
                   1 	CHAR
                   2 	NUMERIC
                   3 	DECIMAL
                   4 	INTEGER
                   5 	SMALLINT
                   6 	FLOAT
                   7 	REAL
                   8 	DOUBLE
                   12 	VARCHAR
                   91 	DATE
                   92 	TIME
                   93 	TIMESTAMP
                   1111  	OTHER */                   
                }                
//                     while (rsData.next()) 
//                     {
//                         System.out.println("Name : " + rsData.getString(2));
//                     }
                 //}
             } 
             catch (SQLException e) 
             {
                 Messagebox.show("SQLException: " + e.getMessage(),"Smart Writer",Messagebox.OK,Messagebox.ERROR);
             }
             finally 
             {
                 if (cs != null) 
                 {
                     try 
                     {
                         cs.close();
                     } 
                     catch (SQLException e) 
                     {
                         Messagebox.show("SQLException: " + e.getMessage(),"Smart Writer",Messagebox.OK,Messagebox.ERROR);
                     }
                 }
                 if (con != null) 
                 {
                     try 
                     {
                         con.close();
                     } catch (SQLException e) 
                     {
                         System.err.println("SQLException: " + e.getMessage());
                     }
                 }
             }
            return arrayData;
        }
        private CallableStatement GetCallableStatement(Connection con,String ProcedureName, Object[] Params)
        {
            CallableStatement cs=null;
            try
            {
                 String CallString ="";
                 if(Params.length==0)
                 {
                     cs = con.prepareCall("{call "+ProcedureName+"}");
                 }
                 else
                 {
                        for(int x=0;x<Params.length;x++)
                        {
                            CallString+=(CallString.equalsIgnoreCase("")?"":",")+ "?";
                            x++;
                            x++;
                        }
                        cs = con.prepareCall("{call "+ProcedureName+"("+CallString+")}");
                        //cs.registerOutParameter(1, Types.VARCHAR);
                        int i=1;
                        for(int x=0;x<Params.length;x++)
                        {
                            cs.setString(i, Params[x+1].toString());
                            x++;
                            x++;
                            i++;
                        }
                        
                 }                
            }
            catch(Exception ex)
            {
                
            }
            return cs;
        }
        public String AddEditReport(HashMap map,ArrayList lstReportFields,ArrayList lstReportSorts,ArrayList lstReportGroups)
        {
            String strReturn="";
     
            //ereport = eReportMaster.f  (map.get("ReportID").toString());
            
            
            
            
            
            
            Connection con=null;
            CallableStatement cs=null;
            try 
            {
                con=GetDBConnections();
                if(con==null)
                    return "UNABLE TO CONNECT TO DATABASE";
                String strSQL ="Select s.ReportID From rt_Columns As s Where s.ReportID=:ReportID";
                
                /*
                 cs= GetCallableStatement(con, strSQL, Params);
                 if(cs!=null)
                    cs.execute();
                 else
                     return arrayData;
                 
                  String str = cs.getString(1);
                 if (str != null) {
                     System.out.println(str);
                 }
                 else 
                 { 
                rsData = cs.getResultSet();
                */
                
            }
            catch(Exception ex)
            {
                
            }
            finally 
             {
                 if (cs != null) 
                 {
                     try 
                     {
                         cs.close();
                     } 
                     catch (SQLException e) 
                     {
                         Messagebox.show("SQLException: " + e.getMessage(),"Smart Writer",Messagebox.OK,Messagebox.ERROR);
                     }
                 }
                 if (con != null) 
                 {
                     try 
                     {
                         con.close();
                     } catch (SQLException e) 
                     {
                         System.err.println("SQLException: " + e.getMessage());
                     }
                 }
             }            
            return strReturn;
        }
        public ResultSet GetDBData(String ProcedureName, Object[] Params)
        {
            ResultSet rsData=null;
            Connection con=GetDBConnections();
            CallableStatement cs = null;
            try 
            {
                 String CallString ="";
                 if(Params.length==0)
                 {
                     cs = con.prepareCall("{call "+ProcedureName+"}");
                 }
                 else
                 {
                        for(int x=0;x<Params.length;x++)
                        {
                            CallString+=(CallString.equalsIgnoreCase("")?"":",")+ "?";
                            x++;
                            x++;
                        }
                        cs = con.prepareCall("{call "+ProcedureName+"("+CallString+")}");
                        //cs.registerOutParameter(1, Types.VARCHAR);
                        int i=1;
                        for(int x=0;x<Params.length;x++)
                        {
                            cs.setString(i, Params[x+1].toString());
                            x++;
                            x++;
                            i++;
                        }
                        
                 }
                 cs.execute();
                 /*String str = cs.getString(1);
                 if (str != null) {
                     System.out.println(str);
                 }
                 else 
                 {*/
                     rsData = cs.getResultSet();
//                     while (rsData.next()) 
//                     {
//                         System.out.println("Name : " + rsData.getString(2));
//                     }
                 //}
             } 
             catch (SQLException e) 
             {
                 Messagebox.show("SQLException: " + e.getMessage(),"Smart Writer",Messagebox.OK,Messagebox.ERROR);
             }
             finally 
             {
                 if (cs != null) 
                 {
                     try {
                         cs.close();
                     } catch (SQLException e) {
                         Messagebox.show("SQLException: " + e.getMessage(),"Smart Writer",Messagebox.OK,Messagebox.ERROR);
                     }
                 }
                 if (con != null) 
                 {
                     try {
                         con.close();
                     } catch (SQLException e) {
                         System.err.println("SQLException: " + e.getMessage());
                     }
                 }
             }           
            
            
            
            return rsData;
        }
    
 
        private Connection GetDBConnections()
        {
            Connection dbConn=null;
            try
            {

                Session session=Sessions.getCurrent();
                String ResourcePaths=session.getAttribute("ResourcePaths").toString();            
                //Read Label_LanguageID;
                File fXmlFile = new File(ResourcePaths+"/ConfigFile.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile); 
                //optional But Recommended
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("Configuration");
                for (int temp = 0; temp < nList.getLength(); temp++) 
                {
                    Node nNode = nList.item(temp);
                    System.out.println("\nCurrent Element :" + nNode.getNodeName());
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) 
                    {
                        Element eElement = (Element) nNode;

                        String DBType = ((NodeList) eElement.getElementsByTagName("DBType")).item(0).getFirstChild().getNodeValue().toString().trim();
                        String user = ((NodeList) eElement.getElementsByTagName("DBUserName")).item(0).getFirstChild().getNodeValue().toString().trim();
                        String password = ((NodeList) eElement.getElementsByTagName("DBPassword")).item(0).getFirstChild().getNodeValue().toString().trim();
                        String servername = ((NodeList) eElement.getElementsByTagName("DBServerName")).item(0).getFirstChild().getNodeValue().toString().trim();
                        String dbname = ((NodeList) eElement.getElementsByTagName("DBName")).item(0).getFirstChild().getNodeValue().toString().trim();
                        String serverport = ((NodeList) eElement.getElementsByTagName("DBPort")).item(0).getFirstChild().getNodeValue().toString().trim();
                        
                        if(DBType.equalsIgnoreCase("MySQL"))
                        {
                            Class.forName("com.mysql.jdbc.Driver").newInstance();
                            dbConn = DriverManager.getConnection("jdbc:mysql://"+ servername+":"+serverport+"/"+ dbname+"?user=" + user + "&password=" + password);
                        }
                        else if(DBType.equalsIgnoreCase("MSSQL"))
                        {
                            Class.forName("com.microsoft.sqlserver.jdbc"); 
                            dbConn = java.sql.DriverManager.getConnection("jdbc:microsoft:sqlserver://"+servername+":"+serverport+";databaseName="+dbname+";",user,password);
                        }
                        else if(DBType.equalsIgnoreCase("HSQLDB"))
                        {
                             Class.forName("org.hsqldb.jdbcDriver");
                             //System.out.println("Driver Loaded.");
                            String url = "jdbc:hsqldb:"+servername+":"+"/"+dbname;
                            dbConn = DriverManager.getConnection(url, user, password);
                        }
                        else if(DBType.equalsIgnoreCase("ORACLE"))
                        {
                           String driver = "oracle.jdbc.driver.OracleDriver";
                           String url = "jdbc:oracle:thin:@"+servername+":"+serverport+":"+dbname; 

                           Class.forName(driver); // load Oracle driver
                           dbConn = DriverManager.getConnection(url, user, password);
                        } 
                        else if(DBType.equalsIgnoreCase("POSTGRES"))
                        {
                            Class.forName("org.postgresql.Driver");
                            String url = "jdbc:postgresql://"+servername+"/"+dbname;
                            dbConn = DriverManager.getConnection(url,user, password); 
                        }                        
                        
                        

                    }
                }            
            }
            catch(Exception ex)
            {
                System.out.println(ex.getMessage());
            } 
            return dbConn;
        }
        private Connection GetReportDBConnections()
        {
            Connection dbConn=null;
            try
            {

                Session session=Sessions.getCurrent();
                String ResourcePaths=session.getAttribute("ResourcePaths").toString();            
                //Read Label_LanguageID;
                File fXmlFile = new File(ResourcePaths+"/ConfigFile.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile); 
                //optional But Recommended
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("Configuration");
                for (int temp = 0; temp < nList.getLength(); temp++) 
                {
                    Node nNode = nList.item(temp);
                    System.out.println("\nCurrent Element :" + nNode.getNodeName());
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) 
                    {
                        Element eElement = (Element) nNode;

                        String DBType = ((NodeList) eElement.getElementsByTagName("ReportDBType")).item(0).getFirstChild().getNodeValue().toString().trim();
                        String user = ((NodeList) eElement.getElementsByTagName("ReportDBUserName")).item(0).getFirstChild().getNodeValue().toString().trim();
                        String password = ((NodeList) eElement.getElementsByTagName("ReportDBPassword")).item(0).getFirstChild().getNodeValue().toString().trim();
                        String servername = ((NodeList) eElement.getElementsByTagName("ReportDBServerName")).item(0).getFirstChild().getNodeValue().toString().trim();
                        String dbname = ((NodeList) eElement.getElementsByTagName("ReportDBName")).item(0).getFirstChild().getNodeValue().toString().trim();
                        String serverport = ((NodeList) eElement.getElementsByTagName("ReportDBPort")).item(0).getFirstChild().getNodeValue().toString().trim();
                        
                        if(DBType.equalsIgnoreCase("MySQL"))
                        {
                            Class.forName("com.mysql.jdbc.Driver").newInstance();
                            dbConn = DriverManager.getConnection("jdbc:mysql://"+ servername+":"+serverport+"/"+ dbname+"?user=" + user + "&password=" + password);
                        }
                        else if(DBType.equalsIgnoreCase("MSSQL"))
                        {
                            Class.forName("com.microsoft.sqlserver.jdbc"); 
                            dbConn = java.sql.DriverManager.getConnection("jdbc:microsoft:sqlserver://"+servername+":"+serverport+";databaseName="+dbname+";",user,password);
                        }
                        else if(DBType.equalsIgnoreCase("HSQLDB"))
                        {
                             Class.forName("org.hsqldb.jdbcDriver");
                             //System.out.println("Driver Loaded.");
                            String url = "jdbc:hsqldb:"+servername+":"+"/"+dbname;
                            dbConn = DriverManager.getConnection(url, user, password);
                        }
                        else if(DBType.equalsIgnoreCase("ORACLE"))
                        {
                           String driver = "oracle.jdbc.driver.OracleDriver";
                           String url = "jdbc:oracle:thin:@"+servername+":"+serverport+":"+dbname; 

                           Class.forName(driver); // load Oracle driver
                           dbConn = DriverManager.getConnection(url, user, password);
                        } 
                        else if(DBType.equalsIgnoreCase("POSTGRES"))
                        {
                            Class.forName("org.postgresql.Driver");
                            String url = "jdbc:postgresql://"+servername+"/"+dbname;
                            dbConn = DriverManager.getConnection(url,user, password); 
                        }                        
                        
                        

                    }
                }            
            }
            catch(Exception ex)
            {
                System.out.println(ex.getMessage());
            } 
            return dbConn;
        }        
}


/*
public void displayDbProperties(){
          java.sql.DatabaseMetaData dm = null;
          java.sql.ResultSet rs = null;
          try{
               con= this.getConnection();
               if(con!=null){
                    dm = con.getMetaData();
                    System.out.println("Driver Information");
                    System.out.println("\tDriver Name: "+ dm.getDriverName());
                    System.out.println("\tDriver Version: "+ dm.getDriverVersion ());
                    System.out.println("\nDatabase Information ");
                    System.out.println("\tDatabase Name: "+ dm.getDatabaseProductName());
                    System.out.println("\tDatabase Version: "+ dm.getDatabaseProductVersion());
                    System.out.println("Avalilable Catalogs ");
                    rs = dm.getCatalogs();
                    while(rs.next()){
                         System.out.println("\tcatalog: "+ rs.getString(1));
                    } 
                    rs.close();
                    rs = null;
                    closeConnection();
               }else System.out.println("Error: No active Connection");
          }catch(Exception e){
               e.printStackTrace();
          }
          dm=null;
     } 


        try {
 
            String dbURL = "jdbc:sqlserver://localhost\\sqlexpress";
            String user = "sa";
            String pass = "secret";
            conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
 
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }


Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
    
      // the sql server url
      String url = "jdbc:microsoft:sqlserver://HOST:1433;DatabaseName=DATABASE";
      
      // get the sql server database connection
      connection = DriverManager.getConnection(url,"THE_USER", "THE_PASSWORD");
*/
