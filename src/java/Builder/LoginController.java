/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builder;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gerald
 */
public class LoginController extends SelectorComposer<Borderlayout>
{
    @Wire
    Button btnLogin;
   @Wire
   Window winKnock;
   @Listen("onClick = #btnLogin")
    public void onLoginClick() 
    {
        System.out.println("");
        try
        {
          winKnock.detach();
        }
        catch(Exception ex)
        {
            
        }
    }    
}
