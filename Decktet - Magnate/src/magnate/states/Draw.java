package magnate.states;

import magnate.logic.Game;

import java.io.Serializable;

/**
 * This state calls the method that either end the game or end the turn of the current player.
 * @author Filipe
 * @version 1.0
 */
public class Draw extends StateAdapter implements Serializable
{   
    public Draw(Game g)
    {
        super(g);
    }
    
    @Override 
    public State end_play()
    {
        if(get_game().f_end_play())
        {
            return new Roll(get_game());
        }
        else
        {
            return new Initialize(get_game());
        }
    }
    
    @Override 
    public State pay_deed(int position, int token_type, int token_number, boolean graphics_mode)
    {
        get_game().f_pay_deed(position, token_type, token_number, graphics_mode);
        return this;
    }
    
    @Override 
    public State trade_tokens(int token_give, int token_receive)
    {
        get_game().f_trade_tokens(token_give, token_receive);
        return this;
    }
    
}
