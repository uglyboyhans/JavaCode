import java.io.File;


public class DeleteFolder {
	public static void deleteFolder(String itsPath){
		File folder=new File(itsPath);
		boolean b=folder.delete();
		if(b){
			System.out.println("The folder is deleted");
		}
		else{
			System.out.println("Failed to delete");
		}
	}
}
