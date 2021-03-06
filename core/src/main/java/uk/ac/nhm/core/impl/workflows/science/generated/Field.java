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
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}addresses"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}academic-appointments"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}degrees"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}email-addresses"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}keywords"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}money"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.symplectic.co.uk/publications/api}text" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}identifiers"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}links"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}person"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}boolean"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}date"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}integer"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}items"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}non-academic-employments"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}pagination"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}people"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}phone-numbers"/>
 *           &lt;element ref="{http://www.symplectic.co.uk/publications/api}web-addresses"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="display-name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "addresses",
    "academicAppointments",
    "degrees",
    "emailAddresses",
    "keywords",
    "money",
    "text",
    "identifiers",
    "links",
    "person",
    "_boolean",
    "date",
    "integer",
    "items",
    "nonAcademicEmployments",
    "pagination",
    "people",
    "phoneNumbers",
    "webAddresses"
})
@XmlRootElement(name = "field")
public class Field {

    protected Addresses addresses;
    @XmlElement(name = "academic-appointments")
    protected AcademicAppointments academicAppointments;
    protected Degrees degrees;
    @XmlElement(name = "email-addresses")
    protected EmailAddresses emailAddresses;
    protected Keywords keywords;
    protected Money money;
    protected String text;
    protected Identifiers identifiers;
    protected Links links;
    protected Person person;
    @XmlElement(name = "boolean")
    protected Boolean _boolean;
    protected Date date;
    protected BigInteger integer;
    protected Items items;
    @XmlElement(name = "non-academic-employments")
    protected NonAcademicEmployments nonAcademicEmployments;
    protected Pagination pagination;
    protected People people;
    @XmlElement(name = "phone-numbers")
    protected PhoneNumbers phoneNumbers;
    @XmlElement(name = "web-addresses")
    protected WebAddresses webAddresses;
    @XmlAttribute(name = "display-name", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String displayName;
    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String name;
    @XmlAttribute(name = "type", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String type;

    /**
     * Gets the value of the addresses property.
     * 
     * @return
     *     possible object is
     *     {@link Addresses }
     *     
     */
    public Addresses getAddresses() {
        return addresses;
    }

    /**
     * Sets the value of the addresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link Addresses }
     *     
     */
    public void setAddresses(Addresses value) {
        this.addresses = value;
    }

    /**
     * Gets the value of the academicAppointments property.
     * 
     * @return
     *     possible object is
     *     {@link AcademicAppointments }
     *     
     */
    public AcademicAppointments getAcademicAppointments() {
        return academicAppointments;
    }

    /**
     * Sets the value of the academicAppointments property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcademicAppointments }
     *     
     */
    public void setAcademicAppointments(AcademicAppointments value) {
        this.academicAppointments = value;
    }

    /**
     * Gets the value of the degrees property.
     * 
     * @return
     *     possible object is
     *     {@link Degrees }
     *     
     */
    public Degrees getDegrees() {
        return degrees;
    }

    /**
     * Sets the value of the degrees property.
     * 
     * @param value
     *     allowed object is
     *     {@link Degrees }
     *     
     */
    public void setDegrees(Degrees value) {
        this.degrees = value;
    }

    /**
     * Gets the value of the emailAddresses property.
     * 
     * @return
     *     possible object is
     *     {@link EmailAddresses }
     *     
     */
    public EmailAddresses getEmailAddresses() {
        return emailAddresses;
    }

    /**
     * Sets the value of the emailAddresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailAddresses }
     *     
     */
    public void setEmailAddresses(EmailAddresses value) {
        this.emailAddresses = value;
    }

    /**
     * Gets the value of the keywords property.
     * 
     * @return
     *     possible object is
     *     {@link Keywords }
     *     
     */
    public Keywords getKeywords() {
        return keywords;
    }

    /**
     * Sets the value of the keywords property.
     * 
     * @param value
     *     allowed object is
     *     {@link Keywords }
     *     
     */
    public void setKeywords(Keywords value) {
        this.keywords = value;
    }

    /**
     * Gets the value of the money property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getMoney() {
        return money;
    }

    /**
     * Sets the value of the money property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setMoney(Money value) {
        this.money = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Gets the value of the identifiers property.
     * 
     * @return
     *     possible object is
     *     {@link Identifiers }
     *     
     */
    public Identifiers getIdentifiers() {
        return identifiers;
    }

    /**
     * Sets the value of the identifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Identifiers }
     *     
     */
    public void setIdentifiers(Identifiers value) {
        this.identifiers = value;
    }

    /**
     * Gets the value of the links property.
     * 
     * @return
     *     possible object is
     *     {@link Links }
     *     
     */
    public Links getLinks() {
        return links;
    }

    /**
     * Sets the value of the links property.
     * 
     * @param value
     *     allowed object is
     *     {@link Links }
     *     
     */
    public void setLinks(Links value) {
        this.links = value;
    }

    /**
     * Gets the value of the person property.
     * 
     * @return
     *     possible object is
     *     {@link Person }
     *     
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the value of the person property.
     * 
     * @param value
     *     allowed object is
     *     {@link Person }
     *     
     */
    public void setPerson(Person value) {
        this.person = value;
    }

    /**
     * Gets the value of the boolean property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBoolean() {
        return _boolean;
    }

    /**
     * Sets the value of the boolean property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBoolean(Boolean value) {
        this._boolean = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDate(Date value) {
        this.date = value;
    }

    /**
     * Gets the value of the integer property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getInteger() {
        return integer;
    }

    /**
     * Sets the value of the integer property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setInteger(BigInteger value) {
        this.integer = value;
    }

    /**
     * Gets the value of the items property.
     * 
     * @return
     *     possible object is
     *     {@link Items }
     *     
     */
    public Items getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value
     *     allowed object is
     *     {@link Items }
     *     
     */
    public void setItems(Items value) {
        this.items = value;
    }

    /**
     * Gets the value of the nonAcademicEmployments property.
     * 
     * @return
     *     possible object is
     *     {@link NonAcademicEmployments }
     *     
     */
    public NonAcademicEmployments getNonAcademicEmployments() {
        return nonAcademicEmployments;
    }

    /**
     * Sets the value of the nonAcademicEmployments property.
     * 
     * @param value
     *     allowed object is
     *     {@link NonAcademicEmployments }
     *     
     */
    public void setNonAcademicEmployments(NonAcademicEmployments value) {
        this.nonAcademicEmployments = value;
    }

    /**
     * Gets the value of the pagination property.
     * 
     * @return
     *     possible object is
     *     {@link Pagination }
     *     
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * Sets the value of the pagination property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pagination }
     *     
     */
    public void setPagination(Pagination value) {
        this.pagination = value;
    }

    /**
     * Gets the value of the people property.
     * 
     * @return
     *     possible object is
     *     {@link People }
     *     
     */
    public People getPeople() {
        return people;
    }

    /**
     * Sets the value of the people property.
     * 
     * @param value
     *     allowed object is
     *     {@link People }
     *     
     */
    public void setPeople(People value) {
        this.people = value;
    }

    /**
     * Gets the value of the phoneNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumbers }
     *     
     */
    public PhoneNumbers getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     * Sets the value of the phoneNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumbers }
     *     
     */
    public void setPhoneNumbers(PhoneNumbers value) {
        this.phoneNumbers = value;
    }

    /**
     * Gets the value of the webAddresses property.
     * 
     * @return
     *     possible object is
     *     {@link WebAddresses }
     *     
     */
    public WebAddresses getWebAddresses() {
        return webAddresses;
    }

    /**
     * Sets the value of the webAddresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link WebAddresses }
     *     
     */
    public void setWebAddresses(WebAddresses value) {
        this.webAddresses = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
