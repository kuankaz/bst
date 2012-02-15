package ru.redsolution.bst.data.table;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentValues;

/**
 * Таблица с именен объекта и его идентификатором.
 * 
 * @author alexander.ivanov
 * 
 */
public abstract class NamedTable extends BaseTable {
	public static interface Fields extends BaseTable.Fields {
		public static final String NAME = "name";
	}

	@Override
	public Collection<String> getFields() {
		Collection<String> collection = new ArrayList<String>(super.getFields());
		collection.add(Fields.NAME);
		return collection;
	}

	/**
	 * @param id
	 * @return Имя объекта по его идентификатору.
	 * @throws MultipleObjectsReturnedException
	 * @throws ObjectDoesNotExistException
	 */
	public String getName(String id) throws ObjectDoesNotExistException,
			MultipleObjectsReturnedException {
		return getById(id).getAsString(Fields.NAME);
	}

	/**
	 * @param id
	 * @param name
	 * @return Набор значений для добавления элемента.
	 */
	protected ContentValues getValues(String id, String name) {
		ContentValues values = new ContentValues();
		values.put(Fields._ID, id);
		values.put(Fields.NAME, name);
		return values;
	}

	/**
	 * Добавить элемент.
	 * 
	 * @param id
	 * @param name
	 */
	public void add(String id, String name) {
		add(getValues(id, name));
	}
}