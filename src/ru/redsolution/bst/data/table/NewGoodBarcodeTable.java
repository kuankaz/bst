package ru.redsolution.bst.data.table;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Список новых штрих кодов для товаров.
 * 
 * @author alexander.ivanov
 * 
 */
public class NewGoodBarcodeTable extends BaseBarcodeTable {
	public static interface Fields extends BaseBarcodeTable.Fields {
		public static final String IS_CUSTOM = "is_custom";
	}

	private static final String NAME = "new_good_barcode";

	private final static NewGoodBarcodeTable instance;

	static {
		instance = new NewGoodBarcodeTable();
		DatabaseHelper.getInstance().addTable(instance);
	}

	public static NewGoodBarcodeTable getInstance() {
		return instance;
	}

	private NewGoodBarcodeTable() {
	}

	@Override
	public String getTableName() {
		return NAME;
	}

	@Override
	protected Collection<String> getFields() {
		Collection<String> collection = new ArrayList<String>(super.getFields());
		collection.add(Fields.IS_CUSTOM);
		return collection;
	}

	@Override
	protected void putValue(ContentValues values, String name, Cursor cursor) {
		super.putValue(values, name, cursor);
		if (Fields.IS_CUSTOM.equals(name))
			values.put(name, getBoolean(values.getAsString(name)));
	}

	@Override
	public void add(String id, String type, String value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Добавить штрих код.
	 * 
	 * @param id
	 *            Идентификатор объекта, для которого создаётся штрих код.
	 * @param isCustom
	 *            Является ли объект пользовательским.
	 * @param type
	 *            Тип штрих кода.
	 * @param value
	 *            Значение штрих кода.
	 */
	public void add(String id, boolean isCustom, String type, String value) {
		ContentValues values = new ContentValues();
		values.put(Fields._ID, id);
		values.put(Fields.IS_CUSTOM, isCustom);
		values.put(Fields.TYPE, type);
		values.put(Fields.VALUE, value);
		add(values);
	}

}
