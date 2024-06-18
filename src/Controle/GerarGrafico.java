package Controle;

import tools.ManipulaArquivo;
import tools.StringTools;

import java.util.ArrayList;
import java.util.List;

public class GerarGrafico {
    public GerarGrafico(String nomeDaClasse, List<String> atributo, String caminho) {
        StringTools st = new StringTools();
        List<String> cg = new ArrayList<>();
        String nomeDaClasseMin = st.plMinus(nomeDaClasse);

        cg.add("package GUIs;\n" +
                "\n" +
                "import javax.swing.*;\n" +
                "\n" +
                "import Entidades."+nomeDaClasse+";\n" +
                "import org.jfree.chart.ChartFactory;\n" +
                "import org.jfree.chart.ChartPanel;\n" +
                "import java.text.ParseException;\n" +
                "import java.text.SimpleDateFormat;\n"+
                "import org.jfree.chart.JFreeChart;\n" +
                "import org.jfree.chart.plot.PlotOrientation;\n" +
                "import org.jfree.data.category.DefaultCategoryDataset;\n" +
                "import org.jfree.data.general.DefaultPieDataset;\n" +
                "import tools.ManipulaArquivo;\n" +
                "\n" +
                "import java.awt.*;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "import java.util.Objects;\n" +
                "\n" +
                "public class "+nomeDaClasse+"GerarGrafico extends JFrame {\n" +
                "    Container cp;\n" +
                "    JLabel lbtitle = new JLabel(\"Escolha o título do gráfico\");\n" +
                "    JTextField tftitle = new JTextField(50);\n" +
                "    JLabel lbX = new JLabel(\"Título abscissas\");\n" +
                "    JTextField tfX = new JTextField(50);\n" +
                "    JLabel lbY = new JLabel(\"Título ordenadas\");\n" +
                "    JTextField tfY = new JTextField(50);\n" +
                "    JLabel lbatributos = new JLabel(\"Escolha dois atributos para montar o gráfico\");\n" +
                "    JComboBox<String> cbtype = new javax.swing.JComboBox<>();\n" +
                "    JLabel lbtype = new JLabel(\"Escolha o tipo do gráfico\");\n" +
                "    JButton criar = new JButton(\"Gerar gráfico\");\n" +
                "    public "+nomeDaClasse+"GerarGrafico(List<String> atributos) {\n" +
                "        cp = getContentPane();\n" +
                "        cp.setLayout(new GridLayout(6,6));\n" +
                "        cp.add(lbtype);\n" +
                "        cp.add(cbtype);\n" +
                "\n" +
                "        cbtype.addItem(\"Pizza\");\n" +
                "        cbtype.addItem(\"Barras\");\n" +
                "\n" +
                "        cp.add(lbtitle);\n" +
                "        cp.add(tftitle);\n" +
                "\n" +
                "        String[] aux;\n" +
                "        String [] values = new String[atributos.size()];\n" +
                "        int i = 0;\n" +
                "        for (String s : atributos) {\n" +
                "            aux = s.split(\";\");\n" +
                "            values[i] = aux[1];\n" +
                "            i++;\n" +
                "        }\n" +
                "        JList listAtributos = new JList(values);\n" +
                "        JScrollPane listaAtributos = new JScrollPane(listAtributos);\n" +
                "        cp.add(lbatributos);\n" +
                "        cp.add(listaAtributos);\n" +
                "        cp.add(lbX);\n" +
                "        cp.add(tfX);\n" +
                "        cp.add(lbY);\n" +
                "        cp.add(tfY);\n" +
                "        cp.add(criar);\n" +
                "        String [] escolhas = new String[4];\n" +
                "        criar.addActionListener(e -> {\n" +
                "            int [] indexes = listAtributos.getSelectedIndices();\n" +
                "            if ((atributos.get(indexes[0]).split(\";\")[0].equals(\"int\") || atributos.get(indexes[0]).split(\";\")[0].equals(\"double\") || atributos.get(indexes[0]).split(\";\")[0].equals(\"long\") || atributos.get(indexes[0]).split(\";\")[0].equals(\"short\") || atributos.get(indexes[0]).split(\";\")[0].equals(\"float\") || atributos.get(indexes[1]).split(\";\")[0].equals(\"double\") || atributos.get(indexes[1]).split(\";\")[0].equals(\"int\") || atributos.get(indexes[1]).split(\";\")[0].equals(\"long\") || atributos.get(indexes[1]).split(\";\")[0].equals(\"short\") || atributos.get(indexes[1]).split(\";\")[0].equals(\"float\")) && indexes.length == 2){\n" +
                "                escolhas[0] = String.valueOf(indexes[0]);\n" +
                "                escolhas[1] = String.valueOf(indexes[1]);\n" +
                "                escolhas[2] = atributos.get(indexes[0]).split(\";\")[1];\n" +
                "                escolhas[3] = atributos.get(indexes[1]).split(\";\")[1];\n" +
                "                if (Objects.equals(cbtype.getSelectedItem(), \"Pizza\")){\n" +
                "                    CriarGrafico(\"Pizza\", tftitle.getText(), escolhas, tfX.getText(), tfY.getText());\n" +
                "                } else{\n" +
                "\n" +
                "                    CriarGrafico(\"Barra\", tftitle.getText(), escolhas, tfX.getText(), tfY.getText());\n" +
                "                }\n" +
                "            } else{\n" +
                "                JOptionPane.showMessageDialog(cp, \"Erro na escolha dos atributos, deve-se escolher 2 e um deve ser numérico\", \"Erro nos atributos\", JOptionPane.PLAIN_MESSAGE);\n" +
                "                listAtributos.requestFocus();\n" +
                "            }\n" +
                "        });\n" +
                "        setDefaultCloseOperation(EXIT_ON_CLOSE);\n" +
                "        setTitle(\"Grafico\");\n" +
                "        setSize(700, 400);\n" +
                "        setLocationRelativeTo(null);\n" +
                "        setVisible(true);\n" +
                "    }\n" +
                "    public void CriarGrafico(String tipo, String title, String[] atributosescolhidos, String titleX, String titleY){\n" +
                "        // revalidei meu painel, para que ele se atualize\n" +
                "        if (tipo.equals(\"Pizza\")) {\n" +
                "            DefaultPieDataset pizza = new DefaultPieDataset();\n" +
                "            List<"+nomeDaClasse+"> lista = carregarDados(\""+nomeDaClasse+".csv\");\n" +
                "           if (lista.size() == 0){\n" +
                "                JOptionPane.showMessageDialog(cp, \"Sem dados armazenados em csv\", \"Erro nos atributos\", JOptionPane.PLAIN_MESSAGE);\n" +
                "            }"+
                "            List<String> lista2 = new ArrayList<>();\n" +
                "            for ("+nomeDaClasse+" y: lista){\n" +
                "                lista2.add(y.toString());\n" +
                "            }\n" +
                "            for (String dado : lista2) {\n" +
                "                String[] z = dado.split(\";\");\n" +
                "                pizza.setValue(z[Integer.parseInt(atributosescolhidos[0])], Float.valueOf(z[Integer.parseInt(atributosescolhidos[1])]));\n" +
                "            }\n" +
                "            JFreeChart grafico = ChartFactory.createPieChart(title, pizza, true, true, false);\n" +
                "            ChartPanel painel = new ChartPanel(grafico, true);\n" +
                "            JPanel primeiroGrafico = new JPanel();\n" +
                "            cp.setLayout(new FlowLayout());\n" +
                "            setSize(1000,500);\n" +
                "            setLocationRelativeTo(null);\n" +
                "            primeiroGrafico.add(painel);\n" +
                "            primeiroGrafico.validate();\n" +
                "\n" +
                "            painel.setVisible(true);\n" +
                "            cp.removeAll(); //removi todos os componentes que podem estar no meu painel\n" +
                "            cp.add(primeiroGrafico);\n" +
                "        } else{\n" +
                "            DefaultCategoryDataset barra = new DefaultCategoryDataset();\n" +
                "            List<"+nomeDaClasse+"> lista = carregarDados(\""+nomeDaClasse+".csv\");\n" +
                "            List<String> lista2 = new ArrayList<>();\n" +
                "            for ("+nomeDaClasse+" y: lista){\n" +
                "                lista2.add(y.toString());\n" +
                "            }\n" +
                "            for (String dado : lista2) {\n" +
                "                String[] z = dado.split(\";\");\n" +
                "                barra.setValue(Float.valueOf(z[Integer.parseInt(atributosescolhidos[1])]), z[Integer.parseInt(atributosescolhidos[0])], \"\");\n" +
                "            }\n" +
                "\n" +
                "            JFreeChart grafico = ChartFactory.createBarChart(title, titleX, titleY, barra, PlotOrientation.VERTICAL, true, true, false);\n" +
                "            ChartPanel painel = new ChartPanel(grafico);\n" +
                "            JPanel primeiroGrafico = new JPanel();\n" +
                "            cp.setLayout(new FlowLayout());\n" +
                "            setSize(1000,500);\n" +
                "            setLocationRelativeTo(null);\n" +
                "            primeiroGrafico.add(painel);\n" +
                "            primeiroGrafico.validate();\n" +
                "\n" +
                "            painel.setVisible(true);\n" +
                "            cp.removeAll(); //removi todos os componentes que podem estar no meu painel\n" +
                "            cp.add(primeiroGrafico);" +
                "try {\n" +
                "                ChartUtilities.saveChartAsJPEG(new java.io.File(title+\".jpg\"), grafico, 700, 600);\n" +
                "            } catch (IOException e) {\n" +
                "                e.printStackTrace();\n" +
                "            }\n" +
                "        }\n" +
                "        cp.revalidate(); // revalidei meu painel, para que ele se atualize\n" +
                "        cp.repaint();\n" +
                "\n" +
                "    }\n");
        String[] aux;
        aux = atributo.get(0).split(";");
        String s = "";
        int w = 0;
        for (int i = 0; i < atributo.size(); i++) {

            aux = atributo.get(i).split(";");
            if ("Date".equals(aux[0])) {
                cg.add("SimpleDateFormat sdf"+i+" = new SimpleDateFormat(\"" + aux[2] + "\");");
                w++;
            }
            s = switch (aux[0]) {
                case "String" -> s + "aux[" + i + "], ";
                case "int" -> s + "Integer.parseInt(aux[" + i + "]), ";
                case "double" -> s + "Double.parseDouble(aux[" + i + "]), ";
                case "Date" -> s + "sdf"+i+".parse(aux[" + i + "]), ";
                case "Boolean" -> s + "Boolean.parseBoolean(aux[" + i + "]), ";
                case "long" -> s + "Long.parseLong(aux[" + i + "]), ";
                case "short" -> s + "Short.parseShort(aux[" + i + "]), ";
                case "float" -> s + "Float.parseFloat(aux[" + i + "]), ";
                case "char" -> s + "(aux[" + i + "]).charAt(0), ";
                default -> s;
            };
        }
        s = s.substring(0, s.length() - 2);
        cg.add("public List<"+nomeDaClasse+"> carregarDados(String caminho) {\n"
                + "        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();\n"
                + "        if (!manipulaArquivo.existeOArquivo(caminho)) {\n"
                + "            manipulaArquivo.criarArquivoVazio(caminho);\n"
                + "        }\n"
                + "         List<"+nomeDaClasse+"> lista = new ArrayList<>();\n"
                + "        List<String> listaDeString = manipulaArquivo.abrirArquivo(caminho);\n"
                + "        //converter de CSV para " + nomeDaClasse + "\n"
                + "        " + nomeDaClasse + " " + st.plMinus(nomeDaClasse) + " = null;\n"
                + "        for (String string : listaDeString) {\n"
                + "            String aux[] = string.split(\";\");\n");
        if (w>0){
            cg.add("try {");
            cg.add("" + st.plMinus(nomeDaClasse) + " = new " + nomeDaClasse + "(" + s + ");\n");
            cg.add("} catch (ParseException e) {\n" +
                    "                e.printStackTrace();\n" +
                    "            }");
        } else{
            cg.add("" + st.plMinus(nomeDaClasse) + " = new " + nomeDaClasse + "(" + s + ");\n");
        }
        cg.add("lista.add(" + st.plMinus(nomeDaClasse) + ");\n"
                + "        }\n"
                + "return lista;\n"
                + "    }");
        cg.add("");
        cg.add("");

        cg.add("} //fim da classe");

        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(caminho+"/src/GUIs/"+nomeDaClasse+"GerarGrafico.java", cg);
    }
}
