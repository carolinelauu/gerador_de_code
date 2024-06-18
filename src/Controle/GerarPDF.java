package Controle;

import java.util.ArrayList;
import java.util.List;
import tools.ManipulaArquivo;
import tools.StringTools;

public class GerarPDF {

    public GerarPDF(String entidade, List<String> atributo, String caminho, String nomeProjeto) {

        StringTools st = new StringTools();
        List<String> codigo = new ArrayList<>();
        String[] aux;
        codigo.add("package GUIs;\n" +
                "\n" +
                "import Entidades."+entidade+";\n" +
                "import com.itextpdf.text.*;\n" +
                "import java.io.FileOutputStream;\n" +
                "import java.io.IOException;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "\n" +
                "import com.itextpdf.text.Font;\n" +
                "import com.itextpdf.text.Font.FontFamily;\n" +
                "import com.itextpdf.text.Image;\n" +
                "import com.itextpdf.text.Rectangle;\n" +
                "import com.itextpdf.text.pdf.PdfPCell;\n" +
                "import com.itextpdf.text.pdf.PdfPTable;\n" +
                "import com.itextpdf.text.pdf.PdfWriter;\n" +
                "import tools.ManipulaArquivo;\n" +
                "\n" +
                "public class CriarPDF"+entidade+" {\n" +
                "\n" +
                "    public CriarPDF"+entidade+"(String nomeEntidade, String grafico) {\n" +
                "        Document document = new Document();\n" +
                "\n" +
                "        try {\n" +
                "            PdfWriter.getInstance(document, new FileOutputStream(\"C:\\\\Users\\\\carol\\\\OneDrive\\\\Documents\\\\IdeaProjects\\\\"+nomeProjeto+"\\\\PDF\\\\\" + \"PDF\" + nomeEntidade + \".pdf\"));\n" +
                "\n" +
                "            document.open();\n" +
                "\n" +
                "            document.setPageSize(PageSize.A4);\n" +
                "\n" +
                "            Font f = new Font(FontFamily.COURIER, 20, Font.BOLD);\n" +
                "            Paragraph p1 = new Paragraph(nomeEntidade, f);\n" +
                "\n" +
                "            p1.setAlignment(Element.ALIGN_CENTER);\n" +
                "            p1.setSpacingAfter(20);\n" +
                "            document.add(new Paragraph(p1));\n" +
                "            document.add(new Paragraph(\"  \"));\n" +
                "\n" +
                "            PdfPCell header = new PdfPCell();\n" +
                "\n" +
                "            header.setBorderWidthBottom(1.0f);\n" +
                "            header.setBorder(Rectangle.AUTHOR);\n" +
                "            header.setBorder(Rectangle.BOTTOM);\n" +
                "            header.setBorder(Rectangle.LEFT);\n" +
                "            header.setColspan(2);\n" +
                "            List<"+entidade+"> "+st.plMinus(entidade)+"s = carregarDados(\""+entidade+".csv\");\n" +
                "            for (int i = 0; i < "+st.plMinus(entidade)+"s.size(); i++) {\n" +
                "               PdfPTable table = new PdfPTable("+atributo.size()+");"+
                "                "+entidade+" "+st.plMinus(entidade)+" = "+st.plMinus(entidade)+"s.get(i);\n");
        String id = "";
        for (int i = 0; i < atributo.size(); i++) {
            aux = atributo.get(i).split(";");
            if (i == 0){
                id = aux[1];
            }
            if (aux[1].contains("Foto")) {
                codigo.add("                Image png;\n" +
                        "                if ("+st.plMinus(entidade)+".get"+st.plMaiusc(aux[1])+"().equals(\"Sim\")) { //tem foto\n" +
                        "                    png = Image.getInstance(\"src\\\\Fotos\\\\\"+"+st.plMinus(entidade)+".get"+st.plMaiusc(id)+"() + \".png\");\n" +
                        "                } else {\n" +
                        "                    png = Image.getInstance(\"src\\\\Fotos\\\\silhueta.png\");\n" +
                        "                }" +
                        "table.addCell(\"" + aux[1] + "\");");
            } else {
                codigo.add("table.addCell(\"" + aux[1] + "\");");
            }
        }
        for (int i = 0; i < atributo.size(); i++) {
            aux = atributo.get(i).split(";");
            if ("String".equals(aux[0])) {
                if (aux[1].contains("Foto")) {
                    codigo.add("table.addCell(png);");
                } else {
                    codigo.add("table.addCell(String.valueOf(" + st.plMinus(entidade) + ".get" + st.plMaiusc(aux[1]) + "()));");
                }
            } else {
                codigo.add("table.addCell(String.valueOf(" + st.plMinus(entidade) + ".get" + st.plMaiusc(aux[1]) + "()));");
            }
        }
        codigo.add(
                """
                                        table.setWidthPercentage(100.0f);
                                        table.setHorizontalAlignment(Element.ALIGN_CENTER);
                                        document.add(table);
                                    }
                                    document.add(new Paragraph("  "));
                                    Image png = Image.getInstance(grafico);

                                    document.add(png);
                                } catch (DocumentException | IOException de) {
                                    System.err.println(de.getMessage());
                                }
                                document.close();
                            }
                        """);
        String s = "";
        int w = 0;
        for (int i = 0; i < atributo.size(); i++) {

            aux = atributo.get(i).split(";");
            if ("Date".equals(aux[0])) {
                codigo.add("SimpleDateFormat sdf"+i+" = new SimpleDateFormat(\"" + aux[2] + "\");");
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
        codigo.add("public List<"+entidade+"> carregarDados(String caminho) {\n"
                + "        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();\n"
                + "        if (!manipulaArquivo.existeOArquivo(caminho)) {\n"
                + "            manipulaArquivo.criarArquivoVazio(caminho);\n"
                + "        }" +
                "List<"+entidade+"> lista = new ArrayList<>();\n"
                + "\n"
                + "        List<String> listaDeString = manipulaArquivo.abrirArquivo(caminho);\n"
                + "        //converter de CSV para " + entidade + "\n"
                + "        " + entidade + " " + st.plMinus(entidade) + ";\n"
                + "        for (String string : listaDeString) {\n"
                + "            String aux[] = string.split(\";\");\n");
        if (w>0){
            codigo.add("try {");
            codigo.add("" + st.plMinus(entidade) + " = new " + entidade + "(" + s + ");\n");
            codigo.add("""
                    } catch (ParseException e) {
                                    e.printStackTrace();
                                }""");
        } else{
            codigo.add("" + st.plMinus(entidade) + " = new " + entidade + "(" + s + ");\n");
        }
        codigo.add("lista.add(" + st.plMinus(entidade) + ");\n"
                + "        }" +
                "return lista;\n"
                + "    }" +
                "}");

        ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
        manipulaArquivo.salvarArquivo(caminho + "/src/GUIs/CriarPDF"+entidade+".java", codigo);
    }

}
