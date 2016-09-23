import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class StylistTest {
  private Stylist firstStylist;
  private Stylist secondStylist;

  @Before
  public void initialize() {
    firstStylist = new Stylist("Maureen Martin", "Color");
    secondStylist = new Stylist("Tyler Brown", "Bridal Style");
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Stylist_instantiatesCorrectly_true() {
    assertEquals(true, firstStylist instanceof Stylist);
  }

  @Test
  public void Stylist_instantiatesWithName_String() {
    assertEquals("Maureen Martin", firstStylist.getName());
  }

  @Test
  public void all_returnsAllInstancesOfStylist_true() {
    firstStylist.save();
    secondStylist.save();
    assertTrue(Stylist.all().get(0).equals(firstStylist));
    assertTrue(Stylist.all().get(1).equals(secondStylist));
  }

  @Test
  public void getId_tasksInstantiateWithAnId() {
    firstStylist.save();
    assertTrue(firstStylist.getId() > 0);
  }

  @Test
  public void find_returnsStylistWithSameId_secondStylist() {
    firstStylist.save();
    secondStylist.save();
    assertEquals(Stylist.find(secondStylist.getId()), secondStylist);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Stylist myStylist = new Stylist("Maureen Martin", "Color");
    assertTrue(firstStylist.equals(myStylist));
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    firstStylist.save();
    assertTrue(Stylist.all().get(0).equals(firstStylist));
  }

  @Test
  public void save_assignsIdToObject() {
    firstStylist.save();
    Stylist savedStylist = Stylist.all().get(0);
    assertEquals(firstStylist.getId(), savedStylist.getId());
  }

  @Test
  public void save_savesStylistIdIntoDB_true() {
    firstStylist.save();
    Stylist savedStylist = Stylist.find(firstStylist.getId());
    assertEquals(savedStylist.getId(), firstStylist.getId());
  }

  @Test
  public void updateStylistName_updatesStylistName_true() {
    firstStylist.save();
    firstStylist.updateStylistName("Mo");
    assertEquals("Mo", Stylist.find(firstStylist.getId()).getName());
  }

  @Test
  public void updateStylistSpecialty_updatesStylistSpecialty_true() {
    firstStylist.save();
    firstStylist.updateStylistSpecialty("Cuts");
    assertEquals("Cuts", Stylist.find(firstStylist.getId()).getSpecialty());
  }

  @Test
  public void getClients_retrievesAllClientsFromDatabase_ClientsList() {
    firstStylist.save();
    Client firstClient = new Client("Kathleen Turner", "503-333-3333", firstStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Dorian Grey", "503-444-4444", firstStylist.getId());
    secondClient.save();
    Client[] clients = new Client[] { firstClient, secondClient };
    assertTrue(firstStylist.getClients().containsAll(Arrays.asList(clients)));
  }

  @Test
  public void delete_deletesStylist_true() {
    firstStylist.save();
    int firstStylistId = firstStylist.getId();
    firstStylist.delete();
    assertEquals(null, Stylist.find(firstStylistId));
  }

}
