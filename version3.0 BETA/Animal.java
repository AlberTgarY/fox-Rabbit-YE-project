
import java.util.*;
/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    private boolean ill;
    private Object Father;
    private Object Mother;
    private List<Object>children = new ArrayList<>();
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    protected String sex;
    protected String yearstage;
    private int age;
    
    private Gender gender;
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        ill = false;
        this.field = field;
        setLocation(location);
    }
    
    public void set_Yearstage(int age,int MAX_AGE)
    {
        if(age<=MAX_AGE*0.3)
        {
            yearstage= "Young";
        }
        if(age<=MAX_AGE*0.7&&age>MAX_AGE*0.3)
        {
            yearstage= "Maturity";
        }
        if(age>MAX_AGE*0.7)
        {
            yearstage= "Old";
        }
    }
    
    public void set_Father(Object father)
    {
        this.Father = father;
    }
    public void set_Mother(Object mother)
    {
        this.Mother = mother;
    }
    public void set_Child(Object child)
    {
        children.add(child);
    }
    public Object get_Father()
    {
        return Father;
    }
    public Object get_Mother()
    {
        return Mother;
    }
    public List get_children()
    {
        return children;
    }
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);
    abstract public void act_night(List<Animal> newAnimals);
    //abstract public void act_foggy(List<Animal> newAnimals);
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }
    protected boolean canBreed()
    {
        return getAge() >= getBREEDINGAGE();
    }
    
    protected void incrementAge()
    {
        age++;
        if(If_getIll())
        {   
            age+=5;
        }
    }
    //protected void NormalLives(List<Animal> newAnimals)
    //{
     //   incrementAge();
    //   incrementHunger();
    //}
    
    abstract protected int getBREEDINGAGE();
    
    protected int getAge()
    {
        return age;
    }  
    protected String getYearStage()
    {
        return yearstage;
    }  
    protected void setAge(int Age)
    {
        this.age= age; 
    }   
   
    public void setSex()
    {
        sex=getGender();
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
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
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    protected boolean getIll()
    {
        ill = true;
        return ill;
    }
    protected boolean If_getIll()
    {
        return ill;
    }
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        Object plant = field.getObjectAt(newLocation);
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.placeAni(this, newLocation);
    }
    
    protected String getGender()
    {
       gender= new Gender();
       sex=gender.get_gender();
       return sex;
    }
    
    protected String getSex()
    {
        return sex;  
    }     
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
}
