package com.prediction.RecommenderApp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MovieDataConvert {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new FileReader("data/test1.dat"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("data/test2.csv"));
		String line;
		while ((line = br.readLine()) != null) {
			String[] values=line.split("\\::");
			bw.write(values[0]+","+ values[1]+","+values[2]+"\n");
		}
		br.close();
		bw.close();

	}

}
