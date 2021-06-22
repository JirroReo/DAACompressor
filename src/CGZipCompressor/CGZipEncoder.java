
package CGZipCompressor;

/** Creates a new instance of CGZipEncoder */
import java.io.*;
import java.util.zip.*;


public class CGZipEncoder{
	
	private String fileName,outputFilename;
	private long fileLen,outputFilelen;
	private String gSummary;

		
	public CGZipEncoder(){
		loadFile("","");
		}
	public CGZipEncoder(String txt){
		loadFile(txt);
		}
	public CGZipEncoder(String txt,String txt2){
		loadFile(txt,txt2);
		}
		
	public void loadFile(String txt){
		fileName = txt;
		outputFilename = txt + ".gz";
		gSummary = "";
		}
	public void loadFile(String txt,String txt2){
		fileName = txt;
		outputFilename = txt2;
		gSummary = "";
		}
		
	public boolean encodeFile() throws Exception{
		
		
		if(fileName.length() == 0) return false;
		try{
		FileInputStream in = new FileInputStream(fileName);
		GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(outputFilename));
		fileLen = in.available();
		if(fileLen == 0 ) throw new Exception("Source File Empty!");
		gSummary += "Original Size : " + fileLen + "\n";

		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
		out.write(buf, 0, len);
		}
		in.close();
		out.finish();
		out.close();
		outputFilelen =  new File(outputFilename).length();
		float cratio = (float)(((outputFilelen)*100)/(float)fileLen);
		gSummary += ("Compressed File Size : " + outputFilelen + "\n");
		gSummary += ("Compression Ratio : " + cratio + "%" + "\n");

		}catch(Exception e){throw e; }
		return true;
		}
		
			
	public String getSummary(){
		return gSummary;
		}

	}
    

