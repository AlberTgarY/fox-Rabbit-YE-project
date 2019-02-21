import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing Deers and Snakees.
 * 
 * @author: YICHENG ZHAN and YE MAO;
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a Snake will be created in any given grid position.
    private static final double Snake_CREATION_PROBABILITY = 0.06;
    // The probability that a Deer will be created in any given grid position.
    private static final double Deer_CREATION_PROBABILITY = 0.13;   
    // The probability that a Cow will be created in any given grid position.
    private static final double COW_CREATION_PROBABILITY= 0.1;
    // The probability that a Grass will be created in any given grid position.
    private static final double GRASS_CREATION_PROBABILITY = 0.19;   
    // The probability that a Tiger will be created in any given grid postion.
    private static final double TIGER_CREATION_PROBABILITY = 0.07;  
    // The probability that a Wolf will be created in any given grid postion.
    private static final double WOLF_CREATION_PROBABILITY=0.04;
    // List of animals in the field.
    private List<Animal> animals;
    // The current weather in this simulation.
    private String currentWeather;
    // check the zone of a single day.
    private String timezone;
    // List of plants in the field.
    private List<Plant> plants;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        animals = new ArrayList<>();
        plants = new ArrayList<>();
        field = new Field(depth, width);
        currentWeather = getWeather();

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);

        view.setColor(Deer.class, Color.ORANGE);
        view.setColor(Snake.class, Color.BLUE);
        view.setColor(Tiger.class, Color.RED);
        view.setColor(Grass.class, Color.GREEN);
        view.setColor(Cow.class,Color.lightGray);
        view.setColor(Wolf.class,Color.gray);
        // Setup a valid starting point
        reset();
    }
    
    /** 
     * return the randomly current weather of the simulation.
     * @return the currentWeather of each timezone.
     */
    protected String getWeather()
    {
        Weather weather= new Weather();
        currentWeather=weather.get_Weather();
        return currentWeather;
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    //
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            // delay(60);   // uncomment this to run more slowly
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * Snake and Deer.
     */
    public void simulateOneStep()
    {
        step++;
        if(step%2==0)
        {
            timezone= "AM";
        }
        else if(step%2==1)
        {
            timezone ="PM";
        }  

        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<>();   
        List<Plant> newPlants = new ArrayList<>();
        // Let all Deers act.
        if(step%2==0)
        // As the timezone  is the "AM", the number of steps is even num.
        {
            //animals' acts if the timezone is aM.
            for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) { 
                Animal animal = it.next();
                animal.act_night(newAnimals);
                if(!animal.isAlive()) {
                    it.remove();
                }
            }
            // all plants' acts in the sunny or Foggy days. during AM.
            if(currentWeather.equals("Sunny")||currentWeather.equals("Foggy"))
            {
                for(Iterator<Plant> it = plants.iterator(); it.hasNext(); ) {
                    Plant plant = it.next();
                    if(!plant.isAlive()) {
                        plant.act_Sunny(newPlants);
                        it.remove();
                    }
                }
            }
            //all  plants' acts in the Rainy days during AM.
            else if(currentWeather.equals("Rainy"))
            {
                for(Iterator<Plant> it = plants.iterator(); it.hasNext(); ) {
                    Plant plant = it.next();
                    if(!plant.isAlive()) {
                        plant.act_Rainy(newPlants);
                        it.remove();
                    }
                }
            }
        }
        // As the timezone  is the "pm", the number of steps is odd num.
        else if(step%2==1)
        {
            //when the timezone is PM,whatever the weather is, 
            //the animals' acts will be shown like below.
            for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
                Animal animal = it.next();
                animal.act(newAnimals);
                if(!animal.isAlive()) {
                    it.remove();
                }
            }
            //all plants' acts in the rainy days at nignt.
            if(currentWeather.equals("Rainy")){
                for(Iterator<Plant> it = plants.iterator(); it.hasNext(); ) {
                    Plant plant = it.next();
                    plant.act_Rainy(newPlants);
                    if(!plant.isAlive()) {
                        it.remove();
                    }
                }
            }
            //all plants' acts in the sunny days of Foggy days at night.
            else if(currentWeather.equals("Sunny")||currentWeather.equals("Foggy")){
                for(Iterator<Plant> it = plants.iterator(); it.hasNext(); ) {
                    Plant plant = it.next();
                    plant.act_Sunny(newPlants);
                    if(!plant.isAlive()) {
                        it.remove();
                    }
                }
            }
        }
        // return the currentWeather of a single timezone.
        currentWeather = getWeather();
        // Add the newly born animals to the main lists.
        animals.addAll(newAnimals);
        // Add the newly born plants to the main lists.
        plants.addAll(newPlants);
        // Show the starting state in the view.
        view.showStatus(step,currentWeather, field,timezone);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        plants.clear();
        populate();
        // Show the starting state in the view.
        view.showStatus(step,currentWeather ,field, timezone);
    }

    /**
     * Randomly populate the field with Snakees and Deers.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        // Update the current weather.
        currentWeather=getWeather();
        // At the beginning the time zone is assumed to be "AM" .
        timezone="AM";
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= Snake_CREATION_PROBABILITY) {
                    Gender gender= new Gender();
                    gender.get_gender();
                    Location location = new Location(row, col);
                    Snake Snake = new Snake(true, field, location,"","",false);
                    Snake.set_Yearstage(Snake.getAge(),Snake.get_Max_Age());
                    Snake.setSex();
                    animals.add(Snake);     
                }
                else if(rand.nextDouble() <= Deer_CREATION_PROBABILITY) {
                    Gender gender= new Gender();
                    gender.get_gender();
                    Location location = new Location(row, col);
                    Deer Deer = new Deer(true, field, location ,"","",false); 
                    Deer.set_Yearstage(Deer.getAge(),Deer.get_Max_Age());
                    Deer.setSex();
                    animals.add(Deer);
                }
                else if(rand.nextDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Grass grass = new Grass(true, field, location,false,false);
                    plants.add(grass);     
                }
                else if(rand.nextDouble() <= COW_CREATION_PROBABILITY) {
                    Gender gender= new Gender();
                    gender.get_gender();
                    Location location = new Location(row, col);
                    Cow cow = new Cow(true, field, location," ","",false);
                    cow.set_Yearstage(cow.getAge(),cow.get_Max_Age());
                    cow.setSex();
                    animals.add(cow);
                }
                else if(rand.nextDouble() <= TIGER_CREATION_PROBABILITY) {
                    Gender gender= new Gender();
                    gender.get_gender();
                    Location location = new Location(row, col);
                    Tiger tiger = new Tiger(true, field, location,"","",false); 
                    tiger.set_Yearstage(tiger.getAge(),tiger.get_Max_Age());
                    tiger.setSex();
                    animals.add(tiger);
                }
                else if(rand.nextDouble() <=  WOLF_CREATION_PROBABILITY) {
                    Gender gender= new Gender();
                    gender.get_gender();
                    Location location = new Location(row, col);
                    Wolf wolf = new Wolf(true, field, location," ","",false); 
                    wolf.set_Yearstage(wolf.getAge(),wolf.get_Max_Age());
                    wolf.setSex();
                    animals.add(wolf);
                    // else leave the location empty.
                }
            }
            view.showStatus(step,currentWeather, field,timezone);
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
