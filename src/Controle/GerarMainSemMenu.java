package Controle;

import java.util.ArrayList;
import java.util.List;
import tools.ManipulaArquivo;
import tools.StringTools;

public class GerarMainSemMenu {
    public GerarMainSemMenu(String nomeDaClasse, List<String> atributo, String caminho) {
        StringTools st = new StringTools();
        List<String> cg = new ArrayList();//código gerado
        cg.add("package Main;\n"

                + "import GUIs."+nomeDaClasse+"GUI;\n"
                + "public class "+nomeDaClasse+"Main {\n"
                + "    public static void main(String[] args) {\n"
                + "        "+nomeDaClasse+"GUI guiDoGerador = new "+nomeDaClasse+"GUI();\n"
                + "    }\n"
                + "\n"
                + "}");

        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(caminho + "/src/Main/"+nomeDaClasse+"Main.java", cg);

    }
}
