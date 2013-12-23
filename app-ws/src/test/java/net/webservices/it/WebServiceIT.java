package net.webservices.it;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import net.webservicex.AccelerationUnit;
import net.webservicex.AccelerationUnitSoap;
import net.webservicex.Accelerations;

import org.junit.Test;

public class WebServiceIT {
    @Test
    public void testSoap12() {
        final AccelerationUnitSoap accelerationUnitSoap12 = new AccelerationUnit()
                .getAccelerationUnitSoap12();
        assertThat(accelerationUnitSoap12.changeAccelerationUnit(1.00,
                Accelerations.GRAV, Accelerations.METER_PERSQUARESECOND),
                is(9.80665));
    }
}
