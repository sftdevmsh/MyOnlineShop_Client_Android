package msh.myonlineshop.clientHandler;

import msh.myonlineshop.models.ProductCategory;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductCategoryClient {
    @GET("productCategory")
    Call<ServiceResponse<ProductCategory>> get();
}
