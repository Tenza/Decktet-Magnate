package magnate.ui.graphics;

import magnate.logic.PlayCard;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import magnate.logic.Card;

/**
 * This class contains all the information regarding the game table.
 * The main JPanel is set to BorderLayout so that the game is always centered.
 * Inside the main panel is a JScrollPane so that the scrolling works in all table.
 * Inside the JScrollPane is another JPanel with a GridBagLayout divided in 3 parts.
 * Each of this 3 parts is JPanel set to GridBagLayout that will hold JLabel with the cards.
 * The first contains the player1 cards, the secound contains the district cards and the third the player 2 cards.
 * The district cards also have to be repainted because of the zoom functionality.
 * @author Filipe
 * @version 1.0
 */
public class Table extends JPanel implements Parameters, Observer
{
    private GameModel model;
    private Cards cards;

    private JPanel table;
    private JPanel districts;
    private JPanel table_player1;
    private JPanel table_player2;
    private JScrollPane table_scroll;
    private Map<Integer, JLabel> images_district;
    private Map<Integer, JLabel> images_table_player1;
    private Map<Integer, JLabel> images_table_player2;
    
    private int table_cards_X;
    private int table_cards_Y;
    private int table_cards_rotated_X;
    private int table_cards_rotated_Y;
    private int table_zoom_increment;
    
    public Table(GameModel m, Cards c) 
    {
        model = m;
        cards = c;
                
        create_view();
        build_view();
        display_view();
        register_listeners();
        register_observers();
        register_scroll();
    }   
    
    // <editor-fold defaultstate="collapsed" desc="View">
    
    /**
     * Creates the arrays and panels to use.
     */
    private void create_view()
    {                 
        table_cards_X = 100;
        table_cards_Y = 140;
        table_cards_rotated_X = 140;
        table_cards_rotated_Y = 100;
        table_zoom_increment = 10;
        
        images_district = new LinkedHashMap<>();
        images_table_player1 = new LinkedHashMap<>();
        images_table_player2 = new LinkedHashMap<>();
           
        setLayout(new BorderLayout());
        setBorder(new TitledBorder(BorderFactory.createTitledBorder("")));      
        
        table = new JPanel();
        districts = new JPanel();
        table_player1 = new JPanel();
        table_player2 = new JPanel();
        table_scroll = new JScrollPane(table);
        
        table.setLayout(new GridBagLayout()); 
        districts.setLayout(new GridBagLayout()); 
        table_player1.setLayout(new GridBagLayout());
        table_player2.setLayout(new GridBagLayout()); 
        
        table_scroll.setBorder(new LineBorder(Color.BLACK, 0));
        table_scroll.getVerticalScrollBar().setUnitIncrement(16); 
        
//        //Shows the outlining of all the panels
//        setBorder(new LineBorder(Color.MAGENTA, 1));
//        table_scroll.setBorder(new LineBorder(Color.CYAN, 1));
//        table.setBorder(new LineBorder(Color.WHITE, 1));
//        table_player1.setBorder(new LineBorder(Color.RED, 1));
//        districts.setBorder(new LineBorder(Color.GREEN, 1));
//        table_player2.setBorder(new LineBorder(Color.BLUE, 1));
    }
    
    /**
     * Calls all the build functions.
     */
    private void build_view()
    {
        build_table_player1();
        build_districts();
        build_table_player2();
    }
    
