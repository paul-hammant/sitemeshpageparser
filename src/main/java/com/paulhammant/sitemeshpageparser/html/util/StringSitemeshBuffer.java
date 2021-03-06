package com.paulhammant.sitemeshpageparser.html.util;

import com.paulhammant.sitemeshpageparser.SitemeshBuffer;
import com.paulhammant.sitemeshpageparser.SitemeshBufferFragment;

import java.io.IOException;
import java.io.Writer;

/**
 * SitemeshBuffer that is a string
 */
public class StringSitemeshBuffer implements SitemeshBuffer {
    private final String buffer;

    public StringSitemeshBuffer(String buffer) {
        this.buffer = buffer;
    }

    public char[] getCharArray() {
        return buffer.toCharArray();
    }

    public int getBufferLength() {
        return buffer.length();
    }

    public int getTotalLength() {
        return buffer.length();
    }

    public int getTotalLength(int start, int length) {
        return length;
    }

    public void writeTo(Writer writer, int start, int length) throws IOException {
        writer.write(buffer, start, length);
    }

    public boolean hasFragments() {
        return false;
    }

    public static SitemeshBufferFragment createBufferFragment(String buffer)
    {
        return new SitemeshBufferFragment(new StringSitemeshBuffer(buffer), 0, buffer.length());
    }

}
