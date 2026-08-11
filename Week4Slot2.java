import java.util.Scanner;

public class Week4Slot2 {
    public static void main(String[] args) {
        // arithmatic operators

        int a = 10;
        int b = 8;
        int c = a*b;
        int d = c/b;

        System.out.println("a : "+a);
        System.out.println("b : "+b);
        System.out.println("c : a*b "+c);
        System.out.println("a : c/b "+d);
        int j = 9;
        System.out.println("j%2 = "+(j%2));
        System.out.println("b%2 = "+(b%2));

        // assignment operator 
        // when we pu m as 7 it is called ^^ so basically it is js assigning
        int m =  7;
        System.out.println("m: "+m);
        System.out.println("m*2: "+(m*2));
        System.out.println("m now: "+m);
        // m=m+9;
        m+=9; // so basically this and the one yang atas atu is the same but this one is a more simplified vers.
        System.out.println("m after m=m+9 is "+m);
        // if there is an equal sign the value is overwritten 

        // comparison operator 
        System.out.println("4<1?"+(4<1));
        System.out.println("4>1?"+(4>1));
        System.out.println("4>=4?"+(4>=4));
        System.out.println("4<=4?"+(4<=4));
        System.out.println("4<=10?"+(4<=10));
        System.out.println("100>=90?"+(100>=90)); //test for equality
        System.out.println("4==4?"+(4==4));
        System.out.println("4!=4?"+(4!=4));//primitive types
        
        // logical operator 
        // only three gate is use which is or, not, and and
        // boolean is for truth and false typa shi only

        boolean isAdult = true;
        boolean isYellowIc = false; 

        System.out.println("isAdult && isYellowIC = "+(isAdult && isYellowIc)); // && = logical AND
        System.out.println("isAdult || isYellowIC ="+(isAdult || isYellowIc)); // || = logical OR

        boolean isEligible = isAdult && isYellowIc;
        System.out.println("isEligible now : "+isEligible);
        // exclamation marl (!) is a NOT
        System.out.println("!isEligible now : "+(!isEligible));

        System.out.println("isEligible now : "+isEligible);


        
    }
    
}
