import peasy.*;

// ================================================================

PeasyCam cam;

// ================================================================

void camSettings(){
	cam = new PeasyCam(this, 0, 0, width / 2 , view);
}

// ================================================================

void camUpdate(){
	double x        = map(knob[0], 0, 100, pow(10, 4) * -1, pow(10, 4));
	double y        = map(knob[1], 0, 100, pow(10, 4) * -1, pow(10, 4));
	double z        = map(knob[2], 0, 100, pow(10, 4) * -1, pow(10, 4));
	double distance = map(knob[3], 0, 100, pow(10, 4) * -1, pow(10, 4));

	// println(x + " + " + y + " + " + z + " + " + distance);

	println("d: "+ cam.getDistance());
	println("p: " + cam.getLookAt()[0] + " " + cam.getLookAt()[1] + " " + cam.getLookAt()[2]);	

	cam.lookAt(x, y, z, distance);

	// cam.rotateX((float)knob[0] / 1000);
	// cam.rotateY((float)knob[1] / 1000);
	// cam.rotateZ((float)knob[2] / 1000);
	// cam.setDistance((float)map(knob[3], 0, 100, -(view * 2), view * 2));
}