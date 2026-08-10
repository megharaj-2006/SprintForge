# SprintForge Database Migrations (Flyway)

This directory contains the database migration scripts managed by **Flyway**.

---

## 1. Migration Naming Conventions

Flyway requires strict naming conventions to identify, order, and execute database migration scripts properly.

### File Format

```text
V<Version>__<Description>.sql
```

> **Note**: Use **two underscores (`__`)** between the version number and the description!

### Examples

- `V1__Baseline.sql` - Initial schema snapshot
- `V2__Add_User_Preferences_Table.sql` - Incremental version 2
- `V2.1__Add_Index_On_Task_Due_Date.sql` - Minor sub-version
- `V2026.08.04.1__Add_Sprint_Goal_Status.sql` - Date-based versioning scheme

---

## 2. Types of Migration Scripts

### Versioned Migrations (`V<Version>__<Description>.sql`)
- Executed **exactly once** in strict sequential order based on version number.
- Recorded in the `flyway_schema_history` table.
- Modifying an already applied versioned migration file causes checksum validation errors.

### Repeatable Migrations (`R__<Description>.sql`)
- Executed whenever their checksum changes (e.g. updating stored procedures, views, or reference data).
- Re-run automatically upon application start after versioned migrations are complete.

---

## 3. Best Practices for Future Migrations

1. **Immutability**: Never alter or delete a versioned migration file that has already been executed in any environment (dev, staging, prod).
2. **Backward Compatibility**:
   - Do NOT drop columns/tables without a phase-out strategy.
   - Default values must be provided for new `NOT NULL` columns.
3. **Idempotency & Safety**:
   - Ensure DDL statements run cleanly.
   - Wrap complex data transformations inside transactional blocks where applicable.
4. **No Destructive Drops in Baseline**:
   - Baseline migration (`V1__Baseline.sql`) must not contain `DROP TABLE` statements.
5. **Entity Alignment**:
   - After writing a migration, update corresponding JPA entities accordingly. Since `spring.jpa.hibernate.ddl-auto` is set to `validate`, application startup will fail if JPA entity definitions do not match the database schema.

---

## 4. How Baseline Migration Works for Existing Databases

For existing environments where database tables already exist prior to integrating Flyway:
- `spring.flyway.baseline-on-migrate=true`
- `spring.flyway.baseline-version=1`

When the application starts against an existing database, Flyway checks for `flyway_schema_history`. If it is absent, Flyway creates `flyway_schema_history` and marks version `1` (`V1__Baseline.sql`) as **applied** without running `V1__Baseline.sql` against the existing database. Future migrations (`V2`, `V3`, etc.) will run sequentially on top of version 1.

For new / fresh databases (e.g., CI/CD pipelines, fresh local environments):
- Flyway executes `V1__Baseline.sql` from scratch to build the complete initial database schema.
