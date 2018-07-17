int stageW      = 800;
int stageH      = 800;
color bgC       = #2F2F2F;
String dataPATH = "../../data";

// ================================================================

void settings(){ 
	// fullScreen(P3D, 2);
	// fullScreen(P3D, SPAN);
	size(stageW, stageH, P3D);
	pixelDensity(displayDensity());	

}

// ================================================================

void setup() {
	background(bgC);
	
	midiSetup();
	camSettings();
	// viewSettings();
	audioSettings();

	tunnelColorSettings();
	tunnelSetting();
	starsSettings();


}

// ================================================================
void draw() {
	background(bgC);
	audioDataUpdate();
	camUpdate();
	// viewUpdate();
	
	starsRender();
	tunnelRender();
}

// ================================================================

void keyPressed(){	
	switch (key) {
		case 'q':
			exit();
		case 'p':
			screenShot();
		break;
	}
}

// ================================================================

boolean letsRender = false;
int     renderNum  = 0;
String  renderPATH = "../render/";

// ================================================================

void screenShot(){
	letsRender = true;
	if (letsRender) {
		letsRender = false;
		save(renderPATH + renderNum + ".png");
		renderNum++;
	}
}