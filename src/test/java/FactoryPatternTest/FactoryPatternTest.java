package FactoryPatternTest;

import builder.BusBuilder;
import builder.DieselTrainBuilder;
import builder.ElectricLightRailBuilder;
import builder.VehicleBuilder;
import factory.VehicleBuilderFactory;
import constants.VehicleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for VehicleBuilderFactory using Factory Pattern
 * Author: @Thuva
 */
public class FactoryPatternTest {

    @Test
    @DisplayName("Factory Pattern Test")
    void testFactoryPattern() {
        System.out.println(" Testing Factory Pattern...");

        // Test BUS builder
        VehicleBuilder busBuilder = VehicleBuilderFactory.createBuilder(VehicleType.BUS);
        assertNotNull(busBuilder, "Bus builder should not be null");
        assertTrue(busBuilder instanceof BusBuilder, "Should be instance of BusBuilder");

        // Test DIESEL TRAIN builder
        VehicleBuilder dieselBuilder = VehicleBuilderFactory.createBuilder(VehicleType.DIESELTRAIN);
        assertNotNull(dieselBuilder, "DieselTrain builder should not be null");
        assertTrue(dieselBuilder instanceof DieselTrainBuilder, "Should be instance of DieselTrainBuilder");

        // Test ELECTRIC LIGHT RAIL builder
        VehicleBuilder electricBuilder = VehicleBuilderFactory.createBuilder(VehicleType.ELECTRICLIGHTRAIL);
        assertNotNull(electricBuilder, "ElectricLightRail builder should not be null");
        assertTrue(electricBuilder instanceof ElectricLightRailBuilder, "Should be instance of ElectricLightRailBuilder");

        // Test invalid type
        Exception exception = assertThrows(IllegalArgumentException.class,
                () ->VehicleBuilderFactory.createBuilder("FACTORY_CAR"));
        assertEquals("Invalid vehicle type", exception.getMessage());

        System.out.println("Factory Pattern Test PASSED");
    }
}
