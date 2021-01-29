/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builder;

//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.sql.Types;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author gerald
 */
public class ReportDesigner extends SelectorComposer<Borderlayout>
{
    Session session;  
   @Wire 
   Textbox txtReportID;
   @Wire 
   Textbox txtReportName;
   @Wire
   Button btnSave;
   @Wire
   Tabpanel tabGroupFieldColumns;
   @Wire
   Combobox cboReportType;
   @Wire
   Combobox cboFont;
   @Wire
   Combobox cboHeaderFontColor;
   @Wire
   Combobox cboDetailFontColor;
   @Wire
   Textbox txtProcedureName;
   @Wire
   Combobox cboPaperType;
   @Wire
   Combobox cboOrientation;
   @Wire
   Decimalbox txtGroupingFontSize;
   @Wire
   Decimalbox txtHeaderFontSize;
   @Wire
   Decimalbox txtDetailFontSize;
   @Wire
   Checkbox chkHasLogo;
   @Wire
   Button btnImage;
   @Wire
   Button btnLoadFields;
   @Wire
   Grid gridColumns;
   @Wire
   Button btnAddSelectedField;
   @Wire 
   Grid gridReportColumns; 
   @Wire
   Grid gridReportGroups;
   @Wire
   Grid gridReportSort;
   @Wire
   Button btnMoveToGrouping;
   @Wire
   Button btnMoveDown;
   @Wire
   Button btnMoveUp;
   @Wire
    Checkbox chkDisabled;
   
   
   
   
   Button btnRemoveSelectedField;
   ListModelList listtrxlmodel;
   ArrayList objColumns= new ArrayList();;
   ArrayList objReportFields= new ArrayList();;
   int SelectedReportFieldIndex=0;
   ArrayList objColumnGroups =new ArrayList();
   ArrayList objColumnSorting =new ArrayList();
   static String SelectedFieldName="";
   static String FieldDataType="";
   static String RemoveSelectedFieldName="";
    @Listen("onClick = #btnLoadFields")
    public void onLoadFieldsClick() 
    {
        System.out.println("");
        try
        {
            if(txtProcedureName.getText().equalsIgnoreCase(""))
            {
                Messagebox.show("Datasource Object Not Specified", "Smart Writer", Messagebox.OK, Messagebox.EXCLAMATION, null);
                return;                
            }
            GetDBObjectFields();
            
        }   
        catch(Exception ex)
        {
            
        }
    }
    @Listen("onClick = #btnAddSelectedField" )
    public void onAddFieldClick()
    {
        try
        {
            if(gridColumns.getRows().getChildren().size()==0)
            {
                Messagebox.show("No Rows on the Fields Grid", "Smart Writer", Messagebox.OK, Messagebox.EXCLAMATION, null);
                return;                 
            }
            if(SelectedFieldName.equalsIgnoreCase(""))
            {
                Messagebox.show("No Rows Selected on the Fields Grid", "Smart Writer", Messagebox.OK, Messagebox.EXCLAMATION, null);
                return;                 
            } 
            //Add The Fields to The Report Fields
            if(objReportFields==null)
            {
                objReportFields= new ArrayList(); 
            }
            boolean bolFound= false;
            for(int i=0;i<objReportFields.size();i++)
            {
                if(((Object[])objReportFields.get(i))[0].toString().equalsIgnoreCase(SelectedFieldName))
                {
                    bolFound= true;
                }
            }
            if(!bolFound)
            {
                objReportFields.add(new Object[]{SelectedFieldName,SelectedFieldName,80,"",FieldDataType});
                RefreshReportGrid();
            }
           
        }
        catch(Exception ex)
        {
            
        }
    }
  
