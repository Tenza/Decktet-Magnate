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

package magnate.ui.graphics;

import magnate.logic.*;
import magnate.states.*;

import java.util.Observable;

/**
 * This class represents the Model in the MVC pattern.
 * The interaction with the Game itself is only made through this class.
 * @author Filipe
 * @version 1.0
 */
public class GameModel extends Observable
{
    private Game game;
    private State state;
    
    private int selected_handcard;
    private int selected_district;
    private int selected_playcard;
    private int selected_playcard_district;
    private int selected_token_count1;
    private int selected_token_count2;
    private int selected_token_count3;
    private int selected_token_index1;
    private int selected_token_index2;  
    private int selected_token_index3;  
    private boolean update;
    
    public GameModel(Game g)
    {
        game = g;
        selected_handcard = 0;
        selected_district = 0;
        selected_playcard = 0;
        selected_playcard_district = 0;
        selected_token_count1 = 0;
        selected_token_count2 = 0;
        selected_token_count3 = 0;
        selected_token_index1 = -1;
        selected_token_index2 = -1;
        selected_token_index3 = -1;
        update = true;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Gets">
    
    public Game get_game()
    {
        return game;
    }
    
    public State get_state()
    {
        return game.get_state();
    }
    
    public State get_state_model()
    {
        return state;
    }
    
    public String get_message()
    {
        return state.message();
    }
    
    public boolean get_update()
    {
        return update;
    }
    
    public int get_selected_handcard()
    {
        return selected_handcard;
    }
    
    public int get_selected_district()
    {
        return selected_district;
    }
    
    public int get_selected_playcard()
    {
        return selected_playcard;
    }
    
    public int get_selected_playcard_district()
    {
        return selected_playcard_district;
    }
    
    public int get_selected_token_index1()
    {
        return selected_token_index1;
    }
    
    public int get_selected_token_count1()
    {
        return selected_token_count1;
    }
    
    public int get_selected_token_index2()
    {
        return selected_token_index2;
    }
    
    public int get_selected_token_count2()
    {
        return selected_token_count2;
    }
    
    public int get_selected_token_index3()
    {
        return selected_token_index3;
    }
    
    public int get_selected_token_count3()
    {
        return selected_token_count3;
    }
    
    // </editor-fold>
      
    // <editor-fold defaultstate="collapsed" desc="Sets">
    
    public void set_game(Game g)
    {
        game = g;
    }
    
    public void set_state(State s)
    {
        state = s;
    }

    public void set_update(boolean u)
    {
        update = u;
    }
    
    public void set_selected_handcard(int i)
    {
        selected_handcard = i;
    }
    
    public void set_selected_district(int i)
    {
        selected_district = i;
    }
   
    public void set_selected_playcard(int i)
    {
        selected_playcard = i;
    }
    
    public void set_selected_playcard_district(int i)
    {
        selected_playcard_district = i;
    }
    
    public void set_selected_token_index1(int i)
    {
        selected_token_index1 = i;
    }
    
    public void set_selected_token_count1(int c)
    {
        selected_token_count1 = c;
    }
    
    public void set_selected_token_index2(int i)
    {
        selected_token_index2 = i;
    }   
    
    public void set_selected_token_count2(int c)
    {
        selected_token_count2 = c;
    }
    
    public void set_selected_token_index3(int i)
    {
        selected_token_index3 = i;
    }

    public void set_selected_token_count3(int c)
    {
        selected_token_count3 = c;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void set_players(String player1, String Player2)
    {
        state = get_state();
        game.set_players(player1, Player2);  
        
        if(update)
        {
            setChanged();
            notifyObservers();
        }
    }
    
    public void roll_dice_10()
    {
        state = get_state();
        game.roll_dice_10();
        
        if(update)
        {
            setChanged();
            notifyObservers();
        }
    }
    
    public void roll_dice_6()
    {
        state = get_state();
        game.roll_dice_6();
        
        if(update)
        {
            setChanged();
            notifyObservers();
        }
    }
    
    public void collect_tokens(int selected)
    {
        state = get_state();
        game.collect_tokens(selected);
        
        if(update)
        {
            setChanged();
            notifyObservers();
        }
    }
    
    public void build(int token_number[])
    {
        state = get_state();
        game.build(selected_handcard, selected_district, token_number);
        
        if(update)
        {
            setChanged();
            notifyObservers();
        }
    }
    
    public void deed()
    {
        state = get_state();
        game.deed(selected_handcard, selected_district);
        
        if(update)
        {
            setChanged();
            notifyObservers();
        }
    }
    
    public void sell()
    {
        state = get_state();
        game.sell(selected_handcard);
        
        if(update)
        {
            setChanged();
            notifyObservers();
        }
    }
    
    public void trade_tokens()
    {
        state = get_state();
        game.trade_tokens(selected_token_index1 + 1, selected_token_index2 + 1);
        
        if(update)
        {
            setChanged();
            notifyObservers();
        }
    }
    
    public void pay_deed()
    {
        state = get_state();
        game.pay_deed(selected_playcard_district, selected_token_index1 + 1, selected_token_count1, true);
        
        if(update)
        {
            setChanged();
            notifyObservers();
        }
    }

    public void end_play()
    {
        state = get_state();
        game.end_play();
        
        if(update)
        {
            setChanged();
            notifyObservers();
        }
    }
    
    public void force_update()
    {
        setChanged();
        notifyObservers();
    }
    
    // </editor-fold>
    
}
