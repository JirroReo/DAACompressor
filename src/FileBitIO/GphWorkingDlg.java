

package FileBitIO;
import java.io.*;
import CLZWCompressor.*;
import javax.swing.*;
import wingph.*;

//GUI Stuff, doesnt really concern the algorithm
public class GphWorkingDlg extends javax.swing.JDialog implements GphGuiConstants{
    
    
    private String gSummary = "";
	private String iFilename,oFilename;
	private boolean bCompress = false;
    
    /** Creates new form GphWorkingDlg */
    public GphWorkingDlg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    GphWorkingDlg(JFrame parent){
		super(parent,true);
		jFrame1 = parent;
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		
	//jProgressBar1.setstr
		
		
	}
    
    public String getSummary(){
		if(gSummary.length() > 0){
		String line = "----------------------------------------------";
		return line + "\n" + gSummary + line;
		}else return "";
		
		}
    
    void doWork(String inputFilename,String outputFilename,int mode,int algorithm){
		File infile = new File(inputFilename);
		System.out.println(inputFilename);
		//chk if file exists
		if(!infile.exists()){
			gSummary += "File Does not Exits!\n";
			return;
			}
		bCompress = (mode == COMPRESS);
                
		//if(bCompress )
		//	jLabel1.setText("Compressing " );
                        
		/*else
			jLabel1.setText("Decompressing " + infile.getName());
		
		setTitle(jLabel1.getText());*/
	
		final int falgo = algorithm;
		iFilename = inputFilename;
		oFilename = outputFilename;
		gSummary = "";

		//Create Thread for Compress/Decompress
		final Runnable closeRunner = new Runnable(){
			public void run(){
				setVisible(false);
				dispose();
			}

			};

		Runnable workingThread = new Runnable(){
			public void run(){
				try{
					boolean success = false;
					switch(falgo){
						/*case COMP_COSMO : 
							if(bCompress){
								CCosmoEncoder ce = new	CCosmoEncoder(iFilename,oFilename);
								success = ce.encodeFile();
								gSummary += ce.getSummary();
								
							}else{
								CCosmoDecoder cde = new	CCosmoDecoder(iFilename,oFilename);
								success = cde.decodeFile();
								gSummary += cde.getSummary();
							}
							break;
						case COMP_HUFFMAN : 
							if(bCompress){
								CHuffmanEncoder he = new	CHuffmanEncoder(iFilename,oFilename);
								success = he.encodeFile();
								gSummary += he.getSummary();
								
							}else{
								CHuffmanDecoder hde = new	CHuffmanDecoder(iFilename,oFilename);
								success = hde.decodeFile();
								gSummary += hde.getSummary();
							}
							break;
						case COMP_SHANNONFANO : 
							if(bCompress){
								CShannonFanoEncoder sfe = new	CShannonFanoEncoder(iFilename,oFilename);
								success = sfe.encodeFile();
								gSummary += sfe.getSummary();
								
							}else{
								CShannonFanoDecoder sfde = new	CShannonFanoDecoder(iFilename,oFilename);
								success = sfde.decodeFile();
								gSummary += sfde.getSummary();
							}
							break;
						case COMP_GZIP : 
							if(bCompress){
								CGZipEncoder gze = new	CGZipEncoder(iFilename,oFilename);
								success  = gze.encodeFile();
								gSummary += gze.getSummary();
								
							}else{
								CGZipDecoder gzde = new	CGZipDecoder(iFilename,oFilename);
								success = gzde.decodeFile();
								gSummary += gzde.getSummary();
							}
							break;
						case COMP_JBC : 
							if(bCompress){
								CJBEncoder jbe = new	CJBEncoder(iFilename,oFilename);
								success = jbe.encodeFile();
								gSummary += jbe.getSummary();
								
							}else{
								CJBDecoder jbde = new	CJBDecoder(iFilename,oFilename);
								success = jbde.decodeFile();
								gSummary += jbde.getSummary();
							}
							break;
						case COMP_RLE:
							if(bCompress){
								CRLEEncoder rle = new	CRLEEncoder(iFilename,oFilename);
								success = rle.encodeFile();
								gSummary += rle.getSummary();
								
							}else{
								CRLEDecoder unrle = new	CRLEDecoder(iFilename,oFilename);
								success = unrle.decodeFile();
								gSummary += unrle.getSummary();
							}
							break;*/

						case COMP_LZW:
							if(bCompress){
								CLZWEncoder lzwe = new	CLZWEncoder(iFilename,oFilename);
								success = lzwe.encodeFile();
								gSummary += lzwe.getSummary();
								
							}else{
								CLZWDecoder lzwd = new	CLZWDecoder(iFilename,oFilename);
								success = lzwd.decodeFile();
								gSummary += lzwd.getSummary();
							}
							break;

					}
					
					}catch(Exception e){
					gSummary += e.getMessage();
					}
					
					try{
						SwingUtilities.invokeAndWait(closeRunner );
					}catch(Exception e){
						gSummary += "\n" + e.getMessage();
						}
				
				}
			};//working thread
			
			
		Thread work = new Thread(workingThread);
		work.start();
		
		setVisible(true);
		
		
		}
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jFrame1 = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jButton1 = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jLabel1.setText("Girish");

        jProgressBar1.setValue(10);
        jProgressBar1.setIndeterminate(true);

        jButton1.setText("Cancel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jButton1)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GphWorkingDlg(new javax.swing.JFrame(), true).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
    
}
