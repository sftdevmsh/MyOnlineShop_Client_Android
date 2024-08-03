package msh.myonlineshop.services;

import msh.myonlineshop.clientHandler.ProductClient;
import msh.myonlineshop.clientHandler.base.ClientHandler;
import msh.myonlineshop.models.Product;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class ProductService {

    public static void getNew(Callback<ServiceResponse<Product>> callback) {
        ClientHandler clientHandler = new ClientHandler();
        ProductClient client = clientHandler.getRetrofit().create(ProductClient.class);
        Call<ServiceResponse<Product>> responseCall = client.getNew();
        responseCall.enqueue(callback);
    }

    public static void getPopular(Callback<ServiceResponse<Product>> callback) {
        ClientHandler clientHandler = new ClientHandler();
        ProductClient client = clientHandler.getRetrofit().create(ProductClient.class);
        Call<ServiceResponse<Product>> responseCall = client.getPopular();
        responseCall.enqueue(callback);
    }

    public static void getByCategory(Callback<ServiceResponse<Product>> callback, long categoryId, int pageNumber, int pageSize) {
        ClientHandler clientHandler = new ClientHandler();
        ProductClient client = clientHandler.getRetrofit().create(ProductClient.class);
        Call<ServiceResponse<Product>> responseCall = client.getByCategory(categoryId, pageNumber, pageSize);
        responseCall.enqueue(callback);
    }
}
