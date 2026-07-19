package prg381group4.controller;

// OWNER: Juan
// Placeholder stub only - no logic yet.
// TODO: reports. Low-stock report (quantity <= reorder_level) and issuance
//       history. Read-only queries through the DAOs.
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import prg381group4.model.Issuance;
import prg381group4.model.Material;
import prg381group4.model.dao.IssuanceDao;
import prg381group4.model.dao.MaterialDao;
import prg381group4.util.DataAccessException;

public class ReportController {

    private final MaterialDao materialDao = new MaterialDao();
    private final IssuanceDao issuanceDao = new IssuanceDao();

    public List<Material> getInventoryReport() throws DataAccessException {
        return materialDao.findAll();
    }

    public List<Material> getLowStockReport() throws DataAccessException {
        return materialDao.findLowStock();
    }

    public List<Issuance> getIssuanceHistoryReport() throws DataAccessException {
        return issuanceDao.findAll();
    }

    public List<MaterialUsage> getMaterialUsageReport() throws DataAccessException {
        List<Issuance> history = issuanceDao.findAll();

        Map<String, Integer> totals = new LinkedHashMap<>();
        for (Issuance i : history) {
            totals.merge(i.getMaterialName(), i.getQuantity(), Integer::sum);
        }

        List<MaterialUsage> report = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : totals.entrySet()) {
            report.add(new MaterialUsage(entry.getKey(), entry.getValue()));
        }
        report.sort((a, b) -> b.getTotalIssued() - a.getTotalIssued());
        return report;
    }

    public static class MaterialUsage {
        private final String materialName;
        private final int totalIssued;

        public MaterialUsage(String materialName, int totalIssued) {
            this.materialName = materialName;
            this.totalIssued = totalIssued;
        }

        public String getMaterialName() {
            return materialName;
        }

        public int getTotalIssued() {
            return totalIssued;
        }
    }
}

