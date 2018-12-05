#include <Settings.h>
#include <Bottle.h>

Bottle *bootle = new Bottle(BLUETOOTH_TX_PIN, BLUETOOTH_RX_PIN, LEDS_PIN, N_LEDS, DEFAULT_USER_WEIGHT);

void setup()
{
  bootle->begin();
}

void loop()
{
  bootle->handle();
}
