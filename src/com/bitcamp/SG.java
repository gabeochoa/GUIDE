package com.bitcamp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class SG
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	public static ArrayList<String> getLogin() throws IOException
	{
		ArrayList<String> logininfo = new ArrayList<String>();
		
		InputStream    fis;
		BufferedReader br;
		String         line;

		fis = new FileInputStream("sendgrid.config");
		br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		while ((line = br.readLine()) != null) {
		    logininfo.add(line);
		}

		// Done with the file
		br.close();
		br = null;
		fis = null;
		
		return logininfo;
	}
	
}
