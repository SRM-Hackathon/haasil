#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include "DHT.h"

#define DHTPIN D4
#define DHTTYPE DHT11
#define FIREBASE_HOST ""
#define FIREBASE_AUTH ""
#define WIFI_SSID ""
#define WIFI_PASSWORD ""

DHT dht(DHTPIN, DHTTYPE);

int r1 = D1;
int r2 = D2;

void setup(){
  Serial.begin(9600);
  pinMode(r1,OUTPUT);
  pinMode(r2,OUTPUT);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  dht.begin();
  delay(500);
  Serial.println("connected: ");
  Serial.println(WiFi.localIP());
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.setFloat("temperature", 0);
  Firebase.setFloat("humidity", 0);
  Firebase.setFloat("heat_index", 0);
}


void loop() {
    delay(2000);
    float h = dht.readHumidity();
    float t = dht.readTemperature();
    if (isnan(h) || isnan(t)) {
      Serial.println("Failed to read from DHT sensor!");
      return;
    }
    float hic = dht.computeHeatIndex(t, h, false);
    Serial.print("Humidity: ");
    Serial.print(h);
    Serial.print(" \t");
    Serial.print("Temperature: ");
    Serial.print(t);
    Serial.print(" *C\t ");
    Serial.print("Heat index: ");
    Serial.print(hic);
    Serial.println(" *C ");

    Firebase.setFloat("temperature", t);
    Firebase.setFloat("humidity", h);
    Firebase.setFloat("heat_index", hic);

    if (Firebase.failed()) {
        Serial.print("setting /number failed:");
        Serial.println(Firebase.error());
        return;
    }
}

