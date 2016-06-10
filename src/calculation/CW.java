package calculation;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@XmlRootElement(name="CW")
@XmlType(propOrder = {"F","G"})
public class CW implements Functions {
    @XmlElement
    public ArrayList<Point> F= new ArrayList<>();
    @XmlElement
    public ArrayList<Point> G= new ArrayList<>();
    @Override
    public double Interpoilation(double num, ArrayList<Point> func1) {
            double result = 0;
            for (int i = 0; i <func1.size(); i++)
            {
                double basisPol = 1;
                for (int j = 0; j < func1.size(); j++)
                {
                    if (i != j)
                    {
                        basisPol *= (num - func1.get(j).getX() ) / (func1.get(i).getX()- func1.get(j).getX());

                    }
                }
                result += basisPol * func1.get(i).getY();
            }
            return result;
    }

    public double x_minimum_search(double a, double b, double E, ArrayList<Point> F, ArrayList<Point> G) {
        double c;
        int i = 0;
        double first,second;
        while (Math.abs(b - a) > E) {
            i++;
            c = (a + b) / 2;
            /*System.out.println(i+"--------  "+(c-E));
            System.out.println("F  "+Interpoilation(c-E , F));
            System.out.println("G  "+Interpoilation(c-E , G));*/
            first=Math.abs(Interpoilation(c - E, F) - Interpoilation(c - E, G));
            second=Math.abs(Interpoilation(c + E, F) - Interpoilation(c + E, G));
            if(first<second) {
                b = c;
            } else {
                a = c;
            }
        }
        return (b + a) / 2;
    }
    public CW readFromXML(String fileName) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(CW.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (CW)unmarshaller.unmarshal(new FileInputStream(fileName));
    }
    public void writeToXML(String fileName) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(CW.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(this, new FileWriter(fileName));
    }
    public static void writeMainLanguage(String text, String path) {

        Path logFile = Paths.get(path);
        try (BufferedWriter writer = Files.newBufferedWriter(logFile,StandardCharsets.UTF_16)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeMainLanguage1(String text, String path) {

        Path logFile = Paths.get(path);
        try (BufferedWriter writer = Files.newBufferedWriter(logFile)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readMainLanguage(String path) {
        StringBuilder sb = new StringBuilder();

        File file=new File(path);
        try {
            //Объект для чтения файла в буфер
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            try {
                //В цикле построчно считываем файл
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                }
            } finally {
                //Также не забываем закрыть файл
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        //Возвращаем полученный текст с файла

        return sb.toString();

    }

    public static void main(String[] args) {
        CW c=new CW();
        c.G.add(new Point(-2,-3));
        c.G.add(new Point(0,1));
        c.G.add(new Point(2,-3));
        c.F.add(new Point(-3,0));
        c.F.add(new Point(3,0));
        System.out.println(c.Interpoilation(1,c.G));
        try {
            c.writeToXML("first.xml");
        }catch(Exception e){e.printStackTrace();}
            //System.out.println(new CW().x_minimum_search(-3,3,0.001,F,G));
        try {
        System.out.println(c.readFromXML("first.xml").F.size());
        }catch (Exception e){

        }
    }

}
