package test;


public class TestString {
	public static void main(String[] args) {
		StringBuilder str = new StringBuilder();
		if (str != null) {
			//System.out.println("not null");
		}
		//System.out.println(StringUtils.isNotEmpty(str.toString()));
		//System.out.println(str.toString());
		String re = "E010102 1  1  1  00000000000025 170000800002478024176.0      300.89";
		
		int p = re.indexOf("170000800002478");
		System.out.println(re.substring(p));
		System.out.println(re.substring(p + 17, p + 29));
		System.out.println(re.substring(p + 29));
	}
}
