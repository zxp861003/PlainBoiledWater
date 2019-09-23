package test;

public class TestMove {

	
	public static void main(String[] args) {
		
		int [] data = {1,4,9,2,12,5,5,6};
		
//		int a1 = 4 << 4;
//		int a2 = 9 << 8;
//		int a3 = 2 << 12;
//		int a4 = 12 << 16;
//		int a5 = 5 << 20 ;
//		int a6 = 5 << 24;
//		int a7 = 6 << 28;
//		
//		int result = a1+a2+a3+a4+a5+a6+a7+1;
		
//		System.out.println(result);
		int aaa= 1700538689;
		
		System.out.println(aaa>>28);
		System.out.println(aaa>>24 & 15);
		System.out.println(aaa>>20 & 15);
		System.out.println(aaa>>16 & 15);
		System.out.println(aaa>>12 & 15);
		System.out.println(aaa>>8 & 15);
		System.out.println(aaa>>4 & 15);
		System.out.println(aaa & 15);
		
		
	}
}
