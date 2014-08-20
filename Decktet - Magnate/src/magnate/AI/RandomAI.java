package magnate.AI;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import magnate.logic.Card;
import magnate.logic.Player;
import magnate.states.*;
import magnate.ui.graphics.Collection;
import magnate.ui.graphics.GameModel;
import magnate.ui.graphics.Parameters;

/**
 * This class implements AI completly randomized.
 * @author Filipe
 * @version 1.0
 */
public class RandomAI implements Parameters, Observer
{
    private GameModel model;
    private Player player;
    
    public RandomAI(GameModel m, Player p)
    {
        model = m;
        player = p;
        
        register_observers();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Update">
    
    /**
     * Add this class to the observers list.
     */
    private void register_observers()
    {
        model.addObserver(this);
    }
    
    /**
     * Calls all the methods of the game with random values.
     * This method disables the update of the views, and re-enables when it is done.
     * This is done because all the data is random, and this can generate multiple invalid calls to the game functions.
     * This way, the process is speedup because the model will ignore every update until everything is complete.
     */
    @Override
    public void update(Observable arg0, Object arg1) 
    {
        int result;
        
        while(model.get_game().get_current_player().equals(player))
        {
            State state = model.get_state();
            model.set_update(false);
            
            result = get_rand(1, 2);
            if(result == 1)
            {
                model.set_selected_token_index1(get_rand(0, 5));
                model.set_selected_token_index2(get_rand(0, 5));
                
                model.trade_tokens();
            }
            else if(result == 2)
            {
                model.set_selected_playcard_district(get_rand(1, 5));
                model.set_selected_token_index1(get_rand(0, 5));
                model.set_selected_token_count1(get_rand(1, 5));
                
                model.pay_deed();
            }
            
            if(state instanceof Initialize)
            {
                break;
            }
            if(state instanceof Roll)
            {
                model.roll_dice_10();
            }
            else if(state instanceof Taxes) 
            {
                model.roll_dice_6();
            }   
            else if(state instanceof Collect)
            {
                Collection collect = new Collection(model);
            }
            else if(state instanceof Play) 
            {
                while(state instanceof Play)
                {           
                    result = get_rand(1, 15);

                    //8 in 15 times calls Build
                    if(result >= 1 && result <= 8)
                    {
                        int card_rank;
                        int card_suits;
                        int token_bet1;
                        int token_bet2;
                        int token_number[];
                        
                        model.set_selected_handcard(get_rand(1, 3));
                        model.set_selected_district(get_rand(1, 5));
                        
                        Card c = model.get_game().get_current_player().get_hand().get(model.get_selected_handcard() - 1);
                        
                        card_rank = c.get_rank();
                        card_suits = c.get_suits().length;
                        token_number = new int[card_suits];
                        
                        if(card_suits == 1)
                        {       
                            token_number[0] = 3;
                        }
                        else if(card_suits == 2)
                        {                            
                            token_bet1 = get_rand(1, (card_rank - 1));
                            
                            token_number[0] = token_bet1;
                            token_number[1] = (card_rank - token_bet1);                         
                        }
                        else if(card_suits == 3)
                        {      
                            token_bet1 = get_rand(1, (card_rank - 2));
                            token_bet2 = get_rand(1, (card_rank - 1) - token_bet1);
                            
                            token_number[0] = token_bet1;
                            token_number[1] = token_bet2;
                            token_number[2] = card_rank - (token_bet1 + token_bet2);
                        }
                        
                        model.build(token_number);

                    }
                    //5 in 15 times calls Deed
                    else if(result >= 9 && result <= 13)
                    {
                        model.set_selected_handcard(get_rand(1, 3));
                        model.set_selected_district(get_rand(1, 5));

                        model.deed();
                    }
                    //2 in 15 times calls Sell
                    else if(result >= 14 && result <= 15)
                    {
                        model.set_selected_handcard(get_rand(1, 3));
                        model.sell();
                    }

                    state = model.get_state();
                }
            }
            else if(state instanceof Draw) 
            {
                model.set_update(true);
                model.end_play();
            }
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Functions">
    
    /**
     * This function gets a ranged random number.
     * @param min The minimal value of the number generated.
     * @param max The maximum value of the number generated.
     * @return the number generated.
     */
    private int get_rand(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }
    
    // </editor-fold>
    
}
