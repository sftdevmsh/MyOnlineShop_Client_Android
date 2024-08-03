package msh.myonlineshop.clientHandler;

import msh.myonlineshop.models.Invoice;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InvoiceClient {
    @GET("invoice/find")
    Call<ServiceResponse<Invoice>> findByCustomerId(
            @Header("Authorization") String token,
            @Query("cid") long cid,
            @Query("pageNumber") int pageNumber,
            @Query("pageSize") int pageSize
    );

    @GET("invoice/getInfo/{id}")
    Call<ServiceResponse<Invoice>> get(
            @Path("id") long id,
            @Header("Authorization") String token
    );


}
