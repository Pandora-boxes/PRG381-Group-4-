
package prg381group4.view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;


public class Menu extends javax.swing.JPanel {

    private EventMenuSelected event;
    
    public void addEventMenuSelected(EventMenuSelected event){
        this.event = event;
        listMenu1.addEventMenuSelected(event);
    }
    public Menu() {
        initComponents();
        setOpaque(false);
        init();
        listMenu1.setOpaque(false);
    }

private void init(){
    listMenu1.addItem(new Menu_model("1","Dashboard",Menu_model.MenuType.MENU));
    listMenu1.addItem(new Menu_model("","Management",Menu_model.MenuType.TITLE));
    listMenu1.addItem(new Menu_model("2","Materials",Menu_model.MenuType.MENU));
    listMenu1.addItem(new Menu_model("3","Suppliers",Menu_model.MenuType.MENU));
    listMenu1.addItem(new Menu_model("4","Cleaners",Menu_model.MenuType.MENU));
    listMenu1.addItem(new Menu_model("5","Issue Stock",Menu_model.MenuType.MENU));
    listMenu1.addItem(new Menu_model("","Reports",Menu_model.MenuType.TITLE));
    listMenu1.addItem(new Menu_model("clipboard","Inventory",Menu_model.MenuType.MENU));
    listMenu1.addItem(new Menu_model("clipboard","Low-Stock",Menu_model.MenuType.MENU));
    listMenu1.addItem(new Menu_model("clipboard","Issuance History",Menu_model.MenuType.MENU));
    listMenu1.addItem(new Menu_model("clipboard","Material Usage",Menu_model.MenuType.MENU));
    listMenu1.addItem(new Menu_model("manage_accounts","Register Staff",Menu_model.MenuType.MENU));
    listMenu1.addItem(new Menu_model("logout","Logout",Menu_model.MenuType.MENU));
    listMenu1.addItem(new Menu_model(""," ",Menu_model.MenuType.EMPTY));
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelMoving = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        listMenu1 = new prg381group4.view.ListMenu<>();

        setPreferredSize(new java.awt.Dimension(215, 357));

        PanelMoving.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prg381group4/icon/Icon.png"))); // NOI18N
        jLabel1.setText("Univercity Cleaning");

        javax.swing.GroupLayout PanelMovingLayout = new javax.swing.GroupLayout(PanelMoving);
        PanelMoving.setLayout(PanelMovingLayout);
        PanelMovingLayout.setHorizontalGroup(
            PanelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMovingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
        );
        PanelMovingLayout.setVerticalGroup(
            PanelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMovingLayout.createSequentialGroup()
                .addGap(0, 15, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        listMenu1.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelMoving, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(listMenu1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelMoving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
private int x;
private int y;

public void initMoving(JFrame frame){
    PanelMoving.addMouseListener(new MouseAdapter(){
        @Override
        public void mousePressed(MouseEvent me) {
            x = me.getX();
            y = me.getY();
        }
    
    });
    PanelMoving.addMouseMotionListener(new MouseAdapter(){
        @Override
        public void mouseDragged(MouseEvent me) {
            frame.setLocation(me.getXOnScreen()-x, me.getYOnScreen()-y);
        }
        
    });
}
@Override
    protected void paintChildren(Graphics graphcs) {
        Graphics2D g2 = (Graphics2D) graphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, Color.decode("#ff9966"), 0, getHeight(), Color.decode("#ff5e62"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth()-20, 0, getWidth(), getHeight());
        super.paintChildren(graphcs);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelMoving;
    private javax.swing.JLabel jLabel1;
    private prg381group4.view.ListMenu<String> listMenu1;
    // End of variables declaration//GEN-END:variables
}
