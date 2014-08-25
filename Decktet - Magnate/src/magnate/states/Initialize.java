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
