package com.example.a80_tech_task.APIUtils;


public class Image {

    public static final String SMALL_IAMGE_SIZE = "w300/";
    public static final String MEDIUM_IAMGE_SIZE = "w700/";
    public static final String BIG_IAMGE_SIZE = "w1280/";

    private String baseUrl = ServiceGenerator.IMAGE_BASE_URL;
    private String imageKey;


    public Image(String imageKey) {
        this.imageKey = imageKey;
    }

    public String getLowQualityImagePath(){
        return baseUrl +SMALL_IAMGE_SIZE+imageKey;

    }
    public String getMediumQualityImagePath(){
        return baseUrl +MEDIUM_IAMGE_SIZE+imageKey;

    }
    public String getHighQualityImagePath(){
        return baseUrl +BIG_IAMGE_SIZE+imageKey;
    }
}
