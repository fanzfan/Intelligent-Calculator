package com.vela.calculator;

public class NoCorrespondingBracketException extends Exception {
    NoCorrespondingBracketException() {
        MainGUI.jTextArea.append("\nError!!\nCheck the NUMBER of \"(\" and \")\" ");
    }
}
