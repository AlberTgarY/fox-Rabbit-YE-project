import java.util.*;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Rabbit extends Animal 
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 50;

    private static final double BREEDING_PROBABILITY = 0.5;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    private static final int GRASS_FOOD_VALUE = 10;

    private int sexProbablity;

    // Individual characteristics (instance fields).
    // The rabbit's age.

    private int foodLevel;
    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location,String yearStage,String sex,boolean ill)
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
    public void act(List<Animal> newRabbits)
    {
        dayLives();
        if(isAlive()) {
            giveBirth(newRabbits);            
            // Try to move into a free location.
            Location newLocation = findFood();
            if(getYearStage()==null)
            {
                if(newLocation == null)
                {
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
            }
            if(getYearStage().equals("Young"))//young rabbit walks around his/her father.
            {
                Object Animalmother = get_Mother();
                if(Animalmother instanceof Rabbit)
                {
                    Rabbit father =(Rabbit) Animalmother;
                    Location location = father.getLocation();
                    newLocation = getField().freefourTimesfourLocation(location);
                    //newLocation = getField().freeAdjacentLocation(getLocation());
                }
            }
            if(getYearStage().equals("Maturity")&&getSex().equals("Male"))
            {
                if(newLocation == null&&get_children()==null) { 
                    // No food found - try to move to a free location.
                    // No children No wife.
                }
                else if(newLocation == null&&get_children()!=null){
                    // No food found & Has children
                    // No child needs to feed - hildren move around hit.
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                else if(newLocation != null&&get_children()!=null)
                {
                    // food found & Has children.
                    // No child needs to feed - move around the children.
                    //判断小兔子foodlevel是否低于闸值，喂养foodlevel最低的小兔子.
                    //食物优先给小兔子，再给老婆（还未写ifstatement判断雌雄）,再给自己.

                    if(get_Couple() instanceof Rabbit)
                    {
                        Rabbit Wife = (Rabbit) get_Couple();
                        int Couple_foodlevel = Wife.get_Foodlevel();
                        int Lowest_foodlevel = lowest_Foodlevel_Rabbit().get_Foodlevel();
                        if(get_Foodlevel()>Couple_foodlevel&&Lowest_foodlevel>Couple_foodlevel)
                        {
                            giveFood(Wife);
                        }
                        if(get_Foodlevel()>Couple_foodlevel&&Lowest_foodlevel<Couple_foodlevel)
                        {
                            giveFood(lowest_Foodlevel_Rabbit());
                        }
                    }                    
                }   
            }
            if(getYearStage().equals("Maturity")&&getSex().equals("Female"))
            {
                 if(newLocation == null&&get_children()==null) { 
                    // No food found - try to move to a free location.
                    // No children No Husband.
                }
                else if(newLocation == null&&get_children()!=null){
                    // No food found & Has children
                    // No child needs to feed - hildren move around hit.
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                else if(newLocation != null&&get_children()!=null)
                {
                    // food found & Has children.
                    // No child needs to feed - move around the children.
                    //判断小兔子foodlevel是否低于闸值，喂养foodlevel最低的小兔子.
                    //食物优先给小兔子，再给老婆（还未写ifstatement判断雌雄）,再给自己.

                    if(get_Couple() instanceof Rabbit)
                    {
                        Rabbit Wife = (Rabbit) get_Couple();
                        int Couple_foodlevel = Wife.get_Foodlevel();
                        int Lowest_foodlevel = lowest_Foodlevel_Rabbit().get_Foodlevel();
                        if(get_Foodlevel()>Couple_foodlevel&&Lowest_foodlevel>Couple_foodlevel)
                        {
                            giveFood(Wife);
                        }
                        if(get_Foodlevel()>Couple_foodlevel&&Lowest_foodlevel<Couple_foodlevel)
                        {
                            giveFood(lowest_Foodlevel_Rabbit());
                        }
                    }                    
                }
            
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

    public Rabbit lowest_Foodlevel_Rabbit()
    {
        int min = 0;

        HashMap<Integer,Rabbit> findLevel= new HashMap();
        for(Object Youngrabbit:get_children())
        {
            if(Youngrabbit instanceof Rabbit){
                Rabbit rabbit = (Rabbit) Youngrabbit;
                findLevel.put(rabbit.get_Foodlevel(),rabbit);
            }
        }
        for(Object Youngrabbit:get_children())
        {
            if(Youngrabbit instanceof Rabbit){
                Rabbit rabbit = (Rabbit) Youngrabbit;
                if(rabbit.get_Foodlevel()<min)
                {
                    min = rabbit.get_Foodlevel();
                }
            }
        }
        return  findLevel.get(min);
    }

    public void act_night(List<Animal> newRabbits)
    {
        dayLives();
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
                if(grass.isAlive()&&grass.If_canEat()) { 
                    if(!grass.If_cantEat())
                    {
                        grass.setDead();
                        foodLevel = GRASS_FOOD_VALUE;
                        return where;
                    }
                    else{
                        grass.setDead();
                        getIll();
                        foodLevel+=3;
                        return where;
                    }
                }
            }
        }
        return null;
    }
    private void giveFood(Rabbit rabbit)
    {
        rabbit.set_Foodlevel(GRASS_FOOD_VALUE); 
    }
    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    protected void incrementAge()
    {
        super.incrementAge();
        set_Yearstage(getAge(),MAX_AGE);
        if(getAge() > MAX_AGE) {
            setDead();
        }
    }

    /** 
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    private void giveBirth(List<Animal> newRabbits)
    {
        //
        Field field = getField();
        Object animal2=field.getObjectAt(getLocation());
        for(Location locate : field.adjacentLocations(getLocation()))
        {
            Object animal1=field.getObjectAt(locate);
            if(animal1 instanceof Rabbit&&animal2 instanceof Rabbit)
            {
                Rabbit rabbit1=(Rabbit)animal1;
                Rabbit rabbit2=(Rabbit)animal2; 
                if(!rabbit1.getSex().equals(rabbit2.getSex()))
                {
                    List<Location> free = field.getFreeAdjacentLocations(getLocation());
                    int births = breed();
                    if(!rabbit1.If_getIll()&&!rabbit2.If_getIll())
                    {
                        for(int b = 0; b < births && free.size() > 0; b++) {
                            Location loc = free.remove(0);
                            Rabbit young = new Rabbit(false, field, loc,"young"," ",false);
                            young.getGender();
                            newRabbits.add(young);
                            young.set_Yearstage(getAge(),MAX_AGE);
                            if(rabbit1.getSex().equals("Male"))
                            {
                                young.set_Father(rabbit1);
                                young.set_Mother(rabbit2);
                            }
                            else
                            {
                                young.set_Mother(rabbit1);
                                young.set_Father(rabbit2);
                            }
                            rabbit1.set_Child(young);
                            rabbit1.set_Couple(rabbit2);
                            rabbit2.set_Couple(rabbit1);
                            rabbit2.set_Child(young);
                        }
                    }
                    else
                    {
                        for(int b = 0; b < births && free.size() > 0; b++) {
                            Location loc = free.remove(0);
                            Rabbit young = new Rabbit(false, field, loc,"young ","",false);
                            young.getIll();
                            young.getGender();
                            newRabbits.add(young);
                            young.set_Yearstage(getAge(),MAX_AGE);
                            if(rabbit1.getSex().equals("Male"))
                            {
                                young.set_Father(rabbit1);
                                young.set_Mother(rabbit2);
                            }
                            else
                            {
                                young.set_Mother(rabbit1);
                                young.set_Father(rabbit2);
                            }
                            rabbit1.set_Child(young);
                            rabbit1.set_Couple(rabbit2);
                            rabbit2.set_Couple(rabbit1);
                            rabbit2.set_Child(young);
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
    private int get_Foodlevel()
    {
        return foodLevel;
    }
    private void set_Foodlevel(int food)
    {
          foodLevel = food;
    }
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    private void dayLives()
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
