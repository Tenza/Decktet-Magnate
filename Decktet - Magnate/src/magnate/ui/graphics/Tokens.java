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

import magnate.logic.Player;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

/**
 * This class contains part of the tokens of the given player.
 * The attribute 'crowns', defines the type of the tokens to be displayed.
 * The extra wrapper (panel) is needed so that the main panel position dont warp when the images are clicked and text is added below.
 * @author Filipe
 * @version 1.0
 */
public class Tokens extends JPanel implements Parameters, Observer
{
    private GameModel model;
    private Cards cards;
    private Player player;
    private Tokens other;
    private boolean crowns;
    
    private Map<Integer, JLabel> images;   
    private List<JLabel> values;
    private List<JLabel> selected;
    
    private JPanel panel;
    
    public Tokens(GameModel m, Cards c, Player p, boolean cw) 
    {
        model = m;
        cards = c;
        player = p;
        crowns = cw;

        create_view();
        build_view();
        display_view();
        register_listeners();
        register_observers();
    }  
    
    // <editor-fold defaultstate="collapsed" desc="View">
    
    /**
     * Creates the arrays, panels and the images to use.
     * There is no need to update the images.
     */
    private void create_view()
    {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        images = new LinkedHashMap<>();
        values = new ArrayList<>();
        selected = new ArrayList<>();
        
        String[] token = player.get_tokens().get_keys_all();
        for(int i = 0; i < token.length; i++)
        {
            ImageIcon image = new ImageIcon(cards.get_image(token[i]).getScaledInstance(token_X, token_Y, Image.SCALE_SMOOTH));
            images.put(i, new JLabel(image));
        }
        
    }
    
    /**
     * Builds the arrays with the current tokens values and cleans the selected ones.
     * This method is called on update.
     */
    private void build_view()
    {
        values.clear();
        selected.clear();
        
        String[] token = player.get_tokens().get_keys_all();
        Integer[] value = player.get_tokens().get_values_all();
        
        for(int i = 0; i < token.length; i++)
        {            
            values.add(new JLabel("Valor: " + value[i]));
            selected.add(new JLabel(""));
        }
    }
    
