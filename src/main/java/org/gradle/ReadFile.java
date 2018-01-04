package org.gradle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {

	static String[] results = null;

	public static String[] value() {

		try {
			File file = new File("/home/hduser/Desktop/APPLICATION/sample.csv");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			String finalLine = "";

			int count = 0;
			while ((line = bufferedReader.readLine()) != null) {
				count++;
				if (count == 2) {
					stringBuffer.append(line);
					finalLine = line;
				}
				// stringBuffer.append("\n");
			}

			results = finalLine.split(",");

			System.out.println(results[1]);
			System.out.println(results[2]);
			System.out.println(results[3]);
			System.out.println(results[4]);
			System.out.println(results[5]);
			System.out.println(results[6]);

			fileReader.close();
			// System.out.println("Contents of file:");
			System.out.println(stringBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}

}
