#
# PARKING LOT PROJECT

This project will be focused on creating a parking lot like system. We'll have only one model class for cars in general which will have the time of entry and the license plate. 

## BEST PRACTICES IN THIS PROJECT 
Most of the best practices used here were already discussed in previous projects. Just adding here the ones not used before or adding more info on previous ones. 

## ENTRY DATE IN MODEL

The repository doesn't have the reponsibility of assigning the entry date. It just has to Store and retrieve data. 

If we had an ID, the logic to create the ID automatically (like in the previous project order-management), it should go on the constructor of the domain class, even if no-arg constructor.

## BIGDECIMAL
We use BigDecimal class in this project because it helps avoiding rounding errors that can happen with double or other numeric values. Usually used for money related processes or when we need precision. 

## CALCULATEFEE METHOD (SERVICE)

You can see in the service we have 2 methods. One is used for "orchestration", if we can call it that; we focus on getting the car, getting the fee and returning the DTO; these are too many responsibilities for a single method, if we want to change the logic, we'd need to change that whole method. By dividing it, we make that the ```calculateFeeAmount``` only has the purpose of returning the total fee and our other method only focuses on retreving information and returning. 