import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import peasy.*; 
import themidibus.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class build extends PApplet {

int stageW      = 800;
int stageH      = 800;
int bgC       = 0xff2F2F2F;
String dataPATH = "../../data";

// ================================================================

public void settings(){ 
	// size(stageW, stageH, P3D);
	fullScreen(P3D);
}

// ================================================================

public void setup() {
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
public void draw() {
	background(bgC);
	audioDataUpdate();
	camUpdate();
	// viewUpdate();
	
	starsRender();
	tunnelRender();
}




// ================================================================

Minim minim;
AudioInput audio;
FFT audioFFT;

// ================================================================

int audioRange  = 12;
int audioMax = 100;

float audioAmp = 270.0f;
float audioIndex = 0.005f;
float audioIndexAmp = audioIndex;
float audioIndexStep = 0.425f;

float[] audioData = new float[audioRange];

// ================================================================

public void audioSettings(){
  minim = new Minim(this);
  audio = minim.getLineIn(Minim.STEREO);

  audioFFT = new FFT(audio.bufferSize(), audio.sampleRate());
  audioFFT.linAverages(audioRange);

  audioFFT.window(FFT.NONE);
  // audioFFT.window(FFT.BARTLETT);
  // audioFFT.window(FFT.BARTLETTHANN);
  // audioFFT.window(FFT.BLACKMAN);
  // audioFFT.window(FFT.COSINE);
  // audioFFT.window(FFT.GAUSS);
  // audioFFT.window(FFT.HAMMING);
  // audioFFT.window(FFT.HANN);
  // audioFFT.window(FFT.LANCZOS);
  // audioFFT.window(FFT.TRIANGULAR);
}

// ================================================================

public void audioDataUpdate(){
  audioFFT.forward(audio.mix);
  updateAudio();
}

// ================================================================

  public void updateAudio(){
    for (int i = 0; i < audioRange; ++i) {
      float indexAvg = (audioFFT.getAvg(i) * audioAmp) * audioIndexAmp;
      float indexCon = constrain(indexAvg, audioMax, audioMax * 2);
      
      if(indexAvg > audioMax) audioData[i] = indexCon;
      else audioData[i] = 100;

      audioData[i] = audioData[i] / 100;
      audioIndexAmp += audioIndexStep;
    }

    audioIndexAmp = audioIndex;
  }

  // ================================================================
  
  public void audioMidiValueUpdate(){
    audioAmp = map(knob[5], 0, 100, 10, 60);
    audioIndex = map(knob[6], 0, 100, 50, 100);
    audioIndexStep = map(knob[7], 0, 100, 2.5f, 100);
  }


// ================================================================

PeasyCam cam;

// ================================================================

public void camSettings(){
	cam = new PeasyCam(this, view / 2);
}

// ================================================================

public void camUpdate(){
	// double x        = map(knob[0], 0, 100, pow(10, 4) * -1, pow(10, 4));
	// double y        = map(knob[1], 0, 100, pow(10, 4) * -1, pow(10, 4));
	// double z        = map(knob[2], 0, 100, pow(10, 4) * -1, pow(10, 4));

	// double x        = 0;
	// double y        = 0;
	// double z        = 0;
	// double distance = map(knob[3], 0, 100,  0, pow(10, 6) * -1);

	// println(x + " + " + y + " + " + z + " + " + distance);

	// println("d: "+ cam.getDistance());
	// println("p: " + cam.getLookAt()[0] + " " + cam.getLookAt()[1] + " " + cam.getLookAt()[2]);	

	// cam.lookAt(x, y, z, distance);

	// cam.rotateX((float)knob[0] / 1000);
	// cam.rotateY((float)knob[1] / 1000);
	// cam.rotateZ((float)knob[2] / 1000);
	
	// cam.setDistance((float)map(knob[3], 0, 100, - (view * 2), view * 2));
}
 

// ================================================================

MidiBus myBus; 

// ================================================================

public void controllerChange(int channel, int number, int value) {  
	midiUpdate(channel, number, value);

  // Receive a controllerChange
  // println();
  // println("Controller Change:");
  // println("--------");
  // println("Channel:" + channel);
  // println("Number:" + number);
  // println("Value:" + value);
}

// ================================================================


int knobNumb = 16;
int[] knob = new int[knobNumb];
String knobTable;

// ================================================================

public void midiSetup(){
  MidiBus.list(); 
  myBus = new MidiBus(this, 0, 1);
}

public void midiUpdate(int channel, int number, int value){
	if(number == 21) knob[0] = (int)map(value, 0, 127, 0, 100);
	if(number == 22) knob[1] = (int)map(value, 0, 127, 0, 100);
	if(number == 23) knob[2] = (int)map(value, 0, 127, 0, 100);
	if(number == 24) knob[3] = (int)map(value, 0, 127, 0, 100);
	if(number == 25) knob[4] = (int)map(value, 0, 127, 0, 100);
	if(number == 26) knob[5] = (int)map(value, 0, 127, 0, 100);
	if(number == 27) knob[6] = (int)map(value, 0, 127, 0, 100);
	if(number == 28) knob[7] = (int)map(value, 0, 127, 0, 100);
	if(number == 41) knob[8] = (int)map(value, 0, 127, 0, 100);
	if(number == 42) knob[9] = (int)map(value, 0, 127, 0, 100);
	if(number == 43) knob[10] = (int)map(value, 0, 127, 0, 100);
	if(number == 44) knob[11] = (int)map(value, 0, 127, 0, 100);
	if(number == 45) knob[12] = (int)map(value, 0, 127, 0, 100);
	if(number == 46) knob[13] = (int)map(value, 0, 127, 0, 100);
	if(number == 47) knob[14] = (int)map(value, 0, 127, 0, 100);
	if(number == 48) knob[15] = (int)map(value, 0, 127, 0, 100);
}

public void midiMonitor(){
	knobTable = "\n\n_________________________________________________________________________________________________________________________________\n|  001  |  002  |  003  |  004  |  005  |  006  |  007  |  008  |  009  |  010  |  011  |  012  |  013  |  014  |  015  |  016  |\n|  "+ String.format("%03d", knob[0]) +"  |  "+ String.format("%03d", knob[1]) +"  |  "+ String.format("%03d", knob[2]) +"  |  "+ String.format("%03d", knob[3]) +"  |  "+ String.format("%03d", knob[4]) +"  |  "+ String.format("%03d", knob[5]) +"  |  "+ String.format("%03d", knob[6]) +"  |  "+ String.format("%03d", knob[7]) +"  |  "+ String.format("%03d", knob[8]) +"  |  "+ String.format("%03d", knob[9]) +"  |  "+ String.format("%03d", knob[10]) +"  |  "+ String.format("%03d", knob[11]) +"  |  "+ String.format("%03d", knob[12]) +"  |  "+ String.format("%03d", knob[13]) +"  |  "+ String.format("%03d", knob[14]) +"  |  "+ String.format("%03d", knob[15]) +"  |\n_________________________________________________________________________________________________________________________________";
	println(knobTable);
}

// ================================================================

int padNumb = 8;
boolean[] pad = new boolean[padNumb];

// ================================================================

public void noteOn(int channel, int number, int value) {
	padSwitch(channel, number, value);

  // Receive a controllerChange
  // println();
  // println("Controller Change:");
  // println("--------");
  // println("Channel:" + channel);
  // println("Number:" + number);
  // println("Value:" + value);
}

public void padSwitch(int channel, int number, int value){

	if(arrow[0]) {
		for (int i = 0; i < padNumb; ++i) {
				pad[i] = false;
		}	
	}
	
	if(number ==  9) pad[0] = !pad[0];
	if(number == 10) pad[1] = !pad[1];
	if(number == 11) pad[2] = !pad[2];
	if(number == 12) pad[3] = !pad[3];
	if(number == 25) pad[4] = !pad[4];
	if(number == 26) pad[5] = !pad[5];
	if(number == 27) pad[6] = !pad[6];
	if(number == 28) pad[7] = !pad[7];

	// padMonitor();
}

public void padMonitor(){
	print("  0: " + pad[0]);
	print("  1: " + pad[1]);
	print("  2: " + pad[2]);
	print("  3: " + pad[3]);
	print("  4: " + pad[4]);
	print("  5: " + pad[5]);
	print("  6: " + pad[6]);
	print("  7: " + pad[7] + "\n");
	println();
	println("____________________\n");
	println();
}

// ================================================================

int arrowNumb = 4;
boolean[] arrow = new boolean[arrowNumb];

// ================================================================

public void rawMidi(byte[] data) {
	int number = (int)(data[1] & 0xFF);
	int value = (int)(data[2] & 0xFF);

	arrowSwitch(number);

  // Receive some raw data
  // data[0] will be the status byte
  // data[1] and data[2] will contain the parameter of the message (e.g. pitch and volume for noteOn noteOff)
 //  println();
 //  println("Raw Midi Data:");
 //  println("--------");
 //  println("Status Byte/MIDI Command:"+(int)(data[0] & 0xFF));
	// println("Number: " + number);	
	// println("Value: " + value);	
}


public void arrowSwitch(int number){
	if(number == 114) arrow[0] = !arrow[0];
	if(number == 115) arrow[1] = !arrow[1];
	if(number == 116) arrow[2] = !arrow[2];
	if(number == 117) arrow[3] = !arrow[3];

	// arrowMonitor();
}

public void arrowMonitor(){
	print("  0: " + arrow[0]);
	print("  1: " + arrow[1]);
	print("  2: " + arrow[2]);
	print("  3: " + arrow[3]);
	println();
	println("____________________\n");
	println();
}

float xoff = 0.0f;
float n;

// ================================================================

public void noiseUpdate(){
	float speed = map(knob[4], 0, 100, .0f, .01f);
 	xoff += speed;
  n = noise(xoff);
}

int stars = 40;

// ================================================================
	
	float[] xS = new float[stars];
	float[] yS = new float[stars];
	float[] zS = new float[stars];

	float[] sizeS = new float[stars];

// ================================================================

public void starsSettings() {
	for (int i = 0; i < stars - 1; ++i) {
		starsPositioning(i);
	}
}

// ================================================================

public void starsRender() {
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

public void starsPositioning(int i) {
	xS[i] = random(-scope, scope);
	yS[i] = random(-scope, scope);
	zS[i] = random(-scope, 0);
}

// ================================================================

public void starSizer(int i){
	float sizer = map(zS[i], -scope, 0, 2, 12);
	sizeS[i] = sizer;
}

// ================================================================

public void starPainter(int i){
	float paint = map(zS[i], -scope, 0, 40, 200);

	stroke(paint);
	
	noFill();
	if(pad[7]) fill(paint, 10);
}

// ================================================================

float iperSpace = 10;

// ================================================================

public void starLocator(int i){
	iperSpace = map(knob[12], 0, 100, 0, 50);

	if (zS[i] > scope) zS[i] = -scope;
	else zS[i] += iperSpace;
}



int rects       = audioRange;
int target      = 0;

float acc       = 0.5f;
float increment = 0.01f;
float resistance = 0.0f;
float[] angle     = new float[rects];

float side      = stageW / rects; 
float view      = side * rects;
float scope 		= view / 2;


// ================================================================

int[] colors_1 = new int[rects];
int[] colors_2 = new int[rects];

// ================================================================

float x = 0;
float y = 0;
float[] z = new float[rects];

// ================================================================

public void tunnelSetting(){

}

// ================================================================

public void tunnelColorSettings(){
	colors_1[0]  = 0xffffd700;
	colors_1[1]  = 0xffffca00;
	colors_1[2]  = 0xffffbc00;
	colors_1[3]  = 0xffffaf00;
	colors_1[4]  = 0xffffa200;
	colors_1[5]  = 0xffff9500;
	colors_1[6]  = 0xffff8700;
	colors_1[7]  = 0xffff7a00;
	colors_1[8]  = 0xffff6d00;
	colors_1[9]  = 0xffff6000;
	colors_1[10] = 0xffff5200;
	colors_1[11] = 0xffff4500;
}

// ================================================================

public void tunnelRender(){

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

public void rotationManager(int i){
	if (target == rects) target = 0;

	if(i == target) {
		angle[i] += increment;
	} else {
		angle[i] -= resistance;
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

public void updateMovement(){
	acc = map(knob[8], 0, 100, 0.5f, 5);
	increment = map(knob[9], 0, 100, 0.01f, 0.5f);
	resistance = map(knob[10], 0, 100, 0.0f, 0.05f);

	if (pad[0]) {
		for (int i = 0; i < angle.length; ++i) {
			angle[i] = 0;
		}
	}
}

// ================================================================

public void updateColor(int i){
	int index     = i;
	// color selectC = colors_1[index];

	colorMode(HSB);
	int hueSelector = (int)map(z[i], -scope, scope, 35, 10);
	int selectC   = color(hueSelector, 360, 360);
	int fillC     = selectC;
	// color fillC = 255;


	noFill();
	if(pad[1]) fill(fillC, 5);
	stroke(fillC);
}

// ================================================================

public void positionHandler(int i){
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

public void soundSize(int i){
	int freqSelector = (int)map(z[i], -scope, scope, 0, rects - 1);
	int factorMultiplier = (int)map(z[i], -scope, scope, 1, rects - 1);

	factor = map(knob[11], 0, 100, 10, 20);

	w = (side * audioData[freqSelector]) + factor + (factor * (factorMultiplier));
	h = (side * audioData[freqSelector]) + factor + (factor * (factorMultiplier));
	d = side;
}
boolean CENTER = false;

// ================================================================

float eyeX;
float eyeY;
float eyeZ;
float centerX;
float centerY;
float centerZ;
float upX;
float upY;
float upZ;

// ================================================================

public void viewSettings(){
	eyeX    =  width / 2;
	eyeY    =  height / 2; 
	// eyeZ    = (height / 2) / tan(PI * 30.0 / 180.0);
	eyeZ 		= 0;
	centerX = width / 2;
	centerY = height / 2;
	centerZ = 0.0f;
	upX     = 0.0f; 
	upY     = 1.0f;
	upZ     = 0.0f;

	camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
}

// ================================================================

public void viewUpdate(){
	perspective();

	// eyeX = map(knob[0], 0, 100, -1000, 1000);
	// eyeY = map(knob[1], 0, 100, -1000, 1000);
	// eyeZ = map(knob[2], 0, 100, -1000, 1000);

	// eyeX    = 
	// eyeY    = 
	// centerX = 
	// centerY = 
	// centerZ = 
	// upX     = 
	// upY     = 
	// upZ     = 

	camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);

}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "build" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
