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
