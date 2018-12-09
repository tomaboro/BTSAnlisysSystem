package com.motek.btsAnalisys;

public class Main {
    public static void main(String[] args) {
        Application app = new Application();
        if(args[0] == "mock") {
            app.startMock();
        } else {
            app.start();
        }
    }
}
