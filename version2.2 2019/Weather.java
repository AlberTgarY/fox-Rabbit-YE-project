import java.util.List;
import java.util.ArrayList;
import java.util.*;
/**
 * 在这里给出对类 Weather 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class Weather
{
    private List<String>Weather_list =new ArrayList<>();
    private String weather;

    public Weather()
    {
        Weather_list.add("sunny");
        Weather_list.add("rainy");
       // Weather_list.add("foggy");
    }

    public String choose_Weather(int n)
    {
        return Weather_list.get(n);
    }
    public String random_weather()
    {
        Random rand = new Random();
        int i = rand.nextInt(Weather_list.size());
        weather = Weather_list.get(i);
        return weather;
    }
}
