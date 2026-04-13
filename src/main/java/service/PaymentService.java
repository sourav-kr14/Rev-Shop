package service;

import exception.PaymentException;

public class PaymentService {
    public boolean paymentProcess(int userId,double amount,String method)
    {

        if(amount <=0){
            throw new PaymentException("Invalid amount");
        }
        if(method == null || method.trim().isEmpty())
        {
            throw new PaymentException("Payment method cannot be empty");
        }
        method=method.trim().toUpperCase();
        switch (method)
        {
            case "COD":return true;
            case "UPI":return true;
            case "CARD":return true;
            default:throw new PaymentException("Payment method unsupported");
        }
    }
}