    /**
     * Displays all the data by binding the data on the arrays to the panel.
     * This method is called on update.
     */
    private void display_view()
    {
        panel.removeAll();
        
        int count = 0;
        boolean found = false;
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        
        String[] token = player.get_tokens().get_keys_all();
        
        for(int i = 0; i < token.length; i++)
        {
            for(int j = 0; j < player.get_crowns().size(); j++)
            {
                if(token[i].equals(player.get_crowns().get(j).get_suits()[0]))
                {
                    found = true;
                    break;
                }
            }
            
            if((found && crowns) || (!found && !crowns))
            {
                if(crowns)
                    gbc.insets = new Insets(0, 10, 0, 0);
                else
                    gbc.insets = new Insets(0, 0, 0, 10);
                
                gbc.gridx = count;
                gbc.gridy = 0;
                images.get(i).setBorder(new LineBorder(new Color(214,217,223), 2));
                panel.add(images.get(i), gbc);
                
                gbc.gridx = count;
                gbc.gridy = 1;
                panel.add(values.get(i), gbc);
                
                gbc.gridx = count;
                gbc.gridy = 2;
                panel.add(selected.get(i), gbc);
                
                count++;
            }
            
            found = false; 
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
        
        getParent().validate();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Actions">
    
    /**
     * Register all the listeners of this class.
     */
    private void register_listeners()
    {        
        for(int i = 0; i < player.get_tokens().get_keys_all().length; i++)
        {
            images.get(i).addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e) 
                {
                    click_select_token(e);
                }
            });   
        }
    }
    
    /**
     * This function is called when a token is clicked.
     * Depending on the type of the click, it can increase, decrease or even clean the counter of the selected token.
     */
    private void click_select_token(MouseEvent e)
    {
        if(player.equals(model.get_game().get_current_player()))
        {                     
            int index = get_key(e.getSource());
            Integer[] value = player.get_tokens().get_values_all();
            System.out.println("Token Index: " + index);

            for(int i = 0; i < player.get_tokens().get_keys_all().length ; i++)
            {
                if(i == index)
                {
                    if(SwingUtilities.isLeftMouseButton(e))
                    {         
                        if(model.get_selected_token_index1() == index)
                        {
                            if(value[i] > model.get_selected_token_count1())
                            {
                                model.set_selected_token_count1(model.get_selected_token_count1() + 1);
                                selected.get(index).setText("Selecionado: " + model.get_selected_token_count1());
                                images.get(index).setBorder(new LineBorder(Color.RED, 2));
                            }           
                        }
                        else if(model.get_selected_token_index2() == index)
                        {
                            if(model.get_selected_token_count2() > 1)
                            {
                                model.set_selected_token_count2(model.get_selected_token_count2() - 1);
                                selected.get(index).setText("Selecionado: " + model.get_selected_token_count2());
                                images.get(index).setBorder(new LineBorder(Color.ORANGE, 2));
                            }              
                        }
                        else if(model.get_selected_token_index3() == index)
                        {
                            if(model.get_selected_token_count3() > 1)
                            {
                                model.set_selected_token_count3(model.get_selected_token_count3() - 1);
                                selected.get(index).setText("Selecionado: " + model.get_selected_token_count3());
                                images.get(index).setBorder(new LineBorder(Color.GREEN, 2));
                            }              
                        }
                        else
                        {
                            model.set_selected_token_index1(index);
                            model.set_selected_token_count1(0);
                            selected.get(index).setText("Selecionado");
                            images.get(index).setBorder(new LineBorder(Color.RED, 2));
                        }
                    }
                    else if(SwingUtilities.isRightMouseButton(e))
                    {        
                        if(model.get_selected_token_index1() == index)
                        {
                            if(model.get_selected_token_count1() > 1)
                            {
                                model.set_selected_token_count1(model.get_selected_token_count1() - 1);
                                selected.get(index).setText("Selecionado: " + model.get_selected_token_count1());
                                images.get(index).setBorder(new LineBorder(Color.RED, 2));
                            }
                        }
                        else if(model.get_selected_token_index2() == index)
                        {
                            if(value[i] > model.get_selected_token_count2())
                            {
                                model.set_selected_token_count2(model.get_selected_token_count2() + 1);
                                selected.get(index).setText("Selecionado: " + model.get_selected_token_count2());
                                images.get(index).setBorder(new LineBorder(Color.ORANGE, 2));
                            }
                        }
                        else if(model.get_selected_token_index3() == index)
                        {
                            if(model.get_selected_token_count3() > 1)
                            {
                                model.set_selected_token_count3(model.get_selected_token_count3() - 1);
                                selected.get(index).setText("Selecionado: " + model.get_selected_token_count3());
                                images.get(index).setBorder(new LineBorder(Color.GREEN, 2));
                            }              
                        }
                        else
                        {       
                            model.set_selected_token_index2(index);
                            model.set_selected_token_count2(0);
                            selected.get(index).setText("Selecionado");
                            images.get(index).setBorder(new LineBorder(Color.ORANGE, 2));
                        }
                    }
                    else if(SwingUtilities.isMiddleMouseButton(e))
                    {
                        if(model.get_selected_token_index1() == index)
                        {
                            if(model.get_selected_token_count1() > 1)
                            {
                                model.set_selected_token_count1(model.get_selected_token_count1() - 1);
                                selected.get(index).setText("Selecionado: " + model.get_selected_token_count1());
                                images.get(index).setBorder(new LineBorder(Color.RED, 2));
                            }
                        }
                        else if(model.get_selected_token_index2() == index)
                        {
                            if(model.get_selected_token_count2() > 1)
                            {
                                model.set_selected_token_count2(model.get_selected_token_count2() - 1);
                                selected.get(index).setText("Selecionado: " + model.get_selected_token_count2());
                                images.get(index).setBorder(new LineBorder(Color.ORANGE, 2));
                            }
                        }
                        else if(model.get_selected_token_index3() == index)
                        {
                            if(value[i] > model.get_selected_token_count3())
                            {
                                model.set_selected_token_count3(model.get_selected_token_count3() + 1);
                                selected.get(index).setText("Selecionado: " + model.get_selected_token_count3());
                                images.get(index).setBorder(new LineBorder(Color.GREEN, 2));
                            }              
                        }
                        else
                        {       
                            model.set_selected_token_index3(index);
                            model.set_selected_token_count3(0);
                            selected.get(index).setText("Selecionado");
                            images.get(index).setBorder(new LineBorder(Color.GREEN, 2));
                        }
                    }
                }
                else
                {
                    if(SwingUtilities.isLeftMouseButton(e))
                    {
                        if((model.get_selected_token_index2() != i && model.get_selected_token_index2() != index) &&
                           (model.get_selected_token_index3() != i && model.get_selected_token_index3() != index))
                        {
                            images.get(i).setBorder(new LineBorder(new Color(214,217,223), 2));
                            other.get_image(i).setBorder(new LineBorder(new Color(214,217,223), 2));
                            selected.get(i).setText("");
                            other.get_selected(i).setText("");
                        }
                    }
                    else if(SwingUtilities.isRightMouseButton(e))
                    {
                        if((model.get_selected_token_index1() != i && model.get_selected_token_index1() != index) &&
                           (model.get_selected_token_index3() != i && model.get_selected_token_index3() != index))
                        {
                            images.get(i).setBorder(new LineBorder(new Color(214,217,223), 2));
                            other.get_image(i).setBorder(new LineBorder(new Color(214,217,223), 2));
                            selected.get(i).setText("");
                            other.get_selected(i).setText("");
                        }
                    }
                    else if(SwingUtilities.isMiddleMouseButton(e))
                    {
                        if((model.get_selected_token_index1() != i && model.get_selected_token_index1() != index) &&
                           (model.get_selected_token_index2() != i && model.get_selected_token_index2() != index))
                        {
                            images.get(i).setBorder(new LineBorder(new Color(214,217,223), 2));
                            other.get_image(i).setBorder(new LineBorder(new Color(214,217,223), 2));
                            selected.get(i).setText("");
                            other.get_selected(i).setText("");
                        }
                    }
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
    
    /**
     * Gets a image of the current class.
     * This is used to share information between multiple instances of this class.
     * @param index the index of the image.
     * @return the JLable with the image.
     */
    public JLabel get_image(int index)
    {
        return images.get(index);
    }
    
    /**
     * Gets the selected value of the current class.
     * This is used to share information between multiple instances of this class.
     * @param index the index of the JLable.
     * @return the JLable.
     */
    public JLabel get_selected(int index)
    {
        return selected.get(index);
    }
 
    /**
     * Sets the other instance of this class.
     * This is used in the initialization, to link the instances of this class.
     * @param t the other instance of this class.
     */
    public void set_token(Tokens t)
    {
        other = t;
    }
    
    // </editor-fold>
    
}
