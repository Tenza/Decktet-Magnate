package magnate.ui.graphics;

import magnate.states.*;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import magnate.logic.Card;
import magnate.logic.Files;
import magnate.logic.Player;
import magnate.logic.Token;

/**
 * This class constains the main menu of the game.
 * Most of the actions requiered to change the state of the game are called from here.
 * @author Filipe
 * @version 1.0
 */
public class Menu extends JPanel implements Observer
{
    private GameModel model;
    private JFrame window;
    
    private JButton btn_roll_10;
    private JButton btn_roll_6;
    private JButton btn_build;
    private JButton btn_deed;
    private JButton btn_sell;
    private JButton btn_end_play;
    private JButton btn_pay_deed;
    private JButton btn_trade_tokens;
    private JButton btn_quit;
    
    public Menu(GameModel m, Window w) 
    {
        model = m;
        window = w;

        create_view();
        build_view();
        display_view();
        register_listeners();
        register_observers();
    }
    
    // <editor-fold defaultstate="collapsed" desc="View">
    
    /**
     * Creates the buttons to use.
     */
    private void create_view()
    { 
        setPreferredSize(new Dimension(170, 0));
        setLayout(new GridLayout(11, 1));
        setBorder(new TitledBorder(BorderFactory.createTitledBorder("")));
        
        btn_roll_10 = new JButton("Lançar Dados");
        btn_roll_6 = new JButton("Pagar Taxas");
        btn_build = new JButton("Construir Propriedade");
        btn_deed = new JButton("Iniciar Construção");
        btn_sell = new JButton("Vender Carta");
        btn_end_play = new JButton("Terminar Jogada");
        btn_pay_deed = new JButton("Pagar Propriedade");
        btn_trade_tokens = new JButton("Trocar Tokens");
        btn_quit = new JButton("Abandonar Jogo");
    }
    
    /**
     * Sets the buttons.
     */
    private void build_view()
    {
        set_menu();
    }
    
