
package CHuffmanCompressor;

import java.io.*;
import FileBitIO.*;


public class CHuffmanDecoder implements huffmanSignature{

	
	private String fileName,outputFilename; //Filename for input file, outputFilename for output
	private String[] hCodes = new String[MAXCHARS]; //where we'll put the huffman codes we'll get from the file
	private int distinctChars = 0; //distinct characters counter
	private long fileLen=0,outputFilelen; //length of both input and output files
	
	private FileOutputStream outf; //writer for output file
	private String gSummary; //summary string, used for after processing

	//These functions use loadFile to read the file from the GUI, loadFile is explained below
	public CHuffmanDecoder(){
		loadFile("","");
		}
	public CHuffmanDecoder(String txt){
		loadFile(txt);
		}
	public CHuffmanDecoder(String txt,String txt2){
		loadFile(txt,txt2);
		}
		
	public void loadFile(String txt){ //loads file if only 1 arg is passed
		fileName = txt; //gets original filename
		outputFilename = stripExtension(txt,strExtension);  //strExtension is .huf, returns original filename - .huf
		gSummary = "";
		}
	public void loadFile(String txt,String txt2){ //loads file if 2 args are passed
		fileName = txt;
		outputFilename = txt2; //sets the output file name to the 2nd arg instead of .huf
		gSummary = "";
		}
		//basically removes the extension .huf from the passed file
	String stripExtension(String ff,String ext){
		ff = ff.toLowerCase();
		if(ff.endsWith(ext.toLowerCase())){
			return ff.substring(0,ff.length()-ext.length());
			}
		return "_" + ff;
		}
		
	public boolean decodeFile() throws Exception{ //Main decoding function
		
		if(fileName.length() == 0) return false; //if file is empty, return
		
		for(int i=0;i<MAXCHARS;i++) hCodes[i] = ""; //set hCodes to empty just to be sure
		long i;
		CFileBitReader fin = new CFileBitReader(fileName); //Readers to read the file contents
		fileLen = fin.available();

		String buf;
		buf = fin.getBytes(hSignature.length()); //gets the length of the passed file in bytes, so we'll know when to stop
		
		if(!buf.equals(hSignature)) return false;
		outputFilelen = Long.parseLong(fin.getBits(32),2);
		distinctChars = Integer.parseInt(fin.getBits(8),2) + 1;
		gSummary  += ("Compressed File Size : "+ fileLen + "\n");
		gSummary  += ("Original   File Size : "+ outputFilelen + "\n");
		gSummary  += ("Distinct   Chars     : "+ distinctChars + "\n");
		for(i=0;i<distinctChars;i++){
			
			int ch = Integer.parseInt(fin.getBits(8),2);
			int len = Integer.parseInt(leftPadder(fin.getBits(5),8),2);
			hCodes[ch] = fin.getBits(len);
			//System.out.println((char)ch + " : "  + hCodes[ch]);
			}
		
		try{
		
		outf = new FileOutputStream(outputFilename);
		i = 0;
		int k;
		int ch;
		
		while(i < outputFilelen){	//main decoder, uses a findCodeWord function for each 1s and 0s sequence in the file
				buf = "";
				for(k=0;k<32;k++){ //32 because strings are 32 bits because we pad them with 0s
					buf += fin.getBit();
					ch  = findCodeword(buf);
						if(ch > -1){
							outf.write((char)ch);
							buf = "";
							i++;
							break;
						}
					}
				if(k >=32 ) throw new Exception("Corrupted File!");  //if the character has more than 32 bits then something's wrong
				
			}
		
		outf.close();
		return true;
		
		}catch(Exception e){
			throw e;
		}
 

		}
		
	int findCodeword(String cw){ //main decoder of the character from the 1s and 0s
		int ret = -1;
		for(int i=0;i<MAXCHARS;i++){
			if(hCodes[i] != "" && cw.equals(hCodes[i])){
				ret = i;
				break;
			}
			}
			return ret;
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



