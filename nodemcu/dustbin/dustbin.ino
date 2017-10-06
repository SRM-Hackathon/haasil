#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

int trigPin = D6;
int echoPin = D7;
int r1 = D0;
int r2 = D1;
int act_distance;
int distance;
void setup(){
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output
pinMode(echoPin, INPUT); // Sets the echoPin as an Input
pinMode(r1,OUTPUT);
pinMode(r2,OUTPUT);
  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
    Firebase.setFloat("Dustbins-level", 0);

  }


void loop() {
// put your main code here, to run repeatedly:
digitalWrite(trigPin, LOW);
delayMicroseconds(2);
// Sets the trigPin on HIGH state for 10 micro seconds
digitalWrite(trigPin, HIGH);
delayMicroseconds(1000);
digitalWrite(trigPin, LOW);
// Reads the echoPin, returns the sound wave travel time in microseconds
float duration = pulseIn(echoPin, HIGH);
// Calculating the distance
distance = (duration*0.034)/2 ;

act_distance = map(distance,4,16,100,0);

if(act_distance >0 && act_distance<=50){
  digitalWrite(r1,HIGH);
  digitalWrite(r2,LOW);
  }
else {
  digitalWrite(r2,HIGH);
  digitalWrite(r1,LOW);
  }
Serial.print("Distance: ");
Serial.println(distance);

// set value
  Firebase.setFloat("Dustbins-level", act_distance);
  // handle error
  if (Firebase.failed()) {
      Serial.print("setting /number failed:");
      Serial.println(Firebase.error());
      return;
  }
  delay(100);
}

