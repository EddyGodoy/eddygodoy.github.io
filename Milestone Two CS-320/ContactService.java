import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContactService {

  private String uniqueId;
  private List<Contact> contactList = new ArrayList<>();

  {
    setUniqueId(UUID.randomUUID().toString().substring(0, 10));
  }

  public void newContact() {
    addContact(new Contact(newUniqueId()));
  }

  public void newContact(String firstname) {
    addContact(new Contact(newUniqueId(), firstname));
  }

  public void newContact(String firstname, String lastname) {
    addContact(new Contact(newUniqueId(), firstname, lastname));
  }

  public void newContact(String firstname, String lastname,
                         String phonenumber) {
    addContact(
        new Contact(newUniqueId(), firstname, lastname, phonenumber));
  }

  public void newContact(String firstname, String lastname,
                         String phonenumber, String address) {
    addContact(
        new Contact(newUniqueId(), firstname, lastname,
                    phonenumber, address));
  }

  private void addContact(Contact contact) {
    contactList.add(contact);
  }

  public void deleteContact(String id) {
    Contact contact = searchForContact(id);
    contactList.remove(contact);
  }

  public void updateFirstName(String id, String firstName) {
    searchForContact(id).updateFirstName(firstName);
  }

  public void updateLastName(String id, String lastName) {
    searchForContact(id).updateLastName(lastName);
  }

  public void updatePhoneNumber(String id, String phoneNumber) {
    searchForContact(id).updatePhoneNumber(phoneNumber);
  }

  public void updateAddress(String id, String address) {
    searchForContact(id).updateAddress(address);
  }

  protected List<Contact> getContactList() {
    return contactList;
  }

  private String newUniqueId() {
    String id;

    do {
      id = UUID.randomUUID().toString().substring(0, 10);
    } while (contactExists(id));

    return setUniqueId(id);
  }

  private boolean contactExists(String id) {
    for (Contact contact : contactList) {
      if (id.equals(contact.getContactId())) {
        return true;
      }
    }

    return false;
  }

  private Contact searchForContact(String id) {
    for (Contact contact : contactList) {
      if (id.equals(contact.getContactId())) {
        return contact;
      }
    }

    throw new IllegalArgumentException("The Contact does not exist!");
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public String setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
    return uniqueId;
  }
}