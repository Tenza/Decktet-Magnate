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

package magnate.ui.text;

import magnate.logic.*;
import magnate.states.*;

import java.util.Map;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class implements the game in textmode.
 * This class doesn't have support to games with AI.
 * @author Filipe
 * @version 1.0
 */
public class Text 
{ 
    private State state;
    private Game game;
    private boolean exit = false;

    public Text(Game g) 
    {
        game = g;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Run">
    
    public void run()
    {
        while (!exit) 
        {
            state = game.get_state();
            if(state instanceof Initialize)
            {
                ui_initialize();
            }
            else if(state instanceof Roll) 
            {
                ui_roll();
            } 
            else if(state instanceof Taxes) 
            {
                ui_taxes();
            }
            else if(state instanceof Collect) 
            {
                ui_collect();
            }    
            else if(state instanceof Play) 
            {
                ui_play();
            }
            else if(state instanceof Draw) 
            {
                ui_draw();
            }
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="ui_initialize">
    
    public void ui_initialize()
    {
        Scanner scan = new Scanner(System.in);
        
        System.out.println("\n=== Decktet - Magnata ===\n");
        System.out.println("1 - Definir jogadores");
        System.out.println("2 - Carregar jogo");
        System.out.println("0 - Sair");
        
        int option = get_number(0, 2, "Opção: ");
        
        switch(option)
        {
            case 0:
            {
                exit = true;
                break;
            }
            case 1:
            {
                System.out.print("Nome do primeiro jogador: ");
                String p1 = scan.next();

                System.out.print("Nome do segundo jogador: ");
                String p2 = scan.next();

                System.out.println();
                game.set_players(p1, p2);
                System.out.println(state.message());

                break;
            }
            case 2:
            {
                try (ObjectInputStream in = Files.file_read("Decktet.bin")) 
                {
                    Game g = (Game)in.readObject();
                    if(g != null)
                    {
                        game = g;
                    }
                    System.out.println("Jogo carregado com sucesso!");
                }
                catch(Exception e)
                {
                    System.out.println("Erro ao ler o ficheiro: " + e.getMessage());
                }
                                    
                break;
            }
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="ui_dice">
    
    public void ui_roll()
    {        
        int max;
        System.out.println("\n=== Lançar Dados: " + game.get_current_player().get_name() + " ===\n");
        System.out.println("1 - Lançar dados de 10 faces");
        System.out.println("2 - Pagar propriedade");
        System.out.println("3 - Trocar tokens");
        System.out.println("0 - Abandonar jogo");
        
        int option = get_number(0, 3, "Opção: ");
        
        switch(option)
        {
            case 0:
            {
                try (ObjectOutputStream out = Files.file_write("Decktet.bin", false, false)) 
                {
                    out.writeObject(game);
                }
                catch(IOException e)
                {
                    System.out.println("Erro ao gravar o ficheiro: " + e.getMessage());
                }
                
                exit = true;
                break;
            }
            case 1:
            {
                game.roll_dice_10();
                System.out.println(state.message());
                break;
            }
            case 2:
            {
                max = show_inconstruction();
                if(max > 0)
                {
                    int card = get_number(0, max, "Qual a carta: ");              
                    if (card != 0)
                    {
                        PlayCard c = game.get_card_inconstruction(card - 1);
                        
                        show_tokens(true, false, game.get_current_player());
                        System.out.println("Qual o token que pretende pagar: ");
                        for(int i = 0; i < c.get_suits().length; i++)
                        {
                            System.out.println((i + 1) + " - " + c.get_suits()[i]);
                        }
                        
                        int selected_token = get_number(1, c.get_suits().length, "Opção: ");  
                        int token_number = get_number(1, 9, "Numero de " + c.get_suits()[selected_token - 1] + ": ");
                        
                        game.pay_deed(card, selected_token, token_number, false);
                        System.out.println(state.message());
                    }
                }
                               
                break;
            }
            case 3:    
            {
                max = show_tokens();
                int give = get_number(0, max, "Qual o token a trocar: ", false);
                if (give > 0)
                {
                    int receive = get_number(0, max, "Qual o token a receber: ");
                    if (receive > 0)
                    {
                        game.trade_tokens(give, receive);
                        System.out.println(state.message());
                    }
                }
                
                break;
            }
        }  
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="ui_taxes">
    
    public void ui_taxes()
    {        
        System.out.println("\n=== Taxas: " + game.get_current_player().get_name() + " ===\n");
        System.out.println("1 - Lançar dado de 6 faces");
        
        int option = get_number(1, 1, "Opção: ");
        
        switch(option)
        {
            case 1:
            {
                game.roll_dice_6();
                System.out.println(state.message());
                break;
            }
        }  
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="ui_collect">
    
    public void ui_collect()
    {
        int selected;
        int max;
             
        max = show_collect();

        selected = get_number(1, max, "Opção: ", false);
        game.collect_tokens(selected);             
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="ui_play">
    
    public void ui_play()
    {
        Card c;
        int max;
        int selected;
        int selected_card;
        int selected_district;
        
        List<Card> hand = game.get_current_player().get_hand();
        
        System.out.println("\n=== A jogar: " + game.get_current_player().get_name() + " ===\n");
        System.out.println("1 - Construir propriedade");
        System.out.println("2 - Iniciar construção");
        System.out.println("3 - Vender carta");      
        
        int option = get_number(1, 3, "Opção: ");
        
        switch(option)
        {
            case 1:
            {
                max = show_hand(true);
                selected_card = get_number(0, max, "Qual a carta a jogar: ");
                
                if(selected_card != 0)
                {
                    c = hand.get(selected_card - 1);
                    max = show_districts(c);
                    selected_district = get_number(0, max, "Qual o distrito a jogar: ");

                    if(selected_district != 0)
                    {
                        show_tokens(false);
                        int token_number[] = new int[c.get_suits().length];

                        if(c.get_rank() > 1)
                            System.out.println("A soma dos valores apostados tem que ser igual a " + c.get_rank());
                        else
                            System.out.println("A soma dos valores apostados tem que ser igual a " + (c.get_rank() + 2));

                        for(int i = 0; i < c.get_suits().length; i++)
                        {
                            token_number[i] = get_number(1, 9, "Numero de " + c.get_suits()[i] + ": ", false);
                        }
                        
                        System.out.println();
                        game.build(selected_card, selected_district, token_number);
                        System.out.println(state.message());            
                    }              
                }
                
                break;
            }
            case 2:
            {
                max = show_hand(true, true);
                selected_card = get_number(0, max, "Qual a carta a jogar: ");
                
                if(selected_card != 0)
                {
                    max = show_districts(hand.get(selected_card - 1));
                    selected_district = get_number(0, max, "Qual o distrito a jogar: ");

                    if(selected_district != 0)
                    {
                        game.deed(selected_card, selected_district);
                        System.out.println(state.message());
                    }   
                }
                
                break;
            }
            case 3:
            {
                max = show_hand(false);
                selected = get_number(0, max, "Qual a carta a vender: ");
                
                if(selected != 0)
                {
                    game.sell(selected);
                    System.out.println(state.message());
                }
                
                break;
            }
        } 
        
    }
    
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="ui_draw">
    
    public void ui_draw()
    {      
        int max;
        System.out.println("\n=== Obter Carta: " + game.get_current_player().get_name() + " ===\n");
        System.out.println("1 - Retirar Carta");
        System.out.println("2 - Pagar propriedade");
        System.out.println("3 - Trocar tokens");
        
        int option = get_number(1, 3, "Opção: ");
                
        switch(option)
        {
            case 1:
            {
                game.end_play();
                System.out.println(state.message());
                break;
            }
            case 2:
            {
                max = show_inconstruction();
                if(max > 0)
                {
                    int card = get_number(0, max, "Qual a carta: ");              
                    if (card != 0)
                    {
                        PlayCard c = game.get_card_inconstruction(card - 1);
                        
                        show_tokens(true, false, game.get_current_player());
                        System.out.println("Qual o token que pretende pagar: ");
                        for(int i = 0; i < c.get_suits().length; i++)
                        {
                            System.out.println((i + 1) + " - " + c.get_suits()[i]);
                        }
                        
                        int selected_token = get_number(1, c.get_suits().length, "Opção: ");  
                        int token_number = get_number(1, 9, "Numero de " + c.get_suits()[selected_token - 1] + ": ");
                        
                        game.pay_deed(card, selected_token, token_number, false);
                        System.out.println(state.message());
                    }
                }
                
                break;
            }
            case 3:
            {
                max = show_tokens();
                int give = get_number(0, max, "Qual o token a trocar: ", false);
                if (give > 0)
                {
                    int receive = get_number(0, max, "Qual o token a receber: ");
                    if (receive > 0)
                    {
                        game.trade_tokens(give, receive);
                        System.out.println(state.message());
                    }
                }

                break;
            }
        }   
        
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Show Functions">
    
    /**
     * Shows all the remaining cards on the deck.
     * @return the number of cards.
     */
    public int show_deck()
    {
        List<Card> deck = game.get_deck();
        for(int i = 0; i < deck.size(); i++)
        {            
            String x;
            System.out.format("%-32s", (i + 1) + " - Carta: " + deck.get(i).get_name());
            System.out.format("%-16s", "Valor: " + deck.get(i).get_rank());
            
            x = "";
            String[] suits = deck.get(i).get_suits();
            for(int j = 0; j < suits.length ; j++)
            {
                if(j > 0)
                {
                    x += ", ";
                }
                
                x += suits[j].toString();
            }
            
            System.out.format("%-27s", "Nipes: " + x);
            System.out.println();
        }
        
        return deck.size();
    }
          
    /**
     * Shows the tokens of the given player.
     * @param single_line show tokens on a single line.
     * @param show_numbers show the numbers related to each token.
     * @param p the player.
     * @return the number of tokens.
     */  
    public int show_tokens(boolean single_line, boolean show_numbers, Player p)
    {
        String[] token = p.get_tokens().get_keys_all();
        Integer[] value = p.get_tokens().get_values_all();
        
        if(!single_line)
        {
            System.out.println("Os seus tokens:");
        }
        
        for(int i = 0; i < token.length; i++)
        {
            if(single_line)
            {
                System.out.print(token[i] + " - " + value[i] + " ");
            }
            else
            {
                if(show_numbers)
                {
                    System.out.format("%-15s", (i + 1) + " - " + token[i]);
                }
                else
                {
                    System.out.format("%-15s", token[i]);
                }
                System.out.format("%-10s\n", value[i]);
            }
        }
        
        if(single_line)
        {
            System.out.println();
        }
        else
        {
            if(show_numbers)
            {
                System.out.println("0 - Voltar");
            }
        }
        
        return token.length;
    }
    
    public int show_tokens()
    {
        return show_tokens(false, true, game.get_current_player());
    }
    
    public int show_tokens(boolean show_numbers)
    {
        return show_tokens(false, show_numbers, game.get_current_player());
    }
    
    /**
     * Shows the hand of the current player.
     * @param calculate_tokens shows if it is possible to play that card.
     * @param deed if the card to be played is a deed.
     * @return number of cards in hand.
     */    
    public int show_hand(boolean calculate_tokens, boolean deed)
    {
        List<Card> hand = game.get_current_player().get_hand();
        
        for(int i = 0; i < hand.size() ; i++)
        {      
            String x;
            System.out.format("%-32s", (i + 1) + " - Carta: " + hand.get(i).get_name());
            System.out.format("%-16s", "Valor: " + hand.get(i).get_rank());
            
            x = "";
            String[] suits = hand.get(i).get_suits();
            for(int j = 0; j < suits.length ; j++)
            {
                if(j > 0)
                {
                    x += ", ";
                }
                
                x += suits[j].toString();
            }
            
            System.out.format("%-27s", "Nipes: " + x);
            
            if (calculate_tokens)
            {
                if(game.get_current_player().verify_card_tokens(hand.get(i), deed))
                {
                    System.out.format("%25s", "(Tokens suficientes)\n");
                }
                else
                {
                    System.out.format("%25s", "(Tokens insuficientes)\n");
                }
            }
            else
            {
                System.out.println();
            }
        }
        
        System.out.println("0 - Voltar");
        return hand.size();
    }
    
    public int show_hand(boolean calculate_tokens)
    {
        return show_hand(calculate_tokens, false);
    }
    
    /**
     * Shows all the playable districts of the given card.
     * @param c the card to be tested.
     * @return number of district cards.
     */
    public int show_districts(Card c)
    {        
        List<PlayCard> districts = game.get_districts();
        
        for(int i = 0; i < districts.size() ; i++)
        {  
            System.out.format("%-25s", (i + 1) + " - " + districts.get(i).get_name());
            
            if(game.get_current_player().verify_district(c, i))
            {
                System.out.format("(Possivel)\n");
            }
            else
            {
                System.out.format("(Impossivel)\n");
            }
        }
        System.out.println("0 - Voltar");
        
        return districts.size();
    }
    
    /**
     * Shows all the cards under construction for the current player.
     * @return number of cards in construction.
     */
    public int show_inconstruction()
    {
        int result = 0; 
        
        for(int i = 0; i < game.get_districts().size() ; i++)
        {
            PlayCard card = game.get_current_player().get_district_card(i);
            if(card.get_constructed() == false && card.get_rank() != 11)
            {
                String x;
                System.out.format("%-32s", (i + 1) + " - Carta: " + card.get_name());
                System.out.format("%-16s", "Valor: " + card.get_rank());

                x = "";
                String[] suits = card.get_suits();
                for(int j = 0; j < suits.length ; j++)
                {
                    if(j > 0)
                    {
                        x += ", ";
                    }

                    x += suits[j].toString();
                    
                    x += "(" + card.get_tokens().get_value(suits[j]) + ")"; 
                }

                System.out.format("%-27s", "Nipes: " + x);
                System.out.println();
                result++;
            }         
        }
        if(result == 0)
        {
            System.out.println("Não existem cartas em construção.");
        }
        else
        {
            System.out.println("0 - Voltar");
        }
                
        return result;
    }
    
    /**
     * Show the card that the player has to choose a token from.
     * @return the number of suits.
     */
    public int show_collect()
    {
        int result = 0;
        Map<PlayCard, String> cards = ((Collect)this.state).get_cards();
        int tokens_size = ((Collect)this.state).get_player1_tokens_size() + ((Collect)this.state).get_player2_tokens_size();
       
        if(tokens_size != cards.size())
        {      
            PlayCard card = game.get_card_index(cards, tokens_size);
            String card_player = game.get_value_index(cards, tokens_size);
            
            System.out.println("\n=== Coleta de Recursos: " + card_player + " ===\n");
            
            if(tokens_size == 0)
            {
                System.out.print("Tokens " + game.get_player1().get_name() + ": ");
                show_tokens(true, false, game.get_player1());
                System.out.print("Tokens " + game.get_player2().get_name() + ": ");
                show_tokens(true, false, game.get_player2());
                System.out.println();
            }
            
            System.out.println("Qual o token a receber para a carta: " + card.get_name());
            for(int j = 0; j < card.get_suits().length; j++)
            {
                System.out.println((j + 1) + " - " + card.get_suits()[j]);
            }
            result = card.get_suits().length;
        }
        
        return result;
    }
    
    /**
     * Deals with all the input from the used.
     * @param min minimum number accepted.
     * @param max maximum number accepted.
     * @param line the phrase to be shown.
     * @return the selected value.
     */
    public int get_number(int min, int max, String line)
    {
        return get_number(min, max, line, true);
    }
    
    public int get_number(int min, int max, String line, boolean new_line)
    {
        Scanner scan = new Scanner(System.in);
        
        String option;
        int x = min - 1;
        int selected = x;
        
        while(selected == x)
        { 
            System.out.print(line);
            option = scan.next();

            try
            {
                selected = Integer.parseInt(option);
                if((selected < min) || (selected > max))
                {
                    selected = x;
                    System.out.println("Essa opção não existe.");
                }
            }
            catch (NumberFormatException nfe)
            {
                System.out.println("Essa opção não existe.");
            }
        }
        
        if(new_line && selected != 0)
        {
            System.out.println();
        }
        
        return selected;
    }
    
    // </editor-fold>
    
}
