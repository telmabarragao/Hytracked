#ifndef User_h
#define User_h

#include "Arduino.h"

class User
{
    public:
        User(int weight);
        void setWeight(int weight);
        int getWeight();
        void setIntake(float intake);
        float getIntake();
        void addIntake(float intake);
        void setGoal(float goal);
        float getGoal();
        float calculateGoal(int weight);
        void setProgress(int progress);
        int getProgress();
        int calculateProgress(float intake, float goal);
    private:
        int _weight;
        float _intake;
        float _goal;
        int _progress;
};
#endif