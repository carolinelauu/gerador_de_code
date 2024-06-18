package GUI;

import Controle.*;
import tools.CopiarArquivos;
import tools.ManipulaArquivo;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIdoGerador extends JFrame {

    JLabel lbcaminho = new JLabel("Crie seu projeto");
    List<String> variaveis = new ArrayList<>();
    JLabel escolha = new JLabel("Escolha uma das opções abaixo");
    JLabel lbNomeEntiddade = new JLabel("Nome da Entidade");
    JTextField tfNomeEntidade = new JTextField(20);
    JLabel lbTipo = new JLabel("Tipo");
    JComboBox<String> cbtype = new javax.swing.JComboBox<>();
    JTextArea atributos = new JTextArea();
    JLabel lbNomeAtributo = new JLabel("Nome");
    JTextField tfNomeAtributo = new JTextField(10);
    JLabel lbMascara = new JLabel("Mascara da Data");
    JTextField tfMascara = new JTextField(10);
    JTextField tfcaminho = new JTextField(50);

    JButton procurar = new JButton("PROCURAR");
    JButton criar = new JButton("CRIAR");
    JButton entidade = new JButton("Gerar classe de Entidade");
    JButton controle = new JButton("Gerar classe de Controle");
    JButton gui = new JButton("Gerar classe GUI");
    JButton main = new JButton("Gerar classe Main");
    JButton todas = new JButton("Gerar todas as classes");
    JButton btGerarEstrutura = new JButton("Gerar Estrutura");
    JButton adicionar = new JButton("Adicionar");
    JButton gerarentidade = new JButton("Salvar Entidade");
    JButton undo = new JButton("Ctrl z");
    JButton mascara = new JButton("Mascara da Data");
    JButton adicionar2 = new JButton("Adicionar mascara");
    JButton menu = new JButton("Criar Menu");

    private String caminho = "";
    private final ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
    private final JFileChooser jFileChooser = new JFileChooser();
    private List<String> texto;

    public GUIdoGerador() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        atributos.setEditable(false);
        tfcaminho.setEditable(false);
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        JPanel pnNorte = new JPanel();
        cp.add(pnNorte, BorderLayout.NORTH);
        JPanel pnCentro = new JPanel();
        cp.add(pnCentro, BorderLayout.CENTER);
        JPanel pnSul = new JPanel();
        cp.add(pnSul, BorderLayout.SOUTH);

        for (String s : Arrays.asList("int", "Boolean", "long", "short", "float", "char", "String", "double", "Date", "String (foto)")) {
            cbtype.addItem(s);
        }

        for (JButton button : Arrays.asList(menu, procurar, criar, entidade, gui, controle, main, todas, adicionar, gerarentidade, undo, adicionar2)) {
            button.setBackground(new Color(172, 6, 6));
            button.setFont(new Font("Poppins", Font.BOLD, 15));
            button.setForeground(Color.white);
        }
        atributos.setForeground(new Color(172, 6, 6));

        for (JLabel jLabel : Arrays.asList(lbcaminho, escolha)) {
            jLabel.setForeground(new Color(172, 6, 6));
            jLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            jLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        }

        for (JTextField jTextField : List.of(tfcaminho)) {
            jTextField.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            jTextField.setSize(40, 2);
            jTextField.setFont(new Font("Poppins", Font.BOLD, 13));
            jTextField.setBackground(Color.white);
        }

        pnNorte.add(lbcaminho);
        pnCentro.add(tfcaminho);
        pnSul.add(procurar);
        pnSul.add(criar);
        pnSul.add(btGerarEstrutura);
        btGerarEstrutura.setVisible(false);
        criar.setVisible(false);
        final int[] entid = {0};
        texto = manipulaArquivo.abrirArquivo("ProjetoEscolhido.txt");
        if (texto.size() > 0) {
            caminho = texto.get(0);
            tfcaminho.setText(caminho);
        }
        procurar.addActionListener(e -> {
            criar.setVisible(true);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("DIRETÓRIO", "..", "..");
            jFileChooser.setFileFilter(filter);
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            File file = new File(caminho);
            if (file.exists()) {
                jFileChooser.setCurrentDirectory(file);
            } else {
                file = new File("C:\\Users\\carol\\OneDrive\\Documents\\IdeaProjects");
                if (file.exists()) {
                    jFileChooser.setCurrentDirectory(file);
                } else {
                    jFileChooser.setCurrentDirectory(null);
                }

            }
            if (jFileChooser.showOpenDialog(cp) == JFileChooser.APPROVE_OPTION) {
                caminho = jFileChooser.getSelectedFile().getAbsolutePath();
                tfcaminho.setText(caminho);
                texto.clear();
                texto.add(caminho);
                manipulaArquivo.salvarArquivo("ProjetoEscolhido.txt", texto);
            }
        });
        criar.addActionListener(e ->{
            criar.setVisible(false);
            procurar.setVisible(false);
            lbcaminho.setVisible(false);
            tfcaminho.setVisible(false);
            pnNorte.add(escolha);
            for (JButton jButton : List.of(entidade)) {
                pnCentro.add(jButton);
            }
            escolha.setVisible(true);
            controle.setVisible(true);
            gui.setVisible(true);
            main.setVisible(true);
            todas.setVisible(true);
            entidade.setVisible(true);
            btGerarEstrutura.doClick();
        });
        btGerarEstrutura.addActionListener(e -> {
                    String src = tfcaminho.getText();
                    List<String> listaString = new ArrayList<>();
                    listaString.add("/src/" + "Entidades");
                    listaString.add("/src/" + "GUIs");
                    listaString.add("/src/" + "tools");
                    listaString.add("/src/" + "jars");
                    listaString.add("/src/" + "Main");
                    listaString.add("/src/" + "DAOs");
                    listaString.add("/src/" + "Fotos");
            listaString.add("/src/" + "PDF");
                    for (String pacote : listaString) {
                        File pac = new File(src + pacote);
                        if (!pac.exists()) {
                            new File(src + pacote).mkdir();//cria as pastas
                        }
                    }
            //copiar pacote de ferramentas
            File listaFerramentas = new File("src/tools");
            if (listaFerramentas.exists()) {
                File[] arqs = listaFerramentas.listFiles();
                CopiarArquivos copiarArquivos = new CopiarArquivos();
                assert arqs != null;
                for (File arq : arqs) {
                    copiarArquivos.copiar(arq.getAbsolutePath(),
                            caminho + "/src" + "/tools/" + arq.getName());
                }

            }
            //copiar pacote jars
            File listajar = new File("src/jars");
            if (listajar.exists()) {
                File[] arqs = listajar.listFiles();
                CopiarArquivos copiarArquivos = new CopiarArquivos();
                assert arqs != null;
                for (File arq : arqs) {
                    copiarArquivos.copiar(arq.getAbsolutePath(),
                            caminho + "/src" + "/jars/" + arq.getName());
                }

            }
            //copiar pacote Fotos
            File listaFotos = new File("src/Fotos");
            if (listaFotos.exists()) {
                File[] arqs = listaFotos.listFiles();
                CopiarArquivos copiarArquivos = new CopiarArquivos();
                assert arqs != null;
                for (File arq : arqs) {
                    copiarArquivos.copiar(arq.getAbsolutePath(),
                            caminho + "/src" + "/Fotos/" + arq.getName());
                }

            }
                });
        entidade.addActionListener(e ->{
            variaveis.clear();
            atributos.setText("");
            tfNomeAtributo.setText("");
            tfNomeEntidade.setEditable(true);
            atributos.setVisible(true);
            cbtype.setVisible(true);
            lbTipo.setVisible(true);
            lbNomeAtributo.setVisible(true);
            tfNomeAtributo.setVisible(true);
            adicionar.setVisible(true);
            escolha.setVisible(false);
            entidade.setVisible(false);
            controle.setVisible(false);
            gui.setVisible(false);
            main.setVisible(false);
            todas.setVisible(false);
            pnNorte.add(lbNomeEntiddade);
            pnNorte.add(tfNomeEntidade);
            pnSul.add(lbTipo);
            pnSul.add(cbtype);
            pnSul.add(lbNomeAtributo);
            pnSul.add(tfNomeAtributo);
            pnSul.add(adicionar);
            pnSul.add(undo);

            undo.setVisible(false);
            pnCentro.add(atributos);
        });

        tfNomeEntidade.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                tfNomeEntidade.setBackground(new Color(247, 59, 59));
                tfNomeEntidade.setForeground(Color.white);
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String s = tfNomeEntidade.getText();
                tfNomeEntidade.setForeground(Color.black);
                if (!s.trim().equals("")) {
                    String nomeAjustado = String.valueOf(s.charAt(0)).toUpperCase()
                            + s.substring(1);
                    tfNomeEntidade.setText(nomeAjustado);
                }
                tfNomeEntidade.setBackground(Color.white);
            }
        });

        adicionar.addActionListener(e -> {
            if (!tfNomeAtributo.getText().matches("^[0-9]{1,5}$")) {
                if (((!(cbtype.getSelectedItem() == "Date")  && !(cbtype.getSelectedItem() == "Boolean")) && variaveis.size() == 0) || variaveis.size()>0) {
                    if (cbtype.getSelectedItem() == "Date") {
                        mascara.doClick();
                    } else {
                        if (cbtype.getSelectedItem() == "String (foto)"){
                            atributos.append("private String " + tfNomeAtributo.getText() + "Foto\n");
                            variaveis.add("String;" + tfNomeAtributo.getText() + "Foto;");
                        } else{
                            atributos.append("private " + cbtype.getSelectedItem() + " " + tfNomeAtributo.getText() + "\n");
                            variaveis.add(cbtype.getSelectedItem() + ";" + tfNomeAtributo.getText() + ";");
                        }
                        pnSul.add(gerarentidade);
                        if (variaveis.size() > 0) {
                            gerarentidade.setVisible(true);
                            undo.setVisible(true);
                        }
                    }
                } else{
                    JOptionPane.showMessageDialog(cp, "A chave primária não pode ser Boolean nem Date", "Erro ao gerar variável", JOptionPane.PLAIN_MESSAGE);
                    tfNomeAtributo.selectAll();
                    tfNomeAtributo.requestFocus();
                }
            }else{
                JOptionPane.showMessageDialog(cp, "Campo nome do atributo não pode ser numérico", "Erro ao gerar variável", JOptionPane.PLAIN_MESSAGE);
                tfNomeAtributo.selectAll();
                tfNomeAtributo.requestFocus();
            }
        });

        adicionar2.addActionListener(e ->{
            if (!tfMascara.getText().equals("")){
                tfNomeAtributo.setEditable(true);
                cbtype.setEditable(true);
                adicionar.setEnabled(true);
                atributos.append("private "+ cbtype.getSelectedItem() +" "+tfNomeAtributo.getText()+" "+tfMascara.getText()+"\n");
                variaveis.add(cbtype.getSelectedItem() +";"+tfNomeAtributo.getText()+";"+tfMascara.getText()+";");
                pnSul.add(gerarentidade);
                if (variaveis.size() > 0) {
                    gerarentidade.setVisible(true);
                    undo.setVisible(true);
                    adicionar.setVisible(true);
                    undo.setVisible(true);
                    lbMascara.setVisible(false);
                    tfMascara.setVisible(false);
                    atributos.setVisible(true);
                    tfNomeAtributo.setEditable(true);
                    cbtype.setEnabled(true);
                    adicionar2.setVisible(false);
                    tfMascara.setText("");
                }
            }else{
                JOptionPane.showMessageDialog(cp, "Campo Mascara não pode ser vazio", "Erro ao gerar variável", JOptionPane.PLAIN_MESSAGE);
                tfMascara.selectAll();
                tfMascara.requestFocus();
            }
        });

        mascara.addActionListener(e ->{
            pnCentro.add(lbMascara);
            pnCentro.add(tfMascara);
            lbMascara.setVisible(true);
            tfMascara.setVisible(true);
            tfMascara.requestFocus();
            atributos.setVisible(false);
            tfNomeAtributo.setEditable(false);
            cbtype.setEnabled(false);
            adicionar.setVisible(false);
            gerarentidade.setVisible(false);
            undo.setVisible(false);
            pnSul.add(adicionar2);
            adicionar2.setVisible(true);
        });
        undo.addActionListener(e ->{
            List<String> lista = new ArrayList();

            // obtém todas as linhas de texto do JTextArea
            for(int i = 0; i < atributos.getLineCount(); i++){
                try{
                    int inicio = atributos.getLineStartOffset(i);
                    int fim = atributos.getLineEndOffset(i);
                    String linha = atributos.getText(inicio, fim - inicio);
                    lista.add(linha);
                }
                catch(BadLocationException ble){
                    // possiveis erros são tratados aqui
                }
            }
            // limpa o JTextArea
            atributos.setText("");
            // queremos excluir a ultima linha
            StringBuilder textos = new StringBuilder();
            for(int i = 0; i < lista.size(); i++) {
                if(i != lista.size()-2) // (linha a ser excluida - 1)
                    textos.append(lista.get(i));
            }
            atributos.setText(textos.toString());
            variaveis.remove(variaveis.size()-1);
            if (variaveis.size() == 0) {
                gerarentidade.setVisible(false);
                undo.setVisible(false);
            }
        });
        List<String> entidades = new ArrayList<>();
        gerarentidade.addActionListener(e ->{
            if (!tfNomeEntidade.getText().equals("")) {
                gerarentidade.setVisible(false);
                adicionar.setVisible(false);
                pnNorte.remove(lbNomeEntiddade);
                pnSul.add(lbNomeEntiddade);
                pnNorte.remove(tfNomeEntidade);
                pnSul.add(tfNomeEntidade);
                tfNomeEntidade.setEditable(false);
                atributos.setVisible(false);
                cbtype.setVisible(false);
                undo.setVisible(false);
                lbTipo.setVisible(false);
                lbNomeAtributo.setVisible(false);
                tfNomeAtributo.setVisible(false);
                new GerarClasseDeEntidade(tfNomeEntidade.getText(), variaveis, caminho);
                for (JButton jButton : Arrays.asList(entidade, controle, gui, main, todas)) {
                    pnCentro.add(jButton);
                }
                criar.doClick();
                entidades.add(tfNomeEntidade.getText());
            } else {
                JOptionPane.showMessageDialog(cp, "Nome não pode ser vazio", "Erro ao gerar entidade", JOptionPane.PLAIN_MESSAGE);
                tfNomeEntidade.selectAll();
                tfNomeEntidade.requestFocus();
            }
        });
    menu.addActionListener(e -> {
        new GerarMenuPrincipal("Menu Principal", entidades, caminho);
        new GerarClasseMain(tfNomeEntidade.getText(), variaveis, caminho);
        cp.removeAll();
        new GUIdoGerador();
        setVisible(false);
    });
    controle.addActionListener(e -> {
        new GerarClasseDAOGenerico(tfNomeEntidade.getText(), variaveis, caminho);
        new GerarClasseDAOEspecifico(tfNomeEntidade.getText(), variaveis, caminho);
        controle.setVisible(false);
    });
    gui.addActionListener(e ->{
        new GerarGUI(tfNomeEntidade.getText(), variaveis, caminho);
        new GerarGrafico(tfNomeEntidade.getText(), variaveis, caminho);
        gui.setVisible(false);
    });
    main.addActionListener(e ->{
        new GerarMainSemMenu(tfNomeEntidade.getText(), variaveis, caminho);
        main.setVisible(false);
    });
    todas.addActionListener(e -> {
        controle.doClick();
        gui.doClick();
        String[] nomeProjeto = caminho.split("\\\\");
        System.out.println(nomeProjeto.toString());
        new GerarPDF(tfNomeEntidade.getText(), variaveis, caminho, nomeProjeto[nomeProjeto.length-1]);
        main.doClick();
        pnNorte.removeAll();
        pnNorte.add(escolha);
        todas.setVisible(false);
        if (entidades.size()>1){
            pnCentro.add(menu);
        }
    });

        setTitle("Path");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
