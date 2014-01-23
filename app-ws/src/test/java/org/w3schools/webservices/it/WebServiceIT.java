package org.w3schools.webservices.it;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.w3schools.webservices.TempConvert;
import com.w3schools.webservices.TempConvertSoap;

public class WebServiceIT {
    @Test
    public void testSoap12() {
        final TempConvertSoap soap = new TempConvert().getTempConvertSoap12();
        assertThat(soap.celsiusToFahrenheit("0"), is("32"));
    }
}
