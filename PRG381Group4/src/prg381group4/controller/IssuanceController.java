package prg381group4.controller;

// OWNER: Juan
// Placeholder stub only - no logic yet.
// TODO: issuing/returning stock. Wraps IssuanceDao. Issuing is one transaction
//       (check stock, insert issuance, deduct quantity) and must throw
//       InsufficientStockException when there is not enough stock.

import java.util.List;
import prg381group4.model.Issuance;
import prg381group4.model.dao.IssuanceDao;
import prg381group4.util.DataAccessException;
import prg381group4.util.InsufficientStockException;
import prg381group4.util.Session;
import prg381group4.util.ValidationException;
import prg381group4.util.Validator;

public class IssuanceController {

    private final IssuanceDao issuanceDao = new IssuanceDao();

    public List<Issuance> getAllIssuances() throws DataAccessException {
        return issuanceDao.findAll();
    }

    public List<Issuance> getRecentIssuances(int howMany) throws DataAccessException {
        return issuanceDao.findRecent(howMany);
    }

    public List<Issuance> search(String keyword) throws DataAccessException {
        if (keyword == null || keyword.trim().isEmpty()) {
            return issuanceDao.findAll();
        }
        return issuanceDao.search(keyword.trim());
    }

    public int issueStock(int materialId, int cleanerId, String quantityText, String notes)
            throws ValidationException, DataAccessException, InsufficientStockException {
        int quantity = Validator.requirePositiveInt(quantityText, "Quantity");

        Issuance issuance = new Issuance();
        issuance.setMaterialId(materialId);
        issuance.setCleanerId(cleanerId);
        issuance.setIssuedBy(Session.getCurrentUser().getId());
        issuance.setQuantity(quantity);
        issuance.setNotes(notes == null ? null : notes.trim());
        return issuanceDao.issue(issuance);
    }

    public boolean deleteIssuance(int issuanceId) throws ValidationException, DataAccessException {
        Session.requireSupervisor();
        return issuanceDao.delete(issuanceId);
    }
}
