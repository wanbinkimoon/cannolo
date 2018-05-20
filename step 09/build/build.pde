 int stageW      = 800;
int stageH      = 800;
color bgC       = #2F2F2F;
String dataPATH = "../../data";

// ================================================================

void settings(){ 
	size(stageW, stageH, P3D);
	// fullScreen();
}

// ================================================================

void setup() {
	background(bgC);
	midiSetup();
	tunnelColorSettings();
	audioSettings();
	camSettings();
}

// ================================================================
void draw() {
	background(bgC);
	tunnelRender();
	audioDataUpdate();
	camUpdate();
}
