# CMIS202_FinalProject
Who are the users of the software? 
--------------------------------------------------------------------------------------------------------------------
The users of this software are anyone who sells wine (whether they are a restaurant or a shop), as well as that vendor's
consumers. Vendors are able to categorize and display their inventory in an appealing and convenient format, and their
consumers are able to access, search, and sort that inventory so that they can make an informed choice on their purchase.
The favoriting capability of the software is also useful for both users, as consumers can keep track of what they like,
and vendors can see what their customer base is liking so that they can plan their inventory accordingly.

What is the purpose of the software? 
---------------------------------------------------------------------------------------------------------------
The purpose of this software is to provide a visually appealing and convenient database that is useful to both wine vendors
and the general consumer alike. Wine vendors have a way to easily display their inventory in a way that is eye-catching
and useful to their customer base, as well as see what it is their customer base is enjoying. Consumers can find exactly
what they are looking for, or even discover something new through the program's helpful search and sort algorithms.

Where and when will the software be used? 
----------------------------------------------------------------------------------------------------------
This software could be implemented as a kiosk at a shop, or even on a tablet at the table when ordering wine at a restaurant.
The software could also be implemented on a broader scale on a vendor's website.

How does the software work? 
---------------------------------------------------------------------------------------------------------------------------
The vendor (admin) adds wines through the "AddWinePane", which has several fields laid out for the wine such as color, grape,
region, sweetness, prices, tasting notes, etc. The vendor can also choose a photo for the wine, however there are default options 
for each wine color included in the software. All wines in the software are added to a Wine Tree, which is a binary search tree 
for wines. WineTree includes methods for searching for wines by name, color, grape, producer, and region, so that the consumer 
can search for any wine based on those fields. The Wine class implements Comparable and compares wines by name by default.
Comparators for wines are also defined so that wines can be sorted by color, grape, sweetness, price, or how many people have
favorited the wine. WineList is a linked list implementation for Wine objects, and includes its own implementation of mergeSort -
including one which uses Comparable and another which uest the above defined Comparators. The current wines to be displayed are
extracted from the Wine Tree to a Wine List using the Wine Tree's findBy methods, and the Wine List is sorted using mergeSort
according to the user's choice. Users (and admins) are all kept in a UserHashTable. Both the UserHashTable and Wine Tree load
in at the start of each program from a file. The Wine class keeps track of how many people have favorited a wine, and the User
class keeps track of the user's favorited wines in a WineList.

Why would anyone want to use the software over existing processes? 
------------------------------------------------------------------------------
This software is special in that it offers a customized database for the vendor 
that is unique to their inventory. It takes all of the heavy lifting out of 
categorizing and displaying their wines to their consumers, and even allows them
to have an inside look at what their customer base is thinking. Consumers will
love this program because they can search the inventory of their own local wine
vendor, or even figure out what to order at the table. The software keeps all the
information they need in one easily accessible spot.

![Main_WinePane_WineManagePane](https://github.com/ahorn275/CMIS202_FinalProject/assets/125283940/a8ae53e8-4adf-40fb-91a9-7ed2d6d1e411)
![CreateAccount_Welcome_NoWinesFound](https://github.com/ahorn275/CMIS202_FinalProject/assets/125283940/053109f6-c04e-4934-9e0a-c01ccea0261d)
![DataLoadThread_DataSaveThread](https://github.com/ahorn275/CMIS202_FinalProject/assets/125283940/b55f98b1-ac71-4b53-919e-024c84a04e51)
![UserClasses](https://github.com/ahorn275/CMIS202_FinalProject/assets/125283940/c905843d-7969-471d-9a4d-c9e8034f93d7)
![Wine_WineIterators](https://github.com/ahorn275/CMIS202_FinalProject/assets/125283940/c172c3cb-d3b8-48c1-b24d-55a848984d36)
![WineList_Tree_Node_Comparators](https://github.com/ahorn275/CMIS202_FinalProject/assets/125283940/2659e008-8fa4-4f0c-b9c5-eb017c1c35d9)
