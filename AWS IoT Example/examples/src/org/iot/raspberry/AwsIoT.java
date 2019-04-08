package org.iot.raspberry;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor;
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumidityValue;

public class AwsIoT implements Runnable {
    //Seconds between firing
    private int pollingTime = 15;
    
    //AWS Client
    private static ICloudClient client;
    
    //Grove Pi Sensor
    //Plug this into port D2
    private GroveTemperatureAndHumiditySensor dht;
    
    //Name of the "Thing" inside AWS Console
    private String _deviceName = "YourThingName";

    @Override
    public void run(GrovePi grovePi, Monitor monitor) throws Exception {
        //Connect and setup AWS Client and Grove Pi Sensor
        client = new AWSCloudClient();
        dht = new GroveTemperatureAndHumiditySensor(grovePi, 2, GroveTemperatureAndHumiditySensor.Type.DHT11);
        System.out.println("Polling Time is " + pollingTime + " seconds.");
        System.out.println("------------------");

        while (monitor.isRunning()) {
            //Get data from sensor, package and send to client
            GroveTemperatureAndHumidityValue data = dht.get();
            double hum = data.getHumidity();
            double temp = CtoF(data.getTemperature());

            DataObject dataObj = new DataObject(_deviceName, hum, temp);
            client.WriteData(dataObj);
            System.out.println(dataObj.toString());
            System.out.println("------------------");
            
            //Wait for polling time amount (function takes in milliseconds)
            Thread.sleep(pollingTime * 1000);
        }
    }
    
    //Temp comes in from sensor as celsius   
    private double CtoF(double celsius){
        return 32 + (celsius * 9 / 5);
    }
}
