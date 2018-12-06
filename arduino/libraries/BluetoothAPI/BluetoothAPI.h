#ifndef BluetoothAPI_h
#define BluetoothAPI_h

#include "Arduino.h"
#include <SoftwareSerial.h>
#include <Bottle.h>

class BluetoothAPI
{
    public:
        BluetoothAPI(int bluetoothTXPin, int bluetoothRXPin, Bottle *bottle);
        void setBluetooth(SoftwareSerial *bluetooth);
        SoftwareSerial *getBluetooth();
        void setBottle(Bottle *bottle);
        Bottle *getBottle();
        void begin();
        void handle();
        void receiveWeight();
        void sendGoal();
        void sendProgress();
    private:
        SoftwareSerial *_bluetooth;
        Bottle *_bottle;

};

#endif