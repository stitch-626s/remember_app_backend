package com.remember_app.remember.common;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CharResponseWrapper extends HttpServletResponseWrapper {

    private final CharArrayWriter writer = new CharArrayWriter();

    public CharResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(writer);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                writer.write(b);
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener listener) {
            }
        };
    }

    @Override
    public void flushBuffer() throws IOException {
        writer.flush();
    }

    @Override
    public void reset() {
        super.reset();
        writer.reset();
    }

    @Override
    public void resetBuffer() {
        super.resetBuffer();
        writer.reset();
    }

    public String toString() {
        return writer.toString();
    }

    public byte[] toByteArray() {
        return writer.toString().getBytes(StandardCharsets.UTF_8);
    }
}