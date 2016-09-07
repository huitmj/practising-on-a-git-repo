package SalarySlipKata.feature;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import SalarySlipKata.SalarySlipGenerator;
import SalarySlipKata.domain.EmployeeId;
import SalarySlipKata.infrastructure.Clock;
import SalarySlipKata.infrastructure.Console;
import SalarySlipKata.infrastructure.EmployeeRepository;
import SalarySlipKata.infrastructure.StandardSalarySlipPrinter;

public class PrintSalarySlipFeatureShould {
  private static final EmployeeId EMPLOYEE_ID_12345 = new EmployeeId(12345);

  private EmployeeRepository employeeRepository;
  private SalarySlipGenerator salarySlipGenerator;
  private Console console;
  private StandardSalarySlipPrinter standardSalarySlipGenerator;
  private Clock clock;

  @Before
  public void initialise() {
    employeeRepository = new EmployeeRepository();

    console = mock(Console.class);
    clock = mock(Clock.class);

    standardSalarySlipGenerator = new StandardSalarySlipPrinter(clock, console);
    salarySlipGenerator = new SalarySlipGenerator(employeeRepository, standardSalarySlipGenerator);
  }

  @Test public void
  print_salary_slip_for_an_employee() {
    when(clock.todayAsString()).thenReturn("01 Sep 2016");
    employeeRepository.addEmployee(EMPLOYEE_ID_12345, "John J Doe", 24000);

    salarySlipGenerator.printFor(EMPLOYEE_ID_12345, "Sep 2016");

    verify(console).print(
        "Date: 01 Sep 2016             Salary for period: Sep 2016\n" +
        "                                                         \n" +
        "Employee ID: 12345            Employee Name: John J Doe  \n" +
        "                                                         \n" +
        "EARNINGS                                                 \n" +
        "Basic            £2000.00                                \n"

    );
  }
}
