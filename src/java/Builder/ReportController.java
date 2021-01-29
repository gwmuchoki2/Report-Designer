/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builder;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

/**
 *
 * @author gerald
 */
public class ReportController extends SelectorComposer<Window>
{
    
    //private static final Log log = Log.lookup(MainLayoutComposer.class);
    Session session;  
    @Wire
    Toolbarbutton btnReportViewer;
    @Wire
    Toolbarbutton btnReportDesigner;
    @Wire
    Toolbarbutton onBtnLogOff;
    @Wire
    Window MyWin;
        //@Override
    @Listen("onClick = #btnReportViewer")
    public void onBtnViewer() 
    {
        System.out.println("");
        try
        {
            Window win = null;
            win = (Window) Executions.createComponents("Forms/ReportViewer.zul", MyWin, null);
            win.setTitle("Report Viewer");
            win.setClosable(true);
            win.doModal();
        }
        catch(Exception ex)
        {
            System.out.println(""+ex.getMessage());
        }
         
    
    }
    

    @Listen("onClick = #onBtnLogOff")
    public void onBtnLogOff() 
    {
        System.out.println("");
                try
            {
                Window win = null;
                win = (Window) Executions.createComponents("Forms/KnockKnock.zul", MyWin, null);
                win.setTitle("Report Log In");
                win.setClosable(false);
                win.doModal();
            }
            catch(Exception ex)
            {
                System.out.println(""+ex.getMessage());
            }
         
    }            
    @Listen("onClick = #btnReportDesigner")
    public void onBtnDesigner() 
    {
        System.out.println("");
        try
        {
            Window win = null;
            win = (Window) Executions.createComponents("Forms/ReportDesigner.zul", MyWin, null);
            win.setTitle("Report Designer");
            win.setClosable(true);
            win.doModal();
        }
        catch(Exception ex)
        {
            System.out.println(""+ex.getMessage());
        }
         
    }
    public void doAfterCompose(Window comp) throws Exception 
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
    
    public void FormLoad()
    {
        try
        { 
            session= Sessions.getCurrent(); 
            String sPath  = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
            sPath+="\\resources\\";             
            session.setAttribute("ResourcePaths", sPath);            
            //bd.setReadonly(true);
            //cboUserName.setReadonly(true);
            
            //bd.setText(new GlobalMethods.ApexGlobalClass().CheckNullString(session.getAttribute("MunduNu")));
            System.out.println("");
            try
            {
                Window win = null;
                win = (Window) Executions.createComponents("Forms/KnockKnock.zul", MyWin, null);
                win.setTitle("Report Log In");
                win.setClosable(false);
                win.doModal();
            }
            catch(Exception ex)
            {
                System.out.println(""+ex.getMessage());
            }
            
        }
        catch(Exception ex)
        {
            
        }
    } 
}
