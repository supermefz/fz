package com.hyw.SDS;

public class SDS {
	int len=0;
	int free=0;
	String str= null;
	public SDS(int len, int free,String str) {
		// TODO �Զ����ɵĹ��캯�����
		 this.free = free;
		 this.str = str;
		 this.len = len;
	}
	//����������һ��ֻ�����˿��ַ��� "" �� sds
	public SDS(){
		 this.free = 0;
		 this.str = "";
		 this.len = 0;
	 }
	// ���ݸ����ַ��� str ������һ������ͬ���ַ����� sds
	public SDS(String str){
		 this.free = 0;
		 this.str = str;
		 this.len = str.length();
	 }
	//�ͷŸ����� sds���ڴ�
	 public void sdsfree(SDS s){
		 if (s != null) {
			try {
				s.finalize();
			} catch (Throwable e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	 }
	 
	 public void sdsclear(SDS s){
		 
		  s.free = s.str.length();
		  s.str = "";
		  s.len = 0;
	 }
}
