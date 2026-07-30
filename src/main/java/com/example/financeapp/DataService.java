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

    private static final org.jooq.Table<Record> BUDGET_CATEGORY = table("BUDGET_CATEGORY");
    private static final org.jooq.Field<Long> BUDGET_CAT_ID = field(name("BUDGET_CATEGORY", "ID"), Long.class);
    private static final org.jooq.Field<String> BUDGET_CAT_NAME = field(name("BUDGET_CATEGORY", "NAME"), String.class);

    private static final org.jooq.Table<Record> BUDGET_TRANSACTION = table("BUDGET_TRANSACTION");
    private static final org.jooq.Field<Long> BTX_ID = field(name("BUDGET_TRANSACTION", "ID"), Long.class);
    private static final org.jooq.Field<LocalDate> BTX_DATE = field(name("BUDGET_TRANSACTION", "TX_DATE"), LocalDate.class);
    private static final org.jooq.Field<String> BTX_TYPE = field(name("BUDGET_TRANSACTION", "TX_TYPE"), String.class);
    private static final org.jooq.Field<BigDecimal> BTX_AMOUNT = field(name("BUDGET_TRANSACTION", "AMOUNT"), BigDecimal.class);
    private static final org.jooq.Field<Long> BTX_CAT_ID = field(name("BUDGET_TRANSACTION", "CATEGORY_ID"), Long.class);
    private static final org.jooq.Field<Long> BTX_USER_ID = field(name("BUDGET_TRANSACTION", "USER_ID"), Long.class);
    private static final org.jooq.Field<String> BTX_DESC = field(name("BUDGET_TRANSACTION", "DESCRIPTION"), String.class);

    private static final org.jooq.Table<Record> IMPORT_METADATA = table("IMPORT_METADATA");
    private static final org.jooq.Field<Integer> META_ID = field(name("IMPORT_METADATA", "ID"), Integer.class);
    private static final org.jooq.Field<String> META_FILENAME = field(name("IMPORT_METADATA", "LAST_FILENAME"), String.class);
    private static final org.jooq.Field<java.time.LocalDateTime> META_TIMESTAMP = field(name("IMPORT_METADATA", "UPLOAD_TIMESTAMP"), java.time.LocalDateTime.class);

    // DTO Records
    public record UserDto(Long id, String username) {}
    public record InstituteDto(Long id, String name) {}
    public record CategoryDto(Long id, String name) {}
    public record TransactionDto(Long id, String username, String institute, String category, BigDecimal amount, LocalDate date) {}
    public record DateSummaryDto(LocalDate date, BigDecimal totalAmount) {}
    public record LastEntryDto(BigDecimal amount, String categoryName) {}
    public record DateEntryDto(String instituteName, String categoryName, BigDecimal amount) {}
    public record BudgetTransactionDto(Long id, LocalDate date, String type, BigDecimal amount, String category, String person, String description) {}
    public record ImportMetadataDto(String filename, java.time.LocalDateTime uploadTimestamp) {}

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

    public List<DateSummaryDto> getDateSummaries(String username) {
        var base = create.select(TX_DATE, sum(TX_AMOUNT))
                .from(TRANSACTION_ENTRY)
                .join(APP_USER).on(TX_USER_ID.eq(USER_ID));
        var query = (username != null) ? base.where(USERNAME.eq(username)) : base;
        return query.groupBy(TX_DATE)
                .orderBy(TX_DATE.desc())
                .fetch(org.jooq.Records.mapping(DateSummaryDto::new));
    }

    public List<DateSummaryDto> getChronologicalDateSummaries(String username) {
        var base = create.select(TX_DATE, sum(TX_AMOUNT))
                .from(TRANSACTION_ENTRY)
                .join(APP_USER).on(TX_USER_ID.eq(USER_ID));
        var query = (username != null) ? base.where(USERNAME.eq(username)) : base;
        return query.groupBy(TX_DATE)
                .orderBy(TX_DATE.asc())
                .fetch(org.jooq.Records.mapping(DateSummaryDto::new));
    }

    public LastEntryDto getLastEntry(String username, String instituteName, LocalDate beforeOrOnDate) {
        var base = create.select(TX_AMOUNT, CAT_NAME)
                .from(TRANSACTION_ENTRY)
                .join(APP_USER).on(TX_USER_ID.eq(USER_ID))
                .join(INSTITUTE).on(TX_INST_ID.eq(INST_ID))
                .join(CATEGORY).on(TX_CAT_ID.eq(CAT_ID))
                .where(INST_NAME.eq(instituteName))
                .and(TX_DATE.lessOrEqual(beforeOrOnDate));
        var query = (username != null) ? base.and(USERNAME.eq(username)) : base;
        var record = query.orderBy(TX_DATE.desc(), TX_ID.desc())
                .limit(1)
                .fetchOne();
        if (record == null) {
            return null;
        }
        return new LastEntryDto(record.get(TX_AMOUNT), record.get(CAT_NAME));
    }

    public LastEntryDto getLastEntry(String username, String instituteName, String categoryName, LocalDate beforeOrOnDate) {
        var base = create.select(TX_AMOUNT, CAT_NAME)
                .from(TRANSACTION_ENTRY)
                .join(APP_USER).on(TX_USER_ID.eq(USER_ID))
                .join(INSTITUTE).on(TX_INST_ID.eq(INST_ID))
                .join(CATEGORY).on(TX_CAT_ID.eq(CAT_ID))
                .where(INST_NAME.eq(instituteName))
                .and(CAT_NAME.eq(categoryName))
                .and(TX_DATE.lessOrEqual(beforeOrOnDate));
        var query = (username != null) ? base.and(USERNAME.eq(username)) : base;
        var record = query.orderBy(TX_DATE.desc(), TX_ID.desc())
                .limit(1)
                .fetchOne();
        if (record == null) {
            return null;
        }
        return new LastEntryDto(record.get(TX_AMOUNT), record.get(CAT_NAME));
    }

    public LocalDate getMostRecentEntryDateBefore(String username, LocalDate date) {
        var base = create.select(TX_DATE)
                .from(TRANSACTION_ENTRY)
                .join(APP_USER).on(TX_USER_ID.eq(USER_ID))
                .where(TX_DATE.lessThan(date));
        var query = (username != null) ? base.and(USERNAME.eq(username)) : base;
        return query.orderBy(TX_DATE.desc())
                .limit(1)
                .fetchOne(TX_DATE);
    }

    public List<DateEntryDto> getEntriesForDate(String username, LocalDate date) {
        var base = create.select(INST_NAME, CAT_NAME, sum(TX_AMOUNT))
                .from(TRANSACTION_ENTRY)
                .join(APP_USER).on(TX_USER_ID.eq(USER_ID))
                .join(INSTITUTE).on(TX_INST_ID.eq(INST_ID))
                .join(CATEGORY).on(TX_CAT_ID.eq(CAT_ID))
                .where(TX_DATE.eq(date));
        var query = (username != null) ? base.and(USERNAME.eq(username)) : base;
        return query.groupBy(INST_NAME, CAT_NAME)
                .fetch(r -> new DateEntryDto(r.get(INST_NAME), r.get(CAT_NAME), r.get(sum(TX_AMOUNT), BigDecimal.class)));
    }

    public void saveEntriesForDate(String username, LocalDate date, Collection<DateEntryDto> entries) {
        String targetUser = (username != null) ? username : "Jens";
        Long userId = create.select(USER_ID).from(APP_USER).where(USERNAME.eq(targetUser)).fetchOne(USER_ID);
        if (userId == null) return;

        if (username != null) {
            create.deleteFrom(TRANSACTION_ENTRY)
                    .where(TX_USER_ID.eq(userId))
                    .and(TX_DATE.eq(date))
                    .execute();
        } else {
            create.deleteFrom(TRANSACTION_ENTRY)
                    .where(TX_DATE.eq(date))
                    .execute();
        }

        for (var entry : entries) {
            if (entry.amount() == null) {
                continue;
            }
            Long instId = create.select(INST_ID).from(INSTITUTE).where(INST_NAME.eq(entry.instituteName())).fetchOne(INST_ID);
            Long catId = create.select(CAT_ID).from(CATEGORY).where(CAT_NAME.eq(entry.categoryName())).fetchOne(CAT_ID);
            if (instId != null && catId != null) {
                create.insertInto(TRANSACTION_ENTRY, TX_USER_ID, TX_INST_ID, TX_CAT_ID, TX_AMOUNT, TX_DATE)
                        .values(userId, instId, catId, entry.amount(), date)
                        .execute();
            }
        }
    }

    public void deleteEntriesForDate(String username, LocalDate date) {
        if (username != null) {
            Long userId = create.select(USER_ID).from(APP_USER).where(USERNAME.eq(username)).fetchOne(USER_ID);
            if (userId == null) return;
            create.deleteFrom(TRANSACTION_ENTRY)
                    .where(TX_USER_ID.eq(userId))
                    .and(TX_DATE.eq(date))
                    .execute();
        } else {
            create.deleteFrom(TRANSACTION_ENTRY)
                    .where(TX_DATE.eq(date))
                    .execute();
        }
    }

    public void deleteAllTransactionsExceptDates(List<LocalDate> validDates) {
        if (validDates == null || validDates.isEmpty()) return;
        create.deleteFrom(TRANSACTION_ENTRY)
                .where(TX_DATE.notIn(validDates))
                .execute();
    }

    public record CategoryShareDto(String categoryName, BigDecimal totalAmount) {}
    public record CategoryHistoricalShareDto(LocalDate date, String categoryName, double percentage, BigDecimal totalAmount) {}

    public LocalDate getLatestTransactionDate() {
        return create.select(max(TX_DATE)).from(TRANSACTION_ENTRY).fetchOne(max(TX_DATE));
    }

    public List<CategoryShareDto> getCategorySharesForDate(LocalDate date) {
        if (date == null) return List.of();
        return create.select(CAT_NAME, sum(TX_AMOUNT))
                .from(TRANSACTION_ENTRY)
                .join(CATEGORY).on(TX_CAT_ID.eq(CAT_ID))
                .where(TX_DATE.eq(date))
                .groupBy(CAT_NAME)
                .orderBy(sum(TX_AMOUNT).desc())
                .fetch(org.jooq.Records.mapping(CategoryShareDto::new));
    }

    public List<CategoryHistoricalShareDto> getHistoricalCategorySharePercentages() {
        List<DateSummaryDto> dates = getChronologicalDateSummaries(null);
        if (dates == null || dates.isEmpty()) {
            return List.of();
        }

        List<CategoryHistoricalShareDto> result = new java.util.ArrayList<>();
        for (DateSummaryDto d : dates) {
            BigDecimal totalNetWorth = d.totalAmount();
            if (totalNetWorth == null || totalNetWorth.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            List<CategoryShareDto> shares = getCategorySharesForDate(d.date());
            for (CategoryShareDto share : shares) {
                if (share.totalAmount() == null) continue;
                double pct = share.totalAmount().doubleValue() / totalNetWorth.doubleValue() * 100.0;
                result.add(new CategoryHistoricalShareDto(d.date(), share.categoryName(), Math.max(0.0, pct), share.totalAmount()));
            }
        }
        return result;
    }

    public BigDecimal getCurrentWealth(String username) {
        LocalDate latestDate = getLatestTransactionDate();
        if (latestDate == null) {
            return BigDecimal.ZERO;
        }
        var base = create.select(sum(TX_AMOUNT))
                .from(TRANSACTION_ENTRY)
                .join(APP_USER).on(TX_USER_ID.eq(USER_ID))
                .where(TX_DATE.eq(latestDate));
        var query = (username != null) ? base.and(USERNAME.eq(username)) : base;
        BigDecimal result = query.fetchOne(sum(TX_AMOUNT));
        return result != null ? result : BigDecimal.ZERO;
    }

    // Budget CSV Import & Metadata methods (UC-007)
    public ImportMetadataDto getLatestImportMetadata() {
        return create.select(META_FILENAME, META_TIMESTAMP)
                .from(IMPORT_METADATA)
                .where(META_ID.eq(1))
                .fetchOne(org.jooq.Records.mapping(ImportMetadataDto::new));
    }

    public void updateImportMetadata(String filename) {
        create.deleteFrom(IMPORT_METADATA).where(META_ID.eq(1)).execute();
        if (filename != null) {
            create.insertInto(IMPORT_METADATA, META_ID, META_FILENAME, META_TIMESTAMP)
                    .values(1, filename, java.time.LocalDateTime.now())
                    .execute();
        }
    }

    public void clearImportMetadataAndBudgetTransactions() {
        create.deleteFrom(IMPORT_METADATA).execute();
        create.deleteFrom(BUDGET_TRANSACTION).execute();
    }

    public Long getOrCreateBudgetCategory(String categoryName) {
        Long id = create.select(BUDGET_CAT_ID).from(BUDGET_CATEGORY).where(BUDGET_CAT_NAME.eq(categoryName)).fetchOne(BUDGET_CAT_ID);
        if (id != null) {
            return id;
        }
        create.insertInto(BUDGET_CATEGORY, BUDGET_CAT_NAME)
                .values(categoryName)
                .execute();
        return create.select(BUDGET_CAT_ID).from(BUDGET_CATEGORY).where(BUDGET_CAT_NAME.eq(categoryName)).fetchOne(BUDGET_CAT_ID);
    }

    public List<BudgetTransactionDto> getRecentBudgetTransactions(int limit) {
        return create.select(BTX_ID, BTX_DATE, BTX_TYPE, BTX_AMOUNT, BUDGET_CAT_NAME, USERNAME, BTX_DESC)
                .from(BUDGET_TRANSACTION)
                .join(BUDGET_CATEGORY).on(BTX_CAT_ID.eq(BUDGET_CAT_ID))
                .join(APP_USER).on(BTX_USER_ID.eq(USER_ID))
                .orderBy(BTX_DATE.desc(), BTX_ID.desc())
                .limit(limit)
                .fetch(org.jooq.Records.mapping(BudgetTransactionDto::new));
    }

    public record CsvImportResult(int importedCount, int skippedCount) {}

    public CsvImportResult importBudgetCsvRows(String filename, List<String[]> csvRows) {
        int imported = 0;
        int skipped = 0;

        for (String[] row : csvRows) {
            if (row.length < 6) continue;
            String dateStr = row[0].trim();
            String typeStr = row[1].trim();
            String amountStr = row[2].trim();
            String categoryStr = row[3].trim();
            String personStr = row[4].trim();
            String descStr = row[5].trim();

            if (dateStr.isEmpty() || amountStr.isEmpty() || categoryStr.isEmpty()) continue;

            LocalDate date;
            try {
                date = LocalDate.parse(dateStr, java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (Exception e) {
                continue;
            }

            BigDecimal amount;
            try {
                amount = new BigDecimal(amountStr.replace(",", ".")).setScale(2, java.math.RoundingMode.HALF_UP);
            } catch (Exception e) {
                continue;
            }

            Long userId = create.select(USER_ID).from(APP_USER).where(USERNAME.eq(personStr)).fetchOne(USER_ID);
            if (userId == null) {
                // Default to Jens if user not found
                userId = create.select(USER_ID).from(APP_USER).where(USERNAME.eq("Jens")).fetchOne(USER_ID);
            }

            Long categoryId = getOrCreateBudgetCategory(categoryStr);

            // Delta Check: Check if record already exists
            boolean exists = create.fetchExists(
                    create.selectOne()
                            .from(BUDGET_TRANSACTION)
                            .where(BTX_DATE.eq(date))
                            .and(BTX_USER_ID.eq(userId))
                            .and(BTX_CAT_ID.eq(categoryId))
                            .and(BTX_AMOUNT.eq(amount))
                            .and(BTX_DESC.eq(descStr))
            );

            if (exists) {
                skipped++;
            } else {
                create.insertInto(BUDGET_TRANSACTION, BTX_DATE, BTX_TYPE, BTX_AMOUNT, BTX_CAT_ID, BTX_USER_ID, BTX_DESC)
                        .values(date, typeStr, amount, categoryId, userId, descStr)
                        .execute();
                imported++;
            }
        }

        updateImportMetadata(filename);
        return new CsvImportResult(imported, skipped);
    }

    public record BudgetMonthlyCategorySumDto(String categoryName, java.time.YearMonth yearMonth, BigDecimal totalAmount) {}

    public List<BudgetMonthlyCategorySumDto> getBudgetMonthlyCategorySums() {
        var records = create.select(
                    BUDGET_CAT_NAME,
                    BTX_DATE,
                    BTX_AMOUNT
                )
                .from(BUDGET_TRANSACTION)
                .join(BUDGET_CATEGORY).on(BTX_CAT_ID.eq(BUDGET_CAT_ID))
                .fetch();

        Map<String, Map<java.time.YearMonth, BigDecimal>> aggregated = new java.util.HashMap<>();
        for (var r : records) {
            String cat = r.get(BUDGET_CAT_NAME);
            LocalDate date = r.get(BTX_DATE);
            BigDecimal amt = r.get(BTX_AMOUNT);
            if (cat == null || date == null || amt == null) continue;

            java.time.YearMonth ym = java.time.YearMonth.from(date);
            aggregated.computeIfAbsent(cat, k -> new java.util.HashMap<>())
                    .merge(ym, amt, BigDecimal::add);
        }

        List<BudgetMonthlyCategorySumDto> result = new java.util.ArrayList<>();
        for (var catEntry : aggregated.entrySet()) {
            String catName = catEntry.getKey();
            for (var ymEntry : catEntry.getValue().entrySet()) {
                result.add(new BudgetMonthlyCategorySumDto(catName, ymEntry.getKey(), ymEntry.getValue()));
            }
        }

        result.sort(java.util.Comparator.comparing(BudgetMonthlyCategorySumDto::categoryName)
                .thenComparing(BudgetMonthlyCategorySumDto::yearMonth));
        return result;
    }

    public List<BudgetTransactionDto> getBudgetTransactionsForCategoryAndMonth(String categoryName, java.time.YearMonth yearMonth) {
        if (categoryName == null || yearMonth == null) return List.of();
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return create.select(BTX_ID, BTX_DATE, BTX_TYPE, BTX_AMOUNT, BUDGET_CAT_NAME, USERNAME, BTX_DESC)
                .from(BUDGET_TRANSACTION)
                .join(BUDGET_CATEGORY).on(BTX_CAT_ID.eq(BUDGET_CAT_ID))
                .join(APP_USER).on(BTX_USER_ID.eq(USER_ID))
                .where(BUDGET_CAT_NAME.eq(categoryName))
                .and(BTX_DATE.between(startDate, endDate))
                .orderBy(BTX_DATE.desc(), BTX_ID.desc())
                .fetch(org.jooq.Records.mapping(BudgetTransactionDto::new));
    }

    public record WealthGrowthDecompositionDto(
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal totalWealthStart,
            BigDecimal totalWealthEnd,
            BigDecimal totalDelta,
            BigDecimal netBudgetSavings,
            BigDecimal investmentAppreciation
    ) {}

    public List<WealthGrowthDecompositionDto> getWealthGrowthDecomposition(String username) {
        List<DateSummaryDto> dates = getChronologicalDateSummaries(username);
        if (dates.size() < 2) {
            return List.of();
        }

        List<WealthGrowthDecompositionDto> result = new java.util.ArrayList<>();
        for (int i = 0; i < dates.size() - 1; i++) {
            LocalDate startDate = dates.get(i).date();
            LocalDate endDate = dates.get(i + 1).date();

            BigDecimal startWealth = dates.get(i).totalAmount();
            BigDecimal endWealth = dates.get(i + 1).totalAmount();
            BigDecimal totalDelta = endWealth.subtract(startWealth);

            // Fetch net budget income (income - expense) between (startDate, endDate]
            var btxRecords = create.select(BTX_TYPE, BTX_AMOUNT)
                    .from(BUDGET_TRANSACTION)
                    .where(BTX_DATE.gt(startDate))
                    .and(BTX_DATE.lessOrEqual(endDate))
                    .fetch();

            BigDecimal netSavings = BigDecimal.ZERO;
            for (var r : btxRecords) {
                String type = r.get(BTX_TYPE);
                BigDecimal amt = r.get(BTX_AMOUNT);
                if (amt == null) continue;
                if ("Einnahme".equalsIgnoreCase(type) || "Einnahmen".equalsIgnoreCase(type) || "Income".equalsIgnoreCase(type)) {
                    netSavings = netSavings.add(amt);
                } else {
                    netSavings = netSavings.subtract(amt);
                }
            }

            BigDecimal investmentAppreciation = totalDelta.subtract(netSavings);

            result.add(new WealthGrowthDecompositionDto(
                    startDate,
                    endDate,
                    startWealth,
                    endWealth,
                    totalDelta,
                    netSavings,
                    investmentAppreciation
            ));
        }

        return result;
    }
}
