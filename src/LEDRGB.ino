/*===========================
Program ktory dziala z FuzzyLEDControler,
na Serial przychodza wartosci on je dekoduje i zmienia kolor diody ze wspolna katoda na pinach PWM.
Ten kod najpierw wgrac do Arduino, a potem uzywac aplikacji w JAVA
============================*/

//piny z odpowiednimi diodami
const int rPin = 11;
const int gPin = 9;
const int bPin = 10;

//definicja danych
int redCol = 0;
int greenCol = 0;
int blueCol = 0;

void setup()
{
	Serial.begin(9600);
	Serial.setTimeout(10); //sets the maximum milliseconds to wait for serial data
	pinMode(rPin, OUTPUT); //wyjscia na piny
	pinMode(gPin, OUTPUT);
	pinMode(bPin, OUTPUT);
}

void loop()
{
	String red = "";
	String green = "";
	String blue = "";

	while (Serial.available() > 0)
	{
		//Program dostaje na serialu liczbe np 234100080 ktora sklada sie na R:234 G:100 B:080
		String x = Serial.readString();

		//Do odpowiednich stringow skladuje te dane
		red = x.substring(0, 3);
		green = x.substring(3, 6);
		blue = x.substring(6, 9);

		//Parsuje do inta
		redCol = red.toInt();
		greenCol = green.toInt();
		blueCol = blue.toInt();

		//zmienia kolor diody zgodnie z dostana liczba
		setColor(redCol, greenCol, blueCol);
	}
}

//Ustawia kolor diody na zdefiniowanych pinach wg przyjetych wartosci
void setColor(int rVal, int gVal, int bVal)
{
	analogWrite(rPin, rVal);
	analogWrite(gPin, gVal);
	analogWrite(bPin, bVal);
}