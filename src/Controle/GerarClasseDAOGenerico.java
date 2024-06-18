package Controle;

import java.util.ArrayList;
import java.util.List;
import tools.ManipulaArquivo;
import tools.StringTools;

public class GerarClasseDAOGenerico {

    public GerarClasseDAOGenerico(String nomeDaClasse, List<String> atributo, String caminho) {
        StringTools st = new StringTools();
        String[] aux;
        List<String> cg = new ArrayList();//código gerado
        cg.add("package DAOs;\n"
                + "\n"
                + "import java.util.List;\n"
                + "import javax.persistence.EntityManager;\n"
                + "import javax.persistence.Persistence;\n"
                + "\n"
                + "public class DAOGenerico<T> {\n"
                + "\n"
                + "    public static EntityManager em = Persistence.createEntityManagerFactory(\"UP\").createEntityManager();\n"
                + "    private Class clazz;\n"
                + "\n"
                + "    public DAOGenerico(Class clazz) {\n"
                + "        this.clazz = clazz;\n"
                + "    }\n"
                + "\n"
                + "    public void inserir(T e) {\n"
                + "        em.getTransaction().begin();\n"
                + "        em.persist(e);\n"
                + "        em.getTransaction().commit();\n"
                + "    }\n"
                + "\n"
                + "    public void atualizar(T e) {\n"
                + "        em.getTransaction().begin();\n"
                + "        em.merge(e);\n"
                + "        em.getTransaction().commit();\n"
                + "    }\n"
                + "\n"
                + "    public void remover(T e) {\n"
                + "        em.getTransaction().begin();\n"
                + "        em.remove(e);\n"
                + "        em.getTransaction().commit();\n"
                + "    }\n"
                + "\n"
                + "    public T obter(Long id) {\n"
                + "        Object ent = em.find(clazz, id);\n"
                + "        if (ent == null) {\n"
                + "            return (T) ent;\n"
                + "        } else {\n"
                + "            em.refresh(ent);\n"
                + "            return (T) ent;\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public T obter(Integer id) {\n"
                + "        Object ent = em.find(clazz, id);\n"
                + "        if (ent == null) {\n"
                + "            return (T) ent;\n"
                + "        } else {\n"
                + "            em.refresh(ent);\n"
                + "            return (T) ent;\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public T obter(String id) {\n"
                + "        Object ent = em.find(clazz, id);\n"
                + "        if (ent == null) {\n"
                + "            return (T) ent;\n"
                + "        } else {\n"
                + "            em.refresh(ent);\n"
                + "            return (T) ent;\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    public List<T> list() {\n"
                + "        return em.createQuery(\"SELECT e FROM \" + clazz.getSimpleName() + \" e\").getResultList();\n"
                + "    }\n"
                + "}");
        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(caminho + "/src/DAOs/DAOGenerico.java", cg);
    }
}
