import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Homework3{
public static void main(String[] args) {

    try {
        recordData(inputDataCheck(inputData()));
        System.out.println("Файл записан");
        }catch (InputDataCheckException e){
            System.out.println(e.getMessage());
        }catch (InputDataFormatCheckException e){
            System.out.println(e.getMessage());
            System.out.println(e.getA());
        }catch (RecordDataException e){
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println(e.getStackTrace());
        }
}

public static String[] inputData() throws InputDataCheckException, IOException {
    System.out.println("Введите фамилию, имя, отчество, номер телефона (числа без разделителя и знака), разделенные пробелом:");
    try(Scanner scanner = new Scanner(System.in, "Cp866")) {
        String[] dataArray = scanner.nextLine().split(" ");
        //String text = scanner.nextLine();
        if (dataArray.length != 4){
            throw new InputDataCheckException("Введено неверное количество данных");
        } else {
            return dataArray;
        }
    }catch (Exception e){
        throw new IOException("Произошла ошибка при работе с консолью (несколько пробелов между данными....)");
    }
}
public static String[] inputDataCheck(String[] dataArray) throws InputDataFormatCheckException{
    
    for (int i = 0; i < 3; i++) {
        if (!dataArray[i].chars().allMatch(Character::isLetter)){
            throw new InputDataFormatCheckException("Неверный формат данных: ", dataArray[i]);
        }
    }
    try {
        Integer.parseInt(dataArray[3]);
    }catch (NumberFormatException e){
        throw new InputDataFormatCheckException("Неверный формат данных: ", dataArray[3]);
    }
    return dataArray;
}
        
public static void recordData(String[] dataArray) throws RecordDataException{
    String surname = dataArray[0];
    String name = dataArray[1];
    String patronymic = dataArray[2];
    String phone = dataArray[3];

    String fileName = surname.toLowerCase() + ".txt";
    File file = new File(fileName);
    try (FileWriter fileWriter = new FileWriter(file, true)){
        if (file.length() > 0){
            fileWriter.write('\n');
        }
        fileWriter.write(String.format("%s %s %s %s", surname, name, patronymic, phone));
    }catch (IOException e){
        throw new RecordDataException("Возникла ошибка при работе с файлом");
    }
}
}

class InputDataCheckException extends Exception{
    public InputDataCheckException(String s){
        super(s);
    }
}

class InputDataFormatCheckException extends Exception{
    private String a;
    public InputDataFormatCheckException(String s, String a){
        super(s);
        this.a = a;
    }
    public String getA() {
        return a;
    }
}
class RecordDataException extends IOException{
    public RecordDataException(String s){
        super(s);
    }
}