
package CHuffmanCompressor;

//Custom class to store our symbols in with their frequencies
public class HuffmanNode{
	
	HuffmanNode left,right; //Children
	public long freq;
	public char ch;
	
	//Code Words
	public String huffCode;
	
	
	public HuffmanNode(){//default constructor
		freq = 0;
		ch = 0;
		huffCode = "";
		left = null;
		right = null;
		}
	public HuffmanNode(long xfreq,char xch,HuffmanNode lchild,HuffmanNode rchild){
		freq = xfreq;
		ch = xch;
		left = lchild;
		right = rchild;
		huffCode = "";
		} //constructor with initializers, used more often idek why i made a default constuctor

	
}
