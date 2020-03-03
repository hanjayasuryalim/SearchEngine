package View;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InitialState extends JPanel {
    
    private Button btnQuery;
    private JTextField txtQuery;
    private final UIFrame parent;
    
    public InitialState(UIFrame parent) {
        this.parent = parent;
        
        initComponents();
    }
    
    private void initComponents() {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        
        //Middle JPanel that contains JTextField and Button
        JPanel middle = new JPanel();
        middle.setOpaque(false);
        middle.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        
        //JTextField containing query
        txtQuery = new JTextField(30);
        txtQuery.setText("");
        txtQuery.setFont(parent.fontTxt);
        
        //Setting placeholder for JTextField
        txtQuery.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtQuery.getText().equals(parent.INITIAL)) {
                    txtQuery.setText("");
                    txtQuery.setForeground(Color.BLACK);
                    txtQuery.setBackground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtQuery.getText().isEmpty()) {
                    txtQuery.setForeground(Color.BLACK);
                    txtQuery.setBackground(new Color(245, 245, 245));
                    txtQuery.setText(parent.INITIAL);
                }
            }
        });
        
        //Button to change to JPanel QueryState
        btnQuery = new Button("Search");
        btnQuery.setFont(parent.fontTxt);
        
        //Setting event when button clicked
        btnQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //When button clicked, check if it is filled or not
                if (!txtQuery.getText().equals(parent.INITIAL) && !txtQuery.getText().isEmpty()) {
                    //If the text field is filled, change the panel to query panel
                    System.out.println("Changing to panel query");
                    parent.changeToQueryState(txtQuery.getText());
                }
            }
        });
        
        middle.add(txtQuery);
        middle.add(btnQuery);
        
        this.add(middle, BorderLayout.CENTER);
    }
}
