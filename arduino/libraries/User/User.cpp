#include "Arduino.h"
#include "User.h"

User::User(int weight){
    setWeight(weight);
    setIntake(0);
}

void User::setWeight(int weight){
    _weight = weight;
    setGoal(calculateGoal(weight));
}

int User::getWeight(){
    return _weight;
}

void User::setIntake(float intake){
    _intake = intake;
    setProgress(calculateProgress(intake, getGoal()));
}

float User::getIntake(){
    return _intake;
}

void User::addIntake(float intake){
    setIntake(getIntake() + intake);
}

void User::setGoal(float goal){
    _goal = goal;
}

float User::getGoal(){
  return _goal;
}

float User::calculateGoal(int weight){
    return (float)weight/30;
}

void User::setProgress(int progress){
    _progress = progress;
}

int User::getProgress(){
    return _progress;
}

int User::calculateProgress(float intake, float goal){
    return (int)((intake*100)/goal);
}