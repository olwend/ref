//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.25 at 01:57:20 PM GMT 
//


package uk.ac.nhm.core.impl.workflows.science.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.symplectic.co.uk/publications/api}begin-page" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}end-page"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}page-count"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "beginPage",
    "endPage",
    "pageCount"
})
@XmlRootElement(name = "pagination")
public class Pagination {

    @XmlElement(name = "begin-page")
    protected String beginPage;
    @XmlElement(name = "end-page")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String endPage;
    @XmlElement(name = "page-count")
    protected BigInteger pageCount;

    /**
     * Gets the value of the beginPage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeginPage() {
        return beginPage;
    }

    /**
     * Sets the value of the beginPage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeginPage(String value) {
        this.beginPage = value;
    }

    /**
     * Gets the value of the endPage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndPage() {
        return endPage;
    }

    /**
     * Sets the value of the endPage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndPage(String value) {
        this.endPage = value;
    }

    /**
     * Gets the value of the pageCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPageCount() {
        return pageCount;
    }

    /**
     * Sets the value of the pageCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPageCount(BigInteger value) {
        this.pageCount = value;
    }

}