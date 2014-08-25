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

package magnate.logic;

import java.io.Serializable;

/**
 * This class extends the class card.
 * Adds functionality to play the card.
 * @author Filipe
 * @version 1.0
 */
public class PlayCard extends Card implements Serializable
{
    private Token tokens; 
    private boolean constructed;
   
    public PlayCard(int r, String s[], String n)
    {
        super(r, s, n);
        
        tokens = new Token();
        constructed = false;     
    }
    
    public PlayCard(Card c)
    {
        super(c.get_rank(), c.get_suits(), c.get_name());
        
        tokens = new Token();
        constructed = false;     
    }
    
    // <editor-fold defaultstate="collapsed" desc="Gets">
    
    public boolean get_constructed()
    {
        return constructed;
    } 
        
    public Token get_tokens()
    {
        return tokens;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Sets">
    
    protected void set_constructed(boolean c)
    {
        constructed = c;
    }
    
    protected void add_tokens(String t[])
    {
        tokens.add_values(t);
    }
    
    // </editor-fold>
    
}
