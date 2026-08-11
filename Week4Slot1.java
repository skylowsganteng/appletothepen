import java.util.Scanner;

public class Week4Slot1 {
    // no reserve keywords
    // no space
    // dont start w number
    // for a class it will be pascal case
    // happyBirthday = variable basically camel case
    // java is case sensitive like vv sesnitive like a karen HAHAHA
    // variable is a container to store value but only for specific type
    // must have a data type
    /*
     * type of data got 8 and 6 holds number which is bytes 1, short 2, int 4, long
     * 8 must have L at the end heh get it? L
     * float 4 (must have f at the end),
     * double 8(decimal ). Boolean 1-4 byte, char 2bytes
     */
    // declaring is giving the variable a name but no value
    // while initialize is basically putting in the value to (variable)

    public static void main(String[] args) {
        // basically we do /n hoho
        // System.out.print("hello world \n");
        // System.out.print("\"hello world\"");

        Scanner input = new Scanner(System.in);

        // ani mesti ada untuk input
        // mesti ada prompt text untuk user input
        // System.out.print("tell me the day: ");
        // String day = input.nextLine(); //nextLine is a method to capture string input
        // String day = input.nextLine();
        // System.out.println("day is: "+day);
        // for char we dont use .nextChar but for yang lain ada .next
        // System.out.print();

        // System.out.print("Enter number1: ");
        // String number1 = input.nextLine(); // if input 6
        // System.out.print("Enter number2: ");
        // String number2 = input.nextLine(); // if input 7
        // System.out.println(number1+number2);// jawapannya 67 pasal ya campur kanak2

        // System.out.println("Using parseInt() to convert String number into number");
        // // int numberInt1 = Integer.parseInt(number1);// if input 6
        // // int numberInt2 = Integer.parseInt(number2);// if input 4
        // // System.out.println(numberInt1+numberInt2);//jwpnnya 10
        // System.out.println(Integer.parseInt(number1)+Integer.parseInt(number2));
        // //basically this is the same w yang di atas
        // // but its more simple and simplified

        // String lucky = "5"; // parseInt only knows number not spelled out number
        // int luckyInt = Integer.parseInt(lucky); basically it cannot convert five only
        // 5
        // System.out.println(luckyInt);

        // System.err.print("Enter your fav number: ");
        // int fav = input.nextInt();
        // System.out.print("Enter your name: ");
        // String name = input.nextLine();
        // // the programme will still run but only the number will be entered but the
        // name is not entered

        // System.out.println(name+"! is your fav #: "+fav);

        // solution

        // System.err.print("Enter your fav number: ");
        // int fav = input.nextInt();
        // input.nextLine();
        // System.out.print("Enter your name: ");
        // String name = input.nextLine();
        // System.out.println(name+"! is your fav #: "+fav);

        // // solution 2
        // System.err.print("Enter your fav number: ");
        // int fav = Integer.parseInt(input.nextLine());
        // System.out.print("Enter your name: ");
        // String name = input.nextLine();
        // System.out.println(name+"! is your fav #: "+fav);

        // final double PI = 3.141567;
        // System.err.println(PI);

        // typecasting

        // // implicit typecasting
        // int number0 = 9;
        // double numberDouble =number0;

        // System.err.println(numberDouble);

        // // explicit typecasting
        // double price = 9.999;
        // int priceInt = (int) price;

        // System.out.println(priceInt);

            // char Alpha = 'A';
            // System.out.println(Alpha);
            // char tujuh = '7';
            // System.out.println((int) Alpha);
            // int beta = 98;
            // System.out.println((char) beta);

        // // typecast char to string
        // char delta = 'd';
        // String deltaStr = String.valueOf(delta);

        // // method 2 - most common and easy
        // String deltaStr2 = delta+"";

        // String item="cake";
        // double harga = 99.99;

        // System.out.println("The "+item+" is priced at $"+harga);
        // // rather than using this we can use another method as this tends to have
        // crazy ahh error if got error
        // System.out.printf("the %s is priced at $%.2f", item, harga);
        // basically this is to get  2 dec place 

        // System.out.printf("harganya %d",90);
        // System.out.printf("char %c", 'z');
        // System.out.printf("boolean %b", true);
        // System.out.printf("scientific notation %e", 245.5);

        // Q1

        System.out.print("Enter number1: ");
        int num1 = input.nextInt();
        System.out.print("Enter number2: ");
        int num2 = input.nextInt();
        int sum = num1 + num2;
        System.out.println("The addition of the two numnber is: "+sum);
        int multiplySum = sum*10;
        System.out.println("If you multiply the result of the adition by 10, the answer is: "+multiplySum);
        int multiplyDivide = multiplySum/3;
        System.out.println("If you divide the result of the multiplication by 3, the answer is: "+multiplyDivide);
        int divideSubtract = multiplyDivide - 10;
        System.out.println("If you subtract the result of the division by 10, the answer is: "+divideSubtract);



        // // Q2
        //  System.out.print("Enter your name: ");
        // String name = input.nextLine();
        // System.out.print("Enter your phone number: ");
        // String number = input.nextLine();
        // System.out.print("Enter your age: ");
        // String age = input.nextLine();
       
        // System.out.println("Hi "+name+"! Is your phone number "+number+"?. You are "+age+" years old.");
    

    }
}
