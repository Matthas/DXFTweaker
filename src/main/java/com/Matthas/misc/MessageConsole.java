package com.Matthas.misc;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class MessageConsole {
    private JTextComponent textComponent;
    private PrintStream printStream;

    public MessageConsole(JTextComponent textComponent) {
        this.textComponent = textComponent;
        this.printStream = new PrintStream(new TextAreaOutputStream(textComponent));
    }

    public void redirectOut() {
        System.setOut(printStream);
    }

    public void redirectErr() {
        System.setErr(printStream);
    }

    private static class TextAreaOutputStream extends OutputStream {
        private JTextComponent textComponent;

        public TextAreaOutputStream(JTextComponent textComponent) {
            this.textComponent = textComponent;
        }

        @Override
        public void write(int b) throws IOException {
            appendText(String.valueOf((char) b));
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            appendText(new String(b, off, len));
        }

        private void appendText(String text) {
            SwingUtilities.invokeLater(() -> {
                textComponent.setText(textComponent.getText() + text);
            });
        }
    }
}