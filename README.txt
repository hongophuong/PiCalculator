Concept: Instead of put algorithm code into the Pi calculator, make a new Algorithm interface. Each algorithm implement this interface. 
User of PiCalculator make a instance of concrete algorithm class that he want to use. 
If it is required to have new Algorithm, we don't need to modify old code. we create new class that implement IAlgorithm.
I believe Strategy Pattern have been applied to this project to make it reuseable, open to expand and close to modify.