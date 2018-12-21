using TempAndHumid.Models;

namespace TempAndHumid.Interfaces
{
    interface ICloudClient
    {
        void WriteData(DataObject o);
    }
}
