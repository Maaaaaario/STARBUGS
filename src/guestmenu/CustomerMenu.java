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

    public static void buyFoodDrinksMenu (ArrayList<ShoppingCart> shoppingCartList) {
        System.out.println("Welecome to food and/or drinks menu, please choose what to do");
        System.out.println("Choice 1: buy food");
        System.out.println("Choice 2: buy drinks");
        System.out.println("Choice 3: show shopping cart");
//        System.out.println("Choice 4: show history orders");
        System.out.println("Choice 5: Go to customer main menu");
        CustomerMenu.Choice choice = makeChoice (3); // Call the pre-defined method to let the user make choice
        switch (choice) {
            case Choice1 -> buyFoodMenu (shoppingCartList);
            case Choice2 -> buyDrinksMenu(shoppingCartList);
            case Choice3 -> shoppingCart(shoppingCartList);
            case Choice4 -> historyOrders(shoppingCartList);
            case Choice5 -> customerMainMenu(shoppingCartList);
        }
    }
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
    public static String getStringFromKeyboard(){
        String inputFromKeyboard = keyboardInput.nextLine();
        if(inputFromKeyboard.equals("")) {
            System.out.println ("Invalid input, please type in a name"); // Send warning to the user
            return getStringFromKeyboard (); // The method calls itself recursively to ensure the user makes valid input
        }
        return inputFromKeyboard;
    }
    public static boolean isExistFoodName (String newName, ArrayList<Food> foodList) {
        for (Produce i : foodList) {
            if (i.getName().equals(newName)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isEnoughInventory (String newName, ArrayList<Food> foodList, int inventory) {
        for (Food i : foodList) {
            if (i.getName().equals(newName)) {
                if(i.getInventory()>=inventory) {
                    return true;
                }
            }
        }
        return false;
    }
    public static Food getFoodByName(ArrayList<Food> foodList, String name) {
        for (Food food: foodList) {
            if (food.getName().equals(name)) {
                return food;
            }
        }
        return null;
    }
    public static void buyFood (Food food, int number, ArrayList<ShoppingCart> shoppingCartList) {
        System.out.println("Order Information");
        System.out.println("name"+"\t"+"number"+"\t");
        System.out.println(food.getName()+"\t"+number+"\t");
        double totalPrice = food.getPrice() * number;
        System.out.println("You need to pay:" + totalPrice);
        System.out.println("Do you want to pay the money?please choose to do");
        System.out.println("Choice 1: pay the money");
        System.out.println("Choice 2: cancel and go to the main menu");
        CustomerMenu.Choice choice = makeChoice (2);
        switch (choice) {
            case Choice1 -> payMoney (totalPrice,shoppingCartList);
//            case Choice2 -> addToShoppingCart(food,shoppingCartList);
            case Choice5 -> customerMainMenu(shoppingCartList);
        }
    }
    public static void buyFoodInShoppingCart(ArrayList<ShoppingCart> shoppingCartList) {
        System.out.println("Order Information");
        double totalPrice = 0;
        System.out.println("\t"+"name"+"\t"+"price"+"\t"+"number"+"\t");
        for(ShoppingCart shoppingCart: shoppingCartList) {
            String name = shoppingCart.getShoppingCartProduce().getName();
            double price = shoppingCart.getShoppingCartProduce().getPrice();
            int num = shoppingCart.getShoppingCartProduceNumber();
            totalPrice += price * num;
            System.out.println(name+"\t"+price+"\t"+num);
        }
        System.out.println("You need to pay:" + totalPrice);
        System.out.println("Do you want to pay the money?please choose to do");
        System.out.println("Choice 1: pay the money");
        System.out.println("Choice 2: cancel and go to the main menu");
        CustomerMenu.Choice choice = makeChoice (2);
        switch (choice) {
            case Choice1 -> payMoney (totalPrice,shoppingCartList);
//            case Choice2 -> addToShoppingCart(food,shoppingCartList);
            case Choice2 -> customerMainMenu(shoppingCartList);
        }
    }
    public static void payMoney(double totalPrice, ArrayList<ShoppingCart> shoppingCartList) {
        System.out.println("pay successfully,we will return to the main menu later");
        buyFoodDrinksMenu(shoppingCartList);

    }
    public static void addToShoppingCart(Produce produce, int number, ArrayList<ShoppingCart> shoppingCartList) {
        ShoppingCart newShoppingCart = new ShoppingCart(produce,number);
        boolean flag = false;
        for(ShoppingCart shoppingCart: shoppingCartList) {
            if(shoppingCart.getShoppingCartProduce().getName().equals(produce.getName())) {
                shoppingCart.setShoppingCartProduceNumber(shoppingCart.getShoppingCartProduceNumber()+number);
                flag = true;
            }
        }
        if(!flag) {
            shoppingCartList.add(newShoppingCart);
        }
        System.out.println("add to shopping cart successfully");
        System.out.println("\t"+"name"+"\t"+"price"+"\t"+"number"+"\t");
        for(ShoppingCart shoppingCart: shoppingCartList) {
            String name = shoppingCart.getShoppingCartProduce().getName();
            double price = shoppingCart.getShoppingCartProduce().getPrice();
            int num = shoppingCart.getShoppingCartProduceNumber();
            System.out.println(name+"\t"+price+"\t"+num);
        }
        buyFoodMenu(shoppingCartList);
//        newShoppingCart.addShoppingCartProduce(produce,number,shoppingCart);
//        System.out.println("add to shopping cart successfully");
//        System.out.println("\t"+"name"+"\t"+"price"+"\t"+"number"+"\t");
//        for(Produce p: shoppingCart.shoppingCartProduces) {
//            System.out.println(p.getName()+"\t"+p.getPrice()+"\t");
//
//        }
    }
    public static  void buyFoodMenu(ArrayList<ShoppingCart> shoppingCartList) {
        System.out.println("Welecome to buy food, please choose one type you want to eat");
        ArrayList<String> foodTypes = showFoodTypes(shoppingCartList);
        System.out.print ("\nplease input the id of the foodTypes: ");
        int id = getIntFromKeyboard()-1; // Get input choice from user
        String type = foodTypes.get(id);
        System.out.println("type: "+type);
        ArrayList<Food> foodList = searchFoodByType(type,shoppingCartList);
        System.out.println("please choose some food you want to eat");
        System.out.println("please input the food name you want to buy");
        String foodName = getStringFromKeyboard();
        System.out.println(isExistFoodName(foodName,foodList));
        while(isExistFoodName(foodName,foodList) == false) {
            System.out.println("name error, please input the food name you want to buy again");
            foodName = getStringFromKeyboard();
            System.out.println(isExistFoodName(foodName,foodList));
        }

        System.out.println("foodName: "+foodName);
        System.out.println("please input the number of food you want to buy");
        int number = getIntFromKeyboard(); // Get input choice from user
        while(isEnoughInventory(foodName,foodList,number) == false) {
            System.out.println("number error, please input the proper number you want to buy again");
            number = getIntFromKeyboard(); // Get input choice from user
        }
        Food food = getFoodByName(foodList,foodName);
        System.out.println("Do you want to buy the food immediately or add all food to the shopping cart?pleas choose to do");
        System.out.println("Choice 1: buy food immediately");
        System.out.println("Choice 2: add to the shopping cart");
        System.out.println("Choice 3: return to buyFoodOrDrinksMenu");
        CustomerMenu.Choice choice = makeChoice (3);
        switch (choice) {
            case Choice1 -> buyFood (food,number,shoppingCartList);
            case Choice2 -> addToShoppingCart(food,number,shoppingCartList);
            case Choice3 -> buyFoodDrinksMenu(shoppingCartList);
        }


//        System.out.println("Choice 1: Show all food");
//        System.out.println("Choice 2: Show all food types");
//        System.out.println("Choice 1: Search the food by name or types");
//        System.out.println("Choice 2: buy the food by id or name");
//        System.out.println("Choice 3: return to buyFoodOrDrinksMenu");
//        CustomerMenu.Choice choice = makeChoice (3); // Call the pre-defined method to let the user make choice
//        switch (choice) {
//            case Choice1 -> showAllFood (shoppingCart);
//            case Choice2 -> showFoodTypes(shoppingCart);
//            case Choice3 -> searchFood(shoppingCart);
//        }

    }
    public static  void buyDrinksMenu(ArrayList<ShoppingCart> shoppingCartList) {
        showAllDrinks();

    }
    public static void shoppingCart(ArrayList<ShoppingCart> shoppingCartList) {
        System.out.println("\t"+"name"+"\t"+"price"+"\t"+"number"+"\t");
        for(ShoppingCart shoppingCart: shoppingCartList) {
            String name = shoppingCart.getShoppingCartProduce().getName();
            double price = shoppingCart.getShoppingCartProduce().getPrice();
            int num = shoppingCart.getShoppingCartProduceNumber();
            System.out.println(name+"\t"+price+"\t"+num);
        }
        System.out.println("Do you want to buy the food immediately?pleas choose to do");
        System.out.println("Choice 1: buy food immediately");
        System.out.println("Choice 2: return to buyFoodOrDrinksMenu");
        CustomerMenu.Choice choice = makeChoice (2);
        switch (choice) {
            case Choice1 -> buyFoodInShoppingCart (shoppingCartList);
            case Choice2 -> customerMainMenu(shoppingCartList);
        }
    }
    public static void historyOrders(ArrayList<ShoppingCart> shoppingCartList) {

    }
    public static void returnBuyFoodMenu(ArrayList<ShoppingCart> shoppingCartList) {
        System.out.println("please choose what to do");
        System.out.println("Choice 1: return to buyFoodMenu");
        CustomerMenu.Choice choice = makeChoice (1); // Call the pre-defined method to let the user make choice
        switch (choice) {
            case Choice1 -> buyFoodMenu (shoppingCartList);
        }
    }
    public static void printFoodList(ArrayList<Food> foodList) {
        System.out.println("\t"+"id"+"\t"+"name"+"\t"+"price"+"\t"+"type"+"\t");
        for (Food food: foodList) {
            System.out.println("\t"+food.getId()+"\t"+food.getName()+"\t"+food.getPrice()+"\t"+food.getType()+"\t");
        }
    }
    public static void showAllFood(ArrayList<ShoppingCart> shoppingCartList) {
        try{
            FoodDAO foodDAO = new FoodDAOImpl();
            ArrayList<Food> foodList= foodDAO.getAllFood();
            printFoodList(foodList);

            returnBuyFoodMenu(shoppingCartList);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<String> showFoodTypes(ArrayList<ShoppingCart> shoppingCartList) {
        try {
            FoodDAO foodDAO = new FoodDAOImpl();
            ArrayList<String> foodTypes = foodDAO.getFoodTypes();
            int index = 1;
            System.out.println("id"+"\t"+"Foodtypes"+"\t");
            for(String type: foodTypes) {
                System.out.println(index+"\t"+type);
                index++;
            }
//            returnBuyFoodMenu(shoppingCart);
            return foodTypes;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public static void searchFood(ArrayList<ShoppingCart> shoppingCartList) {
        System.out.println("Welcome to search food, please choose what to do");
        System.out.println("Choice 1: Search food by name");
        System.out.println("Choice 2: Search food by type");
        System.out.println("Choice 3: return to buyFoodMenu");
        CustomerMenu.Choice choice = makeChoice (3);
        switch (choice) {
            case Choice1 -> searchFoodByName (shoppingCartList);
//            case Choice2 -> searchFoodByType (shoppingCart);
            case Choice3 -> buyFoodMenu (shoppingCartList);
        }

    }
    public static void searchFoodByName(ArrayList<ShoppingCart> shoppingCartList) {
        System.out.println("Please input the name of food you are going to search");
        String name = keyboardInput.nextLine();
        try{
            FoodDAO foodDAO = new FoodDAOImpl();
            ArrayList<Food> foodList= foodDAO.getAllFoodByName(name);
            printFoodList(foodList);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



//        while (menuOfCoffeeShop.thisNameDoesNotExist(name) == false) {
//            System.out.println ("The name already exists, please type in again");
//            name = keyboardInput.nextLine();
//        }
    }
    public static ArrayList<Food> searchFoodByType(String type, ArrayList<ShoppingCart> shoppingCartList) {
//        System.out.println("Please input the name of food you are going to search");
//        String name = keyboardInput.nextLine();
        try{
            FoodDAO foodDAO = new FoodDAOImpl();
            ArrayList<Food> foodList= foodDAO.getAllFoodByType(type);
            printFoodList(foodList);
            return foodList;
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
    public static void customerMainMenu (ArrayList<ShoppingCart> shoppingCartList) {
        System.out.println ("Welcome to customer main menu, please choose what to do");
        System.out.println ("Choice 1: Go to buy food and/or drinks menu");
        System.out.println ("Choice 2: Reserve the private room");
        System.out.println ("Choice 3: Un-register Menu");
        System.out.println ("Choice 4: Exit the system");
        CustomerMenu.Choice choice = makeChoice (4);
        switch (choice) {
            case Choice1 -> buyFoodDrinksMenu (shoppingCartList);
            case Choice2 -> ReserveRoomMenu ();
            case Choice3 -> UnregisterMenu ();
            default -> System.exit(0);
        }
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
        ArrayList<ShoppingCart> shoppingCartList= new ArrayList<ShoppingCart>();
//        customerMainMenu(shoppingCart);
        buyFoodMenu(shoppingCartList);
    }
}
