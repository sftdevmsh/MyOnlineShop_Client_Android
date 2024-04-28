package msh.myonlineshop.clients;

import msh.myonlineshop.models.SliderItem;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SliderClient {
    @GET("slider")
    Call<ServiceResponse<SliderItem>> get();
}
