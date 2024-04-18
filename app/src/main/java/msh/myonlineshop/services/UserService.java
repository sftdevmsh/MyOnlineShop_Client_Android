package msh.myonlineshop.services;


import msh.myonlineshop.clients.UserClient;
import msh.myonlineshop.clients.base.ClientHandler;
import msh.myonlineshop.models.User;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class UserService {

    public static void getUserInfoFromServer(Callback<ServiceResponse<User>> callbackUser , String token)
    {
        ClientHandler clientHandler = new ClientHandler();
        UserClient userClient = clientHandler.getRetrofit().create(UserClient.class);
        if(token.toLowerCase().startsWith("bearer"))
            token = "Bearer "+token;
        Call<ServiceResponse<User>> callUser = userClient.getUserInfoFromServer(token);
        callUser.enqueue(callbackUser);
    }

    public static void login(Callback<ServiceResponse<User>> callback, User user)
    {
        ClientHandler clientHandler = new ClientHandler();
        UserClient userClient = clientHandler.getRetrofit().create(UserClient.class);
        Call<ServiceResponse<User>> call = userClient.login(user);
        call.enqueue(callback);
    }

}
