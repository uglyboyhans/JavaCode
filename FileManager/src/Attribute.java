import java.io.File;


public class Attribute {
	public static String attribute(File f){
		String s="";
		if(f.isFile()){
			s=s+"f";
		}
		else{
			s=s+"-";
		}
		if(f.isDirectory()){
			s=s+"d";
		}
		else{
			s=s+"-";
		}
		if(f.canRead()){
			s=s+"r";
		}
		else{
			s=s+"-";
		}
		if(f.canWrite()){
			s=s+"w";
		}
		else{
			s=s+"-";
		}
		return s;
	}
}
