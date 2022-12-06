package adminmenu;
import java.util.Scanner;
import java.util.ArrayList;

public class AdminSystem {

    private static final String SEPARATER = "------------------------------";
    static Scanner keyboardInput; // Define the scanner used to get user input
    public enum Choice {
        Choice1, Choice2, Choice3, Choice4, Choice5
    } // This enum variable defines the choices user may make in the UI
    
    // The method below is used to enable user make choices in UI, number of choices depends on number of functions involved
    public static Choice makeChoice (int numberOfChoices) {
        System.out.print ("Type 1: first choice");
        System.out.print (" Type 2: second choice");
        if (numberOfChoices >= 3) {
            System.out.print (" Type 3: third choice");
            if (numberOfChoices >= 4) {
                System.out.print (" Type 4: fourth choice");
                if (numberOfChoices == 5)
                   System.out.print (" Type 5: fifth choice"); 
            }
        } // Send reminder to the user, the remainder is sent according to number of choices involved in the current menu
        System.out.print ("\nYour choice is: ");
        String inputChoice = keyboardInput.nextLine(); // Get input choice from user
        if (inputChoice.equals("1"))  
            return Choice.Choice1; 
        else if (inputChoice.equals("2"))
            return Choice.Choice2;
        else if ((inputChoice.equals("3")) && (numberOfChoices >= 3)) 
            return Choice.Choice3;
        else if  ((inputChoice.equals("4")) && (numberOfChoices >= 4))
            return Choice.Choice4;
        else if ((inputChoice.equals("5")) && (numberOfChoices == 5))
            return Choice.Choice5;
        // Give value to the enum variable according to what the user typed in
        // Some of the choices can be made only when the number of choices is large enough
        else { // If the user input is not in the valid range
            System.out.println("Invalid input, please try again"); // Sand warning to the user
            return makeChoice (numberOfChoices); // The method calls itself to let the user do input again
        }
    }
    
    // The method below is used get a float number from user
    public static double getDoubleFromKeyboard () {
        String inputFromKeyboard = keyboardInput.nextLine();
        try {
            return Double.parseDouble(inputFromKeyboard); // If the input from user is float number, just return it
        }
        catch (NumberFormatException ex) { // If the user input is invalid, catch the exception
            System.out.println ("Invalid input, please type in a float number"); // Send warning to the user
            return getDoubleFromKeyboard (); // The method calls itself recursively to ensure the user makes valid input
        }
    }
    
    // The method below is used to get an integer number from user
    public static int getIntFromKeyboard () {
        String inputFromKeyboard = keyboardInput.nextLine();
        try {
            return Integer.parseInt(inputFromKeyboard); // If the input from user is integer number, just return it
        }
        catch (NumberFormatException ex) { // If the user input is invalid, catch the exception
            System.out.println ("Invalid input, please type in an integer"); // Send warning to the user
            return getIntFromKeyboard (); // The method calls itself recursively to ensure the user makes valid input
        }
    }
    
    // The method below is a menu used to let the user make choices
    public static void customerRegistrationMenu (MenuOfProducts menuOfCoffeeShop) {
        System.out.println("Welecome to registration menu, please choose what to do");
        System.out.println("Choice 1: Add new registration");
        System.out.println("Choice 2: Remove existing registration");
        System.out.println("Choice 3: Go to admin main menu");
        Choice choice = makeChoice (3); // Call the pre-defined method to let the user make choice
        if (choice == Choice.Choice3) // The program reacts according to the choice made by the user
           adminMainMenu (menuOfCoffeeShop);
    }
    
    // The method below is used to check the remaining number of a product according to the input ID number
    public static void checkRemainingNumberOfProduct (MenuOfProducts menuOfCoffeeShop) {
        System.out.println("Type 1: Check according to Name, Type 2: Check according to ID");
        Choice choice = makeChoice (2); 
        int numberOfRemainingProduct;
        if (choice == Choice.Choice1) {
            System.out.println("Please input the name of product you are going to check remaining number");
            String name = keyboardInput.nextLine();
            numberOfRemainingProduct = menuOfCoffeeShop.checkAttribute ("remainingNumber",name, "");
        }
        else {
            System.out.println("Please input the ID of product you are going to check remaining number");
            String id = keyboardInput.nextLine();
            numberOfRemainingProduct = menuOfCoffeeShop.checkAttribute ("remainingNumber", "", id);
        }
        System.out.println("The remaining number of the chosen product is: " + numberOfRemainingProduct);
        inventaryCheckMenu (menuOfCoffeeShop);
    }
    
