# Multimedia Image Effects
My solution to the image manipulation coursework set for the Multimedia module

## Coursework Instructions 

Write three static methods in Java: spiral, phaseShiftRed and phaseShiftMix, each of which manipulates a jpeg image. The point (x,y) denotes the position of the mouse pointer on the image that is displayed when ImageManipulation.java is running.
spiral moves/recolors (some of) the pixels in a specified square sub-area A(x,y) of an image; pixels outside of A(x,y) are not moved or recolored.

A(x,y) is a square with center (x,y) and corners (x-size, y-size), (x+size,y-size), (x+size, y+size), (x-size, y+size). In the case that A(x,y) is not entirely within a boundary of 10 pixels of the given image, the spiral method keeps the entire image unchanged.
Every pixel within A(x,y) with distance to (x,y) that is less than or equal to size and greater than or equal to n, will be rotated around the point (x,y). The rotation is ANTI-clockwise and the angle for a pixel at distance size from (x,y) will be 0, for pixels at distance n from (x,y) it will be +pi (ie +180 degrees), and for pixels in between the two distances the angle changes linearly (see formula below).

Every pixel within A(x,y) with distance to (x,y) that is greater than size should be colored pure red.
*** SEE LECTURE SLIDES *** Due to potential rounding errors your method should not go through each pixel in A(x,y) and "move" it to its new location/recolor it. It should, however, go through each pixel p in A(x,y), calculate which pixel p' should be "moved" to the location of p and then perform the change by setting the RGB of p to be the RGB of p'.

Hints:
The distance of a point (a,b) to the point (c,d) is the square root of (a-c)(a-c)+(b-d)(b-d).
In MATHEMATICAL coordinate geometry, a point (I,J) in R x R (R the reals) means "move I to the right and J up". A CLOCKWISE rotation of a point (I,J) around the origin (0,0) with angle alpha will result in the point (I*Math.cos(alpha) + J*Math.sin(alpha) , -I*Math.sin(alpha) + J*Math.cos(alpha) ). Note that p' above is computed from p, by ROTATING p clockwise.
The angle alpha by which a pixel is moved is equal to PI*(1-(d-n)/(size-n)), where d is distance of the pixel to the point (x,y). (Note that this is a LINEAR function of d, that is, it is of the form alpha = m*d + k for some m and k. Try out some graphs! Think about the case n=0.)

Note: Due to the rounding/integer division effects you should work with doubles and only convert to an int when pixel coordinates are returned.

phaseShiftRed and phaseShiftMix perform a shift of color in a specified area.
The specified area is a subset S of a square A(x,y) with center (x,y) and corners (x-size, y-size), (x+size,y-size), (x+size, y+size), (x-size, y+size). The subset S is the intersection of A(x,y) and the image; if A(x,y) lies completely within the image, then we colour shift all of A(x,y)

A pixel in S that is less than 2*n pixels away from any edge should not be changed. All other pixels of S will be changed in the following way:

For phaseShiftRed the new red component will be the red component of the pixel; the green and blue components will each be 0 (thus we are simply "selecting" the red component).

For phaseShiftMix the new red component will be the red component of the pixel that is n-pixels down. The green component will be 0. The blue component will be the blue component of the pixel that is n-pixels to the left.
