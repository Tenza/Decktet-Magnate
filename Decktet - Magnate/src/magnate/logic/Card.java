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
