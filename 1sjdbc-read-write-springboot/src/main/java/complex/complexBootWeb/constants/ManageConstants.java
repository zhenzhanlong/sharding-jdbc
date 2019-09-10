package complex.complexBootWeb.constants;

import java.math.BigDecimal;

public class ManageConstants {

	private ManageConstants() {
	}

	/*** 转账人员信息导入文件路径 ****/
	public static final String PERSONAL_TRANSFER_IMPORT_FILE_PATH = "config/import/personal_transfer.properties";

	/*** 资金阈值数据 ***/
	public static final Double MONEY = (double) 50000;

	public static final BigDecimal BIGDECIMAL_MONEY_50000 = BigDecimal.valueOf(50000);
	/*** 最小值 资金阈值数据 ***/
	public static final Double MIN_MONEY = (double) 9000;
	/*** 最大值 资金阈值数据 ***/
	public static final Double MAX_MONEY = (double) 10000;

	public static final Double DIV_MONEY100 = (double) 100;
	/*** 操作入金 ***/
	public static final String OPERATION_IN = "OPERATION_IN";
	/*** 操作出金 ***/
	public static final String OPERATION_OUT = "OPERATION_OUT";

	/*** 转账操作 ***/
	public static final String OPERATION_INFO_TRANSFER = "transfer";
	/*** 提现操作 ***/
	public static final String OPERATION_INFO_WITHDRAW = "withdraw";

	/*** 转账 路径 ***/
	public static final String TRANSFER_ACCOUNTS_URL = "http://10.0.5.2:20000/adapterrouter/tardeAdapter/doPay";

	/*** 提现路径 ***/
	public static final String CASHOUT_ACCOUNTS_URL = "http://10.0.5.2:20000/adapterrouter/outAndInCash/b2bwithdraw";
	/*** 开户 路径 ***/
	public static final String ZT_OPEN_ACCOUNT_URL = "http://10.0.5.2:20000/adapterrouter/accountAdapter/openAccount";
	/*** 账户余额查询 路径 ***/
	public static final String ZT_ACCOUNT_BALANCE_URL = "http://10.0.5.2:20000/adapterrouter/tardeAdapter/balance";
	/*** 转账通知 ***/
	public static final String INFORM_CHANGE_TASK_URL = "http://10.0.5.1:20890/bankCallBack/payCenter/updateUserTradeRecord";

	public static final String QUERY_WITHDRAW_RECORD_URL = "http://10.0.5.2:20000/adapterrouter/clearAndCheckAdapter/tradeRecord";

}
