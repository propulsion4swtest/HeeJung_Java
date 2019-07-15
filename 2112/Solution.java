import java.util.*;
import java.io.*;

public class Solution {

	static int D, W, K;
	static int film[][];
	static int original_film[][];
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for(int iiiii=1; iiiii<=T; iiiii++)
		{
			//�Է� �ޱ�
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			D = Integer.parseInt(st.nextToken());	//�β�
			W = Integer.parseInt(st.nextToken());	//����ũ��
			K = Integer.parseInt(st.nextToken());	//�հݱ���
			
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
			
			//�������� Ž���ϱ�
			int num = 0;		//���� �� ��
			ArrayList<Injection> list = new ArrayList<Injection>();		//������ ���� ����Ʈ
			
			while(num<D)		//���ս� ���ÿ� �� �� �ִ� ���� �ִ� �� : D��
			{
				if(Combination(0, list, num) == true)		break;		//�˻���� ����ϸ� ����������
				num++;
			}		
			
			System.out.println("#"+iiiii+" "+num);		//��� ���
		}
	}

	//�Ķ���� : ���� �ε���, ������ ���� �迭, �ִ� �� ��
	static boolean Combination(int idx, ArrayList<Injection> list, int n)		//n���� ���� ���� ����
	{
		boolean result = false;
		
		if(n == 0)			//0�̸� �ٷ� �̰���
		{
			if(check() == true)
				return true;
			else	return false;
		}
		
		if(n == list.size())		//n���� ���� �� ����� ���
		{
			//���� �־�� : list���� �� �ε������ ��ǰ ���°� �������
			
			for(Injection a : list)
			{
				for(int j=0; j<W; j++)
					film[a.no][j] = a.stat;
			}
			
			
			if(check() == true)
				return true;
			
			//�ʸ� �����·� ������
			for(Injection a : list)
			{
				for(int j=0; j<W; j++)
					film[a.no][j] = original_film[a.no][j];
			}
			
			return false;
		}
		
		for(int i=idx; i<D; i++)
		{
			//�����ε���, A�๰
			Injection temp = new Injection(i, 0);
			list.add(temp);
			result = Combination(i+1, list, n);
			if(result == true)	return true;
			list.remove(list.size()-1);
			
			//�����ε���, B�๰
			temp = new Injection(i, 1);
			list.add(temp);
			result = Combination(i+1, list, n);
			if(result == true)	return true;
			list.remove(list.size()-1);
		}
		
		return result;
	}
	
	static boolean check()				//�հݱ����� �����ϴ��� �˻�. �ʸ� ��ü�� ������ �����ϸ� true return
	{
		boolean result = true;
		
		for(int j=0; j<W; j++)			//���ι��⿡�� K�� �̻� �������� Ư¡�� ������ ���
		{
			int prev = film[0][j];		//���ϱ� ���� �ʱⰪ
			int count = 1;
			
			for(int i=1; i<D; i++)
			{
				int cur = film[i][j];	//���� ��
				if(prev == cur)			//���ӵǾ��ٸ�
				{
					count++;
					if(count == K)		//�ش� ���� ������ �����ϸ� ���� column���� �Ѿ�� �˻�
						break;
				}
				else			//���ӵ��� �ʾҴٸ�
				{
					//�ʱ�ȭ
					prev = cur;
					count = 1;
				}
			}//�� column ��		
			if(count != K)		//�ϳ��� column�� ���� ���ϸ� ����
				return false;
		}
		
		return result;
	}
}

class Injection
{
	int no;
	int stat;	//�๰ Ư��
	Injection(){};
	Injection(int no, int stat)
	{
		this.no= no;	this.stat = stat;
	}
}
