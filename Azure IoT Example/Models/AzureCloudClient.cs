using Microsoft.Azure.Devices.Client;
using Newtonsoft.Json.Linq;
using System;
using System.Diagnostics;
using System.Text;
using TempAndHumid.Interfaces;

namespace TempAndHumid.Models
{
    class AzureCloudClient : ICloudClient
    {
        static DeviceClient deviceClient;

        public AzureCloudClient(string conString)
        {
            try
            {
                deviceClient = DeviceClient.CreateFromConnectionString(conString, TransportType.Mqtt);
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.Message);
            }
        }

        public async void WriteData(DataObject data)
        {
            try
            {
                JObject o = JObject.FromObject(new
                {
                    messageId = Guid.NewGuid(),
                    deviceId = data.DeviceName,
                    timestamp = DateTime.Now.ToString("F"),
                    temperature = data.Temperature,
                    humidity = data.Humidity
                });
                var messageString = o.ToString();
                var message = new Message(Encoding.ASCII.GetBytes(messageString));
                Debug.WriteLine($"{DateTime.Now.ToString("F")} > Sending message: {messageString}");
                await deviceClient.SendEventAsync(message);
                Debug.WriteLine($"{ DateTime.Now.ToString("F")} > Message sent successfully.");
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"{DateTime.Now.ToString("F")} > Error writing to Azure: {ex.Message}");
            }
        }
    }
}
