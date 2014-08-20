package magnate.logic;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class defines methods to deal with files.
 * @author Filipe
 * @version 1.0
 */
public class Files 
{

    // <editor-fold defaultstate="collapsed" desc="IO Functions">
    
    /**
     * Reads the object from the file.
     * @param name the name of the file.
     * @return the object.
     * @throws IOException if name dont exist.
     */
    public static ObjectInputStream file_read(String name) throws IOException 
    {
        File f = new File(name);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
        
        return in;
    }

    /**
     * Writes an object to a file.
     * @param name name of the file.
     * @param new_file overwrits the file.
     * @param append append data.
     * @return the object.
     * @throws IOException if file already exists.
     */
    public static ObjectOutputStream file_write(String name, boolean new_file, boolean append) throws IOException 
    {
        File f = new File(name);
        if (new_file && f.isFile()) 
        {
            throw new IOException("Ficheiro existente");
        }
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f, append));
        
        return out;
    }
    
    // </editor-fold>
    
}
