#
# PARKING LOT PROJECT

This project will be focused on creating a parking lot like system. We'll have only one model class for cars in general which will have the time of entry and the license plate. 

## BEST PRACTICES IN THIS PROJECT 
Most of the best practices used here were already discussed in previous projects. Just adding here the ones not used before or adding more info on previous ones. 

## ENTRY DATE IN MODEL

The repository doesn't have the reponsibility of assigning the entry date. It just has to Store and retrieve data. 

If we had an ID, the logic to create the ID automatically (like in the previous project order-management), it should go on the constructor of the domain class, even if no-arg constructor.