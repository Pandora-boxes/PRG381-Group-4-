package prg381group4.view;

import javax.swing.table.DefaultTableModel;

// Small shared helper for the four Reports panels (Inventory, Low-Stock,
// Issuance History, Material Usage). Kept separate from the Management views
// so the reports work does not clash with the shell being built elsewhere.
//
// Reports are read-only: the user looks at them, never edits them. This helper
// hands back a table model whose cells can never be edited, so every report
// panel gets that behaviour without repeating the same anonymous class.
public class ReportTableUtil {

    // Private constructor: this class is only a bag of static helpers, so there
    // is never a reason to create an instance of it.
    private ReportTableUtil() {
    }

    // Build an empty table model with the given column headings. Rows are added
    // afterwards by the report panel. The isCellEditable override is what makes
    // the report read-only.
    public static DefaultTableModel readOnlyModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}
