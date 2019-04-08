/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iot.raspberry;

/**
 *
 * @author matthew.young
 */
public class DataObject {

    private String DeviceName;
    private double Temperature;
    private double Humidity;
    private final String CurrentTime;

    public DataObject(String name, double hum, double temp){
        this.DeviceName = name;
        this.Temperature = temp;
        this.Humidity = hum;
        this.CurrentTime = Long.toString(System.currentTimeMillis());
    }
    
    /**
     * @return the CurrentTime
     */
    public String getCurrentTime() {
        return CurrentTime;
    }
    
    /**
     * @return the DeviceName
     */
    public String getDeviceName() {
        return DeviceName;
    }

    /**
     * @param DeviceName the DeviceName to set
     */
    public void setDeviceName(String DeviceName) {
        this.DeviceName = DeviceName;
    }

    /**
     * @return the Temperature
     */
    public double getTemperature() {
        return Temperature;
    }

    /**
     * @param Temperature the Temperature to set
     */
    public void setTemperature(double Temperature) {
        this.Temperature = Temperature;
    }

    /**
     * @return the Humidity
     */
    public double getHumidity() {
        return Humidity;
    }

    /**
     * @param Humidity the Humidity to set
     */
    public void setHumidity(double Humidity) {
        this.Humidity = Humidity;
    }
    
    @Override
    public String toString(){
        return "Current Time: " + getCurrentTime() + "\r\nName: " + getDeviceName() + "\r\nTemp: " + getTemperature() + "\r\nHumidity: " + getHumidity();
    }
}
