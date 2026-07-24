
package prg381group4.view;

import java.awt.Color;
import javax.swing.JComponent;
import prg381group4.view.*;

public class MainFrame extends javax.swing.JFrame {

    private MaterialsFrame matFrame;
    private CleanersFrame cleanFrame;
    private IssuanceFrame issuFrame;
    private SuppliersFrame suppFrame;
    private CleanersFrame userFrame;
    private Dashboard dashFrame;
    public MainFrame() {
        initComponents();
        setBackground(new Color(0,0,0,0));
        dashFrame = new Dashboard();
        matFrame = new MaterialsFrame();
        cleanFrame = new CleanersFrame();
        issuFrame = new IssuanceFrame();
        suppFrame = new SuppliersFrame();
        
        menu.initMoving(MainFrame.this);
        menu.addEventMenuSelected(new EventMenuSelected(){
            @Override
            public void selected(int index) {
                switch (index) {
                    case 0:
                        setFrame(dashFrame);
                        break;
                    case 2:
                        setFrame(matFrame);
                        break;
                    case 3:
                        setFrame(suppFrame);
                        break;
                    case 4:
                        setFrame(cleanFrame);
                        break;
                    case 5:
                        setFrame(issuFrame);
                        break;
                    case 7:
                        // Reports are re-created on each click so they always
                        // show the latest data from the database.
                        setFrame(new InventoryReportPanel());
                        break;
                    case 8:
                        setFrame(new LowStockReportPanel());
                        break;
                    case 9:
                        setFrame(new IssuanceHistoryReportPanel());
                        break;
                    case 10:
                        setFrame(new MaterialUsageReportPanel());
                        break;
                    case 11:
                        break;
                    default:
                        break;
                }
            }    
        });
        setFrame(dashFrame);
    }

    public void setFrame(JComponent com){
        mainPanel.removeAll();
        mainPanel.add(com);
        mainPanel.repaint();
        mainPanel.revalidate();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new prg381group4.view.PanelBorder();
        menu = new prg381group4.view.Menu();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

        mainPanel.setOpaque(false);
        mainPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    private prg381group4.view.Menu menu;
    private prg381group4.view.PanelBorder panelBorder1;
    // End of variables declaration//GEN-END:variables
}
