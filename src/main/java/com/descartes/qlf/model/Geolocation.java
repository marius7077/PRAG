package com.descartes.qlf.model;

public class Geolocation {

  private String ipAddress;
  private String city;
  private double latitude;
  private double longitude;

  public Geolocation() {}

  public Geolocation(String ipAddress, String city, double latitude, double longitude) {
    this.ipAddress = ipAddress;
    this.city = city;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }
}
