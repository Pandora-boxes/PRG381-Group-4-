
package prg381group4.view;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

public class ListMenu<E extends Object> extends JList<E>{
    
    private final DefaultListModel model;
    private int selectedIndex = -1;
    private int overIndex = -1;
    private EventMenuSelected event;
    
    public void addEventMenuSelected(EventMenuSelected event){
        this.event = event;
    }
    public ListMenu(){
        model = new DefaultListModel();
        setModel(model);
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    int index = locationToIndex(me.getPoint());
                    Object o = model.getElementAt(index);
                    if(o instanceof Menu_model){
                        Menu_model menu = (Menu_model) o;
                        if (menu.getType() == Menu_model.MenuType.MENU) {
                            selectedIndex = index;
                            if(event != null){
                                event.selected(index);
                            }
                        }
                    } else{
                        selectedIndex = index;
                    }
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                overIndex = -1;
            }
            
        });
        addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent me) {
                int index = locationToIndex(me.getPoint());
                if (index != overIndex){
                    Object o = model.getElementAt(index);
                    if(o instanceof Menu_model){
                        Menu_model menu = (Menu_model) o;
                        if (menu.getType() == Menu_model.MenuType.MENU) {
                            overIndex = index;
                        }else{
                            overIndex = -1;
                        }
                    }
                    repaint();
                }
            }
        });
        
    }

    @Override
    public ListCellRenderer<? super E> getCellRenderer() {
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Menu_model data;
                if(value instanceof Menu_model){
                    data = (Menu_model) value;
                }else{
                    data = new Menu_model("",value+"", Menu_model.MenuType.EMPTY);
                }
                MenuItem item = new MenuItem(data);
                item.setSelected(selectedIndex == index);
                item.setOver(overIndex == index);
                return item;
            }
        };
    }
    
    public void addItem(Menu_model data){
        model.addElement(data);
    }
}
