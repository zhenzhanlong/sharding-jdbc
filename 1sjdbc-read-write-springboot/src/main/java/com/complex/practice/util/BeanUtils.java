package com.complex.practice.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.complex.practice.annotation.Cp;

/**
 * 
 * beanUtil 进行bean的处理,包括深克隆和不用bean之间属性的赋值
 * 
 * @author dwpeng
 */
public class BeanUtils {

	private static Logger _log = LoggerFactory.getLogger(BeanUtils.class);

	/**
	 * 将原来bean的属性赋值给不用同bean属性,这两个bean可以不是同一个类 因为反射和一些嵌套查询,此方法会影响bean的初始化速度
	 * 
	 * @param from
	 *            源bean
	 * @param to
	 *            目的bean b 走自定义标识拷贝
	 * @throws Exception
	 */
	public static void copyProperties(Object from, Object to) throws Exception {
		copyProperties(from, to, false);
	}

	/**
	 * 将原来bean的属性赋值给不用同bean属性,这两个bean可以不是同一个类 因为反射和一些嵌套查询,此方法会影响bean的初始化速度
	 * 
	 * @param from
	 *            源bean
	 * @param to
	 *            目的bean b 走自定义标识拷贝
	 * @throws Exception
	 */
	public static void copyProperties(Object from, Object to, boolean b) throws Exception {

		// 如果两个类是一样的
		// 如果两个类是一样的
		if (!b) {
			org.springframework.beans.BeanUtils.copyProperties(from, to);
		} else {
			copyDProperties(from, to);
		}
	}

	/**
	 * bean的深克隆
	 * 
	 * @param from
	 *            bean
	 * @return Object
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */

