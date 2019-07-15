import java.util.*;
import java.io.*;

//1시간 28분
public class Solution {

	static int D, W, K;
	static int film[][];
	static int original_film[][];
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for(int iiiii=1; iiiii<=T; iiiii++)
		{
			//입력 받기
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			D = Integer.parseInt(st.nextToken());	//두께
			W = Integer.parseInt(st.nextToken());	//가로크기
			K = Integer.parseInt(st.nextToken());	//합격기준
			
			film = new int[D][W];
			original_film = new int[D][W];
			
			for(int i=0; i<D; i++)
			{
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<W; j++)
				{
					film[i][j] = Integer.parseInt(st.nextToken());
					original_film[i][j] = film[i][j];
				}
			}
			
			//조합으로 탐색하기
			int num = 0;		//조합 고를 수
			ArrayList<Injection> list = new ArrayList<Injection>();		//조합을 담을 리스트
			
			while(num<D)		//조합시 동시에 고를 수 있는 막의 최대 수 : D개
			{
				if(Combination(0, list, num) == true)		break;		//검사기준 통과하면 빠져나오기
				num++;
			}		
			
			System.out.println("#"+iiiii+" "+num);		//결과 출력
		}
	}

	//파라미터 : 조합 인덱스, 조합을 담을 배열, 최대 고를 수
	static boolean Combination(int idx, ArrayList<Injection> list, int n)		//n개의 수를 고르는 조합
	{
		boolean result = false;
		
		if(n == 0)			//0이면 바로 이거함
		{
			if(check() == true)
				return true;
			else	return false;
		}
		
		if(n == list.size())		//n개의 막을 다 골랐을 경우
		{
			//직접 넣어보기 : list에는 고를 인덱스들과 약품 상태가 들어있음
			
			for(Injection a : list)
			{
				for(int j=0; j<W; j++)
					film[a.no][j] = a.stat;
			}
			
//			//for test
//			System.out.println("n : " + n);
//			for(int i=0; i<D; i++)
//			{
//				for(int j=0; j<W; j++)
//					System.out.print(film[i][j]+" ");
//				System.out.println();
//			}
//			System.out.println();
			
			if(check() == true)
				return true;
			
			//필름 원상태로 돌리기
			for(Injection a : list)
			{
				for(int j=0; j<W; j++)
					film[a.no][j] = original_film[a.no][j];
			}
			
			return false;
		}
		
		for(int i=idx; i<D; i++)
		{
			//같은인덱스, A약물
			Injection temp = new Injection(i, 0);
			list.add(temp);
			result = Combination(i+1, list, n);
			if(result == true)	return true;
			list.remove(list.size()-1);
			
			//같은인덱스, B약물
			temp = new Injection(i, 1);
			list.add(temp);
			result = Combination(i+1, list, n);
			if(result == true)	return true;
			list.remove(list.size()-1);
		}
		
		return result;
	}
	
	static boolean check()				//합격기준을 만족하는지 검사. 필름 전체가 기준을 만족하면 true return
	{
		boolean result = true;
		
		for(int j=0; j<W; j++)			//세로방향에서 K개 이상 연속적인 특징이 있으면 통과
		{
			int prev = film[0][j];		//비교하기 위한 초기값
			int count = 1;
			
			for(int i=1; i<D; i++)
			{
				int cur = film[i][j];	//현재 값
				if(prev == cur)			//연속되었다면
				{
					count++;
					if(count == K)		//해당 면이 기준을 만족하면 다음 column으로 넘어가서 검사
						break;
				}
				else			//연속되지 않았다면
				{
					//초기화
					prev = cur;
					count = 1;
				}
			}//한 column 끝		
			if(count != K)		//하나의 column라도 만족 못하면 실패
				return false;
		}
		
		return result;
	}
}

class Injection
{
	int no;
	int stat;	//약물 특성
	Injection(){};
	Injection(int no, int stat)
	{
		this.no= no;	this.stat = stat;
	}
}
