import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a Snake.
 * Snakees age, move, eat Deers, and die.
 * 
 * @author ye Mao, yiCheng Zhan.
 * @version 2019.02.20 (2)
 */
public class Snake extends Animal
{
    // Characteristics shared by all Snakees (class variables).
    // The age at which a Snake can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a Snake can live.
    private static final int MAX_AGE = 80;
      // The probability of hunting deer.
    private static final double HUNT_PROBABILITY= 0.75;
    // The likelihood of a Snake breeding.
    private static final double BREEDING_PROBABILITY = 0.24;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single Deer. In effect, this is the
    // number of steps a Snake can go before it has to eat again.
    private static final int Deer_FOOD_VALUE = 20;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The Snake's food level, which is increased by eating Deers.
    private int foodLevel;
 /**
     * Create a Snake. A Snake can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the Snake will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param yearStage The yearStage of the Snake.
     * @param sex The gender of a Snake.
     * @param ill check whether  Snake eats toxic grass.
     */
    public Snake(boolean randomAge, Field field, Location location,String yearStage,String sex,boolean ill)
    {
        super(field, location,yearStage,sex,ill);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(Deer_FOOD_VALUE);
        }
        else {
            setAge(0);
            foodLevel = Deer_FOOD_VALUE;
        }
    }

    /**
     * This is what the Snake does most of the time: it hunts for
     * Deers. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newSnakees A list to return newly born Snakees.
     */
    public void act(List<Animal> newSnakees)
    {
        normalLives();
        if(isAlive()) {
            giveBirth(newSnakees);            
            Location newLocation = null;
            // Move towards a source of food if found.
            if(foodLevel<=0.7*Deer_FOOD_VALUE){
                newLocation = findFood();
            }
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
     * not every time snake could hunt.
     * it depends on the hunt_probability.
     * 
     */
    private boolean can_hunt()
    {
        Random rando= new Random();
        if(rando.nextDouble()<=HUNT_PROBABILITY)
        {
            return true; 
        }
        else
        {
            return false;}
    }
    /**
     * 
     * This is what the Snake does at night. Just Stay 
     * at the orginal loctaion and sleep,but the age will increase
     * normally.
     *
     */
    public void act_night(List<Animal> newSnakees)
    {
        normalLives();
    }
/**
     * return the MAX_AGE of the Snake.
     * @ return the MAX_AGE of the Snake.
     */
    public int get_Max_Age()
    {
        return MAX_AGE;
    }

    /**
     * Increase the age. This could result in the Snake's death.
     */
    protected void incrementAge()
    {
        super.incrementAge();
        if(getAge() > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this Snake more hungry. This could result in the Snake's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
  /**
     * the daily life of a Snake.
     */
    private void normalLives()
    {
        incrementAge();
        incrementHunger();
    }

    /**
     * Look for Deers adjacent to the current location.
     * Only the first live Deer is eaten.
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
            if(animal instanceof Deer) {
                Deer Deer = (Deer) animal;
                if(Deer.isAlive()&&can_hunt()) { 
                    Deer.setDead();
                    foodLevel = Deer_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Check whether or not this Snake is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newSnakees A list to return newly born Snakees.
     */
    private void giveBirth(List<Animal> newSnakees)
    {
        Field field = getField();
        Object animal2=field.getObjectAt(getLocation());
        for(Location locate : field.adjacentLocations(getLocation()))
        {
            Object animal1=field.getObjectAt(locate);
            if(animal1 instanceof Snake&&animal2 instanceof Snake)
            {
                Snake Snake1=(Snake)animal1;
                Snake Snake2=(Snake)animal2;

                if(!Snake1.getSex().equals(Snake2.getSex()))
                {
                    List<Location> free = field.getFreeAdjacentLocations(getLocation());
                    int births = breed();
                    
                    for(int b = 0; b < births && free.size() > 0; b++) {
                        Location loc = free.remove(0);
                        Snake  young = new Snake(false, field, loc,"young","",false);
                        young.getGender();
                        //if newly born deer's parents get ill, young deers will also 
                        //be sick.
                        if(!Snake1.If_getIll()&&!Snake2.If_getIll())
                        {
                        }
                        else{
                            young.getIll();
                        }
                        //Confirm the role of each snake.
                        if(Snake1.getSex().equals("Male"))
                        {
                            young.set_Mother(Snake2);
                            young.set_Father(Snake1);
                        }
                        else
                        {
                            young.set_Mother(Snake1);
                            young.set_Father(Snake2);
                        }
                        //snake's parents will set couple between each other.
                        Snake1.set_Couple(Snake2);
                        Snake2.set_Couple(Snake1);
                        Snake1.set_Child(young);
                        Snake2.set_Child(young);
                        newSnakees.add(young);
                        //add young snakes in the list.
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
     * A Snake can breed if it has reached the breeding age.
     */
    protected int getBREEDINGAGE()
    {
        return BREEDING_AGE;
    }
}
