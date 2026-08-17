import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContactServiceTest {

  protected String contactId, firstNameTest, lastNameTest,
      phoneNumberTest, addressTest;

  protected String tooLongContactId, tooLongFirstName, tooLongLastName,
      tooLongPhoneNumber, tooShortPhoneNumber, tooLongAddress;

  @BeforeEach
  void setUp() {
    contactId = "10293A475F";
    firstNameTest = "Eddy";
    lastNameTest = "Godoy";
    phoneNumberTest = "5553331234";
    addressTest = "1 Dallas Rd Dallas TX 75010";
    tooLongContactId = "112233445566778899";
    tooLongFirstName = "Eddy Eddy Eddy";
    tooLongLastName = "Godoy Godoy Godoy";
    tooLongPhoneNumber = "55512341234";
    tooShortPhoneNumber = "1234567";
    tooLongAddress = "12345 Dallas Road Drive, Dallas, TX 75010";
  }

  @Test
  void newContactTest() throws Exception {
    ContactService service = new ContactService();

    service.newContact();

    String id = service.getUniqueId();
    Contact contact = service.getContact(id);

    assertAll(
        () -> assertNotNull(contact.getContactId()),
        () -> assertEquals("INITIAL", contact.getFirstName()),
        () -> assertEquals("INITIAL", contact.getLastName()),
        () -> assertEquals("1235559999", contact.getPhoneNumber()),
        () -> assertEquals("INITIAL", contact.getAddress()));
  }

  @Test
  void deleteContactTest() throws Exception {
    ContactService service = new ContactService();

    service.newContact();

    String id = service.getUniqueId();

    service.deleteContact(id);

    assertThrows(Exception.class, () -> service.getContact(id));
  }

  @Test
  void updateFirstNameTest() throws Exception {
    ContactService service = new ContactService();

    service.newContact();

    String id = service.getUniqueId();

    service.updateFirstName(id, firstNameTest);

    assertEquals(firstNameTest, service.getContact(id).getFirstName());

    assertThrows(IllegalArgumentException.class,
        () -> service.updateFirstName(id, tooLongFirstName));

    assertThrows(IllegalArgumentException.class,
        () -> service.updateFirstName(id, null));
  }

  @Test
  void updateLastNameTest() throws Exception {
    ContactService service = new ContactService();

    service.newContact();

    String id = service.getUniqueId();

    service.updateLastName(id, lastNameTest);

    assertEquals(lastNameTest, service.getContact(id).getLastName());

    assertThrows(IllegalArgumentException.class,
        () -> service.updateLastName(id, tooLongLastName));

    assertThrows(IllegalArgumentException.class,
        () -> service.updateLastName(id, null));
  }

  @Test
  void updatePhoneNumberTest() throws Exception {
    ContactService service = new ContactService();

    service.newContact();

    String id = service.getUniqueId();

    service.updatePhoneNumber(id, phoneNumberTest);

    assertEquals(phoneNumberTest,
        service.getContact(id).getPhoneNumber());

    assertThrows(IllegalArgumentException.class,
        () -> service.updatePhoneNumber(id, tooLongPhoneNumber));

    assertThrows(IllegalArgumentException.class,
        () -> service.updatePhoneNumber(id, tooShortPhoneNumber));

    assertThrows(IllegalArgumentException.class,
        () -> service.updatePhoneNumber(id, null));
  }

  @Test
  void updateAddressTest() throws Exception {
    ContactService service = new ContactService();

    service.newContact();

    String id = service.getUniqueId();

    service.updateAddress(id, addressTest);

    assertEquals(addressTest,
        service.getContact(id).getAddress());

    assertThrows(IllegalArgumentException.class,
        () -> service.updateAddress(id, tooLongAddress));

    assertThrows(IllegalArgumentException.class,
        () -> service.updateAddress(id, null));
  }
}