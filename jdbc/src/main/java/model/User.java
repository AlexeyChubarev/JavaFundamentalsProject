package model;

import lombok.Value;
import lombok.AllArgsConstructor;

import java.sql.Blob;
import java.sql.Date;

/*
 *
 * SQL and PL/SQL Data Type NUMBER hss JDBC Mapping java.math.BigDecimal
 * SQL and PL/SQL Data Type BLOB has JDBC Mapping java.sql.Blob
 *
 */

@Value
@AllArgsConstructor
public class User
{
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String country;
}
