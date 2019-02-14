import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.0;//2;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.02;   
    private static final double COW_CREATION_PROBABILITY= 0.0;//5;
    private static final double GRASS_CREATION_PROBABILITY = 0.1;    
    private static final double TIGER_CREATION_PROBABILITY = 0.0;//1;   

    // List of animals in the field.
    private List<Animal> animals;
    private String currentWeather;
    private Weather weather;
    private String weathe;
    private String timezone;
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
        
        view.setColor(Rabbit.class, Color.ORANGE);
        view.setColor(Fox.class, Color.BLUE);
        view.setColor(Tiger.class, Color.RED);
        view.setColor(Grass.class, Color.GREEN);
        view.setColor(Cow.class,Color.lightGray);
        // Setup a valid starting point.
        reset();
    }
    protected String getTime()
    {
     if(step%2==0)
     {
        return "Night";
     }
     else
     {
        return "Morning";   
     }
    }
     
    protected String getWeather()
    {
        weather= new Weather();
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
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;
         weathe = getWeather();
         
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
        // Let all rabbits act.
        if(step%2==0)
        { 
            for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) { 
                    Animal animal = it.next();
                    animal.act_night(newAnimals);
                    if(!animal.isAlive()) {
                        it.remove();
                    }
                }
            if(currentWeather.equals("Sunny"))
            {
                for(Iterator<Plant> it = plants.iterator(); it.hasNext(); ) {
                    Plant plant = it.next();
                    if(!plant.isAlive()) {
                        plant.act_Sunny(newPlants);
                        it.remove();
                    }
                }
                currentWeather = getWeather();
            }
            else if(currentWeather.equals("Rainy"))
            {
                for(Iterator<Plant> it = plants.iterator(); it.hasNext(); ) {
                    Plant plant = it.next();
                    if(!plant.isAlive()) {
                        plant.act_Rainy(newPlants);
                        it.remove();
                    }
                }
                currentWeather = getWeather();
            }
            else if(currentWeather.equals("Foggy"))
            {
                for(Iterator<Plant> it = plants.iterator(); it.hasNext(); ) {
                    Plant plant = it.next();
                    if(!plant.isAlive()) {
                        plant.act_Sunny(newPlants);
                        it.remove();
                    }
                }
                currentWeather = getWeather();
            }
        }
        else if(step%2==1)
        {
            for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
                    Animal animal = it.next();
                    animal.act(newAnimals);
                    if(!animal.isAlive()) {
                        it.remove();
                    }
                }
            if(currentWeather.equals("Rainy")){
                for(Iterator<Plant> it = plants.iterator(); it.hasNext(); ) {
                    Plant plant = it.next();
                    plant.act_Rainy(newPlants);
                    if(!plant.isAlive()) {
                        it.remove();
                    }
                }
            }
            else if(currentWeather.equals("Sunny")){
                for(Iterator<Plant> it = plants.iterator(); it.hasNext(); ) {
                    Plant plant = it.next();
                    plant.act_Sunny(newPlants);
                    if(!plant.isAlive()) {
                        it.remove();
                    }
                }
            }
            else if(currentWeather.equals("Foggy"))
            {
                for(Iterator<Plant> it = plants.iterator(); it.hasNext(); ) {
                    Plant plant = it.next();
                    if(!plant.isAlive()) {
                        plant.act_Sunny(newPlants);
                        it.remove();
                    }
                }
                currentWeather = getWeather();
            }
        }
        // Add the newly born foxes and rabbits to the main lists.
        animals.addAll(newAnimals);
        plants.addAll(newPlants);
        //..myFault
        view.showStatus(step,weathe, field,timezone);
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
        view.showStatus(step,weathe ,field, timezone);
    }

    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        //weathe=currentWeather();
        weathe=getWeather();
        
        timezone="am";
        
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Gender gender= new Gender();
                    gender.get_gender();
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location,"",false);
                    fox.setSex();
                    animals.add(fox);     
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Gender gender= new Gender();
                    gender.get_gender();
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location,"",false); 
                    rabbit.setSex();
                    animals.add(rabbit);
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
                    Cow cow = new Cow(true, field, location,"",false); 
                    cow.setSex();
                    animals.add(cow);
                }
                else if(rand.nextDouble() <= TIGER_CREATION_PROBABILITY) {
                    Gender gender= new Gender();
                    gender.get_gender();
                    Location location = new Location(row, col);
                    Tiger tiger = new Tiger(true, field, location,"",false); 
                    tiger.setSex();
                    animals.add(tiger);
                }
                // else leave the location empty.
            }
        }
        view.showStatus(step,weathe, field,timezone);
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
