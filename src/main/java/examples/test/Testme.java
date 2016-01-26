package examples.test;

import catg.CATG;

public class Testme {
    String name;
    int age = 1;

    public  int max (int x, int y) {
        return x > y ? x : y;
    }

    public static void main(String[] args) {
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

        myName = CATG.readString("");
        System.out.println(myName.length() < 3 ? "wrong name" : "right name");

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

    }
}
