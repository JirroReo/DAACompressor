
package wingph;

public interface GphGuiConstants {
    	String[] algorithmNamesArray = {"Huffman Compression",
										"GZip Compression",
										"RLE Compressor",
										"LZW Compressor"
										};

	String[] extensionArray = { ".gz", ".huf", ".rle",};
	
    final int COMP_LZW = 0;
    final int COMP_GZIP = 1;
	final int COMP_HUFFMAN = 2;
	final int COMP_RLE = 3;
	final int COMPRESS = 32;
	final int DECOMPRESS = 33;
	
	
	
}
