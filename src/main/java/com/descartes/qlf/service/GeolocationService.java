package com.descartes.qlf.service;

import com.descartes.qlf.model.Geolocation;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class GeolocationService {

  private DatabaseReader dbReader;

  public GeolocationService() throws IOException {
    File database = new File("GeoLite2-City.mmdb");
    dbReader = new DatabaseReader.Builder(database).build();
  }

  public Geolocation getLocation(String ip) throws IOException, GeoIp2Exception {
    InetAddress ipAddress = InetAddress.getByName(ip);
    CityResponse response = dbReader.city(ipAddress);

    String cityName = response.getCity().getName();
    double latitude = response.getLocation().getLatitude();
    double longitude = response.getLocation().getLongitude();
    return new Geolocation(ip, cityName, latitude, longitude);
  }
}
