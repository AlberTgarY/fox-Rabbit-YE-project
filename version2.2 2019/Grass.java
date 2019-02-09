import java.util.*;
/**
 * 在这里给出对类 grass 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class Grass extends Plant
{
    // 实例变量 - 用你自己的变量替换下面的例子
    private static final int EAT_AGE = 3;
    private static final int MAX_AGE = 10;
    
    private static final double BREEDING_PROBABILITY = 0.3;
    private static final int WATER_LEVEL = 50;
    
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 1;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    private int age;
    private int water;
    /**
     * 类 grass 的对象的构造函数
     */
    public Grass(boolean randomAge, Field field, Location location,boolean Eatable)
    {
        super(field, location);
        age = 0;
        water = WATER_LEVEL;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            water = rand.nextInt(WATER_LEVEL);
            if(age > EAT_AGE)
            {
                canEat();
            }
        }
    }
    public void act_Sunny(List<Plant> newGrass)
    {
        incrementAge();
        incrementWater();
        if(isAlive()&&water>=0.4*(WATER_LEVEL)) {
            giveBirth(newGrass);            
        }
        else{}    
    }
    public void act_Rainy(List<Plant> newGrass)
    {
        water = WATER_LEVEL;
        incrementAge();
        incrementWater();
        if(isAlive()) {}
    }
    private void incrementAge()
    {
        age++;
        if(age > EAT_AGE){
            canEat();
        }
        if(age > MAX_AGE) {
            setDead();
        }
    }
        private void incrementWater()
    {
        water--;
        if(water <= 0) {
            setDead();
        }
    }
    protected int getAge()
    {
        return age;
    }  
        private void giveBirth(List<Plant> newGrass)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Grass young = new Grass(false, field, loc,false);
            newGrass.add(young);
        }
    }
    private int breed()
    {
        int births = 0;
        if(rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
}
