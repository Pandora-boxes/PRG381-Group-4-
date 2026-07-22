
package prg381group4.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashSet;

public class MenuItem extends javax.swing.JPanel {

   
    private boolean selected;
    private boolean over;
    public MenuItem(Menu_model data) {
        initComponents();
        setOpaque(false);
        if (data.getType() == Menu_model.MenuType.MENU) {
            lblIcon.setIcon(data.toIcon());
            lblName.setText(data.getName());
        }else if (data.getType() == Menu_model.MenuType.TITLE){
            lblIcon.setText(data.getName());
            lblIcon.setFont(new Font("sanserif",1,12));
            lblName.setVisible(false);
        }else {
            lblName.setText("  ");
        }
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }
    public void setOver(boolean over){
        this.over = over;
        repaint();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();

        lblName.setText("Menu Text");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblIcon)
                .addGap(18, 18, 18)
                .addComponent(lblName))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics graphcs) {
        if(selected || over){
            Graphics2D g2 = (Graphics2D) graphcs;
            if (selected){
                g2.setColor(new Color(255,255,255,80));
            }else{
                g2.setColor(new Color(255,255,255,30));
            }
            g2.fillRoundRect(10, 0, getWidth()-20, getHeight(), 5, 5);
        }
        super.paintComponent(graphcs); 
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    // End of variables declaration//GEN-END:variables
}
