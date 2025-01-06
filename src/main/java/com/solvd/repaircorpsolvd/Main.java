package com.solvd.repaircorpsolvd;

import com.solvd.repaircorpsolvd.accounting.AccountingProcesses;
import com.solvd.repaircorpsolvd.company.RepCorp;
import com.solvd.repaircorpsolvd.company.RepairService;
import com.solvd.repaircorpsolvd.custom_linked_list.CustomLinkedList;
import com.solvd.repaircorpsolvd.operations.DeliverOrder;
import com.solvd.repaircorpsolvd.operations.PartsOrder;
import com.solvd.repaircorpsolvd.operations.RepairOrder;
import com.solvd.repaircorpsolvd.operations.RepairType;
import com.solvd.repaircorpsolvd.resources.*;
import com.solvd.repaircorpsolvd.staff.*;
import com.solvd.repaircorpsolvd.support.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        //possible addresses
        Address repairServiceAddress = new Address("Zakopane", "01-900", "Krut", "99");
        Address deliverOrderAddress = new Address("Warsaw", "05-090", "Krakowska", "3");
        Address corpAddress = new Address("Warsaw", "05-090", "Main", "15");

        Addresses.addAddresses(new ArrayList<>(List.of(repairServiceAddress, deliverOrderAddress, corpAddress)));
        Address checkAddress = new Address("Warsaw", "05-090", "Main", "14");
        // Addresses.addressExists(checkAddress); throws unknow address
        RepairService repairService = null;
        try {
            repairService = new RepairService
                    ("Whoop and done", "Mobile", repairServiceAddress, 400., new BigDecimal("8000"));
        } catch (AddressNotFoundException | NegativeValueException e) {
            LOGGER.info(e.getMessage());
        }

        // test wrong address
        RepairService repairService1 = null;
        try {
            repairService1 = new RepairService("Whoop and done", "Mobile", checkAddress, 400., new BigDecimal("8000"));
        } catch (AddressNotFoundException | NegativeValueException e) {
            LOGGER.info(e.getMessage());
        }
        // will fail due to the not init instance
        // LOGGER.info("{}", repairService1.getProfile());
        LOGGER.info("{}", repairService1);


        TeamLead teamLead = new TeamLead("Ali", "Baba", 18, JobPosition.TEAMLEAD, "+482322235", "Mobile");
        teamLead.hire();
        RepairTechnician repairTechnician = new RepairTechnician("Josef", "Biden", 28, JobPosition.REPAIR_MASTER, "+482322225", "Mobile");
        repairTechnician.hire();
        repairTechnician.setSpecification(Specification.MOBILE);
        Employee dekiveryMan = new Employee("Hilel", "Bubaleh", 48, JobPosition.DELIVERY, "+482322215");
        dekiveryMan.hire();
        dekiveryMan.setStatusReady(true);
        Accountant accountant = new Accountant("Alisa", "Garmin", 33, JobPosition.ACCOUNTANT, "+482322115");

        repairService.addEmployee(teamLead);
        repairService.removeEmployee(teamLead);
        repairService.addEmployee(teamLead);
        repairService.addEmployee(repairTechnician);
        repairService.addEmployee(dekiveryMan);
        repairService.setTeamLead(teamLead);
        repairService.getTeamLead().notifyPerson("Do something");

        LOGGER.info("{}", dekiveryMan);

        Mobile device1 = new Mobile("Motorola", "G54");
        Mobile device2 = new Mobile("Samsung", "L10");
        Device device3 = new Laptop("Lenovo", "IdeaPad");
        Tablet device4 = new Tablet("Samsung", "TAB S10");
        device4.setNetworkType(NetworkType.FIVE_G);
        device1.setNetworkType(NetworkType.FIVE_G);
        device2.setNetworkType(NetworkType.FIVE_G);

        device4.setUsbType(UsbType.USB_C);
        device1.setUsbType(UsbType.USB_A);
        device2.setUsbType(UsbType.USB_3_1);

        // when can't call        device3.getCamera()  from child class Laptop as declared Device

        Customer customer1 = new Customer("Abu", "Bandit", 18);
        customer1.setDevice(device1);
        customer1.setEmail("olo@gmail.com");
        LOGGER.info("{}", customer1);

        RepairOrder repairOrder1 = new RepairOrder();
        repairOrder1.setCustomer(customer1);
        repairOrder1.addDevice(device1);
        repairOrder1.addDevice(device2);
        repairOrder1.addDevice(device3);
        repairOrder1.setEstimateCost(new BigDecimal("100.00"));
        RepairOrder repairOrder2 = new RepairOrder();
        repairOrder2.setCustomer(customer1);
        repairOrder2.setEstimateCost(new BigDecimal("500.00"));

        RepairType repairType = new RepairType();
        repairType.setMaster(repairTechnician);
        repairType.setDamage("Screen");
        repairType.setComments("Can repair");

        repairOrder1.setRepairType(repairType);
        repairService.addRepairOrder(repairOrder1);
        repairService.addRepairOrder(repairOrder2);

        PartsOrder partsOrder1 = new PartsOrder();
        partsOrder1.setCost(new BigDecimal("50.00"));
        partsOrder1.setWarehouse("Warsaw");
        String part = "Screen " + device1.getMade() + " " + device1.getModel();
        partsOrder1.addParts(part, 2);
        partsOrder1.removePart(part);
        repairOrder1.addPartsOrder(partsOrder1);

        repairOrder1.setComplete();
        repairOrder1.setIncomplete();
        // incomplete again

        DeliverOrder deliverOrder = new DeliverOrder();
        deliverOrder.setCustomer(customer1);
        repairOrder1.setDeliverOrder(deliverOrder);
        deliverOrder.setWeight(.3455);

        deliverOrder.setAddress(deliverOrderAddress);
        deliverOrder.setDeliveryDay(LocalDate.of(2024, 11, 15));
        repairService.addRepairedCount();
        repairService.addRepairedCount();

        RepairService repairService2 = new RepairService();
        repairService2.setName("Test");

        RepCorp corporation = null;
        try {
            corporation = new RepCorp("Fixed Enterprise", new BigDecimal("1000000000"), repairServiceAddress, 255., new BigDecimal("9000"));
        } catch (AddressNotFoundException | NegativeValueException e) {
            LOGGER.info(e.getMessage());
        }


        corporation.setAddress(corpAddress);
        corporation.addService(repairService);
        corporation.addService(repairService2);
        corporation.closeService(repairService2);
        LOGGER.info("Total repaired {}", corporation.getTotalRepaired());

        // compare devices

        LOGGER.info("{}", device2.equals(device2));
        LOGGER.info("{}", device2.equals(device1));
        Mobile device1Clone = new Mobile("Motorola", "G54");
        device1Clone.setNetworkType(NetworkType.FIVE_G);

        repairService.processOrder(repairOrder1);
        repairService.processOrder(deliverOrder);

        teamLead.setBonus(repairTechnician, new BigDecimal("501"));
        LOGGER.info("{}", repairTechnician.getBonus());
        // cant do it
        teamLead.setBonus(repairTechnician, new BigDecimal("500"));
        LOGGER.info("{}", repairTechnician.getBonus());

        accountant.setBonus(teamLead, new BigDecimal("1000"));
        accountant.setBonus(accountant, new BigDecimal("100"));
        // cant do it
        ExecutiveDirector executiveDirector = new ExecutiveDirector("Jeff", "Bezos", 60, JobPosition.EXECUTIVEDIRECTOR, "");
        repairService.setExecutiveDirector(executiveDirector);
        executiveDirector.setSalary(accountant, new BigDecimal("4000"));

        corporation.processRent(repairService, new BigDecimal("5012"));
        corporation.stopRent(repairService2);
        LOGGER.info("{}", device1.onCharging());
        device4.charge();
        LOGGER.info("{}", device4.onCharging());

        try {
            // Normal data
            LOGGER.info("The sum of invoice -> {}", AccountingProcesses.sumOfInvoice("src/main/resources/invoice.txt"));
            // Illegal format
//            LOGGER.info("The sum of invoice -> {}", AccountingProcesses.sumOfInvoice("src/main/resources/test format.txtt"));
            // wrong data
//            LOGGER.info("The sum of invoice -> {}", AccountingProcesses.sumOfInvoice("src/main/resources/wrongData.txt"));
        } catch (EmptyFileException e) {
            LOGGER.info(e.getMessage());
        } catch (CalculationRuntimeException except) {
            LOGGER.info("Not handled problem \nCould not finish the counting -> {}", except);
        }

        CustomLinkedList<String> linkedList = new CustomLinkedList<>();
        linkedList.add("1");
        linkedList.add("2");
        for (String val : linkedList) {
            LOGGER.info(val);
        }
        LOGGER.info("{}", Arrays.toString(linkedList.toArray()));

        linkedList.remove("1");
        LOGGER.info("{}", linkedList.getHead().getValue());
        LOGGER.info("{}", linkedList.getTail().getValue());
        LOGGER.info("{}", linkedList.getSize());
        LOGGER.info("{}", linkedList.isEmpty() + " 1");
        linkedList.remove("1");
        LOGGER.info("{}", linkedList.getSize() + " 2");
        LOGGER.info("{}", linkedList.isEmpty());
        linkedList.remove("2");
        LOGGER.info("{}", linkedList.getSize() + " 3");
        LOGGER.info("{}", linkedList.isEmpty());
        linkedList.add("1");
        linkedList.add("2");
        linkedList.add("3");
        LOGGER.info("{}", linkedList.getSize());
        linkedList.addAll(new ArrayList<>(Arrays.asList("4", "5", "6")));
        LOGGER.info("{}", linkedList);
        LOGGER.info("{}", linkedList.getSize());
        linkedList.addAll(linkedList.size(), new ArrayList<>(Arrays.asList("1", "1", "1")));
        LOGGER.info("{}", linkedList.getSize());
        linkedList.add(5, "10");
        LOGGER.info("{}", linkedList);
        LOGGER.info("{}", linkedList.getSize());
        linkedList.add(0, "10");
        LOGGER.info("{}", linkedList);
        LOGGER.info("{}", linkedList.getSize());
        linkedList.add(linkedList.size(), "10");

        LOGGER.info("{}", linkedList);
        LOGGER.info("{}", linkedList.getSize());
        linkedList.remove(11);
        LOGGER.info("{}", linkedList.lastIndexOf("10"));
        LOGGER.info("{}", linkedList);
        LOGGER.info("{}", linkedList.subList(1, 3));

        ListIterator<String> iterator = linkedList.listIterator();

        // Test hasNext and next
