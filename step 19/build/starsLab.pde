
int stars = 200;

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
		starSound(i);

		pushMatrix();
			translate(xS[i], yS[i], zS[i]);

			sphereDetail(10);
			sphere(sizeS[i]);

		 popMatrix();
	}
}

// ================================================================

void starsPositioning(int i) {
	xS[i] = random(-scope, scope);
	yS[i] = random(-scope, scope);
	zS[i] = random(-scope, scope);
}

// ================================================================

void starSizer(int i){
	float sizer = map(zS[i], -scope, scope, 2, 6);
	sizeS[i] = sizer;
}

// ================================================================

void starPainter(int i){
	int paint = (int)map(zS[i], -scope, 0, 0, 200);

	stroke(255, paint);

	noFill();
	if(pad[7]) fill(255, paint);
}

// ================================================================

float iperSpace = 10;

// ================================================================

void starLocator(int i){
	iperSpace = map(knob[12], 0, 100, 0, 10);

	if (zS[i] > scope) {
		xS[i] = random(-scope, scope);
		yS[i] = random(-scope, scope);
		zS[i] = -scope;
	}
	else zS[i] += iperSpace;
}

// ================================================================

void starSound(int i){
	sizeS[i] = sizeS[i] * audioData[4];
}