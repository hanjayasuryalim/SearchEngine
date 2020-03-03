package View;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

public final class UIFrame extends JFrame {
    
    public final String INITIAL = "Search.... ";
    public final Font fontTxt = new Font("Serif", Font.PLAIN, 16);
    public final Font fontTitle = new Font("Serif", Font.BOLD, 32);
    
    private JScrollPane sp;
    
    public UIFrame() {
        changeToInitialState();
    }
    
    protected void changeToInitialState() {
        //Change to initial state
        this.getContentPane().removeAll();
        this.getContentPane().invalidate();
        
        JPanel startPanel = new InitialState(this);
        this.getContentPane().add(startPanel);
        this.getContentPane().validate();
    }
    
    protected void changeToQueryState(String query) {
        //Change to query state
        this.getContentPane().removeAll();
        this.getContentPane().invalidate();
        
        JPanel startPanel = new QueryState(this, query);
        
        //Add to scrollbar
        sp = new JScrollPane(startPanel);
        sp.setOpaque(false);
        
        //Making smooth scrolling experience
        sp.getVerticalScrollBar().setUnitIncrement(3);
//        sp.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        
        this.getContentPane().add(sp);
        this.getContentPane().validate();
    }
    
    protected void scrollToTop() {
        sp.getVerticalScrollBar().setValue(0);
        sp.getHorizontalScrollBar().setValue(0);
    }
}
