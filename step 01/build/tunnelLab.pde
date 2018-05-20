
// ================================================================

void tunnelRender(){
float rects = knob[8];
float step = knob[9];

	for (int i = 0; i < rects; ++i) {

		noStroke();
		fill((i * 5));

		float x = i * (step / 2);
		float y = i * (step / 2);
		float w = width - (step * i);
		float h = height - (step * i);

		rect(x, y, w, h);
	}
}