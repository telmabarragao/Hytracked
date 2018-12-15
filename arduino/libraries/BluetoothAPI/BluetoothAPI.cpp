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
        if (character == '#'){ // if end of message received
            Serial.print(message); //display message and
            handleCompleteMessage(message);
            message = ""; //clear buffer
            Serial.println();
        }
    }
}

void BluetoothAPI::handleCompleteMessage(String message)
{
	switch(message[0])
    {
        case('w'):
            receiveWeight(message.substring(1, message.length()).toInt());
            break;
        case('g'):
            sendGoal();
            break;
        case('a'):
            sendProgress();
            break;
    }
}

void BluetoothAPI::receiveWeight(int weight){
    getBottle()->getUser()->setWeight(weight);
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

    String test = "p"+String(getBottle()->getUser()->getProgress()) + "," + String(getBottle()->getUser()->getIntake());
    getBluetooth()->print("p"+String(getBottle()->getUser()->getProgress()));
    getBluetooth()->println(" i"+String(getBottle()->getUser()->getIntake())+"#");
    /*getBluetooth()->print(" ");
    getBluetooth()->println(getBottle()->getUser()->getIntake());*/

    Serial.print("Sent: ");
    Serial.print(getBottle()->getUser()->getProgress());
    Serial.print("% ");
    Serial.print(getBottle()->getUser()->getIntake());
    Serial.println("L");
}