
float xoff = 0.0;
float n;

// ================================================================

void noiseUpdate(){
	float speed = map(knob[4], 0, 100, .0, .01);
 	xoff += speed;
  n = noise(xoff);
}