    @Listen("onClick = #btnMoveDown" )
    public void onbtnMoveDown()
    {
        try
        {
            if(gridReportColumns.getRows().getChildren().size()==0)
            {
                Messagebox.show("No Rows on the Report Fields Grid", "Smart Writer", Messagebox.OK, Messagebox.EXCLAMATION, null);
                return;                 
            }
            if(SelectedFieldName.equalsIgnoreCase(""))
            {
                Messagebox.show("No Rows Selected on the Report Fields Grid", "Smart Writer", Messagebox.OK, Messagebox.EXCLAMATION, null);
                return;                 
            } 
            //If Its the Last Column Do Nothing
            
            
            //Move Fields Down 
           
            for(int i=0;i<objReportFields.size();i++)
            {
                if(((Object[])objReportFields.get(i))[0].toString().equalsIgnoreCase(RemoveSelectedFieldName))
                { 
                     
                    if(SelectedReportFieldIndex+1>=objReportFields.size())
                    {
                        return ;
                    }
                    //Switch Values
                    ArrayList nextmovingValue =new ArrayList();
                    nextmovingValue.add(new Object[]{((Object[])objReportFields.get(i))[0],
                                                      ((Object[])objReportFields.get(i))[1],
                                                        ((Object[])objReportFields.get(i))[2],
                                                        ((Object[])objReportFields.get(i))[3],
                                                        ((Object[])objReportFields.get(i))[4]});
                    objReportFields.set(i, new Object[]{((Object[])objReportFields.get(i+1))[0],
                                                      ((Object[])objReportFields.get(i+1))[1],
                                                        ((Object[])objReportFields.get(i+1))[2],
                                                        ((Object[])objReportFields.get(i+1))[3],
                                                        ((Object[])objReportFields.get(i+1))[4]});
                    objReportFields.set(i+1,nextmovingValue.get(0)); 
                    SelectedReportFieldIndex++;
                    RefreshReportGrid();
                    break;
                }
            }
             
        }
        catch(Exception ex)
        {
            
        }
    }    
   
    
    @Listen("onClick = #btnMoveUp" )
    public void onbtnMoveUp()
    {
        try
        {
            if(gridReportColumns.getRows().getChildren().size()==0)
            {
                Messagebox.show("No Rows on the Report Fields Grid", "Smart Writer", Messagebox.OK, Messagebox.EXCLAMATION, null);
                return;                 
            }
            if(SelectedFieldName.equalsIgnoreCase(""))
            {
                Messagebox.show("No Rows Selected on the Report Fields Grid", "Smart Writer", Messagebox.OK, Messagebox.EXCLAMATION, null);
                return;                 
            } 
            //If Its the Last Column Do Nothing
            
            
            //Move Fields Down 
           
            for(int i=0;i<objReportFields.size();i++)
            {
                if(((Object[])objReportFields.get(i))[0].toString().equalsIgnoreCase(RemoveSelectedFieldName))
                { 
                     
                    if(SelectedReportFieldIndex==0)
                    {
                        return ;
                    }
                    //Switch Values
                    ArrayList nextmovingValue =new ArrayList();
                    nextmovingValue.add(new Object[]{((Object[])objReportFields.get(i))[0],
                                                      ((Object[])objReportFields.get(i))[1],
                                                        ((Object[])objReportFields.get(i))[2],
                                                        ((Object[])objReportFields.get(i))[3],
                                                        ((Object[])objReportFields.get(i))[4]});
                    objReportFields.set(i, new Object[]{((Object[])objReportFields.get(i-1))[0],
                                                      ((Object[])objReportFields.get(i-1))[1],
                                                        ((Object[])objReportFields.get(i-1))[2],
                                                        ((Object[])objReportFields.get(i-1))[3],
                                                        ((Object[])objReportFields.get(i-1))[4]});
                    objReportFields.set(i-1,nextmovingValue.get(0)); 
                    SelectedReportFieldIndex--;
                    RefreshReportGrid();
                    break;
                }
            }
             
        }
        catch(Exception ex)
        {
            
        }
    }              
            
