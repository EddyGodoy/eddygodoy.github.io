import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

public class ContactService {

  private String uniqueId;
  private Map<String, Contact> contactList = new HashMap<>();

  {
    setUniqueId(UUID.randomUUID().toString().substring(0, 10));
  }

  public void newContact() {
    Contact contact = new Contact(newUniqueId());
    contactList.put(contact.getContactId(), contact);
  }

  public void newContact(String firstname) {
    Contact contact = new Contact(newUniqueId(), firstname);
    contactList.put(contact.getContactId(), contact);
  }

  public void newContact(String firstname, String lastname) {
    Contact contact = new Contact(newUniqueId(), firstname, lastname);
    contactList.put(contact.getContactId(), contact);
  }

  public void newContact(String firstname, String lastname,
                         String phonenumber) {
    Contact contact =
        new Contact(newUniqueId(), firstname, lastname, phonenumber);
    contactList.put(contact.getContactId(), contact);
  }

  public void newContact(String firstname, String lastname, String phonenumber,
                         String address) {
    Contact contact =
        new Contact(newUniqueId(), firstname, lastname, phonenumber, address);
    contactList.put(contact.getContactId(), contact);
  }

  public void deleteContact(String id) throws Exception {
    if (!contactList.containsKey(id)) {
      throw new Exception("The Contact does not exist!");
    }
    contactList.remove(id);
  }

  public void updateFirstName(String id, String firstName) throws Exception {
    searchForContact(id).updateFirstName(firstName);
  }

  public void updateLastName(String id, String lastName) throws Exception {
    searchForContact(id).updateLastName(lastName);
  }

  public void updatePhoneNumber(String id, String phoneNumber)
      throws Exception {
    searchForContact(id).updatePhoneNumber(phoneNumber);
  }

  public void updateAddress(String id, String address) throws Exception {
    searchForContact(id).updateAddress(address);
  }

  protected Map<String, Contact> getContactList() {
    return contactList;
  }

  public Contact getContact(String id) throws Exception {
    return searchForContact(id);
  }

  private String newUniqueId() {
    return setUniqueId(UUID.randomUUID().toString().substring(0, 10));
  }

  private Contact searchForContact(String id) throws Exception {
    Contact contact = contactList.get(id);

    if (contact == null) {
      throw new Exception("The Contact does not exist!");
    }

    return contact;
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public String setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
    return uniqueId;
  }
}