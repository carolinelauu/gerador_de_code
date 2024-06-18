/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controle;

import java.util.ArrayList;
import java.util.List;
import tools.ManipulaArquivo;
import tools.StringTools;

public class GerarClasseDAOEspecifico {

    public GerarClasseDAOEspecifico(String nomeDaClasse, List<String> atributo, String caminho) {
        StringTools st = new StringTools();
        String[] aux;
        String nome = "";
        String id = "";
        List<String> cg = new ArrayList();//código gerado
        for (String s:atributo) {
            if (s.contains("nome")) {
                nome = s;
            }
            if (s.contains("id") || s.contains("cep")) {
                id = s;
            }
        }
        if (nome.equals("") && id.equals("")) {
            nome = atributo.get(1);
            id = atributo.get(0);
        }
        cg.add("package DAOs;\n"
                + "\n"
                + "\n"
                + "import Entidades."+nomeDaClasse+";\n"
                + "import java.util.ArrayList;\n"
                + "import java.util.List;\n"
                + "\n"
                + "public class DAO"+nomeDaClasse+" extends DAOGenerico<"+nomeDaClasse+"> {\n"
                + "\n"
                + "    private List<"+nomeDaClasse+"> lista = new ArrayList<>();\n"
                + "\n"
                + "    public DAO"+nomeDaClasse+"() {\n"
                + "        super("+nomeDaClasse+".class);\n"
                + "    }\n"
                + "\n"
                + "    public int autoId"+nomeDaClasse+"() {\n"
                + "        Integer a = (Integer) em.createQuery(\"SELECT MAX(e."+id+") FROM "+nomeDaClasse+" e \").getSingleResult();\n"
                + "        if (a != null) {\n"
                + "            return a + 1;\n"
                + "        } else {\n"
                + "            return 1;\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public List<"+nomeDaClasse+"> listByNome(String nome) {\n"
                + "        return em.createQuery(\"SELECT e FROM "+nomeDaClasse+" e WHERE e."+nome+") LIKE :nome\").setParameter(\"nome\", \"%\" + nome + \"%\").getResultList();\n"
                + "    }\n"
                + "\n"
                + "    public List<"+nomeDaClasse+"> listById(int id) {\n"
                + "        return em.createQuery(\"SELECT e FROM "+nomeDaClasse+" + e WHERE e."+id+"= :id\").setParameter(\"id\", id).getResultList();\n"
                + "    }\n"
                + "\n"
                + "    public List<"+nomeDaClasse+"> listInOrderNome() {\n"
                + "        return em.createQuery(\"SELECT e FROM "+nomeDaClasse+" e ORDER BY e."+nome+"\").getResultList();\n"
                + "    }\n"
                + "\n"
                + "    public List<"+nomeDaClasse+"> listInOrderId() {\n"
                + "        return em.createQuery(\"SELECT e FROM "+nomeDaClasse+" e ORDER BY e."+id+"\").getResultList();\n"
                + "    }\n"
                + "\n"
                + "    public List<String> listInOrderNomeStrings(String qualOrdem) {\n"
                + "        List<"+nomeDaClasse+"> lf;\n"
                + "        if (qualOrdem.equals(\"id\")) {\n"
                + "            lf = listInOrderId();\n"
                + "        } else {\n"
                + "            lf = listInOrderNome();\n"
                + "        }\n"
                + "\n"
                + "        List<String> ls = new ArrayList<>();\n"
                + "        for (int i = 0; i < lf.size(); i++) {\n"
                + "            ls.add(lf.get(i).get"+st.plMaiusc(id)+"()+ \"-\" + lf.get(i).get"+st.plMaiusc(nome)+"());\n"
                + "        }\n"
                + "        return ls;\n"
                + "    }\n"
                + "\n"
                + "    public static void main(String[] args) {\n"
                + "        DAO"+nomeDaClasse+" dao"+nomeDaClasse+" = new DAO"+nomeDaClasse+"();\n"
                + "        List<"+nomeDaClasse+"> lista"+nomeDaClasse+" = dao"+nomeDaClasse+".list();\n"
                + "        for ("+nomeDaClasse+" "+st.plMinus(nomeDaClasse)+" : lista"+nomeDaClasse+") {\n"
                + "            System.out.println("+st.plMinus(nomeDaClasse)+".get"+st.plMaiusc(id)+"()+ \"-\" + "+st.plMinus(nomeDaClasse)+".get"+st.plMaiusc(nome)+"());\n"
                + "        }\n"
                + "    }\n"
                + "}");
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(caminho + "/src/DAOs/DAO" + nomeDaClasse + ".java", cg);
    }
}
