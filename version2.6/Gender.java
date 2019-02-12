import java.util.*;
/**
 * Write a description of class gender here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
//abstract public class Gender
public class Gender
{
    // instance variables - replace the example below with your own
    private String male;
    private String female;
    private String gender;
    private int sexProbablity;
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
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    //abstract public String born_sex();
    public String get_gender()
    {
        Random rand = new Random();
        int i = rand.nextInt(gender_list.size());
        gender = gender_list.get(i);
        return gender;
    }
    
}
