package msh.myonlineshop.clients.base;

public class ApiAddresses {
//    public static String baseDomain = "https://onlineshop.holosen.net:9090";
//    public static String baseDomain = "https://localhost:8080";
//    public static String baseDomain = "https://127.0.0.1";
//    public static String baseDomain = "https://10.0.2.2:8080";
//    public static String baseDomain = "https://192.168.1.1";
//    public static String baseDomain = "https://192.168.143.5:8080";
//    public static String baseDomain = "https://192.168.1.1:8080";
    public static String baseDomain = "http://192.168.1.103:8080";

//    public static String baseHttpDomain = "http://onlineshop.holosen.net:9091";
    public static String baseHttpDomain = "http://localhost:8081";
//    public static String baseHttpDomain = "http://127.0.0.1:8081";
//    public static String baseHttpDomain = "http://10.0.2.2:8081";
//    public static String baseHttpDomain = "http://192.168.1.1:8081";
//    public static String baseHttpDomain = "http:////192.168.143.5:8081";
//    public static String baseHttpDomain = "http://192.168.1.103:8081";
    public static String fileUrl = baseHttpDomain + "api/utils/upload/files/";

    public static String getFileUrl(String name){
        return fileUrl + name;
    }
}
