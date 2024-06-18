package Controle;

import tools.ManipulaArquivo;
import tools.StringTools;

import javax.swing.JFileChooser;
import java.util.ArrayList;
import java.util.List;

public class GerarGUI {

    public GerarGUI(String nomeDaClasse, List<String> atributo, String caminho){
        StringTools st = new StringTools();
        List<String> cg = new ArrayList<>();
        String nomeDaClasseMin = st.plMinus(nomeDaClasse);
        cg.add("package GUIs;");
        JFileChooser tf = new JFileChooser();
        cg.add("import Entidades." + nomeDaClasse + ";\n"
                        + "import Controles." + nomeDaClasse + "Controle;\n"
                        + "import java.awt.BorderLayout;\n"
                        + "import java.awt.CardLayout;" +
                "import javax.swing.ImageIcon;" +
                "import java.io.File;" +
                "import javax.swing.JFileChooser;  \n"
                + "import java.text.ParseException;\n"
                + "import com.toedter.calendar.JTextFieldDateEditor;\n"
                        + "import java.awt.Color;\n"
                        + "import java.awt.event.WindowAdapter;\n"
                        + "import java.awt.event.WindowEvent;\n"
                        + "import java.text.ParseException;\n"
                        + "import java.util.ArrayList;\n"
                        + "import com.toedter.calendar.JDateChooser;\n"
                        + "import com.toedter.calendar.JTextFieldDateEditor;\n"
                        + "import java.awt.Container;\n" +
                "import tools.CopiarArquivos;\n" +
                "import tools.DiretorioDaAplicacao;\n" +
                "import tools.ImagemAjustada;"

                        + "import java.awt.FlowLayout;\n"
                        + "import java.awt.GridLayout;\n"
                        + "import javax.swing.BorderFactory;\n"
                        + "import javax.swing.JButton;\n"
                        + "import javax.swing.JDialog;\n"
                        + "import javax.swing.JLabel;\n"
                        + "import javax.swing.JOptionPane;\n"
                        + "import javax.swing.JPanel;\n"
                        + "import javax.swing.JScrollPane;\n"
                        + "import javax.swing.JTable;\n"
                        + "import java.util.Date;\n"
                        + "import java.util.List;\n"
                        + "import javax.swing.JCheckBox;\n"
                        + "import javax.swing.JTextField;\n"
                        + "import javax.swing.table.DefaultTableModel;"
        );
        cg.add("public class " + nomeDaClasse + "GUI extends JDialog{\n");
        cg.add("""
                Container cp;
                    JPanel pnNorte = new JPanel();
                    JPanel pnCentro = new JPanel();
                    JPanel pnSul = new JPanel();
                    JPanel pnLeste = new JPanel(new BorderLayout());
                    JPanel pnLesteA = new JPanel(new FlowLayout(FlowLayout.CENTER));
                     JPanel pnLesteB = new JPanel(new GridLayout(1, 1));""");
        cg.add("""
                JButton btBuscar = new JButton("Buscar");
                    JButton btAdicionar = new JButton("Adicionar");
                    JButton btSalvar = new JButton("Salvar");
                    JButton btAlterar = new JButton("Alterar");
                    JButton btExcluir = new JButton("Excluir");
                    JButton btListar = new JButton("Listar");
                    JButton btCancelar = new JButton("Cancelar");
                    JButton btSalvarPDF = new JButton(\"Salvar PDF\");
                    JButton btGrafico = new JButton("Gerar Gráfico");
                    JButton btAdicionarFoto = new JButton("Adicionar/alterar foto");
                    JButton btRemoverFoto = new JButton("Remover foto");""");

        cg.add("String acao = \"\";");
        cg.add("""
                private JScrollPane scrollTabela = new JScrollPane();

                    private JPanel pnAvisos = new JPanel(new GridLayout(1, 1));
                    private JPanel pnListagem = new JPanel(new GridLayout(1, 1));
                    private JPanel pnVazio = new JPanel(new GridLayout(6, 1));

                    private CardLayout cardLayout;""");
        cg.add("\n");
        String[] aux;
        for (String value : atributo) {
            aux = value.split(";");
            if (aux[0].equals("Date")){
                String masc = aux[2].replaceAll("[a-zA-Z]","#");
                cg.add("JLabel lb" + st.plMaiusc(aux[1]) + " = new JLabel(\"" + st.plMaiusc(aux[1]) + "\");\n");
                cg.add("JDateChooser tf" + st.plMaiusc(aux[1]) + " = new JDateChooser(\""+aux[2]+"\", \""+masc+"\", '_');\n");
            } else if (aux[1].contains("Foto")){
                cg.add("JLabel lbCaminho" + st.plMaiusc(aux[1]) +" = new JLabel(\"\");\n" +
                        "JLabel lb"+ st.plMaiusc(aux[1]) +" = new JLabel();");

            } else if(aux[0].equals("Boolean")){
                cg.add("JLabel lb" + st.plMaiusc(aux[1]) + " = new JLabel(\"" + st.plMaiusc(aux[1]) + "\");\n");
                cg.add("private final JCheckBox cb" + st.plMaiusc(aux[1]) + " = new JCheckBox(\""+ st.plMaiusc(aux[1]) +"\", false);\n");
            } else{
                cg.add("JLabel lb" + st.plMaiusc(aux[1]) + " = new JLabel(\"" + st.plMaiusc(aux[1]) + "\");\n"
                        + "JTextField tf" + st.plMaiusc(aux[1]) + " = new JTextField("+50+");\n");
            }
        }
        cg.add(nomeDaClasse + "Controle" + " controle = new " + nomeDaClasse + "Controle();\n"
                + "    " + nomeDaClasse + " " + nomeDaClasseMin + " = new " + nomeDaClasse + "();");
        cg.add("");
        StringBuilder s = new StringBuilder();
        for (String value : atributo) {
            aux = value.split(";");
            s.append("\"").append(aux[1]).append("\",");
        }
        s = new StringBuilder(s.substring(0, s.length() - 1));//retira a vírgula que está sobrando
        cg.add("String[] colunas = new String[]{" + s + "};");
        cg.add("""
                String[][] dados = new String[0][colunas.length];

                    DefaultTableModel model = new DefaultTableModel(dados, colunas);
                    JTable tabela = new JTable(model);
                    ImagemAjustada imagemAjustada = new ImagemAjustada();
                    DiretorioDaAplicacao diretorioDaAplicacao = new DiretorioDaAplicacao();
                    String dirApp = diretorioDaAplicacao.getDiretorioDaAplicacao();
                    String origem = dirApp + "/src/fotos/silhueta.png";
                    int tamX = 300;
                    int tamY = 300;
                    String temFoto = "";""");
        cg.add("");

        cg.add(" public " + nomeDaClasse + "GUI() {\n");
        cg.add(" setDefaultCloseOperation(DISPOSE_ON_CLOSE);\n"
                + "        cp = getContentPane();\n"
                + "        cp.setLayout(new BorderLayout());\n"
                + "        setTitle(\"CRUD - " + nomeDaClasse + "\");\n"
                + "\n"
                + "        cp.add(pnNorte, BorderLayout.NORTH);\n"
                + "        cp.add(pnCentro, BorderLayout.CENTER);\n"
                + "        cp.add(pnSul, BorderLayout.SOUTH);" +
                "        cp.add(pnLeste, BorderLayout.EAST);\n" +
                "        pnLeste.add(pnLesteA, BorderLayout.NORTH);\n" +
                "        pnLeste.add(pnLesteB, BorderLayout.CENTER);\n" +
                "        pnLesteA.add(btAdicionarFoto);\n" +
                "        pnLesteA.add(btRemoverFoto);" +
                "btAdicionarFoto.setVisible(false);\n" +
                "        btRemoverFoto.setVisible(false);\n"
                + "\n"
                + "        pnCentro.setBorder(BorderFactory.createLineBorder(Color.black));\n"
                + "\n"
                + "        pnNorte.setLayout(new FlowLayout(FlowLayout.LEFT));");
        aux = atributo.get(0).split(";");
        cg.add(" pnNorte.add(lb" + st.plMaiusc(aux[1]) + ");\n"
                + "        pnNorte.add(tf" + st.plMaiusc(aux[1]) + ");\n"
                + "        pnNorte.add(btBuscar);\n"
                + "        pnNorte.add(btAdicionar);\n"
                + "        pnNorte.add(btAlterar);\n"
                + "        pnNorte.add(btExcluir);\n"
                + "        pnNorte.add(btListar);\n"
                + "        pnNorte.add(btSalvar);\n"
                + "        pnNorte.add(btCancelar);\n"
                + "pnNorte.add(btSalvarPDF);"
                + "\n"
                + "        btSalvar.setVisible(false);\n"
                + "        btAdicionar.setVisible(false);\n"
                + "        btAlterar.setVisible(false);\n"
                + "        btExcluir.setVisible(false);\n"
                + "        btCancelar.setVisible(false);"
                + "btSalvarPDF.setVisible(false);");
        cg.add("pnCentro.setLayout(new GridLayout(colunas.length-1, 2));");
        for (int i = 1; i < atributo.size(); i++) {
            aux = atributo.get(i).split(";");
            if ("Boolean".equals(aux[0])) {
                cg.add("pnCentro.add(lb" + st.plMaiusc(aux[1]) + ");\n"
                        + "pnCentro.add(cb" + st.plMaiusc(aux[1]) + ");");
            } else if (aux[1].contains("Foto")){
                cg.add("lb"+st.plMaiusc(aux[1])+".setIcon(imagemAjustada.getImagemAjustada(origem, tamX, tamY));");
                cg.add("  pnCentro.add(lbCaminho"+st.plMaiusc(aux[1])+");\n" +
                        " pnLesteB.add(lb"+st.plMaiusc(aux[1])+");");


            }else if ("Date".equals(aux[0])) {
                cg.add("pnCentro.add(lb" + st.plMaiusc(aux[1]) + ");\n"
                        + "pnCentro.add(tf" + st.plMaiusc(aux[1]) + ");");
                cg.add("((JTextFieldDateEditor)tf"+st.plMaiusc(aux[1])+".getDateEditor ()).setEditable(false);");
            } else{
                cg.add("pnCentro.add(lb" + st.plMaiusc(aux[1]) + ");\n"
                        + "pnCentro.add(tf" + st.plMaiusc(aux[1]) + ");");
            }

        }

        cg.add("""
                cardLayout = new CardLayout();
                       pnSul.setLayout(cardLayout);

                       for (int i = 0; i < 5; i++) {
                           pnVazio.add(new JLabel(" "));
                       }
                       pnSul.add(pnVazio, "vazio");
                       pnSul.add(pnAvisos, "avisos");
                       pnSul.add(pnListagem, "listagem");
                       tabela.setEnabled(false);

                       pnAvisos.add(new JLabel("Avisos"));""".indent(1));
        cg.add("String caminho = \"" + nomeDaClasse + ".csv\";\n"
                + "controle.carregarDados(caminho);");
        cg.add("\n\n// listener Buscar\n");
        cg.add("""
                btBuscar.addActionListener(e -> {
                                
                cardLayout.show(pnSul, "avisos");
                """.indent(1));
        aux = atributo.get(0).split(";");
        cg.add(aux[0]+" valor;");
        switch(aux[0]){
            case "String", "char" -> cg.add("if (!tf"+st.plMaiusc(aux[1])+".equals(\"\")){\n");
            case "int" -> cg.add("try{\n valor = Integer.parseInt(tf"+st.plMaiusc(aux[1])+".getText());");
            case "double" -> cg.add("try{\n valor = Double.parseDouble(tf"+st.plMaiusc(aux[1])+".getText());");
            case "Date" -> {
                cg.add("SimpleDateFormat sdf = new SimpleDateFormat("+st.plMaiusc(aux[1])+")");
                cg.add("try{\n valor = sdf.parse(tf"+st.plMaiusc(aux[1])+".getText());");
            }
            case "long" -> cg.add("try{\n valor = Long.parseLong(tf"+st.plMaiusc(aux[1])+".getText());");
            case "short" -> cg.add("try{\n valor = Short.parseShort(tf"+st.plMaiusc(aux[1])+".getText());");
            case "float" -> cg.add("try{\n valor = Float.parseFloat(tf"+st.plMaiusc(aux[1])+".getText());");
        }
        cg.add("if ("+nomeDaClasseMin+" != null) {\n");
        cg.add(nomeDaClasseMin+" = controle.buscar(valor);");
        cg.add("if ("+nomeDaClasseMin+" != null) {");
        cg.add("""
                btAdicionar.setVisible(false);
                btAlterar.setVisible(true);
                btExcluir.setVisible(true);
                btSalvarPDF.setVisible(false);
                """.indent(20));
        String id = "";
        aux = atributo.get(0).split(";");
        id = aux[1];
        for (int i = 1; i < atributo.size(); i++) {
            aux = atributo.get(i).split(";");
            switch(aux[0]){
                case "Date" ->
                    cg.add("tf" + st.plMaiusc(aux[1]) + ".setDate("
                            + nomeDaClasseMin + ".get" + st.plMaiusc(aux[1]) + "());\n"
                            + "tf" + st.plMaiusc(aux[1]) + ".setEnabled(false);\n");
                case "Boolean" ->
                        cg.add("cb" + st.plMaiusc(aux[1]) + ".setSelected("
                                + nomeDaClasseMin + ".get" + st.plMaiusc(aux[1]) + "());\n"
                                + "cb" + st.plMaiusc(aux[1]) + ".setEnabled(false);\n");
                case "String" -> {
                    if (aux[1].contains("Foto") ) {
                        cg.add("String c = dirApp + \"/src/Fotos/\" +"+nomeDaClasseMin+".get"+st.plMaiusc(id)+"() + \".png\";");
                        cg.add("if ("+nomeDaClasseMin+".get"+st.plMaiusc(aux[1])+"().equals(\"Sim\")) { //tem foto\n" +
                                "                        ImageIcon ii = imagemAjustada.getImagemAjustada(c, tamY, tamY);\n" +
                                "                        lb"+st.plMaiusc(aux[1])+".setIcon(ii);\n" +
                                "                        lbCaminho"+st.plMaiusc(aux[1])+".setText(dirApp + \"/src/Fotos/\" + "+nomeDaClasseMin+".get"+st.plMaiusc(id)+"() + \".png\");\n" +
                                "                    } else {\n" +
                                "                        c = dirApp + \"/src/Fotos/silhueta.png\";\n" +
                                "                        ImageIcon ii = imagemAjustada.getImagemAjustada(c, tamY, tamY);\n" +
                                "                        lb"+st.plMaiusc(aux[1])+".setIcon(ii);\n" +
                                "                    }\n" +
                                "                    lbCaminho"+st.plMaiusc(aux[1])+".setText(c);");
                    } else{
                        cg.add("tf" + st.plMaiusc(aux[1]) + ".setText(String.valueOf("
                                + nomeDaClasseMin + ".get" + st.plMaiusc(aux[1]) + "()));\n"
                                + "tf" + st.plMaiusc(aux[1]) + ".setEditable(false);\n");
                    }
                }
                default ->
                        cg.add("tf" + st.plMaiusc(aux[1]) + ".setText(String.valueOf("
                                + nomeDaClasseMin + ".get" + st.plMaiusc(aux[1]) + "()));\n"
                                + "tf" + st.plMaiusc(aux[1]) + ".setEditable(false);\n");

            }
        }
        cg.add("""
                } else {//não achou na lista
                                   btAdicionar.setVisible(true);
                                   btAlterar.setVisible(false);
                                   btExcluir.setVisible(false);
                                   btSalvarPDF.setVisible(false);""".indent(1));
        for (int i = 1; i < atributo.size(); i++) {
            aux = atributo.get(i).split(";");
            switch (aux[0]){
                case "Date" ->
                        cg.add("tf" + st.plMaiusc(aux[1]) + ".setDate(new Date() );                 \n"
                                + "                    tf" + st.plMaiusc(aux[1]) + ".setEnabled(false); ");
                case "Boolean" ->
                        cg.add("cb" + st.plMaiusc(aux[1]) + ".setSelected(false);                 \n"
                                + "                    cb" + st.plMaiusc(aux[1]) + ".setEnabled(false); ");
                case "String" -> {
                    if (aux[1].contains("Foto")) {
                        cg.add("ImageIcon ii = imagemAjustada.getImagemAjustada(dirApp + \"/src/Fotos/silhueta.png\", tamX, tamY);\n" +
                                "                    lb"+st.plMaiusc(aux[1])+".setIcon(ii);\n" +
                                "                    lbCaminho"+st.plMaiusc(aux[1])+".setText(dirApp + \"/src/Fotos/silhueta.png\");");
                    }
                    }
                default ->
                        cg.add("tf" + st.plMaiusc(aux[1]) + ".setText(\"\");                 \n"
                                + "                    tf" + st.plMaiusc(aux[1]) + ".setEditable(false); ");
            }
        }
        cg.add("}\n }");
        aux = atributo.get(0).split(";");
        int h = 0;
        switch(aux[0]){
            case "String", "char" -> cg.add("} else{ JOptionPane.showMessageDialog(cp,\"Erro no tipo de dados\",\"Erro ao buscar\",JOptionPane.PLAIN_MESSAGE);\n" +
                    " tf" +st.plMaiusc(aux[1])+".selectAll();\n tf"+st.plMaiusc(aux[1])+".requestFocus();\n}\n});\n");
            default -> {
                h++;
                cg.add("} catch (Exception ex"+h+") { JOptionPane.showMessageDialog(cp,\"Erro no tipo de dados\",\"Erro ao buscar\",JOptionPane.PLAIN_MESSAGE);\n" +
                        " tf" +st.plMaiusc(aux[1])+".selectAll();\n tf"+st.plMaiusc(aux[1])+".requestFocus();\n}\n});\n");
            }
        }
        cg.add("btAdicionar.addActionListener(e -> {");
        aux = atributo.get(0).split(";");
        cg.add("tf" + st.plMaiusc(aux[1]) + ".setEditable(false);");
        cg.add("tf" + st.plMaiusc(aux[1]) + ".requestFocus();");
        for (int i = 1; i < atributo.size(); i++) {
            aux = atributo.get(i).split(";");
            switch (aux[0]){
                case "Date" ->
                        cg.add("tf" + st.plMaiusc(aux[1]) + ".setEnabled(true); ");
                case "Boolean" ->
                        cg.add("cb" + st.plMaiusc(aux[1]) + ".setEnabled(true); ");
                default -> {
                    if (aux[1].contains("Foto")) {
                        cg.add("");
                    } else {
                        cg.add("tf" + st.plMaiusc(aux[1]) + ".setEditable(true); ");
                    }
                }
            }
        }
        cg.add("""
                btAdicionar.setVisible(false);
                               btSalvar.setVisible(true);
                               btCancelar.setVisible(true);
                               btBuscar.setVisible(false);
                               btListar.setVisible(false);
                               btAdicionarFoto.setVisible(true);
                               btRemoverFoto.setVisible(true);
                               btSalvarPDF.setVisible(false);
                               acao = "adicionar";
                               temFoto = "Nao";
                       });""".indent(1));
        cg.add(" btSalvar.addActionListener(e -> {\n"
                + " CopiarArquivos copiarArquivos = new CopiarArquivos();" +
                "if (acao.equals(\"adicionar\")) {\n"
                + "                    " + nomeDaClasseMin + " = new " + nomeDaClasse + "();\n"
                + "                }\n"
                + "                " + nomeDaClasse + " " + nomeDaClasseMin + "Antigo = " + nomeDaClasseMin + ";");
            List<String> catchs = new ArrayList<>();
            for (int i = 1; i < atributo.size(); i++) {
                aux = atributo.get(i).split(";");
                switch (aux[0]) {
                    case "String", "char" -> {
                        if (aux[1].contains("Foto")) {
                            cg.add(nomeDaClasseMin+".set"+st.plMaiusc(aux[1])+"(temFoto);");
                            cg.add("String destino = dirApp + \"/src/Fotos/\";\n" +
                                    "                destino = destino + "+nomeDaClasseMin+".get"+st.plMaiusc(id)+"() + \".png\";\n" +
                                    "                copiarArquivos.copiar(lbCaminho"+st.plMaiusc(aux[1])+".getText(), destino);");
                        } else {
                            cg.add("if (!tf" + st.plMaiusc(aux[1]) + ".getText().equals(\"\")) {\n");
                            catchs.add("if;" + i);
                        }
                    }
                    case "double" -> {
                        cg.add("try { \n "+aux[0]+" valor"+i+" = Double.parseDouble(tf" + st.plMaiusc(aux[1]) + ".getText());");
                        catchs.add("ca;"+i);
                    }
                    case "short" -> {
                        cg.add("try { \n "+aux[0]+" valor"+i+" = Short.parseShort(tf" + st.plMaiusc(aux[1]) + ".getText());");
                        catchs.add("ca;"+i);
                    }
                    case "float" -> {
                        cg.add("try { \n "+aux[0]+" valor"+i+" = Float.parseFloat(tf" + st.plMaiusc(aux[1]) + ".getText());");
                        catchs.add("ca;"+i);
                    }
                    case "long" -> {
                        cg.add("try { \n "+aux[0]+" valor"+i+" = Long.parseLong(tf" + st.plMaiusc(aux[1]) + ".getText());");
                        catchs.add("ca;"+i);
                    }
                    case "int" -> {
                        cg.add("try { \n "+aux[0]+" valor"+i+" = Integer.parseInt(tf" + st.plMaiusc(aux[1]) + ".getText());");
                        catchs.add("ca;"+i);
                    }
                }
            }
        for (String value : atributo) {
            aux = value.split(";");
            switch (aux[0]) {
                case "String" -> {
                    if (!aux[1].contains("Foto")) {
                        cg.add(nomeDaClasseMin + ".set" + st.plMaiusc(aux[1]) + "(tf" + st.plMaiusc(aux[1]) + ".getText());");
                        cg.add("tf" + st.plMaiusc(aux[1]) + ".setText(\"\");");
                        cg.add("tf" + st.plMaiusc(aux[1]) + ".setEditable(false);");
                    } else{
                        cg.add("ImageIcon ii = imagemAjustada.getImagemAjustada(dirApp + \"/src/Fotos/silhueta.png\", tamX, tamY);\n" +
                                "                lb"+st.plMaiusc(aux[1])+".setIcon(ii);\n" +
                                "                lbCaminho"+st.plMaiusc(aux[1])+".setText(dirApp + \"/src/Fotos/silhueta.png\");");
                    }
                }
                case "char" ->{
                    cg.add("" + nomeDaClasseMin + ".set" + st.plMaiusc(aux[1]) + "(tf" + st.plMaiusc(aux[1]) + ".getText().charAt(0));");
                    cg.add("tf"+st.plMaiusc(aux[1]) + ".setText(\"\");");
                    cg.add("tf"+st.plMaiusc(aux[1]) + ".setEditable(false);");
                }
                case "Boolean" -> {
                    cg.add("" + nomeDaClasseMin + ".set" + st.plMaiusc(aux[1]) + "(cb" + st.plMaiusc(aux[1]) + ".isSelected());");
                    cg.add("cb"+st.plMaiusc(aux[1]) + ".setSelected(false);");
                    cg.add("cb"+st.plMaiusc(aux[1]) + ".setEnabled(false);");
                }
                case "Date" -> {
                    cg.add("" + nomeDaClasseMin + ".set" + st.plMaiusc(aux[1]) + "(tf" + st.plMaiusc(aux[1]) + ".getDate());");
                    cg.add("tf"+st.plMaiusc(aux[1]) + ".setDate(new Date());");
                    cg.add("tf"+st.plMaiusc(aux[1]) + ".setEnabled(false);");
                }
                case "int" -> {
                    cg.add("" + nomeDaClasseMin + ".set" + st.plMaiusc(aux[1]) + "(Integer.parseInt(tf" + st.plMaiusc(aux[1]) + ".getText()));");
                    cg.add("tf"+st.plMaiusc(aux[1]) + ".setText(\"\");");
                    cg.add("tf"+st.plMaiusc(aux[1]) + ".setEditable(false);");
                }
                case "double" -> {
                    cg.add("" + nomeDaClasseMin + ".set" + st.plMaiusc(aux[1]) + "(Double.parseDouble(tf" + st.plMaiusc(aux[1]) + ".getText()));");
                    cg.add("tf"+st.plMaiusc(aux[1]) + ".setText(\"\");");
                    cg.add("tf"+st.plMaiusc(aux[1]) + ".setEditable(false);");
                }
                case "long" -> {
                    cg.add("" + nomeDaClasseMin + ".set" + st.plMaiusc(aux[1]) + "(Long.parseLong(tf" + st.plMaiusc(aux[1]) + ".getText()));");
                    cg.add("tf"+st.plMaiusc(aux[1]) + ".setText(\"\");");
                    cg.add("tf"+st.plMaiusc(aux[1]) + ".setEditable(false);");
                }
                case "short" -> {
                    cg.add("" + nomeDaClasseMin + ".set" + st.plMaiusc(aux[1]) + "(Short.parseShort(tf" + st.plMaiusc(aux[1]) + ".getText()));");
                    cg.add("tf"+st.plMaiusc(aux[1]) + ".setText(\"\");");
                    cg.add("tf"+st.plMaiusc(aux[1]) + ".setEditable(false);");
                }
                case "float" -> {
                    cg.add("" + nomeDaClasseMin + ".set" + st.plMaiusc(aux[1]) + "(Float.parseFloat(tf" + st.plMaiusc(aux[1]) + ".getText()));");
                    cg.add("tf"+st.plMaiusc(aux[1]) + ".setText(\"\");");
                    cg.add("tf"+st.plMaiusc(aux[1]) + ".setEditable(false);");
                }
            }
        }
        cg.add("                btSalvar.setVisible(false);\n"
                + "                btCancelar.setVisible(false);\n"
                + "                btBuscar.setVisible(true);\n"
                + "                btListar.setVisible(true);" +
                "btSalvarPDF.setVisible(false);" +
                "btAdicionarFoto.setVisible(false);\n" +
                "                btRemoverFoto.setVisible(false);");
        aux = atributo.get(0).split(";");
        cg.add(" tf" + st.plMaiusc(aux[1]) + ".setEnabled(true);\n"
                + "                tf" + st.plMaiusc(aux[1]) + ".setEditable(true);\n"
                + "                tf" + st.plMaiusc(aux[1]) + ".requestFocus();\n"
                + "                tf" + st.plMaiusc(aux[1]) + ".setText(\"\");");

        cg.add("tf" + st.plMaiusc(aux[1]) + ".setText(\"\");\n");
        cg.add("if (acao.equals(\"adicionar\")) {\n"
                + "                    controle.adicionar(" + nomeDaClasseMin + ");\n"
                + "                } else {\n");
        for (String value : atributo) {
            aux = value.split(";");
            if ("String".equals(aux[0])) {
                if (aux[1].contains("Foto")) {
                    cg.add(nomeDaClasseMin + ".set" + st.plMaiusc(aux[1]) + "(temFoto);");
                }
            }
        }
        cg.add("controle.alterar(" + nomeDaClasseMin + ", " + nomeDaClasseMin + "Antigo);\n"
                + "                }\n");
        List<String> catchs2 = new ArrayList<>();
        for(int i = catchs.size()-1;i>=0;i--){
            catchs2.add(catchs.get(i));
        }
        for (String x : catchs2){
            String [] xs = x.split(";");
            for (int i = 1; i < atributo.size(); i++) {
                aux = atributo.get(i).split(";");
                if (Integer.parseInt(xs[1]) == i){
                    switch (xs[0]){
                        case "if" -> cg.add("}else{ JOptionPane.showMessageDialog(cp,\"Erro no tipo de dados ("+aux[1]+")\",\"Erro ao adicionar\",JOptionPane.PLAIN_MESSAGE);\n" +
                                " tf" +st.plMaiusc(aux[1])+".selectAll();\n tf"+st.plMaiusc(aux[1])+".requestFocus();\n}");
                        case "ca" -> cg.add("} catch (Exception ex"+i+") { JOptionPane.showMessageDialog(cp,\"Erro no tipo de dados ("+aux[1]+")\",\"Erro ao adicionar\",JOptionPane.PLAIN_MESSAGE);\n" +
                                " tf" +st.plMaiusc(aux[1])+".selectAll();\n tf"+st.plMaiusc(aux[1])+".requestFocus();\n}");
                    }
                }
            }
        }
        cg.add("});");
        cg.add("""
                btAlterar.addActionListener( e -> {
                               btBuscar.setVisible(false);
                               btAlterar.setVisible(false);
                               btSalvarPDF.setVisible(false);""".indent(1));
        aux = atributo.get(0).split(";");
        cg.add("tf" + st.plMaiusc(aux[1]) + ".setEditable(false);");
        for (int i = 1; i < atributo.size(); i++) {
            aux = atributo.get(i).split(";");
            switch (aux[0]){
                case "Date" ->
                        cg.add("tf" + st.plMaiusc(aux[1]) + ".setEnabled(true); ");
                case "Boolean" ->
                        cg.add("cb" + st.plMaiusc(aux[1]) + ".setEnabled(true); ");
                default -> {
                    if (!aux[1].contains("Foto")) {
                        cg.add("tf" + st.plMaiusc(aux[1]) + ".setEditable(true); ");
                    }
                }
            }
        }
        cg.add("""
                btSalvar.setVisible(true);
                            btCancelar.setVisible(true);
                            btListar.setVisible(false);
                            btExcluir.setVisible(false);
                            btAdicionarFoto.setVisible(true);
                            btRemoverFoto.setVisible(true);
                            acao = "alterar";""");
        cg.add("});");
        cg.add("""
                btExcluir.addActionListener(e -> {
                    int response = JOptionPane.showConfirmDialog(cp, "Confirme a exclusão?", "Confirm",
                                      JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                              btExcluir.setVisible(false);
                              btBuscar.setVisible(true);""".indent(2));
        aux = atributo.get(0).split(";");

        cg.add("tf" + st.plMaiusc(aux[1]) + ".setEnabled(true);\n");
        cg.add("tf" + st.plMaiusc(aux[1]) + ".setEditable(true);\n");
        cg.add("tf" + st.plMaiusc(aux[1]) + ".requestFocus();\n");
        for (int i = 0; i < atributo.size(); i++) {
            aux = atributo.get(i).split(";");
            switch (aux[0]){
                case "Date" ->{
                    cg.add("tf" + st.plMaiusc(aux[1]) + ".setDate(new Date() ); ");
                    if (i > 0) {
                        cg.add("tf" + st.plMaiusc(aux[1]) + ".setEnabled(false);");
                    }
                }
                case "Boolean" ->{
                    cg.add("cb" + st.plMaiusc(aux[1]) + ".setSelected(false); ");
                    if (i > 0) {
                        cg.add("cb" + st.plMaiusc(aux[1]) + ".setEnabled(false);");
                    }
                }
                default -> {
                    if (!aux[1].contains("Foto")) {
                        cg.add("tf" + st.plMaiusc(aux[1]) + ".setText(\"\"); ");
                        if (i > 0) {
                            cg.add("tf" + st.plMaiusc(aux[1]) + ".setEditable(false);");
                        }
                    } else{
                        cg.add(" lb"+st.plMaiusc(aux[1])+".setText(\"\");");
                        cg.add("//excluir a foto\n" +
                                "                String cc = lbCaminho"+st.plMaiusc(aux[1])+".getText();\n" +
                                "                File oArquivo = new File(cc.trim());\n" +
                                "                if (oArquivo.exists()) {\n" +
                                "                   // System.out.println(oArquivo.getAbsolutePath());\n" +
                                "                    oArquivo.delete();//exclui a foto\n" +
                                "                    origem = dirApp + \"/src/Fotos/silhueta.png\";\n" +
                                "                    ImageIcon ii = imagemAjustada.getImagemAjustada(dirApp + \"/src/Fotos/silhueta.png\", tamX, tamY);\n" +
                                "                    lb"+st.plMaiusc(aux[1])+".setIcon(ii);\n" +
                                "                } else {\n" +
                                "                    System.out.println(\"não achou\");\n" +
                                "                }");
                    }
                }
            }
        }
        cg.add(" btAlterar.setVisible(false);" +
                "btSalvarPDF.setVisible(false);\n"
                + "                if (response == JOptionPane.YES_OPTION) {\n"
                + "                    controle.excluir(" + nomeDaClasseMin + ");\n"
                + "                }\n"
                + "        });");
        cg.add(" btListar.addActionListener(e ->{\n"
                + "pnNorte.add(btGrafico);\n"
                + "                List<" + nomeDaClasse + "> lista" + nomeDaClasse + " = controle.listar();");
        s = new StringBuilder();
        for (String value : atributo) {
            aux = value.split(";");
            s.append("\"").append(aux[1]).append("\",");
        }
        s = new StringBuilder(s.substring(0, s.length() - 1));//retira a vírgula que está sobrando
        cg.add("String[] colunas = new String[]{" + s + "};");
        cg.add("String[][] dados = new String[lista" + nomeDaClasse + ".size()][colunas.length];\n");

        cg.add("String aux[];\n"
                + "                for (int i = 0; i < lista" + nomeDaClasse + ".size(); i++) {\n"
                + "                    aux = lista" + nomeDaClasse + ".get(i).toString().split(\";\");\n"
                + "                    for (int j = 0; j < colunas.length; j++) {\n"
                + "                        dados[i][j] = aux[j];\n"
                + "                    }\n"
                + "                }");
        cg.add("""
                cardLayout.show(pnSul, "listagem");
                               scrollTabela.setPreferredSize(tabela.getPreferredSize());
                               pnListagem.add(scrollTabela);
                               scrollTabela.setViewportView(tabela);
                               model.setDataVector(dados, colunas);

                               btAlterar.setVisible(false);
                               btExcluir.setVisible(false);
                               btAdicionar.setVisible(false);
                               btSalvarPDF.setVisible(true);
                       });""".indent(1));
        cg.add("btGrafico.addActionListener(e -> {\n" +
                "            List<String> atributos = new ArrayList<>();\n");
        for (String z: atributo){
            cg.add("atributos.add(\""+z+"\");");
        }
        cg.add("new "+nomeDaClasse+"GerarGrafico(atributos);\n" +
                        "                            setVisible(false);\n" +
                        "                        });");
        cg.add("""
                btCancelar.addActionListener( e -> {
                               btCancelar.setVisible(false);""".indent(1));
        aux = atributo.get(0).split(";");
        cg.add("tf" + st.plMaiusc(aux[1]) + ".setText(\"\");\n"
                + "tf" + st.plMaiusc(aux[1]) + ".requestFocus();\n"
                + "tf" + st.plMaiusc(aux[1]) + ".setEnabled(true);\n"
                + "tf" + st.plMaiusc(aux[1]) + ".setEditable(true);");
        for (int i = 1; i < atributo.size(); i++) {
            aux = atributo.get(i).split(";");
            switch (aux[0]){
                case "Date" -> {
                    cg.add("tf" + st.plMaiusc(aux[1]) + ".setDate(new Date()); ");
                    cg.add("tf" + st.plMaiusc(aux[1]) + ".setEnabled(false);");
                }
                case "Boolean" -> {
                    cg.add("cb" + st.plMaiusc(aux[1]) + ".setSelected(false); ");
                    cg.add("cb" + st.plMaiusc(aux[1]) + ".setEnabled(false);");
                }
                default -> {
                    if (!aux[1].contains("Foto")) {
                        cg.add("tf" + st.plMaiusc(aux[1]) + ".setText(\"\"); ");
                        cg.add("tf" + st.plMaiusc(aux[1]) + ".setEditable(false);");
                    } else{
                        cg.add("lb"+st.plMaiusc(aux[1])+".setText(\"\");");
                    }
                }
            }
        }
        cg.add("""
                btBuscar.setVisible(true);
                               btListar.setVisible(true);
                               btSalvar.setVisible(false);
                               btCancelar.setVisible(false);
                               btAdicionarFoto.setVisible(false);
                               btRemoverFoto.setVisible(false);
                               btSalvarPDF.setVisible(false);

                         
                       });""".indent(1));
        cg.add("""
                btAdicionarFoto.addActionListener( e -> {
                                JFileChooser fc = new JFileChooser();
                                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                                if (fc.showOpenDialog(cp) == JFileChooser.APPROVE_OPTION) {
                                    File img = fc.getSelectedFile();
                                    origem = fc.getSelectedFile().getAbsolutePath();

                                    try {
                """);
        for (String value : atributo) {
            aux = value.split(";");
            if ("String".equals(aux[0])) {
                if (aux[1].contains("Foto")) {
                    cg.add("lb" + st.plMaiusc(aux[1]) + ".setIcon(imagemAjustada.getImagemAjustada(origem, tamX, tamY));" +
                            "lbCaminho" + st.plMaiusc(aux[1]) + ".setText(origem);");
                }
            }
        }
                cg.add(
                "                        \n" +
                "                        temFoto = \"Sim\";\n" +
                "                    } catch (Exception ex) {\n" +
                "                        JOptionPane.showMessageDialog(cp, \"Erro ao carregar a imagem\");\n" +
                "                    }\n" +
                "                }\n" +
                "        });\n" +
                "\n" +
                "        btRemoverFoto.addActionListener( e -> {\n");

        for (String value : atributo) {
            aux = value.split(";");
            if ("String".equals(aux[0])) {
                if (aux[1].contains("Foto")) {
                    cg.add(
                            "                int response = JOptionPane.showConfirmDialog(cp, \"Confirma a remoção da foto?\", \"Confirm\",\n" +
                                    "                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);\n" +
                                    "\n" +
                                    "                if (response == JOptionPane.YES_OPTION) {\n" +
                                    "                    //excluir a foto\n" +
                                    "                    File oArquivo = new File(lbCaminho"+st.plMaiusc(aux[1])+".getText().trim());\n" +
                                    "                    if (oArquivo.exists()) {\n");
                    cg.add("new File(lbCaminho"+st.plMaiusc(aux[1])+".getText()).delete();//exclui a foto\n" +
                            "origem = dirApp + \"/src/Fotos/silhueta.png\";\n" +
                            "ImageIcon ii = imagemAjustada.getImagemAjustada(dirApp + \"/src/Fotos/silhueta.png\", tamX, tamY);\n" +
                            "lb"+st.plMaiusc(aux[1])+".setIcon(ii);");
                }
            }
        }
        cg.add(
                """
                                        temFoto = "Nao";
                    
                        });""".indent(8));
        cg.add("\n\n// listener ao fechar o programa\n");
        cg.add("""
                addWindowListener(new WindowAdapter() {
                           @Override
                           public void windowClosing(WindowEvent e) {
                               //antes de sair, salvar a lista em armazenamento permanente
                               controle.gravarLista(caminho);
                               // Sai da classe
                               dispose();
                           }
                       });

                       setModal(true);
                       pack();
                       setLocationRelativeTo(null);//centraliza na tela
                       setVisible(true);""".indent(1));
        cg.add("");

        cg.add("}//fim do contrutor de GUI\n");

        cg.add("} //fim da classe");

        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(caminho+"/src/GUIs/" + nomeDaClasse + "GUI.java", cg);
    }
}
