import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
public class Calculator extends  JFrame {
	
    private JTextArea jTArea;
    private JButton btAdd, btSubtract, btMultiplay, btDivide, btEqual, btClear, btDot, btBack, btMod, btDoubleZ;
    private JButton btNum[] = new JButton[10];
    private double total = 0, temp = 0;
    private boolean operandExist = false;
    private String operator;
    private boolean flag = false;
        
    private Calculator() {
        
        Container view = getContentPane();
        view.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        JPanel jpnl = new JPanel();
        jpnl.setLayout(new GridLayout(5,4,5,5));
 
        jTArea = new JTextArea(2, 20);
        btAdd = new JButton("+");
        btSubtract = new JButton("-");
        btMultiplay = new JButton("*");
        btDivide = new JButton("/");
        btEqual = new JButton("=");
        btClear = new JButton("C");
        btDot = new JButton(".");
        btBack = new JButton("del");
        btMod = new JButton("%");
        btDoubleZ = new JButton("00");
        		
        
        btEqual.setBackground(new Color(0,255,0));
 
        for(int i=0; i<10; i++) {
        	btNum[i] = new JButton( i + "");
        }
 
        view.add(jTArea);
        jpnl.add(btBack);
        jpnl.add(btDoubleZ);
        jpnl.add(btDot);
        jpnl.add(btMod);
        jpnl.add(btNum[7]);
        jpnl.add(btNum[8]);
        jpnl.add(btNum[9]);
        jpnl.add(btAdd);
        jpnl.add(btNum[4]);
        jpnl.add(btNum[5]);
        jpnl.add(btNum[6]);
        jpnl.add(btSubtract);
        jpnl.add(btNum[1]);
        jpnl.add(btNum[2]);
        jpnl.add(btNum[3]);
        jpnl.add(btMultiplay);
        jpnl.add(btClear);
        jpnl.add(btNum[0]);
        jpnl.add(btEqual);
        jpnl.add(btDivide);
        view.add(jpnl);
        
        NumberListener numberListener = new NumberListener();        
        OperatorListener operatorListener = new OperatorListener();
        ClearListener clearListener = new ClearListener();
        EqualListener EqualListener = new EqualListener();
        DoubleZListener doubleZListener = new DoubleZListener();
        EtcListener etcListener = new EtcListener();
        
        for(int i=0; i<btNum.length; i++) {
            btNum[i].addActionListener(numberListener);
        }
        
 
        btAdd.addActionListener(operatorListener);
        btSubtract.addActionListener(operatorListener);
        btMultiplay.addActionListener(operatorListener);
        btDivide.addActionListener(operatorListener);
        btMod.addActionListener(operatorListener);
        btEqual.addActionListener(EqualListener);
        btClear.addActionListener(clearListener);
        btDot.addActionListener(etcListener);
        btDoubleZ.addActionListener(doubleZListener);
        btBack.addActionListener(etcListener);
    }
 
    private class NumberListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {       
            for(int i =0; i<btNum.length;i++) {
            	if(event.getSource() == btNum[i]) {
            		if(jTArea.getText().equals("0"))
            			jTArea.setText("");
            		jTArea.append(i+"");
            		break;
            	}
            }
        }
    }
    
 
    private class OperatorListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                temp = Double.parseDouble(jTArea.getText());
                operation();
 
            } catch(ArithmeticException e) {
                total = 0;
                operator = "/";
                operandExist = true;
                jTArea.setText("");
            } catch(NullPointerException e) {
      
            } catch(NumberFormatException e) {
            	System.out.printf("피연산자 입력안됨");
            	return;
 
            }
            finally {
                if(!operandExist)
                    total = temp;
                
                JButton btSource = (JButton)event.getSource();
                
                if(btSource== btAdd)
                	operator = "+";
                else if(btSource == btSubtract)
                	operator = "-";
                else if(btSource == btMultiplay)
                	operator = "*";
                else if(btSource == btDivide)
                	operator = "/";
                else if(btSource == btMod)
                	operator = "%";
                operandExist = true;
                jTArea.setText("");
            }
        }
    }
    
    private void operation() {
        if(operator.equals("+")) 
            total+=temp;
        else if(operator.equals("-")) 
            total-=temp;
        else if(operator.equals("*")) 
            total*=temp;
        else if(operator.equals("/")) 
            total/=temp;
        else if(operator.equals("%"))
        	total%=temp;
    	
    }
    
    // 사용자 변경 요구사항
    // 변경 방안
    
    private class DoubleZListener implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		if(jTArea.getText().equals("") || jTArea.getText().equals("0") || jTArea.getText().equals("00"))
    			jTArea.setText("0");
    		else
    			jTArea.append("00");
    	}
    }
    
    private class EtcListener implements ActionListener {
    	public void actionPerformed(ActionEvent event) {
    		
    		JButton btSource = (JButton)event.getSource();
    		
    		if(btSource == btBack) {
					String str = jTArea.getText();
					if(!str.equals("")) {
						str = str.substring(0, str.length()-1);
						jTArea.setText(str);
    			}
    		} else if (btSource == btDot) {
    			if(jTArea.getText().equals(""))
        			jTArea.setText("0.");
        		else if(jTArea.getText().contains("."))
        			jTArea.append("");
        		else 
        			jTArea.append(".");
    			
    		}
    	}
    }
    
 
    private class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            jTArea.setText("");
            total = 0;
            temp = 0;
            operandExist = false;
        }
    }
 
    class EqualListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if(temp == 0)
                jTArea.setText(jTArea.getText());
            else {
                try {
                    temp = Double.parseDouble(jTArea.getText());
                    operation();
                    if(total == Math.floor(total))
                    	jTArea.setText((int)total+"");
                    else
                    	jTArea.setText(Math.round(total*100)/100.0+"");
                    
                   
                } catch(Exception e) {
                    jTArea.setText(total+"");
                }
            }
            temp = 0;
            operandExist = false;
        }
    }
 
    public Dimension getPreferredSize() {
        return new Dimension(270,250);
    }
        
 
    public static void main(String args[]) {
        Calculator cal = new Calculator();
        
        cal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cal.setTitle("계산기");
        cal.setForeground(Color.black);
        cal.setBackground(Color.lightGray);
        cal.setSize(cal.getPreferredSize());
        cal.setResizable(false);
        cal.setVisible(true);
    }
}