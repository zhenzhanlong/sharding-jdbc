package com.complex.practice.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * @author 鑫
 * @Date 2017年3月5日 下午6:48:43
 */
public class RandomCodeUtils {

	private static final String digit_0 = "零壹贰叁肆伍陆柒捌玖";
	private static final String digit_1 = "零一二三四五六七八九";
	private static final String[] digit_2 = { "", "十", "百", "千" };
	private static final String[] digit_3 = { "", "拾", "佰", "仟" };
	private static final String[] digit_4 = { "", "万", "亿", "万亿", "亿亿" };
	private static String[] symbolstr = new String[] { "加", "减", "乘", "除", "+", "-", "×", "÷" };
	private static final String[] equal = { "=", "等于" };

	public static Map<String, Object> getCode(int len) {
		Random random = new Random();
		int type = random.nextInt(4) % 4;
		Map<String, Object> map = new HashMap<String, Object>();
		String code = "";

		switch (type) {
		case 0:
			code = getAlphanumeric(len);
			map.put("result", code.toUpperCase());
			map.put("code", code);
			break;
		case 1:
			code = getAlphabetic(len);
			map.put("result", code.toUpperCase());
			map.put("code", code);
			break;
		case 2:
			code = getNumeric(len);
			map.put("result", code.toUpperCase());
			map.put("code", code);
			break;

		default:
			map = getFormula();
			break;
		}

		return map;

	}

	public static String getAlphanumeric(int leng) {
		return RandomStringUtils.randomAlphanumeric(leng);
	}

	public static String getAlphabetic(int leng) {
		return RandomStringUtils.randomAlphabetic(leng);
	}

	public static String getNumeric(int leng) {
		return RandomStringUtils.randomNumeric(leng);
	}

