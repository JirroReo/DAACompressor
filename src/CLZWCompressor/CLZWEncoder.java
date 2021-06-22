
package CLZWCompressor;


import java.io.*;
import java.util.*;

import FileBitIO.CFileBitWriter;

public class CLZWEncoder implements LZWInterface{
    
    	private String inputFilename,outputFilename;
	private long inputFilelen,outputFilelen;
	private String gSummary = "";
	
	
	private FileInputStream fin;
	private BufferedInputStream in;
	
    
    /** Creates a new instance of CLZWEncoder */
    public CLZWEncoder() {
        
        gSummary = "";
		inputFilename = "";
		outputFilename = "";
		inputFilelen = 0;
		outputFilelen = 0;
    }
    
    	public CLZWEncoder(String txt){
		gSummary = "";
		inputFilename = txt;
		outputFilename = txt + strExtension;
		inputFilelen = 0;
		outputFilelen = 0;
		}
		
	public CLZWEncoder(String txt,String txt2){
		gSummary = "";
		inputFilename = txt;
		outputFilename = txt2;
		inputFilelen = 0;
		outputFilelen = 0;
		}
		
	public boolean encodeFile() throws Exception{
		
		if(inputFilename.length() == 0) return false;
		CFileBitWriter out;
		
		try{
			fin = new FileInputStream (inputFilename);
			in = new BufferedInputStream(fin);
			
			out = new CFileBitWriter(outputFilename);
			
		}catch(Exception e){
			throw e;
		}
		
		try{
		
		gSummary = "";
		inputFilelen = in.available();
		if(inputFilelen == 0 ) throw new Exception("\nFile is Empty!");
		gSummary += "Original File Size : " + inputFilelen +"\n";
        
        Hashtable <String,Integer> table = new Hashtable<String,Integer>();
   		
        for(int k=0;k<MAXCHARS;k++){
        	String buf = "" + (char)k;
        	table.put(buf,new Integer(k));
        }
        
		out.putString(lzwSignature);
		out.putBits(leftPadder(Long.toString(inputFilelen,2),32));
		
		long i = 0;
		int codesUsed = MAXCHARS;
		
		int currentCh = 0;
		String prevStr = "";

			while(i < inputFilelen){
				currentCh = in.read();
				i++;
				String temp = prevStr + (char)currentCh;
				Integer e  = table.get(temp);
				
				if(e == null){ //not found
					if(codesUsed < MAXCODES) table.put(temp,codesUsed++);
					
					Integer encoded = table.get(prevStr);
					if(encoded!=null){
					String wri = leftPadder(Integer.toString(encoded.intValue(),2),MAXBITS);
					out.putBits(wri);
					}
					
					prevStr = "" + (char)currentCh;
				}else{
					prevStr = temp;
				}
				}
		
		
		Integer encoded = table.get(prevStr);
		if(encoded != null){
		String wri = leftPadder(Integer.toString(encoded.intValue(),2),MAXBITS);
		out.putBits(wri);
		}
		
		out.closeFile();
		}catch(Exception e){
			throw e;
		}
		
		outputFilelen =  new File(outputFilename).length();
		float cratio = (float)(((outputFilelen)*100)/(float)inputFilelen);
		gSummary += ("Compressed File Size : " + outputFilelen + "\n");
		gSummary += ("Compression Ratio : " + cratio + "%" + "\n");

			return true;		
		}

		
	String leftPadder(String txt,int n){
		while(txt.length() < n )
			txt =  "0" + txt;
		return txt;
		}
	
	String rightPadder(String txt,int n){
		while(txt.length() < n )
			txt += "0";
		return txt;
		}

	public String getSummary(){
		return gSummary;
		}
    
}
