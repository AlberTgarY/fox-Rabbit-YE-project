import java.util.List;
/**
 * 在这里给出对类 plant 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public abstract class Plant extends Organism
{
    // 实例变量 - 用你自己的变量替换下面的例子
    private boolean alive;
    private boolean eatable;
    private boolean toxic;
    // The animal's field.
    // The animal's position in the field.
    private Location location;

    /**
     * 类 plant 的对象的构造函数
     */
    
    public Plant(Field field, Location location)
    {
        super(field,location);
        eatable = false;
        toxic = false;
        setLocation(location);
    }
    
    protected boolean If_canEat()
    {
        return eatable;
    }
    
    protected boolean If_cantEat()
    {
        return toxic;
    }
    
    protected Field getField()
    {
        return field;
    }
   
    protected boolean cantEat()
    {
        toxic=true;
        return toxic;
    }
    protected boolean canEat()
    {
        eatable = true;
        return eatable;
    }    
    abstract public void act_Sunny(List<Plant> newPlants);
    abstract public void act_Rainy(List<Plant> newPlants);
    

}
