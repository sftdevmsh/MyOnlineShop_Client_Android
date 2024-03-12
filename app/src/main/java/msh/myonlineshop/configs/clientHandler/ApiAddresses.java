package msh.myonlineshop.configs.clientHandler;

public class ApiAddresses {
//    public static String baseDomain = "https://onlineshop.holosen.net:8080";
    public static String baseDomain = "127.0.0.1:8080";
//    public static String baseHttpDomain = "http://onlineshop.holosen.net:8081";
    public static String baseHttpDomain = "127.0.0.1:8080";
    public static String fileUrl = baseHttpDomain + "/api/utils/upload/files/";

    public static String getFileUrl(String name){
        return fileUrl + name;
    }
}
