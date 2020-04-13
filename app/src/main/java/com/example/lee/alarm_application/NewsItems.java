package com.example.lee.alarm_application;

/**
 * alarm_application
 * Class: NewsItems.
 * Created by leedonghun.
 * Created On 2018-09-28.
 * Description:
 */
public class NewsItems {
    private String title1;
    private String img_url1;
    private String companyname1;
    private String time1;
    private String detail_link1;

    public NewsItems(String title1,String url1,String companyname1,String time1,String detail_link1){

        this.title1 = title1;
        this.img_url1 = url1;
        this.companyname1=companyname1;
        this.time1=time1;
        this.detail_link1=detail_link1;
    }
    public String getTitle1() {
        return title1;
    }
    public String getImg_url1() {
        return img_url1;
    }

    public  String getCompanyname1(){

        return companyname1;
    }

    public String getTime1(){

        return time1;
    }
    public  String getDetail_link1(){

        return detail_link1;
    }

}
