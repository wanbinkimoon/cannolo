import themidibus.*; 

// ================================================================

MidiBus myBus; 

// ================================================================

void controllerChange(int channel, int number, int value) {  
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

void midiSetup(){
  MidiBus.list(); 
  myBus = new MidiBus(this, 0, 1);
}

void midiUpdate(int channel, int number, int value){
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

void midiMonitor(){
	knobTable = "\n\n_________________________________________________________________________________________________________________________________\n|  001  |  002  |  003  |  004  |  005  |  006  |  007  |  008  |  009  |  010  |  011  |  012  |  013  |  014  |  015  |  016  |\n|  "+ String.format("%03d", knob[0]) +"  |  "+ String.format("%03d", knob[1]) +"  |  "+ String.format("%03d", knob[2]) +"  |  "+ String.format("%03d", knob[3]) +"  |  "+ String.format("%03d", knob[4]) +"  |  "+ String.format("%03d", knob[5]) +"  |  "+ String.format("%03d", knob[6]) +"  |  "+ String.format("%03d", knob[7]) +"  |  "+ String.format("%03d", knob[8]) +"  |  "+ String.format("%03d", knob[9]) +"  |  "+ String.format("%03d", knob[10]) +"  |  "+ String.format("%03d", knob[11]) +"  |  "+ String.format("%03d", knob[12]) +"  |  "+ String.format("%03d", knob[13]) +"  |  "+ String.format("%03d", knob[14]) +"  |  "+ String.format("%03d", knob[15]) +"  |\n_________________________________________________________________________________________________________________________________";
	println(knobTable);
}

// ================================================================

int padNumb = 8;
boolean[] pad = new boolean[padNumb];

// ================================================================

void noteOn(int channel, int number, int value) {
	padSwitch(channel, number, value);

  // Receive a controllerChange
  // println();
  // println("Controller Change:");
  // println("--------");
  // println("Channel:" + channel);
  // println("Number:" + number);
  // println("Value:" + value);
}

void padSwitch(int channel, int number, int value){

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

void padMonitor(){
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