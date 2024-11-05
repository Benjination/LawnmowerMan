package LAWNMOWERMAN;

public class Sensor
{
    private bool edgeReached;
    

    public isEdgeReached(int j)
    {
        if(j > Mower.location[1])
        {
            return true;
        }
        else 
            return false;
    }

    /* 
    public isBottomReached()
    {
        if(Mower.y + 1 > Lawn.yMax)
        {
            return true;
        }
        else 
            return false;
    }
    */


}