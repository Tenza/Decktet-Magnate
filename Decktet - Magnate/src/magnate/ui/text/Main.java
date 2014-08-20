package magnate.ui.text;

import magnate.logic.Game;

/**
 * Application entry point.
 * @author Filipe
 * @version 1.0
 */
public class Main 
{
    public static void main(String[] args) 
    {
        Text ui = new Text(new Game());
        ui.run();  
    }   
}
