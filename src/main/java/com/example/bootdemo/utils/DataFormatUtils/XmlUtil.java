package com.example.bootdemo.utils.DataFormatUtils;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class XmlUtil {

    /**
     * 将对象转化成xml格式字符串
     * @param obj
     * @return
     * @throws JAXBException
     */
    public static String bean2Xml(Object obj, String encoding) throws JAXBException {
        String result = null;
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        result = writer.toString();

        return result;
    }

    /**
     *对象转成xml文件
     * @param obj
     * @param encoding
     * @param filePath
     * @throws Exception
     */
    public static void bean2XmlFile(Object obj, String encoding, String filePath) throws Exception {
        Writer writer = null;
        try {
            String xml = bean2Xml(obj, encoding);
            File file = new File(filePath);
            if (!file.exists() && file.isFile()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            writer = new FileWriter(file);
            writer.write(xml);
            writer.flush();
        }finally {
            if(writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 将xml文件对象转成bean对象
     * @param xmlFile
     * @param clazz
     * @return
     * @throws JAXBException
     */
    public static Object xml2Bean(File xmlFile, Class clazz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller un = jaxbContext.createUnmarshaller();
        Object obj = un.unmarshal(xmlFile);
        return obj;
    }

    /**
     * 将xml字符串转换成bean对象
     * @param xmlContent
     * @param clazz
     * @return
     * @throws JAXBException
     */
    public static Object xml2Bean(String xmlContent, Class clazz ) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller un = jaxbContext.createUnmarshaller();
        Reader reader = new StringReader(xmlContent);
        return un.unmarshal(reader);
    }

}
