package guestmenu;

import common.CheckUtils;
import common.dto.RegisterInfoDto;
import common.dto.UserDto;
import guestmenu.dao.DrinksDAO;
import guestmenu.dao.DrinksDAOImpl;
import guestmenu.dao.FoodDAO;
import guestmenu.dao.FoodDAOImpl;
import login.businesslogic.UserLogin;
import login.businesslogic.UserLoginImpl;
import login.dao.UserDAO;
import login.dao.UserDAOImpl;
import login.dao.UserLoginDAO;
import login.dao.UserLoginDAOImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerMenu {
    static Scanner keyboardInput; // Define the scanner used to get user input
    private ArrayList<ShoppingCart> shoppingCartList;
    private RegisterInfoDto registerInfo;

    private Boolean isRegistered;

    public CustomerMenu(ArrayList<ShoppingCart> shoppingCartList, RegisterInfoDto registerInfo, Boolean isRegistered) {
        this.shoppingCartList = shoppingCartList;
        this.registerInfo = registerInfo;
        this.isRegistered = isRegistered;
    }

    public void setRegistered(Boolean registered) {
        isRegistered = registered;
    }

    public void setRegisterInfo(RegisterInfoDto registerInfo) {
        this.registerInfo = registerInfo;
    }

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
    public void buyFoodDrinksMenu() {
        System.out.println("Welecome to food and/or drinks menu, please choose what to do");
        System.out.println("1: buy food");
        System.out.println("2: buy drinks");
        System.out.println("3: show shopping cart");
        if(isRegistered) {
            System.out.println(("4: get a free drink by using stamps"));
            System.out.println("5: Go to customer main menu");
        } else {
            System.out.println("4: Exit the system");
        }

        int options = isRegistered? 5 : 4;
        String choice = enterChoice(1,options);
        if(isRegistered) {
            switch (choice) {
                case "1" -> buyFoodMenu (null);
                case "2" -> buyDrinksMenu(null, false);
                case "3" -> shoppingCart();
                case "4" -> getFreeDrinks();
                case "5" -> customerMainMenu(registerInfo.getId());
            }
        } else {
            switch (choice) {
                case "1" -> buyFoodMenu (null);
                case "2" -> buyDrinksMenu(null, false);
                case "3" -> shoppingCart();
                case "4" -> returnToLoginInterface();
            }
        }

    }
    public void returnToLoginInterface() {
        UserLogin userLogin = new UserLoginImpl();
        userLogin.login();
    }
    public void getFreeDrinks() {
        System.out.println("Do you want to use 10 stamps to get a free drinks");
        System.out.println("1. yes");
        System.out.println("2. no");
        String choice = enterChoice(1,2);
        if(choice.equals("1")) {
            if(registerInfo.getStamps()<10) {
                System.out.println("you don't have 10 stamps,we will return to main menu");
                buyFoodDrinksMenu();
            } else {
                buyDrinksMenu(null,true);
            }
        } else {
            buyFoodDrinksMenu();
        }
    }
    public int getIntFromKeyboard () {
        String inputFromKeyboard = keyboardInput.nextLine();
        try {
            return Integer.parseInt(inputFromKeyboard); // If the input from user is integer number, just return it
        }
        catch (NumberFormatException ex) { // If the user input is invalid, catch the exception
            System.out.println ("Invalid input, please type in an integer"); // Send warning to the user
            return getIntFromKeyboard (); // The method calls itself recursively to ensure the user makes valid input
        }
    }
    public String getStringFromKeyboard(){
        String inputFromKeyboard = keyboardInput.nextLine();
        if(inputFromKeyboard.equals("")) {
            System.out.println ("Invalid input, please type in a name"); // Send warning to the user
            return getStringFromKeyboard (); // The method calls itself recursively to ensure the user makes valid input
        }
        return inputFromKeyboard;
    }
    public boolean isExistFoodName (String name, ArrayList<Food> foodList) {
        for (Produce i : foodList) {
            if (i.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public Drinks searchDrinksByName (String name, ArrayList<Drinks> drinksList) {
        for (Drinks drinks : drinksList) {
            if (drinks.getName().equals(name)) {
                return drinks;
            }
        }
        return null;
    }
    public boolean isEnoughInventory (String newName, ArrayList<Food> foodList, int inventory) {
        for (Food i : foodList) {
            if (i.getName().equals(newName)) {
                if(i.getInventory()>=inventory) {
                    return true;
                }
            }
        }
        return false;
    }
    public Food getFoodByName(ArrayList<Food> foodList, String name) {
        for (Food food: foodList) {
            if (food.getName().equals(name)) {
                return food;
            }
        }
        return null;
    }
    public Drinks getDrinksByName(ArrayList<Drinks> drinksList, String name) {
        for (Drinks drinks: drinksList) {
            if (drinks.getName().equals(name)) {
                return drinks;
            }
        }
        return null;
    }
    public Boolean isUsedVipStatus() {
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
    public void buyProduces (Produce produce, int number, String type) {
        Boolean isUseVipStatus = false;
        if(isRegistered) {
            if(registerInfo.getVipStatus()) {
                isUseVipStatus = isUsedVipStatus();
            }
        }

        System.out.println("Order Information");
        String formatInfo = printFormat(2);
        System.out.format(formatInfo,"name","number");
        System.out.println();
        System.out.format(formatInfo,produce.getName(),number);
        double discount = isUseVipStatus && isRegistered? 0.95 : 1.0;
        double totalPrice = produce.getPrice() * number * discount;
        System.out.println("You need to pay:" + totalPrice);
        System.out.println("Do you want to pay the money?please choose to do");
        System.out.println("1: pay the money");
        System.out.println("2: cancel and go to the BuyFoodODrinks menu");
        String choice = enterChoice(1,2);
        FoodDAO foodDAO = new FoodDAOImpl();
        DrinksDAO drinksDAO = new DrinksDAOImpl();
        if(choice.equals("1")) {
            if(type.equals("food")) {
                foodDAO.updateInventory(produce.getId(),number);
                foodDAO.updateSales(produce.getId(), number);
            } else {
                drinksDAO.updateSales(produce.getId(), number);
            }
        }
        switch (choice) {
            case "1" -> payMoney (totalPrice,isUseVipStatus);
            case "2" -> buyFoodDrinksMenu();
        }
    }
    public void buyAllProducesInShoppingCart() {
        Boolean isUseVipStatus = false;
        if(isRegistered) {
            if(registerInfo.getVipStatus()) {
                isUseVipStatus = isUsedVipStatus();
            }
        }
        double discount = isUseVipStatus && isRegistered? 0.95 : 1.0;
        System.out.println("Order Information");
        double totalPrice = 0;
        String formatInfo = printFormat(3);
        System.out.format(formatInfo,"name","price","number");
        System.out.println();
        for(ShoppingCart shoppingCart: shoppingCartList) {
            String name = shoppingCart.getShoppingCartProduce().getName();
            double price = shoppingCart.getShoppingCartProduce().getPrice();
            int num = shoppingCart.getShoppingCartProduceNumber();
            totalPrice += price * num * discount;
            System.out.format(formatInfo,name,price,num);
            System.out.println();
        }
        System.out.println("You need to pay:" + totalPrice);
        System.out.println("Do you want to pay the money?please choose to do");
        System.out.println("1: pay the money");
        System.out.println("2: cancel and go to the main menu");
        String choice = enterChoice(1,2);
        FoodDAO foodDAO = new FoodDAOImpl();
        DrinksDAO drinksDAO = new DrinksDAOImpl();
        if(choice.equals("1")) {
            for(ShoppingCart shoppingCart: shoppingCartList) {
                String name = shoppingCart.getShoppingCartProduce().getId();
                int num = shoppingCart.getShoppingCartProduceNumber();
                if(shoppingCart.getProduceType() == "food") {
                    foodDAO.updateInventory(shoppingCart.getShoppingCartProduce().getId(),shoppingCart.getShoppingCartProduceNumber());
                    foodDAO.updateSales(shoppingCart.getShoppingCartProduce().getId(),shoppingCart.getShoppingCartProduceNumber());
                } else {
                    drinksDAO.updateSales(shoppingCart.getShoppingCartProduce().getId(),shoppingCart.getShoppingCartProduceNumber());
                }
            }
            shoppingCartList.clear();
            payMoney (totalPrice,isUseVipStatus);

        } else {
            buyFoodDrinksMenu();
        }
    }
    public RegisterInfoDto updateStamps(RegisterInfoDto registerInfoDto) {
        int stamps = registerInfoDto.getStamps();
        stamps+=1;
        UserLoginDAO userLoginDAO = new UserLoginDAOImpl();
        userLoginDAO.updateRegisterStamps(registerInfoDto.getId(),stamps);
        registerInfoDto = userLoginDAO.getRegisterInfo(registerInfoDto.getId());
        System.out.println("You are our Vip member now.");
        return registerInfoDto;
    }
    public void payMoney(double totalPrice,Boolean isUseVipStatus) {
        System.out.println("pay successfully!");
        if(isUseVipStatus == false && registerInfo != null) {
            registerInfo = updateStamps(registerInfo);
            if(registerInfo.getStamps()==10) {
                System.out.println("you can get a free drink after next purchase");
            }
            System.out.println("you get a new stamp now");
        }
        System.out.println("we will return to the main menu later");
        buyFoodDrinksMenu();

    }
    public void addToShoppingCart(Produce produce, int number, String pageType, String produceType) {
        ShoppingCart newShoppingCart = new ShoppingCart(produce,number,pageType);
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
        if(pageType == "food") {
            buyFoodMenu(produceType);
        } else {
            buyDrinksMenu(produceType,false);
        }
    }
    public String chooseFoodType() {
        System.out.println("Welecome to buy food, please choose one type you want to eat");
        ArrayList<String> foodTypes = showFoodTypes();
        System.out.print ("\nplease input the number of the foodTypes: ");
        int id = getIntFromKeyboard()-1; // Get input choice from user
        String type = foodTypes.get(id);
//        System.out.println("type: "+type);
        return type;
    }
    public void buyFoodMenu(String foodType) {
        String type = foodType;
        if(type == null) {
            type = chooseFoodType();
        }
        ArrayList<Food> foodList = searchFoodByType(type);
        System.out.println("1: choose a new type again");
        System.out.println("2: choose the food name to buy");
        System.out.println("3: return to buyFoodOrDrinksMenu");
        String choice = enterChoice(1,3);
        if(choice.equals("1")) {
            type = chooseFoodType();
            buyFoodMenu(type);
        }
        if(choice.equals("3")) {
            buyFoodDrinksMenu();
        }
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
            System.out.println("inventory is not enough, please input the proper number you want to buy again");
            number = getIntFromKeyboard(); // Get input choice from user
        }
        Food food = getFoodByName(foodList,foodName);
        System.out.println("Do you want to buy the food immediately or add all food to the shopping cart?pleas choose to do");
        System.out.println("1: buy food immediately");
        System.out.println("2: add to the shopping cart");
        System.out.println("3: return to buyFoodOrDrinksMenu");
        choice = enterChoice(1,3);
        switch (choice) {
            case "1" -> buyProduces (food,number,"food");
            case "2" -> addToShoppingCart(food,number,"food",type);
            case "3" -> buyFoodDrinksMenu();
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
    public String chooseDrinksType() {
        System.out.println("Welecome to buy drinks, please choose one type you want to drink");
        ArrayList<String> drinksTypes = getDrinksTypes();
        System.out.print ("\nplease input the number of the foodTypes: ");
        int id = getIntFromKeyboard()-1; // Get input choice from user
        String type = drinksTypes.get(id);
//        System.out.println("type: "+type);
        return type;
    }
    public void buyDrinksMenu(String drinksType,Boolean isFree) {
        String type = drinksType;
        if(type == null) {
            type = chooseDrinksType();
        }
        ArrayList<Drinks> drinksList = searchDrinksByType(type);
        System.out.println("1: choose a new type again");
        System.out.println("2: choose the drink name to buy");
        System.out.println("3: return to buyFoodOrDrinksMenu");
        String choice = enterChoice(1,3);
        if(choice.equals("1")) {
            type = chooseDrinksType();
            buyDrinksMenu(type,isFree);
        }
        if(choice.equals("3")) {
            buyFoodDrinksMenu();
        }
        System.out.println("please enter the Drinks Name you want to buy");
        String drinksName = getStringFromKeyboard();
        Drinks drinks = searchDrinksByName(drinksName,drinksList);
        while(searchDrinksByName(drinksName,drinksList)==null) {
            System.out.println("the Drinks Name can not be found, please input the drinks name again");
            drinksName = getStringFromKeyboard();
            drinks = searchDrinksByName(drinksName,drinksList);
        }
        if(isFree) {
            System.out.println("you get a free drinks now,we give you a new card later");
            UserLoginDAO userLoginDAO = new UserLoginDAOImpl();
            userLoginDAO.updateRegisterStamps(registerInfo.getId(),0);
            registerInfo = userLoginDAO.getRegisterInfo(registerInfo.getId());
            buyFoodDrinksMenu();
        }
        System.out.println("please input the number of drinks you want to buy");
        int number = getIntFromKeyboard(); // Get input choice from user
        System.out.println("Do you want to buy the drinks immediately?please choose to do");
        System.out.println("1: buy drinks immediately");
        System.out.println("2: add to the shopping cart");
        System.out.println("3: return to buyFoodOrDrinksMenu");
        choice = enterChoice(1,3);
        switch (choice) {
            case "1" -> buyProduces(drinks, number, "drinks");
            case "2" -> addToShoppingCart(drinks, number, "drinks",type);
            case "3" -> buyFoodDrinksMenu();
        }
    }
    public void shoppingCart() {
        String userId = registerInfo != null ? registerInfo.getId() : null;
        if(shoppingCartList.size() == 0) {
            System.out.println("you don't add anythind in the shopping cart");
            System.out.println("please enter 1 to return");
            String choice = enterChoice(1,1);
            if(choice.equals("1")) {
                buyFoodDrinksMenu();
            }
        }
        String formatInfo = printFormat(3);
        System.out.format(formatInfo,"name","price","number");
        System.out.println();
        for(ShoppingCart shoppingCart: shoppingCartList) {
            String name = shoppingCart.getShoppingCartProduce().getName();
            double price = shoppingCart.getShoppingCartProduce().getPrice();
            int num = shoppingCart.getShoppingCartProduceNumber();
            System.out.format(formatInfo,name,price,num);
            System.out.println();
        }
        System.out.println("Do you want to buy the food immediately?pleas choose to do");
        System.out.println("1: buy all produces immediately");
        System.out.println("2: return to buyFoodOrDrinksMenu");
        String choice = enterChoice(1,2);

        switch (choice) {
            case "1" -> buyAllProducesInShoppingCart ();
            case "2" -> buyFoodDrinksMenu();
        }
    }
    public static String printFormat(int number) {
        String formatInfo = "";
        for(int i=0;i<number;i++) {
            formatInfo += "%-12s";
        }
        return formatInfo;
//        String column1Format = "%-12s";
//        String column2Format = "%-12s";
//        String column3Format = "%-12s";
//        String formatInfo = column1Format + column2Format + column3Format;
    }
    public void printFoodList(ArrayList<Food> foodList) {
        String formatInfo = printFormat(3);
        System.out.format(formatInfo,"name","price","type");
        System.out.println();
        for (Food food: foodList) {
            System.out.format(formatInfo, food.getName(),food.getPrice(),food.getType());
            System.out.println();
        }
    }
    public void printDrinksList(ArrayList<Drinks> drinksList){
        String formatInfo = printFormat(3);
        System.out.format(formatInfo,"name","price","type");
        System.out.println();
        for (Drinks drinks: drinksList) {
            System.out.format(formatInfo,drinks.getName(),drinks.getPrice(),drinks.getType());
            System.out.println();
        }
    }
    public ArrayList<String> showFoodTypes() {
        FoodDAO foodDAO = new FoodDAOImpl();
        ArrayList<String> foodTypes = foodDAO.getFoodTypes();
        int index = 1;
        String formatInfo = printFormat(2);
        System.out.format(formatInfo,"number","Foodtypes");
        System.out.println();
        for(String type: foodTypes) {
            System.out.format(formatInfo,index,type);
            System.out.println();
            index++;
        }
        return foodTypes;
    }
    public ArrayList<String> getDrinksTypes() {
        DrinksDAO drinksDAO = new DrinksDAOImpl();
        ArrayList<String> drinksTypes = drinksDAO.getDrinksTypes();
        int index = 1;
        String formatInfo = printFormat(2);
        System.out.format(formatInfo,"number","Foodtypes");
        System.out.println();
        for(String type: drinksTypes) {
            System.out.format(formatInfo,index,type);
            System.out.println();
            index++;
        }
        return drinksTypes;
    }
//    public static void searchFood(ArrayList<ShoppingCart> shoppingCartList,RegisterInfoDto registerInfo) {
//        System.out.println("Welcome to search food, please choose what to do");
//        System.out.println("1: Search food by name");
//        System.out.println("2: Search food by type");
//        System.out.println("3: return to buyFoodMenu");
//        String choice = enterChoice(1,3);
//        switch (choice) {
//            case "1" -> searchFoodByName (shoppingCartList);
//            case "2" -> buyFoodMenu (shoppingCartList,registerInfo);
//        }
//    }
//    public static void searchFoodByName(ArrayList<ShoppingCart> shoppingCartList) {
//        System.out.println("Please input the name of food you are going to search");
//        String name = keyboardInput.nextLine();
//        try{
//            FoodDAO foodDAO = new FoodDAOImpl();
//            ArrayList<Food> foodList= foodDAO.getAllFoodByName(name);
//            printFoodList(foodList);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public ArrayList<Food> searchFoodByType(String type) {
        FoodDAO foodDAO = new FoodDAOImpl();
        ArrayList<Food> foodList= foodDAO.getAllFoodByType(type);
        printFoodList(foodList);
        return foodList;
    }
    public ArrayList<Drinks> searchDrinksByType(String type) {
        DrinksDAO drinksDAO = new DrinksDAOImpl();
        ArrayList<Drinks> drinksList= drinksDAO.getAllDrinksByType(type);
        printDrinksList(drinksList);
        return drinksList;
    }
//    public static void showAllDrinks() {
//        DrinksDAO drinksDAO = new DrinksDAOImpl();
//        ArrayList<Drinks> drinksList= drinksDAO.getAllDrinks();
//        System.out.println("\t"+"id"+"\t"+"name"+"\t"+"price"+"\t"+"type"+"\t");
//        for (Drinks drinks: drinksList) {
//            System.out.println("\t"+drinks.getId()+"\t"+drinks.getName()+"\t"+drinks.getPrice()+"\t"+drinks.getType()+"\t");
//        }
//    }
    public RegisterInfoDto getRegisterInfoById(String userId) {
        UserLoginDAO userLoginDAO = new UserLoginDAOImpl();
        RegisterInfoDto registerInfo = userLoginDAO.getRegisterInfo(userId);
        return registerInfo;
    }
    public RegisterInfoDto updateVipStatus(String userId,boolean status) {
        UserLoginDAO userLoginDAO = new UserLoginDAOImpl();
        userLoginDAO.updateRegisterVipStatus(userId,status);
        RegisterInfoDto registerInfo = userLoginDAO.getRegisterInfo(userId);
        System.out.println("You are our Vip member now.");
        return registerInfo;
    }
    public void remainUnchanged() {

    }
    public void customerMainMenu (String userId) {
        System.out.println("Welcome to customer main menu");
        RegisterInfoDto registerInfo = null;
        Boolean vipStatus = false;
        if(isRegistered) {
            registerInfo = getRegisterInfoById(userId);
            setRegisterInfo(registerInfo);
            System.out.println("your current loyalty status is : "+ registerInfo.getStamps());
            vipStatus = registerInfo.getVipStatus();
            if(!vipStatus) {
                System.out.println("Do you want to join us as a Vip member");
                System.out.println("1: Yes");
                System.out.println("2: No");
                String choice = enterChoice(1,2);
                if(choice.equals("1")) {
                    System.out.println("Do you want to pay the money?please choose to do");
                    System.out.println("1: pay the money");
                    System.out.println("2: cancel and go to the customer menu");
                    choice = enterChoice(1,2);
                    if(choice.equals("1")) {
                        System.out.println("pay the money successfully");
                        registerInfo = updateVipStatus (userId,true);
                    }
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
            case "1" -> buyFoodDrinksMenu ();
            case "2" -> ReserveRoomMenu ();
            case "3" -> UnregisterMenu ();
            case "4" -> returnToLoginInterface();
        }
    }
    public void ReserveRoomMenu() {
        System.out.println("2");
    }
    public void UnregisterMenu(){
        System.out.println("3");
    }
    public void goToGuestMenu() {
        keyboardInput = new Scanner (System.in);

        buyFoodDrinksMenu();
    }

    public void goToCustomerMenu(String userId) {
        keyboardInput = new Scanner (System.in);
        ArrayList<Produce> selectedProduces = new ArrayList<Produce>();
        ArrayList<ShoppingCart> shoppingCartList= new ArrayList<ShoppingCart>();
        new CustomerMenu(shoppingCartList,null,false);
        customerMainMenu(userId);
    }
    public static void main(String[] args) {
//        ArrayList<Produce> selectedProduces = new ArrayList<Produce>();
//        ArrayList<ShoppingCart> shoppingCartList= new ArrayList<ShoppingCart>();
//        customerMainMenu(shoppingCartList,"1",true);
//        goToGuestMenu();
    }
}
