package rs.aik.testautomation.AIKTestAutomation.Action;

import rs.aik.testautomation.AIKTestAutomation.Data.DBCore;
import rs.aik.testautomation.AIKTestAutomation.Data.DBDefault;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;
import rs.aik.testautomation.AIKTestAutomation.Data.DBReport;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Queries {
    public static String accountNumber = "";
    public static List<String> products;

    private static final int PAGINATION_COUNT = 10;     // pagination to show orders per page

    /**
     * Returns Mobile phone number for entered SSN
     * @param SSN Social identity number
     * @return String
     * @throws SQLException
     */
    public static String getMobilePhoneNumber(String SSN) throws SQLException {
        String sqlText = "SELECT d.MobileNumber \n" +
                "FROM [iBankingRetail].[dbo].[Individual] i (nolock)\n" +
                "JOIN [iBankingRetail].[dbo].[IndividualDetails] d (nolock) ON i.IndividualId = d.IndividualID \n" +
                "WHERE i.SocialIdentityNumber = ? ";

        String res = DBDefault.GetString(sqlText, SSN);

        return res;
    }


    /**
     * Returns OTP code for Social Security Number as parameter
     * @param SSN String Social Security Number
     * @return String
     * @throws SQLException
     */
    public static String getOTPCodeFromSSN(String SSN) throws SQLException {
        String sqlText = "SELECT t.TokenSerialNumber *\n" +
                "FROM [SMSOTP].[dbo].[OtpAuthentication] a\n" +
                "JOIN [SMSOTP].[dbo].[OtpCode] c ON c.OtpAuthenticationID = a.OtpAuthenticationID\n" +
                "JOIN [SecurityServerRetail].[dbo].[VASCOIK_Token] t\n" +
                "ON (t.PrefixSerialNumber COLLATE Latin1_General_CI_AS) + (t.TokenSerialNumber COLLATE Latin1_General_CI_AS) = (a.PhoneNumber COLLATE Latin1_General_CI_AS)\n" +
                "where c.Status = 0\n" +
                "AND t.State = 'OK'\n" +
                "AND t.VASCOIKUserID = ? ";

        String res = DBDefault.GetString(sqlText, SSN);

        return res;
    }

    /**
     * Check if exists order in database for OrderNumber in parameter reference and account in parameter accountNumber
     * @param reference Order number in core
     * @param accountNumber Account number
     * @return true or false
     * @throws SQLException
     */
    public static boolean existsOrderForAccount(String reference, String accountNumber) throws SQLException {
        boolean res = false;
        String sqlText = "SELECT  count(*)\n" +
                "FROM [iBankingRetailUpgradedToV4].[dbo].[Order] o\n" +
                "WHERE o.OrderNumberCore = ? \n" +
                "and ((o.DebitAccount = ?) " +
                " or (o.CreditAccount = ?)) " +
                "";

        int a = DBDefault.GetInt(sqlText, reference, accountNumber, accountNumber);
        if (a > 0)
            res = true;
        return res;
    }

    public static String getCardPaymentDataDigital(String SSN) throws SQLException {
        String res = "";
        String sqlText = "SELECT TOP (1) [OrderID] \n" +
                "      ,[Status] \n" +
                "      ,[ReceiveDate] \n" +
                "     , DateDIFF(Minute, [ReceiveDate], GetDate()) \n" +
                "  FROM [iBankingRetailUpgradedToV4].[dbo].[Order] \n" +
                "  WHERE DebtorIdentityNumber = ? \n" +
                "  AND DateDIFF(Minute, [ReceiveDate], GetDate()) < 5 " +
                "";

        ArrayList<HashMap<String, Object>> cardsResult = DBDefault.getObject(sqlText, SSN);
        if (cardsResult.get(0).get("Status").equals("OK"))
            res = cardsResult.get(0).get("OrderID").toString();
        return res;
    }

    /**
     * Returns value date for last credit transaction for account in parameter accountNumber
     * @param accountNumber Credit Account Number
     * @return String date in format DD.MM.YYYY
     * @throws SQLException
     */
    public static String getLastCreditTransactionValueDate(String accountNumber) throws SQLException {
        String sqlText = "SELECT ISNULL((" +
                "SELECT TOP (1) convert(varchar(10),o.ValueDate, 104)\n" +
                "FROM [iBankingRetailUpgradedToV4].[dbo].[Order] o \n" +
                "WHERE o.CreditAccount = ? \n" +
                "ORDER BY o.ValueDate desc \n" +
                "), 'NONE' )";

        String res = DBDefault.GetString(sqlText, accountNumber);
        if(res.equals(null))
            res = "";

        return res;
    }

    /**
     * Returns value date for last debit transaction for account in parameter accountNumber
     * @param accountNumber Debit Account Number
     * @return String date in format DD.MM.YYYY
     * @throws SQLException
     */
    public static String getLastDebitTransactionValueDate(String accountNumber) throws SQLException {
        String sqlText = "SELECT ISNULL((" +
                "SELECT TOP (1) convert(varchar(10),o.ValueDate, 104)\n" +
                "FROM [iBankingRetailUpgradedToV4].[dbo].[Order] o \n" +
                "WHERE o.DebitAccount = ? \n" +
                "ORDER BY o.ValueDate desc \n" +
                "), 'NONE' )";

        String res = DBDefault.GetString(sqlText, accountNumber);
        if(res.equals(null))
            res = "";

        return res;
    }

    public static boolean existsAccountForSSNAndAccountNumber(String SSN, String accountNumber) throws SQLException {
        boolean res = false;
        accountNumber = "%".concat(accountNumber).concat("%");
        String sqlText = "SELECT count (*)\n" +
                "FROM (\n" +
                "\tSELECT a.AccountID\n" +
                "\tFROM [iBankingRetailUpgradedToV4].[dbo].[Individual] i (nolock)\n" +
                "\tJOIN [iBankingRetailUpgradedToV4].dbo.[IndividualDetail] d (nolock) ON i.IndividualId = d.IndividualID \n" +
                "\tJOIN [iBankingRetailUpgradedToV4].dbo.UserProduct up (nolock) ON up.UserID = i.IndividualId\n" +
                "\tJOIN [iBankingRetailUpgradedToV4].dbo.Product p (nolock) ON p.ProductID = up.ProductID\n" +
                "\tJOIN [iBankingRetailUpgradedToV4].[dbo].[SubsystemProduct] sp (nolock) ON sp.SubsystemProductID = p.SubsystemProductID\n" +
                "\tJOIN [iBankingRetailUpgradedToV4].dbo.Account a (nolock) ON a.AccountID = p.ProductID\n" +
                "\tWHERE i.SocialIdentityNumber = ? \n" +
                "\tand a.AccountNumber like ? \n" +
                "\n" +
                "\tUNION ALL\n" +
                "\n" +
                "\tSELECT a.AccountID\n" +
                "\tFROM [iBankingRetail].[dbo].[Individual] i (nolock)\n" +
                "\tJOIN [iBankingRetail].dbo.[IndividualDetails] d (nolock) ON i.IndividualId = d.IndividualID \n" +
                "\tJOIN [iBankingRetail].dbo.UserProduct up (nolock) ON up.UserID = i.IndividualId\n" +
                "\tJOIN [iBankingRetail].dbo.Product p (nolock) ON p.ProductID = up.ProductID\n" +
                "\tJOIN [iBankingRetail].[dbo].[SubsystemProduct] sp (nolock) ON sp.SubsystemProductID = p.SubsystemProductID\n" +
                "\tJOIN [iBankingRetail].dbo.Account a (nolock) ON a.AccountID = p.ProductID\n" +
                "\tWHERE i.SocialIdentityNumber = ? \n" +
                "\tand a.AccountNumber like ? \n" +
                ") b";

        int a = DBDefault.GetInt(sqlText, SSN, accountNumber, SSN, accountNumber);
        if (a > 0)
            res = true;
        return res;
    }

    /**
     * Find all cards for a given customer ssn
     *
     * @param SSN the ssn of the i-banking user
     * @return all user cards
     * @throws SQLException
     */
    public static boolean existsCardForSSNAndCardNumber(String SSN, String cardNumber) throws SQLException {
        boolean res = false;
        String sqlText = "EXEC [dbo].[spc_getAllRetailCoreCards] @customerNumber = ?";
        try {
            ResultSet cards = DBCore.dbQuery(sqlText, SSN);
            if (cards == null) {
                throw new Exception("Got empty result set while calling for card details!");
            }
            while (cards.next()) {
                if (cards.getString("CardNumber").trim().equals(cardNumber)) {
                    res = true;
                    break;
                }
            }
        } catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
        //TODO create query to check if card exists
        return res;
    }

    /**
     * Find all accounts for a given customer ssn
     * @param SSN the ssn of the i-banking user
     * @return all user accounts
     * @throws SQLException
     */
    public static ResultSet getAccountsForOnlineView(String SSN) throws SQLException {
        try {
            String proc_sql = "EXEC [dbo].[spc_getAccountsForOnlineView] @ssn = ?";
            ResultSet accountsResult = DBCore.dbQuery(proc_sql, SSN);

            if (accountsResult == null) {
                throw new Exception("Got empty result set while calling for accounts!");
            }
            return accountsResult;
        } catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

    /**
     * Find account details for a given account id(nrp)
     * @param nrp of the customer account
     * @return the details of these accounts
     * @throws SQLException
     */
    public static ResultSet getAccountsDetails(String nrp) throws SQLException {
        try {
            String proc_sql = "EXEC [dbo].[spc_getAccountDetails] @idAccount = ?";
            ResultSet accountsResult = DBCore.dbQuery(proc_sql, nrp);

            if (accountsResult == null) {
                throw new Exception("Got empty result set while calling for accounts details!");
            }
            return accountsResult;
        }catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

    /**
     * Find account details for a given account id(nrp)
     * @param nrp of the customer account
     * @return the details of these accounts
     * @throws SQLException
     */
    public static ArrayList<HashMap<String, Object>> getAccountsDetailsHash(String nrp) throws SQLException {
        try {
            String proc_sql = "EXEC [dbo].[spc_getAccountDetails] @idAccount = ?";
            ArrayList<HashMap<String, Object>> accountsResult = DBCore.getObject(proc_sql, nrp);

            if (accountsResult == null) {
                throw new Exception("Got empty result set while calling for accounts details!");
            }
            return accountsResult;
        }catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

    /**
     * Find the orders of an account
     * @param nrp of the account
     * @return the orders of the account
     * @throws SQLException
     */
    public static ResultSet getOrdersForAccount(String nrp) throws SQLException {
        try {
            String proc_sql = "EXEC [dbo].[spcGetTurnover]\n" +
                    "\t\t@idAccount = ?,\n" +
                    "\t\t@itemNumber = ?\n";
            ResultSet accountsResult = DBCore.dbQuery(proc_sql, nrp, PAGINATION_COUNT);

            if (accountsResult == null) {
                throw new Exception("Got empty result set while calling for account orders details!");
            }
            return accountsResult;
        } catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

    /**
     * Find the orders of an account
     * @param nrp of the account
     * @return the orders of the account
     * @throws SQLException
     */
    public static ResultSet getOrdersForAccountCustom(String nrp, int pageSize) throws SQLException {
        try {
            String proc_sql = "EXEC [dbo].[spcGetTurnover]\n" +
                    "\t\t@idAccount = ?,\n" +
                    "\t\t@itemNumber = ?\n";
            ResultSet accountsResult = DBCore.dbQuery(proc_sql, nrp, pageSize);

            if (accountsResult == null) {
                throw new Exception("Got empty result set while calling for account orders details!");
            }
            return accountsResult;
        } catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

    /**
     * @param nrp of the account
     * @param fromDate for from date
     * @param toDate for to date
     * @return the orders filtered from @fromDate to @toDate
     * @throws SQLException
     */
    public static ResultSet getOrdersForAccount(String nrp, String fromDate, String toDate) throws SQLException {
        try {
            String proc_sql = "EXEC\t[dbo].[spcGetTurnover]\n" +
                    "\t\t@idAccount = ?,\n" +
                    "\t\t@fromDate = ?,\n" +
                    "\t\t@toDate = ?,\n" +
                    "\t\t@itemNumber = ?";
            ResultSet accountsResult = DBCore.dbQuery(proc_sql, nrp, fromDate, toDate, PAGINATION_COUNT);

            if (accountsResult == null) {
                throw new Exception("Got empty result set while calling for account orders details with filter amount!");
            }
            return accountsResult;
        } catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

    /**
     * @param nrp of the account
     * @param fromAmount for lower-bound amount for filter
     * @param toAmount for upperbound amount for filter
     * @return the orders filtered from @fromAmount to @toAmount
     * @throws SQLException
     */
    public static ResultSet getOrdersForAccount(String nrp, long fromAmount, long toAmount) throws SQLException {
        try {
            String proc_sql = "EXEC\t[dbo].[spcGetTurnover]\n" +
                    "\t\t@idAccount = ?,\n" +
                    "\t\t@fromAmount = ?,\n" +
                    "\t\t@toAmount = ?,\n" +
                    "\t\t@itemNumber = ?";
            ResultSet accountsResult = DBCore.dbQuery(proc_sql, nrp, fromAmount, toAmount, PAGINATION_COUNT);

            if (accountsResult == null) {
                throw new Exception("Got empty result set while calling for account orders details with filter amount!");
            }
            return accountsResult;
        } catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

    /**
     * @param nrp of the account
     * @param debitOrCredit to tell if only get debit (1) or credit (0) orders
     * @return the orders filtered on debit or credit @debitOrCredit
     * @throws SQLException
     */
    public static List<Map<String, String>> getOrdersForAccount(String nrp, int debitOrCredit) throws SQLException {
        try {
            ResultSet data = getOrdersForAccountCustom(nrp, 500);
            List<Map<String, String>> orderRows = new ArrayList<Map<String, String>>();
            ResultSetMetaData meta = data.getMetaData();
            while (data.next()) {
                Map map = new HashMap();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String key = meta.getColumnName(i);
                    String value = data.getString(key);
                    map.put(key, value);
                }
                orderRows.add(map);
            }

            // why use stream? because in the future this can get heave and you can do parallel streaming computing
            // but even like this it has the same complexity as if you would do it in a while loop
            if(debitOrCredit < 2) {
                return orderRows.stream()
                        .filter(row -> row.get("DebitOrCredit").equals((debitOrCredit == 1) ? "1" : "0"))
                        .collect(Collectors.toList());
            }
            else
                return orderRows;
        }catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

    /**
     * @param ssn of the i-banking user
     * @return all user cards(including debit or credit)
     * @throws SQLException
     */
    public static ArrayList<HashMap<String, Object>> getAllRetailCoreCards(String ssn) throws SQLException {
        try {
            String proc_sql = "EXEC [dbo].[spc_getAllRetailCoreCards]\n" +
                    "\t\t@customerNumber = ?";

            ArrayList<HashMap<String, Object>> cardsResult = DBCore.getObject(proc_sql, ssn);

            if (cardsResult == null) {
                throw new Exception("Got empty result set while getting all user core retail cards!");
            }
            return cardsResult;
        } catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

    public static ArrayList<HashMap<String, Object>> GetCardTurnover(String cardId, String dateFrom, String dateTo,String ssn) throws SQLException {
        try {
            String proc_sql = "EXEC\t[dbo].[spc_GetCardTransaction_ods_lcms]\n" +
                    "\t\t@cardId = ?,\n" +
                    "\t\t@startDate = ?,\n" +
                    "\t\t@endDate = ?,\n" +
                    "\t\t@amountFrom = NULL,\n" +
                    "\t\t@amountTo = NULL,\n" +
                    "\t\t@debitCredit = NULL,\n" +
                    "\t\t@transactionType = NULL,\n" +
                    "\t\t@cardholdername = NULL,\n" +
                    "\t\t@ssn = ?\n";

            ArrayList<HashMap<String, Object>> cardsResult = DBCore.getObject(proc_sql, cardId, dateFrom, dateTo, ssn);

            if (cardsResult == null) {
                throw new Exception("Got empty result set while getting all user core retail cards!");
            }
            return cardsResult;
        } catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

    public static ArrayList<HashMap<String, Object>> GetCardTurnover2(String cardId, String dateFrom, String dateTo, String amountFrom, String amountTo, String ssn) throws SQLException {
        try {
            //int amF = Integer.parseInt(amountFrom);
            //int amT = Integer.parseInt(amountTo);
            String proc_sql = "EXEC [dbo].[spc_GetCardTransaction_ods_lcms] " +
                    " @cardId = ?, \n" +
                    " @startDate = ? , \n" +
                    " @endDate = ? , \n" +
                    " @amountFrom = ? , \n" +
                    " @amountTo = ? , \n" +
                    " @debitCredit = NULL,\n" +
                    " @transactionType = NULL,\n" +
                    " @cardholdername = NULL,\n" +
                    " @ssn = ? \n";

            ArrayList<HashMap<String, Object>> cardsResult = DBCore.getObject(proc_sql, cardId, dateFrom, dateTo, amountFrom, amountTo, ssn);

            if (cardsResult == null) {
                throw new Exception("Got empty result set while getting all user core retail cards!");
            }
            return cardsResult;
        } catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }


    /**
     * @param ssn of the i-banking user
     * @param cardId id of the card
     * @return the required card
     * @throws SQLException
     */
    public static ArrayList<HashMap<String, Object>> getCardDetails(String ssn, String cardId) throws SQLException {
        try {
            String proc_sql = "EXEC dbo].[spc_getCardDetailsAndBalance]\n" +
                    "\t\t@idcard = ?,\n" +
                    "\t\t@ssn = ?";

            ArrayList<HashMap<String, Object>> cardDetailsResult = DBCore.getObject(proc_sql, cardId, ssn);

            if (cardDetailsResult == null) {
                throw new Exception("Got empty result set while getting all user core retail card details!");
            }
            return cardDetailsResult;
        } catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

    /**
     * @param ssn of the i-banking user
     * @param cardId id of the card
     * @param fromDate the starting date, in format "01 Jan 2019", is required in procedure
     * @param toDate  the ending date, is required in procedure
     * @return all card transactions from core with the specified date rage
     * @throws SQLException
     */
    public static ArrayList<HashMap<String, Object>> getCardTransactions(String ssn, String cardId, String fromDate, String toDate) throws SQLException {
        try {
            String proc_sql = "EXEC [dbo].[spc_GetCardTransaction_ods_lcms]\n" +
                    "\t\t@cardId = ?,\n" +
                    "\t\t@startDate = ?,\n" +
                    "\t\t@endDate = ?,\n" +
                    "\t\t@ssn = ?";

            ArrayList<HashMap<String, Object>> cardTransactionsDateFilter = DBCore.getObject(proc_sql, cardId, fromDate, toDate, ssn);

            if (cardTransactionsDateFilter == null) {
                throw new Exception("Got empty result set while getting all user core retail card transactions with given date filter!");
            }
            return cardTransactionsDateFilter;
        } catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

    /**
     * @param ssn of the i-banking user
     * @param cardId id of the card
     * @param fromDate the starting date, in format "01 Jan 2019"
     * @param toDate  the ending date
     * @param fromAmount the starting amount to filter
     * @param toAmount the end amount to filter
     * @return all card transactions from core with specified date range and amount range
     * @throws SQLException
     */
    public static ArrayList<HashMap<String, Object>> getCardTransactions(String ssn, String cardId, String fromDate, String toDate, long fromAmount, long toAmount) throws SQLException {
        try {
            String proc_sql = "EXEC [dbo].[spc_GetCardTransaction_ods_lcms]\n" +
                    "\t\t@cardId = ?,\n" +
                    "\t\t@startDate = ?,\n" +
                    "\t\t@endDate = ?,\n" +
                    "\t\t@amountFrom = ?,\n" +
                    "\t\t@amountTo = ?,\n" +
                    "\t\t@ssn = ?";

            ArrayList<HashMap<String, Object>> cardDetailsResult = DBCore.getObject(proc_sql, cardId, fromDate, toDate, fromAmount, toAmount, ssn);

            if (cardDetailsResult == null) {
                throw new Exception("Got empty result set while getting all user core retail card transactions with date and amount filter!");
            }
            return cardDetailsResult;
        } catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

    public static boolean existsCardForSSNAndCardNumberCardType(String SSN, String cardNumber, String cardTypeStyle) throws SQLException{
        boolean res = false;
        String cardType = "";
        switch (cardTypeStyle){
            case "img-flag CR0001" :
                cardType = "1";
                break;
            default:
                cardType = "0";
                break;
        }
        String sqlText = "";
        //TODO create query to check if card exists
        /*
        int a = DataManager.GetIntDB(sqlText, SSN, cardNumber, cardType);
        if (a > 0)
            res = true;

         */
        return res;
    }

    public static boolean existsCardForSSNAndCardNumberCardCurrency(String SSN, String cardNumber, String cardCurrency) throws SQLException {
        boolean res = false;
        String sqlText = "";
        //TODO create query to check if card exists
        /*
        int a = DataManager.GetIntDB(sqlText, SSN, cardNumber, cardCurrency);
        if (a > 0)
            res = true;

         */
        return res;
    }

    public static boolean existsCardForSSNAndCardNumberCardRemainingBalance(String SSN, String cardNumber, String remainingBalance) throws SQLException {
        boolean res = false;
        String sqlText = "";
        //TODO create query to check if card exists
        /*
        int a = DataManager.GetIntDB(sqlText, SSN, cardNumber, remainingBalance);
        if (a > 0)
            res = true;

         */
        return res;
    }

    public static boolean existsCardForSSNAndCardNumberCardStatus(String SSN, String cardNumber, String status) throws SQLException {
        boolean res = false;
        String sqlText = "";
        //TODO create query to check if card exists
        /*
        int a = DataManager.GetIntDB(sqlText, SSN, cardNumber, status);
        if (a > 0)
            res = true;

         */
        return res;
    }

    public static boolean existsCardForSSNAndCardNumberCardCategory(String SSN, String cardNumber, String category) throws SQLException {
        boolean res = false;
        String sqlText = "";
        //TODO create query to check if card exists
        /*
        int a = DataManager.GetIntDB(sqlText, SSN, cardNumber, category);
        if (a > 0)
            res = true;

         */
        return res;
    }

    public static String GetAccountType(int ProductSort) throws SQLException {
        String sqlText = "SELECT a.ProductDescription\n" +
                "FROM [OnlineDataStore_A5].[dbo].[ProductType] a\n" +
                "WHERE a.ProductSort = ? ";

        String res = DBCore.GetString(sqlText, ProductSort);

        if(res.equals(null))
            res = "";
        if(res.equals("Currenta Account"))
            res = "Current Account";
        return  res.toUpperCase();
    }

    public static ArrayList<HashMap<String, Object>> getCardDetailsForName(String ssn, String cardName) throws SQLException {
        List<HashMap<String, Object>> cardMaster = getAllRetailCoreCards(ssn);
        cardMaster = cardMaster.stream()
                .filter(row -> row.get("CardName").equals(cardName))
                .collect(Collectors.toList());
        String ID = cardMaster.get(0).get("CardID").toString();
        return getCardDetails(ssn, ID);
    }

    public static String GetCardID(String cardName, String ssn) throws SQLException {
        List<HashMap<String, Object>> cardMaster = getAllRetailCoreCards(ssn);
        cardMaster = cardMaster.stream()
                .filter(row -> row.get("CardNumber").equals(cardName))
                .collect(Collectors.toList());
        String ID = cardMaster.get(0).get("Id_CardReference").toString();
        return ID;
    }

    public static String getCardPaymentDataLuca(String OrderID) throws SQLException {
        try {
            String sqlText = "SELECT TOP (1000) [ID_Payment]\n" +
                    "      ,[LucaPaymentReference]\n" +
                    "      ,[MidasPaymentReference]\n" +
                    "      ,[Id_StatusPaymentArgos]\n" +
                    "  FROM [LUKA].[dbo].[T07_Payment]\n" +
                    "  WHERE [LucaPaymentReference] = ? ";
            List<HashMap<String, Object>> result = DBCore.getObject(sqlText, OrderID);

            if (result == null) {
                throw new Exception("Got empty result set while calling for accounts!");
            }
            return result.get(0).get("MidasPaymentReference").toString();
        } catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

    public static void writeToTestRailImportResultsTable(String testID, String priority, String result, String platform, String dateOfExecution) throws SQLException {
        //String _prefix = dtcShema;
        String _prefix = "";
        String SQLText = "INSERT INTO " + _prefix + "TestRailImportResults (CaseID, Priority, Result, Platform, DateOfExecution)"
                + "VALUES (?, ?, ?, ?, cast(? AS DATETIME)) ";
        boolean rs = DBReport.dbExecuteSQL(SQLText, testID, priority, result, platform, dateOfExecution);
    }

    /**
     *
     * @param description
     * @return boolean whether it finds records or no
     * @throws SQLException
     */
    public static boolean getWithinAccountPaymentDataLuca(String description) throws SQLException {
        boolean res = false;
        String sqlText = "SELECT count(*) as result\n" +
                "  FROM [LUKA].[dbo].[T07_Payment]\n" +
                " Where [Description] like ?";
        try {
            Object result =  DBCore.getScalar(sqlText, description);
            return ((Integer) result) == 1;
        } catch (Exception exp) {
            Log.error(exp.getMessage());
            throw new SQLException(exp.getMessage());
        }
    }

}