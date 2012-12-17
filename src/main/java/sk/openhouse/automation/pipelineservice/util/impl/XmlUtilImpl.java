package sk.openhouse.automation.pipelineservice.util.impl;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import sk.openhouse.automation.pipelineservice.util.XmlUtil;

public class XmlUtilImpl implements XmlUtil {

    @Override
    public String marshall(Class<?> cls, Object object) throws JAXBException {

        StringWriter response = new StringWriter();

        JAXBContext jaxbContext = JAXBContext.newInstance(cls);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(object, response);

        return response.toString();
    }

    @Override
    public Object unmarshall(Class<?> cls, String xml) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(cls);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshaller.unmarshal(new StringReader(xml));
    }
}