package msh.myonlineshop.services;


import msh.myonlineshop.clients.SliderClient;
import msh.myonlineshop.clients.UserClient;
import msh.myonlineshop.clients.base.ClientHandler;
import msh.myonlineshop.models.SliderItem;
import msh.myonlineshop.models.User;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class SliderService {

    //Call<ServiceResponse<SliderItem>> getAll()
    public static void get(Callback<ServiceResponse<SliderItem>> callback)
    {
        ClientHandler clientHandler = new ClientHandler();
        SliderClient sliderClient = clientHandler.getRetrofit().create(SliderClient.class);
        Call<ServiceResponse<SliderItem>> call = sliderClient.get();
        call.enqueue(callback);
    }

}
