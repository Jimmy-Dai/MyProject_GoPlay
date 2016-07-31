package GoPlay.MyProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GoDemo1 {
	private static int boardSize;
	private String[][] board;

	public void initBoard(int size) {
		this.boardSize = size;
		board = new String[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				board[i][j] = "��";
			}
		}
	}

	public void printBoard() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println("\n");
		}
	}

	public static void main(String[] args) throws IOException {
		GoDemo1 gd = new GoDemo1();
		System.out.print("���������̴�С��");
		Scanner sc = new Scanner(System.in);
		String size = sc.nextLine();
		while (!Pattern.matches("[1-9]+\\d*", size)) {
			System.out.print("����������������䣺");
			size = sc.nextLine();
		}
		int sizeInt = Integer.parseInt(size);
		gd.initBoard(sizeInt);
		gd.printBoard();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputStr = null;
		System.out.print("��������������x,y:");
		while ((inputStr = br.readLine()) != null) {
			// if(!Pattern.matches("[1-sizeInt][0-sizeInt]*,[1-sizeInt][0-sizeInt]*",
			// inputStr)) {
			// System.out.print("��������������������䣺");
			// continue;
			// }
			if (!Pattern.matches("\\d+\\d*,\\d+\\d*", inputStr)) {
				System.out.print("��������������������䣺");
				continue;
			}
			String[] intPos = inputStr.split(",");
			int xPos = Integer.parseInt(intPos[0]);
			int yPos = Integer.parseInt(intPos[1]);
			if (!(xPos > 0 && xPos <= sizeInt) || !(yPos > 0 && yPos <= sizeInt)) {
				System.out.print("���ӳ��磬�����䣺");
				continue;
			} else if (gd.board[xPos - 1][yPos - 1] != "��") {
				gd.board[xPos - 1][yPos - 1] = "��";
			} else {
				System.out.print("�õ��������ӣ������䣺");
				continue;
			}
			gd.printBoard();
			System.out.print("��һ������:");
		}
	}
}
