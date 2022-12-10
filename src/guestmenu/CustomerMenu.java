package guestmenu;

import common.CheckUtils;
import common.dto.RegisterInfoDto;
import common.dto.UserDto;
import guestmenu.dao.DrinksDAO;
import guestmenu.dao.DrinksDAOImpl;
import guestmenu.dao.FoodDAO;
import guestmenu.dao.FoodDAOImpl;
import login.dao.UserDAO;
import login.dao.UserDAOImpl;
import login.dao.UserLoginDAO;
import login.dao.UserLoginDAOImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerMenu {
    static Scanner keyboardInput; // Define the scanner used to get user input
    private String getOption() {
        System.out.println("Enter 1 to retry, enter 2 to go back.");
        System.out.print("[1/2]: ");
        String yourChoice = keyboardInput.nextLine().strip();

        if (!CheckUtils.isValidChoice(yourChoice, 1, 2)) {
            System.out.println("Please enter 1 or 2.");
            System.out.println();
            return getOption();
        }
        return yourChoice;
    }
    public static void buyFoodDrinksMenu (ArrayList<ShoppingCart> shoppingCartList,RegisterInfoDto registerInfo) {
        System.out.println("Welecome to food and/or drinks menu, please choose what to do");
        System.out.println("1: buy food");
        System.out.println("2: buy drinks");
        System.out.println("3: show shopping cart");
        System.out.println("4: Go to customer main menu");
        System.out.print("Your choice is: ");
        String input = keyboardInput.nextLine().strip();

        if (!CheckUtils.isValidChoice(input, 1, 4)) {
            System.out.println("Please enter 1 to 4.");
            System.out.println();
            buyFoodDrinksMenu(shoppingCartList,registerInfo);
        }

        switch (input) {
            case "1" -> buyFoodMenu (shoppingCartList,registerInfo);
            case "2" -> buyDrinksMenu(shoppingCartList,registerInfo);
            case "3" -> shoppingCart(shoppingCartList,registerInfo);
            case "4" -> customerMainMenu(shoppingCartList,"1",true);
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
    public static boolean isExistFoodName (String name, ArrayList<Food> foodList) {
        for (Produce i : foodList) {
            if (i.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public static Drinks searchDrinksByName (String name, ArrayList<Drinks> drinksList) {
        for (Drinks drinks : drinksList) {
            if (drinks.getName().equals(name)) {
                return drinks;
            }
        }
        return null;
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
    public static Drinks getDrinksByName(ArrayList<Drinks> drinksList, String name) {
        for (Drinks drinks: drinksList) {
            if (drinks.getName().equals(name)) {
                return drinks;
            }
        }
        return null;
    }
    public static Boolean isUsedVipStatus(RegisterInfoDto registerInfo) {
        boolean vipStatus = registerInfo.getVipStatus();
        if(vipStatus) {
            System.out.println("Do you want to use your vip status or get one stamp");
            System.out.println("1: use my vip ");
            System.out.println("2: get one stamp");
            String choice = enterChoice(1,2);
            if(choice.equals("1")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    public static void buyProduces (Produce produce, int number, ArrayList<ShoppingCart> shoppingCartList, RegisterInfoDto registerInfo) {
        Boolean isUseVipStatus = false;
        Boolean isRegistered = false;
        if(registerInfo != null) {
            isRegistered = true;
            if(registerInfo.getVipStatus()) {
                isUseVipStatus = isUsedVipStatus(registerInfo);
            }
        }

        System.out.println("Order Information");
        System.out.println("name"+"\t"+"number"+"\t");
        System.out.println(produce.getName()+"\t"+number+"\t");
        double discount = isUseVipStatus && isRegistered? 0.95 : 1.0;
        double totalPrice = produce.getPrice() * number * discount;
        System.out.println("You need to pay:" + totalPrice);
        System.out.println("Do you want to pay the money?please choose to do");
        System.out.println("1: pay the money");
        System.out.println("2: cancel and go to the main menu");
        String choice = enterChoice(1,2);
        switch (choice) {
            case "1" -> payMoney (totalPrice,shoppingCartList,registerInfo,isUseVipStatus);
            case "2" -> customerMainMenu(shoppingCartList,"1",true);
        }
    }
    public static void buyAllProducesInShoppingCart(ArrayList<ShoppingCart> shoppingCartList,RegisterInfoDto registerInfo) {
        Boolean isUseVipStatus = false;
        Boolean isRegistered = false;
        if(registerInfo != null) {
            isRegistered = true;
            if(registerInfo.getVipStatus()) {
                isUseVipStatus = isUsedVipStatus(registerInfo);
            }
        }
        double discount = isUseVipStatus && isRegistered? 0.95 : 1.0;
        System.out.println("Order Information");
        double totalPrice = 0;
        System.out.println("\t"+"name"+"\t"+"price"+"\t"+"number"+"\t");
        for(ShoppingCart shoppingCart: shoppingCartList) {
            String name = shoppingCart.getShoppingCartProduce().getName();
            double price = shoppingCart.getShoppingCartProduce().getPrice();
            int num = shoppingCart.getShoppingCartProduceNumber();
            totalPrice += price * num * discount;
            System.out.println(name+"\t"+price+"\t"+num);
        }
        System.out.println("You need to pay:" + totalPrice);
        System.out.println("Do you want to pay the money?please choose to do");
        System.out.println("1: pay the money");
        System.out.println("2: cancel and go to the main menu");
        String choice = enterChoice(1,2);
        switch (choice) {
            case "1" -> payMoney (totalPrice,shoppingCartList,registerInfo,isUseVipStatus);
            case "2" -> customerMainMenu(shoppingCartList,"1",true);
        }
    }
    public static RegisterInfoDto updateStamps(RegisterInfoDto registerInfoDto) {
        int stamps = registerInfoDto.getStamps();
        stamps+=1;
        UserLoginDAO userLoginDAO = new UserLoginDAOImpl();
        userLoginDAO.updateRegisterStamps(registerInfoDto.getId(),stamps);
        registerInfoDto = userLoginDAO.getRegisterInfo(registerInfoDto.getId());
        System.out.println("You are our Vip member now.");
        return registerInfoDto;
    }
    public static void payMoney(double totalPrice, ArrayList<ShoppingCart> shoppingCartList,RegisterInfoDto registerInfo,Boolean isUseVipStatus) {
        System.out.println("pay successfully!");
        if(isUseVipStatus == false && registerInfo != null) {
            registerInfo = updateStamps(registerInfo);
            if(registerInfo.getStamps()==10) {
                System.out.println("you can get a free drink after next purchase");
            }
            System.out.println("you get a new stamp now");
        }
        System.out.println("we will return to the main menu later");
        buyFoodDrinksMenu(shoppingCartList,registerInfo);

    }
    public static void addToShoppingCart(Produce produce, int number, ArrayList<ShoppingCart> shoppingCartList,RegisterInfoDto registerInfo) {
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
        buyFoodMenu(shoppingCartList,registerInfo);
    }
    public static  void buyFoodMenu(ArrayList<ShoppingCart> shoppingCartList,RegisterInfoDto registerInfo) {
        System.out.println("Welecome to buy food, please choose one type you want to eat");
        ArrayList<String> foodTypes = showFoodTypes(shoppingCartList);
        System.out.print ("\nplease input the id of the foodTypes: ");
        int id = getIntFromKeyboard()-1; // Get input choice from user
        String type = foodTypes.get(id);
        System.out.println("type: "+type);
        ArrayList<Food> foodList = searchFoodByType(type,shoppingCartList);
        System.out.println("please enter the Food Name you want to buy");
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
        System.out.println("1: buy food immediately");
        System.out.println("2: add to the shopping cart");
        System.out.println("3: return to buyFoodOrDrinksMenu");
        String choice = enterChoice(1,3);
        switch (choice) {
            case "1" -> buyProduces (food,number,shoppingCartList,registerInfo);
            case "2" -> addToShoppingCart(food,number,shoppingCartList,registerInfo);
            case "3" -> buyFoodDrinksMenu(shoppingCartList,registerInfo);
        }
    }
    public static String enterChoice(int min,int max) {
        System.out.print("Your choice is: ");
        String input = keyboardInput.nextLine().strip();
        if (!CheckUtils.isValidChoice(input, 1, max)) {
            System.out.println("Please enter 1 to "+max+".");
            System.out.println();
            return enterChoice(min,max);
        }
        return input;
    }
    public static  void buyDrinksMenu(ArrayList<ShoppingCart> shoppingCartList,RegisterInfoDto registerInfo) {
        System.out.println("Welecome to buy drinks, please choose one type you want to drink");
        ArrayList<String> drinksTypes = getDrinksTypes(shoppingCartList);
        String choice = enterChoice(1,drinksTypes.size());//??
        int id = Integer.valueOf(choice)-1; // Get input choice from user
        String type = drinksTypes.get(id);
        System.out.println("type: "+type);
        ArrayList<Drinks> drinksList = searchDrinksByType(type,shoppingCartList);
        System.out.println("please enter the Drinks Name you want to buy");
        String drinksName = getStringFromKeyboard();
        Drinks drinks = searchDrinksByName(drinksName,drinksList);
        while(searchDrinksByName(drinksName,drinksList)==null) {
            System.out.println("the Drinks Name can not be found, please input the drinks name again");
            drinksName = getStringFromKeyboard();
            drinks = searchDrinksByName(drinksName,drinksList);
        }

        System.out.println("please input the number of drinks you want to buy");
        int number = getIntFromKeyboard(); // Get input choice from user
        System.out.println("Do you want to buy the drinks immediately?please choose to do");
        System.out.println("1: buy food immediately");
        System.out.println("2: add to the shopping cart");
        System.out.println("3: return to buyFoodOrDrinksMenu");
        choice = enterChoice(1,3);
        switch (choice) {
            case "1" -> buyProduces(drinks, number, shoppingCartList, registerInfo);
            case "2" -> addToShoppingCart(drinks, number, shoppingCartList, registerInfo);
            case "3" -> buyFoodDrinksMenu(shoppingCartList, registerInfo);
        }
    }
    public static void shoppingCart(ArrayList<ShoppingCart> shoppingCartList,RegisterInfoDto registerInfo) {
        System.out.println("\t"+"name"+"\t"+"price"+"\t"+"number"+"\t");
        for(ShoppingCart shoppingCart: shoppingCartList) {
            String name = shoppingCart.getShoppingCartProduce().getName();
            double price = shoppingCart.getShoppingCartProduce().getPrice();
            int num = shoppingCart.getShoppingCartProduceNumber();
            System.out.println(name+"\t"+price+"\t"+num);
        }
        System.out.println("Do you want to buy the food immediately?pleas choose to do");
        System.out.println("1: buy all produces immediately");
        System.out.println("2: return to buyFoodOrDrinksMenu");
        String choice = enterChoice(1,2);
        switch (choice) {
            case "1" -> buyAllProducesInShoppingCart (shoppingCartList,registerInfo);
            case "2" -> customerMainMenu(shoppingCartList,"1",true);
        }
    }
    public static void returnBuyFoodMenu(ArrayList<ShoppingCart> shoppingCartList,RegisterInfoDto registerInfo) {
        System.out.println("please choose what to do");
        System.out.println("Choice 1: return to buyFoodMenu");
        String choice = enterChoice(1,1);
        switch (choice) {
            case "1" -> buyFoodMenu (shoppingCartList,registerInfo);
        }
    }
    public static void printFoodList(ArrayList<Food> foodList) {
        System.out.println("\t"+"id"+"\t"+"name"+"\t"+"price"+"\t"+"type"+"\t");
        for (Food food: foodList) {
            System.out.println("\t"+food.getId()+"\t"+food.getName()+"\t"+food.getPrice()+"\t"+food.getType()+"\t");
        }
    }
    public static void printDrinksList(ArrayList<Drinks> drinksList){
        System.out.println("\t"+"name"+"\t"+"price"+"\t"+"type"+"\t");
        for (Drinks drinks: drinksList) {
            System.out.println("\t"+drinks.getName()+"\t"+drinks.getPrice()+"\t"+drinks.getType()+"\t");
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
    public static ArrayList<String> getDrinksTypes(ArrayList<ShoppingCart> shoppingCartList) {
        try {
            DrinksDAO drinksDAO = new DrinksDAOImpl();
            ArrayList<String> drinksTypes = drinksDAO.getDrinksTypes();
            int index = 1;
            for(String type: drinksTypes) {
                System.out.println(index+"\t"+type);
                index++;
            }
            return drinksTypes;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void searchFood(ArrayList<ShoppingCart> shoppingCartList,RegisterInfoDto registerInfo) {
        System.out.println("Welcome to search food, please choose what to do");
        System.out.println("1: Search food by name");
        System.out.println("2: Search food by type");
        System.out.println("3: return to buyFoodMenu");
        String choice = enterChoice(1,3);
        switch (choice) {
            case "1" -> searchFoodByName (shoppingCartList);
            case "2" -> buyFoodMenu (shoppingCartList,registerInfo);
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
    }
    public static ArrayList<Food> searchFoodByType(String type, ArrayList<ShoppingCart> shoppingCartList) {
        try{
            FoodDAO foodDAO = new FoodDAOImpl();
            ArrayList<Food> foodList= foodDAO.getAllFoodByType(type);
            printFoodList(foodList);
            return foodList;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<Drinks> searchDrinksByType(String type, ArrayList<ShoppingCart> shoppingCartList) {
        try{
            DrinksDAO drinksDAO = new DrinksDAOImpl();
            ArrayList<Drinks> drinksList= drinksDAO.getAllDrinksByType(type);
            printDrinksList(drinksList);
            return drinksList;
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
    public static RegisterInfoDto getRegisterInfoById(String userId) {
        UserLoginDAO userLoginDAO = new UserLoginDAOImpl();
        RegisterInfoDto registerInfo = userLoginDAO.getRegisterInfo(userId);
        return registerInfo;
    }
    public static RegisterInfoDto updateVipStatus(String userId,boolean status) {
        UserLoginDAO userLoginDAO = new UserLoginDAOImpl();
        userLoginDAO.updateRegisterVipStatus(userId,status);
        RegisterInfoDto registerInfo = userLoginDAO.getRegisterInfo(userId);
        System.out.println("You are our Vip member now.");
        return registerInfo;
    }
    public static void remainUnchanged() {

    }
    public static void customerMainMenu (ArrayList<ShoppingCart> shoppingCartList,String userId, boolean isRegistered) {
        System.out.println("Welcome to customer main menu");
        RegisterInfoDto registerInfo = null;
        Boolean vipStatus = false;
        if(isRegistered) {
            registerInfo = getRegisterInfoById("1");
            System.out.println("your current loyalty status is : "+ registerInfo.getStamps());
            vipStatus = registerInfo.getVipStatus();
            if(!vipStatus) {
                System.out.println("Do you want to join us as a Vip member");
                System.out.println("1: Yes");
                System.out.println("2: No");
                String choice = enterChoice(1,2);
                if(choice.equals("1")) {
                    registerInfo = updateVipStatus (userId,true);
                } else {
                    remainUnchanged();
                }
            }
        }
        System.out.println ("Welcome to customer main menu, please choose what to do");
        System.out.println ("1: Go to buy food and/or drinks menu");
        System.out.println ("2: Reserve the private room");
        System.out.println ("3: Un-register Menu");
        System.out.println ("4: Exit the system");
        String choice = enterChoice(1,4);
        switch (choice) {
            case "1" -> buyFoodDrinksMenu (shoppingCartList,registerInfo);
            case "2" -> ReserveRoomMenu ();
            case "3" -> UnregisterMenu ();
            case "4" -> System.exit(0);
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
        customerMainMenu(shoppingCartList,"1",true);
    }
}
