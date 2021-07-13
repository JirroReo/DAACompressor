
package CHuffmanCompressor;

import java.io.*;
import FileBitIO.CFileBitWriter;


public class CHuffmanEncoder implements huffmanSignature{
	
	
	private String fileName,outputFilename; //Filename for input file, outputFilename for well.. output
	private HuffmanNode root = null; //starting node for the final tree
	private long[] freq  = new long[MAXCHARS]; //inital frequency table
	private String[] hCodes = new String[MAXCHARS]; //inital symbol array, matches the freq table
	private int distinctChars = 0;
	private long fileLen=0,outputFilelen; //used for comparison at the end of compression
	private FileInputStream fin;
	private BufferedInputStream in;	//used for  writing to the output file
	private String gSummary; //displayed at the end of compression

	//function that resets fequency table and count of distinct chars
	void resetFrequency(){
		for(int i=0;i<MAXCHARS;i++)
		freq[i] = 0;
		
		distinctChars = 0;
		fileLen=0;
		gSummary ="";
		}
	
		//These functions use loadFile to read the file from the GUI, loadFile is explained below
	public CHuffmanEncoder(){
		loadFile("","");
		}
	public CHuffmanEncoder(String txt){
		loadFile(txt);
		}
	public CHuffmanEncoder(String txt,String txt2){
		loadFile(txt,txt2);
		}


		
	public void loadFile(String txt){ //loads file if only 1 arg is passed
		fileName = txt; //gets original filename
		outputFilename = txt + strExtension; //strExtension is .huf, returns original filename + .huf
		resetFrequency();
		}

	public void loadFile(String txt,String txt2){ //loads file if 2 args are passed
		fileName = txt;
		outputFilename = txt2; //sets the output file name to the 2nd arg instead of .huf
		resetFrequency();
		}

	public boolean encodeFile() throws Exception{ //main encoding function
		 
		if(fileName.length() == 0) return false; //if file has no content, return
		try{
		fin = new FileInputStream(fileName);
		in = new BufferedInputStream(fin); //Readers to read the file contents
		}catch(Exception e){ throw e; }
		
		//Frequency Analysis
  		try
		{
			fileLen = in.available();								// This block
			if(fileLen == 0) throw new Exception("File is Empty!"); // is pretty
			gSummary += ("Original File Size : "+ fileLen + "\n");  // self-explanatory
			
			long i=0;

			in.mark((int)fileLen);
			distinctChars = 0; //start counting distinct chars
			
			while (i < fileLen) //count distinct chars
			{		
				int ch = in.read();			
				i++;
				if(freq[ch] == 0) distinctChars++;
				freq[ch]++;
				
			}
			in.reset();			
		}
		catch(IOException e)
		{
			throw e;
			//return false;
		}
		gSummary += ("Distinct Chars " + distinctChars + "\n");
		/*
		System.out.println("distinct Chars " + distinctChars);
		 //debug
		for(int i=0;i<MAXCHARS;i++){
			if(freq[i] > 0)
			System.out.println(i + ")" + (char)i + " : " + freq[i]);
		}
		*/
		
		//Custom class, used as substitute for minheap, found in CPriorityQueue.java
		CPriorityQueue  cpq = new CPriorityQueue (distinctChars+1); 
		
		//Put all characters and their frequencies in a single object called HuffmanNode
		//We're then gonna use them for building the huffman tree
		int count = 0;
		for(int i=0;i<MAXCHARS;i++){
			if(freq[i] > 0){
				count ++;
				//System.out.println("ch = " + (char)i + " : freq = " + freq[i]);
				HuffmanNode np = new HuffmanNode(freq[i],(char)i,null,null); 
				//^make a new huffmannode, its frequency is freq[i], its symbol is whats in i, and it has no children on L and R
				cpq.Enqueue(np);
				}
		}
		
		//cpq.displayQ();
		
		HuffmanNode low1,low2; //For extracting the two lowest nodes
		
		while(cpq.totalNodes() > 1){ //until theres only 1 node left in cpq
			low1 = cpq.Dequeue(); //extract lowest node from cpq, put it in low1
			low2 = cpq.Dequeue(); //extract lowest node from cpq, put it in low2
			if(low1 == null || low2 == null) { throw new Exception("PQueue Error!"); } //if for some reason there's no low1 or low2, throw error
			HuffmanNode intermediate = new HuffmanNode((low1.freq+low2.freq),(char)0,low1,low2); //make a new node, set its value to low1+low2, put low1 and low2 as children
			if(intermediate == null) { throw new Exception("Not Enough Memory!"); } //if we cant create the parent node, we mustve ran out of mem
			cpq.Enqueue(intermediate);

		}
		
		//cpq.displayQ();
		//root = new HuffmanNode();

		//at this point theres only one node on cpq left
		root = cpq.Dequeue(); //so we remove it
		buildHuffmanCodes(root,""); //and use it as the root for our tree
		
		for(int i=0;i<MAXCHARS;i++) hCodes[i] = ""; //reset the hCodes array just to be sure
		getHuffmanCodes(root); //we convert that huffman tree to 1s and 0s
		
		/*
		//debug		
		for(int i=0;i<MAXCHARS;i++){
		if(hCodes[i] != ""){ 
		System.out.println(i + " : " + hCodes[i]);
		}
		}
		*/
		
		CFileBitWriter hFile = new CFileBitWriter(outputFilename); //Writer to file
		
		//We write the 1s and 0s to the file, along with the huffman tree
		hFile.putString(hSignature);
		String buf;
		buf = leftPadder(Long.toString(fileLen,2),32); //fileLen
		hFile.putBits(buf);
		buf = leftPadder(Integer.toString(distinctChars-1,2),8); //No of Encoded Chars
		hFile.putBits(buf); //writing 1s and 0s
		
		for(int i=0;i<MAXCHARS;i++){
			if(hCodes[i].length() != 0){
				buf = leftPadder(Integer.toString(i,2),8);
				hFile.putBits(buf);
				buf = leftPadder(Integer.toString(hCodes[i].length(),2),5);
				hFile.putBits(buf);
				hFile.putBits(hCodes[i]);
				}
			} 
		
		long lcount = 0;
		while(lcount < fileLen){
			int ch = in.read();
			hFile.putBits(hCodes[(int)ch]);
			lcount++;
		} //writing the tree to the file

		hFile.closeFile();
		outputFilelen =  new File(outputFilename).length();
		float cratio = (float)(((outputFilelen)*100)/(float)fileLen);
		gSummary += ("Compressed File Size : " + outputFilelen + "\n");
		gSummary += ("Compression Ratio : " + cratio + "%" + "\n");
		return true;
		
		}
	
	void buildHuffmanCodes(HuffmanNode parentNode,String parentCode){ //building the huffman code for the symbols

		parentNode.huffCode = parentCode;
		if(parentNode.left != null)
			buildHuffmanCodes(parentNode.left,parentCode + "0"); //if we go left, 0
		
		if(parentNode.right != null)
			buildHuffmanCodes(parentNode.right,parentCode + "1"); //if we go right, 1
		}
	
	void getHuffmanCodes(HuffmanNode parentNode){ //getting the huffman codes of a symbol
		
		if(parentNode == null) return;
		
		int asciiCode = (int)parentNode.ch;
		if(parentNode.left == null || parentNode.right == null)
		hCodes[asciiCode] = parentNode.huffCode;
		
		if(parentNode.left != null ) getHuffmanCodes(parentNode.left);
		if(parentNode.right != null ) getHuffmanCodes(parentNode.right);
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


