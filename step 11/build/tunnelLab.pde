


float angle     = 0;
float increment = 0.01;
int target      = 0;
float acc       = 0.5;
int rects       = 4;
// ================================================================

color[] colors_1 = new color[5];
color[] colors_2 = new color[5];

// ================================================================

float x = 0;
float y = 0;
float[] z = new float[rects];

// ================================================================

void tunnelColorSettings(){
	colors_1[0] = #ed6b5a;
	colors_1[1] = #f4f1bc;
	colors_1[2] = #9bc1bb;
	colors_1[3] = #5aa3a8;
	colors_1[4] = #e5eade;

	// colors_2[0] = #41ead4;
	// colors_2[1] = #ff206e;
	// colors_2[2] = #fbff12;
	// colors_2[3] = #65ff00;
	// colors_2[4] = #ff0000;
}

// ================================================================

void tunnelRender(){
		
	for (int i = 0; i < rects; ++i) {
		int index = (int)map(i, 0, rects, 0, 4);
		color selectC = colors_1[index];
		color fillC = color(selectC);

		noFill();
		stroke(fillC);

		float side = width / 4;

		x = 0;
		y = 0;
		if (frameCount < 4) z[i] = map(side * i, 0, (width / 4) * rects, -600, 600);
		else z[i] += acc;

		if(z[i] > 600) z[i] = -600;
		
		float w = side;
		float h = side;
		float d = side;

		updateMovement();
		updateColor();

		pushMatrix();
			translate(x, y, z[i]);
			rotationManager(i);
			box(w, h, d);
		popMatrix();

	}
}

// ================================================================

void rotationManager(int index){
	if (target == 4) target = 0;
	if(index == target) {
		rotateZ(angle);
		angle += increment;
	}
	if (angle >= HALF_PI) {
		angle = 0;
		target++;
	}
}

// ================================================================

void updateMovement(){
	acc = map(knob[8], 0, 100, 0.5, 5);
	increment = map(knob[9], 0, 100, 0.01, 0.5);
}

// ================================================================

void updateColor(){
	if(pad[0]) {
		colors_1[0] = #41ead4;
		colors_1[1] = #ff206e;
		colors_1[2] = #fbff12;
		colors_1[3] = #65ff00;
		colors_1[4] = #ff0000;
	} else {
		colors_1[0] = #ed6b5a;
		colors_1[1] = #f4f1bc;
		colors_1[2] = #9bc1bb;
		colors_1[3] = #5aa3a8;
		colors_1[4] = #e5eade;
	}
}