import org.junit.rules.ExternalResource;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class AppointmentTest {
  private Appointment firstAppointment;
  private Appointment secondAppointment;

  @Before
  public void initialize() {
    firstAppointment = new Appointment("08/17", "01:00", 1);
    secondAppointment = new Appointment("09/20", "02:00", 1);
  }

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void Appointment_instantiatesCorrectly_true() {
    assertEquals(true, firstAppointment instanceof Appointment);
  }

  @Test
  public void Appointment_instantiatesWithAppointmentDate_String() {
    assertEquals("08/17", firstAppointment.getDate());
  }

  @Test
  public void all_returnsAllInstancesOfAppointment_true() {
    firstAppointment.save();
    secondAppointment.save();
    assertTrue(Appointment.all().get(0).equals(firstAppointment));
    assertTrue(Appointment.all().get(1).equals(secondAppointment));
  }

  @Test
  public void getId_appointmentsInstantiateWithAnId() {
    firstAppointment.save();
    assertTrue(firstAppointment.getId() > 0);
  }

  @Test
  public void find_returnsAppointmentWithSameId_secondAppointment() {
    firstAppointment.save();
    secondAppointment.save();
    assertEquals(Appointment.findByAId(secondAppointment.getId()), secondAppointment);
  }

  @Test
  public void equals_returnsTrueIfAppointmentDatesAreTheSame() {
    Appointment myAppointment = new Appointment("08/17", "01:00", 1);
    assertTrue(firstAppointment.equals(myAppointment));
  }

  @Test
  public void save_returnsTrueIfAppointmentDatesAreTheSame() {
    firstAppointment.save();
    assertTrue(Appointment.all().get(0).equals(firstAppointment));
  }

  @Test
  public void save_assignsIdToObject() {
    firstAppointment.save();
    Appointment savedAppointment = Appointment.all().get(0);
    assertEquals(firstAppointment.getId(), savedAppointment.getId());
  }

  @Test
  public void save_savesAppointmentIdIntoDB_true() {
    Stylist myClient = new Stylist("Dolly Parton");
    myClient.save();
    Appointment myAppointment = new Appointment("08/17", "01:00", myClient.getId());
    myAppointment.save();
    Appointment savedAppointment = Appointment.findByAId(myAppointment.getId());
    assertEquals(savedAppointment.getClientId(), myClient.getId());
  }

  @Test
  public void delete_deletesAppointment_true() {
    firstAppointment.save();
    int firstAppointmentId = firstAppointment.getId();
    firstAppointment.delete();
    assertEquals(null, Appointment.findByAId(firstAppointmentId));
  }
}
