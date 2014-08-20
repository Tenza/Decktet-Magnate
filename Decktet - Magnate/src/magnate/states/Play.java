package magnate.states;

import magnate.logic.Game;

import java.io.Serializable;

/**
 * This state calls the methods to play the cards on the table.
 * When one of this methos is complete the game advances to the draw state.
 * @author Filipe
 * @version 1.0
 */
public class Play extends StateAdapter implements Serializable
{
    public Play(Game g)
    {
        super(g);
    }
    
    @Override 
    public State build(int card, int district, int token_number[])
    {
        if(get_game().f_build(card, district, token_number))
        {
            return new Draw(get_game());
        }
        else
        {
            return this;   
        }
    }
    
    @Override 
    public State deed(int card, int district)
    {
        if(get_game().f_deed(card, district))
        {
            return new Draw(get_game());
        }
        else
        {
            return this;   
        }
    }
    
    @Override 
    public State sell(int card)
    {  
        if(get_game().f_sell(card))
        {          
            return new Draw(get_game());
        }
        else
        {
            return this;
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
