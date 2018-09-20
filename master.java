package Master;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;



public class master {

	// ��ʼ��������
	public static void initServerConfig() {
		System.out.println("��ʼ����������");
	}

	// ���õ������������з�Ƭ����---���ݿո���з�Ƭ
	public static String[] strsplit(String str) {
		String[] arg = str.split(" ");
		return arg;
	}

	// �����ȽϺ���
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

	//�ж��Ƿ�Ϊ����
	public static boolean isnum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	// set������ʵ��(3������)----���һ��K-V��ֵ��
	public static void set(String[] arg, Map<String, String> map) {
		if (arg.length != 3) {
			System.out.println("set�����������");
		} else {
			map.put(arg[1], arg[2]);
		}
	}

	// setnx������ʵ��(3������)----�鿴������key,���key���ڣ�����ʧ�ܷ���0�����򷵻�1��
	public static int setnx(String[] arg, Map<String, String> map) {
		int tag = 0;
		if (arg.length != 3) {
			System.out.println("setnx�����������");
		} else if (!map.containsKey(arg[1])) {
			map.put(arg[1], arg[2]);
			tag = 1;
		}
		return tag;        //ʧ�ܷ���0���ɹ�����1
	}

	// setex������ʵ��(4������)----��������������Ч�ļ�ֵ��----ʵ�ֲ��������������������
	public static void setex(String[] arg, Map<String, String> map){
		if (arg.length != 4) {
			System.out.println("setex�����������");
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
			System.out.println("ʱ���������");
		}
	}

	// setrange������ʵ��(4������)----�滻��arg[2]���ַ���֮����ַ�
	public static int setrange(String[] arg, Map<String, String> map) {
		int tag = 0;
		char[] temp = new char[200];
		char[] add = new char[100];
		int num = 0;
		Set<String> set = map.keySet();
		if (arg.length != 4) {
			System.out.println("setrange�����������");
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
			System.out.println("�����������������");
		}
		return tag;    //�����޸ĺ��value�ĳ���
	}

	// mset������ʵ��----ͬʱ��Ӷ��K-V��ֵ��
	public static String mset(String[] arg, Map<String, String> map) {
		String str = "0";
		if ((arg.length - 1) % 2 != 0) {
			System.out.println("mset�����������");
		} else {
			for (int i = 1; i <= arg.length - 1; i = i + 2) {
				map.put(arg[i], arg[i + 1]);
			}
			str = "OK";
		}
		return str;
	}

	// msetnx������ʵ��----ͬʱ�����������ڵ�key,���һ��ʧ�ܣ���ȫ��ʧ�ܷ���0�����򷵻�1
	public static int msetnx(String[] arg, Map<String, String> map) {
		int tag = 0;
		if ((arg.length - 1) % 2 != 0) {
			System.out.println("msetnx�����������");
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

	// get������ʵ��----����key����Ӧ��value
	public static String get(String[] arg, Map<String, String> map) {
		String value = "error!";
		if(arg.length != 2) {
			System.out.println("get�����������");
		} else {
			value = map.get(arg[1]);
		}
		return value;
	}

	// getset������ʵ��----����ԭkey��value��������value
	public static String getset(String[] arg, Map<String, String> map) {
		String value = "error!";
		if(arg.length != 3) {
			System.out.println("getset�����������");
		} else {
			value = map.get(arg[1]);
			map.put(arg[1], arg[2]);
		}
		return value;
	}

	// getrange������ʵ��----����range��Χ�ڵ�valueֵ
	public static String getrange(String[] arg, Map<String, String> map) {
		String str = "error!";
		char[] temp = new char[200];
		char[] tmp = new char[200];
		int num = 0;
		if(arg.length != 4) {
			System.out.println("getrange�����������");
		} else {
			temp = map.get(arg[1]).toCharArray();
			for(int i = Integer.parseInt(arg[2]);i <= Integer.parseInt(arg[3]);i++) {
				tmp[num++] = temp[i];
			}
			str = tmp.toString();
		}
		return str;
	}

	// mget������ʵ��----һ�η��ض��key
	public static String[] mget(String[] arg, Map<String, String> map) {
		String[] mstr = new String[200];
		for(int i = 1;i < arg.length;i++) {
			mstr[i-1] = map.get(arg[i]);
		}
		return mstr;
	}

	// ������
	public static void main(String[] args) {

		// ��ʼ��������
		// initServerConfig();

		//int dbnum = 16; // һ��master���ݿ�Ĭ����16��
		Map<String, String> map = new HashMap<String, String>();

		// �õ���Client�˴�����������
		String getC2Mstring = "mset name1 123 name2 qwe";
		String[] arg = strsplit(getC2Mstring); // �õ��ָ�������
		methods(arg, map);

	}

}
