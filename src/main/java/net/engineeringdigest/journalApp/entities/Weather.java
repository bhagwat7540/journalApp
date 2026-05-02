package net.engineeringdigest.journalApp.entities;

public class Weather{
    public Current current;

    public class Current{
        public double temp_c;
        public double temp_f;
        public int humidity;
    }
}

