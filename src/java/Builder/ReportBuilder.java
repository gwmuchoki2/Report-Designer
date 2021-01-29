/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builder;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

/**
 *
 * @author gerald
 */
public class ReportBuilder extends SelectorComposer<Window> 
{
    Session session;  
    @Wire
    Window MyWin;
    private void FormLoad()
    {
        
    }
    
    
    private void GenerateReport()
    {
        try
        {
            
        }
        catch(Exception ex)
        {
            
        }
    }
}
