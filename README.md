#Project

This tool analyzes java code and generates a UML diagram. It detects and draws all classes complete with their fields and methods. It also draws arrows for inheritance from other classes, implementation of interfaces, other used classes, and other associated classes. The Singleton and Decorator design patterns are also detected and notated.  

#Design: 
Milestone 1:
The tool expands upon the ASM class visitors discussed in class. The method visitor decorates the field visitor, which decorates the declaration visitor. As each class is visited, data container objects are created to store the required information about the class, its fields, and its methods. After the method has been visited by all of the visitors, its data is stored before moving onto the next argument. After all of the arguments have been examined, each of the created data containers is parsed into a form usable by GraphViz.

Milestone 2:
The design of the project is nearly identical. We added a MethodVisitor to analyze the bodies of methods, so that we could look for association. Our MethodVisitor class extends asm's method visitor and follows the previously discussed decorator and visitor patterns.

Milestone 3:
We have extended the design implemented in the previous milestones. A new parser was created to handle the sequence diagram format. We then created two new visitors to iterate over methods, using the visitor pattern, and gather the necessary information. We then parse this output into a form that is usable by SDEdit.

Milestone 4:
The design remained stable during this milestone. New visitors were added on top of existing visitors, and small modifications were made to the code that parses our constructed objects into graphViz strings.

Milestone 5:
Design was overhauled during this milestone. Our object representations of the classes now store their information using decorations. Information about the class's methods and fields are stored in the base UML class. Any further patterns detected are represented with decorators. These decorators are responsible for printing their necessary output to GraphViz 

Our decorator detection is implemented by searching over our representation of the classes and adding additional decorators where necessary.

#Responsibilities: 
Milestone 1:
All members worked together to created the project's design.
Jesse Shellabarger - Pair programmed with Nate Briggs. Primarily drove when creating the visitor pattern structure. Assisted with testing once the project was functional. Refactored and improved formatting. 
Nate Briggs - Pair programmed with Jesse Shellabarger. Primarily drove when parsing the data created into a form usable by GraphViz. 
Thomas Bonatti - Produced automated tests that establish the correct behavior of your ASM parsing code.

Milestone 2:
All members discussed changes to design together before any changes were made.
Jesse Shellabarger - Pair programmed with Thomas Bonatti to add functionality for use and association arrows.
Nate Briggs - Created tests to ensure functionality of added features. This includes unit tests as well as acceptance tests.
Thomas Bonatti - Pair programmed with Jesse Shellabarger to add functionality for use and association arrows.

Milestone 3:
Jesse Shellabarger - Pair Programmed with Thomas Bonatti to implement the visitor structure and the required methods. Also worked with Thomas to create this milestone's design.
Nate Briggs - Wrote the parsing algorithm to translate our gathered data into a form usable by SDEdit.
Thomas Bonatti - Pair Programmed with Jesse Shellabarger to implement the visitor structure and the required methods. Helped to create the design and to create the testing suite.

Milestone 4:
Jesse Shellabarger - Pair Programmed with Thomas Bonatti to implement SingletonVisitor classes, one to detect singleton "instance" fields, the other to detect singleton "getter" methods. Also handled producing UML samples.
Nate Briggs = Added singletons to the parsing code.
Thomas Bonatti - Worked with Jesse (see above). Programmed several test cases. Wrote this beautiful, elegant entry in the readme.

Milestone 5:
Jesse Shellabarger - Reworked Milestone 4, with Thomas, design to make the system more extendible. Designed and wrote decorator detection. Wrote documentation.
Nate Briggs - Assisted Jesse in writing decorator detection. Debugged GraphViz output.
Thomas Bonatti - Reworked Milestone 4, with Jesse, design to make the system more extendible. Assisted Jesse in designing decorator detection.


#Instructions:
UML Generation:
DesignParser is the class that drives the application. If running through eclipse, change the run configuration to include the desired 
arguments. If running through the command line, include the desired arguments as usual. The tool will then analyze the given classes and 
print out the data in a form usable by GraphViz. This output can either be given to GraphViz via the pipe operator, or by copy and pasting it into the GraphViz UI.

Sequence Diagram Generation:
SequenceParser is the class that drives the application. If running through eclipse, change the run configuration to include a fully qualified method as an argument. The second argument is optional, and determines how deep the program will examine method calls. After running, the program will output code that can be entered directly into SDEdit.

The system can be further extended by creating new classes to examine the class representation objects. Additional decorators can be added via the decorator pattern, if more information is needed.
