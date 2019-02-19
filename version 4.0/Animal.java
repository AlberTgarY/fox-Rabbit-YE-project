import java.util.*;
/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal extends Organism
{
    // Whether the animal is alive or not.
   
    private boolean ill;
    private Animal Father;
    private Animal Mother;
    private Animal Couple;
    private List<Animal>children = new ArrayList<>();
    // The animal's field.
    
    // The animal's position in the field.
   
    protected String sex;
    private String yearstage = null;
    private Gender gender;
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location,String yearStage,String sex,boolean ill)
    {
        super(field,location);
        ill = false;
        setLocation(location);
    }
    
    public void set_Yearstage(int age,int MAX_AGE)
    {
        if(age<=MAX_AGE*0.3)
        {
            yearstage= "Young";
        }
        else if(age<=MAX_AGE*0.7&&age>MAX_AGE*0.3)
        {
            yearstage= "Maturity";
        }
        else if(age>MAX_AGE*0.7)
        {
            yearstage= "Old";
        }
    }
    
    public void set_Father(Animal father)
    {
        this.Father = father;
    }
    public void set_Mother(Animal mother)
    {
        this.Mother = mother;
    }
     public void set_Couple(Animal couple)
    {
        this.Couple = couple;
    }
    public void set_Child(Animal child)
    {
        children.add(child);
    }
    public Animal get_Father()
    {
        return Father;
    }
    public Animal get_Mother()
    {
        return Mother;
    }
     public Animal get_Couple()
    {
        return Couple;
    }
    
    public boolean has_Children()   
    {
       if(children.size()!=0){
       return true;
    }
       else{return false;
        }
     
    }
    
    public Animal get_lowest_Age()  
    {
    int min = 0;
    HashMap<Integer,Animal> findAge= new HashMap();
    for(Animal Younganimal:children)
    {
        
            findAge.put(Younganimal.getAge(), Younganimal);
        
    }
    for(Animal Younganimal:children)
    {
            if(Younganimal.getAge()<min)
            {
                min = Younganimal.getAge();
            }
     }
      return  findAge.get(min)   ; 
    }
   
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);
    abstract public void act_night(List<Animal> newAnimals);
    //abstract public void act_foggy(List<Animal> newAnimals);
    protected boolean canBreed()
    {
        return getAge() >= getBREEDINGAGE();
    }
    
    abstract protected int getBREEDINGAGE();
    protected String getYearStage()
    {
        return yearstage;
    }  
   
   
    public void setSex()
    {
        sex=getGender();
    }
    //
       protected void incrementAge()
    {
        int age=getAge();
        if(If_getIll())
        {   
            age+=5;
            setAge(age);
        }
        else{
            ++age;
            setAge(age);
            }
    }
    
    protected boolean getIll()
    {
        return ill = true;
    }
    
    protected boolean If_getIll()
    {
        return ill;
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
    
    
}
