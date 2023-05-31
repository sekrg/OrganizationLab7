package collection;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Elements reader.
 */
public class ElementsReader {

    /**
     * The Const.
     */
    final int Const = 8;                              //Кол-во значений в организации
    private int e=Const;                                //для счёта элементов массива stringList
    private String[] order = {"Organization", "name", "coordinates", "x", "y", "creationDate", "annualTurnover", "employeesCount", "type", "officialAddress","zipCode" };
    private List<String[]> list = new ArrayList<>(); //список массивов, который в итоге отправляется на проверку
    private String[] tagList = new String[Const+3];       //список тегов для проверки на очерёдность
    private int tagCounter = 0;                      //для счёта элементов массива tagList
    private String[] stringList = new String[Const];    //массив значений (будущих полей коллекций)
    private boolean lose = true;
    private boolean objectIsClosed = false;
    private int HB;                        //для локализации ошибок в файле

    /**
     * Метод осуществляет проверку структуры файла и записывает полученные поля в список
     *
     * @param nodeList получается в процессе парсинга xml файла при помощи DOM, содержит в себе всю информацию об элементах коллекции
     */
    public void printElements(NodeList nodeList){

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i) instanceof Element) {
             //   System.out.println(((Element) nodeList.item(i)).getTagName());
                tagList[tagCounter%(Const+3)] = ((Element) nodeList.item(i)).getTagName();
                tagCounter++;
                if (((Element) nodeList.item(i)).getTagName().equals("zipCode")&&e==(Const-1)){
                    objectIsClosed = true;
                }
                if (((Element) nodeList.item(i)).getTagName().equals("Organization") && e==Const){
                    if(lose){
                        e=0;
                        lose = false;
                        HB++;
                        objectIsClosed = false;
                    }
                    else {
                        if (tagInspector(tagList)){ //проверка последовательности тегов
                            list.add(stringList);
                        }
                        else System.out.println("Ошибка в последовательности элементов в объекте (№ " + HB + ") \n Объект не был добавлен в коллекцию");
                        e=0;
                        stringList = new String[Const]; //очистка массива
                        HB++;
                        objectIsClosed=false;
                    }
                }
                else{
                    if (!((Element) nodeList.item(i)).getTagName().equals("Organization") && e==Const || !(e==Const) && ((Element) nodeList.item(i)).getTagName().equals("Organization")){
                        if (!(e==Const) && ((Element) nodeList.item(i)).getTagName().equals("Organization")){
                            i--;
                        }
                        if (objectIsClosed){
                            tagList[(tagCounter-1)%(Const+3)] = "Organization";
                            if(tagInspector(tagList)){
                                list.add(stringList);
                            }
                            System.out.println("Ошибка в количестве элементов в объекте (№ null) \n Объект не был добавлен в коллекцию");
                        }
                        else System.out.println("Ошибка в количестве элементов в объекте (№ " + HB + ") \n Объект не был добавлен в коллекцию");
                        e=Const;
                        lose = true;
                        tagCounter = 0;
                        stringList = new String[Const];
                        objectIsClosed=false;
                        continue;
                    }
                }

                if(nodeList.item(i).getTextContent().trim().equals("") || !(nodeList.item(i).getFirstChild().getTextContent().trim().equals(""))){
                    if (!((Element) nodeList.item(i)).getTagName().equals("coordinates") && !((Element) nodeList.item(i)).getTagName().equals("officialAddress")){
                        stringList[e] = nodeList.item(i).getTextContent().trim();
                        e++;
                    }
                }

                if (nodeList.item(i).hasChildNodes()){
                    printElements(nodeList.item(i).getChildNodes());
                }
            }
        }
    }
    /**
     * Вспомогательный метод, проверяющий последовательность тегов в документе.
     * @param absList - массив, содержащий информацию о тегах конкретного элемента коллекции.
     * @return возвращает true, если порядок совпадает с эталонным, иначе - false.
     *
     */

    private boolean tagInspector(String[] absList){
        for (int i = 0; i < Const+3; i++) {
            if (absList[i] != order[i]){
                return false;
            }
        }
        return true;
    }

    /**
     * Gets list.
     *
     * @return Возвращает прошедшие первый этап проверки массивы полей элементов коллекции
     */
    public List<String[]> getList() {
        if (objectIsClosed){
            if (tagInspector(tagList)){ //проверка последовательности тегов
                list.add(stringList);
            }
            else System.out.println("Ошибка в последовательности элементов в объекте (№ " + HB + ") \n Объект не был добавлен в коллекцию");
        }
        return list;
    }
}