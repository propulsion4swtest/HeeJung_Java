import java.io.*;
import java.util.*;

public class Main {

	static int MAX_PERMU = 40320;	//8!의 수
	static int permu_list[][] = new int[MAX_PERMU][9];
	static int total_count = 0;		//순열 갯수 세기
	
	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine().trim());		//이닝
		int score[][] = new int[N][9];		//n번째 이닝에서 할 수 있는 점수
		for(int i=0; i<N; i++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0; j<9; j++)
				score[i][j] = Integer.parseInt(st.nextToken());
		}
		
		Permutation(new int[8], new boolean[9], 0);
		
		int max = Integer.MIN_VALUE;
		
		for(int cur=0; cur<MAX_PERMU; cur++)	//순열 다 탐색
		{
			int cur_score = 0;	//현재 점수
			int inning = 0;		//현재 이닝
			boolean base[]= new boolean[4];	//1루, 2루, 3루
			int out = 0;		//아웃 수
			int player = 0;		//플레이어 번호
			
			while(inning < N)	//이닝 내에서만 하자
			{
				int state = score[inning][permu_list[cur][player]];	//현재 선수가 얼마나 치는지
				
				if(state == 0)
				{
					out++;
				}
				else if(state == 1)
				{
					if(base[3] == true)	{base[3] = false;	cur_score++;}		//3루에 사람이 있으면 점수 증가
					if(base[2] == true)	{base[2] = false;	base[3] = true;}	//2루에 사람이 있으면 3루로 이동
					if(base[1] == true)	{base[1] = false;	base[2] = true;}	//1루에 사람이 있으면 2루로 이동
					base[1] = true;		//1루에 사람 놓기
				}
				else if(state == 2)
				{
					if(base[3] == true)	{base[3] = false;	cur_score++;}
					if(base[2] == true)	{base[2] = false;	cur_score++;}
					if(base[1] == true)	{base[1] = false;	base[3] = true;}
					base[2] = true;
				}
				else if(state == 3)
				{
					if(base[3] == true)	{base[3] = false;	cur_score++;}
					if(base[2] == true)	{base[2] = false;	cur_score++;}
					if(base[1] == true)	{base[1] = false;	cur_score++;}
					base[3] = true;
				}
				else	//state == 4
				{
					if(base[3] == true)	{base[3] = false;	cur_score++;}
					if(base[2] == true)	{base[2] = false;	cur_score++;}
					if(base[1] == true)	{base[1] = false;	cur_score++;}
					cur_score++;
				}
				
				if(out == 3)
				{
					inning++;	out = 0;	//아웃 3번이면 아웃 초기화하고 이닝 넘기기
				}
				
				player++;		//다음 플레이어
				if(player == 9)
					player = 0;
			}
			
			max = Math.max(max, cur_score);
			
		}
		
		System.out.println(max);
		
		br.close();
	}

	//8!를 미리 구해놓기
	static void Permutation(int[] permu, boolean visit[], int count) {	//순열 담을 리스트, 방문 배열, 인덱스
		if(count == 8)		//1의 위치는 정해져 있으므로
		{
			//배열 복사
			for(int i=0; i<3; i++)
				permu_list[total_count][i] = permu[i];
			
			permu_list[total_count][3] = 0;
			
			for(int i=3; i<8; i++)
				permu_list[total_count][i+1] = permu[i];
			
			total_count++;	//순열의 갯수 증가
		}
		
		for(int i=1; i<9; i++)
		{
			if(visit[i] == true)	continue;
			
			visit[i] = true;
			permu[count] = i;
			Permutation(permu, visit, count+1);
			visit[i] = false;
		}
	}
}