	public static Object copyProperties(Object from)
			throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
		return org.apache.commons.beanutils.BeanUtils.cloneBean(from);
	}

	/**
	 * 获取某个对象的方法
	 * 
	 * @param owner
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object invokeGetMethod(Object owner, String methodName) throws Exception {
		// 或得这个类的Class
		Class ownerClass = owner.getClass();
		// 通过Method名和参数的Class数组得到要执行的Method
		Method method = ownerClass.getMethod(methodName);
		// 执行该Method，invoke方法的参数是执行这个方法的对象，和参数数组。返回值是Object，也既是该方法的返回值
		return method.invoke(owner);
	}

	/**
	 * 获取某个对象的方法
	 * 
	 * @param owner
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void invokeSetMethod(Object owner, String methodName, Object[] args) throws Exception {
		// 或得这个类的Class
		Class ownerClass = owner.getClass();
		// 配置参数的Class数组，作为寻找Method的条件
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		// 通过Method名和参数的Class数组得到要执行的Method
		Method method = ownerClass.getMethod(methodName, argsClass);
		// 执行该Method，invoke方法的参数是执行这个方法的对象，和参数数组。返回值是Object，也既是该方法的返回值
		method.invoke(owner, args);
	}

	/**
	 * 获取当前类的所有属性
	 * 
	 * @param owner
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	private static Map<String, Field> getFieldMap(Class classes) throws Exception {

		Map<String, Field> fields = null;
		if (classes == null) {
			return fields;
		}
		Field[] fieldlist = classes.getDeclaredFields();
		if (fieldlist == null) {
			return fields;
		}
		fields = new HashMap<>();
		for (Field fiel : fieldlist) {
			fields.put(fiel.getName(), fiel);
		}
		return fields;
	}

	/**
	 * 通过源属性和目的属性的类型进行对比,
	 * 
	 * @param fromfield
	 *            源属性
	 * @param fromobj
	 *            源属性的值
	 * @param tofiled
	 *            目标类属性
	 * @return Object 目标类属性值
	 * @throws Exception
	 */
	private static Object transObject(Field fromfield, Object fromobj, Field tofiled) throws Exception {
		Object obj = null;
		if (fromfield == null || fromobj == null || tofiled == null) {
			return obj;
		}
		if (tofiled.getType().equals(fromfield.getType())) {

			return fromobj;
		}
		switch (tofiled.getType().getName()) {
		case "java.lang.String":
			if (fromfield.getType().getName().equals("java.lang.Double")
					|| fromfield.getType().getName().equals("double")
					|| fromfield.getType().getName().equals("java.lang.Integer")
					|| fromfield.getType().getName().equals("int")
					|| fromfield.getType().getName().equals("java.lang.Float")
					|| fromfield.getType().getName().equals("float")) {
				obj = String.valueOf(fromobj);
			} else if (fromfield.getType().getName().equals("java.util.Date")) {

				obj = DateUtil.dateToStr((java.util.Date) fromobj);
			}
			break;
		case "double":
		case "java.lang.Double":
			if (fromfield.getType().getName().equals("java.lang.String")) {
				obj = Double.valueOf((String) fromobj);
			}
			break;
		case "int":
		case "java.lang.Integer":
			if (fromfield.getType().getName().equals("java.lang.String")) {
				obj = Integer.valueOf((String) fromobj);
			}
			break;
		case "float":
		case "java.lang.Float":
			if (fromfield.getType().getName().equals("java.lang.String")) {
				obj = Float.valueOf((String) fromobj);
			}
			break;
		case "boolean":
		case "java.lang.Boolean":
			if (fromfield.getType().getName().equals("java.lang.Integer")
					|| fromfield.getType().getName().equals("int")) {
				int falg = ((Integer) fromobj).intValue();
				// 将整形转换为boolean,1真,0为假
				if (falg == 1) {
					obj = Boolean.TRUE;
				} else if (falg == 0) {
					obj = Boolean.FALSE;
				}
			}
			break;
		case "java.util.Date":
			if (fromfield.getType().getName().equals("java.lang.String")) {
				obj = DateUtil.strToDate((String) fromobj);
			} else if (fromfield.getType().getName().equals("java.sql.Date")) {
				java.sql.Date fromdate = (java.sql.Date) fromobj;
				obj = DateUtil.transsqldate(fromdate);
			}
			break;
		default:
			break;
		}

		return obj;

	}

	/**
	 * 通过属性名称生成get方法
	 * 
	 * @param sourcename
	 * @return
	 */
	private static String makeGetMethod(String sourcename) {

		String setmethod = null;

		if (sourcename == null) {

			return setmethod;
		}
		if (sourcename.length() == 1) {
			setmethod = "get" + sourcename.toUpperCase();

		} else {
			setmethod = "get" + sourcename.substring(0, 1).toUpperCase() + sourcename.substring(1);
		}
		return setmethod;

	}

	/**
	 * 通过属性名称生成set方法名
	 * 
	 * @param tofielname
	 *            set属性的名称
	 * @return String set方法名称
	 */
	private static String makeSetMethod(String tofielname) {

		String setmethod = null;

		if (tofielname == null) {

			return setmethod;
		}
		if (tofielname.length() == 1) {
			setmethod = "set" + tofielname.toUpperCase();

		} else {
			setmethod = "set" + tofielname.substring(0, 1).toUpperCase() + tofielname.substring(1);
		}
		return setmethod;

	}

	@SuppressWarnings({ "rawtypes" })
	private static void copyDProperties(Object from, Object to) throws Exception {

		Class toclass = (Class) to.getClass();
		Class fromclass = (Class) from.getClass();
		// 源类中所有属性集合
		Map<String, Field> fromfields = getFieldMap(fromclass);
		if (fromfields == null) {
			return;
		}
		// 得到目标类类中的所有属性集合
		Map<String, Field> tofields = getFieldMap(toclass);
		if (tofields == null) {
			return;
		}
		for (Entry<String, Field> field : tofields.entrySet()) {
			// 目标属性名称
			String tofieldname = field.getKey();
			// 目标属性值
			Field tofield = field.getValue();
			System.out.println("to属性名称:" + tofieldname + ",to属性类型:" + tofield.getType().getName());
			// 判断当前bean是否含有注解
			boolean hasAnnotation = tofield.isAnnotationPresent(Cp.class);
			if (hasAnnotation) {
				// 获取当前属性的全部注解
				Annotation[] annotations = tofield.getAnnotations();
				for (Annotation an : annotations) {
					if (an instanceof Cp) {
						Cp cp = (Cp) an;
						System.out.println("注解属性名:" + cp.sourceName() + ",注解类名称" + cp.className());
						// 对比当前的类
						if (!cp.className().equals(Object.class) && !cp.className().equals(fromclass)) {
							continue;
						} else {
							// 设置源的属性值
							String fomrfielname = cp.sourceName();
							Field fromfile = fromfields.get(fomrfielname);
							// 初始化get方法
							String frommethodName = makeGetMethod(cp.sourceName());
							_log.debug("frommethodName:" + frommethodName);
							// 获取from bean的属性值
							Object getobj = invokeGetMethod(from, frommethodName);
							_log.debug("frommethodName:" + frommethodName + "=" + getobj);
							// 初始化set方法
							String tomethodName = makeSetMethod(tofieldname);
							_log.debug("tomethodName:" + tomethodName);
							// 初始化set方法插入参数
							Object[] para = new Object[1];
							para[0] = transObject(fromfile, getobj, tofield);
							if (para[0] == null) {
								continue;
							}
							// 调用set方法
							invokeSetMethod(to, tomethodName, para);
						}

					} else {

						continue;
					}
				}
			}

		}
	}

	/**
	 * 将javabean 的属性值存入到map
	 * 
	 * @param obj
	 *            javabean
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(key, value);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * @param classes
	 *            生成对象类型
	 * @param map
	 *            transBean2Map 生成的map
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T transMap2Bean(Class<T> classes, Map<String, Object> map) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(classes); // 获取类属性
			Object obj = classes.newInstance(); // 创建 JavaBean 对象
			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					if (map.containsKey(propertyName)) {
						// 赋值
						Object value = map.get(propertyName);
						Object[] args = new Object[] { value };
						descriptor.getWriteMethod().invoke(obj, args);
					}
				}
			}
			return (T) obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// @SuppressWarnings({ "unchecked", "restriction" })
	// public static <T> T makeObjectById(Class<T> classes, Object oldobj) {
	//
	// Object newobj = null;
	// try {
	// newobj = UnsafeSupport.getInstance().allocateInstance(classes);
	// long offset =
	// UnsafeSupport.getInstance().objectFieldOffset(classes.getDeclaredField("id"));
	// Object odlvalue = UnsafeSupport.getInstance().getObject(oldobj, offset);
	// UnsafeSupport.getInstance().putObject(newobj, offset, odlvalue);
	// } catch (InstantiationException e) {
	// } catch (NoSuchFieldException e) {
	// } catch (SecurityException e) {
	// }
	//
	// return (T) newobj;
	// }

}
