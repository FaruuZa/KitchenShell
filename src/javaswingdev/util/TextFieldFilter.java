package javaswingdev.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class TextFieldFilter extends DocumentFilter {
    private String pattern;

    public TextFieldFilter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string != null && string.matches(pattern)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null && text.matches(pattern)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}

