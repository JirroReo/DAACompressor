
package FileBitIO;
import java.io.*;

public class CFileBitReader {
    
    /** Creates a new instance of CFileBitReader */
   	private String fileName;
	
	private File inputFile;
	private FileInputStream fin;
	private BufferedInputStream in;
	private String currentByte;
	
	
	public CFileBitReader() throws Exception{
		try{
			fileName = "";
			//loadFile(fileName);
		}catch(Exception e){
			throw e;
		}
		
	}
	
	public CFileBitReader(String txt) throws Exception{
		try{
			fileName = txt;
			loadFile(fileName);
		}catch(Exception e){
			throw e;
		}
		
	}
	
	public boolean loadFile(String txt) throws Exception{
		fileName = txt;
		
		try
		{
			inputFile = new File(fileName);
			fin = new FileInputStream(inputFile);
			in = new BufferedInputStream(fin);
			currentByte = "";
			return true;

		}catch(Exception e){
			throw e;
		}
		
		//return true;
	}

	 String leftPad8(String txt){
		while(txt.length() < 8 )
			txt =  "0" + txt;
		return txt;
		}
	 String rightPad8(String txt){
		while(txt.length() < 8 )
			txt +=  "0";
		return txt;
		}
		
	public String getBit() throws Exception{
		try{
			if(currentByte.length() == 0 && in.available() >= 1){
				int k = in.read();
				
				currentByte = Integer.toString(k,2);
				currentByte = leftPad8(currentByte);
			}
			if(currentByte.length() > 0){
			String ret = currentByte.substring(0,1);
			currentByte = currentByte.substring(1);
			return ret;
			}
			return "";
		}catch(Exception e){throw e;}
	}
	
	public String getBits(int n) throws Exception{
		try{
			String ret = "";
			for(int i=0;i<n;i++){
			 ret += getBit();
			}
			return ret;
		}catch(Exception e){throw e;}
	}
	
	public String getBytes(int n) throws Exception{
		try{
			String ret = "",temp;
			for(int i=0;i<n;i++){
				temp = getBits(8);
				char k = (char)Integer.parseInt(temp,2);
				ret += k;
			}
			return ret;
		}catch(Exception e){throw e;}
	}
	
	public boolean eof() throws Exception{
		try{
			
			return (in.available()== 0);
		}catch(Exception e){throw e;}
		
		}
		
	public long available() throws Exception{
		try{
			return in.available();
		}catch(Exception e){throw e;}
		
		}


	public void closeFile() throws Exception{
		try{
			in.close();
		}catch(Exception e){throw e;}
		
		}

    
}
