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
