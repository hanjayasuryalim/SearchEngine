package View;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class QueryState extends JPanel {
    
    private JLabel icon;
    private JTextField txtQuery;
    private Button btnQuery;
    private double queryTime;
    private JPanel pnlResult;
    private int maxPage;
    private ArrayList<Integer> result;
    private final String query;
    private final UIFrame parent;
    
    public QueryState(UIFrame parent, String query) {
        this.parent = parent;
        this.query = query;
        
        getAllQueryResults();
        initComponents();
    }
    
    private void initComponents() {
        maxPage = 5;
//        makeDummy();
        
        this.setLayout(new BorderLayout());
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        
        //Top panel for Icon, JTextField, and Button
        JPanel pnlTop = makeTopPanel();
        
        //Middle panel for all query results
        JPanel pnlMiddle = makeMiddlePanel();
        
        //Bottom panel for pagination
        JPanel pnlBottom = makePagination();
        
        //Add all the panel
        this.add(pnlTop, BorderLayout.NORTH);
        this.add(pnlMiddle, BorderLayout.CENTER);
        this.add(pnlBottom, BorderLayout.SOUTH);
    }
    
    private void getAllQueryResults() {
        //Connect to controller to get all query results
        
        
    }
    
    private JPanel makeTopPanel() {
        //To make top panel
        JPanel pnlTop = new JPanel();
        pnlTop.setOpaque(true);
        pnlTop.setBackground(new Color(173, 216, 230));
        pnlTop.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        
        //JLabel for icon
        icon = new JLabel("ICON");
        icon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Change to initial state");
                parent.changeToInitialState();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        
        //JTextField containing query
        txtQuery = new JTextField(30);
        txtQuery.setText(query);
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
        
        pnlTop.add(icon);
        pnlTop.add(txtQuery);
        pnlTop.add(btnQuery);
        
        return pnlTop;
    }
    
    private JPanel makeMiddlePanel() {
        //To make middle panel
        JPanel pnlMiddle = new JPanel();
        pnlMiddle.setOpaque(false);
        pnlMiddle.setLayout(new BorderLayout());
        
        //Create JLabel for time needed to get the query result
        JLabel lblTime = new JLabel();
        lblTime.setText("Time needed to get the result : " + queryTime);
        lblTime.setFont(parent.fontTxt);
        
        //Create panel for query result
        pnlResult = new JPanel();
        refreshResults(0);
        
        //Add all elements
        pnlMiddle.add(lblTime, BorderLayout.NORTH);
        pnlMiddle.add(pnlResult, BorderLayout.CENTER);
        
        return pnlMiddle;
    }
    
    private JPanel makePagination() {
        //To make bottom panel
        JPanel pnlBottom = new JPanel();
        pnlBottom.setOpaque(false);
        pnlBottom.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        
        int max = (int) (Math.ceil((double) result.size() / (double) maxPage));
        for (int i = 0; i < max; i++) {
            Button btnPagination = new Button( Integer.toString(i + 1) );
            btnPagination.setFont(parent.fontTxt);
            
            //Add action listener where button clicked, change all result query
            btnPagination.addActionListener(new PaginationClicked(i));
            
            pnlBottom.add(btnPagination);
        }
        
        return pnlBottom;
    }
    
    //Action Listener for pagination button
    private class PaginationClicked implements ActionListener {
        
        private final int counterPage;
        
        public PaginationClicked (int counterPage) {
            this.counterPage = counterPage;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshResults(counterPage);
            parent.scrollToTop();
        }
    }
    
    private void refreshResults(int counterPage) {
        //Remove all components
        pnlResult.removeAll();
        pnlResult.invalidate();
        
        //To make result panel
        pnlResult.setOpaque(false);
        
        //In one page there are max 10 results
        int min = counterPage * maxPage;
        int max;
        if (result.size() > (counterPage + 1) * maxPage) {
            max = (counterPage + 1) * maxPage;
        } else {
            max = result.size();
        }
        pnlResult.setLayout(new GridLayout(maxPage, 1));
        
        //Looping to make result panel
        for (int i = min; i < max; i++) {
            //Make panel for result
            JPanel content = new JPanel();
            content.setLayout(new GridLayout(4, 1));
            content.setOpaque(false);
            
            //No Doc., Title, Date, Body
            JLabel lblNoDoc = new JLabel(Integer.toString(i));
            lblNoDoc.setFont(parent.fontTxt);
            lblNoDoc.setForeground(new Color(0, 20, 40));
            lblNoDoc.setHorizontalAlignment(JLabel.LEFT);
            
            JLabel lblTitle = new JLabel();
            lblTitle.setFont(parent.fontTitle);
            lblTitle.setHorizontalAlignment(JLabel.LEFT);
            
            JLabel lblDate = new JLabel();
            lblDate.setFont(parent.fontTxt);
            lblDate.setHorizontalAlignment(JLabel.LEFT);
            
            JLabel lblBody = new JLabel();
            lblBody.setFont(parent.fontTxt);
            lblBody.setHorizontalAlignment(JLabel.LEFT);
            
            //Add all components to content panel
            content.add(lblNoDoc);
            content.add(lblTitle);
            content.add(lblDate);
            content.add(lblBody);
            
            pnlResult.add(content);
        }
        
        //Revalidate the panel
        pnlResult.revalidate();
        pnlResult.repaint();
    }
    
    private void makeDummy() {
        result = new ArrayList<>();
        
        result.add(1);
        result.add(2);
        result.add(3);
        result.add(4);
        result.add(5);
        result.add(6);
        result.add(7);
        result.add(8);
        result.add(9);
        result.add(10);
        result.add(11);
        result.add(12);
    }
}
