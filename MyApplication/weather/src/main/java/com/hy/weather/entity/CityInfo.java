package com.hy.weather.entity;

import java.util.List;

/**
 * Created Time: 2017/1/25 16:33.
 *
 * @author HY
 */

public class CityInfo {
    private String cityId;
    private String cityNameCn;
    private String cityNameEn;
    private String country;
    private String countryCode;
    private String exclusiveCn_1;
    private String exclusiveEn_1;
    private String exclusiveCn_2;
    private String exclusiveEn_2;
    private String timeZone;
    private String cityLevel;

    public CityInfo() {

    }

    public CityInfo(String cityNameCn, String cityNameEn, String country, String countryCode, String exclusiveCn_1, String exclusiveEn_1, String exclusiveCn_2, String exclusiveEn_2, String timeZone, String cityLevel, String cityId) {
        this.cityNameCn = cityNameCn;
        this.cityNameEn = cityNameEn;
        this.country = country;
        this.countryCode = countryCode;
        this.exclusiveCn_1 = exclusiveCn_1;
        this.exclusiveEn_1 = exclusiveEn_1;
        this.exclusiveCn_2 = exclusiveCn_2;
        this.exclusiveEn_2 = exclusiveEn_2;
        this.timeZone = timeZone;
        this.cityLevel = cityLevel;
        this.cityId = cityId;
    }

    public CityInfo(List<String> args) {
        this.cityId = args.get(0);
        this.cityNameCn = args.get(1);
        this.cityNameEn = args.get(2);
        this.country = args.get(3);
        this.countryCode = args.get(4);
        this.exclusiveCn_1 = args.get(5);
        this.exclusiveEn_1 = args.get(6);
        this.exclusiveCn_2 = args.get(7);
        this.exclusiveEn_2 = args.get(8);
        this.timeZone = args.get(9);
        this.cityLevel = args.get(10);

    }


    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityNameCn() {
        return cityNameCn;
    }

    public void setCityNameCn(String cityNameCn) {
        this.cityNameCn = cityNameCn;
    }

    public String getCityNameEn() {
        return cityNameEn;
    }

    public void setCityNameEn(String cityNameEn) {
        this.cityNameEn = cityNameEn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getExclusiveCn_1() {
        return exclusiveCn_1;
    }

    public void setExclusiveCn_1(String exclusiveCn_1) {
        this.exclusiveCn_1 = exclusiveCn_1;
    }

    public String getExclusiveEn_1() {
        return exclusiveEn_1;
    }

    public void setExclusiveEn_1(String exclusiveEn_1) {
        this.exclusiveEn_1 = exclusiveEn_1;
    }

    public String getExclusiveCn_2() {
        return exclusiveCn_2;
    }

    public void setExclusiveCn_2(String exclusiveCn_2) {
        this.exclusiveCn_2 = exclusiveCn_2;
    }

    public String getExclusiveEn_2() {
        return exclusiveEn_2;
    }

    public void setExclusiveEn_2(String exclusiveEn_2) {
        this.exclusiveEn_2 = exclusiveEn_2;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getCityLevel() {
        return cityLevel;
    }

    public void setCityLevel(String cityLevel) {
        this.cityLevel = cityLevel;
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "cityId='" + cityId + '\'' +
                ", cityNameCn='" + cityNameCn + '\'' +
                ", cityNameEn='" + cityNameEn + '\'' +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", exclusiveCn_1='" + exclusiveCn_1 + '\'' +
                ", exclusiveEn_1='" + exclusiveEn_1 + '\'' +
                ", exclusiveCn_2='" + exclusiveCn_2 + '\'' +
                ", exclusiveEn_2='" + exclusiveEn_2 + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", cityLevel='" + cityLevel + '\'' +
                '}';
    }
}
