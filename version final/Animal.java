import java.util.*;
/**
 * A class representing shared characteristics of animals.
 * 
 * @author ye Mao, yiCheng Zhan.
 *    @version 2019.02.20
 */
public abstract class Animal extends Organism
{
    // Whether the animal is alive or not.
    // check weather animal is ill.
    private boolean ill;
    // get the animal's father through give birth method.
    private Animal Father;
    // to get the animal's mother through give birth method .
    private Animal Mother;
    // The couple of  animal.
    private Animal Couple;
    // The list of the children of an animal.
    private List<Animal>children = new ArrayList<>();
    // The gender of an animal.
    protected String sex;
    // The year stage of the animal. At the biginning the year stage is null.
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

    /**
     * confirm different year stage through animal'age and MAX_AGE
     * define three year stages,"young","MATURITY" And "Old" 
     * respectively.
     */
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

    /**
     * set animal's father.
     */
    public void set_Father(Animal father)
    {
        this.Father = father;
    }

    /**
     * after boning new animals, set animals' mother.
     */
    public void set_Mother(Animal mother)
    {
        this.Mother = mother;
    }

    /**
     * set animal's couple
     */
    public void set_Couple(Animal couple)
    {
        this.Couple = couple;
    }

    /**
     * set animal's children
     * put them in the children List. because
     * each animal have at least one child.
     */
    public void set_Child(Animal child)
    {
        children.add(child);
    }

    /**
     * return the father of an animal.
     * @return the father of an animal.
     */
    public Animal get_Father()
    {
        return Father;
    }

    /**
     * return the mother of the animal.
     * @return the mother of the animal.
     */
    public Animal get_Mother()
    {
        return Mother;
    }

    /**
     * return the couple of an animal.
     * @return the couple of tha animal.
     */
    public Animal get_Couple()
    {
        return Couple;
    }

    /**
     * check the animal ,whether it has at least one or more child.
     */
    public boolean has_Children()   
    {
        if(children.size()!=0){
            return true;
        }
        else{return false;
        }

    }

    /**
     * get the animal which has the lowest age.
     * their father or mother is responsible for raising them.
     */
    public Animal get_lowest_Age()  
    {
        int min = 0;
        HashMap<Integer,Animal> findAge= new HashMap();
        // find the lowest age among children's age .
        for(Animal Younganimal:children)
        {

            findAge.put(Younganimal.getAge(), Younganimal);

        }
        //match the child specifically through the HashMap;
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

    /**
     * Make this animal act at night
     * whatever it wants/needs to do.
     */
    abstract public void act_night(List<Animal> newAnimals);

    /**
     *  get different animal's breeding age.
     */
    protected boolean canBreed()
    {
        return getAge() >= getBREEDINGAGE();
    }

    /**
     *  get different animal's breeding age.
     */
    abstract protected int getBREEDINGAGE();

    /** 
     * return the yearStage of an animal.
     * @return the yearStage of an animal.
     */
    protected String getYearStage()
    {
        return yearstage;
    }  

    /**
     * define the gender of an animal randomly.
     */
    public void setSex()
    {
        sex=getGender();
    }

    /**
     * the normal rule of increasing age of an animal.
     */
    protected void incrementAge()
    {
        int age=getAge();
        if(If_getIll())
        {   
            //if animal is sick, its age will increases rapidly.
            age+=5;
            setAge(age);
        }
        else{
            // animal's age climbs 
            // normally.
            ++age;
            setAge(age);
        }
    }

    /**
     * make the animal be ill. the boolean ill is true.
     * @return true.
     */
    protected boolean getIll()
    {
        return ill = true;
    }

    /**
     * whether an animal is sick.
     * @return true, if the animal ate ill herbivours and get ill.
     * @return false, if the animal didn't eat before.
     */
    protected boolean If_getIll()
    {
        return ill;
    }

    /**
     * return a random gender.
     * @return the gender of the animal
     */

    protected String getGender()
    {
        gender= new Gender();
        sex=gender.get_gender();
        return sex;
    }

    /**
     * return the animal's gender.
     * @return the gender of the animal
     */
    protected String getSex()
    {
        return sex;  
    }     

}
