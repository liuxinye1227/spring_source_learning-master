package com.spring_util.spel;

import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.junit.Test;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pengYongQiang
 * @date 2021/6/24 23:13
 */
public class DemoTest {
private static final String jsonStr="{\n" +
		"  \"code\": 200,\n" +
		"  \"message\": \"ok\",\n" +
		"  \"data\": {\n" +
		"    \"videoStatisticsData\": {\n" +
		"      \"pageCount\": 0,\n" +
		"      \"record\": [],\n" +
		"      \"pageSize\": 20,\n" +
		"      \"totalCount\": 0,\n" +
		"      \"pageNum\": 1\n" +
		"    },\n" +
		"    \"violationData\": {\n" +
		"      \"pageCount\": 6\n" +
		"    }\n" +
		"  }\n" +
		"}";
	static class C {
		public static Map j = null;
	}
	static class B {
		public static HashMap<Object, Object> map = new HashMap<>();
		static {
			//map.put("bb","999");
		}
	}
	static class A {
		public static HashMap<Object, Object> map = new HashMap<>();
		public  static ArrayList<Object> list = new ArrayList<>();

		static {
			map.put("a", "b");
			map.put("b", new B());
			list.add("123");
		}
	}

	@Test
	public void b() throws NoSuchMethodException {
		ExpressionParser parser = new SpelExpressionParser();

		DefaultParameterNameDiscoverer defaultParameterNameDiscoverer = new DefaultParameterNameDiscoverer();

		User user = new User();
		user.setName("666");
		Method setName = User.class.getMethod("setName", String.class);
		String[] strings = {"张三"};

		EvaluationContext context = new MethodBasedEvaluationContext(user,setName,strings,defaultParameterNameDiscoverer);

		Object value = parser.parseExpression("#name").getValue(context);
		System.out.println(value);
		Object value1 = parser.parseExpression("'name'").getValue(context);
		System.out.println(value1);
		Object value2 = parser.parseExpression("T(com.spring_util.spel.OprateEnum).valueOf('ADD')").getValue();
		System.out.println(value2 instanceof OprateEnum);
		System.out.println(value2);
	}

	public static class User{
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	@Test
	public void a(){

		// Turn on:
		// - auto null reference initialization
		// - auto collection growing
		SpelParserConfiguration config = new SpelParserConfiguration(true, true);
		ExpressionParser parser = new SpelExpressionParser();

		Expression exp = parser.parseExpression("map[b].map[bb] == '999' ");

		A a = new A();

		System.out.println(exp.getValue(a));
		System.out.println( parser.parseExpression("map[b].map[bb]").getValue(a));


		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("aa",jsonObject);
		System.out.println( parser.parseExpression("#aa[data][videoStatisticsData]").getValue(context));

		context.setVariable("aa","123");
		System.out.println( parser.parseExpression("#aa").getValue(context));


		HashMap<String, String> map = new HashMap<>();
		map.put("qqq","pyq");
		System.out.println( parser.parseExpression("#qqq").getValue(map));


	}
}
