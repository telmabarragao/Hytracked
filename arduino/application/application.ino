#include <SoftwareSerial.h>

const int bluetoothTx = 2;
const int bluetoothRx = 3;

int userWeight = 62;
float totalConsumed = 0;

SoftwareSerial bluetooth(bluetoothTx, bluetoothRx);

void setup()
{
  Serial.begin(9600);
  bluetooth.begin(115200);
}

void loop()
{
  if(bluetooth.available()){
    switch((char)bluetooth.read()){
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
    totalConsumed += Serial.readString().toFloat();
  }
}

void receiveWeight(){
  userWeight = bluetooth.readString().toInt();
  Serial.print("Received: set user weight to ");
  Serial.print(userWeight);
  Serial.println("Kg.");
}

void sendGoal(){
  Serial.println("Received: get daily goal.");
  bluetooth.println(getGoal());
  Serial.print("Sent: ");
  Serial.print(getGoal());
  Serial.println("L");
}

void sendProgress(){
  Serial.println("Received: get progress.");
  bluetooth.print(getProgress());
  bluetooth.print(" ");
  bluetooth.println(totalConsumed);
  Serial.print("Sent: ");
  Serial.print(getProgress());
  Serial.print("% ");
  Serial.print(totalConsumed);
  Serial.println("L");
}

float getGoal(){
  return (float)userWeight/30;
}

int getProgress(){
  return (int)((totalConsumed*100)/getGoal());
}
