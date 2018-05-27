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
	size(stageW, stageH, P3D);
	// fullScreen();
}

// ================================================================

public void setup() {
	background(bgC);
	midiSetup();
	tunnelColorSettings();
	audioSettings();
	camSettings();
}

// ================================================================
public void draw() {
	background(bgC);
	tunnelRender();
	audioDataUpdate();
	camUpdate();
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

PeasyCam cam;

// ================================================================

public void camSettings(){
	cam = new PeasyCam(this, 600);

}

// ================================================================

public void camUpdate(){
	// cam.rotateX((float)knob[0] / 1000);
	// cam.rotateY((float)knob[1] / 1000);
	// cam.rotateZ((float)knob[2] / 1000);
	// cam.setDistance((float)map(knob[3], 0, 100, 100.0, -1200.0));
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

	// for (int i = 0; i < padNumb; ++i) {
	// 		pad[i] = false;
	// }	
	
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

float xoff = 0.0f;
float n;

// ================================================================

public void noiseUpdate(){
	float speed = map(knob[4], 0, 100, .0f, .01f);
 	xoff += speed;
  n = noise(xoff);
}
float angle     = 0;
float increment = 0.01f;
int target      = 0;
float acc       = 0.5f;
int rects       = 4;
// ================================================================

int[] colors_1 = new int[5];
int[] colors_2 = new int[5];

// ================================================================

float x = 0;
float y = 0;
float[] z = new float[rects];

// ================================================================

public void tunnelColorSettings(){
	colors_1[0] = 0xffed6b5a;
	colors_1[1] = 0xfff4f1bc;
	colors_1[2] = 0xff9bc1bb;
	colors_1[3] = 0xff5aa3a8;
	colors_1[4] = 0xffe5eade;

	// colors_2[0] = #41ead4;
	// colors_2[1] = #ff206e;
	// colors_2[2] = #fbff12;
	// colors_2[3] = #65ff00;
	// colors_2[4] = #ff0000;
}

// ================================================================

public void tunnelRender(){
		
	for (int i = 0; i < rects; ++i) {
		int index = (int)map(i, 0, rects, 0, 4);
		int selectC = colors_1[index];
		int fillC = color(selectC);

		noFill();
		stroke(fillC);

		float side = width / 4;

		x = 0;
		y = 0;
		if (frameCount < 4) z[i] = map(side * i, 0, (width / 4) * rects, -600, 600);
		else z[i] += acc;

		if(z[i] > 600) z[i] = -600;
		
		float w = side;
		float h = side;
		float d = side;

		updateMovement();
		updateColor();

		pushMatrix();
			translate(x, y, z[i]);
			rotationManager(i);
			box(w, h, d);
		popMatrix();

	}
}

// ================================================================

public void rotationManager(int index){
	if (target == 4) target = 0;
	if(index == target) {
		rotateZ(angle);
		angle += increment;
	}
	if (angle >= HALF_PI) {
		angle = 0;
		target++;
	}
}

// ================================================================

public void updateMovement(){
	acc = map(knob[8], 0, 100, 0.5f, 5);
	increment = map(knob[9], 0, 100, 0.01f, 0.5f);

	println("acc: "+acc);
}

// ================================================================

public void updateColor(){
	if(pad[0]) {
		colors_1[0] = 0xff41ead4;
		colors_1[1] = 0xffff206e;
		colors_1[2] = 0xfffbff12;
		colors_1[3] = 0xff65ff00;
		colors_1[4] = 0xffff0000;
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
