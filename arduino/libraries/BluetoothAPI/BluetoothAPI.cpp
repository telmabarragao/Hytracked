#include "Arduino.h"
#include "BluetoothAPI.h"

BluetoothAPI::BluetoothAPI(int bluetoothTXPin, int bluetoothRXPin, Bottle *bottle){
    setBluetooth(new SoftwareSerial(bluetoothTXPin, bluetoothRXPin));
    setBottle(bottle);
}

void BluetoothAPI::setBluetooth(SoftwareSerial *bluetooth){
    _bluetooth = bluetooth;
}

SoftwareSerial *BluetoothAPI::getBluetooth(){
    return _bluetooth;
}

void BluetoothAPI::setBottle(Bottle *bottle){
    _bottle = bottle;
}

Bottle *BluetoothAPI::getBottle(){
    return _bottle;
}

void BluetoothAPI::begin(){
    getBluetooth()->begin(115200);
}

void BluetoothAPI::handle(){
    char character;
    String message = "";
    while(getBluetooth()->available()) {
        character = (char) getBluetooth()->read();
        message.concat(character);
        if (character == '#') { // if end of message received
            Serial.print(message); //display message and
            message = ""; //clear buffer
            Serial.println();
        }
    }
    /*switch(message[0]){
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
    if(getBluetooth()->available()){
        switch((char)getBluetooth()->read()){
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
    }*/
}

void BluetoothAPI::receiveWeight(){
    getBottle()->getUser()->setWeight(getBluetooth()->readString().toInt());
    Serial.print("Received: set user weight to ");
    Serial.print(getBottle()->getUser()->getWeight());
    Serial.println("Kg.");
}

void BluetoothAPI::sendGoal(){
    Serial.println("Received: get daily goal.");
    getBluetooth()->println(getBottle()->getUser()->getGoal());
    Serial.print("Sent: ");
    Serial.print(getBottle()->getUser()->getGoal());
    Serial.println("L");
}

void BluetoothAPI::sendProgress(){
    Serial.println("Received: get progress.");

    getBluetooth()->print(getBottle()->getUser()->getProgress());
    getBluetooth()->print(" ");
    getBluetooth()->println(getBottle()->getUser()->getIntake());

    Serial.print("Sent: ");
    Serial.print(getBottle()->getUser()->getProgress());
    Serial.print("% ");
    Serial.print(getBottle()->getUser()->getIntake());
    Serial.println("L");
}