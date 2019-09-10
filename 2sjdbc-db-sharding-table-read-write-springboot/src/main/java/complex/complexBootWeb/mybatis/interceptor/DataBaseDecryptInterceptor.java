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
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
/**
 * mybatis 数据解密解析器
 * 
 * @author lenovo
 *
 */
public class DataBaseDecryptInterceptor implements Interceptor {
	private final Logger logger = LoggerFactory.getLogger(DataBaseEncryptInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		String method_name = invocation.getMethod().getName();
		Object parameter = invocation.getArgs()[1];
//		if (parameter instanceof MSystemConsumerInfoEntityPO) {// 系统用户
//			logger.debug("系统用户查询数据加密");
//			this.systemConsumerUpdate((MSystemConsumerInfoEntityPO) parameter, method_name);
//		}

		Object returnValue = invocation.proceed();

		if (returnValue instanceof ArrayList<?>) {
			List<?> list = (ArrayList<?>) returnValue;
//			if (parameter instanceof MSystemConsumerInfoEntityPO) {// 系统用户
//				logger.debug("系统用户数据查询解密");
//				this.systemConsumerQuery((List<MSystemConsumerInfoEntityPO>) list, method_name);
//			} 

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
	// /**系统用户更新加密***/
	// public void systemConsumerUpdate(MSystemConsumerInfoEntityPO consumer,String
	// method_name){
	// if( StringUtils.isNotBlank( consumer.getConsumer_name() ) ){
	// consumer.setConsumer_name( Cryptos.encode( consumer.getConsumer_name() ));
	// }
	// if( StringUtils.isNotBlank( consumer.getConsumer_code() )){
	// consumer.setConsumer_code( Cryptos.encode(consumer.getConsumer_code()));
	// }
	// if( StringUtils.isNotBlank( consumer.getMobile_tel() )){
	// consumer.setMobile_tel( Cryptos.encode(consumer.getMobile_tel()) );
	// }
	//
	// }
	// /**系统用户查询解密***/
	// public void systemConsumerQuery(List<MSystemConsumerInfoEntityPO> list,String
	// method_name){
	// MSystemConsumerInfoEntityPO consumer=null;
	// for(Object obj:list){
	// if(obj instanceof MSystemConsumerInfoEntityPO){
	// consumer = (MSystemConsumerInfoEntityPO)obj;
	// consumer.setConsumer_name( Cryptos.decode( consumer.getConsumer_name() ));
	// consumer.setConsumer_code( Cryptos.decode(consumer.getConsumer_code()));
	// consumer.setMobile_tel( Cryptos.decode(consumer.getMobile_tel()) );
	// }else{
	// break;
	// }
	// }
	// }
}