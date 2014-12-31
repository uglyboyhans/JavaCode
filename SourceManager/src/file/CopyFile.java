package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile {
	public static void copyFile(String inFile, String outFile)
			throws IOException {
		File in = new File(inFile);
		File out = new File(outFile+"\\"+in.getName());
		FileInputStream fin = new FileInputStream(in);
		FileOutputStream fout = new FileOutputStream(out);

		int length = 2097152;// 2mÄÚ´æ
		byte[] buffer = new byte[length];

		while (true) {
			int ins = fin.read(buffer);
			if (ins == -1) {
				fin.close();
				fout.flush();
				fout.close();
				break;
			} else
				fout.write(buffer, 0, ins);

		}
	}
}
