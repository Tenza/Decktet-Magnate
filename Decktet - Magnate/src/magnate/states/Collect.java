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
import magnate.logic.PlayCard;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * This state fills the local HashMaps with the cards on the game in the on_enter method.
 * Then the method defined by the game, fills the ArrayLists with the options chosen by the players.
 * This state is complete when the size of the arrays are equal.
 * @author Filipe
 * @version 1.0
 */
public class Collect extends StateAdapter implements Serializable
{   
    private List<String> player1_tokens = new ArrayList<>();
    private List<String> player2_tokens = new ArrayList<>();
    
    private Map<PlayCard, String> cards = new LinkedHashMap<>();
    private Map<PlayCard, String> cards_constructed = new LinkedHashMap<>();
        
    public Collect(Game g)
    {
        super(g);
    }
    
    @Override 
    public State collect_tokens(int selected_token)
    {
        if(get_game().f_collect_tokens(selected_token))
        {          
            return new Play(get_game());
        }
        else
        {
            return this;
        }  
    }
    
    @Override
    public void on_enter()
    {      
        set_cards(get_game().get_dice());
        get_game().collect_tokens(0);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Gets">
    
    public Map<PlayCard, String> get_cards()
    {
        return cards;
    }
    
    public Map<PlayCard, String> get_cards_constructed()
    {
        return cards_constructed;
    }
    
    public List<String> get_player1_tokens()
    {
        return player1_tokens;
    }
    
    public int get_player1_tokens_size()
    {
        return player1_tokens.size();
    }
    
    public List<String> get_player2_tokens()
    {
        return player2_tokens;
    }
    
    public int get_player2_tokens_size()
    {
        return player2_tokens.size();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Sets">
    
    /**
     * Constructs the maps with all the cards and players.
     * @param dice the value of the dice rolled.
     */
    private void set_cards(int dice)
    {
        List<List<PlayCard>> t1 = get_game().get_player1().get_player_table();
        List<List<PlayCard>> t2 = get_game().get_player2().get_player_table();
        
        for(int i = 0; i < t1.size(); i++)
        {
            for(int j = 0; j < t1.get(i).size(); j++)
            {
                if(t1.get(i).get(j).get_rank() == dice)
                {
                    if(!t1.get(i).get(j).get_constructed())
                    {
                        cards.put(t1.get(i).get(j), get_game().get_player1().get_name());
                    }
                    else
                    {
                        cards_constructed.put(t1.get(i).get(j), get_game().get_player1().get_name());
                    }
                }
            }
        }
        
        for(int i = 0; i < t2.size(); i++)
        {
            for(int j = 0; j < t2.get(i).size(); j++)
            {
                if(t2.get(i).get(j).get_rank() == dice)
                {
                    if(!t2.get(i).get(j).get_constructed())
                    {
                        cards.put(t2.get(i).get(j), get_game().get_player2().get_name());
                    }
                    else
                    {
                        cards_constructed.put(t2.get(i).get(j), get_game().get_player2().get_name());
                    }
                }
            }
        }
    }
    
    // </editor-fold>
    
}
