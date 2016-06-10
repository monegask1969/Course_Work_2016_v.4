package calculation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileWriter;

public interface RWXML {
    CW readFromXML(String fileName) throws Exception;

    void writeToXML(String fileName) throws Exception;
}