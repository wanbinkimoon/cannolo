
int stars = 40;

// ================================================================
	
	float[] xS = new float[stars];
	float[] yS = new float[stars];
	float[] zS = new float[stars];

	float[] sizeS = new float[stars];

// ================================================================

void starsSettings() {
	for (int i = 0; i < stars - 1; ++i) {
		starsPositioning(i);
	}
}

// ================================================================

void starsRender() {
	for (int i = 0; i < stars - 1; ++i) {
		starLocator(i);	

		starSizer(i);	
		starPainter(i);

		pushMatrix();
			translate(xS[i], yS[i], zS[i]);

			sphereDetail(4);
			sphere(sizeS[i]);
		 popMatrix();
	}
}

// ================================================================

void starsPositioning(int i) {
	xS[i] = random(-scope, scope);
	yS[i] = random(-scope, scope);
	zS[i] = random(-scope, 0);
}

// ================================================================

void starSizer(int i){
	float sizer = map(zS[i], -scope, 0, 2, 12);
	sizeS[i] = sizer;
}

// ================================================================

void starPainter(int i){
	float paint = map(zS[i], -scope, 0, 40, 200);

	stroke(paint);
	
	noFill();
	if(pad[7]) fill(paint, 10);
}

// ================================================================

float iperSpace = 10;

// ================================================================

void starLocator(int i){
	iperSpace = map(knob[12], 0, 100, 0, 50);

	if (zS[i] > scope) zS[i] = -scope;
	else zS[i] += iperSpace;
}