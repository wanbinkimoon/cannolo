


int rects       = audioRange;
int target      = 0;

float acc       = 0.5;
float increment = 0.0;
float resistance = 0.0;
float[] angle     = new float[rects];

float side      = stageW / rects; 
float view      = side * rects;
float scope 		= view / 2;


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

		updateMovement();
		positionHandler(i);
		updateColor(i);
		
		pushMatrix();
			translate(x, y, z[i]);
			if(!pad[0]) rotationManager(i);
			soundSize(i);
			box(w, h, d);
		popMatrix();

	}
}

// ================================================================

void rotationManager(int i){
	if (target == rects - 1) target = 0;

	if(i == target) {
		angle[i] += increment;
	} else {
		angle[i] += increment - resistance;
	}

	rotateZ(angle[i]);

		// println("target: "+target);
		// println("angle[target]: "+angle[target]);

	if (angle[target] >= HALF_PI) {
		// angle[i] = 0;
		target++;
	}
}

// ================================================================

void updateMovement(){
	acc = map(knob[8], 0, 100, 0.5, 5);
	increment = map(knob[9], 0, 100, 0.0, 0.5);
	resistance = map(knob[10], 0, 100, 0.0, 0.5);

	if (pad[0]) {
		for (int i = 0; i < angle.length; ++i) {
			angle[i] = 0;
		}
	}
}

// ================================================================

void updateColor(int i){
	int index     = i;
	// color selectC = colors_1[index];

	colorMode(HSB);
	int hueSelector = (int)map(z[i], -scope, scope, 35, 10);
	color selectC   = color(hueSelector, 360, 360);
	color fillC     = selectC;
	// color fillC = 255;


	noFill();
	if(pad[1]) fill(fillC, 5);
	stroke(fillC);
}

// ================================================================

void positionHandler(int i){
	x = 0;
	y = 0;
	
	if (frameCount < rects) z[i] = map(side * i, 0, view, -scope, scope);
	if (z[i] > scope) z[i] = -scope;
	else z[i] += acc;
}

// ================================================================

float w;
float h;
float d;
float factor;	

// ================================================================

void soundSize(int i){
	int freqSelector = (int)map(z[i], -scope, scope, 0, rects - 1);
	int factorMultiplier = (int)map(z[i], -scope, scope, 1, rects - 1);

	factor = map(knob[11], 0, 100, 10, 20);

	w = (side * audioData[freqSelector]) + factor + (factor * (factorMultiplier));
	h = (side * audioData[freqSelector]) + factor + (factor * (factorMultiplier));
	d = side;
}