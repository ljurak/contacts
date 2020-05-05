# Contacts

Console app for contacts management offering creating, updating, removing and searching user contacts.
Application can optionally save and load data from a file.

## How to start

```bash
git clone https://github.com/ljurak/contacts.git
cd contacts
mvn clean package
cd target
java -jar contacts.jar [file_with_data] (e.g. java -jar contacts.jar contacts.db)
```