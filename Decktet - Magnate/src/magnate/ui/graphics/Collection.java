package magnate.ui.graphics;

import magnate.logic.PlayCard;
import magnate.states.Collect;
import magnate.states.State;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * This class is temporaritly created to handle the Collect State.
 * This is done because the collect state has to ask questions to both players in any turn.
 * @author Filipe
 * @version 1.0
 */
public class Collection 
{
    private GameModel model;
    
    public Collection(GameModel m)  
    {
        model = m;
        
        collect_tokens();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Functions">
    
    /**
     * Selects a token from a card currently in construction.
     * If the card belongs to AI, it is set automatically.
     * If the card belongs to a player a showInputDialog pops up.
     * This can happen either if it is the player playing or the AI.
     */
    private void collect_tokens()
    {
        State state = model.get_state();

        while(state instanceof Collect)
        { 
            int game_mode = model.get_game().get_game_mode();
            
            int tokens_size = ((Collect)state).get_player1_tokens_size() + ((Collect)state).get_player2_tokens_size();
            Map<PlayCard, String> cards = ((Collect)state).get_cards();
            
            if(tokens_size != cards.size())
            { 
                PlayCard card = model.get_game().get_card_index(cards, tokens_size);
                String card_player = model.get_game().get_value_index(cards, tokens_size);
            
                if((game_mode == 0) || ((game_mode == 1) && (model.get_game().get_player1().get_name().equals(card_player))))
                { 
                    Object[] possibilities = new Object[card.get_suits().length];
                    Map<String, Integer> suits = new LinkedHashMap<>();

                    for(int j = 0; j < card.get_suits().length; j++)
                    {
                        suits.put(card.get_suits()[j], j + 1);
                        possibilities[j] = card.get_suits()[j];
                    }
                
                    String s = (String)JOptionPane.showInputDialog(
                        null,
                        "<html><b>Jogador: " + card_player + "</b></html>"
                        + "\nQual o token a receber para a carta: " + card.get_name() + "\n",
                        "Coleta de Tokens",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        possibilities,
                        possibilities[0]);
                    
                    if ((s != null) && (s.length() > 0)) 
                    {
                        model.collect_tokens(suits.get(s));
                    }
                }
                else
                {
                    int min, max, result;
                    min = 1;
                    max = card.get_suits().length; 

                    Random rand = new Random();
                    result = rand.nextInt(max - min + 1) + min;
                    
                    model.collect_tokens(result);
                }   
            }

            state = model.get_state();
        }
    }
    
    // </editor-fold>
    
}
