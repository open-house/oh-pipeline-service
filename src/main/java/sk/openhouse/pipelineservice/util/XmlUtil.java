package sk.openhouse.pipelineservice.util;

import javax.xml.bind.JAXBException;

/**
 * XML util/helper
 * 
 * @author pete
 */
public interface XmlUtil {

    /**
     * Marshalls jaxb object to string
     * 
     * @param cls class of the object to be marshalled
     * @param object to be marshalled
     * @return string representation of the supplied object
     * @throws JAXBException
     */
    String marshall(Class<?> cls, Object object) throws JAXBException;

    /**
     * Unmarshalls string to jaxb object
     * 
     * @param cls class of the returned objec
     * @param xml string representation of the jaxb object
     * @return jaxb object
     * @throws JAXBException
     */
    Object unmarshall(Class<?> cls, String xml) throws JAXBException;
}
