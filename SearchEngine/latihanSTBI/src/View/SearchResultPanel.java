/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import LanguageModel.LM;
import LanguageModel.RankedItem;
import Search.*;
import java.awt.Container;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import vektorruang.SearchTFIDF;

/**
 *
 * @author specter2k11
 */
public class SearchResultPanel extends javax.swing.JPanel {

    /**
     * Creates new form SearchResultPanel
     */
    public SearchResultPanel(String searchQuery, int mode, int model, int resultAmount) {
        initComponents();
        searchTextField.setText(searchQuery);
        outputNumberSpinner.setValue(resultAmount);
        switch(mode){
            case 0 : {
                modeButtonGroup.setSelected(orModeButton.getModel(), true);
                break;
            }
            case 1 : {
                modeButtonGroup.setSelected(andModeButton.getModel(), true);
                break;
            }
            case 2 : {
                modeButtonGroup.setSelected(advModeButton.getModel(), true);
                break;
            }
            default : {
                
                break;
            }
        }
        switch(model){
            case 0 : {
                modelButtonGroup.setSelected(probabilisticModelButton.getModel(), true);
                ArrayList<RankedItem> searchResult = SearchTFIDF.getTFIDFRanking(searchQuery);
                ArrayList<RankedItem> resultList = new ArrayList<RankedItem>();
                if(searchResult.size() > resultAmount) {
                    for(int i = 0; i < resultAmount; i++){
                        resultList.add(searchResult.get(i));
                    }
                } else {
                    resultList = searchResult;
                }
                int resultCount = resultList.size();
                Container resultContainer = new Container();
                resultContainer.setLayout(new GridLayout(0,1));
                for(RankedItem document : resultList) {
                    RankedItem tempItem = new RankedItem();
                    tempItem.setDocId(document.docId);
                    tempItem.setScore(0);
                    ResultLinePanel searchResultPanel = new ResultLinePanel(tempItem,searchQuery,mode,model,resultAmount);
                    resultContainer.add(searchResultPanel);
                }
                long timeElapsed = SearchTFIDF.getTimeElapsed();
                NumberFormat f = new DecimalFormat("#0.00");
                responseLabel.setText("Showing " + resultCount + " results (" + f.format(timeElapsed/(double)1000000000) + " seconds)");
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() { 
                        searchResultPane.getVerticalScrollBar().setValue(0);
                        searchResultPane.getVerticalScrollBar().setUnitIncrement(20);
                    }
                 });
                searchResultPane.getViewport().setView(resultContainer);
                break;
            }
            case 1 : {
                modelButtonGroup.setSelected(languageModelButton.getModel(),true);
                Search s = new Search();
                LM searchModel = new LM(searchQuery);
                ArrayList<String> searchResult = s.search(searchQuery,mode);
                ArrayList<RankedItem> resultList = new ArrayList<RankedItem>();
                ArrayList<RankedItem> languageResult = searchModel.calculateProbability();
                
                if(languageResult.size() > resultAmount){
                    for(int i = 0; i < resultAmount; i++){
                        resultList.add(languageResult.get(i));
                    }
                } else {
                    for(int i = 0; i < languageResult.size(); i++){
                        resultList.add(languageResult.get(i));
                    }
                }
                
                int resultCount = resultList.size();
                Container resultContainer = new Container();
                resultContainer.setLayout(new GridLayout(0,1));
                for(RankedItem targetDoc : resultList) {
                    ResultLinePanel searchResultPanel = new ResultLinePanel(targetDoc,searchQuery,mode,model,resultAmount);
                    resultContainer.add(searchResultPanel);
                }
                long timeElapsed = searchModel.getTimeElapsed();
                NumberFormat f = new DecimalFormat("#0.00");
                responseLabel.setText("Showing " + resultCount + " results (" + f.format(timeElapsed/(double)1000000000) + " seconds)");
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() { 
                        searchResultPane.getVerticalScrollBar().setValue(0);
                        searchResultPane.getVerticalScrollBar().setUnitIncrement(20);
                    }
                 });
                searchResultPane.getViewport().setView(resultContainer);
                break;
            }
            default : {
                
                break;
            }
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modeButtonGroup = new javax.swing.ButtonGroup();
        modelButtonGroup = new javax.swing.ButtonGroup();
        searchBarPanel = new javax.swing.JPanel();
        searchButton = new javax.swing.JButton();
        searchTextField = new javax.swing.JTextField();
        searchBarSeparator = new javax.swing.JSeparator();
        searchResultPane = new javax.swing.JScrollPane();
        responseLabel = new javax.swing.JLabel();
        modeSelectPanel = new javax.swing.JPanel();
        orModeButton = new javax.swing.JRadioButton();
        searchModeLabel = new javax.swing.JLabel();
        andModeButton = new javax.swing.JRadioButton();
        advModeButton = new javax.swing.JRadioButton();
        outputNumberSpinner = new javax.swing.JSpinner();
        resultAmountLabel = new javax.swing.JLabel();
        searchModelLabel = new javax.swing.JLabel();
        probabilisticModelButton = new javax.swing.JRadioButton();
        languageModelButton = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchBarPanelLayout = new javax.swing.GroupLayout(searchBarPanel);
        searchBarPanel.setLayout(searchBarPanelLayout);
        searchBarPanelLayout.setHorizontalGroup(
            searchBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchBarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchTextField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchButton)
                .addContainerGap())
        );
        searchBarPanelLayout.setVerticalGroup(
            searchBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchBarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchButton)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        searchResultPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        searchResultPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        responseLabel.setText("Result and Response Time");

        modeButtonGroup.add(orModeButton);
        orModeButton.setText("OR");

        searchModeLabel.setText("Search Mode");

        modeButtonGroup.add(andModeButton);
        andModeButton.setText("AND");

        modeButtonGroup.add(advModeButton);
        advModeButton.setText("Advanced Query");

        outputNumberSpinner.setModel(new javax.swing.SpinnerNumberModel(10, 10, null, 5));

        resultAmountLabel.setText("Result Amount");

        searchModelLabel.setText("Model");

        modelButtonGroup.add(probabilisticModelButton);
        probabilisticModelButton.setText("TF-IDF");

        modelButtonGroup.add(languageModelButton);
        languageModelButton.setText("Language Model");

        orModeButton.setActionCommand("0");
        andModeButton.setActionCommand("1");
        advModeButton.setActionCommand("2");
        probabilisticModelButton.setActionCommand("0");
        languageModelButton.setActionCommand("1");

        javax.swing.GroupLayout modeSelectPanelLayout = new javax.swing.GroupLayout(modeSelectPanel);
        modeSelectPanel.setLayout(modeSelectPanelLayout);
        modeSelectPanelLayout.setHorizontalGroup(
            modeSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modeSelectPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modeSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchModeLabel)
                    .addComponent(resultAmountLabel)
                    .addComponent(searchModelLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(modeSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modeSelectPanelLayout.createSequentialGroup()
                        .addComponent(probabilisticModelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(languageModelButton))
                    .addComponent(outputNumberSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(modeSelectPanelLayout.createSequentialGroup()
                        .addComponent(orModeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(andModeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(advModeButton)))
                .addContainerGap(239, Short.MAX_VALUE))
        );
        modeSelectPanelLayout.setVerticalGroup(
            modeSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modeSelectPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modeSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchModelLabel)
                    .addComponent(probabilisticModelButton)
                    .addComponent(languageModelButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(modeSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(orModeButton)
                    .addComponent(searchModeLabel)
                    .addComponent(andModeButton)
                    .addComponent(advModeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(modeSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputNumberSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resultAmountLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(modeSelectPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchResultPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(responseLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1)
                    .addComponent(searchBarSeparator))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchBarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modeSelectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchBarSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(responseLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchResultPane, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        SearchResultPanel resultPanel = new SearchResultPanel(searchTextField.getText(),Integer.parseInt(modeButtonGroup.getSelection().getActionCommand()),Integer.parseInt(modelButtonGroup.getSelection().getActionCommand()), Integer.parseInt(outputNumberSpinner.getValue().toString()));
        parentFrame.getContentPane().remove(this);
        parentFrame.setContentPane(resultPanel);
        parentFrame.repaint();
        parentFrame.validate();
    }//GEN-LAST:event_searchButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton advModeButton;
    private javax.swing.JRadioButton andModeButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton languageModelButton;
    private javax.swing.ButtonGroup modeButtonGroup;
    private javax.swing.JPanel modeSelectPanel;
    private javax.swing.ButtonGroup modelButtonGroup;
    private javax.swing.JRadioButton orModeButton;
    private javax.swing.JSpinner outputNumberSpinner;
    private javax.swing.JRadioButton probabilisticModelButton;
    private javax.swing.JLabel responseLabel;
    private javax.swing.JLabel resultAmountLabel;
    private javax.swing.JPanel searchBarPanel;
    private javax.swing.JSeparator searchBarSeparator;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel searchModeLabel;
    private javax.swing.JLabel searchModelLabel;
    private javax.swing.JScrollPane searchResultPane;
    private javax.swing.JTextField searchTextField;
    // End of variables declaration//GEN-END:variables
}
