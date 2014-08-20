package magnate.logic;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * This class represents a single player in the game.
 * @author Filipe
 * @version 1.0
 */
public class Player implements Parameters, Serializable
{  
    private Game game;
    private String name;
    private Token tokens;
    private List<Card> hand;
    private List<Card> crowns;
    private List<List<PlayCard>> player_table;
    
    public Player(Game g) 
    {
        game = g;
        tokens = new Token();
        hand = new ArrayList<>();
        crowns = new ArrayList<>();
        player_table = new ArrayList<>();
        
        for(int i = 0; i < district_numer; i++)
        {
            player_table.add(new ArrayList<PlayCard>());
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Gets">
    
    public String get_name()
    {
        return name;
    }
    
    public List<Card> get_hand()
    {
        return hand;
    }
    
    public List<Card> get_crowns()
    {
        return crowns;
    }
    
    public Token get_tokens()
    {
        return tokens;
    }
    
    public List<List<PlayCard>> get_player_table()
    {
        return player_table;
    }
    
    /**
     * Gets the last card played in a district.
     * @param district the district index.
     * @return the last played card in that district.
     */
    public PlayCard get_district_card(int district)
    {
        PlayCard card;
        
        if(player_table.get(district).size() > 0)
        {
            card = player_table.get(district).get(player_table.get(district).size() - 1);
        }
        else
        {
            card = game.get_districts().get(district);
        }
        
        return card;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Sets">
    
    protected void set_name(String n)
    {
        name = n;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Adds">
    
    protected void add_hand(Card c)
    {
        hand.add(c);
    }
    
    protected void add_crown(Card c)
    {
        crowns.add(c);
    }
    
    protected void add_tokens(String t[])
    {
        tokens.add_values(t);
    }
    
    protected void add_player_table(int i, PlayCard c)
    {
        player_table.get(i).add(c);
    }
    
    // </editor-fold>
       
    // <editor-fold defaultstate="collapsed" desc="Operations">
    
    /**
     * This method applys taxes to the player.
     * @param dice the number of the dice.
     */
    protected void pay_taxes(int dice)
    {
        String t = tokens.get_name(dice);
        
        if(tokens.get_value(t) > 1)
        {
            tokens.set_value(t, 1);
        }
    }
    
    /**
     * Draws a card from the deck.
     * @param deck the deck, to remove the card.
     * @return the drawed card.
     */
    protected Card draw_card(List<Card> deck)
    {
        Card c = deck.get(deck.size() - 1);
        add_hand(c);
        deck.remove(deck.size() - 1);   
        
        return c;
    }
    
    /**
     * This method adds a token for each crown.
     * Is used in the initialization and when the player gets a 10 on the dice.
     */
    protected void add_tokens_v10()
    {
        for(int i = 0; i < get_crowns().size(); i++)
        {
            add_tokens(get_crowns().get(i).get_suits());       
        }
    }
    
    /**
     * Adds a card to the table.
     * (Remove it from the hand after.)
     * @param c the card to be added.
     * @param district the district.
     */
    protected void play_card(Card c, int district)
    {
        player_table.get(district).add(new PlayCard(c));
    }
    
    /**
     * Sets a card to constructed and removes the tokens from the player.
     * @param district the district of the card.
     * @param tokens the tokens to remove.
     */
    protected void pay_card(int district, int tokens[])
    {
        PlayCard c = get_district_card(district);
        c.set_constructed(true);
        
        for(int i = 0; i < c.get_suits().length; i++)
        {
            this.tokens.remove_value(c.get_suits()[i], tokens[i]);
        }
    }
       
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Verifys">
    
    /**
     * Verifys if the player has enough tokens to play the card.
     * @param c the card to be played.
     * @return true if can be played.
     */
    public boolean verify_card_tokens(Card c, boolean deed)
    {
        int count = 0;
        boolean result = true;
        
        for(String suit : c.get_suits())
        {
            if(!tokens.contains_key(suit))
            {
                result = false;
                break;
            }
            
            count += tokens.get_value(suit);
        }
        
        //Aces costs 3 tokens.
        if(c.get_rank() == 1)
        {
            count = count - 2;
        }
        
        if(c.get_rank() > count && !deed)
        {
            result = false;
        }
        
        return result;
    }
    
    /**
     * Verifys if the tokens selected are legitimate.
     * @param c the card to check from.
     * @param token_number the array with the values corresponding to the tokens bet.
     * @return true if passes the tests.
     */
    public boolean verify_card_tokens(Card c, int token_number[])
    {
        boolean result = false;
        int total = 0;
        
        for(int t : token_number)
        {
            if(t > 0)
            {
                total += t;
            }
            else
            {
                total = 0;
                break;
            }
        }
        
        if(c.get_rank() == 1)
        {
            total = total - 2;
        }

        if (total == c.get_rank())
        {
            for(int i = 0; i < c.get_suits().length; i++)
            {                    
                if(tokens.get_value(c.get_suits()[i]) >= token_number[i])
                {
                    result = true;
                }
                else
                {
                    result = false;
                    break;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Verifys if a given card can be played in the given district.
     * @param c the card to be played.
     * @param district the number of the district to play.
     * @return true if can be played.
     */
    public boolean verify_district(Card c, int district)
    {
        boolean result = false;
        
        if(player_table.get(district).size() > 0)
        {
            if(player_table.get(district).get(player_table.get(district).size() - 1).get_constructed())
            {           
                String[] last_card_suits = player_table.get(district).get(player_table.get(district).size() - 1).get_suits();
                for(String suit : last_card_suits)
                {
                    String[] test_card_suits = c.get_suits();
                    for(String suit2 : test_card_suits)
                    {
                        if(suit.equals(suit2))
                        {
                            result = true;
                            break;
                        }
                    }
                    if(result)
                    {
                        break;
                    }
                }
            }
        }
        else
        {
            String[] district_suits = game.get_districts().get(district).get_suits();
            for(String suit : district_suits)
            {
                String[] test_card_suits = c.get_suits();
                for(String suit2 : test_card_suits)
                {
                    if(suit.equals(suit2))
                    {
                        result = true;
                        break;
                    }
                }
                if(result)
                {
                    break;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Verifys the total of points in a given district.
     * (For the end game)
     * @param district the district number.
     * @return total of points.
     */
    protected int verify_district_points(int district)
    {
        int result = 0;
        
        for(PlayCard card : player_table.get(district))
        {   
            if(card.get_constructed())
            {          
                if(card.get_rank() == 1)
                {
                    for(PlayCard card2 : player_table.get(district))
                    {
                        if(card2.suit_exist(card.get_suits()[0]))
                        {
                            result++;
                        }
                    }
                }
                else
                {
                    result += card.get_rank();
                }
            }
        }
   
        return result;
    }
    
    /**
     * This method adds the ranks of constructed cards.
     * @return the sum of the ranks.
     */
    protected int verify_total_rank()
    {      
        int result = 0;
        
        for(int i = 0; i < game.get_districts().size(); i++)
        {
            for(PlayCard card : player_table.get(i))
            {
                if(card.get_constructed())
                {
                    result += card.get_rank();
                } 
            }
        }
        
        return result;
    }
    
     // </editor-fold>
    
}
