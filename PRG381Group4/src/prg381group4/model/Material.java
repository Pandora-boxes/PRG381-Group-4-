package prg381group4.model;

/**
 * A cleaning material held in stock (e.g. bleach, mops, gloves).
 */
public class Material {

    private int materialId;
    private String code;
    private String name;
    private String unit;
    private int quantity;
    private int reorderLevel;
    private double unitPrice;
    private int supplierId;
    private String supplierName;   // read-only, filled by the DAO's JOIN

    public Material() {
    }

    public Material(int materialId, String code, String name, String unit,
                    int quantity, int reorderLevel, double unitPrice, int supplierId) {
        this.materialId = materialId;
        this.code = code;
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.unitPrice = unitPrice;
        this.supplierId = supplierId;
    }

    /**
     * Business rule: an item is low on stock once it drops to or below its
     * reorder level. The dashboard and the low-stock report both use this.
     */
    public boolean isLowStock() {
        return quantity <= reorderLevel;
    }

    public double getStockValue() {
        return quantity * unitPrice;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public String toString() {
        return code + " - " + name;
    }
}
