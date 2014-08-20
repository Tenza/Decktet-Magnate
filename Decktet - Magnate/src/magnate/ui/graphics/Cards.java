package magnate.ui.graphics;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * This class holds all the images with the cards of the game.
 * There are two Hashmaps that hold the images in horizontal and vertical positions.
 * The horizontal images are rotated on the fly.
 * @author Filipe
 * @version 1.0
 */
public class Cards 
{   
    private static Map<String, Image> images = new LinkedHashMap<>();
    private static Map<String, Image> images_rotated = new LinkedHashMap<>();
    
    public Cards()
    {
        load_images();
        load_images_rotated();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Gets">

    public Map<String, Image> get_images() 
    {
        return images;
    }
    
    public Image get_image(String name) 
    {
        return images.get(name);
    }
    
    public Map<String, Image> get_images_rotated() 
    {
        return images_rotated;
    }
    
    public Image get_image_rotated(String name) 
    {
        return images_rotated.get(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Load Cards">
    
    /**
     * Loads all the original images into the main array.
     */
    private void load_images()
    {
        images.put("Ace of Moons", create_image("Images/01.gif"));
        images.put("Ace of Suns", create_image("Images/02.gif"));
        images.put("Ace of Leaves", create_image("Images/03.gif"));
        images.put("Ace of Wyrms", create_image("Images/04.gif"));
        images.put("Ace of Waves", create_image("Images/05.gif"));
        images.put("Ace of Knots", create_image("Images/06.gif"));

        images.put("The Author", create_image("Images/07.gif"));
        images.put("The Origin", create_image("Images/08.gif"));
        images.put("The Desert", create_image("Images/09.gif"));

        images.put("The Savage", create_image("Images/10.gif"));
        images.put("The Painter", create_image("Images/11.gif"));
        images.put("The Journey", create_image("Images/12.gif"));

        images.put("The Battle", create_image("Images/13.gif"));
        images.put("The Mountain", create_image("Images/14.gif"));
        images.put("The Sailor", create_image("Images/15.gif"));
        
        images.put("The Soldier", create_image("Images/16.gif"));
        images.put("The Discovery", create_image("Images/17.gif"));
        images.put("The Forest", create_image("Images/18.gif"));

        images.put("The Penitent", create_image("Images/19.gif"));
        images.put("The Lunatic", create_image("Images/20.gif"));
        images.put("The Market", create_image("Images/21.gif"));
        
        images.put("The Chance Meeting", create_image("Images/22.gif"));
        images.put("The Castle", create_image("Images/23.gif"));
        images.put("The Cave", create_image("Images/24.gif"));

        images.put("The Betrayal", create_image("Images/25.gif"));
        images.put("The Mill", create_image("Images/26.gif"));
        images.put("The Diplomat", create_image("Images/27.gif"));

        images.put("The Pact", create_image("Images/28.gif"));
        images.put("The Merchant", create_image("Images/29.gif"));
        images.put("The Darkness", create_image("Images/30.gif"));
        
        //Courts
        images.put("The Consul", create_image("Images/42.gif"));
        images.put("The Window", create_image("Images/43.gif"));
        images.put("The Rite", create_image("Images/44.gif"));
        images.put("The Island", create_image("Images/45.gif"));
        
        //Pawns
        images.put("The Watchman", create_image("Images/37.gif"));
        images.put("The Harvest", create_image("Images/38.gif"));
        images.put("The Excuse", create_image("Images/39.gif"));
        images.put("The Borderland", create_image("Images/40.gif"));
        images.put("The Light Keeper", create_image("Images/41.gif"));
        
        //Crowns
        images.put("The Huntress", create_image("Images/31.gif"));
        images.put("The Bard", create_image("Images/32.gif"));
        images.put("The Windfall", create_image("Images/33.gif"));
        images.put("The End", create_image("Images/34.gif"));
        images.put("The Calamity", create_image("Images/35.gif"));
        images.put("The Sea", create_image("Images/36.gif"));       
     
        //Other
        images.put("Moons", create_image("Images/Tokens1.gif"));
        images.put("Suns", create_image("Images/Tokens2.gif"));
        images.put("Waves", create_image("Images/Tokens3.gif"));
        images.put("Leaves", create_image("Images/Tokens4.gif"));
        images.put("Wyrms", create_image("Images/Tokens5.gif"));
        images.put("Knots", create_image("Images/Tokens6.gif"));
        images.put("Card Back", create_image("Images/Back.gif"));
        images.put("Spacer", create_image("Images/Spacer.gif"));
    }
    
    /**
     * Loads all the images after rotation.
     * Some images (see Other above) are not in this array because they are not needed with the rotation applyed.
     */
    private void load_images_rotated()
    {
        images_rotated.put("Ace of Moons", create_image_rotated("Images/01.gif"));
        images_rotated.put("Ace of Suns", create_image_rotated("Images/02.gif"));
        images_rotated.put("Ace of Leaves", create_image_rotated("Images/03.gif"));
        images_rotated.put("Ace of Wyrms", create_image_rotated("Images/04.gif"));
        images_rotated.put("Ace of Waves", create_image_rotated("Images/05.gif"));
        images_rotated.put("Ace of Knots", create_image_rotated("Images/06.gif"));

        images_rotated.put("The Author", create_image_rotated("Images/07.gif"));
        images_rotated.put("The Origin", create_image_rotated("Images/08.gif"));
        images_rotated.put("The Desert", create_image_rotated("Images/09.gif"));

        images_rotated.put("The Savage", create_image_rotated("Images/10.gif"));
        images_rotated.put("The Painter", create_image_rotated("Images/11.gif"));
        images_rotated.put("The Journey", create_image_rotated("Images/12.gif"));

        images_rotated.put("The Battle", create_image_rotated("Images/13.gif"));
        images_rotated.put("The Mountain", create_image_rotated("Images/14.gif"));
        images_rotated.put("The Sailor", create_image_rotated("Images/15.gif"));
        
        images_rotated.put("The Soldier", create_image_rotated("Images/16.gif"));
        images_rotated.put("The Discovery", create_image_rotated("Images/17.gif"));
        images_rotated.put("The Forest", create_image_rotated("Images/18.gif"));

        images_rotated.put("The Penitent", create_image_rotated("Images/19.gif"));
        images_rotated.put("The Lunatic", create_image_rotated("Images/20.gif"));
        images_rotated.put("The Market", create_image_rotated("Images/21.gif"));
        
        images_rotated.put("The Chance Meeting", create_image_rotated("Images/22.gif"));
        images_rotated.put("The Castle", create_image_rotated("Images/23.gif"));
        images_rotated.put("The Cave", create_image_rotated("Images/24.gif"));

        images_rotated.put("The Betrayal", create_image_rotated("Images/25.gif"));
        images_rotated.put("The Mill", create_image_rotated("Images/26.gif"));
        images_rotated.put("The Diplomat", create_image_rotated("Images/27.gif"));

        images_rotated.put("The Pact", create_image_rotated("Images/28.gif"));
        images_rotated.put("The Merchant", create_image_rotated("Images/29.gif"));
        images_rotated.put("The Darkness", create_image_rotated("Images/30.gif"));
        
        //Courts
        images_rotated.put("The Consul", create_image_rotated("Images/42.gif"));
        images_rotated.put("The Window", create_image_rotated("Images/43.gif"));
        images_rotated.put("The Rite", create_image_rotated("Images/44.gif"));
        images_rotated.put("The Island", create_image_rotated("Images/45.gif"));
        
        //Pawns
        images_rotated.put("The Watchman", create_image_rotated("Images/37.gif"));
        images_rotated.put("The Harvest", create_image_rotated("Images/38.gif"));
        images_rotated.put("The Excuse", create_image_rotated("Images/39.gif"));
        images_rotated.put("The Borderland", create_image_rotated("Images/40.gif"));
        images_rotated.put("The Light Keeper", create_image_rotated("Images/41.gif"));
        
        //Crowns
        images_rotated.put("The Huntress", create_image_rotated("Images/31.gif"));
        images_rotated.put("The Bard", create_image_rotated("Images/32.gif"));
        images_rotated.put("The Windfall", create_image_rotated("Images/33.gif"));
        images_rotated.put("The End", create_image_rotated("Images/34.gif"));
        images_rotated.put("The Calamity", create_image_rotated("Images/35.gif"));
        images_rotated.put("The Sea", create_image_rotated("Images/36.gif"));    
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Load Functions">
    
    /**
     * Loads a single image from a physical path.
     * @param path the location of the image.
     * @return the image loaded in memory.
     */
    private Image create_image(String path) 
    {
        BufferedImage image = null;
        
        try 
        {
            image = ImageIO.read(getClass().getClassLoader().getResource(path));
        } 
        catch (IOException e) 
        {
            System.out.println("Erro ao carregar a Imagem: " + e);
        }
        
        return image;
    }
    
    /**
     * Loads and rotates a single image from a physical path.
     * @param path the location of the image.
     * @return the image rotated loaded in memory.
     */
    private Image create_image_rotated(String path) 
    {
        BufferedImage image = null;
               
        try 
        {
            image = ImageIO.read(getClass().getClassLoader().getResource(path));
            
            AffineTransform transform = new AffineTransform();
            transform.translate(image.getHeight() / 2,image.getWidth() / 2);
            transform.rotate(Math.PI / 2);
            transform.translate(-image.getWidth() / 2,-image.getHeight() / 2);
            
            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            image = op.filter(image, null);
        } 
        catch (IOException e) 
        {
            System.out.println("Erro ao carregar a Imagem: " + e);
        }
        
        return image;
    }
 
    // </editor-fold>
    
}
