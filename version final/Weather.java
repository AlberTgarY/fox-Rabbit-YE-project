import java.util.List;
import java.util.ArrayList;
import java.util.*;
/**
 * A class that contains the weather of the simulator.
 * 
 * Author: ZHANYICHENG & MAOYE;
 */
public class Weather
{
    private List<String>Weather_list =new ArrayList<>();
    private String weather;
    /**
     * initialize the weathers of the simulator
     */
    public Weather()
    {
        Weather_list.add("Sunny");
        Weather_list.add("Rainy");
        Weather_list.add("Foggy");
       // Weather_list.add("foggy");
    }
    /**
     * get a weather for simulator`s next step.
     * @return: return a weather.
     */
    public String choose_Weather(int n)
    {
        return Weather_list.get(n);
    }
    /**
     * get a random weather for simulator`s next step.
     * @return: return a random weather.
     */
    public String get_Weather()
    {
        Random rand = new Random();
        int i = rand.nextInt(Weather_list.size());
        weather = Weather_list.get(i);
        return weather;
    }
}
