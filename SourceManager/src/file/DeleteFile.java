package file;

import java.io.File;
//import java.util.Scanner;


public class DeleteFile {
	public static void delete(File f){
		boolean b=f.delete();
		if(b){
			System.out.println("The file is deleted");
		}
		else{
			System.out.println("Failed to delete");
		}
	}

}
