# Holidaymaker
small travel-company app project (built with SQL database and Java)

Notes: 
* Added possibility to display bookings (all at once, or by specific guest).
* Added possibility to display all registered guests in systems.
* added price calculations (if extra bed is chosen or if half/full-board is added and how many days)
* Added all features required to get G/VG-grade (at least according to me).
* Repetitive code: couldn't figure out hot to create a method to reuse all PreparedStatments, and ResultSets.
* Been testing for a good while. Everything seems to work correctly (there could still be load of error. Sorry! :) ).
* Many methods are quite long (due to the fact that I couldn't create methods to reuse code)
* All guests in database have lowercase letters in first_name and last_name.
This because of Java code - now you can enter names in console with lowercase, uppercase
or both without it crashing.
* Also added SQL diagram in SQL folder on github, so you can see how i thought when i created
MySQL database structure.
* Sometimes room search in the app returns very little results. This is because I might have needed to choose TINYINT
 instead of ENUM in asset_profile table, because when filtering by available pool, evening activities etc - many rooms stay hidden, since
 i have profile set to accommodation. Tried to make menu where you can either skip filtering by assets or not, but resulted 
 stackOverflow instead. Maybe you can give me a couple of tips how to surpass this, once we meet in school?  
* "Delete Guest" choice in main menu completely removes guest, and his/hers bookings. ("Cascade", "cascade" in database foreign keys.)
* Database probably has unnecessary views, but since all of them are related in one way or another - I've chosen not to remove them. 
