package com.example.livescore.enums;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class PgSQLEnumType extends EnumType {

    public void nullSafeSet(
            PreparedStatement ps, Object value, int index,
            SharedSessionContractImplementor session
    ) throws HibernateException, SQLException {

        ps.setObject(index, value != null ? ((Enum) value).name() : null, Types.OTHER);
    }

}