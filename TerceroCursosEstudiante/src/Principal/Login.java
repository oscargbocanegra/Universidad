package Principal;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.Statement;
import Utilitarios.*;

public class Login extends JFrame
{
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JTextField txtPassword;
	private Statement st;
	// variaable tipo clase conexion
	Conexion cn;

	// constructor de la clase
	public Login()
	{
		cn = new Conexion();
		pantallaLogin();
		getBotonIngresar();
	}

	void getBotonIngresar()
	{
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.setBounds(119, 96, 89, 23);
		contentPane.add(btnIngresar);
		btnIngresar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					String user = txtUsuario.getText();
					String pw = txtPassword.getText();
					if (user.length() > 0 && pw.length() > 0)
					{
						if (validarUsuario(user, pw) == true)
						{
							PrincipalEst ventanaPrincipal = new PrincipalEst();
							ventanaPrincipal.setVisible(true);
							txtUsuario.setText("");
							txtPassword.setText("");
						} else
						{
							JOptionPane.showMessageDialog(null,"Error usuario incorrecto");
							txtUsuario.setText("");
							txtPassword.setText("");
						}
					}
				} catch (Exception e)
				{
				}
			}
		});
	}

	boolean validarUsuario(String user, String pw)
	{
		Conexion objConexion = new Conexion();
		try
		{
			objConexion.conexion();
			st = objConexion.conn.createStatement();
			String consulta = "select * from usuarios WHERE nombre='"+user+"' AND pwd='"+ pw+"'";
			ResultSet rs = st.executeQuery(consulta);
			if (rs.first())
				return true;
			else
				return false;
			
		} catch (Exception e)
		{
			objConexion.desconectar();
		}
		return false;
	}

	public void pantallaLogin()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 357, 210);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(45, 31, 46, 14);
		contentPane.add(lblNombre);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(119, 28, 201, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(45, 62, 46, 14);
		contentPane.add(lblPassword);

		txtPassword = new JTextField();
		txtPassword.setBounds(119, 59, 201, 20);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);

	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}