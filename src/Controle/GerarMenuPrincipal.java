package Controle;

import tools.ManipulaArquivo;
import tools.StringTools;

import java.util.ArrayList;
import java.util.List;

public class GerarMenuPrincipal {
    public GerarMenuPrincipal(String nomeDaClasse, List<String> entidades, String caminho) {
        StringTools st = new StringTools();
        String[] aux;
        List<String> cg = new ArrayList();//código gerado
        cg.add("package GUIs;");
        cg.add("import java.awt.Container;\n" +
                "import java.awt.Color;\n" +
                "import java.awt.Font;\n"+
                "import java.awt.GridLayout;\n" +
                "import javax.swing.JButton;\n" +
                "import javax.swing.JFrame;");
        cg.add("public class MenuPrincipal extends JFrame {\n" +
                "    public MenuPrincipal() {\n" +
                "        Container cp = getContentPane();\n" +
                "        setDefaultCloseOperation(DISPOSE_ON_CLOSE);");
        cg.add("setTitle(\""+nomeDaClasse+"\");");
        cg.add("cp.setLayout(new GridLayout("+entidades.size()+",1));");

        for(String x: entidades){
            cg.add("JButton bt"+x+" = new JButton(\""+x+"\");");
            cg.add("bt"+x+".setBackground(new Color(172, 6, 6));\n" +
                    "            bt"+x+".setFont(new Font(\"Poppins\", Font.BOLD, 15));\n" +
                    "            bt"+x+".setForeground(Color.white);");
            cg.add("cp.add(bt"+x+");");
            cg.add("bt"+x+".addActionListener(e -> {\n");
            cg.add(x+"GUI gui"+x+" = new "+x+"GUI();});");
        }

        cg.add("setSize(500,500);\n" +
                "        setLocationRelativeTo(null);\n" +
                "        setVisible(true);}}");
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(caminho + "/src/GUIs/MenuPrincipal.java", cg);
    }
}
