package complex.complexBootWeb.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import complex.complexBootWeb.constants.ManageConstants;

public class MoneyUtils {
	public static final Logger logger = LoggerFactory.getLogger(MoneyUtils.class);

	/** **/
	public static Integer accountNumber(BigDecimal a) {
		if (null == a) {
			return 0;
		}
		return a.divide(ManageConstants.BIGDECIMAL_MONEY_50000, 0, BigDecimal.ROUND_CEILING).intValue();
	}

	/**
	 * 根据金额判定人数
	 * 
	 * @param money
	 * @return
	 */
	public static Integer accountNumber(Double money) {
		Integer personNum = 0;
		// 如果小于 直接记录转账
		logger.info("转账金额", money);
		if (money < ManageConstants.MONEY) {
			personNum = 1;
		} else {
			// 将总金额除以50000 第一种情况 amount%count 等于1.00 则将数值变更成2进行划分
			double number = money / 50000;
			logger.info("转账需要人数：", number);
			// 整数标识 n
			int n = (int) number;
			// 过度变量
			personNum = n + 1;
			logger.info("转账需要人数：", personNum);
		}
		return personNum;
	}

	/**
	 * 划分金额 -准备弃用
	 * 
	 * @param totalMoney
	 * @param count
	 * @return
	 */
	public static List<BigDecimal> send(double totalMoney, int count) {
		/*
		 * 前一个arrayList类型为Double用来划分红包的金额 后一个arrayDB用来精确计算,计算数据来源于arrayList的成员.保留两位小数.
		 * 返回BigDecimal类型的集合对象.
		 */
		ArrayList<Double> arrayList = new ArrayList<>();
		List<BigDecimal> araryBD = new ArrayList<BigDecimal>();

		// 发红包拆分成count份数,每份的金额不定
		// 红包不一定是整数,如果有人非要发带小数的红包,那么我们将小数位分离出来.
		double decimalPart = totalMoney - (int) totalMoney;
		// 单独分理出小数,就可以直接处理整数部分了.赋给变量moneyInt
		int moneyInt = (int) (totalMoney);
		logger.info("moneyInt:" + moneyInt);
		Random ra = new Random();
		double temp = 0, sumNum = 0;
		/*
		 * 步骤2. 将总金额分成N个红包,那我们先包好N-1个红包, 每个红包随机赋予(0,MoneyInt)之间的值
		 */
		for (int i = 0; i < count; i++) {
			temp = (double) ra.nextInt(50000);
			logger.info("temp:" + temp);
			arrayList.add(temp);
			sumNum += temp;
		}
		/* * 步骤3. 然后用金额上限/红包总额 得到一个系数. */
		double xishu = moneyInt / sumNum;
		/* 定义与系数运算后的接受变量numAfter/sumAfter */
		double numAfter = 0, sumAfter = 0;
		for (int i = 0; i < count - 1; i++) {
			/** 步骤4. 将每个红包的值 * 系数 = 每个红包的实际值 */
			numAfter = arrayList.get(i) * xishu;
			arrayList.set(i, numAfter);
			// 将numAfter传递给BigDecimal类,需要将浮点数转换成字符串
			String str = numAfter + "";
			BigDecimal decimal = new BigDecimal(str);
			BigDecimal setScale1 = decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			// 将decimal添加到BigDecimal类的集合中
			araryBD.add(setScale1);
			sumAfter += numAfter;
		}
		// 初始化最后一个红包的值为0
		double finalNum = 0;
		/* 步骤5. 最后一个红包的值 = 总的红包金额 - 红包1-红包2-红包3 + 步骤一拆分的余数; */
		finalNum = (double) moneyInt - sumAfter + decimalPart;
		// arrayList.set(count-1,finalNum);
		/*
		 * 将最后一个红包的值转换为String类型的对象,然后传递给BigDecimal
		 * 用过setScale();方法将结果保留两位小数,采用ROUND_HALF_UP参数,也就是所谓的四舍五入.
		 */
		String strFinal = finalNum + "";
		BigDecimal decima2 = new BigDecimal(strFinal);
		BigDecimal setScale2 = decima2.setScale(2, BigDecimal.ROUND_HALF_UP);
		/* 将四舍五入后的结果添加到arrayBD中 */
		araryBD.add(setScale2);

		logger.info(" 使用BigDecimal");
		logger.info("araryBD{}", araryBD);

		return araryBD;
	}

	public static List<BigDecimal> randomPackage(double total_money, int total_people) {
		double max_money = ManageConstants.MAX_MONEY;
		// double lingjiezhi = (total_money / total_people) * 1000;
		double allresult = 0;
		for (int i = 0; i < total_people; i++) {
			// 保护值
			double procte = (total_people - i - 1) * ManageConstants.MIN_MONEY / 1000;
			// 可支配金额
			double zpje = total_money - procte;
			if (zpje * 1000 < max_money) {
				max_money = zpje * 1000;
			}
			double result = restRound(ManageConstants.MIN_MONEY, ManageConstants.MAX_MONEY, i, zpje, total_people - 1);
			total_money = total_money - result;
			allresult += result;
			System.out.format("红包  %.2f,余额  %.2f \n", result, total_money);
		}
		System.out.format("总金额%.2f", allresult);

		return null;
	}

	/**
	 * 
	 * @param min
	 * @param max
	 * @param i
	 * @param money
	 * @param count
	 * @return
	 */
	public static double restRound(double min, double max, int i, double money, int count) {
		double redpac = 0;
		if (i == 19) {
			redpac = money;
		} else {
			redpac = (Math.random() * (max - min) + min) / 1000;
		}
		return redpac;
	}

	public static List<BigDecimal> restrestRounds(double money, int count) {
		// 转钱阈值
		BigDecimal mo = new BigDecimal(Double.toString(money));
		BigDecimal money_ = new BigDecimal(Double.toString(ManageConstants.MAX_MONEY));
		BigDecimal number = new BigDecimal(Double.toString(count - 1));
		List<BigDecimal> list = new ArrayList<>();
		BigDecimal result = money_.multiply(number);
		for (int i = 0; i < count; i++) {

			if (i == count - 1) {

				BigDecimal a1 = mo.subtract(result);
				BigDecimal b1 = new BigDecimal(Double.toString(100));
				// BigDecimal results = a1.multiply(b1);// 相乘结果
				// String money_str = result.toString();
				list.add(mo.subtract(result));
			} else {
				list.add(money_);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		// double total_money=120125;
		// int total_people=3;
		List<BigDecimal> list = randomPackage(12458, 13);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));

		}

	}
}
