package msh.myonlineshop.clients;

import msh.myonlineshop.models.Payment;
import msh.myonlineshop.models.StartPaymentVM;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentClient {
    @POST("payment/")
    Call<ServiceResponse<StartPaymentVM>> addPayment(
            @Body() Payment data
    );
}
