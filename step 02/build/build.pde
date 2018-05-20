int stageW      = 800;
int stageH      = 800;
color bgC       = #FF7700;
String dataPATH = "../../data";

// ================================================================

void settings(){ 
	size(stageW, stageH);
	// fullscreen();
}

// ================================================================

void setup() {
	background(bgC);
	midiSetup();
	tunnelColorSettings();
}

// ================================================================
void draw() {
	background(bgC);
	tunnelRender();
}