    /**
     * Calls all the display functions, and displays the result.
     */
    private void display_view()
    {              
        display_table_player1();
        display_districts();
        display_table_player2();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        table.add(table_player1, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        table.add(districts, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        table.add(table_player2, gbc);
        
        //The panels cannot be removed from table_scroll.
        //This is the only way to update de content.
        table_scroll.repaint(); 
        
        add(table_scroll, BorderLayout.CENTER);
    }
    
    /**
     * Register all the listeners of this class.
     */
    private void register_listeners()
    {
        register_districts();
        register_table_player1();
        register_table_player2();
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
    
    // <editor-fold defaultstate="collapsed" desc="Districts">
    
    /**
     * Builds the hashmap with the image of the district and the index.
     */
    private void build_districts()
    {
        images_district.clear();
        
        Card c;
        JLabel card;
        String card_info;
        List<PlayCard> dist = model.get_game().get_districts();
        
        for(int i = 0; i < dist.size() ; i++)
        {          
            c = dist.get(i);
            ImageIcon image = new ImageIcon(cards.get_image_rotated(c.get_name()).getScaledInstance(table_cards_rotated_X, table_cards_rotated_Y, Image.SCALE_SMOOTH));
            card = new JLabel(image);
            
            card_info = "<html><b>Carta de Distrito<br />Nome: </b>" + c.get_name() + "<br /><b>Naipes: </b>";
            for (String s : c.get_suits())
            {
                card_info += s + " ";
            }       
            card_info  += "</html>";
            
            card.setToolTipText(card_info);
            images_district.put(i, card);
        }
    }
    
    /**
     * Displays the content of the hashmap.
     * Attention: the inserts cannot be changed unless, all the others are also ajusted.
     */
    private void display_districts()
    {
        districts.removeAll();
        List<PlayCard> dist = model.get_game().get_districts();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        
        for(int i = 0; i < dist.size() ; i++)
        {
            if(i != dist.size() - 1)
            {
                gbc.insets = new Insets(0, 0, 0, 60);
            }
            else
            {
                gbc.insets = new Insets(0, 0, 0, 0);
            }
            
            gbc.gridx = i;
            gbc.gridy = 0;
            images_district.get(i).setBorder(new LineBorder(Color.GREEN, 2));
            districts.add(images_district.get(i), gbc);
        }
    }
    
    /**
     * Register the listeners of the district cards.
     */
    private void register_districts()
    {        
        for(int i = 0; i < model.get_game().get_districts().size() ; i++)
        {            
            images_district.get(i).addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e) 
                {
                    click_select_district(e);
                }
            });
        }
    }
    
