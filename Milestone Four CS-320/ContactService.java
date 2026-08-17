import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ContactService {

  private String uniqueId;
  private static final String DB_URL = "jdbc:sqlite:contacts.db";

  public ContactService() {
    setUniqueId(UUID.randomUUID().toString().substring(0, 10));
    createTable();
  }

  private Connection connect() throws SQLException {
    return DriverManager.getConnection(DB_URL);
  }

  private void createTable() {
    String sql =
        "CREATE TABLE IF NOT EXISTS contacts (" +
        "contact_id TEXT PRIMARY KEY, " +
        "first_name TEXT NOT NULL, " +
        "last_name TEXT NOT NULL, " +
        "phone_number TEXT NOT NULL, " +
        "address TEXT NOT NULL)";

    try (Connection connection = connect();
         PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void newContact() throws SQLException {
    addContact(new Contact(newUniqueId()));
  }

  public void newContact(String firstname) throws SQLException {
    addContact(new Contact(newUniqueId(), firstname));
  }

  public void newContact(String firstname, String lastname)
      throws SQLException {
    addContact(new Contact(newUniqueId(), firstname, lastname));
  }

  public void newContact(String firstname, String lastname,
                         String phonenumber) throws SQLException {
    addContact(
        new Contact(newUniqueId(), firstname, lastname, phonenumber));
  }

  public void newContact(String firstname, String lastname,
                         String phonenumber, String address)
      throws SQLException {
    addContact(
        new Contact(newUniqueId(), firstname, lastname,
                    phonenumber, address));
  }

  private void addContact(Contact contact) throws SQLException {
    String sql =
        "INSERT INTO contacts " +
        "(contact_id, first_name, last_name, phone_number, address) " +
        "VALUES (?, ?, ?, ?, ?)";

    try (Connection connection = connect();
         PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, contact.getContactId());
      statement.setString(2, contact.getFirstName());
      statement.setString(3, contact.getLastName());
      statement.setString(4, contact.getPhoneNumber());
      statement.setString(5, contact.getAddress());

      statement.executeUpdate();
    }
  }

  public void deleteContact(String id) throws Exception {
    String sql = "DELETE FROM contacts WHERE contact_id = ?";

    try (Connection connection = connect();
         PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, id);

      if (statement.executeUpdate() == 0) {
        throw new Exception("The Contact does not exist!");
      }
    }
  }

  public void updateFirstName(String id, String firstName)
      throws Exception {
    Contact contact = getContact(id);
    contact.updateFirstName(firstName);

    updateDatabase(
        "UPDATE contacts SET first_name = ? WHERE contact_id = ?",
        firstName, id);
  }

  public void updateLastName(String id, String lastName)
      throws Exception {
    Contact contact = getContact(id);
    contact.updateLastName(lastName);

    updateDatabase(
        "UPDATE contacts SET last_name = ? WHERE contact_id = ?",
        lastName, id);
  }

  public void updatePhoneNumber(String id, String phoneNumber)
      throws Exception {
    Contact contact = getContact(id);
    contact.updatePhoneNumber(phoneNumber);

    updateDatabase(
        "UPDATE contacts SET phone_number = ? WHERE contact_id = ?",
        phoneNumber, id);
  }

  public void updateAddress(String id, String address)
      throws Exception {
    Contact contact = getContact(id);
    contact.updateAddress(address);

    updateDatabase(
        "UPDATE contacts SET address = ? WHERE contact_id = ?",
        address, id);
  }

  private void updateDatabase(String sql, String value, String id)
      throws SQLException {

    try (Connection connection = connect();
         PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, value);
      statement.setString(2, id);

      statement.executeUpdate();
    }
  }

  public Contact getContact(String id) throws Exception {
    String sql =
        "SELECT contact_id, first_name, last_name, phone_number, address " +
        "FROM contacts WHERE contact_id = ?";

    try (Connection connection = connect();
         PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, id);

      try (ResultSet result = statement.executeQuery()) {

        if (!result.next()) {
          throw new Exception("The Contact does not exist!");
        }

        return new Contact(
            result.getString("contact_id"),
            result.getString("first_name"),
            result.getString("last_name"),
            result.getString("phone_number"),
            result.getString("address"));
      }
    }
  }

  private String newUniqueId() {
    return setUniqueId(
        UUID.randomUUID().toString().substring(0, 10));
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public String setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
    return uniqueId;
  }
}