float angle = 0;
float increment = 0.01;
int target = 0;

// ================================================================

color[] colors_1 = new color[5];
color[] colors_2 = new color[5];

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

	float rects = 4;
		
	for (int i = 0; i < rects; ++i) {
		int index = (int)map(i, 0, rects, 0, 4);
		color selectC = colors_1[index];
		color fillC = color(selectC);

		noFill();
		stroke(fillC);

		float side = width / 4;

		float x = 0;
		float y = 0;
		float z = map(side * i, 0, (width / 4) * rects, -600, 600);

		float w = side;
		float h = side;
		float d = side;

		pushMatrix();
			translate(x, y, z);
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