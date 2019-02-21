import java.util.List;
/**
 * A class representing shared characteristics of plant.
 * 
 * @author: YICHENG ZHAN and YE MAO;
 */
public abstract class Plant extends Organism
{
    private boolean eatable;
    private boolean toxic;
    // The animal's field.
    // The animal's position in the field.;
     /**
     * Create a new palnt at location in field.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Plant(Field field, Location location)
    {
        super(field,location);
        eatable = false;
        toxic = false;
        setLocation(location);
    }
    /**
     * check whether the grass can be eaten,
     * if true, the animals can eat them.
     * @return boolean eatable property.
     */
    protected boolean If_canEat()
    {
        return eatable;
    }
     /**
     * return the booean toxic property.
     * @ return the boolean toxic property.
     */
    protected boolean If_cantEat()
    {
        return toxic;
    }
    
    /**
     * return the field of a plant.
     * @return the field of a plant.
     */
    protected Field getField()
    {
        return field;
    }
     /**
     * let the plant be toxic.
     * @return true,the toxic property.
     */
    protected boolean cantEat()
    {
        toxic=true;
        return toxic;
    }
     /**
     * return the boolean eatable true.
     * @return the boolean eatable true.
     */
    protected boolean canEat()
    {
        return eatable= true;
    } 
    
    /**
     * as the weather is sunny, plants will show different behaviours.
     * implement this method in its subclass.
     * @param list<plant>newPlants. a list to receive the newly born plants.
     */
    abstract public void act_Sunny(List<Plant> newPlants);
    /**
     * Makes the grasses act in the rainy days,it means make it do.
     * whatever it wants/ need to do.
     * @param a list to receive the newly born plants.
     */
    abstract public void act_Rainy(List<Plant> newPlants);
    

}
