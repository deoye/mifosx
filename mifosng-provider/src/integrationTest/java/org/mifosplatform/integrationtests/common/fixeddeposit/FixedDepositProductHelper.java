package org.mifosplatform.integrationtests.common.fixeddeposit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mifosplatform.integrationtests.common.CommonConstants;
import org.mifosplatform.integrationtests.common.Utils;
import org.mifosplatform.integrationtests.common.accounting.Account;
import org.mifosplatform.integrationtests.common.accounting.Account.AccountType;

import com.google.gson.Gson;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

@SuppressWarnings({ "unused", "rawtypes" })
public class FixedDepositProductHelper {

    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;

    public FixedDepositProductHelper(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        this.requestSpec = requestSpec;
        this.responseSpec = responseSpec;
    }

    private static final String FIXED_DEPOSIT_PRODUCT_URL = "/mifosng-provider/api/v1/fixeddepositproducts";
    private static final String INTEREST_CHART_URL = "/mifosng-provider/api/v1/interestratecharts";
    private static final String CREATE_FIXED_DEPOSIT_PRODUCT_URL = FIXED_DEPOSIT_PRODUCT_URL + "?" + Utils.TENANT_IDENTIFIER;

    private static final String LOCALE = "en_GB";
    private static final String DIGITS_AFTER_DECIMAL = "4";
    private static final String IN_MULTIPLES_OF = "100";
    private static final String USD = "USD";
    private static final String DAYS = "0";
    private static final String WEEKS = "1";
    private static final String MONTHS = "2";
    private static final String YEARS = "3";
    private static final String DAILY = "1";
    private static final String MONTHLY = "4";
    private static final String QUARTERLY = "5";
    private static final String ANNUALLY = "7";
    private static final String INTEREST_CALCULATION_USING_DAILY_BALANCE = "1";
    private static final String INTEREST_CALCULATION_USING_AVERAGE_DAILY_BALANCE = "2";
    private static final String DAYS_360 = "360";
    private static final String DAYS_365 = "365";
    private static final String NONE = "1";
    private static final String CASH_BASED = "2";
    private static final String WHOLE_TERM = "1";
    private static final String TILL_PREMATURE_WITHDRAWAL = "2";

    private String name = Utils.randomNameGenerator("FIXED_DEPOSIT_PRODUCT_", 6);
    private String shortName = Utils.randomNameGenerator("", 4);
    private String description = Utils.randomNameGenerator("", 20);
    private String interestCompoundingPeriodType = MONTHLY;
    private String interestPostingPeriodType = MONTHLY;
    private String interestCalculationType = INTEREST_CALCULATION_USING_DAILY_BALANCE;
    private String accountingRule = NONE;
    private String lockinPeriodFrequency = "6";
    private String lockingPeriodFrequencyType = MONTHS;
    private String minDepositTerm = "6";
    private String minDepositTermTypeId = MONTHS;
    private String maxDepositTerm = "10";
    private String maxDepositTermTypeId = YEARS;
    private String inMultiplesOfDepositTerm = "2";
    private String inMultiplesOfDepositTermTypeId = MONTHS;
    private String preClosurePenalInterest = "2";
    private String preClosurePenalInterestOnTypeId = WHOLE_TERM;
    private final boolean preClosurePenalApplicable = true;
    private final String currencyCode = USD;
    private final String interestCalculationDaysInYearType = DAYS_365;
    private Account[] accountList = null;

