package msh.myonlineshop.services;

import msh.myonlineshop.clients.BlogClient;
import msh.myonlineshop.clients.UserClient;
import msh.myonlineshop.clients.base.ClientHandler;
import msh.myonlineshop.models.Blog;
import msh.myonlineshop.models.User;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class BlogService {
    public static void getAllData(Callback<ServiceResponse<Blog>> callback, int pageNumber, int pageSize){
        ClientHandler clientHandler = new ClientHandler();
        BlogClient blogClient = clientHandler.getRetrofit().create(BlogClient.class);
        Call<ServiceResponse<Blog>> call = blogClient.getAllData(pageNumber,pageSize);
        call.enqueue(callback);
    }

    public static void increaseVisitCount(Callback<ServiceResponse<Blog>> callback, long id){
        ClientHandler clientHandler = new ClientHandler();
        BlogClient blogClient = clientHandler.getRetrofit().create(BlogClient.class);
        Call<ServiceResponse<Blog>> call = blogClient.increaseVisitCount(id);
        call.enqueue(callback);
    }

}
