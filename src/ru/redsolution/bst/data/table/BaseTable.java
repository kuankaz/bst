package ru.redsolution.bst.data.table;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Реализует базовый функционал по работе с таблицами базы данных.
 * 
 * @author alexander.ivanov
 * 
 */
public abstract class BaseTable implements DatabaseTable {
	public static interface Fields extends BaseColumns {
		public static final String TABLE_NAME = "table_name";
	}

	/**
	 * @return Имя таблицы.
	 */
	protected abstract String getTableName();

	/**
	 * @return Список полей в таблице.
	 */
	public Collection<String> getFields() {
		Collection<String> collection = new ArrayList<String>();
		collection.add(Fields._ID);
		return collection;
	}

	/**
	 * @param name
	 *            Имя поля.
	 * @return Тип поля.
	 */
	public String getFieldType(String name) {
		return "TEXT";
	}

	@Override
	public void create(SQLiteDatabase db) {
		StringBuilder builder = new StringBuilder("CREATE TABLE ");
		builder.append(getTableName());
		builder.append(" (");
		boolean first = true;
		for (String name : getFields()) {
			if (first)
				first = false;
			else
				builder.append(", ");
			builder.append(name);
			builder.append(" ");
			builder.append(getFieldType(name));
		}
		builder.append(");");
		db.execSQL(builder.toString());
	}

	@Override
	public void migrate(SQLiteDatabase db, int toVersion) {
	}

	@Override
	public void clear() {
		DatabaseHelper.getInstance().getWritableDatabase()
				.delete(getTableName(), null, null);
	}

	/**
	 * @param selection
	 * @param selectionArgs
	 * @param orderBy
	 * @return Фильтрованный курсор.
	 */
	protected Cursor filter(String selection, String[] selectionArgs,
			String orderBy) {
		StringBuilder query = new StringBuilder(120);
		query.append("SELECT ");
		boolean first = true;
		for (String name : getFields()) {
			if (first)
				first = false;
			else
				query.append(", ");
			query.append(name);
		}
		query.append(", '");
		query.append(getTableName());
		query.append("' AS ");
		query.append(Fields.TABLE_NAME);
		query.append(" FROM ");
		query.append(getTableName());
		if (selection != null && !"".equals(selection)) {
			query.append(" WHERE ");
			query.append(selection);
		}
		if (orderBy != null && !"".equals(orderBy)) {
			query.append(" ORDER BY ");
			query.append(orderBy);
		}
		return DatabaseHelper.getInstance().getReadableDatabase()
				.rawQuery(query.toString(), selectionArgs);
	}

	/**
	 * @param selection
	 * @param selectionArgs
	 * @return Строковые значения полей объекта.
	 * @throws ObjectDoesNotExistException
	 * @throws MultipleObjectsReturnedException
	 */
	protected ContentValues get(String selection, String[] selectionArgs)
			throws ObjectDoesNotExistException,
			MultipleObjectsReturnedException {
		Cursor cursor = filter(selection, selectionArgs, null);
		try {
			if (cursor.getCount() > 1)
				throw new MultipleObjectsReturnedException();
			if (cursor.moveToFirst()) {
				ContentValues values = new ContentValues();
				for (String name : getFields())
					values.put(name,
							cursor.getString(cursor.getColumnIndex(name)));
				return values;
			}
		} finally {
			cursor.close();
		}
		throw new ObjectDoesNotExistException();
	}

	/**
	 * @return Список всех объетов.
	 */
	public Cursor list() {
		return filter(null, null, null);
	}

	/**
	 * @param id
	 * @return Строковые значения полей объекта.
	 * @throws MultipleObjectsReturnedException
	 * @throws ObjectDoesNotExistException
	 */
	public ContentValues getById(String id) throws ObjectDoesNotExistException,
			MultipleObjectsReturnedException {
		return get(Fields._ID + " = ?", new String[] { id });
	}

	/**
	 * Добавить элемент.
	 * 
	 * @param values
	 * @return
	 */
	protected long add(ContentValues values) {
		return DatabaseHelper.getInstance().getWritableDatabase()
				.insert(getTableName(), null, values);
	}
}
