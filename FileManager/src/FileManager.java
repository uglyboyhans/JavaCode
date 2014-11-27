import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {
	public static void fileManager() throws IOException {
		Scanner input1 = new Scanner(System.in);
		String inp;// = input1.nextLine();
		String[] in ;//= inp.split(" ");
		 while(true) {
			inp = input1.nextLine();
			in = inp.split(" ");
			//input1.close();
			if (in[0].equalsIgnoreCase("exit")) {
				System.out.println("exiting~~~");
				 break;
			} else if (in[0].equalsIgnoreCase("cpfo")) {// copy folder
				CopyFolder.copyFolder(in[1], in[2]);
				//fileManager();
			}else if(in[0].equalsIgnoreCase("cpfi")){//copy file
				CopyFile.copyFile(in[1], in[2]);
			}else if(in[0].equalsIgnoreCase("crfi")){//create file
				CreateFile.createFile(in[1]);
			}else if(in[0].equalsIgnoreCase("crfo")){//create folder
				CreateFolder.createFolder(in[1]);
			}else if(in[0].equalsIgnoreCase("attr")){//attribute
				System.out.println("f: isFile");
				System.out.println("d: isDirectory");
				System.out.println("r: canRead");
				System.out.println("w: canWrite");
				System.out.println(Attribute.attribute(new File(in[1])));
			}else if(in[0].equalsIgnoreCase("lm")){//last modified
				System.out.println(LastModified.lastModified(new File(in[1])));
			}else if(in[0].equalsIgnoreCase("fl")){//folder list
				FolderList.folderList(new File(in[1]));
			}else if(in[0].equalsIgnoreCase("delfi")){//delete file
				DeleteFile.delete(new File(in[1]));
			}else if(in[0].equalsIgnoreCase("delfo")){//delete folder
				DeleteFolder.deleteFolder(in[1]);
			}else if(in[0].equalsIgnoreCase("size")){//size of file
				System.out.println(SizeOf.sizeOf(new File(in[1])));
			}
			else {
				System.out.println("false");
				// continue;
			}
		}input1.close();
	}

	// }

	public static void main(String[] args) throws IOException {
		fileManager();
	}
}