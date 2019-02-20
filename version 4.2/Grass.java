import java.util.*;
/**
 * A model of grass.
 * It contains Grass age, waterlevel, breed, illness and die.
 * 
 * @author ZHANYICHENG AND MAOYE
 */
public class Grass extends Plant
{
    // 实例变量 - 用你自己的变量替换下面的例子
    private static final int EAT_AGE = 3;
    private static final int MAX_AGE = 15;

    private static final double BREEDING_PROBABILITY = 0.3;
    private static final int WATER_LEVEL = 50;

    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    //private List<Integer> toxic_PROBABILITY= new ArrayList();
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    private int water;
    /**
     * Create a new grass. A grass may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param Eatable If true, the grass is eatable.
     * @param Toxic If true, the grass contains a virus which causes a disease.
     */
    public Grass(boolean randomAge, Field field, Location location,boolean Eatable,boolean toxic)
    {
        super(field, location);
        setAge(0);
        water = WATER_LEVEL;
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            water = rand.nextInt(WATER_LEVEL);
            if(getAge() > EAT_AGE)
            {
                canEat();
            }
        }
    }

    /**
     * the plant's actions in the sunny days.
     */
    public void act_Sunny(List<Plant> newGrass)
    {
        incrementAge();//The grass get more water when its raining
        incrementWater();
        if(isAlive()&&water>=0.4*(WATER_LEVEL)) {
            giveBirth(newGrass);            
        }
        else{}    
    }

    /**
     * the plant's actions in the rainy days.
     */
    public void act_Rainy(List<Plant> newGrass)
    {
        water = WATER_LEVEL;//The grass get more water when its raining
        incrementAge();
        incrementWater();
        if(isAlive()) {}//The grass wont grow if its raining
    }

    /**
     *
     * increase the age of the grass.
     * and if the grass is larger than 
     * the EAT_AGE, the grass could be eaten by the animals.
     * and if the grass is over than 
     * the MAX_AGE, the grass will set dead.
     * 
     */
    private void incrementAge()
    {
        int year= getAge();
        setAge(++year);
        if(getAge() > EAT_AGE){//The grass is old enough to eat.
            canEat();
        }
        if(getAge()  > MAX_AGE) {//The grass dead.
            setDead();
        }
    }

    /**
     * the grass will absorb water everyday.
     * when the water level is smaller than 0, the grass 
     * will die.
     */
    private void incrementWater()
    {
        water--;//waterlevel decreases.
        if(water <= 0) {
            setDead();
        }
    }

    /**
     * the whole procedure to give birth to the grass. put them
     * into the newGrass list.
     */
    private void giveBirth(List<Plant> newGrass)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        Random Rand =new Random();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        if(!If_cantEat()){//If the grass is not toxic.
            for(int b = 0; b < births && free.size() > 0; b++) {
                Location loc = free.remove(0);
                int nextRand = Rand.nextInt(1000);
                if(nextRand<=990)//Usually the new grass is healthy.
                {
                    Grass young = new Grass(false, field, loc,false,false);
                    newGrass.add(young);
                }
                else{//The probability of new grass becoming toxic is 1/1000
                    Grass young = new Grass(false, field, loc,false,false);
                    cantEat();//The grass becomes toxic.
                    newGrass.add(young);
                }
            }
        }
        else
        {
            incrementAge();
            //If the grass is toxic, it will not be able to breed.
            //But if any animal eats it before toxic grass dead, 
            //the animal will be infected.
        }
    }

    /**
     * return the the number of newly born grasses.
     * @ return the number of the newly born grasses.
     */
    private int breed()
    {
        int births = 0;
        if(rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
}