	public static Map<String, Object> getFormula() {
		int index, symbol, equal;
		int number1, number2;
		Random random = new Random();
		// 04 加 15 减 26乘 37除
		symbol = random.nextInt(100) % 4;
		// index = random.nextInt(10) % 3;
		index = 0;
		// equal = random.nextInt(10) % 2;
		equal = 0;
		number1 = random.nextInt(10);
		number2 = random.nextInt(10);

		int result = 0;
		StringBuffer stringBuffer = new StringBuffer();

		if (0 == symbol) {

			result = number1 + number2;
			if (number1 < 10 && number2 < 10) {
				stringBuffer.append(getNumberStr(number1 + "", index));
				stringBuffer.append(symbolstr[symbol]);
				stringBuffer.append(getNumberStr(number2 + "", index));
				stringBuffer.append(RandomCodeUtils.equal[equal]);
			} else {
				stringBuffer.append(number1);
				stringBuffer.append(symbolstr[symbol]);
				stringBuffer.append(number2);
				stringBuffer.append(RandomCodeUtils.equal[equal]);
			}

		}

		if (1 == symbol) {
			result = number1 - number2;
			if (number1 < 10 && number2 < 10) {
				stringBuffer.append(getNumberStr(number1 + "", index));
				stringBuffer.append(symbolstr[symbol]);
				stringBuffer.append(getNumberStr(number2 + "", index));
				stringBuffer.append(RandomCodeUtils.equal[equal]);
			} else {
				stringBuffer.append(number1);
				stringBuffer.append(symbolstr[symbol]);
				stringBuffer.append(number2);
				stringBuffer.append(RandomCodeUtils.equal[equal]);
			}
		}

		if (2 == symbol) {
			result = number1 * number2;
			if (number1 < 10 && number2 < 10) {
				stringBuffer.append(getNumberStr(number1 + "", index));
				stringBuffer.append(symbolstr[symbol]);
				stringBuffer.append(getNumberStr(number2 + "", index));
				stringBuffer.append(RandomCodeUtils.equal[equal]);
			} else {
				stringBuffer.append(number1);
				stringBuffer.append(symbolstr[symbol]);
				stringBuffer.append(number2);
				stringBuffer.append(RandomCodeUtils.equal[equal]);
			}
		}

		if (3 == symbol) {

			if (0 == number1) {
				number1 = number1 + 1;
			}

			if (0 == number2) {
				number2 = number2 + 1;
			}

			result = number1 * number2;

			int tyep = number1;

			number1 = result;
			number2 = tyep;

			result = number1 / number2;

			if (number1 < 10 && number2 < 10) {
				stringBuffer.append(getNumberStr(number1 + "", index));
				stringBuffer.append(symbolstr[symbol]);
				stringBuffer.append(getNumberStr(number2 + "", index));
				stringBuffer.append(RandomCodeUtils.equal[equal]);
			} else {
				stringBuffer.append(number1);
				stringBuffer.append(symbolstr[symbol]);
				stringBuffer.append(number2);
				stringBuffer.append(RandomCodeUtils.equal[equal]);
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		map.put("code", stringBuffer.toString());

		return map;
	}

	private static String getNumberStr(String number, int type) {
		if (0 == type) {

			return number;
		}

		if (1 == type) {

			return changeDigit(number, false);
		}

		return changeDigit(number, true);
	}

	/**
	 * Description: 数字转化成整数
	 * 
	 * @param str
	 * @param bo
	 * @return
	 */
	public static String changeDigit(String str, boolean bo) {
		StringBuffer strbu = new StringBuffer();
		int dou = str.indexOf(".");
		// :判断是否为小数还是整数，长度小于零为整数
		if (dou < 0) {
			dou = str.length();
		}
		// :获取整数部分
		String inter = str.substring(0, dou);
		strbu.append(changeInteger(Long.parseLong(inter), bo));
		// :处理小数部分
		if (dou != str.length()) {
			strbu.append("点");
			// :获取小数点后所有数
			String xh = str.substring(dou + 1);
			for (int i = 0; i < xh.length(); i++) {
				if (bo) {
					strbu.append(digit_0.charAt(Integer.parseInt(xh.substring(i, i + 1))));
				} else {
					strbu.append(digit_1.charAt(Integer.parseInt(xh.substring(i, i + 1))));
				}
			}
		}
		String strs = strbu.toString();
		// :处理特殊情况，可能不全
		if (strs.startsWith("零")) {
			strs = strs.substring(1);
		}
		if (strs.startsWith("一十")) {
			strs = strs.substring(1);
		}
		while (strs.endsWith("零")) {
			strs = strs.substring(0, strs.length() - 1);
		}
		if (strs.startsWith("点")) {
			strs = "零" + strs;
		}
		if (strs.endsWith("点")) {
			strs = strs.substring(0, strs.length() - 1);
		}
		if (StringUtils.isBlank(strs)) {
			strs = "零";
		}
		return strs;
	}

	/**
	 * 整数部分转换大写
	 * 
	 * @param lon
	 * @param bo
	 * @return
	 */
	public static String changeInteger(long lon, boolean bo) {
		StringBuffer strbu = new StringBuffer();
		// :增加3位数,为了完成大写转换
		String strN = "000" + lon;
		int strN_L = strN.length() / 4;
		// :根据不同的位数长度，消除strN"0"的个数
		strN = strN.substring(strN.length() - strN_L * 4);
		for (int i = 0; i < strN_L; i++) {
			String s1 = strN.substring(i * 4, i * 4 + 4);
			String s2 = readNumber(s1, bo);
			strbu.append(s2);
			if (s2.length() != 0) {
				strbu.append(digit_4[strN_L - i - 1]);
			}
		}
		String s = new String(strbu);
		if (s.length() != 0 && s.startsWith("零"))
			s = s.substring(1);
		return s;
	}

	/**
	 * 位数小于4时，调用处理数据
	 * 
	 * @param str
	 * @param bo
	 * @return
	 */
	public static String readNumber(String str, boolean bo) {
		StringBuffer strbu = new StringBuffer();
		if (str.length() != 4) {
			return null;
		}
		for (int i = 0; i < 4; i++) {
			char ch = str.charAt(i);
			if (ch == '0' && i > 1 && str.charAt(i - 1) == '0') {
				continue;
			}
			if (ch != '0' && i > 1 && str.charAt(i - 1) == '0') {
				strbu.append('零');
			}
			if (ch != '0') {
				if (bo) {
					strbu.append(digit_0.charAt(ch - 48));
					strbu.append(digit_3[4 - i - 1]);
				} else {
					strbu.append(digit_1.charAt(ch - 48));
					strbu.append(digit_2[4 - i - 1]);
				}
			}
		}
		return strbu.toString();
	}

	public static String arrayToStr(String[] arrays) {
		if (null == arrays || 0 == arrays.length) {
			return null;
		}
		StringBuilder str = new StringBuilder();
		for (String array : arrays) {
			if (str.length() > 0) {
				str.append(",").append(array);
			} else {
				str.append(array);
			}
		}
		return str.toString();
	}

	public static String bigDecimalToStr(BigInteger[] arrays) {
		if (null == arrays || 0 == arrays.length) {
			return null;
		}
		StringBuilder str = new StringBuilder();
		for (BigInteger array : arrays) {
			if (str.length() > 0) {
				str.append(",").append(array);
			} else {
				str.append(array);
			}
		}
		return str.toString();
	}

	public static String intToStr(Integer[] arrays) {
		if (null == arrays || 0 == arrays.length) {
			return null;
		}
		StringBuilder str = new StringBuilder();
		for (Integer array : arrays) {
			if (str.length() > 0) {
				str.append(",").append(array);
			} else {
				str.append(array);
			}
		}
		return str.toString();
	}

	/**
	 * 集合转字符串
	 * 
	 * @param dataList
	 * @return
	 */
	public static String listToStr(List<String> dataList) {
		if (null == dataList || dataList.isEmpty()) {
			return null;
		}
		StringBuilder supplierOrder = new StringBuilder();
		dataList.forEach(str -> {
			if (supplierOrder.length() > 0) {
				supplierOrder.append(",");
			}
			supplierOrder.append(str);

		});
		return supplierOrder.toString();
	}

	public static String encode(byte[] bytes) {
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 随机6位验证码
	 * 
	 * @return
	 */
	public static String buildSmsCode() {
		return String.format("%06d", new Random().nextInt(1000000));
	}

	public static void main(String[] args) {

		// Map<String, Object> map = getCode();
		// StringBuffer sb =new StringBuffer( map.get("code").toString());
		// for (int i = 0; i < sb.length(); i++) {
		// System.out.println(sb.);
		// }

	}
}
