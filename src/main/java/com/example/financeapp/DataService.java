package com.example.financeapp;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.jooq.impl.DSL.*;

@Service
public class DataService {

    @Autowired
    private DSLContext create;

    // Define table and field references dynamically with uppercase names to match metadata
    private static final org.jooq.Table<Record> APP_USER = table("APP_USER");
    private static final org.jooq.Field<Long> USER_ID = field(name("APP_USER", "ID"), Long.class);
    private static final org.jooq.Field<String> USERNAME = field(name("APP_USER", "USERNAME"), String.class);

    private static final org.jooq.Table<Record> INSTITUTE = table("INSTITUTE");
    private static final org.jooq.Field<Long> INST_ID = field(name("INSTITUTE", "ID"), Long.class);
    private static final org.jooq.Field<String> INST_NAME = field(name("INSTITUTE", "NAME"), String.class);

    private static final org.jooq.Table<Record> CATEGORY = table("CATEGORY");
    private static final org.jooq.Field<Long> CAT_ID = field(name("CATEGORY", "ID"), Long.class);
    private static final org.jooq.Field<String> CAT_NAME = field(name("CATEGORY", "NAME"), String.class);

    private static final org.jooq.Table<Record> TRANSACTION_ENTRY = table("TRANSACTION_ENTRY");
    private static final org.jooq.Field<Long> TX_ID = field(name("TRANSACTION_ENTRY", "ID"), Long.class);
    private static final org.jooq.Field<Long> TX_USER_ID = field(name("TRANSACTION_ENTRY", "USER_ID"), Long.class);
    private static final org.jooq.Field<Long> TX_INST_ID = field(name("TRANSACTION_ENTRY", "INSTITUTE_ID"), Long.class);
    private static final org.jooq.Field<Long> TX_CAT_ID = field(name("TRANSACTION_ENTRY", "CATEGORY_ID"), Long.class);
    private static final org.jooq.Field<BigDecimal> TX_AMOUNT = field(name("TRANSACTION_ENTRY", "AMOUNT"), BigDecimal.class);
    private static final org.jooq.Field<LocalDate> TX_DATE = field(name("TRANSACTION_ENTRY", "ENTRY_DATE"), LocalDate.class);

    // DTO Records
    public record UserDto(Long id, String username) {}
    public record InstituteDto(Long id, String name) {}
    public record CategoryDto(Long id, String name) {}
    public record TransactionDto(Long id, String username, String institute, String category, BigDecimal amount, LocalDate date) {}

    public List<UserDto> getUsers() {
        return create.select(USER_ID, USERNAME)
                .from(APP_USER)
                .fetch(r -> new UserDto(r.get(USER_ID), r.get(USERNAME)));
    }

    public List<InstituteDto> getInstitutes() {
        return create.select(INST_ID, INST_NAME)
                .from(INSTITUTE)
                .orderBy(INST_NAME.asc())
                .fetch(r -> new InstituteDto(r.get(INST_ID), r.get(INST_NAME)));
    }

    public void addInstitute(String name) {
        create.insertInto(INSTITUTE, INST_NAME)
                .values(name)
                .execute();
    }

    public boolean instituteExists(String name) {
        return create.fetchExists(
                create.selectOne()
                        .from(INSTITUTE)
                        .where(INST_NAME.equalIgnoreCase(name))
        );
    }

    public boolean isInstituteInUse(Long id) {
        return create.fetchExists(
                create.selectOne()
                        .from(TRANSACTION_ENTRY)
                        .where(TX_INST_ID.eq(id))
        );
    }

    public void deleteInstitute(Long id) {
        create.deleteFrom(INSTITUTE)
                .where(INST_ID.eq(id))
                .execute();
    }

    public List<CategoryDto> getCategories() {
        return create.select(CAT_ID, CAT_NAME)
                .from(CATEGORY)
                .orderBy(CAT_NAME.asc())
                .fetch(r -> new CategoryDto(r.get(CAT_ID), r.get(CAT_NAME)));
    }

    public void addCategory(String name) {
        create.insertInto(CATEGORY, CAT_NAME)
                .values(name)
                .execute();
    }

    public boolean categoryExists(String name) {
        return create.fetchExists(
                create.selectOne()
                        .from(CATEGORY)
                        .where(CAT_NAME.equalIgnoreCase(name))
        );
    }

    public boolean isCategoryInUse(Long id) {
        return create.fetchExists(
                create.selectOne()
                        .from(TRANSACTION_ENTRY)
                        .where(TX_CAT_ID.eq(id))
        );
    }

    public void deleteCategory(Long id) {
        create.deleteFrom(CATEGORY)
                .where(CAT_ID.eq(id))
                .execute();
    }

    public List<TransactionDto> getTransactions(String usernameFilter) {
        var base = create.select(
                TX_ID,
                USERNAME,
                INST_NAME,
                CAT_NAME,
                TX_AMOUNT,
                TX_DATE
        )
        .from(TRANSACTION_ENTRY)
        .join(APP_USER).on(TX_USER_ID.eq(USER_ID))
        .join(INSTITUTE).on(TX_INST_ID.eq(INST_ID))
        .join(CATEGORY).on(TX_CAT_ID.eq(CAT_ID));

        var query = (usernameFilter != null) ? base.where(USERNAME.eq(usernameFilter)) : base;

        return query.orderBy(TX_DATE.desc())
                .fetch(r -> new TransactionDto(
                        r.get(TX_ID),
                        r.get(USERNAME),
                        r.get(INST_NAME),
                        r.get(CAT_NAME),
                        r.get(TX_AMOUNT),
                        r.get(TX_DATE)
                ));
    }

    public void addTransaction(String username, String instituteName, String categoryName, BigDecimal amount, LocalDate date) {
        Long userId = create.select(USER_ID).from(APP_USER).where(USERNAME.eq(username)).fetchOne(USER_ID);
        Long instId = create.select(INST_ID).from(INSTITUTE).where(INST_NAME.eq(instituteName)).fetchOne(INST_ID);
        Long catId = create.select(CAT_ID).from(CATEGORY).where(CAT_NAME.eq(categoryName)).fetchOne(CAT_ID);

        if (userId != null && instId != null && catId != null) {
            create.insertInto(TRANSACTION_ENTRY, TX_USER_ID, TX_INST_ID, TX_CAT_ID, TX_AMOUNT, TX_DATE)
                    .values(userId, instId, catId, amount, date)
                    .execute();
        }
    }

    public void deleteTransaction(Long id) {
        create.deleteFrom(TRANSACTION_ENTRY)
                .where(TX_ID.eq(id))
                .execute();
    }
}
