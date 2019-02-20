import java.util.*;
/**
 * A class representing shared characteristics of all creatures.
 * 
 * @author: YICHENG ZHAN and YE MAO;
 */
public abstract class Organism
{
    protected Field field;
    private int age;
    private boolean alive;
    protected Location location;
    /**
     * Create a new creature at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Organism(Field field,Location location)
    {
        this.field = field;
        this.location = location;
        alive =true;
        
    }
    
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }
    /**
     * set the age of a single animal.
     */
     protected void setAge(int age)
    {
        this.age= age; 
    }   
     /**
     * check the age of a single animal.
     * @return the age of the animal.
     */
        protected int getAge()
    {
        return age;
    }  
      /**
     * to help the animal to set a new location. 
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.placePla(this, newLocation);
    }
     /**
     * if the animal is not alive, the animal will die.
     * set the alive to false.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    /**
     * get the current field of the animal.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
}
