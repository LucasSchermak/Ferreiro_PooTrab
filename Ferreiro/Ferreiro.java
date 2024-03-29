import javax.swing.JOptionPane;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Ferreiro {

	private ArrayList<Armas> arma;

	public String[] leValores(String[] dadosIn) {
		String [] dadosOut = new String[dadosIn.length];
		for(int i = 0;i < dadosIn.length; i++)
			dadosOut[i] = JOptionPane.showInputDialog("Entre com " + dadosIn[i]+":");

		return dadosOut;
	}
	public Lamina leLamina() {
		String [] valores = new String [2];
		String [] nomeVal = {"Nome","Arma One Handed(1) ou Two Handed(2):"};
		valores = leValores(nomeVal);
		String [] valores2 = new String [3];
		String [] nomeVal2 = {"Tamanho do(a) "+valores[0],"Material do(a) "+valores[0],
		"Tipo da Lamina"};
		valores2 = leValores(nomeVal2);

		Lamina laminaArma = new Lamina(valores[0],valores[1],
				valores2[0],valores2[1],valores2[2]);
		return laminaArma;

	}
	public Empunhadura leEmpunhadura() {
		String [] valores = new String [1];
		String [] nomeVal = {"Material da empunhadura"};
		valores = leValores(nomeVal);

		Empunhadura empArma = new Empunhadura(valores[0]);
		return empArma;
	}
	public Estilo leEstilo() {
		String [] valores = new String [2];
		String [] nomeVal = {"Nome","Arma One Handed(1) ou Two Handed(2):"};
		valores = leValores(nomeVal);
		String [] valores2 = new String [2];
		String [] nomeVal2 = {"Material do(a) "+valores[0],"Tamanho do(a) "+valores[0] };
		valores2 = leValores(nomeVal2);

		Estilo estArma = new Estilo(valores[0],valores[1],valores2[0],valores2[1]);
		return estArma;
	}
	public Corda leCorda() {
		String [] valores = new String [1];
		String [] nomeVal = {"Material da corda"};
		valores = leValores(nomeVal);

		Corda cordaArma = new Corda(valores[0]);
		return cordaArma;
	}
	public Projetil leProjetil() {
		String [] valores = new String [1];
		String [] nomeVal = {"Projetil"};
		valores = leValores(nomeVal);

		Projetil projArma = new Projetil(valores[0]);
		return projArma;
	}

	public void menuLoja() {
		arma = new ArrayList<Armas>();

		String menu = "";
		String entrada;
		int opc1, opc2;

		do {
			menu = "Ferraria\n" +
					"Op��es:\n" + 
					"1. Entrar com a Arma\n" +
					"2. Exibir Arma\n" +
					"3. Descartar Arma\n" +
					"4. Salvar Arma\n" +
					"5. Recuperar Arma\n" +
					"9. Sair";
			entrada = JOptionPane.showInputDialog (menu + "\n\n");

			while (!menuNumValido(entrada)) {
				entrada = JOptionPane.showInputDialog(null, menu + 
						"\n\nEntrada inv�lida! Digite um n�mero inteiro.");
			}
			opc1 = new Integer(entrada);

			switch (opc1) {
			case 1:// Entrar dados
				menu = "Entrada de Arma\n" +
						"Op��es:\n" + 
						"1. Melee\n" +
						"2. Ranged\n";
				
				entrada = JOptionPane.showInputDialog (menu + "\n\n");
				while (!menuNumValido(entrada)) {
					entrada = JOptionPane.showInputDialog(null, menu + 
							"\n\nEntrada inv�lida! Digite um n�mero inteiro.");
				}
				opc2 = new Integer(entrada);

				switch (opc2){
				case 1:
				arma.add((Armas)leLamina());
				arma.add((Armas)leEmpunhadura());
				break;
				case 2:
				arma.add((Armas)leEstilo());
				arma.add((Armas)leCorda());
				arma.add((Armas)leProjetil());
				break;
				default: 
					JOptionPane.showMessageDialog(null,"Arma para entrada N�O escolhida!");
					break;
				}
			case 2: // Exibir dados
				if (arma.size() == 0) {
					JOptionPane.showMessageDialog(null,"Entre com a Arma primeiramente");
					break;
				}
				String dados = "";
				for (int i=0; i < arma.size(); i++)	{
					dados += arma.get(i).toString() + "\n";
				}
				JOptionPane.showMessageDialog(null,dados);
				break;
			case 3: // Limpar Dados
				if (arma.size() == 0) {
					JOptionPane.showMessageDialog(null,"Entre com a Arma primeiramente");
					break;
				}
				arma.clear();
				JOptionPane.showMessageDialog(null,"Armas DESCARTADAS com sucesso!");
				break;
			case 4: // Grava Dados
				if (arma.size() == 0) {
					JOptionPane.showMessageDialog(null,"Entre com a Arma primeiramente");
					break;
				}
				salvaArmas(arma);
				JOptionPane.showMessageDialog(null,"Armas SALVAS com sucesso!");
				break;
			case 5: // Recupera Dados
				arma = recuperarArmas();
				if (arma.size() == 0) {
					JOptionPane.showMessageDialog(null,"Sem Armas para apresentar.");
					break;
				}
				JOptionPane.showMessageDialog(null,"Armas RECUPERADAS com sucesso!");
				break;
			case 9:
				JOptionPane.showMessageDialog(null,"Fim do aplicativo Ferreiro");
				break;
			}
		} while (opc1 != 9);
	}

	public void mostrarArmas(String dados) {
		JOptionPane.showMessageDialog(null,"Armas\n--------\n" + dados);
	}
	public void salvaArmas(ArrayList<Armas> arma) {
		ObjectOutputStream outputStream = null;
		try {
			outputStream = new ObjectOutputStream (new FileOutputStream("c:\\temp\\arma.wpn"));
			for(int a=0; a < arma.size();a++) {
				outputStream.writeObject(arma.get(a));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				if(outputStream != null) {
					outputStream.close();
					outputStream.flush();
				}
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	@SuppressWarnings("finally")
	public ArrayList<Armas> recuperarArmas() {
		ObjectInputStream inputStream = null;
		try {
			inputStream = new ObjectInputStream (new FileInputStream("c:\\temp\\arma.wpn"));
			Object obj = null;
			while((obj = inputStream.readObject()) != null) {
				if(obj instanceof Armas) {
					arma.add((Armas)obj);
				}
			}
		} catch (EOFException e) {
			JOptionPane.showMessageDialog(null,"Leitura do documento finalizado.\n\n");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
			return arma;
		}
	}
	public boolean menuNumValido(String x) {
		boolean resultado;
		try {
			Integer.parseInt(x);
			resultado = true;
		} catch (NumberFormatException e) {
			resultado = false;
		}
		return resultado;
	}
	public static void main(String[] args) {
		Ferreiro f = new Ferreiro();
		f.menuLoja();
	}
}