//        LOGGER.info("Iterating forward:");
//        while (iterator.hasNext()) {
//            LOGGER.info("Next: {}", iterator.next());
//        }
//        LOGGER.info("\nIterating backward:");
//        while (iterator.hasPrevious()) {
//            LOGGER.info("Previous: {}", iterator.previous());
//        }
//        iterator.add("Five");
//        iterator.set("Six");
//        while (iterator.hasNext()) {
//            LOGGER.info("Next: {}", iterator.next());
//        }
//
//        while (iterator.hasPrevious()) {
//            LOGGER.info("Previous: {}", iterator.previous());
//        }
//        Map<String, Integer> retTxt = UniqCounter.uniqWordsTxt("src/main/resources/Financier_1109.txt");
//        for (Map.Entry<String, Integer> entry : retTxt.entrySet()) {
//            LOGGER.info("{}: {}", entry.getKey(), entry.getValue());
//        }
//
//        Map<String, Integer> retPdf = UniqCounter.uniqWordsPdf("src/main/resources/Financier_1109.pdf");
//        for (Map.Entry<String, Integer> entry : retPdf.entrySet()) {
//            LOGGER.info("{}: {}", entry.getKey(), entry.getValue());
////        }
//        executiveDirector.setSalary(teamLead, new BigDecimal("20000"));
//        executiveDirector.setSalary(teamLead, new BigDecimal("2000"));
//        executiveDirector.setSalary(teamLead, new BigDecimal("5000"));

        // possible networks data
