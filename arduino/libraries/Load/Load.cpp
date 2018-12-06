#include "Arduino.h"
#include "Load.h"
#include "HX711.h"

Load::Load(int loadPin){
	HX711 scale(DOUT, CLK);
	scale.set_scale(-2006490); // calibration factor: -2006490
    }

float Load::getVolume(){
	scale.power_up();
	float weight = scale.get_units(3)
	scale.power_down();
    return weight ;
}