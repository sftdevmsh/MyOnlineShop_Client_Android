package msh.myonlineshop.handlers.base;

import msh.myonlineshop.configs.clientHandler.ApiAddresses;
import msh.myonlineshop.configs.clientHandler.UnsafeSSLConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientHandler {

    private Retrofit.Builder builder;

    private Retrofit retrofit;

    public ClientHandler() {
        builder = new Retrofit.Builder()
                .baseUrl(ApiAddresses.baseDomain + "/api/")
//                .client(UnsafeSSLConfig.getUnsafeOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
    }

    public Retrofit.Builder getBuilder() {
        return builder;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
