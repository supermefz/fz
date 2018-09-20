package Master;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;



public class master {

	// 初始化服务器
	public static void initServerConfig() {
		System.out.println("初始化服务器！");
	}

	// 将得到的数据流进行分片处理---根据空格进行分片
	public static String[] strsplit(String str) {
		String[] arg = str.split(" ");
		return arg;
	}

	// 方法比较函数
	public static void methods(String[] arg, Map<String, String> map) {
		int tag = 0;
		String str = "0";
		String[] mstr = new String[200];
		if (arg[0].equals("set")) {
			set(arg, map);
		} else if (arg[0].equals("setnx")) {
			tag = setnx(arg, map);
			System.out.println(tag);
		} else if (arg[0].equals("setex")) {
			setex(arg, map);
		} else if (arg[0].equals("setrange")) {
			tag = setrange(arg, map);
			System.out.println(tag);
		} else if (arg[0].equals("mset")) {
			str = mset(arg, map);
			System.out.println(str);
		} else if (arg[0].equals("msetnx")) {
			tag = msetnx(arg, map);
			System.out.println(tag);
		} else if (arg[0].equals("get")) {
			str = get(arg, map);
			System.out.println(str);
		} else if (arg[0].equals("getset")) {
			str = getset(arg, map);
			System.out.println(str);
		} else if (arg[0].equals("getrange")) {
			str = getrange(arg, map);
			System.out.println(str);
		} else if (arg[0].equals("mget")) {
			mstr = mget(arg, map);
			for(int i = 1;i <= mstr.length;i++) {
				System.out.println(i+") "+mstr[i-1]+"\n");
			}
		} else {
			System.out.println("input error!");
		}
	}

	//判断是否为数字
	public static boolean isnum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	// set方法的实现(3个参数)----添加一对K-V键值对
	public static void set(String[] arg, Map<String, String> map) {
		if (arg.length != 3) {
			System.out.println("set参数输入错误！");
		} else {
			map.put(arg[1], arg[2]);
		}
	}

	// setnx方法的实现(3个参数)----查看并设置key,如果key存在，设置失败返回0；否则返回1；
	public static int setnx(String[] arg, Map<String, String> map) {
		int tag = 0;
		if (arg.length != 3) {
			System.out.println("setnx参数输入错误！");
		} else if (!map.containsKey(arg[1])) {
			map.put(arg[1], arg[2]);
			tag = 1;
		}
		return tag;        //失败返回0，成功返回1
	}

	// setex方法的实现(4个参数)----设置在期限内有效的键值对----实现不完整，会出现阻塞现象
	public static void setex(String[] arg, Map<String, String> map){
		if (arg.length != 4) {
			System.out.println("setex参数输入错误！");
		} else if (isnum(arg[2])) { 
			long t1 = System.currentTimeMillis();
			map.put(arg[1], arg[3]);
			while(true) {
				long t2 = System.currentTimeMillis();
				if(t2-t1 > Integer.parseInt(arg[2])*1000) 
					break;
			}
			map.remove(arg[1]);
		} else {
			System.out.println("时间输入错误！");
		}
	}

	// setrange方法的实现(4个参数)----替换第arg[2]个字符串之后的字符
	public static int setrange(String[] arg, Map<String, String> map) {
		int tag = 0;
		char[] temp = new char[200];
		char[] add = new char[100];
		int num = 0;
		Set<String> set = map.keySet();
		if (arg.length != 4) {
			System.out.println("setrange参数输入错误！");
		} else if (isnum(arg[2])) { 
			if(set.contains(arg[1])) {
				temp = map.get(arg[1]).toCharArray();	
				add = arg[3].toCharArray();	
				for(int i = Integer.parseInt(arg[2]);i < Integer.parseInt(arg[2])+arg[3].length();i++) {
					temp[i] = add[num++];
				}
				String str = String.valueOf(temp);
				map.put(arg[1],str);
				tag = str.length();		
			} else {
				add = arg[3].toCharArray();	
				for(int i = 0;i < Integer.parseInt(arg[2]);i++) {
					temp[i] = ' ';
				}
				for(int i = Integer.parseInt(arg[2]);i < Integer.parseInt(arg[2])+arg[3].length();i++) {
					temp[i] = add[num++];
				}
				String str = String.valueOf(temp);
				map.put(arg[1],str);	
				tag = str.length();		
			}
		} else {
			System.out.println("第三个参数输入错误！");
		}
		return tag;    //返回修改后的value的长度
	}

	// mset方法的实现----同时添加多对K-V键值对
	public static String mset(String[] arg, Map<String, String> map) {
		String str = "0";
		if ((arg.length - 1) % 2 != 0) {
			System.out.println("mset参数输入错误！");
		} else {
			for (int i = 1; i <= arg.length - 1; i = i + 2) {
				map.put(arg[i], arg[i + 1]);
			}
			str = "OK";
		}
		return str;
	}

	// msetnx方法的实现----同时输入多个不存在的key,如果一个失败，则全部失败返回0，否则返回1
	public static int msetnx(String[] arg, Map<String, String> map) {
		int tag = 0;
		if ((arg.length - 1) % 2 != 0) {
			System.out.println("msetnx参数输入错误！");
		} else {
			for (int i = 1; i <= arg.length - 1; i = i + 2) {
				if(map.containsKey(arg[i])) {
					return tag;
				}
			}
			for (int i = 1; i <= arg.length - 1; i = i + 2) {
				map.put(arg[i], arg[i + 1]);
			}
			tag = 1;
		}
		return tag;
	}

	// get方法的实现----返回key所对应的value
	public static String get(String[] arg, Map<String, String> map) {
		String value = "error!";
		if(arg.length != 2) {
			System.out.println("get参数输入错误！");
		} else {
			value = map.get(arg[1]);
		}
		return value;
	}

	// getset方法的实现----返回原key的value并设置新value
	public static String getset(String[] arg, Map<String, String> map) {
		String value = "error!";
		if(arg.length != 3) {
			System.out.println("getset参数输入错误！");
		} else {
			value = map.get(arg[1]);
			map.put(arg[1], arg[2]);
		}
		return value;
	}

	// getrange方法的实现----返回range范围内的value值
	public static String getrange(String[] arg, Map<String, String> map) {
		String str = "error!";
		char[] temp = new char[200];
		char[] tmp = new char[200];
		int num = 0;
		if(arg.length != 4) {
			System.out.println("getrange参数输入错误！");
		} else {
			temp = map.get(arg[1]).toCharArray();
			for(int i = Integer.parseInt(arg[2]);i <= Integer.parseInt(arg[3]);i++) {
				tmp[num++] = temp[i];
			}
			str = tmp.toString();
		}
		return str;
	}

	// mget方法的实现----一次返回多个key
	public static String[] mget(String[] arg, Map<String, String> map) {
		String[] mstr = new String[200];
		for(int i = 1;i < arg.length;i++) {
			mstr[i-1] = map.get(arg[i]);
		}
		return mstr;
	}

	// 主函数
	public static void main(String[] args) {

		// 初始化服务器
		// initServerConfig();

		//int dbnum = 16; // 一个master数据库默认有16和
		Map<String, String> map = new HashMap<String, String>();

		// 得到的Client端传来的数据流
		String getC2Mstring = "mset name1 123 name2 qwe";
		String[] arg = strsplit(getC2Mstring); // 得到分割后的数据
		methods(arg, map);

	}

}
