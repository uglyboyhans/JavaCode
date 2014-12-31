package file;

import java.io.File;
import java.io.IOException;


public class FolderList {
	public static void folderList(File folderPath) throws IOException{
		if(folderPath.isDirectory()){
			File files[]=folderPath.listFiles();
			for(int i=0;i<files.length;i++){
			    File currFile=files[i];
			    //if(currFile.isFile()){
			     System.out.println(currFile.getName());
			     System.out.println(Attribute.attribute(currFile)+"    "+SizeOf.sizeOf(currFile)+"    "+LastModified.lastModified(currFile));
			    //}
		    }
	    }
		else{
			System.out.println("Not a directory!");
		}
	}
}
