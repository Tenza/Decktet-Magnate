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
 * This class represents a single card in the game.
 * @author Filipe
 * @version 1.0
 */
public class Card implements Serializable
{   
    private int rank;
    private Token suits;
    private String name;
    
    public Card(int r, String s[], String n)
    {      
        rank = r;
        suits = new Token(s);
        name = n;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Gets">
    
    public int get_rank()
    {
        return rank;
    }
    
    public String[] get_suits()
    {
        return suits.get_keys();
    }
    
    public String get_name()
    {
        return name;
    }  
    
    // </editor-fold>
       
    // <editor-fold defaultstate="collapsed" desc="Functions">
    
    protected boolean suit_exist(String suit)
    {
        return suits.contains_key(suit); 
    } 
    
    // </editor-fold>
    
}
