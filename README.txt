This is a web application which allows its users to track how many calories they consume.

In the src > main > resources > application.properties file
you need to set the spring.jpa.hibernate.ddl-auto to create instead of update when first running the
backend.
Then, after you run the backend once, you need to stop it, delete the target folder
and set spring.jpa.hibernate.ddl-auto to update.
Also, if you run the app while the configuration was on update and now you've changed it to create,
you need to delete the target folder.
In general, whenever you change anything in the application.properties file
you need to delete the target folder.