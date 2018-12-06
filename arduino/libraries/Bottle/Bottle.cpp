#include "Arduino.h"
#include "Bottle.h"

Bottle::Bottle(int ledsPin, int nLeds, int userWeight){
    setLeds(new Adafruit_NeoPixel(60, ledsPin, NEO_GRB + NEO_KHZ800));
    setNLeds(nLeds);
    setUser(new User(userWeight));
}

void Bottle::setLeds(Adafruit_NeoPixel *leds){
    _leds = leds;
}

Adafruit_NeoPixel *Bottle::getLeds(){
    return _leds;
}

void Bottle::setNLeds(int nLeds){
    _nLeds = nLeds;
}

int Bottle::getNLeds(){
    return _nLeds;
}

void Bottle::setUser(User *user){
    _user = user;
}

User *Bottle::getUser(){
    return _user;
}

void Bottle::begin(){
  getLeds()->begin();
  getLeds()->show();
}

void Bottle::handle(){
    if(Serial.available()){
        getUser()->addIntake(Serial.readString().toFloat());
        displayProgress();
    }
}

void Bottle::displayProgress(){
    for(int n = 0; n < getUser()->getProgress()*getNLeds()/100; n++){
        getLeds()->setPixelColor(n, 10, 10, 0);
    }
    getLeds()->show();
}