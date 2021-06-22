
package wingph;
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
          
    }
          /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gph().setVisible(true);
    }
            
          });
        // TODO code application logic here
    }
    
}

    
