import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.IOException;

/**
 * 
 * 0으로 초기화된 2차원 세계
 * 블록이 있는 곳은 1로 채움
 * 빗물이 고일 수 있는 곳은 0
 * 왼쪽에서 오른쪽으로 탐색하면서 갈 수 있는 곳은 2로 바꿔가다가 블록인 1을 만나면 멈추고 아랫줄 탐색 
 * 오른쪽에서 왼쪽으로 탐색하면서 갈 수 있는 곳은 2로 바꿔가다가 블록인 1을 만나면 멈추고 아랫줄 탐색
 * 전부 탐색한 후 배열에서 1인 것만 더하면 됨
 * 
 */
public class _14719빗물 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		int h = Integer.parseInt(st.nextToken());
		int w = Integer.parseInt(st.nextToken());
		
		int[][] world = new int[h][w];

		st = new StringTokenizer(br.readLine(), " ");
		
		// 1로 채우기 / 기둥 세우기
		// 아래서부터 거꾸로 채우는 것 주의!
		for(int i=0; i<w; i++) {
			int block = Integer.parseInt(st.nextToken());
			for(int j=h-1; j>=h-block; j--) {
				world[j][i] = 1;
			}
		}
		
		// 왼-> 오 탐색
		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {
				if(world[i][j] != 1) world[i][j] = 2;
				else if(world[i][j] == 1) break;
			}
		}
		
		// 오-> 왼 탐색
		for(int i=h-1; i>=0; i--) {
			for(int j=w-1; j>=0; j--) {
				if(world[i][j] != 1) world[i][j] = 2;
				else if(world[i][j] == 1) break;
			}
		}
		
		// 값이 0인 곳은 빗물이 찰 수 있음!
		int ans = 0;
		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {
				if(world[i][j] == 0) {
					ans++;
				}
			}
		}
		
		System.out.println(ans);
		
	}
}
