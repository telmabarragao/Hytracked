#include <Settings.h>
#include <Bottle.h>
#include <BluetoothAPI.h>

Bottle *bootle = new Bottle(LEDS_PIN, N_LEDS, DEFAULT_USER_WEIGHT);
BluetoothAPI *bluetoothAPI = new BluetoothAPI(BLUETOOTH_TX_PIN, BLUETOOTH_RX_PIN, bootle);

void setup()
{
    Serial.begin(9600);
    bootle->begin();
    bluetoothAPI->begin();
}

void loop()
{
    bootle->handle();
    bluetoothAPI->handle();
}
