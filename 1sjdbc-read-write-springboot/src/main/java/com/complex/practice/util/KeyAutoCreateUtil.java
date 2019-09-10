/**
 * 
 */
package com.complex.practice.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.redis.constants.RedisConstants;

import redis.clients.jedis.JedisCommands;

/**
 * @author 鑫
 * @Date 2017年4月19日 下午6:52:58 通用常量
 */
public class KeyAutoCreateUtil {
	private KeyAutoCreateUtil() {

	}

	public static String keyCreate() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 生成主键,主键长度由前缀+日期字符串+随机数决定
	 * 
	 * @param primarykeyclass
	 *            表名字实体,每个不同的表对应不同的主键序列
	 * @param cache
	 * @param datestr
	 *            日期字符串
	 * @return Sring 主键
	 */
	public static synchronized String makePrimarykey(PrimarykeyEnum primarykeyEnum, String datestr,
			JedisCommands cache) {
		if (primarykeyEnum == null || cache == null) {
			return null;
		}
		String primarykey = null;
		// 生成主键后缀
		String prefix = null;
		// 生成主键前缀
		String dateprofix = null;
		// 设置日期字符串的前缀
		if (datestr != null) {
			dateprofix = datestr;
		} else {
			dateprofix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		}
		String key = RedisConstants.PRIMARYKEY_SEQUENCE + ":" + primarykeyEnum.getTablename();
		// 判断当前的key是否存在
		boolean priexists = cache.exists(key);
		if (!priexists) {
			// 初始化ket
			cache.set(key, primarykeyEnum.getIntmum());
			primarykey = dateprofix + primarykeyEnum.getIntmum();
			return primarykey;
		} else {
			// 递增
			prefix = cache.incr(key) + "";
			if (prefix.equals(primarykeyEnum.getMaxnum())) {
				cache.set(key, primarykeyEnum.getIntmum());
			}
			primarykey = dateprofix + prefix;
		}
		return primarykey;
	}

	/**
	 * 生成主键,主键长度由前缀+日期字符串+随机数决定
	 * 
	 * @param primarykeyclass
	 *            表名字实体,每个不同的表对应不同的主键序列
	 * @param cache
	 *            日期字符串
	 * @return Sring 主键
	 */
	public static Integer makePrimaryInteger(PrimarykeyEnum primarykeyEnum, JedisCommands cache) {
		return makePrimaryLong(primarykeyEnum, cache).intValue();
	}

	/**
	 * 生成主键,主键长度由前缀+日期字符串+随机数决定
	 * 
	 * @param primarykeyclass
	 *            表名字实体,每个不同的表对应不同的主键序列
	 * @param cache
	 *            日期字符串
	 * @return Sring 主键
	 */
	public static Long makePrimaryLong(PrimarykeyEnum primarykeyEnum, JedisCommands cache) {
		if (primarykeyEnum == null || cache == null) {
			return Long.valueOf(0);
		}
		// 生成主键后缀
		Long prefix = null;
		String key = RedisConstants.PRIMARYKEY_SEQUENCE + ":" + primarykeyEnum.getTablename();
		// 判断当前的key是否存在
		boolean priexists = cache.exists(key);
		if (!priexists) {
			// 初始化ket
			cache.set(key, primarykeyEnum.getIntmum());
			return Long.valueOf((primarykeyEnum.getIntmum()));
		} else {
			// 递增
			prefix = cache.incr(key);
			if (prefix.toString().equals(primarykeyEnum.getMaxnum())) {
				cache.set(key, primarykeyEnum.getIntmum());
			}
			return prefix;
		}
	}
}
