package adminmenu;
import java.util.ArrayList;

public class MenuOfProducts {
    ArrayList<Product> menu;
    
    public MenuOfProducts (ArrayList<Product>menu) {
        this.menu = menu;
    }
    
    @Override
    public String toString () {
    String menuInformation = "";
    for (Product i : menu)
        menuInformation += i.toString();
    return menuInformation;
    }
    
    public boolean thisNameDoesNotExist (String newName) {
        for (Product i : menu) {
            if (i.getProductName().equals(newName)) {
                return false;
            }   
        }
        return true;
    }
    
    public boolean thisIdDoesNotExist (String newId) {
        for (Product i : menu) {
            if (i.getProductId().equals(newId)) {
                return false;
            }   
        }
        return true;
    }
    
    public void addProduct (Product newProduct) {
        menu.add(newProduct);
    }
    
    public int checkAttribute (String attribute, String inputName,String inputId) {
        for (Product i : menu) {
            if ((i.getProductName().equals(inputName)) || (i.getProductId().equals(inputId))) {
                System.out.println("The product with given ID is found");
                if (attribute.equals("sales") )
                    return i.getSales();
                else if (attribute.equals("remainingNumber") )
                    return i.getRemainingNumber();
            }   
        }
        return 0;
    }
    
    public void deleteProduct (String inputName, String inputId) {
        String informationOfDeletedProduct;
        boolean prductIsFound = false;
        for (Product i : menu) {
            if (i.getProductName().equals(inputName) || (i.getProductId().equals(inputId))) {
                informationOfDeletedProduct = i.toString();
                menu.remove(i);
                System.out.println("The product is removed, information of removed product is:");
                System.out.println(informationOfDeletedProduct);
                prductIsFound = true;
                break;
            }   
        }
        if (prductIsFound == false)
            System.out.println("The product you chose does not exist");
    }
    
    public boolean addDiscount (String inputName, String inputId, double discount) {
        Product temporarySave;
        int indexOfReplacedProduct = 0;
        for (Product i : menu) {
            if (i.getProductName().equals(inputName) || i.getProductId().equals(inputId)) {
                temporarySave = i.addDiscount(discount);
                menu.set(indexOfReplacedProduct, temporarySave);
                System.out.println("The product is found and discount is added");
                return true;
            }
            else
                indexOfReplacedProduct ++;
        }
        return false;
    }
    
    public boolean modifyNumber (String inputName, String inputId, int newNumber) {
        Product temporarySave;
        int indexOfReplacedProduct = 0;
        for (Product i : menu) {
            if (i.getProductName().equals(inputName) || i.getProductId().equals(inputId)) {
                temporarySave = i.modifyNumber(newNumber);
                menu.set(indexOfReplacedProduct, temporarySave);
                System.out.println("The product is found and number is modified");
                return true;
            }
            else
                indexOfReplacedProduct ++;
        }
        return false;
    }
}