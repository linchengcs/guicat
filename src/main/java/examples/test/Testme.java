package examples.test;

import catg.CATG;

public class Testme {
    String name;
    int age = 1;

    public  int max (int x, int y) {        return x > y ? x : y;    }

    public static void print(String s) {System.out.println(s);}

    public static void main(String[] args) {

        String s = CATG.readString("oliver");
        int i = CATG.readInt(1);
        MyModel mm = new MyModel(s, i);
        if (mm.check())
            mm.compute();
        /*
        String o = CATG.readString("o");
        String  display = "what ever";
        if (o.startsWith("oliver") ) {
            System.out.println(display + o);
        } else {
            System.out.println(display + o);
        }


        String myName;
        myName = CATG.readString("0");
        int i = Integer.parseInt(myName);
        //int i = myName.length();
        //int i = CATG.readInt(1);
        //if(myName.contains("lin")){
        if (i > 3) {
            print("----------->>>>>>>>>>  adult: " + i);
        } else {
            print("----------->>>>>>>>>>child: " + i);
        }
        */

    }
}

class MyModel {
    String s;
    int i;
    public MyModel(String s, int i) {
        this.s = s;
        this.i = i;
    }

    public boolean check() {
        if (s.equals("oliver")) {
            return true;
        }
        return false;
    }

    public int compute() {
        int x = 0;
        if (i > 10)
            x = 10;
        else
            x = 0;
        return x;
    }
}
