package com.vela.calculator;

import java.io.IOException;

public class VoidResultException extends IOException {
    VoidResultException() {
        MainGUI.jTextArea.append("\nCan't get result, try to rearrange the INPUT content");
    }
}
