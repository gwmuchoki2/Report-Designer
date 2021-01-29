/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builder;

import Entities.eReportFields;
import Entities.eReportGroups;
import Entities.eReportMaster;
import Entities.eReportSorting;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author gerald
 */
@Stateless
public class bSessionWriter 
{
    @PersistenceContext(unitName = "ReportDesignBuilderPU",name="ReportDesignBuilderPU")
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    EntityManager em;
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void GetInitialContext() //throws NamingException
    {   
        try
        {
            if(em==null)
            {
                em=BuilderGlobals.getMyglobalEM();
                //emn=  new GlobalMethods.EntityHelper().
            }            
            if(em==null)
            {
                //em = JpaUtil.getEntityManager("onlineBeanPU");  
                //em.getEntityManagerFactory().getCache().evictAll();
//                Context initCtx = new InitialContext();
//                // perform JNDI lookup to obtain container-managed entity manager
//                em= (javax.persistence.EntityManager)initCtx.lookup("java:comp/env/onlineBeanPU");                
                BuilderGlobals.GetInitialContext();
                em=BuilderGlobals.getMyglobalEM();                
            }
        }
        catch(Exception ex)
        {
            
        }
    }
    public Object[] AddEditReport(HashMap map,ArrayList lstReportFields,ArrayList lstReportSorts,ArrayList lstReportGroups)
    {
       eReportMaster ereport = null;
       Query query = null;
       long reportID=0;
       Object[] Response = new Object[2];
       Response[0]= false;
       Response[1]= "System Error";
       try
       {
            GetInitialContext();
            if(map.get("ReportID").toString().equalsIgnoreCase(""))
            {
                UserTransaction transaction = null; transaction =(UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");//onlineBeanPU
                transaction.begin();
                ereport=new eReportMaster();
                ereport.setCreatedBy(map.get("UserID").toString());
                ereport.setCreatedOn(new Date());
                ereport.setDataSourceName(map.get("ProcedureName").toString());
                ereport.setDetailFontColor(map.get("DetailFontColor").toString());
                ereport.setDetailFontSize(Integer.parseInt( map.get("DetailFontSize").toString()));
                ereport.setGroupFontSize(Integer.parseInt( map.get("GroupingFontSize").toString()));
                ereport.setHeaderFontSize(Integer.parseInt( map.get("HeaderFontSize").toString()));
                ereport.setFontName(  map.get("FontName").toString());
                ereport.setHasLogo(Boolean.parseBoolean(map.get("HasLogo").toString()));
                ereport.setHeaderFontColor(map.get("HeaderFontColor").toString());
                ereport.setIsActive(Boolean.parseBoolean(map.get("Disabled").toString()));
                ereport.setLogoPath(null);
                ereport.setOrientation(map.get("Orientation").toString());
                ereport.setPaperType(map.get("PaperType").toString());
                ereport.setReportName(map.get("ReportName").toString());
                ereport.setReportType(map.get("ReportType").toString());                
                em.persist(ereport);
                
                query = em.createQuery("Select Max(e.ReportID) From eReportMaster As e Where e.CreatedBy=:CreatedBy");
                query.setParameter("CreatedBy", map.get("UserID").toString());
                if(!query.getResultList().isEmpty())
                {
                    reportID = Long.parseLong(query.getResultList().get(0).toString());
                    query = em.createQuery("Update eReportMaster As e Set e.ModuleID=:ModuleID Where e.ReportID=:ReportID");
                    query.setParameter("ReportID", reportID);
                    map.put("ReportID",new BuilderGlobals().ReverseStuffKey(Long.toString(reportID),5));
                    query.setParameter("ModuleID", map.get("ReportID").toString());
                    query.executeUpdate();
                }
                
                //lstReportFields
                eReportFields efield = null;
                for(int i=0;i<lstReportFields.size();i++)
                {
                    efield = new eReportFields();
                    efield.setFieldName(((Object[])lstReportFields.get(i))[0].toString());
                    efield.setSummaryFunction(((Object[])lstReportFields.get(i))[3].toString());
                    efield.setDisplayText(((Object[])lstReportFields.get(i))[1].toString());
                    efield.setColumnWidth(Integer.parseInt(((Object[])lstReportFields.get(i))[2].toString()));
                    efield.setColumnOrder(Integer.parseInt(((Object[])lstReportFields.get(i))[5].toString()));
                    efield.setModuleID( map.get("ReportID").toString());
                    em.persist(efield);
                }
                eReportGroups egroup = null;
                for(int i=0;i<lstReportGroups.size();i++)
                {
                    egroup = new eReportGroups();
                    egroup.setFieldName(((Object[])lstReportGroups.get(i))[0].toString());
                    egroup.setGroupingSortFunction(((Object[])lstReportGroups.get(i))[1].toString()); 
                    egroup.setGroupOrder(Integer.parseInt(((Object[])lstReportGroups.get(i))[2].toString()));
                    egroup.setModuleID( map.get("ReportID").toString());
                    em.persist(egroup);
                }  
                eReportSorting esort =null;
                for(int i=0;i<lstReportSorts.size();i++)
                {
                    esort = new eReportSorting();
                    esort.setFieldName(((Object[])lstReportSorts.get(i))[0].toString());
                    esort.setSortType(((Object[])lstReportSorts.get(i))[1].toString()); 
                    esort.setSortOrder(Integer.parseInt(((Object[])lstReportSorts.get(i))[2].toString()));
                    esort.setModuleID( map.get("ReportID").toString());
                    em.persist(esort);
                }                 
                transaction.commit(); 
                Response[0]= true;
                Response[1] = map.get("ReportID");
            }
            else
            {
               query =em.createQuery("Select e.ReportID From eReportMaster As e Where e.ModuleID=:ModuleID");
               query.setParameter("ModuleID", map.get("ReportID").toString());
               if(query.getResultList().isEmpty())
               {
                    UserTransaction transaction = null; transaction =(UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");//onlineBeanPU
                    transaction.begin(); 
                    ereport = em.find(eReportMaster.class,Long.parseLong(query.getResultList().get(0).toString())); 
                    if(ereport!=null)
                    {
                        ereport.setModifiedBy(map.get("UserID").toString());
                        ereport.setModifiedOn(new Date());
                        ereport.setDataSourceName(map.get("ProcedureName").toString());
                        ereport.setDetailFontColor(map.get("DetailFontColor").toString());
                        ereport.setDetailFontSize(Integer.parseInt( map.get("DetailFontSize").toString()));
                        ereport.setGroupFontSize(Integer.parseInt( map.get("GroupingFontSize").toString()));
                        ereport.setHeaderFontSize(Integer.parseInt( map.get("HeaderFontSize").toString()));
                        ereport.setFontName(  map.get("FontName").toString());
                        ereport.setHasLogo(Boolean.parseBoolean(map.get("HasLogo").toString()));
                        ereport.setHeaderFontColor(map.get("HeaderFontColor").toString());
                        ereport.setIsActive(Boolean.parseBoolean(map.get("Disabled").toString()));
                        ereport.setLogoPath(null);
                        ereport.setOrientation(map.get("Orientation").toString());
                        ereport.setPaperType(map.get("PaperType").toString());
                        ereport.setReportName(map.get("ReportName").toString());
                        ereport.setReportType(map.get("ReportType").toString());                
                        em.persist(ereport);
                    }
                    //Save Report Groups
                    query = em.createQuery("Delete From eReportFields As e Where e.ModuleID=:ModuleID");
                    query.setParameter("ReportID", map.get("ReportID").toString());
                    query.executeUpdate();
                    
                    query = em.createQuery("Delete From eReportGroups As e Where e.ModuleID=:ModuleID");
                    query.setParameter("ReportID", map.get("ReportID").toString());
                    query.executeUpdate();                    
                    
                    query = em.createQuery("Delete From eReportSorting As e Where e.ModuleID=:ModuleID");
                    query.setParameter("ReportID", map.get("ReportID").toString());
                    query.executeUpdate();
                    
                    eReportFields efield = null;
                    for(int i=0;i<lstReportFields.size();i++)
                    {
                        efield = new eReportFields();
                        efield.setFieldName(((Object[])lstReportFields.get(i))[0].toString());
                        efield.setSummaryFunction(((Object[])lstReportFields.get(i))[3].toString());
                        efield.setDisplayText(((Object[])lstReportFields.get(i))[1].toString());
                        efield.setColumnWidth(Integer.parseInt(((Object[])lstReportFields.get(i))[2].toString()));
                        efield.setColumnOrder(Integer.parseInt(((Object[])lstReportFields.get(i))[5].toString()));
                        efield.setModuleID( map.get("ReportID").toString());
                        em.persist(efield);
                    }
                    eReportGroups egroup = null;
                    for(int i=0;i<lstReportGroups.size();i++)
                    {
                        egroup = new eReportGroups();
                        egroup.setFieldName(((Object[])lstReportGroups.get(i))[0].toString());
                        egroup.setGroupingSortFunction(((Object[])lstReportGroups.get(i))[1].toString()); 
                        egroup.setGroupOrder(Integer.parseInt(((Object[])lstReportGroups.get(i))[2].toString()));
                        egroup.setModuleID( map.get("ReportID").toString());
                        em.persist(egroup);
                    }  
                    eReportSorting esort =null;
                    for(int i=0;i<lstReportFields.size();i++)
                    {
                        esort = new eReportSorting();
                        esort.setFieldName(((Object[])lstReportFields.get(i))[0].toString());
                        esort.setSortType(((Object[])lstReportFields.get(i))[1].toString()); 
                        esort.setSortOrder(Integer.parseInt(((Object[])lstReportFields.get(i))[2].toString()));
                        esort.setModuleID( map.get("ReportID").toString());
                        em.persist(esort);
                    }                     
                    transaction.commit(); 
               }
                Response[0]= true;
                Response[1] = map.get("ReportID");               
            } 
       }catch(Exception ex)
       {
                 Response[0]= false;
                Response[1] = ex.getMessage();          
       }
       return Response;
   }
}
