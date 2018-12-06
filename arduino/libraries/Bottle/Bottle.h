#ifndef Bottle_h
#define Bottle_h

#include "Arduino.h"
#include <SoftwareSerial.h>
#include <Adafruit_NeoPixel.h>
#include <User.h>

class Bottle
{
    public:
        Bottle(int ledsPin, int nLeds, int userWeight);
        void setLeds(Adafruit_NeoPixel *leds);
        Adafruit_NeoPixel *getLeds();
        void setNLeds(int nLeds);
        int getNLeds();
        void setUser(User *user);
        User *getUser();
        void begin();
        void handle();
        void displayProgress();
    private:
        Adafruit_NeoPixel *_leds;
        int _nLeds;
        User *_user;
};

#endif