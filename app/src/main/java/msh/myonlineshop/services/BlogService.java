package msh.myonlineshop.services;

import msh.myonlineshop.clientHandler.BlogClient;
import msh.myonlineshop.clientHandler.base.ClientHandler;
import msh.myonlineshop.models.Blog;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class BlogService {
    public static void getAllData(Callback<ServiceResponse<Blog>> callback, int pageNumber, int pageSize){
        ClientHandler clientHandler = new ClientHandler();
        BlogClient blogClient = clientHandler.getRetrofit().create(BlogClient.class);
        Call<ServiceResponse<Blog>> call = blogClient.getAllData(pageNumber,pageSize);
        call.enqueue(callback);
    }

    public static void increaseVisitCount(Callback<ServiceResponse<Long>> callback, long id){
        System.out.println("BlogService _ to increaseVisitCount : "+id);
        ClientHandler clientHandler = new ClientHandler();
        BlogClient blogClient = clientHandler.getRetrofit().create(BlogClient.class);
        Call<ServiceResponse<Long>> call = blogClient.increaseVisitCount(id);
        call.enqueue(callback);
    }

}
