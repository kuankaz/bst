package ru.redsolution.bst.data.table;

/**
 * Список единиц изменения.
 * 
 * @author alexander.ivanov
 * 
 */
public class UomTable extends NamedTable {

	private static final String NAME = "uom";

	private final static UomTable instance;

	static {
		instance = new UomTable();
		DatabaseHelper.getInstance().addTable(instance);
	}

	public static UomTable getInstance() {
		return instance;
	}

	private UomTable() {
	}

	@Override
	public String getTableName() {
		return NAME;
	}

}