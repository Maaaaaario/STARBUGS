package adminmenu;

public class Product {
    private final String nameOfProduct;
    private final String idOfProduct;
    public enum TypeOfProduct {
      Food, Coffee, Softdrink
    }
    private final TypeOfProduct typeOfProduct;
    private final double price;
    private final int remainingNumber;
    final static int MAXIMUM_NUMBER = 100;
    private final double discount;
    private final int sales;
    
    // Constructor
    private double checkValidatePrice (double price) {
      if (price < 0) {
        System.out.println ("The price could not be negative, it would be set to 0 automatically, please double check");
        return 0;
      }
      else
        return price;
    }

    private int checkValidateNumber (int number) {
      if (number < 0) {
        System.out.println ("The product number could not be negative, it would be set to 0 automatically, please double check");
        return 0;
      }
      else if (number > MAXIMUM_NUMBER) {
        System.out.println ("The product number has reached maximum, please double check");
        return MAXIMUM_NUMBER;
      }
      else 
        return number;
    }
    
    private int checkValidateSales (int sales) {
        if (sales < 0) {
        System.out.println ("The number of products sold could not be negative, it would be set to 0 automatically, please double check");
        return 0;
        }
        else
            return sales;
    }
    
    private double checkValidateDiscount (double discount) {
      if (discount < 0) {
        System.out.println ("The account of sale must be positive, it would be set to 1 automatically, please double check");
        return 0;
      }
      else if (discount >= 1) {
        System.out.println ("The account of sale could not be larger than 1, it would be set to 1 automatically, please double check");
        return 0;
      }
      else 
        return discount;
    }
    
    public Product ( String nameOfProduct, String idOfProduct,TypeOfProduct typeOfProduct, double price, int remainingNumber,
                      double account, int sales) {
      this.nameOfProduct = nameOfProduct;
      this.idOfProduct = idOfProduct;
      this.typeOfProduct = typeOfProduct;
      this.price = checkValidatePrice (price);
      this.remainingNumber = checkValidateNumber (remainingNumber);
      this.discount = checkValidateDiscount (account);
      this.sales = checkValidateSales (sales);
    }
    
    // Behaviors
    @Override
    public String toString () {
       String productInform = "";
       productInform += "Product name: " + nameOfProduct;
       productInform += " ID: " + idOfProduct;
       productInform += " Type: " + typeOfProduct;
       productInform += " Price: " + price;
       productInform += " Remaining: " + remainingNumber;
       productInform += "\nThe discount of this product: " + discount;
       productInform += " The previous sales of this product is: " + sales;
       return productInform;
    }
    
    public String getProductName () {
        return nameOfProduct;
    }
    
    public String getProductId () {
        return idOfProduct;
    }
    
    public int getRemainingNumber () {
        return remainingNumber;
    }
    
    public int getSales () {
        return sales;
    }
    
    public Product addDiscount (double newDiscount) {
        return new Product (nameOfProduct, idOfProduct, typeOfProduct, price, remainingNumber, newDiscount, sales);
    }
    
    public Product modifyNumber (int newNumber) {
        return new Product (nameOfProduct, idOfProduct, typeOfProduct, price, newNumber, discount, sales);
    }
}
