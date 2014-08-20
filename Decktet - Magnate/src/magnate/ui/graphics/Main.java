package magnate.ui.graphics;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Application entry point.
 * The game runs inside the run function because swing is not thread safe (Single thread EDT).
 * @author Filipe
 * @version 1.0
 */
public class Main 
{
    public static void main(String[] args) 
    {
        
        //<editor-fold defaultstate="collapsed" desc="Look and feel">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try 
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
            {
                if ("Nimbus".equals(info.getName())) 
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    UIManager.put("info", Color.WHITE);
                    break;
                }
            }
        } 
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) 
        {
            System.out.println("Erro ao carregar Nimbus!\n" + ex + "\nA iniciar com 'look and feel' default...");
        }
        
        //</editor-fold>
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() 
            {
                Setup setup  = new Setup();  
            }
         });
    }    
}
