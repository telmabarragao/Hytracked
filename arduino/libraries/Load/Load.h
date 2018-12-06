#ifndef Load_h
#define Load_h

#include "Arduino.h"

class Load{
    public:
        Load(int loadPin);
        float getVolume();
    private:
};

#endif