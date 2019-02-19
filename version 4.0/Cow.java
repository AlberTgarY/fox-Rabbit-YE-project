import java.util.Random;
import java.util.*;

/**
 * 在这里给出对类 cow 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class Cow extends Animal
{
    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 100;
    private static final double BREEDING_PROBABILITY = 0.3;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;

    private static final int GRASS_FOOD_VALUE = 50;
    //The cow's age.

    private static final Random rand = Randomizer.getRandom();
    
    private int foodLevel;
    // A shared random number generator to control breeding.
    /**
     * 类 cow 的对象的构造函数
     */
    public Cow(boolean randomAge,Field field,Location location,String yearStage,String sex,boolean ill)
    {
        super(field, location,yearStage,sex,ill);
        setAge(0);
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(GRASS_FOOD_VALUE);
        }
        else {
            foodLevel = GRASS_FOOD_VALUE;
        }
    }

    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Animal> newCows)
    {
        normalLives();
        if(isAlive()) {
            giveBirth(newCows);            
            // Try to move into a free location.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    public void act_night(List<Animal> newFoxes)
    {
        normalLives();
    }

    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object plant = field.getObjectAt(where);
            if(plant instanceof Grass) {
                Grass grass = (Grass) plant;
               if(grass.isAlive()) { 
                    if(!grass.If_cantEat()){
                    grass.setDead();
                    foodLevel = GRASS_FOOD_VALUE;
                    return where;
                }
                 else{
                    grass.setDead();
                    getIll();
                    foodLevel+=5;
                    return where;
                }
            }
        }
    }
        return null;
}  
    

    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    protected void incrementAge()
    {
        super.incrementAge();
        if(getAge() > MAX_AGE) {
            setDead();
        }
    }

    /** 
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    private void giveBirth(List<Animal> newCows)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        Object animal2=field.getObjectAt(getLocation());
        for(Location locate : field.adjacentLocations(getLocation()))
        {
            Object animal1=field.getObjectAt(locate);
            if(animal1 instanceof Cow&&animal2 instanceof Cow)
            {
                Cow cow1=(Cow)animal1;
                Cow cow2=(Cow)animal2; 
                if(!cow1.getSex().equals(cow2.getSex()))
                {
                    List<Location> free = field.getFreeAdjacentLocations(getLocation());
                    int births = breed();
                   
                    for(int b = 0; b < births && free.size() > 0; b++) {
                        Location loc = free.remove(0);                
                        Cow  young = new Cow(false, field, loc,"young","",false);
                        young.getGender();
                        young.set_Yearstage(getAge(),MAX_AGE);
                        
                        if(!cow1.If_getIll()&&!cow2.If_getIll())
                        {
                            
                        }
                        else{young.getIll();};
                        
                        newCows.add(young);
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
    
    private void normalLives()
    {
        incrementAge();
        incrementHunger();
    }
    
    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    protected int getBREEDINGAGE()
    {
        return BREEDING_AGE;
    }
}

