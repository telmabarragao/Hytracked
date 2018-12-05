#ifndef Bottle_h
#define Bottle_h

#include "Arduino.h"
#include <SoftwareSerial.h>
#include <Adafruit_NeoPixel.h>

class Bottle
{
    public:
        Bottle(int bluetoothTXPin, int bluetoothRXPin, int ledsPin, int nLeds, int userWeight);
        void begin();
        void handle();
        void receiveWeight();
        void sendGoal();
        void sendProgress();
        float getGoal();
        int getProgress();
        void displayProgress();
    private:
        SoftwareSerial *_bluetooth;
        Adafruit_NeoPixel *_leds;
        int _nLeds;
        int _userWeight;
        float _totalConsumed;
};

#endif