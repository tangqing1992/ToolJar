package www.tq.weather.unit;

public class DataUnit {

    /*向上取整 四舍五入*/
    public static  String getvalueCeil(double value,int digit){
        try {
            int intvalue = 1;
            for (int i =0;i<digit;i++){
                intvalue = intvalue*10;
            }
            return (Math.ceil(value*intvalue))/intvalue +"";
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  null;
    }

    /*向下取整 只取对应位数*/
    public static  String getvalueFloor(double value,int digit){
        try {
            int intvalue = 1;
            for (int i =0;i<digit;i++){
                intvalue = intvalue*10;
            }
            return (Math.floor(value*intvalue))/intvalue +"";
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  null;
    }



    /*向上取整 四舍五入*/
    public static  String getvalueCeil(float value,int digit){
        try {
            int intvalue = 1;
            for (int i =0;i<digit;i++){
                intvalue = intvalue*10;
            }
            return (Math.ceil(value*intvalue))/intvalue +"";
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  null;
    }

    /*向下取整 只取对应位数*/
    public static  String getvalueFloor(float value,int digit){
        try {
            int intvalue = 1;
            for (int i =0;i<digit;i++){
                intvalue = intvalue*10;
            }
            return (Math.floor(value*intvalue))/intvalue +"";
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  null;
    }


    /*向上取整 四舍五入*/
    public static  String getvalueCeil(long value,int digit){
        try {
            int intvalue = 1;
            for (int i =0;i<digit;i++){
                intvalue = intvalue*10;
            }
            return (Math.ceil(value*intvalue))/intvalue +"";
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  null;
    }

    /*向下取整 只取对应位数*/
    public static  String getvalueFloor(long value,int digit){
        try {
            int intvalue = 1;
            for (int i =0;i<digit;i++){
                intvalue = intvalue*10;
            }
            return (Math.floor(value*intvalue))/intvalue +"";
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  null;
    }
}
