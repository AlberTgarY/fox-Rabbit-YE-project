import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat Wolfs, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Wolf  extends Animal
{
    // Characteristics shared by all foxes (class variables).

    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 20;
    // The age to which a fox can live.
    private static final int MAX_AGE = 200;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.9;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;
    // The food value of a single Wolf. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_COW_FOOD_VALUE = 40;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The fox's age.
    private int sexProbablity;

    // Individual characteristics (instance fields).
    // The Wolf's age.

    // The fox's food level, which is increased by eating Wolfs.
    private int foodLevel;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Wolf(boolean randomAge, Field field, Location location,String yearStage,String sex,boolean ill)
    {
        super(field, location,yearStage,sex,ill);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(RABBIT_COW_FOOD_VALUE);
        }
        else {
            setAge(0);
            foodLevel = RABBIT_COW_FOOD_VALUE;
        }
    }

    /**
     * This is what the fox does most of the time: it hunts for
     * Wolfs. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newFoxes A list to return newly born foxes.
     */
    public void act(List<Animal> newWolfs)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newWolfs);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Increase the age. This could result in the fox's death.
     */
    protected void incrementAge()
    {
        super.incrementAge();
        if(getAge() > MAX_AGE) {
            setDead();
        }
    }

    public void act_night(List<Animal> newWolfs)
    {
        incrementAge();
        incrementHunger();
    }

    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for Wolfs adjacent to the current location.
     * Only the first live Wolf is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit  rabbit= (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    if(rabbit.If_getIll())
                    {
                    foodLevel+=10;
                    getIll();
                }
                else
                {
                    foodLevel=RABBIT_COW_FOOD_VALUE/2;
                }
                return where;
            }
            }
            else if(animal instanceof Cow) {
                Cow cow = (Cow) animal;
                if(cow.isAlive()) { 
                    cow.setDead();
                    if(cow.If_getIll())
                    {
                    foodLevel = RABBIT_COW_FOOD_VALUE/2;
                    getIll();
                    return where;
                }
                else
                {
                    foodLevel=RABBIT_COW_FOOD_VALUE;
                    return where;
                }
                }
            }
        }
        return null;
    }
    
    
    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born foxes.
     */
    private void giveBirth(List<Animal> newWolfs)
    {
        Field field = getField();
        Object animal2=field.getObjectAt(getLocation());
        for(Location locate : field.adjacentLocations(getLocation()))
        {
            Object animal1=field.getObjectAt(locate);
            if(animal1 instanceof Wolf&&animal2 instanceof Wolf)
            {
                Wolf Wolf1=(Wolf)animal1;
                Wolf Wolf2=(Wolf)animal2;
                if(!Wolf1.getSex().equals(Wolf2.getSex()))
                {
                    List<Location> free = field.getFreeAdjacentLocations(getLocation());
                    int births = breed();
                    for(int b = 0; b < births && free.size() > 0; b++) {
                        Location loc = free.remove(0);
                        Wolf young = new Wolf(false, field, loc,"young"," ",false);
                        young.getGender();
                        newWolfs.add(young);
                        if(!Wolf1.getIll()&&!Wolf2.getIll())
                        {
                            //born the Wolf;
                        }
                        else
                        {
                          young.If_getIll();   
                        }
                    }
                }
            }
        }   
    
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A fox can breed if it has reached the breeding age.
     */
    protected int getBREEDINGAGE()
    {
        return BREEDING_AGE;
    }
}