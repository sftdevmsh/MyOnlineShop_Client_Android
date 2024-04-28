package msh.myonlineshop.clients;

import msh.myonlineshop.models.Content;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ContentClient {
    @GET("content/getAllData")
    Call<ServiceResponse<Content>> getAll();
}
