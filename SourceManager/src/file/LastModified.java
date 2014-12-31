package file;

import java.io.File;
import java.util.Date;


public class LastModified {
	public static Date lastModified(File f){
		long t=f.lastModified();
		Date d=new Date(t);
		return d;
	}

}
