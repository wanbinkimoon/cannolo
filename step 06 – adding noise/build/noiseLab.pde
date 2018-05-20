
float xoff = 0.0;
float n;

// ================================================================

void noiseUpdate(){
	float speed = map(knob[13], 0, 100, .005, .05);
 	xoff += speed;
  n = noise(xoff);
}