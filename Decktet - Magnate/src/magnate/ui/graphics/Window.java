package magnate.ui.graphics;

import magnate.logic.Game;
import magnate.logic.Files;
import magnate.AI.RandomAI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.ObjectInputStream;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This class represents the main Window.
 * All the classes that extend JPanel are contained here.
 * @author Filipe
 * @version 1.0
 */
public class Window extends JFrame implements Observer
{   
    private GameModel model;
    private Cards cards;
      
    private Container container;
    private JPanel table;
    
    private JPanel play;
    private JPanel menu;
    private JPanel deck;
    private JPanel player1;
    private JPanel player2; 
    private JPanel player1_container;
    private JPanel player2_container;
    
    private Table game_table;
    private Menu game_menu;
    private Deck game_deck;
    private Hand hand_player1;
    private Hand hand_player2;
    private Tokens crowns_player1;
    private Tokens tokens_player1;
    private Tokens crowns_player2;
    private Tokens tokens_player2;
    
    private JLabel message;
    private JLabel player1_name;
    private JLabel player2_name;
  
    public Window(GameModel g, int gm, String p1, String p2) 
    {           
        model = g;
        set_game(gm, p1, p2);
        
        create_view();
        build_view();
        display_view();
        register_observers();
        set_window();
    }
    
    // <editor-fold defaultstate="collapsed" desc="View">
    
    /**
     * Creates all the panels that the window holds.
     */
    private void create_view()
    {
        //Window Panel Holders
        container = getContentPane();
	container.setLayout(new BorderLayout());
        
        table = new JPanel();
        table.setLayout(new BorderLayout());
        
        play = new JPanel();
        play.setLayout(new BorderLayout());
        
        player1 = new JPanel();
        player1.setLayout(new BorderLayout());
        
        player2 = new JPanel();
        player2.setLayout(new BorderLayout());
        
        menu = new JPanel();
        menu.setLayout(new BorderLayout());
        
        deck = new JPanel();
        deck.setLayout(new BorderLayout());
        
        player1_container = new JPanel();
        player1_container.setLayout(new BorderLayout());
        
        player2_container = new JPanel();
        player2_container.setLayout(new BorderLayout());
    }
    
    /**
     * Builds and sets all classes to be added to this window.
     */
    private void build_view()
    {
        cards = new Cards();
        message = new JLabel(model.get_message(), SwingConstants.CENTER);
        player1_name = new JLabel(model.get_game().get_player1().get_name(), SwingConstants.CENTER);
        player2_name = new JLabel(model.get_game().get_player2().get_name(), SwingConstants.CENTER);
        
        game_table = new Table(model, cards);
        
        game_menu = new Menu(model, this);
        game_deck = new Deck(model, cards);
        
        hand_player1 = new Hand(model, cards, model.get_game().get_player1());
        hand_player2 = new Hand(model, cards, model.get_game().get_player2());
        
        crowns_player1 = new Tokens(model, cards, model.get_game().get_player1(), true);
        tokens_player1 = new Tokens(model, cards, model.get_game().get_player1(), false);
        crowns_player2 = new Tokens(model, cards, model.get_game().get_player2(), true);
        tokens_player2 = new Tokens(model, cards, model.get_game().get_player2(), false);
        
        crowns_player1.set_token(tokens_player1);
        tokens_player1.set_token(crowns_player1);
        crowns_player2.set_token(tokens_player2);
        tokens_player2.set_token(crowns_player2);
        
        message.setFont(new Font("Courier New", Font.BOLD, 14));
        player1_name.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        player2_name.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        set_fonts(); 
    }
    
