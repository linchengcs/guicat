package examples.test;

import catg.CATG;
import janala.Main;

public class Testme {
    String name;
    int age = 1;

    public  int max (int x, int y) {        return x > y ? x : y;    }

    public static void print(String s) {System.out.println(s);}

    public static void main(String[] args) {

        String o = CATG.readString("o");
        String  display = o;
        if (o.equals("oliver") ) {
            System.out.println(display);
        } else {
            System.out.println(display);
        }


        String myName;
        myName = CATG.readString("0");
        int i = Integer.parseInt(myName);
        //int i = myName.length();
        //int i = CATG.readInt(1);
        if (i > 3) {
            print("----------->>>>>>>>>>  adult: " + i);
        } else {
            print("----------->>>>>>>>>>child: " + i);
        }

    }
}
