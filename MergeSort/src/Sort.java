import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Sort extends JPanel {
	String str = "";

	void MergeIt(int A[], int p, int q, int r) {
		int n1 = q - p + 1;
		int n2 = r - q;

		int L[] = new int[n1 + 1];
		int R[] = new int[n2 + 1];
		int i;
		int j;
		for (i = 0; i < n1; i++)
			L[i] = A[p + i - 1];
		for (j = 0; j < n2; j++)
			R[j] = A[q + j];
		L[n1] = 999999999;
		R[n2] = 999999999;
		i = 0;
		j = 0;
		for (int k = p - 1; k < r; k++)
			if (L[i] <= R[j]) {
				A[k] = L[i];
				i++;
			} else {
				A[k] = R[j];
				j++;
			}
	}

	void MergeSort(int A[], int p, int r) {

		if (p < r) {
			int q = (p + r) / 2;
			MergeSort(A, p, q);
			str = str + "after MergeSort(A," + p + "," + q + "):" + "\n";
			for (int i : A) {
				str = str + i + " ";
			}
			str = str + "\n";
			MergeSort(A, q + 1, r);
			str = str + "after MergeSort(A," + q + 1 + "," + r + "):" + "\n";

			for (int i : A) {
				str = str + i + " ";
			}
			str = str + "\n";

			MergeIt(A, p, q, r);
			str = str + "after MergeIt(A," + p + "," + q + "," + r + "):"
					+ "\n";
			for (int i : A) {
				str = str + i + " ";
			}
			str = str + "\n";
		}
	}

	public String n = "";
	private JTextField textField;

	public Sort() {
		setPreferredSize(new Dimension(400, 550));
		JButton btnOk = new JButton("ok~~~~");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, btnOk, 44,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnOk, 225,
				SpringLayout.WEST, this);
		setLayout(springLayout);

		JTextPane textPane = new JTextPane();
		springLayout.putConstraint(SpringLayout.NORTH, textPane, 8,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, textPane, 168,
				SpringLayout.WEST, this);

		add(textPane);
		add(btnOk);

		JTextPane textPane_2 = new JTextPane();
		springLayout.putConstraint(SpringLayout.EAST, textPane_2, -6,
				SpringLayout.WEST, btnOk);
		springLayout.putConstraint(SpringLayout.SOUTH, textPane, -13,
				SpringLayout.NORTH, textPane_2);
		springLayout.putConstraint(SpringLayout.EAST, textPane, 0,
				SpringLayout.EAST, textPane_2);
		springLayout.putConstraint(SpringLayout.NORTH, textPane_2, 44,
				SpringLayout.NORTH, this);
		add(textPane_2);

		JLabel lblNewLabel = new JLabel("size of A");
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 89,
				SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, 0,
				SpringLayout.SOUTH, textPane);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, -6,
				SpringLayout.WEST, textPane);
		add(lblNewLabel);

		JLabel label = new JLabel("A[  ]");
		springLayout.putConstraint(SpringLayout.WEST, textPane_2, 6,
				SpringLayout.EAST, label);
		springLayout.putConstraint(SpringLayout.NORTH, label, 48,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, label, 10,
				SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, label, -359,
				SpringLayout.EAST, this);
		add(label);

		JTextArea textArea = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 144,
				SpringLayout.SOUTH, btnOk);
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, -228,
				SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, textArea, -10,
				SpringLayout.EAST, this);
		// add(textArea);

		JScrollPane scrollPane = new JScrollPane(textArea);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 39,
				SpringLayout.SOUTH, btnOk);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0,
				SpringLayout.WEST, textPane_2);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -35,
				SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -41,
				SpringLayout.EAST, this);
		add(scrollPane);

		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int n = Integer.parseInt(textPane.getText());
				int[] A = new int[n];
				String input = textPane_2.getText();
				String[] in = input.split(" ");
				for (int i = 0; i < n; i++) {
					A[i] = Integer.parseInt(in[i]);
				}
				MergeSort(A, 1, n);
				textArea.setText(str);
				str = "";
			}
		});
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("                 sort");
		frame.setSize(400, 550);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(new Sort());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}
}
