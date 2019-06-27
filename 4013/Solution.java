import java.io.*;
import java.util.*;

public class Solution {

	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int ii=1; ii<=T; ii++)
		{
			int K = Integer.parseInt(br.readLine());
			
			int arr[][] = new int[5][8];	//숫자 편하게 세려고 1~4 사용. 자성 정보를 담을 배열
			
			//자성 정보 입력
			for(int i=1; i<=4; i++)
			{
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int j=0; j<8; j++)
					arr[i][j] = Integer.parseInt(st.nextToken());

			}//자성 정보 입력 끝
			
			//K번 반복하며 자석 회전
			for(int i=0; i<K; i++)
			{
				StringTokenizer st = new StringTokenizer(br.readLine());
				int num = Integer.parseInt(st.nextToken());		//돌릴 자석
				int dir = Integer.parseInt(st.nextToken());		//자석 돌릴 방향
				
				//자석끼리 맞닿는 부분 : 1자석 2 - 2자석 6 / 2자석 2 - 3자석 6 / 3자석 2 - 4자석 6
				//1-3, 2-4끼리 같은 방향으로 회전하고, 각 그룹은 서로 반대 방향으로 회전
				int flag[] = new int[5];	//회전 여부. 0이면 회전 안하고, 1이나 -1이면 회전
				
				switch(num)
				{
				case 1:
					flag[1] = dir;			//1의 회전방향 설정
					
					if(arr[1][2] == arr[2][6])	break;	//2번과 같은 극성이면 탈출
					flag[2] = dir*(-1);		//다른 극성이면 1과 반대방향으로 회전
					
					if(arr[2][2] == arr[3][6])	break;
					flag[3] = dir;
					
					if(arr[3][2] == arr[4][6])	break;
					flag[4] = dir*(-1);			break;
					
				case 2:
					flag[2] = dir;
					
					if(arr[2][6] != arr[1][2])	flag[1] = dir*(-1);	//1 체크
					
					if(arr[2][2] == arr[3][6])	break;
					flag[3] = dir*(-1);
					
					if(arr[3][2] == arr[4][6])	break;
					flag[4] = dir;				break;
					
				case 3:
					flag[3] = dir;
					
					if(arr[3][2] != arr[4][6])	flag[4] = dir*(-1);	//4 체크
					
					if(arr[2][2] == arr[3][6])	break;
					flag[2] = dir*(-1);
					
					if(arr[1][2] == arr[2][6])	break;
					flag[1] = dir;				break;
					
				case 4:
					flag[4] = dir;			//1의 회전방향 설정
					
					if(arr[4][6] == arr[3][2])	break;	//2번과 같은 극성이면 탈출
					flag[3] = dir*(-1);		//다른 극성이면 1과 반대방향으로 회전
					
					if(arr[3][6] == arr[2][2])	break;
					flag[2] = dir;
					
					if(arr[2][6] == arr[1][2])	break;
					flag[1] = dir*(-1);			break;
				}
				
				//회전 정보에 따라 회전
				for(int j=1; j<=4; j++)
				{
					if(flag[j] != 0)
						rotate(flag[j], arr[j]);
				}
			}
			
			//결과 계산
			int sum = 0;
			for(int i=1; i<=4; i++)
				sum += Math.pow(2, (i-1)) * arr[i][0];
			
			System.out.println("#"+ii+" "+sum);
		}
	}
	
	//방향에 따른 회전
	static public void rotate(int dir, int arr[])
	{
		if(dir == 1)
			clockwise(arr);
		else
			counterClockwise(arr);
	}
	//배열 시계방향 회전
	static public void clockwise(int[] arr)
	{
		int temp = arr[7];
		for(int i=7; i>0; i--)
			arr[i] = arr[i-1];
		arr[0] = temp;

	}
	//배열 반시계방향 회전
	static public void counterClockwise(int[] arr)
	{
		int temp = arr[0];
		for(int i=0; i<7; i++)
			arr[i] = arr[i+1];
		arr[7] = temp;
	}
}