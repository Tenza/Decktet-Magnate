package magnate.states;

import magnate.logic.Game;

import java.io.Serializable;

/**
 * This class provides the definition of all the state methods of the game.
 * This is used so that we dont have to define all the methods in each state class.
 * @author Filipe
 * @version 1.0
 */
public class StateAdapter extends State implements Serializable
{    
    public StateAdapter(Game g)
    {
        super(g);
    }
    
    @Override public State set_players(String player1, String player2) {return this;}
    @Override public State roll_dice_10() {return this;}
    @Override public State collect_tokens(int selected_token) {return this;}
    @Override public State roll_dice_6() {return this;}   
    @Override public State build(int card, int district, int token_number[]) {return this;}   
    @Override public State deed(int card, int district) {return this;}
    @Override public State sell(int card) {return this;}   
    @Override public State end_play() {return this;}
    @Override public State pay_deed(int position, int token_type, int token_number, boolean graphics_mode) {return this;}
    @Override public State trade_tokens(int token_give, int token_receive) {return this;}
    @Override public void on_enter() {}  
    
}