    @Listen("onClick = #btnRemoveSelectedField" )
    public void onRemoveSelectedField()
    {
        try
        {
            if(gridReportColumns.getRows().getChildren().isEmpty())
            { 
                Messagebox.show("No Rows on the Report Fields Grid", "Smart Writer", Messagebox.OK, Messagebox.EXCLAMATION, null);
                return;                 
            }
            if(SelectedFieldName.equalsIgnoreCase(""))
            {
                Messagebox.show("No Rows Selected on the Report Fields Grid", "Smart Writer", Messagebox.OK, Messagebox.EXCLAMATION, null);
                return;                 
            } 
            //Add The Fields to The Report Fields
            if(objReportFields==null)
            {
                Messagebox.show("No Rows Selected on the Report Fields Grid", "Smart Writer", Messagebox.OK, Messagebox.EXCLAMATION, null);
                return;
            }
            boolean bolFound= false;
            ArrayList tempArrayList = new ArrayList();
            for(int i=0;i<objReportFields.size();i++)
            {
                if(((Object[])objReportFields.get(i))[0].toString().equalsIgnoreCase(RemoveSelectedFieldName))
                {
                    bolFound= true;
                    
                }
                else
                {
                    tempArrayList.add(new Object[]{((Object[])objReportFields.get(i))[0],
                                    ((Object[])objReportFields.get(i))[1],
                                    ((Object[])objReportFields.get(i))[2],
                                    ((Object[])objReportFields.get(i))[3],
                                    ((Object[])objReportFields.get(i))[4]
                                });
                }
            }
            if(bolFound)
            {
                objReportFields = tempArrayList; 
                RefreshReportGrid();
                tempArrayList=null;
            }
           
        }
        catch(Exception ex)
        {
            
        }
    }  
    
