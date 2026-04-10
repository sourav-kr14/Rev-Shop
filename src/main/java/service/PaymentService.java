package service;

public class PaymentService {
    public boolean paymentProcess(int userId,double amount,String method)
    {
        System.out.println("Processing " +method + "for Rs. "+amount);
        if(method.equalsIgnoreCase("COD"))
        {
            System.out.println("Cash on Delivery Selected");
            return true;
        }
        if(method.equalsIgnoreCase("UPI")|| method.equalsIgnoreCase("CARD"))
        {
            System.out.println("Payment successfull via "+method);
            return true;
        }
        return false;
    }
}
