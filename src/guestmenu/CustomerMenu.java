package guestmenu;

import guestmenu.dao.DrinksDAO;
import guestmenu.dao.DrinksDAOImpl;
import guestmenu.dao.FoodDAO;
import guestmenu.dao.FoodDAOImpl;

import java.util.ArrayList;
import java.util.Scanner;

public class CustomerMenu {
    static Scanner keyboardInput; // Define the scanner used to get user input
    public enum Choice {
        Choice1, Choice2, Choice3, Choice4, Choice5
    } // This enum variable defines the choices user may make in the UI
    public static CustomerMenu.Choice makeChoice (int numberOfChoices) {
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
            return CustomerMenu.Choice.Choice1;
        else if (inputChoice.equals("2"))
            return CustomerMenu.Choice.Choice2;
        else if ((inputChoice.equals("3")) && (numberOfChoices >= 3))
            return CustomerMenu.Choice.Choice3;
        else if  ((inputChoice.equals("4")) && (numberOfChoices >= 4))
            return CustomerMenu.Choice.Choice4;
        else if ((inputChoice.equals("5")) && (numberOfChoices == 5))
            return CustomerMenu.Choice.Choice5;
            // Give value to the enum variable according to what the user typed in
            // Some of the choices can be made only when the number of choices is large enough
        else { // If the user input is not in the valid range
            System.out.println("Invalid input, please try again"); // Sand warning to the user
            return makeChoice (numberOfChoices); // The method calls itself to let the user do input again
        }
    }

    public static void customerRegistrationMenu (MenuOfBuyProduce menuOfBuyProduce) {
        System.out.println("Welecome to food and/or drinks menu, please choose what to do");
        System.out.println("Choice 1: buy food");
        System.out.println("Choice 2: buy drinks");
        System.out.println("Choice 3: Go to customer main menu");
        CustomerMenu.Choice choice = makeChoice (3); // Call the pre-defined method to let the user make choice
        switch (choice) {
            case Choice1 -> buyFoodMenu (menuOfBuyProduce);
            case Choice2 -> buyDrinksMenu(menuOfBuyProduce);
            case Choice3 -> customerMainMenu(menuOfBuyProduce);
        }
    }
    public static  void buyFoodMenu(MenuOfBuyProduce menuOfBuyProduce) {
        showAllFood();

    }
    public static  void buyDrinksMenu(MenuOfBuyProduce menuOfBuyProduce) {
        showAllDrinks();

    }
    public static void showAllFood() {
        try{
            FoodDAO foodDAO = new FoodDAOImpl();
            ArrayList<Food> foodList= foodDAO.getAllFood();
            System.out.println("\t"+"id"+"\t"+"name"+"\t"+"price"+"\t"+"type"+"\t");
            for (Food food: foodList) {
                System.out.println("\t"+food.getId()+"\t"+food.getName()+"\t"+food.getPrice()+"\t"+food.getType()+"\t");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void showAllDrinks() {
        try {
            DrinksDAO drinksDAO = new DrinksDAOImpl();
            ArrayList<Drinks> drinksList= drinksDAO.getAllDrinks();
            System.out.println("\t"+"id"+"\t"+"name"+"\t"+"price"+"\t"+"type"+"\t");
            for (Drinks drinks: drinksList) {
                System.out.println("\t"+drinks.getId()+"\t"+drinks.getName()+"\t"+drinks.getPrice()+"\t"+drinks.getType()+"\t");
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public static void customerMainMenu (MenuOfBuyProduce menuOfBuyProduce) {
        System.out.println ("Welecome to customer main menu, please choose what to do");
        System.out.println ("Choice 1: Go to buy food and/or drinks menu");
        System.out.println ("Choice 2: Reserve the private room");
        System.out.println ("Choice 3: Un-register Menu");
        System.out.println ("Choice 4: Exit the system");
        CustomerMenu.Choice choice = makeChoice (4);
        switch (choice) {
            case Choice1 -> buyFoodDrinksMenu (menuOfBuyProduce);
            case Choice2 -> ReserveRoomMenu ();
            case Choice3 -> UnregisterMenu ();
            default -> System.exit(0);
        }
    }
    public static void buyFoodDrinksMenu(MenuOfBuyProduce menuOfBuyProduce) {
        customerRegistrationMenu(menuOfBuyProduce);
    }
    public static void ReserveRoomMenu() {
        System.out.println("2");
    }
    public static void UnregisterMenu(){
        System.out.println("3");
    }


    public static void main(String[] args) {
        keyboardInput = new Scanner (System.in);
        ArrayList<Produce> selectedProduces = new ArrayList<Produce>();
        MenuOfBuyProduce menuOfBuyProduce= new MenuOfBuyProduce(selectedProduces);
        customerMainMenu(menuOfBuyProduce);
    }
}