    public static void addDiscount (MenuOfProducts menuOfCoffeeShop) {
        System.out.println("Type 1: Add according to Name, Type 2: Add according to ID");
        Choice choice = makeChoice (2);
        String name = "";
        String id = "";
        if (choice == Choice.Choice1) {
            System.out.println("Please input the name of product you are going to add discount");
            name = keyboardInput.nextLine();
        }
        else {
            System.out.println("Please input the ID of product you are going to add discount");
            id = keyboardInput.nextLine();
        }
        System.out.println("Please input the discount of product");
        double discount = getDoubleFromKeyboard ();
        while ((discount <= 0) || (discount >= 1)) {
            System.out.println("The discount must be larger than 0 and smaller than 1");
            discount = getDoubleFromKeyboard ();
        }
        boolean feedback;
        if (choice == Choice.Choice1)
            feedback = menuOfCoffeeShop.addDiscount(name,"", discount);
        else
            feedback = menuOfCoffeeShop.addDiscount("",id, discount);
        if (feedback == false) {
            System.out.println("The ID does not exist, please try again");
            addDiscount (menuOfCoffeeShop);
        }
        else
            inventaryCheckMenu (menuOfCoffeeShop);
    }
    
    public static void inventaryCheckMenu (MenuOfProducts menuOfCoffeeShop) {
        System.out.println("Welecome to inventary check menu, please choose what to do");
        System.out.println("Choice 1: Check the number of remaining products");
        System.out.println("Choice 2: Offer a discount");
        System.out.println("Choice 3: Go to admin main menu");
        Choice choice = makeChoice (3);
        if (choice == Choice.Choice1)
            checkRemainingNumberOfProduct (menuOfCoffeeShop);
        if (choice == Choice.Choice2)
            addDiscount (menuOfCoffeeShop);
        if (choice == Choice.Choice3)
           adminMainMenu (menuOfCoffeeShop);
    }
    
    public static void checkSales (MenuOfProducts menuOfCoffeeShop) {
        System.out.println("Type 1: Check according to Name, Type 2: Check according to ID");
        Choice choice = makeChoice (2);
        int sales;
        if (choice == Choice.Choice1) {
            System.out.println("Please input the name of product you are going to check sales");
            String name = keyboardInput.nextLine();
            sales = menuOfCoffeeShop.checkAttribute ("sales",name, "");
        }
        else {
            System.out.println("Please input the ID of product you are going to check sales");
            String id = keyboardInput.nextLine();
            sales = menuOfCoffeeShop.checkAttribute ("sales","", id);
        }
        System.out.println("The sales is: " + sales);
        inventaryAdjustmentMenu (menuOfCoffeeShop);
    }
    
    public static boolean checkIfIdContainsOnlyNumbers (String id) {
        for (int i = 0; i < id.length(); i ++) {
            if (!Character.isDigit(id.charAt(i)))
                return false;
        }
        return true;
    }
    public static void addInventory (MenuOfProducts menuOfCoffeeShop) {
        System.out.println("Please input the name of product you are going to add");
        String name = keyboardInput.nextLine();
        while (menuOfCoffeeShop.thisNameDoesNotExist(name) == false) {
            System.out.println ("The name already exists, please type in again");
            name = keyboardInput.nextLine();
        }    
        System.out.println("Please input the id of product you are going to add");
        String id = keyboardInput.nextLine();
        boolean idContainsOnlyNumbers = checkIfIdContainsOnlyNumbers (id);
        while ((menuOfCoffeeShop.thisIdDoesNotExist(id) == false) || (idContainsOnlyNumbers == false)) {
            if (menuOfCoffeeShop.thisIdDoesNotExist(id) == false)
                System.out.println ("The ID already exists, please type in again");
            if (idContainsOnlyNumbers == false)
                System.out.println ("The ID can only contain numbers, please type in again");
            id = keyboardInput.nextLine();
            idContainsOnlyNumbers = checkIfIdContainsOnlyNumbers (id);
        }    
        System.out.println("Please choose the type of product you are going to add");
        System.out.println("Choice 1: Coffee; Choice 2: Soft drink Choice 3: Food. ");
        Choice choice = makeChoice (3);
        Product.TypeOfProduct type;
        type = switch (choice) {
            case Choice1 -> Product.TypeOfProduct.Coffee;
            case Choice2 -> Product.TypeOfProduct.Softdrink;
            default -> Product.TypeOfProduct.Food;
        };
        System.out.println("Please input the price of product you are going to add");
        double price = getDoubleFromKeyboard ();
        System.out.println("Please input the remaining number of product you are going to add");
        int number = getIntFromKeyboard ();
        double discount = 0;
        int sales = 0;
        Product newProduct = new Product (name, id, type, price, number, discount, sales);
        System.out.println("The product you are going to add is:");
        String newProductInform = newProduct.toString();
        System.out.println(newProductInform);
        menuOfCoffeeShop.addProduct (newProduct);
        inventaryAdjustmentMenu (menuOfCoffeeShop);
    }
    
