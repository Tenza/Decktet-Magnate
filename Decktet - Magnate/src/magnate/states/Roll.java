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
 * This state calls the method to roll the 10 face dice.
 * Depending on the result it can either set the taxes state or the collect.
 * @author Filipe
 * @version 1.0
 */
public class Roll extends StateAdapter implements Serializable
{
    public Roll(Game g)
    {
        super(g);
    }
    
    @Override 
    public State roll_dice_10()
    {
        if(get_game().f_roll_dice_10())
        {          
            return new Taxes(get_game());
        }
        else
        {
            return new Collect(get_game());
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
