
package CGZipCompressor;

import java.io.*;
import java.util.zip.*;

public class CGZipDecoder {
    
    /** Creates a new instance of CGZipDecoder */
  
        
     	private String fileName,outputFilename;
	private long fileLen,outputFilelen;
	private String gSummary;

		
	public CGZipDecoder(){
		loadFile("","");
		}
	public CGZipDecoder(String txt){
		loadFile(txt);
		}
	public CGZipDecoder(String txt,String txt2){
		loadFile(txt,txt2);
		}
		
	public void loadFile(String txt){
		fileName = txt;
		outputFilename = stripExtension(txt,".gz");
		gSummary = "";
		}
		
	String stripExtension(String ff,String ext){
		ff = ff.toLowerCase();
		if(ff.endsWith(ext.toLowerCase())){
			return ff.substring(0,ff.length()-ext.length());
			}
		return ff + ".dat";
		}
		
	public void loadFile(String txt,String txt2){
		fileName = txt;
		outputFilename = txt2;
		gSummary = "";
		}
	
	
	public boolean decodeFile()throws Exception{
		
		try{
			
		fileLen = new File(fileName).length();
		
		GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(fileName));
		// Open the output file
		
		OutputStream out = new FileOutputStream(outputFilename);
 

		// Transfer bytes from the compressed file to the output file
		byte[] buf = new byte[1024];
		int len;
		while ((len = gzipInputStream.read(buf)) > 0) {
		out.write(buf, 0, len);
		}
 

		// Close the file and stream
		gzipInputStream.close();
		out.close();
		outputFilelen  = new File(outputFilename).length();
		gSummary  += ("Compressed File Size : "+ fileLen + "\n");
		gSummary  += ("Original   File Size : "+ outputFilelen + "\n");
		

		}catch(Exception e){throw e;}
		
		return true;
		}
	
	public String getSummary(){
		return gSummary;
		}

	}
   
    
    

