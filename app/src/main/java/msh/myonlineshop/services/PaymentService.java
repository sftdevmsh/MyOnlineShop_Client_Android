package msh.myonlineshop.services;


import msh.myonlineshop.clientHandler.PaymentClient;
import msh.myonlineshop.clientHandler.base.ClientHandler;
import msh.myonlineshop.models.Payment;
import msh.myonlineshop.models.StartPaymentVM;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class PaymentService {

    public static void addPayment(Callback<ServiceResponse<StartPaymentVM>> callback, Payment data) {

        ClientHandler clientHandler = new ClientHandler();
        PaymentClient paymentClient = clientHandler.getRetrofit().create(PaymentClient.class);
        Call<ServiceResponse<StartPaymentVM>> responseCall = paymentClient.addPayment(data);
        responseCall.enqueue(callback);
    }
}
