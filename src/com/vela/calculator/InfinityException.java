package com.vela.calculator;

// If the num is Infinity, then throw this exception
public class InfinityException extends Exception{
    InfinityException(){
        MainGUI.jTextArea.append(" = Infinity");
    }
}
