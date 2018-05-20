int Y_AXIS = 1;
int X_AXIS = 2;

// ================================================================

color[] colors_1 = new color[5];
color shadow_1;
color shadow_2;

// ================================================================

void tunnelColorSettings(){
	colors_1[0] = #ed6b5a;
	colors_1[1] = #f4f1bc;
	colors_1[2] = #9bc1bb;
	colors_1[3] = #5aa3a8;
	colors_1[4] = #e5eade;
}

// ================================================================

void tunnelRender(){
	int step = (int)map(knob[9], 0, 100, 20, 180);
	float rectsControl = map(knob[8], 0, 100, 10, 80);

	float rects = map(rectsControl, 4, 80, 4, (width / step));
		
	for (int i = 0; i < rects; ++i) {

		int alpha = (int)map(knob[10], 0, 100, 0, 255);
		// int gray = rand + (50 * (i + 1));
		color selectC = color(255);

		if(pad[0]) selectC = colors_1[0];
		if(pad[1]) selectC = colors_1[1];
		if(pad[2]) selectC = colors_1[2];
		if(pad[3]) selectC = colors_1[3];
		if(pad[4]) selectC = colors_1[4];

		color fillC = color(selectC, alpha);

		shadow_1 = color(40, 40);
		shadow_2 = color(0, 1);

		noStroke();
		fill(fillC);

		noiseUpdate();

		float audioW = map(audioData[3], 0, 100, 4, knob[12]);
		float audioH = map(audioData[11], 0, 100, 4, knob[11]);

		float x = i * (step / 2) - ((int)audioW / 2) + (n * 100);
		float y = i * (step / 2) - ((int)audioH / 2) + (n * 50);
		float w = width - (step * i) + audioW;
		float h = height - (step * i) + audioH;


		rect(x, y, w, h);
		setGradient(x, y, float(step / 2), h, shadow_1, shadow_2, X_AXIS);
		setGradient((int)(w + x - (step / 2)), y, float(step / 2), h, shadow_2, shadow_1, X_AXIS);
		setGradient(x, y, w, float(step / 2), shadow_1, shadow_2, Y_AXIS);
		setGradient(x, (int)(h + y - (step / 2)), w, float(step / 2), shadow_2, shadow_1, Y_AXIS);
	}
}

// ================================================================

void setGradient(float x, float y, float w, float h, color c1, color c2, int axis ) {

  noFill();

  if (axis == Y_AXIS) {  // Top to bottom gradient
    for (int i = (int)y; i <= (int)y+h; i++) {
      float inter = map(i, y, y+h, 0, 1);
      color c = lerpColor(c1, c2, inter);
      stroke(c);
      line(x, i, x+w, i);
    }
  }  
  else if (axis == X_AXIS) {  // Left to right gradient
    for (int i = (int)x; i <= (int)x+w; i++) {
      float inter = map(i, x, x+w, 0, 1);
      color c = lerpColor(c1, c2, inter);
      stroke(c);
      line(i, y, i, y+h);
    }
  }
}