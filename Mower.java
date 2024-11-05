package LAWNMOWERMAN;

public class Mower
{
    private bool power;
    private State direction;
    public static int location[] = {20,20}; 

    public boolean PowerOn()
    {
        

        if (!power)
        {
            power = true;
            Toast.makeText(context, "Power is now on.", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            Toast.makeText(context, "Power is already on.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean PowerOff()
    {
        if (power)
        {
            power = false;
            Toast.makeText(context, "Power is now off.", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            Toast.makeText(context, "Power is already off.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //Forward 
    //Cut
    //turnRight
    //turnLeft

}