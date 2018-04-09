# LMS-INNO
How to run this project in your computer.

# 1 step

Download our database file

# 2 step

In class database write your url, user and password.
![alt text](https://cdn.pbrd.co/images/Hawx15Z.png)

If host its sql11.freemysqlhosting.net, then all works in our host, some test may work slowly.

# 3 step 

Download JDBC Driver and unzip it to somewhere
https://dev.mysql.com/downloads/connector/j/

# 4 step

Import it to your project
If you use IntelliJ IDEA, go to File -> Project Structure -> Libraries -> Click green plus -> find .jar file

# 5 step(not necessary)
If you want run third delivery tests, go to Run -> Edit Configurations -> Write to "VM options:" -ea

------------------

For deleting all information from db, use static method `Database.DeleteAllFromDBAndCreateLibrarian();`

------------------
If something dont work, please write me("@d_slvt")
