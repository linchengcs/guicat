package examples.test;

import catg.CATG;

public class Testme {
    String name;
    int age = 1;

    public  int max (int x, int y) {        return x > y ? x : y;    }

    public static void print(String s) {System.out.println(s);}

    public static void main(String[] args) {

        String myName;
                myName = CATG.readString("0");
                int i = Integer.parseInt(myName);
        //int i = CATG.readInt(1);
        if (i > 12) {
            print(">>>>>>adult: " + i);
        } else {
            print(">>>>>>child: " + i);
        }
    }
}
