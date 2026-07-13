package prg381group4.controller;

import java.util.List;
import prg381group4.model.Material;
import prg381group4.model.dao.MaterialDao;
import prg381group4.util.DataAccessException;
import prg381group4.util.ValidationException;
import prg381group4.util.Validator;

/**
 * REFERENCE IMPLEMENTATION - copy this shape for the other controllers
 * (Cleaner, Supplier, Issuance, Report, Auth).
 *
 * A controller is the ONLY thing a view is allowed to call. Its whole job is:
 *   1. validate the raw text the user typed on the form (through Validator),
 *   2. build the model object,
 *   3. hand it to the DAO,
 *   4. let ValidationException / DataAccessException travel back up to the view,
 *      which shows the message in a dialog.
 *
 * Notice what is NOT here: no SQL, no JDBC, no try/catch that hides errors. The
 * view never imports the DAO and the DAO never imports Swing. Keeping that line
 * clean is what the "software engineering" marks are for.
 */
public class MaterialController {

    // The controller owns its DAO. The view only ever sees this controller.
    private final MaterialDao materialDao = new MaterialDao();

    /** Every material, for the main table. */
    public List<Material> getAllMaterials() throws DataAccessException {
        return materialDao.findAll();
    }

    /** Only items at or below their reorder level - drives the dashboard alert. */
    public List<Material> getLowStock() throws DataAccessException {
        return materialDao.findLowStock();
    }

    /** Search box. A blank keyword just shows everything. */
    public List<Material> search(String keyword) throws DataAccessException {
        if (keyword == null || keyword.trim().isEmpty()) {
            return materialDao.findAll();
        }
        return materialDao.search(keyword.trim());
    }

    /**
     * Validates the form and inserts a new material. The number fields arrive as
     * the raw text the user typed, so Validator both checks AND parses them: an
     * empty or non-numeric quantity becomes a friendly ValidationException here
     * instead of a NumberFormatException crash in the view.
     *
     * @return the generated material id
     */
    public int addMaterial(String code, String name, String unit,
                           String quantityText, String reorderText, String priceText,
                           int supplierId) throws ValidationException, DataAccessException {
        Validator.requireText(code, "Code");
        Validator.requireText(name, "Name");
        Validator.requireText(unit, "Unit");
        int quantity = Validator.requireNonNegativeInt(quantityText, "Quantity");
        int reorder = Validator.requireNonNegativeInt(reorderText, "Reorder level");
        double price = Validator.requireNonNegativeDouble(priceText, "Unit price");

        Material m = new Material();
        m.setCode(code.trim());
        m.setName(name.trim());
        m.setUnit(unit.trim());
        m.setQuantity(quantity);
        m.setReorderLevel(reorder);
        m.setUnitPrice(price);
        m.setSupplierId(supplierId);
        return materialDao.insert(m);
    }

    /** Same validation as addMaterial, but updates the row with this id. */
    public boolean updateMaterial(int materialId, String code, String name, String unit,
                                  String quantityText, String reorderText, String priceText,
                                  int supplierId) throws ValidationException, DataAccessException {
        Validator.requireText(code, "Code");
        Validator.requireText(name, "Name");
        Validator.requireText(unit, "Unit");
        int quantity = Validator.requireNonNegativeInt(quantityText, "Quantity");
        int reorder = Validator.requireNonNegativeInt(reorderText, "Reorder level");
        double price = Validator.requireNonNegativeDouble(priceText, "Unit price");

        Material m = new Material();
        m.setMaterialId(materialId);
        m.setCode(code.trim());
        m.setName(name.trim());
        m.setUnit(unit.trim());
        m.setQuantity(quantity);
        m.setReorderLevel(reorder);
        m.setUnitPrice(price);
        m.setSupplierId(supplierId);
        return materialDao.update(m);
    }

    /**
     * Deletes a material by id. If the team decides deleting should be
     * supervisor-only, add the guard as the first line here:
     *     Session.requireSupervisor();
     * (see util.Session). Left off for now so a storekeeper can tidy the list.
     */
    public boolean deleteMaterial(int materialId) throws DataAccessException {
        return materialDao.delete(materialId);
    }
}
