package msh.myonlineshop.clientHandler;

import msh.myonlineshop.models.Customer;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface CustomerClient {
    @PUT("customer/updateInfo")
    Call<ServiceResponse<Customer>> updateInfo(@Body() Customer data);
}
