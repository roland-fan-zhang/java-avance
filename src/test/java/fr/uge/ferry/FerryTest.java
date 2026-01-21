package fr.uge.ferry;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.lang.reflect.AccessFlag;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class FerryTest {
  /*
  @Nested
  public class Q1 {
    @Test
    @DisplayName("Car creation with valid parameters")
    public void testCarCreationValid() {
      var car = new Car("John", 4, 2);
      assertEquals("John", car.ownerName());
      assertEquals(4, car.passengers());
      assertEquals(2, car.children());
    }

    @Test
    @DisplayName("Car creation with null owner name should throw exception")
    public void testCarCreationNullOwnerName() {
      assertThrows(NullPointerException.class, () -> new Car(null, 4, 2));
    }

    @Test
    @DisplayName("Car creation with negative passengers should throw exception")
    public void testCarCreationNegativePassengers() {
      var exception = assertThrows(IllegalArgumentException.class, () -> new Car("John", -1, 0));
      assertFalse(exception.getMessage().isEmpty());
    }

    @Test
    @DisplayName("Car creation with negative children should throw exception")
    public void testCarCreationNegativeChildren() {
      var exception = assertThrows(IllegalArgumentException.class, () -> new Car("John", 4, -1));
      assertFalse(exception.getMessage().isEmpty());
    }

    @Test
    @DisplayName("Car creation with children >= passengers should throw exception")
    public void testCarCreationChildrenGreaterThanPassengers() {
      var exception1 = assertThrows(IllegalArgumentException.class, () -> new Car("John", 2, 2));
      assertFalse(exception1.getMessage().isEmpty());

      var exception2 = assertThrows(IllegalArgumentException.class, () -> new Car("John", 2, 3));
      assertFalse(exception2.getMessage().isEmpty());
    }

    @Test
    @DisplayName("Car creation with valid parameters")
    public void testCarCreation() {
      var ownerName = "Alice";
      var passengers = 5;
      var children = 2;
      var car = new Car(ownerName, passengers, children);
      assertEquals(ownerName, car.ownerName());
      assertEquals(passengers, car.passengers());
      assertEquals(children, car.children());
    }

    @Test
    @DisplayName("Car creation with no children")
    public void testCarCreationNoChildren() {
      var ownerName = "John";
      var passengers = 1;
      var children = 0;
      var car = new Car(ownerName, passengers, children);
      assertEquals(ownerName, car.ownerName());
      assertEquals(passengers, car.passengers());
      assertEquals(children, car.children());
    }

    @Test
    @DisplayName("Car equals and hashCode")
    public void testCarEqualsAndHashCode() {
      var car1 = new Car("John", 4, 2);
      var car2 = new Car("John", 4, 2);
      var car3 = new Car("Alice", 4, 2);

      assertEquals(car1, car2);
      assertEquals(car1.hashCode(), car2.hashCode());
      assertNotEquals(car1, car3);
    }

    @Test
    @DisplayName("Car toString")
    public void testCarToString() {
      var car = new Car("John", 4, 2);
      var toString = car.toString();
      assertTrue(toString.contains("John"));
      assertTrue(toString.contains("4"));
      assertTrue(toString.contains("2"));
    }

    @Test
    @DisplayName("Car class should be public")
    public void testCarShouldBePublic() {
      assertTrue(Car.class.accessFlags().contains(AccessFlag.PUBLIC));
    }

    @Test
    @DisplayName("Car class should not allow inheritance")
    public void testCarClassShouldNotAllowInheritance() {
      assertTrue(Car.class.accessFlags().contains(AccessFlag.FINAL));
    }
  }


  @Nested
  public class Q2 {
    @Test
    @DisplayName("FerryParser with single car JSON")
    public void testFerryParserSingleCar() throws IOException {
      var jsonText =
          """
            [{
              "ownerName": "John",
              "passengers": 2,
              "children": 1
            }]
            """;

      var list = FerryParser.parse(jsonText);

      assertEquals(1, list.size());
      var first = list.getFirst();
      assertTrue(first instanceof Car);
      if (first instanceof Car car) {
        assertEquals("John", car.ownerName());
        assertEquals(2, car.passengers());
        assertEquals(1, car.children());
      }
    }

    @Test
    @DisplayName("FerryParser with empty JSON array")
    public void testFerryParserEmptyArray() throws IOException {
      var jsonText = "[]";
      var list = FerryParser.parse(jsonText);
      assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("FerryParser with invalid JSON should throw IOException")
    public void testFerryParserInvalidJson() {
      var jsonText = "invalid json";
      assertThrows(IOException.class, () -> FerryParser.parse(jsonText));
    }

    @Test
    @DisplayName("FerryParser with null should throw NullPointerException")
    public void testFerryParserINull() {
      assertThrows(NullPointerException.class, () -> FerryParser.parse(null));
    }

    @Test
    @DisplayName("FerryParser class should be public")
    public void testFerryParserShouldBePublic() {
      assertTrue(FerryParser.class.accessFlags().contains(AccessFlag.PUBLIC));
    }

    @Test
    @DisplayName("FerryParser class constructor should not be public")
    public void testFerryParserConstructor() {
      assertEquals(0, FerryParser.class.getConstructors().length);
    }

    @Test
    @DisplayName("FerryParser class should not allow inheritance")
    public void testFerryParserDoNotAllowInheritance() {
      assertTrue(FerryParser.class.accessFlags().contains(AccessFlag.FINAL));
    }

    @Test
    @DisplayName("FerryParser field should be a constant")
    public void testFerryParserFieldShouldBeAConstant() {
      assertTrue(
          Arrays.stream(FerryParser.class.getDeclaredFields())
              .allMatch(f -> f.accessFlags().containsAll(
                  Set.of(AccessFlag.PRIVATE, AccessFlag.FINAL, AccessFlag.STATIC))));
    }
  }


  @Nested
  public class Q3 {
    @Test
    @DisplayName("FerryFare with empty list")
    public void testFerryFareComputationEmpty() {
      var cars = List.<Car>of();
      var fare = FerryFare.computeFare(cars);
      assertTrue(fare.isEmpty());
    }

    @Test
    @DisplayName("FerryFare with single car")
    public void testFerryFareComputationSingleCar() {
      var car = new Car("John", 4, 2);
      var cars = List.of(car);
      var fare = FerryFare.computeFare(cars);

      assertEquals(1, fare.size());
      assertEquals(400, fare.get("John"));
      assertEquals(Map.of("John", 400), fare);
    }

    @Test
    @DisplayName("FerryFare with same owner multiple cars")
    public void testFerryFareComputationSameOwner() {
      var car1 = new Car("John", 4, 2);
      var car2 = new Car("John", 2, 1);
      var cars = List.of(car1, car2);

      var fare = FerryFare.computeFare(cars);

      assertEquals(1, fare.size());
      assertEquals(600, fare.get("John")); // 400 + 200
      assertEquals(Map.of("John", 600), fare);
    }

    @Test
    @DisplayName("FerryFare is implemented efficiently")
    public void testFerryFareWithALotOfCars() {
      var cars = IntStream.range(0, 1_000_000).mapToObj(i -> new Car("John" + i, 1, 0)).toList();

      var fare =
          assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> FerryFare.computeFare(cars));

      assertEquals(1_000_000, fare.size());
      assertEquals(100_000_000, fare.values().stream().mapToInt(v -> v).sum());
    }

    @Test
    @DisplayName("FerryFare is implemented efficiently (same owner)")
    public void testFerryFareWithALotOfCarsSameOwner() {
      var cars = IntStream.range(0, 1_000_000).mapToObj(_ -> new Car("John", 1, 0)).toList();

      var fare =
          assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> FerryFare.computeFare(cars));

      assertEquals(1, fare.size());
      assertEquals(100_000_000, fare.get("John"));
      assertEquals(Map.of("John", 100_000_000), fare);
    }

    @Test
    @DisplayName("FerryFare computeFare null should throw a NullPointerException")
    public void testFerryFareComputationNull() {
      assertThrows(NullPointerException.class, () -> FerryFare.computeFare(null));
    }

    @Test
    @DisplayName("FerryFare computeFare return an unmodifiable map")
    public void testFerryFareComputationMapIsUnmodifiable() {
      var cars = new ArrayList<Car>();
      cars.add(new Car("John", 1, 0));
      var fare = FerryFare.computeFare(cars);

      assertAll(
          () -> assertThrows(UnsupportedOperationException.class,
              () -> fare.put("John", 42)),
          () -> assertThrows(UnsupportedOperationException.class,
              () -> fare.remove("John")),
          () -> assertThrows(UnsupportedOperationException.class,
              fare::clear)
      );
    }

    @Test
    @DisplayName("FerryFare class should be public")
    public void testFerryFareClassShouldBePublic() {
      assertTrue(FerryFare.class.accessFlags().contains(AccessFlag.PUBLIC));
    }

    @Test
    @DisplayName("FerryFare class should not allow inheritance")
    public void testFerryFareClassShouldNotAllowInheritance() {
      assertTrue(FerryFare.class.accessFlags().contains(AccessFlag.FINAL));
    }

    @Test
    @DisplayName("FerryFare class constructor should not be public")
    public void testFerryFareClassConstructorShouldNotBePublic() {
      assertEquals(0, FerryFare.class.getConstructors().length);
    }

    @Test
    @DisplayName("FerryFare class should not have field")
    public void testFerryFareClassShouldNotHaveField() {
      assertEquals(0, FerryFare.class.getDeclaredFields().length);
    }
  }


  @Nested
  public class Q4 {
    @Test
    @DisplayName("Truck creation with valid parameters")
    public void testTruckCreationValid() {
      var truck = new Truck("World Inc", 1000);
      assertEquals("World Inc", truck.companyName());
      assertEquals(1000, truck.weight());
    }

    @Test
    @DisplayName("Truck creation with null company name should throw exception")
    public void testTruckCreationNullCompanyName() {
      assertThrows(NullPointerException.class, () -> new Truck(null, 1000));
    }

    @Test
    @DisplayName("Truck creation with negative weight should throw exception")
    public void testTruckCreationNegativeWeight() {
      var exception =
          assertThrows(IllegalArgumentException.class, () -> new Truck("World Inc", -1));
      assertFalse(exception.getMessage().isEmpty());
    }

    @Test
    @DisplayName("Truck creation with valid weights")
    public void testTruckCreation() {
      var truck = new Truck("Company", 100);
      assertEquals("Company", truck.companyName());
      assertEquals(100, truck.weight());
    }

    @Test
    @DisplayName("Truck creation with 0 weight")
    public void testTruckCreationZeroWeight() {
      var truck = new Truck("Company", 0);
      assertEquals("Company", truck.companyName());
      assertEquals(0, truck.weight());
    }

    @Test
    @DisplayName("Truck equals and hashCode")
    public void testTruckEqualsAndHashCode() {
      var truck1 = new Truck("World Inc", 1000);
      var truck2 = new Truck("World Inc", 1000);
      var truck3 = new Truck("Global Corp", 1000);

      assertEquals(truck1, truck2);
      assertEquals(truck1.hashCode(), truck2.hashCode());
      assertNotEquals(truck1, truck3);
    }

    @Test
    @DisplayName("Truck toString")
    public void testTruckToString() {
      var truck = new Truck("World Inc", 1000);
      var toString = truck.toString();
      assertTrue(toString.contains("World Inc"));
      assertTrue(toString.contains("1000"));
    }

    @Test
    @DisplayName("FerryFare with single truck")
    public void testFerryFareComputationSingleTruck() {
      var truck = new Truck("World Inc", 1000);
      var trucks = List.of(truck);
      var fare = FerryFare.computeFare(trucks);

      assertEquals(1, fare.size());
      assertEquals(2000, fare.get("World Inc"));
      assertEquals(Map.of("World Inc", 2000), fare);
    }

    @Test
    @DisplayName("FerryFare with multiple cars and trucks")
    public void testFerryFareComputationMultiple() {
      var car1 = new Car("John", 4, 2);
      var car2 = new Car("Alice", 2, 1);
      var truck1 = new Truck("World Inc", 1000);
      var truck2 = new Truck("Global Corp", 500);
      var list = List.of(car1, car2, truck1, truck2);

      var fare = FerryFare.computeFare(list);

      assertEquals(4, fare.size());
      assertEquals(400, fare.get("John"));
      assertEquals(200, fare.get("Alice"));
      assertEquals(2000, fare.get("World Inc"));
      assertEquals(1000, fare.get("Global Corp"));
      assertEquals(
          Map.of(
              "John", 400,
              "Alice", 200,
              "World Inc", 2000,
              "Global Corp", 1000),
          fare);
    }

    @Test
    @DisplayName("FerryFare with same company multiple trucks")
    public void testFerryFareComputationSameCompany() {
      var truck1 = new Truck("World Inc", 1000);
      var truck2 = new Truck("World Inc", 500);
      var trucks = List.of(truck1, truck2);

      var fare = FerryFare.computeFare(trucks);

      assertEquals(1, fare.size());
      assertEquals(3000, fare.get("World Inc")); // 2000 + 1000
      assertEquals(Map.of("World Inc", 3000), fare);
    }

    @Test
    @DisplayName("FerryFare computeFare return an unmodifiable map")
    public void testFerryFareComputationMapIsUnmodifiable() {
      var truck = new ArrayList<Truck>();
      truck.add(new Truck("John", 100));
      var fare = FerryFare.computeFare(truck);

      assertAll(
          () -> assertThrows(UnsupportedOperationException.class,
              () -> fare.put("John", 42)),
          () -> assertThrows(UnsupportedOperationException.class,
              () -> fare.remove("John")),
          () -> assertThrows(UnsupportedOperationException.class,
              fare::clear)
      );
    }

    @Test
    @DisplayName("Truck class should be public")
    public void testTruckClassShouldBePublic() {
      assertTrue(Truck.class.accessFlags().contains(AccessFlag.PUBLIC));
    }

    @Test
    @DisplayName("Truck class is correctly implemented")
    public void testTruckClassShouldNotAllowInheritance() {
      assertTrue(Truck.class.accessFlags().contains(AccessFlag.FINAL));
    }

    @Test
    @DisplayName("Common super type is correctly implemented")
    public void testCommonSupertypeIsCorrectlyIsCorrectlyImplemented() {
      var carSupertypes = Set.of(Car.class.getInterfaces());
      var truckSupertypes = Set.of(Truck.class.getInterfaces());
      var supertypes = new LinkedHashSet<>(carSupertypes);
      supertypes.retainAll(truckSupertypes);

      assertEquals(1, supertypes.size());
      var superType = supertypes.getFirst();
      assertAll(
          () -> assertTrue(superType.accessFlags().contains(AccessFlag.PUBLIC)),
          () -> assertNotNull(superType.getPermittedSubclasses()));
    }
  }


  @Nested
  public class Q5 {
    @Test
    @DisplayName("FerryParser with single truck JSON")
    public void testFerryParserSingleTruck() throws IOException {
      var jsonText =
          """
            [{
              "companyName": "World Inc",
              "weight": 1000
            }]
            """;

      var list = FerryParser.parse(jsonText);

      assertEquals(1, list.size());
      var first = list.getFirst();
      assertTrue(first instanceof Truck);
      if (first instanceof Truck truck) {
        assertEquals("World Inc", truck.companyName());
        assertEquals(1000, truck.weight());
      }
    }

    @Test
    @DisplayName("FerryParser with mixed cars and trucks JSON")
    public void testFerryParserMixed() throws IOException {
      var jsonText =
          """
            [{
              "ownerName": "John",
              "passengers": 2,
              "children": 1
            },
            {
              "companyName": "World Inc",
              "weight": 1000
            }]
            """;

      var list = FerryParser.parse(jsonText);

      assertEquals(2, list.size());

      var first = list.getFirst();
      assertTrue(first instanceof Car);
      if (first instanceof Car car) {
        assertEquals("John", car.ownerName());
        assertEquals(2, car.passengers());
        assertEquals(1, car.children());
      }

      var last = list.getLast();
      assertTrue(last instanceof Truck);
      if (last instanceof Truck truck) {
        assertEquals("World Inc", truck.companyName());
        assertEquals(1000, truck.weight());
      }
    }

    @Test
    @DisplayName("Integration test with parsing and fare computation")
    public void testIntegrationParsingAndFareComputation() throws IOException {
      var jsonText =
          """
              [{
                "ownerName": "John",
                "passengers": 2,
                "children": 1
              },
              {
                "companyName": "World Inc",
                "weight": 1000
              }]
              """;

      var list = FerryParser.parse(jsonText);
      var fare = FerryFare.computeFare(list);

      assertEquals(2, fare.size());
      assertEquals(200, fare.get("John"));
      assertEquals(2000, fare.get("World Inc"));
      assertEquals(Map.of("John", 200, "World Inc", 2000), fare);
      assertThrows(UnsupportedOperationException.class, () -> fare.put("John", 42));
    }
  }


  @Nested
  public class Q6 {
    @Test
    @DisplayName("computeFare preserve the ordering")
    public void testFarePreserveOrdering() {
      var cars =
          List.of(
              new Car("John", 1, 0),
              new Car("Jane", 1, 0),
              new Truck("Joan", 300),
              new Car("Jean", 1, 0));

      var fare = FerryFare.computeFare(cars);
      var fareNames = List.copyOf(fare.keySet());

      assertEquals(List.of("John", "Jane", "Joan", "Jean"), fareNames);
    }

    @Test
    @DisplayName("computeFare preserve the ordering (same owner or company)")
    public void testFarePreserveOrderingWithSameOwnerOrCompany() {
      var cars =
          List.of(
              new Car("John", 1, 0),
              new Car("Jane", 1, 0),
              new Truck("John", 700),
              new Car("Jean", 1, 0));

      var fare = FerryFare.computeFare(cars);
      var fareNames = List.copyOf(fare.keySet());

      assertEquals(List.of("John", "Jane", "Jean"), fareNames);
    }

    @Test
    @DisplayName("computeFare preserve the ordering (a lot of cars)")
    public void testFarePreserveOrderingWithALotOfCars() {
      var cars = IntStream.range(0, 1_000_000).mapToObj(i -> new Car("John" + i, 1, 0)).toList();

      var fare =
          assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> FerryFare.computeFare(cars));

      var count = 0;
      for (var ownerName : fare.keySet()) {
        assertEquals("John" + count++, ownerName);
      }
    }

    @Test
    @DisplayName("computeFare preserve the ordering (a lot of trucks)")
    public void testFarePreserveOrderingWithALotOfTrucks() {
      var trucks =
          IntStream.range(0, 1_000_000).mapToObj(i -> new Truck("Company" + i, 100)).toList();

      var fare =
          assertTimeoutPreemptively(Duration.ofMillis(1_000), () -> FerryFare.computeFare(trucks));

      var count = 0;
      for (var ownerName : fare.keySet()) {
        assertEquals("Company" + count++, ownerName);
      }
    }
  }


  @Nested
  public class Q7 {
    @Test
    @DisplayName("computeFareWithFleetDiscount with empty list")
    public void testComputeFareWithFleetDiscountEmpty() {
      var fare = FerryFare.computeFareWithFleetDiscount(List.of(), 3);
      assertTrue(fare.isEmpty());
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with single car below fleet size")
    public void testComputeFareWithFleetDiscountSingleCarBelowFleetSize() {
      var car = new Car("John", 4, 2);
      var cars = List.of(car);
      var fare = FerryFare.computeFareWithFleetDiscount(cars, 3);

      assertEquals(1, fare.size());
      assertEquals(360, fare.get("John")); // 400 * 90/100 = 360 (10% discount)
      assertEquals(Map.of("John", 360), fare);
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with single car at fleet size")
    public void testComputeFareWithFleetDiscountSingleCarAtFleetSize() {
      var car = new Car("John", 4, 2);
      var cars = List.of(car);
      var fare = FerryFare.computeFareWithFleetDiscount(cars, 1);

      assertEquals(1, fare.size());
      assertEquals(400, fare.get("John")); // No discount
      assertEquals(Map.of("John", 400), fare);
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with multiple cars same owner below fleet size")
    public void testComputeFareWithFleetDiscountMultipleCarsBelowFleetSize() {
      var car1 = new Car("John", 4, 2);
      var car2 = new Car("John", 2, 1);
      var cars = List.of(car1, car2);
      var fare = FerryFare.computeFareWithFleetDiscount(cars, 3);

      assertEquals(1, fare.size());
      assertEquals(540, fare.get("John")); // (400 + 200) * 90/100 = 540
      assertEquals(Map.of("John", 540), fare);
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with multiple cars same owner at fleet size")
    public void testComputeFareWithFleetDiscountMultipleCarsAtFleetSize() {
      var car1 = new Car("John", 4, 2);
      var car2 = new Car("John", 2, 1);
      var car3 = new Car("John", 1, 0);
      var cars = List.of(car1, car2, car3);
      var fare = FerryFare.computeFareWithFleetDiscount(cars, 3);

      assertEquals(1, fare.size());
      assertEquals(700, fare.get("John")); // 400 + 200 + 100 = 700 (no discount)
      assertEquals(Map.of("John", 700), fare);
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with trucks below fleet size")
    public void testComputeFareWithFleetDiscountTrucksBelowFleetSize() {
      var truck1 = new Truck("WorldInc", 1000);
      var truck2 = new Truck("WorldInc", 500);
      var trucks = List.of(truck1, truck2);
      var fare = FerryFare.computeFareWithFleetDiscount(trucks, 3);

      assertEquals(1, fare.size());
      assertEquals(2700, fare.get("WorldInc")); // (2000 + 1000) * 90/100 = 2700
      assertEquals(Map.of("WorldInc", 2700), fare);
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with mixed cars and trucks")
    public void testComputeFareWithFleetDiscountMixedCarsAndTrucks() {
      var car1 = new Car("John", 4, 2);
      var car2 = new Car("John", 2, 1);
      var truck1 = new Truck("WorldInc", 1000);
      var truck2 = new Truck("WorldInc", 500);
      var car3 = new Car("Alice", 3, 1);
      var list = List.of(car1, car2, truck1, truck2, car3);
      var fare = FerryFare.computeFareWithFleetDiscount(list, 3);

      assertEquals(3, fare.size());
      assertEquals(540, fare.get("John")); // 2 cars < 3, so discount: 600 * 90/100 = 540
      assertEquals(2700, fare.get("WorldInc")); // 2 trucks < 3, so discount: 3000 * 90/100 = 2700
      assertEquals(270, fare.get("Alice")); // 1 car < 3, so discount: 300 * 90/100 = 270
      assertEquals(
          Map.of(
              "John", 540,
              "WorldInc", 2700,
              "Alice", 270),
          fare);
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with mixed fleet sizes")
    public void testComputeFareWithFleetDiscountMixedFleetSizes() {
      var car1 = new Car("John", 4, 2);
      var car2 = new Car("John", 2, 1);
      var car3 = new Car("John", 1, 0);
      var truck1 = new Truck("WorldInc", 1000);
      var car4 = new Car("Alice", 3, 1);
      var list = List.of(car1, car2, car3, truck1, car4);
      var fare = FerryFare.computeFareWithFleetDiscount(list, 2);

      assertEquals(3, fare.size());
      assertEquals(700, fare.get("John")); // 3 cars >= 2, no discount: 400 + 200 + 100 = 700
      assertEquals(1800, fare.get("WorldInc")); // 1 truck < 2, discount: 2000 * 90/100 = 1800
      assertEquals(270, fare.get("Alice")); // 1 car < 2, discount: 300 * 90/100 = 270
      assertEquals(
          Map.of(
              "John", 700,
              "WorldInc", 1800,
              "Alice", 270),
          fare);
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount preserves ordering")
    public void testComputeFareWithFleetDiscountPreservesOrdering() {
      var list =
          List.of(
              new Car("John", 1, 0),
              new Car("Jane", 1, 0),
              new Truck("Joan", 300),
              new Car("Jean", 1, 0));

      var fare = FerryFare.computeFareWithFleetDiscount(list, 3);
      var fareNames = List.copyOf(fare.keySet());

      assertEquals(List.of("John", "Jane", "Joan", "Jean"), fareNames);
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount preserve the ordering (a lot of cars)")
    public void testComputeFareWithFleetDiscountPreserveOrderingWithALotOfCars() {
      var cars = IntStream.range(0, 1_000_000).mapToObj(i -> new Car("John" + i, 1, 0)).toList();

      var fare =
          assertTimeoutPreemptively(
              Duration.ofMillis(1_000), () -> FerryFare.computeFareWithFleetDiscount(cars, 2));

      var count = 0;
      for (var ownerName : fare.keySet()) {
        assertEquals("John" + count++, ownerName);
      }
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount preserve the ordering (a lot of trucks)")
    public void testComputeFareWithFleetDiscountPreserveOrderingWithALotOfTrucks() {
      var trucks =
          IntStream.range(0, 1_000_000).mapToObj(i -> new Truck("Company" + i, 100)).toList();

      var fare =
          assertTimeoutPreemptively(
              Duration.ofMillis(1_000), () -> FerryFare.computeFareWithFleetDiscount(trucks, 2));

      var count = 0;
      for (var ownerName : fare.keySet()) {
        assertEquals("Company" + count++, ownerName);
      }
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount return an unmodifiable map")
    public void testComputeFareWithFleetDiscountMapIsUnmodifiable() {
      var list =
          List.of(
              new Car("John", 1, 0),
              new Car("Jane", 1, 0));

      var fare = FerryFare.computeFareWithFleetDiscount(list, 1);

      assertAll(
          () -> assertThrows(UnsupportedOperationException.class,
              () -> fare.put("John", 42)),
          () -> assertThrows(UnsupportedOperationException.class,
              () -> fare.remove("John")),
          () -> assertThrows(UnsupportedOperationException.class,
              fare::clear)
      );
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with null should throw NPE")
    public void testComputeFareWithFleetDiscountNull() {
      assertThrows(
          NullPointerException.class, () -> FerryFare.computeFareWithFleetDiscount(null, 3));
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with zero fleet size")
    public void testComputeFareWithFleetDiscountZeroFleetSize() {
      var car = new Car("John", 4, 2);
      var cars = List.of(car);
      var fare = FerryFare.computeFareWithFleetDiscount(cars, 0);

      assertEquals(1, fare.size());
      assertEquals(400, fare.get("John")); // 1 >= 0, so no discount
      assertEquals(Map.of("John", 400), fare);
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with negative fleet size")
    public void testComputeFareWithFleetDiscountNegativeFleetSize() {
      var exception = assertThrows(IllegalArgumentException.class,
          () -> FerryFare.computeFareWithFleetDiscount(List.of(), -1));
      assertFalse(exception.getMessage().isEmpty());
    }
  }


  @Nested
  public class Q8 {
    @Test
    @DisplayName("computeFareWithFleetDiscount with DiscountFunction - no discount")
    public void testComputeFareWithDiscountFunctionNoDiscount() {
      var car = new Car("John", 4, 2);
      var cars = List.of(car);
      var fare =
          FerryFare.computeFareWithFleetDiscount(
              cars, (fareAmount, _) -> fareAmount); // No discount

      assertEquals(1, fare.size());
      assertEquals(400, fare.get("John"));
      assertEquals(Map.of("John", 400), fare);
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with DiscountFunction - 50% discount")
    public void testComputeFareWithDiscountFunctionHalfDiscount() {
      var car1 = new Car("John", 4, 2);
      var car2 = new Car("John", 2, 1);
      var cars = List.of(car1, car2);
      var fare =
          FerryFare.computeFareWithFleetDiscount(
              cars, (fareAmount, _) -> fareAmount / 2); // 50% discount

      assertEquals(1, fare.size());
      assertEquals(300, fare.get("John")); // (400 + 200) / 2 = 300
      assertEquals(Map.of("John", 300), fare);
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with DiscountFunction - fleet size based discount")
    public void testComputeFareWithDiscountFunctionFleetSizeBased() {
      var car1 = new Car("John", 4, 2);
      var car2 = new Car("John", 2, 1);
      var truck1 = new Truck("WorldInc", 1000);
      var car3 = new Car("Alice", 3, 1);
      var list = List.of(car1, car2, truck1, car3);

      // Discount based on fleet size: if fleet size > 1, apply 20% discount
      var fare =
          FerryFare.computeFareWithFleetDiscount(
              list, (fareAmount, fleet) -> fleet.size() > 1 ? fareAmount * 80 / 100 : fareAmount);

      assertEquals(3, fare.size());
      assertEquals(480, fare.get("John")); // 2 cars > 1, discount: 600 * 80/100 = 480
      assertEquals(2000, fare.get("WorldInc")); // 1 truck = 1, no discount
      assertEquals(300, fare.get("Alice")); // 1 car = 1, no discount
      assertEquals(
          Map.of(
              "John", 480,
              "WorldInc", 2000,
              "Alice", 300),
          fare);
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with DiscountFunction - type based discount")
    public void testComputeFareWithDiscountFunctionTypeBased() {
      var car = new Car("John", 4, 2);
      var truck = new Truck("WorldInc", 1000);
      var list = List.of(car, truck);

      // Discount based on the types: 10% discount for trucks, no discount for cars
      var fare =
          FerryFare.computeFareWithFleetDiscount(
              list,
              (fareAmount, fleet) -> {
                var onlyTrucks = fleet.stream().allMatch(v -> v instanceof Truck);
                return onlyTrucks ? fareAmount * 90 / 100 : fareAmount;
              });

      assertEquals(2, fare.size());
      assertEquals(400, fare.get("John")); // Car, no discount
      assertEquals(1800, fare.get("WorldInc")); // Truck, 10% discount: 2000 * 90/100 = 1800
      assertEquals(Map.of("John", 400, "WorldInc", 1800), fare);
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with DiscountFunction - complex logic")
    public void testComputeFareWithDiscountFunctionComplexLogic() {
      var car1 = new Car("John", 4, 2);
      var car2 = new Car("John", 2, 1);
      var car3 = new Car("John", 1, 0);
      var truck1 = new Truck("WorldInc", 1000);
      var truck2 = new Truck("WorldInc", 500);
      var list = List.of(car1, car2, car3, truck1, truck2);

      // Complex discount: if fare > 1000 and fleet size >= 2, apply 15% discount
      var fare =
          FerryFare.computeFareWithFleetDiscount(
              list,
              (fareAmount, fleet) ->
                  fareAmount > 1000 && fleet.size() >= 2 ? fareAmount * 85 / 100 : fareAmount);

      assertEquals(2, fare.size());
      assertEquals(700, fare.get("John")); // 700 <= 1000, no discount
      assertEquals(
          2550, fare.get("WorldInc")); // 3000 > 1000 and 2 >= 2, discount: 3000 * 85/100 = 2550
      assertEquals(Map.of("John", 700, "WorldInc", 2550), fare);
    }

    @Test
    @DisplayName("computeFareWithFleetDiscount with DiscountFunction - empty list")
    public void testComputeFareWithDiscountFunctionEmpty() {
      var fare = FerryFare.computeFareWithFleetDiscount(List.of(), (_, _) -> fail());

      assertTrue(fare.isEmpty());
    }

    @Test
    @DisplayName("computeFareWithDiscount with DiscountFunction preserves ordering")
    public void testComputeFareWithDiscountFunctionPreservesOrdering() {
      var list =
          List.of(
              new Car("John", 1, 0),
              new Car("Jane", 1, 0),
              new Truck("Joan", 300),
              new Car("Jean", 1, 0));

      var fare = FerryFare.computeFareWithFleetDiscount(list, (fareAmount, _) -> fareAmount);
      var fareNames = List.copyOf(fare.keySet());

      assertEquals(List.of("John", "Jane", "Joan", "Jean"), fareNames);
    }

    @Test
    @DisplayName("computeFareWithDiscount with DiscountFunction preserve the ordering (a lot of cars)")
    public void testFarePreserveOrderingWithALotOfCars() {
      var cars = IntStream.range(0, 1_000_000).mapToObj(i -> new Car("John" + i, 1, 0)).toList();

      var fare =
          assertTimeoutPreemptively(
              Duration.ofMillis(1_000),
              () -> FerryFare.computeFareWithFleetDiscount(cars, (fareAmount, _) -> fareAmount));

      var count = 0;
      for (var ownerName : fare.keySet()) {
        assertEquals("John" + count++, ownerName);
      }
    }

    @Test
    @DisplayName("computeFareWithDiscount with DiscountFunction preserve the ordering (a lot of trucks)")
    public void testFarePreserveOrderingWithALotOfTrucks() {
      var trucks =
          IntStream.range(0, 1_000_000).mapToObj(i -> new Truck("Company" + i, 100)).toList();

      var fare =
          assertTimeoutPreemptively(
              Duration.ofMillis(1_000),
              () -> FerryFare.computeFareWithFleetDiscount(trucks, (fareAmount, _) -> fareAmount));

      var count = 0;
      for (var ownerName : fare.keySet()) {
        assertEquals("Company" + count++, ownerName);
      }
    }

    @Test
    @DisplayName("computeFareWithDiscount with DiscountFunction return an unmodifiable map")
    public void testComputeFareWithDiscountFunctionMapIsUnmodifiable() {
      var list =
          List.of(
              new Car("John", 1, 0),
              new Car("Jane", 1, 0));

      var fare = FerryFare.computeFareWithFleetDiscount(list, (fareAmount, _) -> fareAmount);

      assertAll(
          () -> assertThrows(UnsupportedOperationException.class,
              () -> fare.put("John", 42)),
          () -> assertThrows(UnsupportedOperationException.class,
              () -> fare.remove("John")),
          () -> assertThrows(UnsupportedOperationException.class,
              fare::clear)
      );
    }

    @Test
    @DisplayName("computeFareWithDiscount with DiscountFunction with null list should throw NPE")
    public void testComputeFareWithDiscountFunctionNull() {
      assertThrows(
          NullPointerException.class,
          () -> FerryFare.computeFareWithFleetDiscount(null, (_, _) -> fail()));
    }

    @Test
    @DisplayName("computeFareWithDiscount with DiscountFunction with null function should throw NPE")
    public void testComputeFareWithDiscountFunctionNullFunction() {
      assertThrows(
          NullPointerException.class,
          () -> FerryFare.computeFareWithFleetDiscount(List.of(), null));
    }
  }
   */
}
