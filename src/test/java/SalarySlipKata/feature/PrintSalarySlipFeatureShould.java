package SalarySlipKata.feature;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import SalarySlipKata.Clock;
import SalarySlipKata.application_service.SalarySlipGenerator;
import SalarySlipKata.domain.EmployeeId;
import SalarySlipKata.domain_service.EmployeeRepository;
import SalarySlipKata.infrastructure.Console;
import SalarySlipKata.infrastructure.StandardSalarySlipPrinter;

public class PrintSalarySlipFeatureShould {

  private Console console = mock(Console.class);
  private Clock clock = mock(Clock.class);

  private SalarySlipGenerator salarySlipGenerator;
  private EmployeeRepository employeeRepository;
  private StandardSalarySlipPrinter standardSalarySlipPrinter;

  @Before
  public void initialise() {
    employeeRepository = new EmployeeRepository();
    standardSalarySlipPrinter = new StandardSalarySlipPrinter();
    salarySlipGenerator = new SalarySlipGenerator(standardSalarySlipPrinter);
  }

  @Test public void
  print_the_salary_slip_with_employee_details_and_basic_salary() {
    employeeRepository.addEmployee(new EmployeeId(12345), "John J Doe", 24000);
    salarySlipGenerator.printFor(new EmployeeId(12345), clock.todayAsString());

    verify(console).print(
      "Date: 01 Sep 2016             Salary for period: Sep 2016\n" +
      "\n" +
      "Employee ID: 12345            Employee Name: John J Doe\n" +
      "\n" +
      "EARNINGS                                  \n" +
      "Basic            £2000.00\n"
    );
  } 
}
