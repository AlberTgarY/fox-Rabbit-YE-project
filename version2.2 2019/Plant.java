import java.util.List;
/**
 * 在这里给出对类 plant 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public abstract class Plant
{
    // 实例变量 - 用你自己的变量替换下面的例子
    private boolean alive;
    private boolean eatable;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;

    /**
     * 类 plant 的对象的构造函数
     */
    
    public Plant(Field field, Location location)
    {
        alive = true;
        eatable = false;
        this.field = field;
        setLocation(location);
    }
    
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.placePla(this, newLocation);
    }
    protected boolean If_canEat()
    {
        return eatable;
    }
    protected Field getField()
    {
        return field;
    }
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    protected boolean canEat()
    {
        eatable = true;
        return eatable;
    }    
    abstract public void act_Sunny(List<Plant> newPlants);
            abstract public void act_Rainy(List<Plant> newPlants);
    protected Location getLocation()
    {
        return location;
    }
    protected boolean isAlive()
    {
        return alive;
    }

}