    /**
     * This function is called when a district card is clicked.
     * When clicked it is set a variable in the model with the card clicked.
     */
    private void click_select_district(MouseEvent e)
    {
        int index = get_key(images_district, e.getSource());
        System.out.println("District Card Index: " + index);
        for(int i = 0; i < model.get_game().get_districts().size() ; i++)
        {
            if(i == index)
            {
                images_district.get(i).setBorder(new LineBorder(Color.RED, 2));
                model.set_selected_district(i + 1);
            }
            else
            {
                images_district.get(i).setBorder(new LineBorder(Color.GREEN, 2));
            }
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Player 1">
    
    /**
     * Builds the hashmap with the image of the cards played and the index.
     * Also adds information about the card in the tooltip.
     */
    private void build_table_player1()
    {
        int count = 0;
        images_table_player1.clear();
        
        PlayCard c;
        JLabel card;
        String card_info;
        List<List<PlayCard>> t1 = model.get_game().get_player1().get_player_table();
               
        for(int i = 0; i < t1.size(); i++)
        {
            for(int j = 0; j < t1.get(i).size(); j++)
            {
                c = t1.get(i).get(j);
                
                if(!c.get_constructed())
                {
                    ImageIcon image = new ImageIcon(cards.get_image(c.get_name()).getScaledInstance(table_cards_X, table_cards_Y, Image.SCALE_SMOOTH));
                    card = new JLabel(image);
                    
                    card_info = "<html><b>Carta em Construção<br />Valor: </b>" + c.get_rank() + "<br /><b>Nome: </b>" + c.get_name() + "<br /><b>Naipes: </b>";
                    for (String s : c.get_suits())
                    {
                        card_info += s + " (" + c.get_tokens().get_value(s) + ") ";
                    }    
                    card_info  += "</html>";

                    card.setToolTipText(card_info);
                }
                else
                {
                    ImageIcon image = new ImageIcon(cards.get_image_rotated(c.get_name()).getScaledInstance(table_cards_rotated_X, table_cards_rotated_Y, Image.SCALE_SMOOTH));
                    card = new JLabel(image);
                    
                    card_info = "<html><b>Carta Construida<br />Valor: </b>" + c.get_rank() + "<br /><b>Nome: </b>" + c.get_name() + "<br /><b>Naipes: </b>";
                    for (String s : c.get_suits())
                    {
                        card_info += s + " ";
                    }       
                    card_info  += "</html>";

                    card.setToolTipText(card_info);
                }
                
                images_table_player1.put(count, card);
                count++;
            }
        }
    }
    
    /**
     * Displays the content of the hashmap.
     * Attention: the inserts cannot be changed unless, all the others are also ajusted.
     */
    private void display_table_player1()
    {
        int height = 0;
        int count = 0;
        table_player1.removeAll();
        List<List<PlayCard>> t1 = model.get_game().get_player1().get_player_table();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        
        //Find the highest column to invert the order
        for(int i = 0; i < t1.size(); i++)
        {
            if(t1.get(i).size() > height)
            {
                height = t1.get(i).size();
            }
        }
        
        for(int i = 0; i < t1.size(); i++)
        {
            for(int j = 0; j < t1.get(i).size(); j++)
            {  
                if(i != t1.size() - 1)
                {
                    if(!t1.get(i).get(j).get_constructed())
                    {
                        gbc.insets = new Insets(0, 20, 2, 80);
                    }
                    else
                    {
                        gbc.insets = new Insets(0, 0, 2, 60);
                    }
                }
                else
                {
                    if(!t1.get(i).get(j).get_constructed())
                    {
                        gbc.insets = new Insets(0, 20, 2, 20);
                    }
                    else
                    {
                        gbc.insets = new Insets(0, 0, 2, 0);
                    }
                }

                gbc.gridx = i;
                gbc.gridy = height - j;

                images_table_player1.get(count).setBorder(new LineBorder(new Color(214,217,223), 1));
                table_player1.add(images_table_player1.get(count), gbc);
                count++;
            }
            
            if(t1.get(i).isEmpty())
            {
                ImageIcon image = new ImageIcon(cards.get_image("Spacer").getScaledInstance(table_cards_rotated_X + 4, table_cards_rotated_Y, Image.SCALE_SMOOTH));
                JLabel spacer = new JLabel(image);

                if(i != t1.size() - 1)
                {
                    gbc.insets = new Insets(0, 0, 2, 60);
                }
                else
                {
                    gbc.insets = new Insets(0, 0, 2, 0);
                }
                
                gbc.gridx = i;
                gbc.gridy = 1;
                
                table_player1.add(spacer, gbc);
            }   
        }
    }
    
    /**
     * Register the listeners of the cards played.
     */
    private void register_table_player1()
    {        
        int count = 0;
        List<List<PlayCard>> t1 = model.get_game().get_player1().get_player_table();
        
        for(int i = 0; i < t1.size(); i++)
        {
            for(int j = 0; j < t1.get(i).size(); j++)
            {          
                images_table_player1.get(count).addMouseListener(new MouseAdapter() 
                {
                    @Override
                    public void mousePressed(MouseEvent e) 
                    {
                        click_select_card_player1(e);
                    }
                });
                
                count++;
            } 
        }
    }
    
    /**
     * This function is called when a play card is clicked.
     * When clicked it is set a variable in the model with the card clicked.
     */
    private void click_select_card_player1(MouseEvent e)
    {
        if(model.get_game().get_current_player().equals(model.get_game().get_player1()))
        {        
            int count = 0;
            List<List<PlayCard>> t1 = model.get_game().get_player1().get_player_table();
            int index = get_key(images_table_player1, e.getSource());

            System.out.println("PlayCard Index: " + index);
            for(int i = 0; i < t1.size(); i++)
            {
                for(int j = 0; j < t1.get(i).size(); j++)
                {
                    if(count == index)
                    {
                        images_table_player1.get(count).setBorder(new LineBorder(Color.RED, 1));
                        model.set_selected_playcard(index + 1);
                        model.set_selected_playcard_district(i + 1);
                    }
                    else
                    {
                        images_table_player1.get(count).setBorder(new LineBorder(new Color(214,217,223), 1));
                    }

                    count++;
                }
            }
        }
    }
    
    // </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="Player 2">
    
    /**
     * Builds the hashmap with the image of the cards played and the index.
     * Also adds information about the card in the tooltip.
     */
    private void build_table_player2()
    {
        int count = 0;
        images_table_player2.clear();
        
        PlayCard c;
        JLabel card;
        String card_info;
        List<List<PlayCard>> t1 = model.get_game().get_player2().get_player_table();
               
        for(int i = 0; i < t1.size(); i++)
        {
            for(int j = 0; j < t1.get(i).size(); j++)
            {
                c = t1.get(i).get(j);
                
                if(!c.get_constructed())
                {
                    ImageIcon image = new ImageIcon(cards.get_image(c.get_name()).getScaledInstance(table_cards_X, table_cards_Y, Image.SCALE_SMOOTH));
                    card = new JLabel(image);
                    
                    card_info = "<html><b>Carta em Construção<br />Valor: </b>" + c.get_rank() + "<br /><b>Nome: </b>" + c.get_name() + "<br /><b>Naipes: </b>";
                    for (String s : c.get_suits())
                    {
                        card_info += s + " (" + c.get_tokens().get_value(s) + ") ";
                    }    
                    card_info  += "</html>";

                    card.setToolTipText(card_info);
                }
                else
                {
                    ImageIcon image = new ImageIcon(cards.get_image_rotated(c.get_name()).getScaledInstance(table_cards_rotated_X, table_cards_rotated_Y, Image.SCALE_SMOOTH));
                    card = new JLabel(image);
                    
                    card_info = "<html><b>Carta Construida<br />Valor: </b>" + c.get_rank() + "<br /><b>Nome: </b>" + c.get_name() + "<br /><b>Naipes: </b>";
                    for (String s : c.get_suits())
                    {
                        card_info += s + " ";
                    }       
                    card_info  += "</html>";

                    card.setToolTipText(card_info);
                }
                
                images_table_player2.put(count, card);
                count++;
            }
        }
    }
    
    /**
     * Displays the content of the hashmap.
     * Attention: the inserts cannot be changed unless, all the others are also ajusted.
     */
    private void display_table_player2()
    {
        int count = 0;
        table_player2.removeAll();
        List<List<PlayCard>> t1 = model.get_game().get_player2().get_player_table();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        
        for(int i = 0; i < t1.size(); i++)
        {
            for(int j = 0; j < t1.get(i).size(); j++)
            {  
                if(i != t1.size() - 1)
                {
                    if(!t1.get(i).get(j).get_constructed())
                    {
                        gbc.insets = new Insets(2, 20, 0, 80);
                    }
                    else
                    {
                        gbc.insets = new Insets(2, 0, 0, 60);
                    }
                }
                else
                {
                    if(!t1.get(i).get(j).get_constructed())
                    {
                        gbc.insets = new Insets(2, 20, 0, 20);
                    }
                    else
                    {
                        gbc.insets = new Insets(2, 0, 0, 0);
                    }
                }

                gbc.gridx = i;
                gbc.gridy = j;

                images_table_player2.get(count).setBorder(new LineBorder(new Color(214,217,223), 1));
                table_player2.add(images_table_player2.get(count), gbc);
                count++;
            }
            
            if(t1.get(i).isEmpty())
            {
                ImageIcon image = new ImageIcon(cards.get_image("Spacer").getScaledInstance(table_cards_rotated_X + 4, table_cards_rotated_Y, Image.SCALE_SMOOTH));
                JLabel spacer = new JLabel(image);
                
                if(i != t1.size() - 1)
                {
                    gbc.insets = new Insets(2, 0, 0, 60);
                }
                else
                {
                    gbc.insets = new Insets(2, 0, 0, 0);
                }
                
                gbc.gridx = i;
                gbc.gridy = 1;
                
                table_player2.add(spacer, gbc);
            }
        }
    }
    
    /**
     * Register the listeners of the cards played.
     */
    private void register_table_player2()
    {        
        int count = 0;
        List<List<PlayCard>> t1 = model.get_game().get_player2().get_player_table();
        
        for(int i = 0; i < t1.size(); i++)
        {
            for(int j = 0; j < t1.get(i).size(); j++)
            {                
                images_table_player2.get(count).addMouseListener(new MouseAdapter() 
                {
                    @Override
                    public void mousePressed(MouseEvent e) 
                    {
                        click_select_card_player2(e);
                    }
                });
                
                count++;
            } 
        }
    }
    
    /**
     * This function is called when a play card is clicked.
     * When clicked it is set a variable in the model with the card clicked.
     */
    private void click_select_card_player2(MouseEvent e)
    {
        if(model.get_game().get_current_player().equals(model.get_game().get_player2()))
        {
            int count = 0;
            List<List<PlayCard>> t1 = model.get_game().get_player2().get_player_table();
            int index = get_key(images_table_player2, e.getSource());

            System.out.println("PlayCard Index: " + index);
            for(int i = 0; i < t1.size(); i++)
            {
                for(int j = 0; j < t1.get(i).size(); j++)
                {
                    if(count == index)
                    {
                        images_table_player2.get(count).setBorder(new LineBorder(Color.RED, 1));
                        model.set_selected_playcard(index + 1);
                        model.set_selected_playcard_district(i + 1);
                    }
                    else
                    {
                        images_table_player2.get(count).setBorder(new LineBorder(new Color(214,217,223), 1));
                    }

                    count++;
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
    public int get_key(Map<Integer, JLabel> images, Object value)
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
     * This functions blocks the scroll when CTRL is pressed, and applyes zoom to the table.
     */
    private void register_scroll()
    {
        table_scroll.removeMouseWheelListener(table_scroll.getMouseWheelListeners()[0]);
        
        table_scroll.addMouseWheelListener(new MouseWheelListener() {
        @Override public void mouseWheelMoved(MouseWheelEvent e) {
            if(e.isControlDown()) 
            {
                if(e.getWheelRotation() < 0) 
                {
                    // Zoom +
                    if(table_cards_X < 240)
                    {
                        table_cards_X += table_zoom_increment;
                        table_cards_Y += table_zoom_increment;
                        table_cards_rotated_X += table_zoom_increment;
                        table_cards_rotated_Y += table_zoom_increment;
                        
                        build_view();
                        display_view();
                        register_listeners();

                        getParent().validate();
                    }
                }
                else 
                {
                    // Zoom -
                    if(table_cards_X > 100)
                    {
                        table_cards_X -= table_zoom_increment;
                        table_cards_Y -= table_zoom_increment;
                        table_cards_rotated_X -= table_zoom_increment;
                        table_cards_rotated_Y -= table_zoom_increment;
                        
                        build_view();
                        display_view();
                        register_listeners();

                        getParent().validate();
                    }   
                }
            }
            else if (e.isShiftDown())
            {
                // Horizontal scrolling
                Adjustable adj = table_scroll.getHorizontalScrollBar();
                int scroll = e.getUnitsToScroll() * adj.getBlockIncrement();
                adj.setValue(adj.getValue() + scroll);
            }
            else
            {
                // Vertical scrolling
                Adjustable adj = table_scroll.getVerticalScrollBar();
                int scroll = e.getUnitsToScroll() * adj.getBlockIncrement();
                adj.setValue(adj.getValue() + scroll);
            }
        }});    
    }
    
//      /**
//     * Convenience method that returns a scaled instance of the
//     * provided {@code BufferedImage}.
//     *
//     * @param img the original image to be scaled
//     * @param targetWidth the desired width of the scaled instance,
//     *    in pixels
//     * @param targetHeight the desired height of the scaled instance,
//     *    in pixels
//     * @param hint one of the rendering hints that corresponds to
//     *    {@code RenderingHints.KEY_INTERPOLATION} (e.g.
//     *    {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
//     *    {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
//     *    {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
//     * @param higherQuality if true, this method will use a multi-step
//     *    scaling technique that provides higher quality than the usual
//     *    one-step technique (only useful in downscaling cases, where
//     *    {@code targetWidth} or {@code targetHeight} is
//     *    smaller than the original dimensions, and generally only when
//     *    the {@code BILINEAR} hint is specified)
//     * @return a scaled version of the original {@code BufferedImage}
//     */
//    public BufferedImage getScaledInstance(BufferedImage img,
//                                           int targetWidth,
//                                           int targetHeight,
//                                           Object hint,
//                                           boolean higherQuality)
//    {
//        int type = (img.getTransparency() == Transparency.OPAQUE) ?
//            BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
//        BufferedImage ret = (BufferedImage)img;
//        int w, h;
//        if (higherQuality) {
//            // Use multi-step technique: start with original size, then
//            // scale down in multiple passes with drawImage()
//            // until the target size is reached
//            w = img.getWidth();
//            h = img.getHeight();
//        } else {
//            // Use one-step technique: scale directly from original
//            // size to target size with a single drawImage() call
//            w = targetWidth;
//            h = targetHeight;
//        }
//        
//        do {
//            if (higherQuality && w > targetWidth) {
//                w /= 2;
//                if (w < targetWidth) {
//                    w = targetWidth;
//                }
//            }
//
//            if (higherQuality && h > targetHeight) {
//                h /= 2;
//                if (h < targetHeight) {
//                    h = targetHeight;
//                }
//            }
//
//            BufferedImage tmp = new BufferedImage(w, h, type);
//            Graphics2D g2 = tmp.createGraphics();
//            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
//            g2.drawImage(ret, 0, 0, w, h, null);
//            g2.dispose();
//
//            ret = tmp;
//        } while (w != targetWidth || h != targetHeight);
//
//        return ret;
//    }
    
    // </editor-fold>
    
}
