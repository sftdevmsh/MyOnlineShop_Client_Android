package msh.myonlineshop.clients;

import msh.myonlineshop.models.Size;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SizeClient {
    @GET("size/")
    Call<ServiceResponse<Size>> getAll();
}
