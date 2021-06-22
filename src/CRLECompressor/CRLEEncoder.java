
package CRLECompressor;

import java.io.*;

public class CRLEEncoder implements RLEInterface{
	
	private String inputFilename,outputFilename;
	private long fileLen,outputFilelen;
	
	private FileInputStream fin;
	private FileOutputStream fout;
	private BufferedInputStream in;
	private BufferedOutputStream out;
	private String gSummary = "";
	
	
	public CRLEEncoder(){
		inputFilename = "";
		outputFilename = "";
		fileLen = 0;
		outputFilelen = 0;
		gSummary = "";
		}
		
	public CRLEEncoder(String txt){
		inputFilename = txt;
		outputFilename = txt + strExtension;
		fileLen = 0;
		outputFilelen = 0;
		gSummary = "";
		}
		
	public CRLEEncoder(String txt,String txt2){
		inputFilename = txt;
		outputFilename = txt2;
		fileLen = 0;
		outputFilelen = 0;
		gSummary = "";
		}
	
	public boolean encodeFile() throws Exception {
		
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
		
		gSummary += "Original Size : " + fileLen + "\n";
		
		long i = 0;
		int count = 0;
		int currentCh = 0 ,prevCh = 0;
		
		out.write(rleSignature.getBytes());
		
		prevCh = in.read();
		i++;
		count = 1;
		while(i < fileLen){
			
			do{
				currentCh = in.read();
				i++;
				if(prevCh == currentCh ) count++;
				if(count >= 255) break;
			}while(prevCh == currentCh && i < fileLen);
			/*
			if(count == 1 )
			System.out.print((char)prevCh);
			else
			System.out.print("(" + (char)prevCh + "," + count +")");
			*/
			if(count >= toleranceFrequency || prevCh == ESCAPECHAR){
				out.write(ESCAPECHAR);
				out.write((char)prevCh);
				out.write((char)count);
			}else{
				for(int k=0;k<count;k++) out.write(prevCh);
				
			}
			
			if(prevCh == currentCh) count = 0; else count = 1;
			prevCh = currentCh;
				
			
			
		}
		out.close();
		
		outputFilelen = new File(outputFilename).length();
		float cratio = (float)(outputFilelen*100)/(float)fileLen;
		gSummary += "Compressed File Size : " + outputFilelen + "\n";
		gSummary += "Compressed Ratio : " + cratio + "% \n";
		
		}catch(Exception e){
			throw e;
		}
		
		return true;
		
		}//encode file
		
	public String getSummary(){
		return gSummary;
		}
	
	}


