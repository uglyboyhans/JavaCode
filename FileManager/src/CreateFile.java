import java.io.File;
import java.io.IOException;


public class CreateFile {
	public static File createFile(String pathname){
		File newfile=new File(pathname);
		try {
			newfile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(newfile.exists())
			;//Do Nothing;
		else
			System.out.println("Failed to create~");
		return newfile;
	}

}