    private void RefreshReportGrid()
    {
        try
        {
                listtrxlmodel = new ListModelList(objReportFields);
                gridReportColumns.setModel(listtrxlmodel);                    
                gridReportColumns.setRowRenderer(new RowRenderer() 
                {  
                    public void render(Row row, Object data, int i) throws Exception {
                        row.appendChild(new Label(((Object[])data)[0].toString()));
                        Textbox tb= new Textbox();
                        tb.setId("txt"+((Object[])data)[0].toString());
                        tb.setText(((Object[])data)[1].toString());
                        row.appendChild(tb);
                        
                        Intbox tbi= new Intbox();
                        tbi.setId("int"+((Object[])data)[0].toString());
                        tbi.setText(((Object[])data)[2].toString());
                        row.appendChild(tbi);                        
                         
                        ArrayList states= new ArrayList();
                        Combobox cbo = new Combobox();
                        cbo.setId("cbo"+((Object[])data)[0].toString());                        
                        String IDType = ((Object[])data)[4].toString();                         
                        states =new BuilderGlobals().GetColumnSummaryFunctions("en",IDType);
                        new BuilderGlobals().PopulateZKossCombo(cbo,states);
                        row.appendChild(cbo);
                        row.appendChild(new Label(((Object[])data)[4].toString()));
                         
                        
                        row.addEventListener(Events.ON_CLICK, new EventListener<Event>() 
                        {
                            @Override
                            public void onEvent(Event arg0) throws Exception 
                            {
                                Row row = (Row) arg0.getTarget();
                                Label lblTrxRowID = (Label)row.getChildren().get(0);
                                RemoveSelectedFieldName= ((Label)row.getChildren().get(0)).getValue();
                                SelectedReportFieldIndex = row.getIndex();
                                 
                            }
                        });
                
                        
                    } 
                });               
        }
        catch(Exception ex)
        {
            
        }
    }
    @Listen("onClick = #btnSave")
    public void onSaveClick() 
    {
        System.out.println("");
        try
        {
            
            if(txtReportName.getText().equalsIgnoreCase(""))
            {
                //Messagebox.show("Provide a Report Name", "Smart Writer", Messagebox.OK, Messagebox.EXCLAMATION, null);
                 Clients.showNotification("Provide a Report Name", "info", txtReportName, "end_center", 2000);
                 return;
            }
            if(txtReportName.getText().equalsIgnoreCase(""))
            {
                Messagebox.show("Provide Report Type", "Smart Writer", Messagebox.OK, Messagebox.EXCLAMATION, null);
                //Clients.showNotification("Provide a Report Name", "info", txtReportName, "end_center", 2000);
                return;
            }  
            if(cboReportType.getSelectedItem()==null)
            {
                new BuilderGlobals().showNotify("Specify Report Type", cboReportType);
                cboReportType.setFocus(true);
                return;                
            } 
            
            if(cboFont.getSelectedItem()==null)
            {
                new BuilderGlobals().showNotify("Specify Font", cboFont);
                cboFont.setFocus(true);
                return;                
            }
            if(cboHeaderFontColor.getSelectedItem()==null)
            {
                new BuilderGlobals().showNotify("Specify Header Font Colour", cboFont);
                cboHeaderFontColor.setFocus(true);
                return;                
            } 
            if(cboDetailFontColor.getSelectedItem()==null)
            {
                new BuilderGlobals().showNotify("Specify Detail Font Colour", cboDetailFontColor);
                cboDetailFontColor.setFocus(true);
                return;                
            } 
            if(txtProcedureName.getText().equalsIgnoreCase(""))
            {
                new BuilderGlobals().showNotify("Specify Data Source Name", txtProcedureName);
                txtProcedureName.setFocus(true);
                return;                
            } 
            if(cboPaperType.getSelectedItem()==null)
            {
                new BuilderGlobals().showNotify("Specify Paper Type", cboPaperType);
                cboPaperType.setFocus(true);
                return;                
            } 
            if(cboPaperType.getSelectedItem()==null)
            {
                new BuilderGlobals().showNotify("Specify Paper Type", cboPaperType);
                cboPaperType.setFocus(true);
                return;                
            }
            if(cboOrientation.getSelectedItem()==null)
            {
                new BuilderGlobals().showNotify("Specify Orientation", cboOrientation);
                cboOrientation.setFocus(true);
                return;                
            }     
            if(txtGroupingFontSize.getValue()==null)
            {
                new BuilderGlobals().showNotify("Specify Group Font Size", txtGroupingFontSize);
                txtGroupingFontSize.setFocus(true);
                return;                
            } 
            if(txtGroupingFontSize.getValue()==BigDecimal.valueOf(0))
            {
                new BuilderGlobals().showNotify("Specify Group Font Size", txtGroupingFontSize);
                txtGroupingFontSize.setFocus(true);
                return;                
            }  
            
            if(txtHeaderFontSize.getValue()==null)
            {
                new BuilderGlobals().showNotify("Specify Header Font Size", txtHeaderFontSize);
                txtHeaderFontSize.setFocus(true);
                return;                
            } 
            if(txtHeaderFontSize.getValue()==BigDecimal.valueOf(0))
            {
                new BuilderGlobals().showNotify("Specify Header Font Size", txtHeaderFontSize);
                txtHeaderFontSize.setFocus(true);
                return;                
            }  
            
            if(txtDetailFontSize.getValue()==null)
            {
                new BuilderGlobals().showNotify("Specify Detail Font Size", txtDetailFontSize);
                txtDetailFontSize.setFocus(true);
                return;                
            }  
            if(txtDetailFontSize.getValue()==BigDecimal.valueOf(0))
            {
                new BuilderGlobals().showNotify("Specify Detail Font Size", txtDetailFontSize);
                txtDetailFontSize.setFocus(true);
                return;                
            }  
            
            
            HashMap map = new HashMap();
            map.put("ReportID",txtReportID.getText());
            map.put("ReportName",txtReportName.getText());
            map.put("FontName",cboFont.getSelectedItem().getValue());
            map.put("ReportType",cboReportType.getSelectedItem().getValue());
            map.put("Font",cboFont.getSelectedItem().getValue());
            map.put("HeaderFontColor",cboHeaderFontColor.getSelectedItem().getValue());
            map.put("DetailFontColor",cboDetailFontColor.getSelectedItem().getValue());
            map.put("ProcedureName",txtProcedureName.getText());
            map.put("PaperType",cboPaperType.getSelectedItem().getValue());
            map.put("Orientation",cboOrientation.getSelectedItem().getValue());
            map.put("GroupingFontSize",txtGroupingFontSize.getValue());
            map.put("HeaderFontSize",txtHeaderFontSize.getValue());
            map.put("DetailFontSize",txtDetailFontSize.getValue());
            map.put("HasLogo",chkHasLogo.isChecked());
            map.put("Disabled",chkDisabled.isChecked());
            //session.setAttribute("MunduNu","GEE");
            map.put("LogoURL","");
            map.put("UserID",new BuilderGlobals().CheckNullString(session.getAttribute("MunduNu")));
            //Save The Report Fields
            Rows rows=gridReportColumns.getRows();
            ArrayList lstReportFields = new ArrayList();
            int count=rows.getChildren().size();
            int lp=1;
            if(count==0)
            {
              Messagebox.show("No Rows on the Report Fields Grid", "Smart Writer", Messagebox.OK, Messagebox.EXCLAMATION, null);
                return;                 
            }
            for (  Object child : rows.getChildren()) 
            {
//                try
//                {
                    if(lp<=count)
                    {
                        Row row=(Row)child;  
                        lstReportFields.add(new Object[]{
                                                  ((Label)row.getChildren().get(0)).getValue(),
                                                  ((Textbox)row.getChildren().get(1)).getValue(),
                                                  ((Intbox)row.getChildren().get(2)).getValue(),
                                                  ((Combobox)row.getChildren().get(3)).getSelectedItem().getValue(),
                                                  ((Label)row.getChildren().get(4)).getValue(),
                                                  lp
                                          });
                        lp++;
                    }
//                }
//                catch(Exception ex)
//                {
//                    
//                }
            }            
            //Save The Grouping Fields
            rows=gridReportGroups.getRows();
            ArrayList lstReportGroups = new ArrayList();
            count=rows.getChildren().size();
            lp=1;
            for (  Object child : rows.getChildren()) 
            {
//                try
//                {
                    if(lp<count)
                    {
                        Row row=(Row)child;
                        if(((Combobox)row.getChildren().get(1)).getSelectedItem()!=null)
                        {
                            if(!((Combobox)row.getChildren().get(1)).getSelectedItem().getValue().toString().equalsIgnoreCase("NONE"))
                            {
                                lstReportGroups.add(new Object[]{
                                                          ((Label)row.getChildren().get(0)).getValue(),
                                                          ((Combobox)row.getChildren().get(1)).getSelectedItem().getValue(),
                                                          lp
                                                  });
                            }
                        }
                        lp++;
                    }
//                }
//                catch(Exception ex)
//                {
//                    
//                }
            }            
            //Save The Sorting Fields
            rows=gridReportSort.getRows();
            ArrayList lstReportSorts = new ArrayList();
            count=rows.getChildren().size();
            lp=1;
            for (  Object child : rows.getChildren()) 
            {
//                try
//                {
                    if(lp<count)
                    {
                        Row row=(Row)child;
                        if(((Combobox)row.getChildren().get(1)).getSelectedItem()!=null)
                        {
                            if(!((Combobox)row.getChildren().get(1)).getSelectedItem().getValue().toString().equalsIgnoreCase("NONE"))
                            {
                                lstReportSorts.add(new Object[]{
                                                          ((Label)row.getChildren().get(0)).getValue(),
                                                          ((Combobox)row.getChildren().get(1)).getSelectedItem().getValue(),
                                                          lp
                                                  });
                            }
                        }
                        lp++;
                    }
//                }
//                catch(Exception ex)
//                {
//                    
//                }
            }            
            //Save The Logo
            
            //Save The Report Details
            
            Object[] SaveResponse = new bSessionWriter().AddEditReport(map,lstReportFields,lstReportSorts,lstReportGroups);
            
        }
        catch(Exception ex)
        {
            
        }
    }
//      private void AddGridRowListener()
//    {
//        try
//        {
//            SelectedFieldName=""; 
//            for (Component row : lstTransactions.getRows().getChildren()) 
//            {
//                    row.addEventListener(Events.ON_CLICK, new EventListener<Event>() 
//                    {
//                        @Override
//                        public void onEvent(Event arg0) throws Exception 
//                        {
//                            Row row = (Row) arg0.getTarget();
//                            Label lblTrxRowID = (Label)row.getChildren().get(0);
//                            
//                            SelectedOBNo =  lblTrxRowID.getValue();
//                            SelectedRefNo =  ((Label)row.getChildren().get(1)).getValue();
//                            /*
//                            Boolean rowSelected = (Boolean) row.getAttribute("Selected");
//                            if (rowSelected != null && rowSelected) {
//                                row.setAttribute("Selected", false);
//                                // row.setStyle("");
//                                row.setSclass("");
//                            } else {
//                                row.setAttribute("Selected", true);
//                                // row.setStyle("background-color: #CCCCCC !important");   // inline style
//                                row.setSclass("z-row-background-color-on-select");         // sclass
//                            }*/
//                        }
//                    });
//                }            
//        }
//        catch(Exception ex)
//        {
//            
//        }
//    } 
    private void LoadCombos()
    {
        try
        {
            ArrayList states = new ArrayList();
            BuilderGlobals bglobals = new BuilderGlobals();
            
            states = bglobals.GetSystemCodeDetails("en", "Font");
            bglobals.PopulateZKossCombo(cboFont, states);
            states = bglobals.GetSystemCodeDetails("en", "ReportType");
            bglobals.PopulateZKossCombo(cboReportType, states);     
            states = bglobals.GetSystemCodeDetails("en", "FontColor");
            bglobals.PopulateZKossCombo(cboHeaderFontColor, states);  
            states = bglobals.GetSystemCodeDetails("en", "FontColor");
            bglobals.PopulateZKossCombo(cboDetailFontColor, states);  
            states = bglobals.GetSystemCodeDetails("en", "PaperOrientation");
            bglobals.PopulateZKossCombo(cboOrientation, states);    
            states = bglobals.GetSystemCodeDetails("en", "PaperType");
            bglobals.PopulateZKossCombo(cboPaperType, states);             
            
        }
        catch(Exception ex)
        {
            
        }
    }
   