    /**
     * Displays all the data by binding the window panels to the classes.
     */
    private void display_view()
    {	
        play.add(game_table, BorderLayout.CENTER);
        
        menu.add(game_menu, BorderLayout.CENTER);
        deck.add(game_deck, BorderLayout.CENTER);
        
        player1_container.add(hand_player1, BorderLayout.CENTER);
        player1_container.add(player1_name, BorderLayout.NORTH);
        player1_container.add(crowns_player1, BorderLayout.WEST);
        player1_container.add(tokens_player1, BorderLayout.EAST);
        
        player1.add(Box.createHorizontalStrut(180), BorderLayout.WEST);
        player1.add(Box.createHorizontalStrut(180), BorderLayout.EAST);
        player1.add(player1_container, BorderLayout.CENTER);
           
        player2_container.add(hand_player2, BorderLayout.CENTER);
        player2_container.add(message, BorderLayout.NORTH);
        player2_container.add(player2_name, BorderLayout.SOUTH);
        player2_container.add(crowns_player2, BorderLayout.WEST);
        player2_container.add(tokens_player2, BorderLayout.EAST);
                  
        player2.add(Box.createHorizontalStrut(180), BorderLayout.WEST);
        player2.add(Box.createHorizontalStrut(180), BorderLayout.EAST);
        player2.add(player2_container, BorderLayout.CENTER);
        
        table.add(menu, BorderLayout.EAST);
        table.add(deck, BorderLayout.WEST);
        table.add(play, BorderLayout.CENTER);
        table.add(player1, BorderLayout.NORTH);
        table.add(player2, BorderLayout.SOUTH);
        
        container.add(table, BorderLayout.CENTER);
      
        validate();
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
     * Updates the message displayed.
     */
    @Override
    public void update(Observable arg0, Object arg1) 
    {
        set_fonts();
        String msg = model.get_message();
        msg = "<html><div style=\"text-align:center;\">" + msg.replace("\n", "<br />") + "</div></html>";
        
        message.setText(msg);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Functions">
    
    /**
     * Sets all the properties of this window.
     */
    private void set_window()
    {
        setSize(1280, 720); 
        setTitle("Decktet - Magnate");  
        setExtendedState(MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1360, 870));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("Images/icon.png")));
        setVisible(true);
        
        if(model.get_game().get_game_mode() == 2)
        {
            model.force_update();
        }
    }
    
    /**
     * Sets the font of the displayed text.
     * This method is called on update, so that the color of the current player is diferent.
     */
    private void set_fonts()
    {
        if(model.get_game().get_current_player().equals(model.get_game().get_player1()))
        { 
            player1_name.setFont(new Font("Courier New", Font.BOLD, 14));
            player2_name.setFont(new Font("Courier New", Font.PLAIN, 12));
            player1_name.setForeground(Color.red);
            player2_name.setForeground(Color.black);
        }
        else
        {
            player2_name.setFont(new Font("Courier New", Font.BOLD, 14));
            player1_name.setFont(new Font("Courier New", Font.PLAIN, 12));
            player2_name.setForeground(Color.red);
            player1_name.setForeground(Color.black);
        }
    }
    
    /**
     * This method can load a game, start a new one (by calling set_players) and also sets the AI agents.
     * @param game_mode the selected game mode. 
     * @param p1 Name of the first player.
     * @param p2 Name of the second player.
     */
    private void set_game(int game_mode, String p1, String p2)
    {
        boolean new_game = true;
        
        if(game_mode == 3) //Load Game
        {
            try (ObjectInputStream in = Files.file_read("Decktet.bin")) 
            {
                Game g = (Game)in.readObject();
                if(g != null)
                {
                    model.set_game(g);
                    model.set_state(model.get_state());
                    model.get_state_model().set_message("Jogo carregado com sucesso!");
                    new_game = false;
                }
            }
            catch(Exception e)
            {
                System.out.println("Erro ao ler o ficheiro: " + e.getMessage());
                new_game = true;
            }
        }
        
        if(new_game)
        {
            model.get_game().set_game_mode(game_mode);
            model.set_players(p1, p2);
        }
        
        if(model.get_game().get_game_mode() == 1) //Player VS AI
        {
            RandomAI AI = new RandomAI(model, model.get_game().get_player2());
        }
        else if(model.get_game().get_game_mode() == 2) //AI VS AI
        {
            RandomAI AI1 = new RandomAI(model, model.get_game().get_player1());
            RandomAI AI2 = new RandomAI(model, model.get_game().get_player2());
        }
    }
    
    // </editor-fold>  
    
}