    public String build(final String validFrom, final String validTo) {
        final HashMap<String, Object> map = new HashMap<String, Object>();

        List<HashMap<String, String>> chartSlabs = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> chartSlabsMap1 = new HashMap<String, String>();
        chartSlabsMap1.put("description", "First");
        chartSlabsMap1.put("periodType", MONTHS);
        chartSlabsMap1.put("fromPeriod", "1");
        chartSlabsMap1.put("toPeriod", "6");
        chartSlabsMap1.put("annualInterestRate", "5");
        chartSlabsMap1.put("locale", LOCALE);
        chartSlabs.add(0, chartSlabsMap1);

        HashMap<String, String> chartSlabsMap2 = new HashMap<String, String>();
        chartSlabsMap2.put("description", "Second");
        chartSlabsMap2.put("periodType", MONTHS);
        chartSlabsMap2.put("fromPeriod", "7");
        chartSlabsMap2.put("toPeriod", "12");
        chartSlabsMap2.put("annualInterestRate", "6");
        chartSlabsMap2.put("locale", LOCALE);
        chartSlabs.add(1, chartSlabsMap2);

        HashMap<String, String> chartSlabsMap3 = new HashMap<String, String>();
        chartSlabsMap3.put("description", "Third");
        chartSlabsMap3.put("periodType", MONTHS);
        chartSlabsMap3.put("fromPeriod", "13");
        chartSlabsMap3.put("toPeriod", "18");
        chartSlabsMap3.put("annualInterestRate", "7");
        chartSlabsMap3.put("locale", LOCALE);
        chartSlabs.add(2, chartSlabsMap3);

        HashMap<String, String> chartSlabsMap4 = new HashMap<String, String>();
        chartSlabsMap4.put("description", "Fourth");
        chartSlabsMap4.put("periodType", MONTHS);
        chartSlabsMap4.put("fromPeriod", "19");
        chartSlabsMap4.put("toPeriod", "24");
        chartSlabsMap4.put("annualInterestRate", "8");
        chartSlabsMap4.put("locale", LOCALE);
        chartSlabs.add(3, chartSlabsMap4);

        List<HashMap<String, Object>> charts = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> chartsMap = new HashMap<String, Object>();
        chartsMap.put("fromDate", validFrom);
        chartsMap.put("endDate", validTo);
        chartsMap.put("dateFormat", "dd MMMM yyyy");
        chartsMap.put("locale", LOCALE);
        chartsMap.put("chartSlabs", chartSlabs);
        charts.add(chartsMap);

        map.put("charts", charts);
        map.put("name", this.name);
        map.put("shortName", this.shortName);
        map.put("description", this.description);
        map.put("currencyCode", this.currencyCode);
        map.put("interestCalculationDaysInYearType", this.interestCalculationDaysInYearType);
        map.put("locale", LOCALE);
        map.put("digitsAfterDecimal", DIGITS_AFTER_DECIMAL);
        map.put("inMultiplesOf", IN_MULTIPLES_OF);
        map.put("interestCalculationType", this.interestCalculationType);
        map.put("interestCompoundingPeriodType", this.interestCompoundingPeriodType);
        map.put("interestPostingPeriodType", this.interestPostingPeriodType);
        map.put("accountingRule", this.accountingRule);
        map.put("lockinPeriodFrequency", this.lockinPeriodFrequency);
        map.put("lockinPeriodFrequencyType", this.lockingPeriodFrequencyType);
        map.put("preClosurePenalApplicable", "true");
        map.put("minDepositTermTypeId", this.minDepositTermTypeId);
        map.put("minDepositTerm", this.minDepositTerm);
        map.put("maxDepositTermTypeId", this.maxDepositTermTypeId);
        map.put("maxDepositTerm", this.maxDepositTerm);
        map.put("preClosurePenalApplicable", this.preClosurePenalApplicable);
        map.put("inMultiplesOfDepositTerm", this.inMultiplesOfDepositTerm);
        map.put("inMultiplesOfDepositTermTypeId", this.inMultiplesOfDepositTermTypeId);
        map.put("preClosurePenalInterest", this.preClosurePenalInterest);
        map.put("preClosurePenalInterestOnTypeId", this.preClosurePenalInterestOnTypeId);

        if (this.accountingRule.equals(CASH_BASED)) {
            map.putAll(getAccountMappingForCashBased());
        }

        String FixedDepositProductCreateJson = new Gson().toJson(map);
        System.out.println(FixedDepositProductCreateJson);
        return FixedDepositProductCreateJson;
    }

    public FixedDepositProductHelper withAccountingRuleAsNone() {
        this.accountingRule = NONE;
        return this;
    }

    public FixedDepositProductHelper withAccountingRuleAsCashBased(final Account[] account_list) {
        this.accountingRule = CASH_BASED;
        this.accountList = account_list;
        return this;
    }

    private Map<String, String> getAccountMappingForCashBased() {
        final Map<String, String> map = new HashMap<String, String>();
        if (accountList != null) {
            for (int i = 0; i < this.accountList.length; i++) {
                if (this.accountList[i].getAccountType().equals(Account.AccountType.ASSET)) {
                    final String ID = this.accountList[i].getAccountID().toString();
                    map.put("savingsReferenceAccountId", ID);
                }
                if (this.accountList[i].getAccountType().equals(Account.AccountType.LIABILITY)) {
                    final String ID = this.accountList[i].getAccountID().toString();
                    map.put("savingsControlAccountId", ID);
                    map.put("transfersInSuspenseAccountId", ID);
                }
                if (this.accountList[i].getAccountType().equals(Account.AccountType.EXPENSE)) {
                    final String ID = this.accountList[i].getAccountID().toString();
                    map.put("interestOnSavingsAccountId", ID);
                }
                if (this.accountList[i].getAccountType().equals(Account.AccountType.INCOME)) {
                    final String ID = this.accountList[i].getAccountID().toString();
                    map.put("incomeFromFeeAccountId", ID);
                    map.put("incomeFromPenaltyAccountId", ID);
                }
            }
        }
        return map;
    }

    public static Integer createFixedDepositProduct(final String fixedDepositProductCreateJson, final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec) {
        System.out.println("--------------------- CREATING FIXED DEPOSIT PRODUCT ------------------------");
        return Utils.performServerPost(requestSpec, responseSpec, CREATE_FIXED_DEPOSIT_PRODUCT_URL, fixedDepositProductCreateJson,
                CommonConstants.RESPONSE_RESOURCE_ID);
    }

    public static ArrayList retrieveAllFixedDepositProducts(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        System.out.println("-------------------- RETRIEVING ALL FIXED DEPOSIT PRODUCTS ---------------------");
        final ArrayList response = Utils.performServerGet(requestSpec, responseSpec, FIXED_DEPOSIT_PRODUCT_URL + "?"
                + Utils.TENANT_IDENTIFIER, "");
        return response;
    }

    public static HashMap retrieveFixedDepositProductById(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final String productId) {
        System.out.println("------------------------ RETRIEVING FIXED DEPOSIT PRODUCT BY ID ------------------------");
        final String GET_FD_PRODUCT_BY_ID_URL = FIXED_DEPOSIT_PRODUCT_URL + "/" + productId + "?" + Utils.TENANT_IDENTIFIER;
        final HashMap response = Utils.performServerGet(requestSpec, responseSpec, GET_FD_PRODUCT_BY_ID_URL, "");
        return response;
    }

    public static ArrayList getInterestRateChartSlabsByProductId(final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec, final Integer productId) {
        System.out.println("-------------------- RETRIEVE INTEREST CHART BY PRODUCT ID ---------------------");
        final ArrayList response = Utils.performServerGet(requestSpec, responseSpec, INTEREST_CHART_URL + "?productId=" + productId, "chartSlabs");
        return response;
    }

}