#include "Arduino.h"
#include "Bottle.h"

Bottle::Bottle(int bluetoothTXPin, int bluetoothRXPin, int ledsPin, int nLeds, int userWeight){
        _bluetooth = new SoftwareSerial(bluetoothTXPin, bluetoothRXPin);
        _leds = new Adafruit_NeoPixel(60, ledsPin, NEO_GRB + NEO_KHZ800);
        _nLeds = nLeds;
        _userWeight = userWeight;
        _totalConsumed = 0;
    }

void Bottle::begin(){
  Serial.begin(9600);
  _bluetooth->begin(115200);
  _leds->begin();
  _leds->show();
}

void Bottle::handle(){
    if(_bluetooth->available()){
        switch((char)_bluetooth->read()){
            case('w'):
                receiveWeight();
                break;
            case('g'):
                sendGoal();
                break;
            case('a'):
                sendProgress();
                break;
            }
        }
    if(Serial.available()){
        _totalConsumed += Serial.readString().toFloat();
        displayProgress();
    }
}

void Bottle::receiveWeight(){
  _userWeight = _bluetooth->readString().toInt();
  Serial.print("Received: set user weight to ");
  Serial.print(_userWeight);
  Serial.println("Kg.");
}

void Bottle::sendGoal(){
  Serial.println("Received: get daily goal.");
  _bluetooth->println(getGoal());
  Serial.print("Sent: ");
  Serial.print(getGoal());
  Serial.println("L");
}

void Bottle::sendProgress(){
  Serial.println("Received: get progress.");
  _bluetooth->print(getProgress());
  _bluetooth->print(" ");
  _bluetooth->println(_totalConsumed);
  Serial.print("Sent: ");
  Serial.print(getProgress());
  Serial.print("% ");
  Serial.print(_totalConsumed);
  Serial.println("L");
}

float Bottle::getGoal(){
  return (float)_userWeight/30;
}

int Bottle::getProgress(){
  return (int)((_totalConsumed*100)/getGoal());
}

void Bottle::displayProgress(){
    for(int n = 0; n < ((_totalConsumed*_nLeds)/getGoal()); n++){
        _leds->setPixelColor(n, 255, 0, 255);
    }
    _leds->show();
}