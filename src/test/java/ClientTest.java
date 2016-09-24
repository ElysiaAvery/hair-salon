import org.junit.rules.ExternalResource;
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
    firstClient = new Client("Kathleen Turner", 1);
    secondClient = new Client("Dorian Grey", 1);
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
  public void findById_returnsClientWithSameId_secondClient() {
    firstClient.save();
    secondClient.save();
    assertEquals(Client.findById(secondClient.getId()), secondClient);
  }

  @Test
  public void equals_returnsTrueIfClientNamesAreTheSame() {
    Client myClient = new Client("Kathleen Turner", 1);
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
    Stylist myStylist = new Stylist("Maureen Martin");
    myStylist.save();
    Client myClient = new Client("Kathleen Turner", myStylist.getId());
    myClient.save();
    Client savedClient = Client.findById(myClient.getId());
    assertEquals(savedClient.getStylistId(), myStylist.getId());
  }

  @Test
  public void updateClientName_updatesClientName_true() {
    firstClient.save();
    firstClient.updateClientName("Dolly Parton");
    assertEquals("Dolly Parton", Client.findById(firstClient.getId()).getName());
  }

  @Test
  public void delete_deletesClient_true() {
    firstClient.save();
    int firstClientId = firstClient.getId();
    firstClient.delete();
    assertEquals(null, Client.findById(firstClientId));
  }

}
