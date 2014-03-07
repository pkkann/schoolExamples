
package webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _HelloResponse_QNAME = new QName("http://webservice/", "helloResponse");
    private final static QName _Hello_QNAME = new QName("http://webservice/", "hello");
    private final static QName _Lort_QNAME = new QName("http://webservice/", "lort");
    private final static QName _LortResponse_QNAME = new QName("http://webservice/", "lortResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Lort }
     * 
     */
    public Lort createLort() {
        return new Lort();
    }

    /**
     * Create an instance of {@link Hello }
     * 
     */
    public Hello createHello() {
        return new Hello();
    }

    /**
     * Create an instance of {@link HelloResponse }
     * 
     */
    public HelloResponse createHelloResponse() {
        return new HelloResponse();
    }

    /**
     * Create an instance of {@link LortResponse }
     * 
     */
    public LortResponse createLortResponse() {
        return new LortResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "helloResponse")
    public JAXBElement<HelloResponse> createHelloResponse(HelloResponse value) {
        return new JAXBElement<HelloResponse>(_HelloResponse_QNAME, HelloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Hello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "hello")
    public JAXBElement<Hello> createHello(Hello value) {
        return new JAXBElement<Hello>(_Hello_QNAME, Hello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Lort }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "lort")
    public JAXBElement<Lort> createLort(Lort value) {
        return new JAXBElement<Lort>(_Lort_QNAME, Lort.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LortResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice/", name = "lortResponse")
    public JAXBElement<LortResponse> createLortResponse(LortResponse value) {
        return new JAXBElement<LortResponse>(_LortResponse_QNAME, LortResponse.class, null, value);
    }

}
