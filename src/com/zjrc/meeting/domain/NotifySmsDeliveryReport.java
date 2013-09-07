package com.zjrc.meeting.domain;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
 *         &lt;element name="deliveryReport" type="{http://entity.openmas.chinamobile.com/xsd}DeliveryReport" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "deliveryReport" })
@XmlRootElement(name = "NotifySmsDeliveryReport")
public class NotifySmsDeliveryReport {

	@XmlElementRef(name = "deliveryReport", namespace = "http://openmas.chinamobile.com/pulgin", type = JAXBElement.class)
	protected JAXBElement<DeliveryReport> deliveryReport;

	/**
	 * Gets the value of the deliveryReport property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link JAXBElement }{@code <}{@link DeliveryReport }{@code >}
	 *     
	 */
	public JAXBElement<DeliveryReport> getDeliveryReport() {
		return deliveryReport;
	}

	/**
	 * Sets the value of the deliveryReport property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link JAXBElement }{@code <}{@link DeliveryReport }{@code >}
	 *     
	 */
	public void setDeliveryReport(JAXBElement<DeliveryReport> value) {
		this.deliveryReport = value;
	}

}
