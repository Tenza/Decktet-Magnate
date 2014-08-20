package magnate.logic;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * This class reprensents a set of the diferent types of suits/tokens.
 * Is used in cards, player tokens and deeds.
 * @author Filipe
 * @version 1.0
 */
public class Token implements Serializable
{   
    private Map<String, Integer> tokens = new LinkedHashMap<>();
    
    public Token()
    {
        set_map();
    }
    
    public Token(String values[])
    {
        set_map();
        for(int i = 0; i < values.length; i++)
        {
            tokens.put(values[i], tokens.get(values[i]) + 1);
        }
    }
    
    public Token(Integer values[])
    {
        set_map();
        int i = 0;
        for(Map.Entry<String, Integer> entry : tokens.entrySet()) 
        {
            entry.setValue(values[i]);
            i++;
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Gets">
    
    /**
     * This method gets all the keys USED in the set.
     * (Usefull for card suits.)
     * (This return can be used as initialization or set.)
     * @return array with all the keys.
     */
    public String[] get_keys()
    {                
        List<String> keys = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : tokens.entrySet()) 
        {
            if (entry.getValue() != 0) 
            {
                keys.add(entry.getKey());
            }          
        }
        
        return keys.toArray(new String[keys.size()]);
    }
    
    /**
     * This method gets all the keys in the set.
     * @return array with all the keys.
     */
    public String[] get_keys_all()
    {             
        return tokens.keySet().toArray(new String[tokens.size()]);
    }
    
    /**
     * Gets the value of the token.
     * @param k token key.
     * @return the token value.
     */
    public int get_value(String k)
    {
        return tokens.get(k);
    }
    
    /**
     * This method gets all the values USED in the set.
     * (Usefull for card suits.)
     * @return array with all the keys.
     */
    public Integer[] get_values()
    {        
        List<Integer> values = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : tokens.entrySet()) 
        {
            if (entry.getValue() != 0) 
            {
                values.add(entry.getValue());
            }
        }
        
        return values.toArray(new Integer[values.size()]);
    }
    
    /**
     * This method gets all the values in the set.
     * (This return can be used as initialization or set.)
     * @return array with all the values.
     */
    public Integer[] get_values_all()
    {        
        List<Integer> values = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : tokens.entrySet()) 
        {
            values.add(entry.getValue());
        }
        
        return values.toArray(new Integer[values.size()]);
    }
    
    /**
     * Verifys the existence of a USED key in the set. 
     * (Usefull for card suits.)
     * @param k token key.
     * @return a boolean result.
     */
    public boolean contains_key(String k)
    {
        boolean result = false;
        for(Map.Entry<String, Integer> entry : tokens.entrySet()) 
        {
            if(entry.getKey().equals(k) && (entry.getValue() != 0)) 
            {
               result = true;
               break;
            }
        }
       
        return result;
    }
    
    /**
     * This method converts a given token number to its name.
     * @param i the number of the token.
     * @return the name of the token.
     */
    public String get_name(int i)
    {
        String result = "";
        if(i == 1)
        {
            result = "Moons";
        }
        else if(i == 2)
        {
            result = "Suns";
        }
        else if(i == 3)
        {
            result = "Waves";
        }
        else if(i == 4)
        {
            result = "Leaves";
        }
        else if(i == 5)
        {
            result = "Wyrms";
        }
        else if(i == 6)
        {
            result = "Knots";
        }     
        return result;
    }
    
    /**
     * This method converts a given token name to its number.
     * @param t the name of the token.
     * @return the number of the token.
     */
    public int get_number(String t)
    {
        int result = 0;
        if(t.equals("Moons"))
        {
            result = 1;
        }
        if(t.equals("Suns"))
        {
            result = 2;
        }
        if(t.equals("Waves"))
        {
            result = 3;
        }
        if(t.equals("Leaves"))
        {
            result = 4;
        }
        if(t.equals("Wyrms"))
        {
            result = 5;
        }
        if(t.equals("Knots"))
        {
            result = 6;
        }     
        return result;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Sets">
    
    /**
     * Sets default keys and values on the map.
     */
    private void set_map()
    {
        tokens.put("Moons", 0);
        tokens.put("Suns", 0);
        tokens.put("Waves", 0);
        tokens.put("Leaves", 0);
        tokens.put("Wyrms", 0);
        tokens.put("Knots", 0);
    }
    
    /**
     * Set the token k to v.
     * @param k token key.
     * @param v token value.
     */
    protected void set_value(String k, int v)
    {
        tokens.put(k, v);
    }
    
    /**
     * Removes from k, v values.
     * @param k token key.
     * @param v token value.
     */
    protected void remove_value(String k, int v)
    {
        tokens.put(k, tokens.get(k) - v);
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Adds">
    
    /**
     * This method adds a list of values to the values on map.
     * @param values the array with values to be added. 
     */
    protected void add_values(String values[])
    {
        for(int i = 0; i < values.length; i++)
        {
            tokens.put(values[i], tokens.get(values[i]) + 1);
        }
    }
    
    /**
     * This method adds a single value to the value on map.
     * @param value the value to be added. 
     */
    protected void add_value(String value)
    {
        tokens.put(value, tokens.get(value) + 1);
    }
    
    protected void add_value(String k, int v)
    {
        tokens.put(k, tokens.get(k) + v);
    }
    
    /**
     * This method adds a list of values to the values on map (in order).
     * @param values the array with values to be added. 
     */
    protected void add_values(Integer values[])
    {
        int i = 0;
        for(Map.Entry<String, Integer> entry : tokens.entrySet()) 
        {
            entry.setValue(entry.getValue() + values[i]);
            i++;
        }
    }
    
    // </editor-fold>

}
