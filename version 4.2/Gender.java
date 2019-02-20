import java.util.*;
/**
 * Provide a random gender for every animal.
 *
 *@author: YICHENG ZHAN and YE MAO;
 */
public class Gender
{
    private String male;
    private String female;
    private String gender;
    private int sexProbablity;
    //A list to store the genders.
    private List<String>gender_list =new ArrayList<>();

    /**
     * Constructor for objects of class gender
     */
    public Gender()
    {

        male = "Male";
        female = "Female";
        gender_list.add(male);
        gender_list.add(female);

    }
    
    /**
     * Get a random gender
     * 
     * @return: get a random gender;
     */
    public String get_gender()
    {
        Random rand = new Random();
        int i = rand.nextInt(gender_list.size());
        gender = gender_list.get(i);
        return gender;
    }
    
}
