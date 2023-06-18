DELETE FROM students;

INSERT INTO students (id, name, email)
VALUES
   ('717b5473-3efe-4291-bbac-3ef2119f096a', 'Default Student', 'default@email.com'),
   ('717b5473-3efe-4291-bbac-3ef2119f011a', 'Student To Update', 'updateme@email.com'),
   ('717b5473-3efe-0000-bbac-0ef0000f000a', 'Student To Delete', 'deleteme@email.com');