    private void GetDBObjectFields()
    {
        try
        {
            SelectedFieldName="";
            FieldDataType="";
            RemoveSelectedFieldName="";
            SelectedReportFieldIndex=-1;
            BuilderDatabaseConnection bc = new BuilderDatabaseConnection();
            Object[] Params=new Object[3];
            Params[0] ="CheckColumns";
            Params[1] =1;
            Params[2]="Int";
            
            //ResultSet rsObjectFields = bc.GetObectFields(txtProcedureName.getText(), Params);
            objColumns= bc.GetObectFields(txtProcedureName.getText(), Params);
            if(objColumns==null)
            {
                Messagebox.show("The Application Could Not Fetch the Procedure Fields", "Smart Writer", Messagebox.OK, Messagebox.EXCLAMATION, null);
                return;
            }
            
            if (objColumns != null) 
            {
                
                objColumnGroups = new ArrayList();
                for(int i=0;i<objColumns.size();i++)
                {
                    objColumnGroups.add(new Object[]{((Object[])objColumns.get(i))[0],""});
                }
                ListModelList listtrxlmodelx = new ListModelList(objColumnGroups);
                gridReportGroups.setModel(listtrxlmodelx);                    
                gridReportGroups.setRowRenderer(new RowRenderer() 
                {  
                    public void render(Row row, Object data, int i) throws Exception {
                        row.appendChild(new Label(((Object[])data)[0].toString()));
                        Combobox cbo = new Combobox();
                        cbo.setId("cboGroup"+((Object[])data)[0].toString());                           
                        ArrayList states =new BuilderGlobals().GetSystemCodeDetails("en","GroupSortType");
                        new BuilderGlobals().PopulateZKossCombo(cbo,states);
                        row.appendChild(cbo); 
                                             
                        
                    }
                });
                
                objColumnSorting=objColumnGroups;
                ListModelList listtrxlmodelsort = new ListModelList(objColumnSorting);
                gridReportSort.setModel(listtrxlmodelsort);                    
                gridReportSort.setRowRenderer(new RowRenderer() 
                {  
                    public void render(Row row, Object data, int i) throws Exception {
                        row.appendChild(new Label(((Object[])data)[0].toString()));
                        Combobox cbo = new Combobox();
                        cbo.setId("cboSort"+((Object[])data)[0].toString());                           
                        ArrayList states =new BuilderGlobals().GetSystemCodeDetails("en","ReportSortType");
                        new BuilderGlobals().PopulateZKossCombo(cbo,states);
                        row.appendChild(cbo); 
                                             
                        
                    }
                });                        
                //ResultSetMetaData rsMetaData = rsObjectFields.getMetaData();
                //int numberOfColumns = rsMetaData.getColumnCount();
                //System.out.println("resultSet MetaData column Count=" + numberOfColumns);
                
                //Render To the Grid
                listtrxlmodel = new ListModelList(objColumns);
                gridColumns.setModel(listtrxlmodel);                    
                gridColumns.setRowRenderer(new RowRenderer() 
                {  
                    public void render(Row row, Object data, int i) throws Exception {
                        row.appendChild(new Label(((Object[])data)[0].toString()));
                        row.appendChild(new Label(((Object[])data)[1].toString()));
                         
                        
                        row.addEventListener(Events.ON_CLICK, new EventListener<Event>() 
                        {
                            @Override
                            public void onEvent(Event arg0) throws Exception 
                            {
                                Row row = (Row) arg0.getTarget(); 
                                SelectedFieldName = ((Label)row.getChildren().get(0)).getValue();
                                FieldDataType = ((Label)row.getChildren().get(1)).getValue();
                                
                                //Clients.showNotification("Ref :"+((Label)row.getChildren().get(1)).getValue()+ " ,User ID :"+((Label)row.getChildren().get(0)).getValue());
                                
                                 
                            }
                        }); 
                         
                    } 
                });                
                //AddGridRowListener();
                
                /*
                while (rsObjectFields.next()) 
                {
                    ResultSetMetaData rsmd = rsObjectFields.getMetaData();
                    //String ResultSetMetaData.getColumnTypeName(int column)
                    //int ResultSetMetaData.getColumnType(int column)
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) 
                    {
                        if (i > 1) 
                        {
                        
                        }

                        int type = rsmd.getColumnType(i);
                        if (type == Types.VARCHAR || type == Types.CHAR) 
                        {
                            //out.print(rs.getString(i));
                        } 
                        else 
                        {
                            //out.print(rs.getLong(i));
                        }
                    }
                    break;

                    //out.println();
                }
                */
            }
        }
        catch(Exception ex)
        {
            
        }
    }
    public void FormLoad()
    {
       
        try
        { 
            session= Sessions.getCurrent(); 

            //bd.setReadonly(true);
            //cboUserName.setReadonly(true);
            
            //bd.setText(new GlobalMethods.ApexGlobalClass().CheckNullString(session.getAttribute("MunduNu")));
            System.out.println("");
            //tabGroupFieldColumns.setVisible(false);
            LoadCombos();
        }
        catch(Exception ex)
        {
            
        }
    }
    public void doAfterCompose(Borderlayout comp) throws Exception 
    {
            super.doAfterCompose(comp);
            Events.postEvent("onMainCreate", comp, null);
            FormLoad();
//            if(Servlets.isGecko((javax.servlet.ServletRequest )Executions.getCurrent().getNativeRequest()))
//            {
//                test.setValue("this is firefox");
//            }else
//            {
//             test.setValue("this is not firefox");
//            }
    }    
    
}
