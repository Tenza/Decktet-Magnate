package magnate.states;

import magnate.logic.Game;

import java.io.Serializable;

/**
 * This state calls the method that sets the players on the game and advances to the roll state.
 * @author Filipe
 * @version 1.0
 */
public class Initialize extends StateAdapter implements Serializable
{   
    public Initialize(Game g)
    {
        super(g);
    }
    
    @Override 
    public State set_players(String player1, String player2)
    {
        if(get_game().f_set_players(player1, player2))
        {
            return new Roll(get_game());
        }
        else
        {
            return this;   
        }
    }
    
}
