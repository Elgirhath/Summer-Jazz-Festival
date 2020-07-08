package ag.example1.festivalapp.database.orm;

import android.content.ContentValues;
import android.database.Cursor;

import ag.example1.festivalapp.database.entity.ConcertEntity;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class ORMapper<T extends Serializable> {
    private Class<T> tClass;
    private Field[] columns;

    public ORMapper(Class<T> tClass) {
        this.tClass = tClass;
        columns = ConcertEntity.class.getDeclaredFields();
    }

    public T toObject(Cursor cursor) {
        try {
            T entity = tClass.newInstance();

            for (Field column : columns) {
                String fieldName = column.getName();
                Type fieldType = column.getGenericType();
                Field destField = entity.getClass().getDeclaredField(fieldName);

                if (fieldType == int.class) {
                    int value = cursor.getInt(cursor.getColumnIndex(fieldName));
                    destField.set(entity, value);
                }
                if (fieldType == String.class) {
                    String value = cursor.getString(cursor.getColumnIndex(fieldName));
                    destField.set(entity, value);
                }
            }

            return entity;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public T[] toObjects(Cursor cursor) {
        @SuppressWarnings("unchecked")
        T[] mappedArray = (T[]) Array.newInstance(tClass, cursor.getCount());
        while (cursor.moveToNext()) {
            mappedArray[cursor.getPosition()] = toObject(cursor);
        }
        return mappedArray;
    }

    public String[] toInsertableValues() {
        String[] values = new String[columns.length];

        for (int i = 0; i < columns.length; i++) {
            Field column = columns[i];
            String fieldName = column.getName();

            Type fieldType = column.getGenericType();
            String fieldTypeName = "";

            if (fieldType == int.class) {
                fieldTypeName = "INTEGER";
            }
            else if (fieldType == String.class) {
                fieldTypeName = "TEXT";
            }

            if (column.getAnnotation(Id.class) != null) {
                fieldTypeName += " PRIMARY KEY AUTOINCREMENT";
            }

            values[i] = fieldName + " " + fieldTypeName;
        }

        return values;
    }

    public ContentValues toContentValues(T entity) throws IllegalAccessException {
        ContentValues contentValues = new ContentValues();

        for (Field column : columns) {
            if (column.getAnnotation(Id.class) != null) {
                continue;
            }

            Type fieldType = column.getGenericType();

            if (fieldType == int.class) {
                contentValues.put(column.getName(), (int) column.get(entity));
            }
            if (fieldType == String.class) {
                contentValues.put(column.getName(), (String) column.get(entity));
            }
        }

        return contentValues;
    }
}
