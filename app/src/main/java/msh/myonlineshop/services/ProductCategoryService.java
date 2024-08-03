package msh.myonlineshop.services;

import msh.myonlineshop.clientHandler.ProductCategoryClient;
import msh.myonlineshop.clientHandler.base.ClientHandler;
import msh.myonlineshop.models.ProductCategory;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class ProductCategoryService {
    public static void getAll(Callback<ServiceResponse<ProductCategory>> callback) {
        ClientHandler clientHandler = new ClientHandler();
        ProductCategoryClient client = clientHandler.getRetrofit().create(ProductCategoryClient.class);
        Call<ServiceResponse<ProductCategory>> responseCall = client.get();
        responseCall.enqueue(callback);
    }
}
