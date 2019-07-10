import java.io.*;
import java.util.*;

public class Solution {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int ii=1; ii<=T; ii++)
		{
			String str = br.readLine();
			long result = 1;
			
			if(str.length() == 1)
			{
				System.out.println("#"+ii+" "+result);
				continue;
			}
				
			if(str.charAt(0) != str.charAt(1))
				result = 2;
			
			for(int i=1; i<str.length()-1; i++)
			{
				char a = str.charAt(i-1);
				char b = str.charAt(i);
				char c = str.charAt(i+1);
				
				HashMap<Character, Integer> map = new HashMap<Character, Integer>();
				map.put(a, 1);	map.put(b, 2);	map.put(c, 3);
				
				result *= map.size();
				result %= 1000000007;
			}
			
			if(str.charAt(str.length()-2) != str.charAt(str.length()-1))
				result *= 2;
			
			System.out.println("#"+ii+" "+result%1000000007);
		}	
		br.close();
	}
}