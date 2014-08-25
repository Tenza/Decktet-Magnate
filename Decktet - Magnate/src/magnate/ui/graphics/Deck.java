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

package magnate.ui.graphics;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * This class holds the images of the back of the deck and a counter of the cards.
 * @author Filipe
 * @version 1.0
 */
public class Deck extends JPanel implements Parameters, Observer
{
    private GameModel model;
    private Cards cards;
    
    private JLabel image_deck;
    private JLabel count_deck;
    private JLabel image_disc;
    private JLabel count_disc;
    
    public Deck(GameModel m, Cards c) 
    {
        model = m;
        cards = c;
        
        create_view();
        build_view();
        display_view();
        register_observers();
    }
 
    // <editor-fold defaultstate="collapsed" desc="View">
    
    /**
     * Sets this panel.
     */
    private void create_view()
    {
        setPreferredSize(new Dimension(170, 0));
        setLayout(new GridBagLayout()); 
        setBorder(new TitledBorder(BorderFactory.createTitledBorder("")));
    }
    
    /**
     * Fills the data of the members.
     * There is no need to recall this method. The images dont change.
     */
    private void build_view()
    {   
        ImageIcon image = new ImageIcon(cards.get_image("Card Back").getScaledInstance(deck_cards_X, deck_cards_Y, Image.SCALE_SMOOTH));
       
        image_deck = new JLabel(image);
        image_disc = new JLabel(image);
        
        count_deck = new JLabel("Cartas no Baralho: " + model.get_game().get_deck().size());
        count_disc = new JLabel("Cartas Discartadas: " + model.get_game().get_discarted().size());
    }
    
    /**
     * Shows the data.
     * There is no need to recall this method. The images dont change.
     */
    private void display_view()
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        
        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(image_deck, gbc);
        
        gbc.insets = new Insets(0, 0, 25, 0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(count_deck, gbc);
        
        gbc.insets = new Insets(25, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(image_disc, gbc);
        
        gbc.insets = new Insets(5, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(count_disc, gbc);
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
        count_deck.setText("Cartas no Baralho: " + model.get_game().get_deck().size());
        count_disc.setText("Cartas Discartadas: " + model.get_game().get_discarted().size());
        
        getParent().validate();
    }
    
    // </editor-fold>
    
}