    /**
     * Displays the buttons.
     */
    private void display_view()
    {
        add(new JLabel(""));
        add(btn_roll_10);
        add(btn_roll_6);
        add(btn_build);
        add(btn_deed);
        add(btn_sell);
        add(btn_end_play);
        add(btn_pay_deed);
        add(btn_trade_tokens);
        add(btn_quit);  
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Update">
    
    /**
     * Add this class to the observers list.
     */
    private void register_observers()
    {
        model.addObserver(this);
    }
    
    /**
     * Updates the buttons state.
     */
    @Override
    public void update(Observable arg0, Object arg1) 
    {
        set_menu();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Actions">
    
    /**
     * Register all the listeners of this class.
     */
    private void register_listeners()
    {
        btn_roll_10.addActionListener(new ActionListener() 
        {
            @Override 
            public void actionPerformed(ActionEvent e)
            {
                click_roll_10(e);
            }
        });
        
        btn_roll_6.addActionListener(new ActionListener() 
        {
            @Override 
            public void actionPerformed(ActionEvent e)
            {
                click_roll_6(e);
            }
        });
        
        btn_build.addActionListener(new ActionListener() 
        {
            @Override 
            public void actionPerformed(ActionEvent e)
            {
                click_build(e);
            }
        });
        
        btn_deed.addActionListener(new ActionListener() 
        {
            @Override 
            public void actionPerformed(ActionEvent e)
            {
                click_deed(e);
            }
        });
        
        btn_sell.addActionListener(new ActionListener() 
        {
            @Override 
            public void actionPerformed(ActionEvent e)
            {
                click_sell(e);
            }
        });
        
        btn_end_play.addActionListener(new ActionListener() 
        {
            @Override 
            public void actionPerformed(ActionEvent e)
            {
                click_end_play(e);
            }
        });
        
        btn_pay_deed.addActionListener(new ActionListener() 
        {
            @Override 
            public void actionPerformed(ActionEvent e)
            {
                click_pay_deed(e);
            }
        });
        
        btn_trade_tokens.addActionListener(new ActionListener() 
        {
            @Override 
            public void actionPerformed(ActionEvent e)
            {
                click_trade_tokens(e);
            }
        });
        
        btn_quit.addActionListener(new ActionListener() 
        {
            @Override 
            public void actionPerformed(ActionEvent e)
            {
                click_quit(e);
            }
        });
    }
    
    /**
     * Rolls the 10 face dice.
     * @param e ActionEvent
     */
    public void click_roll_10(ActionEvent e)
    {
        model.roll_dice_10();
    }
    
    /**
     * Rolls the 6 face dice.
     * @param e ActionEvent
     */
    public void click_roll_6(ActionEvent e)
    {
        model.roll_dice_6();
    }
    
    /**
     * Builds a card.
     * The helper function is needed because the game receives an array with 
     * the quantity of the tokens for the selected card and does not take into 
     * account the TYPE of tokens selected. This helper functions verifys if the
     * tokens selected match the card tokens. This fuction also provides abstraction
     * to the type of the click used to select the token.
     * @param e ActionEvent
     */
    public void click_build(ActionEvent e)
    {
        int token_number[] = check_build();
        
        if(token_number != null)
        {
            model.build(token_number);
        }
        else
        {
            model.get_state_model().set_message("Selecione uma carta para construir, os seus tokens e um distrito.");
            model.force_update();
        }
    }
    
    /**
     * Places a card as a deed.
     * @param e ActionEvent
     */
    public void click_deed(ActionEvent e)
    {
        model.deed();
    }
    
    /**
     * Sells a card.
     * @param e ActionEvent
     */
    public void click_sell(ActionEvent e)
    {
        model.sell();
    }
    
    /**
     * Ends the turn of the current player.
     * @param e ActionEvent
     */
    public void click_end_play(ActionEvent e)
    {
        model.end_play();
    }
    
    /**
     * Adds tokens to the selected card.
     * @param e ActionEvent
     */
    public void click_pay_deed(ActionEvent e)
    {
        model.pay_deed();
    }
    
    /**
     * Trades the selected tokens.
     * Attention: Unlike the build function, here the type of the clicks matter.
     * Left Click to select the token to give and right click to select the token to receive.
     * @param e ActionEvent
     */
    public void click_trade_tokens(ActionEvent e)
    {
        model.trade_tokens();
    }
    
    /**
     * Saves the game and exits the game. 
     * No model function here.
     * @param e ActionEvent
     */
    public void click_quit(ActionEvent e)
    {
        try (ObjectOutputStream out = Files.file_write("Decktet.bin", false, false)) 
        {
            out.writeObject(model.get_game());
        }
        catch(IOException ex)
        {
            System.out.println("Erro ao gravar o ficheiro: " + ex.getMessage());
        }

        window.dispose();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Functions">
    
    /**
     * Sets the buttons state according to the game state.
     * Also calls the Collection class on the turn of the PLAYER.
     * (The Collection is also called by the AI but from the AI Class.)
     */
    private void set_menu()
    {
        State state = model.get_state();
        
        if(state instanceof Initialize)
        {
            btn_roll_10.setEnabled(false);
            btn_roll_6.setEnabled(false);
            btn_build.setEnabled(false);
            btn_deed.setEnabled(false);
            btn_sell.setEnabled(false);
            btn_pay_deed.setEnabled(false);
            btn_end_play.setEnabled(false);
            btn_trade_tokens.setEnabled(false);
            btn_quit.setEnabled(false);
        }
        else if(state instanceof Roll) 
        {
            btn_roll_10.setEnabled(true);
            btn_roll_6.setEnabled(false);
            btn_build.setEnabled(false);
            btn_deed.setEnabled(false);
            btn_sell.setEnabled(false);
            btn_end_play.setEnabled(false);
            btn_pay_deed.setEnabled(true);
            btn_trade_tokens.setEnabled(true);
            btn_quit.setEnabled(true);
        } 
        else if(state instanceof Taxes) 
        {
            btn_roll_10.setEnabled(false);
            btn_roll_6.setEnabled(true);
            btn_build.setEnabled(false);
            btn_deed.setEnabled(false);
            btn_sell.setEnabled(false);
            btn_end_play.setEnabled(false);
            btn_pay_deed.setEnabled(true);
            btn_trade_tokens.setEnabled(true);
            btn_quit.setEnabled(true);
        }
        else if(state instanceof Collect) 
        {
            btn_roll_10.setEnabled(false);
            btn_roll_6.setEnabled(false);
            btn_build.setEnabled(false);
            btn_deed.setEnabled(false);
            btn_sell.setEnabled(false);
            btn_end_play.setEnabled(false);
            btn_pay_deed.setEnabled(false);
            btn_trade_tokens.setEnabled(false);
            btn_quit.setEnabled(false); 
        }    
        else if(state instanceof Play) 
        {
            btn_roll_10.setEnabled(false);
            btn_roll_6.setEnabled(false);
            btn_build.setEnabled(true);
            btn_deed.setEnabled(true);
            btn_sell.setEnabled(true);
            btn_end_play.setEnabled(false);
            btn_pay_deed.setEnabled(true);
            btn_trade_tokens.setEnabled(true);
            btn_quit.setEnabled(true); 
        }
        else if(state instanceof Draw) 
        {
            btn_roll_10.setEnabled(false);
            btn_roll_6.setEnabled(false);
            btn_build.setEnabled(false);
            btn_deed.setEnabled(false);
            btn_sell.setEnabled(false);
            btn_end_play.setEnabled(true);
            btn_pay_deed.setEnabled(true);
            btn_trade_tokens.setEnabled(true);
            btn_quit.setEnabled(true);
        }
        
        if(state instanceof Collect) 
        {
            Collection collect = new Collection(model);
        }
               
        model.set_selected_handcard(0);
        model.set_selected_district(0);
        model.set_selected_playcard(0);
        model.set_selected_playcard_district(0);     
        model.set_selected_token_count1(0);
        model.set_selected_token_count2(0);
        model.set_selected_token_count3(0);
        model.set_selected_token_index1(-1);
        model.set_selected_token_index2(-1);
        model.set_selected_token_index3(-1);
    }
    
    /**
     * Builds and tests the array with the selected tokens.
     * Provides abstraction to the type of the click used to select the token.
     * @return the array, NULL if error.
     */
    private int[] check_build()
    {     
        int result[] = null;
        
        if(model.get_selected_handcard() > 0)
        {       
            Player p = model.get_game().get_current_player();
            Card c = p.get_hand().get(model.get_selected_handcard() - 1);
            result = new int[c.get_suits().length];
            int count = 0;

            for(String s : c.get_suits())
            { 
                String selected_token1 = new Token().get_name(model.get_selected_token_index1() + 1);
                String selected_token2 = new Token().get_name(model.get_selected_token_index2() + 1);
                String selected_token3 = new Token().get_name(model.get_selected_token_index3() + 1);

                if(s.equals(selected_token1))
                {
                    result[count] = model.get_selected_token_count1();
                }
                else if(s.equals(selected_token2))
                {
                    result[count] = model.get_selected_token_count2();
                }
                else if(s.equals(selected_token3))
                {
                    result[count] = model.get_selected_token_count3();
                }
                else
                {
                    result = null;
                    break;
                }

                count++;
            }
        }
        
        return result;
    }
    
    // </editor-fold>
    
}
