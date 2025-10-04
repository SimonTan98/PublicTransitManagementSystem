package BuilderPatternTest;

import builder.BusBuilder;
import transferobjects.BusDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuilderPatternTest {

    @Test
    @DisplayName("Builder Pattern Test for BusDTO")
    void testBuilderPattern() {
        System.out.println(" Testing Builder Pattern for BusDTO");

        // Custom bus with all values
        BusDTO bus = (BusDTO) new BusBuilder()
                .addVehicleNumber(3)
                .addVehicleName("BUS")
                .addVehicleType("City Bus")
                .addFuelType("Diesel")
                .addConsumptionRate(40.5)
                .addMaxCapacity(50)
                .addCurrentRouteId(3)
                .addAxleBearingsCondition(10.0)
                .addBrakesCondition(20.5)
                .addWheelsCondition(40.0)
                .addStatus("Active")
                .addLatitude(45.4215)
                .addLongitude(-75.6972)
                .addEmissionRate(5.5)
                .build();

        assertNotNull(bus, "Bus should be built successfully");
        assertEquals(3, bus.getVehicleID());
        assertEquals("BUS", bus.getVehicleName());
        assertEquals("City Bus", bus.getVehicleType());
        assertEquals("Diesel", bus.getFuelType());
        assertEquals(40.5, bus.getConsumptionRate());
        assertEquals(50, bus.getMaxCapacity());
        assertEquals(3, bus.getCurrentRouteID());
        assertEquals(10.0, bus.getAxleBearingsCondition());
        assertEquals(20.5, bus.getBrakesCondition());
        assertEquals(40.0, bus.getWheelsCondition());
        assertEquals("Active", bus.getStatus());
        assertEquals(45.4215, bus.getLatitude());
        assertEquals(-75.6972, bus.getLongitude());
        assertEquals(5.5, bus.getEmissionRate());

        
        BusDTO TestBus = (BusDTO) new BusBuilder()
                .addVehicleNumber(4)
                .addVehicleName("Test Bus") 
                .addVehicleType("Shuttle")
                .addFuelType("Electric")
                .build();

        assertNotNull(TestBus, "Test bus should be built with defaults");
        assertEquals(4, TestBus.getVehicleID());
        assertEquals("Test Bus", TestBus.getVehicleName());
        assertEquals("Shuttle", TestBus.getVehicleType());
        assertEquals("Electric", TestBus.getFuelType());

        System.out.println(" Builder Pattern Test PASSED");
    }
}