package com.truesculpt.onlinelibrary;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.servlet.ServletOutputStream;

class TeeOutputStream extends ServletOutputStream
{
    private ServletOutputStream output1;
    private PrintStream output2;

    public TeeOutputStream(ServletOutputStream output1, OutputStream output2)
    {
        this.output1 = output1;
        this.output2 = new PrintStream(output2);
    }

    public void write(int b)
    {
        try {
			output1.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
        output2.write(b);
    }

    public void print(char c)
    {
        try {
			output1.print(c);
		} catch (IOException e) {
			e.printStackTrace();
		}
        output2.write(c);
    }    
}