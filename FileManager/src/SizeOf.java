import java.io.File;

public class SizeOf {
	public static long sizeOf(File file) {
		long size = 0;
		if(file.isFile()){
			size=file.length();
		}
		else//if (file.isDirectory()) 
		{
			File flist[] = file.listFiles();
			for (int i = 0; i < flist.length; i++) {
				if (flist[i].isDirectory()) {
					size +=sizeOf(flist[i]);//recursion
				}else{
					size += flist[i].length();
				}
			}
		}
		return size;
	}
}
