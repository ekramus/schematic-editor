# TODO #
  * _remember last action_
  * finalize units (mil, etc)
  * when finished element, be selected
  * delete using **del**
  * application look & feel
    * write info about sliders, etc
    * print info into status bar
  * SHIFT for angle locking
  * _transformation_
  * copy, paste
  * add possibility of dashed lines, etc
  * when element selected, two elements are displayed (original and working copy), but only one should be
  * edit elements
  * add support for groups
  * code clean up
  * javadoc
  * split code into modules according to functionality (**VectorGraphicsComponent module partially prepared**)
  * finalize vector editor

# Progress #
  * code polishment stopped in cz.cvut.fel.schematicEditor.application.guiElements.MenuBar.java
  * **correct arc - pie**
  * **correct arc - border only**
2008-05-09
  * **beziere curve fill strange behaviour (alpha not working)**
2008-05-10
  * **correct select**
  * **enable continuous move transformation**
  * **correct control behaviour (only works in right-down direction for Ellipse)**
  * **implement control for all elements, where it is applicable (ellipse, arc, rectangle)**
  * **delete**
2008-05-11
  * **isHit not correctly working for beziere**
  * **isHit not correctly working for rectangle**
  * **correct selection rectangle around selected element**
2008-05-12
  * **remove element duplication when moving** (removed implementing isDisabled(), otherwise repainting of whole scene will be required)
  * **correct bounding box for group (also working with line width)**
2008-05-18
  * **use units, not just pixels**
2008-05-20
  * **added Inches**
2008-05-22
  * **remember alpha when creating**
2008-05-26
  * **just the circle part of arc (new element)**