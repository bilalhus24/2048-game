package proj2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class game2048 implements KeyListener{
	private int score = 0;
	JTextField text;
	private JFrame frame;
	private int[][] board = new int[4][4];
	private JPanel gamePanel;
	private Tiles[] tiles = new Tiles[16];
	
	game2048(){
		frame = new JFrame("2048");
		gamePanel = new JPanel(new GridLayout(4, 4));

		text = new JTextField();
        text.setHorizontalAlignment(JTextField.CENTER);
        text.setEditable(false);
        text.setFont(new Font("Arial", Font.BOLD, 24));
        text.setText("Score: " + score);
        text.setFocusable(false);
        
        
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 725);
        frame.setLayout(new BorderLayout());
        
        frame.addKeyListener(this);

        
        
        setUp();
        
        gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.add(gamePanel, BorderLayout.CENTER);
        
        frame.setVisible(true);
	}
	
	public void setUp() {
		for(int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tiles(0);
			gamePanel.add(tiles[i]);
		}
		frame.add(text, BorderLayout.NORTH);
		
		//filling in squares first
		add();
		add();
	}
	
	public boolean checkForEmpty() {	//checks if there are any empty squares
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(board[i][j] == 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void add() {
		isGameOver();
		//check if theres no possible squares left for numbers to be added
		if(!checkForEmpty()) {
			return;
		} else {
			//90% of new squares added are 2's, rest 10% is 4's
	        int findNum = (int) (Math.random() * 10);
	        int findSpace;
	        do {
	            findSpace = (int) (Math.random() * 16);
	        } while (board[findSpace / 4][findSpace % 4] != 0);
	        
	        board[findSpace/4][findSpace%4] = findNum < 9 ? 2 : 4;   
	        drawSquares(findSpace/4, findSpace%4);
		}
	}
	
	public void drawSquares() {
	    int z = 0;
	    for(int i = 0; i < 4; i++) {
	        for(int j = 0; j < 4; j++) {
	            tiles[z].setValue(board[i][j]);
	        }
	    }
	    text.setText("Score: " + score);
	    gamePanel.revalidate();
	    gamePanel.repaint();
	    isGameOver();
	}

	public void drawSquares(int a, int b) {
	    int z = 0;
	    for(int i = 0; i < 4; i++) {
	        for(int j = 0; j < 4; j++) {
	            tiles[z].setValue(board[i][j]);
	            if(a == i && j == b) {
	                tiles[z].added();
	            }
	            z++;
	        }
	    }
	    text.setText("Score: " + score);
	    gamePanel.revalidate();
	    gamePanel.repaint();
	    isGameOver();
	}

	
	public void isGameOver() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				//game is won
				if(board[i][j] == 2048) {
					JOptionPane.showMessageDialog(null, "You won!", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
		}
		boolean possible = false;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				//check if theres any empty tiles first
				if(board[i][j] == 0) {
					possible = true;
				}
				
				//check if no more possible moves
				if(i > 0) {
					if(board[i][j] == board[i - 1][j]) {
						possible = true;
					}
				}
				if(i < 3) {
					if(board[i][j] == board[i + 1][j]) {
						possible = true;
					}
				}
				if(j > 0) {
					if(board[i][j] == board[i][j - 1]) {
						possible = true;
					}
				}
				if(j < 3) {
					if(board[i][j] == board[i][j + 1]) {
						possible = true;
					}
				}
				if(possible) {
					return;
				}
			}
		}
		if(!possible) {
			JOptionPane.showMessageDialog(null, "You lost. No possible moves left.", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
		}
		return;
	}
	
	public void moveUp() {
	    boolean legalMove = false;

	    for (int j = 0; j < 4; j++) {
	        for (int i = 0; i < 3; i++) {
	            if (board[i][j] == 0) {
	                for (int k = i + 1; k < 4; k++) {
	                    if (board[k][j] != 0) {
	                        board[i][j] = board[k][j];
	                        board[k][j] = 0;
	                        legalMove = true;
	                        break;
	                    }
	                }
	            }
	        }
	    }

	    for (int j = 0; j < 4; j++) {
	        for (int i = 0; i < 3; i++) {
	            if (board[i][j] == board[i + 1][j] && board[i][j] != 0) {
	            	score += board[i][j];
	            	board[i][j] *= 2;
	                board[i + 1][j] = 0;
	                legalMove = true;
	            }
	        }
	    }

	    for (int j = 0; j < 4; j++) {
	        for (int i = 0; i < 3; i++) {
	            if (board[i][j] == 0) {
	                for (int k = i + 1; k < 4; k++) {
	                    if (board[k][j] != 0) {
	                        board[i][j] = board[k][j];
	                        board[k][j] = 0;
	                        legalMove = true;
	                        break;
	                    }
	                }
	            }
	        }
	    }

	    if (legalMove) {
	        add();
	    } else {
	        drawSquares();
	    }
	}

	public void moveDown() {
	    boolean legalMove = false;

	    for (int j = 0; j < 4; j++) {
	        for (int i = 3; i > 0; i--) {
	            if (board[i][j] == 0) {
	                for (int k = i - 1; k >= 0; k--) {
	                    if (board[k][j] != 0) {
	                        board[i][j] = board[k][j];
	                        board[k][j] = 0;
	                        legalMove = true;
	                        break;
	                    }
	                }
	            }
	        }
	    }

	    for (int j = 0; j < 4; j++) {
	        for (int i = 3; i > 0; i--) {
	            if (board[i][j] == board[i - 1][j] && board[i][j] != 0) {
	            	score += board[i][j];
	                board[i][j] *= 2;
	                board[i - 1][j] = 0;
	                legalMove = true;
	            }
	        }
	    }

	    for (int j = 0; j < 4; j++) {
	        for (int i = 3; i > 0; i--) {
	            if (board[i][j] == 0) {
	                for (int k = i - 1; k >= 0; k--) {
	                    if (board[k][j] != 0) {
	                        board[i][j] = board[k][j];
	                        board[k][j] = 0;
	                        legalMove = true;
	                        break;
	                    }
	                }
	            }
	        }
	    }

	    if (legalMove) {
	        add();
	    } else {
	        drawSquares();
	    }
	}

	public void moveRight() {
		boolean legalMove = false;
//		for(int j = 0; j < 4; j++) {
//			for(int i = 3; i > 0; i--) {
//				if(board[j][i] == 0) {
//					board[j][i] = board[j][i - 1];
//					board[j][i - 1] = 0;
//					legalMove = true;
//				} else if (board[j][i] == board[j][i - 1]) {
//					board[j][i] *= 2;
//					board[j][i - 1] = 0;
//					legalMove = true;
//				} else if (board[j][i] != board[j][i - 1]) {
//					continue;
//				}
//			}
//		}
		for(int j = 0; j < 4; j++) {
			for(int i = 3; i > 0; i--) {
				if(board[j][i] == 0) {
					for(int k = i - 1; k >= 0; k--) {
						if (board[j][k] != 0) {
	                        board[j][i] = board[j][k];
	                        board[j][k] = 0;
	                        legalMove = true;
	                        break;
	                    }
					}
				}
			}
		}
		for(int j = 0; j < 4; j++) {
			for(int i = 3; i > 0; i--) {
				if(board[j][i] == board[j][i-1]) {
					score += board[i][j];
					board[j][i] *= 2;
					board[j][i-1] = 0;
					legalMove = true;
				}
			}
		}
		for (int j = 0; j < 4; j++) {
	        for (int i = 3; i > 0; i--) {
	            if (board[j][i] == 0) {
	                for (int k = i - 1; k >= 0; k--) {
	                    if (board[j][k] != 0) {
	                        board[j][i] = board[j][k];
	                        board[j][k] = 0;
	                        legalMove = true;
	                        break;
	                    }
	                }
	            }
	        }
	    }
		if(legalMove) {
			add();
		} else {
			drawSquares();
		}
	}
	public void moveLeft() {
		boolean legalMove = false;
//		for(int j = 0; j < 4; j++) {
//			for(int i = 0; i < 3; i++) {
//				if(board[j][i] == 0) {
//					board[j][i] = board[j][i + 1];
//					board[j][i + 1] = 0;
//					legalMove = true;
//				} else if (board[j][i] == board[j][i + 1]) {
//					board[j][i] *= 2;
//					board[j][i + 1] = 0;
//					legalMove = true;
//				} else if (board[j][i] != board[j][i + 1]) {
//					continue;
//				}
//			}
//		}
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < 3; i++) {
				if(board[j][i] == 0) {
					for(int k = i + 1; k < 4; k++) {
						if (board[j][k] != 0) {
	                        board[j][i] = board[j][k];
	                        board[j][k] = 0;
	                        legalMove = true;
	                        break;
	                    }
					}
				}
			}
		}
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < 3; i++) {
				if(board[j][i] == board[j][i+1]) {
					score += board[i][j];
					board[j][i] *= 2;
					board[j][i+1] = 0;
					legalMove = true;
				}
			}
		}
		for (int j = 0; j < 4; j++) {
	        for (int i = 0; i < 3; i++) {
	            if (board[j][i] == 0) {
	                for (int k = i + 1; k < 4; k++) {
	                    if (board[j][k] != 0) {
	                        board[j][i] = board[j][k];
	                        board[j][k] = 0;
	                        legalMove = true;
	                        break;
	                    }
	                }
	            }
	        }
	    }
		if(legalMove) {
			add();
		} else {
			drawSquares();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//keytyped = invoked when a key is typed. uses keychar, char output
			switch(e.getKeyChar()) {
			case 'a':
				moveLeft();
				break;
			case 's':
				moveDown();
				break;
			case 'd':
				moveRight();
				break;
			case 'w':
				moveUp();
				break;
			}
	}

	@Override
	public void keyPressed(KeyEvent e) {	//works for arrow keys, wont work in keytyped
		//keypressed = invoked when a physical key is pressed down. uses keycode, int output
		switch(e.getKeyCode()) {
			case 37://left
				moveLeft();
				break;
			case 40://down
				moveDown();
				break;
			case 39://right
				moveRight();
				break;
			case 38://up
				moveUp();
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
