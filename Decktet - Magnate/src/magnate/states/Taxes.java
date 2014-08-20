package magnate.states;

import magnate.logic.Game;

import java.io.Serializable;

/**
 * This state calls the method to roll the 6 face dice and advances to the collect state.
 * @author Filipe
 * @version 1.0
 */
public class Taxes extends StateAdapter implements Serializable
{
    public Taxes(Game g)
    {
        super(g);
    }
    
    @Override 
    public State roll_dice_6()
    {
        get_game().f_roll_dice_6();
        return new Collect(get_game());
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
