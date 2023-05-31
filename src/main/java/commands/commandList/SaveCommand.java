package commands.commandList;

import collection.DataBase;
import commands.Command;

import java.io.*;
import java.util.Scanner;

/**
 * The type Save command.
 * Сохранить коллекцию в файл.
 * Добавляет все элементы в единый String и записывает в файл
 */
public class SaveCommand implements Command {

    @Override
    public String execute(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter filename to save collection");
        String filename = scanner.nextLine();
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return "cannot create file";
            }
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version = \"1.1\" encoding=\"UTF-8\" ?>\n");
        sb.append("<OrganizationCatalog>\n");
        for (int i = 0; i < DataBase.getInstance().getDataBase().size(); i++) {
            sb.append("\t<Organization>");
            sb.append("\n\t\t<name>").append(DataBase.getInstance().getDataBase().elementAt(i).getName()).append("</name>");
            sb.append("\n\t\t<coordinates>");
            sb.append("\n\t\t\t<x>").append(DataBase.getInstance().getDataBase().elementAt(i).getCoordinates().getX()).append("</x>");
            sb.append("\n\t\t\t<y>").append(DataBase.getInstance().getDataBase().elementAt(i).getCoordinates().getY()).append("</y>");
            sb.append("\n\t\t</coordinates>");
            sb.append("\n\t\t<creationDate>").append(DataBase.getInstance().getDataBase().elementAt(i).getCreationDate()).append("</creationDate>");
            sb.append("\n\t\t<annualTurnover>").append(DataBase.getInstance().getDataBase().elementAt(i).getAnnualTurnover()).append("</annualTurnover>");
            try {
                Long str = Long.valueOf(DataBase.getInstance().getDataBase().elementAt(i).getEmployeesCount());
                sb.append("\n\t\t<employeesCount>").append(str).append("</employeesCount>");
            } catch (NullPointerException ignored) {
            }
            try {
                String str = String.valueOf(DataBase.getInstance().getDataBase().elementAt(i).getType());
                sb.append("\n\t\t<type>").append(str).append("</type>");
            } catch (NullPointerException ignored) {

            }
            sb.append("\n\t\t<officialAddress>");
            try {
                String str = DataBase.getInstance().getDataBase().elementAt(i).getOfficialAddress().getZipCode();
                sb.append("\n\t\t\t<zipCode>").append(str).append("</zipCode>");
            } catch (NullPointerException ignored) {
            }
            sb.append("\n\t\t</officialAddress>");
            sb.append("\n\t</Organization>\n");
        }
        sb.append("</OrganizationCatalog>\n");
        writer.write(sb.toString());
        writer.close();
        return "saved";
    }

    @Override
    public String getCommandName() {
        return "save";
    }
    @Override
    public String getCommandDescription() {
        return "сохранить коллекцию в файл";
    }
}
