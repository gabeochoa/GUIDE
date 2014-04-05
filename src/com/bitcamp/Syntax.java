package com.bitcamp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Syntax
{
	static ArrayList<String> cplusplus = new ArrayList<String>();
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> checker = new ArrayList<String>();
		ArrayList<String> checkerline = new ArrayList<String>();
		
		InputStream    fis;
		BufferedReader br;
		String         line;

		fis = new FileInputStream("Graph.cpp");
		br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		while ((line = br.readLine()) != null) {
		    for(String s : line.split("\\s+"))
		    {
		    	s.replaceAll(";", "");
		    	checker.add(s);
		    }
		    checker.add("\n");
		}
		br.close();
	
		ArrayList<Integer> a = Highlight(checker);
		for(int i=0; i<a.size(); i++)
		{
			String temp = checker.get(a.get(i));
			System.out.println(temp);
			if(temp.equals("\n"))
			{
				System.out.println("");
			}else
			{
				System.out.print(temp + " ");
			}
		}
	}

	public static void fillFile() throws IOException
	{
		
		InputStream    fis;
		BufferedReader br;
		String         line;

		fis = new FileInputStream("cpluspluskeywords.txt");
		br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		while ((line = br.readLine()) != null) {
		    cplusplus.add(line);
		}
		br.close();
	}
	
	
	public static ArrayList<Integer> Highlight(ArrayList<String> words)
	{
		ArrayList<Integer> ret = new ArrayList<Integer>();
		int i=0;
		for(String s : words)
		{
			for(String k : cplusplus)
			{
				if(s.equals(k))
				{
					System.out.println(k);
					ret.add(i);
				}
			}
			i++;
		}
		return ret;
	}
	
}
