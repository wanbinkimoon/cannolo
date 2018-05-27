boolean CENTER = false;

// ================================================================

float eyeX;
float eyeY;
float eyeZ;
float centerX;
float centerY;
float centerZ;
float upX;
float upY;
float upZ;

// ================================================================

void viewSettings(){
	eyeX    =  width / 2;
	eyeY    =  height / 2; 
	// eyeZ    = (height / 2) / tan(PI * 30.0 / 180.0);
	eyeZ 		= 0;
	centerX = width / 2;
	centerY = height / 2;
	centerZ = 0.0;
	upX     = 0.0; 
	upY     = 1.0;
	upZ     = 0.0;

	camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
}

// ================================================================

void viewUpdate(){
	perspective();

	// eyeX = map(knob[0], 0, 100, -1000, 1000);
	// eyeY = map(knob[1], 0, 100, -1000, 1000);
	// eyeZ = map(knob[2], 0, 100, -1000, 1000);

	// eyeX    = 
	// eyeY    = 
	// centerX = 
	// centerY = 
	// centerZ = 
	// upX     = 
	// upY     = 
	// upZ     = 

	camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);

}