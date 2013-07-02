package com.google.code.morphia.converters;


import java.lang.reflect.Array;

import com.google.code.morphia.mapping.MappedField;
import com.google.code.morphia.mapping.MappingException;


/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * @author scotthernandez
 */
@SuppressWarnings({"rawtypes"})
public class ByteConverter extends TypeConverter implements SimpleValueConverter {
  public ByteConverter() {
    super(byte.class, Byte.class, byte[].class, Byte[].class);
  }

  @Override
  public Object decode(final Class targetClass, final Object val, final MappedField optionalExtraInfo) throws MappingException {
    if (val == null) {
      return null;
    }

    if (val.getClass() == targetClass) {
      return val;
    }

    if (val instanceof Number) {
      return ((Number) val).byteValue();
    }

    if (targetClass.equals(Byte[].class) && val.getClass().equals(byte[].class)) {
      final Class<?> type = targetClass.isArray() ? targetClass.getComponentType() : targetClass;
      return convertToWrapperArray((byte[]) val);
    }
    return Byte.parseByte(val.toString());
  }

  @Override
  public Object encode(final Object value, final MappedField optionalExtraInfo) {
    if (value instanceof Byte[]) {
      return super.encode(convertToPrimitiveArray((Byte[]) value), optionalExtraInfo);
    }
    return super.encode(value, optionalExtraInfo);
  }

  public static Object convertToPrimitiveArray(final Byte[] values) {
    final int length = values.length;
    final Object array = Array.newInstance(byte.class, length);
    for (int i = 0; i < length; i++) {
      Array.set(array, i, values[i]);
    }
    return array;
  }

  public static Object convertToWrapperArray(final byte[] values) {
    final int length = values.length;
    final Object array = Array.newInstance(Byte.class, length);
    for (int i = 0; i < length; i++) {
      Array.set(array, i, new Byte(values[i]));
    }
    return array;
  }
}
