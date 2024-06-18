package Controle;

import java.util.ArrayList;
import java.util.List;
import tools.ManipulaArquivo;
import tools.StringTools;
public class GerarClasseMain {

    public GerarClasseMain(String nomeDaClasse, List<String> atributo, String caminho) {
        StringTools st = new StringTools();
        List<String> cg = new ArrayList();//código gerado
        cg.add("package Main;\n"
                
                + "import GUIs.MenuPrincipal;\n"
                + "public class Main {\n"
                + "    public static void main(String[] args) {\n"
                + "        MenuPrincipal menuPrincipal = new MenuPrincipal();\n"
                + "    }\n"
                + "}");

        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(caminho + "/src/Main/Main.java", cg);

    }

}
