using System;
using System.Diagnostics;
using Windows.ApplicationModel.Background;
using System.Threading.Tasks;
using GrovePi;
using GrovePi.Sensors;
using TempAndHumid.Interfaces;
using TempAndHumid.Models;

namespace TempAndHumid
{
    public sealed class StartupTask : IBackgroundTask
    {
        //Grove Pi Sensor
        //Ensure that the pin given here matches the one that the sensor is connected to
        IDHTTemperatureAndHumiditySensor sensor = DeviceFactory.Build.DHTTemperatureAndHumiditySensor(Pin.DigitalPin2, DHTModel.Dht11);

        //Enter your Azure IoT Hub Device Connection String
        string AzureIoTConnectionString = "";

        //Enter your device's name or hostname
        string _deviceName = "";


        //               Please ensure you have filled out the connection string and device name above
        //-----------------------------------------------------------------------------------------------------------------------
        //

        //Default values
        int pollingTime = 0;
        int pollingTimeMS = 0;
        static ICloudClient client;

        public void Run(IBackgroundTaskInstance taskInstance)
        {
            //60 seconds in between measurements that get sent to the cloud
            pollingTime = 60;
            Debug.WriteLine($"Polling Time is {pollingTime} seconds");
            //Calculate milliseconds to give to the method below for waiting
            pollingTimeMS = pollingTime * 1000;

            //Initialize azure cloud client 
            client = new AzureCloudClient(AzureIoTConnectionString);

            while (true)
            {
                try
                {
                    // Check the value of the sensor
                    sensor.Measure();
                    double sensortemp = sensor.TemperatureInFahrenheit;
                    double sensorhum = sensor.Humidity;

                    //Check for invalid values
                    if (double.IsNaN(sensortemp) || double.IsNaN(sensorhum))
                    {
                        throw new Exception($"Sensor not working - Temperature:{sensortemp}, Humidity:{sensorhum}");
                    }

                    //Sending payload to the cloud
                    Debug.WriteLine($"Temp: {sensortemp.ToString()}F Humidity: {sensorhum}%.");
                    DataObject data = new DataObject()
                    {
                        Temperature = sensortemp,
                        Humidity = sensorhum,
                        DeviceName = _deviceName
                    };
                    client.WriteData(data);
                }
                catch (Exception ex)
                {
                    Debug.WriteLine(ex.Message);
                }

                // Wait for the alloted polling time               
                Task.Delay(pollingTimeMS).Wait();
            }
        }
    }
}