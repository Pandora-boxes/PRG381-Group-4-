package prg381group4.model;

import java.sql.Timestamp;

/**
 * A record of stock being issued to a cleaner. Creating one of these ALWAYS
 * deducts from Material.quantity -- see IssuanceDao.issue(), which does both
 * inside a single database transaction.
 */
public class Issuance {

    private int issuanceId;
    private int materialId;
    private int cleanerId;
    private int issuedBy;          // FK to app_user
    private int quantity;
    private Timestamp issueDate;
    private String notes;

    // read-only display fields, filled by the DAO's JOIN
    private String materialName;
    private String cleanerName;
    private String issuedByName;

    public Issuance() {
    }

    public Issuance(int materialId, int cleanerId, int issuedBy, int quantity, String notes) {
        this.materialId = materialId;
        this.cleanerId = cleanerId;
        this.issuedBy = issuedBy;
        this.quantity = quantity;
        this.notes = notes;
    }

    public int getIssuanceId() {
        return issuanceId;
    }

    public void setIssuanceId(int issuanceId) {
        this.issuanceId = issuanceId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getCleanerId() {
        return cleanerId;
    }

    public void setCleanerId(int cleanerId) {
        this.cleanerId = cleanerId;
    }

    public int getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(int issuedBy) {
        this.issuedBy = issuedBy;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getCleanerName() {
        return cleanerName;
    }

    public void setCleanerName(String cleanerName) {
        this.cleanerName = cleanerName;
    }

    public String getIssuedByName() {
        return issuedByName;
    }

    public void setIssuedByName(String issuedByName) {
        this.issuedByName = issuedByName;
    }
}
