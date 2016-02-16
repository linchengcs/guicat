package examples.test;

import catg.CATG;
import janala.Main;

public class Testme {
    String name;
    int age = 1;

    public  int max (int x, int y) {
        return x > y ? x : y;
    }


    public static void main(String[] args) throws NumberFormatException {
        /*
          Testme t = new Testme();
          t.name = "oliver";
          //        t.age = 1;
          t.age = t.max(1,2);
          int z = t.age;
          t.name=CATG.readString("");
          t.age=CATG.readInt(3);
          if (t.name.equals("oliver"))
          if (t.age == 1)
          ;
        */
        String myName;
        //                String myAge = ageInput.getText();

        /*
        myName = CATG.readString("11") ;
        if (myName == "" || myName == null) myName += "0";
        System.out.println("myName = " + myName +"end");
        */

        //myName = CATG.readString("11");

        myName = Main.readString("11");
        int t = Integer.parseInt(myName);
        //t = CATG.readInt(11);


        //int t = 11;
        //t = Main.readInt(t);
        //Main.isInputAvailable();
        //Main.MakeSymbolic(t);
        //int t = Main.readInt(11);
        //        int t = CATG.readInt(11);


        //Main.Assume(t <= Integer.MAX_VALUE ? 1 : 0);
        //Main.Assume(t >= Integer.MIN_VALUE ? 1 : 0);
        //myName = CATG.readString("11");
        //myName = "11";
        //int t = myName.length();
        System.out.println((t < 5 ? "less then 10" : "greater equal than 10") + " , t = " + t);
        //System.out.println(myName.length() < 3 ? "wrong name" : "right name");


        /*

          int myAge = CATG.readInt(0);
          if ( myAge < 12){
          ;
          }
          System.out.println(myAge < 12 ? "child" : "people");
          String gender = CATG.readString("");
          System.out.println(gender.equals("male") ? "male" : "female");

          int[] myTicketType = {0,1,2};
          int index = CATG.readInt(0);
          if (myTicketType[index] == 1 )
          System.out.println("your are taking flight");
        */
    }
}
