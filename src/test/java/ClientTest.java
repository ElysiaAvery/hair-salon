import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class ClientTest {
  private Client firstClient;
  private Client secondClient;

  @Before
  public void initialize() {
    firstClient = new Client("Kathleen Turner", "503-333-3333", "Very Patient", 1);
    secondClient = new Client("Dorian Grey", "503-444-4444", "Very Picky", 1);
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Client_instantiatesCorrectly_true() {
    assertEquals(true, firstClient instanceof Client);
  }

  @Test
  public void Client_instantiatesWithClientName_String() {
    assertEquals("Kathleen Turner", firstClient.getName());
  }

  @Test
  public void all_returnsAllInstancesOfClient_true() {
    firstClient.save();
    secondClient.save();
    assertTrue(Client.all().get(0).equals(firstClient));
    assertTrue(Client.all().get(1).equals(secondClient));
  }

  @Test
  public void getId_clientsInstantiateWithAnId() {
    firstClient.save();
    assertTrue(firstClient.getId() > 0);
  }

  @Test
  public void find_returnsClientWithSameId_secondClient() {
    firstClient.save();
    secondClient.save();
    assertEquals(Client.find(secondClient.getId()), secondClient);
  }

  @Test
  public void equals_returnsTrueIfClientNamesAreTheSame() {
    Client myClient = new Client("Kathleen Turner", "503-333-3333", "Very Patient", 1);
    assertTrue(firstClient.equals(myClient));
  }

  @Test
  public void save_returnsTrueIfClientNamesAreTheSame() {
    firstClient.save();
    assertTrue(Client.all().get(0).equals(firstClient));
  }

  @Test
  public void save_assignsIdToObject() {
    firstClient.save();
    Client savedClient = Client.all().get(0);
    assertEquals(firstClient.getId(), savedClient.getId());
  }

  @Test
  public void save_savesClientIdIntoDB_true() {
    Stylist myStylist = new Stylist("Maureen Martin", "Color");
    myStylist.save();
    Client myClient = new Client("Kathleen Turner", "503-333-3333", "Very Patient", myStylist.getId());
    myClient.save();
    Client savedClient = Client.find(myClient.getId());
    assertEquals(savedClient.getStylistId(), myStylist.getId());
  }

  @Test
  public void updateClientName_updatesClientName_true() {
    firstClient.save();
    firstClient.updateClientName("Dolly Parton");
    assertEquals("Dolly Parton", Client.find(firstClient.getId()).getName());
  }

  @Test
  public void updateClientPhone_updatesClientPhone_true() {
    firstClient.save();
    firstClient.updateClientPhone("503-888-8888");
    assertEquals("503-888-8888", Client.find(firstClient.getId()).getPhone());
  }

  @Test
  public void updateClientNotes_updatesClientNotes_true() {
    firstClient.save();
    firstClient.updateClientNotes("Has very thick hair.");
    assertEquals("Has very thick hair.", Client.find(firstClient.getId()).getNotes());
  }

  @Test
  public void delete_deletesClient_true() {
    firstClient.save();
    int firstClientId = firstClient.getId();
    firstClient.delete();
    assertEquals(null, Client.find(firstClientId));
  }

}
