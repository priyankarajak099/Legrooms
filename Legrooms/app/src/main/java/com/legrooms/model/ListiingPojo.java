package com.legrooms.model;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

public class ListiingPojo implements Serializable
{
  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  private double longitude;
  private double latitude;

  private String _id;

  public String getId() { return this._id; }

  public void setId(String _id) { this._id = _id; }

  private String activity;

  public String getActivity() { return this.activity; }

  public void setActivity(String activity) { this.activity = activity; }

  private int attendees;

  public int getAttendees() { return this.attendees; }

  public void setAttendees(int attendees) { this.attendees = attendees; }

  private int hours;

  public int getHours() { return this.hours; }

  public void setHours(int hours) { this.hours = hours; }

  private int price;

  public int getPrice() { return this.price; }

  public void setPrice(int price) { this.price = price; }

  private String lRules;

  public String getLRules() { return this.lRules; }

  public void setLRules(String lRules) { this.lRules = lRules; }

  private String desc;

  public String getDesc() { return this.desc; }

  public void setDesc(String desc) { this.desc = desc; }

  private String lTitle;

  public String getLTitle() { return this.lTitle; }

  public void setLTitle(String lTitle) { this.lTitle = lTitle; }

  private String address;

  public String getAddress() { return this.address; }

  public void setAddress(String address) { this.address = address; }

  private String neighborhood;

  public String getNeighborhood() { return this.neighborhood; }

  public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }

  private String city;

  public String getCity() { return this.city; }

  public void setCity(String city) { this.city = city; }

  private String state;

  public String getState() { return this.state; }

  public void setState(String state) { this.state = state; }

  private String country;

  public String getCountry() { return this.country; }

  public void setCountry(String country) { this.country = country; }

  private String email;

  public String getEmail() { return this.email; }

  public void setEmail(String email) { this.email = email; }

  private int __v;

  public int getV() { return this.__v; }

  public void setV(int __v) { this.__v = __v; }

  private String amenities;

  public String getAmenities() { return this.amenities; }

  public void setAmenities(String amenities) { this.amenities = amenities; }

  private String availability;

  public String getAvailability() { return this.availability; }

  public void setAvailability(String availability) { this.availability = availability; }

  private   String images;

  public String getImages() { return this.images; }

  public void setImages(String images) { this.images = images; }
}
