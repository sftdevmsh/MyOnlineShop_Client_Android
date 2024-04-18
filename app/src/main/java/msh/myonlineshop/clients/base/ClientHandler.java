package msh.myonlineshop.clients.base;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientHandler {

    private Retrofit.Builder builder;

    private Retrofit retrofit;

    public ClientHandler() {
        builder = new Retrofit.Builder()
                .baseUrl(ApiAddresses.baseDomain + "/api/")
//.client(UnsafeSSLConfig.getUnsafeOkHttpClient().build())
//for ssl self signed key
//but, no need to use it while "android:usesCleartextTraffic="true" in AndroidManifext.xml
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
