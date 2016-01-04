# project

This tool analyzes java code and generates a UML diagram.

Design: 
The tool expands upon the ASM class visitors discussed in class. The method visitor decorates the field visitor, which decorates the declaration visitor. As each class is visited, data container objects are created to store the required information about the class, its fields, and its methods. After the method has been visited by all of the visitors, its data is stored before moving onto the next argument. After all of the arguements have been examined, each of the created data containers is parsed into a form usable by GraphViz.

Responsibilities: 
All members worked together to created the project's design.
Jesse Shellabarger - Pair programmed with Nate Briggs. Primarily drove when creating the visitor pattern structure.
Nate Briggs - Pair programmed with Jesse Shellabarger. Primarily drove when parsing the data created into a form usable by GraphViz.
Thomas Bonatti - Wrote testing solo.

Instructions:
DesignParser is the class that drives the application. If running through eclipse, change the run configuration to include the desired 
arguments. If running through the command line, include the desired arguments as usual. The tool will then analyze the given classes and 
print out the data in a form usable by GraphViz. This output can either be given to GraphViz via the pipe operator, or by copy and pasting it into the GraphViz UI.