//        for (NetworkType networkType : NetworkType.values()) {
//            LOGGER.info("{} Max speed {}, median speed {}, delay {}", networkType,
//                    networkType.getMaxSpeedMb(), networkType.getMedianSpeedMb(), networkType.getDelayMs());
//        }

//        Consumer<Employee> addBonus = employee -> employee.setBonus(employee.getBonus().add(new BigDecimal("20")));
//        repairService.addBonusAll(addBonus);
//
//        // hard to find usage for Supplier, found in the internet random string
//        Supplier<String> randomString = () -> IntStream.range(0, new Random().nextInt(40))
//                .mapToObj(i -> (char) ('a' + new Random().nextInt(26)))
//                .map(val -> String.valueOf(val)) // or (String::valueOf) by ref
//                .collect(Collectors.joining());
//        LOGGER.info(randomString.get());


//        dekiveryMan.setBonus(BigDecimal.ZERO);
//        Predicate<Employee> hasNoBonus = employee -> employee.getBonus().equals(BigDecimal.ZERO);
//        repairService.getEmployees().forEach(employee ->
//                LOGGER.info(
//                        hasNoBonus.test(employee) ?
//                                String.format("Employee %s has no bonus", employee.getSurname()) :
//                                String.format("Employee %s has bonus", employee.getSurname())
//                )
//        );

