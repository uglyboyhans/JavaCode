import java.io.File;
import java.io.IOException;


public class CopyFolder {
	public static void copyFolder(String OldPath,String goalPath) throws IOException{
		File oldp=new File(OldPath);
		File goalp=new File(goalPath);
		if(oldp.exists()&&goalp.exists()){
			CreateFolder.createFolder(goalPath+"\\"+oldp.getName());
			if(oldp.isDirectory()){
				File files[]=oldp.listFiles();
				for(int i=0;i<files.length;i++){
				    File currFile=files[i];
				    if(currFile.isDirectory()){
				    	copyFolder(currFile.getPath(),goalPath+"\\"+oldp.getName()+"\\"+currFile.getName());
				    }
				    else CopyFile.copyFile(currFile.getPath(), goalPath+"\\"+oldp.getName());
			    }
		    }
		}
		else if(oldp.exists()==false){
			System.out.println("Original folder doesn't exist.");
		}else if(goalp.exists()==false){
			CreateFolder.createFolder(goalPath);
			copyFolder(OldPath,goalPath);
		}
	}

}
