
float angle     = 0;
float increment = 0.01;
int target      = 0;
float acc       = 0.5;
int rects       = 12;
float side      = stageW / rects; 
float view      = side * rects;

// ================================================================

color[] colors_1 = new color[rects];
color[] colors_2 = new color[rects];

// ================================================================

float x = 0;
float y = 0;
float[] z = new float[rects];

// ================================================================

void tunnelSetting(){

}

// ================================================================

void tunnelColorSettings(){
	colors_1[0]  = #ffd700;
	colors_1[1]  = #ffca00;
	colors_1[2]  = #ffbc00;
	colors_1[3]  = #ffaf00;
	colors_1[4]  = #ffa200;
	colors_1[5]  = #ff9500;
	colors_1[6]  = #ff8700;
	colors_1[7]  = #ff7a00;
	colors_1[8]  = #ff6d00;
	colors_1[9]  = #ff6000;
	colors_1[10] = #ff5200;
	colors_1[11] = #ff4500;
}

// ================================================================

void tunnelRender(){

	for (int i = 0; i < rects; ++i) {

		int index     = i;
		color selectC = colors_1[index];
		color fillC   = color(selectC);
		// color fillC = 255;

		int strokeW = (int)map(knob[10], 0, 100, 1, 20);
		strokeWeight(strokeW);

		// fill(fillC, 5);
		noFill();
		stroke(fillC);

		x = 0;
		y = 0;
		// z[i] = map(side * i, 0, (width / 4) * rects, -600, 600);

		float scope = view / 2;

		if (frameCount < rects) z[i] = map(side * i, 0, view, -scope, scope);

		if (z[i] > scope) z[i] = -scope;
		else z[i] += acc;
		
		float w = side;
		float h = side;
		float d = side;

		updateMovement();
		updateColor();

		if(CENTER) {
			x += width / 2;
			y += height / 2;
			z[i] += 0;
		}

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
}