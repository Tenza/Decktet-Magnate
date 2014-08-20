package magnate.ui.graphics;

import magnate.logic.Player;
import magnate.logic.Card;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observer;
import java.util.Observable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * This class contains the cards on the hand of the given player.
 * @author Filipe
 * @version 1.0
 */
public class Hand extends JPanel implements Parameters, Observer
{   
    private GameModel model;
    private Cards cards;
    private Player player;
    
    private JPanel panel;
    private Map<Integer, JLabel> images;
    
    public Hand(GameModel m, Cards c, Player p) 
    {
        model = m;
        cards = c;
        player = p;
           
        create_view();
        build_view();
        display_view();
        register_listeners();
        register_observers();
    }   
    
    // <editor-fold defaultstate="collapsed" desc="View">
    
    /**
     * Creates the array and panels to use.
     */
    private void create_view()
    {
        panel = new JPanel();
        images = new LinkedHashMap<>();
    }
    
    /**
     * Builds the arrays with the current hand cards.
     * Also adds a tooltip with the information of the card.
     * This method is called on update.
     */
    private void build_view()
    {
        images.clear();
        Card c;
        JLabel card;
        String card_info;
        
        for(int i = 0; i < player.get_hand().size() ; i++)
        {
            c = player.get_hand().get(i);
            
            ImageIcon image = new ImageIcon(cards.get_image(c.get_name()).getScaledInstance(hand_cards_X, hand_cards_Y, Image.SCALE_SMOOTH));
            card = new JLabel(image);

            card_info = "<html><b>Carta na Mão<br />Valor: </b>" + c.get_rank() + "<br /><b>Nome: </b>" + c.get_name() + "<br /><b>Naipes: </b>";
            for (String s : c.get_suits())
            {
                card_info += s + " ";
            }       
            card_info  += "</html>";
            
            card.setToolTipText(card_info);
            card.setBorder(new LineBorder(new Color(214,217,223), 2));
            
            images.put(i, card);
        }
    }
    
    /**
     * Displays all the data by binding the data on the arrays to the panel.
     * This method is called on update.
     */
    private void display_view()
    {
        panel.removeAll();

        for(int i = 0; i < player.get_hand().size() ; i++)
        {
            panel.add(images.get(i));
        }

        add(panel);
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
     * Updates this panel.
     */
    @Override
    public void update(Observable arg0, Object arg1) 
    {
        build_view();
        display_view();
        register_listeners();
        
        getParent().validate();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Actions">
    
    /**
     * Register all the listeners of this class.
     */
    private void register_listeners()
    {
        for(int i = 0; i < player.get_hand().size() ; i++)
        {            
            images.get(i).addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e) 
                {
                    click_select_card(e);
                }
            });
        }
    }
    
    /**
     * This function is called when a card is clicked.
     * When clicked it is set a variable in the model with the card clicked.
     */
    private void click_select_card(MouseEvent e)
    {
        if(player.equals(model.get_game().get_current_player()))
        {
            int index = get_key(e.getSource());
            System.out.println("Hand Card Index: " + index);
            
            for(int i = 0; i < player.get_hand().size() ; i++)
            {
                if(i == index)
                {
                    images.get(i).setBorder(new LineBorder(Color.RED, 2));
                    model.set_selected_handcard(i + 1);
                }
                else
                {
                    images.get(i).setBorder(new LineBorder(new Color(214,217,223), 2));
                }
            }
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Functions">
    
    /**
     * Gets the key (ID) of the selected Object.
     * @param value the object selected.
     * @return the ID of the object.
     */
    public int get_key(Object value)
    {   
        int result = -1;
        for(Map.Entry<Integer, JLabel> entry : images.entrySet()) 
        {
            if (entry.getValue().equals(value)) 
            {
                result = entry.getKey();
                break;
            }          
        }
        
        return result;
    }
    
    // </editor-fold>
    
}
