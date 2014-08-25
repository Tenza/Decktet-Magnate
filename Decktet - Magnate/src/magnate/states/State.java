/*
 * Decktet - Magnate
 * Copyright (C) 2012 Filipe Carvalho
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package magnate.states;

import magnate.logic.Game;

import java.io.Serializable;

/**
 * This abstract class defines all the states of the game.
 * This class also provides a message attribute that is used by all the states of the game.
 * This attribute is used to set the result message of the method call.
 * @author Filipe
 * @version 1.0
 */
public abstract class State implements Serializable
{
    private Game game;
    private String message;
    
    public State(Game g) 
    {
        game = g;
    }

    public Game get_game() 
    {
        return game;
    }
    
    public void set_message(String message)
    {
        this.message = message;
    }
    
    public String message()
    {
        return message;
    }
    
    //Initialize
    abstract public State set_players(String player1, String player2);
    
    //Roll
    abstract public State roll_dice_10();
    
    //Collect
    abstract public State collect_tokens(int selected_token);
    
    //Taxes
    abstract public State roll_dice_6();
    
    //Play
    abstract public State build(int card, int district, int token_number[]);
    abstract public State deed(int card, int district);    
    abstract public State sell(int card);
    
    //Buy
    abstract public State end_play();
    
    //Pay, Trade
    abstract public State pay_deed(int position, int token_type, int token_number, boolean graphics_mode);
    abstract public State trade_tokens(int token_give, int token_receive);
    
    //Others
    abstract public void on_enter();
    
}
