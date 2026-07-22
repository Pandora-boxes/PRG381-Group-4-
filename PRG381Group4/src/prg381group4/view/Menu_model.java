
package prg381group4.view;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Menu_model {

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public Menu_model(String icon, String name, MenuType type) {
        this.icon = icon;
        this.name = name;
        this.type = type;
    }
    
    private String icon;
    private String name;
    private MenuType type;
    
    public Icon toIcon(){
        return new ImageIcon(getClass().getResource("/prg381group4/icon/"+ this.getIcon()+".png"));
    }
    public enum MenuType{
        TITLE,MENU,EMPTY
    }
}
