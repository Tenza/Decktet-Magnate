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

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import magnate.logic.Game;

/**
 * This class represents the setup window.
 * The name of the players and the type of the game is set here.
 * @author Filipe
 * @version 1.0
 */
public class Setup extends JFrame
{    
    private JButton btn_start;
    private JComboBox cb_tipo;
    private JLabel lbl_player1;
    private JLabel lbl_player2;
    private JLabel lbl_tipo;
    private JLabel lbl_title;
    private JTextField txt_player1;
    private JTextField txt_player2;
    
    public Setup()
    {
        create_view();
        build_view();
        display_view();
        register_listeners(); 
        set_window();
    }
    
    // <editor-fold defaultstate="collapsed" desc="View">
    
    /**
     * Creates all the components.
     */
    private void create_view()
    { 
        txt_player1 = new JTextField();
        txt_player2 = new JTextField();
        cb_tipo = new JComboBox();
        btn_start = new JButton();
        lbl_tipo = new JLabel();
        lbl_player1 = new JLabel();
        lbl_player2 = new JLabel();
        lbl_title = new JLabel();
    }
    
    /**
     * Builds and sets all components.
     */
    @SuppressWarnings("unchecked")
    private void build_view()
    {
        lbl_player1.setText("Jogador 1:");
        txt_player1.setText("Jogador 1");
        lbl_player2.setText("Jogador 2:");
        txt_player2.setText("Jogador 2");
        lbl_tipo.setText("Tipo de Jogo:");
        lbl_title.setFont(new java.awt.Font("Tahoma", 0, 18));
        lbl_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title.setText("Decktet Magnata");
        btn_start.setText("Iniciar Jogo");
        cb_tipo.setModel(new DefaultComboBoxModel(new String[] { "Jogador VS Jogador", "Jogador VS IA", "IA VS IA", "Carregar Jogo" }));
    }
    
    /**
     * Displays all the components.
     */
    private void display_view()
    {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_tipo)
                    .addComponent(lbl_player2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_player1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_player1)
                    .addComponent(txt_player2)
                    .addComponent(cb_tipo, 0, 220, Short.MAX_VALUE)
                    .addComponent(btn_start, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(62, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_title)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_tipo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_player1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_player1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_player2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_player2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_start)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        
        pack();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Actions">
    
    /**
     * Register all the listeners of this class.
     */
    private void register_listeners()
    {
        cb_tipo.addActionListener(new ActionListener()
        {
            @Override public void actionPerformed(ActionEvent e)
            {
                click_cb_tipo(e);
            }
        });

        
        btn_start.addActionListener(new ActionListener()
        {
            @Override public void actionPerformed(ActionEvent e)
            {
                click_btn_start(e);
            }
        });
    }
    
    /**
     * Sets the player name based on the type of the game chosen.
     * @param e ActionEvent
     */
    private void click_cb_tipo(ActionEvent e) 
    {
        if(cb_tipo.getSelectedIndex() == 0)
        {
            txt_player1.setEnabled(true);
            txt_player1.setText("Jogador 1");
            
            txt_player2.setEnabled(true);
            txt_player2.setText("Jogador 2");
        }
        if(cb_tipo.getSelectedIndex() == 1)
        {
            txt_player1.setEnabled(true);
            txt_player1.setText("Jogador");
            
            txt_player2.setEnabled(false);
            txt_player2.setText("Computador");
        }
        if(cb_tipo.getSelectedIndex() == 2)
        {
            txt_player1.setEnabled(false);
            txt_player1.setText("Computador 1");
            
            txt_player2.setEnabled(false);
            txt_player2.setText("Computador 2");
        }
        if(cb_tipo.getSelectedIndex() == 3)
        {
            txt_player1.setEnabled(false);
            txt_player1.setText("Jogador 1");
            
            txt_player2.setEnabled(false);
            txt_player2.setText("Jogador 2");
        }
    } 
    
    /**
     * Creates the Main window if the names are valid.
     * This window is disposed.
     * @param e 
     */
    private void click_btn_start(ActionEvent e) 
    {             
        if(!txt_player1.getText().equals("") && !txt_player2.getText().equals("") && !txt_player1.getText().equals(txt_player2.getText()))
        {        
            GameModel model = new GameModel(new Game());
            Window ui = new Window(model, cb_tipo.getSelectedIndex(), txt_player1.getText(), txt_player2.getText());
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Functions">
    
    /**
     * Sets all the properties of this window.
     */
    private void set_window()
    {
        setSize(400, 240); 
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Decktet - Magnate");  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("Images/icon.png")));
        setVisible(true);
    }
    
    // </editor-fold>  
    
}
