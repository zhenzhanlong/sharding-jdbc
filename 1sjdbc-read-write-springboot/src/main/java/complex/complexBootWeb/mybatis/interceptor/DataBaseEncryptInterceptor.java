package complex.complexBootWeb.mybatis.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }), })
/**
 * mybatis 数据加密解析器
 * 
 * @author lenovo
 *
 */
public class DataBaseEncryptInterceptor implements Interceptor {

	private final Logger logger = LoggerFactory.getLogger(DataBaseEncryptInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		String method_name = invocation.getMethod().getName();
		Object parameter = invocation.getArgs()[1];
		// if (parameter instanceof MPersonalConsumerInfoEntityPO) {// 个人信息
		// logger.debug("个人信息数据编辑加密");
		// this.personalConsumerInfoEntityUpdate((MPersonalConsumerInfoEntityPO)
		// parameter, method_name);
		// }
		Object returnValue = invocation.proceed();
		if (returnValue instanceof ArrayList<?>) {
			List<?> list = (ArrayList<?>) returnValue;
			// if (parameter instanceof MPersonalConsumerInfoEntityPO) {// 个人信息
			// // this.personalConsumerInfoEntityQuery(list, method_name);
			// }

		}
		return returnValue;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

	// /** 个人信息加密 **/
	// public void personalConsumerInfoEntityUpdate(MPersonalConsumerInfoEntityPO
	// consumer, String method_name) {
	// try {
	//
	// if (StringUtils.isNotBlank(consumer.getPayment_password())) {
	// consumer.setPayment_password(Cryptos.encode(consumer.getPayment_password()));
	// }
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// logger.error("个人用户修改 加密时出错" + ex.getMessage());
	// }
	// }

	// /** 个人信息 解密 **/
	// public void personalConsumerInfoEntityQuery(MPersonalConsumerInfoEntityPO
	// consumer, String method_name) {
	// try {
	//
	// if (StringUtils.isNotBlank(consumer.getPayment_password())) {
	// consumer.setPayment_password(Cryptos.decode(consumer.getPayment_password()));
	// }
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// logger.error("个人用户修改 加密时出错" + ex.getMessage());
	// }
	// }
}