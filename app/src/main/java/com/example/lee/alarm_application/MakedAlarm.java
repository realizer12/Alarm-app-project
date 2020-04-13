package com.example.lee.alarm_application;

import android.os.Bundle;

import java.util.Calendar;

/**
 * Project name: alarm_application
 * Class: MakedAlarm
 * Created by lee.
 * Created On  2018-08-26.
 * Description: 알람 관련  리사이클러뷰에 사용할  어레이리스트에들어갈
 * 알람 생성 클래스이다.
 */
public class MakedAlarm  {

private boolean monday=false;
private boolean tuesday=false;
private boolean wednesday=false;
private boolean thursday=false;
private boolean friday=false;
private boolean saturday=false;
private boolean sunday=false;

private  String monday_text;
private  String tuseday_text;
private  String wednesday_text;
private  String thursday_text;
private  String friday_text;
private  String saturday_text;
private  String sunday_text;

private int Alarmtime;
private int Alarmminute;
private String wayforfinish_alarm;
private String memo;
private String alarmsound_text;

private boolean alamrpushed=false;
private boolean isSelected;
private boolean vibration=false;
private int specificyear;
private int specificmonth;
private int specificday;
private boolean usingspecificdate=false;
private  String alarm_music_path;
private  long alarm_sound_id;

private String barcodenumber="";
private String barcodestuffphotouri="";

//알람 사운드  텍스트 값을 저장한다.

private  double latitude;
private double longtitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getAlarm_music_path() {
        return alarm_music_path;
    }

    public void setAlarm_music_path(String alarm_music_path) {
        this.alarm_music_path = alarm_music_path;
    }

    public String getAlarmsound_text() {
        return alarmsound_text;
    }

    public void setAlarmsound_text(String alarmsound_text) {
        this.alarmsound_text = alarmsound_text;
    }

    public long getAlarm_sound_id() {
        return alarm_sound_id;
    }

    public void setAlarm_sound_id(long alarm_sound_id) {
        this.alarm_sound_id = alarm_sound_id;
    }

    public String getMonday_text() {
        return monday_text;
    }

    public void setMonday_text(String monday_text) {
        this.monday_text = monday_text;
    }

    public String getTuseday_text() {
        return tuseday_text;
    }

    public void setTuseday_text(String tuseday_text) {
        this.tuseday_text = tuseday_text;
    }

    public String getWednesday_text() {
        return wednesday_text;
    }

    public void setWednesday_text(String wednesday_text) {
        this.wednesday_text = wednesday_text;
    }

    public String getThursday_text() {
        return thursday_text;
    }

    public void setThursday_text(String thursday_text) {
        this.thursday_text = thursday_text;
    }

    public String getFriday_text() {
        return friday_text;
    }

    public void setFriday_text(String friday_text) {
        this.friday_text = friday_text;
    }

    public String getSaturday_text() {
        return saturday_text;
    }

    public void setSaturday_text(String saturday_text) {
        this.saturday_text = saturday_text;
    }

    public String getSunday_text() {
        return sunday_text;
    }

    public void setSunday_text(String sunday_text) {
        this.sunday_text = sunday_text;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public boolean isUsingspecificdate() {
        return usingspecificdate;
    }

    public void setUsingspecificdate(boolean usingspecificdate) {
        this.usingspecificdate = usingspecificdate;
    }

    public int getSpecificyear() {
        return specificyear;
    }

    public void setSpecificyear(int specificyear) {
        this.specificyear = specificyear;
    }

    public int getSpecificmonth() {
        return specificmonth;
    }

    public void setSpecificmonth(int specificmonth) {
        this.specificmonth = specificmonth;
    }

    public int getSpecificday() {
        return specificday;
    }

    public void setSpecificday(int specificday) {
        this.specificday = specificday;
    }

    //알람에서 진동 여부를 나타내는 코드
    public boolean isVibration() {
        return vibration;
    }
//알람에서 진동여부의 대한 값을 받아오는 메소드
    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }

    public int getAlarmminute() {
        return Alarmminute;
    }

    public void setAlarmminute(int alarmminute) {
        Alarmminute = alarmminute;
    }

    public int getAlarmtime() {
        return Alarmtime;
    }

    public void setAlarmtime(int Alarmtime) {
        this.Alarmtime = Alarmtime;
    }



    public String getWayforfinish_alarm() {
        return wayforfinish_alarm;
    }

    public void setWayforfinish_alarm(String wayforfinish_alarm) {
        this.wayforfinish_alarm = wayforfinish_alarm;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    //알람 목록의 스위치 버튼이 클릭되었는지 여부이다.
    public boolean isSelected() {

        return isSelected;
    }

    //알람 목록의 스위치 버튼이 클릭되어있는지 값을 받는 메소드
    public void setSelected(boolean selected) {

        this.isSelected = selected;
    }


    public boolean isAlamrpushed() {
        return alamrpushed;
    }

    public void setAlamrpushed(boolean alamrpushed) {
        this.alamrpushed = alamrpushed;
    }



    public String getBarcodenumber() {
        return barcodenumber;
    }

    public void setBarcodenumber(String barcodenumber) {
        this.barcodenumber = barcodenumber;
    }

    public String getBarcodestuffphotouri() {
        return barcodestuffphotouri;
    }

    public void setBarcodestuffphotouri(String barcodestuffphotouri) {
        this.barcodestuffphotouri = barcodestuffphotouri;
    }
}