    public static void deleteDailyInventory (MenuOfProducts menuOfCoffeeShop) {
        System.out.println("Type 1: Check according to Name, Type 2: Check according to ID");
        Choice choice = makeChoice (2);
        if (choice == Choice.Choice1) {
            System.out.println("Please input the name of product you are going to delete");
            String name = keyboardInput.nextLine();
            menuOfCoffeeShop.deleteProduct(name, "");
        }
        else {
            System.out.println("Please input the ID of product you are going to delete");
            String id = keyboardInput.nextLine();
            menuOfCoffeeShop.deleteProduct("", id);
        }
        inventaryAdjustmentMenu (menuOfCoffeeShop);
    }
    
    public static void changeProductNumber (MenuOfProducts menuOfCoffeeShop) {
        System.out.println("Type 1: Change according to Name, Type 2: Change according to ID");
        Choice choice = makeChoice (2);
        String name = "";
        String id = "";
        if (choice == Choice.Choice1) {
            System.out.println("Please input the name of product you are going to change number");
            name = keyboardInput.nextLine();
        }
        else {
            System.out.println("Please input the ID of product you are going to change number");
            id = keyboardInput.nextLine();
        }
        System.out.println("Please input the new number of product");
        int newNumber = getIntFromKeyboard ();
        while ((newNumber < 0) || (newNumber > Product.MAXIMUM_NUMBER)) {
            System.out.println("The number should not be negative or larger than maximum number");
            newNumber = getIntFromKeyboard ();
        }
        boolean feedback;
        if (choice == Choice.Choice1)
            feedback = menuOfCoffeeShop.modifyNumber(name, "", newNumber);
        else
            feedback = menuOfCoffeeShop.modifyNumber("", id, newNumber);
        if (feedback == false) {
            System.out.println("The ID does not exist, please try again");
            addDiscount (menuOfCoffeeShop);
        }
        else
            inventaryAdjustmentMenu (menuOfCoffeeShop);
    }
    
    public static void inventaryAdjustmentMenu (MenuOfProducts menuOfCoffeeShop) {
        System.out.println("Welecome to inventary adjustment menu, please choose what to do");
        System.out.println("Choice 1: Check number of product sold to date this year");
        System.out.println("Choice 2: Add product to daily inventary");
        System.out.println("Choice 3: Delete product from inventary");
        System.out.println("Choice 4: Change number of product in inventory");
        System.out.println("Choice 5: Go to admin main menu");
        Choice choice = makeChoice (5);
        if (choice == Choice.Choice1)
            checkSales (menuOfCoffeeShop);
        if (choice == Choice.Choice2)
            addInventory (menuOfCoffeeShop);
        if (choice == Choice.Choice3)
            deleteDailyInventory (menuOfCoffeeShop);
        if (choice == Choice.Choice4)
            changeProductNumber (menuOfCoffeeShop);
        if (choice == Choice.Choice5)
           adminMainMenu (menuOfCoffeeShop);
    }
    
    public static void reservationCheckMenu (MenuOfProducts menuOfCoffeeShop) {
        System.out.println("Welecome to reservation check menu, please choose what to do");
        System.out.println ("Choice 1: Check existing reservations");
        System.out.println ("Choice 2: Add new researvation");
        System.out.println ("Choice 3: Cancel existing reservation");
        System.out.println ("Choice 4: Go to admin main menu");
        Choice choice = makeChoice (4);
        if (choice == Choice.Choice4)
           adminMainMenu (menuOfCoffeeShop);
    }
    
    // This is the main menu of the administration page
    public static void adminMainMenu (MenuOfProducts menuOfCoffeeShop) {
        System.out.println(SEPARATER);
        System.out.println("Welecome to admin main menu!");
        System.out.println(SEPARATER);
        System.out.println ("Please choose what to do.");
        System.out.println ("1: Go to customer registration menu");
        System.out.println ("2: Go to inventary check menu");
        System.out.println ("3: Go to inventary adjustment menu");
        System.out.println ("4: Go to reservation check menu");
        System.out.println ("5: Exit the system");
        Choice choice = makeChoice (5);
        switch (choice) {
            case Choice1 -> customerRegistrationMenu (menuOfCoffeeShop);
            case Choice2 -> inventaryCheckMenu (menuOfCoffeeShop);
            case Choice3 -> inventaryAdjustmentMenu (menuOfCoffeeShop);
            case Choice4 -> reservationCheckMenu (menuOfCoffeeShop);
            default -> System.exit(0);
        }
    }
    
    public static void startAdminMenu() {
        ArrayList <Product> menu = new ArrayList<>();
        MenuOfProducts menuOfCoffeeShop = new MenuOfProducts (menu);
        keyboardInput = new Scanner (System.in); 
        adminMainMenu (menuOfCoffeeShop);
    }
}