//        Function<Employee, String> getSalary = employee ->
//                "Employee " + employee.getSurname() +
//                        " has total " + employee.getBonus()
//                        .add(employee.getSalary());
//        repairService.getEmployees().forEach(employee -> LOGGER.info(getSalary.apply(employee)));
//
//        List<Employee> employees = repairService.getEmployees();
//        //wrap in lambda
//        Runnable toCallLater = () -> employees.forEach(employee ->
//                LOGGER.info("From runnable {}", getSalary.apply(employee))
//        );

        // do something

//        toCallLater.run();
//
//        repairService.getOrders()
//                .stream()
//                .map(Objects::toString)
//                .forEach(System.out::println);
//        List<Employee> hiredEmployee = repairService.getEmployees().stream()
//                .filter(Employee::getHired)
//                .toList();
//        LOGGER.info(hiredEmployee.toString());
//
//        repairService.getOrders().stream()
//                .flatMap(repairOrder -> repairOrder.getDevices().stream())
//                .forEach(System.out::println);
//
//        List<Device> allDevices = corporation.getServices().stream()
//                .flatMap(service -> service.getOrders().stream())
//                .flatMap(repairOrder -> repairOrder.getDevices().stream())
//                .peek(device -> LOGGER.info("Found new Device {}", device))
//                .collect(Collectors.toCollection(ArrayList::new));
//
//        LOGGER.info("Result {}", allDevices);
//        BigDecimal totalPrice = corporation.getServices().stream()
//                .flatMap(service -> service.getOrders().stream())
//                .map(RepairOrder::getEstimateCost)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        LOGGER.info(totalPrice.toString());
//
//        Boolean allHired = repairService.getEmployees().stream()
//                .allMatch(Employee::getHired);
//        Boolean anyHired = repairService.getEmployees().stream()
//                .anyMatch(Employee::getHired);
//        Employee firstHired = repairService.getEmployees().getFirst();
//        LOGGER.info("\nAll hired {}\nAny hired {}\nFitst hired {}", allHired, anyHired, firstHired);
//        Order firstPaid = corporation.getServices().stream()
//                .flatMap(servive -> servive.getOrders().stream())
//                .filter(Order::getComplete)
//                .findFirst()
//                .orElse(null);
//        LOGGER.info(firstPaid != null ? "First paid order {}" : "No paid orders", firstPaid);
//
//        String theBiggestAddress = Addresses.getAllowedAddresses().stream()
//                .map(Address::getFullAddress)
//                .max(Comparator.comparingInt(String::length))
//                .orElse(null);
//        LOGGER.info("The biggest address -> {}", theBiggestAddress);
//
//        ReflectionExample.processReflection();
//
//        Thread thread = new Thread(() -> IntStream.range(1, 10).forEach(i -> {
//            LOGGER.info("Thread 1 -> {}", i);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                LOGGER.error(e.getMessage());
//            }
//        }));
//
//        // or
//        // will block parallel execution due to sync try operation
//
//        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
//            executorService.execute(() -> IntStream.range(100, 110).forEach(i -> {
//                LOGGER.info("Virtual Thread 1.5 -> {}", i);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                    LOGGER.error(e.getMessage());
//                }
//
//            }));
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
//        }
////
//
//        Runnable runnable = () -> IntStream.range(0, 10).forEach(i -> { // or new Runnable() @Override ..
//            LOGGER.info("Thread 2 -> {}", i);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                LOGGER.error(e.getMessage());
//            }
//        });
//
//
//        Thread thread1 = new Thread(thread);
//        Thread thread2 = new Thread(runnable);
//
//        ExecutorService executorServiceFirst = Executors.newVirtualThreadPerTaskExecutor();
//
//        executorServiceFirst.execute(() -> {
//            // LOGGER does not see the name for virtual threads
//            Thread.currentThread().setName("Virtual thread 1.5");
//            IntStream.range(100, 110).forEach(i -> {
//                LOGGER.info("Virtual Thread 1.5 -> {}", i);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                    LOGGER.error(e.getMessage());
//                }
//            });
//        });
//        thread1.start();
//        thread2.start();
//        executorServiceFirst.close();
////        // fixed executor pool
////
//        try (ExecutorService fixedExecutor = Executors.newFixedThreadPool(2)) {
//            fixedExecutor.execute(thread);
//            fixedExecutor.execute(runnable);
//        }
//
//        /*
//        or
//        fixedExecutor.execute(thread1);
//           fixedExecutor.execute(thread2);
//           */
//
//
//
//        CompletableFuture<String> firstTask = CompletableFuture.supplyAsync(() -> {
//            try {
//                LOGGER.info("Task 1 started");
//                Thread.sleep(500);
//                return "Task 2 is finished";
//            } catch (InterruptedException e) {
//                LOGGER.error(e.getMessage());
//                Thread.currentThread().interrupt();
//                return "Not finished";
//            }
//        });
//
//        firstTask.thenAccept(res -> LOGGER.info("The result of first task is -> {}", res));
//
//        CompletableFuture<Void> secondTask = CompletableFuture.runAsync(() -> {
//            try {
//                LOGGER.info("Task 2 Started");
//                Thread.sleep(1000);
//                LOGGER.info("Task 2 Finished");
//            } catch (InterruptedException e) {
//                LOGGER.error(e.getMessage());
//                Thread.currentThread().interrupt();
//            }
//        });
//
//        CompletableFuture<Void> thirdTask = CompletableFuture.runAsync(() -> {
//            try {
//                LOGGER.info("Task 3 Started");
//                Thread.sleep(2000);
//                LOGGER.info("Task 3 Finished");
//            } catch (InterruptedException e) {
//                LOGGER.error(e.getMessage());
//                Thread.currentThread().interrupt();
//            }
//        });
//
//        try {
//            firstTask.get();
//        } catch (InterruptedException | ExecutionException e) {
//            LOGGER.error(e.getMessage());
//        }
//        CompletableFuture<Void> otherTasks = CompletableFuture.allOf(secondTask, thirdTask);
//        try {
//            otherTasks.get();
//        } catch (InterruptedException | ExecutionException e) {
//            LOGGER.error(e.getMessage());
//        }
//
//        // for git conflict
//        for (int i = 0; i < 100; i++) {
//            LOGGER.info("{}", i);
//        }
//        LOGGER.info("{}", 1);
//        LOGGER.info("{}", 2);


        ConnectionPool connectionPool = ConnectionPool.getInstance();

        for (int i = 0; i < 7; i++) {
            Connection connection = connectionPool.getConnection();

            Runnable toRun = new Runnable() {
                @Override
                public void run() {
                    try {
                        connection.mockFunc();
                    } finally {
                        connectionPool.releaseConnection(connection);
                    }

                }
            };


            Thread connectionThread = new Thread(toRun);

            connectionThread.start();

        }
    }
}
