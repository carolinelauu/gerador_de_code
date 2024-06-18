package Controle;

import java.util.ArrayList;
import java.util.List;
import tools.ManipulaArquivo;
import tools.StringTools;

public class GerarClasseDeEntidade {

    public GerarClasseDeEntidade(String nomeDaClasse, List<String> atributo,String caminho) {
        StringTools st = new StringTools();
        List<String> cg = new ArrayList();//código gerado
        cg.add("package Entidades;");
        cg.add("import java.text.SimpleDateFormat;\n" +
                "import java.util.Date;");
        cg.add("public class " + nomeDaClasse + " {\n");
        String[] aux;
        for (String value : atributo) {
            aux = value.split(";");
            cg.add("private " + aux[0] + " " + aux[1] + ";");
        }
        cg.add("SimpleDateFormat sdf = new SimpleDateFormat(\"dd/MM/yyyy\");");
        cg.add("public " + nomeDaClasse + "() {\n"
                + "    }");

        StringBuilder s = new StringBuilder();
        for (String value : atributo) {
            aux = value.split(";");
            s.append(aux[0]).append(" ").append(aux[1]).append(",");
        }
        s = new StringBuilder(s.substring(0, s.length() - 1));
        cg.add("public " + nomeDaClasse + "(" + s + ") {\n");
        for (String value : atributo) {
            aux = value.split(";");
            cg.add("this." + aux[1] + "=" + aux[1] + ";\n");
        }
        cg.add("}");

        cg.add("\n\n //gets e sets\n");

        for (String value : atributo) {
            aux = value.split(";");
            cg.add("public " + aux[0] + " get" + st.plMaiusc(aux[1]) + "() {\n"
                    + "        return " + aux[1] + ";\n"
                    + "    }\n\n");
        }

        for (String value : atributo) {
            aux = value.split(";");
            cg.add("public void" + " set" + st.plMaiusc(aux[1])
                    + "(" + aux[0] + " " + aux[1] + ") {\n"
                    + "this." + aux[1] + "=" + aux[1] + ";\n"
                    + "    }\n\n");
        }

        cg.add("""
                @Override
                   public String toString() {
                """.indent(1));

        s = new StringBuilder();
        int i = 0;
        for (String value : atributo) {
            aux = value.split(";");
            if ("Date".equals(aux[0])) {
                cg.add("sdf = new SimpleDateFormat(\"" + aux[2] + "\");");
                cg.add("String data"+i+" = sdf.format("+aux[1]+");");
                s.append(" ").append("data").append(i).append("+ \";\"+");
            } else {
                s.append(" ").append(aux[1]).append("+ \";\"+");
            }
            i++;
        }
        s = new StringBuilder(s.substring(0, s.length() - 6));
        cg.add("return "+s + ";");
        cg.add("\n}");

        cg.add("}//fim da classe\n\n");

        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(caminho+"/src/Entidades/" + nomeDaClasse + ".java", cg);

    }

}
