
package CRLECompressor;

import java.io.*;

public class CRLEDecoder implements RLEInterface{
	
	private String inputFilename,outputFilename;
	private long fileLen,outputFilelen;
	
	private FileInputStream fin;
	private FileOutputStream fout;
	private BufferedInputStream in;
	private BufferedOutputStream out;
	private String gSummary = "";
	
	
	public CRLEDecoder(){
		inputFilename = "";
		outputFilename = "";
		fileLen = 0;
		outputFilelen = 0;
		gSummary = "";
		}
		
	public CRLEDecoder(String txt){
		inputFilename = txt;
		outputFilename = txt + strExtension;
		fileLen = 0;
		outputFilelen = 0;
		gSummary = "";
		}
		
	public CRLEDecoder(String txt,String txt2){
		inputFilename = txt;
		outputFilename = txt2;
		fileLen = 0;
		outputFilelen = 0;
		gSummary = "";
		}
	
	public boolean decodeFile() throws Exception {
		
		if(inputFilename.length() == 0) return false;
		
		try{
			fin = new FileInputStream(inputFilename);
			in = new BufferedInputStream(fin);
			
			fout = new FileOutputStream(outputFilename);
			out = new BufferedOutputStream(fout);
			
		}catch(Exception e){
			throw e;
		}
		
		try{
		fileLen = in.available();
		if(fileLen == 0) throw new Exception("\nFile is Empty!");
		
		gSummary += "Compressed File Size : " + fileLen + "\n";
		
		byte[] sig = new byte[rleSignature.length()];
		String buf = "";
		long i = 0;
		int ch,count;
		
		in.read(sig,0,rleSignature.length());
		buf = new String(sig);
		
		if(!rleSignature.equals(buf)) return false;
		
		i = rleSignature.length();
		
		while(i < fileLen){
			ch = in.read(); 
			i++;
			if(ch == ESCAPECHAR && i < fileLen){
				ch = in.read();
				count = in.read();
				i += 2;
				for(int k=0;k<count;k++) out.write((char)ch);
			}else{
				out.write((char)ch);
			}
						
		}
		out.close();
		
		outputFilelen = new File(outputFilename).length();
		gSummary += "Original File Size : " + outputFilelen + "\n";
		
		}catch(Exception e){
			throw e;
		}
		
		return true;
		
		}//encode file
		
	public String getSummary(){
		return gSummary;
		}
	
	}

