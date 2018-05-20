import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
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
	// size(stageW, stageH);
	fullScreen();
}

// ================================================================

public void setup() {
	background(bgC);
	midiSetup();
	tunnelColorSettings();
	audioSettings();
}

// ================================================================
public void draw() {
	background(bgC);
	tunnelRender();
	audioDataUpdate();
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
      float indexCon = constrain(indexAvg, 0, audioMax);
      audioData[i] = indexCon;
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
	for (int i = 0; i < padNumb; ++i) {
			pad[i] = false;
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
int Y_AXIS = 1;
int X_AXIS = 2;

// ================================================================

int[] colors_1 = new int[5];
int shadow_1;
int shadow_2;

// ================================================================

public void tunnelColorSettings(){
	colors_1[0] = 0xffed6b5a;
	colors_1[1] = 0xfff4f1bc;
	colors_1[2] = 0xff9bc1bb;
	colors_1[3] = 0xff5aa3a8;
	colors_1[4] = 0xffe5eade;
}

// ================================================================

public void tunnelRender(){
	int step = (int)map(knob[9], 0, 100, 20, 180);
	float rectsControl = map(knob[8], 0, 100, 10, 80);

	float rects = map(rectsControl, 4, 80, 4, (width / step));
		
	for (int i = 0; i < rects; ++i) {

		int alpha = (int)map(knob[10], 0, 100, 0, 255);
		// int gray = rand + (50 * (i + 1));
		int selectC = color(255);

		if(pad[0]) selectC = colors_1[0];
		if(pad[1]) selectC = colors_1[1];
		if(pad[2]) selectC = colors_1[2];
		if(pad[3]) selectC = colors_1[3];
		if(pad[4]) selectC = colors_1[4];

		int fillC = color(selectC, alpha);

		shadow_1 = color(40, 40);
		shadow_2 = color(0, 1);

		noStroke();
		fill(fillC);

		float audioW = map(audioData[3], 0, 100, 4, knob[12]);
		float audioH = map(audioData[11], 0, 100, 4, knob[11]);

		int x = i * (step / 2) - ((int)audioW / 2);
		int y = i * (step / 2) - ((int)audioH / 2);
		float w = width - (step * i) + audioW;
		float h = height - (step * i) + audioH;

		rect(x, y, w, h);
		setGradient(x, y, PApplet.parseFloat(step / 2), h, shadow_1, shadow_2, X_AXIS);
		setGradient((int)(w + x - (step / 2)), y, PApplet.parseFloat(step / 2), h, shadow_2, shadow_1, X_AXIS);
		setGradient(x, y, w, PApplet.parseFloat(step / 2), shadow_1, shadow_2, Y_AXIS);
		setGradient(x, (int)(h + y - (step / 2)), w, PApplet.parseFloat(step / 2), shadow_2, shadow_1, Y_AXIS);
	}
}

// ================================================================

public void setGradient(int x, int y, float w, float h, int c1, int c2, int axis ) {

  noFill();

  if (axis == Y_AXIS) {  // Top to bottom gradient
    for (int i = y; i <= y+h; i++) {
      float inter = map(i, y, y+h, 0, 1);
      int c = lerpColor(c1, c2, inter);
      stroke(c);
      line(x, i, x+w, i);
    }
  }  
  else if (axis == X_AXIS) {  // Left to right gradient
    for (int i = x; i <= x+w; i++) {
      float inter = map(i, x, x+w, 0, 1);
      int c = lerpColor(c1, c2, inter);
      stroke(c);
      line(i, y, i, y+h);
    }
  }
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
