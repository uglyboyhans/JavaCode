import java.io.File;


public class CreateFolder {
	public static File createFolder(String FolderPath){
		File newFolder=new File(FolderPath);
		newFolder.mkdirs();
		if(newFolder.exists())
			;//Do Nothing
		else
			System.out.println("Failed to create folder~");
		return newFolder;
	}


}
