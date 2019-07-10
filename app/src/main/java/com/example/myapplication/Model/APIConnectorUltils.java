package com.example.myapplication.Model;

public class APIConnectorUltils {
    //    public static final String HOST_NAME = "http://192.168.43.222:8080/MovieHub_API/MovieHubClient/";
//    public static final String HOST_NAME = "http://192.168.73.102:8080/MovieHub_API/MovieHubClient/";
//    public static final String HOST_NAME = "http://201.0.239.10:8080/MovieHub_API/MovieHubClient/";
//        public static final String HOST = "201.0.239.10"; //Haui library
        private static final String HOST = "192.168.43.222"; //Haui free 2
//        private static final String HOST = "192.168.73.100"; //DHCNHN
//    private static final String HOST = "192.168.1.9"; // LongHang
    public static final String HOST_NAME = "http://" + HOST + ":8080/MovieHub_API/MovieHubClient/";
    public static final String HOST_STORAGE = "http://" + HOST + ":8080/Storage/";
    public static final String HOST_STORAGE_IMAGE = "http://" + HOST + ":8080/Storage/ImageProfile/";
    public static final String HOST_STORAGE_FILM = "http://" + HOST + ":8080/MovieHub_API/MovieHubClient/Film";


}
