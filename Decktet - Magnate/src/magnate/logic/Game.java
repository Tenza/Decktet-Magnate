package magnate.logic;

import magnate.states.*;

import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Collections;

/**
 * This class represents the game where each player plays to and from.
 * @author Filipe
 * @version 1.0
 */
public class Game implements Parameters, Serializable
{
    private int dice[];
    private int game_mode;
    private State state;
    private Player player1;
    private Player player2;
    private Player current_player;
    private List<Card> deck;
    private List<Card> discarted;
    private List<PlayCard> districts;
    private boolean secound_round;
    
    public Game()
    {        
        dice = new int[3];
        game_mode = 0;
        state = new Initialize(this);
        player1 = new Player(this);
        player2 = new Player(this);
        deck = new ArrayList<>();
        discarted = new ArrayList<>();
        districts = new ArrayList<>();
        secound_round = false;
        
        set_cards();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Gets">
    
    public int get_dice1() 
    {
        return dice[0];
    }
    
    public int get_dice2() 
    {
        return dice[1];
    }
    
    public int get_dice_taxes() 
    {
        return dice[2];
    }
    
    public int get_dice() 
    {     
        if(dice[0] > dice[1])
        {
            return dice[0];
        }
        else
        {
            return dice[1];
        }  
    }
    
    public int get_game_mode()
    {
        return game_mode;
    }
    
    public State get_state() 
    {
        return state;
    }
    
    public Player get_player1()
    {
        return player1;
    }
    
    public Player get_player2()
    {
        return player2;
    }
    
    public Player get_current_player()
    {
        return current_player;
    }
    
    public Player get_other_player()
    {
        if(current_player.equals(player2))
        {
            return player1;
        }
        else
        {
            return player2;
        }
    }
    
    public List<Card> get_deck()
    {
        return deck;
    }
    
    public List<Card> get_discarted()
    {
        return discarted;
    }
    
    public List<PlayCard> get_districts()
    {
        return districts;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Sets">
    
    /**
     * This method sets all the cards in the game.
     * Adds and shuffles the Deck from wich the players will draw the cards in each hand.
     * Adds and shuffles the Crowns that will be delt to de players in the set_players function.
     * Adds the Pawns and Excuse to the first row of the Player1 and Player2 table.
     * (The number in the Pawns and Courts are switched because of the rules in the extended version)
     */
    private void set_cards()
    {
        long seed = System.nanoTime();
        
        //Set Deck
        deck.add(new Card(1, new String[]{"Moons"}, "Ace of Moons"));
        deck.add(new Card(1, new String[]{"Suns"}, "Ace of Suns"));
        deck.add(new Card(1, new String[]{"Leaves"}, "Ace of Leaves"));
        deck.add(new Card(1, new String[]{"Wyrms"}, "Ace of Wyrms"));
        deck.add(new Card(1, new String[]{"Waves"}, "Ace of Waves"));
        deck.add(new Card(1, new String[]{"Knots"}, "Ace of Knots"));
        
        deck.add(new Card(2, new String[]{"Moons", "Knots"}, "The Author"));
        deck.add(new Card(2, new String[]{"Waves", "Leaves"}, "The Origin"));
        deck.add(new Card(2, new String[]{"Suns", "Wyrms"}, "The Desert"));
        
        deck.add(new Card(3, new String[]{"Leaves", "Wyrms"}, "The Savage"));
        deck.add(new Card(3, new String[]{"Suns", "Knots"}, "The Painter"));
        deck.add(new Card(3, new String[]{"Moons", "Waves"}, "The Journey"));
        
        deck.add(new Card(4, new String[]{"Wyrms", "Knots"}, "The Battle"));
        deck.add(new Card(4, new String[]{"Moons", "Suns"}, "The Mountain"));
        deck.add(new Card(4, new String[]{"Waves", "Leaves"}, "The Sailor"));
          
        deck.add(new Card(5, new String[]{"Wyrms", "Knots"}, "The Soldier"));
        deck.add(new Card(5, new String[]{"Suns", "Waves"}, "The Discovery"));
        deck.add(new Card(5, new String[]{"Moons", "Leaves"}, "The Forest"));
        
        deck.add(new Card(6, new String[]{"Suns", "Wyrms"}, "The Penitent"));
        deck.add(new Card(6, new String[]{"Moons", "Waves"}, "The Lunatic"));
        deck.add(new Card(6, new String[]{"Leaves", "Knots"}, "The Market"));     
        
        deck.add(new Card(7, new String[]{"Moons", "Leaves"}, "The Chance Meeting"));
        deck.add(new Card(7, new String[]{"Suns", "Knots"}, "The Castle"));      
        deck.add(new Card(7, new String[]{"Waves", "Wyrms"}, "The Cave"));
        
        deck.add(new Card(8, new String[]{"Wyrms", "Knots"}, "The Betrayal"));
        deck.add(new Card(8, new String[]{"Waves", "Leaves"}, "The Mill"));
        deck.add(new Card(8, new String[]{"Moons", "Suns"}, "The Diplomat"));
        
        deck.add(new Card(9, new String[]{"Moons", "Suns"}, "The Pact"));
        deck.add(new Card(9, new String[]{"Leaves", "Knots"}, "The Merchant"));
        deck.add(new Card(9, new String[]{"Waves", "Wyrms"}, "The Darkness"));
        
        //Set Courts
        deck.add(new Card(10, new String[]{"Moons", "Waves", "Knots"}, "The Consul"));
        deck.add(new Card(10, new String[]{"Suns", "Leaves", "Knots"}, "The Window"));
        deck.add(new Card(10, new String[]{"Moons", "Leaves", "Wyrms"}, "The Rite"));        
        deck.add(new Card(10, new String[]{"Suns", "Waves", "Wyrms"}, "The Island"));
               
        //Set Pawns
        districts.add(new PlayCard(11, new String[]{"Moons", "Wyrms", "Knots"}, "The Watchman"));
        districts.add(new PlayCard(11, new String[]{"Moons", "Suns", "Leaves"}, "The Harvest"));
        districts.add(new PlayCard(11, new String[]{"Moons", "Suns", "Waves", "Leaves", "Wyrms", "Knots"}, "The Excuse"));
        districts.add(new PlayCard(11, new String[]{"Waves", "Leaves", "Wyrms"}, "The Borderland"));
        districts.add(new PlayCard(11, new String[]{"Suns", "Waves", "Knots"}, "The Light Keeper"));
        
        //Set Crowns
        List<Card> crowns = new ArrayList<>();
        crowns.add(new Card(12, new String[]{"Moons"}, "The Huntress"));
        crowns.add(new Card(12, new String[]{"Suns"}, "The Bard"));
        crowns.add(new Card(12, new String[]{"Knots"}, "The Windfall"));
        crowns.add(new Card(12, new String[]{"Leaves"}, "The End")); 
        crowns.add(new Card(12, new String[]{"Wyrms"}, "The Calamity"));
        crowns.add(new Card(12, new String[]{"Waves"}, "The Sea"));
              
        for(int i = 0; i < 3; i++)
        {            
            player1.add_crown(crowns.get(i*2));                    
            player2.add_crown(crowns.get(i*2+1));
        }
        
        Collections.shuffle(deck, new Random(seed));
        Collections.shuffle(crowns, new Random(seed));
        
//        player1.play_card(deck.get(0), 0);
//        player1.play_card(deck.get(1), 1);
//        player1.play_card(deck.get(2), 2);
//        player1.play_card(deck.get(3), 3);
//        player1.play_card(deck.get(4), 4);        
//        player1.play_card(deck.get(5), 0);
//        player1.play_card(deck.get(6), 1);
//        player1.play_card(deck.get(7), 2);
//        player1.play_card(deck.get(8), 3);
//        player1.play_card(deck.get(9), 4);
//        
//        player2.play_card(deck.get(10), 0);
//        player2.play_card(deck.get(11), 1);
//        player2.play_card(deck.get(12), 2);
//        player2.play_card(deck.get(13), 3);
//        player2.play_card(deck.get(14), 4);        
//        player2.play_card(deck.get(15), 0);
//        player2.play_card(deck.get(16), 1);
//        player2.play_card(deck.get(17), 2);
//        player2.play_card(deck.get(18), 3);
//        player2.play_card(deck.get(19), 4);
//      
//        player1.get_player_table().get(0).get(0).set_constructed(true);
//        player1.get_player_table().get(1).get(0).set_constructed(true);
//        player1.get_player_table().get(2).get(0).set_constructed(true);
//        player1.get_player_table().get(3).get(0).set_constructed(true);
//        player1.get_player_table().get(4).get(0).set_constructed(true);
//        
//        player2.get_player_table().get(0).get(0).set_constructed(true);
//        player2.get_player_table().get(1).get(0).set_constructed(true);
//        player2.get_player_table().get(2).get(0).set_constructed(true);
//        player2.get_player_table().get(3).get(0).set_constructed(true);
//        player2.get_player_table().get(4).get(0).set_constructed(true);
//       
//        player1.add_tokens(new String[]{"Moons", "Suns", "Waves", "Leaves", "Wyrms", "Knots"});
//        player1.add_tokens(new String[]{"Moons", "Suns", "Waves", "Leaves", "Wyrms", "Knots"});
//        player1.add_tokens(new String[]{"Moons", "Suns", "Waves", "Leaves", "Wyrms", "Knots"});
//        player1.add_tokens(new String[]{"Moons", "Suns", "Waves", "Leaves", "Wyrms", "Knots"});
//        player1.add_tokens(new String[]{"Moons", "Suns", "Waves", "Leaves", "Wyrms", "Knots"});
//        
//        player2.add_tokens(new String[]{"Moons", "Suns", "Waves", "Leaves", "Wyrms", "Knots"});
//        player2.add_tokens(new String[]{"Moons", "Suns", "Waves", "Leaves", "Wyrms", "Knots"});
//        player2.add_tokens(new String[]{"Moons", "Suns", "Waves", "Leaves", "Wyrms", "Knots"});
//        player2.add_tokens(new String[]{"Moons", "Suns", "Waves", "Leaves", "Wyrms", "Knots"});
//        player2.add_tokens(new String[]{"Moons", "Suns", "Waves", "Leaves", "Wyrms", "Knots"});
        
    }    
    
    protected void set_state(State state)
    {
        State temp = this.state;
        this.state = state;
        
        if(temp != state)
        {
            this.state.on_enter();
        }     
    }
    
    
    public void set_game_mode(int gm)
    {
        game_mode = gm;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Initialize">
    
    /**
     * Sets all the members of player class.
     * Initializes, sets hand, crowns, tokens and chooses player to start.
     * @param p1 name of the fist player.
     * @param p2 name of the second player.
     */
    public void set_players(String p1, String p2)
    {
        set_state(state.set_players(p1, p2));
    }
    
    public boolean f_set_players(String p1, String p2)
    {
        boolean result;
        if(!p1.equals("") && !p2.equals("") && !(p1.equals(p2)))
        {
            player1.set_name(p1);
            player2.set_name(p2);

            for(int i = 0; i < 3; i++)
            {
                player1.draw_card(deck);
                player2.draw_card(deck);
            }

            player1.add_tokens_v10();
            player2.add_tokens_v10();

            if(randomize_player && game_mode == 0)
            {
                Random rand = new Random();
                int min = 0, max = 1;
                int random_number = rand.nextInt(max - min + 1) + min;

                if(random_number == 0)
                {
                    current_player = player1;
                }
                else
                {
                    current_player = player2;
                }
                
                state.set_message("O jogador '" + current_player.get_name() + "' foi escolhido para iniciar o jogo.");
            }
            else
            {
                current_player = player1;
                state.set_message("Boa sorte '" + current_player.get_name() + "'!");
            }

            result = true;
        }
        else
        {
            result = false;
            state.set_message("Os jogadores não podem ter nomes iguais.");
        }
        
        return result;
    }  
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Dice"> 
    
    /**
     * Rolls the dice and sellects the highest value.
     * Determines if the nest State is Taxes or Collect.
     */
    public void roll_dice_10()
    {
        set_state(state.roll_dice_10());
    }
    
    public boolean f_roll_dice_10()
    {
        Random rand = new Random();
        int min = 1, max = 10;
        
        dice[0] = rand.nextInt(max - min + 1) + min;
        dice[1] = rand.nextInt(max - min + 1) + min;  
        
        state.set_message("Obteu os valores: " + get_dice1() + " e " + get_dice2());
        
        if(dice[0] == 1 || dice[1] == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Taxes">
    
    /**
     * Rolls the dice and applys taxes to both players.
     */
    public void roll_dice_6()
    {
        set_state(state.roll_dice_6());
    }
    
    public void f_roll_dice_6()
    {
        Random rand = new Random();
        int min = 1, max = 6;
        
        dice[2] = rand.nextInt(max - min + 1) + min;
       
        player1.pay_taxes(dice[2]);
        player2.pay_taxes(dice[2]);
        
        state.set_message("Obteu o valore: " + get_dice_taxes() + "\nForam aplicadas taxas ao token: " + new Token().get_name(get_dice_taxes()));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Collect">

    /**
     * This method adds tokens acording to the value of the dice and state of current card.
     * 'player1_tokens' and 'player2_tokens' holds the tokens to be added to each player.
     * 'cards' holds a Map with the information of the cards IN CONSTRUCTION and the player
     * 'cards_constructed' holds a Map with the information of the cards CONSTRUCTED and the player
     * 10 - Same as the begginig, 1 token for each crown, the operation is completed immediately
     * (Works with Courts, they will NOT be rewarded because they have a value of 10)
     * 2-9 - Checks for constructed cards, and the arrays will be filled in each cycle. 
     * If there is no cards IN CONSTRUCTION, the operation is completed immediately
     * 1 - Checks for constructed and in construction cards, the operation is completed immediately
     * @param selected_token the selected token, corresponding to the current card.
     */
    public void collect_tokens(int selected_token)
    {
        set_state(state.collect_tokens(selected_token));
    }
    
    public boolean f_collect_tokens(int selected_token)
    {          
        boolean result = false;
        
        List<String> player1_tokens = ((Collect)this.state).get_player1_tokens();
        List<String> player2_tokens = ((Collect)this.state).get_player2_tokens();
        
        Map<PlayCard, String> cards = ((Collect)this.state).get_cards();
        Map<PlayCard, String> cards_constructed = ((Collect)this.state).get_cards_constructed();
             
        if(get_dice() == 10)
        {
            player1.add_tokens_v10();
            player2.add_tokens_v10();
            result = true;
        }
        else if(get_dice() >= 2 && get_dice() <= 9)
        {
            int tokens_size;
            PlayCard card;
            String card_player;            
            
            //Verifys and adds receved value.
            tokens_size = player1_tokens.size() + player2_tokens.size();          
            if((!cards.isEmpty()) && (selected_token > 0))
            {
                card = get_card_index(cards, tokens_size);
                card_player = get_value_index(cards, tokens_size);

                if(selected_token <= card.get_suits().length)
                {
                    if(card_player.equals(player1.get_name()))
                    {
                        player1_tokens.add(card.get_suits()[selected_token - 1]);
                    }
                    else
                    {
                        player2_tokens.add(card.get_suits()[selected_token - 1]);
                    }
                    state.set_message("Token selecionado.");
                }
            }
            
            tokens_size = player1_tokens.size() + player2_tokens.size();
            if(tokens_size == cards.size())
            {
                if(!cards_constructed.isEmpty())
                {
                    for(Map.Entry<PlayCard, String> entry : cards_constructed.entrySet()) 
                    {
                        for(int j = 0; j < entry.getKey().get_suits().length; j++)
                        {
                            if(entry.getValue().equals(player1.get_name()))
                            {
                                player1_tokens.add(entry.getKey().get_suits()[j]);
                            }
                            else
                            {
                                player2_tokens.add(entry.getKey().get_suits()[j]);
                            }
                        }
                    }
                }
                
                player1.add_tokens(player1_tokens.toArray(new String[player1_tokens.size()]));
                player2.add_tokens(player2_tokens.toArray(new String[player2_tokens.size()]));
                result = true;
                state.set_message("Tokens distribuidos.");
            }   
        }
        else if(get_dice() == 1)
        {     
            //Add tokens of ACES in construction.
            for(Map.Entry<PlayCard, String> entry : cards.entrySet()) 
            {            
                if(entry.getValue().equals(player1.get_name()))
                {
                    player1_tokens.add(entry.getKey().get_suits()[0]);
                }
                else
                {
                    player2_tokens.add(entry.getKey().get_suits()[0]);
                }
            }
            
            //Add tokens of ACES already constructed.
            for(Map.Entry<PlayCard, String> entry : cards_constructed.entrySet()) 
            {
                if(entry.getValue().equals(player1.get_name()))
                {
                    player1_tokens.add(entry.getKey().get_suits()[0]);
                }
                else
                {
                    player2_tokens.add(entry.getKey().get_suits()[0]);
                }
            } 
            
            player1.add_tokens(player1_tokens.toArray(new String[player1_tokens.size()]));
            player2.add_tokens(player2_tokens.toArray(new String[player2_tokens.size()]));
            result = true;
        }

        return result;
    }
    
    /**
     * Gets the card from the given index.
     * @param cards list of cards to be checked.
     * @param index the index to match.
     * @return the name of the player.
     */
    public PlayCard get_card_index(Map<PlayCard, String> cards, int index)
    {
        int i = 0;
        PlayCard result = null;
        for(Map.Entry<PlayCard, String> entry : cards.entrySet()) 
        {  
            if(i == index)
            {
                result = entry.getKey();
                break;
            }
            i++;
        }
        
        return result;
    }
    
    /**
     * Gets the name of the player from the value in the Map, from the given index.
     * @param cards list of cards to be checked.
     * @param index the index to match.
     * @return the name of the player.
     */
    public String get_value_index(Map<PlayCard, String> cards, int index)
    {
        int i = 0;
        String result = null;
        for(Map.Entry<PlayCard, String> entry : cards.entrySet()) 
        {  
            if(i == index)
            {
                result = entry.getValue();
                break;
            }
            i++;
        }
        
        return result;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Build">
    
    /**
     * Sets a builded card. Multiple tests are preformed, and then the card in set.
     * @param card the number selected from the card at hand.
     * @param district the number of the selected district.
     * @param token_number an array of tokens, corresponding to each suit of the card.
     */
    public void build(int card, int district, int token_number[])
    {
        set_state(state.build(card, district, token_number));
    }
    
    public boolean f_build(int card, int district, int token_number[])
    {
        Card c = null;
        boolean tests = false;
        boolean result = false;
        
        if((card > 0 && card <= current_player.get_hand().size()) && (district  > 0 && district <= districts.size()))
        {           
            c = current_player.get_hand().get(card - 1);
            if(current_player.verify_card_tokens(c, false))
            {
                if(current_player.verify_district(c, district - 1))
                {
                    if(current_player.verify_card_tokens(c, token_number))
                    {
                        tests = true;
                    }
                    else
                    {
                        state.set_message("O tokens apostados não são apropriados.");
                    }
                }
                else
                {
                    state.set_message("Não pode jogar essa carta nesse distrito.");
                }
            }
            else
            {
                state.set_message("Não tem tokens suficientes para jogar essa carta.");
            }
        }
        else
        {
            state.set_message("Selecione uma carta para construir e um distrito.");
        }
        
        if(tests && c != null)
        {
            district--;
            current_player.play_card(c, district);
            current_player.get_hand().remove(c);
            current_player.pay_card(district, token_number);
            state.set_message("A carta '" + c.get_name() + "' foi construida." );
            result = true;
        }
        
        return result;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Deed">
    
    /**
     * Sets a card in deed. Multiple tests are preformed, and then the card in set.
     * The player will be taxed with the same amount of suits of the card.
     * @param card the number selected from the card at hand.
     * @param district the number of the selected district.
     */
    public void deed(int card, int district)
    {
        set_state(state.deed(card, district));
    }
    
    public boolean f_deed(int card, int district)
    {
        Card c = null;
        boolean tests = false;
        boolean result = false;
        
        if((card > 0 && card <= current_player.get_hand().size()) && (district  > 0 && district <= districts.size()))
        {
            c = current_player.get_hand().get(card - 1);

            if(current_player.verify_card_tokens(c, true))
            {
                if(current_player.verify_district(c, district - 1))
                {
                    tests = true;
                }
                else
                {
                    state.set_message("Não pode jogar essa carta nesse distrito.");
                }
            }
            else
            {
                state.set_message("Não tem tokens suficientes para jogar essa carta.");
            }
        }
        else
        {
            state.set_message("Selecione uma carta para colocar em construção e um distrito.");
        }
        
        if(tests && c != null)
        {
            current_player.play_card(c, district - 1);
            current_player.get_hand().remove(c);
            
            for(int i = 0; i < c.get_suits().length; i++)
            {
                current_player.get_tokens().remove_value(c.get_suits()[i], 1);
            }
            
            state.set_message("A carta '" + c.get_name() + "' foi posta em construção." );
            result = true;
        }
        
        return result;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Sell">
    
    /**
     * Sells a card. The selled card is put in the discarted pile. And the corresponding tokens are awarded.
     * @param card the number selected from the card at hand.
     */
    public void sell(int card)
    {
        set_state(state.sell(card));
    }
    
    public boolean f_sell(int card)
    {
        boolean result = false;
        List<Card> hand = current_player.get_hand();
        
        if(card > 0 && card <= hand.size())
        {
            card--;
            Card c = hand.get(card);
            
            current_player.add_tokens(c.get_suits());
            
            //Ace gives 2 tokens
            if(c.get_rank() == 1)
            {
                current_player.add_tokens(c.get_suits());
            }

            discarted.add(c);
            hand.remove(card);
            
            state.set_message("A carta '" + c.get_name() + "' foi vendida.");  
            result = true;
        }
        else
        {
            state.set_message("Selecione uma carta para vender.");
        }

        return result;
    }
    
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc="Draw">
    
    /**
     * This method draws a card, switches players, switches decks and verifys for the end game.
     * Compares the points in the districts, the total number of ranks and tokens.
     */
    public void end_play()
    {
        set_state(state.end_play());
    }
    
    public boolean f_end_play()
    {
        boolean result = true;
        
        //Draw Card.
        if(!deck.isEmpty())
        {
            Card c = current_player.draw_card(deck);
            state.set_message("Retirou a carta '" + c.get_name() + "'");
        }
        else
        {
            state.set_message("Ultima jogada!");
        }
        
        //Switch Players.
        if(current_player.equals(player2))
        {
            current_player = player1;
        }
        else
        {
            current_player = player2;
        }
        
        //Switch Deck.
        if(deck.isEmpty() && !secound_round)
        {
            secound_round = true;
            
            deck.addAll(discarted);
            discarted.clear();
            
            state.set_message(state.message() + "\nInicio da segunda ronda!");
        }
        //Verifys Winner.
        else if(deck.isEmpty() && secound_round && current_player.get_hand().size() == 2)
        {
            String winner;
            int p1_rank;
            int p2_rank;
            int p1_points = 0;
            int p2_points = 0;
            int p1_tokens = 0;
            int p2_tokens = 0;
            
            //Gets data.
            for(int i = 0; i < districts.size(); i++)
            {
                int t1 = player1.verify_district_points(i);
                int t2 = player2.verify_district_points(i);
                
                if(t1 > t2)
                {
                    p1_points++;
                }
                else if(t1 < t2)
                {   
                    p2_points++;
                }                
            }
            
            p1_rank = player1.verify_total_rank();
            p2_rank = player2.verify_total_rank();
            
            for(int tokens : player1.get_tokens().get_values_all())
            {
                p1_tokens += tokens;
            }
            
            for(int tokens : player2.get_tokens().get_values_all())
            {
                p2_tokens += tokens;
            }
            
            //Verifys win by district.
            if(p1_points > p2_points)
            {
                winner = player1.get_name();
            }
            else if(p1_points < p2_points)
            {   
                winner = player2.get_name();   
            }
            else
            {
                //Verifys win by rank.
                if(p1_rank > p2_rank)
                {
                    winner = player1.get_name();
                }
                else if(p1_rank < p2_rank)
                {   
                    winner = player2.get_name();   
                }
                else
                {
                    //Verifys win by tokens.
                    if(p1_tokens > p2_tokens)
                    {
                        winner = player1.get_name();
                    }
                    else if(p1_tokens < p2_tokens)
                    {   
                        winner = player2.get_name();   
                    }
                    else
                    {
                        winner = null;
                    }
                }
            }
            
            if(winner != null)
            {           
                state.set_message(winner + " é o vencedor do jogo!\n"
                    + "Pontos:\t" + p1_points + " (" + player1.get_name() + ")      " + p2_points + " (" + player2.get_name() + ")\n"
                    + "Rank:\t" + p1_rank + " (" + player1.get_name() + ")      " + p2_rank + " (" + player2.get_name() + ")\n"
                    + "Tokens:\t" + p1_tokens + " (" + player1.get_name() + ")      " + p2_tokens + " (" + player2.get_name() + ")\n");
            }
            else
            {
                state.set_message("Empate!\n"
                    + "Pontos:\t" + p1_points + " (" + player1.get_name() + ")      " + p2_points + " (" + player2.get_name() + ")\n"
                    + "Rank:\t" + p1_rank + " (" + player1.get_name() + ")      " + p2_rank + " (" + player2.get_name() + ")\n"
                    + "Tokens:\t" + p1_tokens + " (" + player1.get_name() + ")      " + p2_tokens + " (" + player2.get_name() + ")\n");
            }
            result = false;
        } 
        
        return result;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Trade Tokens">
    
    /**
     * Trades 3 tokens of a kind for 1 of another.
     * @param token_give the number of the token to remove from.
     * @param token_receive the number of the token to add to.
     */
    public void trade_tokens(int token_give, int token_receive)
    {
        set_state(state.trade_tokens(token_give, token_receive));
    }
    
    public void f_trade_tokens(int token_give, int token_receive)
    {
        Token tokens = current_player.get_tokens();
        
        if((token_give > 0 && token_give < 7) && (token_receive > 0 && token_receive < 7))
        {     
            if(tokens.get_value(tokens.get_name(token_give)) >= token_trade)
            {
                tokens.add_value(tokens.get_name(token_receive));
                tokens.remove_value(tokens.get_name(token_give), token_trade);
                state.set_message("Tokens trocados com sucesso.");
            }
            else
            {
                state.set_message("Não tem tokens suficientes para completar a operação.");
            }
        }
        else
        {
            state.set_message("Indique os tokens a trocar.");
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Pay Deeds">
    
    /**
     * This method pays for a car in construction. 
     * Multiple verifications are performed.
     * If completed the card is set to constructed.
     * @param position the card to put in construction .
     * (TextMode - The number of the card in construction)
     * (GraphicsMode - The number of the district)
     * @param token_type the number of the token to be added.
     * @param token_number the number to add.
     * @param graphics_mode set graphics mode.
     * (The card number is not calculated)
     * (The token_type is not calculated)
     */
    public void pay_deed(int position, int token_type, int token_number, boolean graphics_mode)
    {
        set_state(state.pay_deed(position, token_type, token_number, graphics_mode));
    }
    
    public void f_pay_deed(int position, int token_type, int token_number, boolean graphics_mode)
    {
        PlayCard card = null;
        boolean tests = false;
             
        int rank = 0;
        String suit = "";
        
        if(position > 0)
        {          
            if(graphics_mode)
            {
                card = current_player.get_district_card(position - 1);
            }
            else
            {
                card = get_card_inconstruction(position - 1);
            }
        }
              
        if(card != null)
        {
            if((token_type > 0 && token_type < 7) && token_number > 0)
            {
                if(graphics_mode)
                {
                    suit = new Token().get_name(token_type);
                }
                else
                {
                    suit = card.get_suits()[token_type - 1];
                }

                if(card.get_rank() > 1)
                {
                    rank = card.get_rank();
                }
                else
                {
                    rank = card.get_rank() + 2;
                }

                if(current_player.get_tokens().get_value(suit) >= token_number)
                {
                    int total = 0;
                    for(int i : card.get_tokens().get_values())
                    {
                        total += i;
                    }

                    if(((token_number + total) <= rank) && (card.suit_exist(suit)))
                    {
                        //Verifys if the tokens already on that suit with the new ones, does not exceeds the value of that card LESS the number of suits (1 token per suit at least!)
                        if((card.get_tokens().get_value(suit) +  token_number) <=  (rank - (card.get_suits().length - 1)))
                        {
                            tests = true;
                        }
                        else
                        {
                            state.set_message("Não pode colocar tantos tokens. Cada naipe tem que ter pelo menos 1 token.");
                        }                   
                    }
                    else
                    {
                        state.set_message("O tokens indicados não são apropriados.");
                    }
                }
                else
                {
                    state.set_message("Não tem tokens suficientes.");
                }
            }
            else
            {
                state.set_message("Indique os tokens a adicionar.");
            }
        }
        else
        {
            state.set_message("Selecione uma carta em construcão.");
        }
        
        if(tests && card != null)
        {            
            card.get_tokens().add_value(suit, token_number);
            current_player.get_tokens().remove_value(suit, token_number);
            
            int total = 0;
            for(int i : card.get_tokens().get_values())
            {
                total += i;
            }

            if(total == rank)
            {
                card.set_constructed(true);
                state.set_message("A carta foi construida!");
            }
            else
            {
                state.set_message("Os tokens foram adicionados à carta.");
            }
        }   
    }
    
    /**
     * Gets the correct card from the given index.
     * (This is needed because the user will only see the cards in construction, without taking into account the district)
     * This method uses the function 'get_district_card' that also gives the district card, if that district is emply.
     * This is why the verification ignores the card with rank 11, wich is the rank of district cards.
     * @param index the index to search
     * @return the card
     */
    public PlayCard get_card_inconstruction(int index)
    {
        int count = 0; 
        PlayCard result = null;
        for(int i = 0; i < get_districts().size() ; i++)
        {
            PlayCard card = current_player.get_district_card(i);
            if((card.get_constructed() == false) && (card.get_rank() != 11))
            {
                if(count == index)
                {
                    result = card;
                    break;
                }
                
                count++;
            }
        }
        
        return result;
    }
    
    // </editor-fold>
    
}
